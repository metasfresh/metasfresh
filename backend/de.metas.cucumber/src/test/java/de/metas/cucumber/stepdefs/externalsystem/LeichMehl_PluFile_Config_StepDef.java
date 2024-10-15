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
import de.metas.externalsystem.leichmehl.LeichMehlPluFileConfigGroupId;
import de.metas.externalsystem.leichmehl.ReplacementSource;
import de.metas.externalsystem.model.I_LeichMehl_PluFile_Config;
import de.metas.externalsystem.model.I_LeichMehl_PluFile_ConfigGroup;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class LeichMehl_PluFile_Config_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final LeichMehl_PluFile_ConfigGroup_StepDefData leichMehlPluFileConfigGroupTable;
	private final LeichMehl_PluFile_Config_StepDefData leichMehlPluFileConfigTable;

	public LeichMehl_PluFile_Config_StepDef(
			@NonNull final LeichMehl_PluFile_ConfigGroup_StepDefData leichMehlPluFileConfigGroupTable,
			@NonNull final LeichMehl_PluFile_Config_StepDefData leichMehlPluFileConfigTable
	)
	{
		this.leichMehlPluFileConfigGroupTable = leichMehlPluFileConfigGroupTable;
		this.leichMehlPluFileConfigTable = leichMehlPluFileConfigTable;
	}

	@And("metasfresh contains LeichMehl_PluFile_Config:")
	public void add_LeichMehl_PluFile_Config(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String leichMehlPluFileConfigIdentifier = DataTableUtil.extractStringForColumnName(row, I_LeichMehl_PluFile_Config.COLUMNNAME_LeichMehl_PluFile_Config_ID
					+ "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

			final String leichMehlPluFileConfigGroupIdentifier = DataTableUtil.extractStringForColumnName(row, I_LeichMehl_PluFile_ConfigGroup.COLUMNNAME_LeichMehl_PluFile_ConfigGroup_ID
					+ "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final LeichMehlPluFileConfigGroupId leichMehlPluFileConfigGroupId = LeichMehlPluFileConfigGroupId.ofRepoId(leichMehlPluFileConfigGroupTable.get(leichMehlPluFileConfigGroupIdentifier).getLeichMehl_PluFile_ConfigGroup_ID());

			final String targetFieldName = DataTableUtil.extractStringForColumnName(row, I_LeichMehl_PluFile_Config.COLUMNNAME_TargetFieldName);

			final I_LeichMehl_PluFile_Config pluFileConfig = CoalesceUtil
					.coalesceSuppliers(() -> queryBL.createQueryBuilder(I_LeichMehl_PluFile_Config.class)
											   .addEqualsFilter(I_LeichMehl_PluFile_Config.COLUMNNAME_LeichMehl_PluFile_ConfigGroup_ID, leichMehlPluFileConfigGroupId)
											   .addEqualsFilter(I_LeichMehl_PluFile_Config.COLUMNNAME_TargetFieldName, targetFieldName)
											   .create()
											   .firstOnly(I_LeichMehl_PluFile_Config.class),
									   () -> InterfaceWrapperHelper.newInstance(I_LeichMehl_PluFile_Config.class));

			assertThat(pluFileConfig).isNotNull();

			pluFileConfig.setLeichMehl_PluFile_ConfigGroup_ID(leichMehlPluFileConfigGroupId.getRepoId());
			pluFileConfig.setTargetFieldName(targetFieldName);

			final String targetFieldType = DataTableUtil.extractStringForColumnName(row, I_LeichMehl_PluFile_Config.COLUMNNAME_TargetFieldType);
			pluFileConfig.setTargetFieldType(targetFieldType);

			final String replacement = DataTableUtil.extractStringForColumnName(row, I_LeichMehl_PluFile_Config.COLUMNNAME_Replacement);
			pluFileConfig.setReplacement(replacement);

			final String replacementRegexp = DataTableUtil.extractStringForColumnName(row, I_LeichMehl_PluFile_Config.COLUMNNAME_ReplaceRegExp);
			pluFileConfig.setReplaceRegExp(replacementRegexp);

			final String replacementSourceValue = DataTableUtil.extractStringForColumnName(row, I_LeichMehl_PluFile_Config.COLUMNNAME_ReplacementSource);
			final ReplacementSource replacementSource = ReplacementSource.valueOf(replacementSourceValue);
			pluFileConfig.setReplacementSource(replacementSource.getCode());

			InterfaceWrapperHelper.saveRecord(pluFileConfig);
			leichMehlPluFileConfigTable.putOrReplace(leichMehlPluFileConfigIdentifier, pluFileConfig);
		}
	}
}
