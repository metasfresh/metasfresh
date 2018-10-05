package de.metas.vertical.healthcare.forum_datenaustausch_ch.rest;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import de.metas.Profiles;
import de.metas.util.web.MetasfreshRestAPIConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.NonNull;

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
@RequestMapping(ImportInvoice440RestController.ENDPOINT)
@Profile(Profiles.PROFILE_App)
@Api(value = "forum-datenaustausch.ch invoice v4.4 XML endpoint")
public class ImportInvoice440RestController
{
	public static final String ENDPOINT = MetasfreshRestAPIConstants.ENDPOINT_API + "/forum-datenaustausch.ch";

	private final XmlToOLCandsService xmlToOLCandsService;

	public ImportInvoice440RestController(@NonNull final XmlToOLCandsService xmlToOLCandsService)
	{
		this.xmlToOLCandsService = xmlToOLCandsService;
	}

	@PostMapping(path = "importInvoiceXML/v440")
	// TODO *maybe* check if the framework can do the marshaling...if it's nice&easy
	@ApiOperation(value = "Upload forum-datenaustausch.ch invoice-XML into metasfresh")
	public String importInvoiceXML(@RequestParam("file") @NonNull final MultipartFile xmlInvoiceFile)
	{
		final String externalOrderId = xmlToOLCandsService.createOLCands(xmlInvoiceFile);
		return externalOrderId;
	}
}
