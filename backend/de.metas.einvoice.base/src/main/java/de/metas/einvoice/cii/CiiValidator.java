package de.metas.einvoice.cii;

import com.helger.commons.error.level.EErrorLevel;
import com.helger.schematron.svrl.SVRLHelper;
import com.helger.schematron.svrl.jaxb.SchematronOutputType;
import com.helger.schematron.xslt.SchematronResourceXSLT;
import de.metas.einvoice.EInvoiceFormat;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Validates a marshalled CII XML document against the EN16931 Schematron rules, and optionally
 * also against the KoSIT/XRechnung Schematron rules when the format is XRechnung.
 *
 * <h2>EN16931 schematron</h2>
 * <p>Precompiled XSLT bundled at {@code de/metas/einvoice/cii/schematron/EN16931-CII-validation.xslt}.
 * Source: ConnectingEurope/eInvoicing-EN16931 v1.3.16, last update 2026-03-30, EUPL v1.2 licence.
 *
 * <h2>KoSIT/XRechnung CII schematron</h2>
 * <p>Precompiled XSLT bundled at {@code de/metas/einvoice/cii/schematron/XRechnung-CII-validation.xslt}.
 * Source: itplr-kosit/xrechnung-schematron release v2.5.0
 * (asset {@code xrechnung-3.0.2-schematron-2.5.0.zip}), file {@code schematron/cii/XRechnung-CII-validation.xsl},
 * compatible with XRechnung 3.0.2 / guideline {@code urn:cen.eu:en16931:2017#compliant#urn:xeinkauf.de:kosit:xrechnung_3.0}.
 * Licence: Apache-2.0.
 *
 * <h2>Runtime requirements</h2>
 * <p>Saxon-HE (9.9.1-8) must be on the classpath for XSLT 2.0 support. Saxon registers itself as a
 * JAXP {@code TransformerFactory} via the {@code META-INF/services} mechanism and is picked up automatically.
 */
public class CiiValidator
{
	private static final Logger log = LoggerFactory.getLogger(CiiValidator.class);

	/** Classpath path to the bundled EN16931 CII precompiled XSLT. */
	private static final String EN16931_XSLT_CLASSPATH =
			"de/metas/einvoice/cii/schematron/EN16931-CII-validation.xslt";

	/** Classpath path to the bundled KoSIT/XRechnung CII precompiled XSLT. */
	private static final String XRECHNUNG_XSLT_CLASSPATH =
			"de/metas/einvoice/cii/schematron/XRechnung-CII-validation.xslt";

	/**
	 * Compiled EN16931 XSLT schematron resource, shared and compiled once at class load time.
	 * {@link SchematronResourceXSLT} is thread-safe after compilation; {@code applySchematronValidationToSVRL}
	 * is safe to call concurrently on the same instance.
	 */
	private static final SchematronResourceXSLT EN16931_SCHEMATRON;

	/**
	 * Compiled KoSIT/XRechnung XSLT schematron resource, shared and compiled once at class load time.
	 * Applied in addition to {@link #EN16931_SCHEMATRON} when the invoice format is XRechnung.
	 */
	private static final SchematronResourceXSLT XRECHNUNG_SCHEMATRON;

	static
	{
		EN16931_SCHEMATRON = SchematronResourceXSLT.fromClassPath(EN16931_XSLT_CLASSPATH);
		if (!EN16931_SCHEMATRON.isValidSchematron())
		{
			throw new ExceptionInInitializerError(
					"EN16931 CII Schematron XSLT could not be loaded from classpath: " + EN16931_XSLT_CLASSPATH
							+ ". Ensure the resource is on the classpath and Saxon-HE is available.");
		}

		XRECHNUNG_SCHEMATRON = SchematronResourceXSLT.fromClassPath(XRECHNUNG_XSLT_CLASSPATH);
		// The KoSIT XSLT is compiled via SchXSLT and emits svrl:metadata elements not covered
		// by ph-schematron's JAXB binding for SVRL 1.0. Disabling SVRL validation prevents the
		// JAXB unmarshaller from rejecting valid output containing those extension elements.
		XRECHNUNG_SCHEMATRON.setValidateSVRL(false);
		if (!XRECHNUNG_SCHEMATRON.isValidSchematron())
		{
			throw new ExceptionInInitializerError(
					"KoSIT/XRechnung CII Schematron XSLT could not be loaded from classpath: " + XRECHNUNG_XSLT_CLASSPATH
							+ ". Ensure the resource is on the classpath and Saxon-HE is available.");
		}
	}

	/**
	 * Validates the given CII XML string against the EN16931 Schematron rules.
	 * Delegates to {@link #validate(String, EInvoiceFormat)} with a {@code null} format,
	 * so only the EN16931 schematron is applied.
	 *
	 * @param ciiXml marshalled CII XML (UTF-8 string); must be non-null and non-empty.
	 * @return validation result with list of failed assertions; never null.
	 * @throws AdempiereException if the XML cannot be parsed or the schematron fails.
	 */
	@NonNull
	public CiiValidationResult validate(@NonNull final String ciiXml)
	{
		return validate(ciiXml, null);
	}

	/**
	 * Validates the given CII XML string.
	 *
	 * <p>Always runs the EN16931 schematron (checks EN16931 BR-* rules). Additionally, when
	 * {@code format} is {@link EInvoiceFormat#XRECHNUNG}, also runs the KoSIT/XRechnung schematron
	 * (checks BR-DE-* rules) and appends its failed assertions to the same result.
	 *
	 * @param ciiXml  marshalled CII XML (UTF-8 string); must be non-null and non-empty.
	 * @param format  the e-invoice format; may be {@code null} (EN16931-only validation).
	 * @return combined validation result with all failed assertions; never null.
	 * @throws AdempiereException if the XML cannot be parsed or any schematron call fails.
	 */
	@NonNull
	public CiiValidationResult validate(@NonNull final String ciiXml, @Nullable final EInvoiceFormat format)
	{
		final List<CiiValidationResult.FailedAssertion> allAssertions = new ArrayList<>();

		// Always run the EN16931 schematron
		allAssertions.addAll(runSchematron(EN16931_SCHEMATRON, ciiXml, "EN16931"));

		// Additionally run the KoSIT/XRechnung schematron for XRechnung format
		if (format != null && format.isXRechnung())
		{
			allAssertions.addAll(runSchematron(XRECHNUNG_SCHEMATRON, ciiXml, "XRechnung"));
		}

		return CiiValidationResult.builder()
				.failedAssertions(allAssertions)
				.build();
	}

	/**
	 * Runs the given XSLT schematron resource against the CII XML and returns the list of
	 * failed assertions.
	 *
	 * @param schematron the schematron resource to apply; must be non-null.
	 * @param ciiXml     the XML document to validate.
	 * @param label      a short label for log/error messages (e.g. "EN16931", "XRechnung").
	 * @return list of failed assertions; never null.
	 * @throws AdempiereException if the SVRL output is null or the schematron throws.
	 */
	@NonNull
	private List<CiiValidationResult.FailedAssertion> runSchematron(
			@NonNull final SchematronResourceXSLT schematron,
			@NonNull final String ciiXml,
			@NonNull final String label)
	{
		final SchematronOutputType svrl;
		try
		{
			svrl = schematron.applySchematronValidationToSVRL(new StreamSource(new StringReader(ciiXml)));
		}
		catch (final Exception ex)
		{
			throw new AdempiereException(label + " CII Schematron validation failed for document", ex);
		}

		if (svrl == null)
		{
			throw new AdempiereException(
					label + " CII Schematron validation returned null SVRL output — the document may not be a valid XML document.");
		}

		return SVRLHelper.getAllFailedAssertions(svrl)
				.stream()
				.map(fa ->
				{
					// IErrorLevel.getID() returns EErrorLevel enum IDs: "fatal_error", "error", "warn", "info".
					// The EN16931 XSLT uses flag="fatal" which DefaultSVRLErrorLevelDeterminator maps to EErrorLevel.FATAL_ERROR.
					// Null flag is treated as FATAL_ERROR (fail-safe: unrecognised = worst case).
					final String severityId = fa.getFlag() != null
							? fa.getFlag().getID()
							: EErrorLevel.FATAL_ERROR.getID();
					log.debug("{} CII failed assertion: id={}, severity={}, message={}",
							label, fa.getID(), severityId, fa.getText());
					return CiiValidationResult.FailedAssertion.builder()
							.ruleId(fa.getID())
							.message(fa.getText())
							.location(fa.getLocation())
							.severity(severityId)
							.build();
				})
				.collect(Collectors.toList());
	}
}
