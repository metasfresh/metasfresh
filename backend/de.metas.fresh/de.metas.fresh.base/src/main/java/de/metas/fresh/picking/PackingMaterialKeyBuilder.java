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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.text.MessageFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.form.terminal.TerminalKeyByNameComparator;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHUPIItemProductQuery;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.i18n.IMsgBL;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;

public class PackingMaterialKeyBuilder
{
	private final ITerminalContext terminalContext;
	private final ZonedDateTime date;

	/** List of M_HU_PI_Item_IDs which were already evaluated and are excluded */
	private final Set<Integer> piItemIdBlackList = new HashSet<>();
	private final Map<String, PackingMaterialKey> packingMaterialKeys = new HashMap<>();

	private final transient IHUPIItemProductDAO piItemProductDAO = Services.get(IHUPIItemProductDAO.class);

	public PackingMaterialKeyBuilder(@NonNull final ITerminalContext terminalContext, final ZonedDateTime date)
	{
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
	public void addProduct(
			final ProductId productId,
			final BPartnerId bpartnerId,
			final BPartnerLocationId bpartnerLocationId // will be used in future
	)
	{
		final IHUPIItemProductQuery queryVO = piItemProductDAO.createHUPIItemProductQuery();
		queryVO.setC_BPartner_ID(BPartnerId.toRepoIdOr(bpartnerId, -1));
		queryVO.setM_Product_ID(ProductId.toRepoId(productId));
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

			final String productValue = Services.get(IProductBL.class).getProductValueAndName(productId);
			final String partnerValue = Services.get(IBPartnerBL.class).getBPartnerValueAndName(bpartnerId);

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

			final HuPackingInstructionsId piId = HuPackingInstructionsId.ofRepoId(piVersion.getM_HU_PI_ID());

			// Make sure it's a concrete PI (i.e. not Virtual, not No-PI)
			if(!piId.isRealPackingInstructions())
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

	public ImmutableList<PackingMaterialKey> getPackingMaterialKeys()
	{
		if (packingMaterialKeys.isEmpty())
		{
			return ImmutableList.of();
		}

		final List<PackingMaterialKey> result = new ArrayList<>(packingMaterialKeys.values());
		Collections.sort(result, TerminalKeyByNameComparator.instance);

		return ImmutableList.copyOf(result);
	}
}
