/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.cucumber.stepdefs;

import com.google.common.base.Splitter;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.OptionalBoolean;
import de.metas.util.StringUtils;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import io.cucumber.datatable.DataTable;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Function;

@EqualsAndHashCode
@ToString
public class DataTableRow
{
	private final int lineNo; // introduced to improve logging/debugging
	private final Map<String, String> map;

	private static final Splitter COMMA_SEPARATED_SPLITTER = Splitter.on(",").omitEmptyStrings();

	DataTableRow(
			final int lineNo,
			@NonNull final Map<String, String> map)
	{
		this.lineNo = lineNo;
		this.map = map;
	}

	public static DataTableRow singleRow(@NonNull final DataTable dataTable)
	{
		return new DataTableRow(1, CollectionUtils.singleElement(dataTable.asMaps()));
	}

	public static DataTableRow singleRow(@NonNull final Map<String, String> map)
	{
		return new DataTableRow(-1, map);
	}

	public Map<String, String> asMap() {return map;}

	@NonNull
	public String getAsString(@NonNull final String columnName)
	{
		return DataTableUtil.extractStringForColumnName(map, columnName);
	}

	@NonNull
	public Optional<String> getAsOptionalString(@NonNull final String columnName)
	{
		String value = map.get(columnName);
		if (value == null && !columnName.startsWith("OPT."))
		{
			value = map.get("OPT." + columnName);
		}

		return Optional.ofNullable(value);
	}

	@NonNull
	public StepDefDataIdentifier getAsIdentifier()
	{
		return getAsIdentifier(StepDefDataIdentifier.SUFFIX);
	}

	@NonNull
	public StepDefDataIdentifier getAsIdentifier(@NonNull final String columnName)
	{
		String string = map.get(columnName);
		if (string == null && !columnName.endsWith(StepDefDataIdentifier.SUFFIX))
		{
			string = map.get(columnName + "." + StepDefDataIdentifier.SUFFIX);
		}

		if (string == null || Check.isBlank(string))
		{
			throw new AdempiereException("Missing value for columnName=" + columnName)
					.appendParametersToMessage()
					.setParameter("row", map);
		}

		return StepDefDataIdentifier.ofString(string);
	}

	@NonNull
	public Optional<StepDefDataIdentifier> getAsOptionalIdentifier(@NonNull final String columnName)
	{
		String string = map.get(columnName);
		if (string == null && !columnName.endsWith(StepDefDataIdentifier.SUFFIX))
		{
			string = map.get(columnName + "." + StepDefDataIdentifier.SUFFIX);
		}
		if (string == null && !columnName.startsWith("OPT.") && !columnName.endsWith(StepDefDataIdentifier.SUFFIX))
		{
			string = map.get("OPT." + columnName + "." + StepDefDataIdentifier.SUFFIX);
		}

		if (string == null || Check.isBlank(string))
		{
			return Optional.empty();
		}

		return Optional.of(StepDefDataIdentifier.ofString(string));
	}

	public BigDecimal getAsBigDecimal(@NonNull final String columnName)
	{
		return DataTableUtil.extractBigDecimalForColumnName(map, columnName);
	}

	public Optional<BigDecimal> getAsOptionalBigDecimal(@NonNull final String columnName)
	{
		return Optional.ofNullable(DataTableUtil.extractBigDecimalOrNullForColumnName(map, columnName));
	}

	public int getAsInt(@NonNull final String columnName)
	{
		return DataTableUtil.extractIntForColumnName(map, columnName);
	}

	@NonNull
	public OptionalInt getAsOptionalInt(@NonNull final String columnName)
	{
		return getAsOptionalString(columnName)
				.map(DataTableRow::parseOptionalInt)
				.orElseGet(OptionalInt::empty);
	}

	private static OptionalInt parseOptionalInt(@Nullable final String value)
	{
		final String valueNorm = StringUtils.trimBlankToNull(value);
		if (valueNorm == null)
		{
			return OptionalInt.empty();
		}

		final int valueInt = NumberUtils.asInt(value);
		return OptionalInt.of(valueInt);
	}

	public boolean getAsBoolean(@NonNull final String columnName)
	{
		final Boolean valueBoolean = getAsOptionalBoolean(columnName).toBooleanOrNull();
		if (valueBoolean == null)
		{
			throw new AdempiereException("No value found for " + columnName);
		}
		return valueBoolean;
	}

	@NonNull
	public OptionalBoolean getAsOptionalBoolean(@NonNull final String columnName)
	{
		final String valueString = getAsOptionalString(columnName).orElse(null);
		return OptionalBoolean.ofNullableString(valueString);
	}

	public Optional<Quantity> getAsOptionalQuantity(
			@NonNull final String valueColumnName,
			@NonNull final String uomColumnName,
			@NonNull final Function<String, I_C_UOM> uomMapper)
	{
		final BigDecimal valueBD = getAsOptionalBigDecimal(valueColumnName).orElse(null);
		if (valueBD == null)
		{
			return Optional.empty();
		}

		final String uomString = getAsOptionalString(uomColumnName).orElse(null);
		if (uomString == null)
		{
			return Optional.empty();
		}

		final I_C_UOM uom = uomMapper.apply(uomString);

		return Optional.of(Quantity.of(valueBD, uom));
	}

	public LocalDate getAsLocalDate(@NonNull final String columnName)
	{
		return parseLocalDate(getAsString(columnName), columnName);
	}

	public Optional<LocalDate> getAsOptionalLocalDate(@NonNull final String columnName)
	{
		return getAsOptionalString(columnName).map(valueStr -> parseLocalDate(valueStr, columnName));
	}

	@NonNull
	private static LocalDate parseLocalDate(final String valueStr, final String columnInfo)
	{
		try
		{
			return LocalDate.parse(valueStr);
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Column `" + columnInfo + "` has invalid LocalDate `" + valueStr + "`");
		}
	}

	public Instant getAsInstant(@NonNull final String columnName)
	{
		return parseInstant(getAsString(columnName), columnName);
	}

	@NonNull
	private static Instant parseInstant(final String valueStr, final String columnInfo)
	{
		try
		{
			return Instant.parse(valueStr);
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Column `" + columnInfo + "` has invalid Instant `" + valueStr + "`");
		}
	}

	public Optional<LocalDateTime> getAsOptionalLocalDateTime(@NonNull final String columnName)
	{
		return getAsOptionalString(columnName).map(valueStr -> parseLocalDateTime(valueStr, columnName));
	}

	@NonNull
	private static LocalDateTime parseLocalDateTime(final String valueStr, final String columnInfo)
	{
		try
		{
			return LocalDateTime.parse(valueStr);
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Column `" + columnInfo + "` has invalid LocalDateTime `" + valueStr + "`");
		}
	}

	public <T extends ReferenceListAwareEnum> Optional<T> getAsOptionalEnum(@NonNull final String columnName, @NonNull Class<T> type)
	{
		try
		{
			return getAsOptionalString(columnName).map(valueStr -> ReferenceListAwareEnums.ofNullableCode(valueStr, type));
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Invalid `" + type.getSimpleName() + "` of column `" + columnName + "`", ex);
		}
	}

	public <T extends ReferenceListAwareEnum> T getAsEnum(@NonNull final String columnName, @NonNull Class<T> type)
	{
		return getAsOptionalEnum(columnName, type)
				.orElseThrow(() -> new AdempiereException("Missing/invalid `" + type.getSimpleName() + "` of column `" + columnName + "`"));
	}
}
