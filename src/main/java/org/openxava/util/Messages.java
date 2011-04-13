package org.openxava.util;

import java.text.*;
import java.util.*;

import javax.servlet.*;



import org.apache.commons.logging.*;
import org.openxava.model.meta.*;


/**
 * Set of messages. <p>
 * 
 * Uses {@link XavaResources} for doing i18n.
 * 
 * @author Javier Paniza
 * @see XavaResources
 */

public class Messages implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static Log log = LogFactory.getLog(Messages.class);
	
	static class Message implements java.io.Serializable {
		private String id;
		private Object [] argv;
		
		public Message(String id, Object [] argv) {
			this.id = id;
			this.argv = argv;
		}
		
		public Message(String id) {
			this(id, null);
		}
		
		public String getId() {
			return id;
		}
				
		public String toString() {
			return toString(Locale.getDefault());
		}
		
		public boolean equals(Object other) {
			if (!(other instanceof Message)) return false;
			Message m = (Message) other;
			return id.equals(m.id);
		}
				
		public int hashCode() {			
			return id.hashCode();
		}
		
		public String toString(Locale locale) {
			try {
				String m = getMessage(id, locale);
				if (argv == null || argv.length == 0) return m;
				return format(m, translate(argv, locale), locale);
			}
			catch (Exception ex) {
				if (XavaPreferences.getInstance().isI18nWarnings()) {
					log.warn(XavaResources.getString("label_i18n_warning", id),ex);
				}
				return id;
			}
		}
				
		private Object[] translate(Object[] argv, Locale locale) {
			Object [] result = new Object[argv.length];
			for (int i = 0; i < argv.length; i++) {
				Object v = argv[i];
				if (v instanceof String) {
					if (v.toString().startsWith("'") && v.toString().endsWith("'")) {
						result[i] = v.toString().substring(1, v.toString().length() - 1);
					}
					else{
						try {
							try {		
								result[i] = Labels.removeUnderlined(Labels.get((String)v, locale));
							}
							catch (MissingResourceException ex) {
								result[i] = getMessage((String) v, locale); 
							}
						}
						catch (Exception ex) {
							result[i] = v;
						}	
					}
				}
				else {
					result[i] = v;
				}
			}			
			return result;
		}

		private String getMessage(String id, Locale locale) throws MissingResourceException, XavaException {
			return XavaResources.getString(locale, id);
		}
		
		private String format(String message, Object [] argv, Locale locale) {
			MessageFormat formatter = new MessageFormat("");
			formatter.setLocale(locale);
			formatter.applyPattern(message);
			return formatter.format(argv);		
		}
		
	}
	
	private Collection messages = new ArrayList();
	private Collection members;
	private boolean closed = false;	
	
	public void add(String idMessage) {
		if (closed) return;
		messages.add(new Message(idMessage));		
	}
	
	public boolean contains(String idMessage) {
		return messages.contains(new Message(idMessage));
	}
	
	public void remove(String idMessage) {
		messages.remove(new Message(idMessage));		
	}
	public void removeAll() {
		messages.clear();
		if (members != null) members.clear();
	}
	
	/**
	 * Clear all error message and does not accept any more messages. <p>
	 * 
	 * If you  call to <code>add</code> after call to this method then 
	 * no exception will throw but the message will not be added.<br>
	 *
	 */
	public void clearAndClose() {
		removeAll();
		closed = true;
	}
	
	public void add(String idMessage, Object [] ids) {
				
		if (closed) return;
		if (ids != null) {
			if (ids.length == 1) {
				addMember(ids[0]);
			}
			else if (ids.length > 1) {
				addMember(ids[0], ids[1]);
			} 
		}
		messages.add(new Message(idMessage, ids));
	}
	

	private void addMember(Object member) {
		addMember(member, null);
	}

	private void addMember(Object member, Object model) {
		if (member instanceof String) {
			Object id = null;
			if (model instanceof String) id = model + "." + member; 		
			else id = member;						
			if (members == null) members = new ArrayList();		
			members.add(id);
		}
	}

	public void add(String idMessage, Object id0) {		
		add(idMessage, new Object [] { id0 });
	}
	
	public void add(String idMessage, Object id0, Object id1) {		
		add(idMessage, new Object [] { id0, id1 });
	}
	
	public void add(String idMessage, Object id0, Object id1, Object id2) {
		add(idMessage, new Object [] { id0, id1, id2 });
	}
	
	public void add(String idMessage, Object id0, Object id1, Object id2, Object id3) {
		add(idMessage, new Object [] { id0, id1, id2, id3 });
	}
	
	public void add(String idMessage, Object id0, Object id1, Object id2, Object id3, Object id4) {
		add(idMessage, new Object [] { id0, id1, id2, id3, id4 });
	}
	
	public void add(String idMessage, Object id0, Object id1, Object id2, Object id3, Object id4, Object id5) {
		add(idMessage, new Object [] { id0, id1, id2, id3, id4, id5 });
	}
	
	public boolean contains() {
		return !messages.isEmpty();
	}
	
	public boolean isEmpty() {
		return messages.isEmpty();
	}
	
	public String toString() {
		Iterator it = messages.iterator();
		StringBuffer r = new StringBuffer();
		while (it.hasNext()) {			
			r.append(it.next());
			r.append('\n');
		}
		return r.toString();
	}
	
	public void add(Messages messages) {	
		if (closed) return;
		this.messages.addAll(messages.messages);		
		if (messages.members != null) {
			if (this.members == null) this.members = new ArrayList();
			this.members.addAll(messages.members);
		}
	}
	
	/**
	 * List of all message texts translated using the default locale.
	 */
	public Collection getStrings() {
		return getStrings(Locale.getDefault());
	}

	/**
	 * List of all message texts translated using the locale of the request.
	 */	
	public Collection getStrings(ServletRequest request) {
		return getStrings(XavaResources.getLocale(request));
	}

	/**
	 * List of all message texts translated using the indicated locale.
	 */	
	public Collection getStrings(Locale locale) {
		Iterator it = messages.iterator();
		Collection r = new ArrayList();
		while (it.hasNext()) {
			r.add(((Message) it.next()).toString(locale));
		}
		return r;
	}
	
	/**
	 * List of all ids of the messages
	 * @return
	 */
	public Collection getIds() {
		Iterator it = messages.iterator();
		Collection r = new ArrayList();
		while (it.hasNext()) {
			r.add(((Message) it.next()).getId());
		}
		return r;				
	}
	
	public boolean memberHas(MetaMember m) {
		if (members == null) return false;		
		if (m.getMetaModel() != null && members.contains(m.getMetaModel().getName() + "." + m.getName())) return true;
		return members.contains(m.getName());
	}
	
	/**
	 * Qualified names of the members affected for this errors. <p>  
	 */
	public Collection getMembers() {
		return members==null?Collections.EMPTY_LIST:members;
	}
	
}
