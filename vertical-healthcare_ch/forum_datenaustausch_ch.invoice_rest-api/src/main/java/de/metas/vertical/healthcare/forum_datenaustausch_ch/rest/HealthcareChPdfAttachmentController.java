package de.metas.vertical.healthcare.forum_datenaustausch_ch.rest;

import static de.metas.invoice_gateway.spi.InvoiceExportClientFactory.ATTATCHMENT_TAGNAME_BELONGS_TO_EXTERNAL_REFERENCE;
import static de.metas.invoice_gateway.spi.InvoiceExportClientFactory.ATTATCHMENT_TAGNAME_EXPORT_PROVIDER;

import lombok.NonNull;

import java.io.IOException;

import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.ImmutableList;

import de.metas.ordercandidate.rest.JsonAttachment;
import de.metas.ordercandidate.rest.OrderCandidatesRestEndpoint;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.commons.ForumDatenaustauschChConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/*
 * #%L
 * de.metas.business
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

@RestController
@RequestMapping(RestApiConstants.ENDPOINT_PDF_ATTACHMENT)
@Conditional(RestApiStartupCondition.class)
@Api(value = "forum-datenaustausch.ch XML endpoint")
public class HealthcareChPdfAttachmentController
{
	private final OrderCandidatesRestEndpoint orderCandidatesRestEndpoint;

	public HealthcareChPdfAttachmentController(
			@NonNull final OrderCandidatesRestEndpoint orderCandidatesRestEndpoint)
	{
		this.orderCandidatesRestEndpoint = orderCandidatesRestEndpoint;
	}

	@PostMapping("/attachedPdfFiles/{externalReference}")
	@ApiOperation(value = "Attach a PDF document to an externalOrderId")
	// TODO only allow PDF
	public JsonAttachment attachPdfFile(
			@ApiParam(value = "Reference string that was returned by the invoice-rest-controller", allowEmptyValue = false) //
			@PathVariable("externalReference") final String externalReference,

			@RequestParam("file") @NonNull final MultipartFile file)
			throws IOException
	{
		final ImmutableList<String> tags = ImmutableList.of(
				ATTATCHMENT_TAGNAME_EXPORT_PROVIDER/* name */, ForumDatenaustauschChConstants.INVOICE_EXPORT_PROVIDER_ID/* value */,
				ATTATCHMENT_TAGNAME_BELONGS_TO_EXTERNAL_REFERENCE/* name */, externalReference/* value */);

		return orderCandidatesRestEndpoint
				.attachFile(
						RestApiConstants.INPUT_SOURCE_INTERAL_NAME,
						externalReference,
						tags,
						file);
	}

	@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No order line candidates were found for the given externalOrderId")
	public static class OLCandsNotFoundExeception extends RuntimeException
	{
		private static final long serialVersionUID = 8216181888558013882L;
	}

}
