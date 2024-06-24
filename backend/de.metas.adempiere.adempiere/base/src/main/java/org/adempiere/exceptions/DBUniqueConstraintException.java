/**
 *
 */
package org.adempiere.exceptions;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.compiere.model.I_AD_Index_Table;
import org.compiere.model.MIndexTable;
import org.compiere.util.DB;

import java.sql.SQLException;

/**
 * Unique Constraint Exception
 *
 * @author Teo Sarca, teo.sarca@gmail.com
 */
public class DBUniqueConstraintException extends DBException
{
	private static final AdMessageKey MSG_SaveErrorNotUnique = AdMessageKey.of("SaveErrorNotUnique");

	private String constraintName = null;
	private MIndexTable index = null;

	public DBUniqueConstraintException(@NonNull final Throwable e)
	{
		super(e);
		setConstraintInfo(e);
	}

	public DBUniqueConstraintException(@NonNull final MIndexTable index)
	{
		super("");// message will be built on demand
		this.constraintName = index.getName();
		this.index = index;
	}

	public DBUniqueConstraintException(@NonNull final SQLException e, String sql, Object[] params)
	{
		super(e, sql, params);
		setConstraintInfo(e);
	}

	private void setConstraintInfo(@NonNull final Throwable e)
	{
		if (!isUniqueContraintError(e))
		{
			return; // not unique constraint, nothing to do
		}
		else if (DB.isPostgreSQL())
		{
			this.constraintName = extractConstraintNameFromPostgreSQLErrorMessage(e.getMessage());
		}

		//
		if (!Check.isBlank(this.constraintName))
		{
			this.index = MIndexTable.getByNameIgnoringCase(this.constraintName);
		}

		if (this.index != null)
		{
			markAsUserValidationError();
		}
	}

	private static String extractConstraintNameFromPostgreSQLErrorMessage(final String msg)
	{
		String constraintName = StringUtils.trimBlankToNull(parseConstraintName(msg, "\"", "\""));
		if (constraintName != null)
		{
			return constraintName;
		}

		// Check for german errors (e.g. Bitte Informationen �ndern.
		// org.postgresql.util.PSQLException: FEHLER: duplizierter Schl�ssel
		// verletzt Unique-Constraint �bpl_billto_unique�)
		return StringUtils.trimBlankToNull(parseConstraintName(msg, "»", "«"));
	}

	@Override
	protected ITranslatableString buildMessage()
	{
		if (index != null)
		{
			final ITranslatableString errorMsg = index.getErrorMsgTrl();
			if (!TranslatableStrings.isBlank(errorMsg))
			{
				final TranslatableStringBuilder message = TranslatableStrings.builder();

				final ITranslatableString indexErrorMsg = index.get_ModelTranslationMap().getColumnTrl(MIndexTable.COLUMNNAME_ErrorMsg, index.getErrorMsg());
				message.append(indexErrorMsg);

				if (Services.get(IDeveloperModeBL.class).isEnabled())
				{
					message.append(" (AD_Index_Table:" + index.getName() + ")");
				}

				return message.build();
			}
			else
			{
				return TranslatableStrings.builder().appendADMessage(MSG_SaveErrorNotUnique).append(" ").appendADElement(I_AD_Index_Table.COLUMNNAME_AD_Index_Table_ID).append(": ").append(index.getName()).build();
			}
		}
		else if (!Check.isBlank(constraintName))
		{
			return TranslatableStrings.builder().appendADMessage(MSG_SaveErrorNotUnique).append(": ").append(constraintName).build();
		}
		else
		{
			final TranslatableStringBuilder message = TranslatableStrings.builder().appendADMessage(MSG_SaveErrorNotUnique);
			final Throwable cause = getCause();
			if (cause != null)
			{
				message.append(": ").append(AdempiereException.extractMessageTrl(cause));
			}
			return message.build();
		}
	}

	private static String parseConstraintName(String sqlException, String quoteStart, String quoteEnd)
	{
		int i = sqlException.indexOf(quoteStart);
		if (i >= 0)
		{
			int i2 = sqlException.indexOf(quoteEnd, i + quoteStart.length());
			if (i2 >= 0)
			{
				return sqlException.substring(i + quoteStart.length(), i2);
			}
		}
		return null;
	}

}
