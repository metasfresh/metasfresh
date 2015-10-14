package org.eevolution.api;

/*
 * #%L
 * ADempiere ERP - Base
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
import java.util.List;
import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;

public interface IProductBOMDAO extends ISingletonService
{

	List<I_PP_Product_BOMLine> retrieveLines(I_PP_Product_BOM productBOM);

	List<I_PP_Product_BOMLine> retrieveLines(I_PP_Product_BOM productBOM, Date date);

	I_PP_Product_BOM retrieveMakeToOrderProductBOM(Properties ctx, int productId, String trxName);

	int retrieveDefaultBOMId(I_M_Product product);

	I_PP_Product_BOM retrieveDefaultBOM(I_M_Product product);

	I_PP_Product_BOM retrieveBOMById(Properties ctx, int productBomId);

	boolean hasBOMs(I_M_Product product);

	IQuery<I_PP_Product_BOMLine> retrieveBOMLinesForProductQuery(Properties ctx, int productId, String trxName);
}
