package de.metas.dlm.partitioner.config;

import javax.annotation.concurrent.Immutable;

import org.adempiere.util.Check;
import org.adempiere.util.StringUtils;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;

import de.metas.dlm.exception.DLMReferenceException;

/*
 * #%L
 * metasfresh-dlm
 * %%
 * Copyright (C) 2016 metas GmbH
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
 * Describes a table reference.
 *
 * @see PartitionerServiceOld#augmentPartitionerConfig(PartitionerConfig, java.util.List)
 * @see DLMReferenceException#getTableReferenceDescriptor()
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Immutable
public class TableReferenceDescriptor
{
	private final String referencingTableName;

	private final int referencedRecordId;

	private final String referencingColumnName;

	private final String referencedTableName;

	private TableReferenceDescriptor(final String referencedTableName,
			final int referencedRecordId,
			final String referencingTableName,
			final String referencingColumnName)
	{
		Check.assumeNotEmpty(referencedTableName, "Param 'referencedTableName' is not null; all params={}", buildParameterString(referencedTableName, referencingTableName, referencingColumnName));
		Check.assumeNotEmpty(referencingTableName, "Param 'referencingTableName' is not null; all params={}", buildParameterString(referencedTableName, referencingTableName, referencingColumnName));
		Check.assumeNotEmpty(referencingColumnName, "Param 'referencingColumnName' is not null; all params={}", buildParameterString(referencedTableName, referencingTableName, referencingColumnName));

		this.referencedTableName = referencedTableName;
		this.referencedRecordId = referencedRecordId;
		this.referencingTableName = referencingTableName;
		this.referencingColumnName = referencingColumnName;

	}

	/**
	 * Creates a descriptor for <code>referencingTableName.referencingColumnName => referencedTableName</code>.
	 *
	 * @param referencingTableName
	 * @param referencingColumnName
	 * @param referencedTableName
	 * @return
	 */
	public static TableReferenceDescriptor of(
			final String referencingTableName,
			final String referencingColumnName,
			final String referencedTableName)
	{
		return new TableReferenceDescriptor(
				referencedTableName,
				-1,
				referencingTableName,
				referencingColumnName);
	}

	/**
	 * Creates a descriptor for <code>referencingTableName.referencingColumnName => referencedTableName[referencedRecordId]</code>.
	 *
	 * @param referencingTableName
	 * @param referencingColumnName
	 * @param referencedTableName
	 * @param referencedRecordId currently this is not really used, but only for info/debugging
	 * @return
	 */
	public static TableReferenceDescriptor of(
			final String referencingTableName,
			final String referencingColumnName,
			final String referencedTableName,
			final int referencedRecordId)
	{
		return new TableReferenceDescriptor(
				referencedTableName,
				referencedRecordId,
				referencingTableName,
				referencingColumnName);
	}

	public String getReferencingTableName()
	{
		return referencingTableName;
	}

	public String getReferencingColumnName()
	{
		return referencingColumnName;
	}

	public String getReferencedTableName()
	{
		return referencedTableName;
	}

	public int getReferencedRecordId()
	{
		return referencedRecordId;
	}

	public ITableRecordReference getReferencedRecord()
	{
		return new TableRecordReference(referencedTableName, referencedRecordId);
	}

	@Override
	public String toString()
	{
		return StringUtils.formatMessage("TableReferenceDescriptor[{}.{} -> {} (concrete record ID={})]", referencingTableName, referencingColumnName, referencedTableName, referencedRecordId);
	}

	private static String buildParameterString(
			final String referencedTableName,
			final String referencingTableName,
			final String referencingColumnName)
	{
		return "[referencedTableName=" + referencedTableName + ", referencingTableName=" + referencingTableName + ", referencingColumnName=" + referencingColumnName + "]";
	}
}
