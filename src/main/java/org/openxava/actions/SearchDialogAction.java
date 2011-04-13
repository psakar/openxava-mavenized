package org.openxava.actions;

import java.util.*;

/**
 * 
 * @author José Luis Dorado
 */

public class SearchDialogAction extends SearchByViewKeyAction {
	
	public void execute() throws Exception {
		Map values = getValuesFromView();
		closeDialog();
		getView().clear();		
		getView().setValues(values);
		super.execute();
	}
	
}