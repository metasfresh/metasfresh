package de.metas.contracts.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;

/*
 * #%L
 * de.metas.contracts
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

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;

import de.metas.contracts.subscription.model.I_C_OrderLine;

/**
 *
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Abonnement_Auftragsverwaltung_(2009_0015_G36)'>(2009_0015_G36)</a>"
 */
// code used to be located in /sw01_swat_it/src/java/org/adempiere/order/subscription/modelvalidator/OrderValidator.java
@Interceptor(I_C_OrderLine.class)
public class C_OrderLine
{

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void updateDataEntry(final I_C_OrderLine ol)
	{
			if (ol.isProcessed())
		{
			final I_C_Order order = ol.getC_Order();

			final Integer subscriptionId = ol.getC_Flatrate_Conditions_ID();

			if (subscriptionId == null || subscriptionId <= 0)
			{
				// TODO figure out wtf this check and this error message mean
				throw new AdempiereException("OrderLine " + ol.getLine()
						+ " of order " + order.getDocumentNo()
						+ " is processed, but I don't know why");
			}
		}
	}

}
