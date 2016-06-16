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

import java.math.BigDecimal;

import org.adempiere.util.ISingletonService;
import org.compiere.apps.search.IInfoSimple;

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
	 *
	 * @return
	 */
	IHUPackingAware createPlain();

	/**
	 * Creates an adapter which wraps a row from Info Window grid and make it behave like an {@link IHUPackingAware}
	 *
	 * @param infoWindow
	 * @param rowIndexModel
	 * @return
	 */
	IHUPackingAware create(final IInfoSimple infoWindow, final int rowIndexModel);

	/**
	 * Copies all fields from one {@link IHUPackingAware} to another.
	 *
	 * @param to
	 * @param from
	 */
	void copy(final IHUPackingAware to, final IHUPackingAware from);

	/**
	 * Checks if given {@link IHUPackingAware} is valid and completely defined.
	 *
	 * @param record
	 * @return true if is valid
	 */
	boolean isValid(IHUPackingAware record);

	/**
	 * Calls {@link #calculateQtyPacks(IHUPackingAware)}. Note: doesn't save.
	 *
	 * @param record
	 */
	void setQtyPacks(IHUPackingAware record);

	/**
	 * Computes the number of TUs required for the given <code>huPackingWare</code>.
	 * 
	 * @param huPackingWare
	 * @return
	 */
	BigDecimal calculateQtyPacks(IHUPackingAware huPackingWare);

	/**
	 * Sets Qty CU.
	 *
	 * @param record
	 * @param qtyPacks aka Qty TUs
	 */
	void setQty(IHUPackingAware record, int qtyPacks);

	boolean isValidQty(IHUPackingAware record);

	/**
	 *
	 * Copy method that supports the choice of overriting the BPartner in the TO model or not Note: in case the bpartner in "to" is null, it will be set from "from" anyway
	 *
	 * @param to
	 * @param from
	 * @param overridePartner if true, the BPartner in "to" will be copied from "from", even i "to" already has a partner set
	 */
	void copy(IHUPackingAware to, IHUPackingAware from, boolean overridePartner);

	/**
	 * This method verifies if the qtyCU given as parameter fits the qtyPacks. If it does, the record will not be updated.
	 * In case the QtyCU is too big or too small to fit in the QtyPacks, it will be changed to the maximum capacity required by the QtyPacks and the M_HU_PI_Item_Product of the record
	 * 
	 * @param record
	 * @param qtyPacks
	 * @param qtyCU
	 */
	void updateQtyIfNeeded(IHUPackingAware record, int qtyPacks, BigDecimal qtyCU);
}
