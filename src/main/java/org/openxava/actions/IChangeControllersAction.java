package org.openxava.actions;

/**
 * To indicate the next controllers to show. <p>
 * 
 * Given that {@link BaseAction) already implements it, you can just call to the 
 * setControllers(), returnToPreviousControllers(), setDefaultControllers(),  
 * addActions(), removeActions() and clearActions() methods from your action. 
 * 
 * @author Javier Paniza
 */

public interface IChangeControllersAction extends IAction {
				
	final static String [] EMPTY_CONTROLLER = new String [] {};
	final static String [] DEFAULT_CONTROLLERS = new String [] { "__DEFAULT_CONTROLLERS__" };	
	final static String [] SAME_CONTROLLERS = null;
	final static String [] PREVIOUS_CONTROLLERS = new String [] { "__PREVIOUS_CONTROLLERS__" };
	
	String [] getNextControllers() throws Exception;		

}
