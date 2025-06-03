/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.business_rule.descriptor.model;

import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.table.api.AdTableId;

import javax.annotation.Nullable;

@Value
@Builder(builderMethodName = "_builder")
public class BusinessRuleWarningTarget
{
	public static final BusinessRuleWarningTarget ROOT_TARGET_RECORD = _builder().type(BusinessRuleWarningTargetType.ROOT_TARGET_RECORD).build();

	@NonNull BusinessRuleWarningTargetType type;
	@Nullable SqlLookup sqlLookup;

	public static BusinessRuleWarningTarget sqlLookup(@NonNull AdTableId adTableId, @NonNull String lookupSQL)
	{
		return _builder()
				.type(BusinessRuleWarningTargetType.SQL_LOOKUP)
				.sqlLookup(SqlLookup.builder()
						.adTableId(adTableId)
						.sql(lookupSQL)
						.build())
				.build();
	}

	public SqlLookup getSqlLookupNotNull()
	{
		return Check.assumeNotNull(sqlLookup, "Target {} has sqlLookup set", this);
	}

	//
	//
	//

	@Value
	@Builder
	public static class SqlLookup
	{
		@NonNull AdTableId adTableId;
		@NonNull String sql;
	}

}

