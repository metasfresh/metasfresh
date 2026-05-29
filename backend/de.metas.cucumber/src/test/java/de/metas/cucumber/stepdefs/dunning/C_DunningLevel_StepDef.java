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

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Dunning;
import org.compiere.model.I_C_DunningLevel;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.compiere.model.I_C_DunningLevel.COLUMNNAME_C_DunningLevel_ID;

@RequiredArgsConstructor
public class C_DunningLevel_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull private final C_DunningLevel_StepDefData dunningLevelTable;
	@NonNull private final C_Dunning_StepDefData dunningTable;

	@Given("metasfresh contains C_DunningLevel:")
	public void metasfresh_contains_C_DunningLevel(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(COLUMNNAME_C_DunningLevel_ID)
				.forEach(this::createC_DunningLevel);
	}

	private void createC_DunningLevel(@NonNull final DataTableRow tableRow)
	{
		final I_C_Dunning dunningRecord = tableRow.getAsIdentifier(I_C_DunningLevel.COLUMNNAME_C_Dunning_ID)
				.lookupIn(dunningTable);
		assertThat(dunningRecord).isNotNull();

		final String name = tableRow.suggestValueAndName().getName();

		final I_C_DunningLevel record = CoalesceUtil.coalesceSuppliers(
				() -> queryBL.createQueryBuilder(I_C_DunningLevel.class)
						.addStringLikeFilter(I_C_DunningLevel.COLUMNNAME_Name, name, true)
						.addEqualsFilter(I_C_DunningLevel.COLUMNNAME_C_Dunning_ID, dunningRecord.getC_Dunning_ID())
						.create()
						.firstOnlyOrNull(I_C_DunningLevel.class),
				() -> InterfaceWrapperHelper.newInstance(I_C_DunningLevel.class));

		assertThat(record).isNotNull();

		final String printName = tableRow.getAsOptionalString(I_C_DunningLevel.COLUMNNAME_PrintName)
				.orElse(name);
		final BigDecimal daysAfterDue = tableRow.getAsOptionalBigDecimal(I_C_DunningLevel.COLUMNNAME_DaysAfterDue)
				.orElse(BigDecimal.ZERO);
		final int daysBetweenDunning = tableRow.getAsOptionalInt(I_C_DunningLevel.COLUMNNAME_DaysBetweenDunning)
				.orElse(0);

		record.setName(name);
		record.setC_Dunning_ID(dunningRecord.getC_Dunning_ID());
		record.setPrintName(printName);
		record.setDaysAfterDue(daysAfterDue);
		record.setDaysBetweenDunning(daysBetweenDunning);

		InterfaceWrapperHelper.save(record);

		tableRow.getAsIdentifier().putOrReplace(dunningLevelTable, record);
	}
}
