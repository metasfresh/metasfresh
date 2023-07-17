package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.export.invoice;

import static de.metas.util.Check.assume;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Objects;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import de.metas.invoice_gateway.spi.model.imp.ImportedInvoiceResponse.RejectedError;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.response.model.payload.XmlBody;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.response.model.payload.body.contact.XMLEmployee;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.response.model.payload.body.contact.XMLTelecom;
import org.adempiere.exceptions.AdempiereException;

import de.metas.invoice_gateway.spi.InvoiceImportClient;
import de.metas.invoice_gateway.spi.model.imp.ImportInvoiceResponseRequest;
import de.metas.invoice_gateway.spi.model.imp.ImportedInvoiceResponse;
import de.metas.invoice_gateway.spi.model.imp.ImportedInvoiceResponse.ImportedInvoiceResponseBuilder;
import de.metas.invoice_gateway.spi.model.imp.ImportedInvoiceResponse.Status;
import de.metas.util.xml.XmlIntrospectionUtil;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.CrossVersionServiceRegistry;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.CrossVersionResponseConverter;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlInvoice;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.response.model.XmlPayload;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.response.model.XmlResponse;
import lombok.NonNull;

import javax.annotation.Nullable;

/*
 * #%L
 * vertical-healthcare_ch.forum_datenaustausch_ch.invoice_base
 * %%
 * Copyright (C) 2019 metas GmbH
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

/**
 * Imports XML into an {@link ImportedInvoiceResponse}.
 *
 * Note that we don't yet have a factory for {@link InvoiceImportClient}s because we don't need it yet.
 * The importing process that we currently have is in the same module and knows to use this implementation.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class InvoiceImportClientImpl implements InvoiceImportClient
{
	private final CrossVersionServiceRegistry crossVersionServiceRegistry;

	public InvoiceImportClientImpl(@NonNull final CrossVersionServiceRegistry crossVersionServiceRegistry)
	{
		this.crossVersionServiceRegistry = crossVersionServiceRegistry;
	}

	@Override
	public ImportedInvoiceResponse importInvoiceResponse(@NonNull final ImportInvoiceResponseRequest request)
	{
		try
		{
			return import0(request);
		}
		catch (final IOException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	private ImportedInvoiceResponse import0(@NonNull final ImportInvoiceResponseRequest request) throws IOException
	{
		final InputStream inputStreamToUse = new ByteArrayInputStream(request.getData());
		assume(inputStreamToUse.markSupported(), "ByteArrayInputStreams support mark and reset; inputStream={}", inputStreamToUse);

		inputStreamToUse.mark(Integer.MAX_VALUE);
		final String xsdName = XmlIntrospectionUtil.extractXsdValueOrNull(inputStreamToUse);
		inputStreamToUse.reset();

		final CrossVersionResponseConverter converter = crossVersionServiceRegistry.getResponseConverterForXsdName(Objects.requireNonNull(xsdName));

		final XmlResponse xInvoiceResponse = converter.toCrossVersionResponse(inputStreamToUse);

		final XmlPayload payload = xInvoiceResponse.getPayload();
		final XmlInvoice invoice = payload.getInvoice();
		final XmlBody body = payload.getBody();

		final Instant invoiceCreatedTimestamp = Instant.ofEpochSecond(invoice.getRequestTimestamp().longValue());

		final Instant invoiceResponseTimestamp = Instant.ofEpochSecond(payload.getResponseTimestamp());

		final ImportedInvoiceResponseBuilder builder = ImportedInvoiceResponse
				.builder()
				.documentNumber(invoice.getRequestId()) //invoiceNo
				.invoiceCreated(invoiceCreatedTimestamp)
				.invoiceResponse(invoiceResponseTimestamp)
				.client(getClient(body))
				.invoiceRecipient(getRecipient(body))
				.reason(getErrors(body))
				.explanation(getExplanation(body))
				.responsiblePerson(getResponsiblePerson(body))
				.phone(getPhone(body))
				.email(getEmail(body))
				.billerEan(getBillerEan(body))
				.request(request);

		if (body.getRejected() != null)
		{
			builder.status(Status.REJECTED);
		}

		return builder.build();
	}

	@NonNull
	private String getClient(@NonNull final XmlBody body)
	{
		return body.getPatient().getPerson().getFamilyName() + " " + body.getPatient().getPerson().getGivenName();
	}

	@NonNull
	private String getBillerEan(@NonNull final XmlBody body)
	{
		return body.getBiller().getEanParty();
	}

	@Nullable
	private String getEmail(@NonNull final XmlBody body)
	{
		if (body.getContact().getEmployee() != null)
			if (body.getContact().getEmployee().getOnline() != null)
			{
				return Joiner
						.on("; ")
						.join(body.getContact().getEmployee().getOnline().getEmail());
			}
		return null;
	}

	@Nullable
	private String getPhone(@NonNull final XmlBody body)
	{
		final XMLEmployee employee = body.getContact().getEmployee();
		if (employee != null)
		{
			final XMLTelecom employeeTelecom = employee.getTelecom();
			if (employeeTelecom != null)
			{
				return Joiner
						.on("; ")
						.join(employeeTelecom.getPhone());
			}
		}
		return null;
	}

	@Nullable
	private String getResponsiblePerson(@NonNull final XmlBody body)
	{
		String response = "";

		final XMLEmployee employee = body.getContact().getEmployee();

		if (employee != null)
		{
			response += employee.getFamilyName();
			if (employee.getGivenName() != null)
			{
				response += " " + employee.getGivenName();
			}
		}

		if (response.isEmpty())
		{
			return null;
		}
		return response;
	}

	@Nullable
	private String getExplanation(final XmlBody body)
	{
		if (body.getRejected() != null)
		{
			return body.getRejected().getExplanation();
		}
		return null;
	}

	@NonNull
	private String getRecipient(@NonNull final XmlBody body)
	{
		return body.getContact().getCompany().getCompanyName();
	}

	@SuppressWarnings("UnstableApiUsage")
	private ImmutableList<RejectedError> getErrors(@NonNull final XmlBody body)
	{
		if (body.getRejected() != null)
		{
			return body.getRejected().getErrors()
					.stream()
					.map(it -> new RejectedError(it.getCode(), it.getText()))
					.collect(ImmutableList.toImmutableList());
		}
		return ImmutableList.of();
	}

}
