package de.metas.printing.model.validator;

/*
 * #%L
 * de.metas.printing.base
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


import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;

import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.api.IPrintingQueueBL;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.util.Services;

@Interceptor(I_C_Printing_Queue.class)
public class C_Printing_Queue
{

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_NEW }
			, ifColumnsChanged = {
					I_C_Printing_Queue.COLUMNNAME_AD_User_ID,
					I_C_Printing_Queue.COLUMNNAME_IsPrintoutForOtherUser,
					I_C_Printing_Queue.COLUMNNAME_Copies })
	public void updateAggregationKey(final I_C_Printing_Queue item)
	{
		final IPrintingQueueBL printingQueueBL = Services.get(IPrintingQueueBL.class);
		printingQueueBL.setItemAggregationKey(item);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void deleteItemRecipients(final I_C_Printing_Queue item)
	{
		final IPrintingDAO printingDAO = Services.get(IPrintingDAO.class);
		printingDAO.deletePrintingQueueRecipients(item);
	}

}
