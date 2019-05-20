package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.response;

import java.util.List;

import com.google.common.collect.ImmutableList;

import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlInvoice;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlInvoice.XmlInvoiceBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.response.model.XmlPayload;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.response.model.XmlPayload.XmlPayloadBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.response.model.XmlResponse;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.response.model.XmlResponse.XmlResponseBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.response.model.payload.XmlBody;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.response.model.payload.XmlBody.XmlBodyBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.response.model.payload.body.XmlRejected;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.response.model.payload.body.XmlRejected.XmlRejectedBuilder;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.response.model.payload.body.rejected.XmlError;
import lombok.NonNull;

/*
 * #%L
 * vertical-healthcare_ch.forum_datenaustausch_ch.invoice_440.response
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

public class Invoice440ToCrossVersionModelTool
{
	public static final Invoice440ToCrossVersionModelTool INSTANCE = new Invoice440ToCrossVersionModelTool();

	private Invoice440ToCrossVersionModelTool()
	{
	}

	public XmlResponse toCrossVersionModel(@NonNull final ResponseType invoice440Response)
	{
		final XmlResponseBuilder builder = XmlResponse.builder();

		updateBuilderForResponse(invoice440Response, builder);

		return builder.build();
	}

	private void updateBuilderForResponse(
			@NonNull final ResponseType invoice440Response,
			@NonNull final XmlResponseBuilder xResponse)
	{
		xResponse.payload(createXmlPayload(invoice440Response.getPayload()));
	}

	private XmlPayload createXmlPayload(@NonNull final PayloadType payload)
	{
		final XmlPayloadBuilder payloadBuilder = XmlPayload.builder();

		payloadBuilder.invoice(createXmlInvoice(payload.getInvoice()));

		payloadBuilder.body(createXmlBody(payload.getBody()));

		return payloadBuilder.build();
	}

	private XmlInvoice createXmlInvoice(@NonNull final InvoiceType invoiceType)
	{
		final XmlInvoiceBuilder xInvoice = XmlInvoice.builder();

		xInvoice.requestTimestamp(invoiceType.getRequestTimestamp());
		xInvoice.requestDate(invoiceType.getRequestDate());
		xInvoice.requestId(invoiceType.getRequestId());

		return xInvoice.build();
	}

	private XmlBody createXmlBody(@NonNull final BodyType body)
	{
		final XmlBodyBuilder bodyBuilder = XmlBody.builder();

		if (body.getRejected() != null)
		{
			bodyBuilder.rejected(createXmlRejected(body.getRejected()));
		}
		return bodyBuilder.build();
	}

	private XmlRejected createXmlRejected(@NonNull final RejectedType rejected)
	{
		final XmlRejectedBuilder rejectedBuilder = XmlRejected.builder();

		rejectedBuilder.explanation(rejected.getExplanation())
				.statusIn(rejected.getStatusIn())
				.statusOut(rejected.getStatusOut());

		if (rejected.getError() != null && !rejected.getError().isEmpty())
		{
			rejectedBuilder.errors(createXmlErrors(rejected.getError()));
		}

		return rejectedBuilder.build();
	}

	private List<XmlError> createXmlErrors(@NonNull final List<ErrorType> error)
	{
		final ImmutableList.Builder<XmlError> errorsBuilder = ImmutableList.builder();

		for (final ErrorType errorType : error)
		{
			final XmlError xmlError = XmlError
					.builder()
					.code(errorType.getCode())
					.errorValue(errorType.getErrorValue())
					.recordId(errorType.getRecordId())
					.text(errorType.getText())
					.validValue(errorType.getValidValue())
					.build();
			errorsBuilder.add(xmlError);
		}
		return errorsBuilder.build();
	}
}
