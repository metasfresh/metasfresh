package de.metas.copy_with_details.template;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.copy.ValueToCopyResolveContext;
import org.compiere.model.copy.ValueToCopyResolved;
import org.compiere.model.copy.ValueToCopyType;

import javax.annotation.Nullable;
import java.util.Optional;

@EqualsAndHashCode
@ToString
public final class CopyTemplate
{
	@Getter @NonNull private final String tableName;
	@Getter @Nullable private final String keyColumnName;
	@NonNull private final ImmutableMap<String, CopyTemplateColumn> columnsByColumnName;

	@Getter @NonNull private final ImmutableList<CopyTemplate> childTemplates;

	//
	// Child/included template specific properties
	@Getter @Nullable private final String linkColumnName;
	@Getter @NonNull private final ImmutableList<String> orderByColumnNames;

	@Builder
	private CopyTemplate(
			@NonNull final String tableName,
			@Nullable final String keyColumnName,
			@NonNull @Singular final ImmutableList<CopyTemplateColumn> columns,
			@NonNull @Singular final ImmutableList<CopyTemplate> childTemplates,
			//
			@Nullable final String linkColumnName,
			@NonNull @Singular final ImmutableList<String> orderByColumnNames)
	{
		this.tableName = tableName;
		this.keyColumnName = keyColumnName;
		this.childTemplates = childTemplates;
		this.linkColumnName = linkColumnName;
		this.orderByColumnNames = orderByColumnNames;
		this.columnsByColumnName = Maps.uniqueIndex(columns, CopyTemplateColumn::getColumnName);
	}

	private CopyTemplateColumn getColumn(final String columnName)
	{
		final CopyTemplateColumn column = columnsByColumnName.get(columnName);
		if (column == null)
		{
			throw new AdempiereException("Column `" + columnName + "` not found in " + this);
		}
		return column;
	}

	private Optional<CopyTemplateColumn> getColumnIfExists(final String columnName)
	{
		return Optional.ofNullable(columnsByColumnName.get(columnName));
	}

	public ValueToCopyResolved getValueToCopy(@NonNull ValueToCopyResolveContext context)
	{
		return getColumn(context.getColumnName()).getValueToCopy().resolve(context);
	}

	public Optional<ValueToCopyType> getValueToCopyType(@NonNull final String columnName)
	{
		return getColumnIfExists(columnName).map(column -> column.getValueToCopy().getType());
	}

	public boolean hasChildTableName(@NonNull final String tableName)
	{
		return childTemplates.stream().anyMatch(childTemplate -> childTemplate.getTableName().equals(tableName));
	}

}
