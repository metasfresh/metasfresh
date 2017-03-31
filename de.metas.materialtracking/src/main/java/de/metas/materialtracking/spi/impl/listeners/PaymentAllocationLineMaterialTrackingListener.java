package de.metas.materialtracking.spi.impl.listeners;

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
import org.adempiere.util.Services;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Payment;

import de.metas.materialtracking.IMaterialTrackingBL;
import de.metas.materialtracking.MTLinkRequest;
import de.metas.materialtracking.MaterialTrackingListenerAdapter;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.validator.MaterialTrackableDocumentByASIInterceptor;

/**
 * Links and unlinks payments as {@link I_C_AllocationLine}s are linked and unlinked.
 * <p>
 * Note that the linking of those allocation lines is likely triggered via {@link MaterialTrackableDocumentByASIInterceptor}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class PaymentAllocationLineMaterialTrackingListener extends MaterialTrackingListenerAdapter
{
	public static final transient PaymentAllocationLineMaterialTrackingListener instance = new PaymentAllocationLineMaterialTrackingListener();
	public static final String LISTENER_TableName = I_C_AllocationLine.Table_Name;

	private PaymentAllocationLineMaterialTrackingListener()
	{
		super();
	}

	/**
	 * Link allocation line's Payment to same material tracking
	 */
	@Override
	public void afterModelLinked(final MTLinkRequest request)
	{
		final I_C_AllocationLine allocationLine = InterfaceWrapperHelper.create(request.getModel(), I_C_AllocationLine.class);
		final I_C_Payment payment = allocationLine.getC_Payment();

		if (payment == null || payment.getC_Payment_ID() <= 0)
		{
			return;
		}

		final IMaterialTrackingBL materialTrackingBL = Services.get(IMaterialTrackingBL.class);
		materialTrackingBL.linkModelToMaterialTracking(
				MTLinkRequest.builder()
						.setModel(payment)
						.setMaterialTracking(request.getMaterialTracking())
						.build());
	}

	/**
	 * UnLink allocation line's Payment from given material tracking
	 */
	@Override
	public void afterModelUnlinked(final Object model, final I_M_Material_Tracking materialTrackingOld)
	{
		final I_C_AllocationLine allocationLine = InterfaceWrapperHelper.create(model, I_C_AllocationLine.class);
		final I_C_Payment payment = allocationLine.getC_Payment();
		if (payment == null || payment.getC_Payment_ID() <= 0)
		{
			return;
		}

		final IMaterialTrackingBL materialTrackingBL = Services.get(IMaterialTrackingBL.class);
		materialTrackingBL.unlinkModelFromMaterialTracking(payment, materialTrackingOld);
	}

}
