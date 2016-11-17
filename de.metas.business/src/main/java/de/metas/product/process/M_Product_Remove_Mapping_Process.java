package de.metas.product.process;

import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.process.JavaProcess;
import de.metas.product.model.I_M_Product;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 *         This process removes the M_Product_Mapping reference from the current product.
 *         The rest of the products from other organizations that have the same M_Product_Mapping_ID are not touched.
 */
public class M_Product_Remove_Mapping_Process extends JavaProcess
{

	@Override
	protected String doIt() throws Exception
	{
		// the product will be determined by the record ID which is also the M_Product_Mapped_V_ID of the view M_Product_Mapped_V
		final I_M_Product product = InterfaceWrapperHelper.create(getCtx(), getRecord_ID(), I_M_Product.class, getTrxName());

		if (product == null)
		{
			return "@NotFound@" + "@M_Product_Target_ID@";
		}

		product.setM_Product_Mapping(null);

		InterfaceWrapperHelper.save(product);

		return "@Success@";
	}

}
