package de.metas.ordercandidate.rest;

import java.io.IOException;
import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.service.OrgId;
import org.compiere.util.Util;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryCreateRequest;
import de.metas.ordercandidate.api.IOLCandBL;
import de.metas.ordercandidate.api.OLCand;
import de.metas.ordercandidate.api.OLCandCreateRequest;
import de.metas.ordercandidate.api.OLCandQuery;
import de.metas.ordercandidate.api.OLCandRepository;
import de.metas.util.Services;
import io.swagger.annotations.ApiParam;
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

	private JsonConverters jsonConverters;

	private OLCandRepository olCandRepo;

	private MasterdataProviderFactory masterdataProviderFactory;

	public OrderCandidatesRestControllerImpl(
			@NonNull final MasterdataProviderFactory masterdataProviderFactory,
			@NonNull final JsonConverters jsonConverters,
			@NonNull final OLCandRepository olCandRepo)
	{
		this.masterdataProviderFactory = masterdataProviderFactory;
		this.jsonConverters = jsonConverters;
		this.olCandRepo = olCandRepo;

	}

	@PostMapping
	@Override
	public ResponseEntity<JsonOLCand> createOrderLineCandidate(@RequestBody final JsonOLCandCreateRequest request)
	{
		final ResponseEntity<JsonOLCandCreateBulkResponse> //
		bulkResponse = createOrderLineCandidates(JsonOLCandCreateBulkRequest.of(request));

		final JsonOLCand result = bulkResponse.getBody().getSingleResult();
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}

	@PostMapping(PATH_BULK)
	@Override
	public ResponseEntity<JsonOLCandCreateBulkResponse> createOrderLineCandidates(@RequestBody @NonNull final JsonOLCandCreateBulkRequest bulkRequest)
	{
		bulkRequest.validate();

		final MasterdataProvider masterdataProvider = masterdataProviderFactory.createMasterDataProvider();
		final ITrxManager trxManager = Services.get(ITrxManager.class);

		// load/create/update the master data (according to SyncAdvice) in a dedicated trx.
		// because when creating the actual order line candidates, there is e.g. code invoked by model interceptors that gets AD_OrgInfo out of transaction.
		trxManager.run(() -> createOrUpdateMasterdata(bulkRequest, masterdataProvider));
		// the required masterdata should be there now, and cached within masterdataProvider for quick retrieval as the olcands are created.

		final JsonOLCandCreateBulkResponse //
		jsonOLCandCreateBulkResponse = trxManager.call(() ->

		// invoke creatOrderLineCandidates with the unchanged bulkRequest, because the request's bpartner and product instances are
		// (at least currently) part of the respective caching keys.
		creatOrderLineCandidates(bulkRequest, masterdataProvider));

		return new ResponseEntity<>(jsonOLCandCreateBulkResponse, HttpStatus.CREATED);
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

		final BPartnerMasterDataProvider bpartnerMasterdataProvider = masterdataProvider.getBPartnerMasterDataProvider();
		bpartnerMasterdataProvider.getCreateBPartnerInfo(json.getBpartner(), orgId);
		bpartnerMasterdataProvider.getCreateBPartnerInfo(json.getBillBPartner(), orgId);
		bpartnerMasterdataProvider.getCreateBPartnerInfo(json.getDropShipBPartner(), orgId);
		bpartnerMasterdataProvider.getCreateBPartnerInfo(json.getHandOverBPartner(), orgId);

		final ProductMasterDataProvider productMasterDataProvider = masterdataProvider.getProductMasterDataProvider();
		productMasterDataProvider.getCreateProductInfo(json.getProduct(), orgId);
	}

	private JsonOLCandCreateBulkResponse creatOrderLineCandidates(
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
				.dataSourceInternalName(dataSourceInternalNameToUse)
				.build();
	}

	@PostMapping("/{dataSourceName}/{externalReference}/attachments")
	@Override
	public ResponseEntity<JsonAttachment> attachFile(
			@PathVariable("dataSourceName") final String dataSourceName,

			@ApiParam(value = "External reference of the order line candidates to which the given file shall be attached", allowEmptyValue = false) //
			@PathVariable("externalReference") final String externalReference,

			@ApiParam(value = "List with an even number of items;\n"
					+ "transformed to a map of key-value pairs and added to the new attachment as tags.\n"
					+ "If the number of items is odd, the last item is discarded.", allowEmptyValue = true) //
			@RequestParam("tags") //
			@Nullable final List<String> tagKeyValuePairs,

			@ApiParam(value = "The file to attach; the attachment's MIME type will be determined from the file extenstion", allowEmptyValue = false) //
			@RequestBody @NonNull final MultipartFile file) throws IOException
	{
		final IOLCandBL olCandsService = Services.get(IOLCandBL.class);

		final OLCandQuery query = OLCandQuery
				.builder()
				.inputDataSourceName(dataSourceName)
				.externalHeaderId(externalReference)
				.build();

		final String fileName = file.getOriginalFilename();
		final byte[] data = file.getBytes();

		final AttachmentEntryCreateRequest request = AttachmentEntryCreateRequest
				.builderFromByteArray(fileName, data)
				.tags(extractTags(tagKeyValuePairs))
				.build();

		final AttachmentEntry attachmentEntry = olCandsService.addAttachment(query, request);

		final JsonAttachment jsonAttachment = toJsonAttachment(
				externalReference,
				dataSourceName,
				attachmentEntry);
		return new ResponseEntity<>(jsonAttachment, HttpStatus.CREATED);
	}

	@VisibleForTesting
	ImmutableMap<String, String> extractTags(@Nullable final List<String> tagKeyValuePairs)
	{
		if (tagKeyValuePairs == null)
		{
			return ImmutableMap.of();
		}
		final ImmutableMap.Builder<String, String> tags = ImmutableMap.builder();

		final int listSize = tagKeyValuePairs.size();
		final int maxIndex = listSize % 2 == 0 ? listSize : listSize - 1;

		for (int i = 0; i < maxIndex; i += 2)
		{
			tags.put(tagKeyValuePairs.get(i), tagKeyValuePairs.get(i + 1));
		}
		return tags.build();
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
				.mimeType(entry.getMimeType())
				.url(entry.getUrl() != null ? entry.getUrl().toString() : null)
				.build();
	}
}
