package de.metas.ordercandidate.modelvalidator;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Scheduler;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;

import de.metas.ordercandidate.model.I_C_OLCandAggAndOrder;
import de.metas.ordercandidate.model.I_C_OLCandProcessor;

@Validator(I_C_OLCandProcessor.class)
public class C_OLCandProcessor
{
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteAggAndOrderRecords(final I_C_OLCandProcessor processor)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(processor);
		final String trxName = InterfaceWrapperHelper.getTrxName(processor);

		final List<I_C_OLCandAggAndOrder> referencingAggAndOrders = new Query(ctx, I_C_OLCandAggAndOrder.Table_Name, I_C_OLCandAggAndOrder.COLUMNNAME_C_OLCandProcessor_ID + "=?", trxName)
				.setParameters(processor.getC_OLCandProcessor_ID())
				.list(I_C_OLCandAggAndOrder.class);

		for (final I_C_OLCandAggAndOrder referencingAggAndOrder : referencingAggAndOrders)
		{
			InterfaceWrapperHelper.delete(referencingAggAndOrder);
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteScheduler(final I_C_OLCandProcessor processor)
	{
		if (processor.getAD_Scheduler_ID() > 0)
		{
			final I_AD_Scheduler referencedScheduler = processor.getAD_Scheduler();
			InterfaceWrapperHelper.delete(referencedScheduler);
		}
	}
}
