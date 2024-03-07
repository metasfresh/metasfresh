package org.adempiere.exceptions;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.util.Check;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Collection;

/**
 * Throwed when there are some fields that are mandatory but unfilled.
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 */
public class FillMandatoryException extends AdempiereException
{
	public static final AdMessageKey MSG_FillMandatory = AdMessageKey.of("FillMandatory");

	/**
	 * @param translated if true we consider that fields are already translated
	 */
	public FillMandatoryException(final boolean translated, final String... fields)
	{
		super(buildMessage(translated, fields));
	}

	/**
	 * @param translated if true we consider that fields are already translated
	 */
	public FillMandatoryException(final boolean translated, final Collection<String> fields)
	{
		super(buildMessage(translated, fields));
	}

	/**
	 * NOTE: fields are considered not translated and they will be translated
	 */
	public FillMandatoryException(final String... fields)
	{
		super(buildMessage(fields));
	}

	public static ITranslatableString buildMessage(
			@Nullable final String... fields)
	{
		return buildMessage(false, fields);
	}

	private static ITranslatableString buildMessage(
			final boolean fieldsAreAlreadyTranslated,
			@Nullable final String... fields)
	{
		if (fields == null || fields.length == 0)
		{
			return TranslatableStrings.adMessage(MSG_FillMandatory);
		}

		final TranslatableStringBuilder builder = TranslatableStrings.builder()
				.appendADMessage(MSG_FillMandatory);

		boolean firstField = true;
		for (final String field : fields)
		{
			if (Check.isBlank(field))
			{
				continue;
			}

			if (firstField)
			{
				builder.append(" ");
			}
			else
			{
				builder.append(", ");
			}

			if (fieldsAreAlreadyTranslated)
			{
				builder.append(field);
			}
			else
			{
				builder.appendADElement(field);
			}

			firstField = false;
		}

		return builder.build();
	}

	private static ITranslatableString buildMessage(
			final boolean fieldsAreAlreadyTranslated,
			@Nullable final Collection<String> fields)
	{
		final String[] fieldsArr = fields != null && !fields.isEmpty()
				? fields.toArray(new String[0])
				: new String[] {};
		return buildMessage(fieldsAreAlreadyTranslated, fieldsArr);
	}

	public static <T> T assumeNotNull(@Nullable final T objectToCheck, @NonNull final String... fields)
	{
		if (objectToCheck == null)
		{
			throw new FillMandatoryException(fields);
		}
		return objectToCheck;
	}
}
