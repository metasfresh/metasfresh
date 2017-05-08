package de.metas.material.dispo.event;

import java.sql.Timestamp;

import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.material.dispo.model.I_MD_EventStore;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.MaterialEventListener;

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

public class EventStoreListener implements MaterialEventListener
{

	@Override
	public void onEvent(MaterialEvent event)
	{
		final I_MD_EventStore eventStoreRecord = InterfaceWrapperHelper.newInstance(I_MD_EventStore.class);

		eventStoreRecord.setEventTime(Timestamp.from(event.getEventDescr().getWhen()));

		// TODO Auto-generated method stub

	}

}
