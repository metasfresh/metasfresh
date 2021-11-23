package de.metas.material.cockpit.view.eventhandler;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.material.cockpit.view.MainDataRecordIdentifier;
import de.metas.material.cockpit.view.mainrecord.MainDataRequestHandler;
import de.metas.material.cockpit.view.mainrecord.UpdateMainDataRequest;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.pporder.PPOrderChangedEvent;
import de.metas.material.event.pporder.PPOrderChangedEvent.ChangedPPOrderLineDescriptor;
import de.metas.material.event.pporder.PPOrderChangedEvent.DeletedPPOrderLineDescriptor;
import de.metas.material.event.pporder.PPOrderLine;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.util.TimeUtil;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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
		updateMainData(ppOrderChangedEvent);

		final List<PPOrderLine> newPPOrderLines = ppOrderChangedEvent.getNewPPOrderLines();

		final OrgId orgId = ppOrderChangedEvent.getEventDescriptor().getOrgId();
		final ZoneId timeZone = orgDAO.getTimeZone(orgId);
		
		final List<UpdateMainDataRequest> requests = new ArrayList<>();
		for (final PPOrderLine newPPOrderLine : newPPOrderLines)
		{
			final MainDataRecordIdentifier identifier = MainDataRecordIdentifier.builder()
					.productDescriptor(newPPOrderLine.getPpOrderLineData().getProductDescriptor())
					.date(TimeUtil.getDay(newPPOrderLine.getPpOrderLineData().getIssueOrReceiveDate(), timeZone))
					.build();

			final BigDecimal qtyRequiredForProduction = //
					newPPOrderLine.getPpOrderLineData().getQtyRequired()
							.subtract(newPPOrderLine.getPpOrderLineData().getQtyDelivered());

			final UpdateMainDataRequest request = UpdateMainDataRequest.builder()
					.identifier(identifier)
					.qtyDemandPPOrder(qtyRequiredForProduction)
					.build();
			requests.add(request);
		}

		final List<DeletedPPOrderLineDescriptor> deletedPPOrderLines = ppOrderChangedEvent.getDeletedPPOrderLines();
		for (final DeletedPPOrderLineDescriptor deletedPPOrderLine : deletedPPOrderLines)
		{
			final MainDataRecordIdentifier identifier = MainDataRecordIdentifier.builder()
					.productDescriptor(deletedPPOrderLine.getProductDescriptor())
					.date(deletedPPOrderLine.getIssueOrReceiveDate().truncatedTo(ChronoUnit.DAYS))
					.build();

			final BigDecimal qtyRequiredForProduction = //
					deletedPPOrderLine.getQtyRequired()
							.subtract(deletedPPOrderLine.getQtyDelivered())
							.negate();

			final UpdateMainDataRequest request = UpdateMainDataRequest.builder()
					.identifier(identifier)
					.qtyDemandPPOrder(qtyRequiredForProduction)
					.build();
			requests.add(request);
		}

		for (final ChangedPPOrderLineDescriptor changedPPOrderLine : ppOrderChangedEvent.getPpOrderLineChanges())
		{
			final BigDecimal qtyDelta = changedPPOrderLine.computeOpenQtyDelta();
			if (qtyDelta.signum() == 0)
			{
				continue;
			}

			final MainDataRecordIdentifier identifier = MainDataRecordIdentifier.builder()
					.productDescriptor(changedPPOrderLine.getProductDescriptor())
					.date(TimeUtil.getDay(changedPPOrderLine.getIssueOrReceiveDate(), timeZone))
					.build();

			final UpdateMainDataRequest request = UpdateMainDataRequest.builder()
					.identifier(identifier)
					.qtyDemandPPOrder(qtyDelta)
					.build();
			requests.add(request);
		}

		requests.forEach(dataUpdateRequestHandler::handleDataUpdateRequest);
	}

	private void updateMainData(@NonNull final PPOrderChangedEvent ppOrderChangedEvent)
	{
		final ZoneId orgZoneId = orgDAO.getTimeZone(ppOrderChangedEvent.getEventDescriptor().getOrgId());

		final MainDataRecordIdentifier mainDataRecordIdentifier = MainDataRecordIdentifier.builder()
				.productDescriptor(ppOrderChangedEvent.getPpOrderAfterChanges().getPpOrderData().getProductDescriptor())
				.date(TimeUtil.getDay(ppOrderChangedEvent.getPpOrderAfterChanges().getPpOrderData().getDatePromised(), orgZoneId))
				.warehouseId(ppOrderChangedEvent.getPpOrderAfterChanges().getPpOrderData().getWarehouseId())
				.build();

		final BigDecimal newQtyOpen = ppOrderChangedEvent.getPpOrderAfterChanges().getPpOrderData().getQtyOpen();
		final BigDecimal oldQtyOpen = ppOrderChangedEvent.getOldQtyRequired().subtract(ppOrderChangedEvent.getOldQtyDelivered());

		final UpdateMainDataRequest updateMainDataRequest = UpdateMainDataRequest.builder()
				.identifier(mainDataRecordIdentifier)
				.qtySupplyPPOrder(newQtyOpen.subtract(oldQtyOpen))
				.build();

		dataUpdateRequestHandler.handleDataUpdateRequest(updateMainDataRequest);
	}

}
