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
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.handlingunits.model.I_M_HU_PI_Version.COLUMNNAME_HU_UnitType;
import static de.metas.handlingunits.model.I_M_HU_PI_Version.COLUMNNAME_IsActive;
import static de.metas.handlingunits.model.I_M_HU_PI_Version.COLUMNNAME_IsCurrent;
import static de.metas.handlingunits.model.I_M_HU_PI_Version.COLUMNNAME_M_HU_PI_ID;
import static de.metas.handlingunits.model.I_M_HU_PI_Version.COLUMNNAME_M_HU_PI_Version_ID;
import static de.metas.handlingunits.model.I_M_HU_PI_Version.COLUMNNAME_Name;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class M_HU_PI_Version_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final StepDefData<I_M_HU_PI> huPiTable;
	private final StepDefData<I_M_HU_PI_Version> huPiVersionTable;

	public M_HU_PI_Version_StepDef(
			@NonNull final StepDefData<I_M_HU_PI> huPiTable,
			@NonNull final StepDefData<I_M_HU_PI_Version> huPiVersionTable)
	{
		this.huPiTable = huPiTable;
		this.huPiVersionTable = huPiVersionTable;
	}

	@And("metasfresh contains M_HU_PI_Version:")
	public void add_M_HU_PI_Version(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			final String huPiIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_HU_PI_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_HU_PI huPi = huPiTable.get(huPiIdentifier);
			assertThat(huPi).isNotNull();

			final String name = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_Name);
			final String huUnitType = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_HU_UnitType); //dev-note: HU_UNITTYPE_AD_Reference_ID=540472;
			final boolean isCurrent = DataTableUtil.extractBooleanForColumnName(row, COLUMNNAME_IsCurrent);
			final boolean active = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + COLUMNNAME_IsActive, true);

			final I_M_HU_PI_Version existingPiVersion = queryBL.createQueryBuilder(I_M_HU_PI_Version.class)
					.addEqualsFilter(COLUMNNAME_M_HU_PI_ID, huPi.getM_HU_PI_ID())
					.addEqualsFilter(COLUMNNAME_Name, name)
					.addEqualsFilter(COLUMNNAME_HU_UnitType, huUnitType)
					.addEqualsFilter(COLUMNNAME_IsCurrent, isCurrent)
					.addEqualsFilter(COLUMNNAME_IsActive, active)
					.create()
					.firstOnlyOrNull(I_M_HU_PI_Version.class);

			final I_M_HU_PI_Version piVersion = CoalesceUtil.coalesceSuppliers(() -> existingPiVersion,
																			   () -> InterfaceWrapperHelper.newInstanceOutOfTrx(I_M_HU_PI_Version.class));

			assertThat(piVersion).isNotNull();

			piVersion.setM_HU_PI_ID(huPi.getM_HU_PI_ID());
			piVersion.setName(name);
			piVersion.setHU_UnitType(huUnitType);
			piVersion.setIsCurrent(isCurrent);
			piVersion.setIsActive(active);

			saveRecord(piVersion);

			final String huPIVersionIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_HU_PI_Version_ID + "." + TABLECOLUMN_IDENTIFIER);
			huPiVersionTable.put(huPIVersionIdentifier, piVersion);
		}
	}

	@And("load M_HU_PI_Version:")
	public void load_M_HU_PI_Version(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			final String huPIVersionIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_HU_PI_Version_ID + "." + TABLECOLUMN_IDENTIFIER);
			final int huPIVersionId = DataTableUtil.extractIntForColumnName(row, COLUMNNAME_M_HU_PI_Version_ID);

			final I_M_HU_PI_Version huPiVersionRecord = InterfaceWrapperHelper.load(huPIVersionId, I_M_HU_PI_Version.class);

			assertThat(huPiVersionRecord).isNotNull();

			huPiVersionTable.put(huPIVersionIdentifier, huPiVersionRecord);
		}
	}
}
