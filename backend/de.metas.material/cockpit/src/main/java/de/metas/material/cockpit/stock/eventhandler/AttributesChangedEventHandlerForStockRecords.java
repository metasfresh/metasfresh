package de.metas.material.cockpit.stock.eventhandler;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.attributes.AttributesChangedEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collection;

/*
 * #%L
 * metasfresh-material-cockpit
 * %%
 * Copyright (C) 2026 metas GmbH
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

/**
 * Handles {@link AttributesChangedEvent} for MD_Stock records.
 * Re-keys the MD_Stock qty from the old AttributesKey bucket to the new one.
 *
 * <p>NOTE: Task 2 stub — logic not yet implemented (see Task 3).</p>
 */
@Service
@Profile(Profiles.PROFILE_App)
public class AttributesChangedEventHandlerForStockRecords
		implements MaterialEventHandler<AttributesChangedEvent>
{
	@Override
	public Collection<Class<? extends AttributesChangedEvent>> getHandledEventType()
	{
		return ImmutableList.of(AttributesChangedEvent.class);
	}

	@Override
	public void handleEvent(final AttributesChangedEvent event)
	{
		// no-op stub — Task 3 will implement the re-key logic
	}
}
