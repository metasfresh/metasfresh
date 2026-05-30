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

	/** Last exception stashed by a failure-catching {@code @When} step; asserted+cleared by {@link #assertOperationFailedWithMessage(String)}. */
	@Nullable
	private AdempiereException lastException;

	/**
	 * Create {@link I_C_TaxDeclaration} records.
	 * Columns: {@code Identifier}, {@code C_AcctSchema_ID}, {@code Date} (any date in the target period), optional {@code Description}.
	 */
	@Given("metasfresh contains C_TaxDeclaration:")
	public void metasfresh_contains_c_tax_declaration(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createTaxDeclaration);
	}

	/**
	 * Neutralise every active {@link I_C_TaxDeclaration} to give each scenario a clean slate without a full DB reset.
	 * The five fields are set together to satisfy interlocking constraints: {@code Processed='N'} unlocks the row and
	 * drops it from the build engine's exclusion; {@code IsCorrection='N'}+{@code Original_ID=null} satisfy the
	 * star-topology CHECK; {@code DocStatus='VO'}+{@code IsActive='N'} remove it from the unique index and overlap guards.
	 */
	@Given("Clear previous Tax Declaration documents")
	public void clear_previous_tax_declarations()
	{
		final List<I_C_TaxDeclaration> existing = queryBL.createQueryBuilder(I_C_TaxDeclaration.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();
		for (final I_C_TaxDeclaration decl : existing)
		{
			decl.setProcessed(false);
			decl.setIsCorrection(false);
			decl.setC_TaxDeclaration_Original_ID(-1);
			decl.setDocStatus(IDocument.STATUS_Voided);
			decl.setIsActive(false);
			InterfaceWrapperHelper.saveRecord(decl);
		}
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

	/** Resolve the {@link I_C_AcctSchema} from the step-def table by identifier, falling back to the first active AcctSchema in the DB. */
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

	/** Trigger {@link TaxDeclarationService#build(TaxDeclarationId)} for the declaration registered under {@code identifier}. */
	@When("the tax declaration {string} is built")
	public void the_tax_declaration_is_built(@NonNull final String identifier)
	{
		final I_C_TaxDeclaration decl = taxDeclarationTable.get(StepDefDataIdentifier.ofString(identifier));
		taxDeclarationService.build(TaxDeclarationId.ofRepoId(decl.getC_TaxDeclaration_ID()));
	}

	/** Complete the declaration via the document engine; any rejection is stashed for {@link #assertOperationFailedWithMessage(String)}. */
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

	/** Reactivate the declaration via the document engine. */
	@When("the tax declaration {string} is reactivated")
	public void reactivate(@NonNull final String identifier)
	{
		lastException = null;
		final I_C_TaxDeclaration decl = taxDeclarationTable.get(StepDefDataIdentifier.ofString(identifier));
		documentBL.processEx(decl, IDocument.ACTION_ReActivate, IDocument.STATUS_InProgress);
		InterfaceWrapperHelper.refresh(decl);
	}

	/** Reactivate the declaration expecting rejection; the resulting exception is stashed for {@link #assertOperationFailedWithMessage(String)}. */
	@When("the tax declaration {string} is reactivated expecting failure")
	public void reactivateExpectingFailure(@NonNull final String identifier)
	{
		lastException = null;
		final I_C_TaxDeclaration decl = taxDeclarationTable.get(StepDefDataIdentifier.ofString(identifier));
		try
		{
			documentBL.processEx(decl, IDocument.ACTION_ReActivate, IDocument.STATUS_InProgress);
			InterfaceWrapperHelper.refresh(decl);
		}
		catch (final AdempiereException e)
		{
			lastException = e;
		}
	}

	/** Assert the Correction inherited {@code C_Period_ID}, {@code DateAcct} and {@code C_AcctSchema_ID} from its Original and links back to it. */
	@Then("the tax declaration {string} is a Correction inheriting Period, DateAcct and AcctSchema from {string}")
	public void assertCorrectionInherits(
			@NonNull final String correctionIdentifier,
			@NonNull final String originalIdentifier)
	{
		final I_C_TaxDeclaration correctionRef = taxDeclarationTable.get(StepDefDataIdentifier.ofString(correctionIdentifier));
		final I_C_TaxDeclaration originalRef = taxDeclarationTable.get(StepDefDataIdentifier.ofString(originalIdentifier));
		final I_C_TaxDeclaration correction = InterfaceWrapperHelper.load(correctionRef.getC_TaxDeclaration_ID(), I_C_TaxDeclaration.class);
		final I_C_TaxDeclaration original = InterfaceWrapperHelper.load(originalRef.getC_TaxDeclaration_ID(), I_C_TaxDeclaration.class);

		assertThat(correction.isCorrection()).as("IsCorrection").isTrue();
		assertThat(correction.getC_TaxDeclaration_Original_ID()).as("C_TaxDeclaration_Original_ID").isEqualTo(original.getC_TaxDeclaration_ID());
		assertThat(correction.getC_Period_ID()).as("C_Period_ID inherited from Original").isEqualTo(original.getC_Period_ID());
		assertThat(correction.getDateAcct()).as("DateAcct inherited from Original").isEqualTo(original.getDateAcct());
		assertThat(correction.getC_AcctSchema_ID()).as("C_AcctSchema_ID inherited from Original").isEqualTo(original.getC_AcctSchema_ID());
	}

	/** Assert the declaration's {@code Processed}, {@code DocStatus} and {@code DocAction} match the expected values. */
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

	/** Assert the last failure-catching step stashed an exception with the given error code (the {@code AD_Message.ErrorCode}, not {@code Value}); clears it afterwards. */
	@Then("the tax declaration completion fails with message {string}")
	@Then("the tax declaration operation fails with message {string}")
	public void assertOperationFailedWithMessage(@NonNull final String expectedErrorCode)
	{
		assertLastExceptionHasErrorCode(expectedErrorCode);
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

	/** Assert the declaration's {@link I_C_TaxDeclarationLine} rows still exist (e.g. reactivation did not purge them). */
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

	/**
	 * Invoke {@link TaxDeclarationService#createCorrection(TaxDeclarationId)} on the original and register the result under {@code <originalIdentifier>_correction}.
	 * Any failure is stashed for {@link #assertOperationFailedWithMessage(String)}.
	 */
	@When("invoke Create Correction on C_TaxDeclaration {string}")
	public void invokeCreateCorrection(@NonNull final String originalIdentifier)
	{
		lastException = null;
		final I_C_TaxDeclaration original = taxDeclarationTable.get(StepDefDataIdentifier.ofString(originalIdentifier));
		try
		{
			final TaxDeclarationId correctionId = taxDeclarationService.createCorrection(TaxDeclarationId.ofRepoId(original.getC_TaxDeclaration_ID()));
			final I_C_TaxDeclaration correction = InterfaceWrapperHelper.load(correctionId.getRepoId(), I_C_TaxDeclaration.class);
			taxDeclarationTable.putOrReplace(StepDefDataIdentifier.ofString(originalIdentifier + "_correction"), correction);
		}
		catch (final AdempiereException e)
		{
			lastException = e;
		}
	}

	/**
	 * Invoke {@link TaxDeclarationService#createCorrectionWithDriftCheck(TaxDeclarationId)} on the chain member and register the result under {@code <identifier>_correction}.
	 * Any failure is stashed for {@link #assertOperationFailedWithMessage(String)}.
	 */
	@When("invoke Create Correction with drift check on C_TaxDeclaration {string}")
	public void invokeCreateCorrectionWithDriftCheck(@NonNull final String identifier)
	{
		lastException = null;
		final I_C_TaxDeclaration chainMember = taxDeclarationTable.get(StepDefDataIdentifier.ofString(identifier));
		try
		{
			final TaxDeclarationId correctionId = taxDeclarationService.createCorrectionWithDriftCheck(TaxDeclarationId.ofRepoId(chainMember.getC_TaxDeclaration_ID()));
			final I_C_TaxDeclaration correction = InterfaceWrapperHelper.load(correctionId.getRepoId(), I_C_TaxDeclaration.class);
			taxDeclarationTable.putOrReplace(StepDefDataIdentifier.ofString(identifier + "_correction"), correction);
		}
		catch (final AdempiereException e)
		{
			lastException = e;
		}
	}

	/** Assert the declaration's {@code IsCorrectionNeeded} flag ({@code Y}/{@code N}); reloads from DB to avoid stale-cache false-positives. */
	@Then("C_TaxDeclaration {string} has IsCorrectionNeeded = {string}")
	public void assertIsCorrectionNeeded(@NonNull final String identifier, @NonNull final String expectedFlag)
	{
		final I_C_TaxDeclaration decl = taxDeclarationTable.get(StepDefDataIdentifier.ofString(identifier));
		final I_C_TaxDeclaration reloaded = InterfaceWrapperHelper.load(decl.getC_TaxDeclaration_ID(), I_C_TaxDeclaration.class);
		final boolean expected = "Y".equalsIgnoreCase(expectedFlag);
		assertThat(reloaded.isCorrectionNeeded()).as("IsCorrectionNeeded").isEqualTo(expected);
	}

	/** Set the declaration's {@code IsCorrectionNeeded} flag ({@code Y}/{@code N}) and save; when {@code Y}, writes a {@code "test-drift"} reason. Used to simulate drift in tests. */
	@Given("C_TaxDeclaration {string} has IsCorrectionNeeded set to {string}")
	public void setIsCorrectionNeeded(@NonNull final String identifier, @NonNull final String flagValue)
	{
		final I_C_TaxDeclaration decl = taxDeclarationTable.get(StepDefDataIdentifier.ofString(identifier));
		final boolean value = "Y".equalsIgnoreCase(flagValue);
		decl.setIsCorrectionNeeded(value);
		InterfaceWrapperHelper.saveRecord(decl);
	}

	/**
	 * Run {@link TaxDeclarationService#checkDrift(TaxDeclarationId)} on the declaration registered under {@code identifier}
	 * and refresh the cached record so that subsequent assertions see the updated {@code IsCorrectionNeeded} value.
	 *
	 * <p>Required columns: {@code identifier} (string — the declaration's step-def identifier)
	 *
	 * <p>Example:
	 * <pre>{@code
	 * When the drift check process is run on tax declaration "tdD7"
	 * }</pre>
	 */
	@When("the drift check process is run on tax declaration {string}")
	public void runDriftCheck(@NonNull final String identifier)
	{
		final I_C_TaxDeclaration record = taxDeclarationTable.get(StepDefDataIdentifier.ofString(identifier));
		taxDeclarationService.checkDrift(TaxDeclarationId.ofRepoId(record.getC_TaxDeclaration_ID()));
		InterfaceWrapperHelper.refresh(record);
	}
}
