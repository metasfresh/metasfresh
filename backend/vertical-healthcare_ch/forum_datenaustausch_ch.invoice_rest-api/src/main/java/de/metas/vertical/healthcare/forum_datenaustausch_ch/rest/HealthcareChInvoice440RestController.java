package de.metas.vertical.healthcare.forum_datenaustausch_ch.rest;

import de.metas.common.ordercandidates.v1.response.JsonAttachment;
import de.metas.common.rest_api.v1.SyncAdvise;
import de.metas.common.rest_api.v1.SyncAdvise.IfExists;
import de.metas.common.rest_api.v1.SyncAdvise.IfNotExists;
import de.metas.util.web.MetasfreshRestAPIConstants;
import de.metas.vertical.healthcare.forum_datenaustausch_ch.rest.xml_to_olcands.XmlToOLCandsService;
import de.metas.vertical.healthcare.forum_datenaustausch_ch.rest.xml_to_olcands.XmlToOLCandsService.CreateOLCandsRequest;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.HealthCareInvoiceDocSubType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.NonNull;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static de.metas.common.util.CoalesceUtil.coalesce;

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
@RequestMapping(value = {
		MetasfreshRestAPIConstants.ENDPOINT_API_DEPRECATED + "/forum-datenaustausch.ch",
		MetasfreshRestAPIConstants.ENDPOINT_API_V1 + "/forum-datenaustausch.ch",
		MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/forum-datenaustausch.ch" })
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

			@ApiParam(defaultValue = "DONT_UPDATE", value = "This is applied only to the biller; the invoice recipient needs to already exist in metasfresh.") //
			@RequestParam(required = false) final SyncAdvise.IfExists ifBPartnersExist,

			@ApiParam(defaultValue = "CREATE", value = "This is applied only to the biller; the invoice recipient needs to already exist in metasfresh.") //
			@RequestParam(required = false) final SyncAdvise.IfNotExists ifBPartnersNotExist,

			@ApiParam(defaultValue = "DONT_UPDATE") //
			@RequestParam(required = false) final SyncAdvise.IfExists ifProductsExist,

			@ApiParam(defaultValue = "CREATE") //
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
	@ApiOperation(value = "Upload a forum-datenaustausch.ch state invoice-XML (\"Kanton\") into metasfresh.")
	public ResponseEntity<JsonAttachment> importDistrictInvoiceXML(

			@RequestParam("file") @NonNull final MultipartFile xmlInvoiceFile,

			@ApiParam(defaultValue = "DONT_UPDATE", value = "This is applied only to the biller; the invoice recipient needs to already exist in metasfresh.") //
			@RequestParam(required = false) final SyncAdvise.IfExists ifBPartnersExist,

			@ApiParam(defaultValue = "CREATE", value = "This is applied only to the biller; the invoice recipient needs to already exist in metasfresh.") //
			@RequestParam(required = false) final SyncAdvise.IfNotExists ifBPartnersNotExist,

			@ApiParam(defaultValue = "DONT_UPDATE") //
			@RequestParam(required = false) final SyncAdvise.IfExists ifProductsExist,

			@ApiParam(defaultValue = "CREATE") //
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

	@PostMapping(path = "importInvoiceXML/v440/GM")
	@ApiOperation(value = "Upload a forum-datenaustausch.ch municipality invoice-XML (\"Gemeinde\") into metasfresh.\n"
			+ "The municipality - which is the invoice recipient - is looked up by the bpartner's value (a.k.a. \"code\") = `GM-<municipality-zip-code>`\n"
			+ "If it does not yet exist, it is created with Organisation=* (any)")
	public ResponseEntity<JsonAttachment> importMunicipalityInvoiceXML(

			@RequestParam("file") @NonNull final MultipartFile xmlInvoiceFile,

			@ApiParam(defaultValue = "DONT_UPDATE", value = "This is applied only to the biller; the invoice recipient (municipality) is always created or updated on the fly.") //
			@RequestParam(required = false) final SyncAdvise.IfExists ifBPartnersExist,

			@ApiParam(defaultValue = "CREATE", value = "This is applied only to the biller; the invoice recipient (municipality) is always created or updated on the fly.") //
			@RequestParam(required = false) final SyncAdvise.IfNotExists ifBPartnersNotExist,

			@ApiParam(defaultValue = "DONT_UPDATE") //
			@RequestParam(required = false) final SyncAdvise.IfExists ifProductsExist,

			@ApiParam(defaultValue = "CREATE") //
			@RequestParam(required = false) final SyncAdvise.IfNotExists ifProductsNotExist)
	{
		final SyncAdvise debitorSyncAdvise = SyncAdvise.builder()
				.ifExists(IfExists.UPDATE_MERGE)
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

		return importInvoiceXML(xmlInvoiceFile, HealthCareInvoiceDocSubType.GM, billerSyncAdvise, debitorSyncAdvise, productSyncAdvise);
	}

	@PostMapping(path = "importInvoiceXML/v440/EA")
	@ApiOperation(value = "Upload a forum-datenaustausch.ch patient invoice-XML (\"Eigenanteil\") into metasfresh.\n"
			+ "The patient - which is the invoice recipient - is looked up by the bpartner's external-ID = `org:EAN-<biller-EAN>_bp:SSN-<patient-SSN>`\n"
			+ "If it does not yet exist, it is created within the biller's own organisation")
	public ResponseEntity<JsonAttachment> importPatientInvoiceXML(

			@RequestParam("file") @NonNull final MultipartFile xmlInvoiceFile,

			@ApiParam(defaultValue = "DONT_UPDATE", value = "This is applied only to the biller; the invoice recipient (patient) is always created or updated on the fly.") //
			@RequestParam(required = false) final SyncAdvise.IfExists ifBPartnersExist,

			@ApiParam(defaultValue = "CREATE", value = "This is applied only to the biller; the invoice recipient (patient) is always created or updated on the fly.") //
			@RequestParam(required = false) final SyncAdvise.IfNotExists ifBPartnersNotExist,

			@ApiParam(defaultValue = "DONT_UPDATE") //
			@RequestParam(required = false) final SyncAdvise.IfExists ifProductsExist,

			@ApiParam(defaultValue = "CREATE") //
			@RequestParam(required = false) final SyncAdvise.IfNotExists ifProductsNotExist)
	{
		// The only kinds of debitors that xmlToOLCandsService is implemented to create are health insurances
		// Those insurances are supposed to live at Org=*, because we don't want them to be duplicated;
		// Likewise, we don't want normal Org>0 users to edit them.
		final SyncAdvise debitorSyncAdvise = SyncAdvise.builder()
				.ifExists(IfExists.UPDATE_MERGE)
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
