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
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_TaxDeclaration;
import org.compiere.model.I_C_TaxDeclarationLine;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class C_TaxDeclaration_StepDef
{
	@NonNull private final C_TaxDeclaration_StepDefData taxDeclarationTable;
	@NonNull private final C_AcctSchema_StepDefData acctSchemaTable;
	@NonNull private final TaxDeclarationService taxDeclarationService = SpringContextHolder.instance.getBean(TaxDeclarationService.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IDocumentBL documentBL = Services.get(IDocumentBL.class);

	/**
	 * Last exception thrown by a {@code @When} step that explicitly catches and stashes errors.
	 * Asserted (and cleared) by {@link #assertCompletionFailedWithMessage(String)} or
	 * {@link #assertOperationFailedWithMessage(String)}.
	 */
	@Nullable
	private AdempiereException lastException;

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

	/**
	 * Complete an existing {@link I_C_TaxDeclaration} via the document engine.
	 *
	 * <p>On success the declaration transitions to {@code DocStatus='CO'}, {@code Processed='Y'},
	 * {@code DocAction='RA'}.  If the handler rejects the completion (e.g. no lines, period overlap)
	 * the resulting {@link AdempiereException} is stashed and can be asserted by
	 * {@link #assertCompletionFailedWithMessage(String)}.
	 *
	 * <p><b>Example:</b>
	 * <pre>{@code
	 * When the tax declaration "taxDecl" is completed
	 * }</pre>
	 */
	@When("the tax declaration {string} is completed")
	public void complete(@NonNull final String identifier)
	{
		lastException = null;
		final I_C_TaxDeclaration decl = taxDeclarationTable.get(StepDefDataIdentifier.ofString(identifier));
		try
		{
			documentBL.processEx(decl, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
			InterfaceWrapperHelper.refresh(decl);
		}
		catch (final AdempiereException e)
		{
			lastException = e;
		}
	}

	/**
	 * Reactivate an existing {@link I_C_TaxDeclaration} via the document engine.
	 *
	 * <p>On success the declaration transitions to {@code DocStatus='DR'}, {@code Processed='N'},
	 * {@code DocAction='CO'}.
	 *
	 * <p><b>Example:</b>
	 * <pre>{@code
	 * When the tax declaration "taxDecl" is reactivated
	 * }</pre>
	 */
	@When("the tax declaration {string} is reactivated")
	public void reactivate(@NonNull final String identifier)
	{
		lastException = null;
		final I_C_TaxDeclaration decl = taxDeclarationTable.get(StepDefDataIdentifier.ofString(identifier));
		documentBL.processEx(decl, IDocument.ACTION_ReActivate, IDocument.STATUS_Drafted);
		InterfaceWrapperHelper.refresh(decl);
	}

	/**
	 * Assert that a {@link I_C_TaxDeclaration} is in a specific state after complete / reactivate.
	 *
	 * <p><b>Required parameters:</b>
	 * <ul>
	 *   <li>{@code identifier} — record identifier in {@link C_TaxDeclaration_StepDefData}</li>
	 *   <li>{@code processed} — expected value of {@code Processed} column ({@code Y} or {@code N})</li>
	 *   <li>{@code docStatus} — expected {@code DocStatus} value (e.g. {@code CO}, {@code DR})</li>
	 *   <li>{@code docAction} — expected {@code DocAction} value (e.g. {@code RA}, {@code CO})</li>
	 * </ul>
	 *
	 * <p><b>Example:</b>
	 * <pre>{@code
	 * Then the tax declaration "taxDecl" has Processed='Y' and DocStatus='CO' and DocAction='RA'
	 * }</pre>
	 */
	@Then("the tax declaration {string} has Processed={string} and DocStatus={string} and DocAction={string}")
	public void assertHeaderState(
			@NonNull final String identifier,
			@NonNull final String processed,
			@NonNull final String docStatus,
			@NonNull final String docAction)
	{
		final I_C_TaxDeclaration decl = taxDeclarationTable.get(StepDefDataIdentifier.ofString(identifier));
		InterfaceWrapperHelper.refresh(decl);

		final boolean expectedProcessed = "Y".equalsIgnoreCase(processed);
		assertThat(decl.isProcessed()).as("Processed").isEqualTo(expectedProcessed);
		assertThat(decl.getDocStatus()).as("DocStatus").isEqualTo(docStatus);
		assertThat(decl.getDocAction()).as("DocAction").isEqualTo(docAction);
	}

	/**
	 * Assert that the most recent {@link #complete(String)} step failed with the given AD_Message key.
	 *
	 * <p>The stashed exception is cleared after the assertion so that subsequent steps
	 * can stash a new one independently.
	 *
	 * <p><b>Example:</b>
	 * <pre>{@code
	 * Then the tax declaration completion fails with message 'TaxDeclaration_NoLinesYet'
	 * }</pre>
	 */
	@Then("the tax declaration completion fails with message {string}")
	public void assertCompletionFailedWithMessage(@NonNull final String adMessageKey)
	{
		assertLastExceptionHasErrorCode(adMessageKey);
	}

	private void assertLastExceptionHasErrorCode(@NonNull final String expectedErrorCode)
	{
		final AdempiereException ex = lastException;
		lastException = null;

		assertThat(ex)
				.as("An AdempiereException with errorCode='%s' should have been thrown", expectedErrorCode)
				.isNotNull();
		assertThat(ex.getErrorCode())
				.as("ErrorCode of the exception")
				.isEqualTo(expectedErrorCode);
	}

	/**
	 * Assert that the {@link I_C_TaxDeclarationLine} rows belonging to the given declaration are
	 * still present in the database (i.e. reactivation did not purge them).
	 *
	 * <p><b>Example:</b>
	 * <pre>{@code
	 * And the C_TaxDeclarationLine rows for "taxDecl" are still present
	 * }</pre>
	 */
	@And("the C_TaxDeclarationLine rows for {string} are still present")
	public void assertLinesStillPresent(@NonNull final String identifier)
	{
		final I_C_TaxDeclaration decl = taxDeclarationTable.get(StepDefDataIdentifier.ofString(identifier));
		final List<I_C_TaxDeclarationLine> lines = queryBL.createQueryBuilder(I_C_TaxDeclarationLine.class)
				.addEqualsFilter(I_C_TaxDeclarationLine.COLUMNNAME_C_TaxDeclaration_ID, decl.getC_TaxDeclaration_ID())
				.create()
				.list(I_C_TaxDeclarationLine.class);
		assertThat(lines).as("C_TaxDeclarationLine rows for declaration %s must not be empty after reactivation", identifier)
				.isNotEmpty();
	}
}
