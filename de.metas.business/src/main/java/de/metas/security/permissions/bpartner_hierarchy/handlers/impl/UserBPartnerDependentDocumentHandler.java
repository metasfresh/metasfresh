package de.metas.security.permissions.bpartner_hierarchy.handlers.impl;

import java.util.stream.Stream;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_User;
import org.springframework.stereotype.Component;

import de.metas.bpartner.BPartnerId;
import de.metas.security.permissions.bpartner_hierarchy.handlers.BPartnerDependentDocument;
import de.metas.security.permissions.bpartner_hierarchy.handlers.BPartnerDependentDocumentHandler;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
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
public class UserBPartnerDependentDocumentHandler implements BPartnerDependentDocumentHandler
{

	@Override
	public String getDocumentTableName()
	{
		return I_AD_User.Table_Name;
	}

	@Override
	public BPartnerDependentDocument extractBPartnerDependentDocumentFromDocumentObj(Object documentObj)
	{
		final I_AD_User userRecord = InterfaceWrapperHelper.create(documentObj, I_AD_User.class);
		final I_AD_User userRecordOld = InterfaceWrapperHelper.createOld(documentObj, I_AD_User.class);

		return BPartnerDependentDocument.builder()
				.documentRef(TableRecordReference.of(documentObj))
				.newBPartnerId(BPartnerId.ofRepoIdOrNull(userRecord.getC_BPartner_ID()))
				.oldBPartnerId(BPartnerId.ofRepoIdOrNull(userRecordOld.getC_BPartner_ID()))
				.build();
	}

	@Override
	public Stream<TableRecordReference> streamRelatedDocumentsByBPartnerId(final BPartnerId bpartnerId)
	{
		return Services.get(IUserDAO.class)
				.getUserIdsByBPartnerId(bpartnerId)
				.stream()
				.map(userId -> toTableRecordReference(userId));
	}

	private static final TableRecordReference toTableRecordReference(final UserId userId)
	{
		return TableRecordReference.of(I_AD_User.Table_Name, userId);
	}

}
