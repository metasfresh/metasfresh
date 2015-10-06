package de.metas.handlingunits.allocation.builder;

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
import java.util.List;

import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.document.IHUDocumentLine;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item;

/**
 * @author al
 */
public interface IHUSplitBuilder
{
	/**
	 * Execute split
	 *
	 * @return resulting HUs
	 */
	List<I_M_HU> split();

	IHUSplitBuilder setHUToSplit(I_M_HU huToSplit);

	IHUSplitBuilder setDocumentLine(IHUDocumentLine huDocumentLine);

	IHUSplitBuilder setCUProduct(I_M_Product product);

	IHUSplitBuilder setCUQty(BigDecimal qty);

	IHUSplitBuilder setCUUOM(I_C_UOM uom);

	IHUSplitBuilder setCUTrxReferencedModel(Object trxReferencedModel);

	IHUSplitBuilder setCUPerTU(BigDecimal cuPerTU);

	IHUSplitBuilder setTUPerLU(BigDecimal tuPerLU);

	IHUSplitBuilder setMaxLUToAllocate(BigDecimal maxLUToAllocate);

	IHUSplitBuilder setTU_M_HU_PI_Item(I_M_HU_PI_Item tuPIItem);

	IHUSplitBuilder setLU_M_HU_PI_Item(I_M_HU_PI_Item luPIItem);

	IHUSplitBuilder setSplitOnNoPI(boolean splitOnNoPI);
}
