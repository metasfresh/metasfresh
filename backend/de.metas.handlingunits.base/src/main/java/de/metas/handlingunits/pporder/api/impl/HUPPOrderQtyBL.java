package de.metas.handlingunits.pporder.api.impl;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.pporder.api.IHUPPOrderQtyBL;
import de.metas.handlingunits.pporder.api.IHUPPOrderQtyDAO;
import de.metas.handlingunits.pporder.api.UpdateDraftReceiptCandidateRequest;
import de.metas.handlingunits.pporder.api.impl.hu_pporder_issue_producer.ReverseDraftIssues;
import de.metas.material.planning.pporder.DraftPPOrderBOMLineQuantities;
import de.metas.material.planning.pporder.DraftPPOrderQuantities;
import de.metas.material.planning.pporder.PPOrderUtil;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order_BOMLine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * #%L
 * de.metas.handlingunits.base
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

public class HUPPOrderQtyBL implements IHUPPOrderQtyBL
{
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IHUPPOrderQtyDAO huPPOrderQtyDAO = Services.get(IHUPPOrderQtyDAO.class);

	@Override
	public void reverseDraftCandidate(final I_PP_Order_Qty candidate)
	{
		final I_PP_Order_BOMLine orderBOMLine = candidate.getPP_Order_BOMLine();
		if (orderBOMLine == null)
		{
			// Main product receipt
			reverseDraftReceipt(candidate);
		}
		else if (PPOrderUtil.isReceipt(orderBOMLine))
		{
			// Co/By Product receipt
			reverseDraftReceipt(candidate);
		}
		else
		{
			// Issue line
			reverseDraftIssue(candidate);
		}
	}

	private void reverseDraftIssue(final I_PP_Order_Qty candidate)
	{
		new ReverseDraftIssues().reverseDraftIssue(candidate);
	}

	private void reverseDraftReceipt(final I_PP_Order_Qty candidate)
	{
		//
		// Get the HU planned to be received and validate it (i.e. make sure it's still planning)
		final I_M_HU huToReceive = candidate.getM_HU();
		if (!X_M_HU.HUSTATUS_Planning.equals(huToReceive.getHUStatus()))
		{
			throw new HUException("Invalid HU Status")
					.setParameter("HUStatus", huToReceive.getHUStatus());
		}

		//
		// Destroy the planned HU
		huTrxBL.createHUContextProcessorExecutor()
				.run(huContext -> {
					handlingUnitsBL.markDestroyed(huContext, huToReceive);
				});

		//
		// Delete the candidate
		huPPOrderQtyDAO.delete(candidate);
	}

	@Override
	public DraftPPOrderQuantities getDraftPPOrderQuantities(@NonNull final PPOrderId ppOrderId)
	{
		Quantity finishedGood_QtyReceived = null;
		final HashMap<PPOrderBOMLineId, DraftPPOrderBOMLineQuantities> linesById = new HashMap<>();

		for (final I_PP_Order_Qty candidate : huPPOrderQtyDAO.retrieveOrderQtys(ppOrderId))
		{
			if (candidate.isProcessed())
			{
				continue;
			}

			final ProductId productId = ProductId.ofRepoId(candidate.getM_Product_ID());
			final Quantity qty = Quantitys.of(candidate.getQty(), UomId.ofRepoId(candidate.getC_UOM_ID()));
			final PPOrderBOMLineId orderBOMLineId = PPOrderBOMLineId.ofRepoIdOrNull(candidate.getPP_Order_BOMLine_ID());

			if (orderBOMLineId == null)
			{
				finishedGood_QtyReceived = finishedGood_QtyReceived != null
						? finishedGood_QtyReceived.add(uomConversionBL.convertQuantityTo(qty, productId, finishedGood_QtyReceived.getUomId()))
						: qty;
			}
			else
			{
				final DraftPPOrderBOMLineQuantities lineToAdd = DraftPPOrderBOMLineQuantities.builder()
						.productId(productId)
						.qtyIssuedOrReceived(qty)
						.build();

				linesById.compute(
						orderBOMLineId,
						(k, existingLine) -> existingLine != null ? existingLine.add(lineToAdd, uomConversionBL) : lineToAdd);
			}
		}

		return DraftPPOrderQuantities.builder()
				.qtyReceived(finishedGood_QtyReceived)
				.bomLineQtys(linesById)
				.build();
	}

	@Override
	public boolean isFinishedGoodsReceipt(@NonNull final I_PP_Order_Qty ppOrderQty)
	{
		return HUPPOrderQtyDAO.isFinishedGoodsReceipt(ppOrderQty);
	}

	@Override
	public void updateDraftReceiptCandidate(@NonNull UpdateDraftReceiptCandidateRequest request)
	{
		final PPOrderId pickingOrderId = request.getPickingOrderId();
		final HuId huId = request.getHuID();
		final Quantity qtyToUpdate = request.getQtyReceived();

		huPPOrderQtyDAO.retrieveOrderQtyForHu(pickingOrderId, huId)
				.filter(candidate -> !candidate.isProcessed() && isFinishedGoodsReceipt(candidate))
				.ifPresent(candidate -> {
					candidate.setQty(qtyToUpdate.toBigDecimal());
					huPPOrderQtyDAO.save(candidate);
				});
	}

	@Override
	public Set<HuId> getFinishedGoodsReceivedHUIds(@NonNull final Set<PPOrderId> ppOrderIds)
	{
		if (ppOrderIds.isEmpty())
		{
			return ImmutableSet.of();
		}
		
		final ImmutableSet.Builder<HuId> result = ImmutableSet.builder();
		for (final PPOrderId ppOrderId : ppOrderIds)
		{
			final Set<HuId> huIds = getFinishedGoodsReceivedHUIds(ppOrderId);
			result.addAll(huIds);
		}
		return result.build();
	}

	@Override
	public Set<HuId> getFinishedGoodsReceivedHUIds(@NonNull final PPOrderId ppOrderId)
	{
		final HashSet<HuId> receivedHUIds = new HashSet<>();
		huPPOrderQtyDAO.retrieveOrderQtyForFinishedGoodsReceive(ppOrderId)
				.stream()
				.filter(I_PP_Order_Qty::isProcessed)
				.forEach(processedCandidate -> {
					final HuId newLUId = HuId.ofRepoIdOrNull(processedCandidate.getNew_LU_ID());
					if (newLUId != null)
					{
						receivedHUIds.add(newLUId);
					}

					final HuId huId = HuId.ofRepoId(processedCandidate.getM_HU_ID());
					receivedHUIds.add(huId);
				});

		return receivedHUIds;
	}

	@Override
	public void setNewLUAndSave(
			@NonNull final List<I_PP_Order_Qty> candidates,
			@NonNull final HuId newLUId)
	{
		candidates.forEach(candidate -> candidate.setNew_LU_ID(newLUId.getRepoId()));
		huPPOrderQtyDAO.saveAll(candidates);
	}

}
