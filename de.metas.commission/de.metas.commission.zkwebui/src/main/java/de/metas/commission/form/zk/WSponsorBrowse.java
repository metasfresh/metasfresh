package de.metas.commission.form.zk;

/*
 * #%L
 * de.metas.commission.zkwebui
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.GridPanel;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.Panel;
import org.adempiere.webui.component.ToolBarButton;
import org.adempiere.webui.editor.WTableDirEditor;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.event.ValueChangeListener;
import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.panel.CustomForm;
import org.adempiere.webui.panel.IFormController;
import org.adempiere.webui.util.TreeItemAction;
import org.adempiere.webui.util.TreeUtils;
import org.compiere.model.GridTab;
import org.compiere.model.I_C_Period;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MPeriod;
import org.compiere.model.MQuery;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Language;
import org.compiere.util.Msg;
import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.North;
import org.zkoss.zkex.zul.South;
import org.zkoss.zkex.zul.West;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Div;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;

import de.metas.commission.dashboard.DPCommisionView;
import de.metas.commission.model.I_P_AdvCommissionDetails;
import de.metas.commission.model.MCAdvComSystem;
import de.metas.commission.modelvalidator.CommissionValidator;

/**
 * 
 * @author teo_sarca
 *
 */
public class WSponsorBrowse implements IFormController
{
    /** Logger.          */
    private static Logger log = LogManager.getLogger(WSponsorBrowse.class);
    
	private final CustomForm form = new CustomForm();
	private final GeneralInfoPanel generalInfoPanel = new GeneralInfoPanel();
	private GridPanel commisionLinesPanel = null;
	private GridTab commisionLinesTab = null;
	
//	private WSearchEditor sponsorField = null;
//	private WDateEditor dateField = null;
	private WTableDirEditor periodField = null;
	private ToolBarButton toggleButton = null;
	private final Div treeTablePanel = new Div();
	private SponsorSearchPanel searchPanel = null;
	private final Tree tree = new Tree();
	private SponsorTreeModel treeModel = null;
	private final Grid table = new Grid();
	
	public WSponsorBrowse()
	{
		super();
        log.info("");
        try
        {
    		initForm();
        	refreshTreeTable();
        }
        catch(Exception e)
        {
            log.error("", e);
        }
//        CLogMgt.setLevel(Level.FINE);

        return;
	}

	@Override
	public ADForm getForm()
	{
		return form;
	}

    protected void initForm() throws Exception
    {
		//
		// Parameters Panel
    	final Panel parametersPanel = new Panel();
    	{
    		MLookup periodLookup = MLookupFactory.get(Env.getCtx(),
    				form.getWindowNo(),
    				0, //Column_ID,
    				DisplayType.Table, //AD_Reference_ID,
    				Env.getLanguage(Env.getCtx()),
    				"C_Period_ID", // ColumnName
    				275, //AD_Reference_Value_ID = C_Period (all) // TODO: hardcoded
    				false, //IsParent,
    				"EXISTS (SELECT * FROM C_Year y WHERE C_Period.C_Year_ID=y.C_Year_ID AND y.C_Calendar_ID=@"+CommissionValidator.CTX_Commission_Calendar_ID+"@)"  //ValidationCode - TODO: hardcoded
    				+" AND TRUNC(StartDate,'MONTH')<>TO_TIMESTAMP('01.03.2010','DD.MM.YYYY')" // TODO: hardcoded per US359
    				+" AND TRUNC(StartDate,'MONTH')<>TO_TIMESTAMP('01.04.2010','DD.MM.YYYY')" // TODO: hardcoded per US359
    		);
    		periodField = new WTableDirEditor("C_Period_ID", true, false, true, periodLookup);
    		setCurrentPeriod();
    		periodField.addValueChangeListener(new ValueChangeListener() {
				@Override
				public void valueChange(ValueChangeEvent evt) {
					refreshTreeTable();
				}
			});
			//
			Hbox layout = new Hbox();
			parametersPanel.appendChild(layout);
			
			layout.appendChild(new Label(Msg.translate(Env.getCtx(), "C_Period_ID")));
			layout.appendChild(periodField.getComponent());
			
	    	toggleButton = new ToolBarButton("");
	    	toggleButton.setName("btnToggle");
	    	toggleButton.setImage("/images/Multi16.png");
	    	toggleButton.setTooltiptext(Msg.getMsg(Env.getCtx(),"Multi"));
	    	//toggleButton.setStyle(EMBEDDED_TOOLBAR_BUTTON_STYLE);
	    	toggleButton.setSclass("embedded-toolbar-button");
	    	toggleButton.setPressed(false);
	    	layout.appendChild(toggleButton);
	    	toggleButton.addEventListener(Events.ON_CLICK, new EventListener() {
				@Override
				public void onEvent(Event event) throws Exception {
					togglePresentation();
				}
			});
    	}
		
		//
		// Tree
    	tree.setTreeitemRenderer(new SponsorTreeitemRenderer());
    	tree.setVflex(true);
    	tree.setWidth("100%");
    	tree.setHeight("100%");
        tree.setFixedLayout(false);
        tree.setPageSize(-1); // Due to bug in the new paging functionality
//        tree.setStyle("border: none");
//    	final Panel treePanel = new Panel();
		treeTablePanel.appendChild(tree);
		treeTablePanel.setWidth("100%");
		treeTablePanel.setHeight("100%");
		tree.addEventListener(Events.ON_SELECT, new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
				refreshSelectedNode();
			}
		});
		
		//
		// Table
		initTable();
		
		//
		// Search panel
		searchPanel = new SponsorSearchPanel(this);
		
		
		//
		// Detail Panel - General Info

		final Borderlayout detailPanel = new Borderlayout();
    	detailPanel.setWidth("98%");
    	final North northPanel = new North();
    	northPanel.setBorder("none");
    	detailPanel.appendChild(northPanel);
      	final South southPanel = new South();
      	southPanel.setBorder("none");
      	detailPanel.appendChild(southPanel);
    	northPanel.setHeight("22%");
    	northPanel.setStyle("overflow:auto;");
		southPanel.setHeight("78%");
//    	southPanel.setStyle("overflow:auto;");

		{
			Groupbox box = createGroupbox(northPanel, "WSponsorBrowse.GeneralInfo");
			box.appendChild(generalInfoPanel);
//			box.setHeight("74%");
		}
		//
		// Detail Panel - Commision Lines of the selected month
		{
			Groupbox box = createGroupbox(southPanel, "WSponsorBrowse.CommisionLinesForMonth");
			//
			commisionLinesTab = DPCommisionView.createGridTab(form.getWindowNo(),
					I_P_AdvCommissionDetails.Table_Name,
					MQuery.getNoRecordQuery(I_P_AdvCommissionDetails.Table_Name, false) );
			commisionLinesTab.setQuery(MQuery.getNoRecordQuery(I_P_AdvCommissionDetails.Table_Name, false));
			commisionLinesPanel = new GridPanel(form.getWindowNo());
			commisionLinesPanel.refresh(commisionLinesTab);
			commisionLinesPanel.showGrid(true);
			commisionLinesPanel.setStyle("width:95%; height:95%");
			// Reset Column Sizes
//			for (Object c : commisionLinesPanel.getListbox().getColumns().getChildren())
//			{
//				((org.zkoss.zul.Column)c).setWidth(null);
//			}
//			gridPanel.setStyle("display: block;");
			box.appendChild(commisionLinesPanel);
			box.setHeight("99%");
		}
		
		//
		// Layout
		Borderlayout contentPane = new Borderlayout();
		form.appendChild(contentPane);
		form.setHeight("100%");
		
		North north = new North();
		north.appendChild(parametersPanel);
		contentPane.appendChild(north);
		
		final West west = new West();
		contentPane.appendChild(west);
//		west.appendChild(treeTablePanel);
		{
			west.setWidth("450px");
			Borderlayout l = new Borderlayout();
			west.appendChild(l);
			
			final Center c = new Center();
			l.appendChild(c);
			c.appendChild(treeTablePanel);
			
			South s = new South();
			s.appendChild(searchPanel);
			l.appendChild(s);
		}
		
		Center center = new Center();
		center.appendChild(detailPanel);
		contentPane.appendChild(center);
		
//		South south = new South();
//		south.appendChild(searchPanel);
//		contentPane.appendChild(south);
	}
    
    private void initTable()
    {
    	final SponsorTableRenderer renderer = new SponsorTableRenderer();
    	
    	renderer.createHeader(table);
		table.setVflex(true);
		table.setFixedLayout(true);
		table.setVisible(false);
		treeTablePanel.appendChild(table);
//		table.addEventListener(Events.ON_CLICK, this);
		table.setRowRenderer(renderer);
		table.addEventListener(Events.ON_CLICK, new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
				refreshSelectedRow(event);
			}
		});

    }
    
    private static final Groupbox createGroupbox(AbstractComponent parent, String titleMsg)
    {
		Groupbox box = new Groupbox();
		parent.appendChild(box);
		
		String title = Msg.translate(Env.getCtx(), titleMsg);
		Caption caption = new Caption(Msg.translate(Env.getCtx(), title));
		box.appendChild(caption);
		
		return box;
    }
    
    public void setCurrentPeriod()
    {
    	final Properties ctx = Env.getCtx();
    	final Timestamp date = Env.getContextAsDate(ctx, "#Date");
    	final int C_Calendar_ID = Env.getContextAsInt(ctx, CommissionValidator.CTX_Commission_Calendar_ID);
    	if (C_Calendar_ID <= 0)
    		return;
    	
    	I_C_Period period = MPeriod.findByCalendar(ctx, date, C_Calendar_ID, null);
    	if (period == null)
    	{
    		log.warn("Period not found for - Date="+date+", C_Calendar_ID="+C_Calendar_ID);
    		return;
    	}
    	if (periodField != null)
    		periodField.setValue(period.getC_Period_ID());
    }
    
    public int getC_Period_ID()
    {
    	if (periodField == null)
    		return -1;
    	if (! (periodField.getValue() instanceof Number) )
    		return -1;
    	int C_Period_ID = ((Number)periodField.getValue()).intValue();
    	if (C_Period_ID <= 0)
    		return -1;
    	return C_Period_ID;
    }
    
    public Timestamp getDate()
    {
    	int C_Period_ID = getC_Period_ID();
    	if (C_Period_ID <= 0)
    		return null;
    	MPeriod period = MPeriod.get(Env.getCtx(), C_Period_ID);
    	return period.getEndDate();
    }
    
    public int getMonth()
    {
    	Timestamp date = getDate();
    	if (date == null)
    		return -1;
		GregorianCalendar cal = new GregorianCalendar(Language.getLoginLanguage().getLocale());
		cal.setTime(date);
		return cal.get(Calendar.MONTH) + 1;
    }
    
    public int getYear()
    {
    	Timestamp date = getDate();
    	if (date == null)
    		return -1;
		GregorianCalendar cal = new GregorianCalendar(Language.getLoginLanguage().getLocale());
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
    }
    
    public int getAD_User_ID()
    {
    	return Env.getAD_User_ID(Env.getCtx());
//    	return 1012495; // P0000020
//    	return 51592; // "Peter Mölleney"
//    	return 1012509; // The Root: @.com
    }

	private void refreshTreeTable()
	{
		refreshTree();
		refreshTable(false);
	}
	private void refreshTree()
	{
		final int comSystemId = Env.getContextAsInt(Env.getCtx(), CommissionValidator.CTX_Commission_System_ID);
		final MCAdvComSystem comSystem = new MCAdvComSystem(Env.getCtx(), comSystemId, null);

		log.debug("Date=" + getDate() + ", AD_User_ID=" + getAD_User_ID() + ", C_AdvComSystem_ID=" + comSystemId);
		treeModel = new SponsorTreeModel(getAD_User_ID(), getC_Period_ID(), getDate(), comSystem);
		tree.setModel(null);
		tree.setModel(treeModel);
		table.setModel((ListModel)null);
	}
	
	private void refreshTable(boolean force)
	{
		if (!table.isVisible() && !force)
			return;
		//
		ListModel tableModel = table.getModel();
		if ( (tableModel instanceof SponsorTableModel))
			return;
			
		tableModel = new SponsorTableModel(treeModel);
		table.setModel((ListModel)null);
		table.setModel(tableModel);
	}
	
	private void refreshSelectedNode()
	{
		Treeitem treeitem = tree.getSelectedItem();
		if (treeitem == null)
		{
			onNodeSelected(null);
			return;
		}
		Object value = treeitem.getValue();
		if (value == null)
		{
			onNodeSelected(null);
			return;
		}
		SponsorTreeNode node = (SponsorTreeNode)value;
		onNodeSelected(node);
		selectTableRow(node);
	}
	
	private void selectTableRow(SponsorTreeNode node)
	{
		final SponsorTableRenderer renderer = (SponsorTableRenderer) table.getRowRenderer();
		
		if (node == null)
		{
			renderer.setCurrentRow(null, null);
			return;
		}
		
		Rows rows = table.getRows();
		if (rows != null)
		{
			for (Object c : rows.getChildren())
			{
				Row row = (Row)c;
				if (!renderer.isRowRendered(row))
				{
					table.renderRow(row);
				}
				SponsorTreeNode rowNode = (SponsorTreeNode) row.getAttribute(SponsorTableRenderer.ATTRIBUTE_Node);
				if (rowNode == node)
				{
					renderer.setCurrentRow(row, null);
					break;
				}
			}
		}
	}
	
	private void selectTreeNode(final SponsorTreeNode node)
	{
		if (node == null)
		{
			tree.setSelectedItem(null);
			return;
		}
		final Treeitem[] selectedItem = new Treeitem[]{null};
		TreeUtils.traverse(tree, new TreeItemAction()
		{
			@Override
			public void run(Treeitem treeItem)
			{
				if (selectedItem[0] != null)
					return;
				treeItem.getTree().renderItem(treeItem);
				if (treeItem.getValue() == node)
				{
					selectedItem[0] = treeItem;
				}
			}
		});
		//
		if (selectedItem[0] != null)
		{
			select(selectedItem[0]);
		}
	}
	
	public void select(SponsorTreeNode node)
	{
		if (isTreeView())
		{
			selectTreeNode(node);
		}
		else
		{
			selectTableRow(node);
		}
		generalInfoPanel.setSponsorTreeNode(node);
	}
	
	private void select(Treeitem treeItem)
	{
		Treeitem parent = treeItem.getParentItem();
		while (parent != null)
		{
			if (!parent.isOpen())
				parent.setOpen(true);
			parent = parent.getParentItem();
		}
		treeItem.getTree().setSelectedItem(treeItem);
	}
	
	public void refreshSelectedRow(Event event)
	{
		if (event.getTarget() == table && (event.getData() instanceof Div))
		{
			Div div = (Div)event.getData();
			Row row = (Row)div.getParent();
			SponsorTreeNode node = (SponsorTreeNode)row.getAttribute(SponsorTableRenderer.ATTRIBUTE_Node);
			//String columnName = (String)div.getAttribute(SponsorTableRenderer.ATTRIBUTE_ColumnName);
			onNodeSelected(node);
		}
	}
	
	private boolean onNodeSelectedRunning = false;
	private void onNodeSelected(SponsorTreeNode node)
	{
		if (onNodeSelectedRunning)
			return;
		try
		{
			onNodeSelectedRunning = true;
			onNodeSelected0(node);
		}
		finally
		{
			onNodeSelectedRunning = false;
		}
	}
	private void onNodeSelected0(SponsorTreeNode node)
	{
		log.info("node="+node);
		generalInfoPanel.setSponsorTreeNode(node);
		//
		// Refresh Commision Lines:
//		final long ts = System.currentTimeMillis();
		int month = getMonth();
		int year = getYear();
		MQuery query = new MQuery(I_P_AdvCommissionDetails.Table_Name);
		query.addRestriction(I_P_AdvCommissionDetails.COLUMNNAME_C_Sponsor_ID, MQuery.EQUAL, node.getSponsor_ID());
		query.addRestriction(I_P_AdvCommissionDetails.COLUMNNAME_month, MQuery.EQUAL, month);
		
		// -tsa: commented out because we have performance issues when we filter by year.
		//       As a temporary solution (until 12.2010) we can leave the year out.
		// -ts: performance issues have been solved using a materialized view, commenting it in again
		query.addRestriction(I_P_AdvCommissionDetails.COLUMNNAME_year, MQuery.EQUAL, year);
		
		commisionLinesTab.setQuery(query);
		commisionLinesTab.query(false);
//		System.out.println("***refreshDetailPanel[commission lines]: "+node+" - "+(System.currentTimeMillis() - ts)+"ms");
	}
	
	public SponsorTreeNode getSelectedSponsorTreeNode()
	{
		return generalInfoPanel.getSponsorTreeNode();
	}
	
	public SponsorTableModel getSponsorTableModel()
	{
		SponsorTableModel model = (SponsorTableModel)table.getModel();
		if (model != null)
		{
			return model;
		}
		//
		refreshTable(true);
		model = (SponsorTableModel)table.getModel();
		return model;
	}
	
	public void togglePresentation()
	{
		if (table.isVisible())
		{
			table.setVisible(false);
			tree.setVisible(true);
			selectTreeNode(getSelectedSponsorTreeNode());
		}
		else
		{
			table.setVisible(true);
			tree.setVisible(false);
			refreshTable(false);
			selectTableRow(getSelectedSponsorTreeNode());
		}
	}
	
	public boolean isTreeView()
	{
		return !table.isVisible();
	}
}
