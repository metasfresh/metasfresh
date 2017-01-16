package de.metas.handlingunits;

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


import java.util.Date;

import org.adempiere.uom.api.Quantity;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.impl.HUTransaction;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Trx_Line;

/**
 * Transaction Line Candidate. Use the constructor of {@link HUTransaction} to get instances.
 *
 * Based on this object the actual {@link I_M_HU_Trx_Line}s will be created.
 *
 * @author tsa
 *
 */
public interface IHUTransaction
{
	/**
	 * @return unique transaction ID
	 */
	String getId();

	/**
	 * The document which was demanding this transaction to be produced.
	 *
	 * @return referenced document/line (model)
	 */
	Object getReferencedModel();

	void setReferencedModel(Object referencedModel);

	/**
	 * Gets {@link #getM_HU_Item()}'s handling unit
	 *
	 * @return affected HU
	 */
	I_M_HU getM_HU();

	/**
	 * Gets affected HU Item.
	 *
	 * If this value is null it means that no HU Item is affected by this transaction but in most of the cases its counter part transaction will affect an HU Item.
	 *
	 * <p>
	 * e.g. Transferring the Qty from a receipt schedule to a handling unit. This can be the transaction which is subtracting the Qty from receipt schedule (so no HU Item is affected) and its
	 * counterpart is the transaction which is adding the Qty to an HU Item.
	 * </p>
	 *
	 * @return affected HU Item
	 */
	I_M_HU_Item getM_HU_Item();

	/**
	 * Gets actual Virtual HU Item to be used for storing the Qty.
	 *
	 * NOTE: this VHU needs to be included in {@link #getM_HU_Item()}.
	 *
	 * @return VHU item or <code>null</code>
	 */
	I_M_HU_Item getVHU_Item();

	/**
	 * Gets actual Virtual HU to be used for storing the Qty
	 *
	 * @return VHU or <code>null</code>
	 */
	I_M_HU getVHU();

	/**
	 * Gets transaction product
	 *
	 * i.e. the product which was transfered.
	 *
	 * @return transaction product.
	 */
	I_M_Product getProduct();

	/**
	 * Gets transaction Qty/UOM. It's value is absolute and it means:
	 * <ul>
	 * <li>negative: outbound qty
	 * <li>positive: inbound qty
	 * </ul>
	 *
	 * @return absolute Qty/UOM
	 */
	Quantity getQuantity();

	/**
	 * Gets counterpart transaction.
	 *
	 * NOTE: each transaction shall have a counterpart. i.e. when qty was subtracted from one place, there shall be another place where the Qty was added.
	 *
	 * @return counterpart transaction
	 */
	IHUTransaction getCounterpart();

	/**
	 * Cross link this transaction with given transaction by cross setting their {@link #getCounterpart()} properties.
	 *
	 * NOTE: DON'T call it directly. It will be called by API
	 *
	 * @param counterpartTrx
	 */
	void pair(IHUTransaction counterpartTrx);

	/**
	 * @return transaction date
	 */
	Date getDate();

	/**
	 * @return locator which shall be used in the HU Trx
	 */
	I_M_Locator getM_Locator();

	/**
	 * @return HU status used in the HU Trx
	 */
	String getHUStatus();

	/**
	 * Force trxLine to be considered processed and never be actually processed.
	 *
	 * i.e. don't change HU's storage
	 */
	void setSkipProcessing();

	/**
	 * @return true if trxLine is considered to be processed, but never actually processed
	 */
	boolean isSkipProcessing();
}
