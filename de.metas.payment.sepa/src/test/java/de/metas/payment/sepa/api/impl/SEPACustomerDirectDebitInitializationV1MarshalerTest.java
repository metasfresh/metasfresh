package de.metas.payment.sepa.api.impl;

/*
 * #%L
 * de.metas.payment.sepa
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Env;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.payment.sepa.model.I_SEPA_Export;
import de.metas.payment.sepa.model.I_SEPA_Export_Line;
import de.metas.payment.sepa.sdd.jaxb.v1.Document;

public class SEPACustomerDirectDebitInitializationV1MarshalerTest
{
	private Properties ctx;
	private String trxName;
	private boolean printXmlDocumentAfterTest = false;

	private SEPACustomerDirectDebitInitializationV1Marshaler xmlGenerator = null;
	private Document xmlDocument;

	@Before
	public void beforeTest()
	{
		AdempiereTestHelper.get().init();
		this.ctx = Env.getCtx();
		this.trxName = ITrx.TRXNAME_None;

		this.xmlGenerator = new SEPACustomerDirectDebitInitializationV1Marshaler();
		this.xmlDocument = null;
		this.printXmlDocumentAfterTest = true;
	}

	@After
	public void afterTest()
	{
		if (printXmlDocumentAfterTest)
		{
			final String xmlDocumentStr = xmlDocument == null ? null : xmlGenerator.toString(xmlDocument);
			System.out.println("XMLDocument:-------------------------------------------------------------------------------"
					+ "\n" + xmlDocumentStr
					+ "\n-----------------------------------------------------------------------------------------");

			printXmlDocumentAfterTest = false;
		}
	}

	@Test
	public void test() throws Exception
	{
		final I_SEPA_Export sepaExport = createSEPAExport(
				"org" // SEPA_CreditorIdentifier
				, "INGBNL2A" // bic
		);
		createSEPAExportLine(sepaExport
				, "001" // SEPA_MandateRefNo
				, "NL31INGB0000000044" // IBAN
				, "INGBNL2A" // BIC
				, new BigDecimal("100") // amount
		);
		createSEPAExportLine(sepaExport
				, "002" // SEPA_MandateRefNo
				, "NL31INGB0000000044" // IBAN
				, "INGBNL2A" // BIC
				, new BigDecimal("30") // amount
		);

		xmlDocument = xmlGenerator.createDocument(sepaExport);

		Assert.assertThat("Invalid Document/CstmrDrctDbtInitn/GrpHdr/CtrlSum",
				xmlDocument.getCstmrDrctDbtInitn().getGrpHdr().getCtrlSum(), // actual
				Matchers.comparesEqualTo(new BigDecimal("130"))
				);
		Assert.assertThat("Invalid Document/CstmrDrctDbtInitn/GrpHdr/NbOfTxs",
				xmlDocument.getCstmrDrctDbtInitn().getGrpHdr().getNbOfTxs(), // actual
				Matchers.equalTo("2")
				);
		Assert.assertThat("Invalid Document/CstmrDrctDbtInitn/InitgPty/Nm",
				xmlDocument.getCstmrDrctDbtInitn().getGrpHdr().getInitgPty().getNm(), // actual
				Matchers.equalTo(sepaExport.getSEPA_CreditorIdentifier())
				);
	}

	private I_SEPA_Export createSEPAExport(
			final String SEPA_CreditorIdentifier
			, final String bic
			)
	{
		final I_SEPA_Export sepaExport = InterfaceWrapperHelper.create(ctx, I_SEPA_Export.class, trxName);
		sepaExport.setSEPA_CreditorIdentifier(SEPA_CreditorIdentifier);
		sepaExport.setSwiftCode(bic);
		InterfaceWrapperHelper.save(sepaExport);
		return sepaExport;
	}

	private I_SEPA_Export_Line createSEPAExportLine(final I_SEPA_Export sepaExport
			, final String SEPA_MandateRefNo
			, final String iban
			, final String bic
			, final BigDecimal amt
			)
	{
		final I_SEPA_Export_Line line = InterfaceWrapperHelper.newInstance(I_SEPA_Export_Line.class, sepaExport);
		line.setSEPA_Export(sepaExport);
		line.setIBAN(iban);
		line.setSwiftCode(bic);
		line.setAmt(amt);
		line.setSEPA_MandateRefNo(SEPA_MandateRefNo);

		InterfaceWrapperHelper.save(line);
		return line;
	}
}
