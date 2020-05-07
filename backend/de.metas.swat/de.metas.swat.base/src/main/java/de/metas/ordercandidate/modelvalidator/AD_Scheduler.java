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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
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

import de.metas.ordercandidate.model.I_C_OLCandProcessor;

@Validator(I_AD_Scheduler.class)
public class AD_Scheduler
{

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void resetOLCandProcessorReference(final I_AD_Scheduler scheduler)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(scheduler);
		final String trxName = InterfaceWrapperHelper.getTrxName(scheduler);

		final List<I_C_OLCandProcessor> referencingProcessors =
				new Query(ctx, I_C_OLCandProcessor.Table_Name, I_C_OLCandProcessor.COLUMNNAME_AD_Scheduler_ID + "=?", trxName)
						.setParameters(scheduler.getAD_Scheduler_ID())
						.list(I_C_OLCandProcessor.class);

		for (final I_C_OLCandProcessor processor : referencingProcessors)
		{
			processor.setAD_Scheduler_ID(0);
			InterfaceWrapperHelper.save(processor);
		}
	}
}
