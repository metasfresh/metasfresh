package de.metas.handlingunits.impl;

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

import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHUPIItemProductDisplayNameBuilder;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_UOM;
import org.compiere.util.DisplayType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/* package */class HUPIItemProductDisplayNameBuilder implements IHUPIItemProductDisplayNameBuilder
{
	private I_M_HU_PI_Item_Product _huPIItemProduct;
	private BigDecimal _qtyTUPlanned;
	private BigDecimal _qtyTUMoved;
	private BigDecimal _qtyCapacity;
	private boolean _showAnyProductIndicator = false; // default false for backward compatibility

	public HUPIItemProductDisplayNameBuilder()
	{
		super();
	}

	@Override
	public String build()
	{
		final StringBuilder sb = new StringBuilder();

		//
		// Append Quantities
		final String qtysStr = getQtysDisplayName();
		final boolean hasQtysStr;
		if (!Check.isEmpty(qtysStr))
		{
			sb.append(qtysStr);
			hasQtysStr = true;
		}
		else
		{
			hasQtysStr = false;
		}

		//
		// Append PI Item Product Display Name
		final String itemProductName = getM_HU_PI_Item_Product_DisplayName();
		if (hasQtysStr)
		{
			sb.append(" x [");
		}
		sb.append(itemProductName);
		if (hasQtysStr)
		{
			sb.append("]");
		}

		//
		// Return the build string
		return sb.toString();
	}

	private String getM_HU_PI_Item_Product_DisplayName()
	{
		//
		// If M_HU_PI_Item_Product.Name was already set and we don't have any other customizations,
		// then we can reuse it
		final String itemProductDisplayNameCached = getM_HU_PI_Item_Product_DisplayName_Cached();
		if (!Check.isEmpty(itemProductDisplayNameCached, true))
		{
			return itemProductDisplayNameCached;
		}

		//
		// Build it right now
		final String itemProductDisplayName = buildItemProductDisplayName();
		return itemProductDisplayName;
	}

	private final String getM_HU_PI_Item_Product_DisplayName_Cached()
	{
		// If we customized the capacity, we shall build the display string again
		if (_qtyCapacity != null)
		{
			return null;
		}

		final I_M_HU_PI_Item_Product itemProduct = getM_HU_PI_Item_Product();
		return itemProduct.getName();
	}

	@Override
	public String buildItemProductDisplayName()
	{
		final I_M_HU_PI_Item_Product itemProduct = getM_HU_PI_Item_Product();
		final I_M_HU_PI_Item piItem = itemProduct.getM_HU_PI_Item();
		if (piItem == null || piItem.getM_HU_PI_Item_ID() <= 0)
		{
			return "";
		}

		final StringBuilder sb = new StringBuilder();

		//
		// PI Name
		final String piName = piItem.getM_HU_PI_Version().getM_HU_PI().getName();
		sb.append(piName);

		final BigDecimal qtyCapacity = getQtyCapacity();
		if (!isInfiniteCapacity() && qtyCapacity.signum() != 0)
		{
			final DecimalFormat qtyFormat = DisplayType.getNumberFormat(DisplayType.Quantity);
			final String qtyStr = qtyFormat.format(qtyCapacity);
			sb.append(" x ").append(qtyStr);

			final String qtyCapacityUOMSymbol = getQtyCapacityUOMSymbol();
			if (!Check.isEmpty(qtyCapacityUOMSymbol, true))
			{
				sb.append(" ").append(qtyCapacityUOMSymbol);
			}
		}
		
		//
		// Show "Any product allowed" indicator
		if (isShowAnyProductIndicator() && isAnyProductAllowed())
		{
			sb.append(" ").append("*");
		}

		return sb.toString();
	}

	private final boolean isInfiniteCapacity()
	{
		if (_qtyCapacity != null)
		{
			return false;
		}
		return getM_HU_PI_Item_Product().isInfiniteCapacity();
	}

	/**
	 * @return capacity; never returns null
	 */
	private final BigDecimal getQtyCapacity()
	{
		if (_qtyCapacity != null)
		{
			return _qtyCapacity;
		}

		return getM_HU_PI_Item_Product().getQty();
	}

	private final String getQtyCapacityUOMSymbol()
	{
		final I_C_UOM uom = IHUPIItemProductBL.extractUOMOrNull(getM_HU_PI_Item_Product());
		if (uom == null)
		{
			return null;
		}

		return uom.getUOMSymbol();
	}

	/**
	 *
	 * @return QtyMoved / QtyPlanned string
	 */
	private String getQtysDisplayName()
	{
		if (_qtyTUPlanned == null && _qtyTUMoved == null)
		{
			return null;
		}

		final StringBuilder sb = new StringBuilder();

		//
		// Qty Moved
		if (_qtyTUMoved != null)
		{
			sb.append(toQtyTUString(_qtyTUMoved));
		}

		//
		// Qty Planned
		{
			if (sb.length() > 0)
			{
				sb.append(" / ");
			}
			sb.append(toQtyTUString(_qtyTUPlanned));
		}

		return sb.toString();
	}

	private final String toQtyTUString(final BigDecimal qtyTU)
	{
		if (qtyTU == null)
		{
			return "?";
		}

		return qtyTU
				.setScale(0, RoundingMode.UP) // it's general integer, just making sure we don't end up with 3.0000000
				.toString();
	}

	@Override
	public IHUPIItemProductDisplayNameBuilder setM_HU_PI_Item_Product(final I_M_HU_PI_Item_Product huPIItemProduct)
	{
		_huPIItemProduct = huPIItemProduct;
		return this;
	}
	
	@Override
	public IHUPIItemProductDisplayNameBuilder setM_HU_PI_Item_Product(@NonNull final HUPIItemProductId id)
	{
		final I_M_HU_PI_Item_Product huPIItemProduct = Services.get(IHUPIItemProductDAO.class).getRecordById(id);
		return setM_HU_PI_Item_Product(huPIItemProduct);
	}

	private I_M_HU_PI_Item_Product getM_HU_PI_Item_Product()
	{
		Check.assumeNotNull(_huPIItemProduct, "_huPIItemProduct not null");
		return _huPIItemProduct;
	}

	@Override
	public IHUPIItemProductDisplayNameBuilder setQtyTUPlanned(final BigDecimal qtyTUPlanned)
	{
		_qtyTUPlanned = qtyTUPlanned;
		return this;
	}

	@Override
	public IHUPIItemProductDisplayNameBuilder setQtyTUPlanned(final int qtyTUPlanned)
	{
		setQtyTUPlanned(BigDecimal.valueOf(qtyTUPlanned));
		return this;
	}

	@Override
	public IHUPIItemProductDisplayNameBuilder setQtyTUMoved(final BigDecimal qtyTUMoved)
	{
		_qtyTUMoved = qtyTUMoved;
		return this;
	}

	@Override
	public IHUPIItemProductDisplayNameBuilder setQtyTUMoved(final int qtyTUMoved)
	{
		setQtyTUMoved(BigDecimal.valueOf(qtyTUMoved));
		return this;
	}

	@Override
	public HUPIItemProductDisplayNameBuilder setQtyCapacity(final BigDecimal qtyCapacity)
	{
		_qtyCapacity = qtyCapacity;
		return this;
	}

	@Override
	public IHUPIItemProductDisplayNameBuilder setShowAnyProductIndicator(boolean showAnyProductIndicator)
	{
		this._showAnyProductIndicator = showAnyProductIndicator;
		return this;
	}
	
	private boolean isShowAnyProductIndicator()
	{
		return _showAnyProductIndicator;
	}
	
	private boolean isAnyProductAllowed()
	{
		return getM_HU_PI_Item_Product().isAllowAnyProduct();
	}
}
