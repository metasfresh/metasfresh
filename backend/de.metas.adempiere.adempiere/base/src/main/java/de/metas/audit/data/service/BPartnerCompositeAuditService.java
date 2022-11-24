/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.audit.data.service;

import de.metas.JsonMapperUtil;
import de.metas.audit.data.Action;
import de.metas.audit.data.ExternalSystemParentConfigId;
import de.metas.audit.data.IMasterDataExportAuditService;
import de.metas.audit.data.model.DataExportAuditId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.bpartner.v2.response.JsonResponseBPartner;
import de.metas.common.bpartner.v2.response.JsonResponseComposite;
import de.metas.common.bpartner.v2.response.JsonResponseContact;
import de.metas.common.bpartner.v2.response.JsonResponseLocation;
import de.metas.location.LocationId;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Nullable;
import java.util.Optional;

@Service
public class BPartnerCompositeAuditService implements IMasterDataExportAuditService
{
	//dev-note: meant to capture any rest calls made against `api/v2/bpartner`
	private final static String BPARTNER_RESOURCE = "/api/v2/bpartner/**";

	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

	private final DataExportAuditService dataExportAuditService;

	public BPartnerCompositeAuditService(@NonNull final DataExportAuditService dataExportAuditService)
	{
		this.dataExportAuditService = dataExportAuditService;
	}

	@Override
	public void performDataAuditForRequest(@NonNull final GenericDataExportAuditRequest genericDataExportAuditRequest)
	{
		if (!isHandled(genericDataExportAuditRequest))
		{
			return;
		}

		final ExternalSystemParentConfigId externalSystemParentConfigId = genericDataExportAuditRequest.getExternalSystemParentConfigId();
		final PInstanceId pInstanceId = genericDataExportAuditRequest.getPInstanceId();

		final Object exportedObject = genericDataExportAuditRequest.getExportedObject();
		final Optional<JsonResponseComposite> bpartnerComposite = JsonMapperUtil.tryDeserializeToType(exportedObject, JsonResponseComposite.class);
		if (bpartnerComposite.isPresent())
		{
			auditBPartnerComposite(bpartnerComposite.get(), externalSystemParentConfigId, pInstanceId);
			return;
		}

		final Optional<JsonResponseLocation> bpartnerLocationComposite = JsonMapperUtil.tryDeserializeToType(exportedObject, JsonResponseLocation.class);
		if (bpartnerLocationComposite.isPresent())
		{
			auditBPartnerLocation(bpartnerLocationComposite.get(), null/*dataExportParentId*/, externalSystemParentConfigId, pInstanceId);
			return;
		}

		final Optional<JsonResponseContact> adUserComposite = JsonMapperUtil.tryDeserializeToType(exportedObject, JsonResponseContact.class);
		adUserComposite.ifPresent(jsonResponseContact -> auditAdUser(jsonResponseContact, null/*dataExportParentId*/, externalSystemParentConfigId, pInstanceId));
	}

	@Override
	public boolean isHandled(@NonNull final GenericDataExportAuditRequest genericDataExportAuditRequest)
	{
		final AntPathMatcher antPathMatcher = new AntPathMatcher();

		return antPathMatcher.match(BPARTNER_RESOURCE, genericDataExportAuditRequest.getRequestURI());
	}

	private void auditBPartnerComposite(
			@NonNull final JsonResponseComposite jsonResponseComposite,
			@Nullable final ExternalSystemParentConfigId externalSystemParentConfigId,
			@Nullable final PInstanceId pInstanceId)
	{
		final JsonResponseBPartner jsonResponseBPartner = jsonResponseComposite.getBpartner();

		final BPartnerId bPartnerId = BPartnerId.ofRepoId(jsonResponseBPartner.getMetasfreshId().getValue());

		final DataExportAuditId bPartnerDataExportAuditId = auditBPartner(bPartnerId, externalSystemParentConfigId, pInstanceId);

		jsonResponseComposite
				.getLocations()
				.forEach(jsonResponseLocation -> auditBPartnerLocation(jsonResponseLocation, bPartnerDataExportAuditId, externalSystemParentConfigId, pInstanceId));

		jsonResponseComposite
				.getContacts()
				.forEach(jsonResponseContact -> auditAdUser(jsonResponseContact, bPartnerDataExportAuditId, externalSystemParentConfigId, pInstanceId));
	}

	@NonNull
	private DataExportAuditId auditBPartner(
			@NonNull final BPartnerId bPartnerId,
			@Nullable final ExternalSystemParentConfigId externalSystemParentConfigId,
			@Nullable final PInstanceId pInstanceId)
	{
		final DataExportAuditRequest bPartnerDataExportAuditRequest = DataExportAuditRequest.builder()
				.tableRecordReference(TableRecordReference.of(I_C_BPartner.Table_Name, bPartnerId.getRepoId()))
				.action(Action.Standalone)
				.externalSystemConfigId(externalSystemParentConfigId)
				.adPInstanceId(pInstanceId)
				.build();

		return dataExportAuditService.createExportAudit(bPartnerDataExportAuditRequest);
	}

	private void auditBPartnerLocation(
			@NonNull final JsonResponseLocation jsonResponseLocation,
			@Nullable final DataExportAuditId dataExportParentId,
			@Nullable final ExternalSystemParentConfigId externalSystemParentConfigId,
			@Nullable final PInstanceId pInstanceId)
	{
		final Action exportAction = dataExportParentId != null ? Action.AlongWithParent : Action.Standalone;

		final int bPartnerLocId = jsonResponseLocation.getMetasfreshId().getValue();

		final I_C_BPartner_Location bpartnerLocationRecord = InterfaceWrapperHelper.load(bPartnerLocId, I_C_BPartner_Location.class);

		final BPartnerLocationId bPartnerLocationId = BPartnerLocationId.ofRepoId(bpartnerLocationRecord.getC_BPartner_ID(),
																				  bpartnerLocationRecord.getC_BPartner_Location_ID());

		final DataExportAuditRequest bpLocationRequest = DataExportAuditRequest.builder()
				.tableRecordReference(TableRecordReference.of(I_C_BPartner_Location.Table_Name, bPartnerLocationId.getRepoId()))
				.action(exportAction)
				.externalSystemConfigId(externalSystemParentConfigId)
				.adPInstanceId(pInstanceId)
				.parentExportAuditId(dataExportParentId)
				.build();

		final DataExportAuditId bpartnerLocationExportAuditId = dataExportAuditService.createExportAudit(bpLocationRequest);

		final LocationId locationId = bpartnerDAO.getLocationId(bPartnerLocationId);

		final DataExportAuditRequest locationRequest = DataExportAuditRequest.builder()
				.tableRecordReference(TableRecordReference.of(I_C_Location.Table_Name, locationId.getRepoId()))
				.action(Action.AlongWithParent)
				.externalSystemConfigId(externalSystemParentConfigId)
				.adPInstanceId(pInstanceId)
				.parentExportAuditId(bpartnerLocationExportAuditId)
				.build();

		dataExportAuditService.createExportAudit(locationRequest);
	}

	private void auditAdUser(
			@NonNull final JsonResponseContact jsonResponseContact,
			@Nullable final DataExportAuditId dataExportParentId,
			@Nullable final ExternalSystemParentConfigId externalSystemParentConfigId,
			@Nullable final PInstanceId pInstanceId)
	{
		final Action exportAction = dataExportParentId != null ? Action.AlongWithParent : Action.Standalone;

		final DataExportAuditRequest adUserRequest = DataExportAuditRequest.builder()
				.tableRecordReference(TableRecordReference.of(I_AD_User.Table_Name, jsonResponseContact.getMetasfreshId().getValue()))
				.action(exportAction)
				.externalSystemConfigId(externalSystemParentConfigId)
				.adPInstanceId(pInstanceId)
				.parentExportAuditId(dataExportParentId)
				.build();

		dataExportAuditService.createExportAudit(adUserRequest);
	}
}
