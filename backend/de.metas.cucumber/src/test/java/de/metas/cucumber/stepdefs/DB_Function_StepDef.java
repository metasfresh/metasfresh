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

package de.metas.cucumber.stepdefs;

import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.dunning.api.IDunningBL;
import de.metas.dunning.api.IDunningContext;
import de.metas.dunning.api.impl.AbstractDunningCandidateSource;
import de.metas.dunning.interfaces.I_C_Dunning;
import de.metas.dunning.interfaces.I_C_DunningLevel;
import de.metas.dunning.model.I_C_DunningDoc;
import de.metas.dunning.model.I_C_DunningDoc_Line;
import de.metas.dunning.model.I_C_DunningDoc_Line_Source;
import de.metas.dunning.model.I_C_Dunning_Candidate;
import de.metas.organization.IOrgDAO;
import de.metas.cache.CacheMgt;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.service.ClientId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Location;
import org.compiere.util.DB;
import org.compiere.util.Env;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions to read the text result of a PL/pgSQL / SQL report function and assert it line by line,
 * plus the set-up steps the QR-bill scenarios need (organization as QR-bill creditor, a dunning letter on an invoice).
 */
public class DB_Function_StepDef
{
	private static final Pattern SQL_FUNCTION_NAME = Pattern.compile("[A-Za-z_][A-Za-z0-9_]*(\\.[A-Za-z_][A-Za-z0-9_]*)?");

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IDunningBL dunningBL = Services.get(IDunningBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private static final String QR_ORG_VALUE = "901";
	private static final String ESR_ENABLED_SYSCONFIG = "de.metas.payment.esr.Enabled";

	/** ESR on/off before the scenario switched it on; null if it did not */
	private Boolean esrEnabledBeforeScenario;

	private final C_Invoice_StepDefData invoiceTable;

	/** C_DunningDoc_ID by scenario identifier */
	private final Map<String, Integer> dunningDocIdsByIdentifier = new HashMap<>();

	/** the invoice a dunning letter was created for, by dunning letter identifier */
	private final Map<String, String> invoiceIdentifiersByDunningDocIdentifier = new HashMap<>();

	public DB_Function_StepDef(@NonNull final C_Invoice_StepDefData invoiceTable)
	{
		this.invoiceTable = invoiceTable;
	}

	/**
	 * Calls <code>SELECT QR_Code FROM &lt;function&gt;(&lt;id&gt;)</code>, splits the text result by line break and compares the
	 * lines given in the table.
	 * <ul>
	 * <li>Columns: <code>LineNo</code> (1-based), <code>Value</code> (an empty cell means an empty line).</li>
	 * <li>A <code>Value</code> starting with <code>~</code> is a regular expression the whole line must match.</li>
	 * <li><code>&lt;GrandTotal&gt;</code> in a <code>Value</code> stands for the grand total of the invoice (of the dunning letter), scale 2.</li>
	 * </ul>
	 */
	@Then("^the SQL function (\\S+) called with the ID of (\\S+) returns these lines:$")
	public void the_sql_function_returns_these_lines(
			@NonNull final String functionName,
			@NonNull final String recordIdentifier,
			@NonNull final DataTable dataTable)
	{
		assertThat(functionName).as("function name").matches(SQL_FUNCTION_NAME);

		final int recordId = getRecordId(recordIdentifier);

		final String text = DB.getSQLValueStringEx(
				ITrx.TRXNAME_None,
				"SELECT QR_Code FROM " + functionName + "(?::numeric)",
				recordId);
		assertThat(text).as("result of " + functionName + "(" + recordId + ")").isNotNull();

		final I_C_Invoice invoice = invoiceTable.get(invoiceIdentifiersByDunningDocIdentifier.getOrDefault(recordIdentifier, recordIdentifier));
		InterfaceWrapperHelper.refresh(invoice);
		final String grandTotal = invoice
				.getGrandTotal().setScale(2, RoundingMode.HALF_UP).toPlainString();

		final String[] lines = text.split("\n", -1);
		final List<String> mismatches = new ArrayList<>();

		for (final Map<String, String> row : dataTable.asMaps())
		{
			final int lineNo = Integer.parseInt(DataTableUtil.extractStringForColumnName(row, "LineNo"));
			final String expected = (row.get("Value") == null ? "" : row.get("Value")).replace("<GrandTotal>", grandTotal);
			final String actual = lineNo >= 1 && lineNo <= lines.length ? lines[lineNo - 1] : "<no such line>";

			final boolean matches = expected.startsWith("~")
					? actual.matches(expected.substring(1))
					: expected.equals(actual);
			if (!matches)
			{
				mismatches.add("line " + lineNo + ": expected <" + expected + "> but was <" + actual + ">");
			}
		}

		assertThat(mismatches)
				.as("lines of " + functionName + "(" + recordId + ") that differ; whole result: " + text.replace("\n", "|"))
				.isEmpty();
	}

	/**
	 * Moves the (draft) invoice to an own organization (value {@value #QR_ORG_VALUE}) that is a QR-bill creditor: the organization's
	 * business partner gets the given name and address and one ESR bank account with the given IBAN / QR-IBAN. A separate organization
	 * keeps every other feature's data (in the default organization) untouched. Also switches the ESR module on (the way customers
	 * who print QR-bills run it), until the end of the scenario.
	 * Columns: <code>Name</code>, <code>Address1</code>, <code>Postal</code>, <code>City</code>, <code>CountryCode</code>,
	 * <code>IBAN</code>, <code>OPT.QR_IBAN</code>, <code>OPT.SEPA_CreditorIdentifier</code>.
	 */
	@Given("^the invoice identified by (\\S+) belongs to an organization that is the QR-bill creditor:$")
	public void the_invoice_belongs_to_an_organization_that_is_the_qr_bill_creditor(
			@NonNull final String invoiceIdentifier,
			@NonNull final DataTable dataTable)
	{
		final Map<String, String> row = dataTable.asMaps().get(0);
		final I_C_Invoice invoice = invoiceTable.get(invoiceIdentifier);

		final I_AD_Org org = queryBL.createQueryBuilder(I_AD_Org.class)
				.addEqualsFilter(I_AD_Org.COLUMNNAME_Value, QR_ORG_VALUE)
				.create()
				.firstOnly(I_AD_Org.class);
		final I_AD_Org orgRecord = org != null ? org : InterfaceWrapperHelper.newInstanceOutOfTrx(I_AD_Org.class);
		orgRecord.setValue(QR_ORG_VALUE);
		orgRecord.setName("QR-bill creditor organization");
		orgDAO.save(orgRecord);

		final String name = DataTableUtil.extractStringForColumnName(row, "Name");
		final I_C_BPartner existingOrgBPartner = queryBL.createQueryBuilder(I_C_BPartner.class)
				.addEqualsFilter(I_C_BPartner.COLUMNNAME_AD_OrgBP_ID, orgRecord.getAD_Org_ID())
				.create()
				.firstOnly(I_C_BPartner.class);
		final I_C_BPartner orgBPartner = existingOrgBPartner != null ? existingOrgBPartner : newInstance(I_C_BPartner.class);
		orgBPartner.setValue(QR_ORG_VALUE);
		orgBPartner.setName(name);
		orgBPartner.setCompanyName(name);
		orgBPartner.setIsCompany(true);
		orgBPartner.setAD_OrgBP_ID(orgRecord.getAD_Org_ID());
		save(orgBPartner);

		final I_C_Country country = queryBL.createQueryBuilder(I_C_Country.class)
				.addEqualsFilter(I_C_Country.COLUMNNAME_CountryCode, DataTableUtil.extractStringForColumnName(row, "CountryCode"))
				.create()
				.firstOnlyNotNull(I_C_Country.class);
		final I_C_Location location = newInstance(I_C_Location.class);
		location.setC_Country_ID(country.getC_Country_ID());
		location.setAddress1(DataTableUtil.extractStringForColumnName(row, "Address1"));
		location.setPostal(DataTableUtil.extractStringForColumnName(row, "Postal"));
		location.setCity(DataTableUtil.extractStringForColumnName(row, "City"));
		save(location);

		final I_C_BPartner_Location orgBPLocation = newInstance(I_C_BPartner_Location.class);
		orgBPLocation.setC_BPartner_ID(orgBPartner.getC_BPartner_ID());
		orgBPLocation.setC_Location_ID(location.getC_Location_ID());
		orgBPLocation.setIsBillTo(true);
		orgBPLocation.setIsShipTo(true);
		save(orgBPLocation);

		final I_AD_OrgInfo existingOrgInfo = queryBL.createQueryBuilder(I_AD_OrgInfo.class)
				.addEqualsFilter(I_AD_OrgInfo.COLUMNNAME_AD_Org_ID, orgRecord.getAD_Org_ID())
				.create()
				.firstOnly(I_AD_OrgInfo.class);
		final I_AD_OrgInfo orgInfo = existingOrgInfo != null ? existingOrgInfo : InterfaceWrapperHelper.newInstanceOutOfTrx(I_AD_OrgInfo.class);
		orgInfo.setAD_Org_ID(orgRecord.getAD_Org_ID());
		orgInfo.setOrg_BPartner_ID(orgBPartner.getC_BPartner_ID());
		orgInfo.setOrgBP_Location_ID(orgBPLocation.getC_BPartner_Location_ID());
		InterfaceWrapperHelper.saveRecord(orgInfo);

		// one ESR account only
		queryBL.createQueryBuilder(I_C_BP_BankAccount.class)
				.addEqualsFilter(I_C_BP_BankAccount.COLUMNNAME_C_BPartner_ID, orgBPartner.getC_BPartner_ID())
				.create()
				.list(I_C_BP_BankAccount.class)
				.forEach(account -> {
					account.setIsEsrAccount(false); // the dunning function does not look at IsActive of the ESR account
					account.setIsActive(false);
					save(account);
				});

		final I_C_BP_BankAccount bankAccount = newInstance(I_C_BP_BankAccount.class);
		bankAccount.setC_BPartner_ID(orgBPartner.getC_BPartner_ID());
		bankAccount.setC_Currency_ID(invoice.getC_Currency_ID());
		bankAccount.setIsEsrAccount(true);
		bankAccount.setIsDefaultESR(true);
		bankAccount.setAccountNo("1234567");
		bankAccount.setESR_RenderedAccountNo("01-1234-5");
		bankAccount.setIBAN(DataTableUtil.extractStringForColumnName(row, "IBAN"));

		final String qrIban = DataTableUtil.extractStringOrNullForColumnName(row, "OPT.QR_IBAN");
		if (Check.isNotBlank(qrIban))
		{
			bankAccount.setQR_IBAN(qrIban);
		}
		final String creditorIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT.SEPA_CreditorIdentifier");
		if (Check.isNotBlank(creditorIdentifier))
		{
			bankAccount.setSEPA_CreditorIdentifier(creditorIdentifier);
		}
		save(bankAccount);

		// move the invoice (and its lines) to the organization
		invoice.setAD_Org_ID(orgRecord.getAD_Org_ID());
		save(invoice);
		queryBL.createQueryBuilder(I_C_InvoiceLine.class)
				.addEqualsFilter(I_C_InvoiceLine.COLUMNNAME_C_Invoice_ID, invoice.getC_Invoice_ID())
				.create()
				.list(I_C_InvoiceLine.class)
				.forEach(line -> {
					line.setAD_Org_ID(orgRecord.getAD_Org_ID());
					save(line);
				});

		enableEsrUntilEndOfScenario();
	}

	@After
	public void restoreEsrSysConfig()
	{
		if (esrEnabledBeforeScenario != null)
		{
			sysConfigBL.setValue(ESR_ENABLED_SYSCONFIG, esrEnabledBeforeScenario, ClientId.SYSTEM, StepDefConstants.ORG_ID_SYSTEM);
			CacheMgt.get().reset();
			esrEnabledBeforeScenario = null;
		}
	}

	private void enableEsrUntilEndOfScenario()
	{
		if (esrEnabledBeforeScenario == null)
		{
			esrEnabledBeforeScenario = sysConfigBL.getBooleanValue(ESR_ENABLED_SYSCONFIG, true, ClientId.SYSTEM.getRepoId());
		}
		sysConfigBL.setValue(ESR_ENABLED_SYSCONFIG, true, ClientId.SYSTEM, StepDefConstants.ORG_ID_SYSTEM);
		CacheMgt.get().reset();
	}

	/**
	 * Creates a dunning letter (C_DunningDoc) that contains the given invoice, using the regular dunning candidate creation and
	 * processing.
	 */
	@Given("^a dunning letter identified by (\\S+) is created for the invoice identified by (\\S+)$")
	public void a_dunning_letter_is_created_for_the_invoice(
			@NonNull final String dunningDocIdentifier,
			@NonNull final String invoiceIdentifier)
	{
		final I_C_Invoice invoice = invoiceTable.get(invoiceIdentifier);

		// the invoice's organization has one default dunning (C_Dunning_Candidate_Invoice_v1); reuse it if it exists
		final I_C_Dunning existingDunning = queryBL.createQueryBuilder(I_C_Dunning.class)
				.addEqualsFilter(I_C_Dunning.COLUMNNAME_AD_Org_ID, invoice.getAD_Org_ID())
				.addEqualsFilter(I_C_Dunning.COLUMNNAME_IsDefault, true)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_C_Dunning.class);
		final I_C_Dunning dunning;
		if (existingDunning != null)
		{
			dunning = existingDunning;
		}
		else
		{
			dunning = newInstance(I_C_Dunning.class);
			dunning.setName("QR-bill dunning of org " + invoice.getAD_Org_ID());
			dunning.setCreateLevelsSequentially(true);
			dunning.setIsActive(true);
			dunning.setIsDefault(true);
			dunning.setAD_Org_ID(invoice.getAD_Org_ID());
			save(dunning);
		}

		final I_C_DunningLevel existingLevel = queryBL.createQueryBuilder(I_C_DunningLevel.class)
				.addEqualsFilter(I_C_DunningLevel.COLUMNNAME_C_Dunning_ID, dunning.getC_Dunning_ID())
				.addOnlyActiveRecordsFilter()
				.orderBy(I_C_DunningLevel.COLUMNNAME_C_DunningLevel_ID)
				.create()
				.first(I_C_DunningLevel.class);
		final I_C_DunningLevel dunningLevel;
		if (existingLevel != null)
		{
			dunningLevel = existingLevel;
		}
		else
		{
			dunningLevel = newInstance(I_C_DunningLevel.class);
			dunningLevel.setC_Dunning_ID(dunning.getC_Dunning_ID());
			dunningLevel.setName("Level 1");
			dunningLevel.setPrintName("Level 1");
			dunningLevel.setIsActive(true);
			dunningLevel.setIsShowAllDue(false);
			dunningLevel.setIsShowNotDue(false);
			dunningLevel.setDaysBetweenDunning(0);
			dunningLevel.setDaysAfterDue(BigDecimal.ZERO);
			dunningLevel.setInterestPercent(BigDecimal.ZERO);
			dunningLevel.setFeeAmt(BigDecimal.ZERO);
			dunningLevel.setIsWriteOff(false);
			save(dunningLevel);
		}

		final IDunningContext dunningContext = dunningBL.createDunningContext(Env.getCtx(), dunningLevel, new Date(), ITrx.TRXNAME_None);
		dunningBL.createDunningCandidates(dunningContext);
		// like the user-triggered "create dunning docs" process, process the selected candidates only (here: the ones of the invoice)
		final AbstractDunningCandidateSource candidatesOfInvoice = new AbstractDunningCandidateSource()
		{
			@Override
			public Iterator<I_C_Dunning_Candidate> iterator()
			{
				return queryBL.createQueryBuilder(I_C_Dunning_Candidate.class)
						.addEqualsFilter(I_C_Dunning_Candidate.COLUMNNAME_AD_Table_ID, InterfaceWrapperHelper.getTableId(I_C_Invoice.class))
						.addEqualsFilter(I_C_Dunning_Candidate.COLUMNNAME_Record_ID, invoice.getC_Invoice_ID())
						.addEqualsFilter(I_C_Dunning_Candidate.COLUMNNAME_Processed, false)
						.addOnlyActiveRecordsFilter()
						.orderBy(I_C_Dunning_Candidate.COLUMNNAME_C_Dunning_Candidate_ID)
						.create()
						.list(I_C_Dunning_Candidate.class)
						.iterator();
			}
		};
		candidatesOfInvoice.setDunningContext(dunningContext);
		dunningBL.processCandidates(dunningContext, candidatesOfInvoice);

		final int dunningDocId = queryBL.createQueryBuilder(I_C_Dunning_Candidate.class)
				.addEqualsFilter(I_C_Dunning_Candidate.COLUMNNAME_AD_Table_ID, InterfaceWrapperHelper.getTableId(I_C_Invoice.class))
				.addEqualsFilter(I_C_Dunning_Candidate.COLUMNNAME_Record_ID, invoice.getC_Invoice_ID())
				.andCollectChildren(I_C_DunningDoc_Line_Source.COLUMN_C_Dunning_Candidate_ID)
				.andCollect(I_C_DunningDoc_Line_Source.COLUMN_C_DunningDoc_Line_ID)
				.andCollect(I_C_DunningDoc_Line.COLUMN_C_DunningDoc_ID)
				.orderBy(I_C_DunningDoc.COLUMNNAME_C_DunningDoc_ID)
				.create()
				.firstOptional(I_C_DunningDoc.class)
				.map(I_C_DunningDoc::getC_DunningDoc_ID)
				.orElseThrow(() -> new AdempiereException("No dunning letter was created for invoice " + invoice.getDocumentNo()));

		dunningDocIdsByIdentifier.put(dunningDocIdentifier, dunningDocId);
		invoiceIdentifiersByDunningDocIdentifier.put(dunningDocIdentifier, invoiceIdentifier);
	}

	private int getRecordId(@NonNull final String recordIdentifier)
	{
		final Integer dunningDocId = dunningDocIdsByIdentifier.get(recordIdentifier);
		if (dunningDocId != null)
		{
			return dunningDocId;
		}
		return invoiceTable.get(recordIdentifier).getC_Invoice_ID();
	}
}
