package de.metas.procurement.base.order.process;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.process.ISvrProcessPrecondition;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.GridTab;
import org.compiere.process.SvrProcess;
import org.compiere.util.TrxRunnableAdapter;

import de.metas.document.engine.IDocActionBL;
import de.metas.flatrate.api.IFlatrateBL;
import de.metas.process.RunOutOfTrx;
import de.metas.procurement.base.IPMM_RfQ_BL;
import de.metas.procurement.base.IServerSyncBL;
import de.metas.procurement.base.IWebuiPush;
import de.metas.procurement.base.impl.SyncObjectsFactory;
import de.metas.procurement.base.model.I_C_Flatrate_Term;
import de.metas.procurement.base.rfq.model.I_C_RfQ;
import de.metas.procurement.base.rfq.model.I_C_RfQResponseLine;
import de.metas.procurement.sync.SyncRfQCloseEvent;
import de.metas.procurement.sync.protocol.SyncProductSuppliesRequest;
import de.metas.rfq.IRfqBL;
import de.metas.rfq.IRfqDAO;
import de.metas.rfq.model.I_C_RfQResponse;

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

public class C_RfQ_PublishResults extends SvrProcess implements ISvrProcessPrecondition
{
	// services
	private final transient IRfqBL rfqBL = Services.get(IRfqBL.class);
	private final transient IRfqDAO rfqDAO = Services.get(IRfqDAO.class);
	private final transient IPMM_RfQ_BL pmmRfqBL = Services.get(IPMM_RfQ_BL.class);
	private final transient IDocActionBL docActionBL = Services.get(IDocActionBL.class);
	private final transient IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final transient IWebuiPush webuiPush = Services.get(IWebuiPush.class);
	private final transient SyncObjectsFactory syncObjectsFactory = SyncObjectsFactory.newFactory();
	private final transient IServerSyncBL serverSyncBL = Services.get(IServerSyncBL.class);

	//
	// State
	final List<SyncRfQCloseEvent> syncRfQCloseEvents = new ArrayList<>();
	final SyncProductSuppliesRequest syncProductSuppliesRequest = new SyncProductSuppliesRequest();

	@Override
	public boolean isPreconditionApplicable(final GridTab gridTab)
	{
		final I_C_RfQ rfq = InterfaceWrapperHelper.create(gridTab, I_C_RfQ.class);

		return rfqBL.isClosed(rfq)
				&& pmmRfqBL.isProcurement(rfq);
	}

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		//
		// Process: validate, complete the contracts
		trxManager.run(new TrxRunnableAdapter()
		{
			@Override
			public void run(final String localTrxName) throws Exception
			{
				processInTrx();
			}
		});

		//
		// Publish everything to webui server (out of trx)
		publishToWebui();

		return MSG_OK;
	}

	private final void processInTrx()
	{
		final I_C_RfQ rfq = getRecord(I_C_RfQ.class);

		for (final I_C_RfQResponse rfqResponse : rfqDAO.retrieveAllResponses(rfq))
		{
			for (final I_C_RfQResponseLine rfqResponseLine : rfqDAO.retrieveResponseLines(rfqResponse, I_C_RfQResponseLine.class))
			{
				checkCompleteContract(rfqResponseLine);

				// Create and collect the RfQ close event
				final SyncRfQCloseEvent syncRfQCloseEvent = syncObjectsFactory.createSyncRfQCloseEvent(rfqResponseLine);
				if (syncRfQCloseEvent != null)
				{
					syncRfQCloseEvents.add(syncRfQCloseEvent);

					syncProductSuppliesRequest.getProductSupplies().addAll(syncRfQCloseEvent.getPlannedSupplies());
				}
			}
		}
	}

	private void checkCompleteContract(final I_C_RfQResponseLine rfqResponseLine)
	{
		if (!rfqResponseLine.isSelectedWinner())
		{
			return;
		}

		final I_C_Flatrate_Term contract = rfqResponseLine.getC_Flatrate_Term();
		if (contract == null)
		{
			throw new AdempiereException("@NotFound@ @C_Flatrate_Term_ID@: " + rfqResponseLine);
		}

		if (docActionBL.isStatusDraftedOrInProgress(contract))
		{
			flatrateBL.complete(contract);
		}
		else if (docActionBL.isStatusCompleted(contract))
		{
			// already completed => nothing to do
		}
		else
		{
			throw new AdempiereException("@Invalid@ @DocStatus@: " + contract);
		}
	}

	private final void publishToWebui()
	{
		trxManager.assertThreadInheritedTrxNotExists();
		
		// Actually push to webui
		webuiPush.pushRfQCloseEvents(syncRfQCloseEvents);

		// Internally push the planned product supplies, to create the PMM_PurchaseCandidates
		serverSyncBL.reportProductSupplies(syncProductSuppliesRequest);

	}
}
