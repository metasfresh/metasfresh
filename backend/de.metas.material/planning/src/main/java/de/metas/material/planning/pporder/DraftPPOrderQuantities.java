/*
 * #%L
 * metasfresh-material-planning
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

package de.metas.material.planning.pporder;

import com.google.common.collect.ImmutableMap;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;
import org.eevolution.api.PPOrderBOMLineId;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;

@EqualsAndHashCode
@ToString
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public final class DraftPPOrderQuantities
{
	public static DraftPPOrderQuantities NONE = builder().build();

	@Getter
	private final Optional<Quantity> qtyReceived;
	private final ImmutableMap<PPOrderBOMLineId, DraftPPOrderBOMLineQuantities> bomLineQtysById;

	@Builder
	private DraftPPOrderQuantities(
			@Nullable final Quantity qtyReceived,
			@Nullable @Singular final Map<PPOrderBOMLineId, DraftPPOrderBOMLineQuantities> bomLineQtys)
	{
		this.qtyReceived = Optional.ofNullable(qtyReceived);
		this.bomLineQtysById = bomLineQtys != null ? ImmutableMap.copyOf(bomLineQtys) : ImmutableMap.of();
	}

	public Optional<Quantity> getQtyIssuedOrReceived(@NonNull final PPOrderBOMLineId orderBOMLineId)
	{
		final DraftPPOrderBOMLineQuantities bomLineQtys = bomLineQtysById.get(orderBOMLineId);
		return bomLineQtys != null
				? Optional.of(bomLineQtys.getQtyIssuedOrReceived())
				: Optional.empty();
	}

	public boolean isSomethingReceived()
	{
		return qtyReceived.map(quantity -> quantity.signum() > 0).orElse(false);
	}
}
