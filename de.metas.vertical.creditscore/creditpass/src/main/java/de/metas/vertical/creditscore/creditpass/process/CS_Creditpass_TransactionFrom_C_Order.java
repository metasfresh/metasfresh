package de.metas.vertical.creditscore.creditpass.process;

/*
 * #%L
 *  de.metas.vertical.creditscore.creditpass.process
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

import de.metas.bpartner.BPartnerId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.vertical.creditscore.creditpass.service.CreditPassTransactionService;
import org.compiere.Adempiere;
import org.compiere.model.I_C_Order;

public class CS_Creditpass_TransactionFrom_C_Order extends JavaProcess implements IProcessPrecondition
{
	final CreditPassTransactionService creditPassTransactionService = Adempiere.getBean(CreditPassTransactionService.class);

	@Override protected String doIt() throws Exception
	{
		I_C_Order order = getRecord(I_C_Order.class);
		BPartnerId bPartnerId = BPartnerId.ofRepoId(order.getC_BPartner_ID());
		String paymentRule = order.getPaymentRule();

		//TODO check config days
		creditPassTransactionService.getAndSaveCreditScore(paymentRule, bPartnerId, getCtx());
		//TODO update fields
		return MSG_OK;
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{

		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		I_C_Order order = getRecord(I_C_Order.class);
		if(order.getC_BPartner_ID() < 0){
			return ProcessPreconditionsResolution.rejectWithInternalReason("The order has no business partner");
		}

		if (creditPassTransactionService.hasConfigForPartnerId(BPartnerId.ofRepoId(order.getC_BPartner_ID())))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Business partner has no associated creditPass config");
		}

		return ProcessPreconditionsResolution.accept();
	}

}
