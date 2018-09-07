package de.metas.ordercandidate.rest;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.OrgId;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Org;
import org.compiere.util.Env;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.ordercandidate.api.IOLCandBL;
import de.metas.ordercandidate.api.OLCand;
import de.metas.ordercandidate.api.OLCandCreateRequest;
import de.metas.ordercandidate.api.OLCandRepository;
import de.metas.ordercandidate.model.I_C_OLCand;
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
		final Set<OrgId> orgIds = getCreateOrganizations(bulkRequest);
		assertCanCreateNewOLCands(orgIds);

		final ITrxManager trxManager = Services.get(ITrxManager.class);
		return trxManager.call(() -> creatOrdersInTrx(bulkRequest));
	}

	private Set<OrgId> getCreateOrganizations(final JsonOLCandCreateBulkRequest bulkRequest)
	{
		assertCanCreateNewOrganizations();

		final MasterdataProvider masterdataProvider = MasterdataProvider.newInstance();

		final Set<OrgId> orgIds = bulkRequest.getRequests()
				.stream()
				.map(request -> getCreateOrganization(request, masterdataProvider))
				.filter(Predicates.notNull())
				.collect(ImmutableSet.toImmutableSet());

		return orgIds;
	}

	private OrgId getCreateOrganization(final JsonOLCandCreateRequest request, final MasterdataProvider masterdataProvider)
	{
		final JsonOrganization org = request.getOrg();
		if (org == null)
		{
			return null;
		}

		return masterdataProvider.getCreateOrgId(org);
	}

	private JsonOLCandCreateBulkResponse creatOrdersInTrx(final JsonOLCandCreateBulkRequest bulkRequest)
	{
		final MasterdataProvider masterdataProvider = MasterdataProvider.newInstance();

		final List<OLCandCreateRequest> requests = bulkRequest
				.getRequests()
				.stream()
				.map(request -> fromJson(request, masterdataProvider))
				.collect(ImmutableList.toImmutableList());

		final List<OLCand> olCands = olCandRepo.create(requests);
		return jsonConverters.toJson(olCands, masterdataProvider);
	}

	private void assertCanCreateNewOrganizations()
	{
		final IUserRolePermissions userPermissions = Env.getUserRolePermissions();
		final Properties ctx = Env.getCtx();
		final int adClientId = Env.getAD_Client_ID(ctx);
		final int adTableId = InterfaceWrapperHelper.getTableId(I_AD_Org.class);
		final String errmsg = userPermissions.checkCanCreateNewRecord(adClientId, OrgId.ANY.getRepoId(), adTableId);
		if (errmsg != null)
		{
			throw new AdempiereException(errmsg);
		}
	}

	private void assertCanCreateNewOLCands(final Set<OrgId> orgIds)
	{
		final IUserRolePermissions userPermissions = Env.getUserRolePermissions();
		final Properties ctx = Env.getCtx();
		final int contextClientId = Env.getAD_Client_ID(ctx);
		final int adTableId = InterfaceWrapperHelper.getTableId(I_C_OLCand.class);

		final OrgId contextOrgId = OrgId.ofRepoIdOrNull(Env.getAD_Org_ID(ctx));
		final Set<OrgId> orgIdsEffective;
		if (contextOrgId != null && !orgIds.contains(contextOrgId))
		{
			orgIdsEffective = ImmutableSet.<OrgId> builder()
					.addAll(orgIds)
					.add(contextOrgId)
					.build();
		}
		else
		{
			orgIdsEffective = orgIds;
		}

		for (final OrgId orgId : orgIdsEffective)
		{
			final String errmsg = userPermissions.checkCanCreateNewRecord(contextClientId, orgId.getRepoId(), adTableId);
			if (errmsg != null)
			{
				throw new AdempiereException(errmsg);
			}
		}
	}

	private OLCandCreateRequest fromJson(
			final JsonOLCandCreateRequest request,
			final MasterdataProvider masterdataProvider)
	{
		return jsonConverters.fromJson(request, masterdataProvider)
				.adInputDataSourceInternalName(DATA_SOURCE_INTERNAL_NAME)
				.build();
	}

	@PostMapping("/{id}/attachments")
	@Override
	public void attachFile(
			@PathVariable("id") final String olCandIdStr,
			@RequestParam("file") @NonNull final MultipartFile file)
			throws IOException
	{
		final int olCandId = Integer.parseInt(olCandIdStr);
		final String filename = file.getOriginalFilename();
		final byte[] data = file.getBytes();

		Services.get(IOLCandBL.class).addAttachment(olCandId, filename, data);
	}
}
