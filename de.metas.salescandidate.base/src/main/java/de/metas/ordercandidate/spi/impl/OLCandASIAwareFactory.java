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


import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactory;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;

import de.metas.ordercandidate.api.IOLCandEffectiveValuesBL;
import de.metas.ordercandidate.model.I_C_OLCand;

/**
 * Returns an ASIAware with the given olCand's <b>effective</b> product.<br>
 * Or returns <code>null</code> if the given parameter is null or not an olCand.
 *
 * @see IOLCandEffectiveValuesBL#getM_Product_Effective(I_C_OLCand)
 * @task http://dewiki908/mediawiki/index.php/08803_ADR_from_Partner_versus_Pricelist
 */
public class OLCandASIAwareFactory implements IAttributeSetInstanceAwareFactory
{
	@Override
	public IAttributeSetInstanceAware createOrNull(final Object model)
	{
		if (model == null)
		{
			return null;
		}
		if (!InterfaceWrapperHelper.isInstanceOf(model, I_C_OLCand.class))
		{
			return null;
		}

		final I_C_OLCand olCand = InterfaceWrapperHelper.create(model, I_C_OLCand.class);

		// note: returning an anonymous implementation because it is rather trivial and it feels a bit like pollution to add another "real" class just for this purpose.
		return new IAttributeSetInstanceAware()
		{
			@Override
			public I_M_Product getM_Product()
			{
				return Services.get(IOLCandEffectiveValuesBL.class).getM_Product_Effective(olCand);
			}
			
			@Override
			public int getM_Product_ID()
			{
				return Services.get(IOLCandEffectiveValuesBL.class).getM_Product_Effective_ID(olCand);
			}

			@Override
			public I_M_AttributeSetInstance getM_AttributeSetInstance()
			{
				return olCand.getM_AttributeSetInstance();
			}

			@Override
			public int getM_AttributeSetInstance_ID()
			{
				return olCand.getM_AttributeSetInstance_ID();
			}

			@Override
			public void setM_AttributeSetInstance(final I_M_AttributeSetInstance asi)
			{
				olCand.setM_AttributeSetInstance(asi);
			}

			@Override
			public String toString()
			{
				return "IAttributeSetInstanceAware[" + olCand.toString() + "]";
			}
		};
	}
}
