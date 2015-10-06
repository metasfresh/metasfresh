package de.metas.payment.esr.api.impl;

/*
 * #%L
 * de.metas.payment.esr
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


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.util.Env;

import de.metas.payment.esr.ESRConstants;
import de.metas.payment.esr.api.IESRImportBL;
import de.metas.payment.esr.exception.ESRParserException;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.payment.esr.model.X_ESR_ImportLine;

class ESRReceiptLineMatcher extends AbstractESRLineMatcher
{
	public final static String ERR_WRONG_CTRL_AMT = "ESR_Wrong_Ctrl_Amt";
	public final static String ERR_WRONG_CTRL_QTY = "ESR_Wrong_Ctrl_Qty";
	public final static String ERR_WRONG_CTRL_LINE_LENGTH = "ESR_Wrong_Ctrl_Line_Length";
	public final static String ERR_WRONG_TRX_TYPE = "ESR_Wrong_Trx_Type";

	@Override
	public void match(final I_ESR_ImportLine importLine)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(importLine);
		final IESRImportBL esrImportBL = Services.get(IESRImportBL.class);

		final String trxType = importLine.getESRTrxType();
		Check.assume(ESRConstants.ESRTRXTYPE_Receipt.equals(trxType), Services.get(IMsgBL.class).getMsg(ctx, ERR_WRONG_TRX_TYPE, new Object[] { trxType }));

		final String esrImportLineText = importLine.getESRLineText();

		if (esrImportLineText.length() != 87)
		{
			throw new ESRParserException(Services.get(IMsgBL.class).getMsg(ctx, ERR_WRONG_CTRL_LINE_LENGTH, new Object[] { esrImportLineText.length() }));
		}

		final I_ESR_Import importHdr = importLine.getESR_Import(); // task 05500
		
		// set the control amount (from the control line)
		final String ctrlAmtStr = esrImportLineText.substring(39, 51);
		try
		{
			final BigDecimal controlAmount = new BigDecimal(ctrlAmtStr).divide(Env.ONEHUNDRED, 2, RoundingMode.UNNECESSARY);
			importHdr.setESR_Control_Amount(controlAmount);
		}
		catch (NumberFormatException e)
		{
			logger.log(Level.INFO, e.getLocalizedMessage(), e);
			esrImportBL.addErrorMsg(importLine, Services.get(IMsgBL.class).getMsg(ctx, ERR_WRONG_CTRL_AMT, new Object[] { ctrlAmtStr }));
		}

		// set the control trx quantities (from the control line)
		final String trxQtysStr = esrImportLineText.substring(51, 63);
		try
		{
			final BigDecimal controlTrxQty = new BigDecimal(trxQtysStr);
			importHdr.setESR_Control_Trx_Qty(controlTrxQty);
		}
		catch (NumberFormatException e)
		{
			logger.log(Level.INFO, e.getLocalizedMessage(), e);
			esrImportBL.addErrorMsg(importLine, Services.get(IMsgBL.class).getMsg(ctx, ERR_WRONG_CTRL_QTY, new Object[] { trxQtysStr }));
		}

		importLine.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Control_Line);

		final boolean isValid = Check.isEmpty(importLine.getErrorMsg());
		if (isValid)
		{
			importLine.setIsValid(true);
			importLine.setProcessed(true);
		}
		
		InterfaceWrapperHelper.save(importHdr);
		InterfaceWrapperHelper.save(importLine);
	}
}
