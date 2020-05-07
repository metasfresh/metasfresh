package org.adempiere.mm.attributes.api.impl;

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


import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.IBPartnerAware;
import org.adempiere.mm.attributes.api.IBPartnerAwareFactory;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;

import de.metas.fresh.model.I_C_BPartner;
import lombok.NonNull;

public class InOutLineBPartnerAware implements IBPartnerAware
{
	public static final IBPartnerAwareFactory factory = new IBPartnerAwareFactory()
	{
		@Override
		public IBPartnerAware createBPartnerAware(Object model)
		{
			final I_M_InOutLine inoutLine = InterfaceWrapperHelper.create(model, I_M_InOutLine.class);
			final IBPartnerAware partnerAware = new InOutLineBPartnerAware(inoutLine);
			return partnerAware;
		}
	};

	private final I_M_InOutLine inoutLine;

	private InOutLineBPartnerAware(@NonNull final I_M_InOutLine inoutLine)
	{
		this.inoutLine = inoutLine;
	}

	@Override
	public int getAD_Client_ID()
	{
		return inoutLine.getAD_Client_ID();
	}

	@Override
	public int getAD_Org_ID()
	{
		return inoutLine.getAD_Org_ID();
	}

	@Override
	public boolean isSOTrx()
	{
		final I_M_InOut inout = getM_InOut();
		return inout.isSOTrx();
	}

	@Override
	public I_C_BPartner getC_BPartner()
	{
		final I_M_InOut inout = getM_InOut();
		final I_C_BPartner partner = InterfaceWrapperHelper.create(inout.getC_BPartner(), I_C_BPartner.class);
		if (partner == null)
		{
			return null;
		}
		return partner;
	}

	private final I_M_InOut getM_InOut()
	{
		final I_M_InOut inout = inoutLine.getM_InOut();
		if (inout == null)
		{
			throw new AdempiereException("M_InOut_ID was not set in " + inoutLine);
		}
		return inout;
	}

	@Override
	public String toString()
	{
		return String.format("InOutLineBPartnerAware [inoutLine=%s, isSOTrx()=%s, getC_BPartner()=%s, getM_InOut()=%s]", inoutLine, isSOTrx(), getC_BPartner(), getM_InOut());
	}
}
