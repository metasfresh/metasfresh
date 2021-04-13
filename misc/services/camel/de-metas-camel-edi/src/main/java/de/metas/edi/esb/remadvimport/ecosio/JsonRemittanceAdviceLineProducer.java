/*
 * #%L
 * de-metas-camel-edi
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.edi.esb.remadvimport.ecosio;

import at.erpel.schemas._1p0.documents.extensions.edifact.AdjustmentType;
import at.erpel.schemas._1p0.documents.extensions.edifact.BusinessEntityType;
import at.erpel.schemas._1p0.documents.extensions.edifact.FurtherIdentificationType;
import at.erpel.schemas._1p0.documents.extensions.edifact.ItemType;
import at.erpel.schemas._1p0.documents.extensions.edifact.MonetaryAmountType;
import at.erpel.schemas._1p0.documents.extensions.edifact.REMADVListLineItemExtensionType;
import at.erpel.schemas._1p0.documents.extensions.edifact.TaxType;
import at.erpel.schemas._1p0.documents.extensions.edifact.VATType;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import de.metas.common.rest_api.v1.remittanceadvice.JsonRemittanceAdviceLine;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static de.metas.edi.esb.remadvimport.ecosio.EcosioRemadvConstants.DOCUMENT_ZONE_ID;
import static de.metas.edi.esb.remadvimport.ecosio.EcosioRemadvConstants.DOC_PREFIX;
import static de.metas.edi.esb.remadvimport.ecosio.EcosioRemadvConstants.GLN_PREFIX;
import static de.metas.edi.esb.remadvimport.ecosio.EcosioRemadvConstants.TAX_RATES_TO_IGNORE;

@Value
public class JsonRemittanceAdviceLineProducer
{
	private static final Logger logger = Logger.getLogger(JsonRemittanceAdviceLineProducer.class.getName());



	@NonNull REMADVListLineItemExtensionType remadvLineItemExtension;

	public static JsonRemittanceAdviceLineProducer of(@NonNull final REMADVListLineItemExtensionType remadvListLineItemExtensionType)
	{
		return new JsonRemittanceAdviceLineProducer(remadvListLineItemExtensionType);
	}

	@NonNull
	public JsonRemittanceAdviceLine getLine()
	{
		try
		{
			final REMADVListLineItemExtensionType.MonetaryAmounts monetaryAmounts = remadvLineItemExtension.getMonetaryAmounts();

			if (monetaryAmounts == null)
			{
				throw new RuntimeException("No MonetaryAmounts found for line!");
			}

			return JsonRemittanceAdviceLine.builder()
					.invoiceIdentifier(getInvoiceIdentifier())

					.bpartnerIdentifier(getBPartnerIdentifier().orElse(null))

					.invoiceBaseDocType(getInvoiceDocType().orElse(null))

					.dateInvoiced(getDateInvoiced().orElse(null))

					.remittedAmount(asBigDecimal(monetaryAmounts.getRemittedAmount())
							.orElseThrow(() -> new RuntimeException("RemittedAmount not found on line!")))

					.invoiceGrossAmount(asBigDecimal(monetaryAmounts.getInvoiceGrossAmount()).orElse(null))

					.paymentDiscountAmount(asBigDecimal(monetaryAmounts.getPaymentDiscountAmount()).orElse(null))

					.serviceFeeAmount(asBigDecimal(monetaryAmounts.getCommissionAmount()).orElse(null))

					.serviceFeeVatRate(getServiceFeeVATRate(monetaryAmounts).orElse(null))
					.build();
		}
		catch (final Exception e)
		{
			logger.log(Level.SEVERE, "Unexpected exception while building JsonRemittanceAdviceLine for line: " + remadvLineItemExtension, e);
			throw e;
		}
	}

	@NonNull
	private String getInvoiceIdentifier()
	{
		if (StringUtils.isEmpty(remadvLineItemExtension.getDocumentNumber()))
		{
			throw new RuntimeException("getInvoiceIdentifier: Missing invoice identifier!");
		}

		return DOC_PREFIX + remadvLineItemExtension.getDocumentNumber();
	}

	@NonNull
	private Optional<String> getBPartnerIdentifier()
	{
		final BusinessEntityType customer = remadvLineItemExtension.getCustomer();

		if (customer == null)
		{
			logger.log(Level.INFO, "*** getBPartnerIdentifier: No customer present on line: " + remadvLineItemExtension);
			return Optional.empty();
		}

		if (!StringUtils.isEmpty(customer.getGLN()))
		{
			return Optional.of(GLN_PREFIX + customer.getGLN());
		}

		final Optional<String> customerGLN = customer.getFurtherIdentification()
				.stream()
				.filter(type -> type.getIdentificationType().equals("GLN"))
				.map(FurtherIdentificationType::getValue)
				.findFirst();

		if (customerGLN.isEmpty())
		{
			logger.log(Level.INFO, "*** getBPartnerIdentifier: No customer GLN identification found on line: " + remadvLineItemExtension);
			return Optional.empty();
		}

		return customerGLN.map(gln -> GLN_PREFIX + gln);
	}

	@NonNull
	private Optional<String> getInvoiceDocType()
	{
		if (StringUtils.isEmpty(remadvLineItemExtension.getDocumentName()))
		{
			logger.log(Level.INFO, "getInvoiceDocType no invoiceDocType found for line! Line: " + remadvLineItemExtension);
			return Optional.empty();
		}

		final Optional<String> invoiceDocBaseType = InvoiceType.getByEdiCode(remadvLineItemExtension.getDocumentName())
				.map(InvoiceType::getMetasDocBaseType);

		if (invoiceDocBaseType.isEmpty())
		{
			logger.log(Level.INFO, "getInvoiceDocType no invoiceDocType found for line! Line: " + remadvLineItemExtension);
		}

		return invoiceDocBaseType;
	}

	@NonNull
	private Optional<String> getDateInvoiced()
	{
		final XMLGregorianCalendar dateInvoice = remadvLineItemExtension.getDocumentDate();

		if (dateInvoice == null)
		{
			return Optional.empty();
		}

		final Instant instantDateInvoice = dateInvoice.toGregorianCalendar()
				.toZonedDateTime()
				.withZoneSameLocal(ZoneId.of(DOCUMENT_ZONE_ID))
				.toInstant();

		return Optional.of(instantDateInvoice.toString());
	}

	@NonNull
	private Optional<BigDecimal> asBigDecimal(@Nullable final MonetaryAmountType monetaryAmountType)
	{
		if (monetaryAmountType == null)
		{
			return Optional.empty();
		}

		return Optional.of(monetaryAmountType.getAmount().abs());
	}

	@VisibleForTesting
	Optional<BigDecimal> getServiceFeeVATRate(@NonNull final REMADVListLineItemExtensionType.MonetaryAmounts monetaryAmounts)
	{
		if (CollectionUtils.isEmpty(monetaryAmounts.getAdjustment()))
		{
			logger.log(Level.INFO, "No adjustments found on line! Line:" + remadvLineItemExtension);
			return Optional.empty();
		}

		final ImmutableSet<BigDecimal> vatTaxRateSet = monetaryAmounts.getAdjustment()
				.stream()
				.map(AdjustmentType::getTax)
				.filter(Objects::nonNull)
				.map(TaxType::getVAT)
				.filter(Objects::nonNull)
				.map(VATType::getItem)
				.flatMap(List::stream)
				.map(ItemType::getTaxRate)
				.filter(type -> !TAX_RATES_TO_IGNORE.contains(type.toString()))
				.collect(ImmutableSet.toImmutableSet());

		if (vatTaxRateSet.size() > 1)
		{
			throw new RuntimeException("Multiple vatTax rates found on the line! TaxRates: " + vatTaxRateSet);
		}
		else if (vatTaxRateSet.isEmpty())
		{
			logger.log(Level.INFO, "No vat tax rates found on line! Line:" + remadvLineItemExtension);
			return Optional.empty();
		}
		else
		{
			return vatTaxRateSet.stream().findFirst();
		}
	}

	@Getter
	private enum InvoiceType
	{
		SALES_INVOICE("RG", "ARI"),
		CREDIT_MEMO("GS", "ARC");

		@NonNull
		private final String ediCode;

		@NonNull
		private final String metasDocBaseType;

		InvoiceType(final @NonNull String ediCode, final @NonNull String metasDocBaseType)
		{
			this.ediCode = ediCode;
			this.metasDocBaseType = metasDocBaseType;
		}

		@NonNull
		private static Optional<InvoiceType> getByEdiCode(@NonNull final String ediCode)
		{
			return Arrays.stream(values())
					.filter(type -> type.getEdiCode().equals(ediCode))
					.findFirst();
		}
	}
}
