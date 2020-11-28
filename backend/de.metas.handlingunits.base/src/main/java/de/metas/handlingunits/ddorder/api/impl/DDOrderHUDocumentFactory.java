package de.metas.handlingunits.ddorder.api.impl;

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


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.compiere.model.I_M_Locator;
import org.eevolution.api.IDDOrderDAO;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;

import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.document.IHUDocument;
import de.metas.handlingunits.document.IHUDocumentFactory;
import de.metas.handlingunits.document.impl.AbstractHUDocumentFactory;
import de.metas.handlingunits.document.impl.HandlingUnitHUDocumentFactory;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.product.ProductId;
import de.metas.quantity.Capacity;
import de.metas.util.Services;

/**
 * Creates {@link IHUDocument}s from {@link I_DD_Order}.
 *
 * Actually it selects all handling units from each {@link I_DD_OrderLine#getM_Locator()}s.
 *
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/05392_Rolle_Bereitsteller_%28109951640418%29#HU_Editor
 */
public class DDOrderHUDocumentFactory extends AbstractHUDocumentFactory<I_DD_Order>
{
	public DDOrderHUDocumentFactory()
	{
		super(I_DD_Order.class);
	}

	private IHUDocumentFactory getHandlingUnitsHUDocumentFactory()
	{
		return new HandlingUnitHUDocumentFactory()
		{
			@Override
			protected I_M_HU getInnerHU(final I_M_HU hu)
			{
				// NOTE: we are always returning the HU, even if it does not have a parent
				return hu;
			}
		};
	}

	@Override
	protected void createHUDocumentsFromTypedModel(final HUDocumentsCollector documentsCollector, final I_DD_Order ddOrder)
	{
		final IHUDocumentFactory huFactory = getHandlingUnitsHUDocumentFactory();

		final Set<Integer> seenHuIds = new HashSet<>();
		for (final I_DD_OrderLine line : Services.get(IDDOrderDAO.class).retrieveLines(ddOrder))
		{
			//
			// Create HUDocuments
			final I_M_Locator locator = line.getM_Locator();
			final Iterator<I_M_HU> hus = Services.get(IHandlingUnitsDAO.class).retrieveTopLevelHUsForLocator(locator);
			while (hus.hasNext())
			{
				final I_M_HU hu = hus.next();
				final int huId = hu.getM_HU_ID();
				if (!seenHuIds.add(huId))
				{
					// already added
					continue;
				}

				final List<IHUDocument> lineDocuments = huFactory.createHUDocumentsFromModel(hu);
				documentsCollector.getHUDocuments().addAll(lineDocuments);
			}

			//
			// Create target Capacities
			final BigDecimal qtyToDeliver = line.getQtyOrdered().subtract(line.getQtyDelivered());
			final Capacity targetCapacity = Capacity.createCapacity(
					qtyToDeliver, // qty
					ProductId.ofRepoId(line.getM_Product_ID()),
					line.getC_UOM(),
					false// allowNegativeCapacity
					);
			documentsCollector.getTargetCapacities().add(targetCapacity);
		}
	}
}
