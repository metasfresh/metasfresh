/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.cucumber.stepdefs.material.dispo;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.context.SharedTestContext;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.model.I_MD_Candidate_QtyDetails;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.assertj.core.api.Assertions;
import org.compiere.model.IQuery;

@RequiredArgsConstructor
public class MD_Candidate_QtyDetails_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final MD_Candidate_QtyDetails_StepDefData qtyDetailsTable;
	@NonNull private final MaterialDispoDataItem_StepDefData materialDispoDataItemStepDefData;

	@And("metasfresh contains this MD_Candidate_Demand_Detail data")
	public void metasfresh_generates_this_MD_Candidate_Demand_Detail_data(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::validateQtyDetails);
	}

	private void validateQtyDetails(@NonNull final DataTableRow tableRow)
	{
		final IQuery<I_MD_Candidate_QtyDetails> query = getIMdCandidateQtyDetailsIQuery(tableRow);
		final I_MD_Candidate_QtyDetails qtyDetails = query
				.firstOnly(I_MD_Candidate_QtyDetails.class);
		Assertions.assertThat(qtyDetails).isNotNull();
		Assertions.assertThat(qtyDetails.getQty()).as("Quantity").isEqualByComparingTo(tableRow.getAsBigDecimal(I_MD_Candidate_QtyDetails.COLUMNNAME_Qty));

		qtyDetailsTable.putIfMissing(tableRow.getAsIdentifier(), qtyDetails);
	}

	private IQuery<I_MD_Candidate_QtyDetails> getIMdCandidateQtyDetailsIQuery(final @NonNull DataTableRow tableRow)
	{
		final CandidateId candidateId = tableRow.getAsIdentifier(I_MD_Candidate_QtyDetails.COLUMNNAME_MD_Candidate_ID)
				.lookupIdIn(materialDispoDataItemStepDefData);

		final MaterialDispoDataItem materialDispoDataItem = tableRow.getAsOptionalIdentifier(I_MD_Candidate_QtyDetails.COLUMNNAME_Detail_MD_Candidate_ID)
				.flatMap(materialDispoDataItemStepDefData::getOptional)
				.orElse(null);

		final ICompositeQueryFilter<I_MD_Candidate_QtyDetails> detailCandidateFilter = queryBL.createCompositeQueryFilter(I_MD_Candidate_QtyDetails.class)
				.setJoinOr();
		if (materialDispoDataItem != null)
		{
			detailCandidateFilter.addEqualsFilter(I_MD_Candidate_QtyDetails.COLUMNNAME_Detail_MD_Candidate_ID, materialDispoDataItem.getStockCandidateId());
			detailCandidateFilter.addEqualsFilter(I_MD_Candidate_QtyDetails.COLUMNNAME_Detail_MD_Candidate_ID, materialDispoDataItem.getCandidateId());
		}
		else
		{
			detailCandidateFilter.addIsNull(I_MD_Candidate_QtyDetails.COLUMNNAME_Detail_MD_Candidate_ID);
		}

		final IQuery<I_MD_Candidate_QtyDetails> query = queryBL.createQueryBuilder(I_MD_Candidate_QtyDetails.class)
				.addEqualsFilter(I_MD_Candidate_QtyDetails.COLUMNNAME_MD_Candidate_ID, candidateId)
				.filter(detailCandidateFilter)
				.create();

		SharedTestContext.put("query", query);
		return query;
	}

}
