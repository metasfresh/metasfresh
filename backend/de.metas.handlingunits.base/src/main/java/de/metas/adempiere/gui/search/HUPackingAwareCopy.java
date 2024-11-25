package de.metas.adempiere.gui.search;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.compiere.model.I_M_AttributeSetInstance;

import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
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

public class HUPackingAwareCopy
{
	public static final HUPackingAwareCopy from(final IHUPackingAware from)
	{
		return new HUPackingAwareCopy(from);
	}

	private final IHUPackingAware from;
	private boolean overridePartner = true;

	public enum ASICopyMode
	{
		Clone, CopyID
	}

	private ASICopyMode asiCopyMode = ASICopyMode.Clone;

	private HUPackingAwareCopy(@NonNull final IHUPackingAware from)
	{
		this.from = from;
	}

	public void copyTo(@NonNull final IHUPackingAware to)
	{
		to.setM_Product_ID(from.getM_Product_ID());
		copyASI(to);

		to.setC_UOM_ID(from.getC_UOM_ID());
		to.setQty(from.getQty());
		to.setM_HU_PI_Item_Product_ID(from.getM_HU_PI_Item_Product_ID());
		to.setQtyTU(from.getQtyTU());
		from.getQtyCUsPerTU().ifPresent(to::setQtyCUsPerTU);

		copyBPartner(to);
	}

	private void copyASI(final IHUPackingAware to)
	{
		final int asiId = from.getM_AttributeSetInstance_ID();
		if (asiId <= 0)
		{
			return;
		}

		if (asiCopyMode == ASICopyMode.Clone)
		{
			final I_M_AttributeSetInstance fromASI = load(asiId, I_M_AttributeSetInstance.class);
			final I_M_AttributeSetInstance toASI = Services.get(IAttributeDAO.class).copy(fromASI);
			to.setM_AttributeSetInstance_ID(toASI.getM_AttributeSetInstance_ID());
		}
		else if (asiCopyMode == ASICopyMode.CopyID)
		{
			to.setM_AttributeSetInstance_ID(asiId);
		}
		else
		{
			throw new IllegalStateException("Unknown asiCopyMode: " + asiCopyMode);
		}
	}

	private void copyBPartner(final IHUPackingAware to)
	{
		// 08276
		// do not modify the partner in the orderline if it was already set
		if (to.getC_BPartner_ID() <= 0 || overridePartner)
		{
			to.setC_BPartner_ID(from.getC_BPartner_ID());
		}
	}

	/**
	 * @param overridePartner if true, the BPartner will be copied, even if "to" record already has a partner set
	 */
	public HUPackingAwareCopy overridePartner(final boolean overridePartner)
	{
		this.overridePartner = overridePartner;
		return this;
	}

	public HUPackingAwareCopy asiCopyMode(@NonNull final ASICopyMode asiCopyMode)
	{
		this.asiCopyMode = asiCopyMode;
		return this;
	}
}
