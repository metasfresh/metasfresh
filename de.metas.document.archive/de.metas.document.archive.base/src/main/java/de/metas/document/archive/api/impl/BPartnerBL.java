/**
 * 
 */
package de.metas.document.archive.api.impl;

import org.adempiere.util.Check;
import org.apache.commons.lang.BooleanUtils;

import de.metas.document.archive.api.IBPartnerBL;
import de.metas.document.archive.model.I_AD_User;
import de.metas.document.archive.model.I_C_BPartner;

/*
 * #%L
 * de.metas.document.archive.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class BPartnerBL implements IBPartnerBL
{

	@Override
	public boolean isInvoiceEmailEnabled(I_C_BPartner bpartner, I_AD_User user)
	{
		Check.assume(bpartner!=null, "Partner shoudl not be null!");
		
		final Boolean matchingisInvoiceEmailEnabled;
		String isInvoiceEmailEnabled = bpartner.getIsInvoiceEmailEnabled();
		//
		// check flag from partner 
		if (Check.isEmpty(isInvoiceEmailEnabled, true))
		{
			if (user == null)
			{
				return Boolean.TRUE;
			}
			
			//
			// if is empty in partner, check it in user
			isInvoiceEmailEnabled = user.getIsInvoiceEmailEnabled();
			//
			//if is empty also in user, return true - we do not want to let filtering by this if is not completed
			if (Check.isEmpty(isInvoiceEmailEnabled, true))
			{
				matchingisInvoiceEmailEnabled = Boolean.TRUE;
			}
			else
			{
				matchingisInvoiceEmailEnabled =  BooleanUtils.toBoolean(isInvoiceEmailEnabled);
			}
		}
		else
		{
			matchingisInvoiceEmailEnabled =  BooleanUtils.toBoolean(isInvoiceEmailEnabled);
		}

		return matchingisInvoiceEmailEnabled;
	}

}
