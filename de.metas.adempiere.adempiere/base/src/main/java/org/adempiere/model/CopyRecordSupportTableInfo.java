/**
 *
 */
package org.adempiere.model;

import java.util.List;

import com.google.common.collect.ImmutableList;

import de.metas.i18n.ITranslatableString;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
public class CopyRecordSupportTableInfo
{
	/** Translated name */
	private final ITranslatableString name;
	private final String tableName;
	private final String linkColumnName;
	private final String parentTableName;
	private final List<String> orderByColumnNames;

	@Builder
	private CopyRecordSupportTableInfo(
			@NonNull final ITranslatableString name,
			final String tableName,
			final String linkColumnName,
			final String parentTableName,
			final List<String> orderByColumnNames)
	{
		this.name = name;
		this.tableName = tableName;
		this.linkColumnName = linkColumnName;
		this.parentTableName = parentTableName;
		this.orderByColumnNames = orderByColumnNames != null ? ImmutableList.copyOf(orderByColumnNames) : ImmutableList.of();
	}

	public String getName(final String adLanguage)
	{
		return name.translate(adLanguage);
	}
}
