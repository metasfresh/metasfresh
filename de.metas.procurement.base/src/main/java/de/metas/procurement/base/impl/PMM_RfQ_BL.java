package de.metas.procurement.base.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import de.metas.document.engine.IDocActionBL;
import de.metas.flatrate.api.IFlatrateBL;
import de.metas.flatrate.model.I_C_Flatrate_Conditions;
import de.metas.procurement.base.IPMM_RfQ_BL;
import de.metas.procurement.base.IPMM_RfQ_DAO;
import de.metas.procurement.base.PMMContractBuilder;
import de.metas.procurement.base.model.I_C_Flatrate_Term;
import de.metas.procurement.base.rfq.model.I_C_RfQ;
import de.metas.procurement.base.rfq.model.I_C_RfQResponseLine;
import de.metas.rfq.IRfqBL;
import de.metas.rfq.IRfqDAO;
import de.metas.rfq.exceptions.RfQDocumentNotClosedException;
import de.metas.rfq.exceptions.RfQException;
import de.metas.rfq.model.I_C_RfQResponse;
import de.metas.rfq.model.I_C_RfQResponseLineQty;

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

public class PMM_RfQ_BL implements IPMM_RfQ_BL
{
	private static final String RFQTYPE_Procurement = "P";

	@Override
	public boolean isProcurement(final de.metas.rfq.model.I_C_RfQ rfq)
	{
		final String rfqType = rfq.getRfQType();
		return RFQTYPE_Procurement.equals(rfqType);
	}

	@Override
	public boolean isProcurement(final I_C_RfQResponse rfqResponse)
	{
		return isProcurement(rfqResponse.getC_RfQ());
	}
	
	@Override
	public boolean isDraft(I_C_RfQResponseLine rfqResponseLine)
	{
		return Services.get(IRfqBL.class).isDraft(rfqResponseLine);
	}

	@Override
	public boolean isClosed(final de.metas.rfq.model.I_C_RfQResponseLine rfqResponseLine)
	{
		return Services.get(IRfqBL.class).isClosed(rfqResponseLine);
	}

	@Override
	public boolean isCompletedOrClosed(final de.metas.rfq.model.I_C_RfQResponse rfqResponse)
	{
		final IRfqBL rfqBL = Services.get(IRfqBL.class);
		return rfqBL.isCompleted(rfqResponse) || rfqBL.isClosed(rfqResponse);
	}

	@Override
	public boolean isCompletedOrClosed(final de.metas.rfq.model.I_C_RfQResponseLine rfqResponseLine)
	{
		final IRfqBL rfqBL = Services.get(IRfqBL.class);
		return rfqBL.isCompleted(rfqResponseLine) || rfqBL.isClosed(rfqResponseLine);
	}

	@Override
	public void createDraftContractsForWinners(final de.metas.rfq.model.I_C_RfQ rfq)
	{
		final IRfqDAO rfqDAO = Services.get(IRfqDAO.class);
		for (final I_C_RfQResponse rfqResponse : rfqDAO.retrieveAllResponses(rfq))
		{
			createDraftContractsForSelectedWinners(rfqResponse);
		}
	}

	@Override
	public void createDraftContractsForSelectedWinners(final I_C_RfQResponse rfqResponse)
	{
		// Make sure it's completed or closed
		final IPMM_RfQ_BL pmmRfqBL = Services.get(IPMM_RfQ_BL.class);
		if (!pmmRfqBL.isCompletedOrClosed(rfqResponse))
		{
			throw new RfQDocumentNotClosedException(rfqResponse);
		}

		final IRfqDAO rfqDAO = Services.get(IRfqDAO.class);
		final I_C_RfQ rfq = InterfaceWrapperHelper.create(rfqResponse.getC_RfQ(), I_C_RfQ.class);
		final I_C_Flatrate_Conditions contractConditions = rfq.getC_Flatrate_Conditions();

		//
		// Iterate RfQ response lines and create a contract for those which are selected as a winner
		for (final I_C_RfQResponseLine rfqResponseLine : rfqDAO.retrieveResponseLines(rfqResponse, I_C_RfQResponseLine.class))
		{
			if (rfqResponseLine.isSelectedWinner())
			{
				createDraftContract(rfqResponseLine, contractConditions);
			}
			else
			{
				voidContract(rfqResponseLine);
			}

		}
	}

	private void createDraftContract(final I_C_RfQResponseLine rfqResponseLine, final I_C_Flatrate_Conditions conditions)
	{
		//
		// Check if there is already a contract
		final I_C_Flatrate_Term contractExisting = rfqResponseLine.getC_Flatrate_Term();
		if (contractExisting != null)
		{
			final IDocActionBL docActionBL = Services.get(IDocActionBL.class);
			// Voided/Reversed contract
			if (docActionBL.isStatusReversedOrVoided(contractExisting))
			{
				// => get rid of it, we will create a new one
				rfqResponseLine.setC_Flatrate_Term(null);

			}
			// Draft/InProgress contract
			else if (docActionBL.isStatusDraftedOrInProgress(contractExisting))
			{
				// => consider the contract was already created and do nothing
				return;
			}
			// Completed/Closed contract
			else if (docActionBL.isStatusCompletedOrClosed(contractExisting))
			{
				// => consider the contract was already created and do nothing
				return;
			}
			else
			{
				throw new RfQException("@Invalid@ @C_Flatrate_Term_ID@ @DocStatus@: " + contractExisting.getDocStatus());
			}
		}

		//
		// Create a new contract
		final PMMContractBuilder contractBuilder = PMMContractBuilder.newBuilder()
				.setCtx(InterfaceWrapperHelper.getCtx(rfqResponseLine))
				.setFailIfNotCreated(true)
				.setComplete(false)
				.setC_Flatrate_Conditions(conditions)
				.setStartDate(rfqResponseLine.getDateWorkStart())
				.setEndDate(rfqResponseLine.getDateWorkComplete())
				.setC_BPartner(rfqResponseLine.getC_BPartner())
				.setC_Currency(rfqResponseLine.getC_Currency())
				.setPMM_Product(rfqResponseLine.getPMM_Product())
				.setC_UOM(rfqResponseLine.getC_UOM());

		// Price
		contractBuilder.setFlatrateAmtPerUOM(rfqResponseLine.getPrice());

		// Quantities
		final IRfqDAO rfqDAO = Services.get(IRfqDAO.class);
		for (final I_C_RfQResponseLineQty rfqResponseLineQty : rfqDAO.retrieveResponseQtys(rfqResponseLine))
		{
			final Date day = rfqResponseLineQty.getDatePromised();
			final BigDecimal qtyPromised = rfqResponseLineQty.getQtyPromised();
			contractBuilder.addQtyPlanned(day, qtyPromised);
		}

		final I_C_Flatrate_Term contract = contractBuilder.build();

		//
		// Link the new contract to our RfQ response line
		rfqResponseLine.setC_Flatrate_Term(contract);
		InterfaceWrapperHelper.save(rfqResponseLine, ITrx.TRXNAME_ThreadInherited);
	}

	private void voidContract(final I_C_RfQResponseLine rfqResponseLine)
	{
		//
		// Check if there is already a contract
		final I_C_Flatrate_Term contractExisting = rfqResponseLine.getC_Flatrate_Term();
		if (contractExisting == null)
		{
			// no existing contract => nothing to do
			return;
		}

		final IDocActionBL docActionBL = Services.get(IDocActionBL.class);
		// Voided/Reversed contract
		if (docActionBL.isStatusReversedOrVoided(contractExisting))
		{
			// already voided => just unlink it
		}
		// Draft/InProgress contract
		else if (docActionBL.isStatusDraftedOrInProgress(contractExisting))
		{
			Services.get(IFlatrateBL.class).voidIt(contractExisting);
		}
		// Completed contract
		else if (docActionBL.isStatusCompleted(contractExisting))
		{
			Services.get(IFlatrateBL.class).voidIt(contractExisting);
		}
		else
		{
			throw new RfQException("@Invalid@ @C_Flatrate_Term_ID@ @DocStatus@: " + contractExisting.getDocStatus());
		}

		//
		// Unlink the existing contract
		rfqResponseLine.setC_Flatrate_Term(null);
		InterfaceWrapperHelper.save(rfqResponseLine, ITrx.TRXNAME_ThreadInherited);
	}

	@Override
	public void checkCompleteContractsForWinners(final I_C_RfQResponse rfqResponse)
	{
		final IPMM_RfQ_DAO pmmRfqDAO = Services.get(IPMM_RfQ_DAO.class);
		for (final I_C_RfQResponseLine rfqResponseLine : pmmRfqDAO.retrieveResponseLines(rfqResponse))
		{
			checkCompleteContractIfWinner(rfqResponseLine);
		}
	}

	@Override
	public void checkCompleteContractIfWinner(I_C_RfQResponseLine rfqResponseLine)
	{
		if (!rfqResponseLine.isSelectedWinner())
		{
			// TODO: make sure the is no contract
			return;
		}

		final I_C_Flatrate_Term contract = rfqResponseLine.getC_Flatrate_Term();
		if (contract == null)
		{
			throw new AdempiereException("@NotFound@ @C_Flatrate_Term_ID@: " + rfqResponseLine);
		}

		final IDocActionBL docActionBL = Services.get(IDocActionBL.class);
		if (docActionBL.isStatusDraftedOrInProgress(contract))
		{
			final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
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

}
