/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.cucumber.stepdefs.contract;

import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.EmptyUtil;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Matching;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.productCategory.M_Product_Category_StepDefData;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Product_Category;

import java.util.List;
import java.util.Map;

import static de.metas.contracts.model.I_C_Flatrate_Matching.COLUMNNAME_C_Flatrate_Conditions_ID;
import static de.metas.contracts.model.I_C_Flatrate_Matching.COLUMNNAME_C_Flatrate_Matching_ID;
import static de.metas.contracts.model.I_C_Flatrate_Matching.COLUMNNAME_M_Product_Category_Matching_ID;
import static de.metas.contracts.model.I_C_Flatrate_Matching.COLUMNNAME_SeqNo;
import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;

public class C_Flatrate_Matching_StepDef
{

	private final C_Flatrate_Conditions_StepDefData conditionsTable;
	private final M_Product_Category_StepDefData productCategoryStepDefData;
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public C_Flatrate_Matching_StepDef(@NonNull final C_Flatrate_Conditions_StepDefData conditionsTable, @NonNull final M_Product_Category_StepDefData productCategoryStepDefData)
	{
		this.conditionsTable = conditionsTable;
		this.productCategoryStepDefData = productCategoryStepDefData;
	}

	@Given("metasfresh contains C_Flatrate_Matchings:")
	public void metasfresh_contains_c_flatrate_matchings(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String conditionsIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_Flatrate_Conditions_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Flatrate_Conditions conditionsRecord = conditionsTable.get(conditionsIdentifier);
			assertThat(conditionsRecord).as("Missing C_Flatrate_Conditions for identifier=%s", conditionsIdentifier).isNotNull();

			final int seqNo = DataTableUtil.extractIntForColumnName(tableRow, COLUMNNAME_SeqNo);

			final I_C_Flatrate_Matching matchingRecord = CoalesceUtil.coalesceSuppliersNotNull(
					() -> queryBL.createQueryBuilder(I_C_Flatrate_Matching.class)
							.addEqualsFilter(COLUMNNAME_C_Flatrate_Conditions_ID, conditionsRecord.getC_Flatrate_Conditions_ID())
							.addEqualsFilter(COLUMNNAME_SeqNo, seqNo).orderBy(COLUMNNAME_C_Flatrate_Matching_ID)
							.create().first(),
					() -> InterfaceWrapperHelper.newInstance(I_C_Flatrate_Matching.class));

			matchingRecord.setC_Flatrate_Conditions_ID(conditionsRecord.getC_Flatrate_Conditions_ID());
			matchingRecord.setSeqNo(seqNo);
			matchingRecord.setIsActive(true);
			matchingRecord.setC_Flatrate_Transition_ID(StepDefConstants.FLATRATE_TRANSITION_ID.getRepoId());

			final String productCategoryIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, COLUMNNAME_M_Product_Category_Matching_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (EmptyUtil.isNotBlank(productCategoryIdentifier))
			{
				final I_M_Product_Category productCategoryRecord = productCategoryStepDefData.get(productCategoryIdentifier);
				assertThat(productCategoryRecord).as("Missing M_Product_Category for identifier=%s", productCategoryIdentifier).isNotNull();
				matchingRecord.setM_Product_Category_Matching_ID(productCategoryRecord.getM_Product_Category_ID());
			}

			InterfaceWrapperHelper.saveRecord(matchingRecord);
		}
	}
}