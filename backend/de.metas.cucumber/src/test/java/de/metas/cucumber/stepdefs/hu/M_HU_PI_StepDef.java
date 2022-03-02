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

package de.metas.cucumber.stepdefs.hu;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.handlingunits.model.I_M_HU_PI.COLUMNNAME_IsActive;
import static de.metas.handlingunits.model.I_M_HU_PI.COLUMNNAME_M_HU_PI_ID;
import static de.metas.handlingunits.model.I_M_HU_PI.COLUMNNAME_Name;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class M_HU_PI_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final StepDefData<I_M_HU_PI> huPiTable;

	public M_HU_PI_StepDef(@NonNull final StepDefData<I_M_HU_PI> huPiTable)
	{
		this.huPiTable = huPiTable;
	}

	@And("metasfresh contains M_HU_PI:")
	public void add_M_HU_PI(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			final String name = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_Name);
			final boolean active = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + COLUMNNAME_IsActive, true);

			final I_M_HU_PI huPiRecord = CoalesceUtil.coalesceSuppliers(() -> queryBL.createQueryBuilder(I_M_HU_PI.class)
																				.addEqualsFilter(COLUMNNAME_Name, name)
																				.addEqualsFilter(COLUMNNAME_IsActive, active)
																				.create()
																				.firstOnlyOrNull(I_M_HU_PI.class),
																		() -> InterfaceWrapperHelper.newInstanceOutOfTrx(I_M_HU_PI.class));

			assertThat(huPiRecord).isNotNull();

			huPiRecord.setName(name);
			huPiRecord.setIsActive(active);

			saveRecord(huPiRecord);

			final String huPiIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_HU_PI_ID + "." + TABLECOLUMN_IDENTIFIER);
			huPiTable.putOrReplace(huPiIdentifier, huPiRecord);
		}
	}

	@And("load M_HU_PI:")
	public void load_M_HU_PI(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			final String huPiIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_HU_PI_ID + "." + TABLECOLUMN_IDENTIFIER);
			final int huPiId = DataTableUtil.extractIntForColumnName(row, COLUMNNAME_M_HU_PI_ID);

			final I_M_HU_PI huPiRecord = InterfaceWrapperHelper.load(huPiId, I_M_HU_PI.class);

			huPiTable.putOrReplace(huPiIdentifier, huPiRecord);
		}
	}
}
