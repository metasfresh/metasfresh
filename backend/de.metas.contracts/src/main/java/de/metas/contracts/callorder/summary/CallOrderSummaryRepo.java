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

package de.metas.contracts.callorder.summary;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.callorder.summary.model.CallOrderSummary;
import de.metas.contracts.callorder.summary.model.CallOrderSummaryData;
import de.metas.contracts.callorder.summary.model.CallOrderSummaryId;
import de.metas.contracts.model.I_C_CallOrderSummary;
import de.metas.lang.SOTrx;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.save;

@Repository
public class CallOrderSummaryRepo
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public CallOrderSummary getById(@NonNull final CallOrderSummaryId summaryId)
	{
		final I_C_CallOrderSummary record = InterfaceWrapperHelper.load(summaryId, I_C_CallOrderSummary.class);

		return ofRecord(record);
	}

	@NonNull
	public Optional<CallOrderSummary> getByFlatrateTermId(@NonNull final FlatrateTermId flatrateTermId)
	{
		return queryBL.createQueryBuilder(I_C_CallOrderSummary.class)
				.addEqualsFilter(I_C_CallOrderSummary.COLUMNNAME_C_Flatrate_Term_ID, flatrateTermId)
				.create()
				.firstOnlyOptional(I_C_CallOrderSummary.class)
				.map(CallOrderSummaryRepo::ofRecord);
	}

	@NonNull
	public CallOrderSummary create(@NonNull final CallOrderSummaryData summaryData)
	{
		final I_C_CallOrderSummary record = InterfaceWrapperHelper.newInstance(I_C_CallOrderSummary.class);

		setSummaryDataToRecord(record, summaryData);

		save(record);

		return ofRecord(record);
	}

	@NonNull
	public CallOrderSummary update(@NonNull final CallOrderSummary callOrderSummary)
	{
		final I_C_CallOrderSummary record = InterfaceWrapperHelper.load(callOrderSummary.getSummaryId(), I_C_CallOrderSummary.class);

		setSummaryDataToRecord(record, callOrderSummary.getSummaryData());

		save(record);

		return ofRecord(record);
	}

	private static void setSummaryDataToRecord(@NonNull final I_C_CallOrderSummary record, @NonNull final CallOrderSummaryData callOrderSummaryData)
	{
		record.setC_Order_ID(callOrderSummaryData.getOrderId().getRepoId());
		record.setC_OrderLine_ID(callOrderSummaryData.getOrderLineId().getRepoId());

		record.setC_Flatrate_Term_ID(callOrderSummaryData.getFlatrateTermId().getRepoId());
		record.setM_Product_ID(callOrderSummaryData.getProductId().getRepoId());

		record.setC_UOM_ID(callOrderSummaryData.getUomId().getRepoId());
		record.setQtyEntered(callOrderSummaryData.getQtyEntered());
		record.setQtyDeliveredInUOM(callOrderSummaryData.getQtyDelivered());
		record.setQtyInvoicedInUOM(callOrderSummaryData.getQtyInvoiced());

		record.setPOReference(callOrderSummaryData.getPoReference());
		record.setIsSOTrx(callOrderSummaryData.getSoTrx().toBoolean());

		if (callOrderSummaryData.getAttributeSetInstanceId() != null)
		{
			record.setM_AttributeSetInstance_ID(callOrderSummaryData.getAttributeSetInstanceId().getRepoId());
		}
	}

	@NonNull
	private static CallOrderSummary ofRecord(@NonNull final I_C_CallOrderSummary record)
	{
		return CallOrderSummary.builder()
				.summaryId(CallOrderSummaryId.ofRepoId(record.getC_CallOrderSummary_ID()))
				.summaryData(ofRecordData(record))
				.build();
	}

	@NonNull
	private static CallOrderSummaryData ofRecordData(@NonNull final I_C_CallOrderSummary record)
	{
		return CallOrderSummaryData.builder()
				.orderId(OrderId.ofRepoId(record.getC_Order_ID()))
				.orderLineId(OrderLineId.ofRepoId(record.getC_OrderLine_ID()))
				.flatrateTermId(FlatrateTermId.ofRepoId(record.getC_Flatrate_Term_ID()))
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.uomId(UomId.ofRepoId(record.getC_UOM_ID()))
				.qtyEntered(record.getQtyEntered())
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoIdOrNull(record.getM_AttributeSetInstance_ID()))
				.qtyDelivered(record.getQtyDeliveredInUOM())
				.qtyInvoiced(record.getQtyInvoicedInUOM())
				.poReference(record.getPOReference())
				.isActive(record.isActive())
				.soTrx(SOTrx.ofBoolean(record.isSOTrx()))
				.build();
	}
}
