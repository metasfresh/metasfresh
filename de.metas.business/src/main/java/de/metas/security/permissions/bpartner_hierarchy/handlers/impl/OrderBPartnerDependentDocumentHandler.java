package de.metas.security.permissions.bpartner_hierarchy.handlers.impl;

import java.util.stream.Stream;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Component;

import de.metas.bpartner.BPartnerId;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
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
class OrderBPartnerDependentDocumentHandler implements BPartnerDependentDocumentHandler
{
	private final IOrderDAO ordersRepo = Services.get(IOrderDAO.class);

	@Override
	public String getDocumentTableName()
	{
		return I_C_Order.Table_Name;
	}

	@Override
	public BPartnerDependentDocument extractBPartnerDependentDocumentFromDocumentObj(final Object documentObj)
	{
		final I_C_Order orderRecord = InterfaceWrapperHelper.create(documentObj, I_C_Order.class);
		final I_C_Order orderRecordOld = InterfaceWrapperHelper.createOld(documentObj, I_C_Order.class);

		return BPartnerDependentDocument.builder()
				.documentRef(TableRecordReference.of(documentObj))
				.newBPartnerId(BPartnerId.ofRepoIdOrNull(orderRecord.getC_BPartner_ID()))
				.oldBPartnerId(BPartnerId.ofRepoIdOrNull(orderRecordOld.getC_BPartner_ID()))
				.build();
	}

	@Override
	public Stream<TableRecordReference> streamRelatedDocumentsByBPartnerId(final BPartnerId bpartnerId)
	{
		return ordersRepo.streamOrderIdsByBPartnerId(bpartnerId)
				.map(orderId -> toTableRecordReference(orderId));
	}

	private static final TableRecordReference toTableRecordReference(final OrderId orderId)
	{
		return TableRecordReference.of(I_C_Order.Table_Name, orderId);
	}
}
