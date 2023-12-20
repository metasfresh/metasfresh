package de.metas.order.createFrom.po_from_so.impl;

import de.metas.organization.ClientAndOrgId;
import de.metas.util.Services;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.util.ArrayKeyBuilder;
import org.compiere.util.Util.ArrayKey;



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
 *
 * @author metas-dev <dev@metasfresh.com>
 */
class CreatePOLineFromSOLinesAggregationKeyBuilder extends AbstractOrderLineAggregationKeyBuilder
{

	/*package*/ static CreatePOLineFromSOLinesAggregationKeyBuilder INSTANCE = new CreatePOLineFromSOLinesAggregationKeyBuilder();

	public static final String SYSCONFIG_GROUP_LINES_BY_PROMISED_DATE = "de.metas.order.C_Order_CreatePOFromSOs.GroupLinesByPromisedDate";

	private final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

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

		final ArrayKeyBuilder arrayKeyBuilder = ArrayKey.builder()
				.appendId(salesOrderLine.getM_Product_ID())
				.append(attributeSetInstanceBL.buildDescription(attributeSetInstance, verboseDescription));

		if (sysConfigBL.getBooleanValue(SYSCONFIG_GROUP_LINES_BY_PROMISED_DATE, false, ClientAndOrgId.ofClientAndOrg(salesOrderLine.getAD_Client_ID(), salesOrderLine.getAD_Org_ID())) && salesOrderLine.getDatePromised() != null)
		{
			arrayKeyBuilder.append(salesOrderLine.getDatePromised());
		}
		return arrayKeyBuilder.build()
				.toString();
	}
}
