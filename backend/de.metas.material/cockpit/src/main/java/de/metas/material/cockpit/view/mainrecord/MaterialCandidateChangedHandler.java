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
import de.metas.material.event.stockcandidate.MaterialCandidateChangedEvent;
import de.metas.organization.IOrgDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Collection;

@Component
@Profile(Profiles.PROFILE_MaterialDispo)
public class MaterialCandidateChangedHandler implements MaterialEventHandler<MaterialCandidateChangedEvent>
{
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final MainDataRequestHandler mainDataRequestHandler;

	public MaterialCandidateChangedHandler(@NonNull final MainDataRequestHandler mainDataRequestHandler)
	{
		this.mainDataRequestHandler = mainDataRequestHandler;
	}

	@Override
	public Collection<Class<? extends MaterialCandidateChangedEvent>> getHandledEventType()
	{
		return ImmutableList.of(MaterialCandidateChangedEvent.class);
	}

	@Override
	public void handleEvent(final MaterialCandidateChangedEvent event)
	{
		final ZoneId orgZoneId = orgDAO.getTimeZone(event.getEventDescriptor().getOrgId());

		final UpdateMainDataRequest updateMainDataRequest = UpdateMainDataRequest.builder()
				.identifier(MainDataRecordIdentifier.createForMaterial(event.getMaterialDescriptor(), orgZoneId))
				.qtySupplyRequired(event.getQtyFulfilledDelta().negate())
				.build();

		mainDataRequestHandler.handleDataUpdateRequest(updateMainDataRequest);
	}
}
