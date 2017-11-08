package de.metas.inoutcandidate.api;

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

import java.math.BigDecimal;
import java.util.Optional;

import javax.annotation.Nullable;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.interfaces.I_C_OrderLine;
import lombok.Builder;
import lombok.NonNull;

/**
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class OlAndSched
{
	private final I_M_ShipmentSchedule shipmentSchedule;
	@Nullable
	private final I_C_OrderLine orderLineOrNull;
	private final IDeliverRequest deliverRequest;
	private final BigDecimal initialSchedQtyDelivered;

	private boolean availForShipmentRun;

	@Deprecated
	public OlAndSched(
			@Nullable final org.compiere.model.I_C_OrderLine ol,
			@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		this(ol, shipmentSchedule, Services.get(IShipmentScheduleHandlerBL.class).createDeliverRequest(shipmentSchedule));
	}

	@Builder
	private OlAndSched(
			@Nullable final org.compiere.model.I_C_OrderLine orderLineOrNull,
			@NonNull final I_M_ShipmentSchedule shipmentSchedule,
			@Nullable final IDeliverRequest deliverRequest)
	{
		this.orderLineOrNull = InterfaceWrapperHelper.create(orderLineOrNull, I_C_OrderLine.class);
		this.shipmentSchedule = shipmentSchedule;

		if (deliverRequest == null)
		{
			this.deliverRequest = Services.get(IShipmentScheduleHandlerBL.class).createDeliverRequest(shipmentSchedule);
		}
		else
		{
			this.deliverRequest = deliverRequest;
		}

		initialSchedQtyDelivered = shipmentSchedule.getQtyDelivered();
	}

	public IDeliverRequest getDeliverRequest()
	{
		return deliverRequest;
	}

	public Optional<I_C_OrderLine> getOl()
	{
		return Optional.ofNullable(orderLineOrNull);
	}

	public I_M_ShipmentSchedule getSched()
	{
		return shipmentSchedule;
	}

	/**
	 * @return shipment schedule's QtyToDeliver_Override or <code>null</code>
	 */
	public BigDecimal getQtyOverride()
	{
		return InterfaceWrapperHelper.getValueOrNull(shipmentSchedule, I_M_ShipmentSchedule.COLUMNNAME_QtyToDeliver_Override);
	}

	public boolean isAvailForShipmentRun()
	{
		return availForShipmentRun;
	}

	public void setAvailForShipmentRun(final boolean availForShipmentRun)
	{
		this.availForShipmentRun = availForShipmentRun;
	}

	public BigDecimal getInitialSchedQtyDelivered()
	{
		return initialSchedQtyDelivered;
	}

	@Override
	public String toString()
	{
		return String.valueOf(orderLineOrNull) + " / " + shipmentSchedule;
	}
}
