package org.openxava.actions;

import org.openxava.hibernate.*;
import org.openxava.jpa.*;
import org.openxava.util.*;

/**
 * @author Javier Paniza
 */

public class GenerateReportAction extends TabBaseAction implements IForwardAction {
	
	private String type;	

	public void execute() throws Exception {
		if (!("pdf".equals(getType()) || "csv".equals(getType()))) {
			throw new XavaException("report_type_not_supported", getType(), "pdf, csv");
		}
		getRequest().getSession().setAttribute("xava_reportTab", getTab());		
		getRequest().getSession().setAttribute("xava_selectedRowsReportTab", getTab().getSelected()); 
		if (!Is.emptyString(XHibernate.getDefaultSchema())) {
			getRequest().getSession().setAttribute("xava_hibernateDefaultSchemaTab", XHibernate.getDefaultSchema());
		}
		if (!Is.emptyString(XPersistence.getDefaultSchema())) {
			getRequest().getSession().setAttribute("xava_jpaDefaultSchemaTab", XPersistence.getDefaultSchema());
		}
	}
	
	public boolean inNewWindow() {
		return true;				
	}

	public String getForwardURI() {		
		return "/xava/list." + getType() + 
			"?application=" + getRequest().getParameter("application") +
			"&module=" + getRequest().getParameter("module") +
			"&time=" + System.currentTimeMillis();
	}

	public String getType() {
		return type;
	}

	public void setType(String string) {
		type = string;
	}

}
