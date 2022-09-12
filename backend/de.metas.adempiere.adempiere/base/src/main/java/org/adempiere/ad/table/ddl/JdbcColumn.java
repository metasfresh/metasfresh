package org.adempiere.ad.table.ddl;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * Existing database column (retrieved via JDBC driver)
 */
@Value
@Builder
class JdbcColumn
{
	@NonNull String columnName;
	boolean mandatory; // i.e. NOT NULL
}
