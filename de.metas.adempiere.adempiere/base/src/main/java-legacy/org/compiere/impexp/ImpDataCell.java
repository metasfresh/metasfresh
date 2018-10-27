package org.compiere.impexp;

import java.sql.Timestamp;

import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.util.Check;

/**
 * A cell of {@link ImpDataCell}.
 * 
 * @author tsa
 *
 */
public class ImpDataCell
{
	private final ImpFormatRow impFormatRow;
	private String value = "";
	private CellErrorMessage errorMessage = null;

	public ImpDataCell(final ImpFormatRow impFormatRow)
	{
		super();
		this.impFormatRow = impFormatRow;
	}

	public String getColumnName()
	{
		Check.assumeNotNull(impFormatRow, "impFormatRow not null");
		return impFormatRow.getColumnName();
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

		if (impFormatRow != null && impFormatRow.isNumber() && "0".equals(value))
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

	public void setValue(final Object value)
	{
		String valueStr = value == null ? "" : value.toString();
		CellErrorMessage errorMessage = null;

		if (impFormatRow != null)
		{
			try
			{
				valueStr = impFormatRow.parse(valueStr);
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

		Check.assumeNotNull(impFormatRow, "impFormatRow not null");
		if (impFormatRow.isString())
		{
			return DB.TO_STRING(value);
		}
		else if (impFormatRow.isDate())
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
