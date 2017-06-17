package de.metas.invoicecandidate.callout;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryUpdater;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.ui.sideactions.model.ExecutableSideAction;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.GridTab;
import org.compiere.model.IQuery;
import org.compiere.util.Env;

import de.metas.adempiere.form.IClientUI;
import de.metas.i18n.IMsgBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

/**
 * Side Action: set {@link I_C_Invoice_Candidate#COLUMN_ApprovalForInvoicing} to current selected invoice candidates.
 *
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/08602_Rechnungsdispo_UI_%28106621797084%29
 */
/* package */class IC_ApproveForInvoicing_Action extends ExecutableSideAction
{
	// services
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private final transient IClientUI clientUI = Services.get(IClientUI.class);

	// messages
	private static final String MSG_Root = "C_Invoice_Candidate.SideActions.ApproveForInvoicing.";
	private static final String MSG_DisplayName = MSG_Root + "title";
	private static final String MSG_DoYouWantToUpdate_1P = MSG_Root + "DoYouWantToUpdate";

	// parameters
	private final GridTab gridTab;
	private final int windowNo;
	private final String displayName;

	private boolean running = false;

	public IC_ApproveForInvoicing_Action(final GridTab gridTab)
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
		final IQuery<I_C_Invoice_Candidate> query = retrieveInvoiceCandidatesToApproveQuery()
				.create();

		//
		// Fail if there is nothing to update
		final int countToUpdate = query.count();
		if (countToUpdate <= 0)
		{
			throw new AdempiereException("@NoSelection@");
		}

		//
		// Ask user if we shall updated
		final boolean doUpdate = clientUI.ask()
				.setParentWindowNo(windowNo)
				.setAdditionalMessage(msgBL.getMsg(Env.getCtx(), MSG_DoYouWantToUpdate_1P, new Object[] { countToUpdate }))
				.setDefaultAnswer(false)
				.getAnswer();
		if (!doUpdate)
		{
			return;
		}

		//
		// Update selected invoice candidates and mark them as approved for invoicing
		final int countUpdated = query.update(new IQueryUpdater<I_C_Invoice_Candidate>()
		{
			@Override
			public boolean update(final I_C_Invoice_Candidate ic)
			{
				ic.setApprovalForInvoicing(true);
				return MODEL_UPDATED;
			}
		});

		//
		// Refresh rows, because they were updated
		if (countUpdated > 0)
		{
			gridTab.dataRefreshAll();
		}

		//
		// Inform the user
		clientUI.info(windowNo,
				"Updated", // AD_Message/title
				"#" + countUpdated // message
		);
	}

	private final IQueryBuilder<I_C_Invoice_Candidate> retrieveInvoiceCandidatesToApproveQuery()
	{
		return gridTab.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addNotEqualsFilter(I_C_Invoice_Candidate.COLUMN_Processed, true) // not processed
				.addNotEqualsFilter(I_C_Invoice_Candidate.COLUMN_ApprovalForInvoicing, true) // not already approved
		;
	}
}
