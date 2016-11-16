package de.metas.dunning.writeoff.invoice.process;

/*
 * #%L
 * de.metas.dunning
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


import java.util.Properties;

import org.adempiere.util.Services;

import de.metas.adempiere.form.IClientUI;
import de.metas.dunning.invoice.api.IInvoiceSourceBL;
import de.metas.process.SvrProcess;

public class C_Dunning_Candidate_MassWriteOff extends SvrProcess
{

	final public static String MSG_DODISMISSAL = "StartMassDismissal";

	@Override
	protected void prepare()
	{
		// Nothing to do.

	}

	@Override
	protected String doIt()
	{
		final boolean doWriteOff = Services.get(IClientUI.class).ask(0, MSG_DODISMISSAL);
		if (!doWriteOff)
		{
			return "Canceled";
		}

		final Properties ctx = getCtx();
		final String writeOffDescription = getProcessInfo().getTitle() + " #" + getAD_PInstance_ID();

		final int writeOffCount = Services.get(IInvoiceSourceBL.class).writeOffDunningDocs(ctx, writeOffDescription, this);

		return "@WriteOff@ #" + writeOffCount;
	}

}
