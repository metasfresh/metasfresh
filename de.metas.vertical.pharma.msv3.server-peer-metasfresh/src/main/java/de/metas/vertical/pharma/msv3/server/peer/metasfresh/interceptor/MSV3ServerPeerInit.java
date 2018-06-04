package de.metas.vertical.pharma.msv3.server.peer.metasfresh.interceptor;

import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.util.Services;
import org.springframework.stereotype.Component;

import de.metas.Profiles;
import de.metas.impex.api.IInputDataSourceDAO;
import de.metas.impex.api.InputDataSourceCreateRequest;
import de.metas.vertical.pharma.msv3.server.peer.metasfresh.MSV3ServerPeerMetasfreshConfiguration;
import de.metas.vertical.pharma.msv3.server.peer.metasfresh.listeners.OrderCreateRequestRabbitMQListener;

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

@Component
public class MSV3ServerPeerInit extends AbstractModuleInterceptor
{
	@Override
	protected void onAfterInit()
	{
		if (Profiles.isProfileActive(Profiles.PROFILE_App))
		{
			ensureDataSourceExists();
		}
	}

	private void ensureDataSourceExists()
	{
		Services.get(IInputDataSourceDAO.class).createIfMissing(InputDataSourceCreateRequest.builder()
				.entityType(MSV3ServerPeerMetasfreshConfiguration.ENTITY_TYPE)
				.internalName(OrderCreateRequestRabbitMQListener.DATA_SOURCE_INTERNAL_NAME)
				.destination(false)
				.build());
	}

}
