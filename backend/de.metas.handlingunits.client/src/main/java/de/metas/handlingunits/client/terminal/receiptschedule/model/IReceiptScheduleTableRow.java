package de.metas.handlingunits.client.terminal.receiptschedule.model;

/*
 * #%L
 * de.metas.handlingunits.client
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
import java.util.Date;

import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.util.KeyNamePair;

import de.metas.adempiere.form.terminal.table.annotation.TableInfo;
import de.metas.handlingunits.client.terminal.select.api.IPOSTableRow;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;

public interface IReceiptScheduleTableRow extends IPOSTableRow
{
	@TableInfo(hidden = true)
	@Override
	I_C_BPartner getC_BPartner();

	@TableInfo(hidden = true)
	@Override
	I_C_Order getC_Order();

	@TableInfo(displayName = "BPName", translate = true, seqNo = 10)
	String getBPName();

	@TableInfo(displayName = "ProductName", translate = true, seqNo = 20)
	String getProductNameAndASI();

	@TableInfo(displayName = "QtyOrdered", translate = true, seqNo = 30)
	BigDecimal getQtyOrdered();

	@TableInfo(displayName = "QtyMoved", translate = true, seqNo = 40)
	BigDecimal getQtyMoved();

	@TableInfo(displayName = "UOMSymbol", translate = true, seqNo = 50)
	String getUOMSymbol();

	@TableInfo(displayName = "M_HU_PI_Item_Product_ID", translate = true, seqNo = 60)
	String getM_HU_PI_Item_Product_Name();

	@TableInfo(displayName = "MovementDate", translate = true, seqNo = 70)
	Date getMovementDate();

	@TableInfo(displayName = "PreparationTime", translate = true, seqNo = 80)
	java.sql.Time getPreparationTime();

	@TableInfo(displayName = "QualityDiscountPercent", translate = true, seqNo = 90)
	BigDecimal getQualityDiscountPercent();

	@TableInfo(displayName = "QualityNote", translate = true, seqNo = 100, hidden = true)
	String getQualityNote();

	@TableInfo(displayName = "M_Warehouse_Dest_ID", translate = true, seqNo = 110
			, lookupTableName = I_M_ReceiptSchedule.Table_Name
			, lookupColumnName = I_M_ReceiptSchedule.COLUMNNAME_M_Warehouse_Dest_ID)
	KeyNamePair getM_Warehouse_Dest();

	void setM_Warehouse_Dest(final KeyNamePair warehouseDest);
}
