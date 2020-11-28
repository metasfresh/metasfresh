package de.metas.handlingunits.document;

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

import org.compiere.model.I_C_UOM;

import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.product.ProductId;

/**
 * @author ad
 *
 */
public interface IHUDocumentLine
{
	// NOTE: we are not adding parent getter on purpose.
	// because we sometimes want to work with a document line without loading the whole document
	// IHUDocument getHUDocument();

	String getDisplayName();

	int getC_BPartner_Location_ID();

	ProductId getProductId();

	BigDecimal getQty();

	I_C_UOM getC_UOM();

	BigDecimal getQtyAllocated();

	/**
	 *
	 * @return i.e. underlying document line (e.g. M_InOutLine, DD_OrderLine etc)
	 */
	Object getTrxReferencedModel();

	IHUDocumentLine getReversal();

	/**
	 * Create Allocation Source having in scope only given HU.
	 *
	 * NOTE: given <code>hu</code> needs to be assigned to this document line.
	 *
	 * @param hu handling unit that is assigned to this document line.
	 * @return allocation source
	 */
	IAllocationSource createAllocationSource(I_M_HU hu);

	boolean isReadOnly();

	void setReadOnly(final boolean readonly);

	I_M_HU_Item getInnerHUItem();

	BigDecimal getQtyToAllocate();

	IHUAllocations getHUAllocations();
}
