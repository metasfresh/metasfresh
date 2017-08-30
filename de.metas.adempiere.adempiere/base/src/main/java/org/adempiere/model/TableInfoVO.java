/**
 *
 */
package org.adempiere.model;

import lombok.Builder;
import lombok.Value;

@Value
public class TableInfoVO
{
	/** Translated name */
	private final String name;
	private final String tableName;
	private final String linkColumnName;
	private final String parentTableName;

	@Builder
	private TableInfoVO(final String name, final String tableName, final String linkColumnName, final String parentTableName)
	{
		this.name = name;
		this.tableName = tableName;
		this.linkColumnName = linkColumnName;
		this.parentTableName = parentTableName;
	}
}
