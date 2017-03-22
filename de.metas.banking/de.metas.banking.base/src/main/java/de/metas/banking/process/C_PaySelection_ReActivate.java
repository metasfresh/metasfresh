package de.metas.banking.process;

import org.adempiere.util.Services;
import org.compiere.model.I_C_PaySelection;

import de.metas.banking.payment.IPaySelectionBL;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;

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
public class C_PaySelection_ReActivate extends JavaProcess implements IProcessPrecondition
{
	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		final I_C_PaySelection paySelection = context.getSelectedModel(I_C_PaySelection.class);
		if (!paySelection.isProcessed())
		{
			return ProcessPreconditionsResolution.reject("not processed");
		}

		return ProcessPreconditionsResolution.accept();
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
