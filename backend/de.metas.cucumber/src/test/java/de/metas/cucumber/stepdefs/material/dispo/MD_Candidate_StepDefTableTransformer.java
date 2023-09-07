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

import de.metas.common.util.time.SystemTime;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.X_MD_Candidate;
import de.metas.product.ProductId;
import io.cucumber.datatable.DataTable;
import io.cucumber.datatable.TableTransformer;
import io.cucumber.java.DataTableType;
import lombok.NonNull;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.eevolution.model.I_PP_Product_Planning.COLUMNNAME_M_AttributeSetInstance_ID;

public class MD_Candidate_StepDefTableTransformer implements TableTransformer<MD_Candidate_StepDefTable>
{
	private final M_Product_StepDefData productTable;

	public MD_Candidate_StepDefTableTransformer(@NonNull final M_Product_StepDefData productTable)
	{
		this.productTable = productTable;
	}

	@DataTableType
	@Override
	public MD_Candidate_StepDefTable transform(@NonNull final DataTable dataTable)
	{
		final MD_Candidate_StepDefTable.MD_Candidate_StepDefTableBuilder materialDispoTableBuilder = MD_Candidate_StepDefTable.builder();

		final List<Map<String, String>> dataTableRows = dataTable.asMaps(String.class, String.class);

		for (final Map<String, String> dataTableRow : dataTableRows)
		{
			final String identifier = DataTableUtil.extractRecordIdentifier(dataTableRow, I_MD_Candidate.COLUMNNAME_MD_Candidate_ID, "MD_Candidate");

			final String candidateTypeStr = dataTableRow.get(I_MD_Candidate.COLUMNNAME_MD_Candidate_Type);
			Assertions.assertThat(candidateTypeStr).as("Missing value for %s in dataTableRow=%s",I_MD_Candidate.COLUMNNAME_MD_Candidate_Type, dataTableRow).isNotBlank();
			final CandidateType type = CandidateType.ofCode(candidateTypeStr);

			final CandidateBusinessCase businessCase = CandidateBusinessCase.ofCodeOrNull(dataTableRow.get("OPT." + I_MD_Candidate.COLUMNNAME_MD_Candidate_BusinessCase));

			final String productIdentifier = DataTableUtil.extractStringForColumnName(dataTableRow, I_M_Product.COLUMNNAME_M_Product_ID + ".Identifier");
			final int productId = StepDefUtil.extractId(productIdentifier, productTable);

			final Instant time;
			final String timeInLocalTimeZone = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT.DateProjected_LocalTimeZone");

			if (timeInLocalTimeZone != null)
			{
				final LocalDateTime localDateTime = LocalDateTime.parse(timeInLocalTimeZone);
				time = localDateTime.atZone(SystemTime.zoneId()).toInstant();
			}
			else
			{
				time = DataTableUtil.extractInstantForColumnName(dataTableRow, I_MD_Candidate.COLUMNNAME_DateProjected);
			}

			BigDecimal qty = DataTableUtil.extractBigDecimalForColumnName(dataTableRow, I_MD_Candidate.COLUMNNAME_Qty);

			if (type.equals(CandidateType.DEMAND) || type.equals(CandidateType.INVENTORY_DOWN) || type.equals(CandidateType.UNEXPECTED_DECREASE))
			{
				qty = qty.negate();
			}

			final BigDecimal atp = DataTableUtil.extractBigDecimalForColumnName(dataTableRow, I_MD_Candidate.COLUMNNAME_Qty_AvailableToPromise);

			final String attributeSetInstanceIdentifier = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT." + COLUMNNAME_M_AttributeSetInstance_ID + "." + TABLECOLUMN_IDENTIFIER);

			final boolean simulated = DataTableUtil.extractBooleanForColumnNameOr(dataTableRow, "OPT." + X_MD_Candidate.MD_CANDIDATE_STATUS_Simulated, false);

			final MD_Candidate_StepDefTable.MaterialDispoTableRow tableRow = MD_Candidate_StepDefTable.MaterialDispoTableRow.builder()
					.identifier(identifier)
					.type(type)
					.businessCase(businessCase)
					.productId(ProductId.ofRepoId(productId))
					.time(time)
					.qty(qty)
					.atp(atp)
					.attributeSetInstanceId(attributeSetInstanceIdentifier)
					.simulated(simulated)
					.build();
			materialDispoTableBuilder.row(identifier, tableRow);
		}

		return materialDispoTableBuilder.build();
	}
}
