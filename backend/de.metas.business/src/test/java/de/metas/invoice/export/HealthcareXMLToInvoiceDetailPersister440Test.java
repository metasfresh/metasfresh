/*
 * #%L
 * de.metas.business
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

package de.metas.invoice.export;

import de.metas.bpartner.BPartnerId;
import de.metas.business.TestInvoice;
import de.metas.business.TestInvoiceLine;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.detail.InvoiceWithDetailsRepository;
import de.metas.lang.SOTrx;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.Invoice440RequestConversionService;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.XmlRequest;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Invoice_Detail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class HealthcareXMLToInvoiceDetailPersister440Test
{
	private final ZoneId timeZone = ZoneId.of("Europe/Berlin");
	private OrgId orgId;
	private TestInvoice testInvoice;
	private XmlRequest xmlRequest;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		orgId = AdempiereTestHelper.createOrgWithTimeZone("org", timeZone);
		testInvoice = TestInvoice.builder()
				.orgId(orgId)
				.soTrx(SOTrx.SALES)
				.customerId(BPartnerId.ofRepoId(10))
				.testInvoiceLine(TestInvoiceLine.builder()
										 .productId(ProductId.ofRepoId(20))
										 .externalIds("doesnt_really_matter_1")
										 .uomId(UomId.ofRepoId(1))
										 .build())
				.build().createInvoiceRecord();

		final InputStream inputStream = getClass().getResourceAsStream("/de/metas/invoice/export/md_440_tp_kvg_de.xml");

		final Invoice440RequestConversionService requestConverter = new Invoice440RequestConversionService();
		xmlRequest = requestConverter.toCrossVersionRequest(inputStream);
	}

	private Timestamp parseTimestamp(final String localDate)
	{
		return Timestamp.from(LocalDate.parse(localDate).atStartOfDay(timeZone).toInstant());
	}

	/**
	 * Create a C_Invoice (with just one line) and then verifies that the correct I_C_Invoice_Details are generated.
	 */
	@Test
	void extractInvoiceDetails()
	{
		// given
		testInvoice = TestInvoice.builder()
				.orgId(orgId)
				.soTrx(SOTrx.SALES)
				.customerId(BPartnerId.ofRepoId(10))
				.testInvoiceLine(TestInvoiceLine.builder()
										 .productId(ProductId.ofRepoId(20))
										 .externalIds("doesnt_really_matter_1")
										 .uomId(UomId.ofRepoId(1))
										 .build())
				.build().createInvoiceRecord();

		final InputStream inputStream = getClass().getResourceAsStream("/de/metas/invoice/export/md_440_tp_kvg_de.xml");

		final Invoice440RequestConversionService requestConverter = new Invoice440RequestConversionService();
		xmlRequest = requestConverter.toCrossVersionRequest(inputStream);

		// when
		new HealthcareXMLToInvoiceDetailPersister(new InvoiceWithDetailsRepository()).extractInvoiceDetails(xmlRequest, testInvoice.getInvoiceRecord());

		// then
		final List<I_C_Invoice_Detail> records = POJOLookupMap.get().getRecords(I_C_Invoice_Detail.class);
		final int invoiceId = InvoiceId.toRepoId(testInvoice.getInvoiceId());

		assertThat(records)
				//.hasSize(39)
				.extracting("C_Invoice_ID", "C_InvoiceLine_ID", "Label", "Description", "Date")
				.containsExactlyInAnyOrder(
						//.contains(
						tuple(invoiceId, 0, "Biller_GLN", "2011234567890", null),
						tuple(invoiceId, 0, "Biller_ZSR", "H121111", null),
						tuple(invoiceId, 0, "Biller_Email", "info@biller.ch", null),
						tuple(invoiceId, 0, "Biller_Phone", "061 956 99 00", null),
						tuple(invoiceId, 0, "Biller_ZIP", "4414", null),
						tuple(invoiceId, 0, "Biller_City", "Frenkendorf", null),
						tuple(invoiceId, 0, "Biller_Street", "Billerweg 128", null),

						tuple(invoiceId, 0, "Biller_GivenName", "Biller AG", null),
						tuple(invoiceId, 0, "Biller_FamilyName", "Abteilung Inkasso", null),
						tuple(invoiceId, 0, "Guarantor_Salutation", "Herr", null),
						tuple(invoiceId, 0, "Guarantor_GivenName", "Xaver", null),
						tuple(invoiceId, 0, "Guarantor_FamilyName", "Garant", null),
						tuple(invoiceId, 0, "Guarantor_Street", "Garantenallee 12", null),
						tuple(invoiceId, 0, "Guarantor_ZIP", "7300", null),
						tuple(invoiceId, 0, "Guarantor_City", "Chur", null),
						tuple(invoiceId, 0, "Patient_Salutation", "Herr", null),
						tuple(invoiceId, 0, "Patient_GivenName", "Peter", null),
						tuple(invoiceId, 0, "Patient_FamilyName", "Muster", null),
						tuple(invoiceId, 0, "Patient_BirthDate", null, parseTimestamp("1964-02-28")),
						tuple(invoiceId, 0, "Patient_Street", "Musterstrasse 5", null),
						tuple(invoiceId, 0, "Patient_ZIP", "7304", null),
						tuple(invoiceId, 0, "Patient_City", "Maienfeld", null),
						tuple(invoiceId, 0, "Patient_SSN", "7561234567890", null),
						tuple(invoiceId, 0, "Insurance_CompanyName", "Krankenkasse AG", null),
						tuple(invoiceId, 0, "KVG_InsuredId", "123.45.678-012", null),
						tuple(invoiceId, 0, "Referrer_Salutation", "Herr", null),
						tuple(invoiceId, 0, "Referrer_Title", "Dr. med.", null),
						tuple(invoiceId, 0, "Referrer_GivenName", "Herbert", null),
						tuple(invoiceId, 0, "Referrer_FamilyName", "Ueberweiser", null),
						tuple(invoiceId, 0, "Referrer_Street", "Referrerstrasse 11", null),
						tuple(invoiceId, 0, "Referrer_ZIP", "5000", null),
						tuple(invoiceId, 0, "Referrer_City", "Aarau", null),
						tuple(invoiceId, 0, "Referrer_ZSR", "R234567", null),
						tuple(invoiceId, 0, "Referrer_GLN", "2034567890333", null),
						tuple(invoiceId, 0, "Referrer_Phone", "061 956 99 00", null),
						tuple(invoiceId, 0, "Treatment_Date_Begin", null, parseTimestamp("2013-03-08")),
						tuple(invoiceId, 0, "Treatment_Date_End", null, parseTimestamp("2013-03-20")),

						tuple(invoiceId, invoiceLineId(0), "Service_Date", null, parseTimestamp("2013-03-08")),
						tuple(invoiceId, invoiceLineId(0), "Service_Name", "Konsultation, erste 5 Min. (Grundkonsultation)", null)
				);
	}

	private int invoiceLineId(final int idx)
	{
		return InvoiceAndLineId.toRepoId(testInvoice.getTestInvoiceLines().get(idx).getInvoiceAndLineId());
	}
}
