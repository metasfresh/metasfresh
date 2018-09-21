package de.metas.aggregation.model.validator;

/*
 * #%L
 * de.metas.aggregation
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
import org.compiere.util.CacheMgt;

import de.metas.aggregation.api.IAggregationDAO;
import de.metas.aggregation.listeners.IAggregationListeners;
import de.metas.aggregation.model.I_C_Aggregation;
import de.metas.aggregation.model.I_C_AggregationItem;
import de.metas.util.Services;

@Interceptor(I_C_AggregationItem.class)
public class C_AggregationItem
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void validate(final I_C_AggregationItem aggregationItem)
	{
		//
		// Make sure we are not introducing cycles
		Services.get(IAggregationDAO.class).checkIncludedAggregationCycles(aggregationItem);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_DELETE })
	public void resetCache(final I_C_AggregationItem aggregationItem)
	{
		// NOTE: it's better to reset all because of complex logic and included aggregation dependencies
		CacheMgt.get().reset(I_C_Aggregation.Table_Name);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_DELETE })
	public void fireListeners(final I_C_AggregationItem aggregationItem)
	{
		final IAggregationListeners aggregationListeners = Services.get(IAggregationListeners.class);
		final I_C_Aggregation aggregation = aggregationItem.getC_Aggregation();
		aggregationListeners.fireAggregationChanged(aggregation);
	}

}
