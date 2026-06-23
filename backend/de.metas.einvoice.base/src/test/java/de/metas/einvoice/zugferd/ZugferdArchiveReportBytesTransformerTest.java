package de.metas.einvoice.zugferd;

/*
 * #%L
 * de.metas.einvoice.base
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

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.attachments.AttachmentEntryService;
import de.metas.einvoice.EInvoiceCiiService;
import de.metas.einvoice.EInvoiceConfigService;
import de.metas.einvoice.EInvoiceFormat;
import de.metas.invoice.InvoiceId;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.pdfbox.pdmodel.graphics.color.PDOutputIntent;
import org.apache.xmpbox.XMPMetadata;
import org.apache.xmpbox.schema.PDFAIdentificationSchema;
import org.apache.xmpbox.xml.XmpSerializer;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceTax;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mustangproject.validator.EPart;
import org.mustangproject.validator.ESeverity;
import org.mustangproject.validator.ValidationContext;
import org.mustangproject.validator.ValidationResultItem;
import org.mustangproject.validator.ZUGFeRDValidator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * TDD for {@link ZugferdArchiveReportBytesTransformer}:
 *
 * <ul>
 *   <li>For a {@code C_Invoice} whose BPartner is configured as {@code EInvoiceType=Z}
 *       (ZUGFeRD) AND which has a pre-attached {@code <DocNo>_zugferd.xml} attachment: feeding a
 *       fixture PDF/A-3 as the "report bytes" must produce a valid ZUGFeRD file by embedding the
 *       CII XML read from the attachment (Mustang validation: zero PDF/A-3 and Factur-X container
 *       errors). No CII regeneration happens.</li>
 *   <li>For a non-Z invoice (XRechnung) and for a non-{@code C_Invoice} record: the
 *       output bytes must be byte-identical to the input.</li>
 * </ul>
 *
 * <p>Uses the same PDF/A-3 fixture builder as {@link ZugferdAssemblerTest}.
 */
public class ZugferdArchiveReportBytesTransformerTest
{
	private AttachmentEntryService attachmentEntryService;
	private ZugferdArchiveReportBytesTransformer transformer;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		Env.setContext(Env.getCtx(), Env.CTXNAME_AD_User_ID, 10);

		attachmentEntryService = AttachmentEntryService.createInstanceForUnitTesting();
		transformer = new ZugferdArchiveReportBytesTransformer(attachmentEntryService);
	}

	// =========================================================================
	// Case 1: EInvoiceType=Z with pre-attached CII → embeds CII from attachment
	// =========================================================================

	@Test
	void transform_zugferd_invoice_withAttachedCii_producesValidZugferdPdf() throws Exception
	{
		// Build the invoice fixture (ZUGFeRD recipient)
		final I_C_Invoice invoice = buildInvoice(EInvoiceFormat.ZUGFeRD);
		final InvoiceId invoiceId = InvoiceId.ofRepoId(invoice.getC_Invoice_ID());

		// Attach a pre-generated CII XML (simulating what the completion gate would have saved)
		final String ciiXml = generateCiiXmlForInvoice(invoice);
		final String filename = invoice.getDocumentNo() + "_zugferd.xml";
		attachmentEntryService.createNewAttachment(invoice, filename, ciiXml.getBytes(StandardCharsets.UTF_8));

		final byte[] pdfA3Bytes = buildFixturePdfA3();
		final TableRecordReference recordRef = TableRecordReference.of(I_C_Invoice.Table_Name, invoiceId.getRepoId());

		final byte[] result = transformer.transform(recordRef, pdfA3Bytes);

		assertThat(result)
				.as("Result must be non-empty")
				.isNotEmpty();
		assertThat(result)
				.as("Result must differ from plain PDF/A-3 input (CII XML must have been embedded)")
				.isNotEqualTo(pdfA3Bytes);

		// Validate the assembled ZUGFeRD PDF: zero PDF/A-3 conformance and Factur-X structural errors
		assertZeroContainerErrors(result);
	}

	// =========================================================================
	// Case 2: EInvoiceType=Z but NO attachment → pass-through (no error)
	// =========================================================================

	@Test
	void transform_zugferd_invoice_withoutAttachment_passesThroughUnchanged() throws Exception
	{
		final I_C_Invoice invoice = buildInvoice(EInvoiceFormat.ZUGFeRD);
		final InvoiceId invoiceId = InvoiceId.ofRepoId(invoice.getC_Invoice_ID());

		// No CII attachment present (abnormal: attachment not yet created or already cleaned up)
		final byte[] pdfA3Bytes = buildFixturePdfA3();
		final TableRecordReference recordRef = TableRecordReference.of(I_C_Invoice.Table_Name, invoiceId.getRepoId());

		// Without an attachment the transformer has nothing to embed — it must pass through unchanged
		final byte[] result = transformer.transform(recordRef, pdfA3Bytes);

		assertThat(result)
				.as("Without a _zugferd.xml attachment the transformer must return input bytes unchanged")
				.isEqualTo(pdfA3Bytes);
	}

	// =========================================================================
	// Case 3: non-Z invoice (XRechnung) → byte-identical pass-through
	// =========================================================================

	@Test
	void transform_xrechnung_invoice_isByteIdentical() throws Exception
	{
		final I_C_Invoice invoice = buildInvoice(EInvoiceFormat.XRECHNUNG);
		final InvoiceId invoiceId = InvoiceId.ofRepoId(invoice.getC_Invoice_ID());

		final byte[] pdfBytes = new byte[] { 1, 2, 3, 4, 5 };
		final TableRecordReference recordRef = TableRecordReference.of(I_C_Invoice.Table_Name, invoiceId.getRepoId());

		final byte[] result = transformer.transform(recordRef, pdfBytes);

		assertThat(result)
				.as("Non-Z invoice must produce byte-identical output")
				.isEqualTo(pdfBytes);
	}

	// =========================================================================
	// Case 4: non-C_Invoice record → byte-identical pass-through
	// =========================================================================

	@Test
	void transform_nonInvoiceRecord_isByteIdentical()
	{
		// Use a record ref for a different table (e.g. M_InOut)
		final TableRecordReference recordRef = TableRecordReference.of("M_InOut", 999);
		final byte[] pdfBytes = new byte[] { 10, 20, 30 };

		final byte[] result = transformer.transform(recordRef, pdfBytes);

		assertThat(result)
				.as("Non-C_Invoice record must produce byte-identical output")
				.isEqualTo(pdfBytes);
	}

	// =========================================================================
	// Fixture builders
	// =========================================================================

	/**
	 * Generates real CII XML for the given invoice by running the full CII pipeline.
	 * Used to pre-populate the attachment so the transformer can read it.
	 */
	private static String generateCiiXmlForInvoice(@NonNull final I_C_Invoice invoice)
	{
		final EInvoiceConfigService configService = new EInvoiceConfigService();
		final EInvoiceCiiService ciiService = new EInvoiceCiiService(configService, null, null);
		final InvoiceId invoiceId = InvoiceId.ofRepoId(invoice.getC_Invoice_ID());
		return ciiService.generateAndValidate(invoiceId)
				.orElseThrow(() -> new IllegalStateException("CII generation returned empty for test invoice"))
				.getCiiXml();
	}

	/**
	 * Builds a complete invoice fixture — mirrors
	 * {@code C_InvoiceXRechnungOnCompleteTest.buildCompleteInvoice} so the CII mapper can
	 * resolve all required BT-* fields without NPEs.
	 */
	private I_C_Invoice buildInvoice(@NonNull final EInvoiceFormat format)
	{
		// === Seller org ===
		final I_AD_Org org = newInstance(I_AD_Org.class);
		saveRecord(org);

		final I_C_Country country = newInstance(I_C_Country.class);
		country.setCountryCode("DE");
		saveRecord(country);

		final I_C_Location sellerLoc = newInstance(I_C_Location.class);
		sellerLoc.setAddress1("Musterstraße 1");
		sellerLoc.setCity("Berlin");
		sellerLoc.setPostal("10115");
		sellerLoc.setC_Country_ID(country.getC_Country_ID());
		saveRecord(sellerLoc);

		final I_C_BPartner sellerBP = newInstance(I_C_BPartner.class);
		sellerBP.setName("Muster GmbH");
		sellerBP.setVATaxID("DE123456789");
		sellerBP.setEMail("invoice@muster.de");
		sellerBP.setAD_OrgBP_ID(org.getAD_Org_ID());
		saveRecord(sellerBP);

		final I_C_BPartner_Location sellerBPLoc = newInstance(I_C_BPartner_Location.class);
		sellerBPLoc.setC_BPartner_ID(sellerBP.getC_BPartner_ID());
		sellerBPLoc.setC_Location_ID(sellerLoc.getC_Location_ID());
		sellerBPLoc.setIsBillTo(true);
		saveRecord(sellerBPLoc);

		final I_AD_OrgInfo orgInfo = newInstance(I_AD_OrgInfo.class);
		orgInfo.setAD_Org_ID(org.getAD_Org_ID());
		orgInfo.setOrg_BPartner_ID(sellerBP.getC_BPartner_ID());
		saveRecord(orgInfo);

		// Seller contact — required for KoSIT BR-DE-2/5/6/7
		final I_AD_User sellerContact = newInstance(I_AD_User.class);
		sellerContact.setC_BPartner_ID(sellerBP.getC_BPartner_ID());
		sellerContact.setName("Max Mustermann");
		sellerContact.setPhone("+49 30 123456789");
		sellerContact.setEMail("max.mustermann@muster.de");
		saveRecord(sellerContact);

		// === Currency + bank ===
		final I_C_Currency currency = newInstance(I_C_Currency.class);
		currency.setISO_Code("EUR");
		saveRecord(currency);

		final I_C_BP_BankAccount sellerBank = newInstance(I_C_BP_BankAccount.class);
		sellerBank.setC_BPartner_ID(sellerBP.getC_BPartner_ID());
		sellerBank.setC_Currency_ID(currency.getC_Currency_ID());
		sellerBank.setIBAN("DE89370400440532013000");
		sellerBank.setIsDefault(true);
		sellerBank.setAD_Org_ID(org.getAD_Org_ID());
		saveRecord(sellerBank);

		// === Buyer ===
		final I_C_Location buyerLoc = newInstance(I_C_Location.class);
		buyerLoc.setAddress1("Käuferweg 5");
		buyerLoc.setCity("Hamburg");
		buyerLoc.setPostal("20095");
		buyerLoc.setC_Country_ID(country.getC_Country_ID());
		saveRecord(buyerLoc);

		final I_C_BPartner buyerBP = newInstance(I_C_BPartner.class);
		buyerBP.setName("Käufer AG");
		buyerBP.setIsEInvoiceRecipeint(true);
		buyerBP.setEInvoiceType(format.getCode());
		buyerBP.setEInvoice_BuyerReference("991-1234512345-06");
		saveRecord(buyerBP);

		final I_C_BPartner_Location buyerBPLoc = newInstance(I_C_BPartner_Location.class);
		buyerBPLoc.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		buyerBPLoc.setC_Location_ID(buyerLoc.getC_Location_ID());
		buyerBPLoc.setIsBillTo(true);
		buyerBPLoc.setEMail("einkauf@kaeufer.de");
		saveRecord(buyerBPLoc);

		// === DocType + Invoice ===
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType("ARI");
		saveRecord(docType);

		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setDocumentNo("ZUGFERD-TEST-001");
		invoice.setDateInvoiced(Timestamp.from(LocalDate.of(2024, 6, 15).atStartOfDay(ZoneOffset.UTC).toInstant()));
		invoice.setC_Currency_ID(currency.getC_Currency_ID());
		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		invoice.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(buyerBPLoc.getC_BPartner_Location_ID());
		invoice.setTotalLines(new BigDecimal("100.00"));
		invoice.setGrandTotal(new BigDecimal("119.00"));
		invoice.setPaymentRule("T");
		// BT-9 Payment due date — required by BR-CO-25 when GrandTotal > 0
		invoice.setDueDate(Timestamp.from(LocalDate.of(2024, 7, 15).atStartOfDay(ZoneOffset.UTC).toInstant()));
		saveRecord(invoice);

		// === Tax ===
		final I_C_Tax tax = newInstance(I_C_Tax.class);
		tax.setName("MWSt 19%");
		tax.setEN16931VATCategory("S");
		tax.setRate(new BigDecimal("19"));
		saveRecord(tax);

		final I_C_InvoiceTax invoiceTax = newInstance(I_C_InvoiceTax.class);
		invoiceTax.setC_Invoice_ID(invoice.getC_Invoice_ID());
		invoiceTax.setC_Tax_ID(tax.getC_Tax_ID());
		invoiceTax.setTaxBaseAmt(new BigDecimal("100.00"));
		invoiceTax.setTaxAmt(new BigDecimal("19.00"));
		saveRecord(invoiceTax);

		// === UOM + Product + line ===
		final I_C_UOM uom = newInstance(I_C_UOM.class);
		uom.setName("Stück");
		uom.setX12DE355("PCE");
		saveRecord(uom);

		final I_M_Product product = newInstance(I_M_Product.class);
		product.setName("Testprodukt");
		product.setValue("TP-SVC-001");
		saveRecord(product);

		final I_C_InvoiceLine line = newInstance(I_C_InvoiceLine.class);
		line.setC_Invoice_ID(invoice.getC_Invoice_ID());
		line.setLine(10);
		line.setM_Product_ID(product.getM_Product_ID());
		line.setC_UOM_ID(uom.getC_UOM_ID());
		line.setC_Tax_ID(tax.getC_Tax_ID());
		line.setQtyInvoiced(new BigDecimal("1"));
		line.setPriceActual(new BigDecimal("100.00"));
		line.setLineNetAmt(new BigDecimal("100.00"));
		saveRecord(line);

		return invoice;
	}

	// -----------------------------------------------------------------------
	// PDF/A-3 fixture builder — same as ZugferdAssemblerTest
	// -----------------------------------------------------------------------

	private static byte[] buildFixturePdfA3() throws Exception
	{
		try (PDDocument doc = new PDDocument())
		{
			final PDPage page = new PDPage();
			page.setResources(new PDResources());
			doc.addPage(page);

			final XMPMetadata xmp = XMPMetadata.createXMPMetadata();
			final PDFAIdentificationSchema pdfaId = xmp.createAndAddPFAIdentificationSchema();
			pdfaId.setPart(3);
			pdfaId.setConformance("B");

			final ByteArrayOutputStream xmpOut = new ByteArrayOutputStream();
			new XmpSerializer().serialize(xmp, xmpOut, true);
			final PDMetadata metadata = new PDMetadata(doc);
			metadata.importXMPMetadata(xmpOut.toByteArray());
			doc.getDocumentCatalog().setMetadata(metadata);

			final byte[] iccProfile = loadSrgbIccProfile();
			final PDOutputIntent outputIntent = new PDOutputIntent(doc, new ByteArrayInputStream(iccProfile));
			outputIntent.setInfo("sRGB IEC61966-2.1");
			outputIntent.setOutputCondition("sRGB IEC61966-2.1");
			outputIntent.setOutputConditionIdentifier("Custom");
			outputIntent.setRegistryName("");
			doc.getDocumentCatalog().addOutputIntent(outputIntent);

			final ByteArrayOutputStream out = new ByteArrayOutputStream();
			doc.save(out);
			return out.toByteArray();
		}
	}

	private static byte[] loadSrgbIccProfile() throws IOException
	{
		final String[] candidates = {
				"org/apache/pdfbox/resources/icc/ISOcoated_v2_300_bas.icc",
				"org/apache/pdfbox/resources/icc/sRGB.icc",
		};
		for (final String path : candidates)
		{
			try (InputStream is = ZugferdArchiveReportBytesTransformerTest.class.getClassLoader().getResourceAsStream(path))
			{
				if (is != null)
				{
					final ByteArrayOutputStream buf = new ByteArrayOutputStream();
					final byte[] tmp = new byte[4096];
					int n;
					while ((n = is.read(tmp)) != -1)
					{
						buf.write(tmp, 0, n);
					}
					return buf.toByteArray();
				}
			}
		}
		throw new IllegalStateException(
				"No sRGB ICC profile found on classpath. Checked: "
						+ Arrays.toString(candidates));
	}

	// -----------------------------------------------------------------------
	// ZUGFeRD validation helper — mirrors ZugferdAssemblerTest
	// -----------------------------------------------------------------------

	private static class InspectableValidator extends ZUGFeRDValidator
	{
		ValidationContext getContext()
		{
			return context;
		}
	}

	private static void assertZeroContainerErrors(final byte[] zugferdBytes) throws IOException
	{
		final Path tmp = Files.createTempFile("zugferd-transformer-test-", ".pdf");
		try
		{
			Files.write(tmp, zugferdBytes);
			final InspectableValidator validator = new InspectableValidator();
			validator.validate(tmp.toAbsolutePath().toString());

			final ValidationContext ctx = validator.getContext();
			assertThat(ctx)
					.as("ValidationContext must be available after validate()")
					.isNotNull();

			final List<ValidationResultItem> containerErrors = ctx.getResults().stream()
					.filter(item -> item.getSeverity() == ESeverity.error
							|| item.getSeverity() == ESeverity.fatal
							|| item.getSeverity() == ESeverity.exception)
					.filter(item -> item.getPart() == EPart.pdf || item.getPart() == EPart.fx)
					.collect(Collectors.toList());

			final String errorSummary = containerErrors.stream()
					.map(item -> "[" + item.getPart() + "/" + item.getSeverity() + "] " + item.getMessage())
					.collect(Collectors.joining("; "));

			assertThat(containerErrors)
					.as("ZUGFeRD transformer must produce ZERO PDF/A-3 (EPart.pdf) and "
							+ "Factur-X structural (EPart.fx) errors. Found: " + errorSummary)
					.isEmpty();
		}
		finally
		{
			Files.deleteIfExists(tmp);
		}
	}
}
