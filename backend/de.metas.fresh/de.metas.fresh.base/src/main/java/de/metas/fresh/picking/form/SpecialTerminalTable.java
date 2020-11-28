/**
 * 
 */
package de.metas.fresh.picking.form;

/*
 * #%L
 * de.metas.swat.base
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

import java.awt.Color;
import java.awt.Component;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ListSelectionModel;

import org.adempiere.ad.service.IDeveloperModeBL;
import org.compiere.minigrid.IDColumn;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.swing.TerminalTable;
import de.metas.i18n.IMsgBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.util.Services;

@SuppressWarnings("serial")
final class SpecialTerminalTable extends TerminalTable
{
	private static final String COLUMNNAME_ROWID = "ROWID";
	private static final String COLUMNNAME_M_Product_ID = I_M_ShipmentSchedule.COLUMNNAME_M_Product_ID;
	private static final String COLUMNNAME_Qty = "Qty";
	private static final String COLUMNNAME_DeliveryDate = I_M_ShipmentSchedule.COLUMNNAME_DeliveryDate;
	private static final String COLUMNNAME_PreparationDate = de.metas.tourplanning.model.I_M_ShipmentSchedule.COLUMNNAME_PreparationDate;
	private static final String COLUMNNAME_BPValue = I_C_BPartner.COLUMNNAME_Value;
	private static final String COLUMNNAME_C_BPartner_Location_ID = I_M_ShipmentSchedule.COLUMNNAME_C_BPartner_Location_ID;
	private static final String COLUMNNAME_M_Warehouse_Dest_ID = I_M_ShipmentSchedule.COLUMNNAME_M_Warehouse_Dest_ID;
	/** Barcode matching type column */
	private static final String COLUMNNAME_MatchingType = "B";

	private final Map<String, Integer> columnName2index = new HashMap<>();

	/**
	 * @param tc
	 */
	public SpecialTerminalTable(ITerminalContext tc)
	{
		super(tc);
		setAutoResize(true);

		columnName2index.clear();
		// setMultiSelection(false);
		setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		//
		// Create Columns
		addColumn(COLUMNNAME_ROWID);

		addColumn(COLUMNNAME_M_Product_ID);
		addColumn(COLUMNNAME_Qty);
		addColumn(COLUMNNAME_DeliveryDate); // 01676
		addColumn(COLUMNNAME_PreparationDate); // 01676

		addColumn(COLUMNNAME_MatchingType);

		//
		// Setup columns
		// NOTE: we need to do this in two phases (addColumns and then setup)
		// because there is a fucked up thing regarding org.compiere.minigrid.MiniTable.addColumn(String)
		// ... see the comment from there
		setupColumnIfExists(COLUMNNAME_ROWID, DisplayType.ID, IDColumn.class);
		// ((MiniTable)miniTable).setColorColumn(idColumnIndex); // not needed
		setupColumnIfExists(COLUMNNAME_M_Product_ID, DisplayType.String, String.class);
		setupColumnIfExists(COLUMNNAME_Qty, DisplayType.Quantity, BigDecimal.class);
		setupColumnIfExists(COLUMNNAME_DeliveryDate, DisplayType.Date, Timestamp.class); // 01676
		setupColumnIfExists(COLUMNNAME_PreparationDate, DisplayType.Time, Timestamp.class);
		setupColumnIfExists(COLUMNNAME_BPValue, DisplayType.String, String.class);
		setupColumnIfExists(COLUMNNAME_C_BPartner_Location_ID, DisplayType.String, String.class);
		setupColumnIfExists(COLUMNNAME_M_Warehouse_Dest_ID, DisplayType.String, String.class);
		setupColumnIfExists(COLUMNNAME_MatchingType, DisplayType.String, String.class);

		autoSize();
	}

	@Override
	public void addColumn(final String columnName)
	{
		super.addColumn(columnName);

		final int columnIndex = getColumnCount() - 1;
		columnName2index.put(columnName, columnIndex);
	}

	private void setupColumnIfExists(
			final String columnName,
			final int displayType,
			final Class<?> columnClass)
	{
		final Integer columnIndex = columnName2index.get(columnName);
		if (columnIndex == null)
		{
			// column does not exist, skip it
			return;
		}

		final String columnNameTrl;
		if (IDColumn.class.equals(columnClass))
		{
			columnNameTrl = " ";
		}
		else if (Services.get(IDeveloperModeBL.class).isEnabled())
		{
			columnNameTrl = columnName;
		}
		else if (columnName.length() <= 2)
		{
			// don't translate short column names
			columnNameTrl = columnName;
		}
		else
		{
			columnNameTrl = Services.get(IMsgBL.class).translate(Env.getCtx(), columnName);
		}

		final boolean readonly = true;
		setColumnClass(columnIndex, displayType, columnClass, readonly, columnNameTrl);
	}

	private void setCellValue(final String columnName, final int rowIndex, final Object value)
	{
		final Integer columnIndex = columnName2index.get(columnName);
		if (columnIndex == null)
		{
			// If column is missing, then we just skip setting the value
			return;
		}

		setValueAt(value, rowIndex, columnIndex);
	}

	public final void updateRow(final int rowIdx, final TableRow currentRow, final ITableRowSearchSelectionMatcher matcher)
	{
		final TableRowKey key = currentRow.getKey();

		// set values
		setCellValue(COLUMNNAME_ROWID, rowIdx, new IDColumn(key.hashCode())); // ID
		setCellValue(COLUMNNAME_M_Product_ID, rowIdx, currentRow.getProductName());
		setCellValue(COLUMNNAME_Qty, rowIdx, currentRow.getQtyToDeliver());
		setCellValue(COLUMNNAME_DeliveryDate, rowIdx, currentRow.getDeliveryDate());
		setCellValue(COLUMNNAME_PreparationDate, rowIdx, currentRow.getPreparationDate());
		setCellValue(COLUMNNAME_BPValue, rowIdx, currentRow.getBpartnerValue()); // BP Value
		setCellValue(COLUMNNAME_C_BPartner_Location_ID, rowIdx, currentRow.getBpartnerLocationName()); // BP Location Name
		setCellValue(COLUMNNAME_M_Warehouse_Dest_ID, rowIdx, currentRow.getWarehouseDestName());

		final String matchingType;
		if (matcher != null && matcher.match(key))
		{
			matchingType = matcher.getName();
		}
		else
		{
			matchingType = null;
		}
		setCellValue(COLUMNNAME_MatchingType, rowIdx, matchingType);
	}

	public void setColorForIDColumn(final Color color, final int row)
	{
		final Object value = getValueAt(row, 0);
		final boolean isSelected = isCellSelected(row, 0);
		final boolean hasFocus = hasFocus();
		final int column = 0; // ID Column
		final Component comp = getCellRenderer(row, 0).getTableCellRendererComponent(this, value, isSelected, hasFocus, row, column);
		comp.setBackground(color);
	}

	@Override
	public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend)
	{
		final ListSelectionModel selectionModel = getSelectionModel();
		boolean selected = selectionModel.isSelectedIndex(rowIndex);
		if (selected)
		{
			selectionModel.removeSelectionInterval(rowIndex, rowIndex);
			getValueAt(rowIndex, columnIndex);
		}
		else
		{
			selectionModel.addSelectionInterval(rowIndex, rowIndex);
		}
	}
}
