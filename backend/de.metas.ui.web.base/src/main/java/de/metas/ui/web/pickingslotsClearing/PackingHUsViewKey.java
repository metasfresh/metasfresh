package de.metas.ui.web.pickingslotsClearing;

import de.metas.ui.web.view.ViewId;
import de.metas.util.Check;
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

@lombok.Value
class PackingHUsViewKey
{
	public static PackingHUsViewKey ofPackingHUsViewId(final ViewId packingHUsViewId)
	{
		return new PackingHUsViewKey(packingHUsViewId);
	}

	public static ViewId extractPickingSlotClearingViewId(final ViewId packingHUsViewId)
	{
		return ViewId.ofParts(PickingSlotsClearingViewFactory.WINDOW_ID, packingHUsViewId.getViewIdPart());
	}

	public static ViewId incrementPackingHUsViewIdVersion(final ViewId packingHUsViewId)
	{
		final int version = packingHUsViewId.getPartAsInt(4);
		return createPackingHUsViewIdFromParts(
				packingHUsViewId.getPart(1),
				packingHUsViewId.getPartAsInt(2),
				packingHUsViewId.getPartAsInt(3),
				version + 1);
	}

	private static ViewId createPackingHUsViewIdFromParts(
			@NonNull final String pickingSlotsClearingViewIdPart,
			final int bpartnerId,
			final int bpartnerLocationId,
			final int version)
	{
		return ViewId.ofParts(
				PackingHUsViewFactory.WINDOW_ID, // part 0
				pickingSlotsClearingViewIdPart, // part 1
				String.valueOf(Math.max(bpartnerId, 0)), // part 2
				String.valueOf(Math.max(bpartnerLocationId, 0)), // part 3
				String.valueOf(Math.max(version, 0)) // part 4
		);
	}

	int bpartnerId;
	int bpartnerLocationId;
	ViewId packingHUsViewId;

	@lombok.Builder
	private PackingHUsViewKey(
			@NonNull final String pickingSlotsClearingViewIdPart,
			final int bpartnerId,
			final int bpartnerLocationId)
	{
		this.bpartnerId = Math.max(bpartnerId, 0);
		this.bpartnerLocationId = Math.max(bpartnerLocationId, 0);

		this.packingHUsViewId = createPackingHUsViewIdFromParts(
				pickingSlotsClearingViewIdPart,
				this.bpartnerId,
				this.bpartnerLocationId,
				0);
	}

	private PackingHUsViewKey(@NonNull final ViewId packingHUsViewId)
	{
		Check.assumeEquals(packingHUsViewId.getWindowId(), PackingHUsViewFactory.WINDOW_ID, "Invalid packingHUsViewId: {}", packingHUsViewId);

		this.bpartnerId = packingHUsViewId.getPartAsInt(2);
		this.bpartnerLocationId = packingHUsViewId.getPartAsInt(3);
		this.packingHUsViewId = packingHUsViewId;
	}

	public ViewId getPickingSlotsClearingViewId()
	{
		return extractPickingSlotClearingViewId(packingHUsViewId);
	}

	public boolean isBPartnerIdMatching(final int bpartnerIdToMatch)
	{
		return (bpartnerIdToMatch <= 0 && bpartnerId <= 0)
				|| bpartnerIdToMatch == bpartnerId;
	}

	public boolean isBPartnerLocationIdMatching(final int bpartnerLocationIdToMatch)
	{
		return (bpartnerLocationIdToMatch <= 0 && bpartnerLocationId <= 0)
				|| bpartnerLocationIdToMatch == bpartnerLocationId;
	}
}
