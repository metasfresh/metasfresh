package de.metas.material.dispo.commons.candidate;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import org.adempiere.util.Check;

import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Demand_Detail;
import de.metas.material.event.commons.DocumentLineDescriptor;
import de.metas.material.event.commons.OrderLineDescriptor;
import de.metas.material.event.commons.SubscriptionLineDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-material-dispo
 * %%
 * Copyright (C) 2017 metas GmbH
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
@Value
@Builder(toBuilder = true)
public class DemandDetail implements BusinessCaseDetail
{
	public static DemandDetail forDocumentDescriptor(
			final int shipmentScheduleId,
			@NonNull final DocumentLineDescriptor documentDescriptor,
			@NonNull final BigDecimal plannedQty)
	{
		final int orderId;
		final int orderLineId;
		final int subscriptionProgressId;
		if (documentDescriptor instanceof OrderLineDescriptor)
		{
			final OrderLineDescriptor orderLineDescriptor = (OrderLineDescriptor)documentDescriptor;
			orderLineId = orderLineDescriptor.getOrderLineId();
			orderId = orderLineDescriptor.getOrderId();
			subscriptionProgressId = -1;
		}
		else if (documentDescriptor instanceof SubscriptionLineDescriptor)
		{
			orderLineId = 0;
			orderId = 0;
			subscriptionProgressId = ((SubscriptionLineDescriptor)documentDescriptor).getSubscriptionProgressId();
		}
		else
		{
			Check.errorIf(true,
					"The given documentDescriptor has an unexpected type; documentDescriptor={}", documentDescriptor);
			return null;
		}

		return DemandDetail.builder()
				.shipmentScheduleId(shipmentScheduleId)
				.orderLineId(orderLineId)
				.orderId(orderId)
				.subscriptionProgressId(subscriptionProgressId)
				.plannedQty(plannedQty).build();
	}

	public static DemandDetail forSupplyRequiredDescriptorOrNull(
			@Nullable final SupplyRequiredDescriptor supplyRequiredDescriptor)
	{
		if (supplyRequiredDescriptor == null)
		{
			return null;
		}
		return DemandDetail.builder()
				.demandCandidateId(supplyRequiredDescriptor.getDemandCandidateId())
				.forecastId(supplyRequiredDescriptor.getForecastId())
				.forecastLineId(supplyRequiredDescriptor.getForecastLineId())
				.orderId(supplyRequiredDescriptor.getOrderId())
				.orderLineId(supplyRequiredDescriptor.getOrderLineId())
				.shipmentScheduleId(supplyRequiredDescriptor.getShipmentScheduleId())
				.subscriptionProgressId(supplyRequiredDescriptor.getSubscriptionProgressId())
				.plannedQty(supplyRequiredDescriptor.getMaterialDescriptor().getQuantity())
				.build();
	}

	public static DemandDetail forDemandDetailRecord(
			@NonNull final I_MD_Candidate_Demand_Detail demandDetailRecord)
	{
		final I_MD_Candidate demandRecord = demandDetailRecord.getMD_Candidate();

		return DemandDetail.builder()
				.shipmentScheduleId(demandDetailRecord.getM_ShipmentSchedule_ID())
				.forecastId(demandRecord.getM_Forecast_ID())
				.forecastLineId(demandDetailRecord.getM_ForecastLine_ID())
				.orderId(demandRecord.getC_Order_ID())
				.orderLineId(demandDetailRecord.getC_OrderLine_ID())
				.subscriptionProgressId(demandDetailRecord.getC_SubscriptionProgress_ID())
				.plannedQty(demandDetailRecord.getPlannedQty()).build();
	}

	public static DemandDetail forShipmentScheduleIdAndOrderLineId(
			final int shipmentScheduleId,
			final int orderLineId,
			final int orderId,
			@NonNull final BigDecimal plannedQty)
	{
		return DemandDetail.builder()
				.shipmentScheduleId(shipmentScheduleId)
				.orderLineId(orderLineId)
				.orderId(orderId)
				.plannedQty(plannedQty).build();
	}

	public static DemandDetail forForecastLineId(
			final int forecastLineId,
			final int forecastId,
			@NonNull final BigDecimal plannedQty)
	{
		return DemandDetail.builder()
				.forecastLineId(forecastLineId)
				.forecastId(forecastId)
				.plannedQty(plannedQty).build();
	}

	int forecastId;

	int forecastLineId;

	int shipmentScheduleId;

	int orderId;

	int orderLineId;

	int subscriptionProgressId;

	BigDecimal plannedQty;

	/**
	 * Used when a new supply candidate is created, to link it to it's respective demand candidate;
	 * When a demand detail is loaded from DB, this field is always <= 0.
	 */
	int demandCandidateId;

	@Override
	public CandidateBusinessCase getCandidateBusinessCase()
	{
		return CandidateBusinessCase.SHIPMENT;
	}

	public static DemandDetail castOrNull(@Nullable final BusinessCaseDetail businessCaseDetail)
	{
		final boolean canBeCast = businessCaseDetail != null && businessCaseDetail instanceof DemandDetail;
		if (canBeCast)
		{
			return cast(businessCaseDetail);
		}
		return null;
	}

	public static DemandDetail cast(@NonNull final BusinessCaseDetail businessCaseDetail)
	{
		return (DemandDetail)businessCaseDetail;
	}
}
