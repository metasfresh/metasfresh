package de.metas.security.permissions.bpartner_hierarchy.handlers.impl;

import java.util.stream.Stream;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.springframework.stereotype.Component;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.security.permissions.bpartner_hierarchy.handlers.BPartnerDependentDocument;
import de.metas.security.permissions.bpartner_hierarchy.handlers.BPartnerDependentDocumentHandler;
import de.metas.util.Services;
import lombok.NonNull;

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
class BPartnerToBPartnerDependentDocumentHandler implements BPartnerDependentDocumentHandler
{

	@Override
	public String getDocumentTableName()
	{
		return I_C_BPartner.Table_Name;
	}

	@Override
	public BPartnerDependentDocument extractBPartnerDependentDocumentFromDocumentObj(Object documentObj)
	{
		final I_C_BPartner bpartnerRecord = InterfaceWrapperHelper.create(documentObj, I_C_BPartner.class);
		final I_C_BPartner bpartnerRecordOld = InterfaceWrapperHelper.createOld(documentObj, I_C_BPartner.class);

		return BPartnerDependentDocument.builder()
				.documentRef(TableRecordReference.of(documentObj))
				.newBPartnerId(BPartnerId.ofRepoIdOrNull(bpartnerRecord.getBPartner_Parent_ID()))
				.oldBPartnerId(BPartnerId.ofRepoIdOrNull(bpartnerRecordOld.getBPartner_Parent_ID()))
				.build();
	}

	@Override
	public Stream<TableRecordReference> streamRelatedDocumentsByBPartnerId(@NonNull final BPartnerId bpartnerId)
	{
		return Services.get(IBPartnerDAO.class)
				.streamChildBPartnerIds(bpartnerId)
				.map(childBPartnerId -> TableRecordReference.of(I_C_BPartner.Table_Name, childBPartnerId));
	}
}
