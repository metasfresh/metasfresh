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

import de.metas.i18n.ExplainedOptional;
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
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Function;

@EqualsAndHashCode
@ToString
public class DataTableRow
{
	private final int lineNo; // introduced to improve logging/debugging
	final Map<String, String> map;

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
		final String columnNameEffective = findEffectiveColumnName(columnName);
		if (columnNameEffective == null)
		{
			throw new AdempiereException("Column `" + columnName + "` is missing from " + this);
		}

		final String string = map.get(columnNameEffective);
		if (string == null || Check.isBlank(string))
		{
			throw new AdempiereException("Missing value for columnName=" + columnNameEffective)
					.appendParametersToMessage()
					.setParameter("row", this);
		}
		return string;
	}

	@NonNull
	public List<String> getAsCommaSeparatedString(@NonNull final String columnName)
	{
		final String value = getAsString(columnName);
		return Arrays.asList(value.split(","));
	}

	@Nullable
	private String findEffectiveColumnName(@NonNull final String columnName)
	{
		if (map.containsKey(columnName))
		{
			return columnName;
		}

		if (!columnName.startsWith("OPT."))
		{
			final String optColumnName = "OPT." + columnName;
			if (map.containsKey(optColumnName))
			{
				return optColumnName;
			}
		}

		return null;
	}

	@NonNull
	public Optional<String> getAsOptionalString(@NonNull final String columnName)
	{
		final String columnNameEffective = findEffectiveColumnName(columnName);
		if (columnNameEffective == null)
		{
			return Optional.empty(); // column is missing
		}

		String value = map.get(columnNameEffective);
		return Optional.ofNullable(value);
	}

	public String getAsName(@NonNull final String columnName)
	{
		return resolveName(getAsString(columnName));
	}

	public Optional<String> getAsOptionalName(@NonNull final String columnName)
	{
		return getAsOptionalString(columnName).map(DataTableRow::resolveName);
	}

	@NonNull
	private static String resolveName(@NonNull final String name)
	{
		String nameResolved = StringUtils.trimBlankToNull(name);
		if (nameResolved == null)
		{
			throw new AdempiereException("Invalid name: `" + name + "`");
		}

		nameResolved = nameResolved.replace("@Date@", Instant.now().toString());
		return nameResolved;
	}

	public ValueAndName suggestValueAndName()
	{
		ValueAndName valueAndName = getOptionalValueAndName().orElse(null);
		if (valueAndName != null)
		{
			return valueAndName;
		}

		final StepDefDataIdentifier recordIdentifier = getAsOptionalIdentifier().orElse(null);
		if (recordIdentifier != null)
		{
			return ValueAndName.unique(recordIdentifier.getAsString());
		}

		return ValueAndName.unique();
	}

	public ExplainedOptional<ValueAndName> getOptionalValueAndName()
	{
		String name = getAsOptionalName("Name").orElse(null);
		String value = getAsOptionalName("Value").orElse(null);
		if (name == null)
		{
			if (value == null)
			{
				return ExplainedOptional.emptyBecause("At least Value or Name columns shall contain a valid name string");
			}
			else
			{
				return ExplainedOptional.of(ValueAndName.ofValue(value));
			}
		}
		else
		{
			if (value == null)
			{
				return ExplainedOptional.of(ValueAndName.ofName(name));
			}
			else
			{
				return ExplainedOptional.of(ValueAndName.ofValueAndName(value, name));
			}
		}
	}

	@NonNull
	public StepDefDataIdentifier getAsIdentifier()
	{
		return getAsIdentifier(StepDefDataIdentifier.SUFFIX);
	}

	@NonNull
	public Optional<StepDefDataIdentifier> getAsOptionalIdentifier() {return getAsOptionalIdentifier(StepDefDataIdentifier.SUFFIX);}

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
		return parseBigDecimal(getAsString(columnName), columnName);
	}

	public Optional<BigDecimal> getAsOptionalBigDecimal(@NonNull final String columnName)
	{
		return getAsOptionalString(columnName).map(valueStr -> parseBigDecimal(valueStr, columnName));
	}

	private BigDecimal parseBigDecimal(@Nullable String valueStr, @NonNull String columnInfo)
	{
		try
		{
			return NumberUtils.asBigDecimal(valueStr);
		}
		catch (Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex)
					.appendParametersToMessage()
					.setParameter("columnName", columnInfo);
		}
	}

	public int getAsInt(@NonNull final String columnName)
	{
		return parseInt(getAsString(columnName), columnName);
	}

	@NonNull
	public OptionalInt getAsOptionalInt(@NonNull final String columnName)
	{
		return getAsOptionalString(columnName)
				.map(valueStr -> parseOptionalInt(valueStr, columnName))
				.orElseGet(OptionalInt::empty);
	}

	private static OptionalInt parseOptionalInt(@Nullable final String valueStr, String columnInfo)
	{
		final String valueStrNorm = StringUtils.trimBlankToNull(valueStr);
		if (valueStrNorm == null)
		{
			return OptionalInt.empty();
		}

		final int valueInt = parseInt(valueStrNorm, columnInfo);
		return OptionalInt.of(valueInt);
	}

	private static int parseInt(@Nullable final String valueStr, String columnInfo)
	{
		final String valueStrNorm = StringUtils.trimBlankToNull(valueStr);
		if (valueStrNorm == null)
		{
			throw new AdempiereException("Column `" + columnInfo + "` contains empty/blank value. Please use a legit integer.");
		}

		final Integer valueInt = NumberUtils.asIntegerOrNull(valueStrNorm);
		if (valueInt == null)
		{
			throw new AdempiereException("Column `" + columnInfo + "` has invalid Integer value `" + valueStr + "`");
		}
		return valueInt;
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

	public Timestamp getAsLocalDateTimestamp(@NonNull final String columnName)
	{
		return Timestamp.valueOf(getAsLocalDate(columnName).atStartOfDay());
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
