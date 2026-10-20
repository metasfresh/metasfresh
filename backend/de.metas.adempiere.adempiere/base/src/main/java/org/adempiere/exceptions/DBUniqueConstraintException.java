/**
 *
 */
package org.adempiere.exceptions;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.compiere.model.MIndexTable;
import org.compiere.util.DB;

import javax.annotation.Nullable;
import java.sql.SQLException;

import static de.metas.util.Check.isEmpty;

/**
 * Unique Constraint Exception
 *
 * @author Teo Sarca, teo.sarca@gmail.com
 */
public class DBUniqueConstraintException extends DBException
{
	public static final String DB_UNIQUE_CONSTRAINT_ERROR_CODE = "DBUniqueConstraint";
	private static final long serialVersionUID = -1436774241410586947L;

	private String constraintName = null;
	private MIndexTable index = null;

	public DBUniqueConstraintException(final Throwable e)
	{
		super("@SaveErrorNotUnique@:" + e.getLocalizedMessage(), e);
		setConstraintInfo(e);
	}

	public DBUniqueConstraintException(final MIndexTable index)
	{
		super("@SaveErrorNotUnique@ @AD_Index_Table_ID@:" + index.getName());
		this.constraintName = index.getName();
		this.index = index;
	}

	public DBUniqueConstraintException(final SQLException e, final String sql, final Object[] params)
	{
		super(e, sql, params);
		setConstraintInfo(e);
	}

	public static String getConstraintName(final SQLException e)
	{
		return null;
	}

	private void setConstraintInfo(final Throwable e)
	{
		if (!isUniqueContraintError(e))
		{
			return; // not unique constraint, nothing to do
		}
		else if (DB.isPostgreSQL())
		{
			//
			//
			final String msg = e.getMessage();
			this.constraintName = parseConstraintName(msg, "\"", "\"");
			// Check for german errors (e.g. Bitte Informationen �ndern.
			// org.postgresql.util.PSQLException: FEHLER: duplizierter Schl�ssel
			// verletzt Unique-Constraint �bpl_billto_unique�)
			if (Check.isEmpty(this.constraintName))
			{
				this.constraintName = parseConstraintName(msg, "»", "«");
			}
		}
		else
		{
			// FIXME implement for Oracle
		}
		//
		if (!Check.isEmpty(this.constraintName, true))
		{
			this.index = MIndexTable.getByNameIgnoringCase(this.constraintName);
		}
	}

	@Override
	protected ITranslatableString buildMessage()
	{
		if (index != null && !isEmpty(index.getErrorMsg(), true))
		{
			final TranslatableStringBuilder message = TranslatableStrings.builder();

			final ITranslatableString indexErrorMsg = index.get_ModelTranslationMap().getColumnTrl(MIndexTable.COLUMNNAME_ErrorMsg, index.getErrorMsg());
			message.append(indexErrorMsg);

			if (Services.get(IDeveloperModeBL.class).isEnabled())
			{
				message.append("(AD_Index_Table:" + index.getName() + ")");
			}

			return message.build();
		}
		else
		{
			return super.buildMessage();
		}
	}

	private static String parseConstraintName(final String sqlException, final String quoteStart, final String quoteEnd)
	{
		final int i = sqlException.indexOf(quoteStart);
		if (i >= 0)
		{
			final int i2 = sqlException.indexOf(quoteEnd, i + quoteStart.length());
			if (i2 >= 0)
			{
				return sqlException.substring(i + quoteStart.length(), i2);
			}
		}
		return null;
	}

	@Nullable
	@Override
	public String getErrorCode()
	{
		return DB_UNIQUE_CONSTRAINT_ERROR_CODE;
	}

}
