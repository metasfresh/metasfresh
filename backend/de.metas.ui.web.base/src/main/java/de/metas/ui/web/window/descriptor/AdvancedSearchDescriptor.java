package de.metas.ui.web.window.descriptor;

import com.google.common.base.MoreObjects;
import de.metas.ui.web.window.model.Document;

import javax.annotation.concurrent.Immutable;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2021 metas GmbH
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
 * Describes which window to be used to capture the fields for quickly creating a record for a given table.
 */
@Immutable
public final class AdvancedSearchDescriptor
{
	public static AdvancedSearchDescriptor of(final String tableName, final int advSearchWindowId, final AdvancedSearchSelectionProcessor processor)
	{
		return new AdvancedSearchDescriptor(tableName, advSearchWindowId, processor);
	}

	public interface AdvancedSearchSelectionProcessor
	{
		void processSelection(Document document, final String fieldName, String selectionIdStr);
	}

	private final String tableName;
	private final int windowId;
	private final AdvancedSearchSelectionProcessor processor;

	private AdvancedSearchDescriptor(final String tableName, final int windowId, final AdvancedSearchSelectionProcessor processor)
	{
		super();
		this.tableName = tableName;
		this.windowId = windowId;
		this.processor = processor;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add(tableName, tableName)
				.add("windowId", windowId)
				.toString();
	}

	public String getTableName()
	{
		return tableName;
	}

	public int getWindowId()
	{
		return windowId;
	}

	public AdvancedSearchSelectionProcessor getProcessor()
	{
		return processor;
	}
}
