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

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Scheduler;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.ordercandidate.model.I_C_OLCandAggAndOrder;
import de.metas.ordercandidate.model.I_C_OLCandProcessor;
import de.metas.util.Services;

@Interceptor(I_C_OLCandProcessor.class)
@Component
public class C_OLCandProcessor
{
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteAggAndOrderRecords(final I_C_OLCandProcessor processor)
	{
		Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_OLCandAggAndOrder.class)
				.addEqualsFilter(I_C_OLCandAggAndOrder.COLUMNNAME_C_OLCandProcessor_ID, processor.getC_OLCandProcessor_ID())
				.create()
				.delete();
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
