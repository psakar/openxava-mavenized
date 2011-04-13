package org.openxava.web.taglib;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import org.apache.commons.logging.*;
import org.openxava.controller.meta.*;
import org.openxava.util.*;

/**
 * @author Javier Paniza
 */

public class ActionTag extends TagSupport {
	
	private static Log log = LogFactory.getLog(ActionTag.class);
	
	private IActionTag actionTag;
	private String action;
	private String argv;
	
	public int doStartTag() throws JspException {
		try {
			if (Is.emptyString(getAction())) {
				actionTag = null; 
				return SKIP_BODY;
			}

			MetaAction metaAction = MetaControllers.getMetaAction(getAction());
			if (metaAction.hasImage()) actionTag = new ImageTag();   
			else if(XavaPreferences.getInstance().isButtonsForNoImageActions()) actionTag = new ButtonTag();  
			else actionTag = new LinkTag(); 
			actionTag.setPageContext(pageContext);
			actionTag.setAction(action);
			actionTag.setArgv(argv);
			return actionTag.doStartTag();			
		}
		catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new JspException(XavaResources.getString("action_tag_error", getAction()));
		}		
	}

	public int doAfterBody() throws JspException { 
		return actionTag==null?super.doAfterBody():actionTag.doAfterBody();					
	}

	public int doEndTag() throws JspException { 
		return actionTag==null?super.doEndTag():actionTag.doEndTag();
	}

	public String getAction() {
		return action;
	}

	public void setAction(String string) {
		action = string;		
	}

	public String getArgv() {
		return argv;
	}

	public void setArgv(String string) {
		argv = string;
	}

}