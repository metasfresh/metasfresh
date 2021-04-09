package de.metas.elasticsearch.denormalizers.impl;

import com.google.common.base.MoreObjects;
import de.metas.elasticsearch.indexer.source.ESModelToIndex;
import de.metas.util.Check;
import lombok.NonNull;

/*
 * #%L
 * de.metas.elasticsearch.server
 * %%
 * Copyright (C) 2018 metas GmbH
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

final class POModelValueExtractor implements IESModelValueExtractor
{
	public static POModelValueExtractor of(final String modelValueTableName)
	{
		return new POModelValueExtractor(modelValueTableName);
	}

	private final String modelValueTableName;

	private POModelValueExtractor(@NonNull final String modelValueTableName)
	{
		Check.assumeNotEmpty(modelValueTableName, "modelValueTableName is not empty");
		this.modelValueTableName = modelValueTableName;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper("extractor")
				.addValue(modelValueTableName)
				.toString();
	}

	@Override
	public ESModelToIndex extractValue(final ESModelToIndex model, final String columnName)
	{
		return model.getFieldValueAsModel(columnName, modelValueTableName);
	}
}