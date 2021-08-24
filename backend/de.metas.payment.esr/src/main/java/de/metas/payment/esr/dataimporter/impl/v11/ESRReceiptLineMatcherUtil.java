package de.metas.payment.esr.dataimporter.impl.v11;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.compiere.util.Env;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.payment.esr.ESRConstants;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.payment.esr
 * %%
 * Copyright (C) 2017 metas GmbH
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
@UtilityClass
public class ESRReceiptLineMatcherUtil
{

	public final static  AdMessageKey ERR_WRONG_CTRL_AMT =  AdMessageKey.of("ESR_Wrong_Ctrl_Amt");
	public final static  AdMessageKey ERR_WRONG_CTRL_QTY =  AdMessageKey.of("ESR_Wrong_Ctrl_Qty");
	public final static  AdMessageKey ERR_WRONG_TRX_TYPE =  AdMessageKey.of("ESR_Wrong_Trx_Type");

	public boolean isReceiptLine(@NonNull final String v11LineStr)
	{
		final String trxType = ESRTransactionLineMatcherUtil.extractEsrTrxType(v11LineStr);
		return ESRConstants.ESRTRXTYPE_Receipt.equals(trxType);
	}

	/**
	 * 
	 * @param v11LineStr
	 * @return {@code true} if the given string is a V11 receipt (control) line, but does not have the correct length of 87.
	 */
	public boolean isReceiptLineWithWrongLength(@NonNull final String v11LineStr)
	{
		if (!isReceiptLine(v11LineStr))
		{
			return false;
		}

		return v11LineStr.length() != 87;
	}

	/**
	 * If there is a problem extracting the amount, it logs an error message to {@link Loggables#get()}.
	 * 
	 * @param esrImportLineText
	 * @return
	 */
	public BigDecimal extractCtrlAmount(@NonNull final String esrImportLineText)
	{
		// set the control amount (from the control line)
		final String ctrlAmtStr = esrImportLineText.substring(39, 51);
		try
		{
			final BigDecimal controlAmount = new BigDecimal(ctrlAmtStr).divide(Env.ONEHUNDRED, 2, RoundingMode.UNNECESSARY);
			return controlAmount;
		}
		catch (NumberFormatException e)
		{
			Loggables.addLog(Services.get(IMsgBL.class).getMsg(Env.getCtx(), ERR_WRONG_CTRL_AMT, new Object[]
				{ ctrlAmtStr }));
			return null;
		}
	}

	/**
	 * If there is a problem extracting the qty, it logs an error message to {@link Loggables#get()}.
	 * 
	 * @param esrImportLineText
	 * @return
	 */
	public BigDecimal extractCtrlQty(@NonNull final String esrImportLineText)
	{
		final String trxQtysStr = esrImportLineText.substring(51, 63);
		try
		{
			final BigDecimal controlTrxQty = new BigDecimal(trxQtysStr);
			return controlTrxQty;
		}
		catch (NumberFormatException e)
		{
			Loggables.addLog(Services.get(IMsgBL.class).getMsg(Env.getCtx(), ERR_WRONG_CTRL_QTY, new Object[] { trxQtysStr }));
			return null;
		}
	}
}
