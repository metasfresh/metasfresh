package de.metas.payment.sepa.api.impl;

import de.metas.currency.CurrencyCode;
import de.metas.payment.sepa.api.ISEPADocumentDAO;
import de.metas.payment.sepa.model.I_SEPA_Export;
import de.metas.payment.sepa.model.I_SEPA_Export_Line;
import de.metas.payment.sepa.model.I_SEPA_Export_Line_Ref;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Bank;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.I_C_PaySelectionLine;

import java.math.BigDecimal;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.compiere.model.X_C_PaySelection.PAYSELECTIONTRXTYPE_CreditTransfer;

public class SEPATestHelper
{
	@NonNull private final ISEPADocumentDAO sepaDocumentDAO = Services.get(ISEPADocumentDAO.class);

	private I_C_Currency currency;
	private I_C_BPartner bPartner;
	private I_C_BPartner ungroupedPartner;
	private I_C_Bank bank;
	private I_C_Bank ungroupedBank;
	private I_C_BP_BankAccount bp_bankAccount;
	private I_C_BP_BankAccount ungrouped_bp_bankAccount;
	private I_C_Invoice firstInvoice;
	private I_C_Invoice secondInvoice;
	private I_C_Invoice ungroupedInvoice;
	@Getter
	private I_C_PaySelection paySelection;
	private I_C_PaySelectionLine firstGroupedPaySelectionLine;
	private I_C_PaySelectionLine secondGroupedPaySelectionLine;
	private I_C_PaySelectionLine ungroupedPaySelectionLine;

	@NonNull
	public I_SEPA_Export createSepaExport()
	{
		createMockData();
		final CreateSEPAExportFromPaySelectionCommand command = new CreateSEPAExportFromPaySelectionCommand(paySelection, true);
		return command.run();
	}
	
	public void createMockData()
	{
		currency = newInstance(I_C_Currency.class);
		currency.setISO_Code(CurrencyCode.USD.toThreeLetterCode());
		save(currency);

		final I_C_Country country = newInstance(I_C_Country.class);
		save(country);

		final I_C_Location location = newInstance(I_C_Location.class);
		location.setC_Country_ID(country.getC_Country_ID());
		location.setRegionName("regionName");
		location.setAddress1("addr1");
		location.setAddress2("addr2");
		location.setCity("city");
		location.setPostal("postal");
		save(location);

		final I_C_Location ungroupedLocation = newInstance(I_C_Location.class);
		ungroupedLocation.setC_Country_ID(country.getC_Country_ID());
		ungroupedLocation.setRegionName("regionName_2");
		ungroupedLocation.setAddress1("addr1_2");
		ungroupedLocation.setAddress2("addr2_2");
		ungroupedLocation.setCity("city_2");
		ungroupedLocation.setPostal("postal_2");
		save(location);

		bPartner = newInstance(I_C_BPartner.class);
		bPartner.setName("bpName");
		bPartner.setIsCompany(true);
		bPartner.setAD_OrgBP_ID(1000000);
		save(bPartner);

		ungroupedPartner = newInstance(I_C_BPartner.class);
		ungroupedPartner.setName("ungroupedPartnerName");
		ungroupedPartner.setIsCompany(true);
		ungroupedPartner.setAD_OrgBP_ID(1000001);
		save(ungroupedPartner);

		final I_C_BPartner_Location bPartnerLocation = newInstance(I_C_BPartner_Location.class);
		bPartnerLocation.setC_BPartner_ID(bPartner.getC_BPartner_ID());
		bPartnerLocation.setC_Location_ID(location.getC_Location_ID());
		save(bPartnerLocation);

		final I_C_BPartner_Location ungroupedPartnerLocation = newInstance(I_C_BPartner_Location.class);
		ungroupedPartnerLocation.setC_BPartner_ID(ungroupedPartner.getC_BPartner_ID());
		ungroupedPartnerLocation.setC_Location_ID(ungroupedLocation.getC_Location_ID());
		save(ungroupedPartnerLocation);

		bank = newInstance(I_C_Bank.class);
		bank.setName("bankName");
		bank.setSwiftCode("swiftCode");
		save(bank);

		ungroupedBank = newInstance(I_C_Bank.class);
		ungroupedBank.setName("ungroupedBankName");
		ungroupedBank.setSwiftCode("ungroupedSwiftCode");
		save(ungroupedBank);

		bp_bankAccount = newInstance(I_C_BP_BankAccount.class);
		bp_bankAccount.setC_BPartner_ID(bPartner.getC_BPartner_ID());
		bp_bankAccount.setC_Bank_ID(bank.getC_Bank_ID());
		bp_bankAccount.setC_Currency_ID(currency.getC_Currency_ID());
		bp_bankAccount.setIBAN("iban");
		bp_bankAccount.setRoutingNo("routingNo");
		bp_bankAccount.setSEPA_CreditorIdentifier("sepaCreditorIdentifier");
		save(bp_bankAccount);

		ungrouped_bp_bankAccount = newInstance(I_C_BP_BankAccount.class);
		ungrouped_bp_bankAccount.setC_BPartner_ID(ungroupedPartner.getC_BPartner_ID());
		ungrouped_bp_bankAccount.setC_Bank_ID(ungroupedBank.getC_Bank_ID());
		ungrouped_bp_bankAccount.setC_Currency_ID(currency.getC_Currency_ID());
		ungrouped_bp_bankAccount.setIBAN("ungroupedIban");
		ungrouped_bp_bankAccount.setRoutingNo("ungroupedRoutingNo");
		ungrouped_bp_bankAccount.setSEPA_CreditorIdentifier("ungroupedSepaCreditorIdentifier");
		save(ungrouped_bp_bankAccount);

		firstInvoice = newInstance(I_C_Invoice.class);
		firstInvoice.setC_BPartner_ID(bPartner.getC_BPartner_ID());
		firstInvoice.setC_BPartner_Location_ID(ungroupedPartnerLocation.getC_BPartner_Location_ID());
		firstInvoice.setDescription("firstInvoiceDescription");
		save(firstInvoice);

		firstInvoice = newInstance(I_C_Invoice.class);
		firstInvoice.setC_BPartner_ID(bPartner.getC_BPartner_ID());
		firstInvoice.setC_BPartner_Location_ID(ungroupedPartnerLocation.getC_BPartner_Location_ID());
		firstInvoice.setDescription("firstInvoiceDescription");
		save(firstInvoice);

		secondInvoice = newInstance(I_C_Invoice.class);
		secondInvoice.setC_BPartner_ID(bPartner.getC_BPartner_ID());
		secondInvoice.setC_BPartner_Location_ID(ungroupedPartnerLocation.getC_BPartner_Location_ID());
		secondInvoice.setDescription("secondInvoiceDescription");
		save(secondInvoice);

		ungroupedInvoice = newInstance(I_C_Invoice.class);
		ungroupedInvoice.setC_BPartner_ID(bPartner.getC_BPartner_ID());
		ungroupedInvoice.setC_BPartner_Location_ID(ungroupedPartnerLocation.getC_BPartner_Location_ID());
		ungroupedInvoice.setDescription("ungroupedInvoiceDescription");
		save(ungroupedInvoice);

		paySelection = newInstance(I_C_PaySelection.class);
		paySelection.setAD_Org_ID(1000000);
		paySelection.setPaySelectionTrxType(PAYSELECTIONTRXTYPE_CreditTransfer);
		paySelection.setC_BP_BankAccount_ID(bp_bankAccount.getC_BP_BankAccount_ID());
		saveRecord(paySelection);

		firstGroupedPaySelectionLine = newInstance(I_C_PaySelectionLine.class);
		firstGroupedPaySelectionLine.setC_PaySelection_ID(paySelection.getC_PaySelection_ID());
		firstGroupedPaySelectionLine.setAD_Org_ID(1000000);
		firstGroupedPaySelectionLine.setC_Invoice_ID(firstInvoice.getC_Invoice_ID());
		firstGroupedPaySelectionLine.setC_BP_BankAccount_ID(bp_bankAccount.getC_BP_BankAccount_ID());
		firstGroupedPaySelectionLine.setPayAmt(BigDecimal.TEN);
		firstGroupedPaySelectionLine.setC_BPartner_ID(bPartner.getC_BPartner_ID());
		firstGroupedPaySelectionLine.setReference("firstLine");
		save(firstGroupedPaySelectionLine);

		secondGroupedPaySelectionLine = newInstance(I_C_PaySelectionLine.class);
		secondGroupedPaySelectionLine.setAD_Org_ID(1000000);
		secondGroupedPaySelectionLine.setC_PaySelection_ID(paySelection.getC_PaySelection_ID());
		secondGroupedPaySelectionLine.setC_Invoice_ID(secondInvoice.getC_Invoice_ID());
		secondGroupedPaySelectionLine.setC_BP_BankAccount_ID(bp_bankAccount.getC_BP_BankAccount_ID());
		secondGroupedPaySelectionLine.setPayAmt(BigDecimal.ONE);
		secondGroupedPaySelectionLine.setC_BPartner_ID(bPartner.getC_BPartner_ID());
		secondGroupedPaySelectionLine.setReference("secondLine");
		save(secondGroupedPaySelectionLine);

		ungroupedPaySelectionLine = newInstance(I_C_PaySelectionLine.class);
		ungroupedPaySelectionLine.setAD_Org_ID(1000001);
		ungroupedPaySelectionLine.setC_PaySelection_ID(paySelection.getC_PaySelection_ID());
		ungroupedPaySelectionLine.setC_Invoice_ID(ungroupedInvoice.getC_Invoice_ID());
		ungroupedPaySelectionLine.setC_BP_BankAccount_ID(ungrouped_bp_bankAccount.getC_BP_BankAccount_ID());
		ungroupedPaySelectionLine.setPayAmt(BigDecimal.TEN);
		ungroupedPaySelectionLine.setC_BPartner_ID(ungroupedPartner.getC_BPartner_ID());
		save(ungroupedPaySelectionLine);
	}

	public void creditTransferIsGroupTransactionTrue(@NonNull final I_SEPA_Export sepaExport)
	{
		// three C_PaySelectionLine(s), two of them with the same aggregation key, the other one with a different aggregation key
		assertThat(sepaExport.getRecord_ID()).isEqualTo(paySelection.getC_PaySelection_ID());
		assertThat(sepaExport.getAD_Table_ID()).isEqualTo(InterfaceWrapperHelper.getTableId(I_C_PaySelection.class));
		final List<I_SEPA_Export_Line> lines = sepaDocumentDAO.retrieveLines(sepaExport);
		assertThat(lines.size()).isEqualTo(2);

		final I_SEPA_Export_Line ungroupedLine = lines.get(0);
		assertThat(ungroupedLine.getAD_Org_ID()).isEqualTo(1000001);
		assertThat(ungroupedLine.getAmt()).isEqualTo(ungroupedPaySelectionLine.getPayAmt());
		assertThat(ungroupedLine.getC_BP_BankAccount_ID()).isEqualTo(ungrouped_bp_bankAccount.getC_BP_BankAccount_ID());
		assertThat(ungroupedLine.getC_Currency_ID()).isEqualTo(currency.getC_Currency_ID());
		assertThat(ungroupedLine.getC_BPartner_ID()).isEqualTo(ungroupedPartner.getC_BPartner_ID());
		assertThat(ungroupedLine.getSwiftCode()).isEqualTo(ungroupedBank.getSwiftCode());
		assertThat(ungroupedLine.getSEPA_Export_ID()).isEqualTo(sepaExport.getSEPA_Export_ID());
		assertThat(ungroupedLine.isGroupLine()).isEqualTo(false);
		assertThat(ungroupedLine.getDescription()).isEqualTo(ungroupedInvoice.getDescription());
		assertThat(ungroupedLine.getStructuredRemittanceInfo()).isEqualTo(ungroupedPaySelectionLine.getReference());
		assertThat(ungroupedLine.getAD_Table_ID()).isEqualTo(InterfaceWrapperHelper.getTableId(I_C_PaySelectionLine.class));
		assertThat(ungroupedLine.getRecord_ID()).isEqualTo(ungroupedPaySelectionLine.getC_PaySelectionLine_ID());

		assertThat(ungroupedLine.getNumberOfReferences()).isEqualTo(0);

		final I_SEPA_Export_Line aggregatedLine = lines.get(1);
		assertThat(aggregatedLine.getAD_Org_ID()).isEqualTo(1000000);
		assertThat(aggregatedLine.getAmt()).isEqualTo(firstGroupedPaySelectionLine.getPayAmt().add(secondGroupedPaySelectionLine.getPayAmt()));
		assertThat(aggregatedLine.getC_BP_BankAccount_ID()).isEqualTo(bp_bankAccount.getC_BP_BankAccount_ID());
		assertThat(aggregatedLine.getC_Currency_ID()).isEqualTo(currency.getC_Currency_ID());
		assertThat(aggregatedLine.getC_BPartner_ID()).isEqualTo(bPartner.getC_BPartner_ID());
		assertThat(aggregatedLine.getSwiftCode()).isEqualTo(bank.getSwiftCode());
		assertThat(aggregatedLine.getSEPA_Export_ID()).isEqualTo(sepaExport.getSEPA_Export_ID());
		assertThat(aggregatedLine.isGroupLine()).isEqualTo(true);
		assertThat(aggregatedLine.getDescription()).isEqualTo(firstInvoice.getDescription() + "," + secondInvoice.getDescription());
		assertThat(aggregatedLine.getStructuredRemittanceInfo()).isEqualTo(firstGroupedPaySelectionLine.getReference() + "," + secondGroupedPaySelectionLine.getReference());
		assertThat(aggregatedLine.getRecord_ID()).isEqualTo(0);
		assertThat(aggregatedLine.getAD_Table_ID()).isEqualTo(0);

		assertThat(aggregatedLine.getNumberOfReferences()).isEqualTo(2);

		final List<I_SEPA_Export_Line_Ref> lineRefs = sepaDocumentDAO.retrieveLineReferences(aggregatedLine);
		final I_SEPA_Export_Line_Ref firstRef = lineRefs.stream()
				.filter(ref -> ref.getRecord_ID() == firstGroupedPaySelectionLine.getC_PaySelectionLine_ID())
				.findFirst()
				.orElse(null);

		assertThat(firstRef).isNotNull();
		assertThat(firstRef.getAD_Org_ID()).isEqualTo(1000000);
		assertThat(firstRef.getAD_Table_ID()).isEqualTo(InterfaceWrapperHelper.getTableId(I_C_PaySelectionLine.class));
		assertThat(firstRef.getRecord_ID()).isEqualTo(firstGroupedPaySelectionLine.getC_PaySelectionLine_ID());
		assertThat(firstRef.getSEPA_Export_ID()).isEqualTo(sepaExport.getSEPA_Export_ID());
		assertThat(firstRef.getSEPA_Export_Line_ID()).isEqualTo(aggregatedLine.getSEPA_Export_Line_ID());
		assertThat(firstRef.getDescription()).isEqualTo(firstInvoice.getDescription());
		assertThat(firstRef.getStructuredRemittanceInfo()).isEqualTo(firstGroupedPaySelectionLine.getReference());
		assertThat(firstRef.getAmt()).isEqualTo(firstGroupedPaySelectionLine.getPayAmt());
		assertThat(firstRef.getC_Currency_ID()).isEqualTo(aggregatedLine.getC_Currency_ID());

		final I_SEPA_Export_Line_Ref secondRef = lineRefs.stream()
				.filter(ref -> ref.getRecord_ID() == secondGroupedPaySelectionLine.getC_PaySelectionLine_ID())
				.findFirst()
				.orElse(null);

		assertThat(secondRef).isNotNull();
		assertThat(secondRef.getAD_Org_ID()).isEqualTo(1000000);
		assertThat(secondRef.getAD_Table_ID()).isEqualTo(InterfaceWrapperHelper.getTableId(I_C_PaySelectionLine.class));
		assertThat(secondRef.getRecord_ID()).isEqualTo(secondGroupedPaySelectionLine.getC_PaySelectionLine_ID());
		assertThat(secondRef.getSEPA_Export_ID()).isEqualTo(sepaExport.getSEPA_Export_ID());
		assertThat(secondRef.getSEPA_Export_Line_ID()).isEqualTo(aggregatedLine.getSEPA_Export_Line_ID());
		assertThat(secondRef.getDescription()).isEqualTo(secondInvoice.getDescription());
		assertThat(secondRef.getStructuredRemittanceInfo()).isEqualTo(secondGroupedPaySelectionLine.getReference());
		assertThat(secondRef.getAmt()).isEqualTo(secondGroupedPaySelectionLine.getPayAmt());
		assertThat(secondRef.getC_Currency_ID()).isEqualTo(aggregatedLine.getC_Currency_ID());
	}

	public void creditTransferIsGroupTransactionFalse(@NonNull final I_SEPA_Export sepaExport)
	{
		// three C_PaySelectionLine(s), two of them with the same aggregation key, the other one with a different aggregation key
		assertThat(sepaExport.getRecord_ID()).isEqualTo(paySelection.getC_PaySelection_ID());
		assertThat(sepaExport.getAD_Table_ID()).isEqualTo(InterfaceWrapperHelper.getTableId(I_C_PaySelection.class));
		final List<I_SEPA_Export_Line> lines = sepaDocumentDAO.retrieveLines(sepaExport);
		assertThat(lines.size()).isEqualTo(3);

		final I_SEPA_Export_Line firstLine = lines.get(0);
		assertThat(firstLine.getAD_Org_ID()).isEqualTo(1000000);
		assertThat(firstLine.getAmt()).isEqualTo(firstGroupedPaySelectionLine.getPayAmt());
		assertThat(firstLine.getC_BP_BankAccount_ID()).isEqualTo(bp_bankAccount.getC_BP_BankAccount_ID());
		assertThat(firstLine.getC_Currency_ID()).isEqualTo(currency.getC_Currency_ID());
		assertThat(firstLine.getC_BPartner_ID()).isEqualTo(bPartner.getC_BPartner_ID());
		assertThat(firstLine.getSwiftCode()).isEqualTo(bank.getSwiftCode());
		assertThat(firstLine.getSEPA_Export_ID()).isEqualTo(sepaExport.getSEPA_Export_ID());
		assertThat(firstLine.isGroupLine()).isEqualTo(false);
		assertThat(firstLine.getDescription()).isEqualTo(firstInvoice.getDescription());
		assertThat(firstLine.getStructuredRemittanceInfo()).isEqualTo(firstGroupedPaySelectionLine.getReference());
		assertThat(firstLine.getAD_Table_ID()).isEqualTo(InterfaceWrapperHelper.getTableId(I_C_PaySelectionLine.class));
		assertThat(firstLine.getRecord_ID()).isEqualTo(firstGroupedPaySelectionLine.getC_PaySelectionLine_ID());

		assertThat(firstLine.getNumberOfReferences()).isEqualTo(0);

		final I_SEPA_Export_Line secondLine = lines.get(1);
		assertThat(secondLine.getAD_Org_ID()).isEqualTo(1000000);
		assertThat(secondLine.getAmt()).isEqualTo(secondGroupedPaySelectionLine.getPayAmt());
		assertThat(secondLine.getC_BP_BankAccount_ID()).isEqualTo(bp_bankAccount.getC_BP_BankAccount_ID());
		assertThat(secondLine.getC_Currency_ID()).isEqualTo(currency.getC_Currency_ID());
		assertThat(secondLine.getC_BPartner_ID()).isEqualTo(bPartner.getC_BPartner_ID());
		assertThat(secondLine.getSwiftCode()).isEqualTo(bank.getSwiftCode());
		assertThat(secondLine.getSEPA_Export_ID()).isEqualTo(sepaExport.getSEPA_Export_ID());
		assertThat(secondLine.isGroupLine()).isEqualTo(false);
		assertThat(secondLine.getDescription()).isEqualTo(secondInvoice.getDescription());
		assertThat(secondLine.getStructuredRemittanceInfo()).isEqualTo(secondGroupedPaySelectionLine.getReference());
		assertThat(secondLine.getAD_Table_ID()).isEqualTo(InterfaceWrapperHelper.getTableId(I_C_PaySelectionLine.class));
		assertThat(secondLine.getRecord_ID()).isEqualTo(secondGroupedPaySelectionLine.getC_PaySelectionLine_ID());

		assertThat(secondLine.getNumberOfReferences()).isEqualTo(0);

		final I_SEPA_Export_Line ungroupedLine = lines.get(2);
		assertThat(ungroupedLine.getAD_Org_ID()).isEqualTo(1000001);
		assertThat(ungroupedLine.getAmt()).isEqualTo(ungroupedPaySelectionLine.getPayAmt());
		assertThat(ungroupedLine.getC_BP_BankAccount_ID()).isEqualTo(ungrouped_bp_bankAccount.getC_BP_BankAccount_ID());
		assertThat(ungroupedLine.getC_Currency_ID()).isEqualTo(currency.getC_Currency_ID());
		assertThat(ungroupedLine.getC_BPartner_ID()).isEqualTo(ungroupedPartner.getC_BPartner_ID());
		assertThat(ungroupedLine.getSwiftCode()).isEqualTo(ungroupedBank.getSwiftCode());
		assertThat(ungroupedLine.getSEPA_Export_ID()).isEqualTo(sepaExport.getSEPA_Export_ID());
		assertThat(ungroupedLine.isGroupLine()).isEqualTo(false);
		assertThat(ungroupedLine.getDescription()).isEqualTo(ungroupedInvoice.getDescription());
		assertThat(ungroupedLine.getStructuredRemittanceInfo()).isEqualTo(ungroupedPaySelectionLine.getReference());
		assertThat(ungroupedLine.getAD_Table_ID()).isEqualTo(InterfaceWrapperHelper.getTableId(I_C_PaySelectionLine.class));
		assertThat(ungroupedLine.getRecord_ID()).isEqualTo(ungroupedPaySelectionLine.getC_PaySelectionLine_ID());

		assertThat(ungroupedLine.getNumberOfReferences()).isEqualTo(0);
	}

	public void assertCommonDebitDirectCases(@NonNull final I_SEPA_Export sepaExport)
	{
		// three C_PaySelectionLine(s), two of them with the same aggregation key, the other one with a different aggregation key
		assertThat(sepaExport.getRecord_ID()).isEqualTo(paySelection.getC_PaySelection_ID());
		assertThat(sepaExport.getAD_Table_ID()).isEqualTo(InterfaceWrapperHelper.getTableId(I_C_PaySelection.class));
		assertThat(sepaExport.getSEPA_CreditorIdentifier()).isEqualTo(bp_bankAccount.getSEPA_CreditorIdentifier());
		final List<I_SEPA_Export_Line> lines = sepaDocumentDAO.retrieveLines(sepaExport);
		assertThat(lines.size()).isEqualTo(3);

		final I_SEPA_Export_Line firstLine = lines.get(0);
		assertThat(firstLine.getAD_Org_ID()).isEqualTo(1000000);
		assertThat(firstLine.getAmt()).isEqualTo(firstGroupedPaySelectionLine.getPayAmt());
		assertThat(firstLine.getC_BP_BankAccount_ID()).isEqualTo(bp_bankAccount.getC_BP_BankAccount_ID());
		assertThat(firstLine.getC_Currency_ID()).isEqualTo(currency.getC_Currency_ID());
		assertThat(firstLine.getC_BPartner_ID()).isEqualTo(bPartner.getC_BPartner_ID());
		assertThat(firstLine.getSwiftCode()).isEqualTo(bank.getSwiftCode());
		assertThat(firstLine.getSEPA_Export_ID()).isEqualTo(sepaExport.getSEPA_Export_ID());
		assertThat(firstLine.isGroupLine()).isEqualTo(false);
		assertThat(firstLine.getDescription()).isEqualTo(firstInvoice.getDescription());
		assertThat(firstLine.getStructuredRemittanceInfo()).isEqualTo(firstGroupedPaySelectionLine.getReference());
		assertThat(firstLine.getAD_Table_ID()).isEqualTo(InterfaceWrapperHelper.getTableId(I_C_PaySelectionLine.class));
		assertThat(firstLine.getRecord_ID()).isEqualTo(firstGroupedPaySelectionLine.getC_PaySelectionLine_ID());

		assertThat(firstLine.getNumberOfReferences()).isEqualTo(0);

		final I_SEPA_Export_Line secondLine = lines.get(1);
		assertThat(secondLine.getAD_Org_ID()).isEqualTo(1000000);
		assertThat(secondLine.getAmt()).isEqualTo(secondGroupedPaySelectionLine.getPayAmt());
		assertThat(secondLine.getC_BP_BankAccount_ID()).isEqualTo(bp_bankAccount.getC_BP_BankAccount_ID());
		assertThat(secondLine.getC_Currency_ID()).isEqualTo(currency.getC_Currency_ID());
		assertThat(secondLine.getC_BPartner_ID()).isEqualTo(bPartner.getC_BPartner_ID());
		assertThat(secondLine.getSwiftCode()).isEqualTo(bank.getSwiftCode());
		assertThat(secondLine.getSEPA_Export_ID()).isEqualTo(sepaExport.getSEPA_Export_ID());
		assertThat(secondLine.isGroupLine()).isEqualTo(false);
		assertThat(secondLine.getDescription()).isEqualTo(secondInvoice.getDescription());
		assertThat(secondLine.getStructuredRemittanceInfo()).isEqualTo(secondGroupedPaySelectionLine.getReference());
		assertThat(secondLine.getAD_Table_ID()).isEqualTo(InterfaceWrapperHelper.getTableId(I_C_PaySelectionLine.class));
		assertThat(secondLine.getRecord_ID()).isEqualTo(secondGroupedPaySelectionLine.getC_PaySelectionLine_ID());

		assertThat(secondLine.getNumberOfReferences()).isEqualTo(0);

		final I_SEPA_Export_Line ungroupedLine = lines.get(2);
		assertThat(ungroupedLine.getAD_Org_ID()).isEqualTo(1000001);
		assertThat(ungroupedLine.getAmt()).isEqualTo(ungroupedPaySelectionLine.getPayAmt());
		assertThat(ungroupedLine.getC_BP_BankAccount_ID()).isEqualTo(ungrouped_bp_bankAccount.getC_BP_BankAccount_ID());
		assertThat(ungroupedLine.getC_Currency_ID()).isEqualTo(currency.getC_Currency_ID());
		assertThat(ungroupedLine.getC_BPartner_ID()).isEqualTo(ungroupedPartner.getC_BPartner_ID());
		assertThat(ungroupedLine.getSwiftCode()).isEqualTo(ungroupedBank.getSwiftCode());
		assertThat(ungroupedLine.getSEPA_Export_ID()).isEqualTo(sepaExport.getSEPA_Export_ID());
		assertThat(ungroupedLine.isGroupLine()).isEqualTo(false);
		assertThat(ungroupedLine.getDescription()).isEqualTo(ungroupedInvoice.getDescription());
		assertThat(ungroupedLine.getStructuredRemittanceInfo()).isEqualTo(ungroupedPaySelectionLine.getReference());
		assertThat(ungroupedLine.getAD_Table_ID()).isEqualTo(InterfaceWrapperHelper.getTableId(I_C_PaySelectionLine.class));
		assertThat(ungroupedLine.getRecord_ID()).isEqualTo(ungroupedPaySelectionLine.getC_PaySelectionLine_ID());

		assertThat(ungroupedLine.getNumberOfReferences()).isEqualTo(0);
	}
}
