package de.metas.vertical.pharma.msv3.server.peer.metasfresh.services;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.metas.vertical.pharma.msv3.server.model.I_MSV3_Customer_Config;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3UserChangedEvent;
import de.metas.vertical.pharma.msv3.server.peer.service.MSV3ServerPeerService;

/*
 * #%L
 * metasfresh-pharma.msv3.server-peer-metasfresh
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

@Service
public class MSV3CustomerConfigService
{
	@Autowired
	private MSV3ServerPeerService msv3ServerPeerService;

	public void publishConfigChanged(final I_MSV3_Customer_Config configRecord)
	{
		msv3ServerPeerService.publishUserChangedEvent(toMSV3UserChangedEvent(configRecord));
	}

	public void publishConfigDeleted(final String username)
	{
		msv3ServerPeerService.publishUserChangedEvent(MSV3UserChangedEvent.deletedEvent(username));
	}

	public void publishAllConfig()
	{
		Services.get(IQueryBL.class)
				.createQueryBuilder(I_MSV3_Customer_Config.class)
				// .addOnlyActiveRecordsFilter() // ALL, even if is not active. For those inactive we will generate delete events
				.orderBy(I_MSV3_Customer_Config.COLUMN_MSV3_Customer_Config_ID)
				.create()
				.stream(I_MSV3_Customer_Config.class)
				.map(configRecord -> toMSV3UserChangedEvent(configRecord))
				.forEach(msv3ServerPeerService::publishUserChangedEvent);
	}

	private static MSV3UserChangedEvent toMSV3UserChangedEvent(final I_MSV3_Customer_Config configRecord)
	{
		if (configRecord.isActive())
		{
			return MSV3UserChangedEvent.prepareCreatedOrUpdatedEvent()
					.username(configRecord.getUserID())
					.password(configRecord.getPassword())
					.bpartnerId(configRecord.getC_BPartner_ID())
					.bpartnerLocationId(configRecord.getC_BPartner_Location_ID())
					.build();
		}
		else
		{
			return MSV3UserChangedEvent.deletedEvent(configRecord.getUserID());
		}

	}
}
