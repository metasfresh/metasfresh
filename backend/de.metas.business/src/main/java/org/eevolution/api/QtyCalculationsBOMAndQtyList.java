/*
 * #%L
 * de.metas.business
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

package org.eevolution.api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityUOMConverter;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@EqualsAndHashCode
@ToString
public class QtyCalculationsBOMAndQtyList
{
	public static QtyCalculationsBOMAndQtyList of(@NonNull final List<QtyCalculationsBOMAndQty> list)
	{
		return !list.isEmpty() ? new QtyCalculationsBOMAndQtyList(list) : EMPTY;
	}

	public static final QtyCalculationsBOMAndQtyList EMPTY = new QtyCalculationsBOMAndQtyList(ImmutableList.of());

	private final ImmutableList<QtyCalculationsBOMAndQty> list;

	private QtyCalculationsBOMAndQtyList(@NonNull final List<QtyCalculationsBOMAndQty> list)
	{
		this.list = ImmutableList.copyOf(list);
	}

	public ImmutableSet<ProductId> getAllComponentIds()
	{
		return list.stream()
				.flatMap(bomAndQty -> bomAndQty.getBom().getLines().stream())
				.map(QtyCalculationsBOMLine::getProductId)
				.collect(ImmutableSet.toImmutableSet());
	}

	public Optional<Quantity> computeQtyOfComponentsRequired(
			@NonNull final ProductId componentId,
			@NonNull final QuantityUOMConverter uomConverter)
	{
		return list.stream()
				.map(bomAndQty -> bomAndQty.computeQtyOfComponentsRequired(componentId, uomConverter))
				.filter(Objects::nonNull)
				.reduce(Quantity::addNullables);
	}

}
