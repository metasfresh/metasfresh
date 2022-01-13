/*
 * #%L
 * metasfresh-material-dispo-commons
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

package de.metas.material.dispo.commons.repository.query;

import de.metas.inventory.InventoryId;
import de.metas.inventory.InventoryLineId;
import de.metas.material.dispo.commons.candidate.businesscase.StockChangeDetail;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_StockChange_Detail;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;

import javax.annotation.Nullable;

@Value
@Builder
public class StockChangeDetailQuery
{
	@Nullable
	public static StockChangeDetailQuery ofStockChangeDetailOrNull(
			@Nullable final StockChangeDetail stockChangeDetail)
	{
		if (stockChangeDetail == null)
		{
			return null;
		}

		return StockChangeDetailQuery.builder()
				.freshQuantityOnHandRepoId(stockChangeDetail.getFreshQuantityOnHandRepoId())
				.freshQuantityOnHandLineRepoId(stockChangeDetail.getFreshQuantityOnHandLineRepoId())
				.inventoryId(stockChangeDetail.getInventoryId())
				.inventoryLineId(stockChangeDetail.getInventoryLineId())
				.isReverted(stockChangeDetail.getIsReverted())
				.build();
	}

	@Nullable
	Integer freshQuantityOnHandRepoId;

	@Nullable
	Integer freshQuantityOnHandLineRepoId;

	@Nullable
	InventoryId inventoryId;

	@Nullable
	InventoryLineId inventoryLineId;

	@Nullable
	Boolean isReverted;

	public void augmentQueryBuilder(@NonNull final IQueryBuilder<I_MD_Candidate> builder)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_MD_Candidate_StockChange_Detail> stockChangeDetailSubQueryBuilder = queryBL
				.createQueryBuilder(I_MD_Candidate_StockChange_Detail.class)
				.addOnlyActiveRecordsFilter();

		final Integer computedFreshQtyOnHandId = NumberUtils.asInteger(getFreshQuantityOnHandRepoId(), -1);
		if (computedFreshQtyOnHandId > 0)
		{
			stockChangeDetailSubQueryBuilder.addEqualsFilter(I_MD_Candidate_StockChange_Detail.COLUMN_Fresh_QtyOnHand_ID, computedFreshQtyOnHandId);
		}

		final Integer computedFreshQtyOnHandLineId = NumberUtils.asInteger(getFreshQuantityOnHandLineRepoId(), -1);
		if (computedFreshQtyOnHandLineId > 0)
		{
			stockChangeDetailSubQueryBuilder.addEqualsFilter(I_MD_Candidate_StockChange_Detail.COLUMN_Fresh_QtyOnHand_Line_ID, computedFreshQtyOnHandLineId);
		}

		final Integer computedInventoryId = NumberUtils.asInteger(getInventoryId(), -1);
		if (computedInventoryId > 0)
		{
			stockChangeDetailSubQueryBuilder.addEqualsFilter(I_MD_Candidate_StockChange_Detail.COLUMN_M_Inventory_ID, computedInventoryId);
		}

		final Integer computedInventoryLineId = NumberUtils.asInteger(getInventoryLineId(), -1);
		if (computedInventoryLineId > 0)
		{
			stockChangeDetailSubQueryBuilder.addEqualsFilter(I_MD_Candidate_StockChange_Detail.COLUMN_M_InventoryLine_ID, computedInventoryLineId);
		}

		if (getIsReverted() != null)
		{
			stockChangeDetailSubQueryBuilder.addEqualsFilter(I_MD_Candidate_StockChange_Detail.COLUMN_IsReverted, getIsReverted());
		}

		builder.addInSubQueryFilter(I_MD_Candidate.COLUMN_MD_Candidate_ID,
									I_MD_Candidate_StockChange_Detail.COLUMN_MD_Candidate_ID,
									stockChangeDetailSubQueryBuilder.create());
	}
}
