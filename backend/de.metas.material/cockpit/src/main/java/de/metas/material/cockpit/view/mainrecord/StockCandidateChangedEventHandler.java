/*
 * #%L
 * metasfresh-material-cockpit
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

package de.metas.material.cockpit.view.mainrecord;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.material.cockpit.view.MainDataRecordIdentifier;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.stockcandidate.StockCandidateChangedEvent;
import de.metas.organization.IOrgDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Collection;

@Service
@Profile(Profiles.PROFILE_MaterialDispo)
public class StockCandidateChangedEventHandler implements MaterialEventHandler<StockCandidateChangedEvent>
{
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final MainDataRequestHandler mainDataRequestHandler;

	public StockCandidateChangedEventHandler(@NonNull final MainDataRequestHandler mainDataRequestHandler)
	{
		this.mainDataRequestHandler = mainDataRequestHandler;
	}

	@Override
	public Collection<Class<? extends StockCandidateChangedEvent>> getHandledEventType()
	{
		return ImmutableList.of(StockCandidateChangedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final StockCandidateChangedEvent event)
	{
		final ZoneId orgZoneId = orgDAO.getTimeZone(event.getEventDescriptor().getOrgId());

		final UpdateMainStockDataRequest updateMainStockDataRequest = UpdateMainStockDataRequest.builder()
				.identifier(MainDataRecordIdentifier.createForMaterial(event.getMaterialDescriptor(), orgZoneId))
				.qtyStockCurrent(event.getMaterialDescriptor().getQuantity())
				.build();

		mainDataRequestHandler.handleStockUpdateRequest(updateMainStockDataRequest);
	}
}
