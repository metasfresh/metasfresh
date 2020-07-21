package de.metas.vertical.healthcare.forum_datenaustausch_ch.rest;

import static de.metas.common.util.CoalesceUtil.coalesce;

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
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.HealthCareInvoiceDocSubType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
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

@Api(tags = { "forum-datenaustausch.ch XML endpoint" })
@SwaggerDefinition(tags = {
		@Tag(name = "forum-datenaustausch.ch XML endpoint", description = "forum-datenaustausch.ch invoice v4.4 XML endpoint")
})
public class HealthcareChInvoice440RestController
{
	private final XmlToOLCandsService xmlToOLCandsService;

	public HealthcareChInvoice440RestController(@NonNull final XmlToOLCandsService xmlToOLCandsService)
	{
		this.xmlToOLCandsService = xmlToOLCandsService;
	}

	@PostMapping(path = "importInvoiceXML/v440/KV")
	@ApiOperation(value = "Upload a forum-datenaustausch.ch insurance invoice-XML (\"Krankenversicherung\") into metasfresh")
	public ResponseEntity<JsonAttachment> importInsuranceInvoiceXML(

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
		// Districts / Kantone are supposed to live at Org=*, because we don't want them to be duplicated;
		// Likewise, we don't want normal Org>0 users to edit them.
		final SyncAdvise debitorSyncAdvise = SyncAdvise.builder()
				.ifExists(IfExists.DONT_UPDATE)
				.ifNotExists(IfNotExists.FAIL)
				.build();

		// wrt to the biller-bpartner's org, we use the same advise as with the biller itself
		final SyncAdvise billerSyncAdvise = SyncAdvise.builder()
				.ifExists(coalesce(ifBPartnersExist, IfExists.DONT_UPDATE))
				.ifNotExists(coalesce(ifBPartnersNotExist, IfNotExists.CREATE))
				.build();

		final SyncAdvise productSyncAdvise = SyncAdvise.builder()
				.ifExists(coalesce(ifProductsExist, IfExists.DONT_UPDATE))
				.ifNotExists(coalesce(ifProductsNotExist, IfNotExists.CREATE))
				.build();

		return importInvoiceXML(xmlInvoiceFile, HealthCareInvoiceDocSubType.KV, billerSyncAdvise, debitorSyncAdvise, productSyncAdvise);
	}

	@PostMapping(path = "importInvoiceXML/v440/KT")
	@ApiOperation(value = "Upload a forum-datenaustausch.ch state invoice-XML (\"Kanton\") into metasfresh")
	public ResponseEntity<JsonAttachment> importDistrictInvoiceXML(

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
		// Districts / Kantone are supposed to live at Org=*, because we don't want them to be duplicated;
		// Likewise, we don't want normal Org>0 users to edit them.
		final SyncAdvise debitorSyncAdvise = SyncAdvise.builder()
				.ifExists(IfExists.DONT_UPDATE)
				.ifNotExists(IfNotExists.FAIL)
				.build();

		// wrt to the biller-bpartner's org, we use the same advise as with the biller itself
		final SyncAdvise billerSyncAdvise = SyncAdvise.builder()
				.ifExists(coalesce(ifBPartnersExist, IfExists.DONT_UPDATE))
				.ifNotExists(coalesce(ifBPartnersNotExist, IfNotExists.CREATE))
				.build();

		final SyncAdvise productSyncAdvise = SyncAdvise.builder()
				.ifExists(coalesce(ifProductsExist, IfExists.DONT_UPDATE))
				.ifNotExists(coalesce(ifProductsNotExist, IfNotExists.CREATE))
				.build();

		return importInvoiceXML(xmlInvoiceFile, HealthCareInvoiceDocSubType.KT, billerSyncAdvise, debitorSyncAdvise, productSyncAdvise);
	}

	@PostMapping(path = "importInvoiceXML/v440/EA")
	@ApiOperation(value = "Upload a forum-datenaustausch.ch patient invoice-XML (\"Eigenanteil\") into metasfresh")
	public ResponseEntity<JsonAttachment> importPatientInvoiceXML(

			@RequestParam("file") @NonNull final MultipartFile xmlInvoiceFile,

			@ApiParam(required = false, defaultValue = "DONT_UPDATE", value = "This is applied only to the biller; the invoice recipient (patient) is always created or updated on the fly.") //
			@RequestParam(required = false) final SyncAdvise.IfExists ifBPartnersExist,

			@ApiParam(required = false, defaultValue = "CREATE", value = "This is applied only to the biller; the invoice recipient (patient) is always created or updated on the fly.") //
			@RequestParam(required = false) final SyncAdvise.IfNotExists ifBPartnersNotExist,

			@ApiParam(required = false, defaultValue = "DONT_UPDATE") //
			@RequestParam(required = false) final SyncAdvise.IfExists ifProductsExist,

			@ApiParam(required = false, defaultValue = "CREATE") //
			@RequestParam(required = false) final SyncAdvise.IfNotExists ifProductsNotExist)
	{
		// The only kinds of debitors that xmlToOLCandsService is implemented to create are health insurances
		// Those insurances are supposed to live at Org=*, because we don't want them to be duplicated;
		// Likewise, we don't want normal Org>0 users to edit them.
		final SyncAdvise debitorSyncAdvise = SyncAdvise.builder()
				.ifExists(IfExists.UPDATE_REMOVE)
				.ifNotExists(IfNotExists.CREATE)
				.build();

		// wrt to the biller-bpartner's org, we use the same advise as with the biller itself
		final SyncAdvise billerSyncAdvise = SyncAdvise.builder()
				.ifExists(coalesce(ifBPartnersExist, IfExists.DONT_UPDATE))
				.ifNotExists(coalesce(ifBPartnersNotExist, IfNotExists.CREATE))
				.build();

		final SyncAdvise productSyncAdvise = SyncAdvise.builder()
				.ifExists(coalesce(ifProductsExist, IfExists.DONT_UPDATE))
				.ifNotExists(coalesce(ifProductsNotExist, IfNotExists.CREATE))
				.build();

		return importInvoiceXML(xmlInvoiceFile, HealthCareInvoiceDocSubType.EA, billerSyncAdvise, debitorSyncAdvise, productSyncAdvise);
	}

	private ResponseEntity<JsonAttachment> importInvoiceXML(
			@NonNull final MultipartFile xmlInvoiceFile,
			@NonNull final HealthCareInvoiceDocSubType targetDocType,
			@NonNull final SyncAdvise billerSyncAdvise,
			@NonNull final SyncAdvise debitorSyncAdvise,
			@NonNull final SyncAdvise productSyncAdvise)
	{

		final CreateOLCandsRequest createOLCandsRequest = CreateOLCandsRequest.builder()
				.xmlInvoiceFile(xmlInvoiceFile)
				.targetDocType(targetDocType)
				.billerSyncAdvise(billerSyncAdvise)
				.debitorSyncAdvise(debitorSyncAdvise)
				.productSyncAdvise(productSyncAdvise)
				.build();

		final JsonAttachment result = xmlToOLCandsService.createOLCands(createOLCandsRequest);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}
}
