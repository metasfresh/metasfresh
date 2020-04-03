package de.metas.aggregation.callout;

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


import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.impl.TableIdsCache;

import de.metas.aggregation.model.I_C_Aggregation;

@Callout(I_C_Aggregation.class)
public class C_Aggregation
{
	@CalloutMethod(columnNames = I_C_Aggregation.COLUMNNAME_AD_Table_ID)
	public void onAD_Table_ID(final I_C_Aggregation aggregation, ICalloutField field)
	{
		if (aggregation.getAD_Table_ID() <= 0)
		{
			return;
		}

		final AdTableId adTableId = AdTableId.ofRepoId(aggregation.getAD_Table_ID());
		final String entityType = TableIdsCache.instance.getEntityType(adTableId);
		aggregation.setEntityType(entityType);
	}
}
