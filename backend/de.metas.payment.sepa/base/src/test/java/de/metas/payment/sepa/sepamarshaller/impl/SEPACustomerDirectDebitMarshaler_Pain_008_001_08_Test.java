package de.metas.payment.sepa.sepamarshaller.impl;

import de.metas.banking.BankAccountId;
import de.metas.banking.api.BankAccountService;
import de.metas.banking.mandate.DirectDebitMandate;
import de.metas.banking.mandate.DirectDebitMandateId;
import de.metas.banking.mandate.DirectDebitMandateType;
import de.metas.organization.OrgId;
import de.metas.payment.sepa.api.SEPAExportContext;
import de.metas.payment.sepa.api.SEPAExportLineId;
import de.metas.payment.sepa.model.I_SEPA_Export;
import de.metas.payment.sepa.model.I_SEPA_Export_Line;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

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
import static de.metas.payment.sepa.Pain001_001_09_XmlTestHelper.count;
import static de.metas.payment.sepa.Pain001_001_09_XmlTestHelper.parse;
import static de.metas.payment.sepa.Pain001_001_09_XmlTestHelper.text;
import static de.metas.payment.sepa.Pain001_001_09_XmlTestHelper.texts;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
	private static final String PMT_INF = "Document/CstmrDrctDbtInitn/PmtInf";
	private static final String CDTR_PSTL_ADR = PMT_INF + "/Cdtr/PstlAdr";

	private OrgId orgId;
	private int nextMandateId = 1;

	@BeforeEach
	public void beforeTest()
	{
		AdempiereTestHelper.get().init();

		orgId = AdempiereTestHelper.createOrgWithTimeZone();
		Env.setOrgId(Env.getCtx(), orgId);

		SpringContextHolder.registerJUnitBean(BankAccountService.newInstanceForUnitTesting());
	}

	@Test
	public void allEdgeData_validatesAgainstGbic5Xsd() throws Exception
	{
		// creditor: the org's linked partner, whose bill-to town consists only of characters outside the SEPA character set
		final I_C_BPartner orgPartner = createOrgPartner();
		createLocation(orgPartner, "€€", true, true, true);

		final I_SEPA_Export sepaExport = createSEPAExport("1000001");

		final SEPAExportContext.SEPAExportContextBuilder exportContext = SEPAExportContext.builder();

		// no BIC; name > 70 characters; mandate reference with umlauts and "&"; description > 140 characters; one-off mandate
		final I_SEPA_Export_Line line1 = createLine(sepaExport, "Müller & Söhne € Großhandel " + repeat("X", 60), "DE02120300000000202051", null, "Rechnung „Nr. 1“ " + repeat("Y", 140));
		exportContext.directDebitMandate(lineId(line1), createMandate("MREF-Ä&ö/001", false));

		// umlauts and "&" in the name; empty description; recurring
		final I_SEPA_Export_Line line2 = createLine(sepaExport, "Bäckerei Groß & Co", "DE02500105170137075030", "INGDDEFFXXX", "");
		exportContext.directDebitMandate(lineId(line2), createMandate("MREF-B2B-002", true));

		// no description at all; recurring
		final I_SEPA_Export_Line line3 = createLine(sepaExport, "Normal Debtor GmbH", "DE12500105170648489890", "INGDDEFFXXX", null);
		exportContext.directDebitMandate(lineId(line3), createMandate("MREF-B2B-003", true));

		// name consists only of characters outside the SEPA character set
		final I_SEPA_Export_Line line4 = createLine(sepaExport, "€ € €", "DE89370400440532013000", "COBADEFFXXX", "Rechnung 4");
		exportContext.directDebitMandate(lineId(line4), createMandate("MREF-B2B-004", true));

		assertValidAgainstXsd(marshal(sepaExport, exportContext.build()));
	}

	@Test
	public void creditorAddress_fullAddressOfDefaultBillToLocation() throws Exception
	{
		final I_C_BPartner orgPartner = createOrgPartner();
		createLocation(orgPartner, "Hamburg", true, false, true);
		createLocation(orgPartner, "Berlin", true, true, true);

		final Document doc = marshalOneLineExport();

		assertThat(text(doc, CDTR_PSTL_ADR + "/StrtNm")).isEqualTo("Teststraße 1");
		assertThat(text(doc, CDTR_PSTL_ADR + "/PstCd")).isEqualTo("10115");
		assertThat(text(doc, CDTR_PSTL_ADR + "/TwnNm")).isEqualTo("Berlin");
		assertThat(text(doc, CDTR_PSTL_ADR + "/Ctry")).isEqualTo("DE");
	}

	@Test
	public void creditorAddress_noDefault_firstActiveBillToLocationById() throws Exception
	{
		final I_C_BPartner orgPartner = createOrgPartner();
		createLocation(orgPartner, "Inactive Town", true, true, false);
		createLocation(orgPartner, "Ship-to Town", false, false, true);
		createLocation(orgPartner, "Hamburg", true, false, true);
		createLocation(orgPartner, "Bremen", true, false, true);

		final Document doc = marshalOneLineExport();

		assertThat(text(doc, CDTR_PSTL_ADR + "/TwnNm")).isEqualTo("Hamburg");
	}

	@Test
	public void creditorAddress_noBillToLocation_noPostalAddress() throws Exception
	{
		final I_C_BPartner orgPartner = createOrgPartner();
		createLocation(orgPartner, "Ship-to Town", false, false, true);

		final Document doc = marshalOneLineExport();

		assertThat(text(doc, PMT_INF + "/Cdtr/Nm")).isEqualTo("SEPA Test Creditor GmbH");
		assertThat(count(doc, CDTR_PSTL_ADR)).isZero();
	}

	@Test
	public void longDocumentNo_paymentAndEndToEndIdsStayUnique() throws Exception
	{
		createLocation(createOrgPartner(), "Berlin", true, true, true);
		final I_SEPA_Export sepaExport = createSEPAExport(repeat("1", 35));
		final SEPAExportContext.SEPAExportContextBuilder exportContext = SEPAExportContext.builder();
		for (int i = 1; i <= 10; i++)
		{
			final I_SEPA_Export_Line line = createLine(sepaExport, "Debtor " + i, "DE02120300000000202051", "BYLADEM1001", "Rechnung " + i);
			exportContext.directDebitMandate(lineId(line), createMandate("MREF-" + i, true));
		}

		final byte[] xml = marshal(sepaExport, exportContext.build());

		assertValidAgainstXsd(xml);
		final Document doc = parse(xml);
		assertThat(texts(doc, PMT_INF + "/PmtInfId")).hasSize(10).doesNotHaveDuplicates();
		assertThat(texts(doc, PMT_INF + "/DrctDbtTxInf/PmtId/EndToEndId")).hasSize(10).doesNotHaveDuplicates();
	}

	@Test
	public void noLineToExport_fails()
	{
		createLocation(createOrgPartner(), "Berlin", true, true, true);
		final I_SEPA_Export sepaExport = createSEPAExport("1000001");
		final I_SEPA_Export_Line line = createLine(sepaExport, "Debtor", "DE02120300000000202051", "BYLADEM1001", "Rechnung 1");
		line.setIsError(true);
		save(line);

		assertThatThrownBy(() -> marshal(sepaExport, SEPAExportContext.builder().build()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("NoLines");
	}

	/**
	 * @return the XSD-validated, parsed file of an export with one line
	 */
	private Document marshalOneLineExport() throws Exception
	{
		final I_SEPA_Export sepaExport = createSEPAExport("1000001");
		final I_SEPA_Export_Line line = createLine(sepaExport, "Debtor GmbH", "DE02120300000000202051", "BYLADEM1001", "Rechnung 1");
		final byte[] xml = marshal(sepaExport, SEPAExportContext.builder().directDebitMandate(lineId(line), createMandate("MREF-1", true)).build());
		assertValidAgainstXsd(xml);
		return parse(xml);
	}

	private static byte[] marshal(@NonNull final I_SEPA_Export sepaExport, @NonNull final SEPAExportContext exportContext)
	{
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		new SEPACustomerDirectDebitMarshaler_Pain_008_001_08(exportContext).marshal(sepaExport, out);
		return out.toByteArray();
	}

	private static SEPAExportLineId lineId(@NonNull final I_SEPA_Export_Line line)
	{
		return SEPAExportLineId.ofRepoId(line.getSEPA_Export_Line_ID());
	}

	private static I_SEPA_Export createSEPAExport(@NonNull final String documentNo)
	{
		final I_SEPA_Export sepaExport = newInstance(I_SEPA_Export.class);
		sepaExport.setDocumentNo(documentNo);
		sepaExport.setSEPA_CreditorName("SEPA Test Creditor GmbH");
		sepaExport.setSEPA_CreditorIdentifier("DE98ZZZ09999999999");
		sepaExport.setIBAN("DE89370400440532013000");
		sepaExport.setSwiftCode("COBADEFFXXX");
		sepaExport.setPaymentDate(Timestamp.valueOf("2026-11-02 00:00:00"));
		save(sepaExport);
		return sepaExport;
	}

	private I_C_BPartner createOrgPartner()
	{
		final I_C_BPartner orgPartner = createBPartner("SEPA Test Creditor GmbH");
		orgPartner.setAD_OrgBP_ID(orgId.getRepoId());
		save(orgPartner);
		return orgPartner;
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

	private static void createLocation(
			@NonNull final I_C_BPartner bpartner,
			@NonNull final String city,
			final boolean billTo,
			final boolean billToDefault,
			final boolean active)
	{
		final I_C_Country country = newInstance(I_C_Country.class);
		country.setCountryCode("DE");
		country.setName("DE");
		save(country);

		final I_C_Location location = newInstance(I_C_Location.class);
		location.setAddress1("Teststraße 1");
		location.setPostal("10115");
		location.setCity(city);
		location.setC_Country_ID(country.getC_Country_ID());
		save(location);

		final I_C_BPartner_Location bpLocation = newInstance(I_C_BPartner_Location.class);
		bpLocation.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		bpLocation.setC_Location_ID(location.getC_Location_ID());
		bpLocation.setName(city);
		bpLocation.setIsBillTo(billTo);
		bpLocation.setIsBillToDefault(billToDefault);
		bpLocation.setIsActive(active);
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
