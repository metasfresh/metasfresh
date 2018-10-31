package de.metas.materialtracking.qualityBasedInvoicing.impl;

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

import java.math.BigDecimal;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;

import de.metas.materialtracking.WaschprobeOrderData;
import de.metas.materialtracking.WaschprobeStandardMasterData;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionOrder;
import de.metas.util.Services;

public class PPOrderQualityCalculatorTest
{
	private WaschprobeStandardMasterData data;
	// private IContextAware context;

	/** Our class under test */
	private PPOrderQualityCalculator calculator = new PPOrderQualityCalculator();

	private I_M_Material_Tracking materialTracking;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager.setThreadInheritedTrxName(trxManager.createTrxName("threadInheritedDummytrx"));

		this.data = new WaschprobeStandardMasterData();
		// this.context = data.getContext();

		this.materialTracking = data.createMaterialTracking();
	}

	@Test
	public void test_QM_QtyDeliveredPercOfRaw()
	{
		//
		// Create QI Order
		final WaschprobeOrderData qiOrderData = new WaschprobeOrderData(data, TimeUtil.getDay(2015, 12, 6))
				.assignToMaterialTracking(materialTracking)
				.setCarrot_Washed_QtyDelivered(new BigDecimal("100"))
				.setCarrot_Unwashed_QtyDelivered(new BigDecimal("200"))
				.setCarrot_Big_QtyDelivered(new BigDecimal("-30")) // co-product Qty
		;
		final IQualityInspectionOrder qiOrder = qiOrderData.createQualityInspectionOrder();

		//
		// Perform calculation of QM_QtyDeliveredPercOfRaw
		calculator.updateQM_QtyDeliveredPercOfRaw(qiOrder);

		//
		// Refresh everything because the calculator changed and saved in another instance
		qiOrderData.refresh();

		//
		// Check QM_QtyDeliveredPercOfRaw
		qiOrderData.assert_Carrot_Washed_QM_QtyDeliveredPercOfRaw(new BigDecimal("50")); // Expect: 100/200%
		qiOrderData.assert_Carrot_Unwashed_QM_QtyDeliveredPercOfRaw(new BigDecimal("100")); // Expect: 100/100% (our raw material)
		qiOrderData.assert_Carrot_Big_QM_QtyDeliveredPercOfRaw(new BigDecimal("15")); // Expect: 30/200%
	}

	@Test
	public void test_QM_QtyDeliveredAvg()
	{
		//
		// Create QI order 1:
		final WaschprobeOrderData qiOrderData01 = new WaschprobeOrderData(data, TimeUtil.getDay(2015, 12, 6))
				.assignToMaterialTracking(materialTracking)
				.setCarrot_Washed_QtyDelivered(new BigDecimal("100"))
				.setCarrot_Unwashed_QtyDelivered(new BigDecimal("200"))
				.setCarrot_Big_QtyDelivered(new BigDecimal("-30")) // co-product Qty
		;
		final IQualityInspectionOrder qiOrder01 = qiOrderData01.createQualityInspectionOrder();

		//
		// Create QI order 2:
		final WaschprobeOrderData qiOrderData02 = new WaschprobeOrderData(data, TimeUtil.getDay(2016, 3, 4))
				.assignToMaterialTracking(materialTracking)
				.setCarrot_Washed_QtyDelivered(new BigDecimal("150"))
				.setCarrot_Unwashed_QtyDelivered(new BigDecimal("200"))
				.setCarrot_Big_QtyDelivered(new BigDecimal("-40")) // co-product Qty
		;
		final IQualityInspectionOrder qiOrder02 = qiOrderData02.createQualityInspectionOrder();

		//
		// Perform calculation of QM_QtyDeliveredAvg for qiOrder01
		{
			final IQualityInspectionOrder qiOrder00 = null; // there is no previous QI order
			calculator.updateQM_QtyDeliveredAvg(qiOrder01, qiOrder00);
			qiOrderData01.refresh();

			//
			// Check QM_QtyDeliveredAvg for qiOrder01
			// => i.e. QtyDelivered shall be copied to QM_QtyDeliveredAvg
			qiOrderData01.assert_Carrot_Washed_QM_QtyDeliveredAvg(new BigDecimal("100"));
			qiOrderData01.assert_Carrot_Unwashed_QM_QtyDeliveredAvg(new BigDecimal("200"));
			qiOrderData01.assert_Carrot_Big_QM_QtyDeliveredAvg(new BigDecimal("30"));
		}

		//
		// Perform calculation of QM_QtyDeliveredAvg for qiOrder02
		{
			calculator.updateQM_QtyDeliveredAvg(qiOrder02, qiOrder01);
			qiOrderData02.refresh();

			//
			// Check QM_QtyDeliveredAvg for qiOrder02
			qiOrderData02.assert_Carrot_Washed_QM_QtyDeliveredAvg(new BigDecimal("125")); // Expect: (100+150) / 2
			qiOrderData02.assert_Carrot_Unwashed_QM_QtyDeliveredAvg(new BigDecimal("200")); // Expect: (200+200) / 2
			qiOrderData02.assert_Carrot_Big_QM_QtyDeliveredAvg(new BigDecimal("35")); // Expect: (30 + 40) / 2
		}
	}
}
