package de.metas.material.dispo.commons.candidate.businesscase;

import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.event.commons.DocumentLineDescriptor;
import de.metas.material.event.commons.OrderLineDescriptor;
import de.metas.material.event.commons.SubscriptionLineDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;

import static de.metas.common.util.IdConstants.UNSPECIFIED_REPO_ID;
import static de.metas.common.util.IdConstants.toUnspecifiedIfZero;

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
	@NonNull
	public static DemandDetail forDocumentLine(
			final int shipmentScheduleId,
			@NonNull final DocumentLineDescriptor documentDescriptor,
			@NonNull final BigDecimal plannedQty)
	{
		final int orderId;
		final int orderLineId;
		final int subscriptionProgressId;
		final int forecastId = UNSPECIFIED_REPO_ID;
		final int forecastLineId = UNSPECIFIED_REPO_ID;
		if (documentDescriptor instanceof OrderLineDescriptor)
		{
			final OrderLineDescriptor orderLineDescriptor = (OrderLineDescriptor)documentDescriptor;
			orderLineId = orderLineDescriptor.getOrderLineId();
			orderId = orderLineDescriptor.getOrderId();
			subscriptionProgressId = UNSPECIFIED_REPO_ID;
		}
		else if (documentDescriptor instanceof SubscriptionLineDescriptor)
		{
			orderLineId = UNSPECIFIED_REPO_ID;
			orderId = UNSPECIFIED_REPO_ID;
			subscriptionProgressId = ((SubscriptionLineDescriptor)documentDescriptor).getSubscriptionProgressId();
		}
		else
		{
			throw new AdempiereException("The given documentDescriptor has an unexpected type")
					.appendParametersToMessage()
					.setParameter("documentDescriptor", documentDescriptor);
		}

		return DemandDetail.builder()
				.shipmentScheduleId(shipmentScheduleId)
				.orderLineId(orderLineId)
				.orderId(orderId)
				.forecastLineId(forecastLineId)
				.forecastId(forecastId)
				.subscriptionProgressId(subscriptionProgressId)
				.qty(plannedQty).build();
	}

	public static DemandDetail forSupplyRequiredDescriptorOrNull(@Nullable final SupplyRequiredDescriptor supplyRequiredDescriptor)
	{
		return supplyRequiredDescriptor != null
				? forSupplyRequiredDescriptor(supplyRequiredDescriptor)
				: null;
	}

	public static DemandDetail forSupplyRequiredDescriptor(@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor)
	{
		return DemandDetail.builder()
				.demandCandidateId(toUnspecifiedIfZero(supplyRequiredDescriptor.getDemandCandidateId()))
				.forecastId(toUnspecifiedIfZero(supplyRequiredDescriptor.getForecastId()))
				.forecastLineId(toUnspecifiedIfZero(supplyRequiredDescriptor.getForecastLineId()))
				.orderId(toUnspecifiedIfZero(supplyRequiredDescriptor.getOrderId()))
				.orderLineId(toUnspecifiedIfZero(supplyRequiredDescriptor.getOrderLineId()))
				.shipmentScheduleId(toUnspecifiedIfZero(supplyRequiredDescriptor.getShipmentScheduleId()))
				.subscriptionProgressId(toUnspecifiedIfZero(supplyRequiredDescriptor.getSubscriptionProgressId()))
				.qty(supplyRequiredDescriptor.getMaterialDescriptor().getQuantity())
				.build();
	}

	public static DemandDetail forShipmentScheduleIdAndOrderLineId(
			final int shipmentScheduleId,
			final int orderLineId,
			final int orderId,
			@NonNull final BigDecimal qty)
	{
		return DemandDetail.builder()
				.shipmentScheduleId(shipmentScheduleId)
				.orderLineId(orderLineId)
				.orderId(orderId)
				.qty(qty).build();
	}

	public static DemandDetail forForecastLineId(
			final int forecastLineId,
			final int forecastId,
			@NonNull final BigDecimal qty)
	{
		return DemandDetail.builder()
				.forecastLineId(forecastLineId)
				.forecastId(forecastId)
				.qty(qty).build();
	}

	int forecastId;

	int forecastLineId;

	int shipmentScheduleId;

	int orderId;

	int orderLineId;

	int subscriptionProgressId;

	BigDecimal qty;

	/**
	 * Used when a new supply candidate is created, to link it to it's respective demand candidate;
	 * When a demand detail is loaded from DB, this field is always <= 0.
	 */
	int demandCandidateId;

	/**
	 *  dev-note: it's about an {@link de.metas.material.event.MaterialEvent} traceId, currently used when posting SupplyRequiredEvents
	 */
	@With
	@Nullable
	String traceId;

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
