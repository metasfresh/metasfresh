package de.metas.handlingunits.material.interceptor;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.HuId;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.PostMaterialEventService;
import de.metas.util.Check;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

final class HUAttributeChangesCollector
{
	private final PostMaterialEventService materialEventService;

	private final AtomicBoolean disposed = new AtomicBoolean();
	private final HashMap<HuId, HUAttributeChanges> huAttributeChanges = new HashMap<>();

	public HUAttributeChangesCollector(@NonNull final PostMaterialEventService materialEventService)
	{
		this.materialEventService = materialEventService;
	}

	public void collect(@NonNull final HUAttributeChange change)
	{
		Check.assume(!disposed.get(), "Collector shall not be disposed: {}", this);
		final HUAttributeChanges huChanges = huAttributeChanges.computeIfAbsent(change.getHuId(), HUAttributeChanges::new);
		huChanges.collect(change);
	}

	public void createAndPostMaterialEvents()
	{
		if (disposed.getAndSet(true))
		{
			throw new AdempiereException("Collector was already disposed: " + this);
		}

		final ImmutableList<MaterialEvent> events = huAttributeChanges.values()
				.stream()
				.map(this::createMaterialEvent)
				.collect(ImmutableList.toImmutableList());

		this.huAttributeChanges.clear();

		materialEventService.postEventsNow(events);
	}

	private MaterialEvent createMaterialEvent(final HUAttributeChanges changes)
	{
		// TODO: introduce and handle a new event which implements MaterialEvent
		throw new UnsupportedOperationException();
	}
}
