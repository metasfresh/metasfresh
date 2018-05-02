package de.metas.shipper.gateway.derkurier;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableSet;

import de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrder;
import de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrderLine;
import de.metas.shipper.gateway.spi.DeliveryOrderRepository;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryPosition;
import lombok.NonNull;

/*
 * #%L
 * de.metas.shipper.gateway.derkurier
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Repository
public class DerKurierDeliveryOrderRepository implements DeliveryOrderRepository
{

	@Override
	public String getShipperGatewayId()
	{
		return DerKurierConstants.SHIPPER_GATEWAY_ID;
	}

	@Override
	public ITableRecordReference toTableRecordReference(DeliveryOrder deliveryOrder)
	{
		return TableRecordReference.of(I_DerKurier_DeliveryOrder.Table_Name, deliveryOrder.getRepoId());
	}

	@Override
	public DeliveryOrder getByRepoId(final int deliveryOrderRepoId)
	{
		final I_DerKurier_DeliveryOrder orderPO = loadAssumeRecordExists(deliveryOrderRepoId, I_DerKurier_DeliveryOrder.class);

		final DeliveryOrder deliveryOrder = toDeliveryOrder(orderPO);
		return deliveryOrder;
	}

	private DeliveryOrder toDeliveryOrder(I_DerKurier_DeliveryOrder orderPO)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DeliveryOrder save(@NonNull final DeliveryOrder order)
	{
		final I_DerKurier_DeliveryOrder headerRecord;
		if (order.getRepoId() <= 0)
		{
			headerRecord = newInstance(I_DerKurier_DeliveryOrder.class);
			InterfaceWrapperHelper.save(headerRecord);
		}
		else
		{
			headerRecord = loadAssumeRecordExists(order.getRepoId(), I_DerKurier_DeliveryOrder.class);
		}
		final List<DeliveryPosition> deliveryPositions = order.getDeliveryPositions();
		for (final DeliveryPosition deliveryPosition : deliveryPositions)
		{
			final I_DerKurier_DeliveryOrderLine lineRecord = loadOrNewInstance(deliveryPosition.getRepoId());
			InterfaceWrapperHelper.save(lineRecord);

			syncPackageAllocation(lineRecord, deliveryPosition.getPackageIds());

		}
		// TODO Auto-generated method stub
		return order.toBuilder()
				.repoId(headerRecord.getDerKurier_DeliveryOrder_ID()).build();
	}

	private void syncPackageAllocation(
			@NonNull final I_DerKurier_DeliveryOrderLine lineRecord,
			@NonNull final ImmutableSet<Integer> packageIds)
	{
		// TODO create and delete; leave existing records that didn't change untouched
		for (final int packageId : packageIds)
		{

		}
	}

	private I_DerKurier_DeliveryOrderLine loadOrNewInstance(final int deliveryOrderLineRepoId)
	{
		if (deliveryOrderLineRepoId <= 0)
		{
			return newInstance(I_DerKurier_DeliveryOrderLine.class);
		}
		return loadAssumeRecordExists(deliveryOrderLineRepoId, I_DerKurier_DeliveryOrderLine.class);
	}

	private <T> T loadAssumeRecordExists(
			final int repoId,
			@NonNull final Class<T> modelClass)
	{
		Check.assume(repoId > 0, "deliveryOrderRepoId > 0");
		final T orderPO = InterfaceWrapperHelper.load(repoId, modelClass);
		Check.assumeNotNull(orderPO, "A data recordfor modelClass={} and ID={}", modelClass.getSimpleName(), repoId);

		return orderPO;
	}

}
