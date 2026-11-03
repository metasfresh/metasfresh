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

package de.metas.cucumber.stepdefs.order;

import com.google.common.collect.ImmutableList;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.contract.C_Flatrate_Term_StepDefData;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Order_CompensationGroup;

import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for creating and validating {@link I_C_Order_CompensationGroup} records.
 * Compensation groups link order lines that belong to the same "Mischkarton" (mixed carton).
 * The DESADV export uses these groups to merge sub-article pack items into the main article's packing.
 */
@RequiredArgsConstructor
public class C_Order_CompensationGroup_StepDef
{
	private final @NonNull C_Order_CompensationGroup_StepDefData compGroupTable;
	private final @NonNull C_Order_StepDefData orderTable;
	private final @NonNull C_Flatrate_Term_StepDefData contractTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Creates {@link I_C_Order_CompensationGroup} records.
	 * <p>
	 * DataTable columns:
	 * <ul>
	 *     <li>{@code Identifier} (required) — identifier for later reference</li>
	 *     <li>{@code C_Order_ID} (required) — identifier of the parent order</li>
	 *     <li>{@code Name} (required) — name of the compensation group (e.g., "Mischkarton")</li>
	 *     <li>{@code OPT.C_Flatrate_Term_ID.Identifier} (optional) — the contract term that triggered this
	 *         compensation group (set when a contract, not the "Mischkarton" quick-input, created the group)</li>
	 * </ul>
	 * <p>
	 * Example usage in feature file:
	 * <pre>
	 * Given metasfresh contains C_Order_CompensationGroups:
	 *   | Identifier | C_Order_ID | Name        |
	 *   | cg_1       | o_1        | Mischkarton |
	 * </pre>
	 */
	@Given("metasfresh contains C_Order_CompensationGroups:")
	public void createCompensationGroups(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_Order orderRecord = row.getAsIdentifier(I_C_Order_CompensationGroup.COLUMNNAME_C_Order_ID)
					.lookupNotNullIn(orderTable);

			final I_C_Order_CompensationGroup record = newInstance(I_C_Order_CompensationGroup.class);
			record.setAD_Org_ID(orderRecord.getAD_Org_ID());
			record.setC_Order_ID(orderRecord.getC_Order_ID());
			record.setName(row.getAsString(I_C_Order_CompensationGroup.COLUMNNAME_Name));

			row.getAsOptionalIdentifier(I_C_Order_CompensationGroup.COLUMNNAME_C_Flatrate_Term_ID)
					.map(identifier -> identifier.lookupNotNullIn(contractTable))
					.map(I_C_Flatrate_Term::getC_Flatrate_Term_ID)
					.ifPresent(record::setC_Flatrate_Term_ID);

			saveRecord(record);

			compGroupTable.putOrReplace(row.getAsIdentifier(), record);
		});
	}

	/**
	 * Asserts that the given order has no active {@link I_C_Order_CompensationGroup} at all.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: C_Order_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then no C_Order_CompensationGroup exists for order "order_1"
	 * </pre>
	 */
	@Then("no C_Order_CompensationGroup exists for order {string}")
	public void noCompensationGroupExistsForOrder(@NonNull final String orderIdentifier)
	{
		final I_C_Order orderRecord = orderTable.get(orderIdentifier);
		assertThat(orderRecord).as("Missing C_Order with identifier %s", orderIdentifier).isNotNull();

		final boolean exists = queryBL.createQueryBuilder(I_C_Order_CompensationGroup.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Order_CompensationGroup.COLUMNNAME_C_Order_ID, orderRecord.getC_Order_ID())
				.create()
				.anyMatch();

		assertThat(exists).as("C_Order_CompensationGroup exists for order %s", orderIdentifier).isFalse();
	}

	/**
	 * Asserts the exact set of active {@link I_C_Order_CompensationGroup}s of the given order: one row per group, matched by {@code Name}.
	 * Each found group is registered under the row's {@code Identifier}, so later steps can refer to a group the system created
	 * (e.g. one cloned when the order was copied).
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias under which the found group is registered<br>
	 *   <b>Name</b> — (required) the group's name; must be unique within the order<br>
	 *   <b>C_Flatrate_Term_ID</b> — (optional, identifier-ref, null-allowed) the contract term that created the group; {@code null} asserts a group not created by a contract<br>
	 * @cucumber.depends StepDefData: C_Order_StepDefData, C_Flatrate_Term_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then the C_Order_CompensationGroups of order "order_1" are:
	 *   | Identifier | Name        | C_Flatrate_Term_ID |
	 *   | cg_1       | Mischkarton | null               |
	 * </pre>
	 */
	@Then("the C_Order_CompensationGroups of order {string} are:")
	public void validateCompensationGroupsOfOrder(@NonNull final String orderIdentifier, @NonNull final DataTable dataTable)
	{
		final I_C_Order orderRecord = orderTable.get(orderIdentifier);
		assertThat(orderRecord).as("Missing C_Order with identifier %s", orderIdentifier).isNotNull();

		final List<I_C_Order_CompensationGroup> groups = queryBL.createQueryBuilder(I_C_Order_CompensationGroup.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Order_CompensationGroup.COLUMNNAME_C_Order_ID, orderRecord.getC_Order_ID())
				.create()
				.list();

		final DataTableRows rows = DataTableRows.of(dataTable);
		assertThat(groups)
				.as("C_Order_CompensationGroups of order %s", orderIdentifier)
				.extracting(I_C_Order_CompensationGroup::getName)
				.containsExactlyInAnyOrderElementsOf(rows.stream().map(row -> row.getAsString(I_C_Order_CompensationGroup.COLUMNNAME_Name)).collect(ImmutableList.toImmutableList()));

		rows.forEach(row -> {
			final String name = row.getAsString(I_C_Order_CompensationGroup.COLUMNNAME_Name);
			final I_C_Order_CompensationGroup group = groups.stream()
					.filter(candidate -> name.equals(candidate.getName()))
					.collect(GuavaCollectors.singleElementOrThrow(() -> new AdempiereException("Expected exactly one group named " + name + " in order " + orderIdentifier)));

			row.getAsOptionalIdentifier(I_C_Order_CompensationGroup.COLUMNNAME_C_Flatrate_Term_ID)
					.ifPresent(termIdentifier -> assertThat(group.getC_Flatrate_Term_ID())
							.as("C_Flatrate_Term_ID of group %s of order %s", name, orderIdentifier)
							.isEqualTo(termIdentifier.isNullPlaceholder() ? 0 : termIdentifier.lookupNotNullIn(contractTable).getC_Flatrate_Term_ID()));

			compGroupTable.putOrReplace(row.getAsIdentifier(), group);
		});
	}
}
