/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.contracts.callorder.interceptor;

import com.google.common.collect.ImmutableList;
import de.metas.contracts.callorder.summary.CallOrderSummaryService;
import de.metas.contracts.callorder.summary.model.CallOrderSummary;
import de.metas.contracts.callorder.summary.model.CallOrderSummaryId;
import de.metas.contracts.model.I_C_CallOrderDetail;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_CallOrderDetail.class)
@Component
public class C_CallOrderDetail
{
	private final IUOMConversionBL conversionBL = Services.get(IUOMConversionBL.class);

	private final CallOrderSummaryService summaryService;

	public C_CallOrderDetail(@NonNull final CallOrderSummaryService summaryService)
	{
		this.summaryService = summaryService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_NEW },
			ifColumnsChanged = { I_C_CallOrderDetail.COLUMNNAME_QtyDeliveredInUOM })
	public void syncQtyDeliveredChanged(@NonNull final I_C_CallOrderDetail callOrderDetail)
	{
		final CallOrderSummaryId callOrderSummaryId = CallOrderSummaryId.ofRepoId(callOrderDetail.getC_CallOrderSummary_ID());

		final Quantity qtyDelivered = getQtyDelivered(callOrderDetail);

		if (InterfaceWrapperHelper.isNew(callOrderDetail))
		{
			summaryService.addQtyDelivered(callOrderSummaryId, qtyDelivered);
			return;
		}

		final CallOrderSummary callOrderSummary = summaryService.getById(callOrderSummaryId);

		final I_C_CallOrderDetail oldRecord = InterfaceWrapperHelper.createOld(callOrderDetail, I_C_CallOrderDetail.class);

		final Quantity oldQtyDelivered = getQtyDelivered(oldRecord);

		final Quantity deltaQtyDelivered = conversionBL.computeSum(
				UOMConversionContext.of(callOrderSummary.getSummaryData().getProductId()),
				ImmutableList.of(oldQtyDelivered.negate(), qtyDelivered),
				qtyDelivered.getUomId());

		summaryService.addQtyDelivered(callOrderSummaryId, deltaQtyDelivered);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_NEW },
			ifColumnsChanged = { I_C_CallOrderDetail.COLUMNNAME_QtyInvoicedInUOM })
	public void syncQtyInvoicedChanged(@NonNull final I_C_CallOrderDetail callOrderDetail)
	{
		final CallOrderSummaryId callOrderSummaryId = CallOrderSummaryId.ofRepoId(callOrderDetail.getC_CallOrderSummary_ID());

		final Quantity qtyInvoiced = getQtyInvoiced(callOrderDetail);

		if (InterfaceWrapperHelper.isNew(callOrderDetail))
		{
			summaryService.addQtyInvoiced(callOrderSummaryId, qtyInvoiced);
			return;
		}

		final CallOrderSummary callOrderSummary = summaryService.getById(callOrderSummaryId);

		final I_C_CallOrderDetail oldRecord = InterfaceWrapperHelper.createOld(callOrderDetail, I_C_CallOrderDetail.class);

		final Quantity oldQtyInvoiced = getQtyInvoiced(oldRecord);

		final Quantity deltaQtyInvoiced = conversionBL.computeSum(
				UOMConversionContext.of(callOrderSummary.getSummaryData().getProductId()),
				ImmutableList.of(oldQtyInvoiced.negate(), qtyInvoiced),
				qtyInvoiced.getUomId());

		summaryService.addQtyInvoiced(callOrderSummaryId, deltaQtyInvoiced);
	}

	@NonNull
	private static Quantity getQtyDelivered(@NonNull final I_C_CallOrderDetail callOrderDetail)
	{
		return Quantitys.of(callOrderDetail.getQtyDeliveredInUOM(), UomId.ofRepoId(callOrderDetail.getC_UOM_ID()));
	}

	@NonNull
	private static Quantity getQtyInvoiced(@NonNull final I_C_CallOrderDetail callOrderDetail)
	{
		return Quantitys.of(callOrderDetail.getQtyInvoicedInUOM(), UomId.ofRepoId(callOrderDetail.getC_UOM_ID()));
	}
}
