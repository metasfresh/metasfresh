package de.metas.impexp.parser;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;

import de.metas.impexp.format.ImpFormatColumn;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;

/**
 * A line from import file, which needs to be imported.
 *
 */
@EqualsAndHashCode
@ToString
public class ImpDataLine
{
	private final String lineStr;
	private final int fileLineNo;
	private final ImmutableList<ImpDataCell> cells;
	private final ErrorMessage parseError;

	@Builder
	private ImpDataLine(
			final int fileLineNo,
			@Nullable final String lineStr,
			@Nullable @Singular final ImmutableList<ImpDataCell> cells,
			@Nullable final ErrorMessage parseError)
	{
		this.fileLineNo = fileLineNo;
		this.lineStr = lineStr;

		if (parseError == null)
		{
			this.cells = cells;
			this.parseError = null;
		}
		else
		{
			this.cells = null;
			this.parseError = parseError;
		}
	}

	public int getFileLineNo()
	{
		return fileLineNo;
	}

	public String getLineString()
	{
		return lineStr;
	}

	public boolean hasErrors()
	{
		return parseError != null
				|| (cells != null && cells.stream().anyMatch(ImpDataCell::isCellError));
	}

	public String getErrorMessageAsStringOrNull()
	{
		return getErrorMessageAsStringOrNull(-1);
	}

	public String getErrorMessageAsStringOrNull(final int maxLength)
	{
		final int maxLengthEffective = maxLength > 0 ? maxLength : Integer.MAX_VALUE;

		final StringBuilder result = new StringBuilder();

		if (parseError != null)
		{
			result.append(parseError.getMessage());
		}

		if (cells != null)
		{
			for (final ImpDataCell cell : cells)
			{
				if (!cell.isCellError())
				{
					continue;
				}

				final String cellErrorMessage = cell.getCellErrorMessage().getMessage();

				if (result.length() > 0)
				{
					result.append("; ");
				}

				result.append(cellErrorMessage);

				if (result.length() >= maxLengthEffective)
				{
					break;
				}
			}
		}

		return result.length() > 0
				? StringUtils.trunc(result.toString(), maxLengthEffective)
				: null;
	}

	public List<Object> getJdbcValues(@NonNull final List<ImpFormatColumn> columns)
	{
		final int columnsCount = columns.size();

		if (parseError != null)
		{
			final ArrayList<Object> nulls = new ArrayList<>(columnsCount);
			for (int i = 0; i < columnsCount; i++)
			{
				nulls.add(null);
			}

			return nulls;
		}
		else
		{
			final ArrayList<Object> values = new ArrayList<>(columnsCount);

			final int cellsCount = cells.size();
			for (int i = 0; i < columnsCount; i++)
			{
				if (i < cellsCount)
				{
					values.add(cells.get(i).getJdbcValue());
				}
				else
				{
					values.add(null);
				}
			}

			return values;
		}
	}
}
