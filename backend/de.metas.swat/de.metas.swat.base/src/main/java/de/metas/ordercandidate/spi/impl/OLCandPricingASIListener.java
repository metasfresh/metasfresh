package de.metas.ordercandidate.spi.impl;

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


import java.util.List;

import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactoryService;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.IModelAttributeSetInstanceListener;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IPricingAttribute;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.pricing.attributebased.IAttributePricingBL;

/**
 * Keeps the olCand's ASI up to date after changes in the product or the pricing.
 * 
 * @author ts
 * @task http://dewiki908/mediawiki/index.php/08803_ADR_from_Partner_versus_Pricelist
 */
public class OLCandPricingASIListener implements IModelAttributeSetInstanceListener
{

	// don't fire on particular columns, because we also need it to fire on other columns from other modules
	private static final List<String> SOURCE_COLUMN_NAMES = ANY_SOURCE_COLUMN;

	// private static final List<String> SOURCE_COLUMN_NAMES = Arrays.asList(
	// I_C_OLCand.COLUMNNAME_M_Product_ID,
	// I_C_OLCand.COLUMNNAME_M_Product_Override_ID,
	// I_C_OLCand.COLUMNNAME_PriceInternal);

	@Override
	public String getSourceTableName()
	{
		return I_C_OLCand.Table_Name;
	}

	@Override
	public List<String> getSourceColumnNames()
	{
		return SOURCE_COLUMN_NAMES;
	}

	/**
	 * Obtains the given olCand's pricing result and resets the olCand's ASI with the list of {@link IPricingResult#getPricingAttributes()}.
	 * <p>
	 * About corner cases:
	 * <ul>
	 * <li>if there is no dynamic-attribute pricing result (of if the price was not calculated!), it does nothing. Rationale: this implementation's job is to sync/reset the result of a successful
	 * price calculation with the olCand's ASI. If there is no such pricing result, we assume that this method's service is not wanted.
	 * <li>if the pricing result has no pricing attributes, then the olCand gets an "empty" ASI with no attribute instances
	 * <li>if the olCand has already an ASI, then that ASI is discarded and ignored.
	 * <li>We do <b>not</b> care if the attribute set of the olCand's product matches the attribute values of the pricing result. Rationale: we assume that the pricing result was created for this
	 * olCand and its quantity, PIIP, date etc. and in particular also for its product.
	 * </ul>
	 * 
	 * @see OLCandPriceValidator#DYNATTR_OLCAND_PRICEVALIDATOR_PRICING_RESULT
	 */
	@Override
	public void modelChanged(final Object model)
	{
		final I_C_OLCand olCand = InterfaceWrapperHelper.create(model, I_C_OLCand.class);

		final IPricingResult pricingResult = OLCandPriceValidator.DYNATTR_OLCAND_PRICEVALIDATOR_PRICING_RESULT.getValue(olCand);

		if (pricingResult == null || !pricingResult.isCalculated())
		{
			return; // nothing to do
		}

		final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
		final IAttributeSetInstanceAwareFactoryService attributeSetInstanceAwareFactoryService = Services.get(IAttributeSetInstanceAwareFactoryService.class);

		final IAttributeSetInstanceAware asiAware = attributeSetInstanceAwareFactoryService.createOrNull(olCand);
		Check.assumeNotNull(asiAware,
				"We have an asiAware for C_OLCand {}, because we implemented and registered the factory {}",
				olCand, OLCandASIAwareFactory.class.getName());

		asiAware.setM_AttributeSetInstance(null); // reset, because we want getCreateASI to give us a new ASI
		final List<IPricingAttribute> pricingAttributes = pricingResult.getPricingAttributes();
		if (!pricingAttributes.isEmpty())
		{
			attributeSetInstanceBL.getCreateASI(asiAware);
			
			final IAttributePricingBL attributePricingBL = Services.get(IAttributePricingBL.class);
			attributePricingBL.addToASI(asiAware, pricingAttributes);
		}
	}
}
