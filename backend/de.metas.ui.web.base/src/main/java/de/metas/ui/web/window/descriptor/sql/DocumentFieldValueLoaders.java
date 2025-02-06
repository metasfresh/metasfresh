package de.metas.ui.web.window.descriptor.sql;

import com.google.common.collect.ImmutableList;
import de.metas.logging.LogManager;
import de.metas.ui.web.window.datatypes.ColorValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.Password;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.model.lookup.LabelsLookup;
import de.metas.util.Check;
import de.metas.util.ColorId;
import de.metas.util.IColorRepository;
import de.metas.util.MFColor;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.compiere.util.DisplayType;
import org.compiere.util.SecureEngine;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.awt.*;
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
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.stream.Stream;

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

	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	public static DocumentFieldValueLoader toBigDecimal(final String sqlColumnName, final boolean encrypted, @NonNull final OptionalInt minPrecision)
	{
		if (minPrecision.isPresent())
		{
			return new BigDecimalWithPrecisionDocumentFieldValueLoader(sqlColumnName, encrypted, minPrecision.getAsInt());
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

	public static DocumentFieldValueLoader toLabelValues(@NonNull final String sqlValuesColumn)
	{
		return new LoadLabelsValues(sqlValuesColumn);
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

	private static final Logger logger = LogManager.getLogger(DocumentFieldValueLoaders.class);

	private DocumentFieldValueLoaders()
	{
	}

	@Nullable
	private static Object decrypt(@Nullable final Object value)
	{
		if (value == null)
		{
			return null;
		}
		return SecureEngine.decrypt(value);
	}

	@Value
	private static class StringDocumentFieldValueLoader implements DocumentFieldValueLoader
	{
		String sqlColumnName;

		@Override
		public Object retrieveFieldValue(final ResultSet rs, final boolean isDisplayColumnAvailable, final String adLanguage, final LookupDescriptor lookupDescriptor_NOTUSED) throws SQLException
		{
			return rs.getString(sqlColumnName);
		}
	}

	@Value
	private static class EncryptedStringDocumentFieldValueLoader implements DocumentFieldValueLoader
	{
		String sqlColumnName;

		@Override
		@Nullable
		public Object retrieveFieldValue(final ResultSet rs, final boolean isDisplayColumnAvailable, final String adLanguage, final LookupDescriptor lookupDescriptor_NOTUSED) throws SQLException
		{
			final String value = rs.getString(sqlColumnName);
			return decrypt(value);
		}
	}

	@Value
	private static class PasswordDocumentFieldValueLoader implements DocumentFieldValueLoader
	{
		String sqlColumnName;

		@Override
		public Password retrieveFieldValue(final ResultSet rs, final boolean isDisplayColumnAvailable, final String adLanguage, final LookupDescriptor lookupDescriptor_NOTUSED) throws SQLException
		{
			final String value = rs.getString(sqlColumnName);
			return Password.ofNullableString(value);
		}
	}

	@Value
	private static class EncryptedPasswordDocumentFieldValueLoader implements DocumentFieldValueLoader
	{
		String sqlColumnName;

		@Override
		@Nullable
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
	private static class ByteArrayDocumentFieldValueLoader implements DocumentFieldValueLoader
	{
		String sqlColumnName;
		boolean encrypted;

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
	private static class BooleanDocumentFieldValueLoader implements DocumentFieldValueLoader
	{
		String sqlColumnName;
		boolean encrypted;

		@Override
		public Boolean retrieveFieldValue(final ResultSet rs, final boolean isDisplayColumnAvailable, final String adLanguage, final LookupDescriptor lookupDescriptor_NOTUSED) throws SQLException
		{
			String valueStr = rs.getString(sqlColumnName);
			if (encrypted)
			{
				if (valueStr != null)
				{
					final Object valueDecrypt = decrypt(valueStr);
					valueStr = valueDecrypt != null ? valueDecrypt.toString() : null;
				}
			}

			return DisplayType.toBoolean(valueStr);
		}
	}

	@Value
	private static class JULDateDocumentFieldValueLoader implements DocumentFieldValueLoader
	{
		String sqlColumnName;
		boolean encrypted;

		@Override
		public Object retrieveFieldValue(final ResultSet rs, final boolean isDisplayColumnAvailable, final String adLanguage, final LookupDescriptor lookupDescriptor_NOTUSED) throws SQLException
		{
			final Timestamp valueTS = rs.getTimestamp(sqlColumnName);
			final java.util.Date value = valueTS == null ? null : new java.util.Date(valueTS.getTime());
			return encrypted ? decrypt(value) : value;
		}
	}

	@Value
	private static class ZonedDateTimeDocumentFieldValueLoader implements DocumentFieldValueLoader
	{
		String sqlColumnName;
		boolean encrypted;

		@Override
		public Object retrieveFieldValue(final ResultSet rs, final boolean isDisplayColumnAvailable, final String adLanguage, final LookupDescriptor lookupDescriptor_NOTUSED) throws SQLException
		{
			final ZonedDateTime value = TimeUtil.asZonedDateTime(rs.getTimestamp(sqlColumnName));
			return encrypted ? decrypt(value) : value;
		}
	}

	@Value
	private static class InstantDocumentFieldValueLoader implements DocumentFieldValueLoader
	{
		String sqlColumnName;
		boolean encrypted;

		@Override
		public Object retrieveFieldValue(final ResultSet rs, final boolean isDisplayColumnAvailable, final String adLanguage, final LookupDescriptor lookupDescriptor_NOTUSED) throws SQLException
		{
			final Instant value = TimeUtil.asInstant(rs.getTimestamp(sqlColumnName));
			return encrypted ? decrypt(value) : value;
		}
	}

	@Value
	private static class LocalDateDocumentFieldValueLoader implements DocumentFieldValueLoader
	{
		String sqlColumnName;
		boolean encrypted;

		@Override
		public Object retrieveFieldValue(final ResultSet rs, final boolean isDisplayColumnAvailable, final String adLanguage, final LookupDescriptor lookupDescriptor_NOTUSED) throws SQLException
		{
			final LocalDate value = TimeUtil.asLocalDate(rs.getTimestamp(sqlColumnName));
			return encrypted ? decrypt(value) : value;
		}
	}

	@Value
	private static class LocalTimeDocumentFieldValueLoader implements DocumentFieldValueLoader
	{
		String sqlColumnName;
		boolean encrypted;

		@Override
		public Object retrieveFieldValue(final ResultSet rs, final boolean isDisplayColumnAvailable, final String adLanguage, final LookupDescriptor lookupDescriptor_NOTUSED) throws SQLException
		{
			final LocalTime value = TimeUtil.asLocalTime(rs.getTimestamp(sqlColumnName));
			return encrypted ? decrypt(value) : value;
		}
	}

	@Value
	private static class BigDecimalDocumentFieldValueLoader implements DocumentFieldValueLoader
	{
		String sqlColumnName;
		boolean encrypted;

		@Override
		public Object retrieveFieldValue(final ResultSet rs, final boolean isDisplayColumnAvailable, final String adLanguage, final LookupDescriptor lookupDescriptor_NOTUSED) throws SQLException
		{
			final BigDecimal value = rs.getBigDecimal(sqlColumnName);
			return encrypted ? decrypt(value) : value;
		}
	}

	@Value
	private static class BigDecimalWithPrecisionDocumentFieldValueLoader implements DocumentFieldValueLoader
	{
		String sqlColumnName;
		boolean encrypted;
		int minPrecision;

		@Override
		public Object retrieveFieldValue(final ResultSet rs, final boolean isDisplayColumnAvailable, final String adLanguage, final LookupDescriptor lookupDescriptor_NOTUSED) throws SQLException
		{
			BigDecimal value = rs.getBigDecimal(sqlColumnName);
			value = value == null ? null : NumberUtils.setMinimumScale(value, minPrecision);
			return encrypted ? decrypt(value) : value;
		}
	}

	@Value
	private static class IntegerDocumentFieldValueLoader implements DocumentFieldValueLoader
	{
		String sqlColumnName;
		boolean encrypted;

		@Override
		public Object retrieveFieldValue(final ResultSet rs, final boolean isDisplayColumnAvailable, final String adLanguage, final LookupDescriptor lookupDescriptor_NOTUSED) throws SQLException
		{
			final int valueInt = rs.getInt(sqlColumnName);
			final Integer value = rs.wasNull() ? null : valueInt;
			return encrypted ? decrypt(value) : value;
		}
	}

	@Value
	private static class IntegerLookupValueDocumentFieldValueLoader implements DocumentFieldValueLoader
	{
		@NonNull String sqlColumnName;

		@NonNull String sqlDisplayColumnName;

		@Override
		@Nullable
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
				final IntegerLookupValue lookupValue = SqlForFetchingLookupById.retrieveIntegerLookupValue(rs, sqlDisplayColumnName, adLanguage);
				if (lookupValue != null)
				{
					return lookupValue;
				}
			}

			return IntegerLookupValue.unknown(id);
		}
	}

	@Value
	private static class StringLookupValueDocumentFieldValueLoader implements DocumentFieldValueLoader
	{
		@NonNull String sqlColumnName;

		@NonNull String sqlDisplayColumnName;

		@Override
		@Nullable
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
				final StringLookupValue lookupValue = SqlForFetchingLookupById.retrieveStringLookupValue(rs, sqlDisplayColumnName, adLanguage);
				if (lookupValue != null)
				{
					return lookupValue;
				}
			}

			return StringLookupValue.unknown(key);
		}
	}

	@Value
	private static class LoadLabelsValues implements DocumentFieldValueLoader
	{
		@NonNull String sqlValuesColumn;

		@Override
		public LookupValuesList retrieveFieldValue(
				@NonNull final ResultSet rs,
				final boolean isDisplayColumnAvailable_NOTUSED,
				final String adLanguage_NOTUSED,
				@Nullable final LookupDescriptor lookupDescriptor) throws SQLException
		{
			// FIXME: atm we are avoiding NPE by returning EMPTY.
			if (lookupDescriptor == null)
			{
				return LookupValuesList.EMPTY;
			}

			final LabelsLookup lookup = LabelsLookup.cast(lookupDescriptor);
			final List<Object> ids = retrieveIds(rs);
			return lookup.retrieveExistingValuesByIds(ids);
		}

		private List<Object> retrieveIds(final ResultSet rs) throws SQLException
		{
			final Array sqlArray = rs.getArray(sqlValuesColumn);
			if (sqlArray != null)
			{
				return Stream.of((Object[])sqlArray.getArray())
						.filter(Objects::nonNull)
						.collect(ImmutableList.toImmutableList());
			}
			else
			{
				return ImmutableList.of();
			}
		}
	}

	@Builder
	@Value
	private static class ColorDocumentFieldValueLoader implements DocumentFieldValueLoader
	{
		@NonNull String sqlColumnName;

		@Override
		@Nullable
		public Object retrieveFieldValue(
				@NonNull final ResultSet rs,
				final boolean isDisplayColumnAvailable_NOTUSED,
				final String adLanguage_NOTUSED,
				final LookupDescriptor lookupDescriptor_NOTUSED) throws SQLException
		{
			final ColorId adColorId = ColorId.ofRepoIdOrNull(rs.getInt(sqlColumnName));
			if (adColorId == null)
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
