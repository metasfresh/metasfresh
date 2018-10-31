package de.metas.storage;

/*
 * #%L
 * de.metas.storage
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

import org.adempiere.mm.attributes.api.IAttributeSet;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Locator;

import de.metas.product.ProductId;

public interface IStorageRecord
{
	/** @return An unique identifier of this storage record */
	String getId();

	/** @return summary, user friendly information about this storage line */
	String getSummary();

	ProductId getProductId();

	I_M_Locator getLocator();

	I_C_BPartner getC_BPartner();

	IAttributeSet getAttributes();

	/**
	 * 
	 * @return quantity on hand (in product's stocking UOM)
	 */
	BigDecimal getQtyOnHand();
}
