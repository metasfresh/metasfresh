package de.metas.security.permissions.bpartner_hierarchy;

import java.util.stream.Stream;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.springframework.stereotype.Component;

import de.metas.bpartner.BPartnerId;

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
	public BPartnerDependentDocument extractOrderBPartnerDependentDocumentFromDocumentObj(Object documentObj)
	{
		final I_C_BPartner bpartnerRecord = InterfaceWrapperHelper.create(documentObj, I_C_BPartner.class);
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(bpartnerRecord.getC_BPartner_ID());

		return BPartnerDependentDocument.builder()
				.documentRef(TableRecordReference.of(documentObj))
				.newBPartnerId(bpartnerId)
				.oldBPartnerId(bpartnerId)
				.build();
	}

	@Override
	public BPartnerDependentDocument extractOrderBPartnerDependentDocumentFromDocumentRef(final TableRecordReference documentRef)
	{
		documentRef.assertTableName(I_C_BPartner.Table_Name);
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(documentRef.getRecord_ID());

		return BPartnerDependentDocument.builder()
				.documentRef(documentRef)
				.newBPartnerId(bpartnerId)
				.oldBPartnerId(bpartnerId)
				.build();
	}

	@Override
	public Stream<TableRecordReference> streamRelatedDocumentsByBPartnerId(final BPartnerId bpartnerId)
	{
		// TODO fetch child BPartners from parent `bpartnerId`
		return Stream.empty();
	}
}
