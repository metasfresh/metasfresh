package de.metas.impexp.parser;

import javax.annotation.Nullable;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * A cell of {@link ImpDataCell}.
 *
 */
@EqualsAndHashCode
@ToString
public class ImpDataCell
{
	public static ImpDataCell value(final Object value)
	{
		final ErrorMessage errorMessage = null;
		return new ImpDataCell(value, errorMessage);
	}

	public static ImpDataCell error(final ErrorMessage errorMessage)
	{
		final Object value = null;
		return new ImpDataCell(value, errorMessage);
	}

	private final Object value;
	private final ErrorMessage errorMessage;

	private ImpDataCell(
			@Nullable final Object value,
			@Nullable ErrorMessage errorMessage)
	{
		this.value = value;
		this.errorMessage = errorMessage;
	}

	/** @return true if the cell has some errors (i.e. {@link #getCellErrorMessage()} is not null) */
	public boolean isCellError()
	{
		return errorMessage != null;
	}

	/** @return cell error message or null */
	public ErrorMessage getCellErrorMessage()
	{
		return errorMessage;
	}

	public final Object getJdbcValue()
	{
		return value;
	}

}
