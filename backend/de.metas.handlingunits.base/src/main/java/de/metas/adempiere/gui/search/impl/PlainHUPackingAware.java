package de.metas.adempiere.gui.search.impl;

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
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

<<<<<<< HEAD
import java.math.BigDecimal;

import org.adempiere.mm.attributes.AttributeSetInstanceId;

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.adempiere.gui.search.IHUPackingAware;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import lombok.Data;
<<<<<<< HEAD
=======
import org.adempiere.mm.attributes.AttributeSetInstanceId;

import java.math.BigDecimal;
import java.util.Optional;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

/**
 * Plain POJO implementation of {@link IHUPackingAware}
 *
 * @author tsa
<<<<<<< HEAD
 *
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 */
@Data
public class PlainHUPackingAware implements IHUPackingAware
{
	private ProductId productId;
	private AttributeSetInstanceId asiId;
	private BigDecimal qty;
	private UomId uomId;
	private HUPIItemProductId piItemProductId;
	private BigDecimal qtyTU;
<<<<<<< HEAD
=======
	private BigDecimal qtyCUsPerTU;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	private BPartnerId bpartnerId;
	private boolean inDispute = false;

	@Override
	@Deprecated
	public int getM_Product_ID()
	{
		return ProductId.toRepoId(getProductId());
	}

	@Override
	@Deprecated
	public void setM_Product_ID(final int productId)
	{
		setProductId(ProductId.ofRepoIdOrNull(productId));
	}

	@Override
	@Deprecated
	public int getC_UOM_ID()
	{
		return UomId.toRepoId(getUomId());
	}

	@Override
	@Deprecated
	public void setC_UOM_ID(final int uomId)
	{
		setUomId(UomId.ofRepoIdOrNull(uomId));
	}

	@Override
	@Deprecated
	public int getM_HU_PI_Item_Product_ID()
	{
		return HUPIItemProductId.toRepoId(getPiItemProductId());
	}

	@Override
	@Deprecated
	public void setM_HU_PI_Item_Product_ID(final int piItemProductId)
	{
		setPiItemProductId(HUPIItemProductId.ofRepoIdOrNull(piItemProductId));
	}

	@Override
	@Deprecated
	public int getM_AttributeSetInstance_ID()
	{
		return AttributeSetInstanceId.toRepoId(getAsiId());
	}

	@Override
	@Deprecated
	public void setM_AttributeSetInstance_ID(final int M_AttributeSetInstance_ID)
	{
		setAsiId(AttributeSetInstanceId.ofRepoIdOrNull(M_AttributeSetInstance_ID));
	}

	@Override
	@Deprecated
	public int getC_BPartner_ID()
	{
		return BPartnerId.toRepoId(getBpartnerId());
	}

	@Override
	@Deprecated
	public void setC_BPartner_ID(final int bpartnerId)
	{
		setBpartnerId(BPartnerId.ofRepoIdOrNull(bpartnerId));
	}
<<<<<<< HEAD
}
=======

	@Override
	public Optional<BigDecimal> getQtyCUsPerTU()
	{
		return Optional.ofNullable(qtyCUsPerTU);
	}

	@Override
	public void setQtyCUsPerTU(final BigDecimal qtyCUsPerTU)
	{
		this.qtyCUsPerTU = qtyCUsPerTU;
	}
}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
