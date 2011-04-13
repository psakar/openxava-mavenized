package org.openxava.web.style;

import java.util.*;

import org.apache.commons.logging.*;
import org.openxava.util.*;

/**
 * This class and its subclasses is used from JSP code to give
 * style to the web applications. <p>
 * 
 * The nomenclature is:
 * <ul>
 * <li>By default: CSS class name.
 * <li>..Style: A CSS inline style.
 * <li>..Image: URI of image
 * <li>..Events: code for javascript events
 * <li>..StartDecoration/EndDecoration: HTML code to put before and after.
 * <li>..Spacing: Table spacing
 * </ul>
 * 
 * @author Javier Paniza
 */ 

public class Style {
	
	private static Collection<String> additionalCssFiles; 	
	private static Log log = LogFactory.getLog(Style.class);
	private static Style instance = null;
	private String cssFile; 
	private boolean insidePortal; 
	private String browser; 
	
	public Style() { 		
	}
	
	public static Style getInstance() {
		if (instance == null) {
			try {
				instance = (Style) Class.forName(XavaPreferences.getInstance().getStyleClass()).newInstance();
				instance.cssFile = XavaPreferences.getInstance().getStyleCSS();
			}
			catch (Exception ex) {
				log.warn(XavaResources.getString("default_style_warning"), ex); 					
				instance = new Style();
				instance.cssFile = "default.css";
			}			
		}
		return instance;
	}
	
	public String [] getNoPortalModuleJsFiles() { 
		return null;
	}
	
	protected String getJQueryCss() { 
		return "/xava/style/ui-lightness/jquery-ui.css";
	}

	protected Collection<String> createAdditionalCssFiles() {
		return Arrays.asList( 
			"/xava/editors/calendar/skins/aqua/theme.css", 
			"/xava/style/openxava.css",
			"/xava/style/yahoo-treeview/fonts/fonts-min.css",
			"/xava/style/yahoo-treeview/treeview.css",
			getJQueryCss()
		);
	}
	
	/**
	 * These css files will be always added, inside and outside portal. <p>
	 * 
	 * To refine it overwrite the {@link #createAdditionalCssFiles} method.<br>
	 */
	final public Collection<String> getAdditionalCssFiles() {
		if (additionalCssFiles == null) {
			additionalCssFiles = createAdditionalCssFiles();
		}
		return additionalCssFiles;
	}
	
	public String getInitThemeScript() { 
		return null;
	}
	
	public String getNoPortalModuleStartDecoration(String title) {
		return "";
	}
	
	public String getNoPortalModuleEndDecoration() {
		return "";
	}
	
	public String getCssFile() {
		return cssFile;
	}

	public String getEditorWrapper() { 
		return "";
	}
	
	public String getModule() {
		return "portlet-font";		
	}
	
	public String getModuleSpacing() {
		return "style='padding: 4px;'";		
	}
		
	public String getButtonBar() {
		return "ox-button-bar"; 
	}
	
	public String getButtonBarButton() { 
		return "ox-button-bar-button";
	}	
	
	public String getButtonBarModeButton() {
		return "ox-button-bar-button";
	}
	
	public String getButtonBarActiveModeButton() { 
		return "ox-button-bar-button active";
	}
							
	public String getDetail() {
		return "";
	}
			
	public String getList() {  
		return "list";
	}
		
	public String getListCellSpacing() {
		return ""; 
	}
	
	public String getListStyle() { 
		return ""; 
	}	
	
	public String getListCellStyle() {
		return ""; 
	}
	
	
	public String getListHeader() { 
		return "list";
	}
	
	public String getListHeaderCell() { 
		return getListHeader();
	}
	
	public String getListSubheader() {
		return "list-subheader";
	}	
	
	public String getListSubheaderCell() { 
		return getListSubheader();
	}
	
	public String getListOrderBy() {
		return "";
	}
	
	public String getListPair() { 
		return "list-pair";
	}
	
	/** 
	 * @param  Since v4m5 it has no parameters
	 */
	public String getListPairEvents() {  
		return "";
	}	
	
	public String getListPairCell() { 
		return getListPair();
	}
	
	public String getListOdd() { 
		return "list-odd";
	}
	
	/** 
	 * @param  Since v4m5 it has no parameters
	 */
	public String getListOddEvents() {   
		return "";
	}		
		
	public String getListOddCell() { 
		return getListOdd();
	}
	
	public String getListPairSelected() { 
		return "list-pair-selected";
	}
	
	public String getListOddSelected() { 
		return "list-odd-selected";
	}
		
	public String getListInfo() {
		return "list-info";
	}
	
	public String getListInfoDetail() { 
		return getListInfo() + " list-info-detail" + ((isIE7() || isIE6())?" ie7":""); 
		
	}
		
	public String getListTitle() {
		return "list-title";
	}
	
	public String getListTitleWrapper() {
		return "";
	}
	
	public String getFrameHeaderStartDecoration() {
		return getFrameHeaderStartDecoration(0); 
	}

	public String getFrameHeaderStartDecoration(int width) {  
		StringBuffer r = new StringBuffer();
		String widthAttribute = width == 0?"":"width=" + width+ "% ";
		r.append("<table ");
		r.append(widthAttribute);
		r.append(" class='");
		r.append(getFrame());
		r.append("' style='float:left; margin-right:4px'"); 
		r.append(getFrameSpacing());
		r.append(">");
		r.append("<tr class='");
		r.append(getFrameTitle());
		r.append("'>");
		r.append("<th class='");
		r.append(getFrameTitleLabel());
		r.append("'>\n");		
		return r.toString();
	}	
	public String getFrameHeaderEndDecoration() { 		
		return "</th></tr>";			
	}
	
	public String getFrameTitleStartDecoration() { 		
		return "<span style='float: left'>"; 		
	}	
	public String getFrameTitleEndDecoration() { 
		return "</span>";
	}
	public String getFrameActionsStartDecoration() { 
		return "<span style='float: right'>";
	}	
	public String getFrameActionsEndDecoration() { 
		return "</span>";
	}		
	
	public String getFrameContentStartDecoration() { 
		return getFrameContentStartDecoration(UUID.randomUUID().toString(), false);
	}
		
	public String getFrameContentStartDecoration(String id, boolean closed) { 
		StringBuffer r = new StringBuffer();		
		r.append("<tr id='");
		r.append(id);
		r.append("' ");
		if (closed) r.append("style='display: none;'");
		r.append("><td class='");
		r.append(getFrameContent());		
		r.append("'>\n");
		return r.toString();
	}
	
	
	public String getFrameContentEndDecoration() { 
		return "\n</td></tr></table>";
	}
	
	protected String getFrame() { 
		return "frame";
	}
	
	protected String getFrameTitle() {  
		return getFrame();
	}
	
	protected String getFrameTitleLabel() { 
		return getFrameTitle();
	}
				
	protected String getFrameContent() { 
		return getFrame();
	}
	
	protected String getFrameSpacing() { 
		return "";
	}
		
	public String getEditor() { 
		return "editor";
	}
	
	public String getLabel() { 
		return "portlet-form-field-label";
	}
	
	public String getSmallLabel() {
		return "small-label";
	}
		
	public String getErrors() { 
		return "errors";
	}
	
	public String getErrorStartDecoration () { 
		return "";
	}
	
	public String getErrorEndDecoration () { 
		return "";
	}
	
		

	public String getMessages() { 
		return "messages";
	}

	/**
	 * For messages and errors
	 */
	public String getMessagesWrapper() { 
		return "";
	}
	
	public String getMessageStartDecoration () { 
		return "";
	}
	
	public String getMessageEndDecoration () { 
		return "";
	}	
	
	public String getProcessing() { 
		return "processing";
	}
			
	public String getButton() {
		return "portlet-form-button";
	}
	
	public String getAscendingImage() {
		return "ascending.gif";
	}
	
	public String getDescendingImage() {
		return "descending.gif";
	}
	
	public String getAscending2Image() { 
		return "ascending2.gif";
	}
	
	public String getDescending2Image() { 
		return "descending2.gif";
	}	
	
	public String getSection() {
		return "Jetspeed";
	}
	
	public String getSectionTableAttributes() {
		return "border='0' cellpadding='0' cellspacing='0'";
	}
	
	public String getSectionActive() {
		return "activeSection";
	}	
	
	public String getSectionLink() {
		return null;
	}
	
	public String getSectionLinkStyle() {
		return null;
	}
		
	public String getSectionBarStartDecoration() {
		return "";
	}
	
	public String getSectionBarEndDecoration() {
		return "";
	}
			
	public String getActiveSectionTabStartDecoration() {
		return "<td class='TabLeft' nowrap='true'>&nbsp;</td><td class='TabMiddle' style='vertical-align: middle; text-align: center;' nowrap='true'>";
	}
	
	public String getActiveSectionTabEndDecoration() {
		return "</td><td class='TabRight' nowrap='true'>&nbsp;</td>";		
	}
	
	public String getSectionTabStartDecoration() {
		return "<td class='TabLeftLow' nowrap='true'>&nbsp;</td><td class='TabMiddleLow' style='vertical-align: middle; text-align: center;' nowrap='true'>";
	}
	
	public String getSectionTabEndDecoration() {
		return "</td><td class='TabRightLow' nowrap='true'>&nbsp;</td>";		
	}		
		
	public String getCollectionListActions() { 
		return getButtonBar(); 
	}
	
	/**
	 * If it starts with 'xava/' the context path is inserted before.
	 */
	public String getRestoreImage() {
		return "images/restore.gif";
	}

	/**
	 * If it starts with 'xava/' the context path is inserted before.
	 */
	
	public String getMaximizeImage() {
		return "images/maximize.gif";
	}
	
	/**
	 * If it starts with 'xava/' the context path is inserted before.
	 */
	public String getMinimizeImage() { 
		return "images/minimize.gif";
	}	

	/**
	 * If it starts with 'xava/' the context path is inserted before.
	 */	
	public String getRemoveImage() {
		return "images/remove.gif";
	}
	
	public String getLoadingModuleImage() {
		return "images/loading-module.gif";
	}
	
	/**
	 * If <code>true</code< the header in list is aligned as data displayed in its column. <p>
	 * 
	 * By default is <code>false</code> and it's used the portal default alignament for headers.
	 */
	public boolean isAlignHeaderAsData() {
		return false;
	}
	
	/**
	 * If <code>true</code> the style for selected row (or special style) in a list
	 * is applied to the row (tr) and to <b>also the cell</b> (td). <p>
	 * 
	 * If <code>false</code> the style is applied only to the row (tr).<p> 
	 *
     * By default is <code>true</code>.
	 */
	public boolean isApplySelectedStyleToCellInList() {
		return true;
	}
	
	public String getBottomButtonsStyle() {
		return "";
	}
	
	public boolean isNeededToIncludeCalendar() {
		return true;
	}
	
	public boolean isInsidePortal() {
		return insidePortal;
	}

	public void setInsidePortal(boolean insidePortal) {
		this.insidePortal = insidePortal;
	}

	public String getSelectedRow(){
		return "selected-row";
	}
	
	public String getSelectedRowStyle(){
		return "";
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getBrowser() {
		return browser;
	}
	
	protected boolean isFirefox() { 		
		return browser == null?false:browser.contains("Firefox");
	}
	
	/** @since 4m5 */
	protected boolean isIE6() { 		
		return browser == null?false:browser.contains("MSIE 6");
	}
	
	/** @since 4m5 */
	protected boolean isIE7() { 		
		return browser == null?false:browser.contains("MSIE 7");
	}
	
		
	/**
	 * @since 4m5
	 */
	public String getCurrentRow() {
		return "current-row"; 
	}
	
	/**
	 * @since 4m5
	 */
	public String getCurrentRowCell() {
		return ""; 
	}
	
	/**
	 * @since 4m5
	 */
	public String getPageNavigationArrowDisable() { 
		return "page-navigation page-navigation-arrow page-navigation-arrow-disable"; 
	}
	
	/**
	 * @since 4m5
	 */
	public String getPageNavigationSelected() { 		
		return "page-navigation-selected";
	}
	
	/**
	 * @since 4m5
	 */
	public String getPageNavigation() { 		
		return "page-navigation";
	}
	
	/**
	 * @since 4m5
	 */
	public String getPageNavigationArrow() { 		
		return "page-navigation page-navigation-arrow";
	}
	
	/**
	 * @since 4m5
	 */
	public String getRowsPerPage() { 		
		return "rows-per-page";
	}

	/**
	 * @since 4m6
	 */
	public String getHelpImage() {
		return "images/help.png";
	}
	
	/** @since 4m6 */
	final public String getActiveSectionTabStartDecoration(boolean first, boolean last) {
		if (first) {
			return getActiveSectionFirstTabStartDecoration();
		}
		else if (last){
			return getActiveSectionLastTabStartDecoration();
		}
		return getActiveSectionTabStartDecoration();
	}
	
	/** @since 4m6 */
	final public String getSectionTabStartDecoration(boolean first, boolean last) {
		if (first) {
			return getSectionFirstTabStartDecoration();
		}
		else if (last){
			return getSectionLastTabStartDecoration();
		}
		return getSectionTabStartDecoration();
	}
	
	/** @since 4m6 */
	public String getActiveSectionFirstTabStartDecoration() { 
		return getActiveSectionTabStartDecoration(); 
	} 
	
	/** @since 4m6 */
	public String getActiveSectionLastTabStartDecoration() { 
		return getActiveSectionTabStartDecoration(); 
	} 
	
	/** @since 4m6 */
	public String getSectionFirstTabStartDecoration() { 
		return getSectionTabStartDecoration(); 
	} 
	
	/** @since 4m6 */
	public String getSectionLastTabStartDecoration() { 
		return getSectionTabStartDecoration(); 
	}
		
	/**
	 * CSS class for the help icon, link or button. <p>
	 * 
	 * @since 4m6
	 */
	public String getHelp() {
		return "ox-help";
	}
	
	public String getTotalRow() { 
		return "";
	}
	
	public String getTotalCell() { 
		return "";
	}
	
	public String getTotalCellStyle() {
		return getTotalCellAlignStyle();
	}
	
	protected String getTotalCellAlignStyle() { 
		return "vertical-align: middle; text-align: right;	padding-right: 0px;";
	}
	
	public String getTotalEmptyCellStyle() { 
		return ""; 
	}
	
	public String getTotalCapableCellStyle() { 
		return getTotalEmptyCellStyle() + "vertical-align: top; text-align: right;	padding: 0px;";
	}
	
	
}