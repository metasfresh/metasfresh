package de.metas.payment.spi.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXB;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;

import de.metas.payment.camt054.jaxb.AccountIdentification4Choice;
import de.metas.payment.camt054.jaxb.AccountNotification12;
import de.metas.payment.camt054.jaxb.ActiveOrHistoricCurrencyAndAmount;
import de.metas.payment.camt054.jaxb.AmountAndCurrencyExchange3;
import de.metas.payment.camt054.jaxb.AmountAndCurrencyExchangeDetails3;
import de.metas.payment.camt054.jaxb.BankToCustomerDebitCreditNotificationV06;
import de.metas.payment.camt054.jaxb.BankTransactionCodeStructure4;
import de.metas.payment.camt054.jaxb.BankTransactionCodeStructure5;
import de.metas.payment.camt054.jaxb.BankTransactionCodeStructure6;
import de.metas.payment.camt054.jaxb.CashAccount25;
import de.metas.payment.camt054.jaxb.CreditDebitCode;
import de.metas.payment.camt054.jaxb.CreditorReferenceInformation2;
import de.metas.payment.camt054.jaxb.CreditorReferenceType1Choice;
import de.metas.payment.camt054.jaxb.CreditorReferenceType2;
import de.metas.payment.camt054.jaxb.DateAndDateTimeChoice;
import de.metas.payment.camt054.jaxb.Document;
import de.metas.payment.camt054.jaxb.EntryDetails7;
import de.metas.payment.camt054.jaxb.EntryTransaction8;
import de.metas.payment.camt054.jaxb.GroupHeader58;
import de.metas.payment.camt054.jaxb.RemittanceInformation11;
import de.metas.payment.camt054.jaxb.ReportEntry8;
import de.metas.payment.camt054.jaxb.ReportingSource1Choice;
import de.metas.payment.camt054.jaxb.StructuredRemittanceInformation13;
import de.metas.payment.esr.dataloader.ESRStatement;
import de.metas.payment.esr.dataloader.impl.ESRDataImporterCamt54;
import de.metas.payment.esr.model.I_ESR_Import;
import lombok.NonNull;

/*
 * #%L
 * de.metas.payment.esr
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class ESRDataLoaderCamt54Tests
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void test() throws Exception
	{
		// group header (A-Level)
		// optional, to indicate this is a test
		final GroupHeader58 grpHdr = new GroupHeader58();
		grpHdr.setAddtlInf("TEST");

		// reporting source (B-Level)
		// https://www.six-interbank-clearing.com/dam/downloads/de/standardization/iso/swiss-recommendations/implementation-guidelines-camt.pdf
		// Seite 17 and Seite 58 (de)
		// ..just setting one of the possible values, don't know yet which one to take
		// note: this is an "AOS (Additional Optional Services)", see Seite 14. It can be used to distinguish between different sorts of camt54 messages
		final ReportingSource1Choice rptgSrc = new ReportingSource1Choice();
		rptgSrc.setPrtry("C53F");

		// IBAN (B-Level)
		final AccountIdentification4Choice id = new AccountIdentification4Choice();
		id.setIBAN("CH-1234-12354");

		final CashAccount25 acct = new CashAccount25();
		acct.setId(id);

		// amount (C-Level)
		final ActiveOrHistoricCurrencyAndAmount amtOfStatement = new ActiveOrHistoricCurrencyAndAmount();
		amtOfStatement.setValue(new BigDecimal("23.42"));
		amtOfStatement.setCcy("CHF");

		// dates (C-Level)
		// Seite 83
		final DateAndDateTimeChoice bookgDt = new DateAndDateTimeChoice();
		bookgDt.setDt(mkXMLGegrorianCal("2017-05-23"));
		final DateAndDateTimeChoice valDt = new DateAndDateTimeChoice();
		valDt.setDt(mkXMLGegrorianCal("2017-05-24"));

		// bank transaction code (C-Level)
		// Seite 83
		// the following sais credit, i.e. incoming payment: Domain = PMNT / Family = RCDT / Sub-Family = VCOM
		// there could also be reverse, which would be similar but with Sub-Family = CAJT
		final BankTransactionCodeStructure6 fmly = new BankTransactionCodeStructure6();
		fmly.setCd("RCDT");
		fmly.setSubFmlyCd("VCOM");

		final BankTransactionCodeStructure5 domn = new BankTransactionCodeStructure5();
		domn.setCd("PMNT");
		domn.setFmly(fmly);

		final BankTransactionCodeStructure4 bkTxCd = new BankTransactionCodeStructure4();
		bkTxCd.setDomn(domn);

		// transaction amount (D-Level)
		// see Seite 84. Note that activeOrHistoricCurrencyAnd13DecimalAmount is added in two places
		final ActiveOrHistoricCurrencyAndAmount amtOfTransaction = new ActiveOrHistoricCurrencyAndAmount();
		amtOfTransaction.setValue(new BigDecimal("23.42"));
		amtOfTransaction.setCcy("CHF");

		final AmountAndCurrencyExchangeDetails3 amountAndCurrencyExchangeDetails3 = new AmountAndCurrencyExchangeDetails3();
		amountAndCurrencyExchangeDetails3.setAmt(amtOfTransaction);

		final AmountAndCurrencyExchange3 amountAndCurrencyExchange3 = new AmountAndCurrencyExchange3();
		amountAndCurrencyExchange3.setTxAmt(amountAndCurrencyExchangeDetails3);
		
		// ESR reference number (D-Level)
		// https://www.six-interbank-clearing.com/dam/downloads/de/standardization/iso/swiss-recommendations/implementation-guidelines-camt.pdf
		// Seite 63
		final CreditorReferenceInformation2 cdtrRefInf = new CreditorReferenceInformation2();
		cdtrRefInf.setRef("ESR-Referenznummer");

		// set the creditorReferenceInformation2's type to specify that "ref" contains an ESR reference
		// see Seite 84
		final CreditorReferenceType1Choice cdOrPrtry = new CreditorReferenceType1Choice();
		cdOrPrtry.setPrtry("ISR Reference");
		final CreditorReferenceType2 tp = new CreditorReferenceType2();
		tp.setCdOrPrtry(cdOrPrtry);

		cdtrRefInf.setTp(tp);

		final StructuredRemittanceInformation13 strd = new StructuredRemittanceInformation13();
		strd.setCdtrRefInf(cdtrRefInf);

		final RemittanceInformation11 rmtInf = new RemittanceInformation11();
		rmtInf.getStrd().add(strd);

		final EntryTransaction8 txDtl = new EntryTransaction8(); // transaction detail
		txDtl.setBkTxCd(bkTxCd);
		txDtl.setRmtInf(rmtInf);
		txDtl.setAmt(amtOfTransaction);
		txDtl.setAmtDtls(amountAndCurrencyExchange3);

		final EntryDetails7 entryDetails7 = new EntryDetails7();
		entryDetails7.getTxDtls().add(txDtl);

		final ReportEntry8 ntry = new ReportEntry8();
		ntry.setAmt(amtOfStatement);
		ntry.setBookgDt(bookgDt);
		ntry.setValDt(valDt);
		ntry.setCdtDbtInd(CreditDebitCode.CRDT);
		ntry.getNtryDtls().add(entryDetails7);

		//
		// add them
		final AccountNotification12 ntfctn = new AccountNotification12();
		ntfctn.setAcct(acct);
		ntfctn.setRptgSrc(rptgSrc);
		ntfctn.getNtry().add(ntry);

		// this is the actual message to be sent inside a "document"
		final BankToCustomerDebitCreditNotificationV06 bkToCstmrDbtCdtNtfctn = new BankToCustomerDebitCreditNotificationV06();
		bkToCstmrDbtCdtNtfctn.setGrpHdr(grpHdr);
		bkToCstmrDbtCdtNtfctn.getNtfctn().add(ntfctn);
		final Document document = new Document();
		document.setBkToCstmrDbtCdtNtfctn(bkToCstmrDbtCdtNtfctn);

		// JAXB.marshal(document, System.out);

		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		JAXB.marshal(document, out);
		
		final I_ESR_Import esrImport = newInstance(I_ESR_Import.class);
		save(esrImport);

		final ESRStatement esrStatement = new ESRDataImporterCamt54(esrImport, new ByteArrayInputStream(out.toByteArray())).load();
		assertThat(esrStatement, notNullValue());
		assertThat(esrStatement.getTransactions().size(), is(1));
		assertThat(esrStatement.getTransactions().get(0).getAmount(), is(new BigDecimal("23.42")));
	}

	private XMLGregorianCalendar mkXMLGegrorianCal(@NonNull final String dateStr) throws ParseException, DatatypeConfigurationException
	{
		final DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		final Date dDate = format.parse(dateStr);

		GregorianCalendar gregory = new GregorianCalendar();
		gregory.setTime(dDate);

		return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory);
	}
}
