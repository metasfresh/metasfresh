package de.metas.ordercandidate.rest;

import java.io.IOException;
import java.util.List;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.service.OrgId;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.ImmutableList;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryCreateRequest;
import de.metas.ordercandidate.api.IOLCandBL;
import de.metas.ordercandidate.api.OLCand;
import de.metas.ordercandidate.api.OLCandCreateRequest;
import de.metas.ordercandidate.api.OLCandQuery;
import de.metas.ordercandidate.api.OLCandRepository;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.ordercandidate.rest-api
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

@Service
@RestController
@RequestMapping(OrderCandidatesRestEndpoint.ENDPOINT)
public class OrderCandidatesRestControllerImpl implements OrderCandidatesRestEndpoint
{
	public static final String DATA_SOURCE_INTERNAL_NAME = "SOURCE." + OrderCandidatesRestControllerImpl.class.getName();

	@Autowired
	private JsonConverters jsonConverters;
	@Autowired
	private OLCandRepository olCandRepo;

	@PostMapping
	@Override
	public JsonOLCand createOrderLineCandidate(@RequestBody final JsonOLCandCreateRequest request)
	{
		return createOrderLineCandidates(JsonOLCandCreateBulkRequest.of(request)).getSingleResult();
	}

	@PostMapping(PATH_BULK)
	@Override
	public JsonOLCandCreateBulkResponse createOrderLineCandidates(@RequestBody @NonNull final JsonOLCandCreateBulkRequest bulkRequest)
	{
		bulkRequest.validate();

		final MasterdataProvider masterdataProvider = MasterdataProvider.newInstance(Env.getCtx());

		createOrUpdateMasterdata(bulkRequest, masterdataProvider);

		final ITrxManager trxManager = Services.get(ITrxManager.class);
		return trxManager.call(() -> creatOrdersInTrx(bulkRequest, masterdataProvider));
	}

	private void assertCanCreate(
			@NonNull final JsonOLCandCreateRequest request,
			@NonNull final MasterdataProvider masterdataProvider)
	{
		final OrgId orgId = masterdataProvider.getCreateOrgId(request.getOrg());
		masterdataProvider.assertCanCreateNewOLCand(orgId);
	}

	private void createOrUpdateMasterdata(
			@NonNull final JsonOLCandCreateBulkRequest bulkRequest,
			@NonNull final MasterdataProvider masterdataProvider)
	{
		bulkRequest.getRequests()
				.stream()
				.forEach(request -> createOrUpdateMasterdata(request, masterdataProvider));
	}

	private void createOrUpdateMasterdata(
			@NonNull final JsonOLCandCreateRequest json,
			@NonNull final MasterdataProvider masterdataProvider)
	{
		final OrgId orgId = masterdataProvider.getCreateOrgId(json.getOrg());

		masterdataProvider.getCreateBPartnerInfo(json.getBpartner(), orgId);
		masterdataProvider.getCreateBPartnerInfo(json.getBillBPartner(), orgId);
		masterdataProvider.getCreateBPartnerInfo(json.getDropShipBPartner(), orgId);
		masterdataProvider.getCreateBPartnerInfo(json.getHandOverBPartner(), orgId);
	}

	private JsonOLCandCreateBulkResponse creatOrdersInTrx(
			@NonNull final JsonOLCandCreateBulkRequest bulkRequest,
			@NonNull final MasterdataProvider masterdataProvider)
	{
		final List<OLCandCreateRequest> requests = bulkRequest
				.getRequests()
				.stream()
				.peek(request -> assertCanCreate(request, masterdataProvider))
				.map(request -> fromJson(request, masterdataProvider))
				.collect(ImmutableList.toImmutableList());

		final List<OLCand> olCands = olCandRepo.create(requests);
		return jsonConverters.toJson(olCands, masterdataProvider);
	}

	private OLCandCreateRequest fromJson(
			@NonNull final JsonOLCandCreateRequest request,
			@NonNull final MasterdataProvider masterdataProvider)
	{
		final String dataSourceInternalNameToUse = Util.coalesce(
				request.getDataSourceInternalName(),
				DATA_SOURCE_INTERNAL_NAME);

		return jsonConverters
				.fromJson(request, masterdataProvider)
				.adInputDataSourceInternalName(dataSourceInternalNameToUse)
				.build();
	}

	@PostMapping("/{dataSourceName}/{externalReference}/attachments")
	@Override
	public JsonAttachment attachFile(
			@PathVariable("dataSourceName") final String dataSourceName,
			@PathVariable("externalReference") final String externalReference,
			@RequestParam("file") @NonNull final MultipartFile file)
			throws IOException
	{
		final IOLCandBL olCandsService = Services.get(IOLCandBL.class);

		final OLCandQuery query = OLCandQuery
				.builder()
				.inputDataSourceName(dataSourceName)
				.externalReference(externalReference)
				.build();

		final String fileName = file.getOriginalFilename();
		final byte[] data = file.getBytes();
		final AttachmentEntryCreateRequest request = AttachmentEntryCreateRequest.fromByteArray(fileName, data);

		final AttachmentEntry attachmentEntry = olCandsService.addAttachment(query, request);

		return toJsonAttachment(
				externalReference,
				dataSourceName,
				attachmentEntry);
	}

	private JsonAttachment toJsonAttachment(
			@NonNull final String externalReference,
			@NonNull final String dataSourceName,
			@NonNull final AttachmentEntry entry)
	{
		final String attachmentId = Integer.toString(entry.getId().getRepoId());

		return JsonAttachment.builder()
				.externalReference(externalReference)
				.dataSourceName(dataSourceName)
				.attachmentId(attachmentId)
				.type(entry.getType())
				.filename(entry.getFilename())
				.contentType(entry.getContentType())
				.url(entry.getUrl() != null ? entry.getUrl().toString() : null)
				.build();
	}
}
