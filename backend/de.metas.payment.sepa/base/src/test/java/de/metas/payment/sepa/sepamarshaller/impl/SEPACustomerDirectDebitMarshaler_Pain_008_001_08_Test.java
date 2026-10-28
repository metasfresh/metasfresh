package de.metas.payment.sepa.sepamarshaller.impl;

import de.metas.banking.BankAccountId;
import de.metas.banking.api.BankAccountService;
import de.metas.banking.api.BankRepository;
import de.metas.banking.mandate.DirectDebitMandate;
import de.metas.banking.mandate.DirectDebitMandateId;
import de.metas.banking.mandate.DirectDebitMandateType;
import de.metas.currency.CurrencyRepository;
import de.metas.organization.OrgId;
import de.metas.payment.sepa.api.SEPAExportContext;
import de.metas.payment.sepa.model.I_SEPA_Export;
import de.metas.payment.sepa.model.I_SEPA_Export_Line;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.payment.sepa.base
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

public class SEPACustomerDirectDebitMarshaler_Pain_008_001_08_Test
{
	private static final String XSD_RESOURCE = "/pain.008.001.08_GBIC_5.xsd";

	private OrgId orgId;
	private int nextMandateId = 1;

	@BeforeEach
	public void beforeTest()
	{
		AdempiereTestHelper.get().init();

		orgId = AdempiereTestHelper.createOrgWithTimeZone();
		Env.setOrgId(Env.getCtx(), orgId);

		SpringContextHolder.registerJUnitBean(new BankAccountService(new BankRepository(), new CurrencyRepository()));
	}

	@Test
	public void allEdgeData_validatesAgainstGbic5Xsd() throws Exception
	{
		// creditor: the org's linked partner, whose bill-to town consists only of characters outside the SEPA character set
		final I_C_BPartner orgPartner = createBPartner("SEPA Test Creditor GmbH");
		orgPartner.setAD_OrgBP_ID(orgId.getRepoId());
		save(orgPartner);
		createBillToLocation(orgPartner, "Teststraße 1", "10115", "€€");

		final I_SEPA_Export sepaExport = newInstance(I_SEPA_Export.class);
		sepaExport.setDocumentNo("1000001");
		sepaExport.setSEPA_CreditorName("SEPA Test Creditor GmbH");
		sepaExport.setSEPA_CreditorIdentifier("DE98ZZZ09999999999");
		sepaExport.setIBAN("DE89370400440532013000");
		sepaExport.setSwiftCode("COBADEFFXXX");
		sepaExport.setPaymentDate(Timestamp.valueOf("2026-11-02 00:00:00"));
		save(sepaExport);

		final SEPAExportContext.SEPAExportContextBuilder exportContext = SEPAExportContext.builder();

		// no BIC; name > 70 characters; mandate reference with umlauts and "&"; description > 140 characters; one-off mandate
		final I_SEPA_Export_Line line1 = createLine(sepaExport, "Müller & Söhne € Großhandel " + repeat("X", 60), "DE02120300000000202051", null, "Rechnung „Nr. 1“ " + repeat("Y", 140));
		exportContext.directDebitMandate(line1.getSEPA_Export_Line_ID(), createMandate("MREF-Ä&ö/001", false));

		// umlauts and "&" in the name; empty description; recurring
		final I_SEPA_Export_Line line2 = createLine(sepaExport, "Bäckerei Groß & Co", "DE02500105170137075030", "INGDDEFFXXX", "");
		exportContext.directDebitMandate(line2.getSEPA_Export_Line_ID(), createMandate("MREF-B2B-002", true));

		// no description at all; recurring
		final I_SEPA_Export_Line line3 = createLine(sepaExport, "Normal Debtor GmbH", "DE12500105170648489890", "INGDDEFFXXX", null);
		exportContext.directDebitMandate(line3.getSEPA_Export_Line_ID(), createMandate("MREF-B2B-003", true));

		// name consists only of characters outside the SEPA character set
		final I_SEPA_Export_Line line4 = createLine(sepaExport, "€ € €", "DE89370400440532013000", "COBADEFFXXX", "Rechnung 4");
		exportContext.directDebitMandate(line4.getSEPA_Export_Line_ID(), createMandate("MREF-B2B-004", true));

		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		new SEPACustomerDirectDebitMarshaler_Pain_008_001_08(exportContext.build()).marshal(sepaExport, out);

		assertValidAgainstXsd(out.toByteArray());
	}

	private static void assertValidAgainstXsd(final byte[] xml) throws Exception
	{
		final URL xsd = SEPACustomerDirectDebitMarshaler_Pain_008_001_08_Test.class.getResource(XSD_RESOURCE);
		assertThat(xsd).as("XSD %s on the classpath", XSD_RESOURCE).isNotNull();

		SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
				.newSchema(xsd)
				.newValidator()
				.validate(new StreamSource(new ByteArrayInputStream(xml)));
	}

	private static I_C_BPartner createBPartner(@NonNull final String name)
	{
		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		bpartner.setName(name);
		bpartner.setValue(name);
		save(bpartner);
		return bpartner;
	}

	private static void createBillToLocation(
			@NonNull final I_C_BPartner bpartner,
			@NonNull final String address1,
			@NonNull final String postal,
			@NonNull final String city)
	{
		final I_C_Country country = newInstance(I_C_Country.class);
		country.setCountryCode("DE");
		country.setName("DE");
		save(country);

		final I_C_Location location = newInstance(I_C_Location.class);
		location.setAddress1(address1);
		location.setPostal(postal);
		location.setCity(city);
		location.setC_Country_ID(country.getC_Country_ID());
		save(location);

		final I_C_BPartner_Location bpLocation = newInstance(I_C_BPartner_Location.class);
		bpLocation.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		bpLocation.setC_Location_ID(location.getC_Location_ID());
		bpLocation.setName("Billing");
		bpLocation.setIsBillTo(true);
		bpLocation.setIsBillToDefault(true);
		bpLocation.setIsActive(true);
		save(bpLocation);
	}

	private static I_SEPA_Export_Line createLine(
			@NonNull final I_SEPA_Export sepaExport,
			@NonNull final String debtorName,
			@NonNull final String iban,
			@Nullable final String swiftCode,
			@Nullable final String description)
	{
		final I_C_BPartner debtor = createBPartner(debtorName);

		final I_SEPA_Export_Line line = newInstance(I_SEPA_Export_Line.class);
		line.setSEPA_Export(sepaExport);
		line.setC_BPartner_ID(debtor.getC_BPartner_ID());
		line.setIBAN(iban);
		line.setSwiftCode(swiftCode);
		line.setAmt(new BigDecimal("75.00"));
		line.setDescription(description);
		line.setIsActive(true);
		line.setIsError(false);
		save(line);
		return line;
	}

	private DirectDebitMandate createMandate(@NonNull final String reference, final boolean recurring)
	{
		final int id = nextMandateId++;
		return DirectDebitMandate.builder()
				.id(DirectDebitMandateId.ofRepoId(id))
				.bankAccountId(BankAccountId.ofRepoId(id))
				.orgId(orgId)
				.type(DirectDebitMandateType.B2B)
				.reference(reference)
				.mandateDate(LocalDate.of(2013, 9, 23))
				.isRecurring(recurring)
				.build();
	}

	private static String repeat(@NonNull final String text, final int times)
	{
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < times; i++)
		{
			sb.append(text);
		}
		return sb.toString();
	}
}
