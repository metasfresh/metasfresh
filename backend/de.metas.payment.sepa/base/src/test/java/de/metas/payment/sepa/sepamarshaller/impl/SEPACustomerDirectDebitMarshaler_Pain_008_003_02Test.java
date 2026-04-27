package de.metas.payment.sepa.sepamarshaller.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;

import de.metas.banking.api.BankAccountService;
import de.metas.banking.api.BankRepository;
import de.metas.currency.CurrencyRepository;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.money.CurrencyId;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.payment.sepa.api.SEPAProtocol;
import de.metas.payment.sepa.jaxb.sct.pain_008_003_02.Document;
import de.metas.payment.sepa.model.I_SEPA_Export;
import de.metas.payment.sepa.model.I_SEPA_Export_Line;
import de.metas.user.UserRepository;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.payment.sepa.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

class SEPACustomerDirectDebitMarshaler_Pain_008_003_02Test
{

	private SEPACustomerDirectDebitMarshaler_Pain_008_003_02 xmlGenerator;
	private Document xmlDocument;

	private CurrencyId eur;
	private CurrencyId chf;

	@BeforeEach
	public void beforeTest()
	{
		AdempiereTestHelper.get().init();
		Services.registerService(IBPartnerBL.class, new BPartnerBL(new UserRepository()));
		final BankAccountService bankAccountService = new BankAccountService(new BankRepository(), new CurrencyRepository()); 
		SpringContextHolder.registerJUnitBean(bankAccountService);

		this.xmlGenerator = new SEPACustomerDirectDebitMarshaler_Pain_008_003_02(bankAccountService);
		this.xmlDocument = null;

		eur = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);
		chf = PlainCurrencyDAO.createCurrencyId(CurrencyCode.CHF);

	}

	@Test
	public void createDocument_batch() throws Exception
	{
		final I_SEPA_Export sepaExport = createSEPAExport(
				"SEPA_CreditorName", // SEPA_CreditorName
				"SEPA_CreditorIdentifier", // SEPA_CreditorIdentifier
				"INGBNL2A" // bic
		);
		createSEPAExportLine(sepaExport,
				"001",// SEPA_MandateRefNo
				"NL31INGB0000000044",// IBAN
				"INGBNL2A", // BIC
				new BigDecimal("100"), // amount
				eur);
		createSEPAExportLine(sepaExport,
				"002", // SEPA_MandateRefNo
				"NL31INGB0000000044", // IBAN
				"INGBNL2A",// BIC
				new BigDecimal("30"), // amount
				eur);

		createSEPAExportLine(sepaExport,
				"002", // SEPA_MandateRefNo
				"NL31INGB0000000044", // IBAN
				"INGBNL2A",// BIC
				new BigDecimal("40"), // amount
				chf);

		// invoke the method under test
		xmlDocument = xmlGenerator.createDocument(sepaExport);

		assertThat(xmlDocument.getCstmrDrctDbtInitn().getGrpHdr().getCtrlSum()).isEqualByComparingTo("170");
		assertThat(xmlDocument.getCstmrDrctDbtInitn().getGrpHdr().getNbOfTxs()).isEqualTo("3");
		assertThat(xmlDocument.getCstmrDrctDbtInitn().getGrpHdr().getInitgPty().getNm()).isEqualTo("SEPA_CreditorName");

		assertThat(xmlDocument.getCstmrDrctDbtInitn().getPmtInf()).allSatisfy(pmtInf -> assertThat(pmtInf.getCdtr().getNm()).isEqualTo("bankAccount.A_Name"));
		assertThat(xmlDocument.getCstmrDrctDbtInitn().getPmtInf()).allSatisfy(pmtInf -> assertThat(pmtInf.getCdtrSchmeId().getId().getPrvtId().getOthr().getId()).isEqualTo("SEPA_CreditorIdentifier"));
	}

	private I_SEPA_Export createSEPAExport(
			final String SEPA_CreditorName,
			final String SEPA_CreditorIdentifier,
			final String bic)
	{
		final I_SEPA_Export sepaExport = newInstance(I_SEPA_Export.class);
		sepaExport.setSEPA_Protocol(SEPAProtocol.DIRECT_DEBIT_PAIN_008_003_02.getCode());
		sepaExport.setSEPA_CreditorName(SEPA_CreditorName);
		sepaExport.setSEPA_CreditorIdentifier(SEPA_CreditorIdentifier);
		sepaExport.setSwiftCode(bic);
		sepaExport.setIsExportBatchBookings(true);
		save(sepaExport);

		return sepaExport;
	}

	private I_SEPA_Export_Line createSEPAExportLine(
			final I_SEPA_Export sepaExport,
			final String SEPA_MandateRefNo,
			final String iban,
			final String swiftCode,
			final BigDecimal amt,
			final CurrencyId currencyId)
	{
		return createSEPAExportLine(sepaExport, SEPA_MandateRefNo, iban, swiftCode, amt, currencyId, null, null, null, null);
	}

	private I_SEPA_Export_Line createSEPAExportLine(
			final I_SEPA_Export sepaExport,
			final String SEPA_MandateRefNo,
			final String iban,
			final String swiftCode,
			final BigDecimal amt,
			final CurrencyId currencyId,
			final String aStreet,
			final String aZip,
			final String aCity,
			final String aCountry)
	{

		final I_C_BP_BankAccount bankAccount = newInstance(I_C_BP_BankAccount.class);
		bankAccount.setC_Currency_ID(currencyId.getRepoId());
		bankAccount.setIBAN(iban);
		bankAccount.setSwiftCode(swiftCode);
		bankAccount.setIsEsrAccount(true);
		bankAccount.setA_Name("bankAccount.A_Name");
		bankAccount.setA_Street(aStreet);
		bankAccount.setA_Zip(aZip);
		bankAccount.setA_City(aCity);
		bankAccount.setA_Country(aCountry);
		save(bankAccount);

		final I_SEPA_Export_Line line = newInstance(I_SEPA_Export_Line.class);
		line.setIBAN(iban);
		line.setSwiftCode(swiftCode);
		line.setAmt(amt);
		line.setC_Currency_ID(currencyId.getRepoId());
		line.setSEPA_MandateRefNo(SEPA_MandateRefNo);

		line.setC_BP_BankAccount(bankAccount);
		line.setSEPA_Export(sepaExport);
		line.setIsActive(true);
		line.setIsError(false);
		save(line);

		return line;
	}

	// ----------------------------------------------------------------------------------------------
	// Address resolution — bank account is authoritative (me03#29173).
	// ----------------------------------------------------------------------------------------------

	@Test
	public void creditorAddress_bankAccountAuthoritative_fullAddress()
	{
		final I_SEPA_Export sepaExport = createSEPAExport("Creditor", "CRED1", "INGBNL2A");
		createSEPAExportLine(sepaExport,
				"001", "NL31INGB0000000044", "INGBNL2A",
				new BigDecimal("100"), eur,
				"Bankstrasse 1", "8000", "Zuerich", "CH");

		xmlDocument = xmlGenerator.createDocument(sepaExport);

		final var cdtr = xmlDocument.getCstmrDrctDbtInitn().getPmtInf().get(0).getCdtr();
		assertThat(cdtr.getNm()).isEqualTo("bankAccount.A_Name");
		assertThat(cdtr.getPstlAdr()).isNotNull();
		assertThat(cdtr.getPstlAdr().getCtry()).isEqualTo("CH");
		assertThat(cdtr.getPstlAdr().getAdrLine()).containsExactly("Bankstrasse 1", "8000 Zuerich");
	}

	@Test
	public void creditorAddress_bankAccountAuthoritative_partialAddress_streetOnly()
	{
		// Bank account has only the street populated. Under the new contract this
		// is emitted as-is — previously the entire address element was dropped
		// because isAddressComplete() returned false.
		final I_SEPA_Export sepaExport = createSEPAExport("Creditor", "CRED1", "INGBNL2A");
		createSEPAExportLine(sepaExport,
				"001", "NL31INGB0000000044", "INGBNL2A",
				new BigDecimal("100"), eur,
				"Bankstrasse 1", null, null, null);

		xmlDocument = xmlGenerator.createDocument(sepaExport);

		final var cdtr = xmlDocument.getCstmrDrctDbtInitn().getPmtInf().get(0).getCdtr();
		assertThat(cdtr.getPstlAdr()).isNotNull();
		assertThat(cdtr.getPstlAdr().getCtry()).isNull();
		assertThat(cdtr.getPstlAdr().getAdrLine()).containsExactly("Bankstrasse 1");
	}

	@Test
	public void creditorAddress_bankAccountEmpty_noPstlAdr()
	{
		// All address fields blank → keep previous behaviour: no PstlAdr element at all.
		final I_SEPA_Export sepaExport = createSEPAExport("Creditor", "CRED1", "INGBNL2A");
		createSEPAExportLine(sepaExport,
				"001", "NL31INGB0000000044", "INGBNL2A",
				new BigDecimal("100"), eur);

		xmlDocument = xmlGenerator.createDocument(sepaExport);

		final var cdtr = xmlDocument.getCstmrDrctDbtInitn().getPmtInf().get(0).getCdtr();
		assertThat(cdtr.getNm()).isEqualTo("bankAccount.A_Name");
		assertThat(cdtr.getPstlAdr()).isNull();
	}

}
