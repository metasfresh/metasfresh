package de.metas.vertical.healthcare.forum_datenaustausch_ch.rest;

import lombok.NonNull;

import org.springframework.context.annotation.Conditional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import de.metas.ordercandidate.rest.JsonAttachment;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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
@RequestMapping(RestApiConstants.ENDPOINT_INVOICE_440)
@Conditional(RestApiStartupCondition.class)
@Api(value = "forum-datenaustausch.ch invoice v4.4 XML endpoint")
public class HealthcareChInvoice440RestController
{
	private final XmlToOLCandsService xmlToOLCandsService;

	public HealthcareChInvoice440RestController(@NonNull final XmlToOLCandsService xmlToOLCandsService)
	{
		this.xmlToOLCandsService = xmlToOLCandsService;
	}

	@PostMapping(path = "importInvoiceXML/v440")
	@ApiOperation(value = "Upload forum-datenaustausch.ch invoice-XML into metasfresh")
	public JsonAttachment importInvoiceXML(@RequestParam("file") @NonNull final MultipartFile xmlInvoiceFile)
	{
		return xmlToOLCandsService.createOLCands(xmlInvoiceFile);
	}
}
