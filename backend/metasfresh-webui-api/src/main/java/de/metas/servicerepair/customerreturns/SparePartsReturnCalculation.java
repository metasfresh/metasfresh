/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.servicerepair.customerreturns;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.inout.InOutAndLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityUOMConverter;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;
import lombok.Value;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.QtyCalculationsBOM;
import org.eevolution.api.QtyCalculationsBOMLine;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@ToString
public class SparePartsReturnCalculation
{
	@Getter
	private final ImmutableList<FinishedGood> finishedGoods;
	private final ImmutableList<SparePart> spareParts;

	@Builder
	private SparePartsReturnCalculation(
			@Singular @NonNull final ImmutableList<FinishedGood> finishedGoods,
			@Singular @NonNull final ImmutableList<SparePart> spareParts)
	{
		this.finishedGoods = finishedGoods;
		this.spareParts = spareParts;
	}

	public ImmutableSet<ProductId> getAllowedSparePartIds()
	{
		return finishedGoods.stream()
				.flatMap(FinishedGood::streamComponentIds)
				.collect(ImmutableSet.toImmutableSet());
	}

	public Optional<Quantity> computeQtyOfSparePartsRequiredNet(
			@NonNull final ProductId sparePartId,
			@NonNull final QuantityUOMConverter uomConverter)
	{
		final Quantity qtyOfSparePartsRequiredGross = computeQtyOfSparePartsRequiredGross(sparePartId, uomConverter).orElse(null);
		if (qtyOfSparePartsRequiredGross == null)
		{
			return Optional.empty();
		}

		final Quantity qtyOfSparePartsAlreadyReturned = computeQtyOfSparePartsAlreadyReturned(
				sparePartId,
				qtyOfSparePartsRequiredGross.getUOM(),
				uomConverter);

		final Quantity qtyOfSparePartsRequiredNet = qtyOfSparePartsRequiredGross.subtract(qtyOfSparePartsAlreadyReturned);
		return Optional.of(qtyOfSparePartsRequiredNet);
	}

	private Optional<Quantity> computeQtyOfSparePartsRequiredGross(
			@NonNull final ProductId componentId,
			@NonNull final QuantityUOMConverter uomConverter)
	{
		return finishedGoods.stream()
				.map(finishedGood -> finishedGood.computeQtyOfComponentsRequired(componentId, uomConverter))
				.filter(Objects::nonNull)
				.reduce(Quantity::addNullables);
	}

	private Quantity computeQtyOfSparePartsAlreadyReturned(
			@NonNull final ProductId sparePartId,
			@NonNull final I_C_UOM uom,
			@NonNull final QuantityUOMConverter uomConverter)
	{
		Quantity qtyTotal = Quantity.zero(uom);
		final UomId uomId = UomId.ofRepoId(uom.getC_UOM_ID());
		for (final SparePart sparePart : spareParts)
		{
			if (!ProductId.equals(sparePart.getSparePartId(), sparePartId))
			{
				continue;
			}

			final Quantity qty = uomConverter.convertQuantityTo(sparePart.getQty(), sparePartId, uomId);
			qtyTotal = qtyTotal.add(qty);
		}

		return qtyTotal;
	}

	//
	//
	// ------------
	//
	//

	@Value
	@Builder
	public static class FinishedGood
	{
		@NonNull Quantity qty;
		@NonNull QtyCalculationsBOM sparePartsBOM;
		@NonNull InOutAndLineId customerReturnLineId;

		public Stream<ProductId> streamComponentIds()
		{
			return sparePartsBOM.getLines().stream().map(QtyCalculationsBOMLine::getProductId);
		}

		@Nullable
		public Quantity computeQtyOfComponentsRequired(
				@NonNull final ProductId componentId,
				@NonNull final QuantityUOMConverter uomConverter)
		{
			final QtyCalculationsBOMLine bomLine = sparePartsBOM.getLineByComponentId(componentId).orElse(null);
			if (bomLine == null)
			{
				return null;
			}

			final Quantity qtyInBomUOM = uomConverter.convertQuantityTo(qty, bomLine.getBomProductId(), bomLine.getBomProductUOMId());
			return bomLine.computeQtyRequired(qtyInBomUOM);
		}

		public ProductId getProductId()
		{
			return sparePartsBOM.getBomProductId();
		}
	}

	@Value
	@Builder
	public static class SparePart
	{
		@NonNull ProductId sparePartId;
		@NonNull Quantity qty;
		@NonNull InOutAndLineId customerReturnLineId;
	}

}
