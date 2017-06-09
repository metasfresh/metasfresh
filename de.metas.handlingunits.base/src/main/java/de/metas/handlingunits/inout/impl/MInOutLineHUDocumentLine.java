package de.metas.handlingunits.inout.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.inout.service.IMTransactionDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Transaction;

import de.metas.handlingunits.document.IHUAllocations;
import de.metas.handlingunits.document.impl.AbstractHUDocumentLine;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.storage.impl.MTransactionProductStorage;

/* package */class MInOutLineHUDocumentLine extends AbstractHUDocumentLine
{

	private final I_M_InOutLine ioLine;
	private String displayName;

	public MInOutLineHUDocumentLine(final org.compiere.model.I_M_InOutLine ioLine, final I_M_Transaction mtrx)
	{
		super(
				new MTransactionProductStorage(mtrx, ioLine.getC_UOM()) // storage
				, mtrx // referencedModel
		);

		Check.assumeNotNull(ioLine, "ioLine not null");
		Check.assume(ioLine.getM_InOutLine_ID() > 0, "ioLine({}) is saved", ioLine);
		this.ioLine = InterfaceWrapperHelper.create(ioLine, I_M_InOutLine.class);
	}

	@Override
	public String getDisplayName()
	{
		if (displayName == null)
		{
			displayName = new StringBuilder()
					.append(ioLine.getLine()).append(": ")
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
		return null;
	}

	@Override
	public I_M_Transaction getTrxReferencedModel()
	{
		return (I_M_Transaction)super.getTrxReferencedModel();
	}

	private MInOutLineHUDocumentLine reversalSourceLine = null;

	@Override
	public MInOutLineHUDocumentLine getReversal()
	{
		if (reversalSourceLine == null)
		{
			final org.compiere.model.I_M_InOutLine reversalLine = ioLine.getReversalLine();
			final I_M_Transaction mtrx = getTrxReferencedModel();
			final I_M_Transaction reversalMTrx = Services.get(IMTransactionDAO.class).retrieveReversalTransaction(reversalLine, mtrx);

			reversalSourceLine = new MInOutLineHUDocumentLine(reversalLine, reversalMTrx);
			reversalSourceLine.setReversal(this);
		}
		return reversalSourceLine;
	}

	private void setReversal(final MInOutLineHUDocumentLine reversalLine)
	{
		reversalSourceLine = reversalLine;
	}

	@Override
	public I_C_BPartner getC_BPartner()
	{
		final I_M_InOut inout = ioLine.getM_InOut();
		return inout.getC_BPartner();
	}

	@Override
	public int getC_BPartner_Location_ID()
	{
		final I_M_InOut inout = ioLine.getM_InOut();
		return inout.getC_BPartner_Location_ID();
	}
}
