package org.adempiere.ad.persistence.modelgen;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.util.text.annotation.ToStringBuilder;

import com.google.common.base.Optional;

/**
 * Model Generator's Column Info
 *
 * @author tsa
 *
 */
/* package */class ColumnInfo
{
	@ToStringBuilder(skip = true)
	private TableAndColumnInfoRepository repository;
	//
	private final String tableName;
	private final String columnName;
	private boolean isUpdateable;
	private boolean isMandatory;
	private int displayType;
	private int adReferenceId;
	private int fieldLength;
	private String defaultValue;
	private String valueMin;
	private String valueMax;
	private String vFormat;
	private String callout;
	private String name;
	private String description;
	private boolean virtualColumn;
	private boolean lazyLoading;
	private boolean isEncrypted;
	private boolean isKey;
	private int seqNo;
	private int adTableId;
	private boolean isIdentifier;
	private String tableIdColumnName = null;

	public ColumnInfo(final String tableName, final String columnName,
			final boolean isUpdateable, final boolean isMandatory,
			final int displayType, final int adReferenceId,
			final int fieldLength,
			final String defaultValue, final String valueMin, final String valueMax,
			final String vFormat,
			final String callout,
			final String name, final String description,
			final boolean virtualColumn,
			final boolean isEncrypted,
			final boolean isKey,
			final int seqNo,
			final int adTableId)
	{
		this.tableName = tableName;
		this.columnName = columnName;
		this.isUpdateable = isUpdateable;
		this.isMandatory = isMandatory;
		this.displayType = displayType;
		this.adReferenceId = adReferenceId;
		this.fieldLength = fieldLength;
		this.defaultValue = defaultValue;
		this.valueMin = valueMin;
		this.valueMax = valueMax;
		this.vFormat = vFormat;
		this.callout = callout;
		this.name = name;
		this.description = description;
		this.virtualColumn = virtualColumn;
		this.isEncrypted = isEncrypted;
		this.isKey = isKey;
		this.seqNo = seqNo;
		this.adTableId = adTableId;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	void setRepository(final TableAndColumnInfoRepository repository)
	{
		this.repository = repository;
	}

	public String getTableName()
	{
		return tableName;
	}

	public String getColumnName()
	{
		return columnName;
	}

	public boolean isUpdateable()
	{
		return isUpdateable;
	}

	void setUpdateable(final boolean isUpdateable)
	{
		this.isUpdateable = isUpdateable;
	}

	public boolean isMandatory()
	{
		return isMandatory;
	}

	void setMandatory(final boolean isMandatory)
	{
		this.isMandatory = isMandatory;
	}

	public int getDisplayType()
	{
		return displayType;
	}

	void setDisplayType(final int displayType)
	{
		this.displayType = displayType;
	}

	/**
	 * i.e. AD_Column.AD_Reference_Value_ID
	 */
	public int getAD_Reference_ID()
	{
		return adReferenceId;
	}

	void setAD_Reference_ID(final int AD_Reference_ID)
	{
		adReferenceId = AD_Reference_ID;
	}

	public int getFieldLength()
	{
		return fieldLength;
	}

	void setFieldLength(final int fieldLength)
	{
		this.fieldLength = fieldLength;
	}

	public String getDefaultValue()
	{
		return defaultValue;
	}

	void setDefaultValue(final String defaultValue)
	{
		this.defaultValue = defaultValue;
	}

	public String getValueMin()
	{
		return valueMin;
	}

	void setValueMin(final String valueMin)
	{
		this.valueMin = valueMin;
	}

	public String getValueMax()
	{
		return valueMax;
	}

	void setValueMax(final String valueMax)
	{
		this.valueMax = valueMax;
	}

	public String getvFormat()
	{
		return vFormat;
	}

	void setvFormat(final String vFormat)
	{
		this.vFormat = vFormat;
	}

	public String getCallout()
	{
		return callout;
	}

	void setCallout(final String callout)
	{
		this.callout = callout;
	}

	public String getName()
	{
		return name;
	}

	void setName(final String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	void setDescription(final String description)
	{
		this.description = description;
	}

	public boolean isVirtualColumn()
	{
		return virtualColumn;
	}

	void setVirtualColumn(final boolean virtualColumn)
	{
		this.virtualColumn = virtualColumn;
	}
	
	public boolean isLazyLoading()
	{
		return lazyLoading;
	}
	
	public void setLazyLoading(boolean lazyLoading)
	{
		this.lazyLoading = lazyLoading;
	}

	public boolean isEncrypted()
	{
		return isEncrypted;
	}

	void setEncrypted(final boolean isEncrypted)
	{
		this.isEncrypted = isEncrypted;
	}

	public boolean isKey()
	{
		return isKey;
	}

	void setKey(final boolean isKey)
	{
		this.isKey = isKey;
	}

	public int getAD_Table_ID()
	{
		return adTableId;
	}

	void setAD_Table_ID(final int AD_Table_ID)
	{
		adTableId = AD_Table_ID;
	}

	public int getSeqNo()
	{
		return seqNo;
	}

	public boolean isIdentifier()
	{
		return isIdentifier;
	}

	void setIdentifier(boolean isIdentifier)
	{
		this.isIdentifier = isIdentifier;
	}

	/**
	 * Gets referenced table info (in case of Table or Search references which have the AD_Reference_Value_ID set)
	 * 
	 * @return
	 */
	public Optional<TableReferenceInfo> getTableReferenceInfo()
	{
		return repository.getTableReferenceInfo(getAD_Reference_ID());
	}

	public Optional<ListInfo> getListInfo()
	{
		return repository.getListInfo(getAD_Reference_ID());
	}

	public String getTableIdColumnName()
	{
		return tableIdColumnName;
	}

	public void setTableIdColumnName(String tableIdColumnName)
	{
		this.tableIdColumnName = tableIdColumnName;
	}
}
