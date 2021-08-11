/*
 * #%L
 * de.metas.elasticsearch
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

package de.metas.fulltextsearch.indexer.handler;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.logging.LogManager;
import de.metas.util.GuavaCollectors;
import lombok.NonNull;
import org.adempiere.ad.table.api.TableName;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class FTSModelIndexerRegistry
{
	private static final Logger logger = LogManager.getLogger(FTSModelIndexerRegistry.class);

	private final ImmutableMap<TableName, FTSModelIndexer> indexersBySourceTableName;

	public FTSModelIndexerRegistry(@NonNull final Optional<List<FTSModelIndexer>> indexers)
	{
		this.indexersBySourceTableName = indexers.orElseGet(ImmutableList::of)
				.stream()
				.flatMap(indexer -> indexer.getHandledSourceTableNames()
						.stream()
						.map(sourceTableName -> GuavaCollectors.entry(sourceTableName, indexer)))
				.collect(GuavaCollectors.toImmutableMap());

		logger.info("Indexers: {}", this.indexersBySourceTableName);
	}

	public Optional<FTSModelIndexer> getBySourceTableName(@NonNull final TableName sourceTableName)
	{
		return Optional.ofNullable(indexersBySourceTableName.get(sourceTableName));
	}

	public List<FTSModelIndexer> getBySourceTableNames(@NonNull final Collection<TableName> sourceTableNames)
	{
		return sourceTableNames.stream()
				.distinct()
				.map(indexersBySourceTableName::get)
				.filter(Objects::nonNull)
				.distinct()
				.collect(ImmutableList.toImmutableList());
	}

}
