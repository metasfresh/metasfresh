package de.metas.impexp.format;

import java.util.List;

import com.google.common.collect.ImmutableList;

import de.metas.util.Check;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

/**
 * Import Format Definition
 */
public final class ImpFormat
{
	@Getter
	private final ImpFormatId id;

	@Getter
	private final String name;
	@Getter
	private final ImpFormatType formatType;
	@Getter
	private final boolean multiLine;
	@Getter
	private final boolean manualImport;

	/** The Table to be imported */
	@Getter
	private final ImportTableDescriptor importTableDescriptor;

	@Getter
	private final ImmutableList<ImpFormatColumn> columns;

	@Builder
	private ImpFormat(
			@NonNull final ImpFormatId id,
			@NonNull final String name,
			@NonNull final ImpFormatType formatType,
			final boolean multiLine,
			final boolean manualImport,
			@NonNull final ImportTableDescriptor importTableDescriptor,
			@NonNull @Singular final List<ImpFormatColumn> columns)
	{
		Check.assumeNotEmpty(name, "name is not empty");
		Check.assumeNotEmpty(columns, "columns is not empty");

		this.id = id;
		this.name = name;
		this.formatType = formatType;
		this.multiLine = multiLine;
		this.manualImport = manualImport;
		this.importTableDescriptor = importTableDescriptor;
		this.columns = ImmutableList.copyOf(columns);
	}

	public String getImportTableName()
	{
		return getImportTableDescriptor().getTableName();
	}

	public String getImportKeyColumnName()
	{
		return getImportTableDescriptor().getKeyColumnName();
	}
}
