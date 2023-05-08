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

package de.metas.cucumber.stepdefs.projectType;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.project.ProjectCategory;
import de.metas.project.ProjectTypeId;
import de.metas.project.ProjectTypeRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_ProjectType;
import org.compiere.util.Env;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;

public class C_ProjectType_StepDef
{
	private final ProjectTypeRepository projectTypeRepository = SpringContextHolder.instance.getBean(ProjectTypeRepository.class);

	private final C_ProjectType_StepDefData projectTypeTable;

	public C_ProjectType_StepDef(@NonNull final C_ProjectType_StepDefData projectTypeTable)
	{
		this.projectTypeTable = projectTypeTable;
	}

	@And("load C_ProjectType:")
	public void load_C_ProjectType(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			loadProjectType(row);
		}
	}

	private void loadProjectType(@NonNull final Map<String, String> row)
	{
		final String projectCategoryStr = DataTableUtil.extractStringForColumnName(row, I_C_ProjectType.COLUMNNAME_ProjectCategory);

		final ProjectCategory projectCategory = ProjectCategory.ofCode(projectCategoryStr);

		final ProjectTypeId projectTypeId = projectTypeRepository.getFirstIdByProjectCategoryAndOrg(projectCategory, Env.getOrgId());

		final I_C_ProjectType projectTypeRecord = InterfaceWrapperHelper.load(projectTypeId, I_C_ProjectType.class);

		assertThat(projectTypeRecord).isNotNull();

		final String identifier = DataTableUtil.extractStringForColumnName(row, I_C_ProjectType.COLUMNNAME_C_ProjectType_ID + "." + TABLECOLUMN_IDENTIFIER);
		projectTypeTable.putOrReplace(identifier, projectTypeRecord);
	}
}
