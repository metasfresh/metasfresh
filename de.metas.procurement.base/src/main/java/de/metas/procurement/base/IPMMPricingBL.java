package de.metas.procurement.base;

import org.adempiere.pricing.spi.IPricingRule;
import org.adempiere.util.ISingletonService;

import de.metas.flatrate.model.I_C_Flatrate_Term;

/*
 * #%L
 * de.metas.procurement.base
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
 * BL used to calculate the pricing for various PMM models.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IPMMPricingBL extends ISingletonService
{
	/**
	 * Basically use the pricing engine to update the given {@code pricingAware} instance,
	 * no matter whether it is assigned to a {@link I_C_Flatrate_Term} or not.
	 * 
	 * @param pricingAware
	 */
	void updatePricing(IPMMPricingAware pricingAware);

	/**
	 * Assume that the given {@code pricingAware} instance has a {@link I_C_Flatrate_Term} (fail if it hasn't!) and take the pricing from that term.
	 * Unlike {@link #updatePricing(IPMMPricingAware)}, this method can be called from an actual {@link IPricingRule}, after it was made sure that the given {@code pricingAware} instance qualifies.
	 * 
	 * @param pricingAware
	 * @return
	 */
	boolean updatePriceFromContract(IPMMPricingAware pricingAware);
}
