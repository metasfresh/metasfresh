/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.banking.process;

import com.google.common.collect.ImmutableSet;
import de.metas.banking.model.BankStatementId;
import de.metas.banking.model.I_C_BankStatement;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.document.engine.DocStatus;
import de.metas.i18n.IMsgBL;
import de.metas.payment.PaymentId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.payment_allocation.PaymentsViewFactory;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

public class C_BankStatement_AllocatePayment extends JavaProcess implements IProcessPrecondition
{
	private final static String BANK_STATEMENT_MUST_BE_COMPLETED_MSG = "de.metas.banking.process.C_BankStatementLine_AllocatePayment.Bank_Statement_has_to_be_Completed";

	private final IMsgBL iMsgBL = Services.get(IMsgBL.class);
	private final IViewsRepository viewsFactory = SpringContextHolder.instance.getBean(IViewsRepository.class);
	private final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final I_C_BankStatement selectedBankStatement = context.getSelectedModel(I_C_BankStatement.class);
		final DocStatus docStatus = DocStatus.ofCode(selectedBankStatement.getDocStatus());
		if (!docStatus.isCompleted())
		{
			return ProcessPreconditionsResolution.reject(iMsgBL.getTranslatableMsgText(BANK_STATEMENT_MUST_BE_COMPLETED_MSG));
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final ImmutableSet<PaymentId> paymentIds = retrievePaymentIds();
		if (paymentIds.isEmpty())
		{
			throw new AdempiereException("@NoOpenPayments@")
					.markAsUserValidationError();
		}

		final ViewId viewId = viewsFactory.createView(CreateViewRequest.builder(PaymentsViewFactory.WINDOW_ID)
				.setFilterOnlyIds(PaymentId.toIntSet(paymentIds))
				.build())
				.getViewId();

		getResult().setWebuiViewToOpen(ProcessExecutionResult.WebuiViewToOpen.builder()
				.viewId(viewId.getViewId())
				.target(ProcessExecutionResult.ViewOpenTarget.ModalOverlay)
				.build());

		return MSG_OK;
	}

	private ImmutableSet<PaymentId> retrievePaymentIds()
	{
		final BankStatementId bankStatementId = BankStatementId.ofRepoId(getRecord_ID());

		return bankStatementDAO.getLinesPaymentIds(bankStatementId);
	}
}
