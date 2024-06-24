/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.cucumber.stepdefs.externalsystem;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.externalsystem.model.I_LeichMehl_PluFile_ConfigGroup;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class LeichMehl_PluFile_ConfigGroup_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final LeichMehl_PluFile_ConfigGroup_StepDefData leichMehlPluFileConfigGroupTable;

	public LeichMehl_PluFile_ConfigGroup_StepDef(
		@NonNull final LeichMehl_PluFile_ConfigGroup_StepDefData leichMehlPluFileConfigGroupTable
	)
	{
		this.leichMehlPluFileConfigGroupTable = leichMehlPluFileConfigGroupTable;
	}

	@And("metasfresh contains LeichMehl_PluFile_ConfigGroup:")
	public void add_LeichMehl_PluFile_ConfigGroup(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String leichMehlPluFileConfigGroupIdentifier = DataTableUtil.extractStringForColumnName(row, I_LeichMehl_PluFile_ConfigGroup.COLUMNNAME_LeichMehl_PluFile_ConfigGroup_ID
					+ "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

			final String name = DataTableUtil.extractStringForColumnName(row, I_LeichMehl_PluFile_ConfigGroup.COLUMNNAME_Name);

			final I_LeichMehl_PluFile_ConfigGroup pluFileConfigGroup = CoalesceUtil
					.coalesceSuppliers(() -> queryBL.createQueryBuilder(I_LeichMehl_PluFile_ConfigGroup.class)
											   .addEqualsFilter(I_LeichMehl_PluFile_ConfigGroup.COLUMNNAME_Name, name)
											   .create()
											   .firstOnly(I_LeichMehl_PluFile_ConfigGroup.class),
									   () -> InterfaceWrapperHelper.newInstance(I_LeichMehl_PluFile_ConfigGroup.class));

			assertThat(pluFileConfigGroup).isNotNull();

			pluFileConfigGroup.setName(name);

			InterfaceWrapperHelper.saveRecord(pluFileConfigGroup);
			leichMehlPluFileConfigGroupTable.putOrReplace(leichMehlPluFileConfigGroupIdentifier, pluFileConfigGroup);

		}
	}
}
