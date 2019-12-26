package org.adempiere.mm.attributes.countryattribute.impl;

/*
 * #%L
 * de.metas.swat.base
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
import org.adempiere.mm.attributes.countryattribute.ICountryAware;
import org.adempiere.mm.attributes.countryattribute.ICountryAwareFactory;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;

import de.metas.util.Check;

public class InOutLineCountryAware implements ICountryAware
{
	public static final ICountryAwareFactory factory = new ICountryAwareFactory()
	{

		@Override
		public ICountryAware createCountryAware(final Object model)
		{
			final I_M_InOutLine inoutLine = InterfaceWrapperHelper.create(model, I_M_InOutLine.class);
			return new InOutLineCountryAware(inoutLine);
		}
	};

	private final I_M_InOutLine inoutLine;

	private InOutLineCountryAware(final I_M_InOutLine inoutLine)
	{
		super();
		Check.assumeNotNull(inoutLine, "inoutLine not null");
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
	public I_C_Country getC_Country()
	{
		final I_M_InOut inout = getM_InOut();
		final I_C_BPartner_Location bpLocation = InterfaceWrapperHelper.load(inout.getC_BPartner_Location_ID(), I_C_BPartner_Location.class);
		if (bpLocation == null)
		{
			return null;
		}
		return bpLocation.getC_Location().getC_Country();
	}

	private I_M_InOut getM_InOut()
	{
		final I_M_InOut inout = inoutLine.getM_InOut();
		if (inout == null)
		{
			throw new AdempiereException("M_InOut_ID was not set in " + inoutLine);
		}
		return inout;
	}
}
