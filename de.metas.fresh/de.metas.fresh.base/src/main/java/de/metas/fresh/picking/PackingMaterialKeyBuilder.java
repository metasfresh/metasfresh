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


import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.adempiere.form.terminal.TerminalKeyByNameComparator;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.model.I_M_Product;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHUPIItemProductQuery;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.i18n.IMsgBL;
import de.metas.interfaces.I_C_BPartner;

public class PackingMaterialKeyBuilder
{
	private final ITerminalContext terminalContext;
	private final Date date;

	/** List of M_HU_PI_Item_IDs which were already evaluated and are excluded */
	private final Set<Integer> piItemIdBlackList = new HashSet<>();
	private final Map<String, PackingMaterialKey> packingMaterialKeys = new HashMap<>();

	private final transient IHUPIItemProductDAO piItemProductDAO = Services.get(IHUPIItemProductDAO.class);

	public PackingMaterialKeyBuilder(final ITerminalContext terminalContext, final Date date)
	{
		super();

		Check.assumeNotNull(terminalContext, "terminalContext not null");
		this.terminalContext = terminalContext;

		this.date = date;
	}

	/**
	 * Create {@link PackingMaterialKey}s for given parameters.
	 * 
	 * @param productId
	 * @param bpartnerId
	 * @param bpartnerLocationId
	 * @throws AdempiereException if no {@link PackingMaterialKey} was added for this request
	 */
	public void addProduct(final int productId,
			final int bpartnerId,
			final int bpartnerLocationId // will be used in future
	)
	{
		final IHUPIItemProductQuery queryVO = piItemProductDAO.createHUPIItemProductQuery();
		queryVO.setC_BPartner_ID(bpartnerId);
		queryVO.setM_Product_ID(productId);
		queryVO.setDate(date);
		queryVO.setAllowAnyProduct(true);

		// Flag used to check if we added at least one PackingMaterialKey
		boolean added = false;

		final Properties ctx = terminalContext.getCtx();
		final List<I_M_HU_PI_Item_Product> itemProducts = piItemProductDAO.retrieveHUItemProducts(ctx, queryVO, ITrx.TRXNAME_None);
		for (final I_M_HU_PI_Item_Product itemProduct : itemProducts)
		{
			final PackingMaterialKey packingMaterialKey = addItemProduct(itemProduct);
			if (packingMaterialKey == null)
			{
				continue;
			}

			added = true;
		}

		//
		// No PackingMaterialKey was added by this request
		// => throw exception
		if (!added)
		{
			final String translatedErrMsgWithParams = Services.get(IMsgBL.class).parseTranslation(ctx, "@HU_PI_NotFoundFor_ProductAndPartner@");

			final I_M_Product product = productId <= 0 ? null : InterfaceWrapperHelper.create(ctx, productId, I_M_Product.class, ITrx.TRXNAME_None);
			final String productValue = product == null ? "*" : product.getValue() + "_" + product.getName();
			final I_C_BPartner bpartner = bpartnerId <= 0 ? null : InterfaceWrapperHelper.create(ctx, bpartnerId, I_C_BPartner.class, ITrx.TRXNAME_None);
			final String partnerValue = bpartner == null ? "*" : bpartner.getValue() + "_" + bpartner.getName();

			final String exceptionMessage = MessageFormat.format(translatedErrMsgWithParams, productValue, partnerValue);
			throw new AdempiereException(exceptionMessage);
		}
	}

	private PackingMaterialKey addItemProduct(final I_M_HU_PI_Item_Product itemProduct)
	{
		if (itemProduct == null)
		{
			return null;
		}
		if (!itemProduct.isActive())
		{
			return null;
		}

		final int piItemId = itemProduct.getM_HU_PI_Item_ID();
		if (piItemIdBlackList.contains(piItemId))
		{
			return null;
		}

		final String packingMaterialKeyId = mkAggregationKey(itemProduct);

		final PackingMaterialKey packingMaterialKey;
		if (packingMaterialKeys.containsKey(packingMaterialKeyId))
		{
			packingMaterialKey = packingMaterialKeys.get(packingMaterialKeyId);
		}
		else
		{
			final I_M_HU_PI_Item item = itemProduct.getM_HU_PI_Item();
			if (!item.isActive())
			{
				// item not active
				piItemIdBlackList.add(piItemId);
				return null;
			}

			final I_M_HU_PI_Version piVersion = item.getM_HU_PI_Version();
			if (!piVersion.isCurrent())
			{
				// this is not an active version, skip it
				piItemIdBlackList.add(piItemId);
				return null;
			}

			final int piId = piVersion.getM_HU_PI_ID();

			// Make sure it's a concrete PI (i.e. not Virtual, not No-PI)
			if (!Services.get(IHandlingUnitsBL.class).isConcretePI(piId))
			{
				piItemIdBlackList.add(piItemId);
				return null;
			}

			final I_M_HU_PI huPI = piVersion.getM_HU_PI();
			if (!huPI.isActive())
			{
				piItemIdBlackList.add(piItemId);
				return null;
			}

			packingMaterialKey = new PackingMaterialKey(terminalContext, itemProduct);
			packingMaterialKeys.put(packingMaterialKeyId, packingMaterialKey);
		}

		packingMaterialKey.addRestriction(itemProduct);
		return packingMaterialKey;
	}

	private String mkAggregationKey(final I_M_HU_PI_Item_Product piItemProduct)
	{
		return String.valueOf(piItemProduct.getM_HU_PI_Item_Product_ID());
	}

	public List<PackingMaterialKey> getPackingMaterialKeys()
	{
		if (packingMaterialKeys.isEmpty())
		{
			return Collections.emptyList();
		}

		final List<PackingMaterialKey> result = new ArrayList<>(packingMaterialKeys.values());
		Collections.sort(result, TerminalKeyByNameComparator.instance);

		return result;
	}
}
