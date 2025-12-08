/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.handlingunits.inout.returns.customer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.CoalesceUtil;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.hutransaction.IHUTrxListener;
import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.handlingunits.inout.IHUInOutDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Trx_Hdr;
import de.metas.handlingunits.model.I_M_HU_Trx_Line;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.trace.HUTraceEventsService;
import de.metas.handlingunits.trace.HUTraceForReturnedQtyRequest;
import de.metas.handlingunits.trace.interceptor.HUTraceModuleInterceptor;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.IReference;
import org.compiere.model.I_M_InOut;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

final class CreateReturnedHUsTrxListener implements IHUTrxListener
{
	public static final CreateReturnedHUsTrxListener instance = new CreateReturnedHUsTrxListener();

	private final IHUTrxBL trxBL = Services.get(IHUTrxBL.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final HUTraceEventsService huTraceEventService = HUTraceModuleInterceptor.INSTANCE.getHUTraceEventsService();
	private final IHUInOutDAO huInOutDAO = Services.get(IHUInOutDAO.class);

	@Override
	public void afterTrxProcessed(
			@NonNull final IReference<I_M_HU_Trx_Hdr> trxHdrRef,
			@NonNull final List<I_M_HU_Trx_Line> trxLines)
	{
		//
		// Make sure this transaction is about customer returns
		// If not, it means its not the subject of this listener
		final List<I_M_HU_Trx_Line> customerReturnTrxList = trxLines.stream()
				.filter(this::isRelatedToCreatedReturnHUs)
				.collect(ImmutableList.toImmutableList());

		if (customerReturnTrxList.isEmpty())
		{
			return;//nothing to do
		}

		customerReturnTrxList.stream()
				.map(this::buildRequest)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.forEach(huTraceEventService::createAndAddFor);
	}

	private boolean isRelatedToCreatedReturnHUs(@NonNull final I_M_HU_Trx_Line trxLine)
	{
		final IHUTrxBL trxBL = Services.get(IHUTrxBL.class);
		final IHUInOutBL huInOutBL = Services.get(IHUInOutBL.class);

		final I_M_InOutLine inOutLine = trxBL.getReferencedObjectOrNull(trxLine, I_M_InOutLine.class);

		return inOutLine != null
				&& huInOutBL.isCustomerReturn(inOutLine.getM_InOut())
				&& trxLine.getM_HU() != null
				&& inOutLine.getReturn_Origin_InOutLine_ID() > 0
				&& Services.get(IHandlingUnitsBL.class).isVirtual(trxLine.getM_HU());
	}

	private Optional<HUTraceForReturnedQtyRequest> buildRequest(@NonNull final I_M_HU_Trx_Line trxLine)
	{
		final I_M_HU returnedVHU = trxLine.getM_HU();

		final I_M_InOutLine returnLine = trxBL.getReferencedObjectOrNull(trxLine, I_M_InOutLine.class);
		final I_M_InOut returnHeader = returnLine.getM_InOut();

		final InOutLineId shipmentLineId = InOutLineId.ofRepoId(returnLine.getReturn_Origin_InOutLine_ID());

		final Map<InOutLineId, List<I_M_HU>> shippedHUs = huInOutDAO.retrieveShippedHUsByShipmentLineId(ImmutableSet.of(shipmentLineId));

		if (Check.isEmpty(shippedHUs.get(shipmentLineId)))
		{
			return Optional.empty();
		}

		final Set<HuId> shippedTopLevelHuIds = shippedHUs.get(shipmentLineId)
				.stream()
				.map(I_M_HU::getM_HU_ID)
				.map(HuId::ofRepoId)
				.collect(Collectors.toSet());

		final Set<HuId> shippedVHUIds = handlingUnitsBL.getVHUIds(shippedTopLevelHuIds);

		final ProductId productId = ProductId.ofRepoId(trxLine.getM_Product_ID());

		final HuId topLevelReturnedHUId = HuId.ofRepoId(handlingUnitsBL.getTopLevelParent(returnedVHU).getM_HU_ID());

		final UomId uomIdToUse = UomId.ofRepoId(CoalesceUtil.firstGreaterThanZero(trxLine.getC_UOM_ID(),
																				  returnLine.getC_UOM_ID()));
		final HUTraceForReturnedQtyRequest addTraceRequest = HUTraceForReturnedQtyRequest.builder()
				.returnedVirtualHU(returnedVHU)
				.topLevelReturnedHUId(topLevelReturnedHUId)
				.sourceShippedVHUIds(shippedVHUIds)
				.customerReturnId(InOutId.ofRepoId(returnHeader.getM_InOut_ID()))
				.docStatus(returnHeader.getDocStatus())
				.eventTime(Instant.now())
				.orgId(OrgId.ofRepoId(returnLine.getAD_Org_ID()))
				.productId(productId)
				.qty(Quantitys.of(trxLine.getQty(), uomIdToUse))
				.build();

		return Optional.of(addTraceRequest);
	}
}
