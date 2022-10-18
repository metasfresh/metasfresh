/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.dunning;

import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.sectioncode.M_SectionCode_StepDefData;
import de.metas.dunning.model.I_C_DunningDoc;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DunningLevel;
import org.compiere.model.I_M_SectionCode;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;

public class C_DunningDoc_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final C_DunningLevel_StepDefData dunningLevelTable;
	private final M_SectionCode_StepDefData sectionCodeTable;
	private final C_BPartner_StepDefData bpartnerTable;

	public C_DunningDoc_StepDef(
			@NonNull final C_DunningLevel_StepDefData dunningLevelTable,
			@NonNull final M_SectionCode_StepDefData sectionCodeTable,
			@NonNull final C_BPartner_StepDefData bpartnerTable)
	{
		this.dunningLevelTable = dunningLevelTable;
		this.sectionCodeTable = sectionCodeTable;
		this.bpartnerTable = bpartnerTable;
	}

	@And("validate C_DunningDoc:")
	public void validate_C_DunningDoc(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final List<I_C_DunningDoc> dunningDocs = retrieveDunningDoc(row);
			assertThat(dunningDocs).isNotEmpty();

			dunningDocs.forEach(doc -> validateDunningDoc(doc, row));
		}
	}

	@NonNull
	private List<I_C_DunningDoc> retrieveDunningDoc(@NonNull final Map<String, String> row)
	{
		final String bpIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_DunningDoc.COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_BPartner bPartner = bpartnerTable.get(bpIdentifier);

		return queryBL.createQueryBuilder(I_C_DunningDoc.class)
				.addEqualsFilter(I_C_DunningDoc.COLUMNNAME_C_BPartner_ID, bPartner.getC_BPartner_ID())
				.create()
				.list();
	}

	private void validateDunningDoc(@NonNull final I_C_DunningDoc dunningDoc, @NonNull final Map<String, String> row)
	{
		final String dunningLevelIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_DunningDoc.COLUMNNAME_C_DunningLevel_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_C_DunningLevel dunningLevel = dunningLevelTable.get(dunningLevelIdentifier);

		assertThat(dunningDoc.getC_DunningLevel_ID()).isEqualTo(dunningLevel.getC_DunningLevel_ID());

		final String sectionCodeIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_DunningDoc.COLUMNNAME_M_SectionCode_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(sectionCodeIdentifier))
		{
			final I_M_SectionCode sectionCode = sectionCodeTable.get(sectionCodeIdentifier);
			assertThat(dunningDoc.getM_SectionCode_ID()).isEqualTo(sectionCode.getM_SectionCode_ID());
		}

		final Boolean processed = DataTableUtil.extractBooleanForColumnNameOr(row, I_C_DunningDoc.COLUMNNAME_Processed, false);
		assertThat(dunningDoc.isProcessed()).isEqualTo(processed);
	}
}
