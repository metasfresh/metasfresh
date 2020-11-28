package de.metas.handlingunits.client.terminal.ddorder.model;

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
import java.util.List;
import java.util.Set;

import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_DD_OrderLine;

import de.metas.adempiere.form.terminal.table.annotation.TableInfo;
import de.metas.handlingunits.client.terminal.ddorder.api.impl.DDOrderTableRowAggregationKey;
import de.metas.handlingunits.client.terminal.select.api.IPOSTableRow;

public interface IDDOrderTableRow extends IPOSTableRow
{
	@TableInfo(hidden = true)
	@Override
	I_C_BPartner getC_BPartner();

	@TableInfo(hidden = true)
	Set<Integer> getDD_Order_IDs();

	@TableInfo(hidden = true)
	List<I_DD_OrderLine> getDD_OrderLines();

	@TableInfo(hidden = true)
	DDOrderTableRowAggregationKey getAggregationKey();

	@TableInfo(hidden = true)
	List<DDOrderLineAndAlternatives> getDDOrderLineAndAlternatives();

	@TableInfo(hidden = true)
	@Override
	I_C_Order getC_Order();

	@TableInfo(displayName = "DateOrdered", translate = true, seqNo = 10)
	Date getDateOrdered();

	@TableInfo(displayName = "DatePromised", translate = true, seqNo = 20)
	Date getDatePromised();

	@TableInfo(displayName = "BPName", translate = true, seqNo = 30)
	String getBPName();

	@TableInfo(displayName = "ProductName", translate = true, seqNo = 40)
	String getProductName();

	@TableInfo(hidden = true)
	List<I_M_Product> getM_Products();

	@TableInfo(displayName = "QtyOrdered", translate = true, seqNo = 50)
	BigDecimal getQtyOrdered();

	@TableInfo(displayName = "QtyDelivered", translate = true, seqNo = 60)
	BigDecimal getQtyDelivered();

	@TableInfo(displayName = "M_HU_PI_Item_Product_ID", translate = true, seqNo = 70)
	String getM_HU_PI_Item_Product_Name();

	@TableInfo(displayName = "SourceWarehouse", translate = true, seqNo = 80)
	String getSourceWarehouseName();

	@TableInfo(hidden = true)
	I_M_Locator getM_Locator_From();
}
