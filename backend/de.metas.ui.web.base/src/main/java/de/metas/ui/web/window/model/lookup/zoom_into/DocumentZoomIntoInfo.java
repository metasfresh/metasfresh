package de.metas.ui.web.window.model.lookup.zoom_into;

import de.metas.document.references.zoom_into.CustomizedWindowInfo;
import de.metas.document.references.zoom_into.CustomizedWindowInfoMap;
import de.metas.ui.web.window.datatypes.WindowId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.element.api.AdWindowId;

import javax.annotation.Nullable;
import java.util.Optional;

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
@Builder(toBuilder = true)
public class DocumentZoomIntoInfo
{
	public static DocumentZoomIntoInfo of(final String tableName, final int id)
	{
		final Integer idObj = id >= 0 ? id : null;
		return builder().tableName(tableName).recordId(idObj).build();
	}

	@NonNull String tableName;
	Integer recordId;
	WindowId windowId;

	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	public DocumentZoomIntoInfo overrideWindowIdIfPossible(@Nullable final Optional<WindowId> windowId)
	{
		if (windowId == null || !windowId.isPresent())
		{
			return this;
		}
		return toBuilder().windowId(windowId.get()).build();
	}

	public DocumentZoomIntoInfo overrideWindowIdIfPossible(@NonNull final CustomizedWindowInfoMap customizedWindowInfoMap)
	{
		if (this.windowId == null)
		{
			return this;
		}

		final AdWindowId adWindowId = this.windowId.toAdWindowIdOrNull();
		if (adWindowId == null)
		{
			return this;
		}

		final WindowId customizedWindowId = customizedWindowInfoMap
				.getCustomizedWindowInfo(adWindowId)
				.map(CustomizedWindowInfo::getCustomizationWindowId)
				.map(WindowId::of)
				.orElse(null);
		if (customizedWindowId == null)
		{
			return this;
		}

		return !WindowId.equals(this.windowId, customizedWindowId)
				? toBuilder().windowId(customizedWindowId).build()
				: this;
	}

	public boolean isRecordIdPresent()
	{
		return recordId != null;
	}
}
