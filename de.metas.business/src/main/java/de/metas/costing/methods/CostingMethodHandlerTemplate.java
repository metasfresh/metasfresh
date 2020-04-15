package de.metas.costing.methods;

import java.util.Optional;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableSet;

import de.metas.costing.CostAmount;
import de.metas.costing.CostDetail;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailCreateResult;
import de.metas.costing.CostSegment;
import de.metas.costing.CostingDocumentRef;
import de.metas.order.OrderLineId;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

public abstract class CostingMethodHandlerTemplate implements CostingMethodHandler
{
	protected final CostingMethodHandlerUtils utils;

	private static final ImmutableSet<String> HANDLED_TABLE_NAMES = ImmutableSet.<String> builder()
			.add(CostingDocumentRef.TABLE_NAME_M_MatchInv)
			.add(CostingDocumentRef.TABLE_NAME_M_MatchPO)
			.add(CostingDocumentRef.TABLE_NAME_M_InOutLine)
			.add(CostingDocumentRef.TABLE_NAME_M_InventoryLine)
			.add(CostingDocumentRef.TABLE_NAME_M_MovementLine)
			.add(CostingDocumentRef.TABLE_NAME_C_ProjectIssue)
			.build();

	protected CostingMethodHandlerTemplate(@NonNull final CostingMethodHandlerUtils utils)
	{
		this.utils = utils;
	}

	@Override
	public final Set<String> getHandledTableNames()
	{
		return HANDLED_TABLE_NAMES;
	}

	@Override
	public Optional<CostAmount> calculateSeedCosts(final CostSegment costSegment, final OrderLineId orderLineId)
	{
		return Optional.empty();
	}

	@Override
	public final Optional<CostDetailCreateResult> createOrUpdateCost(final CostDetailCreateRequest request)
	{
		final CostDetail existingCostDetail = utils.getExistingCostDetail(request).orElse(null);
		if (existingCostDetail != null)
		{
			return Optional.of(utils.toCostDetailCreateResult(existingCostDetail));
		}
		else
		{
			return Optional.ofNullable(createCostOrNull(request));
		}
	}

	private final CostDetailCreateResult createCostOrNull(final CostDetailCreateRequest request)
	{
		//
		// Create new cost detail
		final CostingDocumentRef documentRef = request.getDocumentRef();
		if (documentRef.isTableName(CostingDocumentRef.TABLE_NAME_M_MatchPO))
		{
			return createCostForMatchPO(request);
		}
		else if (documentRef.isTableName(CostingDocumentRef.TABLE_NAME_M_MatchInv))
		{
			return createCostForMatchInvoice(request);
		}
		else if (documentRef.isTableName(CostingDocumentRef.TABLE_NAME_M_InOutLine))
		{
			final Boolean outboundTrx = documentRef.getOutboundTrx();
			if (outboundTrx)
			{
				return createCostForMaterialShipment(request);
			}
			else
			{
				return createCostForMaterialReceipt(request);
			}
		}
		else if (documentRef.isTableName(CostingDocumentRef.TABLE_NAME_M_MovementLine))
		{
			return createCostForMovementLine(request);
		}
		else if (documentRef.isTableName(CostingDocumentRef.TABLE_NAME_M_InventoryLine))
		{
			return createCostForInventoryLine(request);
		}
		else if (documentRef.isTableName(CostingDocumentRef.TABLE_NAME_C_ProjectIssue))
		{
			return createCostForProjectIssue(request);
		}
		else
		{
			throw new AdempiereException("Unknown documentRef: " + documentRef);
		}
	}

	protected CostDetailCreateResult createCostForMatchPO(final CostDetailCreateRequest request)
	{
		// nothing on this level
		return null;
	}

	protected CostDetailCreateResult createCostForMatchInvoice(final CostDetailCreateRequest request)
	{
		// nothing on this level
		return null;
	}

	protected CostDetailCreateResult createCostForMaterialReceipt(final CostDetailCreateRequest request)
	{
		// nothing on this level
		return null;
	}

	protected CostDetailCreateResult createCostForMaterialShipment(final CostDetailCreateRequest request)
	{
		return createOutboundCostDefaultImpl(request);
	}

	protected CostDetailCreateResult createCostForMovementLine(final CostDetailCreateRequest request)
	{
		return createOutboundCostDefaultImpl(request);
	}

	protected CostDetailCreateResult createCostForInventoryLine(final CostDetailCreateRequest request)
	{
		return createOutboundCostDefaultImpl(request);
	}

	protected CostDetailCreateResult createCostForProjectIssue(final CostDetailCreateRequest request)
	{
		throw new UnsupportedOperationException();
	}

	protected abstract CostDetailCreateResult createOutboundCostDefaultImpl(final CostDetailCreateRequest request);
}
