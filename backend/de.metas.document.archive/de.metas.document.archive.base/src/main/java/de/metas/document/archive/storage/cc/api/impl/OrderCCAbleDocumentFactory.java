package de.metas.document.archive.storage.cc.api.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.document.archive.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.adempiere.model.I_C_Order;
import de.metas.document.archive.storage.cc.api.ICCAbleDocument;
import de.metas.document.archive.storage.cc.api.ICCAbleDocumentFactory;
import de.metas.util.Check;

public class OrderCCAbleDocumentFactory implements ICCAbleDocumentFactory
{
	@Override
	public ICCAbleDocument createCCAbleDocumentAdapter(@NonNull final Object model)
	{
		// Assume that the model is an instance of C_Order, minor block optimization
		Check.assume(InterfaceWrapperHelper.isInstanceOf(model, I_C_Order.class), "model instanceof I_C_Order");

		final I_C_Order order = InterfaceWrapperHelper.create(model, I_C_Order.class);
		final String documentTitle = order.getDocumentNo();

		final IBPartnerDAO partnerDAO = Services.get(IBPartnerDAO.class);

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(order.getBill_BPartner_ID());
		final String recipientName = partnerDAO.getBPartnerNameById(bpartnerId);

		final BPartnerLocationId billLocationId = BPartnerLocationId.ofRepoId(bpartnerId, order.getBill_Location_ID());
		final String fax = partnerDAO.getBPartnerLocationByIdEvenInactive(billLocationId).getFax();

		final ICCAbleDocument ccAdapter = new OrderCCAbleDocumentAdapter(documentTitle, recipientName, fax);
		return ccAdapter;
	}
}
