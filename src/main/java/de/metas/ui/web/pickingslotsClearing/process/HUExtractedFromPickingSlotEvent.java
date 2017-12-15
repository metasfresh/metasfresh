package de.metas.ui.web.pickingslotsClearing.process;

import org.adempiere.util.Check;

import lombok.Builder;
import lombok.Value;

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

@Value
public class HUExtractedFromPickingSlotEvent
{
	private final int huId;
	private final int bpartnerId;
	private final int bpartnerLocationId;

	@Builder
	private HUExtractedFromPickingSlotEvent(final int huId, final int bpartnerId, final int bpartnerLocationId)
	{
		Check.assume(huId > 0, "huId > 0");
		this.huId = huId;
		this.bpartnerId = bpartnerId > 0 ? bpartnerId : 0;
		this.bpartnerLocationId = bpartnerLocationId > 0 ? bpartnerLocationId : 0;
	}

}
