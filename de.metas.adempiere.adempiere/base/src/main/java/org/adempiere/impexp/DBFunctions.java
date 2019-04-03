/**
 * 
 */
package org.adempiere.impexp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Builder
@Value
public class DBFunctions
{
	private static final transient Logger log = LogManager.getLogger(DBFunctions.class);
	private final static String IMPORT_AFTER_ROW = "IMPORT_AFTER_ROW";

	@NonNull
	private final String tableName;
	
	@NonNull
	@Default
	private final List<DBFunction> importFunctions = ImmutableList.of();

	public List<DBFunction> getEliglibleImportFunctions()
	{
		final List<DBFunction> availableAfterRowFunctions = new ArrayList<>();
		for (final DBFunction function : importFunctions)
		{
			if (isEligibleFunction(function))
			{
				availableAfterRowFunctions.add(function);
			}
			else
			{
				log.warn("Function {} from schema {} is not eliglible for importing process!", function.getName(), function.getSchema());
			}
		}
		
		return ImmutableList.copyOf(availableAfterRowFunctions);
	}

	private boolean isEligibleFunction(@NonNull final DBFunction function)
	{
		final String routine_name = function.getName();
		return StringUtils.containsIgnoreCase(routine_name, IMPORT_AFTER_ROW);
	}

}
