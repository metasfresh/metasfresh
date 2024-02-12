package org.adempiere.ad.persistence.modelgen;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

import java.util.Optional;

/**
 * Model Generator's Column Info
 *
 * @author tsa
 */
@Value
@ToString(exclude = "repository")
class ColumnInfo
{
	TableAndColumnInfoRepository repository;
	//
	String tableName;
	String columnName;
	boolean isUpdatable;
	boolean isMandatory;
	int displayType;
	/**
	 * i.e. AD_Column.AD_Reference_Value_ID
	 */
	int adReferenceId;
	int fieldLength;
	String defaultValue;
	String valueMin;
	String valueMax;
	String vFormat;
	String callout;
	String name;
	String description;
	boolean virtualColumn;
	boolean lazyLoading;
	boolean isEncrypted;
	boolean isKey;
	int seqNo;
	int adTableId;
	boolean isIdentifier;
	String tableIdColumnName;

	@Builder
	private ColumnInfo(
			@NonNull final TableAndColumnInfoRepository repository,
			final String tableName,
			final String columnName,
			final boolean isUpdatable,
			final boolean isMandatory,
			final int displayType,
			final int adReferenceId,
			final int fieldLength,
			final String defaultValue,
			final String valueMin,
			final String valueMax,
			final String vFormat,
			final String callout,
			final String name,
			final String description,
			final boolean virtualColumn,
			final boolean isEncrypted,
			final boolean isKey,
			final boolean isIdentifier,
			final boolean lazyLoading,
			final int seqNo,
			final int adTableId,
			final String tableIdColumnName)
	{
		this.repository = repository;
		this.tableName = tableName;
		this.columnName = columnName;
		this.isUpdatable = isUpdatable;
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
		this.isIdentifier = isIdentifier;
		this.lazyLoading = lazyLoading;
		this.seqNo = seqNo;
		this.adTableId = adTableId;
		this.tableIdColumnName = tableIdColumnName;
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
