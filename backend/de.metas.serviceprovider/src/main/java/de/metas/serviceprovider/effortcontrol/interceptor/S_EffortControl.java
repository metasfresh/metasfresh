/*
 * #%L
 * de.metas.serviceprovider.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.serviceprovider.effortcontrol.interceptor;

import de.metas.serviceprovider.effortcontrol.agg.key.impl.EffortControlKeyBuilder;
import de.metas.serviceprovider.model.I_S_EffortControl;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_S_EffortControl.class)
@Component
public class S_EffortControl
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW })
	public void setEffortControlKey(@NonNull final I_S_EffortControl record)
	{
		if (record.getEffortAggregationKey() == null)
		{
			final String effortControlAggKey = new EffortControlKeyBuilder().buildKey(record);
			record.setEffortAggregationKey(effortControlAggKey);
		}
	}


	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = {
			I_S_EffortControl.COLUMNNAME_AD_Org_ID,
			I_S_EffortControl.COLUMNNAME_C_Activity_ID,
			I_S_EffortControl.COLUMNNAME_C_Project_ID
	})
	public void forbidUpdates(@NonNull final I_S_EffortControl record)
	{
		throw new AdempiereException("OrgId, ActivityId & ProjectId cannot be changed for an effort control record!")
				.appendParametersToMessage()
				.setParameter("S_EffortControl_ID", record.getS_EffortControl_ID())
				.setParameter("AD_Org_ID", record.getAD_Org_ID())
				.setParameter("C_Project_ID", record.getC_Project_ID())
				.setParameter("C_Activity_ID", record.getC_Activity_ID());
	}
}
