package de.metas.contracts.compensationGroup.contract;

import lombok.NonNull;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.model.I_C_OrderLine;

/*
 * #%L
 * de.metas.contracts
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

/**
 * {@code NOT (IsGroupCompensationLine=Y AND C_Order_CompensationGroup_ID IN (groups with a
 * {@code C_Flatrate_Term_ID}))} — excludes a sales order's contract-created compensation (discount) lines
 * from being copied onto a purchase order.
 * <p>
 * Registered in {@code MainValidator.registerFactories()} via
 * {@code IC_Order_CreatePOFromSOsDAO.addAdditionalOrderLinesFilter}, so it applies to every SO-line-to-PO-line
 * copy path — both the manual {@code C_Order_CreatePOFromSOs} process and the auto-created, auto-completed
 * drop-ship purchase order ({@code DropshipPOFromSOService}).
 * <p>
 * The purchase order doesn't need — and must not carry — the sales order's own contract discount line: the
 * PO's own completion ({@code C_Order_ContractCompensationGroup}) builds its own group from whatever contract
 * matches the PO's own bill partner (the vendor), independently of the SO's contract.
 */
public class ContractCompensationGroupOrderLineFilter implements IQueryFilter<I_C_OrderLine>
{
	private final IQueryFilter<I_C_OrderLine> delegate;

	public ContractCompensationGroupOrderLineFilter(@NonNull final ContractCompensationGroupRepository contractCompensationGroupRepository)
	{
		this.delegate = contractCompensationGroupRepository.createContractCompensationLineMatcher().negate();
	}

	@Override
	public boolean accept(final I_C_OrderLine orderLine)
	{
		return delegate.accept(orderLine);
	}
}
