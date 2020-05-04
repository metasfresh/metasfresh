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


import java.math.BigDecimal;
import java.util.Set;

import de.metas.product.ProductId;

/**
 * Implementations of this interface are handling document lines which are about packing materials
 *
 * @author tsa
 *
 */
public interface IPackingMaterialDocumentLine
{

	/**
	 *
	 * @return packing material product
	 */
	ProductId getProductId();

	/**
	 *
	 * @return how many packing materials there are
	 */
	BigDecimal getQty();

	/**
	 * Add a source document line.
	 *
	 * Its quantity will be used to increase this line's qty.
	 */
	void addSourceOrderLine(IPackingMaterialDocumentLineSource source);

	/**
	 *
	 * @return all source lines which are linked to this packing material line
	 */
	Set<IPackingMaterialDocumentLineSource> getSources();

}
