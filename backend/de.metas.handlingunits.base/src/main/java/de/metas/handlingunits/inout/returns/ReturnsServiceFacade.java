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

package de.metas.handlingunits.inout.returns;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.impl.CopyHUsCommand;
import de.metas.handlingunits.impl.CopyHUsResponse;
import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.handlingunits.inout.returns.customer.CreateCustomerReturnLineReq;
import de.metas.handlingunits.inout.returns.customer.CustomerReturnLineCandidate;
import de.metas.handlingunits.inout.returns.customer.CustomerReturnsWithoutHUsProducer;
import de.metas.handlingunits.inout.returns.customer.MultiCustomerHUReturnsInOutProducer;
import de.metas.handlingunits.inout.returns.customer.MultiCustomerHUReturnsResult;
import de.metas.handlingunits.inout.returns.vendor.MultiVendorHUReturnsInOutProducer;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InOut;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Main class for all material returns related services
 */
@Service
public class ReturnsServiceFacade
{
	private final IHUInOutBL huInOutBL = Services.get(IHUInOutBL.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final CustomerReturnsWithoutHUsProducer customerReturnsWithoutHUsProducer;

	public ReturnsServiceFacade(
			@NonNull final CustomerReturnsWithoutHUsProducer customerReturnsWithoutHUsProducer)
	{
		this.customerReturnsWithoutHUsProducer = customerReturnsWithoutHUsProducer;
	}

	public boolean isCustomerReturn(@NonNull final org.compiere.model.I_M_InOut inout)
	{
		return huInOutBL.isCustomerReturn(inout);
	}

	public boolean isVendorReturn(@NonNull final org.compiere.model.I_M_InOut inout)
	{
		return huInOutBL.isVendorReturn(inout);
	}

	public boolean isEmptiesReturn(@NonNull final org.compiere.model.I_M_InOut inout)
	{
		return huInOutBL.isEmptiesReturn(inout);
	}

	public MultiCustomerHUReturnsResult createCustomerReturnInOutForHUs(final Collection<I_M_HU> shippedHUsToReturn)
	{
		return MultiCustomerHUReturnsInOutProducer.builder()
				.shippedHUsToReturn(shippedHUsToReturn)
				.build()
				.create();
	}

	public void createVendorReturnInOutForHUs(final List<I_M_HU> hus, final Timestamp movementDate)
	{
		MultiVendorHUReturnsInOutProducer.newInstance()
				.setMovementDate(movementDate)
				.addHUsToReturn(hus)
				.create();
	}

	public List<InOutId> createCustomerReturnsFromCandidates(@NonNull final List<CustomerReturnLineCandidate> candidates)
	{
		return customerReturnsWithoutHUsProducer.create(candidates);
	}

	public I_M_InOutLine createCustomerReturnLine(@NonNull final CreateCustomerReturnLineReq request)
	{
		return customerReturnsWithoutHUsProducer.createReturnLine(request);
	}

	public void assignHandlingUnitToHeaderAndLine(
			@NonNull final org.compiere.model.I_M_InOutLine customerReturnLine,
			@NonNull final I_M_HU hu)
	{
		final ImmutableList<I_M_HU> hus = ImmutableList.of(hu);
		assignHandlingUnitsToHeaderAndLine(customerReturnLine, hus);

	}

	public void assignHandlingUnitsToHeaderAndLine(
			@NonNull final org.compiere.model.I_M_InOutLine customerReturnLine,
			@NonNull final List<I_M_HU> hus)
	{
		if (hus.isEmpty())
		{
			return;
		}

		final InOutId customerReturnId = InOutId.ofRepoId(customerReturnLine.getM_InOut_ID());
		final I_M_InOut customerReturn = huInOutBL.getById(customerReturnId, I_M_InOut.class);

		huInOutBL.addAssignedHandlingUnits(customerReturn, hus);
		huInOutBL.setAssignedHandlingUnits(customerReturnLine, hus);
	}

	public void createReturnHandlingUnitsIfNeeded(@NonNull final org.compiere.model.I_M_InOut customerReturn)
	{
		final InOutId originInOutId = InOutId.ofRepoIdOrNull(customerReturn.getReturn_Origin_InOut_ID());
		if (originInOutId == null)
		{
			return;
		}
		if (!huInOutBL.retrieveHandlingUnits(customerReturn).isEmpty())
		{
			return;
		}

		final List<I_M_InOutLine> returnLines = huInOutBL.retrieveLines(customerReturn, I_M_InOutLine.class);
		final Set<InOutLineId> originLineIds = returnLines.stream()
				.map(I_M_InOutLine::getReturn_Origin_InOutLine_ID)
				.map(InOutLineId::ofRepoIdOrNull)
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());

		final Map<InOutLineId, List<I_M_HU>> originHusMap = huInOutBL.retrieveShippedHUsByShipmentLineId(originLineIds);
		if (originHusMap.isEmpty())
		{
			return;
		}

		for (final I_M_InOutLine returnLine : returnLines)
		{
			if (isSkipReturnLine(returnLine))
			{
				continue;
			}

			final InOutLineId originLineId = InOutLineId.ofRepoIdOrNull(returnLine.getReturn_Origin_InOutLine_ID());
			if (originLineId == null)
			{
				continue;
			}

			final List<I_M_HU> originHus = originHusMap.get(originLineId);

			if (originHus.isEmpty())
			{
				continue;
			}

			final ProductId productId = ProductId.ofRepoId(returnLine.getM_Product_ID());
			final UomId productUomId = productBL.getStockUOMId(productId);
			final Quantity qtyToReturn = Quantitys.of(returnLine.getMovementQty(), productUomId);

			final Quantity totalAvailableQty = originHus.stream()
					.filter(hu -> !isMixedHU(hu)) // Skip mixed HUs
					.map(hu -> getHuQty(hu, productId))
					.reduce(Quantity::add)
					.orElse(Quantitys.zero(qtyToReturn.getUomId()));

			if (qtyToReturn.compareTo(totalAvailableQty) > 0)
			{
				throw new AdempiereException("Quantity to return " + qtyToReturn +
						" exceeds available HU quantity (" + totalAvailableQty + ")");
			}

			Quantity qtyRemainingToReturn = qtyToReturn;

			for (final I_M_HU originHU : originHus)
			{
				if (qtyRemainingToReturn.signum() <= 0)
				{
					break;
				}

				final Quantity huQty = getHuQty(originHU, productId);
				if (huQty.signum() <= 0)
				{
					continue;
				}

				final Quantity qtyToCopy = huQty.min(qtyRemainingToReturn);

				final CopyHUsCommand.CopyHUsCommandBuilder copyBuilder = CopyHUsCommand.builder()
						.huIdToCopy(HuId.ofRepoId(originHU.getM_HU_ID()))
						.targetWarehouseId(WarehouseId.ofRepoId(customerReturn.getM_Warehouse_ID()));

				if (qtyToCopy.compareTo(huQty) != 0)
				{
					copyBuilder.qtyCU(qtyToCopy);
					copyBuilder.limitProductId(productId);
				}

				final CopyHUsResponse response = copyBuilder.build().execute();

				final I_M_HU newHU = response.getSingleItem().getNewHU();

				huInOutBL.setAssignedHandlingUnits(returnLine, com.google.common.collect.ImmutableList.of(newHU));

				qtyRemainingToReturn = qtyRemainingToReturn.subtract(qtyToCopy);
			}
		}
	}

	private boolean isMixedHU(final I_M_HU hu)
	{
		final Set<ProductId> productIds = new HashSet<>();
		collectProductIds(hu, productIds);
		return productIds.size() > 1;
	}

	private void collectProductIds(final I_M_HU hu, final Set<ProductId> productIds)
	{
		// 1. Collect from current HU (e.g. CU, or Aggregated TU)
		handlingUnitsBL.getStorageFactory().getStorage(hu).getProductStorages().stream()
				.map(IHUProductStorage::getProductId)
				.forEach(productIds::add);

		if (productIds.size() > 1)
		{
			return;
		}

		// 2. Recurse children
		final List<I_M_HU> children = handlingUnitsBL.retrieveIncludedHUs(hu);
		for (final I_M_HU child : children)
		{
			collectProductIds(child, productIds);
			if (productIds.size() > 1)
			{
				return;
			}
		}
	}

	private boolean isSkipReturnLine(final I_M_InOutLine returnLine)
	{
		return returnLine.isDescription() || returnLine.isPackagingMaterial();
	}

	private Quantity getHuQty(final I_M_HU hu, final ProductId productId)
	{
		return handlingUnitsBL.getStorageFactory().getStorage(hu).getProductStorages().stream()
				.filter(s -> ProductId.equals(s.getProductId(), productId))
				.map(IHUProductStorage::getQty)
				.reduce(Quantity::add)
				.orElseGet(() -> {
					// Fallback if no storage found, return 0 in product UOM
					final UomId productUomId = Services.get(IProductBL.class).getStockUOMId(productId);
					return Quantitys.zero(productUomId);
				});
	}
}
