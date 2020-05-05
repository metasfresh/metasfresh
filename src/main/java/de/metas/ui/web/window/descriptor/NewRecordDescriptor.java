package de.metas.ui.web.window.descriptor;

import javax.annotation.concurrent.Immutable;

import com.google.common.base.MoreObjects;

import de.metas.ui.web.window.model.Document;

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

/**
 * Describes which window to be used to capture the fields for quickly creating a record for a given BPartner.
 *
 * @author metas-dev <dev@metasfresh.com>
 * @task https://github.com/metasfresh/metasfresh/issues/1090
 */
@Immutable
public final class NewRecordDescriptor
{
	public static final NewRecordDescriptor of(final String tableName, final int newRecordWindowId, final NewRecordProcessor processor)
	{
		return new NewRecordDescriptor(tableName, newRecordWindowId, processor);
	}

	public static interface NewRecordProcessor
	{
		int processNewRecordDocument(Document document);
	}

	private final String tableName;
	private final int newRecordWindowId;
	private final NewRecordProcessor processor;

	private NewRecordDescriptor(final String tableName, final int newRecordWindowId, final NewRecordProcessor processor)
	{
		super();
		this.tableName = tableName;
		this.newRecordWindowId = newRecordWindowId;
		this.processor = processor;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add(tableName, tableName)
				.add("newRecordWindowId", newRecordWindowId)
				.toString();
	}

	public String getTableName()
	{
		return tableName;
	}

	public int getNewRecordWindowId()
	{
		return newRecordWindowId;
	}

	public NewRecordProcessor getProcessor()
	{
		return processor;
	}
}
