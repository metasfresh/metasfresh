package de.metas.ui.web.window.model.lookup;

import java.util.Optional;

import javax.annotation.Nullable;

import org.adempiere.util.lang.impl.TableRecordReference;

import de.metas.ui.web.window.datatypes.WindowId;
import lombok.Builder;
import lombok.NonNull;
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
@Builder(toBuilder = true)
public final class DocumentZoomIntoInfo
{
	public static DocumentZoomIntoInfo of(String tableName, int id)
	{
		final Integer idObj = id >= 0 ? id : null;
		return builder().tableName(tableName).recordId(idObj).build();
	}

	@NonNull
	private final String tableName;
	private final Integer recordId;
	private final WindowId windowId;

	public DocumentZoomIntoInfo overrideWindowIdIfPossible(@Nullable final Optional<WindowId> windowId)
	{
		if (windowId == null || !windowId.isPresent())
		{
			return this;
		}
		return toBuilder().windowId(windowId.get()).build();
	}

	public boolean isRecordIdPresent()
	{
		return recordId != null;
	}

	public TableRecordReference toTableRecordReference()
	{
		if (recordId == null)
		{
			throw new IllegalStateException("Cannot convert to " + TableRecordReference.class + " when recordId is missing: " + this);
		}
		return TableRecordReference.of(tableName, recordId);
	}
}
