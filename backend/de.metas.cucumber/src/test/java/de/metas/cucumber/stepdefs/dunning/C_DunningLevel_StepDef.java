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
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Dunning;
import org.compiere.model.I_C_DunningLevel;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class C_DunningLevel_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final C_DunningLevel_StepDefData dunningLevelTable;
	private final C_Dunning_StepDefData dunningTable;

	public C_DunningLevel_StepDef(
			@NonNull final C_DunningLevel_StepDefData dunningLevelTable,
			@NonNull final C_Dunning_StepDefData dunningTable)
	{
		this.dunningLevelTable = dunningLevelTable;
		this.dunningTable = dunningTable;
	}

	@Given("metasfresh contains C_DunningLevel:")
	public void metasfresh_contains_C_DunningLevel(@NonNull final DataTable dataTable)
	{
		dataTable.asMaps().forEach(this::createC_DunningLevel);
	}

	private void createC_DunningLevel(@NonNull final Map<String, String> row)
	{
		final String dunningIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_DunningLevel.COLUMNNAME_C_Dunning_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_C_Dunning dunningRecord = dunningTable.get(dunningIdentifier);

		final String name = DataTableUtil.extractStringForColumnName(row, I_C_DunningLevel.COLUMNNAME_Name);

		final I_C_DunningLevel record = CoalesceUtil.coalesceSuppliers(
				() -> queryBL.createQueryBuilder(I_C_DunningLevel.class)
						.addStringLikeFilter(I_C_DunningLevel.COLUMNNAME_Name, name, true)
						.addEqualsFilter(I_C_DunningLevel.COLUMNNAME_C_Dunning_ID, dunningRecord.getC_Dunning_ID())
						.create()
						.firstOnlyOrNull(I_C_DunningLevel.class),
				() -> InterfaceWrapperHelper.newInstance(I_C_DunningLevel.class));

		assertThat(record).isNotNull();

		record.setName(name);
		record.setC_Dunning_ID(dunningRecord.getC_Dunning_ID());
		record.setPrintName("printName");
		record.setDaysAfterDue(BigDecimal.ZERO);
		record.setDaysBetweenDunning(0);

		InterfaceWrapperHelper.save(record);

		final String dunningLevelIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_DunningLevel.COLUMNNAME_C_DunningLevel_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		dunningLevelTable.putOrReplace(dunningLevelIdentifier, record);
	}
}
