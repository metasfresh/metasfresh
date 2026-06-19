package de.metas.einvoice;

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
 * Public entry point for CII e-invoice generation and EN16931 Schematron validation.
 *
 * <p>Orchestrates the pipeline:
 * <ol>
 *   <li>{@link EInvoiceConfigService#resolveForInvoice(InvoiceId)} — determine if the invoice's
 *       BPartner is an e-invoice recipient and resolve the format configuration.</li>
 *   <li>{@link CiiMapper#map(I_C_Invoice, EInvoiceRecipientConfig)} — map the invoice to a CII
 *       {@link CrossIndustryInvoiceType} domain object.</li>
 *   <li>Marshal the CII object to XML.</li>
 *   <li>{@link CiiValidator#validate(String)} — validate the marshalled XML against EN16931
 *       Schematron rules.</li>
 * </ol>
 *
 * <p>Returns {@link Optional#empty()} when the BPartner is not an e-invoice recipient,
 * so callers do not need to check eligibility themselves.
 */
@Service
public class EInvoiceCiiService
{
	private static final Logger log = LoggerFactory.getLogger(EInvoiceCiiService.class);

	@NonNull private final EInvoiceConfigService configService;
	@NonNull private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

	public EInvoiceCiiService(@NonNull final EInvoiceConfigService configService)
	{
		this.configService = configService;
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
		final Optional<EInvoiceRecipientConfig> configOpt = configService.resolveForInvoice(invoiceId);
		if (!configOpt.isPresent())
		{
			log.debug("Invoice {} is not an e-invoice recipient — skipping CII generation.", invoiceId);
			return Optional.empty();
		}

		final EInvoiceRecipientConfig config = configOpt.get();
		final I_C_Invoice invoice = invoiceDAO.getByIdInTrx(invoiceId);

		// Map invoice to CII domain object
		final CrossIndustryInvoiceType cii = new CiiMapper().map(invoice, config);

		// Marshal to XML
		final String ciiXml = marshalToXml(cii);

		// Validate against EN16931 Schematron
		final CiiValidator validator = new CiiValidator();
		final CiiValidationResult validationResult = validator.validate(ciiXml);

		if (!validationResult.isValid())
		{
			log.warn("EN16931 Schematron validation found {} FATAL/ERROR failures for invoice {}. Rule IDs: {}",
					validationResult.getFatalAndErrorRuleIds().size(),
					invoiceId,
					validationResult.getFatalAndErrorRuleIds());
		}

		return Optional.of(GenerateAndValidateResult.builder()
				.ciiXml(ciiXml)
				.validationResult(validationResult)
				.build());
	}

	@NonNull
	private String marshalToXml(@NonNull final CrossIndustryInvoiceType cii)
	{
		try
		{
			final JAXBContext ctx = JAXBContext.newInstance(CrossIndustryInvoiceType.class, ObjectFactory.class);
			final Marshaller marshaller = ctx.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
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
