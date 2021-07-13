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

package de.metas.handlingunits.material.interceptor.transactionevent;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimaps;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.IHUAssignmentDAO.HuAssignment;
import de.metas.inout.InOutAndLineId;
import de.metas.material.event.commons.HUDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MovementLine;
import org.eevolution.model.I_PP_Cost_Collector;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Creates {@link HUDescriptor}s for the HUs that are assigned to records via {@link de.metas.handlingunits.model.I_M_HU_Assignment}.
 */
@Component // not calling it service, because right now it's intended to be called only from the M_Transaction model interceptor
public class HUDescriptorsFromHUAssignmentService
{
	private final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);

	private HUDescriptorService huDescriptorService;

	public HUDescriptorsFromHUAssignmentService(@NonNull final HUDescriptorService huDescriptorService)
	{
		this.huDescriptorService = huDescriptorService;
	}

	public ImmutableList<HUDescriptor> createHuDescriptorsForInOutLine(
			@NonNull final InOutAndLineId inOutLineId,
			final boolean deleted)
	{
		final TableRecordReference inOutLineRef = TableRecordReference.of(I_M_InOutLine.Table_Name, inOutLineId.getInOutLineId());
		return createHUDescriptorsUsingHuAssignments(inOutLineRef, deleted);
	}

	public ImmutableList<HUDescriptor> createHuDescriptorsForCostCollector(
			@NonNull final I_PP_Cost_Collector costCollector,
			final boolean deleted)
	{
		return createHUDescriptorsUsingHuAssignments(costCollector, deleted);
	}

	public ImmutableList<HUDescriptor> createHuDescriptorsForMovementLine(
			@NonNull final I_M_MovementLine movementLine,
			final boolean deleted)
	{
		return createHUDescriptorsUsingHuAssignments(movementLine, deleted);
	}

	private ImmutableList<HUDescriptor> createHUDescriptorsUsingHuAssignments(
			@NonNull final Object huReferencedModel,
			final boolean deleted)
	{
		final List<HuAssignment> huAssignments = huAssignmentDAO.retrieveLowLevelHUAssignmentsForModel(huReferencedModel);

		final ImmutableList.Builder<HUDescriptor> result = ImmutableList.builder();
		for (final HuAssignment huAssignment : huAssignments)
		{
			result.addAll(huDescriptorService.createHuDescriptors(huAssignment.getLowestLevelHU(), deleted));
		}

		return result.build();
	}

	@VisibleForTesting
	@Builder(builderMethodName = "newMaterialDescriptors", builderClassName = "_MaterialDescriptorsBuilder")
	private Map<MaterialDescriptor, Collection<HUDescriptor>> createMaterialDescriptors(
			@NonNull final TransactionDescriptor transaction,
			@Nullable final BPartnerId customerId,
			@Nullable final BPartnerId vendorId,
			@NonNull final Collection<HUDescriptor> huDescriptors)
	{
		// aggregate HUDescriptors based on their product & attributes
		final ImmutableListMultimap<ProductDescriptor, HUDescriptor> //
				huDescriptorsByProduct = Multimaps.index(huDescriptors, HUDescriptor::getProductDescriptor);

		final ImmutableMap.Builder<MaterialDescriptor, Collection<HUDescriptor>> result = ImmutableMap.builder();

		for (final Map.Entry<ProductDescriptor, Collection<HUDescriptor>> entry : huDescriptorsByProduct.asMap().entrySet())
		{
			final ProductDescriptor productDescriptor = entry.getKey();
			final Collection<HUDescriptor> huDescriptorsForCurrentProduct = entry.getValue();

			final BigDecimal quantity = huDescriptorsForCurrentProduct
					.stream()
					.map(HUDescriptor::getQuantity)
					.map(qty -> transaction.getMovementQty().signum() >= 0 ? qty : qty.negate()) // set signum according to transaction.movementQty
					.reduce(BigDecimal.ZERO, BigDecimal::add);

			final MaterialDescriptor materialDescriptor = MaterialDescriptor.builder()
					.warehouseId(transaction.getWarehouseId())
					.date(transaction.getTransactionDate())
					.productDescriptor(productDescriptor)
					.customerId(customerId)
					.vendorId(vendorId)
					.quantity(quantity)
					.build();

			result.put(materialDescriptor, huDescriptorsForCurrentProduct);
		}

		return result.build();
	}

}
