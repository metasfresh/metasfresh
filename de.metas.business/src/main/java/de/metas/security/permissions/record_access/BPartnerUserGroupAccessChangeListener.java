package de.metas.security.permissions.record_access;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.bpartner.BPartnerId;
import de.metas.logging.LogManager;
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
	private final ImmutableMap<String, BPartnerDependentDocumentHandler> dependentDocumentHandlersByTableName;
	private UserGroupRecordAccessService service;

	public BPartnerUserGroupAccessChangeListener(
			@NonNull final List<BPartnerDependentDocumentHandler> dependentDocumentHandlers)
	{
		dependentDocumentHandlersByTableName = Maps.uniqueIndex(dependentDocumentHandlers, BPartnerDependentDocumentHandler::getDocumentTableName);
		logger.info("BPartnerDependentDocumentHandlers: {}", dependentDocumentHandlersByTableName);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("handlers", dependentDocumentHandlersByTableName)
				.toString();
	}

	@Override
	public void setUserGroupRecordAccessService(UserGroupRecordAccessService service)
	{
		this.service = service;
	}

	public Set<String> getDependentDocumentTableNames()
	{
		return dependentDocumentHandlersByTableName.keySet();
	}

	public void onBPartnerDependentDocumentCreated(final TableRecordReference documentRef)
	{
		final BPartnerId bpartnerId = extractBPartnerIdFromDependentDocument(documentRef);
		if (bpartnerId == null)
		{
			return;
		}

		service.copyAccess(documentRef, toTableRecordReference(bpartnerId));
	}

	@Override
	public void onAccessGranted(@NonNull final UserGroupRecordAccess request)
	{
		if (request.getRecordRef().isOfType(I_C_BPartner.class))
		{
			final BPartnerId bpartnerId = extractBPartnerId(request);
			streamBPartnerRelatedRecords(bpartnerId)
					.map(request::withRecordRef)
					.forEach(service::grantAccess);
		}
	}

	@Override
	public void onAccessRevoked(@NonNull final UserGroupRecordAccess request)
	{
		if (request.getRecordRef().isOfType(I_C_BPartner.class))
		{
			final BPartnerId bpartnerId = extractBPartnerId(request);
			streamBPartnerRelatedRecords(bpartnerId)
					.map(request::withRecordRef)
					.forEach(service::revokeAccess);
		}
	}

	private Stream<TableRecordReference> streamBPartnerRelatedRecords(@NonNull final BPartnerId bpartnerId)
	{
		return dependentDocumentHandlersByTableName.values()
				.stream()
				.flatMap(handler -> handler.streamRelatedDocumentsByBPartnerId(bpartnerId));
	}

	private static final BPartnerId extractBPartnerId(final UserGroupRecordAccess request)
	{
		final TableRecordReference recordRef = request.getRecordRef();
		recordRef.assertTableName(I_C_BPartner.Table_Name);
		return BPartnerId.ofRepoId(recordRef.getRecord_ID());
	}

	private BPartnerId extractBPartnerIdFromDependentDocument(final TableRecordReference documentRef)
	{
		final String documentTableName = documentRef.getTableName();
		final BPartnerDependentDocumentHandler handler = dependentDocumentHandlersByTableName.get(documentTableName);
		if (handler == null)
		{
			return null;
		}

		return handler.extractBPartnerIdFromDependentDocument(documentRef).orElse(null);
	}

	private static final TableRecordReference toTableRecordReference(final BPartnerId bpartnerId)
	{
		return TableRecordReference.of(I_C_BPartner.Table_Name, bpartnerId.getRepoId());
	}
}
