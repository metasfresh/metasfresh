package de.metas.edi.api.impl;

/*
 * #%L
 * de.metas.edi
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.edi.api.IEDIInputDataSourceBL;
import de.metas.edi.api.IEDIInvoiceCandBL;
import de.metas.edi.model.I_C_BPartner;
import de.metas.edi.model.I_C_Invoice_Candidate;
import de.metas.edi.model.I_C_OLCand;
import de.metas.edi.model.I_C_Order;
import de.metas.edi.model.I_M_InOut;
import de.metas.edi.model.I_M_InOutLine;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;

import java.util.Properties;

public class EDIInvoiceCandBL implements IEDIInvoiceCandBL
{

	private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
	private final IEDIInputDataSourceBL inputDataSourceBL = Services.get(IEDIInputDataSourceBL.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

	@Override
	public void setEdiEnabled(@NonNull final I_C_Invoice_Candidate candidate)
	{
		final boolean isEdiEnabled = computeEdiEnabled(candidate);

		candidate.setIsEdiEnabled(isEdiEnabled);
	}

	private boolean computeEdiEnabled(@NonNull final I_C_Invoice_Candidate candidate)
	{
		// purchase side not relevant
		if (!candidate.isSOTrx())
		{
			return false;
		}

		final AdTableId adTableId = AdTableId.ofRepoIdOrNull(candidate.getAD_Table_ID());
		if (adTableId == null)
		{
			return false;
		}

		final I_C_BPartner ediBPartner = bpartnerDAO.getById(BPartnerId.ofRepoId(candidate.getBill_BPartner_ID()), I_C_BPartner.class);
		final boolean isEDIRecipient = ediBPartner.isEdiInvoicRecipient();
		if (!isEDIRecipient)
		{
			return false;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(candidate);
		final String trxName = InterfaceWrapperHelper.getTrxName(candidate);

		final String tableName = adTableDAO.retrieveTableName(adTableId);

		// case Order
		if (I_C_OrderLine.Table_Name.equals(tableName))
		{
			final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(ctx, candidate.getRecord_ID(), I_C_OrderLine.class, trxName);

			Check.assumeNotNull(orderLine, "Invoice candidate {} has a record ID that points to an invalid order line", candidate);

			final I_C_Order order = InterfaceWrapperHelper.create(orderLine.getC_Order(), de.metas.edi.model.I_C_Order.class);

			return order.isEdiEnabled();
		}

		// case order candidate
		else if (I_C_OLCand.Table_Name.equals(tableName))
		{
			final I_C_OLCand olcand = InterfaceWrapperHelper.create(ctx, candidate.getRecord_ID(), I_C_OLCand.class, trxName);

			Check.assumeNotNull(olcand, "Invoice candidate {} has a record ID that points to an invalid order candidate", candidate);

			final int inputDataSourceId = olcand.getAD_InputDataSource_ID();
			return inputDataSourceBL.isEDIInputDataSource(inputDataSourceId);
		}

		// case inout
		else if (I_M_InOut.Table_Name.equals(tableName))
		{
			final I_M_InOut inout = InterfaceWrapperHelper.create(ctx, candidate.getRecord_ID(), I_M_InOut.class, trxName);

			Check.assumeNotNull(inout, "Invoice candidate {} has a record ID that points to an invalid inout", candidate);

			return inout.isEdiEnabled();
		}

		else if (I_M_InOutLine.Table_Name.equals(tableName))
		{
			final I_M_InOutLine ioline = InterfaceWrapperHelper.create(ctx, candidate.getRecord_ID(), I_M_InOutLine.class, trxName);

			Check.assumeNotNull(ioline, "Invoice candidate {} has a record ID that points to an invalid inout line", candidate);

			final I_M_InOut inout = InterfaceWrapperHelper.create(ioline.getM_InOut(), de.metas.edi.model.I_M_InOut.class);

			Check.assumeNotNull(inout, "Inout Line {}  cannot have M_InOut =  null", ioline);

			return inout.isEdiEnabled();
		}
		// no other case covered yet
		else
		{
			return false;
		}
	}
}
