package de.metas.procurement.base.order.callout;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.ui.sideactions.model.ExecutableSideAction;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.GridTab;
import org.compiere.model.IQuery;
import org.compiere.util.Env;

import de.metas.adempiere.form.IClientUI;
import de.metas.lock.api.ILockCommand;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.procurement.base.model.I_PMM_PurchaseCandidate;
import de.metas.procurement.base.order.async.PMM_GenerateOrders;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class PMM_CreatePurchaseOrders_Action extends ExecutableSideAction
{
	// services
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private final transient IClientUI clientUI = Services.get(IClientUI.class);
	private final transient ILockManager lockManager = Services.get(ILockManager.class);

	// messages
	private static final String MSG_Root = "PMM_PurchaseCandidate.SideActions.CreatePurchaseOrders.";
	private static final String MSG_DisplayName = MSG_Root + "title";
	private static final String MSG_DoYouWantToUpdate_1P = MSG_Root + "DoYouWantToUpdate";

	// parameters
	private final GridTab gridTab;
	private final int windowNo;
	private final String displayName;

	private boolean running = false;

	public PMM_CreatePurchaseOrders_Action(final GridTab gridTab)
	{
		super();

		Check.assumeNotNull(gridTab, "gridTab not null");
		this.gridTab = gridTab;
		windowNo = gridTab.getWindowNo();

		displayName = msgBL.translate(Env.getCtx(), MSG_DisplayName);
	}

	@Override
	public String getDisplayName()
	{
		return displayName;
	}

	/**
	 * @return true if this action is currently running
	 */
	public final boolean isRunning()
	{
		return running;
	}

	@Override
	public void execute()
	{
		running = true;
		try
		{
			execute0();
		}
		finally
		{
			running = false;
		}
	}

	private void execute0()
	{
		final IQuery<I_PMM_PurchaseCandidate> query = retrieveRecordsToProcess()
				.create();

		//
		// Fail if there is nothing to update
		final int countToProcess = query.count();
		if (countToProcess <= 0)
		{
			throw new AdempiereException("@NoSelection@");
		}

		//
		// Ask user if we shall process
		final boolean doUpdate = clientUI.ask()
				.setParentWindowNo(windowNo)
				.setAdditionalMessage(msgBL.getMsg(Env.getCtx(), MSG_DoYouWantToUpdate_1P, new Object[] { countToProcess }))
				.setDefaultAnswer(false)
				.getAnswer();
		if (!doUpdate)
		{
			return;
		}

		//
		// Process them
		final int countProcessed = process(query);

		//
		// Refresh rows, because they were updated
		if (countProcessed > 0)
		{
			gridTab.dataRefreshAll();
		}

		//
		// Inform the user
		clientUI.info(windowNo,
				"Updated",  // AD_Message/title
				"#" + countProcessed // message
		);
	}

	private final IQueryBuilder<I_PMM_PurchaseCandidate> retrieveRecordsToProcess()
	{
		return gridTab.createQueryBuilder(I_PMM_PurchaseCandidate.class)
				.addOnlyActiveRecordsFilter()
				.filter(lockManager.getNotLockedFilter(I_PMM_PurchaseCandidate.class))
				.addNotEqualsFilter(I_PMM_PurchaseCandidate.COLUMN_QtyToOrder, BigDecimal.ZERO)
				.orderBy()
				.addColumn(I_PMM_PurchaseCandidate.COLUMNNAME_AD_Org_ID)
				.addColumn(I_PMM_PurchaseCandidate.COLUMNNAME_M_Warehouse_ID)
				.addColumn(I_PMM_PurchaseCandidate.COLUMNNAME_C_BPartner_ID)
				.addColumn(I_PMM_PurchaseCandidate.COLUMNNAME_DatePromised)
				.addColumn(I_PMM_PurchaseCandidate.COLUMNNAME_M_PricingSystem_ID)
				.addColumn(I_PMM_PurchaseCandidate.COLUMNNAME_C_Currency_ID)
				.endOrderBy()
				//
				;
	}

	private final int process(final IQuery<I_PMM_PurchaseCandidate> query)
	{
		final List<I_PMM_PurchaseCandidate> candidates = query.list();
		if (candidates.isEmpty())
		{
			throw new AdempiereException("@NoSelection@");
		}

		final LockOwner lockOwner = LockOwner.newOwner(getClass().getSimpleName());
		final ILockCommand elementsLocker = lockManager.lock()
				.setOwner(lockOwner)
				.setAutoCleanup(false);

		PMM_GenerateOrders.enqueue(Env.getCtx(), elementsLocker, candidates);

		return candidates.size();
	}
}
