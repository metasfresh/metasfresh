package de.metas.vertical.healthcare.forum_datenaustausch_ch.commons;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;

import lombok.NonNull;

import javax.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.stereotype.Component;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Maps;

import de.metas.invoice_gateway.spi.InvoiceExportClient;
import de.metas.invoice_gateway.spi.model.AddressInfo;
import de.metas.invoice_gateway.spi.model.ESRPaymentInfo;
import de.metas.invoice_gateway.spi.model.Invoice;
import de.metas.invoice_gateway.spi.model.InvoiceAttachment;
import de.metas.invoice_gateway.spi.model.InvoiceExportResult;
import de.metas.invoice_gateway.spi.model.InvoiceLine;
import de.metas.invoice_gateway.spi.model.InvoiceTax;
import de.metas.invoice_gateway.spi.model.MetasfreshVersion;
import de.metas.invoice_gateway.spi.model.Money;
import de.metas.invoice_gateway.spi.model.PersonInfo;
import de.metas.util.Check;
import de.metas.vertical.healthcare.forum_datenaustausch_ch.commons.Types.RequestType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.CrossVersionRequestConverter;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.XmlPayload;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.XmlRequest;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.XmlPayload.PayloadMod;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.XmlRequest.RequestMod;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlCompany;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlPerson;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlPostal;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.payload.XmlBody;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.payload.XmlBody.BodyMod;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.payload.XmlInvoice.InvoiceMod;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.payload.body.XmlEsr;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.payload.body.XmlService;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.payload.body.XmlBalance.BalanceMod;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.payload.body.XmlProlog.PrologMod;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.payload.body.XmlService.ServiceModWithSelector;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.payload.body.XmlService.ServiceModWithSelector.ServiceModWithSelectorBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.payload.body.esr.XmlBank;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.payload.body.esr.XmlEsr9;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.payload.body.prolog.XmlSoftware.SoftwareMod;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.payload.body.vat.XmlVatRate;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.payload.body.vat.XmlVat.VatMod;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.payload.body.vat.XmlVat.VatMod.VatModBuilder;

/*
 * #%L
 * vertical-healthcare_ch.invoice_gateway.forum_datenaustausch_ch.invoice_commons
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Component
public class InvoiceExportClientImpl implements InvoiceExportClient
{

	private final CrossVersionServiceRegistry crossVersionServiceRegistry;

	public InvoiceExportClientImpl(@NonNull final CrossVersionServiceRegistry crossVersionServiceRegistry)
	{
		this.crossVersionServiceRegistry = crossVersionServiceRegistry;
	}

	@Override
	public boolean canExport(Invoice invoice)
	{
		final ImmutableMultimap<CrossVersionRequestConverter<?>, InvoiceAttachment> //
		converters = extractConverters(invoice.getInvoiceAttachments());

		// TODO check if
		// * the invoice's language and currency is OK, and if the invoice has a supported XML attachment
		// * ..and if all the invoice's lines have an externalId that matches a serviceRecord from the XML attachment
		return !converters.isEmpty();
	}

	@Override
	public List<InvoiceExportResult> export(Invoice invoice)
	{
		final ImmutableMultimap<CrossVersionRequestConverter<?>, InvoiceAttachment> //
		converter2ConvertableAttachment = extractConverters(invoice.getInvoiceAttachments());

		ImmutableList.Builder<InvoiceExportResult> exportResults = ImmutableList.builder();

		for (final CrossVersionRequestConverter<? extends Object> converter : converter2ConvertableAttachment.keySet())
		{
			for (final InvoiceAttachment attachment : converter2ConvertableAttachment.get(converter))
			{
				final XmlRequest xRequest = converter.toCrossVersionRequest(attachment.getData());

				final XmlRequest xAugmentedRequest = augmentRequest(xRequest, invoice);

				final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

				converter.fromCrossVersionRequest(xAugmentedRequest, new ByteArrayOutputStream());

				final InvoiceExportResult exportResult = InvoiceExportResult.builder()
						.data(outputStream)
						.fileName("Export_" + attachment.getFileName())
						.typeName(attachment.getTypeName())
						.build();

				exportResults.add(exportResult);
			}
		}

		return exportResults.build();
	}

	private ImmutableMultimap<CrossVersionRequestConverter<?>, InvoiceAttachment> extractConverters(
			@NonNull final List<InvoiceAttachment> invoiceAttachments)
	{
		final Builder<CrossVersionRequestConverter<?>, InvoiceAttachment> //
		result = ImmutableMultimap.<CrossVersionRequestConverter<?>, InvoiceAttachment> builder();

		for (final InvoiceAttachment attachment : invoiceAttachments)
		{
			final CrossVersionRequestConverter<?> converter = crossVersionServiceRegistry.getConverter(attachment.getTypeName());
			if (converter == null)
			{
				continue;
			}

			result.put(converter, attachment);
		}

		return result.build();
	}

	private XmlRequest augmentRequest(
			@NonNull final XmlRequest xRequest,
			@NonNull final Invoice invoice)
	{
		final RequestMod requestMod = RequestMod
				.builder()
				.payloadMod(createPayloadMod(invoice, xRequest.getPayload()))
				.build();

		return xRequest.withMod(requestMod);
	}

	private PayloadMod createPayloadMod(
			@NonNull final Invoice invoice,
			@NonNull final XmlPayload xPayLoad)
	{
		final boolean reversal = invoice.isReversal();

		final boolean creditAdvise = (reversal && invoice.getAmount().signum() > 0)
				|| (!reversal && invoice.getAmount().signum() < 0);

		return PayloadMod.builder()
				.type(RequestType.INVOICE.getValue())
				.storno(reversal)
				.creditAdvice(creditAdvise)
				.invoiceMod(createInvoiceMod(invoice))
				.bodyMod(createBodyMod(invoice, xPayLoad.getBody()))
				.build();
	}

	private InvoiceMod createInvoiceMod(@NonNull final Invoice invoice)
	{
		return InvoiceMod.builder()
				.requestTimestamp(BigInteger.valueOf(invoice.getInvoiceTimestamp().getEpochSecond()))
				.requestDate(asXmlCalendar(invoice.getInvoiceDate()))
				.requestId(invoice.getDocumentNumber())
				.build();
	}

	private XMLGregorianCalendar asXmlCalendar(@NonNull final GregorianCalendar cal)
	{
		try
		{
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
		}
		catch (DatatypeConfigurationException e)
		{
			throw new RuntimeException(e);
		}
	}

	private BodyMod createBodyMod(
			@NonNull final Invoice invoice,
			@NonNull final XmlBody xBody)
	{
		return BodyMod
				.builder()
				.prologMod(createPrologMod(invoice.getMetasfreshVersion()))
				.balanceMod(createBalanceMod(invoice))
				.esr(createXmlEsr(invoice.getPaymentInfo()))
				.serviceModsWithSelectors(createServiceModsWithSelectors(invoice, xBody))
				.build();
	}

	private PrologMod createPrologMod(@NonNull final MetasfreshVersion metasfreshVersion)
	{
		final SoftwareMod softwareMod = createSoftwareMod(metasfreshVersion);
		return PrologMod.builder()
				.pkgMod(softwareMod)
				.generatorMod(createSoftwareMod(metasfreshVersion))
				.build();
	}

	private SoftwareMod createSoftwareMod(@NonNull final MetasfreshVersion metasfreshVersion)
	{
		final long versionNumber = metasfreshVersion.getMajor() * 100 + metasfreshVersion.getMinor(); // .. as advised in the documentation

		return SoftwareMod
				.builder()
				.name("metasfresh")
				.version(versionNumber)
				.description(metasfreshVersion.getFullVersion())
				.id(0L)
				.copyright("Copyright (C) 2018 metas GmbH")
				.build();
	}

	private BalanceMod createBalanceMod(@NonNull final Invoice invoice)
	{
		final Money amount = invoice.getAmount();

		final Money alreadyPaidAmount = invoice.getAlreadyPaidAmount();
		final BigDecimal amountDue = amount.getAmount().subtract(alreadyPaidAmount.getAmount());

		return BalanceMod.builder()
				.currency(amount.getCurrency())
				.amount(amount.getAmount())
				.amountDue(amountDue)
				.vatMod(createVatMod(invoice))
				.build();
	}

	private VatMod createVatMod(@NonNull final Invoice invoice)
	{
		final VatModBuilder builder = VatMod.builder();

		BigDecimal vatAmountSum = ZERO;
		for (final InvoiceTax invoiceTax : invoice.getInvoiceTaxes())
		{
			final XmlVatRate xVatRate = XmlVatRate.builder()
					.amount(invoiceTax.getBaseAmount())
					.vatRate(invoiceTax.getRatePercent())
					.vat(invoiceTax.getVatAmount())
					.build();
			builder.vatRate(xVatRate);

			vatAmountSum = vatAmountSum.add(invoiceTax.getVatAmount());
		}

		return builder
				.vat(vatAmountSum)
				.vatNumber(invoice.getBiller().getVatNumber())
				.build();
	}

	private XmlEsr createXmlEsr(@NonNull final ESRPaymentInfo paymentInfo)
	{
		return XmlEsr9.builder()
				.type("16or27")
				.participantNumber(paymentInfo.getParticipantNumber())
				.referenceNumber(paymentInfo.getReferenceNumber())
				.codingLine(paymentInfo.getCodingLine())
				.bank(createXmlBank(paymentInfo))
				.build();
	}

	private XmlBank createXmlBank(@NonNull final ESRPaymentInfo paymentInfo)
	{
		return XmlBank.builder()
				.company(createXmlCompany(paymentInfo.getCompanyName(), paymentInfo.getAddressInfo()))
				.person(createXmlPerson(paymentInfo.getPersonInfo(), paymentInfo.getAddressInfo()))
				.build();
	}

	private XmlCompany createXmlCompany(
			@Nullable final String companyName,
			@NonNull final AddressInfo addressInfo)
	{
		if (companyName == null)
		{
			return null;
		}
		return XmlCompany.builder()
				.companyname(companyName)
				.postal(createXmlPostal(addressInfo))
				.build();

	}

	private XmlPerson createXmlPerson(
			@Nullable final PersonInfo personInfo,
			@NonNull final AddressInfo addressInfo)
	{
		if (personInfo == null)
		{
			return null;
		}
		return XmlPerson.builder()
				.familyname(personInfo.getFamilyName())
				.givenname(personInfo.getGivenName())
				.postal(createXmlPostal(addressInfo))
				.build();
	}

	private XmlPostal createXmlPostal(@NonNull final AddressInfo addressInfo)
	{
		return XmlPostal.builder()
				.street(addressInfo.getStreet())
				.zip(addressInfo.getZip())
				.city(addressInfo.getCity())
				.stateCode(addressInfo.getState())
				.countryCode(addressInfo.getIsoCountryCode())
				.build();
	}

	private List<ServiceModWithSelector> createServiceModsWithSelectors(
			@NonNull final Invoice invoice,
			@NonNull final XmlBody xBody)
	{
		final ImmutableList.Builder<ServiceModWithSelector> serviceMods = ImmutableList.builder();

		final List<XmlService> xServices = xBody.getServices();
		final ImmutableMap<Integer, XmlService> //
		recordId2xService = Maps.uniqueIndex(xServices, XmlService::getRecordId);

		for (final InvoiceLine invoiceLine : invoice.getInvoiceLines())
		{
			final List<String> externalIdSegments = Splitter.on("_").splitToList(invoiceLine.getExternalId());
			Check.assume(!externalIdSegments.isEmpty(), "Every line of an exportable invoice needs to have an externalId; invoiceLine={}, invoice={}", invoiceLine, invoice);

			final String recordIdStr = externalIdSegments.get(externalIdSegments.size() - 1);
			final int recordId = Integer.parseInt(recordIdStr);

			final XmlService xServiceForInvoiceLine = recordId2xService.get(recordId);
			final BigDecimal xServiceAmount = xServiceForInvoiceLine.getAmount();
			final BigDecimal xServiceExternalFactor = xServiceForInvoiceLine.getExternalFactor();

			final Money invoiceLineMoney = invoiceLine.getLineAmount();
			final BigDecimal invoiceLineAmount = invoiceLineMoney.getAmount();

			final ServiceModWithSelectorBuilder serviceMod = ServiceModWithSelector
					.builder()
					.recordId(recordId);

			if (invoiceLineAmount.compareTo(xServiceAmount) == 0)
			{
				serviceMods.add(serviceMod.build()); // nothing to change
				continue;
			}

			serviceMod.amount(invoiceLineAmount);

			final BigDecimal externalFactorMod;
			if (xServiceAmount.signum() == 0 || xServiceExternalFactor.signum() == 0)
			{
				externalFactorMod = ONE;
			}
			else
			{
				externalFactorMod = invoiceLineAmount
						.setScale(invoiceLineAmount.scale() + 10)
						.multiply(xServiceExternalFactor)
						.divide(xServiceAmount);
			}
			serviceMod.externalFactor(externalFactorMod);
			serviceMods.add(serviceMod.build());
		}
		return serviceMods.build();
	}
}
