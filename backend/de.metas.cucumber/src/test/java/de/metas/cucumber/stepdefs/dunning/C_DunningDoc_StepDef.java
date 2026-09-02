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

import de.metas.bpartner.BPartnerId;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.dunning.model.I_C_DunningDoc;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.SoftAssertions;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class C_DunningDoc_StepDef
{
	private final C_DunningLevel_StepDefData dunningLevelTable;
	private final C_BPartner_StepDefData bpartnerTable;
	private final C_DunningDoc_StepDefData dunningDocTable;

	@And("validate C_DunningDoc:")
	public void validate_C_DunningDoc(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_C_DunningDoc.COLUMNNAME_C_DunningDoc_ID)
				.forEach(this::validateDunningDoc);
	}

	private void validateDunningDoc(@NonNull final DataTableRow row)
	{
		final I_C_DunningDoc dunningDoc = row.getAsIdentifier()
				.lookupIn(dunningDocTable);
		assertThat(dunningDoc).isNotNull();

		final SoftAssertions softly = new SoftAssertions();
		
		row.getAsOptionalIdentifier(I_C_DunningDoc.COLUMNNAME_C_DunningLevel_ID)
				.map(dunningLevelTable::get)
				.ifPresent(dunningLevel -> softly.assertThat(dunningDoc.getC_DunningLevel_ID())
						.as(I_C_DunningDoc.COLUMNNAME_C_DunningLevel_ID)
						.isEqualTo(dunningLevel.getC_DunningLevel_ID()));

		row.getAsOptionalBoolean(I_C_DunningDoc.COLUMNNAME_Processed)
				.ifPresent(processed -> softly.assertThat(dunningDoc.isProcessed())
						.isEqualTo(processed));

		row.getAsOptionalIdentifier(I_C_DunningDoc.COLUMNNAME_C_BPartner_ID)
				.map(bpartnerTable::getId)
				.ifPresent(bPartnerId -> softly.assertThat(BPartnerId.ofRepoIdOrNull(dunningDoc.getC_BPartner_ID()))
						.as(I_C_DunningDoc.COLUMNNAME_C_BPartner_ID)
						.isEqualTo(bPartnerId));
		
		softly.assertAll();
	}
}
