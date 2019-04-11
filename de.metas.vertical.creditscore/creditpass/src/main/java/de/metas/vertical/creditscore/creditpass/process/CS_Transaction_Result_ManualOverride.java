/*
 *
 *  * #%L
 *  * %%
 *  * Copyright (C) <current year> metas GmbH
 *  * %%
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as
 *  * published by the Free Software Foundation, either version 2 of the
 *  * License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public
 *  * License along with this program. If not, see
 *  * <http://www.gnu.org/licenses/gpl-2.0.html>.
 *  * #L%
 *
 */

package de.metas.vertical.creditscore.creditpass.process;

import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.process.*;
import de.metas.util.Services;
import de.metas.vertical.creditscore.base.model.I_CS_Transaction_Result;
import de.metas.vertical.creditscore.base.spi.model.ResultCode;
import de.metas.vertical.creditscore.creditpass.CreditPassConstants;
import de.metas.vertical.creditscore.creditpass.model.extended.I_C_Order;
import org.adempiere.ad.service.IADReferenceDAO;
import org.apache.commons.lang3.StringUtils;
import org.compiere.model.X_C_Order;
import org.compiere.util.Env;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.save;

public class CS_Transaction_Result_ManualOverride extends JavaProcess implements IProcessPrecondition
{

	@Param(mandatory = true, parameterName = CreditPassConstants.PROCESS_RESULT_OVERRIDE_PARAM)
	private String resultOverride;

	@Override protected String doIt() throws Exception
	{
		I_CS_Transaction_Result transactionResult = getProcessInfo().getRecord(I_CS_Transaction_Result.class);
		transactionResult.setResponseCodeOverride(resultOverride);
		transactionResult.setResponseCodeEffective(resultOverride);

		final I_C_Order order = load(transactionResult.getC_Order_ID(), I_C_Order.class);
		if (order != null && StringUtils.equals(order.getPaymentRule(), transactionResult.getPaymentRule()))
		{
			if (ResultCode.fromName(resultOverride) == ResultCode.P)
			{
				order.setCreditpassFlag(false);
				final ITranslatableString message = Services.get(IMsgBL.class).getTranslatableMsgText(CreditPassConstants.CREDITPASS_STATUS_SUCCESS_MESSAGE_KEY);
				order.setCreditpassStatus(message.translate(Env.getAD_Language()));
			}
			else
			{
				order.setCreditpassFlag(true);
				final String paymentRuleName = Services.get(IADReferenceDAO.class).retrieveListNameTrl(X_C_Order.PAYMENTRULE_AD_Reference_ID, transactionResult.getPaymentRule());
				final ITranslatableString message = Services.get(IMsgBL.class).getTranslatableMsgText(CreditPassConstants.CREDITPASS_STATUS_FAILURE_MESSAGE_KEY, paymentRuleName);
				order.setCreditpassStatus(message.translate(Env.getAD_Language()));
			}
		}

		save(transactionResult);
		save(order);
		return MSG_OK;
	}

	@Override public ProcessPreconditionsResolution checkPreconditionsApplicable(IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final I_CS_Transaction_Result result = context.getSelectedModel(I_CS_Transaction_Result.class);
		if (ResultCode.fromName(result.getResponseCode()) != ResultCode.M)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Result cannot be overridden because result code is not manual");
		}

		return ProcessPreconditionsResolution.accept();
	}

}
