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

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.material.cockpit.view.ddorderdetail.DDOrderDetailRequestHandler;
import de.metas.material.cockpit.view.mainrecord.MainDataRequestHandler;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.ddorder.DDOrderDeletedEvent;
import de.metas.material.event.ddorder.DDOrderLine;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Collection;

@Component
@Profile(Profiles.PROFILE_MaterialDispo)
public class DDOrderDeletedEventHandler implements MaterialEventHandler<DDOrderDeletedEvent>
{
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final MainDataRequestHandler mainDataRequestHandler;
	private final DDOrderDetailRequestHandler ddOrderDetailRequestHandler;

	public DDOrderDeletedEventHandler(
			@NonNull final MainDataRequestHandler mainDataRequestHandler,
			@NonNull final DDOrderDetailRequestHandler ddOrderDetailRequestHandler)
	{
		this.mainDataRequestHandler = mainDataRequestHandler;
		this.ddOrderDetailRequestHandler = ddOrderDetailRequestHandler;
	}

	@Override
	public Collection<Class<? extends DDOrderDeletedEvent>> getHandledEventType()
	{
		return ImmutableList.of(DDOrderDeletedEvent.class);
	}

	@Override
	public void handleEvent(final DDOrderDeletedEvent event)
	{
		final OrgId orgId = event.getEventDescriptor().getOrgId();
		final ZoneId timeZone = orgDAO.getTimeZone(orgId);

		for (final DDOrderLine ddOrderLine : event.getDdOrder().getLines())
		{
			final DDOrderMainDataHandler mainDataUpdater = DDOrderMainDataHandler.builder()
					.ddOrderDetailRequestHandler(ddOrderDetailRequestHandler)
					.mainDataRequestHandler(mainDataRequestHandler)
					.abstractDDOrderEvent(event)
					.ddOrderLine(ddOrderLine)
					.orgZone(timeZone)
					.build();

			mainDataUpdater.handleDelete();
		}

	}
}
