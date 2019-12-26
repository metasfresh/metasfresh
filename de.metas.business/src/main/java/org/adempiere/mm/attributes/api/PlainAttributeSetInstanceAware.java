package org.adempiere.mm.attributes.api;

import javax.annotation.Nullable;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;

import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@ToString
@EqualsAndHashCode
public class PlainAttributeSetInstanceAware implements IAttributeSetInstanceAware
{
	private final ProductId productId;
	private final AttributeSetInstanceId attributeSetInstanceId;

	public static PlainAttributeSetInstanceAware forProductIdAndAttributeSetInstanceId(
			final int productId,
			final int attributeSetInstanceId)
	{
		return new PlainAttributeSetInstanceAware(
				ProductId.ofRepoIdOrNull(productId),
				AttributeSetInstanceId.ofRepoIdOrNone(attributeSetInstanceId));
	}

	public static PlainAttributeSetInstanceAware forProductIdAndAttributeSetInstanceId(
			final ProductId productId,
			final int attributeSetInstanceId)
	{
		return new PlainAttributeSetInstanceAware(
				productId,
				AttributeSetInstanceId.ofRepoIdOrNone(attributeSetInstanceId));
	}

	public static PlainAttributeSetInstanceAware forProductIdAndAttributeSetInstanceId(
			@NonNull final ProductId productId,
			@Nullable final AttributeSetInstanceId attributeSetInstanceId)
	{
		return new PlainAttributeSetInstanceAware(productId, attributeSetInstanceId);
	}

	private PlainAttributeSetInstanceAware(
			@Nullable final ProductId productId,
			@NonNull final AttributeSetInstanceId attributeSetInstanceId)
	{
		this.productId = productId;
		this.attributeSetInstanceId = attributeSetInstanceId;
	}

	@Override
	public I_M_Product getM_Product()
	{
		return Services.get(IProductDAO.class).getById(productId);
	}

	@Override
	public int getM_Product_ID()
	{
		return ProductId.toRepoId(productId);
	}

	public ProductId getProductId()
	{
		return productId;
	}

	@Override
	public I_M_AttributeSetInstance getM_AttributeSetInstance()
	{
		return Services.get(IAttributeDAO.class).getAttributeSetInstanceById(attributeSetInstanceId);
	}

	@Override
	public int getM_AttributeSetInstance_ID()
	{
		return attributeSetInstanceId.getRepoId();
	}

	@Override
	public void setM_AttributeSetInstance(I_M_AttributeSetInstance asi)
	{
		throw new UnsupportedOperationException("Changing the M_AttributeSetInstance is not supported for " + PlainAttributeSetInstanceAware.class.getName());
	}

	@Override
	public void setM_AttributeSetInstance_ID(int M_AttributeSetInstance_ID)
	{
		throw new UnsupportedOperationException("Changing the M_AttributeSetInstance_ID is not supported for " + PlainAttributeSetInstanceAware.class.getName());
	}
}
