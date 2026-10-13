package de.metas.einvoice;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
import de.metas.document.archive.mailrecipient.DocOutboundLogMailRecipientRegistry;
import de.metas.email.MailService;
import de.metas.einvoice.cii.CiiMapper;
import de.metas.einvoice.cii.CiiValidationResult;
import de.metas.einvoice.cii.CiiValidator;
import de.metas.einvoice.cii.model.CrossIndustryInvoiceType;
import de.metas.einvoice.cii.model.ObjectFactory;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Invoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.Optional;

/**
 * Public entry point for CII e-invoice generation and Schematron validation.
 *
 * <p>Orchestrates the pipeline:
 * <ol>
 *   <li>{@link EInvoiceConfigService#resolveForInvoice(I_C_Invoice)} — determine if the invoice's
 *       BPartner is an e-invoice recipient and resolve the format configuration.</li>
 *   <li>{@link CiiMapper#map(I_C_Invoice, EInvoiceRecipientConfig)} — map the invoice to a CII
 *       {@link CrossIndustryInvoiceType} domain object.</li>
 *   <li>Marshal the CII object to XML.</li>
 *   <li>{@link CiiValidator#validate(String, EInvoiceFormat)} — validate the marshalled XML
 *       against EN16931 Schematron rules always, plus the KoSIT/XRechnung schematron when the
 *       resolved format is XRechnung.</li>
 * </ol>
 *
 * <p>Returns {@link Optional#empty()} when the BPartner is not an e-invoice recipient,
 * so callers do not need to check eligibility themselves.
 */
@Service
public class EInvoiceCiiService
{
	private static final Logger log = LoggerFactory.getLogger(EInvoiceCiiService.class);

	/**
	 * JAXBContext is thread-safe once built and expensive to construct (full classpath scan).
	 * Build it once at class-load time and reuse across calls.
	 */
	private static final JAXBContext JAXB_CTX;

	static
	{
		try
		{
			JAXB_CTX = JAXBContext.newInstance(CrossIndustryInvoiceType.class, ObjectFactory.class);
		}
		catch (final JAXBException ex)
		{
			throw new ExceptionInInitializerError(ex);
		}
	}

	@NonNull private final EInvoiceConfigService configService;
	@Nullable private final DocOutboundLogMailRecipientRegistry mailRecipientRegistry;
	@Nullable private final MailService mailService;
	@NonNull private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

	/**
	 * {@code mailRecipientRegistry} and {@code mailService} are nullable so that existing tests
	 * that do not exercise the BT-49/BT-34 resolvers can pass {@code null} without a NPE from a
	 * {@code @NonNull} guard. In a live Spring context both beans are always present.
	 */
	public EInvoiceCiiService(
			@NonNull final EInvoiceConfigService configService,
			@Nullable final DocOutboundLogMailRecipientRegistry mailRecipientRegistry,
			@Nullable final MailService mailService)
	{
		this.configService = configService;
		this.mailRecipientRegistry = mailRecipientRegistry;
		this.mailService = mailService;
	}

	/**
	 * Generates a CII XML e-invoice for the given invoice and validates it against EN16931 rules.
	 *
	 * @param invoiceId the invoice to process; must be non-null.
	 * @return result containing the generated XML and validation outcome, or empty if the invoice's
	 *         BPartner is not configured as an e-invoice recipient.
	 */
	@NonNull
	public Optional<GenerateAndValidateResult> generateAndValidate(@NonNull final InvoiceId invoiceId)
	{
		// Load the invoice once and pass it to both config resolution and mapping.
		final I_C_Invoice invoice = invoiceDAO.getByIdInTrx(invoiceId);

		final EInvoiceRecipientConfig config = configService.resolveForInvoice(invoice).orElse(null);
		if (config == null)
		{
			log.debug("Invoice {} is not an e-invoice recipient — skipping CII generation.", invoiceId);
			return Optional.empty();
		}

		// Map invoice to CII domain object
		final CrossIndustryInvoiceType cii = new CiiMapper(mailRecipientRegistry, mailService).map(invoice, config);

		// Marshal to XML
		final String ciiXml = marshalToXml(cii);

		// Validate against EN16931 Schematron (always), plus KoSIT/XRechnung when the format is XRechnung.
		final CiiValidator validator = new CiiValidator();
		final CiiValidationResult validationResult = validator.validate(ciiXml, config.getFormat());

		if (!validationResult.isValid())
		{
			log.warn("E-invoice Schematron validation found {} FATAL/ERROR failures for invoice {}. Rule IDs: {}",
					validationResult.getFatalAndErrorRuleIds().size(),
					invoiceId,
					validationResult.getFatalAndErrorRuleIds());
		}

		return Optional.of(GenerateAndValidateResult.builder()
				.ciiXml(ciiXml)
				.validationResult(validationResult)
				.build());
	}

	/**
	 * Maps the four CII namespace URIs to their standard short prefixes.
	 * Without this mapper, JAXB-RI assigns auto-generated prefixes (ns2, ns3, …) which are
	 * semantically equivalent but fail the Mustangproject prefix check in
	 * {@code CustomXMLProvider.setXML()} that expects {@code rsm:CrossIndustryInvoice}.
	 */
	private static final NamespacePrefixMapper CII_NAMESPACE_PREFIX_MAPPER = new NamespacePrefixMapper()
	{
		@Override
		public String getPreferredPrefix(final String namespaceUri, final String suggestion, final boolean requirePrefix)
		{
			switch (namespaceUri)
			{
				case "urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100":
					return "rsm";
				case "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100":
					return "ram";
				case "urn:un:unece:uncefact:data:standard:UnqualifiedDataType:100":
					return "udt";
				case "urn:un:unece:uncefact:data:standard:QualifiedDataType:100":
					return "qdt";
				default:
					return suggestion;
			}
		}
	};

	@NonNull
	private String marshalToXml(@NonNull final CrossIndustryInvoiceType cii)
	{
		try
		{
			// Marshaller is NOT thread-safe — create a new one per call, reuse the shared JAXBContext.
			final Marshaller marshaller = JAXB_CTX.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			// Apply standard CII namespace prefixes (rsm/ram/udt/qdt) so the XML is accepted
			// by Mustangproject's CustomXMLProvider.setXML() which checks for rsm:CrossIndustryInvoice.
			// JAXB-RI property "com.sun.xml.bind.namespacePrefixMapper" is supported by jaxb-impl 2.x (Java 8).
			marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", CII_NAMESPACE_PREFIX_MAPPER);
			final StringWriter sw = new StringWriter();
			marshaller.marshal(new ObjectFactory().createCrossIndustryInvoice(cii), sw);
			return sw.toString();
		}
		catch (final JAXBException ex)
		{
			throw new AdempiereException("Failed to marshal CII invoice to XML", ex);
		}
	}

	/**
	 * Result of {@link EInvoiceCiiService#generateAndValidate(InvoiceId)}.
	 */
	@Value
	@Builder
	public static class GenerateAndValidateResult
	{
		/** Marshalled CII XML string (always set). */
		@NonNull String ciiXml;

		/** EN16931 Schematron validation outcome. */
		@NonNull CiiValidationResult validationResult;

		/** Convenience: true when the generated XML passes EN16931 validation with no FATAL/ERROR rules. */
		public boolean isValid()
		{
			return validationResult.isValid();
		}

		/** Convenience: returns the rule IDs of any FATAL/ERROR violations. */
		@Nullable
		public java.util.List<String> getFatalAndErrorRuleIds()
		{
			return validationResult.getFatalAndErrorRuleIds();
		}
	}
}
