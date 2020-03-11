package de.metas.security.permissions.bpartner_hierarchy;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.slf4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.logging.LogManager;
import de.metas.security.Principal;
import de.metas.security.permissions.Access;
import de.metas.security.permissions.bpartner_hierarchy.BPartnerDependentDocumentEvent.EventType;
import de.metas.security.permissions.bpartner_hierarchy.handlers.BPartnerDependentDocumentHandler;
import de.metas.security.permissions.bpartner_hierarchy.handlers.BPartnerDependentDocumentHandlersMap;
import de.metas.security.permissions.record_access.PermissionIssuer;
import de.metas.security.permissions.record_access.RecordAccess;
import de.metas.security.permissions.record_access.RecordAccessCopyRequest;
import de.metas.security.permissions.record_access.RecordAccessFeature;
import de.metas.security.permissions.record_access.RecordAccessGrantRequest;
import de.metas.security.permissions.record_access.RecordAccessRevokeRequest;
import de.metas.security.permissions.record_access.RecordAccessService;
import de.metas.security.permissions.record_access.handlers.RecordAccessHandler;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Component
public class BPartnerHierarchyRecordAccessHandler implements RecordAccessHandler
{
	private static final Logger logger = LogManager.getLogger(BPartnerHierarchyRecordAccessHandler.class);

	private final RecordAccessService service;
	private final BPartnerDependentDocumentHandlersMap dependentDocumentHandlers;

	public BPartnerHierarchyRecordAccessHandler(
			@NonNull @Lazy final RecordAccessService service,
			@NonNull final List<BPartnerDependentDocumentHandler> dependentDocumentHandlers)
	{
		this.service = service;

		this.dependentDocumentHandlers = BPartnerDependentDocumentHandlersMap.of(dependentDocumentHandlers);
		logger.info("{}", dependentDocumentHandlers);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("handlers", dependentDocumentHandlers)
				.toString();
	}

	boolean isEnabled()
	{
		return service.isFeatureEnabled(RecordAccessFeature.BPARTNER_HIERARCHY);
	}

	@Override
	public Set<RecordAccessFeature> getHandledFeatures()
	{
		return ImmutableSet.of(RecordAccessFeature.BPARTNER_HIERARCHY);
	}

	@Override
	public Set<String> getHandledTableNames()
	{
		return dependentDocumentHandlers.getTableNames();
	}

	public void onBPartnerSalesRepChanged(@NonNull final BPartnerSalesRepChangedEvent event)
	{
		if (!isEnabled())
		{
			return;
		}

		if (!event.isSalesRepChanged())
		{
			return;
		}

		if (event.getOldSalesRepId() != null)
		{
			service.revokeAccess(RecordAccessRevokeRequest.builder()
					.recordRef(TableRecordReference.of(I_C_BPartner.Table_Name, event.getBpartnerId()))
					.principal(Principal.userId(event.getOldSalesRepId()))
					.revokeAllPermissions(true)
					.issuer(PermissionIssuer.AUTO_BP_HIERARCHY)
					.requestedBy(event.getChangedBy())
					.build());
		}

		if (event.getNewSalesRepId() != null)
		{
			service.grantAccess(RecordAccessGrantRequest.builder()
					.recordRef(TableRecordReference.of(I_C_BPartner.Table_Name, event.getBpartnerId()))
					.principal(Principal.userId(event.getNewSalesRepId()))
					.permission(Access.READ)
					.permission(Access.WRITE)
					.issuer(PermissionIssuer.AUTO_BP_HIERARCHY)
					.requestedBy(event.getChangedBy())
					.build());
		}
	}

	public void onBPartnerDependentDocumentEvent(@NonNull final BPartnerDependentDocumentEvent event)
	{
		final TableRecordReference documentRef = event.getDocumentRef();

		final TableRecordReference grantFrom;
		final TableRecordReference revokeFrom;

		final EventType eventType = event.getEventType();
		final BPartnerId newBPartnerId = event.getNewBPartnerId();
		if (EventType.NEW_RECORD.equals(eventType))
		{
			// newBPartnerId can be null if documentRef is a C_BPArtner with BPartner_Parent_ID <= 0 (see BPartnerToBPartnerDependentDocumentHandler)
			grantFrom = newBPartnerId != null ? toTableRecordReference(newBPartnerId) : null;
			revokeFrom = null;
		}
		else if (EventType.BPARTNER_CHANGED.equals(eventType))
		{
			final BPartnerId oldBPartnerId = event.getOldBPartnerId();

			grantFrom = newBPartnerId != null ? toTableRecordReference(newBPartnerId) : null;
			revokeFrom = oldBPartnerId != null ? toTableRecordReference(oldBPartnerId) : null;
		}
		else
		{
			throw new AdempiereException("Unknown event type: " + eventType);
		}

		//
		if (Objects.equals(grantFrom, revokeFrom))
		{
			return;
		}

		service.copyAccess(RecordAccessCopyRequest.builder()
				.target(documentRef)
				.grantFrom(grantFrom)
				.revokeFrom(revokeFrom)
				.issuer(PermissionIssuer.AUTO_BP_HIERARCHY)
				.requestedBy(event.getUpdatedBy())
				.build());
	}

	@Override
	public void onAccessGranted(@NonNull final RecordAccess access)
	{
		if (access.getRecordRef().isOfType(I_C_BPartner.class))
		{
			final BPartnerId bpartnerId = extractBPartnerId(access);

			dependentDocumentHandlers
					.streamBPartnerRelatedRecords(bpartnerId)
					.map(recordRef -> RecordAccessGrantRequest.builder()
							.recordRef(recordRef)
							.principal(access.getPrincipal())
							.permission(access.getPermission())
							.issuer(PermissionIssuer.AUTO_BP_HIERARCHY)
							.requestedBy(access.getCreatedBy())
							.parentAccess(access)
							.build())
					.forEach(service::grantAccess);
		}
	}

	@Override
	public void onAccessRevoked(@NonNull final RecordAccess access)
	{
		if (access.getRecordRef().isOfType(I_C_BPartner.class))
		{
			final BPartnerId bpartnerId = extractBPartnerId(access);

			dependentDocumentHandlers
					.streamBPartnerRelatedRecords(bpartnerId)
					.map(recordRef -> RecordAccessRevokeRequest.builder()
							.recordRef(recordRef)
							.principal(access.getPrincipal())
							.permission(access.getPermission())
							.issuer(PermissionIssuer.AUTO_BP_HIERARCHY)
							.requestedBy(access.getCreatedBy())
							.build())
					.forEach(service::revokeAccess);
		}
	}

	private static final BPartnerId extractBPartnerId(final RecordAccess request)
	{
		final TableRecordReference recordRef = request.getRecordRef();
		recordRef.assertTableName(I_C_BPartner.Table_Name);
		return BPartnerId.ofRepoId(recordRef.getRecord_ID());
	}

	private static final TableRecordReference toTableRecordReference(@NonNull final BPartnerId bpartnerId)
	{
		return TableRecordReference.of(I_C_BPartner.Table_Name, bpartnerId);
	}
}
