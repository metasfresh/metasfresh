package de.metas.ui.web.pickingslotsClearing;

import de.metas.ui.web.view.ViewId;
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
/* package */final class PackingHUsViewKey
{
	public static PackingHUsViewKey ofPackingHUsViewId(final ViewId packingHUsViewId)
	{
		final String pickingSlotsClearingViewIdPart = packingHUsViewId.getViewIdPart();
		final int bpartnerId = packingHUsViewId.getPartAsInt(2);
		final int bpartnerLocationId = packingHUsViewId.getPartAsInt(3);

		return new PackingHUsViewKey(pickingSlotsClearingViewIdPart, bpartnerId, bpartnerLocationId);
	}

	public static final ViewId extractPickingSlotClearingViewId(final ViewId packingHUsViewId)
	{
		return ViewId.ofParts(PickingSlotsClearingViewFactory.WINDOW_ID, packingHUsViewId.getViewIdPart());
	}

	private final int bpartnerId;
	private final int bpartnerLocationId;
	private final ViewId packingHUsViewId;

	@lombok.Builder
	private PackingHUsViewKey(
			@NonNull final String pickingSlotsClearingViewIdPart,
			final int bpartnerId,
			final int bpartnerLocationId)
	{
		this.bpartnerId = bpartnerId > 0 ? bpartnerId : 0;
		this.bpartnerLocationId = bpartnerLocationId > 0 ? bpartnerLocationId : 0;

		this.packingHUsViewId = ViewId.ofParts(
				PackingHUsViewFactory.WINDOW_ID, // part 0
				pickingSlotsClearingViewIdPart, // part 1
				String.valueOf(this.bpartnerId), // part 2
				String.valueOf(this.bpartnerLocationId) // part 3
		);
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
