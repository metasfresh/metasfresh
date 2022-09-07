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

package de.metas.cucumber.stepdefs.workflow;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_AD_Workflow;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.compiere.model.I_AD_Workflow.COLUMNNAME_AD_Workflow_ID;
import static org.compiere.model.I_AD_Workflow.COLUMNNAME_Name;

public class AD_Workflow_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final AD_Workflow_StepDefData workflowTable;

	public AD_Workflow_StepDef(@NonNull final AD_Workflow_StepDefData workflowTable)
	{
		this.workflowTable = workflowTable;
	}

	@And("load AD_Workflow:")
	public void load_AD_Workflow(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			final String workflowName = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_Name);

			final I_AD_Workflow workflow = queryBL.createQueryBuilder(I_AD_Workflow.class)
					.addEqualsFilter(COLUMNNAME_Name, workflowName)
					.create()
					.firstOnlyNotNull(I_AD_Workflow.class);

			final String workflowIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_AD_Workflow_ID + "." + TABLECOLUMN_IDENTIFIER);
			workflowTable.put(workflowIdentifier, workflow);
		}
	}

}
