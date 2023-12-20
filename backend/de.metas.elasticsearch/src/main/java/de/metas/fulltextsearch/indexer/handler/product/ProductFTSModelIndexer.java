/*
 * #%L
 * de.metas.elasticsearch
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.fulltextsearch.indexer.handler.product;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.fulltextsearch.config.ESDocumentToIndex;
import de.metas.fulltextsearch.config.ESDocumentToIndexChunk;
import de.metas.fulltextsearch.config.ESDocumentToIndexTemplate;
import de.metas.fulltextsearch.config.FTSConfig;
import de.metas.fulltextsearch.indexer.handler.FTSModelIndexer;
import de.metas.fulltextsearch.indexer.queue.ModelToIndex;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.TableName;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_Product;
import org.compiere.util.Evaluatee;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class ProductFTSModelIndexer implements FTSModelIndexer
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public Set<TableName> getHandledSourceTableNames()
	{
		return ImmutableSet.of(TableName.ofString(I_M_Product.Table_Name));
	}

	@Override
	public List<ESDocumentToIndexChunk> createDocumentsToIndex(
			@NonNull final List<ModelToIndex> requests,
			@NonNull final FTSConfig config)
	{
		final Set<ProductId> productIds = extractAffectedProductIds(requests);

		final ESDocumentToIndexChunk.ESDocumentToIndexChunkBuilder result = ESDocumentToIndexChunk.builder();

		result.documentIdsToDelete(productIds.stream().map(String::valueOf).collect(ImmutableList.toImmutableList()));

		if (!productIds.isEmpty())
		{
			final ESDocumentToIndexTemplate documentToIndexTemplate = config.getDocumentToIndexTemplate();

			final List<I_M_Product> records = queryBL
					.createQueryBuilder(I_M_Product.class)
					.addOnlyActiveRecordsFilter()
					.addInArrayFilter(I_M_Product.COLUMN_M_Product_ID, productIds)
					.create()
					.list();
			for (final I_M_Product record : records)
			{
				final String esDocumentId = String.valueOf(record.getM_Product_ID());
				final Evaluatee evalCtx = InterfaceWrapperHelper.getEvaluatee(record);
				final ESDocumentToIndex documentToIndex = documentToIndexTemplate.resolve(evalCtx, esDocumentId);
				result.documentToIndex(documentToIndex);
			}
		}

		return ImmutableList.of(result.build());
	}

	private ImmutableSet<ProductId> extractAffectedProductIds(@NonNull final List<ModelToIndex> requests)
	{
		final ImmutableSet.Builder<ProductId> productIds = ImmutableSet.builder();

		for (final ModelToIndex request : requests)
		{
			final TableRecordReference sourceModelRef = request.getSourceModelRef();
			if (sourceModelRef == null)
			{
				continue;
			}

			productIds.add(ProductId.ofRepoId(sourceModelRef.getRecord_ID()));

		}

		return productIds.build();
	}
}
