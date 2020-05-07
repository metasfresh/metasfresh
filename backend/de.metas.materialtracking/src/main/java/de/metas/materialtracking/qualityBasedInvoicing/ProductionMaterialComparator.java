package de.metas.materialtracking.qualityBasedInvoicing;

/*
 * #%L
 * de.metas.materialtracking
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
import java.util.Comparator;

/**
 * {@link IProductionMaterial} comparator which mainly is used for {@link #equals(IProductionMaterial, IProductionMaterial)}.
 *
 * NOTE:
 * <ul>
 * <li>The {@link #compare(IProductionMaterial, IProductionMaterial)} method will assure a predictible order but not quite a logical one.
 * <li>while comparing there will be no quantity conversion so the Qty and UOM shall be equal
 * </ul>
 *
 * @author tsa
 *
 */
public final class ProductionMaterialComparator implements Comparator<IProductionMaterial>
{
	public static final ProductionMaterialComparator instance = new ProductionMaterialComparator();

	private ProductionMaterialComparator()
	{
		super();
	}

	public boolean equals(final IProductionMaterial pm1, final IProductionMaterial pm2)
	{
		final int cmp = compare(pm1, pm2);
		return cmp == 0;
	}

	@Override
	public int compare(final IProductionMaterial pm1, final IProductionMaterial pm2)
	{
		if (pm1 == pm2)
		{
			return 0;
		}
		if (pm1 == null)
		{
			return -1;
		}
		if (pm2 == null)
		{
			return +1;
		}

		//
		// Compare Types
		{
			final ProductionMaterialType type1 = pm1.getType();
			final ProductionMaterialType type2 = pm2.getType();
			final int cmp = type1.compareTo(type2);
			if (cmp != 0)
			{
				return cmp;
			}
		}

		//
		// Compare Product
		{
			final int productId1 = pm1.getM_Product().getM_Product_ID();
			final int productId2 = pm2.getM_Product().getM_Product_ID();
			if (productId1 != productId2)
			{
				return productId1 - productId2;
			}
		}

		//
		// Compare Qty
		{
			final BigDecimal qty1 = pm1.getQty();
			final BigDecimal qty2 = pm2.getQty();
			final int cmp = qty1.compareTo(qty2);
			if (cmp != 0)
			{
				return cmp;
			}
		}

		//
		// Compare UOM
		{
			final int uomId1 = pm1.getC_UOM().getC_UOM_ID();
			final int uomId2 = pm2.getC_UOM().getC_UOM_ID();
			if (uomId1 != uomId2)
			{
				return uomId1 - uomId2;
			}
		}

		//
		// Compare QM_QtyDeliveredPercOfRaw
		{
			final BigDecimal qty1 = pm1.getQM_QtyDeliveredPercOfRaw();
			final BigDecimal qty2 = pm2.getQM_QtyDeliveredPercOfRaw();
			final int cmp = qty1.compareTo(qty2);
			if (cmp != 0)
			{
				return cmp;
			}
		}

		//
		// Compare QM_QtyDeliveredPercOfRaw
		{
			final BigDecimal qty1 = pm1.getQM_QtyDeliveredAvg();
			final BigDecimal qty2 = pm2.getQM_QtyDeliveredAvg();
			final int cmp = qty1.compareTo(qty2);
			if (cmp != 0)
			{
				return cmp;
			}
		}

		//
		// If we reach this point, they are equal
		return 0;
	}
}
