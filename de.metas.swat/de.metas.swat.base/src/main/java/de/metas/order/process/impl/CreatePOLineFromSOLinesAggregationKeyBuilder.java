package de.metas.order.process.impl;

import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.util.Services;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import de.metas.interfaces.I_C_OrderLine;

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
public class CreatePOLineFromSOLinesAggregationKeyBuilder extends AbstractOrderLineAggregationKeyBuilder
{

	/*package*/ static CreatePOLineFromSOLinesAggregationKeyBuilder INSTANCE = new CreatePOLineFromSOLinesAggregationKeyBuilder();

	private CreatePOLineFromSOLinesAggregationKeyBuilder()
	{
	}

	/**
	 * @return a key consisting of the given line's product and ASI.
	 */
	@Override
	public String buildKey(final I_C_OrderLine salesOrderLine)
	{
		final I_M_AttributeSetInstance attributeSetInstance = salesOrderLine.getM_AttributeSetInstance();
		final boolean verboseDescription = true;

		final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
		final ArrayKey poLineKey = Util.mkKey(
				salesOrderLine.getM_Product_ID(),
				attributeSetInstanceBL.buildDescription(attributeSetInstance, verboseDescription)
				);

		return poLineKey.toString();
	}
}
