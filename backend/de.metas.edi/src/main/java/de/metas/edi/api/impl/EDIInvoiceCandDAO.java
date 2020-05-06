package de.metas.edi.api.impl;

/*
 * #%L
 * de.metas.edi
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


import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_BPartner;

import de.metas.edi.api.IEDIInvoiceCandDAO;
import de.metas.edi.model.I_C_Invoice_Candidate;
import de.metas.util.Services;

public class EDIInvoiceCandDAO implements IEDIInvoiceCandDAO
{
	@Override
	public void updateEdiRecipientInvoiceCandidates(final I_C_BPartner bPartner, final boolean isEDIRecipient)
	{
		// Services
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		queryBL.createQueryBuilder(I_C_Invoice_Candidate.class, bPartner)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_Bill_BPartner_ID, bPartner.getC_BPartner_ID())
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_Processed, false)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_IsEdiInvoicRecipient, !isEDIRecipient)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.create()
				.updateDirectly()
				.addSetColumnValue(I_C_Invoice_Candidate.COLUMNNAME_IsEdiInvoicRecipient, isEDIRecipient)
				.execute();
	}
}
