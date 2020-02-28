package de.metas.payment.sepa.api.impl;

/*
 * #%L
 * de.metas.payment.sepa
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
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_PaySelection;

import de.metas.payment.sepa.api.IPaymentDAO;
import de.metas.payment.sepa.model.I_SEPA_Export;
import de.metas.util.Services;

public class PaymentDAO implements IPaymentDAO
{

	@Override
	public I_SEPA_Export retrieveForPaySelection(final I_C_PaySelection paySelection)
	{
		final int paySelectionTableID = InterfaceWrapperHelper.getTableId(I_C_PaySelection.class);
		
		return Services.get(IQueryBL.class).createQueryBuilder(I_SEPA_Export.class, paySelection)
				.addEqualsFilter(I_SEPA_Export.COLUMNNAME_AD_Table_ID, paySelectionTableID)
				.addEqualsFilter(I_SEPA_Export.COLUMNNAME_Record_ID, paySelection.getC_PaySelection_ID())
				.addEqualsFilter(I_SEPA_Export.COLUMNNAME_Processed, false)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_SEPA_Export.class);
	}

}
