package de.metas.vertical.pharma.msv3.server.peer.metasfresh.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.Adempiere;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.vertical.pharma.msv3.server.model.I_MSV3_Customer_Config;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3UserChangedEvent;
import de.metas.vertical.pharma.msv3.server.peer.service.CustomerConfigEventsQueue;

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

@Interceptor(I_MSV3_Customer_Config.class)
@Component
public class MSV3_Customer_Config
{
	private CustomerConfigEventsQueue getCustomerConfigEventsQueue()
	{
		return Adempiere.getBean(CustomerConfigEventsQueue.class);
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	public void onCreated(final I_MSV3_Customer_Config configRecord)
	{
		if (configRecord.isActive())
		{
			final CustomerConfigEventsQueue queue = getCustomerConfigEventsQueue();
			queue.publish(MSV3UserChangedEvent.prepareCreatedEvent()
					.username(configRecord.getUserID())
					.password(configRecord.getPassword())
					.bpartnerId(configRecord.getC_BPartner_ID())
					.bpartnerLocationId(configRecord.getC_BPartner_Location_ID())
					.build());
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE)
	public void onUpdated(final I_MSV3_Customer_Config configRecord)
	{
		final CustomerConfigEventsQueue queue = getCustomerConfigEventsQueue();
		if (configRecord.isActive())
		{
			queue.publish(MSV3UserChangedEvent.prepareUpdatedEvent()
					.username(configRecord.getUserID())
					.password(configRecord.getPassword())
					.bpartnerId(configRecord.getC_BPartner_ID())
					.bpartnerLocationId(configRecord.getC_BPartner_Location_ID())
					.build());
		}
		else
		{
			queue.publish(MSV3UserChangedEvent.deletedEvent(configRecord.getUserID()));
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_DELETE)
	public void onDeleted(final I_MSV3_Customer_Config configRecord)
	{
		final CustomerConfigEventsQueue queue = getCustomerConfigEventsQueue();
		queue.publish(MSV3UserChangedEvent.deletedEvent(configRecord.getUserID()));
	}
}
