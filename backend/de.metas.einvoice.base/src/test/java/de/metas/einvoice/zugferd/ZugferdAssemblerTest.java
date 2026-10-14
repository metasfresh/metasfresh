package de.metas.einvoice.zugferd;

import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSObject;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
import org.apache.pdfbox.pdmodel.graphics.color.PDOutputIntent;
import org.apache.xmpbox.XMPMetadata;
import org.apache.xmpbox.schema.PDFAIdentificationSchema;
import org.apache.xmpbox.xml.XmpSerializer;
import org.junit.jupiter.api.Test;
import org.mustangproject.validator.EPart;
import org.mustangproject.validator.ESeverity;
import org.mustangproject.validator.ValidationContext;
import org.mustangproject.validator.ValidationResultItem;
import org.mustangproject.validator.ZUGFeRDValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlunit.assertj.XmlAssert;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * TDD for {@link ZugferdAssembler#embed(byte[], String)}.
 *
 * <p>Embeds a minimal EN16931 CII XML into a fixture PDF/A-3 and asserts:
 * <ol>
 *   <li>The Mustang ZUGFeRDValidator reports ZERO PDF/A-3 conformance errors (EPart.pdf)
 *       and ZERO Factur-X structural errors (EPart.fx) at severity error/fatal/exception.
 *       CII-content schematron (EPart.ox/xr) is explicitly excluded — that is the
 *       responsibility of CiiMapper/EInvoiceCiiService, not the assembler.</li>
 *   <li>The {@code factur-x.xml} extracted back from the assembled PDF is XML-equal
 *       to the input CII string, proving the assembler embedded the exact content
 *       without corruption.</li>
 *   <li>The attachment named {@code factur-x.xml} is present with
 *       {@code AFRelationship = Alternative}</li>
 *   <li>The XMP metadata contains the Factur-X conformance declaration
 *       ({@code fx:ConformanceLevel})</li>
 * </ol>
 *
 * <p>The SAMPLE_CII_XML fixture is schema-structurally valid but intentionally not full
 * EN16931-schematron-valid (many mandatory BT-* fields are absent). The assembler's contract is
 * the <em>container</em> (PDF/A-3 + Factur-X embedding), not the CII content; CII-content
 * validation is covered by {@code CiiValidatorTest}. This test therefore asserts zero
 * container-level errors and round-trip XML fidelity; it deliberately does not assert
 * {@code wasCompletelyValid()}.
 */
public class ZugferdAssemblerTest
{
	private static final Logger log = LoggerFactory.getLogger(ZugferdAssemblerTest.class);

	/**
	 * Minimal CII XML fixture for container-level testing.
	 * Intentionally schema-structurally valid but NOT full EN16931-schematron-valid —
	 * many mandatory BT-* fields are absent. CII-content schematron validation is
	 * covered by {@link de.metas.einvoice.cii.CiiValidatorTest}; this fixture
	 * exercises the PDF/A-3 container embedding only.
	 */
	private static final String SAMPLE_CII_XML =
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
					+ "<rsm:CrossIndustryInvoice\n"
					+ "    xmlns:rsm=\"urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100\"\n"
					+ "    xmlns:ram=\"urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100\"\n"
					+ "    xmlns:udt=\"urn:un:unece:uncefact:data:standard:UnqualifiedDataType:100\"\n"
					+ "    xmlns:qdt=\"urn:un:unece:uncefact:data:standard:QualifiedDataType:100\">\n"
					+ "  <rsm:ExchangedDocumentContext>\n"
					+ "    <ram:GuidelineSpecifiedDocumentContextParameter>\n"
					+ "      <ram:ID>urn:cen.eu:en16931:2017</ram:ID>\n"
					+ "    </ram:GuidelineSpecifiedDocumentContextParameter>\n"
					+ "  </rsm:ExchangedDocumentContext>\n"
					+ "  <rsm:ExchangedDocument>\n"
					+ "    <ram:ID>TEST-2024-001</ram:ID>\n"
					+ "    <ram:TypeCode>380</ram:TypeCode>\n"
					+ "    <ram:IssueDateTime>\n"
					+ "      <udt:DateTimeString format=\"102\">20240615</udt:DateTimeString>\n"
					+ "    </ram:IssueDateTime>\n"
					+ "  </rsm:ExchangedDocument>\n"
					+ "  <rsm:SupplyChainTradeTransaction>\n"
					+ "    <ram:IncludedSupplyChainTradeLineItem>\n"
					+ "      <ram:AssociatedDocumentLineDocument>\n"
					+ "        <ram:LineID>1</ram:LineID>\n"
					+ "      </ram:AssociatedDocumentLineDocument>\n"
					+ "      <ram:SpecifiedTradeProduct>\n"
					+ "        <ram:Name>Test Product</ram:Name>\n"
					+ "      </ram:SpecifiedTradeProduct>\n"
					+ "      <ram:SpecifiedLineTradeAgreement>\n"
					+ "        <ram:NetPriceProductTradePrice>\n"
					+ "          <ram:ChargeAmount>100.00</ram:ChargeAmount>\n"
					+ "        </ram:NetPriceProductTradePrice>\n"
					+ "      </ram:SpecifiedLineTradeAgreement>\n"
					+ "      <ram:SpecifiedLineTradeDelivery>\n"
					+ "        <ram:BilledQuantity unitCode=\"C62\">1</ram:BilledQuantity>\n"
					+ "      </ram:SpecifiedLineTradeDelivery>\n"
					+ "      <ram:SpecifiedLineTradeSettlement>\n"
					+ "        <ram:ApplicableTradeTax>\n"
					+ "          <ram:TypeCode>VAT</ram:TypeCode>\n"
					+ "          <ram:CategoryCode>S</ram:CategoryCode>\n"
					+ "          <ram:RateApplicablePercent>19</ram:RateApplicablePercent>\n"
					+ "        </ram:ApplicableTradeTax>\n"
					+ "        <ram:SpecifiedTradeSettlementLineMonetarySummation>\n"
					+ "          <ram:LineTotalAmount>100.00</ram:LineTotalAmount>\n"
					+ "        </ram:SpecifiedTradeSettlementLineMonetarySummation>\n"
					+ "      </ram:SpecifiedLineTradeSettlement>\n"
					+ "    </ram:IncludedSupplyChainTradeLineItem>\n"
					+ "    <ram:ApplicableHeaderTradeAgreement>\n"
					+ "      <ram:BuyerReference>991-1234512345-06</ram:BuyerReference>\n"
					+ "      <ram:SellerTradeParty>\n"
					+ "        <ram:Name>Muster GmbH</ram:Name>\n"
					+ "        <ram:PostalTradeAddress>\n"
					+ "          <ram:PostcodeCode>10115</ram:PostcodeCode>\n"
					+ "          <ram:LineOne>Musterstraße 1</ram:LineOne>\n"
					+ "          <ram:CityName>Berlin</ram:CityName>\n"
					+ "          <ram:CountryID>DE</ram:CountryID>\n"
					+ "        </ram:PostalTradeAddress>\n"
					+ "        <ram:SpecifiedTaxRegistration>\n"
					+ "          <ram:ID schemeID=\"VA\">DE123456789</ram:ID>\n"
					+ "        </ram:SpecifiedTaxRegistration>\n"
					+ "      </ram:SellerTradeParty>\n"
					+ "      <ram:BuyerTradeParty>\n"
					+ "        <ram:Name>Käufer AG</ram:Name>\n"
					+ "        <ram:PostalTradeAddress>\n"
					+ "          <ram:PostcodeCode>20095</ram:PostcodeCode>\n"
					+ "          <ram:LineOne>Käuferweg 5</ram:LineOne>\n"
					+ "          <ram:CityName>Hamburg</ram:CityName>\n"
					+ "          <ram:CountryID>DE</ram:CountryID>\n"
					+ "        </ram:PostalTradeAddress>\n"
					+ "      </ram:BuyerTradeParty>\n"
					+ "    </ram:ApplicableHeaderTradeAgreement>\n"
					+ "    <ram:ApplicableHeaderTradeDelivery/>\n"
					+ "    <ram:ApplicableHeaderTradeSettlement>\n"
					+ "      <ram:InvoiceCurrencyCode>EUR</ram:InvoiceCurrencyCode>\n"
					+ "      <ram:ApplicableTradeTax>\n"
					+ "        <ram:CalculatedAmount>19.00</ram:CalculatedAmount>\n"
					+ "        <ram:TypeCode>VAT</ram:TypeCode>\n"
					+ "        <ram:BasisAmount>100.00</ram:BasisAmount>\n"
					+ "        <ram:CategoryCode>S</ram:CategoryCode>\n"
					+ "        <ram:RateApplicablePercent>19</ram:RateApplicablePercent>\n"
					+ "      </ram:ApplicableTradeTax>\n"
					+ "      <ram:SpecifiedTradePaymentTerms>\n"
					+ "        <ram:DueDateDateTime>\n"
					+ "          <udt:DateTimeString format=\"102\">20240715</udt:DateTimeString>\n"
					+ "        </ram:DueDateDateTime>\n"
					+ "      </ram:SpecifiedTradePaymentTerms>\n"
					+ "      <ram:SpecifiedTradeSettlementHeaderMonetarySummation>\n"
					+ "        <ram:LineTotalAmount>100.00</ram:LineTotalAmount>\n"
					+ "        <ram:TaxBasisTotalAmount>100.00</ram:TaxBasisTotalAmount>\n"
					+ "        <ram:TaxTotalAmount currencyID=\"EUR\">19.00</ram:TaxTotalAmount>\n"
					+ "        <ram:GrandTotalAmount>119.00</ram:GrandTotalAmount>\n"
					+ "        <ram:DuePayableAmount>119.00</ram:DuePayableAmount>\n"
					+ "      </ram:SpecifiedTradeSettlementHeaderMonetarySummation>\n"
					+ "    </ram:ApplicableHeaderTradeSettlement>\n"
					+ "  </rsm:SupplyChainTradeTransaction>\n"
					+ "</rsm:CrossIndustryInvoice>\n";

	// -----------------------------------------------------------------------
	// Fixture: minimal PDF/A-3B built with PDFBox
	// -----------------------------------------------------------------------

	/**
	 * Builds a minimal but structurally valid PDF/A-3B document in memory using PDFBox.
	 * The PDF contains:
	 * <ul>
	 *   <li>One blank page</li>
	 *   <li>XMP metadata with pdfaid:part=3, pdfaid:conformance=B</li>
	 *   <li>An sRGB ICC profile as OutputIntent (loaded from PDFBox's bundled profile)</li>
	 * </ul>
	 * ZUGFeRDExporterFromA3 2.11.0 does not validate the PDF/A conformance of the input
	 * (ensurePDFIsValid returns true unconditionally) so a structurally complete PDF/A-3
	 * is sufficient to exercise the assembler.
	 */
	private static byte[] buildFixturePdfA3() throws Exception
	{
		try (PDDocument doc = new PDDocument())
		{
			// Add a page with an explicit (empty) Resources dictionary.
			// ZUGFeRDExporterFromA3.removeCidSet() iterates page resources to fix CIDSet entries;
			// without a Resources dict, page.getResources() returns null → NPE at the iteration.
			final PDPage page = new PDPage();
			page.setResources(new PDResources());
			doc.addPage(page);

			// --- XMP metadata with pdfaid:part=3, pdfaid:conformance=B ---
			final XMPMetadata xmp = XMPMetadata.createXMPMetadata();
			// Note: XMPBox 2.0.27 spells this method "PFA" (not "PDFA") — that is the actual API name
			final PDFAIdentificationSchema pdfaId = xmp.createAndAddPFAIdentificationSchema();
			pdfaId.setPart(3);
			pdfaId.setConformance("B");

			final ByteArrayOutputStream xmpOut = new ByteArrayOutputStream();
			new XmpSerializer().serialize(xmp, xmpOut, true);
			final PDMetadata metadata = new PDMetadata(doc);
			metadata.importXMPMetadata(xmpOut.toByteArray());
			doc.getDocumentCatalog().setMetadata(metadata);

			// --- sRGB ICC output intent ---
			// PDFBox bundles sRGB.icc; load it from its classpath location
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

	/**
	 * Loads the sRGB ICC profile bundled with PDFBox (org/apache/pdfbox/resources/icc/ISOcoated_v2_300_bas.icc
	 * or similar). Throws {@link IllegalStateException} if no candidate resolves, so a resource-path
	 * shift surfaces as a test failure rather than a silently-invalid fixture.
	 */
	private static byte[] loadSrgbIccProfile() throws IOException
	{
		// PDFBox 2.x bundles this sRGB profile used by the preflight module
		final String[] candidates = {
				"org/apache/pdfbox/resources/icc/ISOcoated_v2_300_bas.icc",
				"org/apache/pdfbox/resources/icc/sRGB.icc",
		};
		for (final String path : candidates)
		{
			try (InputStream is = ZugferdAssemblerTest.class.getClassLoader().getResourceAsStream(path))
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
						+ Arrays.toString(candidates)
						+ ". Verify PDFBox ICC resources are present — the fixture PDF/A-3 requires a valid ICC output intent.");
	}

	// -----------------------------------------------------------------------
	// Subclass to expose ValidationContext after validate()
	// -----------------------------------------------------------------------

	/**
	 * Thin wrapper over {@link ZUGFeRDValidator} that exposes the protected
	 * {@code context} field so tests can inspect individual {@link ValidationResultItem}s
	 * by part (pdf/fx/ox) and severity without parsing the XML report string.
	 */
	private static class InspectableValidator extends ZUGFeRDValidator
	{
		ValidationContext getContext()
		{
			return context;
		}
	}

	// -----------------------------------------------------------------------
	// Tests
	// -----------------------------------------------------------------------

	/**
	 * Core container-level test: embeds the sample CII into a fixture PDF/A-3 and asserts:
	 * <ol>
	 *   <li>Zero validator items with severity {@code error}, {@code fatal}, or {@code exception}
	 *       and part {@code pdf} (PDF/A-3 conformance) or {@code fx} (Factur-X structural).
	 *       CII-content schematron parts ({@code ox}, {@code xr}) are explicitly excluded —
	 *       the SAMPLE_CII_XML is intentionally minimal and will have schematron failures;
	 *       those are upstream's responsibility (CiiMapper/EInvoiceCiiService).</li>
	 *   <li>The {@code factur-x.xml} extracted back from the output PDF is XML-equal to
	 *       the input CII string, proving round-trip fidelity of the container embedding.</li>
	 * </ol>
	 *
	 * <p>A fully schematron-valid CII fixture is not readily available in this module;
	 * CII-content validation is covered by {@code CiiValidatorTest}. This test therefore does
	 * not assert {@code wasCompletelyValid()} — it asserts only that the assembler produced a
	 * structurally correct PDF/A-3 Factur-X container.
	 */
	@Test
	void embed_producesZugferdPdf_containerLevelValid() throws Exception
	{
		final byte[] pdfA3 = buildFixturePdfA3();
		final byte[] result = ZugferdAssembler.embed(pdfA3, SAMPLE_CII_XML);

		assertThat(result)
				.as("embed() must return non-null, non-empty bytes")
				.isNotEmpty();

		// ---- 1. Validate container conformance with Mustang ZUGFeRDValidator ----
		final Path tmp = Files.createTempFile("zugferd-test-", ".pdf");
		try
		{
			Files.write(tmp, result);
			final InspectableValidator validator = new InspectableValidator();
			validator.validate(tmp.toAbsolutePath().toString());

			final ValidationContext ctx = validator.getContext();
			assertThat(ctx)
					.as("ValidationContext must be available after validate()")
					.isNotNull();

			// Collect all error/fatal items whose part is pdf (PDF/A-3 conformance)
			// or fx (Factur-X structural) — these are the assembler's responsibility.
			// EPart.ox (CII schematron EN16931) and EPart.xr (KoSIT XRechnung) are excluded.
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
					.as("Assembler must produce ZERO PDF/A-3 conformance (EPart.pdf) and "
							+ "Factur-X structural (EPart.fx) errors/fatals/exceptions. "
							+ "Found " + containerErrors.size() + " container error(s): " + errorSummary)
					.isEmpty();

			// Log total result counts at debug level for diagnostics
			log.debug("ZUGFeRDValidator results: total={}, pdf_errors={}, fx_errors={}, "
							+ "ox_errors={}, xr_errors={}",
					ctx.getResults().size(),
					ctx.getResults().stream().filter(i -> i.getPart() == EPart.pdf
							&& (i.getSeverity() == ESeverity.error || i.getSeverity() == ESeverity.fatal || i.getSeverity() == ESeverity.exception)).count(),
					ctx.getResults().stream().filter(i -> i.getPart() == EPart.fx
							&& (i.getSeverity() == ESeverity.error || i.getSeverity() == ESeverity.fatal || i.getSeverity() == ESeverity.exception)).count(),
					ctx.getResults().stream().filter(i -> i.getPart() == EPart.ox
							&& (i.getSeverity() == ESeverity.error || i.getSeverity() == ESeverity.fatal || i.getSeverity() == ESeverity.exception)).count(),
					ctx.getResults().stream().filter(i -> i.getPart() == EPart.xr
							&& (i.getSeverity() == ESeverity.error || i.getSeverity() == ESeverity.fatal || i.getSeverity() == ESeverity.exception)).count());
		}
		finally
		{
			Files.deleteIfExists(tmp);
		}

		// ---- 2. Round-trip fidelity: extract factur-x.xml and assert XML-equality ----
		final String embeddedXml = extractEmbeddedFacturXXml(result);
		assertThat(embeddedXml)
				.as("factur-x.xml must be extractable from the assembled PDF")
				.isNotEmpty();

		XmlAssert.assertThat(embeddedXml)
				.and(SAMPLE_CII_XML)
				.ignoreWhitespace()
				.areSimilar();
	}

	/**
	 * Structural test: the assembled PDF must contain the Factur-X file attachment named
	 * {@code factur-x.xml} with {@code AFRelationship = Alternative}, and the XMP metadata
	 * must contain the Factur-X conformance level declaration.
	 */
	@Test
	void embed_facturXAttachment_presentWithAlternativeRelationshipAndXmp() throws Exception
	{
		final byte[] pdfA3 = buildFixturePdfA3();
		final byte[] result = ZugferdAssembler.embed(pdfA3, SAMPLE_CII_XML);

		try (PDDocument doc = PDDocument.load(result))
		{
			// ---- 1. Check factur-x.xml attachment with AFRelationship=Alternative ----
			// Mustangproject sets /AF as a COSArray of file-specification dictionaries.
			// We also fall back to scanning the EmbeddedFiles name tree for older or alternative builds.
			final COSDictionary catalog = doc.getDocumentCatalog().getCOSObject();
			final COSBase afBase = catalog.getDictionaryObject(COSName.getPDFName("AF"));

			boolean foundFacturXAlt = false;
			if (afBase instanceof COSArray)
			{
				foundFacturXAlt = checkAfArray((COSArray) afBase);
			}
			if (!foundFacturXAlt)
			{
				// Fall back: scan the EmbeddedFiles name tree
				foundFacturXAlt = checkEmbeddedFileNames(doc);
			}

			assertThat(foundFacturXAlt)
					.as("PDF must contain a file attachment named 'factur-x.xml' "
							+ "with AFRelationship=Alternative")
					.isTrue();

			// ---- 2. Check Factur-X XMP present ----
			final PDMetadata metadataStream = doc.getDocumentCatalog().getMetadata();
			assertThat(metadataStream)
					.as("Assembled PDF must have XMP metadata")
					.isNotNull();
			final byte[] xmpBytes = metadataStream.toByteArray();
			final String xmp = new String(xmpBytes, StandardCharsets.UTF_8);
			assertThat(xmp)
					.as("XMP must contain Factur-X namespace or conformance level declaration")
					.containsAnyOf(
							"urn:factur-x:pdfa:CrossIndustryDocument:invoice:1p0#",
							"fx:ConformanceLevel",
							"factur-x");
		}
	}

	// -----------------------------------------------------------------------
	// Helpers
	// -----------------------------------------------------------------------

	/**
	 * Extracts the UTF-8 content of the {@code factur-x.xml} embedded file from the assembled PDF.
	 * Uses PDFBox's high-level {@link PDComplexFileSpecification} / {@link PDEmbeddedFile} API
	 * to walk the EmbeddedFiles name tree.
	 */
	private static String extractEmbeddedFacturXXml(final byte[] pdfBytes) throws IOException
	{
		try (PDDocument doc = PDDocument.load(pdfBytes))
		{
			final COSDictionary namesDict = (COSDictionary) doc.getDocumentCatalog()
					.getCOSObject()
					.getDictionaryObject(COSName.NAMES);
			if (namesDict == null)
			{
				return "";
			}
			final COSDictionary embeddedFilesDict = (COSDictionary) namesDict.getDictionaryObject(
					COSName.getPDFName("EmbeddedFiles"));
			if (embeddedFilesDict == null)
			{
				return "";
			}
			final COSArray namesArray = (COSArray) embeddedFilesDict.getDictionaryObject(COSName.NAMES);
			if (namesArray == null)
			{
				return "";
			}
			// Pairs: [name, fileSpec, name, fileSpec, ...]
			for (int i = 0; i + 1 < namesArray.size(); i += 2)
			{
				final String name = namesArray.getString(i);
				if (!"factur-x.xml".equalsIgnoreCase(name))
				{
					continue;
				}
				COSBase specBase = namesArray.getObject(i + 1);
				if (specBase instanceof COSObject)
				{
					specBase = ((COSObject) specBase).getObject();
				}
				if (!(specBase instanceof COSDictionary))
				{
					continue;
				}
				final PDComplexFileSpecification spec = new PDComplexFileSpecification((COSDictionary) specBase);
				PDEmbeddedFile ef = spec.getEmbeddedFileUnicode();
				if (ef == null)
				{
					ef = spec.getEmbeddedFile();
				}
				if (ef != null)
				{
					final byte[] content = ef.toByteArray();
					return new String(content, StandardCharsets.UTF_8);
				}
			}
		}
		return "";
	}

	/** Checks the AF (Associated Files) array for factur-x.xml with AFRelationship=Alternative. */
	private static boolean checkAfArray(final COSArray afArray)
	{
		for (int i = 0; i < afArray.size(); i++)
		{
			final Object item = afArray.getObject(i);
			final COSDictionary fileSpec;
			if (item instanceof COSObject)
			{
				final Object base = ((COSObject) item).getObject();
				if (!(base instanceof COSDictionary))
				{
					continue;
				}
				fileSpec = (COSDictionary) base;
			}
			else if (item instanceof COSDictionary)
			{
				fileSpec = (COSDictionary) item;
			}
			else
			{
				continue;
			}
			final String uf = fileSpec.getString("UF");
			final String f = fileSpec.getString(COSName.F);
			final String rel = fileSpec.getNameAsString("AFRelationship");
			final boolean isFacturX = "factur-x.xml".equalsIgnoreCase(uf) || "factur-x.xml".equalsIgnoreCase(f);
			if (isFacturX && "Alternative".equals(rel))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Fallback: walk the EmbeddedFiles name tree looking for factur-x.xml
	 * and check its AFRelationship in the file specification dictionary.
	 */
	private static boolean checkEmbeddedFileNames(final PDDocument doc)
	{
		try
		{
			final COSDictionary names = (COSDictionary) doc.getDocumentCatalog()
					.getCOSObject()
					.getDictionaryObject(COSName.NAMES);
			if (names == null)
			{
				return false;
			}
			final COSDictionary embeddedFiles = (COSDictionary) names.getDictionaryObject(
					COSName.getPDFName("EmbeddedFiles"));
			if (embeddedFiles == null)
			{
				return false;
			}
			final COSArray namesArray = (COSArray) embeddedFiles.getDictionaryObject(COSName.NAMES);
			if (namesArray == null)
			{
				return false;
			}
			// The names array is pairs: [name, fileSpec, name, fileSpec, ...]
			for (int i = 0; i + 1 < namesArray.size(); i += 2)
			{
				final String name = namesArray.getString(i);
				if (!"factur-x.xml".equalsIgnoreCase(name))
				{
					continue;
				}
				final Object specObj = namesArray.getObject(i + 1);
				final COSDictionary fileSpec;
				if (specObj instanceof COSObject)
				{
					final Object base = ((COSObject) specObj).getObject();
					fileSpec = base instanceof COSDictionary ? (COSDictionary) base : null;
				}
				else
				{
					fileSpec = specObj instanceof COSDictionary ? (COSDictionary) specObj : null;
				}
				if (fileSpec == null)
				{
					continue;
				}
				final String rel = fileSpec.getNameAsString("AFRelationship");
				if ("Alternative".equals(rel))
				{
					return true;
				}
			}
		}
		catch (final ClassCastException e)
		{
			// Unexpected PDF structure (wrong COS type) — log for diagnostics and fall through to false
			log.debug("checkEmbeddedFileNames: unexpected COS type while scanning EmbeddedFiles tree", e);
		}
		return false;
	}

}
