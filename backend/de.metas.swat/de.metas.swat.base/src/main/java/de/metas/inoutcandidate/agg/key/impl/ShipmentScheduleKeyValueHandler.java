package de.metas.inoutcandidate.agg.key.impl;

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

import de.metas.async.AsyncBatchId;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.DocTypeId;
import de.metas.inoutcandidate.CarrierGoodsTypeId;
import de.metas.inoutcandidate.CarrierProductId;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.impl.ShipmentScheduleHeaderAggregationKeyBuilder;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.OrderId;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.agg.key.IAggregationKeyValueHandler;
import org.adempiere.util.lang.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * AggregationKey value handler for shipment schedules.
 *
 * @author al
 * @see ShipmentScheduleHeaderAggregationKeyBuilder
 */
public class ShipmentScheduleKeyValueHandler implements IAggregationKeyValueHandler<I_M_ShipmentSchedule>
{
	private static final String VERSION = "2";

	@Override
	public List<Object> getValues(@NonNull final I_M_ShipmentSchedule sched)
	{
		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
		final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);

		final List<Object> values = new ArrayList<>();

		values.add("v:" + VERSION);

		values.add("dt:" + DocTypeId.toRepoId(DocTypeId.ofRepoIdOrNull(sched.getC_DocType_ID())));
		values.add("bp:" + shipmentScheduleEffectiveBL.getBPartnerId(sched).getRepoId());
		values.add("bpl:" + BPartnerLocationId.toRepoId(shipmentScheduleEffectiveBL.getBPartnerLocationId(sched)));
		if (!shipmentScheduleBL.isSchedAllowsConsolidate(sched))
		{
			final OrderId orderId = OrderId.ofRepoIdOrNull(sched.getC_Order_ID());
			values.add("ord:" + OrderId.toRepoId(orderId));
		}
		values.add("wh:" + shipmentScheduleEffectiveBL.getWarehouseId(sched).getRepoId());

		final BPartnerContactId bpartnerContactId = shipmentScheduleEffectiveBL.getBPartnerContactId(sched);
		if (bpartnerContactId != null)
		{
			values.add("bpc:" + bpartnerContactId.getRepoId());
		}
		values.add("org:" + sched.getAD_Org_ID());

		final ShipperId shipperId = ShipperId.ofRepoIdOrNull(sched.getM_Shipper_ID());
		if (shipperId != null)
		{
			values.add("shp:" + shipperId.getRepoId());

			final CarrierGoodsTypeId carrierGoodsTypeId = CarrierGoodsTypeId.ofRepoIdOrNull(sched.getCarrier_Goods_Type_ID());
			if (carrierGoodsTypeId != null)
			{
				values.add("cgt:" + carrierGoodsTypeId.getRepoId());
			}

			final CarrierProductId carrierProductId = CarrierProductId.ofRepoIdOrNull(sched.getCarrier_Product_ID());
			if (carrierProductId != null)
			{
				values.add("cpr:" + carrierProductId.getRepoId());
			}
		}

		final AsyncBatchId asyncBatchId = AsyncBatchId.ofRepoIdOrNull(sched.getC_Async_Batch_ID());
		if (asyncBatchId != null)
		{
			values.add("ab:" + asyncBatchId.getRepoId());
		}

		final String externalHeaderId = sched.getExternalHeaderId();
		if (externalHeaderId != null)
		{
			values.add("extId:" + externalHeaderId);
		}

		return values;
	}


	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}
}
