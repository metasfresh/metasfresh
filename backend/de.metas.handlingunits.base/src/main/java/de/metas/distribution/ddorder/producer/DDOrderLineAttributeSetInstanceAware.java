package de.metas.distribution.ddorder.producer;

import com.google.common.base.MoreObjects;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_DD_OrderLine;

/*
 * #%L
 * de.metas.handlingunits.base
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

/**
 * Wraps {@link I_DD_OrderLine} and makes it behave like {@link IAttributeSetInstanceAware}.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
class DDOrderLineAttributeSetInstanceAware implements IAttributeSetInstanceAware
{
	public static DDOrderLineAttributeSetInstanceAware ofASIFrom(final I_DD_OrderLine ddOrderLine)
	{
		final boolean isASITo = false;
		return new DDOrderLineAttributeSetInstanceAware(ddOrderLine, isASITo);
	}

	public static DDOrderLineAttributeSetInstanceAware ofASITo(final I_DD_OrderLine ddOrderLine)
	{
		final boolean isASITo = true;
		return new DDOrderLineAttributeSetInstanceAware(ddOrderLine, isASITo);
	}

	private final I_DD_OrderLine ddOrderLine;
	private final boolean isASITo;

	private DDOrderLineAttributeSetInstanceAware(@NonNull final I_DD_OrderLine ddOrderLine, final boolean isASITo)
	{
		this.ddOrderLine = ddOrderLine;
		this.isASITo = isASITo;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("ddOrderLine", ddOrderLine)
				.add("isASITo", isASITo)
				.toString();
	}

	@Override
	public I_M_Product getM_Product()
	{
		final IProductBL productBL = Services.get(IProductBL.class);
		return productBL.getById(ProductId.ofRepoId(ddOrderLine.getM_Product_ID()));
	}

	@Override
	public int getM_Product_ID()
	{
		return ddOrderLine.getM_Product_ID();
	}

	@Override
	public I_M_AttributeSetInstance getM_AttributeSetInstance()
	{
		return isASITo ? ddOrderLine.getM_AttributeSetInstanceTo() : ddOrderLine.getM_AttributeSetInstance();
	}

	@Override
	public int getM_AttributeSetInstance_ID()
	{
		return isASITo ? ddOrderLine.getM_AttributeSetInstanceTo_ID() : ddOrderLine.getM_AttributeSetInstance_ID();
	}

	@Override
	public void setM_AttributeSetInstance(final I_M_AttributeSetInstance asi)
	{
		if (isASITo)
		{
			ddOrderLine.setM_AttributeSetInstanceTo(asi);
		}
		else
		{
			ddOrderLine.setM_AttributeSetInstance(asi);
		}
	}

	@Override
	public void setM_AttributeSetInstance_ID(final int M_AttributeSetInstance_ID)
	{
		if (isASITo)
		{
			ddOrderLine.setM_AttributeSetInstanceTo_ID(M_AttributeSetInstance_ID);
		}
		else
		{
			ddOrderLine.setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID);
		}
	}
}
