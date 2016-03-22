/**
 * 
 */
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


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.misc.service.IPOService;
import org.adempiere.model.GridTabWrapper;
import org.adempiere.model.MFreightCost;
import org.adempiere.model.POWrapper;
import org.adempiere.util.Services;
import org.adempiere.webui.component.NumberBox;
import org.adempiere.webui.editor.WEditor;
import org.adempiere.webui.editor.WEditorPopupMenu;
import org.adempiere.webui.panel.ADTabpanel;
import org.adempiere.webui.util.GridTabDataBinder;
import org.compiere.model.GridTab;
import org.compiere.model.GridTab.DataNewCopyMode;
import org.compiere.model.GridWindow;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.MBPGroup;
import org.compiere.model.MBPartner;
import org.compiere.model.MDocType;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MQuery;
import org.compiere.model.MUser;
import org.compiere.model.Query;
import org.compiere.model.StateChangeEvent;
import org.compiere.model.StateChangeListener;
import org.compiere.model.X_C_Order;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.North;
import org.zkoss.zkex.zul.West;
import org.zkoss.zul.Div;

import de.metas.adempiere.service.IOrderBL;
import de.metas.adempiere.webui.component.PropertyInfoPanel;
import de.metas.adempiere.webui.component.WizardPage;
import de.metas.adempiere.webui.component.WizardPanel;
import de.metas.adempiere.webui.util.ZkUtil;
import de.metas.commission.interfaces.I_C_DocType;
import de.metas.web.component.ADTabpanelCRUDToolbarListener;

/**
 * @author tsa
 * @author cg
 * 
 */
public class WB2BOrderProductWizardPage
		implements WizardPage
{
	private final Logger log = LogManager.getLogger(getClass());
	
	private final Div mainPanel = new Div();
	private final Div panelProduct = new Div();
	private final Div panelOverview = new Div();
	private final Div panelLines = new Div();

	private GridWindow gridWindow;
	private GridTab gridTabOrder;
	private GridTab gridTabOrderLine;
	private ADTabpanel tabpanelOrder;
	private ADTabpanel tabpanelOrderLine;
	private final Map<Integer, GridTabDataBinder> dataBinderMap = new HashMap<Integer, GridTabDataBinder>();
	private final int windowNo;
	private WEditor productEditor;
	private WEditor qtyEditor;

	private final PropertyInfoPanel overviewInfo = new PropertyInfoPanel();
	private boolean isInitialized;

	// private final CWindowToolbar toolbarOrderLine = new CWindowToolbar(true);

	public WB2BOrderProductWizardPage(int windowNo)
	{
		this.windowNo = windowNo;
		init();
	}

	@Override
	public void init()
	{
		if (isInitialized)
			return;

		try
		{
			initGridWindow();
			loadOrder();
			initLayout();
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
		this.isInitialized = true;
	}

	private void initLayout()
	{
		remveOrderBinder();
		initGeneralLayout();
		initQtyFastInputPanel();
		initOrderOverview();
		initOrderLinesPanel();
	}

	private void remveOrderBinder()
	{
		//order binder
		GridTabDataBinder orderDataBinder =  dataBinderMap.get(gridTabOrder.getAD_Tab_ID());
		tabpanelOrder.getEditor(de.metas.commission.interfaces.I_C_Order.COLUMNNAME_C_BPartner_ID).removeValuechangeListener(orderDataBinder);
		tabpanelOrder.getEditor(de.metas.commission.interfaces.I_C_Order.COLUMNNAME_C_BPartner_Location_ID).removeValuechangeListener(orderDataBinder);
		tabpanelOrder.getEditor(de.metas.commission.interfaces.I_C_Order.COLUMNNAME_AD_User_ID).removeValuechangeListener(orderDataBinder);
		tabpanelOrder.getEditor(de.metas.commission.interfaces.I_C_Order.COLUMNNAME_Bill_BPartner_ID).removeValuechangeListener(orderDataBinder);
		tabpanelOrder.getEditor(de.metas.commission.interfaces.I_C_Order.COLUMNNAME_Bill_Location_ID).removeValuechangeListener(orderDataBinder);
		tabpanelOrder.getEditor(de.metas.commission.interfaces.I_C_Order.COLUMNNAME_Bill_User_ID).removeValuechangeListener(orderDataBinder);
	}
	
	private void initGridWindow()
	{
		final Properties ctx = Env.getCtx();
		final int AD_Window_ID = 540072; // TODO: HARDCODED - B2B Auftrag (internal)
		gridWindow = GridWindow.get(ctx, this.windowNo, AD_Window_ID, false);
		if (gridWindow == null)
		{
			throw new AdempiereException("Can not load window for table " + I_C_Order.Table_Name
					+ " (AD_Window_ID=" + AD_Window_ID + ")."
					+ " Please check permissions.");
		}

		for (int i = 0; i < gridWindow.getTabCount(); i++)
		{
			GridTab gridTab = gridWindow.getTab(i);
			String tableName = gridTab.getTableName();
			if (!I_C_Order.Table_Name.equals(tableName) && !I_C_OrderLine.Table_Name.equals(tableName))
			{
				continue;
			}

			if (I_C_Order.Table_Name.equals(tableName) && this.gridTabOrder == null)
			{
				this.gridTabOrder = gridTab;
			}
			else if (I_C_OrderLine.Table_Name.equals(tableName) && this.gridTabOrderLine == null)
			{
				this.gridTabOrderLine = gridTab;
			}
			else
			{
				continue;
			}

			if (!gridTab.isLoadComplete())
			{
				gridWindow.initTab(i);
			}
			GridTabDataBinder dataBinder = new GridTabDataBinder(gridTab);
			dataBinderMap.put(gridTab.getAD_Tab_ID(), dataBinder);
		}
		
		//
		// Configure gridTabOrderLine:
		WEditorPopupMenu.setDisableItemInContext(gridTabOrderLine.getWindowNo(), gridTabOrderLine.getTabNo(),
				I_C_OrderLine.COLUMNNAME_M_Product_ID,
				WEditorPopupMenu.ACTION_ZOOM,
				true);
	}

	private void initGeneralLayout()
	{
		Borderlayout layout = new Borderlayout();
		layout.setWidth("100%");
		layout.setHeight("100%");
		mainPanel.appendChild(layout);
		mainPanel.setWidth("100%");
		mainPanel.setHeight("100%");

		North north = new North();
		north.setHeight("40%");
		north.setSplittable(true);
		layout.appendChild(north);
		{
			Borderlayout layout2 = new Borderlayout();
			north.appendChild(layout2);

			West w = new West();
			w.setWidth("40%");
			layout2.appendChild(w);
			w.appendChild(panelProduct);
			panelProduct.setWidth("100%");
			panelProduct.setHeight("100%");

			Center c = new Center();
			layout2.appendChild(c);
			c.appendChild(panelOverview);
			panelOverview.setWidth("100%");
			panelOverview.setHeight("100%");
		}

		Center center = new Center();
		layout.appendChild(center);
		center.appendChild(panelLines);
		panelLines.setWidth("100%");
		panelLines.setHeight("100%");

		panelOverview.appendChild(overviewInfo);
	}

	private void initQtyFastInputPanel()
	{
		PropertyInfoPanel pipProduct = new PropertyInfoPanel();
		panelProduct.getChildren().clear();
		panelProduct.appendChild(pipProduct);
		//
		productEditor = tabpanelOrder.getEditor(de.metas.commission.interfaces.I_C_Order.COLUMNNAME_M_Product_ID);
		productEditor.getPopupMenu().setDisableItemDynamic(WEditorPopupMenu.ACTION_ZOOM, true);
		productEditor.getGridField().getLookup().refresh(); // force refresh; is not loading always the drop down list
		
		qtyEditor = tabpanelOrder.getEditor(de.metas.commission.interfaces.I_C_Order.COLUMNNAME_Qty_FastInput);
		if (qtyEditor.getComponent() instanceof NumberBox)
		{
			((NumberBox)qtyEditor.getComponent()).setCalculatorEnabled(false);
		}
		
		pipProduct.resetProperties();
		pipProduct.addProperty(de.metas.commission.interfaces.I_C_Order.COLUMNNAME_M_Product_ID, productEditor.getComponent());
		pipProduct.addProperty(de.metas.commission.interfaces.I_C_Order.COLUMNNAME_Qty_FastInput, qtyEditor.getComponent());
	}

	private void initOrderOverview()
	{
		gridTabOrderLine.addStateChangeListener(new StateChangeListener()
		{
			@Override
			public void stateChange(StateChangeEvent event)
			{
				updateOrderOverview();
			}
		});
	}

	private void initOrderLinesPanel()
	{
		tabpanelOrderLine = new ADTabpanel();
		tabpanelOrderLine.init(null, this.windowNo, gridTabOrderLine, gridWindow);
		tabpanelOrderLine.createUI();
		tabpanelOrderLine.getGridView().showGrid(true);
		//invalidate the double click event: switch row presentation
		tabpanelOrderLine.getListPanel().getListbox().removeEventListener(Events.ON_DOUBLE_CLICK, tabpanelOrderLine);
		
		ADTabpanelCRUDToolbarListener toolbar = new ADTabpanelCRUDToolbarListener(tabpanelOrderLine);
		toolbar.setParentTabpanel(tabpanelOrder);
		
		{
			final Borderlayout l = new Borderlayout();
			l.setWidth("100%");
			l.setHeight("100%");
			if (panelLines.getChildren().size() > 0)
				panelLines.getChildren().clear();
			panelLines.appendChild(l);

			final North n = new North();
			l.appendChild(n);
			n.appendChild(toolbar.getToolbar());

			final Center c = new Center();
			c.setFlex(true);
			l.appendChild(c);
			c.appendChild(tabpanelOrderLine);
		}
		// need to refresh, because otherwise the tab is not refreshed
		gridTabOrderLine.setQuery(MQuery.getEqualQuery(de.metas.commission.interfaces.I_C_Order.COLUMNNAME_C_Order_ID,
									gridTabOrder.getRecord_ID()));
		gridTabOrderLine.query(false);
		gridTabOrderLine.dataRefreshAll();
	}

	private void loadOrder()
	{
		// metas: tsa: We need to create the tabpanel here, before querying the GridTab because
		// else the editors will not be updated
		this.tabpanelOrder = new ADTabpanel();
		tabpanelOrder.init(null, this.windowNo, gridTabOrder, gridWindow);
		tabpanelOrder.createUI();
		tabpanelOrder.activate(true);

		final Properties ctx = Env.getCtx();
		Env.setContext(ctx, this.windowNo, "IsSOTrx", true);

		int C_DocType_ID = getDefaultDocType_ID();
		MUser user = MUser.get(ctx);
		final int C_BPartner_ID = user.getC_BPartner_ID();
		if (C_BPartner_ID <= 0)
		{
			throw new AdempiereException("@NotFound@ @C_BPartner_ID@ (@AD_User_ID@: " + user + ")");
		}

		final String whereClause = de.metas.commission.interfaces.I_C_Order.COLUMNNAME_C_BPartner_ID + "=?"
				+ " AND " + de.metas.commission.interfaces.I_C_Order.COLUMNNAME_C_DocTypeTarget_ID + "=?"
				+ " AND " + de.metas.commission.interfaces.I_C_Order.COLUMNNAME_DocStatus + " IN (?, ?)"
				+ " AND " + de.metas.commission.interfaces.I_C_Order.COLUMNNAME_IsB2BOrder + "=?"
				+ " AND " + de.metas.commission.interfaces.I_C_Order.COLUMNNAME_CreatedBy + "=?"
		;
		int C_Order_ID = new Query(ctx, de.metas.commission.interfaces.I_C_Order.Table_Name, whereClause, null)
				.setParameters(C_BPartner_ID, C_DocType_ID
						, X_C_Order.DOCSTATUS_Drafted, X_C_Order.DOCSTATUS_InProgress
						, true // IsB2BOrder
						, user.getAD_User_ID() // CreatedBy
				)
				.firstIdOnly();

		if (C_Order_ID <= 0)
		{
		
			gridTabOrder.setQuery(null);
			gridTabOrder.getTableModel().setChanged(false); // metas: c.ghita@metas.ro : need to do this as workaround;
															// if the payment is not credit card
			gridTabOrder.query(true);
			gridTabOrder.dataNew(DataNewCopyMode.NoCopy);
			//
			final int AD_Org_ID = Env.getAD_Org_ID(ctx);
			final MOrgInfo orgInfo = MOrgInfo.get(ctx, AD_Org_ID, null);
			int M_Warehouse_ID = orgInfo != null ? orgInfo.getM_Warehouse_ID() : -1;
			if (M_Warehouse_ID <= 0)
			{
				M_Warehouse_ID = Env.getContextAsInt(ctx, "#M_Warehouse_ID");
				log.warn("Could not found warehouse for AD_Org_ID="+AD_Org_ID);
			}
			de.metas.commission.interfaces.I_C_Order order = GridTabWrapper.create(gridTabOrder, de.metas.commission.interfaces.I_C_Order.class);
			order.setIsSOTrx(true);
			order.setAD_Org_ID(AD_Org_ID);
			order.setC_DocTypeTarget_ID(C_DocType_ID);
			order.setC_DocType_ID(C_DocType_ID);
			order.setIsB2BOrder(true);
			order.setM_Warehouse_ID(M_Warehouse_ID);
			order.setC_BPartner_ID(C_BPartner_ID);
			if (order.getBill_BPartner_ID() <=0)
			{
			
				order.setBill_BPartner_ID(C_BPartner_ID);
			}
			int M_PricingSystem_ID = ((MBPartner)order.getC_BPartner()).get_ValueAsInt("M_PricingSystem_ID");
			if (M_PricingSystem_ID == 0)
				M_PricingSystem_ID = ((MBPGroup)order.getC_BPartner().getC_BP_Group()).get_ValueAsInt("M_PricingSystem_ID");
			order.setM_PricingSystem_ID(M_PricingSystem_ID);
			order.setProcessed(false);
			final IOrderBL orderBL = Services.get(IOrderBL.class);
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			order.setDateOrdered(ts);
			order.setDateAcct(ts);
			final boolean fixPrice = X_C_Order.FREIGHTCOSTRULE_FixPrice.equals(order.getFreightCostRule());
			if (!fixPrice)
			{
				orderBL.updateFreightAmt(ctx, order, null);
			}
			if (!gridTabOrder.dataSave(true))
			{
				throw new AdempiereException();
			}
			gridTabOrder.query(false);
			gridTabOrder.getKeyID(gridTabOrder.getCurrentRow());
	   	
			C_Order_ID = order.getC_Order_ID();
			if (order.getC_Order_ID() <= 0)
				throw new AdempiereException("Not SAVED!");
			
			gridTabOrderLine.setQuery(MQuery.getNoRecordQuery(de.metas.commission.interfaces.I_C_OrderLine.Table_Name, true));
			gridTabOrderLine.query(true);
			gridTabOrderLine.dataNew(DataNewCopyMode.NoCopy);
			de.metas.commission.interfaces.I_C_OrderLine orderLine = GridTabWrapper.create(gridTabOrderLine, de.metas.commission.interfaces.I_C_OrderLine.class);
			orderLine.setC_Order_ID(order.getC_Order_ID());
			Services.get(IPOService.class).copyClientOrg(order, orderLine);
			orderLine.setC_BPartner_ID(order.getC_BPartner_ID());
			orderLine.setC_BPartner_Location_ID(order.getC_BPartner_Location_ID());
			orderLine.setM_Warehouse_ID(order.getM_Warehouse_ID());
			orderLine.setDateOrdered(order.getDateOrdered());
			orderLine.setDatePromised(order.getDatePromised());
			orderLine.setC_Currency_ID(order.getC_Currency_ID());
			MOrder ord = POWrapper.getPO(order);
			MFreightCost freightCost = MFreightCost.retrieveFor(ord);
			orderLine.setQtyOrdered(BigDecimal.ONE);
			orderLine.setQtyEntered(BigDecimal.ONE);
			orderLine.setM_Product_ID(freightCost.getM_Product_ID());
			final BigDecimal freightCostAmt = order.getFreightAmt();
			orderLine.setPriceActual(freightCostAmt);
			// 07090: not setting PriceUOM: i think we don't need it here
			if (!gridTabOrderLine.dataSave(true))
			{
				throw new AdempiereException();
			}
		}
		else
		{
			gridTabOrder.setQuery(MQuery.getEqualQuery(de.metas.commission.interfaces.I_C_Order.COLUMNNAME_C_Order_ID, C_Order_ID));
			gridTabOrder.query(false);
			de.metas.commission.interfaces.I_C_Order order = GridTabWrapper.create(gridTabOrder, de.metas.commission.interfaces.I_C_Order.class);
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			if (order.getDateOrdered().before(ts) && !TimeUtil.isSameDay(order.getDateOrdered(), ts))
			{
				order.setDateOrdered(ts);
				order.setDateAcct(ts);
				if (!gridTabOrder.dataSave(true))
				{
					throw new AdempiereException();
				}
				gridTabOrder.setQuery(MQuery.getEqualQuery(de.metas.commission.interfaces.I_C_Order.COLUMNNAME_C_Order_ID, C_Order_ID));
				gridTabOrder.query(false);
				//
				MOrder or = new MOrder(ctx, C_Order_ID, null);
				for (MOrderLine ol : or.getLines())
				{
					ol.setDatePromised(ts);
					ol.saveEx();
				}
			}
			
		}
		// put the right orderline in tab
		gridTabOrderLine.setQuery(MQuery.getEqualQuery(de.metas.commission.interfaces.I_C_Order.COLUMNNAME_C_Order_ID, C_Order_ID));
		gridTabOrderLine.query(false);
		// lock order
		gridTabOrder.lock(Env.getCtx(), C_Order_ID, true);
	}

	private int getDefaultDocType_ID()
	{

		final String whereClause = I_C_DocType.COLUMNNAME_DocBaseType + "=?"
									+ " AND (" + I_C_DocType.COLUMNNAME_DocSubType + "=?"
									+ " OR " + I_C_DocType.COLUMNNAME_DocSubType + "=?)"
									+ " AND " + I_C_DocType.COLUMNNAME_IsB2BOrder + " = ?"
									+ " AND " + I_C_DocType.COLUMNNAME_AD_Org_ID + " IN (0,?)";
		int C_DocType_ID = new Query(Env.getCtx(), I_C_DocType.Table_Name, whereClause, null)
									.setParameters(MDocType.DOCBASETYPE_SalesOrder,
											MDocType.DOCSUBTYPE_PrepayOrder,
											de.metas.prepayorder.model.I_C_DocType.DOCSUBTYPE_PrepayOrder_metas,
											true,
											Env.getAD_Org_ID(Env.getCtx()))
									.setOnlyActiveRecords(true)
									.setClient_ID()
									.setOrderBy(MDocType.COLUMNNAME_AD_Org_ID + " DESC ")
									.firstId();

		if (C_DocType_ID <= 0)
		{
			throw new AdempiereException("@NotFound@ @C_DocType_ID@ (@DocBaseType@:SO, @DocSubType@:PR)");
		}

		return C_DocType_ID;
	}

	private void updateOrderOverview()
	{
		if (!gridTabOrder.isOpen())
			return;

		gridTabOrder.dataRefresh();

		de.metas.commission.interfaces.I_C_Order order = GridTabWrapper.create(gridTabOrder, de.metas.commission.interfaces.I_C_Order.class);
		IOrderBL orderBL = Services.get(IOrderBL.class);
		ZkUtil.setOrderOverview(overviewInfo, orderBL, order);

		if (wizardPanel != null && wizardPanel.getCurrentPage() == this)
			wizardPanel.updateButtonStatus();
	}

	public GridWindow getGridWindow()
	{
		return gridWindow;
	}

	public GridTab getOrderGridTab()
	{
		return gridTabOrder;
	}

	public ADTabpanel getOrderTabpanel()
	{
		return tabpanelOrder;
	}

	public GridTab getOrderLineGridTab()
	{
		return gridTabOrderLine;
	}

	public de.metas.commission.interfaces.I_C_Order getC_Order()
	{
		if (gridTabOrder == null || !gridTabOrder.isOpen())
			return null;
		de.metas.commission.interfaces.I_C_Order order = GridTabWrapper.create(gridTabOrder, de.metas.commission.interfaces.I_C_Order.class);
		return order;
	}

	@Override
	public Component getComponent()
	{
		return mainPanel;
	}

	@Override
	public boolean isAllowAction(String name)
	{
		if (gridTabOrderLine == null || !gridTabOrderLine.isOpen())
			return false;
		if (gridTabOrderLine.getRowCount() <= 0)
			return false;
		if (name.equals(WizardPanel.ACTION_Finish) || name.equals(WizardPanel.ACTION_New)
				|| WizardPanel.ACTION_Cancel.equals(name))
			return false;
		return true;
	}

	@Override
	public void onHide(String action)
	{
	}

	@Override
	public void onShow()
	{
		if (getWizardPanel().getCurrentPage() instanceof WB2BOrderHistoryWizardPage)
		{
			loadOrder();
			initOrderLinesPanel();
			initQtyFastInputPanel();
		}
		else 
		{
			int C_BPartner_Location_ID = (Integer)gridTabOrder.getValue(I_C_Order.COLUMNNAME_C_BPartner_Location_ID);
			int C_Order_ID = (Integer)gridTabOrder.getValue(I_C_Order.COLUMNNAME_C_Order_ID);
			boolean changed = false;
			if (C_Order_ID > 0)
			{
				
				MOrder order = new MOrder(Env.getCtx(), C_Order_ID, null);
				MOrderLine[] lines = order.getLines();
				for (int i=0; i<lines.length; i++)
				{
					if (C_BPartner_Location_ID != lines[i].getC_BPartner_Location_ID())
					{
						lines[i].setC_BPartner_Location_ID(C_BPartner_Location_ID);
						lines[i].saveEx();
						changed = true;
					}
				}
				if (changed)
				{
					de.metas.commission.interfaces.I_C_Order iorder = POWrapper.create(order, de.metas.commission.interfaces.I_C_Order.class);
					IOrderBL orderBL = Services.get(IOrderBL.class);
					ZkUtil.setOrderOverview(overviewInfo, orderBL, iorder);
					gridTabOrderLine.dataRefreshAll();
				}
			}
		}
	}

	@Override
	public void setWizardPanel(WizardPanel panel)
	{
		this.wizardPanel = panel;
	}

	public WizardPanel getWizardPanel()
	{
		return wizardPanel;
	}

	private WizardPanel wizardPanel;

	@Override
	public boolean isInitialized()
	{
		return isInitialized;
	}
}
