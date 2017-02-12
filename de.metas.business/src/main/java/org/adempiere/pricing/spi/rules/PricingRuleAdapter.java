package org.adempiere.pricing.spi.rules;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.Iterator;

import org.adempiere.pricing.spi.IPricingRule;
import org.compiere.model.I_M_DiscountSchemaLine;
import org.compiere.model.I_M_PriceList_Version;

import de.metas.adempiere.model.I_M_ProductPrice;

/**
 * Adapter for {@link IPricingRule} which allows developer to implement only the mandatory methods.
 * 
 * It is highly recommended to extend this class instead of extending the {@link IPricingRule} interface.
 * 
 * @author tsa
 */
public abstract class PricingRuleAdapter implements IPricingRule
{
	@Override
	public void updateFromDiscounLine(final I_M_PriceList_Version plv, final Iterator<I_M_ProductPrice> productPrices, final I_M_DiscountSchemaLine dsl)
	{
		// Nothing at this level.
		// To be implemented by extending classes which have the need to do something here.
	}

}
