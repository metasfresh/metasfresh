package de.metas.procurement.base.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import de.metas.flatrate.model.I_C_Flatrate_Conditions;
import de.metas.procurement.base.IPMM_RfQ_BL;
import de.metas.procurement.base.PMMContractBuilder;
import de.metas.procurement.base.model.I_C_Flatrate_Term;
import de.metas.procurement.base.rfq.model.I_C_RfQ;
import de.metas.procurement.base.rfq.model.I_C_RfQResponseLine;
import de.metas.rfq.IRfqBL;
import de.metas.rfq.IRfqDAO;
import de.metas.rfq.exceptions.RfQDocumentNotClosedException;
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
	public boolean isClosed(final de.metas.rfq.model.I_C_RfQResponseLine rfqResponse)
	{
		return Services.get(IRfqBL.class).isClosed(rfqResponse);
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
		// Make sure it's closed
		final IRfqBL rfqBL = Services.get(IRfqBL.class);
		if (!rfqBL.isClosed(rfqResponse))
		{
			throw new RfQDocumentNotClosedException(rfqResponse);
		}

		final IRfqDAO rfqDAO = Services.get(IRfqDAO.class);

		final I_C_RfQ rfq = InterfaceWrapperHelper.create(rfqResponse.getC_RfQ(), I_C_RfQ.class);
		final I_C_Flatrate_Conditions contractConditions = rfq.getC_Flatrate_Conditions();

		for (final I_C_RfQResponseLine rfqResponseLine : rfqDAO.retrieveResponseLines(rfqResponse, I_C_RfQResponseLine.class))
		{
			if (!rfqResponseLine.isSelectedWinner())
			{
				continue;
			}

			createDraftContract(rfqResponseLine, contractConditions);
		}
	}

	private void createDraftContract(final I_C_RfQResponseLine rfqResponseLine, final I_C_Flatrate_Conditions conditions)
	{
		// Don't create the contract again if there is already one
		if (rfqResponseLine.getC_Flatrate_Term_ID() > 0)
		{
			return;
		}

		final PMMContractBuilder contractBuilder = PMMContractBuilder.newBuilder()
				.setCtx(InterfaceWrapperHelper.getCtx(rfqResponseLine))
				.setFailIfNotCreated(true)
				.setComplete(true)
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

		rfqResponseLine.setC_Flatrate_Term(contract);
		InterfaceWrapperHelper.save(rfqResponseLine, ITrx.TRXNAME_ThreadInherited);
	}

}
