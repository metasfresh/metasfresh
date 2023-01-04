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

package de.metas.serviceprovider.issue.agg.key.impl;

import de.metas.serviceprovider.model.I_S_Issue;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.agg.key.IAggregationKeyRegistry;
import org.adempiere.util.agg.key.IAggregationKeyValueHandler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * AggregationKey value handler for Issue
 */
@Component
public class IssueEffortKeyValueHandler implements IAggregationKeyValueHandler<I_S_Issue>
{
	@Override
	public List<Object> getValues(@NonNull final I_S_Issue model)
	{
		final List<Object> values = new ArrayList<>();

		values.add(model.getAD_Org_ID());
		values.add(model.getC_Activity_ID());
		values.add(model.getC_Project_ID());

		return values;
	}

	@PostConstruct
	public void register()
	{
		final IAggregationKeyRegistry keyRegistry = Services.get(IAggregationKeyRegistry.class);

		keyRegistry.registerAggregationKeyValueHandler(IssueEffortKeyBuilder.REGISTRATION_KEY, this);

		keyRegistry.registerDependsOnColumnnames(IssueEffortKeyBuilder.REGISTRATION_KEY,
												 I_S_Issue.COLUMNNAME_AD_Org_ID,
												 I_S_Issue.COLUMNNAME_C_Activity_ID,
												 I_S_Issue.COLUMNNAME_C_Project_ID);
	}
}
