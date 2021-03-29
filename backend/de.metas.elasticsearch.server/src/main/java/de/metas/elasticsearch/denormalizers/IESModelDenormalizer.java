package de.metas.elasticsearch.denormalizers;

import de.metas.elasticsearch.config.ESModelIndexerProfile;
import de.metas.elasticsearch.indexer.source.ESModelToIndex;
import org.elasticsearch.common.xcontent.XContentBuilder;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/*
 * #%L
 * de.metas.business
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface IESModelDenormalizer
{
	Map<String, Object> denormalizeModel(ESModelToIndex value);

	void appendMapping(XContentBuilder builder, @Nullable String fieldName) throws IOException;

	ESModelIndexerProfile getProfile();

	/**
	 * @return model's table name this denormalizer supports
	 */
	String getModelTableName();

	/**
	 * Extracts the Elasticsearch document ID from given model.
	 */
	@Nullable
	String extractId(ESModelToIndex model);

	Set<String> getFullTextSearchFieldNames();
}
