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

package de.metas.cucumber.stepdefs.gljournal;

import de.metas.acct.GLCategoryId;
import de.metas.acct.GLCategoryRepository;
import de.metas.acct.GLCategoryType;
import de.metas.acct.api.AccountDimension;
import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAccountDAO;
import de.metas.acct.api.IAcctSchemaBL;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.acct.process.GL_Journal_ChangePostingType;
import de.metas.acct.process.GL_Journal_GenerateYearEnding;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.accounting.C_ElementValue_StepDefData;
import de.metas.cucumber.stepdefs.accounting.PostingQueueChecks;
import de.metas.currency.ICurrencyDAO;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.organization.OrgId;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessInfo;
import de.metas.security.IRoleDAO;
import de.metas.security.Role;
import de.metas.security.RoleId;
import de.metas.user.UserId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.I_GL_JournalBatch;
import org.compiere.model.I_GL_JournalLine;
import org.compiere.model.X_GL_Journal;
import org.compiere.model.X_GL_JournalLine;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for classic GL journals ({@link I_GL_Journal} / {@link I_GL_JournalLine}, window "GL Journal"; not the SAP GL journal).
 *
 * <p>The header and the lines are saved directly, the journal is completed through the document engine
 * (which schedules its posting), and the "Change posting type" process ({@link GL_Journal_ChangePostingType}) is run as the WebUI role.
 *
 * <p><b>Gherkin usage example</b>:
 * <pre>{@code
 * And metasfresh contains GL_Journal:
 *   | Identifier | DateDoc    | PostingType |
 *   | gj         | 2024-01-10 | S           |
 * And metasfresh contains GL_JournalLines:
 *   | GL_Journal_ID | Account_DR_ID | Account_CR_ID | Amount |
 *   | gj            | gjDebit       | gjCredit      | 100.00 |
 * And the GL journal "gj" is completed
 * And the GL journal "gj" is eventually posted with PostingType "S" and 2 Fact_Acct lines
 * When the GL_Journal_ChangePostingType process is invoked for GL journal "gj" with PostingType "A"
 * Then the GL journal "gj" is eventually posted with PostingType "A" and 2 Fact_Acct lines
 * }</pre>
 */
@RequiredArgsConstructor
public class GL_Journal_StepDef
{
	@NonNull private final GL_Journal_StepDefData glJournalTable;
	@NonNull private final GL_JournalBatch_StepDefData glJournalBatchTable;
	@NonNull private final C_ElementValue_StepDefData elementValueTable;
	@NonNull private final PostingQueueChecks postingQueueChecks;

	private final GLCategoryRepository glCategoryRepository = SpringContextHolder.instance.getBean(GLCategoryRepository.class);
	private final IAcctSchemaBL acctSchemaBL = Services.get(IAcctSchemaBL.class);
	private final IAccountDAO accountDAO = Services.get(IAccountDAO.class);
	private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
	private final IRoleDAO roleDAO = Services.get(IRoleDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Creates GL journal batches in the standard org with the default GL journal document type.
	 * <b>Columns</b>: {@code Identifier} (required), {@code PostingType} (optional, default {@code A}).
	 */
	@Given("metasfresh contains GL_JournalBatch:")
	public void createGLJournalBatches(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final StepDefDataIdentifier identifier = row.getAsIdentifier();
			final DocTypeId docTypeId = docTypeDAO.getDocTypeId(DocTypeQuery.builder()
					.docBaseType(DocBaseType.GLJournal)
					.adClientId(StepDefConstants.CLIENT_ID.getRepoId())
					.adOrgId(StepDefConstants.ORG_ID.getRepoId())
					.build());

			final I_GL_JournalBatch record = InterfaceWrapperHelper.newInstance(I_GL_JournalBatch.class);
			record.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
			record.setC_DocType_ID(docTypeId.getRepoId());
			record.setDocumentNo("cucumber-" + identifier.getAsString() + "-" + System.nanoTime());
			record.setDescription("cucumber GL journal batch " + identifier.getAsString());
			record.setPostingType(row.getAsOptionalString(I_GL_JournalBatch.COLUMNNAME_PostingType).orElse(X_GL_Journal.POSTINGTYPE_Actual));
			record.setDocStatus(IDocument.STATUS_Drafted);
			record.setDocAction(IDocument.ACTION_Complete);
			InterfaceWrapperHelper.saveRecord(record);

			glJournalBatchTable.putOrReplace(identifier, record);
		});
	}

	/**
	 * Creates drafted GL journal headers (no batch) in the primary accounting schema, with the default GL journal document type.
	 * <b>Columns</b>: {@code Identifier} (required), {@code DateDoc} (required), {@code DateAcct} (optional, default DateDoc),
	 * {@code PostingType} (optional, default {@code A}), {@code GL_JournalBatch_ID} (optional, identifier of a batch created with {@code metasfresh contains GL_JournalBatch:}).
	 */
	@Given("metasfresh contains GL_Journal:")
	public void createGLJournals(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createGLJournal);
	}

	private void createGLJournal(@NonNull final DataTableRow row)
	{
		final StepDefDataIdentifier identifier = row.getAsIdentifier();
		final AcctSchema acctSchema = acctSchemaBL.getPrimaryAcctSchema(StepDefConstants.CLIENT_ID);
		final OrgId orgId = StepDefConstants.ORG_ID;

		final Timestamp dateDoc = row.getAsLocalDateTimestamp(I_GL_Journal.COLUMNNAME_DateDoc);
		final Timestamp dateAcct = row.getAsOptionalLocalDateTimestamp(I_GL_Journal.COLUMNNAME_DateAcct).orElse(dateDoc);

		final DocTypeId docTypeId = docTypeDAO.getDocTypeId(DocTypeQuery.builder()
				.docBaseType(DocBaseType.GLJournal)
				.adClientId(StepDefConstants.CLIENT_ID.getRepoId())
				.adOrgId(orgId.getRepoId())
				.build());
		final GLCategoryId glCategoryId = glCategoryRepository.getDefaultId(StepDefConstants.CLIENT_ID, GLCategoryType.Manual)
				.orElseThrow(() -> new AdempiereException("No default GL_Category found for " + StepDefConstants.CLIENT_ID));
		final CurrencyConversionTypeId conversionTypeId = currencyDAO.getDefaultConversionTypeId(StepDefConstants.CLIENT_ID, orgId, TimeUtil.asInstant(dateAcct));

		final I_GL_Journal record = InterfaceWrapperHelper.newInstance(I_GL_Journal.class);
		record.setAD_Org_ID(orgId.getRepoId());
		record.setC_DocType_ID(docTypeId.getRepoId());
		record.setC_AcctSchema_ID(acctSchema.getId().getRepoId());
		record.setPostingType(row.getAsOptionalString(I_GL_Journal.COLUMNNAME_PostingType).orElse(X_GL_Journal.POSTINGTYPE_Actual));
		record.setDateDoc(dateDoc);
		record.setDateAcct(dateAcct);
		record.setC_Currency_ID(acctSchema.getCurrencyId().getRepoId());
		record.setC_ConversionType_ID(conversionTypeId.getRepoId());
		record.setCurrencyRate(BigDecimal.ONE);
		record.setGL_Category_ID(glCategoryId.getRepoId());
		row.getAsOptionalIdentifier(I_GL_Journal.COLUMNNAME_GL_JournalBatch_ID)
				.map(batchIdentifier -> batchIdentifier.lookupNotNullIn(glJournalBatchTable))
				.ifPresent(batch -> record.setGL_JournalBatch_ID(batch.getGL_JournalBatch_ID()));
		record.setDescription("cucumber GL journal " + identifier.getAsString());
		record.setDocStatus(IDocument.STATUS_Drafted);
		record.setDocAction(IDocument.ACTION_Complete);
		InterfaceWrapperHelper.saveRecord(record);

		glJournalTable.putOrReplace(identifier, record);
	}

	/**
	 * Adds one line per row to GL journals created with {@code metasfresh contains GL_Journal:}.
	 * <b>Columns</b>: {@code GL_Journal_ID} (journal identifier), {@code Account_DR_ID} / {@code Account_CR_ID}
	 * (identifiers of {@code C_ElementValue}s), {@code Amount} (journal currency; booked debit and credit).
	 */
	@And("metasfresh contains GL_JournalLines:")
	public void createGLJournalLines(@NonNull final DataTable dataTable)
	{
		final int[] lineNo = { 0 };
		DataTableRows.of(dataTable).forEach(row -> {
			final I_GL_Journal journal = row.getAsIdentifier(I_GL_JournalLine.COLUMNNAME_GL_Journal_ID).lookupNotNullIn(glJournalTable);
			final BigDecimal amount = row.getAsBigDecimal("Amount");

			final I_GL_JournalLine line = InterfaceWrapperHelper.newInstance(I_GL_JournalLine.class);
			line.setAD_Org_ID(journal.getAD_Org_ID());
			line.setGL_Journal_ID(journal.getGL_Journal_ID());
			line.setLine(10 * (++lineNo[0]));
			line.setType(X_GL_JournalLine.TYPE_Normal);
			line.setC_Currency_ID(journal.getC_Currency_ID());
			line.setC_ConversionType_ID(journal.getC_ConversionType_ID());
			line.setCurrencyRate(BigDecimal.ONE);
			line.setDateAcct(journal.getDateAcct());
			line.setAccount_DR_ID(getValidCombinationId(row.getAsIdentifier("Account_DR_ID").lookupNotNullIdIn(elementValueTable), journal));
			line.setAccount_CR_ID(getValidCombinationId(row.getAsIdentifier("Account_CR_ID").lookupNotNullIdIn(elementValueTable), journal));
			line.setAmtSourceDr(amount);
			line.setAmtSourceCr(amount);
			line.setAmtAcctDr(amount);
			line.setAmtAcctCr(amount);
			InterfaceWrapperHelper.saveRecord(line);
		});
	}

	private int getValidCombinationId(@NonNull final ElementValueId elementValueId, @NonNull final I_GL_Journal journal)
	{
		final AccountId accountId = accountDAO.getOrCreateOutOfTrx(
				AccountDimension.builder()
						.setAcctSchemaId(AcctSchemaId.ofRepoId(journal.getC_AcctSchema_ID()))
						.setC_ElementValue_ID(elementValueId.getRepoId())
						.setAD_Client_ID(journal.getAD_Client_ID())
						.setAD_Org_ID(OrgId.ANY.getRepoId())
						.build());
		return accountId.getRepoId();
	}

	/** Completes the GL journal and waits for status CO. */
	@And("the GL journal {string} is completed")
	public void completeGLJournal(@NonNull final String identifier)
	{
		final I_GL_Journal record = glJournalTable.get(StepDefDataIdentifier.ofString(identifier));
		InterfaceWrapperHelper.refresh(record);
		documentBL.processEx(record, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
		InterfaceWrapperHelper.refresh(record);
	}

	/** Reactivates the (completed) GL journal. */
	@When("the GL journal {string} is reactivated")
	public void reactivateGLJournal(@NonNull final String identifier)
	{
		final I_GL_Journal record = glJournalTable.get(StepDefDataIdentifier.ofString(identifier));
		InterfaceWrapperHelper.refresh(record);
		documentBL.processEx(record, IDocument.ACTION_ReActivate, IDocument.STATUS_InProgress);
		InterfaceWrapperHelper.refresh(record);
	}

	/** Remembers the number of posting requests ({@code Document_Acct_Log}) and notes ({@code AD_Note}) of the journal. */
	@And("the posting requests of GL journal {string} are remembered")
	public void rememberPostingRequests(@NonNull final String identifier)
	{
		final I_GL_Journal record = glJournalTable.get(StepDefDataIdentifier.ofString(identifier));
		postingQueueChecks.remember(I_GL_Journal.class, record.getGL_Journal_ID());
	}

	/** Waits a few seconds (posting is asynchronous), then asserts that no posting request and no note was created since {@code the posting requests of GL journal ... are remembered}. */
	@Then("GL journal {string} got no new posting request and no note")
	public void assertNoNewPostingRequest(@NonNull final String identifier) throws InterruptedException
	{
		final I_GL_Journal record = glJournalTable.get(StepDefDataIdentifier.ofString(identifier));
		postingQueueChecks.assertNothingNewSinceRemembered(I_GL_Journal.class, record.getGL_Journal_ID());
	}

	/** The journal is not posted and has no accounting entries (nothing queued it again). */
	@Then("the GL journal {string} is not posted and has no Fact_Acct lines")
	public void assertNotPosted(@NonNull final String identifier)
	{
		final I_GL_Journal record = glJournalTable.get(StepDefDataIdentifier.ofString(identifier));
		InterfaceWrapperHelper.refresh(record);
		assertThat(record.isPosted()).as("GL_Journal.Posted of %s", identifier).isFalse();
		final int factAcctCount = queryBL.createQueryBuilder(I_Fact_Acct.class)
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_AD_Table_ID, InterfaceWrapperHelper.getTableId(I_GL_Journal.class))
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_Record_ID, record.getGL_Journal_ID())
				.create()
				.count();
		assertThat(factAcctCount).as("Fact_Acct lines of %s", identifier).isZero();
	}

	/** Asserts the current {@code GL_Journal.PostingType} of the journal. */
	@Then("the GL journal {string} has GL_Journal.PostingType {string}")
	public void assertPostingType(@NonNull final String identifier, @NonNull final String postingType)
	{
		final I_GL_Journal record = glJournalTable.get(StepDefDataIdentifier.ofString(identifier));
		InterfaceWrapperHelper.refresh(record);
		assertThat(record.getPostingType()).as("GL_Journal.PostingType of %s", identifier).isEqualTo(postingType);
	}

	/** Asserts the current {@code GL_JournalBatch.PostingType} of the batch. */
	@Then("the GL journal batch {string} has GL_JournalBatch.PostingType {string}")
	public void assertBatchPostingType(@NonNull final String identifier, @NonNull final String postingType)
	{
		final I_GL_JournalBatch record = glJournalBatchTable.get(StepDefDataIdentifier.ofString(identifier));
		InterfaceWrapperHelper.refresh(record);
		assertThat(record.getPostingType()).as("GL_JournalBatch.PostingType of %s", identifier).isEqualTo(postingType);
	}

	/** Runs process {@code GL_Journal_ChangePostingType} on the batch, as the WebUI role. */
	@When("the GL_Journal_ChangePostingType process is invoked for GL journal batch {string} with PostingType {string}")
	public void invokeChangePostingTypeOnBatch(@NonNull final String identifier, @NonNull final String postingType)
	{
		final I_GL_JournalBatch batch = glJournalBatchTable.get(StepDefDataIdentifier.ofString(identifier));
		runProcessAsWebUIRole(GL_Journal_ChangePostingType.class, ClientId.ofRepoId(batch.getAD_Client_ID()),
				I_GL_JournalBatch.Table_Name, batch.getGL_JournalBatch_ID(), builder -> builder.addParameter("PostingType", postingType));
	}

	/**
	 * Runs the year-end process ({@link GL_Journal_GenerateYearEnding}) for the primary accounting schema and the standard org, as the WebUI role.
	 * It creates one drafted GL journal with {@code PostingType=S} that moves the balances of all expense/revenue accounts
	 * (booked as {@code PostingType=A} within the year of the given date) to the income summary account. The journal is registered under the given identifier.
	 */
	@When("the GL_Journal_GenerateYearEnding process is invoked for DateAcct {string} and the created GL journal is registered as {string}")
	public void invokeGenerateYearEnding(@NonNull final String dateAcct, @NonNull final String identifier)
	{
		final AcctSchema acctSchema = acctSchemaBL.getPrimaryAcctSchema(StepDefConstants.CLIENT_ID);
		final Timestamp dateAcctTs = Timestamp.valueOf(LocalDate.parse(dateAcct).atStartOfDay());

		final Set<Integer> idsBefore = retrieveYearEndingJournalIds(dateAcctTs);

		runProcessAsWebUIRole(GL_Journal_GenerateYearEnding.class, StepDefConstants.CLIENT_ID, null, 0, builder -> builder
				.addParameter(I_GL_Journal.COLUMNNAME_C_AcctSchema_ID, acctSchema.getId().getRepoId())
				.addParameter(I_GL_Journal.COLUMNNAME_AD_Org_ID, StepDefConstants.ORG_ID.getRepoId())
				.addParameter(I_GL_Journal.COLUMNNAME_DateAcct, dateAcctTs));

		final Set<Integer> newIds = new HashSet<>(retrieveYearEndingJournalIds(dateAcctTs));
		newIds.removeAll(idsBefore);
		assertThat(newIds).as("GL_Journal records (PostingType=S, DateAcct=%s) created by GL_Journal_GenerateYearEnding", dateAcct).hasSize(1);

		glJournalTable.putOrReplace(StepDefDataIdentifier.ofString(identifier),
				InterfaceWrapperHelper.load(newIds.iterator().next(), I_GL_Journal.class));
	}

	private Set<Integer> retrieveYearEndingJournalIds(@NonNull final Timestamp dateAcct)
	{
		return queryBL.createQueryBuilder(I_GL_Journal.class)
				.addEqualsFilter(I_GL_Journal.COLUMNNAME_PostingType, X_GL_Journal.POSTINGTYPE_Statistical)
				.addEqualsFilter(I_GL_Journal.COLUMNNAME_DateAcct, dateAcct)
				.create()
				.listIds()
				.stream().collect(Collectors.toSet());
	}

	/**
	 * Runs the "Change posting type" process (class {@link GL_Journal_ChangePostingType}) on the journal, as the WebUI role of the journal's client.
	 */
	@When("the GL_Journal_ChangePostingType process is invoked for GL journal {string} with PostingType {string}")
	public void invokeChangePostingType(@NonNull final String identifier, @NonNull final String postingType)
	{
		final I_GL_Journal journal = glJournalTable.get(StepDefDataIdentifier.ofString(identifier));
		runProcessAsWebUIRole(GL_Journal_ChangePostingType.class, ClientId.ofRepoId(journal.getAD_Client_ID()), I_GL_Journal.Table_Name, journal.getGL_Journal_ID(), builder -> builder.addParameter("PostingType", postingType));
	}

	private void runProcessAsWebUIRole(
			@NonNull final Class<?> processClass,
			@NonNull final ClientId clientId,
			@Nullable final String recordTableName,
			final int recordId,
			@NonNull final Consumer<ProcessInfo.ProcessInfoBuilder> parameterSetter)
	{
		final AdProcessId processId = adProcessDAO.retrieveProcessIdByClass(processClass);

		final UserId loggedUserId = Env.getLoggedUserId();
		final RoleId roleId = roleDAO.getUserRoles(loggedUserId)
				.stream()
				.filter(r -> "WebUI".equals(r.getName()))
				.map(Role::getId)
				.findFirst()
				.orElseThrow(() -> new AdempiereException("WebUI role not found for user " + loggedUserId));

		final ProcessInfo.ProcessInfoBuilder builder = ProcessInfo.builder()
				.setAD_Process_ID(processId.getRepoId())
				.setClientId(clientId)
				.setRoleId(roleId)
				.setCreateTemporaryCtx();
		if (recordTableName != null)
		{
			builder.setRecord(recordTableName, recordId);
		}
		parameterSetter.accept(builder);

		builder.buildAndPrepareExecution()
				.switchContextWhenRunning()
				.executeSync()
				.getResult()
				.propagateErrorIfAny();
	}

	/**
	 * A completed journal must end up posted (accounting entries re-created) with the given posting type: {@code GL_Journal.Posted='Y'},
	 * {@code GL_Journal.PostingType} as given, and the expected number of {@code Fact_Acct} rows of that posting type.
	 */
	@Then("the GL journal {string} is eventually posted with PostingType {string} and {int} Fact_Acct lines")
	public void assertEventuallyPosted(
			@NonNull final String identifier,
			@NonNull final String postingType,
			final int expectedFactAcctCount) throws InterruptedException
	{
		final I_GL_Journal record = glJournalTable.get(StepDefDataIdentifier.ofString(identifier));

		try
		{
			StepDefUtil.tryAndWait(20, 500, () -> isPosted(record, postingType, expectedFactAcctCount));
		}
		catch (final AssertionError | RuntimeException e)
		{
			// the wait timed out (StepDefUtil.tryAndWait throws AssertionFailedError) - fall through to the assertions below, which describe the actual state
		}

		InterfaceWrapperHelper.refresh(record);
		final int factAcctCount = countFactAcct(record, postingType);
		assertThat(record.getDocStatus()).as("GL_Journal.DocStatus").isEqualTo(IDocument.STATUS_Completed);
		assertThat(record.getPostingType()).as("GL_Journal.PostingType").isEqualTo(postingType);
		assertThat(record.isPosted())
				.as("GL_Journal.Posted of the completed journal %s (Fact_Acct lines with PostingType=%s: %s) - a completed journal must be posted again after its entries were deleted",
						identifier, postingType, factAcctCount)
				.isTrue();
		assertThat(factAcctCount).as("Fact_Acct lines with PostingType=%s", postingType).isEqualTo(expectedFactAcctCount);
	}

	private boolean isPosted(@NonNull final I_GL_Journal record, @NonNull final String postingType, final int expectedFactAcctCount)
	{
		InterfaceWrapperHelper.refresh(record);
		return record.isPosted() && countFactAcct(record, postingType) == expectedFactAcctCount;
	}

	private int countFactAcct(@NonNull final I_GL_Journal record, @NonNull final String postingType)
	{
		return queryBL.createQueryBuilder(I_Fact_Acct.class)
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_AD_Table_ID, InterfaceWrapperHelper.getTableId(I_GL_Journal.class))
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_Record_ID, record.getGL_Journal_ID())
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_PostingType, postingType)
				.create()
				.count();
	}
}
