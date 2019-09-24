package de.metas.security.permissions.bpartner_hierarchy.handlers.impl;

import java.util.stream.Stream;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_R_Request;
import org.springframework.stereotype.Component;

import de.metas.bpartner.BPartnerId;
import de.metas.request.RequestId;
import de.metas.request.api.IRequestDAO;
import de.metas.security.permissions.bpartner_hierarchy.handlers.BPartnerDependentDocument;
import de.metas.security.permissions.bpartner_hierarchy.handlers.BPartnerDependentDocumentHandler;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.business
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
public class RequestBPartnerDependentDocumentHandler implements BPartnerDependentDocumentHandler
{

	@Override
	public String getDocumentTableName()
	{
		return I_R_Request.Table_Name;
	}

	@Override
	public BPartnerDependentDocument extractBPartnerDependentDocumentFromDocumentObj(final Object documentObj)
	{
		final I_R_Request requestRecord = InterfaceWrapperHelper.create(documentObj, I_R_Request.class);
		final I_R_Request requestRecordOld = InterfaceWrapperHelper.createOld(documentObj, I_R_Request.class);

		return BPartnerDependentDocument.builder()
				.documentRef(TableRecordReference.of(documentObj))
				.newBPartnerId(BPartnerId.ofRepoIdOrNull(requestRecord.getC_BPartner_ID()))
				.oldBPartnerId(BPartnerId.ofRepoIdOrNull(requestRecordOld.getC_BPartner_ID()))
				.build();
	}

	@Override
	public Stream<TableRecordReference> streamRelatedDocumentsByBPartnerId(final BPartnerId bpartnerId)
	{
		final IRequestDAO requestsRepo = Services.get(IRequestDAO.class);
		return requestsRepo.streamRequestIdsByBPartnerId(bpartnerId)
				.map(requestId -> toTableRecordReference(requestId));
	}

	private static TableRecordReference toTableRecordReference(final RequestId requestId)
	{
		return TableRecordReference.of(I_R_Request.Table_Name, requestId);
	}
}
