package de.metas.purchasecandidate.process;

import de.metas.document.DocTypeId;
import de.metas.document.engine.DocStatus;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.purchasecandidate.command.CreatePurchaseOrderFromRequisitionCommand;
import de.metas.order.process.C_Order_CreationProcess;
import de.metas.process.Param;
import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryBL;

import com.google.common.collect.ImmutableSet;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.purchasecandidate.PurchaseCandidateId;
import de.metas.purchasecandidate.async.C_PurchaseCandidates_GeneratePurchaseOrders;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.compiere.model.I_C_Order;

import java.sql.Timestamp;

/*
 * #%L
 * de.metas.purchasecandidate.base
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

public class C_PurchaseCandiate_Create_PurchaseOrders
		extends JavaProcess
		implements IProcessPrecondition
{

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(
			@NonNull final IProcessPreconditionsContext context)
	{
		if (!I_C_PurchaseCandidate.Table_Name.equals(context.getTableName()))
		{
			ProcessPreconditionsResolution.reject();
		}

		final boolean containsEligibleRecords = context.getSelectedModels(I_C_PurchaseCandidate.class)
				.stream()
				.filter(I_C_PurchaseCandidate::isPrepared)
				.findAny().isPresent();

		return ProcessPreconditionsResolution.acceptIf(containsEligibleRecords);
	}

	@Override
	protected String doIt() throws Exception
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final ImmutableSet<PurchaseCandidateId> purchaseCandidateIds = queryBL
				.createQueryBuilder(I_C_PurchaseCandidate.class)
				.filter(getProcessInfo().getQueryFilterOrElse(ConstantQueryFilter.of(false)))
				.create()
				.stream()
				.filter(I_C_PurchaseCandidate::isPrepared)
				.map(I_C_PurchaseCandidate::getC_PurchaseCandidate_ID)
				.map(PurchaseCandidateId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
		createPurchaseOrders(purchaseCandidateIds);

		return MSG_OK;
	}

	protected void createPurchaseOrders(final ImmutableSet<PurchaseCandidateId> purchaseCandidateIds)
	{
		C_PurchaseCandidates_GeneratePurchaseOrders.enqueue(purchaseCandidateIds);
	}

}
