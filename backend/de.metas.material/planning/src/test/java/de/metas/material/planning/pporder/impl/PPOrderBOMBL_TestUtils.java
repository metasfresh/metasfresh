package de.metas.material.planning.pporder.impl;

import java.sql.Timestamp;

import de.metas.common.util.time.SystemTime;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.I_PP_Product_BOM;

import de.metas.material.event.pporder.PPOrder;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2017 metas GmbH
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
/**
 * Contains code to set stuff needed needed not directly for the tests,
 * but because the tests work with {@link I_PP_Order} etc and the code under tests works with {@link PPOrder} pojos.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@UtilityClass
public class PPOrderBOMBL_TestUtils
{
	public void setCommonValues(I_PP_Order ppOrder)
	{
		final Timestamp t1 = SystemTime.asTimestamp();
		ppOrder.setDateOrdered(t1);
		ppOrder.setDatePromised(TimeUtil.addDays(t1, 2));
		ppOrder.setDateStartSchedule(TimeUtil.addDays(t1, 1));

		if (ppOrder.getPP_Product_BOM_ID() <= 0)
		{
			final I_PP_Product_BOM productBOM = InterfaceWrapperHelper.newInstance(I_PP_Product_BOM.class);
			productBOM.setC_UOM_ID(ppOrder.getC_UOM_ID());
			InterfaceWrapperHelper.save(productBOM);

			ppOrder.setPP_Product_BOM(productBOM);
		}
	}

	public void setCommonValues(I_PP_Order_BOMLine ppOrderBOMLine)
	{
		// final I_PP_Order_BOM ppOrderBOM = InterfaceWrapperHelper.newInstance(I_PP_Order_BOM.class);
		// ppOrderBOM.setPP_Order_ID(ppOrderBOMLine.getPP_Order_ID());
		// InterfaceWrapperHelper.save(ppOrderBOM);
		//
		// ppOrderBOMLine.setPP_Order_BOM(ppOrderBOM);

//		if (ppOrderBOMLine.getPP_Product_BOMLine_ID() <= 0)
//		{
//			final I_PP_Product_BOMLine ppProductBomLine = InterfaceWrapperHelper.newInstance(I_PP_Product_BOMLine.class);
//			ppProductBomLine.setIssueMethod(X_PP_Order_BOMLine.ISSUEMETHOD_Issue);
//			InterfaceWrapperHelper.save(ppProductBomLine);
//			ppOrderBOMLine.setPP_Product_BOMLine(ppProductBomLine);
//		}
//		if (Check.isEmpty(ppOrderBOMLine.getIssueMethod()))
//		{
//			ppOrderBOMLine.setIssueMethod();
//		}

		ppOrderBOMLine.setValidFrom(Timestamp.valueOf("0000-01-01 00:00:00"));
		ppOrderBOMLine.setValidTo(Timestamp.valueOf("9999-12-31 23:59:59"));

	}
}
