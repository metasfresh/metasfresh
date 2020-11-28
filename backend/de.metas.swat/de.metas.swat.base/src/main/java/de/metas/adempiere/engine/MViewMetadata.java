package de.metas.adempiere.engine;

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


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.keyvalue.MultiKey;

public class MViewMetadata
{
	private String sql;
	private String targetTableName;
	private Set<String> targetKeyColumns;
	private Map<MultiKey, String> relations = new HashMap<MultiKey, String>();

	private Map<String, Set<String>> sourceTables = new HashMap<String, Set<String>>();

	public String getTargetTableName()
	{
		return targetTableName;
	}

	public void setTargetTableName(String targetTableName)
	{
		this.targetTableName = targetTableName;
	}

	public Set<String> getTargetKeyColumns()
	{
		return targetKeyColumns;
	}

	public void setTargetKeyColumns(String... columns)
	{
		final Set<String> s = new HashSet<String>();
		for (String c : columns)
			s.add(c);
		this.targetKeyColumns = s;
	}

	public String getSql()
	{
		return sql;
	}

	public void setSql(String sql)
	{
		this.sql = sql;
	}

	public void addSourceTable(String tableName, String... columns)
	{
		Set<String> s = new HashSet<String>();
		for (String c : columns)
			s.add(c);
		sourceTables.put(tableName, s);
	}

	public Set<String> getSourceTables()
	{
		return sourceTables.keySet();
	}

	public Set<String> getSourceColumns(String sourceTableName)
	{
		return sourceTables.get(sourceTableName);
	}

	public void addRelation(String sourceTableName, String targetTableName, String whereClause)
	{
		final MultiKey key = new MultiKey(sourceTableName, targetTableName);
		relations.put(key, whereClause);
	}

	public String getRelationWhereClause(String sourceTableName, String targetTableName)
	{
		final MultiKey key = new MultiKey(sourceTableName, targetTableName);
		return relations.get(key);
	}

	@Override
	public String toString()
	{
		return "MViewMetadata [targetTableName=" + targetTableName + ", targetKeyColumns=" + targetKeyColumns + ", sourceTables=" + sourceTables + ", sql=" + sql + "]";
	}

}
