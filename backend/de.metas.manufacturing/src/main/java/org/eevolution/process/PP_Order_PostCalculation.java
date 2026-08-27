package org.eevolution.process;

/*
 * #%L
 * de.metas.manufacturing
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

import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.CostingMethod;
import de.metas.costing.methods.PPOrderCostDifferenceDistributor;
import de.metas.document.engine.DocStatus;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.compiere.SpringContextHolder;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;

import javax.annotation.Nullable;

/**
 * Discharges the WIP cost residual of a manufacturing order, offered only while it is completed and
 * not yet closed.
 */
public class PP_Order_PostCalculation extends JavaProcess implements IProcessPrecondition
{
	@NonNull private final PPOrderCostDifferenceDistributor costDifferenceDistributor = SpringContextHolder.instance.getBean(PPOrderCostDifferenceDistributor.class);
	@NonNull private final IAcctSchemaDAO acctSchemasRepo = Services.get(IAcctSchemaDAO.class);

	/** Only these accumulate into PP_Order_Cost; without that there is no residual to discharge. */
	private static final ImmutableSet<CostingMethod> COSTING_METHODS_WITH_ORDER_COSTS = ImmutableSet.of(
			CostingMethod.AveragePO,
			CostingMethod.LastPOPrice,
			CostingMethod.MovingAverageInvoice);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		final I_PP_Order ppOrder = context.getSelectedModel(I_PP_Order.class);
		return ProcessPreconditionsResolution.acceptIf(isEligible(ppOrder));
	}

	private boolean isEligible(@Nullable final I_PP_Order ppOrder)
	{
		if (ppOrder == null)
		{
			return false;
		}

		// Distributing closes the order, so this withdraws the action once the residual is discharged. After a
		// PP_Order_UnClose it is offered again, correctly: a run without further activity finds nothing to discharge.
		// Completed and Closed are distinct statuses, so isCompleted() alone already excludes a closed order.
		final DocStatus docStatus = DocStatus.ofNullableCodeOrUnknown(ppOrder.getDocStatus());
		return docStatus.isCompleted() && hasOrderCosts(ppOrder);
	}

	/**
	 * Standard costing values every issue and receipt at standard and never accumulates into
	 * {@code PP_Order_Cost}, so the residual is always zero there and the action would silently do nothing.
	 * Offer it only for the costing methods that do accumulate.
	 * <p>
	 * Resolved from the accounting schema, not from the product: only a cost element whose costing method
	 * matches the schema's is accountable, and the distributor resolves the residual the same way. Reading a
	 * per-product-category override here would disagree with what actually posts.
	 */
	private boolean hasOrderCosts(@NonNull final I_PP_Order ppOrder)
	{
		final AcctSchema acctSchema = acctSchemasRepo.getByClientAndOrg(
				ClientId.ofRepoId(ppOrder.getAD_Client_ID()),
				OrgId.ofRepoId(ppOrder.getAD_Org_ID()));

		return COSTING_METHODS_WITH_ORDER_COSTS.contains(acctSchema.getCosting().getCostingMethod());
	}

	@Override
	protected String doIt()
	{
		final PPOrderId ppOrderId = getPPOrderId();

		costDifferenceDistributor.distribute(ppOrderId);

		return MSG_OK;
	}

	private PPOrderId getPPOrderId()
	{
		Check.assumeEquals(getTableName(), I_PP_Order.Table_Name, "TableName");
		return PPOrderId.ofRepoId(getRecord_ID());
	}
}
