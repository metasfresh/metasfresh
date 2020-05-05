package de.metas.tourplanning.exceptions;

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
import org.compiere.model.I_C_BPartner;

/**
 * Exception thrown when a business partner was expected to be a vendor too but it's not.
 * 
 * @author tsa
 *
 */
public class BPartnerNotVendorException extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5233575710830756282L;
	private static final String MSG_PartnerNotVendor = "PartnerNotVendor";

	public BPartnerNotVendorException(final I_C_BPartner bpartner)
	{
		super(buildMsg(bpartner));
	}

	private static final String buildMsg(final I_C_BPartner bpartner)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("@" + MSG_PartnerNotVendor + "@");

		if (bpartner != null)
		{
			sb.append("\n @C_BPartner_ID@: ").append(bpartner.getName());
		}

		return sb.toString();
	}
}
