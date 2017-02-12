package de.metas.handlingunits.receiptschedule.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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


import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;

import de.metas.handlingunits.document.IHUAllocations;
import de.metas.handlingunits.document.impl.AbstractHUDocumentLine;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;

public class ReceiptScheduleHUDocumentLine extends AbstractHUDocumentLine
{
	//
	// Services
	private final IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);

	private String displayName;
	private final ReceiptScheduleHUAllocations huAllocations;

	public ReceiptScheduleHUDocumentLine(final I_M_ReceiptSchedule schedule)
	{
		super(
				Services.get(IHUReceiptScheduleBL.class).createProductStorage(schedule) // storage
				, schedule // reference model
		);

		huAllocations = new ReceiptScheduleHUAllocations(schedule, getStorage());
	}

	@Override
	public I_M_ReceiptSchedule getTrxReferencedModel()
	{
		return (I_M_ReceiptSchedule)super.getTrxReferencedModel();
	}

	private final I_M_ReceiptSchedule getM_ReceiptSchedule()
	{
		return getTrxReferencedModel();
	}

	@Override
	public String getDisplayName()
	{
		if (displayName == null)
		{
			displayName = new StringBuilder()
					.append(getM_Product().getName())
					.append(" x ")
					.append(getQty())
					.append(getC_UOM().getUOMSymbol())
					.toString();
		}
		return displayName;
	}

	@Override
	public IHUAllocations getHUAllocations()
	{
		return huAllocations;
	}

	@Override
	public I_C_BPartner getC_BPartner()
	{
		final I_M_ReceiptSchedule rs = getM_ReceiptSchedule();
		return receiptScheduleBL.getC_BPartner_Effective(rs);
	}

	@Override
	public int getC_BPartner_Location_ID()
	{
		final I_M_ReceiptSchedule rs = getM_ReceiptSchedule();
		return receiptScheduleBL.getC_BPartner_Location_Effective_ID(rs);
	}

	@Override
	public String toString()
	{
		return "ReceiptScheduleHUDocumentLine [displayName=" + displayName + ", huAllocations=" + huAllocations + "]";
	}


}
