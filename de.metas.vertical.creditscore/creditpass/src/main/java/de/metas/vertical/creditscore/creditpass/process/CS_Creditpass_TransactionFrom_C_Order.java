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
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.order.OrderId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import de.metas.vertical.creditscore.base.model.I_CS_Transaction_Result;
import de.metas.vertical.creditscore.base.spi.model.TransactionResult;
import de.metas.vertical.creditscore.creditpass.CreditPassConstants;
import de.metas.vertical.creditscore.creditpass.model.extended.I_C_Order;
import de.metas.vertical.creditscore.creditpass.service.CreditPassTransactionService;
import org.adempiere.ad.service.IADReferenceDAO;
import org.apache.commons.lang3.StringUtils;
import org.compiere.Adempiere;
import org.compiere.model.X_C_Order;
import org.compiere.util.Env;

import java.util.Arrays;
import java.util.Collections;

import static org.adempiere.model.InterfaceWrapperHelper.save;

public class CS_Creditpass_TransactionFrom_C_Order extends JavaProcess implements IProcessPrecondition
{
	private final CreditPassTransactionService creditPassTransactionService = Adempiere.getBean(CreditPassTransactionService.class);

	@Override protected String doIt() throws Exception
	{
		final I_C_Order order = getRecord(I_C_Order.class);
		final BPartnerId bPartnerId = BPartnerId.ofRepoId(order.getC_BPartner_ID());
		final OrderId orderId = OrderId.ofRepoId(order.getC_Order_ID());
		final String paymentRule = order.getPaymentRule();

		TransactionResult transactionResult = creditPassTransactionService.getAndSaveCreditScore(paymentRule, orderId, bPartnerId);

		if (transactionResult.getResultCode() == CreditPassConstants.REQUEST_SUCCESS_CODE)
		{
			order.setCreditpassFlag(false);
			final ITranslatableString message = Services.get(IMsgBL.class).getTranslatableMsgText(CreditPassConstants.CREDITPASS_STATUS_SUCCESS_MESSAGE_KEY);
			order.setCreditpassStatus(message.translate(Env.getAD_Language()));
		}
		else
		{
			order.setCreditpassFlag(true);
			//TODO check if one payment rule is enough
			String paymentRuleName = Services.get(IADReferenceDAO.class).retrieveListNameTrl(X_C_Order.PAYMENTRULE_AD_Reference_ID, paymentRule);
			final ITranslatableString message = Services.get(IMsgBL.class).getTranslatableMsgText(CreditPassConstants.CREDITPASS_STATUS_FAILURE_MESSAGE_KEY, paymentRuleName);
			order.setCreditpassStatus(message.translate(Env.getAD_Language()));
		}
		save(order);
		getResult().setRecordsToOpen(I_CS_Transaction_Result.Table_Name, Collections.singletonList(transactionResult.getTransactionResultId().getRepoId()), null);
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

		final I_C_Order order = context.getSelectedModel(I_C_Order.class);
		if (order.getC_BPartner_ID() < 0)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("The order has no business partner");
		}

		if (!order.getCreditpassFlag())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Creditpass request not needed");
		}

		return ProcessPreconditionsResolution.accept();
	}

}
