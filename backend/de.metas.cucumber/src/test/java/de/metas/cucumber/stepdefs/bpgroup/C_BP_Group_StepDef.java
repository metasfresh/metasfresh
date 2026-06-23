/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.cucumber.stepdefs.bpgroup;

import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.ValueAndName;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BP_Group;

/**
 * Responsible for creating {@code C_BP_Group} (business-partner group) records.
 */
@RequiredArgsConstructor
public class C_BP_Group_StepDef
{
	@NonNull private final C_BP_Group_StepDefData bpGroupTable;

	/**
	 * @cucumber.stepdef Creates {@code C_BP_Group} records, optionally nested under a parent group.
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias for cross-step reference<br>
	 *   <b>Name</b> — (optional) group name; auto-generated when omitted<br>
	 *   <b>Parent_BP_Group_ID</b> — (optional, identifier-ref) parent business-partner group<br>
	 * @cucumber.depends StepDefData: C_BP_Group_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains C_BP_Groups:
	 *   | Identifier     |
	 *   | groupPreferred |
	 *   | groupOther     |
	 * </pre>
	 */
	@Given("metasfresh contains C_BP_Groups:")
	public void createBPGroups(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_C_BP_Group.COLUMNNAME_C_BP_Group_ID)
				.forEach(row -> {
					final ValueAndName valueAndName = row.suggestValueAndName();

					final I_C_BP_Group bpGroupRecord = InterfaceWrapperHelper.newInstance(I_C_BP_Group.class);
					bpGroupRecord.setIsActive(true);
					bpGroupRecord.setValue(valueAndName.getValue());
					bpGroupRecord.setName(valueAndName.getName());

					row.getAsOptionalIdentifier(I_C_BP_Group.COLUMNNAME_Parent_BP_Group_ID)
							.map(bpGroupTable::getId)
							.ifPresent(parentId -> bpGroupRecord.setParent_BP_Group_ID(parentId.getRepoId()));

					InterfaceWrapperHelper.saveRecord(bpGroupRecord);

					row.getAsOptionalIdentifier().ifPresent(identifier -> bpGroupTable.putOrReplace(identifier, bpGroupRecord));
				});
	}
}
