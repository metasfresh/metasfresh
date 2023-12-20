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

import de.metas.ad_reference.ADReferenceService;
import de.metas.bpartner.BPartnerId;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import de.metas.vertical.creditscore.base.model.I_CS_Transaction_Result;
import de.metas.vertical.creditscore.base.spi.model.ResultCode;
import de.metas.vertical.creditscore.base.spi.model.TransactionResult;
import de.metas.vertical.creditscore.creditpass.CreditPassConstants;
import de.metas.vertical.creditscore.creditpass.model.extended.I_C_Order;
import de.metas.vertical.creditscore.creditpass.service.CreditPassTransactionService;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.X_C_Order;
import org.compiere.util.Env;

import java.util.List;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.save;

public class CS_Creditpass_TransactionFrom_C_Order extends JavaProcess implements IProcessPrecondition
{
	private final CreditPassTransactionService creditPassTransactionService = SpringContextHolder.instance.getBean(CreditPassTransactionService.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	@Override protected String doIt() throws Exception
	{
		final OrderId orderId = OrderId.ofRepoId(getRecord_ID());
		final I_C_Order order = orderDAO.getById(orderId, de.metas.vertical.creditscore.creditpass.model.extended.I_C_Order.class);
		final BPartnerId bPartnerId = BPartnerId.ofRepoId(order.getC_BPartner_ID());
		final String paymentRule = order.getPaymentRule();

		final List<TransactionResult> transactionResults = creditPassTransactionService.getAndSaveCreditScore(paymentRule, orderId, bPartnerId);

		final TransactionResult transactionResult = transactionResults.stream().findFirst().get();
		if (transactionResult.getResultCodeEffective() == ResultCode.P)
		{
			order.setCreditpassFlag(false);
			final ITranslatableString message = Services.get(IMsgBL.class).getTranslatableMsgText(CreditPassConstants.CREDITPASS_STATUS_SUCCESS_MESSAGE_KEY);
			order.setCreditpassStatus(message.translate(Env.getAD_Language()));
		}
		else
		{
			order.setCreditpassFlag(true);
			final ADReferenceService adReferenceService = ADReferenceService.get();
			final String paymentRuleName = adReferenceService.retrieveListNameTrl(X_C_Order.PAYMENTRULE_AD_Reference_ID, paymentRule);
			final ITranslatableString message = Services.get(IMsgBL.class).getTranslatableMsgText(CreditPassConstants.CREDITPASS_STATUS_FAILURE_MESSAGE_KEY, paymentRuleName);
			order.setCreditpassStatus(message.translate(Env.getAD_Language()));
		}
		save(order);
		final List<Integer> tableRecordReferences = transactionResults.stream()
				.map(tr -> tr.getTransactionResultId().getRepoId())
				.collect(Collectors.toList());
		getResult().setRecordsToOpen(I_CS_Transaction_Result.Table_Name, tableRecordReferences, null);
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

		final OrderId orderId = OrderId.ofRepoId(context.getSingleSelectedRecordId());
		final I_C_Order order = orderDAO.getById(orderId, de.metas.vertical.creditscore.creditpass.model.extended.I_C_Order.class);
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
