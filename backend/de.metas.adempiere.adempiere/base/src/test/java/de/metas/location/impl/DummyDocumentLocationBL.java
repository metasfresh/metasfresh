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

package de.metas.location.impl;

import de.metas.bpartner.service.IBPartnerBL;
import de.metas.document.location.impl.DocumentLocationBL;
import de.metas.document.location.adapter.IDocumentBillLocationAdapter;
import de.metas.document.location.adapter.IDocumentDeliveryLocationAdapter;
import de.metas.document.location.adapter.IDocumentHandOverLocationAdapter;
import de.metas.document.location.adapter.IDocumentLocationAdapter;
import lombok.NonNull;

/**
 * Dummy Document Location BL. This service is required for testing Dunning module, decoupled from database.
 */
public class DummyDocumentLocationBL extends DocumentLocationBL
{
	public DummyDocumentLocationBL(final @NonNull IBPartnerBL bpartnerBL)
	{
		super(bpartnerBL);
	}

	@Override
	public void updateRenderedAddressAndCapturedLocation(IDocumentLocationAdapter locationAdapter)
	{
		locationAdapter.setBPartnerAddress("Dummy BP Address: " + locationAdapter);
	}

	@Override
	public void updateRenderedAddressAndCapturedLocation(IDocumentBillLocationAdapter locationAdapter)
	{
		locationAdapter.setBillToAddress("Dummy BillTo Address: " + locationAdapter);
	}

	@Override
	public void updateRenderedAddressAndCapturedLocation(IDocumentDeliveryLocationAdapter locationAdapter)
	{
		locationAdapter.setDeliveryToAddress("Dummy DeliveryTo Address: " + locationAdapter);
	}

	@Override
	public void updateRenderedAddressAndCapturedLocation(IDocumentHandOverLocationAdapter locationAdapter)
	{
		locationAdapter.setHandOverAddress("Dummy Handover Address: " + locationAdapter);
	}

}
