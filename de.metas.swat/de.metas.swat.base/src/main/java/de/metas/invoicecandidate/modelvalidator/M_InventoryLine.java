package de.metas.invoicecandidate.modelvalidator;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.util.Services;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.model.ModelValidator;

import de.metas.invoicecandidate.api.IInvoiceCandDAO;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Interceptor(I_M_InventoryLine.class)
public class M_InventoryLine
{
	/**
	 * If an M_InventoryLine is deleted, then this method deletes the candidates which directly reference that line via <code>AD_Table_ID</code> and <code>Record_ID</code>.
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteC_Invoice_Candidates(final I_M_InventoryLine inventoryLine)
	{
		Services.get(IInvoiceCandDAO.class).deleteAllReferencingInvoiceCandidates(inventoryLine);
	}
}
