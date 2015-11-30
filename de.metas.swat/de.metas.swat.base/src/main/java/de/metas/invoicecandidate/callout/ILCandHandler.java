package de.metas.invoicecandidate.callout;

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


import java.util.Properties;

import org.adempiere.model.GridTabWrapper;
import org.adempiere.util.Services;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;

import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;

public class ILCandHandler extends CalloutEngine
{
	public String className(
			final Properties ctx,
			final int WindowNo,
			final GridTab mTab,
			final GridField mField,
			final Object value)
	{
		final I_C_ILCandHandler ilCandGenerator = GridTabWrapper.create(mTab, I_C_ILCandHandler.class);

		final boolean failIfClassNotFound = false;
		Services.get(IInvoiceCandidateHandlerBL.class).evalClassName(ilCandGenerator, failIfClassNotFound);

		return "";
	}
}
