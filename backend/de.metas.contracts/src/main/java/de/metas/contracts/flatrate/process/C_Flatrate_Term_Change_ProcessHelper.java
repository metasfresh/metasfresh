/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.contracts.flatrate.process;

import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.i18n.AdMessageKey;
import de.metas.util.Services;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Invoice;

import java.util.List;

@UtilityClass
public class C_Flatrate_Term_Change_ProcessHelper
{
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);

	private static final AdMessageKey MSG_hasInvoices = AdMessageKey.of("TermHasInvoices");

	protected void throwExceptionIfTermHasInvoices(final I_C_Flatrate_Term term)
	{
		if (termHasInvoices(term))
		{
			throwHasInvoicesException();
		}
	}

	protected boolean termHasInvoices(final I_C_Flatrate_Term term)
	{
		final List<I_C_Invoice> invoices = flatrateDAO.retrieveInvoicesForFlatrateTerm(term);

		return !invoices.isEmpty();
	}

	protected void throwHasInvoicesException()
	{
		throw new AdempiereException(MSG_hasInvoices);
	}

}
