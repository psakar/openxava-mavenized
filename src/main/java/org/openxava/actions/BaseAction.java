package org.openxava.actions;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.openxava.controller.*;
import org.openxava.controller.meta.*;
import org.openxava.util.*;
import org.openxava.web.DescriptionsLists;

/**
 * 
 * @author Javier Paniza
 */

abstract public class BaseAction implements IAction, IRequestAction, IModuleContextAction, IChangeModeAction, IChangeControllersAction {
	
	private Messages errors;
	private Messages messages;
	private Environment environment;
	private transient HttpServletRequest request;
	private ModuleContext context; 
	private String nextMode;
	private String [] nextControllers;
	
	public Messages getErrors() {
		return errors;
	}

	public void setErrors(Messages errors) {
		this.errors = errors;
	}
	
	public Messages getMessages() {
		return messages;
	}

	public void setMessages(Messages messages) {
		this.messages = messages;
	}
		
	protected void addErrors(Messages errors) {
		this.errors.add(errors);
	}
	
	protected void addError(String messageId) {
		errors.add(messageId);
	}
	
	protected void addError(String messageId, Object [] ids) {
		errors.add(messageId, ids);
	}
	
	protected void addError(String messageId, Object id0) {
		errors.add(messageId, new Object [] {id0});
	}
	
	protected void addError(String messageId, Object id0, Object id1) {
		errors.add(messageId, new Object [] {id0, id1});
	}
	
	protected void addError(String messageId, Object id0, Object id1, Object id2) {
		errors.add(messageId, new Object [] {id0, id1, id2});
	}
	
	protected void addError(String messageId, Object id0, Object id1, Object id2, Object id3) {
		errors.add(messageId, new Object [] {id0, id1, id2, id3});
	}
	
	protected void addError(String messageId, Object id0, Object id1, Object id2, Object id3, Object id4) {
		errors.add(messageId, new Object [] {id0, id1, id2, id3, id4});
	}
	
	protected void addError(String messageId, Object id0, Object id1, Object id2, Object id3, Object id4, Object id5) {
		errors.add(messageId, new Object [] {id0, id1, id2, id3, id4, id5});
	}
	
	protected void addMessages(Messages messages) {
		this.messages.add(messages);
	}
	
	protected void addMessage(String messajeId) {
		messages.add(messajeId);
	}
	
	/**
	 * If the id is a String between quotes (') will be displayed literally, untranslated 
	 */
	protected void addMessage(String messageId, Object [] ids) {
		messages.add(messageId, ids);
	}
	
	/**
	 * If the id is a String between quotes (') will be displayed literally, untranslated 
	 */
	protected void addMessage(String messageId, Object id0) {
		messages.add(messageId, new Object [] {id0});
	}
	
	/**
	 * If the id is a String between quotes (') will be displayed literally, untranslated 
	 */
	protected void addMessage(String messageId, Object id0, Object id1) {
		messages.add(messageId, new Object [] {id0, id1});
	}
	
	/**
	 * If the id is a String between quotes (') will be displayed literally, untranslated 
	 */
	protected void addMessage(String messageId, Object id0, Object id1, Object id2) {
		messages.add(messageId, new Object [] {id0, id1, id2});
	}
	
	/**
	 * If the id is a String between quotes (') will be displayed literally, untranslated 
	 */
	protected void addMessage(String messageId, Object id0, Object id1, Object id2, Object id3) {
		messages.add(messageId, new Object [] {id0, id1, id2, id3});
	}
	
	/**
	 * If the id is a String between quotes (') will be displayed literally, untranslated 
	 */
	protected void addMessage(String messageId, Object id0, Object id1, Object id2, Object id3, Object id4) {
		messages.add(messageId, new Object [] {id0, id1, id2, id3, id4});
	}
	
	/**
	 * If the id is a String between quotes (') will be displayed literally, untranslated 
	 */
	protected void addMessage(String messageId, Object id0, Object id1, Object id2, Object id3, Object id4, Object id5) {
		messages.add(messageId, new Object [] {id0, id1, id2, id3, id4, id5});
	}
	
		
	public void executeBefore() throws Exception {	
	}
	
	public void executeAfter() throws Exception {	
	}
	
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
	public Environment getEnvironment() {
		return environment;
	}
	
	/**
	 * Reset the cache of all descriptions-list and 
	 * others uses of descriptionsEditors.	 
	 */
	protected void resetDescriptionsCache() {
		DescriptionsLists.resetDescriptionsCache(request.getSession());		
	}
	
	public void setRequest(HttpServletRequest request) { 
		this.request= request;
	}
	
	/**
	 * With this method you can access directly to the
	 * web application resources, but it ties you to 
	 * implementation technology (servlets), hence it's 
	 * better to avoid it if you have alternative and 
	 * are thinking in migrating to another tecnology. 
	 * 
	 * @since 4m1
	 */
	protected HttpServletRequest getRequest() { 
		return this.request;
	}
	
	/**
	 * The Locale of the current request. <p> 
	 */
    protected Locale getLocale() {
        return Locales.getCurrent();
    }

	/**
	 * @since 4m1
	 */    
	protected ModuleContext getContext() {  
		if (context == null) throw new XavaException("context_null_in_action");
		return context;
	}

	public void setContext(ModuleContext context) {
		this.context = context;
	}

	/**
	 * @since 4m1
	 */
	public String getNextMode() {
		return nextMode;
	}

	/**
	 * @since 4m1
	 */	
	protected void setNextMode(String nextMode) {
		this.nextMode = nextMode;
	}

	/**
	 * @since 4m1
	 */	
	protected ModuleManager getManager() { 
		return (ModuleManager) getContext().get(request, "manager");
	}	
	
	/**
	 * Actions add added even if they are hidden. <p>
	 * 
	 * @since 4m2
	 */		
	protected void addActions(String ... qualifiedActions) { 
		for (String qualifiedAction: qualifiedActions) {
			if (Is.emptyString(qualifiedAction)) continue; 
			MetaAction action = MetaControllers.getMetaAction(qualifiedAction);
			if (action.isHidden()) { 
				action = action.cloneMetaAction();
				action.setHidden(false);
			}
			getManager().addMetaAction(action);
		}
	}
	
	/**
	 * @since 4m2
	 */		
	protected void removeActions(String ... qualifiedActions) { 
		for (String qualifiedAction: qualifiedActions) {
			getManager().removeMetaAction(MetaControllers.getMetaAction(qualifiedAction));
		}
	}

	/**
	 * @since 4m2
	 */		
	protected void clearActions() {		
		getManager().memorizeControllers();										
		getManager().setControllersNames(IChangeControllersAction.EMPTY_CONTROLLER);
	}
	
	/**
	 * @since 4m2
	 */	
	protected void setControllers(String ... controllers) {  
		nextControllers = controllers;
	}

	/**
	 * @since 4m2
	 */	
	protected void returnToPreviousControllers() { 
		setControllers(PREVIOUS_CONTROLLERS);
	}
	
	/**
	 * Set the default controllers of the current module. <p>
	 * 
	 * The defaults controllers are those declared in application.xml for the
	 * module, so the initial controllers when the module starts. <br>
	 * Also, this method clear the stack of controllers navigation, that is, if after
	 * calling to this method you call to {@link returnToPreviousController}() it will 
	 * not have effect.<br>
	 * 
	 * @since 4m2
	 */
	protected void setDefaultControllers() { 
		setControllers(DEFAULT_CONTROLLERS);
	}

	public String[] getNextControllers() throws Exception { 		
		return nextControllers;
	}
       
}
