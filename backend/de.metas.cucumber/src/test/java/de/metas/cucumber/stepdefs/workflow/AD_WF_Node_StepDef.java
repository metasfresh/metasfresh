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
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_WF_Node;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_AD_Workflow.COLUMNNAME_AD_WF_Node_ID;

public class AD_WF_Node_StepDef
{
	private final AD_WF_Node_StepDefData workflowNodeTable;

	public AD_WF_Node_StepDef(@NonNull final AD_WF_Node_StepDefData workflowNodeTable)
	{
		this.workflowNodeTable = workflowNodeTable;
	}

	@And("load AD_WF_Node:")
	public void load_AD_WF_Node(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			final Integer id = DataTableUtil.extractIntegerOrNullForColumnName(row, COLUMNNAME_AD_WF_Node_ID);
			assertThat(id).isNotNull();

			final I_AD_WF_Node workflowNode = InterfaceWrapperHelper.load(id, I_AD_WF_Node.class);

			final String workflowNodeIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_AD_WF_Node_ID + "." + TABLECOLUMN_IDENTIFIER);
			workflowNodeTable.putOrReplace(workflowNodeIdentifier, workflowNode);
		}
	}
}
