package de.metas.handlingunits.allocation.strategy;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HUItemType;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.IAllocationStrategy;
import de.metas.handlingunits.allocation.impl.AllocationDirection;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.IMutableAllocationResult;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.hutransaction.impl.HUTransactionCandidate;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.storage.IHUItemStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.quantity.Quantity;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

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

public class UniformAllocationStrategy implements IAllocationStrategy
{
	private final AllocationDirection direction;
	private final AllocationStrategySupportingServicesFacade services;

	public UniformAllocationStrategy(
			@NonNull final AllocationDirection direction,
			@NonNull final AllocationStrategySupportingServicesFacade services)
	{
		this.direction = direction;
		this.services = services;
	}

	@Override
	public final IAllocationResult execute(
			@NonNull final I_M_HU hu,
			@NonNull final IAllocationRequest request)
	{
		final IMutableAllocationResult result = AllocationUtils.createMutableAllocationResult(request);
		if (result.isCompleted())
		{
			return result; // happens if the request's qty is zero
		}

		final List<AllocCandidate> candidates = extractAllocCandidates(hu, request);
		updateQtyToAllocate(candidates, request.getQuantity());

		for (final AllocCandidate candidate : candidates)
		{
			final IAllocationResult itemResult = processCandidate(candidate, request);
			AllocationUtils.mergeAllocationResult(result, itemResult);
		}

		return result;
	}

	private List<AllocCandidate> extractAllocCandidates(
			@NonNull final I_M_HU hu,
			@NonNull final IAllocationRequest request)
	{
		final IHUStorageFactory huStorageFactory = request.getHuContext().getHUStorageFactory();

		final ArrayList<AllocCandidate> candidates = new ArrayList<>();
		final List<I_M_HU_Item> huItems = services.retrieveItems(hu);
		for (final I_M_HU_Item item : huItems)
		{
			final HUItemType itemType = services.getItemType(item);

			if (HUItemType.Material.equals(itemType)
					&& services.isVirtual(item))
			{
				final IHUItemStorage huItemStorage = huStorageFactory.getStorage(item);
				final Quantity currentQty = huItemStorage.getQuantity(request.getProductId(), request.getC_UOM());
				candidates.add(AllocCandidate.builder()
						.type(AllocCandidateType.MATERIAL)
						.huItem(item)
						.includedHU(null)
						.currentQty(currentQty)
						.build());
			}
			if (HUItemType.Material.equals(itemType)
					|| HUItemType.HandlingUnit.equals(itemType)
					|| HUItemType.HUAggregate.equals(itemType))
			{
				final List<I_M_HU> includedHUs = services.retrieveIncludedHUs(item);
				for (final I_M_HU includedHU : includedHUs)
				{
					final IHUStorage huStorage = huStorageFactory.getStorage(includedHU);
					final Quantity currentQty = huStorage.getQuantity(request.getProductId(), request.getC_UOM());

					candidates.add(AllocCandidate.builder()
							.type(AllocCandidateType.INCLUDED_HU)
							.huItem(item)
							.includedHU(includedHU)
							.currentQty(currentQty)
							.build());
				}
			}
		}

		return candidates;
	}

	void updateQtyToAllocate(
			@NonNull final List<AllocCandidate> candidates,
			@NonNull final Quantity qtyToAllocateTarget)
	{
		if (candidates.isEmpty())
		{
			throw new AdempiereException("No candidates for " + qtyToAllocateTarget);
		}
		else if (candidates.size() == 1)
		{
			candidates.get(0).setQtyToAllocate(qtyToAllocateTarget);
			return;
		}

		Quantity currentQtyTotal = null;
		final List<AllocCandidate> nonZeroCandidates = new ArrayList<>();
		for (final AllocCandidate candidate : candidates)
		{
			final Quantity currentQty = candidate.getCurrentQty();

			currentQtyTotal = currentQtyTotal != null
					? currentQtyTotal.add(currentQty)
					: currentQty;

			if (currentQty.signum() != 0)
			{
				nonZeroCandidates.add(candidate);
			}
			else
			{
				candidate.setQtyToAllocate(candidate.getCurrentQty().toZero());
			}
		}

		if (nonZeroCandidates.isEmpty())
		{
			throw new HUException("At least one non zero candidate was expected: " + candidates);
		}

		Quantity qtyToAllocateRemaining = qtyToAllocateTarget;
		for (int idx = 0, lastIdx = nonZeroCandidates.size() - 1; idx <= lastIdx; idx++)
		{
			final AllocCandidate candidate = nonZeroCandidates.get(idx);

			// metas-ts: i don't really understand what we do here, but to avoid rounding errors, we need a precision that is big, compared to the quantities' precisions.
			// Otherwise, e.g. with a prcision of just four:
			// currentQtyTotalBD=104 (HU with 104PCE)
			// currentCandidateQtyBD=2 (TU with 2PCE)
			// => percent = 1.9231 (<=rounded up) => qtyToAllocate ends up 3 instead of 2.
			// why don't we take qtyToAllocate := currentCandidateQtyBD directly? IDK.
			final Quantity qtyToAllocate;
			if (idx != lastIdx)
			{
				final BigDecimal currentCandidateQtyBD = candidate.getCurrentQty().toBigDecimal();
				final BigDecimal currentQtyTotalBD = currentQtyTotal.toBigDecimal();
				final int precision = (currentCandidateQtyBD.precision() + currentCandidateQtyBD.precision()) * 20; // try to avoid rounding issues
				final Percent percent = Percent.of(currentCandidateQtyBD, currentQtyTotalBD, precision);

				qtyToAllocate = qtyToAllocateTarget.multiply(percent, RoundingMode.HALF_UP); // with the default UOM-rounding-mode of "UP", we might get 2.00..008 => 3 which was wrong
			}
			else
			{
				qtyToAllocate = qtyToAllocateRemaining;
			}

			candidate.setQtyToAllocate(qtyToAllocate);
			qtyToAllocateRemaining = qtyToAllocateRemaining.subtract(qtyToAllocate);
		}
	}

	private IAllocationResult processCandidate(final AllocCandidate candidate, final IAllocationRequest initialRequest)
	{
		final IAllocationRequest requestActual = AllocationUtils.derive(initialRequest)
				.setQuantity(candidate.getQtyToAllocate())
				.setForceQtyAllocation(true)
				.create();
		if (requestActual.isZeroQty())
		{
			return AllocationUtils.nullResult();
		}

		if (AllocCandidateType.MATERIAL.equals(candidate.getType()))
		{
			return processCandidate_Material(candidate, initialRequest, requestActual);
		}
		else if (AllocCandidateType.INCLUDED_HU.equals(candidate.getType()))
		{
			return execute(candidate.getIncludedHU(), requestActual);
		}
		else
		{
			throw new AdempiereException("Unknown type: " + candidate.getType());
		}
	}

	private IAllocationResult processCandidate_Material(final AllocCandidate candidate, final IAllocationRequest initialRequest, final IAllocationRequest requestActual)
	{
		final I_M_HU_Item vhuItem = candidate.getHuItem();
		final I_M_HU_Item itemFirstNotPureVirtual = services.getFirstNotPureVirtualItem(vhuItem);
		final Quantity qtyTrx = AllocationUtils.getQuantity(requestActual, direction);
		final Object referencedModel = AllocationUtils.getReferencedModel(requestActual);

		final HUTransactionCandidate trx = HUTransactionCandidate.builder()
				.model(referencedModel)
				.huItem(itemFirstNotPureVirtual)
				.vhuItem(vhuItem)
				.productId(requestActual.getProductId())
				.quantity(qtyTrx)
				.date(requestActual.getDate())
				.build();

		final BigDecimal qtyToAllocate = initialRequest.getQty();
		final BigDecimal qtyAllocated = requestActual.getQty();

		return AllocationUtils.createQtyAllocationResult(
				qtyToAllocate,
				qtyAllocated,
				ImmutableList.of(trx), // trxs
				ImmutableList.of()); // attributeTrxs
	}

	private enum AllocCandidateType
	{
		MATERIAL, INCLUDED_HU
	}

	@Data
	@Builder
	private static class AllocCandidate
	{
		@NonNull
		private final AllocCandidateType type;

		@NonNull
		private final I_M_HU_Item huItem;

		@Nullable
		private final I_M_HU includedHU;

		@NonNull
		private final Quantity currentQty;

		private Quantity qtyToAllocate;
	}
}
