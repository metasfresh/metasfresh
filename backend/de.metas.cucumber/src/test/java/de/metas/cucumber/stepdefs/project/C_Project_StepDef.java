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

package de.metas.cucumber.stepdefs.project;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.ValueAndName;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_Project;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class C_Project_StepDef
{
	public static final int DEFAULT_CURRENCY_ID = 102;
	@NonNull private final C_Project_StepDefData projectTable;
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Create C_Project records with minimal required fields.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns <b>Identifier</b> — (required) alias to store the project under<br>
	 * <b>Name</b> — (required) project name<br>
	 * <b>Value</b> — (optional) project value/search key, defaults to Name if not specified<br>
	 * @cucumber.example <pre>
	 * Given metasfresh contains C_Projects:
	 *   | Identifier | Name         | Value        |
	 *   | project_1  | TestProject1 | PROJ_001     |
	 * </pre>
	 */
	@Given("metasfresh contains C_Projects:")
	public void metasfresh_contains_c_projects(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_C_Project.COLUMNNAME_C_Project_ID)
				.forEach(this::createProject);
	}

	private void createProject(@NonNull final DataTableRow row)
	{
		final ValueAndName valueAndName = row.suggestValueAndName();
		final I_C_Project project = CoalesceUtil.coalesceSuppliersNotNull(
				() -> queryBL.createQueryBuilder(I_C_Project.class)
						.addOnlyActiveRecordsFilter()
						.addEqualsFilter(I_C_Project.COLUMNNAME_Value, valueAndName.getValue())
						.create()
						.firstOnly(I_C_Project.class),
				() -> newInstance(I_C_Project.class));

		project.setName(valueAndName.getName());
		project.setValue(valueAndName.getValue());

		project.setC_Currency_ID(row.getAsOptionalInt(I_C_Project.COLUMNNAME_C_Currency_ID)
				.orElse(DEFAULT_CURRENCY_ID));

		saveRecord(project);

		row.getAsIdentifier().put(projectTable, project);
	}

	/**
	 * Reloads the given C_Project records and asserts their values.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: C_Project_StepDefData
	 * @cucumber.columns <b>Identifier</b> — (required) the project's alias<br>
	 * <b>InvoicedAmt</b> — (optional) the amount invoiced against the project (summed up when an invoice of the project is completed)<br>
	 * @cucumber.example <pre>
	 * Then validate C_Projects:
	 *   | Identifier | InvoicedAmt |
	 *   | project    | 1190        |
	 * </pre>
	 */
	@And("validate C_Projects:")
	public void validate_c_projects(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::validateProject);
	}

	private void validateProject(@NonNull final DataTableRow row)
	{
		final I_C_Project project = load(row.getAsIdentifier().lookupNotNullIdIn(projectTable), I_C_Project.class);

		row.getAsOptionalBigDecimal(I_C_Project.COLUMNNAME_InvoicedAmt)
				.ifPresent(expected -> assertThat(project.getInvoicedAmt()).as("InvoicedAmt").isEqualByComparingTo(expected));
	}
}
