/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2020 metas GmbH
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

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.product.ProductId;
import io.cucumber.datatable.DataTable;
import io.cucumber.datatable.TableTransformer;
import io.cucumber.java.DataTableType;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class MD_Candidate_StepDefTableTransformer implements TableTransformer<MD_Candidate_StepDefTable>
{
	@DataTableType
	@Override
	public MD_Candidate_StepDefTable transform(@NonNull final DataTable dataTable)
	{
		final MD_Candidate_StepDefTable.MD_Candidate_StepDefTableBuilder materialDispoTableBuilder = MD_Candidate_StepDefTable.builder();

		final List<Map<String, String>> dataTableRows = dataTable.asMaps();

		for (final Map<String, String> dataTableRow : dataTableRows)
		{
			final String identifier = DataTableUtil.extractRecordIdentifier(dataTableRow,"MD_Candidate");

			final CandidateType type = CandidateType.ofCode(dataTableRow.get("Type"));
			final CandidateBusinessCase businessCase = CandidateBusinessCase.ofCodeOrNull(dataTableRow.get("BusinessCase"));

			final int productId = DataTableUtil.extractIntForColumnName(dataTableRow, "M_Product_ID");

			final Instant time = DataTableUtil.extractInstantForColumnName(dataTableRow, "Time");
			final BigDecimal qty = DataTableUtil.extractBigDecimalForColumnName(dataTableRow, "Qty");
			final BigDecimal atp = DataTableUtil.extractBigDecimalForColumnName(dataTableRow, "ATP");

			final MD_Candidate_StepDefTable.MaterialDispoTableRow tableRow = MD_Candidate_StepDefTable.MaterialDispoTableRow.builder()
					.identifier(identifier)
					.type(type)
					.businessCase(businessCase)
					.productId(ProductId.ofRepoId(productId))
					.time(time)
					.qty(qty)
					.atp(atp)
					.build();
			materialDispoTableBuilder.row(identifier, tableRow);
		}

		return materialDispoTableBuilder.build();
	}
}
