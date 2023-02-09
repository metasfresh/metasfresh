package de.metas.adempiere.gui.search;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.adempiere.gui.search.impl.PlainHUPackingAware;
import de.metas.quantity.Capacity;
import de.metas.quantity.Quantity;
import de.metas.util.ISingletonService;
import org.compiere.apps.search.IInfoSimple;

import java.math.BigDecimal;

/**
 * Service used for manipulating {@link IHUPackingAware}.
 *
 * @author tsa
 *
 */
public interface IHUPackingAwareBL extends ISingletonService
{
	/**
	 * Creates a plain (not database coupled) {@link IHUPackingAware} POJO implementation.
	 */
	IHUPackingAware createPlain();

	/**
	 * Creates an adapter which wraps a row from Info Window grid and make it behave like an {@link IHUPackingAware}
	 *
	 */
	IHUPackingAware create(final IInfoSimple infoWindow, final int rowIndexModel);

	default HUPackingAwareCopy prepareCopyFrom(final IHUPackingAware from)
	{
		return HUPackingAwareCopy.from(from);
	}

	/**
	 * Copies all fields from one {@link IHUPackingAware} to another.
	 */
	default void copy(final IHUPackingAware to, final IHUPackingAware from)
	{
		prepareCopyFrom(from).copyTo(to);
	}

	/**
	 * Note: doesn't save.
	 */
	void setQtyTU(IHUPackingAware record);

	Capacity calculateCapacity(IHUPackingAware record);

	/**
	 * Sets Qty CU from packing instructions and {@code qtyPacks}
	 *
	 * @param qtyPacks a.k.ak Qty TUs
	 */
	void setQtyCUFromQtyTU(IHUPackingAware record, int qtyPacks);

	/**
	 * This method verifies if the qtyCU given as parameter fits the qtyPacks. If it does, the record will not be updated.
	 * In case the QtyCU is too big or too small to fit in the QtyPacks, it will be changed to the maximum capacity required by the QtyPacks and the M_HU_PI_Item_Product of the record
	 */
	void updateQtyIfNeeded(IHUPackingAware record, int qtyPacks, Quantity qtyCU);

	void computeAndSetQtysForNewHuPackingAware(final PlainHUPackingAware huPackingAware, final BigDecimal quickInputQty);

	boolean isInfiniteCapacityTU(IHUPackingAware huPackingAware);
}
