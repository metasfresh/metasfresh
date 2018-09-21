package org.adempiere.uom.api;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
import java.util.Properties;

import org.compiere.model.I_C_UOM_Conversion;
import org.compiere.model.I_M_Product;

import de.metas.util.ISingletonService;

public interface IUOMConversionDAO extends ISingletonService
{

	List<I_C_UOM_Conversion> retrieveProductConversions(Properties ctx, I_M_Product product);

	List<I_C_UOM_Conversion> retrieveGenericConversions(Properties ctx);
}
