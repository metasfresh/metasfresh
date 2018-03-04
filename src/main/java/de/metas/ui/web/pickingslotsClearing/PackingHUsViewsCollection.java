package de.metas.ui.web.pickingslotsClearing;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import de.metas.ui.web.handlingunits.HUEditorView;
import de.metas.ui.web.pickingslotsClearing.process.HUExtractedFromPickingSlotEvent;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
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

/* package */final class PackingHUsViewsCollection
{
	private final ConcurrentHashMap<PackingHUsViewKey, HUEditorView> packingHUsViewsByKey = new ConcurrentHashMap<>();

	public Optional<HUEditorView> getByKeyIfExists(@NonNull final PackingHUsViewKey key)
	{
		return Optional.ofNullable(packingHUsViewsByKey.get(key));
	}

	@FunctionalInterface
	static interface PackingHUsViewSupplier
	{
		HUEditorView createPackingHUsView(PackingHUsViewKey key);
	}

	public HUEditorView computeIfAbsent(@NonNull final PackingHUsViewKey key, @NonNull final PackingHUsViewSupplier packingHUsViewFactory)
	{
		return packingHUsViewsByKey.computeIfAbsent(key, packingHUsViewFactory::createPackingHUsView);
	}

	public Optional<HUEditorView> removeIfExists(@NonNull final PackingHUsViewKey key)
	{
		final HUEditorView packingHUsViewRemoved = packingHUsViewsByKey.remove(key);
		return Optional.ofNullable(packingHUsViewRemoved);
	}

	public void handleEvent(@NonNull final HUExtractedFromPickingSlotEvent event)
	{
		packingHUsViewsByKey.entrySet()
				.stream()
				.filter(entry -> isEventMatchingKey(event, entry.getKey()))
				.map(entry -> entry.getValue())
				.forEach(packingHUsView -> packingHUsView.addHUIdAndInvalidate(event.getHuId()));
	}

	private static final boolean isEventMatchingKey(final HUExtractedFromPickingSlotEvent event, final PackingHUsViewKey key)
	{
		return key.isBPartnerIdMatching(event.getBpartnerId())
				&& key.isBPartnerLocationIdMatching(event.getBpartnerLocationId());
	}
}
