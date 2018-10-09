package de.metas.vertical.healthcare.forum_datenaustausch_ch.commons.invoice_440;

import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.google.common.annotations.VisibleForTesting;

import de.metas.invoice_gateway.spi.InvoiceExportClient;
import de.metas.invoice_gateway.spi.model.Invoice;
import de.metas.invoice_gateway.spi.model.InvoiceTax;
import de.metas.invoice_gateway.spi.model.Money;
import de.metas.util.Check;
import de.metas.vertical.healthcare_ch.invoice_gateway.forum_datenaustausch_ch.invoice_440.request.BalanceType;
import de.metas.vertical.healthcare_ch.invoice_gateway.forum_datenaustausch_ch.invoice_440.request.BodyType;
import de.metas.vertical.healthcare_ch.invoice_gateway.forum_datenaustausch_ch.invoice_440.request.InvoiceType;
import de.metas.vertical.healthcare_ch.invoice_gateway.forum_datenaustausch_ch.invoice_440.request.ObjectFactory;
import de.metas.vertical.healthcare_ch.invoice_gateway.forum_datenaustausch_ch.invoice_440.request.PayloadType;
import de.metas.vertical.healthcare_ch.invoice_gateway.forum_datenaustausch_ch.invoice_440.request.ProcessingType;
import de.metas.vertical.healthcare_ch.invoice_gateway.forum_datenaustausch_ch.invoice_440.request.PrologType;
import de.metas.vertical.healthcare_ch.invoice_gateway.forum_datenaustausch_ch.invoice_440.request.RequestType;
import de.metas.vertical.healthcare_ch.invoice_gateway.forum_datenaustausch_ch.invoice_440.request.SoftwareType;
import de.metas.vertical.healthcare_ch.invoice_gateway.forum_datenaustausch_ch.invoice_440.request.TransportType;
import de.metas.vertical.healthcare_ch.invoice_gateway.forum_datenaustausch_ch.invoice_440.request.TransportType.Via;
import de.metas.vertical.healthcare_ch.invoice_gateway.forum_datenaustausch_ch.invoice_440.request.VatRateType;
import de.metas.vertical.healthcare_ch.invoice_gateway.forum_datenaustausch_ch.invoice_440.request.VatType;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-healthcare.invoice_gateway.forum_datenaustausch_ch.invoice_commons
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

public class Invoice440ExportClient implements InvoiceExportClient
{
	private static final String CURRENCY_CHF = "CHF";
	private final ObjectFactory jaxbRequestObjectFactory = new ObjectFactory();

	private enum Modus
	{
		PRODUCTION("production"),

		TEST("test");

		@Getter
		private final String value;

		private Modus(String value)
		{
			this.value = value;
		}
	}

	private enum Language
	{
		DE("de"),

		IT("it"),

		FR("fr");

		@Getter
		private final String value;

		private Language(String value)
		{
			this.value = value;
		}
	}

	private static final long VALIDATION_STATUS_OK = 0L;

	private enum Type
	{
		INVOICE("invoice"),

		REMINDER("reminder");

		@Getter
		private final String value;

		private Type(String value)
		{
			this.value = value;
		}
	}

	@Override
	public void export(@NonNull final Invoice invoice)
	{
		createJaxb(invoice);
	}

	@VisibleForTesting
	JAXBElement<RequestType> createJaxb(@NonNull final Invoice invoice)
	{
		final RequestType requestType = jaxbRequestObjectFactory.createRequestType();
		requestType.setModus(Modus.TEST.getValue()); // TODO hardcoded
		requestType.setLanguage(Language.DE.getValue()); // TODO hardcoded
		requestType.setValidationStatus(VALIDATION_STATUS_OK);

		requestType.setProcessing(createProcessingType(invoice));

		requestType.setPayload(createPayloadType(invoice));

		return jaxbRequestObjectFactory.createRequest(requestType);
	}

	private ProcessingType createProcessingType(@NonNull final Invoice invoice)
	{
		final ProcessingType processingType = jaxbRequestObjectFactory.createProcessingType();

		processingType.setTransport(createTransportType(invoice));

		return processingType;
	}

	private TransportType createTransportType(@NonNull final Invoice invoice)
	{
		final TransportType transportType = jaxbRequestObjectFactory.createTransportType();

		transportType.setFrom(invoice.getBiller().getEan().getValue());
		transportType.setTo(invoice.getRecipient().getEan().getValue());
		transportType.getVia().add(createTransportTypeVia(invoice));

		return transportType;
	}

	private Via createTransportTypeVia(@NonNull final Invoice invoice)
	{
		final Via transportTypeVia = jaxbRequestObjectFactory.createTransportTypeVia();
		transportTypeVia.setSequenceId(1);
		transportTypeVia.setVia(invoice.getBiller().getEan().getValue());
		return transportTypeVia;
	}

	private PayloadType createPayloadType(@NonNull final Invoice invoice)
	{
		final PayloadType payloadType = jaxbRequestObjectFactory.createPayloadType();

		payloadType.setType(Type.INVOICE.getValue()); // TODO hardcoded

		final boolean reversal = invoice.isReversal();
		payloadType.setStorno(reversal);

		payloadType.setCopy(invoice.isCopy());

		final boolean creditAdvise = (reversal && invoice.getAmount().signum() > 0)
				|| (!reversal && invoice.getAmount().signum() < 0);
		payloadType.setCreditAdvice(creditAdvise);

		// payloadType.setCredit(value); we don't send "General Credit Requests", so there is no credit element
		payloadType.setInvoice(createInvoiceType(invoice));
		// payloadType.setReminder(value); we currently don't do dunning
		payloadType.setBody(createBodyType(invoice));

		return payloadType;
	}

	private InvoiceType createInvoiceType(@NonNull final Invoice invoice)
	{
		final InvoiceType invoiceType = jaxbRequestObjectFactory.createInvoiceType();

		invoiceType.setRequestTimestamp(BigInteger.valueOf(invoice.getInvoiceTimestamp().getEpochSecond()));
		invoiceType.setRequestDate(extractRequestDate(invoice));
		invoiceType.setRequestId(invoice.getDocumentNumber());

		return invoiceType;
	}

	private XMLGregorianCalendar extractRequestDate(@NonNull final Invoice invoice)
	{
		try
		{
			return DatatypeFactory
					.newInstance()
					.newXMLGregorianCalendar(invoice.getInvoiceDate().toString());
		}
		catch (DatatypeConfigurationException e)
		{
			throw new RuntimeException("unable to convert invoiceDate=" + invoice.getInvoiceDate() + " to XMLGregorianCalendar; invoice=" + invoice, e);
		}
	}

	private BodyType createBodyType(@NonNull final Invoice invoice)
	{
		final BodyType bodyType = jaxbRequestObjectFactory.createBodyType();

		bodyType.setPlace("association"); // TODO hardcoded
		bodyType.setRole("other"); // TODO hardcoded
		bodyType.setRoleTitle("roleTitle"); // TODO mandatory if role=other

		bodyType.setProlog(createPrologType());

		bodyType.setBalance(createBalanceType(invoice));

		return bodyType;
	}

	private PrologType createPrologType()
	{
		final PrologType prologType = jaxbRequestObjectFactory.createPrologType();
		final SoftwareType softwareType = createSoftwareType();
		prologType.setPackage(softwareType);
		prologType.setGenerator(softwareType);

		return prologType;
	}

	private SoftwareType createSoftwareType()
	{
		final SoftwareType softwareType = jaxbRequestObjectFactory.createSoftwareType();
		softwareType.setName("metasfresh");
		// softwareType.setVersion(value); TODO get the version; use 100*main version + minor version as the version
		// softwareType.setDescription(value); TODO get version string from some properties that we generate during the build
		return softwareType;
	}

	private BalanceType createBalanceType(@NonNull final Invoice invoice)
	{
		final BalanceType balanceType = jaxbRequestObjectFactory.createBalanceType();
		final Money amount = invoice.getAmount();
		Check.assume(CURRENCY_CHF.equals(amount.getCurrency()), "The given invoice.amount's currency needs to be CHF; invoice={}", invoice);

		balanceType.setCurrency(amount.getCurrency());

		balanceType.setAmount(amount.getAmount());

		final Money alreadyPaidAmount = invoice.getAlreadyPaidAmount();
		Check.assume(CURRENCY_CHF.equals(alreadyPaidAmount.getCurrency()), "The given invoice.alreadyPaidAmount's currency needs to be CHF; invoice={}", invoice);

		final BigDecimal amountDue = amount.getAmount().subtract(alreadyPaidAmount.getAmount());
		balanceType.setAmountDue(amountDue);

		balanceType.setVat(createVatType(invoice));

		return balanceType;
	}

	private VatType createVatType(@NonNull final Invoice invoice)
	{
		final VatType vatType = jaxbRequestObjectFactory.createVatType();

		BigDecimal vat = ZERO;
		for (final InvoiceTax invoiceTax : invoice.getInvoiceTaxes())
		{
			vat = vat.add(invoiceTax.getVatAmount());
			vatType.getVatRate().add(createVatRateType(invoiceTax));
		}

		vatType.setVat(vat);
		vatType.setVatNumber(invoice.getBiller().getVatNumber());
		return vatType;
	}

	private VatRateType createVatRateType(@NonNull final InvoiceTax invoiceTax)
	{
		final VatRateType vatRateType = jaxbRequestObjectFactory.createVatRateType();

		vatRateType.setAmount(invoiceTax.getBaseAmount());
		vatRateType.setVatRate(invoiceTax.getRatePercent());
		vatRateType.setVat(invoiceTax.getVatAmount());

		return vatRateType;
	}
}
