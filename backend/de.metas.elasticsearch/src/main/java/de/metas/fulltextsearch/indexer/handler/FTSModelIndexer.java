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

import de.metas.fulltextsearch.config.ESDocumentToIndexChunk;
import de.metas.fulltextsearch.config.FTSConfig;
import de.metas.fulltextsearch.indexer.queue.ModelToIndex;
import org.adempiere.ad.table.api.TableName;

import java.util.List;
import java.util.Set;

public interface FTSModelIndexer
{
	Set<TableName> getHandledSourceTableNames();

	List<ESDocumentToIndexChunk> createDocumentsToIndex(List<ModelToIndex> requests, final FTSConfig config);
}
