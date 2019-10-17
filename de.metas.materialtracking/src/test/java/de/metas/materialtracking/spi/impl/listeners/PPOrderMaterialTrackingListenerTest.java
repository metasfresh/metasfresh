package de.metas.materialtracking.spi.impl.listeners;

import static org.assertj.core.api.Assertions.assertThat;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.model.InterfaceWrapperHelper;
import org.eevolution.model.I_PP_Order;
import de.metas.materialtracking.impl.MaterialTrackingPPOrderBL_IsQualityInspectionOrder_Test;
import de.metas.materialtracking.model.I_M_Material_Tracking_Ref;

/**
 * Tests {@link PPOrderMaterialTrackingListener}, checks:
 * <ul>
 * <li>if {@link I_M_Material_Tracking_Ref#COLUMN_IsQualityInspectionDoc} is set correctly
 * </ul>
 *
 * @author tsa
 *
 */
public class PPOrderMaterialTrackingListenerTest extends MaterialTrackingPPOrderBL_IsQualityInspectionOrder_Test
{
	@Override
	protected void testIsQualityInspectionOrder(final boolean isQualityInspectionOrderExpected, final I_PP_Order ppOrder)
	{
		super.testIsQualityInspectionOrder(isQualityInspectionOrderExpected, ppOrder);

		test_IsQualityInspectionDoc(isQualityInspectionOrderExpected, ppOrder);
	}

	/**
	 * Tests {@link PPOrderMaterialTrackingListener#setIsQualityInspectionDoc(I_M_Material_Tracking_Ref, I_PP_Order)}.
	 */
	private void test_IsQualityInspectionDoc(final boolean isQualityInspectionDocExpected, final I_PP_Order ppOrder)
	{
		final I_M_Material_Tracking_Ref materialTrackingRef = createM_Material_Tracking_Ref();

		PPOrderMaterialTrackingListener.instance.setIsQualityInspectionDoc(materialTrackingRef, ppOrder);

		assertThat(materialTrackingRef.isQualityInspectionDoc())
				.as("Invalid M_Material_Tracking_Ref.isQualityInspectionDoc() result")
				.isEqualTo(isQualityInspectionDocExpected);
	}

	private I_M_Material_Tracking_Ref createM_Material_Tracking_Ref()
	{
		I_M_Material_Tracking_Ref materialTrackingRef = InterfaceWrapperHelper.newInstance(I_M_Material_Tracking_Ref.class, context);
		return materialTrackingRef;
	}
}
