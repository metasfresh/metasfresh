package de.metas.material.cockpit.view.eventhandler;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.material.cockpit.view.MainDataRecordIdentifier;
import de.metas.material.cockpit.view.mainrecord.MainDataRequestHandler;
import de.metas.material.cockpit.view.mainrecord.UpdateMainDataRequest;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.pporder.PPOrderChangedEvent;
import de.metas.material.event.pporder.PPOrderLine;
import de.metas.organization.IOrgDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.TimeUtil;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
 * #%L
 * metasfresh-material-cockpit
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

@Service
@Profile(Profiles.PROFILE_App) // it's important to have just *one* instance of this listener, because on each event needs to be handled exactly once.
public class PPOrderChangedEventHandler implements MaterialEventHandler<PPOrderChangedEvent>
{
	private final MainDataRequestHandler dataUpdateRequestHandler;
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	public PPOrderChangedEventHandler(@NonNull final MainDataRequestHandler dataUpdateRequestHandler)
	{
		this.dataUpdateRequestHandler = dataUpdateRequestHandler;
	}

	@Override
	public Collection<Class<? extends PPOrderChangedEvent>> getHandledEventType()
	{
		return ImmutableList.of(PPOrderChangedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final PPOrderChangedEvent ppOrderChangedEvent)
	{
		if (!ppOrderChangedEvent.getNewDocStatus().isCompleted() && !ppOrderChangedEvent.isJustReactivated())
		{
			return;
		}

		updateMainData(ppOrderChangedEvent);
	}

	private void updateMainData(@NonNull final PPOrderChangedEvent ppOrderChangedEvent)
	{
		final ZoneId orgZoneId = orgDAO.getTimeZone(ppOrderChangedEvent.getEventDescriptor().getOrgId());

		updateMainDataForPPOrder(ppOrderChangedEvent, orgZoneId);
		updateMainDataForPPOrderLines(ppOrderChangedEvent, orgZoneId);
	}

	private void updateMainDataForPPOrder(
			@NonNull final PPOrderChangedEvent ppOrderChangedEvent,
			@NonNull final ZoneId orgZoneId)
	{
		final MainDataRecordIdentifier mainDataRecordIdentifier = MainDataRecordIdentifier.builder()
				.warehouseId(ppOrderChangedEvent.getPpOrderAfterChanges().getPpOrderData().getWarehouseId())
				.productDescriptor(ppOrderChangedEvent.getPpOrderAfterChanges().getPpOrderData().getProductDescriptor())
				.date(TimeUtil.getDay(ppOrderChangedEvent.getPpOrderAfterChanges().getPpOrderData().getDatePromised(), orgZoneId))
				.build();
		
		final BigDecimal qtySupplyPPOrder;
		if (ppOrderChangedEvent.isJustCompleted())
		{
			qtySupplyPPOrder = ppOrderChangedEvent.getPpOrderAfterChanges().getPpOrderData().getQtyOpen();
		}
		else if (ppOrderChangedEvent.isJustReactivated())
		{
			qtySupplyPPOrder = ppOrderChangedEvent.getPpOrderAfterChanges().getPpOrderData().getQtyOpen().negate();
		}
		else if (ppOrderChangedEvent.getNewDocStatus().isCompleted())
		{
			//dev-note: this is about receiving goods (qtyDelivered changes)
			final BigDecimal newQtyOpen = ppOrderChangedEvent.getPpOrderAfterChanges().getPpOrderData().getQtyOpen();
			final BigDecimal oldQtyOpen = ppOrderChangedEvent.getOldQtyRequired().subtract(ppOrderChangedEvent.getOldQtyDelivered());
			qtySupplyPPOrder = newQtyOpen.subtract(oldQtyOpen);
		}
		else
		{
			throw new AdempiereException("Invalid PPOrder.DocStatus!")
					.appendParametersToMessage()
					.setParameter("NewDocStatus", ppOrderChangedEvent.getNewDocStatus())
					.setParameter("OldDocStatus", ppOrderChangedEvent.getOldDocStatus());
		}

		final UpdateMainDataRequest updateMainDataRequest = UpdateMainDataRequest.builder()
				.identifier(mainDataRecordIdentifier)
				.qtySupplyPPOrder(qtySupplyPPOrder)
				.build();

		dataUpdateRequestHandler.handleDataUpdateRequest(updateMainDataRequest);
	}

	private void updateMainDataForPPOrderLines(
			@NonNull final PPOrderChangedEvent ppOrderChangedEvent,
			@NonNull final ZoneId orgZoneId)
	{
		final List<UpdateMainDataRequest> requests = new ArrayList<>();
		for (final PPOrderLine ppOrderLine : ppOrderChangedEvent.getPpOrderAfterChanges().getLines())
		{
			final MainDataRecordIdentifier identifier = MainDataRecordIdentifier.builder()
					.productDescriptor(ppOrderLine.getPpOrderLineData().getProductDescriptor())
					.date(TimeUtil.getDay(ppOrderLine.getPpOrderLineData().getIssueOrReceiveDate(), orgZoneId))
					.warehouseId(ppOrderChangedEvent.getPpOrderAfterChanges().getPpOrderData().getWarehouseId())
					.build();

			final BigDecimal qtyRequiredForProduction = getActualQtyRequiredForProduction(ppOrderChangedEvent, ppOrderLine);

			requests.add(UpdateMainDataRequest.builder()
								 .identifier(identifier)
								 .qtyDemandPPOrder(qtyRequiredForProduction)
								 .build());
		}

		requests.forEach(dataUpdateRequestHandler::handleDataUpdateRequest);
	}

	@NonNull
	private BigDecimal getActualQtyRequiredForProduction(
			@NonNull final PPOrderChangedEvent ppOrderChangedEvent,
			@NonNull final PPOrderLine ppOrderLine)
	{
		final BigDecimal actualQtyRequired;
		if (ppOrderChangedEvent.isJustCompleted())
		{
			actualQtyRequired = ppOrderLine.getPpOrderLineData().getQtyOpen();
		}
		else if (ppOrderChangedEvent.isJustReactivated())
		{
			actualQtyRequired = ppOrderLine.getPpOrderLineData().getQtyOpen().negate();
		}
		else if (ppOrderChangedEvent.getNewDocStatus().isCompleted())
		{
			//dev-note: this is about qty issued
			actualQtyRequired = ppOrderChangedEvent.getChangedDescriptorForPPOrderLineId(ppOrderLine.getPpOrderLineId())
					.map(PPOrderChangedEvent.ChangedPPOrderLineDescriptor::computeOpenQtyDelta)
					.orElse(BigDecimal.ZERO);
		}
		else
		{
			throw new AdempiereException("Invalid PPOrder.DocStatus!")
					.appendParametersToMessage()
					.setParameter("NewDocStatus", ppOrderChangedEvent.getNewDocStatus())
					.setParameter("OldDocStatus", ppOrderChangedEvent.getOldDocStatus());
		}
		
		return actualQtyRequired;
	}
}
