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

package de.metas.cucumber.stepdefs.tax_declaration;

import de.metas.acct.tax.TaxDeclarationId;
import de.metas.acct.tax.TaxDeclarationService;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.acctschema.C_AcctSchema_StepDefData;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_TaxDeclaration;
import org.compiere.util.TimeUtil;

import java.sql.Timestamp;
import java.time.LocalDate;

@RequiredArgsConstructor
public class C_TaxDeclaration_StepDef
{
	@NonNull private final C_TaxDeclaration_StepDefData taxDeclarationTable;
	@NonNull private final C_AcctSchema_StepDefData acctSchemaTable;
	@NonNull private final TaxDeclarationService taxDeclarationService = SpringContextHolder.instance.getBean(TaxDeclarationService.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Create {@link I_C_TaxDeclaration} records.
	 *
	 * <p><b>Required columns:</b>
	 * <ul>
	 *   <li>{@code Identifier} — step-def reference key</li>
	 *   <li>{@code C_AcctSchema_ID} — identifier of a {@link I_C_AcctSchema} in {@link C_AcctSchema_StepDefData}</li>
	 *   <li>{@code Date} — any date inside the period (format {@code yyyy-MM-dd}); the period
	 *       of the AcctSchema's calendar covering this date is selected and assigned to
	 *       {@code C_Period_ID}, plus the period's end date is stored in {@code DateAcct}</li>
	 * </ul>
	 *
	 * <p><b>Optional columns:</b>
	 * <ul>
	 *   <li>{@code Description}</li>
	 * </ul>
	 *
	 * <p><b>Example:</b>
	 * <pre>{@code
	 * Given metasfresh contains C_TaxDeclaration:
	 *   | Identifier | C_AcctSchema_ID | Date       |
	 *   | td1        | acctSchema      | 2024-01-15 |
	 * }</pre>
	 */
	@Given("metasfresh contains C_TaxDeclaration:")
	public void metasfresh_contains_c_tax_declaration(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createTaxDeclaration);
	}

	private void createTaxDeclaration(@NonNull final DataTableRow row)
	{
		final I_C_AcctSchema acctSchema = resolveAcctSchema(row);
		final Timestamp dateTs = TimeUtil.asTimestamp(LocalDate.parse(row.getAsString("Date")));
		final I_C_Period period = queryBL.createQueryBuilder(I_C_Period.class)
				.addOnlyActiveRecordsFilter()
				.addCompareFilter(I_C_Period.COLUMNNAME_StartDate, Operator.LESS_OR_EQUAL, dateTs)
				.addCompareFilter(I_C_Period.COLUMNNAME_EndDate, Operator.GREATER_OR_EQUAL, dateTs)
				.orderBy(I_C_Period.COLUMNNAME_StartDate)
				.create()
				.firstOnlyNotNull(I_C_Period.class);

		final I_C_TaxDeclaration decl = InterfaceWrapperHelper.newInstance(I_C_TaxDeclaration.class);
		decl.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
		decl.setC_AcctSchema_ID(acctSchema.getC_AcctSchema_ID());
		decl.setC_Period_ID(period.getC_Period_ID());
		decl.setDateAcct(TimeUtil.asTimestamp(period.getEndDate()));
		row.getAsOptionalString("Description").ifPresent(decl::setDescription);
		InterfaceWrapperHelper.saveRecord(decl);

		row.getAsOptionalIdentifier().ifPresent(id -> taxDeclarationTable.putOrReplace(id, decl));
	}

	/**
	 * Resolve the {@link I_C_AcctSchema} from the step-def data table (if a previously loaded record
	 * is registered under the identifier) or fall back to loading the first active AcctSchema from the DB
	 * (environment-agnostic, suitable for single-schema test environments).
	 */
	private I_C_AcctSchema resolveAcctSchema(@NonNull final DataTableRow row)
	{
		final StepDefDataIdentifier acctSchemaIdentifier = row.getAsIdentifier("C_AcctSchema_ID");
		return acctSchemaTable.getOptional(acctSchemaIdentifier)
				.orElseGet(() -> queryBL.createQueryBuilder(I_C_AcctSchema.class)
						.addOnlyActiveRecordsFilter()
						.orderBy(I_C_AcctSchema.COLUMNNAME_C_AcctSchema_ID)
						.create()
						.firstOnlyNotNull(I_C_AcctSchema.class));
	}

	/**
	 * Trigger the build process for an existing {@link I_C_TaxDeclaration} by calling
	 * {@link TaxDeclarationService#build(TaxDeclarationId)}.
	 *
	 * <p><b>Required:</b> {@code identifier} — refers to a record previously stored
	 * in {@link C_TaxDeclaration_StepDefData}.
	 *
	 * <p><b>Example:</b>
	 * <pre>{@code
	 * When the tax declaration 'td1' is built
	 * }</pre>
	 */
	@When("the tax declaration {string} is built")
	public void the_tax_declaration_is_built(@NonNull final String identifier)
	{
		final I_C_TaxDeclaration decl = taxDeclarationTable.get(StepDefDataIdentifier.ofString(identifier));
		taxDeclarationService.build(TaxDeclarationId.ofRepoId(decl.getC_TaxDeclaration_ID()));
	}
}
