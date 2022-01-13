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

package de.metas.material.dispo.commons.candidate.businesscase;

import de.metas.inventory.InventoryId;
import de.metas.inventory.InventoryLineId;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateStockChangeDetailId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;

@Value
@Builder(toBuilder = true)
public class StockChangeDetail implements BusinessCaseDetail
{
	@Nullable
	CandidateStockChangeDetailId candidateStockChangeDetailId;

	@Nullable
	CandidateId candidateId;

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

	@NonNull
	Instant eventDate;

	@Override
	public CandidateBusinessCase getCandidateBusinessCase()
	{
		return CandidateBusinessCase.STOCK_CHANGE;
	}

	@Override
	public BigDecimal getQty()
	{
		throw new AdempiereException("StockChangeDetail doesn't carry Qty!");
	}

	@Nullable
	public static StockChangeDetail castOrNull(@Nullable final BusinessCaseDetail businessCaseDetail)
	{
		if (!(businessCaseDetail instanceof StockChangeDetail))
		{
			return null;
		}
		return cast(businessCaseDetail);
	}

	@NonNull
	public static StockChangeDetail cast(@NonNull final BusinessCaseDetail businessCaseDetail)
	{
		return (StockChangeDetail)businessCaseDetail;
	}
}
