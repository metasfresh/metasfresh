/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.invoice.sequence;

import de.metas.document.sequence.BillToCountryIdProvider;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationId;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.Evaluatee;

import java.util.Objects;


public class InvoiceBillToCountryIdProvider implements BillToCountryIdProvider
{

	private final ILocationDAO locationDAO = Services.get(ILocationDAO.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

	@Override
	public ProviderResult computeValueInfo(final Evaluatee eval)
	{
		String test = InterfaceWrapperHelper.getModelTableName(eval);
		if(!I_C_Invoice.Table_Name.equals(InterfaceWrapperHelper.getModelTableName(eval)))
		{
			return ProviderResult.EMPTY;
		}

		String test2 = eval.get_ValueAsString(I_C_Invoice.COLUMNNAME_IsSOTrx);
		if(Objects.equals(eval.get_ValueAsString(I_C_Invoice.COLUMNNAME_IsSOTrx), "N"))
		{
			return ProviderResult.EMPTY;
		}
		final LocationId bpartnerLocationValueId = LocationId.ofRepoIdOrNull(eval.get_ValueAsInt(I_C_Invoice.COLUMNNAME_C_BPartner_Location_Value_ID, 0));
		Check.assumeNotNull(bpartnerLocationValueId, "bpartnerLocationValueId should be present");

		return ProviderResult.of(locationDAO.getCountryIdByLocationId(bpartnerLocationValueId));
	}
}
