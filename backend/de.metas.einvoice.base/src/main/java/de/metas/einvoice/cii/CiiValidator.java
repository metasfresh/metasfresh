package de.metas.einvoice.cii;

import com.helger.commons.error.level.EErrorLevel;
import com.helger.commons.io.resource.ClassPathResource;
import com.helger.schematron.svrl.SVRLHelper;
import com.helger.schematron.svrl.jaxb.SchematronOutputType;
import com.helger.schematron.xslt.SchematronResourceXSLT;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Validates a marshalled CII XML document against the EN16931 Schematron rules
 * using the precompiled XSLT from ConnectingEurope/eInvoicing-EN16931 v1.3.16.
 *
 * <p>The XSLT resource is bundled at:
 * {@code de/metas/einvoice/cii/schematron/EN16931-CII-validation.xslt}
 *
 * <p>Source: https://github.com/ConnectingEurope/eInvoicing-EN16931/releases/download/validation-1.3.16/en16931-cii-1.3.16.zip
 * File: xslt/EN16931-CII-validation.xslt, version 1.3.16, last update 2026-03-30, EUPL v1.2 licence.
 *
 * <p>Runtime requires Saxon-HE (9.9.1-8) on the classpath for XSLT 2.0 support (the XSLT uses
 * {@code version="2.0"}). Saxon registers itself as a JAXP {@code TransformerFactory} via the
 * {@code META-INF/services} mechanism and is picked up automatically.
 */
public class CiiValidator
{
	private static final Logger log = LoggerFactory.getLogger(CiiValidator.class);

	/** Classpath path to the bundled EN16931 CII precompiled XSLT. */
	private static final String XSLT_CLASSPATH =
			"de/metas/einvoice/cii/schematron/EN16931-CII-validation.xslt";

	/**
	 * Validates the given CII XML string against the EN16931 Schematron rules.
	 *
	 * @param ciiXml marshalled CII XML (UTF-8 string); must be non-null and non-empty.
	 * @return validation result with list of failed assertions; never null.
	 * @throws AdempiereException if the XSLT resource cannot be loaded or the XML cannot be parsed.
	 */
	@NonNull
	public CiiValidationResult validate(@NonNull final String ciiXml)
	{
		final SchematronResourceXSLT schematron = SchematronResourceXSLT.fromClassPath(XSLT_CLASSPATH);

		if (!schematron.isValidSchematron())
		{
			throw new AdempiereException(
					"EN16931 CII Schematron XSLT could not be loaded from classpath: " + XSLT_CLASSPATH
							+ ". Ensure the resource is on the classpath and Saxon-HE is available.");
		}

		final SchematronOutputType svrl;
		try
		{
			svrl = schematron.applySchematronValidationToSVRL(new StreamSource(new StringReader(ciiXml)));
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("EN16931 CII Schematron validation failed for document", ex);
		}

		if (svrl == null)
		{
			throw new AdempiereException(
					"EN16931 CII Schematron validation returned null SVRL output — the document may not be a valid XML document.");
		}

		final List<CiiValidationResult.FailedAssertion> failedAssertions =
				SVRLHelper.getAllFailedAssertions(svrl)
						.stream()
						.map(fa ->
						{
							// IErrorLevel.getID() returns EErrorLevel enum IDs: "fatal_error", "error", "warn", "info".
							// The EN16931 XSLT uses flag="fatal" which DefaultSVRLErrorLevelDeterminator maps to EErrorLevel.FATAL_ERROR.
							// Null flag is treated as FATAL_ERROR (fail-safe: unrecognised = worst case).
							final String severityId = fa.getFlag() != null
									? fa.getFlag().getID()
									: EErrorLevel.FATAL_ERROR.getID();
							log.debug("EN16931 CII failed assertion: id={}, severity={}, message={}",
									fa.getID(), severityId, fa.getText());
							return CiiValidationResult.FailedAssertion.builder()
									.ruleId(fa.getID())
									.message(fa.getText())
									.location(fa.getLocation())
									.severity(severityId)
									.build();
						})
						.collect(Collectors.toList());

		return CiiValidationResult.builder()
				.failedAssertions(failedAssertions)
				.build();
	}
}
