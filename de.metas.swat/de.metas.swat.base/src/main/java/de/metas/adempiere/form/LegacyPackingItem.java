/**
 * 
 */
package de.metas.adempiere.form;

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


import java.math.BigDecimal;
import java.util.Map;

import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.adempiere.model.I_M_Product;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.interfaces.I_C_OrderLine;

/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Transportverpackung_%282009_0022_G61%29'>(2009_0022_G61)</a>"
 */
@Deprecated
public class LegacyPackingItem extends AbstractPackingItem implements Comparable<LegacyPackingItem>
{
	private final String trxName;

	/** Copy constructor */
	public LegacyPackingItem(final IPackingItem orig)
	{
		super(orig);
		if (orig instanceof LegacyPackingItem)
		{
			final LegacyPackingItem item = (LegacyPackingItem)orig;
			this.trxName = item.trxName;
		}
		else
		{
			throw new IllegalArgumentException("Item " + orig + " is not instanceof " + LegacyPackingItem.class);
		}
	}

	public LegacyPackingItem(final Map<I_M_ShipmentSchedule, BigDecimal> scheds2Qtys, final String trxName)
	{
		super(scheds2Qtys);
		this.trxName = trxName;
	}

	public LegacyPackingItem(final Map<I_M_ShipmentSchedule, BigDecimal> scheds2Qtys, final int groupingKey, final String trxName)
	{
		super(scheds2Qtys, groupingKey);
		this.trxName  = trxName;
	}


	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(getQtySum());
		sb.append(" x ");

		final I_M_Product product = getM_Product();
		if (product != null)
		{
			sb.append(product.getValue());
			sb.append(" (");
			if (product.isDiverse())
			{
				final I_C_OrderLine ol = InterfaceWrapperHelper.create(getShipmentSchedules().iterator().next().getC_OrderLine(), I_C_OrderLine.class);
				sb.append(ol.getProductDescription());
			}
			else
			{
				sb.append(product.getName());
			}
			sb.append(")");
		}
		else
		{
			sb.append("");
		}

		return sb.toString();
	}

	@Override
	public int compareTo(final LegacyPackingItem o)
	{
		final String trxName = getTrxName();
		final BigDecimal volOther = o.getQtySum().multiply(o.retrieveVolumeSingle(trxName));
		final BigDecimal volThis = getQtySum().multiply(retrieveVolumeSingle(trxName));

		return volThis.compareTo(volOther);
	}

	@Override
	public IPackingItem copy()
	{
		return new LegacyPackingItem(this);
	}

	public String getTrxName()
	{
		return trxName;
	}
}
