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


import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;

import de.metas.aggregation.api.IAggregationDAO;
import de.metas.aggregation.listeners.IAggregationListeners;
import de.metas.aggregation.model.I_C_Aggregation;
import de.metas.util.Services;

@Interceptor(I_C_Aggregation.class)
public class C_Aggregation
{
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_C_Aggregation.COLUMNNAME_AD_Table_ID)
	public void assertTableNotChangedWhenItemsDefined(final I_C_Aggregation aggregation)
	{
		final boolean hasItems = Services.get(IAggregationDAO.class)
				.retrieveAllItemsQuery(aggregation)
				.create()
				.anyMatch();

		if (hasItems)
		{
			throw new AdempiereException("Changing table not allowed when having items defined");
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteItems(final I_C_Aggregation aggregation)
	{
		Services.get(IAggregationDAO.class)
				.retrieveAllItemsQuery(aggregation)
				.create()
				.delete();
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_DELETE })
	public void fireListeners(final I_C_Aggregation aggregation, final int changeType)
	{
		final IAggregationListeners aggregationListeners = Services.get(IAggregationListeners.class);
		final ModelChangeType type = ModelChangeType.valueOf(changeType);

		if (type.isNew())
		{
			aggregationListeners.fireAggregationCreated(aggregation);
		}
		else if (type.isChange())
		{
			aggregationListeners.fireAggregationChanged(aggregation);
		}
		else if (type.isDelete())
		{
			aggregationListeners.fireAggregationDeleted(aggregation);
		}
	}
}
