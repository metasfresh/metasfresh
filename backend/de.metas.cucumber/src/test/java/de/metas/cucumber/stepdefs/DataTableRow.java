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

import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.i18n.ExplainedOptional;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.quantity.Quantity;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.OptionalBoolean;
import de.metas.util.Optionals;
import de.metas.util.StringUtils;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.text.tabular.Row;
import de.metas.util.text.tabular.Table;
import io.cucumber.datatable.DataTable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Function;

@EqualsAndHashCode
public class DataTableRow
{
	@Getter private final int lineNo; // introduced to improve logging/debugging
	@NonNull
	private final Map<String, String> map;
	@Nullable
	@Setter
	private String additionalRowIdentifierColumnName;

	DataTableRow(
			final int lineNo,
			@NonNull final Map<String, String> map)
	{
		this.lineNo = lineNo;
		this.map = map;
	}

	@Override
	@Deprecated
	public String toString() {return toTabularString();}

	public static DataTableRow singleRow(@NonNull final DataTable dataTable)
	{
		return new DataTableRow(1, CollectionUtils.singleElement(dataTable.asMaps()));
	}

	public static DataTableRow singleRow(@NonNull final Map<String, String> map)
	{
		return new DataTableRow(-1, map);
	}

	public Map<String, String> asMap()
	{
		return map;
	}

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

		final String value = map.get(columnNameEffective);
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

		if (nameResolved.contains("@Date@"))
		{
			final String timestamp = Instant.now().toString();
			nameResolved = nameResolved.replace("@Date@", timestamp);
		}

		return nameResolved;
	}

	public ValueAndName suggestValueAndName()
	{
		final ValueAndName valueAndName = getOptionalValueAndName().orElse(null);
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
		final String name = getAsOptionalName("Name").orElse(null);
		final String value = getAsOptionalName("Value").orElse(null);
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
		return getAsOptionalIdentifier()
				.orElseThrow(() -> new AdempiereException("No row identifier")
						.appendParametersToMessage()
						.setParameter("row", map)
						.setParameter("additionalRowIdentifierColumnName", additionalRowIdentifierColumnName));
	}

	@NonNull
	public Optional<StepDefDataIdentifier> getAsOptionalIdentifier()
	{
		return Optionals.firstPresentOfSuppliers(
				() -> getAsOptionalIdentifier(StepDefDataIdentifier.SUFFIX),
				() -> additionalRowIdentifierColumnName != null ? getAsOptionalIdentifier(additionalRowIdentifierColumnName) : Optional.empty()
		);
	}

	@NonNull
	public StepDefDataIdentifier getAsIdentifier(@NonNull final String columnName)
	{
		return getAsOptionalIdentifier(columnName)
				.orElseThrow(() -> new AdempiereException("Missing value for columnName=" + columnName)
						.appendParametersToMessage()
						.setParameter("row", map));
	}

	@NonNull
	public Optional<StepDefDataIdentifier> getAsOptionalIdentifier(@NonNull final String columnName)
	{
		String string = null;
		if (!columnName.startsWith("OPT.") && !columnName.endsWith(StepDefDataIdentifier.SUFFIX))
		{
			string = map.get("OPT." + columnName + "." + StepDefDataIdentifier.SUFFIX);
		}
		if (string == null && !columnName.startsWith("OPT."))
		{
			string = map.get("OPT." + columnName);
		}
		if (string == null && !columnName.endsWith(StepDefDataIdentifier.SUFFIX))
		{
			string = map.get(columnName + "." + StepDefDataIdentifier.SUFFIX);
		}
		if (string == null && !columnName.startsWith("OPT.") && !columnName.endsWith(StepDefDataIdentifier.SUFFIX))
		{
			string = map.get("OPT." + columnName + "." + StepDefDataIdentifier.SUFFIX);
		}

		if (string == null)
		{
			string = map.get(columnName);
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

	private BigDecimal parseBigDecimal(@Nullable final String valueStr, @NonNull final String columnInfo)
	{
		try
		{
			return NumberUtils.asBigDecimal(valueStr);
		}
		catch (final Exception ex)
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

	private static OptionalInt parseOptionalInt(@Nullable final String valueStr, final String columnInfo)
	{
		final String valueStrNorm = StringUtils.trimBlankToNull(valueStr);
		if (valueStrNorm == null)
		{
			return OptionalInt.empty();
		}

		final int valueInt = parseInt(valueStrNorm, columnInfo);
		return OptionalInt.of(valueInt);
	}

	private static int parseInt(@Nullable final String valueStr, final String columnInfo)
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

	public Quantity getAsQuantity(
			@NonNull final String valueColumnName,
			@Nullable final String uomColumnName,
			@NonNull final Function<X12DE355, I_C_UOM> uomMapper)
	{
		return getAsOptionalQuantity(valueColumnName, uomColumnName, uomMapper)
				.orElseThrow(() -> new AdempiereException("No value found for " + valueColumnName));
	}

	public Optional<Quantity> getAsOptionalQuantity(
			@NonNull final String valueColumnName,
			@NonNull final Function<X12DE355, I_C_UOM> uomMapper)
	{
		return getAsOptionalQuantity(valueColumnName, null, uomMapper);
	}

	public Optional<Quantity> getAsOptionalQuantity(
			@NonNull final String valueColumnName,
			@Nullable final String uomColumnName,
			@NonNull final Function<X12DE355, I_C_UOM> uomMapper)
	{
		final String valueStr = getAsOptionalString(valueColumnName).map(StringUtils::trimBlankToNull).orElse(null);
		if (valueStr == null)
		{
			return Optional.empty();
		}

		final int spaceIdx = valueStr.indexOf(" ");
		final BigDecimal valueBD;
		X12DE355 uomCode;
		if (spaceIdx <= 0)
		{
			valueBD = parseBigDecimal(valueStr, valueColumnName);
			uomCode = null;
		}
		else
		{
			valueBD = parseBigDecimal(valueStr.substring(0, spaceIdx), valueColumnName);
			uomCode = X12DE355.ofNullableCode(valueStr.substring(spaceIdx).trim());
		}

		if (uomCode == null)
		{
			if (uomColumnName == null)
			{
				throw new AdempiereException("When UOM is not incorporated in `" + valueColumnName + "` then an UOM column name shall be provided");
			}

			uomCode = getAsUOMCode(uomColumnName);
		}

		final I_C_UOM uom = uomMapper.apply(uomCode);
		return Optional.of(Quantity.of(valueBD, uom));
	}

	@NonNull
	public X12DE355 getAsUOMCode(@NonNull final String columnName)
	{
		String valueStr = getAsOptionalString(columnName).orElse(null);
		if (valueStr == null && !columnName.endsWith("X12DE355"))
		{
			valueStr = getAsOptionalString(columnName + ".X12DE355").orElse(null);
		}
		if (valueStr == null)
		{
			throw new AdempiereException("No value found for " + columnName);
		}
		return X12DE355.ofCode(valueStr);
	}

	public CurrencyCode getAsCurrencyCode()
	{
		return getAsOptionalCurrencyCode()
				.orElseThrow(() -> new AdempiereException("No currency code found"));
	}

	public Optional<CurrencyCode> getAsOptionalCurrencyCode()
	{
		return Optionals.firstPresentOfSuppliers(
						() -> getAsOptionalString("C_Currency.ISO_Code"),
						() -> getAsOptionalString("C_Currency." + StepDefDataIdentifier.SUFFIX),
						() -> getAsOptionalString("C_Currency_ID")
				)
				.map(CurrencyCode::ofThreeLetterCode);
	}

	public Money getAsMoney(
			@NonNull final String valueColumnName,
			@NonNull final Function<CurrencyCode, CurrencyId> currencyCodeMapper)
	{
		return getAsOptionalMoney(valueColumnName, currencyCodeMapper)
				.orElseThrow(() -> new AdempiereException("No value found for " + valueColumnName));
	}

	public Optional<Money> getAsOptionalMoney(
			@NonNull final String valueColumnName,
			@NonNull final Function<CurrencyCode, CurrencyId> currencyCodeMapper)
	{
		return getAsOptionalAmount(valueColumnName)
				.map(amount -> amount.toMoney(currencyCodeMapper));
	}

	public Optional<Amount> getAsOptionalAmount(@NonNull final String valueColumnName)
	{
		final String valueStr = getAsOptionalString(valueColumnName).map(StringUtils::trimBlankToNull).orElse(null);
		if (valueStr == null)
		{
			return Optional.empty();
		}

		final int spaceIdx = valueStr.indexOf(" ");
		final BigDecimal valueBD;
		CurrencyCode currencyCode;
		if (spaceIdx <= 0)
		{
			valueBD = parseBigDecimal(valueStr, valueColumnName);
			currencyCode = null;
		}
		else
		{
			valueBD = parseBigDecimal(valueStr.substring(0, spaceIdx), valueColumnName);
			final String currencyCodeStr = StringUtils.trimBlankToNull(valueStr.substring(spaceIdx));
			currencyCode = currencyCodeStr != null ? CurrencyCode.ofThreeLetterCode(currencyCodeStr) : null;
		}

		if (currencyCode == null)
		{
			currencyCode = getAsOptionalCurrencyCode().orElse(null);
		}

		if (currencyCode == null)
		{
			throw new AdempiereException("No currency code incorporated into `" + valueColumnName + "`: " + valueStr);
		}

		return Optional.of(Amount.of(valueBD, currencyCode));
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
		catch (final Exception ex)
		{
			throw new AdempiereException("Column `" + columnInfo + "` has invalid LocalDate `" + valueStr + "`");
		}
	}

	public Timestamp getAsLocalDateTimestamp(@NonNull final String columnName)
	{
		return toTimestamp(getAsLocalDate(columnName));
	}

	private static Timestamp toTimestamp(final LocalDate localDate)
	{
		return Timestamp.valueOf(localDate.atStartOfDay());
	}

	public Optional<Timestamp> getAsOptionalLocalDateTimestamp(@NonNull final String columnName)
	{
		return getAsOptionalLocalDate(columnName).map(DataTableRow::toTimestamp);
	}

	@SuppressWarnings("unused")
	public Timestamp getAsInstantTimestamp(@NonNull final String columnName)
	{
		return Timestamp.from(getAsInstant(columnName));
	}

	public Optional<Timestamp> getAsOptionalInstantTimestamp(@NonNull final String columnName)
	{
		return getAsOptionalInstant(columnName).map(Timestamp::from);
	}

	public Instant getAsInstant(@NonNull final String columnName)
	{
		return parseInstant(getAsString(columnName), columnName);
	}

	public Optional<Instant> getAsOptionalInstant(@NonNull final String columnName)
	{
		return getAsOptionalString(columnName).map(valueStr -> parseInstant(valueStr, columnName));
	}

	@NonNull
	private static Instant parseInstant(@NonNull final String valueStr, final String columnInfo)
	{
		try
		{
			if (valueStr.contains("T"))
			{
				if (valueStr.endsWith("Z"))
				{
					return Instant.parse(valueStr);
				}
				else
				{
					return toInstant(LocalDateTime.parse(valueStr));
				}
			}
			else
			{
				return toInstant(LocalDate.parse(valueStr).atStartOfDay());
			}
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Column `" + columnInfo + "` has invalid Instant `" + valueStr + "`");
		}
	}

	private static Instant toInstant(@NonNull final LocalDateTime ldt)
	{
		// IMPORTANT: we use JVM timezone instead of SystemTime.zoneId()
		// because that's the timezone java.sql.Timestamp would use it too,
		// and because most of currently logic is silently assuming that
		final ZoneId jvmTimeZone = ZoneId.systemDefault();
		return ldt.atZone(jvmTimeZone).toInstant();
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
		catch (final Exception ex)
		{
			throw new AdempiereException("Column `" + columnInfo + "` has invalid LocalDateTime `" + valueStr + "`");
		}
	}

	public <T extends ReferenceListAwareEnum> Optional<T> getAsOptionalEnum(@NonNull final String columnName, @NonNull final Class<T> type)
	{
		try
		{
			return getAsOptionalString(columnName).map(valueStr -> ReferenceListAwareEnums.ofNullableCode(valueStr, type));
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Invalid `" + type.getSimpleName() + "` of column `" + columnName + "`", ex);
		}
	}

	public <T extends ReferenceListAwareEnum> T getAsEnum(@NonNull final String columnName, @NonNull final Class<T> type)
	{
		return getAsOptionalEnum(columnName, type)
				.orElseThrow(() -> new AdempiereException("Missing/invalid `" + type.getSimpleName() + "` of column `" + columnName + "`"));
	}

	public String toTabularString()
	{
		return toTabular().toTabularString();
	}

	public Table toTabular()
	{
		final Table table = new Table();
		table.addRow(toTabularRow());
		table.updateHeaderFromRows();
		//table.removeColumnsWithBlankValues(); // to be decided by the caller
		return table;

	}

	public Row toTabularRow()
	{
		final Row row = new Row();
		if (lineNo > 0)
		{
			row.put("#", lineNo);
		}
		row.putAll(map);
		return row;

	}
}
