package de.metas.inoutcandidate.process;

/*
 * #%L
 * de.metas.swat.base
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


import org.adempiere.ad.process.ISvrProcessPrecondition;
import org.adempiere.util.Services;
import org.compiere.process.SvrProcess;

import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;

/**
 * Close receipt schedule line.
 *
 * This is counter-part of {@link M_ReceiptSchedule_ReOpen}.
 *
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/08480_Korrekturm%C3%B6glichkeit_Wareneingang_-_Menge%2C_Packvorschrift%2C_Merkmal_%28109195602347%29
 *
 */
public class M_ReceiptSchedule_Close extends SvrProcess implements ISvrProcessPrecondition
{
	private final transient IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);

	@Override
	public boolean isPreconditionApplicable(final PreconditionsContext context)
	{
		final I_M_ReceiptSchedule receiptSchedule = context.getModel(I_M_ReceiptSchedule.class);

		// Make sure receipt schedule is open
		if (receiptScheduleBL.isClosed(receiptSchedule))
		{
			return false;
		}

		return true;
	}

	@Override
	protected String doIt() throws Exception
	{
		final I_M_ReceiptSchedule receiptSchedule = getRecord(I_M_ReceiptSchedule.class);
		receiptScheduleBL.close(receiptSchedule);

		return MSG_OK;
	}
}
