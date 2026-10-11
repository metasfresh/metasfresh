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

package de.metas.cucumber.stepdefs.gljournal_sap;

import de.metas.acct.Account;
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
import de.metas.acct.gljournal_sap.PostingSign;
import de.metas.acct.gljournal_sap.SAPGLJournalId;
import de.metas.acct.gljournal_sap.service.SAPGLJournalLineCreateRequest;
import de.metas.acct.gljournal_sap.service.SAPGLJournalService;
import de.metas.acct.model.I_SAP_GLJournal;
import de.metas.acct.model.X_SAP_GLJournal;
import de.metas.bpartner.BPartnerId;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.accounting.C_ElementValue_StepDefData;
import de.metas.cucumber.stepdefs.tax.C_Tax_StepDefData;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.ICurrencyDAO;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.dimension.Dimension;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.tax.api.TaxId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Step definitions for SAP GL journals ({@link I_SAP_GLJournal} / {@code SAP_GLJournalLine}, the "Hauptbuchjournal" window).
 *
 * <p>The journal is created the way the window does it: the header record is saved (document no. from the document type), the lines are added
 * through {@link SAPGLJournalService#createLine}, and the journal is completed through the document engine, which also schedules the posting
 * ({@code Doc_SAPGLJournal}). Use {@code Wait until documents <identifier> is posted} afterwards.
 *
 * <p><b>Gherkin usage example</b>:
 * <pre>{@code
 * And metasfresh contains C_ElementValues:
 *   | Identifier    |
 *   | counterAcct   |
 * And metasfresh contains SAP_GLJournal:
 *   | Identifier | DateDoc    | DateAcct   | C_Currency_ID |
 *   | sgj        | 2024-01-10 | 2024-01-12 | EUR           |
 * And metasfresh contains SAP_GLJournalLines:
 *   | SAP_GLJournal_ID | PostingSign | Amount | Account_ID  | C_Tax_ID | SAP_DetermineTaxBase | C_BPartner_ID |
 *   | sgj              | D           | 19.00  |             | tax19p   | Y                    | vendor        |
 *   | sgj              | C           | 19.00  | counterAcct |          |                      |               |
 * And the SAP GL journal "sgj" is completed
 * And Wait until documents sgj is posted
 * }</pre>
 */
@RequiredArgsConstructor
public class SAP_GLJournal_StepDef
{
	@NonNull private final SAP_GLJournal_StepDefData glJournalTable;
	@NonNull private final C_ElementValue_StepDefData elementValueTable;
	@NonNull private final C_Tax_StepDefData taxTable;
	@NonNull private final C_BPartner_StepDefData bpartnerTable;

	private final SAPGLJournalService glJournalService = SpringContextHolder.instance.getBean(SAPGLJournalService.class);
	private final CurrencyRepository currencyRepository = SpringContextHolder.instance.getBean(CurrencyRepository.class);
	private final GLCategoryRepository glCategoryRepository = SpringContextHolder.instance.getBean(GLCategoryRepository.class);
	private final IAcctSchemaBL acctSchemaBL = Services.get(IAcctSchemaBL.class);
	private final IAccountDAO accountDAO = Services.get(IAccountDAO.class);
	private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);

	/**
	 * Creates drafted SAP GL journal headers in the primary accounting schema of {@link StepDefConstants#CLIENT_ID}, organisation {@link StepDefConstants#ORG_ID},
	 * with the default GL journal document type ({@code DocBaseType=GLJ}).
	 *
	 * <p><b>Columns</b>:
	 * <ul>
	 *     <li>{@code Identifier} — (required) identifier of the journal</li>
	 *     <li>{@code DateDoc} — (required) document date</li>
	 *     <li>{@code DateAcct} — (optional) accounting date; defaults to {@code DateDoc} (as the window's callout does)</li>
	 *     <li>{@code C_Currency_ID} — (optional) ISO code of the journal currency; defaults to the accounting schema currency</li>
	 *     <li>{@code Description} — (optional)</li>
	 * </ul>
	 */
	@Given("metasfresh contains SAP_GLJournal:")
	public void createSAPGLJournals(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createSAPGLJournal);
	}

	private void createSAPGLJournal(@NonNull final DataTableRow row)
	{
		final StepDefDataIdentifier identifier = row.getAsIdentifier();
		final AcctSchema acctSchema = acctSchemaBL.getPrimaryAcctSchema(StepDefConstants.CLIENT_ID);
		final OrgId orgId = StepDefConstants.ORG_ID;

		final Timestamp dateDoc = row.getAsLocalDateTimestamp(I_SAP_GLJournal.COLUMNNAME_DateDoc);
		final Timestamp dateAcct = row.getAsOptionalLocalDateTimestamp(I_SAP_GLJournal.COLUMNNAME_DateAcct).orElse(dateDoc);

		final CurrencyId acctCurrencyId = acctSchema.getCurrencyId();
		final CurrencyId currencyId = row.getAsOptionalString(I_SAP_GLJournal.COLUMNNAME_C_Currency_ID)
				.map(CurrencyCode::ofThreeLetterCode)
				.map(currencyRepository::getCurrencyIdByCurrencyCode)
				.orElse(acctCurrencyId);

		final DocTypeId docTypeId = docTypeDAO.getDocTypeId(DocTypeQuery.builder()
				.docBaseType(DocBaseType.GLJournal)
				.adClientId(StepDefConstants.CLIENT_ID.getRepoId())
				.adOrgId(orgId.getRepoId())
				.build());
		final GLCategoryId glCategoryId = glCategoryRepository.getDefaultId(StepDefConstants.CLIENT_ID, GLCategoryType.Manual)
				.orElseThrow(() -> new AdempiereException("No default GL_Category found for " + StepDefConstants.CLIENT_ID));
		final CurrencyConversionTypeId conversionTypeId = currencyDAO.getDefaultConversionTypeId(StepDefConstants.CLIENT_ID, orgId, TimeUtil.asInstant(dateAcct));

		final I_SAP_GLJournal record = InterfaceWrapperHelper.newInstance(I_SAP_GLJournal.class);
		record.setAD_Org_ID(orgId.getRepoId());
		record.setC_DocType_ID(docTypeId.getRepoId());
		record.setC_AcctSchema_ID(acctSchema.getId().getRepoId());
		record.setPostingType(X_SAP_GLJournal.POSTINGTYPE_Actual);
		record.setDateDoc(dateDoc);
		record.setDateAcct(dateAcct);
		record.setAcct_Currency_ID(acctCurrencyId.getRepoId());
		record.setC_Currency_ID(currencyId.getRepoId());
		record.setC_ConversionType_ID(conversionTypeId.getRepoId());
		// same currency => rate 1 (the column default); a foreign currency is converted with the conversion type's rate (CurrencyRate=0 => no fixed rate)
		record.setCurrencyRate(CurrencyId.equals(currencyId, acctCurrencyId) ? BigDecimal.ONE : BigDecimal.ZERO);
		record.setGL_Category_ID(glCategoryId.getRepoId());
		record.setDescription(row.getAsOptionalString(I_SAP_GLJournal.COLUMNNAME_Description).orElse("cucumber SAP GL journal " + identifier.getAsString()));
		record.setDocStatus(IDocument.STATUS_Drafted);
		record.setDocAction(IDocument.ACTION_Complete);
		InterfaceWrapperHelper.saveRecord(record);

		glJournalTable.putOrReplace(identifier, record);
	}

	/**
	 * Adds lines to SAP GL journals created with {@code metasfresh contains SAP_GLJournal:}, in the given order.
	 *
	 * <p><b>Columns</b>:
	 * <ul>
	 *     <li>{@code SAP_GLJournal_ID} — (required) identifier of the journal</li>
	 *     <li>{@code PostingSign} — (required) {@code D} (debit) or {@code C} (credit)</li>
	 *     <li>{@code Amount} — (required) amount in the journal currency</li>
	 *     <li>{@code Account_ID} — (optional) identifier of a {@code C_ElementValue} (see {@code metasfresh contains C_ElementValues:});
	 *         if empty, {@code C_Tax_ID} is required and the tax account is used: the tax's {@code T_Credit_Acct} for {@code D},
	 *         its {@code T_Due_Acct} for {@code C} (the account a generated tax line gets)</li>
	 *     <li>{@code C_Tax_ID} — (optional) identifier of a {@code C_Tax}</li>
	 *     <li>{@code SAP_DetermineTaxBase} — (optional, default {@code N}) {@code Y} together with {@code C_Tax_ID} makes the line a tax line:
	 *         its {@code Fact_Acct} row gets the tax's VAT code with amount type Tax</li>
	 *     <li>{@code C_BPartner_ID} — (optional) identifier of a business partner</li>
	 *     <li>{@code Description} — (optional)</li>
	 * </ul>
	 */
	@And("metasfresh contains SAP_GLJournalLines:")
	public void createSAPGLJournalLines(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createSAPGLJournalLine);
	}

	private void createSAPGLJournalLine(@NonNull final DataTableRow row)
	{
		final I_SAP_GLJournal journalRecord = row.getAsIdentifier(I_SAP_GLJournal.COLUMNNAME_SAP_GLJournal_ID).lookupNotNullIn(glJournalTable);
		final SAPGLJournalId glJournalId = SAPGLJournalId.ofRepoId(journalRecord.getSAP_GLJournal_ID());

		final PostingSign postingSign = PostingSign.ofCode(row.getAsString("PostingSign"));
		final TaxId taxId = row.getAsOptionalIdentifier("C_Tax_ID")
				.map(taxTable::getId)
				.orElse(null);
		final BPartnerId bpartnerId = row.getAsOptionalIdentifier("C_BPartner_ID")
				.map(bpartnerTable::getId)
				.orElse(null);

		final Account account = row.getAsOptionalIdentifier("Account_ID")
				.map(elementValueIdentifier -> getAccountByElementValue(elementValueIdentifier.lookupNotNullIdIn(elementValueTable), journalRecord))
				.orElseGet(() -> {
					if (taxId == null)
					{
						throw new AdempiereException("Either Account_ID or C_Tax_ID must be set: " + row);
					}
					return glJournalService.getTaxProvider().getTaxAccount(taxId, acctSchemaIdOf(journalRecord), postingSign);
				});

		glJournalService.createLine(
				SAPGLJournalLineCreateRequest.builder()
						.postingSign(postingSign)
						.account(account)
						.amount(row.getAsBigDecimal("Amount"))
						.taxId(taxId)
						.determineTaxBaseSAP(row.getAsOptionalBoolean("SAP_DetermineTaxBase").orElseFalse())
						.bpartnerId(bpartnerId)
						.description(row.getAsOptionalString("Description").orElse(null))
						.dimension(Dimension.EMPTY)
						.build(),
				glJournalId);

		// the header totals were updated by the service on another instance of the record
		InterfaceWrapperHelper.refresh(journalRecord);
	}

	private Account getAccountByElementValue(@NonNull final ElementValueId elementValueId, @NonNull final I_SAP_GLJournal journalRecord)
	{
		final AccountId accountId = accountDAO.getOrCreateOutOfTrx(
				AccountDimension.builder()
						.setAcctSchemaId(acctSchemaIdOf(journalRecord))
						.setC_ElementValue_ID(elementValueId.getRepoId())
						.setAD_Client_ID(journalRecord.getAD_Client_ID())
						.setAD_Org_ID(OrgId.ANY.getRepoId())
						.build());
		return Account.ofId(accountId);
	}

	private static AcctSchemaId acctSchemaIdOf(@NonNull final I_SAP_GLJournal journalRecord)
	{
		return AcctSchemaId.ofRepoId(journalRecord.getC_AcctSchema_ID());
	}

	/**
	 * Completes the SAP GL journal through the document engine; completion also schedules its posting.
	 */
	@And("the SAP GL journal {string} is completed")
	public void completeSAPGLJournal(@NonNull final String identifier)
	{
		final I_SAP_GLJournal record = glJournalTable.get(StepDefDataIdentifier.ofString(identifier));
		InterfaceWrapperHelper.refresh(record);
		documentBL.processEx(record, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
		InterfaceWrapperHelper.refresh(record);
	}
}
