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
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.resource.S_Resource_StepDefData;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_S_Resource;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_AD_Workflow.COLUMNNAME_AD_WF_Node_ID;

public class AD_WF_Node_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final AD_WF_Node_StepDefData workflowNodeTable;
	private final AD_Workflow_StepDefData workflowTable;
	private final S_Resource_StepDefData resourceTable;

	public AD_WF_Node_StepDef(
			@NonNull final AD_WF_Node_StepDefData workflowNodeTable,
			@NonNull final AD_Workflow_StepDefData workflowTable,
			@NonNull final S_Resource_StepDefData resourceTable)
	{
		this.workflowNodeTable = workflowNodeTable;
		this.workflowTable = workflowTable;
		this.resourceTable = resourceTable;
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

	@And("create AD_WF_Node:")
	public void create_AD_WF_Node(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			final String name = DataTableUtil.extractStringForColumnName(row, I_AD_WF_Node.COLUMNNAME_Name);
			final String value = DataTableUtil.extractStringForColumnName(row, I_AD_WF_Node.COLUMNNAME_Value);
			final String description = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_AD_WF_Node.COLUMNNAME_Description);
			final int duration = DataTableUtil.extractIntForColumnName(row, I_AD_WF_Node.COLUMNNAME_Duration);

			final String workflowIdentifier = DataTableUtil.extractStringForColumnName(row, I_AD_WF_Node.COLUMNNAME_AD_Workflow_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_AD_Workflow workflow = workflowTable.get(workflowIdentifier);

			final String resourceIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_AD_WF_Node.COLUMNNAME_S_Resource_ID + "." + TABLECOLUMN_IDENTIFIER);
			final Integer resourceID = Optional.ofNullable(resourceIdentifier)
					.map(resourceTable::get)
					.map(I_S_Resource::getS_Resource_ID)
					.orElse(-1);

			final I_AD_WF_Node wfNode = InterfaceWrapperHelper.newInstance(I_AD_WF_Node.class);
			wfNode.setName(name);
			wfNode.setDescription(description);
			wfNode.setValue(value);
			wfNode.setDuration(duration);
			wfNode.setAD_Workflow_ID(workflow.getAD_Workflow_ID());
			wfNode.setS_Resource_ID(resourceID);

			InterfaceWrapperHelper.saveRecord(wfNode);

			final String wfNodeIdentifier = DataTableUtil.extractStringForColumnName(row, I_AD_WF_Node.COLUMNNAME_AD_WF_Node_ID + "." + TABLECOLUMN_IDENTIFIER);
			workflowNodeTable.put(wfNodeIdentifier, wfNode);
		}
	}

	@And("^after not more than (.*)s, AD_WF_Node are found:$")
	public void validate_AD_WF_Node(final int timeoutSec, @NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			try
			{
				StepDefUtil.tryAndWait(timeoutSec, 1000, () -> load_AD_WF_Node(row));
			}
			catch (InterruptedException e)
			{
				throw new RuntimeException(e);
			}
		}
	}

	private boolean load_AD_WF_Node(@NonNull final Map<String, String> row)
	{
		final String name = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_AD_WF_Node.COLUMNNAME_Name);
		final String value = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_AD_WF_Node.COLUMNNAME_Value);
		final String description = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_AD_WF_Node.COLUMNNAME_Description);
		final int duration = DataTableUtil.extractIntForColumnName(row, I_AD_WF_Node.COLUMNNAME_Duration);

		final String workflowIdentifier = DataTableUtil.extractStringForColumnName(row, I_AD_WF_Node.COLUMNNAME_AD_Workflow_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_AD_Workflow workflow = workflowTable.get(workflowIdentifier);

		final String resourceIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_AD_WF_Node.COLUMNNAME_S_Resource_ID + "." + TABLECOLUMN_IDENTIFIER);
		final Integer resourceID = Optional.ofNullable(resourceIdentifier)
				.map(resourceTable::get)
				.map(I_S_Resource::getS_Resource_ID)
				.orElse(-1);

		final IQueryBuilder<I_AD_WF_Node> wfNodeQueryBuilder = queryBL.createQueryBuilder(I_AD_WF_Node.class)
				.addEqualsFilter(I_AD_WF_Node.COLUMNNAME_AD_Workflow_ID, workflow.getAD_Workflow_ID())
				.addEqualsFilter(I_AD_WF_Node.COLUMNNAME_Duration, duration);

		if (resourceID > 0)
		{
			wfNodeQueryBuilder.addEqualsFilter(I_AD_WF_Node.COLUMNNAME_S_Resource_ID, resourceID);
		}
		if (Check.isNotBlank(name))
		{
			wfNodeQueryBuilder.addEqualsFilter(I_AD_WF_Node.COLUMNNAME_Name, name);
		}
		if (Check.isNotBlank(value))
		{
			wfNodeQueryBuilder.addEqualsFilter(I_AD_WF_Node.COLUMNNAME_Value, value);
		}
		if (Check.isNotBlank(description))
		{
			wfNodeQueryBuilder.addEqualsFilter(I_AD_WF_Node.COLUMNNAME_Description, description);
		}
		final Optional<I_AD_WF_Node> wfNode = wfNodeQueryBuilder.create()
				.firstOnlyOptional(I_AD_WF_Node.class);

		if (!wfNode.isPresent())
		{
			return false;
		}

		final String wfNodeIdentifier = DataTableUtil.extractStringForColumnName(row, I_AD_WF_Node.COLUMNNAME_AD_WF_Node_ID + "." + TABLECOLUMN_IDENTIFIER);
		workflowNodeTable.putOrReplace(wfNodeIdentifier, wfNode.get());

		return true;
	}
}
