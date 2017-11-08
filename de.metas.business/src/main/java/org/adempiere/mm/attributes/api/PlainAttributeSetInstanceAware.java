package org.adempiere.mm.attributes.api;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class PlainAttributeSetInstanceAware implements IAttributeSetInstanceAware
{
	private final int productId;
	private final int attributeSetInstanceId;
	
	public static PlainAttributeSetInstanceAware forProductIdAndAttributeSetInstanceId(
			final int productId,
			final int attributeSetInstanceId)
	{
		return new PlainAttributeSetInstanceAware(productId, attributeSetInstanceId);
	}
		
	private PlainAttributeSetInstanceAware(final int productId, final int attributeSetInstanceId)
	{
		this.productId = productId;
		this.attributeSetInstanceId = attributeSetInstanceId;
	}

	@Override
	public I_M_Product getM_Product()
	{
		return load(productId, I_M_Product.class);
	}

	@Override
	public int getM_Product_ID()
	{
		return productId;
	}

	@Override
	public I_M_AttributeSetInstance getM_AttributeSetInstance()
	{
		return load(attributeSetInstanceId, I_M_AttributeSetInstance.class);
	}

	@Override
	public int getM_AttributeSetInstance_ID()
	{
		return attributeSetInstanceId;
	}

	@Override
	public void setM_AttributeSetInstance(I_M_AttributeSetInstance asi)
	{
		throw new UnsupportedOperationException("Changing the M_AttributeSetInstance is not supported for " + PlainAttributeSetInstanceAware.class.getName());
	}
}
