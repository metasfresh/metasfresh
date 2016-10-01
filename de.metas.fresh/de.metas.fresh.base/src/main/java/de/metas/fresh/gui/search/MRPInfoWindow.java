package de.metas.fresh.gui.search;

/*
 * #%L
 * de.metas.fresh.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Properties;

import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.event.ListSelectionEvent;

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.apps.AEnv;
import org.compiere.apps.ConfirmPanel;
import org.compiere.apps.form.FormFrame;
import org.compiere.apps.form.VTrxMaterial;
import org.compiere.apps.search.IInfoQueryCriteria;
import org.compiere.apps.search.InfoSimple;
import org.compiere.apps.search.history.IInvoiceHistoryTabHandler;
import org.compiere.apps.search.history.impl.InvoiceHistory;
import org.compiere.apps.search.history.impl.InvoiceHistoryContext;
import org.compiere.minigrid.MiniTable;
import org.compiere.model.I_AD_User_SortPref_Hdr;
import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;
import org.compiere.swing.CButton;
import org.compiere.swing.CCheckBox;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.eevolution.form.VMRPDetailed;

import de.metas.fresh.model.I_Fresh_QtyOnHand;

/**
 * This is about the MRP <b>Product</b> Info window.
 * NOTE: consider moving this to either de.metas.handlingunits or org.adempiere.libero if and when one starts to depend on the other.
 */
public class MRPInfoWindow extends InfoSimple
{
	private static final long serialVersionUID = 6846972730305506649L;

	//
	// Services
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	private static final String SYSCONFIG_DAYS_RANGE = "de.metas.fresh.MRPProductInfo.dayRange";

	// FRESH-531
	// Possibility to switch the conference displaying on and off
	private static final String SYSCONFIG_DisplayConferenceFlag = "de.metas.fresh.MRPProductInfo.DisplayConferenceFlag";

	private static final String MSG_ResetSortFilters = "de.metas.fresh.MRPProductInfo.ResetSortFilters";

	private CButton mrpButton = null;
	private CButton movementButton = null;
	private CButton freshQtyOnHandButton = null;
	private CCheckBox isConferenceSortPreferencesCheckBox = null;
	private CButton resetSortFiltersButton = null;

	private MRPInfoWindowDetails panelDetails;

	/**
	 * task 09961
	 * 
	 * variable that keeps the selected product ID for further selections
	 */
	private Integer selectedProductID = 0;

	public MRPInfoWindow(final Frame frame, final Boolean modal)
	{
		super(frame, modal);

		p_table.setClearFiltersAfterRefresh(false); // 08329: do not clear filters after refresh
	}

	@Override
	public void initAddonPanel()
	{
		final Properties ctx = getCtx();

		//
		// extend the confirm panel (in the lower part or the window)
		mrpButton = ConfirmPanel.createNewButton("MRP");
		confirmPanel.addButton(mrpButton);
		mrpButton.setEnabled(true);
		mrpButton.addActionListener(this);

		movementButton = ConfirmPanel.createZoomButton(msgBL.getMsg(ctx, VTrxMaterial.MOVEMENT_TITLE));
		confirmPanel.addButton(movementButton);
		movementButton.setEnabled(true);
		movementButton.addActionListener(this);

		freshQtyOnHandButton = ConfirmPanel.createZoomButton(msgBL.translate(ctx, I_Fresh_QtyOnHand.COLUMNNAME_Fresh_QtyOnHand_ID));
		confirmPanel.addButton(freshQtyOnHandButton);
		freshQtyOnHandButton.setEnabled(true);
		freshQtyOnHandButton.addActionListener(this);

		panelDetails = new MRPInfoWindowDetails(this);
		addonPanel.setLayout(new BorderLayout());
		addonPanel.add(panelDetails.getComponent(), BorderLayout.CENTER);

		//
		// Configure parameter panel custom filters
		//
		// Conference Sort Preferences CheckBox
		{
			final boolean isDisplayConference = Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_DisplayConferenceFlag, false);

			// FRESH-531: Only display the Conference flag if the DisplayConferenceFlag Sys Config's value is on true
			if (isDisplayConference)
			{
				isConferenceSortPreferencesCheckBox = new CCheckBox(msgBL.translate(ctx, I_AD_User_SortPref_Hdr.COLUMNNAME_IsConference));
				isConferenceSortPreferencesCheckBox.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(final ActionEvent e)
					{
						//
						// Set conference mode on/off
						final Boolean isConferenceSortPreferences = isConferenceSortPreferencesCheckBox.isSelected();
						setConferenceSortPreferences(isConferenceSortPreferences);

						//
						// Set initial sorting preferences to conference/user
						initSortingPreferences(ctx);

						//
						// Clear and refresh sort options
						p_table.setReloadOriginalSorting(true); // shall clear user sorting
						p_table.clearSortCriteria(); // reset possible user sorting and sort by our preferences
						repaint();
					}
				});
				parameterPanel.add(isConferenceSortPreferencesCheckBox);
			}
		}
		//
		// Sort Filters Button
		{
			resetSortFiltersButton = new CButton(msgBL.translate(ctx, MSG_ResetSortFilters));
			resetSortFiltersButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(final ActionEvent e)
				{
					if (!e.getSource().equals(resetSortFiltersButton))
					{
						return;
					}
					p_table.setReloadOriginalSorting(true); // clear user sorting
					p_table.clearSortCriteria();
					repaint();
				}
			});
			parameterPanel.add(resetSortFiltersButton);
		}
	}

	private int getM_Product_ID()
	{
		final Integer id = getSelectedRowKey();
		return id == null || id == 0 ? -1 : id;
	}

	@Override
	public void actionPerformed(final ActionEvent e)
	{
		final Properties ctx = getCtx();

		final int windowNo = Env.getWindowNo(this);
		final int productId = getM_Product_ID();
		final int row = p_table.getSelectedRow();

		//
		// Info MRP
		if (e.getSource().equals(mrpButton) && row != -1)
		{
			if (productId <= 0)
			{
				return;
			}
			final FormFrame ff = new FormFrame();
			final int rangeDays = Services.get(ISysConfigBL.class).getIntValue(SYSCONFIG_DAYS_RANGE, -1, Env.getAD_Client_ID(ctx), Env.getAD_Org_ID(ctx));

			@SuppressWarnings("unused")
			final VMRPDetailed vmrpPanel = new VMRPDetailed(windowNo, ff, productId, rangeDays);

			AEnv.showCenterWindow(getWindow(), ff);
			ff.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
		}
		//
		// Warenbewegung Ubersicht
		else if (e.getSource().equals(movementButton) && row != -1)
		{
			if (productId <= 0)
			{
				return;
			}
			final FormFrame ff = new FormFrame();

			final VTrxMaterial vtrxMaterialPanel = new VTrxMaterial();

			try
			{
				vtrxMaterialPanel.init(windowNo, ff);
				vtrxMaterialPanel.setProductFieldValue(productId);
				final Timestamp datePromised = getDatePromisedParameter();
				if (datePromised != null)
				{
					vtrxMaterialPanel.setDate(datePromised);
				}
				ff.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);

				ff.setTitle(msgBL.getMsg(ctx, VTrxMaterial.MOVEMENT_TITLE));
			}
			catch (final Exception e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			AEnv.showCenterWindow(getWindow(), ff);
		}
		//
		// Fresh QtyOnHand
		else if (e.getSource().equals(freshQtyOnHandButton))
		{
			final Timestamp datePromised = getDatePromisedParameter();

			final MQuery query = new MQuery(I_Fresh_QtyOnHand.Table_Name);
			query.addRestriction(I_Fresh_QtyOnHand.COLUMNNAME_DateDoc, Operator.EQUAL, datePromised);
			query.setRecordCount(1);
			final int AD_WindowNo = getAD_Window_ID(I_Fresh_QtyOnHand.Table_Name, true);
			zoom(AD_WindowNo, query);
		}
		//
		// Others: forward to parent class
		else
		{
			super.actionPerformed(e);
		}
	}

	@Override
	public void valueChanged(final ListSelectionEvent e)
	{
		super.valueChanged(e);
		if (e.getValueIsAdjusting())
		{
			return;
		}
		if (isLoading())
		{
			return;
		}

		try
		{
			Integer selectedRowKey = getSelectedRowKey();

			// task 09961
			// update the selected product ID and keep it in memory so it can be reselected after refresh
			if (selectedRowKey != null)
			{
				selectedProductID = selectedRowKey;
			}

			getWindow().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			panelDetails.refresh();

		}
		finally
		{
			getWindow().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		}
	}

	@Override
	protected void selectRowAfterLoad()
	{
		final MiniTable miniTable = (MiniTable)getMiniTable();

		// flag to know if a line with the same product was found in the second selection
		boolean found = false;

		// variable to keep the row number which is about to do be highlighted (selected)
		int rowNumber = 0;

		for (int i = 0; i < miniTable.getRowCount(); i++)
		{
			final KeyNamePair productIdValue = (KeyNamePair)miniTable.getValueAt(i, "M_Product_ID");

			if (selectedProductID == productIdValue.getKey())
			{
				// in case there exists a row for the product that was selected before, highlight (select this row)
				miniTable.setSelectedRows(Collections.singletonList(i));

				// it means the row for the product was found
				found = true;

				// remember the row number where the product was found. This will be later used to know the position of the row and make sure it is visible
				rowNumber = i;

				break;
			}
		}

		if (!found)
		{
			// in case there was no row found for the initially selected product, let the selection work as before
			// (fallback to the implementation from Info class)
			super.selectRowAfterLoad();

			// in case there are rows but this one was not found, do not keep the product in memory
			if (miniTable.getRowCount() > 0)
			{
				selectedProductID = 0;
			}
		}

		// scroll to visible. Usefull in case the row of the product was/is placed below the bottom of the window
		scrollToVisible(miniTable, rowNumber, 0);

	}

	/**
	 * From {@link http://stackoverflow.com/questions/853020/jtable-scrolling-to-a-specified-row-index}
	 * 
	 * @param table
	 * @param rowIndex
	 * @param vColIndex
	 */
	private static void scrollToVisible(JTable table, int rowIndex, int vColIndex)
	{
		if (!(table.getParent() instanceof JViewport))
		{
			return;
		}
		JViewport viewport = (JViewport)table.getParent();

		// This rectangle is relative to the table where the
		// northwest corner of cell (0,0) is always (0,0).
		Rectangle rect = table.getCellRect(rowIndex, vColIndex, true);

		// The location of the viewport relative to the table
		Point pt = viewport.getViewPosition();

		// Translate the cell location so that it is relative
		// to the view, assuming the northwest corner of the
		// view is (0,0)
		rect.setLocation(rect.x - pt.x, rect.y - pt.y);

		table.scrollRectToVisible(rect);
	}

	private Timestamp getDatePromisedParameter()
	{
		final IInfoQueryCriteria datePromisedParam = getParameterByColumnNameOrNull("DateGeneral");
		final Timestamp datePromised = (Timestamp)datePromisedParam.getParameterValue(0, false); // index=0; returnValueTo=false (date will take the first editor)
		return datePromised;
	}

	@Override
	protected void showHistory()
	{
		final Integer M_Product_ID = getSelectedRowKey();
		if (M_Product_ID == null || M_Product_ID <= 0)
		{
			return;
		}

		int M_Warehouse_ID = getContextVariableAsInt("M_Warehouse_ID");
		if (M_Warehouse_ID <= 0)
		{
			M_Warehouse_ID = 0; // backward compatibility (see InvoiceHistory)
		}

		int M_AttributeSetInstance_ID = getContextVariableAsInt("M_AttributeSetInstance_ID");
		if (M_AttributeSetInstance_ID == -1)
		{
			M_AttributeSetInstance_ID = 0; // backward compatibility (see InvoiceHistory)
		}

		final int C_BPartner_ID = 0; // N/A

		final Timestamp datePromised = getDatePromisedParameter();

		final InvoiceHistoryContext ihCtx = InvoiceHistoryContext.builder()
				.setC_BPartner_ID(C_BPartner_ID)
				.setM_Product_ID(M_Product_ID)
				.setM_Warehouse_ID(M_Warehouse_ID)
				.setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID)
				.setDatePromised(datePromised) // filter by the date parameter
				.setRowSelectionAllowed(true)
				.build();

		final IInvoiceHistoryTabHandler invoiceHistoryTabHandler = ihCtx.getInvoiceHistoryTabHandler();
		invoiceHistoryTabHandler.setTabEnabled(InvoiceHistory.TAB_PRICEHISTORY, false);
		invoiceHistoryTabHandler.setTabEnabled(InvoiceHistory.TAB_RESERVED, true);
		invoiceHistoryTabHandler.setTabEnabled(InvoiceHistory.TAB_ORDERED, true);
		invoiceHistoryTabHandler.setTabEnabled(InvoiceHistory.TAB_UNCONFIRMED, false);
		invoiceHistoryTabHandler.setTabEnabled(InvoiceHistory.TAB_ATP, false);
		invoiceHistoryTabHandler.setTabEnabled(InvoiceHistory.TAB_RECEIVED, true);
		invoiceHistoryTabHandler.setTabEnabled(InvoiceHistory.TAB_DELIVERED, true);

		//
		InvoiceHistory ih = new InvoiceHistory(ihCtx, getWindow());
		//
		ih.setBounds(80, 380, 1420, 450); // Fresh 08574: Position and resize window for users to see everything nicely and as much as possible from the fist go on wide screens
		//
		ih.setVisible(true);
		ih = null;
	}

	@Override
	public void showWindow()
	{
		final Window window = getWindow();
		AEnv.showMaximized(window);
	}

	@Override
	public boolean hasZoom()
	{
		return false;
	}
}
