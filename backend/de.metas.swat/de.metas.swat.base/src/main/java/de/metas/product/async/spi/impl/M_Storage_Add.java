package de.metas.product.async.spi.impl;

/*
 * #%L
 * de.metas.swat.base
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


import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.api.IParams;
import org.compiere.model.MStorage;

import de.metas.async.api.IWorkpackageParamDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.product.IStorageBL;

/**
 * This workpackage processor invokes {@link MStorage#add(java.util.Properties, int, int, int, int, int, java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal, String)}.
 *
 * @author ts
 * @task http://dewiki908/mediawiki/index.php/08999_Lieferdisposition_a.frieden_%28104263801724%29
 */
public class M_Storage_Add extends WorkpackageProcessorAdapter
{
	public static final String WP_PARAM_M_Warehouse_ID = "M_Warehouse_ID";
	public static final String WP_PARAM_M_Locator_ID = "M_Locator_ID";
	public static final String WP_PARAM_M_Product_ID = "M_Product_ID";
	public static final String WP_PARAM_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";
	public static final String WP_PARAM_reservationAttributeSetInstance_ID = "reservationAttributeSetInstance_ID";
	public static final String WP_PARAM_diffQtyOnHand = "diffQtyOnHand";
	public static final String WP_PARAM_diffQtyReserved = "diffQtyReserved";
	public static final String WP_PARAM_diffQtyOrdered = "diffQtyOrdered";

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workpackage, final String localTrxName)
	{
		final IWorkpackageParamDAO workpackageParamDAO = Services.get(IWorkpackageParamDAO.class);

		final IParams params = workpackageParamDAO.retrieveWorkpackageParams(workpackage);

		final boolean success = Services.get(IStorageBL.class).add(InterfaceWrapperHelper.getCtx(workpackage),
				params.getParameterAsInt(WP_PARAM_M_Warehouse_ID),
				params.getParameterAsInt(WP_PARAM_M_Locator_ID),
				params.getParameterAsInt(WP_PARAM_M_Product_ID),
				params.getParameterAsInt(WP_PARAM_M_AttributeSetInstance_ID),
				params.getParameterAsInt(WP_PARAM_reservationAttributeSetInstance_ID),
				params.getParameterAsBigDecimal(WP_PARAM_diffQtyOnHand),
				params.getParameterAsBigDecimal(WP_PARAM_diffQtyReserved),
				params.getParameterAsBigDecimal(WP_PARAM_diffQtyOrdered),
				localTrxName);

		if (!success)
		{
			throw new AdempiereException("MStorage.add() returned false. Please check the workpackage log for details");
		}

		return Result.SUCCESS;
	}

}
