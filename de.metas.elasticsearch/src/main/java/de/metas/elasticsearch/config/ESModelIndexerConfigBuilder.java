package de.metas.elasticsearch.config;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.adempiere.model.ModelColumn;
import org.adempiere.util.Check;

import de.metas.elasticsearch.trigger.ESDocumentIndexTriggerInterceptor;
import de.metas.elasticsearch.trigger.ESOnDeleteTriggerInterceptor;
import de.metas.elasticsearch.trigger.IESModelIndexerTrigger;

/*
 * #%L
 * de.metas.elasticsearch
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class ESModelIndexerConfigBuilder
{
	private final Consumer<ESModelIndexerConfigBuilder> configInstaller;

	private final String _modelTableName;

	private final String _indexName;
	private String _indexType;
	private boolean allowChangingIndexType = true;

	private final List<IESModelIndexerTrigger> _triggers = new ArrayList<>();

	public ESModelIndexerConfigBuilder(final Consumer<ESModelIndexerConfigBuilder> configInstaller, final String indexName, final String modelTableName)
	{
		super();

		Check.assumeNotNull(configInstaller, "Parameter configInstaller is not null");
		this.configInstaller = configInstaller;

		Check.assumeNotEmpty(indexName, "indexName is not empty");
		_indexName = indexName;

		Check.assumeNotEmpty(modelTableName, "modelTableName is not empty");
		_modelTableName = modelTableName;
	}

	public void buildAndInstall()
	{
		configInstaller.accept(this);
	}

	public String getId()
	{
		return getIndexName() + "#" + getIndexType();
	}

	public String getIndexName()
	{
		return _indexName;
	}

	/**
	 * Sets the index type.
	 *
	 * By default, the index type is the same as the table name.
	 *
	 * @param indexType
	 */
	public ESModelIndexerConfigBuilder setIndexType(final String indexType)
	{
		if (!allowChangingIndexType)
		{
			throw new IllegalStateException("Changing indexType from " + getIndexType() + " to " + indexType + " is no longer allowed because the index type was already used");
		}

		_indexType = indexType;
		return this;
	}

	public String getIndexType()
	{
		if (Check.isEmpty(_indexType, true))
		{
			return getModelTableName();
		}
		else
		{
			return _indexType;
		}
	}

	public String getModelTableName()
	{
		// note: we assume is not null at this point
		return _modelTableName;
	}

	public List<IESModelIndexerTrigger> getTriggers()
	{
		return _triggers;
	}

	public <DocumentType, ModelType> ESModelIndexerConfigBuilder triggerOnDocumentChanged(final Class<DocumentType> documentClass, final ModelColumn<ModelType, DocumentType> modelParentColumn)
	{
		final String modelParentColumnName = modelParentColumn.getColumnName();
		final ESDocumentIndexTriggerInterceptor<DocumentType> trigger = new ESDocumentIndexTriggerInterceptor<>(documentClass, getModelTableName(), modelParentColumnName, getId());

		_triggers.add(trigger);
		allowChangingIndexType = false;

		return this;
	}

	public ESModelIndexerConfigBuilder triggerOnDelete()
	{
		final ESOnDeleteTriggerInterceptor trigger = new ESOnDeleteTriggerInterceptor(getModelTableName(), getId());

		_triggers.add(trigger);
		allowChangingIndexType = false;

		return this;
	}
}
