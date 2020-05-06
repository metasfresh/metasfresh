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
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;

import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.api.IPrintingQueueBL;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.printing.model.I_C_Printing_Queue_Recipient;
import de.metas.util.Services;

@Interceptor(I_C_Printing_Queue_Recipient.class)
public class C_Printing_Queue_Recipient
{

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_DELETE }
			, ifColumnsChanged = { I_C_Printing_Queue_Recipient.COLUMNNAME_AD_User_ToPrint_ID })
	public void updateAggregationKey(I_C_Printing_Queue_Recipient itemRecipient)
	{
		if (!Services.get(IPrintingDAO.class).isUpdatePrintingQueueAggregationKey(itemRecipient))
		{
			return;
		}
		
		final IPrintingQueueBL printingQueueBL = Services.get(IPrintingQueueBL.class);
		final I_C_Printing_Queue item = itemRecipient.getC_Printing_Queue();
		printingQueueBL.setItemAggregationKey(item);
		InterfaceWrapperHelper.save(item);
	}

}
