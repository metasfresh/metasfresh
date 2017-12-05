package org.adempiere.ad.table;

import org.adempiere.util.Check;

import lombok.Builder;
import lombok.Value;

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
 * 
 * Example of how this could work in the real life:
 * 
 * <h1>Case 1 (Ad_Table_ID, Record_ID)</h1>
 * 
 * <li>Have a table Origin
 * <li>Have a table Target
 * <li>The table Origin has the column AD_Table_ID and Record_ID
 * <li>The table Origin has entries with values: Origin.AD_Table_ID = Target.Table_ID ; Origin.Record_ID = Target.Record_ID
 * <li>=>
 * <li>Origin = originTableName
 * <li>Origin.Record_ID = recordIdColumnName
 * <li>Target = targetTableName
 * 
 * <h1>Case 2 (Prefix_Ad_Table_ID, Prefix_Record_ID)</h1>>
 * 
 * <li>Have a table Origin
 * <li>Have a table Target
 * <li>The table Origin has the column Target_AD_Table_ID and Target_Record_ID
 * <li>The table Origin has entries with values: Origin.Target_AD_Table_ID = Target.Table_ID ; Origin.Target_Record_ID = Target.Record_ID
 * <li>=>
 * <li>Origin = originTableName
 * <li>Origin.Target_Record_ID = recordIdColumnName
 * <li>Target = targetTableName
 *
 * @see PartitionerServiceOld#augmentPartitionerConfig(PartitionerConfig, java.util.List)
 * @see DLMReferenceException#getTableReferenceDescriptor()
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Value
public final class TableRecordIdDescriptor
{
	/**
	 * Creates a descriptor for <code>referencingTableName.referencingColumnName => referencedTableName</code>.
	 */
	public static TableRecordIdDescriptor of(
			final String originTableName,
			final String recordIdColumnName,
			final String targetTableName)
	{
		return builder()
				.targetTableName(targetTableName)
				.originTableName(originTableName)
				.recordIdColumnName(recordIdColumnName)
				.build();
	}

	private final String originTableName;
	private final String recordIdColumnName;
	private final String targetTableName;

	@Builder
	private TableRecordIdDescriptor(
			final String targetTableName,
			final String originTableName,
			final String recordIdColumnName)
	{
		Check.assumeNotEmpty(targetTableName, "Param 'referencedTableName' is not null");
		Check.assumeNotEmpty(originTableName, "Param 'referencingTableName' is not null");
		Check.assumeNotEmpty(recordIdColumnName, "Param 'referencingColumnName' is not null");

		this.targetTableName = targetTableName;
		this.originTableName = originTableName;
		this.recordIdColumnName = recordIdColumnName;

	}

}
