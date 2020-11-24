package org.adempiere.ad.persistence.modelgen;

import com.google.common.base.Optional;

import lombok.Data;
import lombok.ToString;

/**
 * Model Generator's Column Info
 *
 * @author tsa
 *
 */
@Data
@ToString(exclude = "repository")
/* package */class ColumnInfo
{
	private TableAndColumnInfoRepository repository;
	//
	private final String tableName;
	private final String columnName;
	private final boolean isUpdateable;
	private final boolean isMandatory;
	private final int displayType;
	/** i.e. AD_Column.AD_Reference_Value_ID */
	private final int adReferenceId;
	private final int fieldLength;
	private final String defaultValue;
	private final String valueMin;
	private final String valueMax;
	private final String vFormat;
	private final String callout;
	private final String name;
	private final String description;
	private final boolean virtualColumn;
	private boolean lazyLoading;
	private final boolean isEncrypted;
	private final boolean isKey;
	private final int seqNo;
	private final int adTableId;
	private boolean isIdentifier;
	private String tableIdColumnName;

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

	/**
	 * Gets referenced table info (in case of Table or Search references which have the AD_Reference_Value_ID set)
	 */
	public Optional<TableReferenceInfo> getTableReferenceInfo()
	{
		return repository.getTableReferenceInfo(getAdReferenceId());
	}

	public Optional<ListInfo> getListInfo()
	{
		return repository.getListInfo(getAdReferenceId());
	}
}
