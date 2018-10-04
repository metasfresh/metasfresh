package de.metas.ordercandidate.rest;

import java.io.IOException;
import java.util.List;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.service.OrgId;
import org.compiere.util.Env;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.ImmutableList;

import de.metas.ordercandidate.api.IOLCandBL;
import de.metas.ordercandidate.api.OLCand;
import de.metas.ordercandidate.api.OLCandCreateRequest;
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
	public JsonOLCand createOrder(@RequestBody final JsonOLCandCreateRequest request)
	{
		return createOrders(JsonOLCandCreateBulkRequest.of(request)).getSingleResult();
	}

	@PostMapping(PATH_BULK)
	@Override
	public JsonOLCandCreateBulkResponse createOrders(@RequestBody @NonNull final JsonOLCandCreateBulkRequest bulkRequest)
	{
		final MasterdataProvider masterdataProvider = MasterdataProvider.newInstance(Env.getCtx());

		createOrUpdateMasterdata(bulkRequest, masterdataProvider);

		final ITrxManager trxManager = Services.get(ITrxManager.class);
		return trxManager.call(() -> creatOrdersInTrx(bulkRequest, masterdataProvider));
	}

	private void assertCanCreate(final JsonOLCandCreateRequest request, final MasterdataProvider masterdataProvider)
	{
		final OrgId orgId = masterdataProvider.getCreateOrgId(request.getOrg());
		masterdataProvider.assertCanCreateNewOLCand(orgId);
	}

	private void createOrUpdateMasterdata(final JsonOLCandCreateBulkRequest bulkRequest, final MasterdataProvider masterdataProvider)
	{
		bulkRequest.getRequests()
				.stream()
				.forEach(request -> createOrUpdateMasterdata(request, masterdataProvider));
	}

	private void createOrUpdateMasterdata(final JsonOLCandCreateRequest json, final MasterdataProvider masterdataProvider)
	{
		final OrgId orgId = masterdataProvider.getCreateOrgId(json.getOrg());

		masterdataProvider.getCreateBPartnerInfo(json.getBpartner(), orgId);
		masterdataProvider.getCreateBPartnerInfo(json.getBillBPartner(), orgId);
		masterdataProvider.getCreateBPartnerInfo(json.getDropShipBPartner(), orgId);
		masterdataProvider.getCreateBPartnerInfo(json.getHandOverBPartner(), orgId);
	}

	private JsonOLCandCreateBulkResponse creatOrdersInTrx(final JsonOLCandCreateBulkRequest bulkRequest, final MasterdataProvider masterdataProvider)
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
			final JsonOLCandCreateRequest request,
			final MasterdataProvider masterdataProvider)
	{
		return jsonConverters.fromJson(request, masterdataProvider)
				.adInputDataSourceInternalName(DATA_SOURCE_INTERNAL_NAME)
				.build();
	}

	@PostMapping("/{externalId}/attachments")
	@Override
	public void attachFile(
			@PathVariable("externalId") final String olCandExternalId,
			@RequestParam("file") @NonNull final MultipartFile file)
			throws IOException
	{
		final IOLCandBL olCandsService = Services.get(IOLCandBL.class);

		final String filename = file.getOriginalFilename();
		final byte[] data = file.getBytes();
		olCandsService.addAttachment(olCandExternalId, filename, data);
	}
}
