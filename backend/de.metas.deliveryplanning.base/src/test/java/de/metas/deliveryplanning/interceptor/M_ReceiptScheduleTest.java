/*
 * #%L
 * de.metas.deliveryplanning.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.deliveryplanning.interceptor;

import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_OrderLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * A packaging-material order line must not produce a delivery planning.
 */
class M_ReceiptScheduleTest
{
	private DeliveryPlanningService deliveryPlanningService;
	private M_ReceiptSchedule interceptor;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		deliveryPlanningService = Mockito.mock(DeliveryPlanningService.class);
		interceptor = new M_ReceiptSchedule(deliveryPlanningService);
	}

	private I_M_ReceiptSchedule receiptScheduleFor(final boolean packagingMaterial)
	{
		final I_C_OrderLine orderLine = InterfaceWrapperHelper.newInstance(I_C_OrderLine.class);
		orderLine.setIsPackagingMaterial(packagingMaterial);
		InterfaceWrapperHelper.save(orderLine);

		final I_M_ReceiptSchedule sched = InterfaceWrapperHelper.newInstance(I_M_ReceiptSchedule.class);
		sched.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
		InterfaceWrapperHelper.save(sched);
		return sched;
	}

	@Test
	@DisplayName("a packaging-material line is dropped before anything is scheduled")
	void packagingMaterialLine_isDropped()
	{
		interceptor.createDeliveryPlanning(receiptScheduleFor(true));

		verify(deliveryPlanningService, never()).isAutoCreateEnabled(any());
	}

	@Test
	@DisplayName("an ordinary line still reaches the auto-create check")
	void ordinaryLine_reachesAutoCreateCheck()
	{
		interceptor.createDeliveryPlanning(receiptScheduleFor(false));

		verify(deliveryPlanningService).isAutoCreateEnabled(any());
	}
}
