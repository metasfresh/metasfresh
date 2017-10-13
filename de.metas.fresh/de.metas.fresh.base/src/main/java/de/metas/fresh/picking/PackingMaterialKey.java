/**
 *
 */
package de.metas.fresh.picking;

/*
 * #%L
 * de.metas.fresh.base
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


import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.KeyNamePair;

import de.metas.adempiere.form.terminal.TerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHUPIItemProductQuery;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;

/**
 * @author cg
 *
 */
public class PackingMaterialKey extends TerminalKey
{
	// services
	private final transient IHUPIItemProductDAO itemProductDAO = Services.get(IHUPIItemProductDAO.class);
	private final transient IHUPIItemProductBL piItemProductBL = Services.get(IHUPIItemProductBL.class);

	private final String tableName;
	private final String id;
	private final String displayName;
	private final I_M_HU_PI_Item_Product itemProduct;
	private final I_M_HU_PI huPI;
	private final KeyNamePair value;

	private final Set<I_M_HU_PI_Item_Product> restrictions = new LinkedHashSet<I_M_HU_PI_Item_Product>();
	private final Set<I_M_HU_PI_Item_Product> restrictionsRO = Collections.unmodifiableSet(restrictions);

	PackingMaterialKey(final ITerminalContext terminalContext, final I_M_HU_PI_Item_Product itemProduct)
	{
		super(terminalContext);

		Check.assumeNotNull(itemProduct, "itemProduct not null");
		this.itemProduct = itemProduct;

		huPI = itemProduct.getM_HU_PI_Item().getM_HU_PI_Version().getM_HU_PI();

		tableName = I_M_HU_PI.Table_Name;

		final int piItemProductId = itemProduct.getM_HU_PI_Item_Product_ID();
		id = String.valueOf(piItemProductId);
		displayName = piItemProductBL
				.buildDisplayName()
				.setM_HU_PI_Item_Product(itemProduct)
				.setShowAnyProductIndicator(true)
				.buildItemProductDisplayName();
		value = new KeyNamePair(piItemProductId, displayName);
	}

	/** @return PI Item Product; never returns null */
	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product()
	{
		return itemProduct;
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public Object getName()
	{
		return value.getName();
	}

	@Override
	public String getTableName()
	{
		return tableName;
	}

	@Override
	public KeyNamePair getValue()
	{
		return value;
	}

	@Override
	public String getText()
	{
		return displayName;
	}

	public void addRestriction(final I_M_HU_PI_Item_Product itemProduct)
	{
		if (!hasRestriction(itemProduct))
		{
			restrictions.add(itemProduct);
		}
	}

	private boolean hasRestriction(final I_M_HU_PI_Item_Product itemProduct)
	{
		// NOTE: for some reason, the equals() method is not working properly so we check here explicitly for duplicates
		final int itemProductId = itemProduct.getM_HU_PI_Item_Product_ID();
		for (final I_M_HU_PI_Item_Product r : restrictions)
		{
			if (r.getM_HU_PI_Item_Product_ID() == itemProductId)
			{
				// already added
				return true;
			}
		}

		return false;
	}

	public Set<I_M_HU_PI_Item_Product> getRestrictions()
	{
		return restrictionsRO;
	}

	@Override
	public String getDebugInfo()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("M_HU_PI:").append(huPI.getM_HU_PI_ID());

		if (!restrictions.isEmpty())
		{
			sb.append("\n").append("Restrictions(" + restrictions.size() + "):");
			for (final I_M_HU_PI_Item_Product r : restrictions)
			{
				sb.append("\n").append("Product=" + r.getM_Product() + ", BPartner=" + r.getC_BPartner() + ", M_HU_PI_Item_Product_ID=" + r.getM_HU_PI_Item_Product_ID());
			}
		}
		return sb.toString();
	}

	public boolean isCompatible(final ITerminalContext terminalContext, final FreshProductKey productKey)
	{
		Check.assumeNotNull(productKey, "productKey not null");

		//
		// Create query
		final IHUPIItemProductQuery query = itemProductDAO.createHUPIItemProductQuery();
		query.setC_BPartner_ID(productKey.getC_BPartner_ID());
		// query.setC_BPartner_Location_ID(productKey.getC_BPartner_Location_ID()); // maybe in future
		query.setM_Product_ID(productKey.getM_Product_ID());
		query.setAllowAnyProduct(true);

		//
		// Check if matches
		final Properties ctx = terminalContext.getCtx();
		return itemProductDAO.matches(ctx, getRestrictions(), query);
	}

	/**
	 * Checks if this packing material key is compatible with ALL of given productKeys.
	 *
	 * @param terminalContext
	 * @param productKeys
	 * @return true if compatible.
	 */
	public boolean isCompatibleWithAll(final ITerminalContext terminalContext, final Collection<FreshProductKey> productKeys)
	{
		Check.assumeNotNull(productKeys, "productKeys not null");
		if (productKeys.isEmpty())
		{
			return false;
		}

		for (final FreshProductKey productKey : productKeys)
		{
			if (!isCompatible(terminalContext, productKey))
			{
				return false;
			}
		}

		return true;
	}

	/**
	 * Checks if this packing material key has exactly the same packing material configuration as product key's default configuration.
	 *
	 * @param productKey product key or <code>null</code>
	 * @return true if the given product key's default packing material is contained in this packing material key
	 */
	public boolean hasSamePackingMaterials(final FreshProductKey productKey)
	{
		// guard agaist null
		if (productKey == null)
		{
			return false;
		}

		// If product key has no default PIP => return false
		final I_M_HU_PI_Item_Product productKeyDefaultPIP = productKey.getM_HU_PI_Item_Product();
		if (productKeyDefaultPIP == null)
		{
			return false;
		}
		final int productKeyDefaultPIP_Id = productKeyDefaultPIP.getM_HU_PI_Item_Product_ID();

		//
		// Check if the product key's default packing material is the same as this packing material
		final I_M_HU_PI_Item_Product piItemProduct = getM_HU_PI_Item_Product();
		if (piItemProduct.getM_HU_PI_Item_Product_ID() == productKeyDefaultPIP_Id)
		{
			return true;
		}

		//
		// Check additional added restrictions (i.e. PIPs)
		// NOTE: additional restrictions is kind of legacy but i've implemented because it might be that we will re-use it in future.
		for (final I_M_HU_PI_Item_Product pip : getRestrictions())
		{
			if (pip.getM_HU_PI_Item_Product_ID() == productKeyDefaultPIP_Id)
			{
				return true;
			}
		}

		//
		// We found nothing that matches the product key's default PIP
		return false;
	}

	public boolean isInfiniteCapacity()
	{
		return itemProduct.isInfiniteCapacity();
	}
}
