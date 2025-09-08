package de.metas.distribution.ddorder.hu_spis;

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

import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelDAO;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.document.IHUDocument;
import de.metas.handlingunits.document.IHUDocumentFactory;
import de.metas.handlingunits.document.impl.AbstractHUDocumentFactory;
import de.metas.handlingunits.document.impl.HandlingUnitHUDocumentFactory;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.product.ProductId;
import de.metas.quantity.Capacity;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.SpringContextHolder;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Creates {@link IHUDocument}s from {@link I_DD_Order}.
 * <p>
 * Actually it selects all handling units from each {@link I_DD_OrderLine#getM_Locator_ID()}s.
 *
 * @implSpec task http://dewiki908/mediawiki/index.php/05392_Rolle_Bereitsteller_%28109951640418%29#HU_Editor
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
		final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final IHUDocumentFactory huFactory = getHandlingUnitsHUDocumentFactory();

		final Set<Integer> seenHuIds = new HashSet<>();
		final DDOrderLowLevelDAO ddOrderLowLevelDAO = SpringContextHolder.instance.getBean(DDOrderLowLevelDAO.class);
		for (final I_DD_OrderLine line : ddOrderLowLevelDAO.retrieveLines(ddOrder))
		{
			//
			// Create HUDocuments
			final LocatorId locatorId = warehouseDAO.getLocatorIdByRepoId(line.getM_Locator_ID());
			final Iterator<I_M_HU> hus = handlingUnitsDAO.retrieveTopLevelHUsForLocator(locatorId);
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
			final Quantity qtyToDeliver = Quantitys.of(
					line.getQtyOrdered().subtract(line.getQtyDelivered()),
					UomId.ofRepoId(line.getC_UOM_ID()));
			final Capacity targetCapacity = Capacity.createCapacity(
					qtyToDeliver.toBigDecimal(), // qty
					ProductId.ofRepoId(line.getM_Product_ID()),
					qtyToDeliver.getUOM(),
					false// allowNegativeCapacity
			);
			documentsCollector.getTargetCapacities().add(targetCapacity);
		}
	}
}
