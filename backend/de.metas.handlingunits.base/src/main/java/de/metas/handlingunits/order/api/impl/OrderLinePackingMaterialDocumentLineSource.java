package de.metas.handlingunits.order.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.HuPackingMaterial;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IPackingMaterialDocumentLineSource;
import de.metas.handlingunits.inout.IHUPackingMaterialDAO;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.ObjectUtils;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

/* package */class OrderLinePackingMaterialDocumentLineSource implements IPackingMaterialDocumentLineSource
{
	//
	// Services
	private final transient IHUPackingMaterialDAO packingMaterialDAO = Services.get(IHUPackingMaterialDAO.class);
	private final transient IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	private final I_C_OrderLine orderLine;

	private final List<I_M_HU_PackingMaterial> packingMaterials;

	public OrderLinePackingMaterialDocumentLineSource(@NonNull final org.compiere.model.I_C_OrderLine orderLine)
	{
		this.orderLine = InterfaceWrapperHelper.create(orderLine, I_C_OrderLine.class);
		Check.assume(!this.orderLine.isPackagingMaterial(), "Orderline shall have PackingMaterial flag not set: {}", this.orderLine);

		final I_M_HU_PI_Item_Product piItemProduct = this.orderLine.getM_HU_PI_Item_Product();
		if (piItemProduct != null && piItemProduct.getM_HU_PI_Item_Product_ID() > 0)
		{
			// do not create a new line if the pip is infinite capacity : task 05316
			if (piItemProduct.isInfiniteCapacity())
			{
				packingMaterials = Collections.emptyList();
			}
			else
			{
				packingMaterials = Collections.unmodifiableList(packingMaterialDAO.retrievePackingMaterials(piItemProduct));
			}
		}
		else
		{
			packingMaterials = Collections.emptyList();
		}
	}

	public I_C_OrderLine getC_OrderLine()
	{
		return orderLine;
	}

	@Override
	public List<I_M_HU_PackingMaterial> getM_HU_PackingMaterials()
	{
		return packingMaterials;
	}

	@Override
	public BigDecimal getQty()
	{
		// TODO: why not using getQtyEnteredTU?
		// yes, why not indeed?

		final BigDecimal qtyItemCapacity = orderLine.getQtyItemCapacity();
		if (qtyItemCapacity.signum() == 0)
		{
			return BigDecimal.ZERO;
		}

		final BigDecimal qtyOrdered = orderLine.getQtyOrdered();

		return qtyOrdered.divide(qtyItemCapacity, 0, RoundingMode.UP);
	}

	@Override
	public @Nullable ProductId getLUProductId()
	{
		final HuPackingInstructionsId luPIId = HuPackingInstructionsId.ofRepoIdOrNull(orderLine.getM_LU_HU_PI_ID());
		if (luPIId == null)
		{
			return null;
		}
		return packingMaterialDAO.getLUPIItemForHUPI(BPartnerId.ofRepoId(orderLine.getC_BPartner_ID()), luPIId)
				.map(HuPackingMaterial::getProductId)
				.orElse(null);
	}

	@Override
	public BigDecimal getQtyLU()
	{
		return orderLine.getQtyLU();
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}
}
