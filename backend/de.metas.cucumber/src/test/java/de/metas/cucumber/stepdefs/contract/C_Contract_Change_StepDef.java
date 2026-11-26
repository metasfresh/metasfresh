/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.cucumber.stepdefs.contract;

import de.metas.contracts.model.I_C_Contract_Change;
import de.metas.contracts.model.I_C_Flatrate_Transition;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefConstants;
import io.cucumber.datatable.DataTable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import io.cucumber.java.en.Given;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Creates {@link I_C_Contract_Change} records — the allowed changes (cancel/status-change, with their deadline)
 * a {@link I_C_Flatrate_Transition} permits during the running term.
 */
@RequiredArgsConstructor
public class C_Contract_Change_StepDef
{
	private final @NonNull C_Contract_Change_StepDefData contractChangeTable;
	private final @NonNull C_Flatrate_Transition_StepDefData transitionTable;

	/**
	 * Creates {@link I_C_Contract_Change} records.
	 * <p>
	 * DataTable columns:
	 * <ul>
	 *     <li>{@code Identifier} (required) — identifier for later reference</li>
	 *     <li>{@code C_Flatrate_Transition_ID} (required, identifier-ref) — the transition this change applies to</li>
	 *     <li>{@code Action} (required) — the change action code (e.g. {@code ST}/{@code SU})</li>
	 *     <li>{@code ContractStatus} (required) — the contract status this record grants (e.g. {@code Qu})</li>
	 *     <li>{@code DeadLine} (required) — the deadline value, before contract end, up to which the change is allowed</li>
	 *     <li>{@code DeadLineUnit} (required) — {@code day}/{@code week}/{@code month}/{@code year}</li>
	 * </ul>
	 * <pre>
	 * And metasfresh contains C_Contract_Change:
	 *   | Identifier | C_Flatrate_Transition_ID.Identifier | Action | ContractStatus | DeadLine | DeadLineUnit |
	 *   | change_1   | transition_zeroDur                  | SU     | Qu             | 1        | day          |
	 * </pre>
	 */
	@Given("metasfresh contains C_Contract_Change:")
	public void createContractChanges(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_Flatrate_Transition transition = row.getAsIdentifier(I_C_Contract_Change.COLUMNNAME_C_Flatrate_Transition_ID)
					.lookupNotNullIn(transitionTable);

			final I_C_Contract_Change record = newInstance(I_C_Contract_Change.class);
			record.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
			record.setC_Flatrate_Transition_ID(transition.getC_Flatrate_Transition_ID());
			record.setAction(row.getAsString(I_C_Contract_Change.COLUMNNAME_Action));
			record.setContractStatus(row.getAsString(I_C_Contract_Change.COLUMNNAME_ContractStatus));
			record.setDeadLine(row.getAsInt(I_C_Contract_Change.COLUMNNAME_DeadLine));
			record.setDeadLineUnit(row.getAsString(I_C_Contract_Change.COLUMNNAME_DeadLineUnit));

			saveRecord(record);

			contractChangeTable.putOrReplace(row.getAsIdentifier(), record);
		});
	}
}
