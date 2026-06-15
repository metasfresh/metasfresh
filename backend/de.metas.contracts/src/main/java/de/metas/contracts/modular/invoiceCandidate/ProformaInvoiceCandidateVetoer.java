/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.modular.invoiceCandidate;

import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inoutcandidate.spi.ModelWithoutInvoiceCandidateVetoer;
import de.metas.invoicecandidate.model.I_M_InOutLine;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_InOut;

public class ProformaInvoiceCandidateVetoer implements ModelWithoutInvoiceCandidateVetoer
{
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);

	@Override
	public OnMissingCandidate foundModelWithoutInvoiceCandidate(final Object model)
	{
		final I_M_InOutLine inOutLineRecord = InterfaceWrapperHelper.create(model, I_M_InOutLine.class);
		final I_M_InOut inOutRecord = inOutDAO.getById(InOutId.ofRepoId(inOutLineRecord.getM_InOut_ID()));

		if(docTypeBL.isProformaShipment(DocTypeId.ofRepoId(inOutRecord.getC_DocType_ID())))
		{
			return OnMissingCandidate.I_VETO;
		}
		return OnMissingCandidate.I_DONT_CARE;
	}
}
