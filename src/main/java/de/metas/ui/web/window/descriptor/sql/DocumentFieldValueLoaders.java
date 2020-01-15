package de.metas.ui.web.window.descriptor.sql;

import java.awt.Color;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

import org.compiere.util.DisplayType;
import org.compiere.util.SecureEngine;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.ui.web.window.datatypes.ColorValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.Password;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.model.lookup.LabelsLookup;
import de.metas.util.Check;
import de.metas.util.IColorRepository;
import de.metas.util.MFColor;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Factory methods to create specific {@link DocumentFieldValueLoader} instances.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class DocumentFieldValueLoaders
{
	public static DocumentFieldValueLoader toString(final String sqlColumnName, final boolean encrypted)
	{
		if (encrypted)
		{
			return new EncryptedStringDocumentFieldValueLoader(sqlColumnName);
		}
		else
		{
			return new StringDocumentFieldValueLoader(sqlColumnName);
		}
	}

	public static DocumentFieldValueLoader toPassword(final String sqlColumnName, final boolean encrypted)
	{
		if (encrypted)
		{
			return new EncryptedPasswordDocumentFieldValueLoader(sqlColumnName);
		}
		else
		{
			return new PasswordDocumentFieldValueLoader(sqlColumnName);
		}
	}

	public static DocumentFieldValueLoader toByteArray(final String sqlColumnName, final boolean encrypted)
	{
		return new ByteArrayDocumentFieldValueLoader(sqlColumnName, encrypted);
	}

	public static DocumentFieldValueLoader toBoolean(final String sqlColumnName, final boolean encrypted)
	{
		return new BooleanDocumentFieldValueLoader(sqlColumnName, encrypted);
	}

	public static DocumentFieldValueLoader toJULDate(final String sqlColumnName, final boolean encrypted)
	{
		return new JULDateDocumentFieldValueLoader(sqlColumnName, encrypted);
	}

	public static DocumentFieldValueLoader toZonedDateTime(final String sqlColumnName, final boolean encrypted)
	{
		return new ZonedDateTimeDocumentFieldValueLoader(sqlColumnName, encrypted);
	}

	public static DocumentFieldValueLoader toInstant(final String sqlColumnName, final boolean encrypted)
	{
		return new InstantDocumentFieldValueLoader(sqlColumnName, encrypted);
	}

	public static DocumentFieldValueLoader toLocalDate(final String sqlColumnName, final boolean encrypted)
	{
		return new LocalDateDocumentFieldValueLoader(sqlColumnName, encrypted);
	}

	public static DocumentFieldValueLoader toLocalTime(final String sqlColumnName, final boolean encrypted)
	{
		return new LocalTimeDocumentFieldValueLoader(sqlColumnName, encrypted);
	}

	public static DocumentFieldValueLoader toBigDecimal(final String sqlColumnName, final boolean encrypted, final Integer precision)
	{
		if (precision != null)
		{
			return new BigDecimalWithPrecisionDocumentFieldValueLoader(sqlColumnName, encrypted, precision);
		}
		else
		{
			return new BigDecimalDocumentFieldValueLoader(sqlColumnName, encrypted);
		}
	}

	public static DocumentFieldValueLoader toInteger(final String sqlColumnName, final boolean encrypted)
	{
		return new IntegerDocumentFieldValueLoader(sqlColumnName, encrypted);
	}

	public static DocumentFieldValueLoader toLookupValue(
			@NonNull final String sqlColumnName,
			@NonNull final String sqlDisplayColumnName,
			final boolean numericKey)
	{
		if (Check.isEmpty(sqlDisplayColumnName, true))
		{
			throw new IllegalArgumentException("sqlDisplayColumnName shall not be null or empty");
		}

		if (numericKey)
		{
			return new IntegerLookupValueDocumentFieldValueLoader(sqlColumnName, sqlDisplayColumnName/* , sqlDescriptionColumnName */);
		}
		else
		{
			return new StringLookupValueDocumentFieldValueLoader(sqlColumnName, sqlDisplayColumnName/* , sqlDescriptionColumnName */);
		}
	}

	public static DocumentFieldValueLoader toLabelValues(final String sqlColumnName)
	{
		return new LabelsLookupValueDocumentFieldValueLoader(sqlColumnName);
	}

	public static DocumentFieldValueLoader toColor(final String sqlColumnName)
	{
		return new ColorDocumentFieldValueLoader(sqlColumnName);
	}

	//
	//
	//
	//
	//

	private static final transient Logger logger = LogManager.getLogger(DocumentFieldValueLoaders.class);

	private DocumentFieldValueLoaders()
	{
	}

	private static Object decrypt(final Object value)
	{
		if (value == null)
		{
			return null;
		}
		return SecureEngine.decrypt(value);
	}

	@Value
	private static final class StringDocumentFieldValueLoader implements DocumentFieldValueLoader
	{
		private final String sqlColumnName;

		@Override
		public Object retrieveFieldValue(final ResultSet rs, final boolean isDisplayColumnAvailable, final String adLanguage, final LookupDescriptor lookupDescriptor_NOTUSED) throws SQLException
		{
			final String value = rs.getString(sqlColumnName);
			return value;
		}
	}

	@Value
	private static final class EncryptedStringDocumentFieldValueLoader implements DocumentFieldValueLoader
	{
		private final String sqlColumnName;

		@Override
		public Object retrieveFieldValue(final ResultSet rs, final boolean isDisplayColumnAvailable, final String adLanguage, final LookupDescriptor lookupDescriptor_NOTUSED) throws SQLException
		{
			final String value = rs.getString(sqlColumnName);
			return decrypt(value);
		}
	}

	@Value
	private static final class PasswordDocumentFieldValueLoader implements DocumentFieldValueLoader
	{
		private final String sqlColumnName;

		@Override
		public Password retrieveFieldValue(final ResultSet rs, final boolean isDisplayColumnAvailable, final String adLanguage, final LookupDescriptor lookupDescriptor_NOTUSED) throws SQLException
		{
			final String value = rs.getString(sqlColumnName);
			return Password.ofNullableString(value);
		}
	}

	@Value
	private static final class EncryptedPasswordDocumentFieldValueLoader implements DocumentFieldValueLoader
	{
		private final String sqlColumnName;

		@Override
		public Password retrieveFieldValue(final ResultSet rs, final boolean isDisplayColumnAvailable, final String adLanguage, final LookupDescriptor lookupDescriptor_NOTUSED) throws SQLException
		{
			final String value = rs.getString(sqlColumnName);
			final Object valueDecrypted = decrypt(value);
			if (valueDecrypted == null)
			{
				return null;
			}
			return Password.ofNullableString(valueDecrypted.toString());
		}
	}

	@Value
	private static final class ByteArrayDocumentFieldValueLoader implements DocumentFieldValueLoader
	{
		private final String sqlColumnName;
		private final boolean encrypted;

		@Override
		public byte[] retrieveFieldValue(final ResultSet rs, final boolean isDisplayColumnAvailable, final String adLanguage, final LookupDescriptor lookupDescriptor_NOTUSED) throws SQLException
		{
			final Object valueObj = rs.getObject(sqlColumnName);
			final byte[] valueBytes;
			if (rs.wasNull())
			{
				valueBytes = null;
			}
			else if (valueObj instanceof Clob)
			{
				final Clob lob = (Clob)valueObj;
				final long length = lob.length();
				valueBytes = lob.getSubString(1, (int)length).getBytes(StandardCharsets.UTF_8);
			}
			else if (valueObj instanceof Blob)
			{
				final Blob lob = (Blob)valueObj;
				final long length = lob.length();
				valueBytes = lob.getBytes(1, (int)length);
			}
			else if (valueObj instanceof String)
			{
				valueBytes = ((String)valueObj).getBytes(StandardCharsets.UTF_8);
			}
			else if (valueObj instanceof byte[])
			{
				valueBytes = (byte[])valueObj;
			}
			else
			{
				logger.warn("Unknown LOB value '{}' for {}. Considering it null.", valueObj, sqlColumnName);
				valueBytes = null;
			}
			//
			return valueBytes;
		}
	}

	@Value
	private static final class BooleanDocumentFieldValueLoader implements DocumentFieldValueLoader
	{
		private final String sqlColumnName;
		private final boolean encrypted;

		@Override
		public Boolean retrieveFieldValue(final ResultSet rs, final boolean isDisplayColumnAvailable, final String adLanguage, final LookupDescriptor lookupDescriptor_NOTUSED) throws SQLException
		{
			String valueStr = rs.getString(sqlColumnName);
			if (encrypted)
			{
				valueStr = valueStr == null ? null : decrypt(valueStr).toString();
			}

			return DisplayType.toBoolean(valueStr);
		}
	}

	@Value
	private static final class JULDateDocumentFieldValueLoader implements DocumentFieldValueLoader
	{
		private final String sqlColumnName;
		private final boolean encrypted;

		@Override
		public Object retrieveFieldValue(final ResultSet rs, final boolean isDisplayColumnAvailable, final String adLanguage, final LookupDescriptor lookupDescriptor_NOTUSED) throws SQLException
		{
			final Timestamp valueTS = rs.getTimestamp(sqlColumnName);
			final java.util.Date value = valueTS == null ? null : new java.util.Date(valueTS.getTime());
			return encrypted ? decrypt(value) : value;
		}
	}

	@Value
	private static final class ZonedDateTimeDocumentFieldValueLoader implements DocumentFieldValueLoader
	{
		private final String sqlColumnName;
		private final boolean encrypted;

		@Override
		public Object retrieveFieldValue(final ResultSet rs, final boolean isDisplayColumnAvailable, final String adLanguage, final LookupDescriptor lookupDescriptor_NOTUSED) throws SQLException
		{
			final ZonedDateTime value = TimeUtil.asZonedDateTime(rs.getTimestamp(sqlColumnName));
			return encrypted ? decrypt(value) : value;
		}
	}

	@Value
	private static final class InstantDocumentFieldValueLoader implements DocumentFieldValueLoader
	{
		private final String sqlColumnName;
		private final boolean encrypted;

		@Override
		public Object retrieveFieldValue(final ResultSet rs, final boolean isDisplayColumnAvailable, final String adLanguage, final LookupDescriptor lookupDescriptor_NOTUSED) throws SQLException
		{
			final Instant value = TimeUtil.asInstant(rs.getTimestamp(sqlColumnName));
			return encrypted ? decrypt(value) : value;
		}
	}

	@Value
	private static final class LocalDateDocumentFieldValueLoader implements DocumentFieldValueLoader
	{
		private final String sqlColumnName;
		private final boolean encrypted;

		@Override
		public Object retrieveFieldValue(final ResultSet rs, final boolean isDisplayColumnAvailable, final String adLanguage, final LookupDescriptor lookupDescriptor_NOTUSED) throws SQLException
		{
			final LocalDate value = TimeUtil.asLocalDate(rs.getTimestamp(sqlColumnName));
			return encrypted ? decrypt(value) : value;
		}
	}

	@Value
	private static final class LocalTimeDocumentFieldValueLoader implements DocumentFieldValueLoader
	{
		private final String sqlColumnName;
		private final boolean encrypted;

		@Override
		public Object retrieveFieldValue(final ResultSet rs, final boolean isDisplayColumnAvailable, final String adLanguage, final LookupDescriptor lookupDescriptor_NOTUSED) throws SQLException
		{
			final LocalTime value = TimeUtil.asLocalTime(rs.getTimestamp(sqlColumnName));
			return encrypted ? decrypt(value) : value;
		}
	}

	@Value
	private static final class BigDecimalDocumentFieldValueLoader implements DocumentFieldValueLoader
	{
		private final String sqlColumnName;
		private final boolean encrypted;

		@Override
		public Object retrieveFieldValue(final ResultSet rs, final boolean isDisplayColumnAvailable, final String adLanguage, final LookupDescriptor lookupDescriptor_NOTUSED) throws SQLException
		{
			final BigDecimal value = rs.getBigDecimal(sqlColumnName);
			return encrypted ? decrypt(value) : value;
		}
	}

	@Value
	private static final class BigDecimalWithPrecisionDocumentFieldValueLoader implements DocumentFieldValueLoader
	{
		private final String sqlColumnName;
		private final boolean encrypted;
		private final int precision;

		@Override
		public Object retrieveFieldValue(final ResultSet rs, final boolean isDisplayColumnAvailable, final String adLanguage, final LookupDescriptor lookupDescriptor_NOTUSED) throws SQLException
		{
			BigDecimal value = rs.getBigDecimal(sqlColumnName);
			value = value == null ? null : NumberUtils.setMinimumScale(value, precision);
			return encrypted ? decrypt(value) : value;
		}
	}

	@Value
	private static final class IntegerDocumentFieldValueLoader implements DocumentFieldValueLoader
	{
		private final String sqlColumnName;
		private final boolean encrypted;

		@Override
		public Object retrieveFieldValue(final ResultSet rs, final boolean isDisplayColumnAvailable, final String adLanguage, final LookupDescriptor lookupDescriptor_NOTUSED) throws SQLException
		{
			final int valueInt = rs.getInt(sqlColumnName);
			final Integer value = rs.wasNull() ? null : valueInt;
			return encrypted ? decrypt(value) : value;
		}
	}

	@Value
	private static final class IntegerLookupValueDocumentFieldValueLoader implements DocumentFieldValueLoader
	{
		@NonNull
		private final String sqlColumnName;

		@NonNull
		private final String sqlDisplayColumnName;

		@Override
		public IntegerLookupValue retrieveFieldValue(
				final ResultSet rs,
				final boolean isDisplayColumnAvailable,
				final String adLanguage,
				final LookupDescriptor lookupDescriptor_NOTUSED) throws SQLException
		{
			final int id = rs.getInt(sqlColumnName);
			if (rs.wasNull())
			{
				return null;
			}

			if (isDisplayColumnAvailable)
			{
				final DisplayNameAndDescription result = DocumentFieldValueLoaders.extractDisplayNameAndDescription(
						rs,
						sqlDisplayColumnName,
						adLanguage);

				return IntegerLookupValue.builder()
						.id(id)
						.displayName(result.getDisplayName())
						.description(result.getDescription())
						.build();
			}
			else
			{
				return IntegerLookupValue.unknown(id);
			}
		}
	}

	@Value
	private static final class StringLookupValueDocumentFieldValueLoader implements DocumentFieldValueLoader
	{
		@NonNull
		private final String sqlColumnName;

		@NonNull
		private final String sqlDisplayColumnName;

		@Override
		public StringLookupValue retrieveFieldValue(
				@NonNull final ResultSet rs,
				final boolean isDisplayColumnAvailable,
				final String adLanguage,
				final LookupDescriptor lookupDescriptor_NOTUSED) throws SQLException
		{
			final String key = rs.getString(sqlColumnName);
			if (rs.wasNull())
			{
				return null;
			}

			if (isDisplayColumnAvailable)
			{
				final DisplayNameAndDescription result = DocumentFieldValueLoaders.extractDisplayNameAndDescription(
						rs,
						sqlDisplayColumnName,
						adLanguage);

				return StringLookupValue.builder()
						.id(key)
						.displayName(result.getDisplayName())
						.description(result.getDescription())
						.build();
			}
			else
			{
				return StringLookupValue.unknown(key);
			}
		}
	}

	private static DisplayNameAndDescription extractDisplayNameAndDescription(
			@NonNull final ResultSet rs,
			final String sqlDisplayColumnName,
			final String adLanguage) throws SQLException
	{
		final ITranslatableString displayName;
		final ITranslatableString description;

		final Array array = rs.getArray(sqlDisplayColumnName);
		if (array == null)
		{
			displayName = TranslatableStrings.empty();
			description = TranslatableStrings.empty();
		}
		else
		{
			final String[] nameAndDescription = (String[])array.getArray();
			displayName = TranslatableStrings.singleLanguage(adLanguage, nameAndDescription[0]);

			final boolean hasDescription = nameAndDescription.length > 1;
			if (hasDescription)
			{
				description = TranslatableStrings.singleLanguage(adLanguage, nameAndDescription[1]);
			}
			else
			{
				description = TranslatableStrings.empty();
			}
		}
		return new DisplayNameAndDescription(displayName, description);
	}

	@Value
	private static class DisplayNameAndDescription
	{
		@NonNull
		ITranslatableString displayName;

		@NonNull
		ITranslatableString description;
	}

	@Value
	private static class LabelsLookupValueDocumentFieldValueLoader implements DocumentFieldValueLoader
	{
		private final String sqlColumnName;
		private final String sqlDisplayColumnName = null;

		@Override
		public LookupValuesList retrieveFieldValue(final ResultSet rs, final boolean isDisplayColumnAvailable, final String adLanguage, final LookupDescriptor lookupDescriptor) throws SQLException
		{
			// FIXME: atm we are avoiding NPE by returning EMPTY.
			// We need to make sure we always get a lookup...
			// Also, please note this method is called with null lookup when loading view row, even though the labels field is not part of the grid.
			// This might be a performance penalty too, so:
			// * make sure never load labels in grid mode
			// * or, load labels for the whole page, all together (avoiding SQL N+1 issue)
			if (lookupDescriptor == null)
			{
				return LookupValuesList.EMPTY;
			}

			final LabelsLookup lookup = LabelsLookup.cast(lookupDescriptor);

			final String linkColumnName = lookup.getLinkColumnName();
			final int linkId = rs.getInt(linkColumnName);
			return lookup.retrieveExistingValues(linkId);
		}
	}

	@Builder
	@Value
	private static class ColorDocumentFieldValueLoader implements DocumentFieldValueLoader
	{
		@NonNull
		private final String sqlColumnName;

		@Override
		public Object retrieveFieldValue(ResultSet rs, boolean isDisplayColumnAvailable, String adLanguage, LookupDescriptor lookupDescriptor) throws SQLException
		{
			final int adColorId = rs.getInt(sqlColumnName);
			if (adColorId <= 0)
			{
				return null;
			}

			final IColorRepository colorRepository = Services.get(IColorRepository.class);
			final MFColor color = colorRepository.getColorById(adColorId);
			if (color == null)
			{
				return null;
			}

			final Color awtColor = color.toFlatColor().getFlatColor();
			return ColorValue.ofRGB(awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue());
		}

	}
}
