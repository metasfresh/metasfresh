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
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.common.rest_api.v1.remittanceadvice.JsonRemittanceAdviceLine;
import de.metas.common.util.Check;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.springframework.util.CollectionUtils;

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

import static de.metas.common.util.CoalesceUtil.coalesceNotNull;
import static de.metas.edi.esb.remadvimport.ecosio.EcosioRemadvConstants.ADJUSTMENT_CODE_19;
import static de.metas.edi.esb.remadvimport.ecosio.EcosioRemadvConstants.ADJUSTMENT_CODE_67;
import static de.metas.edi.esb.remadvimport.ecosio.EcosioRemadvConstants.ADJUSTMENT_CODE_90;
import static de.metas.edi.esb.remadvimport.ecosio.EcosioRemadvConstants.DOCUMENT_ZONE_ID;
import static de.metas.edi.esb.remadvimport.ecosio.EcosioRemadvConstants.DOC_PREFIX;
import static de.metas.edi.esb.remadvimport.ecosio.EcosioRemadvConstants.GLN_PREFIX;
import static de.metas.edi.esb.remadvimport.ecosio.JsonRemittanceAdviceLineProducer.InvoiceType.CREDIT_MEMO;
import static java.math.BigDecimal.ZERO;

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

			final BigDecimal invoiceGrossAmount = asBigDecimalAbs(monetaryAmounts.getInvoiceGrossAmount()).orElse(null);

			final BigDecimal remittedAmount = asBigDecimalAbs(monetaryAmounts.getRemittedAmount())
					.orElseThrow(() -> new RuntimeException("RemittedAmount not found on line!"));
			final BigDecimal serviceFeeAmount = getServiceFeeAmount(monetaryAmounts);
			final BigDecimal paymentDiscountAmount = getPaymentDiscountAmount(monetaryAmounts);

			// the difference might be a few cents, rappen etc. we will add it to the discount
			final BigDecimal difference = coalesceNotNull(invoiceGrossAmount, ZERO)
					.subtract(remittedAmount)
					.subtract(coalesceNotNull(serviceFeeAmount, ZERO))
					.subtract(coalesceNotNull(paymentDiscountAmount, ZERO));

			return JsonRemittanceAdviceLine.builder()
					.invoiceIdentifier(getInvoiceIdentifier())
					.bpartnerIdentifier(getBPartnerIdentifier().orElse(null))
					.invoiceBaseDocType(getInvoiceDocType().orElse(null))
					.dateInvoiced(getDateInvoiced().orElse(null))
					.remittedAmount(remittedAmount)
					.invoiceGrossAmount(invoiceGrossAmount)
					.paymentDiscountAmount(sumNullableBigDecimals(paymentDiscountAmount, difference))
					.serviceFeeAmount(serviceFeeAmount)
					.serviceFeeVatRate(getServiceFeeVATRate(monetaryAmounts).orElse(null))
					.build();
		}
		catch (final Exception e)
		{
			logger.log(Level.SEVERE, "Unexpected exception while building JsonRemittanceAdviceLine for line: " + remadvLineItemExtension, e);
			throw e;
		}
	}

	@Nullable
	private BigDecimal getServiceFeeAmount(@NonNull final REMADVListLineItemExtensionType.MonetaryAmounts monetaryAmounts)
	{
		// note that the amounts are negative in the XML, we need to negate them
		Optional<BigDecimal> serviceFeeAmtTerm_67_opt = getAdjustmentAmount(monetaryAmounts, ADJUSTMENT_CODE_67);
		Optional<BigDecimal> serviceFeeAmtTerm_90_opt = getAdjustmentAmount(monetaryAmounts, ADJUSTMENT_CODE_90);
		if (!isCreditMemo())
		{
			// In a credit memo this amount is *positive*. In a normal invoice it is negative. Either way, we need the invoicable service fee amount to be positive.
			// Also, I'm afraid of just doing "abs" here, because one day ther might be a yet different case that we might then miss.
			serviceFeeAmtTerm_67_opt = serviceFeeAmtTerm_67_opt.map(BigDecimal::negate);
			serviceFeeAmtTerm_90_opt = serviceFeeAmtTerm_90_opt.map(BigDecimal::negate);
		}

		final BigDecimal adjustmentServiceFeeAmountTerm1 = serviceFeeAmtTerm_67_opt.orElse(null);
		final BigDecimal adjustmentServiceFeeAmountTerm2 = serviceFeeAmtTerm_90_opt.orElse(null);

		return sumNullableBigDecimals(adjustmentServiceFeeAmountTerm1, adjustmentServiceFeeAmountTerm2);
	}

	@Nullable
	private BigDecimal getPaymentDiscountAmount(@NonNull final REMADVListLineItemExtensionType.MonetaryAmounts monetaryAmounts)
	{
		// these amounts are also negative in the XML, we need to negate them
		final BigDecimal paymentDiscountAmount = asBigDecimalAbs(monetaryAmounts.getPaymentDiscountAmount()).map(BigDecimal::negate).orElse(null);
		final BigDecimal adjustmentDiscountAmount = getAdjustmentAmount(monetaryAmounts, ADJUSTMENT_CODE_19).map(BigDecimal::negate).orElse(null);

		final BigDecimal paymentDiscountTotalAmount = sumNullableBigDecimals(paymentDiscountAmount, adjustmentDiscountAmount);

		return paymentDiscountTotalAmount != null ? paymentDiscountTotalAmount.abs() : null;
	}

	@Nullable
	private BigDecimal sumNullableBigDecimals(@Nullable final BigDecimal term1, @Nullable final BigDecimal term2)
	{
		if (term1 == null)
		{
			return term2;
		}

		if (term2 == null)
		{
			return term1;
		}

		return term1.add(term2);
	}

	@NonNull
	private Optional<BigDecimal> getAdjustmentAmount(
			@NonNull final REMADVListLineItemExtensionType.MonetaryAmounts monetaryAmounts,
			@NonNull final String adjustmentCode)
	{

		final ImmutableList<BigDecimal> adjustmentTypeList = monetaryAmounts
				.getAdjustment().stream()
				.filter(adjustment -> adjustmentCode.equals(adjustment.getReasonCode()))
				.map(adjustmentType -> asBigDecimal(adjustmentType.getAdjustmentMonetaryAmount()))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(ImmutableList.toImmutableList());

		if (CollectionUtils.isEmpty(adjustmentTypeList))
		{
			return Optional.empty();
		}

		return Optional.of(adjustmentTypeList.stream().reduce(ZERO, BigDecimal::add));
	}

	@NonNull
	private String getInvoiceIdentifier()
	{
		if (Check.isBlank(remadvLineItemExtension.getDocumentNumber()))
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

		if (Check.isNotBlank(customer.getGLN()))
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
		if (Check.isBlank(remadvLineItemExtension.getDocumentName()))
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

	private boolean isCreditMemo()
	{
		return getInvoiceDocType().orElse("").equals(CREDIT_MEMO.metasDocBaseType);
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
	private Optional<BigDecimal> asBigDecimalAbs(@Nullable final MonetaryAmountType monetaryAmountType)
	{
		return asBigDecimal(monetaryAmountType);
	}

	@NonNull
	private Optional<BigDecimal> asBigDecimal(@Nullable final MonetaryAmountType monetaryAmountType)
	{
		if (monetaryAmountType == null)
		{
			return Optional.empty();
		}

		return Optional.of(monetaryAmountType.getAmount());
	}

	@VisibleForTesting
	Optional<BigDecimal> getServiceFeeVATRate(@NonNull final REMADVListLineItemExtensionType.MonetaryAmounts monetaryAmounts)
	{
		if (CollectionUtils.isEmpty(monetaryAmounts.getAdjustment()))
		{
			logger.log(Level.INFO, "No adjustments found on line! Line:" + remadvLineItemExtension);
			return Optional.empty();
		}

		final List<String> targetAdjustmentCodes = Arrays.asList(ADJUSTMENT_CODE_67, ADJUSTMENT_CODE_90);

		final ImmutableSet<BigDecimal> vatTaxRateSet = monetaryAmounts.getAdjustment()
				.stream()
				.filter(adjustmentType -> targetAdjustmentCodes.contains(adjustmentType.getReasonCode()))
				.map(AdjustmentType::getTax)
				.filter(Objects::nonNull)
				.map(TaxType::getVAT)
				.filter(Objects::nonNull)
				.map(VATType::getItem)
				.flatMap(List::stream)
				.map(ItemType::getTaxRate)
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
	enum InvoiceType
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
