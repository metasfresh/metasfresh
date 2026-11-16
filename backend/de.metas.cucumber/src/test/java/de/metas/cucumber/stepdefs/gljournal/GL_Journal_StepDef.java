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
import de.metas.acct.api.IAccountDAO;
import de.metas.acct.api.IAcctSchemaBL;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.accounting.C_ElementValue_StepDefData;
import de.metas.acct.process.GL_Journal_ChangePostingType;
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
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.I_GL_JournalLine;
import org.compiere.model.X_GL_Journal;
import org.compiere.model.X_GL_JournalLine;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;

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
	@NonNull private final C_ElementValue_StepDefData elementValueTable;

	private final GLCategoryRepository glCategoryRepository = org.compiere.SpringContextHolder.instance.getBean(GLCategoryRepository.class);
	private final IAcctSchemaBL acctSchemaBL = Services.get(IAcctSchemaBL.class);
	private final IAccountDAO accountDAO = Services.get(IAccountDAO.class);
	private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
	private final IRoleDAO roleDAO = Services.get(IRoleDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Creates drafted GL journal headers (no batch) in the primary accounting schema, with the default GL journal document type.
	 * <b>Columns</b>: {@code Identifier} (required), {@code DateDoc} (required), {@code DateAcct} (optional, default DateDoc),
	 * {@code PostingType} (optional, default {@code A}).
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
						.setAcctSchemaId(de.metas.acct.api.AcctSchemaId.ofRepoId(journal.getC_AcctSchema_ID()))
						.setC_ElementValue_ID(elementValueId.getRepoId())
						.setAD_Client_ID(journal.getAD_Client_ID())
						.setAD_Org_ID(OrgId.ANY.getRepoId())
						.build());
		return accountId.getRepoId();
	}

	/**
	 * Allows posting with posting type Statistical on the account ({@code C_ElementValue.PostStatistical}), which a journal with
	 * {@code PostingType=S} requires.
	 */
	@Given("C_ElementValue {string} allows posting of statistical amounts")
	public void allowStatisticalPosting(@NonNull final String identifier)
	{
		final org.compiere.model.I_C_ElementValue elementValue = InterfaceWrapperHelper.load(
				elementValueTable.getId(StepDefDataIdentifier.ofString(identifier)).getRepoId(),
				org.compiere.model.I_C_ElementValue.class);
		elementValue.setPostStatistical(true);
		InterfaceWrapperHelper.saveRecord(elementValue);
	}

	@And("the GL journal {string} is completed")
	public void completeGLJournal(@NonNull final String identifier)
	{
		final I_GL_Journal record = glJournalTable.get(StepDefDataIdentifier.ofString(identifier));
		InterfaceWrapperHelper.refresh(record);
		documentBL.processEx(record, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
		InterfaceWrapperHelper.refresh(record);
	}

	/**
	 * Runs the "Change posting type" process (class {@link GL_Journal_ChangePostingType}) on the journal, as the WebUI role of the journal's client.
	 */
	@When("the GL_Journal_ChangePostingType process is invoked for GL journal {string} with PostingType {string}")
	public void invokeChangePostingType(@NonNull final String identifier, @NonNull final String postingType)
	{
		final I_GL_Journal journal = glJournalTable.get(StepDefDataIdentifier.ofString(identifier));
		final AdProcessId processId = adProcessDAO.retrieveProcessIdByClass(GL_Journal_ChangePostingType.class);

		final UserId loggedUserId = Env.getLoggedUserId();
		final RoleId roleId = roleDAO.getUserRoles(loggedUserId)
				.stream()
				.filter(r -> "WebUI".equals(r.getName()))
				.map(Role::getId)
				.findFirst()
				.orElseThrow(() -> new AdempiereException("WebUI role not found for user " + loggedUserId));

		ProcessInfo.builder()
				.setAD_Process_ID(processId.getRepoId())
				.setClientId(ClientId.ofRepoId(journal.getAD_Client_ID()))
				.setRoleId(roleId)
				.setCreateTemporaryCtx()
				.setRecord(I_GL_Journal.Table_Name, journal.getGL_Journal_ID())
				.addParameter("PostingType", postingType)
				.buildAndPrepareExecution()
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
		catch (final RuntimeException | AssertionError e)
		{
			// fall through to the assertions below, which describe the actual state
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
