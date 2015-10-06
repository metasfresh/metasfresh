/******************************************************************************
 * Product: Posterita Ajax UI 												  *
 * Copyright (C) 2007 Posterita Ltd.  All Rights Reserved.                    *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * Posterita Ltd., 3, Draper Avenue, Quatre Bornes, Mauritius                 *
 * or via info@posterita.org or http://www.posterita.org/                     *
 *****************************************************************************/

package org.adempiere.webui.panel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.model.tree.IPOTreeSupportFactory;
import org.adempiere.model.tree.spi.IPOTreeSupport;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.webui.LayoutUtils;
import org.adempiere.webui.component.Column;
import org.adempiere.webui.component.Columns;
import org.adempiere.webui.component.EditorBox;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.GridPanel;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.component.SimpleTreeModel;
import org.adempiere.webui.editor.IZoomableEditor;
import org.adempiere.webui.editor.WButtonEditor;
import org.adempiere.webui.editor.WEditor;
import org.adempiere.webui.editor.WEditorPopupMenu;
import org.adempiere.webui.editor.WebEditorFactory;
import org.adempiere.webui.event.ContextMenuListener;
import org.adempiere.webui.util.GridTabDataBinder;
import org.adempiere.webui.window.FDialog;
import org.compiere.model.DataStatusEvent;
import org.compiere.model.DataStatusListener;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.GridTabMaxRows;
import org.compiere.model.GridTable;
import org.compiere.model.GridWindow;
import org.compiere.model.MLookup;
import org.compiere.model.MTree;
import org.compiere.model.MTreeNode;
import org.compiere.model.X_AD_FieldGroup;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.zkoss.zk.au.out.AuFocus;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.West;
import org.zkoss.zul.Div;
import org.zkoss.zul.Group;
import org.zkoss.zul.Groupfoot;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Space;
import org.zkoss.zul.Treeitem;

/**
 *
 * This class is based on org.compiere.grid.GridController written by Jorg Janke.
 * Changes have been brought for UI compatibility.
 *
 * @author Jorg Janke
 *
 * @author <a href="mailto:agramdass@gmail.com">Ashley G Ramdass</a>
 * @date Feb 25, 2007
 * @version $Revision: 0.10 $
 *
 * @author Low Heng Sin
 */
public class ADTabpanel extends Div implements Evaluatee, EventListener,
DataStatusListener, IADTabpanel, VetoableChangeListener
{
	/**
	 * generated serial version ID
	 */
	private static final long serialVersionUID = 6945934489328360251L;

	private static final CLogger logger;

    static
    {
        logger = CLogger.getCLogger(ADTabpanel.class);
    }

    private GridTab           gridTab;

    @SuppressWarnings("unused")
	private GridWindow        gridWindow;

    private AbstractADWindowPanel      windowPanel;

    private int               windowNo;

    private Grid              grid;

    private ArrayList<WEditor> editors = new ArrayList<WEditor>();

    private ArrayList<String> editorIds = new ArrayList<String>();

    private boolean			  uiCreated = false;

    private GridPanel		  listPanel;

    private Map<String, List<org.zkoss.zul.Row>> fieldGroupContents = new HashMap<String, List<org.zkoss.zul.Row>>();

    private Map<String, List<org.zkoss.zul.Row>> fieldGroupHeaders = new HashMap<String, List<org.zkoss.zul.Row>>();

	private ArrayList<org.zkoss.zul.Row> rowList;

	private Component formComponent = null;

	private ADTreePanel treePanel = null;

	private GridTabDataBinder dataBinder;

	private Map<Integer, Group> includedTab = new HashMap<Integer, Group>();
	private Map<Integer, Groupfoot> includedTabFooter = new HashMap<Integer, Groupfoot>();

	private List<EmbeddedPanel> includedPanel = new ArrayList<EmbeddedPanel>();

	private boolean active = false;

	private Group currentGroup;

	private boolean m_vetoActive = false;

	public ADTabpanel()
	{
        init();
    }

    private void init()
    {
        initComponents();
    }

    private void initComponents()
    {
    	LayoutUtils.addSclass("adtab-content", this);

        grid = new Grid();
        //have problem moving the following out as css class
        grid.setWidth("100%");
        grid.setHeight("100%");
        grid.setVflex(true); // metas: 02714, 03118: let the browser calculate it's size
        grid.setStyle("margin:0; padding:0; position: absolute");
        grid.makeNoStrip();

        listPanel = new GridPanel();
        listPanel.getListbox().addEventListener(Events.ON_DOUBLE_CLICK, this);
    }

    /**
     *
     * @param winPanel
     * @param windowNo
     * @param gridTab
     * @param gridWindow
     */
    public void init(AbstractADWindowPanel winPanel, int windowNo, GridTab gridTab,
            GridWindow gridWindow)
    {
        this.windowNo = windowNo;
        this.gridWindow = gridWindow;
        this.gridTab = gridTab;
        this.windowPanel = winPanel;
        gridTab.addDataStatusListener(this);
        this.dataBinder = new GridTabDataBinder(gridTab);

        this.getChildren().clear();

		Borderlayout layout = new Borderlayout();
		layout.setParent(this);
		layout.setStyle("width: 100%; height: 100%; position: absolute;");
		
		final Div centerDiv = new Div();
		centerDiv.setStyle("width: 100%; height: 100%; position: absolute;");
		centerDiv.appendChild(grid);
		
		final Center center = new Center();
		center.setStyle("overflow : auto;"); // metas: 02714, 03118: display panel content's scrollbars if needed
		center.appendChild(centerDiv);
//		center.appendChild(grid);
		layout.appendChild(center);
		formComponent = grid;
		
        initTree();
		if (treePanel != null)
		{
			West west = new West();
			west.appendChild(treePanel);
			west.setWidth("300px");
			west.setCollapsible(true);
			west.setSplittable(true);
			west.setAutoscroll(true);
			layout.appendChild(west);

			center.setFlex(true);
		}
		
        centerDiv.appendChild(listPanel);
        listPanel.setVisible(false);
        listPanel.setWindowNo(windowNo);
        listPanel.setADWindowPanel(winPanel);
        
		layout.appendChild(center);

        gridTab.getTableModel().addVetoableChangeListener(this);
    }
    
    private void initTree()
    {
    	treePanel = null;
    	
		if (!gridTab.isTreeTab())
			return;
		
		int AD_Tree_ID = MTree.getDefaultAD_Tree_ID (Env.getAD_Client_ID(Env.getCtx()), gridTab.getKeyColumnName());
		if (AD_Tree_ID <= 0)
			return;

		treePanel = new ADTreePanel();
		treePanel.getTree().addEventListener(Events.ON_SELECT, this);
    }

    /**
     * Create UI components if not already created
     */
    public void createUI()
    {
    	if (uiCreated) return;

    	uiCreated = true;

    	//setup columns
    	Columns columns = new Columns();
    	grid.appendChild(columns);
    	Column col = new Column();
    	col.setWidth("14%");
    	columns.appendChild(col);
    	col = new Column();
    	col.setWidth("35%");
    	columns.appendChild(col);
    	col = new Column();
    	col.setWidth("14%");
    	columns.appendChild(col);
    	col = new Column();
    	col.setWidth("35%");
    	columns.appendChild(col);
    	col = new Column();
    	col.setWidth("2%");
    	columns.appendChild(col);

    	Rows rows = grid.newRows();
        GridField fields[] = gridTab.getFields();
        org.zkoss.zul.Row row = new Row();

        String currentFieldGroup = null;
        for (int i = 0; i < fields.length; i++)
        {
            GridField field = fields[i];
            if (field.isDisplayed())
            {
            	//included tab
            	if (field.getIncluded_Tab_ID() > 0)
            	{
            		if (row.getChildren().size() == 2)
        			{
        				row.appendChild(createSpacer());
                        row.appendChild(createSpacer());
                        row.appendChild(createSpacer());
                        rows.appendChild(row);
                        if (rowList != null)
            				rowList.add(row);
        			} else if (row.getChildren().size() > 0)
        			{
        				rows.appendChild(row);
        				if (rowList != null)
            				rowList.add(row);
        			}

            		//end current field group
            		if (currentGroup != null) {
            			row = new Groupfoot();
            			rows.appendChild(row);
            			currentGroup = null;
            			currentFieldGroup = null;
            		}

            		row = new Row();
            		row.setSpans("5");
        			row.appendChild(new Separator());
        			rows.appendChild(row);

            		row = new Group();
            		row.setSpans("2,3");
            		rows.appendChild(row);
            		includedTab.put(field.getIncluded_Tab_ID(), (Group)row);
            		row = new Groupfoot();
            		rows.appendChild(row);
            		includedTabFooter.put(field.getIncluded_Tab_ID(), (Groupfoot)row);

            		for (EmbeddedPanel ep : includedPanel) {
            			if (ep.adTabId == field.getIncluded_Tab_ID()) {
            				ep.group = includedTab.get(ep.adTabId);
            				createEmbeddedPanelUI(ep);
            				break;
            			}
            		}

            		row = new Row();
            		continue;
            	}

            	//normal field
            	String fieldGroup = field.getFieldGroup();
            	if (fieldGroup != null && fieldGroup.trim().length() > 0)
            	{
            		if (!fieldGroup.equals(currentFieldGroup))
            		{
            			currentFieldGroup = fieldGroup;
            			if (row.getChildren().size() == 2)
            			{
            				row.appendChild(createSpacer());
                            row.appendChild(createSpacer());
                            row.appendChild(createSpacer());
                            rows.appendChild(row);
                            if (rowList != null)
                				rowList.add(row);
                            row = new Row();
            			} else if (row.getChildren().size() > 0)
            			{
            				rows.appendChild(row);
            				if (rowList != null)
                				rowList.add(row);
            				row = new Row();
            			}

            			List<org.zkoss.zul.Row> headerRows = new ArrayList<org.zkoss.zul.Row>();
            			fieldGroupHeaders.put(fieldGroup, headerRows);

            			row.setSpans("5");
            			row.appendChild(new Separator());
            			rows.appendChild(row);
            			headerRows.add(row);

        				rowList = new ArrayList<org.zkoss.zul.Row>();
        				fieldGroupContents.put(fieldGroup, rowList);

            			if (X_AD_FieldGroup.FIELDGROUPTYPE_Label.equals(field.getFieldGroupType()))
            			{
            				row = new Row();
                			row.setSpans("4");
            				Label groupLabel = new Label(fieldGroup);
            				row.appendChild(groupLabel);
            				row.appendChild(createSpacer());
            				rows.appendChild(row);
            				headerRows.add(row);

            				row = new Row();
	                        row.setSpans("4");
	                        Separator separator = new Separator();
	                        separator.setBar(true);
	            			row.appendChild(separator);
	            			row.appendChild(createSpacer());
	            			rows.appendChild(row);
	            			headerRows.add(row);
            			}
            			else
            			{
            				row = new Group(fieldGroup);
            				if (X_AD_FieldGroup.FIELDGROUPTYPE_Tab.equals(field.getFieldGroupType()) || field.getIsCollapsedByDefault())
            				{
            					((Group)row).setOpen(false);
            				}
            				currentGroup = (Group)row;
            				rows.appendChild(row);
            				headerRows.add(row);
            			}

            			row = new Row();
            		}
            	}

                if (!field.isSameLine() || field.isLongField())
                {
                	//next line
                	if(row.getChildren().size() > 0)
                	{
	                    if (row.getChildren().size() == 2)
	                    {
	                        row.appendChild(createSpacer());
	                        row.appendChild(createSpacer());
	                        row.appendChild(createSpacer());
	                    }
	                    {
	                    	row.appendChild(createSpacer());
	                    }
	                    rows.appendChild(row);
	                    if (rowList != null)
	        				rowList.add(row);
	                    row = new Row();
                	}
                }
                else if (row.getChildren().size() == 4)
                {
                	//next line if reach max column ( 4 )
                	row.appendChild(createSpacer());
                	rows.appendChild(row);
                    if (rowList != null)
        				rowList.add(row);
                    row = new Row();
                }

                WEditor editor = WebEditorFactory.getEditor(gridTab, field, false);

                if (editor != null) // Not heading
                {
                    editor.setGridTab(this.getGridTab());
                	field.addPropertyChangeListener(editor);
                    editors.add(editor);
                    editorIds.add(editor.getComponent().getUuid());
                    if (field.isFieldOnly())
                    {
                    	row.appendChild(createSpacer());
                    }
                    else
                    {
                    	Div div = new Div();
                        div.setAlign("right");
                        Label label = editor.getLabel();
	                    div.appendChild(label);
	                    if (label.getDecorator() != null)
	                    	div.appendChild(label.getDecorator());
	                    row.appendChild(div);
                    }
                    row.appendChild(editor.getComponent());
                    if (field.isLongField()) {
                    	row.setSpans("1,3,1");
                    	row.appendChild(createSpacer());
                    	rows.appendChild(row);
                    	if (rowList != null)
            				rowList.add(row);
                    	row = new Row();
                    }

                    if (editor instanceof WButtonEditor)
                    {
                    	if (windowPanel != null)
                    		((WButtonEditor)editor).addActionListener(windowPanel);
                    }
                    else
                    {
                    	editor.addValueChangeListener(dataBinder);
                    }

                    //streach component to fill grid cell
                    editor.fillHorizontal();

                    //setup editor context menu
                    WEditorPopupMenu popupMenu = editor.getPopupMenu();
                    if (popupMenu != null)
                    {
                    	popupMenu.addMenuListener((ContextMenuListener)editor);
                        this.appendChild(popupMenu);
                        if (!field.isFieldOnly())
                        {
                        	Label label = editor.getLabel();
	                        if (popupMenu.isZoomEnabled() && editor instanceof IZoomableEditor)
	                        {
	                        	label.setStyle("cursor: pointer; text-decoration: underline;");
	                        	label.addEventListener(Events.ON_CLICK, new ZoomListener((IZoomableEditor) editor));
	                        }

	                        label.setContext(popupMenu.getId());
                        }
                    }
                }
                else if (field.isHeading())
                {
    				//display just a label if we are "heading only"
    				Label label = new Label(field.getHeader());
    				Div div = new Div();
    				div.setAlign("center");
    				row.appendChild(createSpacer());
    				div.appendChild(label);
    				row.appendChild(div);
    			}
            }
        }

        //last row
        if (row.getChildren().size() > 0)
        {
            if (row.getChildren().size() == 2)
            {
                row.appendChild(createSpacer());
                row.appendChild(createSpacer());
                row.appendChild(createSpacer());
            }
            rows.appendChild(row);
            if (rowList != null)
				rowList.add(row);
        }

        //create tree
        if (gridTab.isTreeTab() && treePanel != null) {
			int AD_Tree_ID = MTree.getDefaultAD_Tree_ID (
				Env.getAD_Client_ID(Env.getCtx()), gridTab.getKeyColumnName());
			treePanel.initTree(AD_Tree_ID, windowNo);
        }

        if (!gridTab.isSingleRow() && !isGridView())
        	switchRowPresentation();
    }

	private Component createSpacer() {
		return new Space();
	}

	/**
	 * Validate display properties of fields of current row
	 * @param col
	 */
    public void dynamicDisplay (int col)
    {
        if (!gridTab.isOpen())
        {
            return;
        }

        //  Selective
        if (col > 0)
        {
            GridField changedField = gridTab.getField(col);
            String columnName = changedField.getColumnName();
            ArrayList<?> dependants = gridTab.getDependantFields(columnName);
            logger.config("(" + gridTab.toString() + ") "
                + columnName + " - Dependents=" + dependants.size());
            if (dependants.size() == 0 && !gridTab.getCalloutExecutor().hasCallouts(changedField))
            {
                return;
            }
        }

        boolean noData = gridTab.getRowCount() == 0;
        logger.config(gridTab.toString() + " - Rows=" + gridTab.getRowCount());
        for (WEditor comp : editors)
        {
            GridField mField = comp.getGridField();
            if (mField != null && mField.getIncluded_Tab_ID() <= 0)
            {
                if (mField.isDisplayed(true))       //  check context
                {
                    if (!comp.isVisible())
                    {
                        comp.setVisible(true);      //  visibility
                    }
                    if (noData)
                    {
                        comp.setReadWrite(false);
                    }
                    else
                    {
                    	//comp.dynamicDisplay();
                        boolean rw = mField.isEditable(true);   //  r/w - check Context
                        comp.setReadWrite(rw);
                        comp.setMandatory(mField.isMandatory(true));    //  check context
                        
                        // metas: we need to refresh the component after we set all flags (see 04499)
                        comp.dynamicDisplay();
                    }
                }
                else if (comp.isVisible())
                {
                    comp.setVisible(false);
                }
                setColor(comp); // metas
            }
        }   //  all components

        //hide row if all editor within the row is invisible
        List<?> rows = grid.getRows().getChildren();
        for(int i = 0; i < rows.size(); i++)
        {
        	org.zkoss.zul.Row row = (org.zkoss.zul.Row) rows.get(i);
        	List<?> components = row.getChildren();
        	boolean visible = false;
        	boolean editorRow = false;
        	for (int j = 0; j < components.size(); j++)
        	{
        		Component component = (Component) components.get(j);
        		if (editorIds.contains(component.getUuid()))
        		{
        			editorRow = true;
        			if (component.isVisible())
        			{
        				visible = true;
        				break;
        			}
        		}
        	}
        	if (editorRow && (row.isVisible() != visible))
        		row.setVisible(visible);
        }

        //hide fieldgroup if all editor row within the fieldgroup is invisible
        for(Iterator<Entry<String, List<org.zkoss.zul.Row>>> i = fieldGroupHeaders.entrySet().iterator(); i.hasNext();)
        {
        	Map.Entry<String, List<org.zkoss.zul.Row>> entry = i.next();
        	List<org.zkoss.zul.Row> contents = fieldGroupContents.get(entry.getKey());
        	boolean visible = false;
        	for (org.zkoss.zul.Row row : contents)
        	{
        		if (row.isVisible())
        		{
        			visible = true;
        			break;
        		}
        	}
        	List<org.zkoss.zul.Row> headers = entry.getValue();
        	for(org.zkoss.zul.Row row : headers)
        	{
        		if (row.isVisible() != visible)
        			row.setVisible(visible);
        	}
        }

        logger.config(gridTab.toString() + " - fini - " + (col<=0 ? "complete" : "seletive"));
    }   //  dynamicDisplay

    /**
     * @return String
     */
    public ILogicExpression getDisplayLogic()
    {
        return gridTab.getDisplayLogic();
    }

    /**
     * @return String
     */
    public String getTitle()
    {
        return gridTab.getName();
    } // getTitle

    /**
     * @param variableName
     */
    public String get_ValueAsString(String variableName)
    {
        return Env.getContext(Env.getCtx(), windowNo, variableName);
    } // get_ValueAsString

    /**
     * @return The tab level of this Tabpanel
     */
    public int getTabLevel()
    {
        return gridTab.getTabLevel();
    }

    /**
     * Is panel need refresh
     * @return boolean
     */
    public boolean isCurrent()
    {
        return gridTab != null ? gridTab.isCurrent() : false;
    }

    /**
     *
     * @return windowNo
     */
    public int getWindowNo()
    {
        return windowNo;
    }

    /**
     * Retrieve from db
     */
    public void query()
    {
    	boolean open = gridTab.isOpen();
        gridTab.query(false);
        if (listPanel.isVisible() && !open)
        	gridTab.getTableModel().fireTableDataChanged();
        if (treePanel != null) treePanel.filterIds(gridTab.getKeyIDs()); // metas
    }

    /**
     * Retrieve from db
     * @param onlyCurrentRows
     * @param onlyCurrentDays
     * @param maxRows
     */
    @Override
	public void query (final boolean onlyCurrentRows, final int onlyCurrentDays, final GridTabMaxRows maxRows)
    {
    	boolean open = gridTab.isOpen();
        gridTab.query(onlyCurrentRows, onlyCurrentDays, maxRows);
        if (listPanel.isVisible() && !open)
        	gridTab.getTableModel().fireTableDataChanged();
        if (treePanel != null) treePanel.filterIds(gridTab.getKeyIDs()); // metas
    }

    /**
     * @return GridTab
     */
    @Override
	public GridTab getGridTab()
    {
        return gridTab;
    }

    /**
     * Refresh current row
     */
    public void refresh()
    {
        gridTab.dataRefresh();
    }

    /**
     * Activate/deactivate panel
     * @param activate
     */
    public void activate(boolean activate)
    {
    	active = activate;
        if (listPanel.isVisible()) {
        	if (activate)
        		listPanel.activate(gridTab);
        	else
        		listPanel.deactivate();
        } else {
        	if (activate) {
        		formComponent.setVisible(activate);
        		setFocusToField();
        	}
        }

        //activate embedded panel
        for(EmbeddedPanel ep : includedPanel)
        {
        	activateChild(activate, ep);
        }
    }

	private void activateChild(boolean activate, EmbeddedPanel panel) {
		if (activate)
		{
			panel.windowPanel.getADTab().evaluate(null);
			panel.windowPanel.getADTab().setSelectedIndex(0);
			panel.tabPanel.query(false, 0, GridTabMaxRows.NO_RESTRICTION);
		}
		panel.tabPanel.activate(activate);
	}

	/**
	 * set focus to first active editor
	 */
	private void setFocusToField() {
		WEditor toFocus = null;
		for (WEditor editor : editors) {
			if (editor.isHasFocus() && editor.isVisible() && editor.getComponent().getParent() != null) {
				toFocus = editor;
				break;
			}

			if (toFocus == null) {
				if (editor.isVisible() && editor.isReadWrite() && editor.getComponent().getParent() != null) {
					toFocus = editor;
				}
			}
		}
		if (toFocus != null) {
			Component c = toFocus.getComponent();
			if (c instanceof EditorBox) {
				c = ((EditorBox)c).getTextbox();
			}
			Clients.response(new AuFocus(c));
		}
	}

    /**
     * @param event
     * @see EventListener#onEvent(Event)
     */
    public void onEvent(Event event)
    {
    	if (event.getTarget() == listPanel.getListbox())
    	{
    		this.switchRowPresentation();
    	}
    	else if (event.getTarget() == treePanel.getTree()) {
    		Treeitem item =  treePanel.getTree().getSelectedItem();
    		navigateTo((MTreeNode)item.getValue());
    	}
    }

    private void navigateTo(MTreeNode value) {
    	MTreeNode treeNode = value; //(MTreeNode) value.getData();
    	//  We Have a TreeNode
		int nodeID = treeNode.getNode_ID();
		//  root of tree selected - ignore
		//if (nodeID == 0)
			//return;

		//  Search all rows for mode id
		int size = gridTab.getRowCount();
		int row = -1;
		for (int i = 0; i < size; i++)
		{
			if (gridTab.getKeyID(i) == nodeID)
			{
				row = i;
				break;
			}
		}
		if (row == -1)
		{
			if (nodeID > 0)
				logger.log(Level.WARNING, "Tab does not have ID with Node_ID=" + nodeID);
			return;
		}

		//  Navigate to node row
		gridTab.navigate(row);
	}

    /**
     * @param e
     * @see DataStatusListener#dataStatusChanged(DataStatusEvent)
     */
	public void dataStatusChanged(DataStatusEvent e)
    {
    	//ignore background event
    	if (Executions.getCurrent() == null) return;
    	
    	// metas: cg: task 03475 start : check for errors, warnings
		//	Set Message / Info
    	StringBuffer sb = new StringBuffer();
		if (e.getAD_Message() != null || e.getInfo() != null)
		{
			String msg = e.getMessage();
			if (msg != null && msg.length() > 0)
				sb.append(Services.get(IMsgBL.class).getMsg(Env.getCtx(), e.getAD_Message()));
			String info = e.getInfo();
			if (info != null && info.length() > 0)
			{
				if (sb.length() > 0 && !sb.toString().trim().endsWith(":"))
					sb.append(": ");
				sb.append(info);
			}
			if (sb.length() > 0)
			{
				int pos = sb.indexOf("\n");
				if (pos != -1)  // replace CR/NL
					sb.replace(pos, pos+1, " - ");
			}
		}

		//  Confirm Error
		if (e.isError() && !e.isConfirmed())
		{
			FDialog.error(windowNo, sb.toString());
			e.setConfirmed(true);   //  show just once - if MTable.setCurrentRow is involved the status event is re-issued
		}
		//  Confirm Warning
		else if (e.isWarning() && !e.isConfirmed())
		{
			FDialog.error(windowNo, sb.toString());
			e.setConfirmed(true);   //  show just once - if MTable.setCurrentRow is involved the status event is re-issued
		}
		// metas: cg: task 03475 end 
    	
        int col = e.getChangedColumn();
        logger.config("(" + gridTab + ") Col=" + col + ": " + e.toString());

        //  Process Callout
        final GridField mField = gridTab.getField(col);
		if (mField != null) 
		{
			//  Dependencies & Callout
			final String msg = gridTab.processFieldChange(mField, false); // force=false
            if (msg.length() > 0)
            {
                FDialog.error(windowNo, this, msg);
            }

            // Refresh the list on dependant fields
    		final List<GridField> list = gridTab.getDependantFields(mField.getColumnName());
    		for (int i = 0; i < list.size(); i++)
    		{
    			final GridField dependentField = list.get(i);
    		//	log.trace(log.l5_DData, "Dependent Field", dependentField==null ? "null" : dependentField.getColumnName());
    			//  if the field has a lookup
    			if (dependentField != null && dependentField.getLookup() instanceof MLookup)
    			{
    				MLookup mLookup = (MLookup)dependentField.getLookup();
    				//  if the lookup is dynamic (i.e. contains this columnName as variable)
    				if (mLookup.getParameters().contains(mField.getColumnName()))
    				//if (mLookup.getValidation().indexOf("@"+mField.getColumnName()+"@") != -1)
    				{
    					mLookup.refresh();
    				}
    			}
    		}   //  for all dependent fields

        }
        //if (col >= 0)
        if (!uiCreated)
        	createUI();
        dynamicDisplay(col);

        //sync tree
        if (treePanel != null) {
        	if ("Deleted".equalsIgnoreCase(e.getAD_Message()))
        		if (e.Record_ID != null
        				&& e.Record_ID instanceof Integer
        				&& ((Integer)e.Record_ID != gridTab.getRecord_ID()))
        			deleteNode((Integer)e.Record_ID);
        		else
        			setSelectedNode(gridTab.getRecord_ID());
        	else
        		setSelectedNode(gridTab.getRecord_ID());
        }

        if (listPanel.isVisible()) {
        	listPanel.updateListIndex();
        	listPanel.dynamicDisplay(col);
        }

        if (!includedPanel.isEmpty()) {
        	for (EmbeddedPanel panel : includedPanel)
        		panel.tabPanel.query(false, 0, GridTabMaxRows.NO_RESTRICTION);
        }

    }

    private void deleteNode(int recordId) {
		if (recordId <= 0) return;

		SimpleTreeModel model = (SimpleTreeModel) treePanel.getTree().getModel();

		if (treePanel.getTree().getSelectedItem() != null) {
			MTreeNode treeNode = (MTreeNode) treePanel.getTree().getSelectedItem().getValue();
//			MTreeNode data = (MTreeNode) treeNode.getData();
			if (treeNode.getNode_ID() == recordId) {
				model.removeNode(treeNode);
				return;
			}
		}

		MTreeNode treeNode = model.find(null, recordId);
		if (treeNode != null) {
			model.removeNode(treeNode);
		}
	}

	private void addNewNode() 
	{
    	if (gridTab.getRecord_ID() > 0) 
    	{
    		final IPOTreeSupport poTreeSupport = Services.get(IPOTreeSupportFactory.class).get(gridTab.getTableName());
			//
			SimpleTreeModel model = (SimpleTreeModel) treePanel.getTree().getModel();
			MTreeNode parent = model.getRoot();
			MTreeNode node = poTreeSupport.getNodeInfo(gridTab);
			model.addNode(parent, node, parent.getChildCount());
//			root.add(node);
			int[] path = model.getPath(model.getRoot(), node);
			Treeitem ti = treePanel.getTree().renderItemByPath(path);
			treePanel.getTree().setSelectedItem(ti);
    	}
	}

	private void setSelectedNode(int recordId) {
		if (recordId <= 0) return;

		if (treePanel.getTree().getSelectedItem() != null) {
			MTreeNode treeNode = (MTreeNode) treePanel.getTree().getSelectedItem().getValue();
//			MTreeNode data = (MTreeNode) treeNode.getData();
			if (treeNode.getNode_ID() == recordId) return;
		}

		SimpleTreeModel model = (SimpleTreeModel) treePanel.getTree().getModel();
		MTreeNode treeNode = model.find(null, recordId);
		if (treeNode != null) {
			int[] path = model.getPath(model.getRoot(), treeNode);
			Treeitem ti = treePanel.getTree().renderItemByPath(path);
			treePanel.getTree().setSelectedItem(ti);
		} else {
			addNewNode();
		}
	}

	/**
	 * Toggle between form and grid view
	 */
	public void switchRowPresentation() {
		if (formComponent.isVisible()) {
			formComponent.setVisible(false);
			//de-activate embedded panel
	        for(EmbeddedPanel ep : includedPanel)
	        {
	        	activateChild(false, ep);
	        }
		} else {
			formComponent.setVisible(true);
			//activate embedded panel
	        for(EmbeddedPanel ep : includedPanel)
	        {
	        	activateChild(true, ep);
	        }
		}
		listPanel.setVisible(!formComponent.isVisible());
		if (listPanel.isVisible()) {
			listPanel.refresh(gridTab);
			listPanel.scrollToCurrentRow();
		} else {
			listPanel.deactivate();
		}
	}

	class ZoomListener implements EventListener {

		private IZoomableEditor searchEditor;

		ZoomListener(IZoomableEditor editor) {
			searchEditor = editor;
		}

		public void onEvent(Event event) throws Exception {
			if (Events.ON_CLICK.equals(event.getName())) {
				searchEditor.actionZoom();
			}

		}

	}

	/**
	 * Embed detail tab
	 * @param ctx
	 * @param windowNo
	 * @param gridWindow
	 * @param adTabId
	 * @param tabIndex
	 * @param tabPanel
	 */
	public void embed(Properties ctx, int windowNo, GridWindow gridWindow,
			int adTabId, int tabIndex, IADTabpanel tabPanel) {
		EmbeddedPanel ep = new EmbeddedPanel();
		ep.tabPanel = tabPanel;
		ep.adTabId = adTabId;
		ep.tabIndex = tabIndex;
		ep.gridWindow = gridWindow;
		includedPanel.add(ep);
		Group group = includedTab.get(adTabId);
		ep.group = group;
		if (tabPanel instanceof ADTabpanel) {
			ADTabpanel atp = (ADTabpanel) tabPanel;
			atp.listPanel.setPageSize(-1);
		}
		ADWindowPanel panel = new ADWindowPanel(ctx, windowNo, gridWindow, tabIndex, tabPanel);
		ep.windowPanel = panel;

		if (group != null) {
			createEmbeddedPanelUI(ep);
			if (active)
				activateChild(true, ep);
		}
	}

	class EmbeddedPanel {
		Group group;
		GridWindow gridWindow;
		int tabIndex;
		ADWindowPanel windowPanel;
		IADTabpanel tabPanel;
		int adTabId;
	}

	/**
	 * @see IADTabpanel#afterSave(boolean)
	 */
	@Override
	public void afterSave(boolean onSaveEvent) {
		if (!includedPanel.isEmpty()) {
        	for (EmbeddedPanel panel : includedPanel)
        		panel.tabPanel.query(false, 0, GridTabMaxRows.NO_RESTRICTION);
        }
	}

	private void createEmbeddedPanelUI(EmbeddedPanel ep) {
		org.zkoss.zul.Row row = new Row();
		row.setSpans("5");
		grid.getRows().insertBefore(row, includedTabFooter.get(ep.adTabId));
		ep.windowPanel.createPart(row);
		ep.windowPanel.getComponent().setWidth("100%");
		ep.windowPanel.getComponent().setStyle("position: relative");
		ep.windowPanel.getComponent().setHeight("400px");

		Label title = new Label(ep.gridWindow.getTab(ep.tabIndex).getName());
		ep.group.appendChild(title);
		ep.group.appendChild(ep.windowPanel.getToolbar());
		ep.windowPanel.getStatusBar().setZclass("z-group-foot");
		ep.windowPanel.initPanel(-1, null);
	}

	@Override
	public void focus() {
		if (formComponent.isVisible())
			this.setFocusToField();
		else
			listPanel.focus();
	}

	public void setFocusToField(String columnName) {
		if (formComponent.isVisible()) {
			boolean found = false;
			for (WEditor editor : editors) {
				if (found)
					editor.setHasFocus(false);
				else if (columnName.equals(editor.getColumnName())) {
					editor.setHasFocus(true);
					Clients.response(new AuFocus(editor.getComponent()));
					found = true;
				}
			}
		} else {
			listPanel.setFocusToField(columnName);
		}
	}

	/**
	 * @see IADTabpanel#onEnterKey()
	 */
	public boolean onEnterKey() {
		if (listPanel.isVisible()) {
			return listPanel.onEnterKey();
		}
		return false;
	}

	/**
	 * @param e
	 * @see VetoableChangeListener#vetoableChange(PropertyChangeEvent)
	 */
	public void vetoableChange(PropertyChangeEvent e)
			throws PropertyVetoException {
		//  Save Confirmation dialog    MTable-RowSave
		if (e.getPropertyName().equals(GridTable.PROPERTY))
		{
			//  throw new PropertyVetoException will call this listener again to revert to old value
			if (m_vetoActive)
			{
				//ignore
				m_vetoActive = false;
				return;
			}
			if (!Env.isAutoCommit(Env.getCtx(), getWindowNo()) || gridTab.getCommitWarning().length() > 0)
			{
				if (!FDialog.ask(getWindowNo(), this, "SaveChanges?", gridTab.getCommitWarning()))
				{
					m_vetoActive = true;
					throw new PropertyVetoException ("UserDeniedSave", e);
				}
			}
			return;
		}   //  saveConfirmation
	}

	/**
	 * @return boolean
	 */
	public boolean isGridView() {
		return listPanel.isVisible();
	}

	/**
	 * @param gTab
	 * @return embedded panel or null if not found
	 */
	public IADTabpanel findEmbeddedPanel(GridTab gTab) {
		IADTabpanel panel = null;
		for(EmbeddedPanel ep : includedPanel) {
			if (ep.tabPanel.getGridTab().equals(gTab)) {
				return ep.tabPanel;
			}
		}
		return panel;
	}

	/**
	 * 
	 * @return GridPanel
	 */
	public GridPanel getGridView() {
		return listPanel;
	}

// metas: begin
	public WEditor getEditor(String columnName)
	{
		for (WEditor editor : editors)
		{
			if (columnName.equals(editor.getColumnName()))
			{
				return editor;
			}
		}
		return null;
	}
		
	/**
	 * metas: c.ghita@metas.ro
	 * get the gridpanel
	 * @return listPanel
	 */
	public GridPanel getListPanel()
	{
		return listPanel;
	}
	
	/**
	 * 
	 * @param gTab
	 * @return
	 * @author tsa, 02795
	 */
	public ADWindowPanel findEmbeddedWindowPanel(GridTab gTab)
	{
		for (EmbeddedPanel ep : includedPanel)
		{
			if (ep.tabPanel.getGridTab().equals(gTab))
			{
				return ep.windowPanel;
			}
		}
		return null;
	}

	/**
	 * Update fields color based on color logic
	 * 
	 * @param editor
	 * @task http://dewiki908/mediawiki/index.php/03514_Port_color_logic_from_Swing_to_ZK_%282012111410000028%29
	 */
	public static void setColor(WEditor editor)
	{
		// TODO: to be implemented in http://dewiki908/mediawiki/index.php/03514_Port_color_logic_from_Swing_to_ZK_%282012111410000028%29
	}
// metas: end
}
