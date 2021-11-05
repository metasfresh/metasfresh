/*
 * #%L
 * metasfresh-material-dispo-service
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

package de.metas.material.dispo.service.event.handler.ddorder;

import de.metas.material.cockpit.model.I_MD_Cockpit_DDOrder_Detail;
import de.metas.material.cockpit.view.DDOrderDetailIdentifier;
import de.metas.material.cockpit.view.MainDataRecordIdentifier;
import de.metas.material.cockpit.view.ddorderdetail.DDOrderDetailRequestHandler;
import de.metas.material.cockpit.view.ddorderdetail.DDOrderDetailType;
import de.metas.material.cockpit.view.ddorderdetail.InsertDDOrderDetailRequest;
import de.metas.material.cockpit.view.ddorderdetail.RemoveDDOrderDetailRequest;
import de.metas.material.cockpit.view.ddorderdetail.UpdateDDOrderDetailRequest;
import de.metas.material.cockpit.view.mainrecord.MainDataRequestHandler;
import de.metas.material.cockpit.view.mainrecord.UpdateMainDataRequest;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.event.ddorder.AbstractDDOrderEvent;
import de.metas.material.event.ddorder.DDOrderLine;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;

import static de.metas.material.dispo.service.event.handler.ddorder.DDOrderAdvisedOrCreatedHandler.computeDate;
import static de.metas.material.dispo.service.event.handler.ddorder.DDOrderAdvisedOrCreatedHandler.computeWarehouseId;

@Value
@Builder
public class DDOrderMainDataHandler
{
	@NonNull
	ZoneId orgZone;
	@NonNull
	AbstractDDOrderEvent abstractDDOrderEvent;
	@NonNull
	DDOrderLine ddOrderLine;

	@NonNull
	DDOrderDetailRequestHandler ddOrderDetailRequestHandler;
	@NonNull
	MainDataRequestHandler mainDataRequestHandler;

	public void handleDelete()
	{
		deleteForCandidateType(CandidateType.DEMAND);
		deleteForCandidateType(CandidateType.SUPPLY);
	}

	public void handleUpdate()
	{
		updateForCandidateType(CandidateType.DEMAND);
		updateForCandidateType(CandidateType.SUPPLY);
	}

	private void deleteForCandidateType(@NonNull final CandidateType candidateType)
	{
		final MainDataRecordIdentifier mainDataRecordIdentifier = getMainRecordIdentifier(candidateType);

		final de.metas.material.cockpit.view.ddorderdetail.DDOrderDetailIdentifier ddOrderDetailIdentifier = de.metas.material.cockpit.view.ddorderdetail.DDOrderDetailIdentifier
				.createForWarehouseRelatedIdentifier(mainDataRecordIdentifier,
													 DDOrderDetailIdentifier.of(getWarehouseIdForType(candidateType),
																				ddOrderLine.getDdOrderLineId()));

		final Optional<I_MD_Cockpit_DDOrder_Detail> existingDDOrderDetail = ddOrderDetailRequestHandler
				.retrieveDDOrderDetail(ddOrderDetailIdentifier);

		if (!existingDDOrderDetail.isPresent())
		{
			return;
		}

		final BigDecimal deltaQtyPending = existingDDOrderDetail.get().getQtyPending().negate();

		final UpdateMainDataRequest updateMainDataRequest = buildUpdateMainDataRequest(mainDataRecordIdentifier, candidateType, deltaQtyPending);

		mainDataRequestHandler.handleDataUpdateRequest(updateMainDataRequest);

		final RemoveDDOrderDetailRequest removeDDOrderDetailRequest = RemoveDDOrderDetailRequest.of(ddOrderDetailIdentifier);

		ddOrderDetailRequestHandler.handleRemoveDetailRequest(removeDDOrderDetailRequest);
	}

	private void updateForCandidateType(@NonNull final CandidateType candidateType)
	{
		final MainDataRecordIdentifier mainDataRecordIdentifier = getMainRecordIdentifier(candidateType);

		final de.metas.material.cockpit.view.ddorderdetail.DDOrderDetailIdentifier ddOrderDetailIdentifier = de.metas.material.cockpit.view.ddorderdetail.DDOrderDetailIdentifier
				.createForWarehouseRelatedIdentifier(mainDataRecordIdentifier,
													 DDOrderDetailIdentifier.of(getWarehouseIdForType(candidateType),
																				ddOrderLine.getDdOrderLineId()));

		final Optional<I_MD_Cockpit_DDOrder_Detail> existingDDOrderDetail = ddOrderDetailRequestHandler
				.retrieveDDOrderDetail(ddOrderDetailIdentifier);

		final BigDecimal deltaQtyPending = existingDDOrderDetail.map(detail -> getQtyPending().subtract(detail.getQtyPending()))
				.orElseGet(this::getQtyPending);

		final UpdateMainDataRequest updateMainDataRequest = buildUpdateMainDataRequest(mainDataRecordIdentifier, candidateType, deltaQtyPending);

		mainDataRequestHandler.handleDataUpdateRequest(updateMainDataRequest);

		if (existingDDOrderDetail.isPresent())
		{
			final UpdateDDOrderDetailRequest updateDetailRequest = UpdateDDOrderDetailRequest.builder()
					.ddOrderDetailIdentifier(ddOrderDetailIdentifier)
					.qtyPending(getQtyPending())
					.build();

			ddOrderDetailRequestHandler.handleUpdateDetailRequest(updateDetailRequest);
		}
		else
		{
			final InsertDDOrderDetailRequest insertDDOrderDetailRequest = InsertDDOrderDetailRequest.builder()
					.ddOrderDetailIdentifier(ddOrderDetailIdentifier)
					.detailType(DDOrderDetailType.ofCode(candidateType.getCode()))
					.qtyPending(getQtyPending())
					.build();

			ddOrderDetailRequestHandler.handleInsertDetailRequest(insertDDOrderDetailRequest);
		}
	}

	@NonNull
	private BigDecimal getQtyPending()
	{
		if (ddOrderLine.getQtyPending() == null)
		{
			throw new AdempiereException("ddOrderLine.qtyPending should never be null in case of DDOrderCreatedEvent!")
					.appendParametersToMessage()
					.setParameter("ddOrderLineId", ddOrderLine.getDdOrderLineId());
		}

		return ddOrderLine.getQtyPending();
	}

	@NonNull
	private WarehouseId getWarehouseIdForType(@NonNull final CandidateType candidateType)
	{
		return computeWarehouseId(abstractDDOrderEvent, candidateType);
	}

	@NonNull
	private Instant getDate(@NonNull final CandidateType candidateType)
	{
		return TimeUtil.getDay(computeDate(abstractDDOrderEvent, ddOrderLine, candidateType), orgZone);
	}

	@NonNull
	private MainDataRecordIdentifier getMainRecordIdentifier(@NonNull final CandidateType candidateType)
	{
		return MainDataRecordIdentifier.builder()
				.date(getDate(candidateType))
				.warehouseId(getWarehouseIdForType(candidateType))
				.productDescriptor(ddOrderLine.getProductDescriptor())
				.build();
	}

	@NonNull
	private UpdateMainDataRequest buildUpdateMainDataRequest(
			@NonNull final MainDataRecordIdentifier mainDataRecordIdentifier,
			@NonNull final CandidateType candidateType,
			@NonNull final BigDecimal qtyPending)
	{
		final UpdateMainDataRequest.UpdateMainDataRequestBuilder updateMainDataRequestBuilder = UpdateMainDataRequest.builder()
				.identifier(mainDataRecordIdentifier);

		switch (candidateType)
		{
			case DEMAND:
				return updateMainDataRequestBuilder.qtyDemandDDOrder(qtyPending).build();
			case SUPPLY:
				return updateMainDataRequestBuilder.qtySupplyDDOrder(qtyPending).build();
			default:
				throw new AdempiereException("CandidateType not supported! CandidateType = " + candidateType);
		}
	}
}
