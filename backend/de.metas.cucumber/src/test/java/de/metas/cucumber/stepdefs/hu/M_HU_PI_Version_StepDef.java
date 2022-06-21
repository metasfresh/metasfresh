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

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.handlingunits.model.I_M_HU_PI_Version.COLUMNNAME_M_HU_PI_Version_ID;
import static org.assertj.core.api.Assertions.*;

public class M_HU_PI_Version_StepDef
{
	private final M_HU_PI_Version_StepDefData huPiVersionTable;

	public M_HU_PI_Version_StepDef(
			@NonNull final M_HU_PI_Version_StepDefData huPiVersionTable)
	{
		this.huPiVersionTable = huPiVersionTable;
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
