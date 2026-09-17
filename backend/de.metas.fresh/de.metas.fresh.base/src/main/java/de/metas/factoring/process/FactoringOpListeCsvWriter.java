/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.factoring.process;

import com.google.common.base.Joiner;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Serialises a {@link FactoringOpListeExportData} to the Crédit Agricole silent-factoring
 * OP-Liste CSV format (byte-for-byte matching the customer's reference file).
 *
 * <p>Modelled on {@code de.metas.datev.DATEVCsvExporter}: takes a typed domain object and
 * does all locale formatting (German {@code ,} decimal separator, {@code dd.MM.yyyy} dates)
 * in Java — no SQL-side {@code to_char}/{@code translate} tricks.
 *
 * <p>Output shape:
 * <ul>
 *   <li>UTF-8 encoding with a leading BOM ({@code 0xEF 0xBB 0xBF}).</li>
 *   <li>11 semicolon-separated fields per row.</li>
 *   <li>CRLF ({@code \r\n}) line terminator on every row (including the last).</li>
 * </ul>
 */
@UtilityClass
public class FactoringOpListeCsvWriter
{
	private static final byte[] UTF8_BOM = { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };
	private static final String LINE_TERMINATOR = "\r\n";
	private static final String FIELD_SEPARATOR = ";";
	private static final Joiner FIELD_JOINER = Joiner.on(FIELD_SEPARATOR).useForNull("");

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	// German number format: `,` decimal separator, no thousand grouping, 2 fraction digits.
	private static final ThreadLocal<DecimalFormat> AMOUNT_FORMAT = ThreadLocal.withInitial(() -> {
		final DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.GERMANY);
		symbols.setDecimalSeparator(',');
		final DecimalFormat fmt = new DecimalFormat("0.00", symbols);
		fmt.setGroupingUsed(false);
		return fmt;
	});

	/** Writes the export data as CSV bytes (UTF-8 + BOM + CRLF + {@code ;}-delimited). */
	public static byte[] toCsvBytes(@NonNull final FactoringOpListeExportData data) throws IOException
	{
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		out.write(UTF8_BOM);

		final StringBuilder sb = new StringBuilder();
		sb.append(buildHeaderLine(data)).append(LINE_TERMINATOR);
		for (final FactoringOpListeDetailRow row : data.getDetailRows())
		{
			sb.append(buildDetailLine(row, data.getCurrencyIso())).append(LINE_TERMINATOR);
		}

		out.write(sb.toString().getBytes(StandardCharsets.UTF_8));
		return out.toByteArray();
	}

	/**
	 * Header row — 11 fields:
	 * {@code 01 ; SAF ; EFAG ; contractNo ; clientAccountId ; currency ; "" ; uploadDate ; rowCount ; sumD ; sumC}.
	 */
	private static String buildHeaderLine(@NonNull final FactoringOpListeExportData data)
	{
		final List<String> fields = Arrays.asList(
				"01",
				"SAF",
				"EFAG",
				data.getContractNo(),
				data.getClientAccountId(),
				data.getCurrencyIso(),
				"",                                        // field 7: technical semicolon (empty)
				formatDate(data.getUploadDate()),
				formatAmount(BigDecimal.valueOf(data.totalRowCount())),
				formatAmount(data.getSumD()),
				formatAmount(data.getSumC()));
		return FIELD_JOINER.join(fields);
	}

	/**
	 * Detail row — 11 fields:
	 * {@code 02 ; debitorNo ; debitorName ; documentNo ; dateInvoiced ; dueDate ; currency ; grandTotal ; openAmt ; D|C ; ""}.
	 */
	private static String buildDetailLine(@NonNull final FactoringOpListeDetailRow row, @NonNull final String currencyIso)
	{
		final List<String> fields = Arrays.asList(
				"02",
				row.getDebitorNo(),
				row.getDebitorName(),
				row.getDocumentNo(),
				formatDate(row.getDateInvoiced()),
				formatDate(row.getDueDate()),
				currencyIso,
				formatAmount(row.getGrandTotal()),
				formatAmount(row.getOpenAmount()),
				row.getDebitCreditFlag().name(),
				"");                                        // field 11: internal semicolon (empty)
		return FIELD_JOINER.join(fields);
	}

	private static String formatDate(@NonNull final LocalDate date)
	{
		return date.format(DATE_FORMATTER);
	}

	private static String formatAmount(@NonNull final BigDecimal amount)
	{
		return AMOUNT_FORMAT.get().format(amount.setScale(2, RoundingMode.HALF_UP));
	}
}
