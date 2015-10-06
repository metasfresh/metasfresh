package de.metas.inoutcandidate.modelvalidator;

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


import java.util.Collections;
import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.util.Services;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.ModelValidator;

import de.metas.inoutcandidate.api.IReceiptScheduleProducerFactory;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.spi.IReceiptScheduleProducer;

@Validator(I_C_OrderLine.class)
public class C_OrderLine_ReceiptSchedule
{
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = { I_C_OrderLine.COLUMNNAME_QtyOrderedOverUnder })
	public void createReceiptSchedules(final I_C_OrderLine orderLine)
	{
		if (!C_Order_ReceiptSchedule.isEligibleForReceiptSchedule(orderLine.getC_Order()))
		{
			return;
		}

		final IReceiptScheduleProducer producer = Services.get(IReceiptScheduleProducerFactory.class)
				.createProducer(I_C_OrderLine.Table_Name, false);

		final List<I_M_ReceiptSchedule> previousSchedules = Collections.emptyList();
		producer.createOrUpdateReceiptSchedules(orderLine, previousSchedules);
	}
}
