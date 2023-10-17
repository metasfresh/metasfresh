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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.util.Optional;

import de.metas.quantity.Quantity;

/**
 * Implementations or adapters of this interface model a record (e.g. C_OrderLine) which supports handling unit packing instructions (e.g. packing item, qty etc).
 *
 * NOTE to developers: if you add/change fields in this interface, make sure you will also change the business logic related methods from {@link IHUPackingAwareBL} (e.g. copying).
 *
 * @author tsa
 *
 */
public interface IHUPackingAware
{
	String COLUMNNAME_M_Product_ID = "M_Product_ID";
	String COLUMNNAME_Qty_CU = "Qty_CU";
	String COLUMNNAME_QtyPacks = "QtyPacks";
	String COLUMNNAME_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	// 05967: The C_BPartner_ID is also needed

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	// And DateOrdered too
	String COLUMNAME_DateOrdered = "DateOrdered";

	int getM_Product_ID();

	void setM_Product_ID(int productId);

	int getM_AttributeSetInstance_ID();

	void setM_AttributeSetInstance_ID(int M_AttributeSetInstance_ID);

	int getC_UOM_ID();

	void setC_UOM_ID(int uomId);

	/** @param qty quantity (aka Qty CU) */
	void setQty(BigDecimal qtyInHUsUOM);

	/** @return quantity (aka Qty CU)
	 * TODO: change to {@link Quantity}, so the UOM is known
	 */
	BigDecimal getQty();

	int getM_HU_PI_Item_Product_ID();

	void setM_HU_PI_Item_Product_ID(final int huPiItemProductId);

	/** @return Qty Packs (aka Qty TU). */
	BigDecimal getQtyTU();

	/** @param qtyPacks Qty Packs (aka Qty TU). */
	void setQtyTU(final BigDecimal qtyPacks);

	void setC_BPartner_ID(int bpartnerId);

	default void setQtyCUsPerTU(BigDecimal qtyCUsPerTU) {}

	default Optional<BigDecimal> getQtyCUsPerTU() { return Optional.empty();}

	int getC_BPartner_ID();

	/**
	 * Checks if this record is in dispute.
	 *
	 * For records which are in dispute:
	 * <ul>
	 * <li>QtyPacks will be set to ZERO
	 * </ul>
	 *
	 * @return true if this record is in dispute
	 */
	boolean isInDispute();

	void setInDispute(boolean inDispute);
}