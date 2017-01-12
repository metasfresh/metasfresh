package de.metas.handlingunits.allocation.transfer;

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
import java.util.List;

import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.document.IHUDocumentLine;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item;

/**
 * Instances of this interface can be user to perform HU split. A split means that a HU is distributed on two new HUs. The original HU is destroyed in the process.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IHUSplitBuilder
{
	/**
	 * Execute split
	 *
	 * @return resulting HUs
	 */
	List<I_M_HU> split();

	/**
	 *
	 * @param huToSplit the HU we want to off something. Could be a palette from which we want to take 3 IFCOs.
	 * @return
	 */
	IHUSplitBuilder setHUToSplit(I_M_HU huToSplit);

	IHUSplitBuilder setDocumentLine(IHUDocumentLine huDocumentLine);

	/**
	 *
	 * @param product the (endcustomer) product which we want to take from the HU. Not a packaging product, but the actual good.
	 * @return
	 */
	IHUSplitBuilder setCUProduct(I_M_Product product);

	/**
	 *
	 * @param qty the quantity of the product which we want to split.
	 *            If we want to split 3 IFCOs with two of them containing 10 items each and the third IFCO containing 7 items, then this value is 27.
	 * @return
	 */
	IHUSplitBuilder setCUQty(BigDecimal qty);

	/**
	 *
	 * @param uom the UOM of the quantity set by {@link #setCUQty(BigDecimal)}.
	 * @return
	 */
	IHUSplitBuilder setCUUOM(I_C_UOM uom);

	IHUSplitBuilder setCUTrxReferencedModel(Object trxReferencedModel);

	/**
	 *
	 * @param cuPerTU qty of customer units that shall be in the new HU that we split off.
	 * @return
	 */
	IHUSplitBuilder setCUPerTU(BigDecimal cuPerTU);

	/**
	 *
	 * @param tuPerLU qty of trade units that shall be in the new HU.
	 *            If we have a palette with 10 IFCOs and we decide to split 4 IFCOs into a new HU, then this value is 4.
	 * @return
	 */
	IHUSplitBuilder setTUPerLU(BigDecimal tuPerLU);

	IHUSplitBuilder setMaxLUToAllocate(BigDecimal maxLUToAllocate);

	IHUSplitBuilder setTU_M_HU_PI_Item(I_M_HU_PI_Item tuPIItem);

	IHUSplitBuilder setLU_M_HU_PI_Item(I_M_HU_PI_Item luPIItem);

	/**
	 *
	 *
	 * @param splitOnNoPI not 100% sure but i think this needs to be <code>true</code> if we split individual CUs. See {@link IHandlingUnitsDAO#getNo_HU_PI_ID()}.
	 * @return
	 */
	IHUSplitBuilder setSplitOnNoPI(boolean splitOnNoPI);
}
