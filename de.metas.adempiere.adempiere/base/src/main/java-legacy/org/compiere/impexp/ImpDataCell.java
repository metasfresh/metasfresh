package org.compiere.impexp;

import java.sql.Timestamp;

import javax.annotation.Nullable;

import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.util.Check;
import lombok.NonNull;

/**
 * A cell of {@link ImpDataCell}.
 * 
 * @author tsa
 *
 */
public class ImpDataCell
{
	private final ImpFormatColumn impFormatColumn;
	private String value = "";
	private CellErrorMessage errorMessage = null;

	public ImpDataCell(@NonNull final ImpFormatColumn impFormatColumn)
	{
		this.impFormatColumn = impFormatColumn;
	}

	public ImpDataCell()
	{
		this.impFormatColumn = null;
	}

	public String getColumnName()
	{
		Check.assumeNotNull(impFormatColumn, "impFormatColumn not null");
		return impFormatColumn.getColumnName();
	}

	public boolean isEmpty()
	{
		return Check.isEmpty(value, false);
	}

	public boolean isEmptyOrZero()
	{
		if (isEmpty())
		{
			return true;
		}

		if (impFormatColumn != null && impFormatColumn.isNumber() && "0".equals(value))
		{
			return true;
		}

		if (impFormatColumn != null && impFormatColumn.isDate() && "00000000".equals(value))
		{
			return true;
		}

		return false;
	}

	public String getValueAsString()
	{
		return value;
	}

	/** @return true if the cell has some errors (i.e. {@link #getCellErrorMessage()} is not null) */
	public boolean isCellError()
	{
		return errorMessage != null;
	}

	/** @return cell error message or null */
	public CellErrorMessage getCellErrorMessage()
	{
		return errorMessage;
	}

	public void setValue(@Nullable final Object value)
	{
		String valueStr = value == null ? "" : value.toString();
		CellErrorMessage errorMessage = null;

		if (impFormatColumn != null)
		{
			try
			{
				valueStr = impFormatColumn.parse(valueStr);
			}
			catch (final Throwable ex)
			{
				errorMessage = CellErrorMessage.of(ex);
			}
		}

		this.value = valueStr;
		this.errorMessage = errorMessage;
	}

	public final String getValueAsSQL()
	{
		final String value = getValueAsString();
		if (value == null)
		{
			return "NULL";
		}

		Check.assumeNotNull(impFormatColumn, "impFormatColumn not null");
		if (impFormatColumn.isString())
		{
			return DB.TO_STRING(value);
		}
		else if (impFormatColumn.isDate())
		{
			final Timestamp ts = Env.parseTimestamp(value);
			return DB.TO_DATE(ts, false);
		}
		else
		{
			return value;
		}
	}

	public final String getColumnNameEqualsValueSql()
	{
		return new StringBuilder()
				.append(getColumnName())
				.append("=")
				.append(getValueAsSQL())
				.toString();
	}
}
