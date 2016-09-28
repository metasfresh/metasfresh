package de.metas.banking.process;

import org.adempiere.ad.process.ISvrProcessPrecondition;
import org.adempiere.util.Services;
import org.compiere.model.I_C_PaySelection;
import org.compiere.process.SvrProcess;

import de.metas.banking.payment.IPaySelectionBL;

/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Re-activate the selected payment selection.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class C_PaySelection_ReActivate extends SvrProcess implements ISvrProcessPrecondition
{
	@Override
	public boolean isPreconditionApplicable(final PreconditionsContext context)
	{
		final I_C_PaySelection paySelection = context.getModel(I_C_PaySelection.class);
		if (!paySelection.isProcessed())
		{
			return false;
		}

		return true;
	}

	@Override
	protected void prepare()
	{
	}

	@Override
	protected String doIt() throws Exception
	{
		final I_C_PaySelection paySelection = getRecord(I_C_PaySelection.class);
		Services.get(IPaySelectionBL.class).reActivate(paySelection);

		return MSG_OK;
	}
}
