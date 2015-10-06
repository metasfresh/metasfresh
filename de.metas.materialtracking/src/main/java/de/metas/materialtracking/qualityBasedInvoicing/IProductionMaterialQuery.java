package de.metas.materialtracking.qualityBasedInvoicing;

/*
 * #%L
 * de.metas.materialtracking
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


import java.util.List;

import org.compiere.model.I_M_Product;
import org.eevolution.model.I_PP_Order;

public interface IProductionMaterialQuery
{

	/**
	 * Types filter. Only return records that have one of the given types.
	 *
	 * @param type
	 * @return
	 */
	IProductionMaterialQuery setTypes(ProductionMaterialType... types);

	List<ProductionMaterialType> getTypes();

	IProductionMaterialQuery setM_Product(I_M_Product product);

	I_M_Product getM_Product();

	IProductionMaterialQuery setPP_Order(I_PP_Order ppOrder);

	I_PP_Order getPP_Order();
}
