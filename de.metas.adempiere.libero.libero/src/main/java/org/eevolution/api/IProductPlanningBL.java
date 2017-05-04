package org.eevolution.api;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.eevolution.model.I_PP_Product_Planning;

public interface IProductPlanningBL extends ISingletonService
{

	I_PP_Product_Planning createPlainProductPlanning(final I_PP_Product_Planning productPlanning);

	I_PP_Product_Planning createPlainProductPlanning(Properties ctx);

	/**
	 * Gets the actual PP_Product_Planning_ID that was used to create given product planning.
	 * 
	 * @param productPlanning
	 * @return actual PP_Product_Planning_ID or <code>-1</code>
	 */
	int getBase_Product_Planning_ID(I_PP_Product_Planning productPlanning);

}
