package org.adempiere.mm.attributes.listeners.adr;

/*
 * #%L
 * de.metas.fresh.base
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

import java.util.Collections;
import java.util.List;

import org.adempiere.mm.attributes.api.IADRAttributeBL;
import org.adempiere.mm.attributes.api.IModelAttributeSetInstanceListener;
import org.adempiere.mm.attributes.api.impl.BPartnerAwareAttributeUpdater;
import org.adempiere.mm.attributes.api.impl.OrderLineBPartnerAware;
import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.edi.api.IEDIOLCandBL;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.model.I_C_Order_Line_Alloc;
import de.metas.util.Services;

public class OrderLineAllocADRModelAttributeSetInstanceListener implements IModelAttributeSetInstanceListener
{
	@Override
	public String getSourceTableName()
	{
		return I_C_Order_Line_Alloc.Table_Name;
	}

	@Override
	public List<String> getSourceColumnNames()
	{
		return Collections.emptyList();
	}

	/**
	 * Updates the ASI of the <code>C_Order_Line_Alloc</code>'s <code>C_OrderLine</code> with the BPartner's ADR attribute, <b>if</b> that order line is a purchase order line.
	 */
	@Override
	public void modelChanged(Object model)
	{
		final I_C_Order_Line_Alloc alloc = InterfaceWrapperHelper.create(model, I_C_Order_Line_Alloc.class);
		final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(alloc.getC_OrderLine(), I_C_OrderLine.class);

		// final boolean forceApply = isEDIInput(alloc); // task 08692: We needed to copy the attribute even if it's a SO transaction, if this OL_Cand is about EDI, however...
		final boolean forceApply = false; // task 08803 ...as of now, EDI-order lines shall have the same attribute values that manually created order lines have.

		new BPartnerAwareAttributeUpdater()
				.setBPartnerAwareFactory(OrderLineBPartnerAware.factory)
				.setBPartnerAwareAttributeService(Services.get(IADRAttributeBL.class))
				.setSourceModel(orderLine)
				.setForceApplyForSOTrx(forceApply)
				.updateASI();
	}

	@SuppressWarnings("unused")
	private final boolean isEDIInput(final I_C_Order_Line_Alloc alloc)
	{
		// Services
		final IEDIOLCandBL ediOLCandBL = Services.get(IEDIOLCandBL.class);

		final I_C_OLCand olCand = alloc.getC_OLCand();
		final boolean ediInput = ediOLCandBL.isEDIInput(olCand);
		return ediInput;
	}
}
