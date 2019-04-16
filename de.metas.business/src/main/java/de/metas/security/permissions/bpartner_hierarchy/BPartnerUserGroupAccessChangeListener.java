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

import de.metas.bpartner.BPartnerId;
import de.metas.logging.LogManager;
import de.metas.security.permissions.bpartner_hierarchy.BPartnerDependentDocumentEvent.EventType;
import de.metas.security.permissions.record_access.UserGroupRecordAccess;
import de.metas.security.permissions.record_access.UserGroupRecordAccessGrantRequest;
import de.metas.security.permissions.record_access.UserGroupRecordAccessRevokeRequest;
import de.metas.security.permissions.record_access.UserGroupRecordAccessService;
import de.metas.security.permissions.record_access.listeners.UserGroupAccessChangeListener;
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
class BPartnerUserGroupAccessChangeListener implements UserGroupAccessChangeListener
{
	private static final Logger logger = LogManager.getLogger(BPartnerUserGroupAccessChangeListener.class);
	private final UserGroupRecordAccessService service;
	private final BPartnerDependentDocumentHandlersMap dependentDocumentHandlers;

	public BPartnerUserGroupAccessChangeListener(
			@NonNull @Lazy final UserGroupRecordAccessService service,
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

	public Set<String> getDependentDocumentTableNames()
	{
		return dependentDocumentHandlers.getTableNames();
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
			grantFrom = toTableRecordReference(newBPartnerId);
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

		service.copyAccess(documentRef, grantFrom, revokeFrom);
	}

	@Override
	public void onAccessGranted(@NonNull final UserGroupRecordAccess access)
	{
		if (access.getRecordRef().isOfType(I_C_BPartner.class))
		{
			final BPartnerId bpartnerId = extractBPartnerId(access);

			dependentDocumentHandlers
					.streamBPartnerRelatedRecords(bpartnerId)
					.map(recordRef -> UserGroupRecordAccessGrantRequest.builder()
							.recordRef(recordRef)
							.principal(access.getPrincipal())
							.permission(access.getPermission())
							.build())
					.forEach(service::grantAccess);
		}
	}

	@Override
	public void onAccessRevoked(@NonNull final UserGroupRecordAccess access)
	{
		if (access.getRecordRef().isOfType(I_C_BPartner.class))
		{
			final BPartnerId bpartnerId = extractBPartnerId(access);

			dependentDocumentHandlers
					.streamBPartnerRelatedRecords(bpartnerId)
					.map(recordRef -> UserGroupRecordAccessRevokeRequest.builder()
							.recordRef(recordRef)
							.principal(access.getPrincipal())
							.permission(access.getPermission())
							.build())
					.forEach(service::revokeAccess);
		}
	}

	private static final BPartnerId extractBPartnerId(final UserGroupRecordAccess request)
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
