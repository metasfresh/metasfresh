/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2021 metas GmbH
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

package org.eevolution.productioncandidate.agg.key.impl;

import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.agg.key.IAggregationKeyRegistry;
import org.adempiere.util.agg.key.IAggregationKeyValueHandler;
import org.adempiere.util.lang.ObjectUtils;
import org.eevolution.model.I_PP_Order_Candidate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * AggregationKey value handler for production order candidates.
 */
@Component
public class PPOrderCandidateKeyValueHandler implements IAggregationKeyValueHandler<I_PP_Order_Candidate>
{
	@Override
	public List<Object> getValues(@NonNull final I_PP_Order_Candidate orderCandidateRecord)
	{
		final List<Object> values = new ArrayList<>();

		values.add(orderCandidateRecord.getM_Warehouse_ID());
		values.add(orderCandidateRecord.getS_Resource_ID());
		values.add(Math.max(orderCandidateRecord.getWorkStation_ID(), 0));

		if (orderCandidateRecord.getPP_Product_Planning_ID() > 0)
		{
			values.add(orderCandidateRecord.getPP_Product_Planning_ID());
		}

		values.add(orderCandidateRecord.getPP_Product_BOM_ID());
		values.add(orderCandidateRecord.getM_Product_ID());
		values.add(orderCandidateRecord.getM_AttributeSetInstance());

		if (orderCandidateRecord.getC_OrderLine_ID() > 0)
		{
			values.add(orderCandidateRecord.getC_OrderLine_ID());
		}

		if (orderCandidateRecord.getM_ShipmentSchedule_ID() > 0)
		{
			values.add(orderCandidateRecord.getM_ShipmentSchedule_ID());
		}

		values.add(orderCandidateRecord.getDatePromised());
		values.add(orderCandidateRecord.getDateStartSchedule());
		values.add(orderCandidateRecord.getC_UOM_ID());

		return values;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@PostConstruct
	public void register()
	{
		final IAggregationKeyRegistry keyRegistry = Services.get(IAggregationKeyRegistry.class);

		keyRegistry.registerAggregationKeyValueHandler(PPOrderCandidateHeaderAggregationKeyBuilder.REGISTRATION_KEY, this);

		keyRegistry.registerDependsOnColumnnames(PPOrderCandidateHeaderAggregationKeyBuilder.REGISTRATION_KEY,
												 I_PP_Order_Candidate.COLUMNNAME_M_Warehouse_ID,
												 I_PP_Order_Candidate.COLUMNNAME_S_Resource_ID,
												 I_PP_Order_Candidate.COLUMNNAME_PP_Product_Planning_ID,
												 I_PP_Order_Candidate.COLUMNNAME_PP_Product_BOM_ID,
												 I_PP_Order_Candidate.COLUMNNAME_M_Product_ID,
												 I_PP_Order_Candidate.COLUMNNAME_M_AttributeSetInstance_ID,
												 I_PP_Order_Candidate.COLUMNNAME_C_OrderLine_ID,
												 I_PP_Order_Candidate.COLUMNNAME_M_ShipmentSchedule_ID,
												 I_PP_Order_Candidate.COLUMNNAME_DatePromised,
												 I_PP_Order_Candidate.COLUMNNAME_DateStartSchedule,
												 I_PP_Order_Candidate.COLUMNNAME_C_UOM_ID);
	}
}