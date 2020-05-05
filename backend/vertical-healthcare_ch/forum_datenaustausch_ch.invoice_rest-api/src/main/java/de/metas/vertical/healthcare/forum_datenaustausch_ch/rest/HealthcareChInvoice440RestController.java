package de.metas.vertical.healthcare.forum_datenaustausch_ch.rest;

import static de.metas.util.lang.CoalesceUtil.coalesce;

import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import de.metas.rest_api.common.SyncAdvise;
import de.metas.rest_api.common.SyncAdvise.IfExists;
import de.metas.rest_api.common.SyncAdvise.IfNotExists;
import de.metas.rest_api.ordercandidates.response.JsonAttachment;
import de.metas.vertical.healthcare.forum_datenaustausch_ch.rest.XmlToOLCandsService.CreateOLCandsRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@RequestMapping(RestApiConstants.ENDPOINT_INVOICE_440)
@Conditional(RestApiStartupCondition.class)
@Api(description = "forum-datenaustausch.ch invoice v4.4 XML endpoint")
public class HealthcareChInvoice440RestController
{
	private final XmlToOLCandsService xmlToOLCandsService;

	public HealthcareChInvoice440RestController(@NonNull final XmlToOLCandsService xmlToOLCandsService)
	{
		this.xmlToOLCandsService = xmlToOLCandsService;
	}

	@PostMapping(path = "importInvoiceXML/v440")
	@ApiOperation(value = "Upload forum-datenaustausch.ch invoice-XML into metasfresh")
	public ResponseEntity<JsonAttachment> importInvoiceXML(

			@RequestParam("file") @NonNull final MultipartFile xmlInvoiceFile,

			@ApiParam(required = false, defaultValue = "DONT_UPDATE", value = "This is applied only to the biller; the invoice recipient needs to already exist in metasfresh.") //
			@RequestParam(required = false) final SyncAdvise.IfExists ifBPartnersExist,

			@ApiParam(required = false, defaultValue = "CREATE", value = "This is applied only to the biller; the invoice recipient needs to already exist in metasfresh.") //
			@RequestParam(required = false) final SyncAdvise.IfNotExists ifBPartnersNotExist,

			@ApiParam(required = false, defaultValue = "DONT_UPDATE") //
			@RequestParam(required = false) final SyncAdvise.IfExists ifProductsExist,

			@ApiParam(required = false, defaultValue = "CREATE") //
			@RequestParam(required = false) final SyncAdvise.IfNotExists ifProductsNotExist)
	{

		// wrt to the biller-bpartner's org, we use the same advise as with the biller itself
		final SyncAdvise billerSyncAdvise = SyncAdvise.builder()
				.ifExists(coalesce(ifBPartnersExist, IfExists.DONT_UPDATE))
				.ifNotExists(coalesce(ifBPartnersNotExist, IfNotExists.CREATE))
				.build();

		// The only kinds of debitors that xmlToOLCandsService is implemented to create are health insurances
		// Those insurances are supposed to live at Org=*, because we don't want them to be duplicated;
		// Likewise, we don't want normal Org>0 users to edit them.
		final SyncAdvise debitorSyncAdvise = SyncAdvise.READ_ONLY;

		final SyncAdvise productSyncAdvise = SyncAdvise.builder()
				.ifExists(coalesce(ifProductsExist, IfExists.DONT_UPDATE))
				.ifNotExists(coalesce(ifProductsNotExist, IfNotExists.CREATE))
				.build();

		final CreateOLCandsRequest createOLCandsRequest = CreateOLCandsRequest.builder()
				.xmlInvoiceFile(xmlInvoiceFile)
				.billerSyncAdvise(billerSyncAdvise)
				.debitorSyncAdvise(debitorSyncAdvise)
				.productSyncAdvise(productSyncAdvise)
				.build();

		final JsonAttachment result = xmlToOLCandsService.createOLCands(createOLCandsRequest);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}
}
