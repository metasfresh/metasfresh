package de.metas.order.createFrom.po_from_so.impl;

import de.metas.order.createFrom.po_from_so.PurchaseTypeEnum;
import lombok.NonNull;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import de.metas.util.Services;



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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
/**
 * Used by {@link CreatePOLineFromSOLinesAggregator} to create the keys that decide which sales order line belongs to which purchase order line.
 * @author metas-dev <dev@metasfresh.com>
 *
 */
class CreatePOLineFromSOLinesAggregationKeyBuilder extends AbstractOrderLineAggregationKeyBuilder
{
	/** Merges sales order lines with the same product and attributes into one purchase order line. */
	private static final CreatePOLineFromSOLinesAggregationKeyBuilder MERGE_SAME_PRODUCT_AND_ATTRIBUTES = new CreatePOLineFromSOLinesAggregationKeyBuilder(false);

	/**
	 * One purchase order line per sales order line. Used for drop-ship purchase orders: the drop-ship receipt
	 * books each received HU onto the shipment schedule of the one sales order line its purchase order line is allocated to.
	 */
	private static final CreatePOLineFromSOLinesAggregationKeyBuilder ONE_PER_SALES_ORDER_LINE = new CreatePOLineFromSOLinesAggregationKeyBuilder(true);

	private final boolean onePerSalesOrderLine;

	private CreatePOLineFromSOLinesAggregationKeyBuilder(final boolean onePerSalesOrderLine)
	{
		this.onePerSalesOrderLine = onePerSalesOrderLine;
	}

	static CreatePOLineFromSOLinesAggregationKeyBuilder forPurchaseType(@NonNull final PurchaseTypeEnum purchaseType)
	{
		return purchaseType.isDropship() ? ONE_PER_SALES_ORDER_LINE : MERGE_SAME_PRODUCT_AND_ATTRIBUTES;
	}

	/**
	 * @return a key consisting of the given line's product and ASI, plus the line itself if there is one purchase order line per sales order line.
	 */
	@Override
	public String buildKey(final I_C_OrderLine salesOrderLine)
	{
		final I_M_AttributeSetInstance attributeSetInstance = salesOrderLine.getM_AttributeSetInstance();
		final boolean verboseDescription = true;

		final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
		final String asiDescription = attributeSetInstanceBL.buildDescription(attributeSetInstance, verboseDescription);

		final ArrayKey poLineKey = onePerSalesOrderLine
				? Util.mkKey(salesOrderLine.getM_Product_ID(), asiDescription, salesOrderLine.getC_OrderLine_ID())
				: Util.mkKey(salesOrderLine.getM_Product_ID(), asiDescription);

		return poLineKey.toString();
	}
}
