package de.metas.payment.sepa.api.impl;

import com.google.common.collect.ImmutableList;
import de.metas.banking.mandate.DirectDebitMandate;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * #%L
 * de.metas.payment.sepa.base
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

/**
 * Checks that every line of a direct debit pay selection has a usable mandate, so that the export can be refused as a whole.
 */
class DirectDebitMandateChecker
{
	private static final AdMessageKey MSG_CheckFailed = AdMessageKey.of("de.metas.payment.sepa.DirectDebitMandateCheckFailed");
	private static final AdMessageKey MSG_Missing = AdMessageKey.of("de.metas.payment.sepa.DirectDebitMandateMissing");
	private static final AdMessageKey MSG_Expired = AdMessageKey.of("de.metas.payment.sepa.DirectDebitMandateExpired");
	private static final AdMessageKey MSG_OneOffUsed = AdMessageKey.of("de.metas.payment.sepa.DirectDebitMandateOneOffUsed");
	private static final AdMessageKey MSG_OneOffMultipleLines = AdMessageKey.of("de.metas.payment.sepa.DirectDebitMandateOneOffMultipleLines");

	@NonNull private final IMsgBL msgBL = Services.get(IMsgBL.class);

	/** The WebUI collapses line breaks, so the problems are joined with this separator. */
	private static final String PROBLEM_SEPARATOR = " · ";

	enum ProblemType
	{
		MISSING, EXPIRED, ONE_OFF_USED, ONE_OFF_MULTIPLE_LINES
	}

	@Value
	@Builder
	static class LineWithMandate
	{
		@Nullable String partnerValue;
		@Nullable String partnerName;
		@NonNull String iban;
		@Nullable DirectDebitMandate mandate;
	}

	@Value
	@Builder
	static class DirectDebitMandateProblem
	{
		@NonNull ProblemType type;
		@Nullable String partnerValue;
		@Nullable String partnerName;
		@NonNull String iban;
		@Nullable String mandateReference;
		@Nullable LocalDate referenceDate;
		/** only for {@link ProblemType#ONE_OFF_MULTIPLE_LINES} */
		int lineCount;
		/** only for {@link ProblemType#EXPIRED} */
		@Nullable LocalDate collectionDate;
	}

	/**
	 * Finds the lines that cannot be collected.
	 * <ul>
	 * <li>One reason per line, by precedence: {@link ProblemType#MISSING}, else {@link ProblemType#EXPIRED}, else {@link ProblemType#ONE_OFF_USED}.</li>
	 * <li>{@link ProblemType#ONE_OFF_MULTIPLE_LINES} is reported only for a one-off mandate that is not yet used and sits on more than one line
	 * (once per mandate, with the line count). A used one-off on several lines yields {@code ONE_OFF_USED} only.</li>
	 * <li>An identical problem (same partner, bank account and mandate, e.g. several invoices of one debtor) is reported once.</li>
	 * <li>Order: line order, followed by the multiple-lines problems.</li>
	 * </ul>
	 *
	 * @param collectionDate the date the mandates are checked against for expiry
	 * @return the problems; empty if every line can be collected
	 */
	ImmutableList<DirectDebitMandateProblem> check(@NonNull final List<LineWithMandate> lines, @NonNull final LocalDate collectionDate)
	{
		final ImmutableList.Builder<DirectDebitMandateProblem> problems = ImmutableList.builder();
		final Map<DirectDebitMandate, List<LineWithMandate>> unusedOneOffLinesByMandate = new LinkedHashMap<>();

		for (final LineWithMandate line : lines)
		{
			final DirectDebitMandate mandate = line.getMandate();
			if (mandate == null)
			{
				problems.add(problem(ProblemType.MISSING, line).build());
			}
			else if (mandate.isExpiredAt(collectionDate))
			{
				problems.add(problem(ProblemType.EXPIRED, line)
						.mandateReference(mandate.getReference())
						.referenceDate(mandate.getReferenceDate())
						.collectionDate(collectionDate)
						.build());
			}
			else if (mandate.isOneOffAlreadyUsed())
			{
				problems.add(problem(ProblemType.ONE_OFF_USED, line)
						.mandateReference(mandate.getReference())
						.referenceDate(mandate.getReferenceDate())
						.build());
			}
			else if (!mandate.isRecurring())
			{
				unusedOneOffLinesByMandate.computeIfAbsent(mandate, k -> new ArrayList<>()).add(line);
			}
		}

		unusedOneOffLinesByMandate.forEach((mandate, mandateLines) -> {
			if (mandateLines.size() > 1)
			{
				problems.add(problem(ProblemType.ONE_OFF_MULTIPLE_LINES, mandateLines.get(0))
						.mandateReference(mandate.getReference())
						.lineCount(mandateLines.size())
						.build());
			}
		});

		return problems.build().stream().distinct().collect(ImmutableList.toImmutableList());
	}

	private static DirectDebitMandateProblem.DirectDebitMandateProblemBuilder problem(final ProblemType type, final LineWithMandate line)
	{
		return DirectDebitMandateProblem.builder()
				.type(type)
				.partnerValue(line.getPartnerValue())
				.partnerName(line.getPartnerName())
				.iban(line.getIban());
	}

	/**
	 * @return the exception to throw; its message is the header followed by all problems, its error code is the one of the header message
	 */
	AdempiereException toException(@NonNull final List<DirectDebitMandateProblem> problems)
	{
		final TranslatableStringBuilder message = TranslatableStrings.builder()
				.append(TranslatableStrings.adMessage(MSG_CheckFailed))
				.append(" ");
		boolean first = true;
		for (final DirectDebitMandateProblem problem : problems)
		{
			if (!first)
			{
				message.append(PROBLEM_SEPARATOR);
			}
			first = false;
			message.append(toMessage(problem));
		}

		// the header message's AD_Message.ErrorCode (and not the one of a problem line) identifies the failure
		final String errorCode = msgBL.getErrorCode(MSG_CheckFailed);
		return new AdempiereException(message.build(), errorCode != null ? errorCode : MSG_CheckFailed.toAD_Message());
	}

	/** @return the partner value, followed by its name if there is one */
	private static String toPartnerLabel(final DirectDebitMandateProblem problem)
	{
		return Stream.of(problem.getPartnerValue(), problem.getPartnerName())
				.filter(Check::isNotBlank)
				.collect(Collectors.joining(" - "));
	}

	private static ITranslatableString toMessage(final DirectDebitMandateProblem problem)
	{
		final String partner = toPartnerLabel(problem);

		switch (problem.getType())
		{
			case MISSING:
				return TranslatableStrings.adMessage(MSG_Missing, partner, problem.getIban());
			case EXPIRED:
				return TranslatableStrings.adMessage(MSG_Expired, partner, problem.getMandateReference(),
						TranslatableStrings.date(problem.getReferenceDate()), TranslatableStrings.date(problem.getCollectionDate()));
			case ONE_OFF_USED:
				return TranslatableStrings.adMessage(MSG_OneOffUsed, partner, problem.getMandateReference(),
						TranslatableStrings.date(problem.getReferenceDate()));
			case ONE_OFF_MULTIPLE_LINES:
				return TranslatableStrings.adMessage(MSG_OneOffMultipleLines, partner, problem.getMandateReference(), problem.getLineCount());
			default:
				throw new AdempiereException("Unsupported problem type: " + problem.getType());
		}
	}
}
