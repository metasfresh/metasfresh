/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.product;

import javax.annotation.Nullable;

import org.compiere.model.X_M_Product;

import com.google.common.collect.ImmutableSet;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Product life-cycle status ("BBS-Status" in the UI), backed by the {@code M_Product.ProductLifeCycleStatus}
 * reference list (values O/A/G/N; see {@code X_M_Product.PRODUCTLIFECYCLESTATUS_*}).
 * <p>
 * Encodes, in one place, which {@link ProductLifeCycleAction}s are allowed for a given status:
 * <pre>
 * | Action      | OK | PHASE_OUT | BLOCKED | DO_NOT_DELIVER |
 * |-------------|:--:|:---------:|:-------:|:--------------:|
 * | PURCHASE    | Y  |    N      |    N    |       Y         |
 * | SELL        | Y  |    Y      |    N    |       Y         |
 * | PICK        | Y  |    Y      |    N    |       Y         |
 * | MANUFACTURE | Y  |    Y      |    N    |       Y         |
 * | SHIP        | Y  |    Y      |    N    |       N         |
 * </pre>
 * {@code OK} (and a {@code null} status, via {@link #ofNullableCode(String)}) is fully permissive by design
 * (self-gating; no {@code SysConfig} needed).
 */
@RequiredArgsConstructor
public enum BBSStatus implements ReferenceListAwareEnum
{
	/** Fully allowed. */
	OK(X_M_Product.PRODUCTLIFECYCLESTATUS_OK, ImmutableSet.of()),

	/** Blocks new purchasing only. */
	PHASE_OUT(X_M_Product.PRODUCTLIFECYCLESTATUS_PhaseOut, ImmutableSet.of(ProductLifeCycleAction.PURCHASE)),

	/** Blocks everything. */
	BLOCKED(X_M_Product.PRODUCTLIFECYCLESTATUS_Blocked, ImmutableSet.copyOf(ProductLifeCycleAction.values())),

	/** Blocks shipping only. */
	DO_NOT_DELIVER(X_M_Product.PRODUCTLIFECYCLESTATUS_DeliveryStop, ImmutableSet.of(ProductLifeCycleAction.SHIP)),
	;

	private static final ValuesIndex<BBSStatus> typesByCode = ReferenceListAwareEnums.index(values());

	@Getter @NonNull private final String code;

	@NonNull private final ImmutableSet<ProductLifeCycleAction> blockedActions;

	/**
	 * @return {@code true} if the given action is allowed while the product is in this status.
	 */
	public boolean isAllowed(@NonNull final ProductLifeCycleAction action)
	{
		return !blockedActions.contains(action);
	}

	@Nullable
	public static BBSStatus ofNullableCode(@Nullable final String code)
	{
		return typesByCode.ofNullableCode(code);
	}

	public static BBSStatus ofCode(@NonNull final String code)
	{
		return typesByCode.ofCode(code);
	}

	@Nullable
	public static String toCodeOrNull(@Nullable final BBSStatus status)
	{
		return status != null ? status.getCode() : null;
	}
}
