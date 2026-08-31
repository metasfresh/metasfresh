/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.deliveryplanning;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.deliveryplanning.DeliveryPlanningList.AggregationKeyField;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.TranslatableStrings;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

/**
 * Asserts that a delivery-planning action was REJECTED with the message a feature file names, matching the
 * {@code AD_Message} itself rather than its wording, so no message text is frozen into a feature file.
 */
public class DeliveryPlanningRejectionHelper
{
	/** the {@code AD_Message} the action is expected to be rejected with */
	private static final String COLUMNNAME_ErrorAdMessage = "ErrorAdMessage";
	/** a raw rejection text, {@code @token@}s included, for a rejection that carries no {@code AD_Message} */
	private static final String COLUMNNAME_ErrorMessage = "ErrorMessage";
	/** the {@link AggregationKeyField}s the rejection message has to name, all of them, in ONE message */
	private static final String COLUMNNAME_ErrorFields = "ErrorFields";

	private static final ImmutableSet<String> EXPECTATION_COLUMNS = ImmutableSet.of(
			COLUMNNAME_ErrorAdMessage, COLUMNNAME_ErrorMessage, COLUMNNAME_ErrorFields);

	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	/**
	 * Runs the given action, and - when the row names any expected rejection - asserts that it was rejected that way
	 * instead of succeeding. A row naming none just runs the action.
	 *
	 * @param otherKnownColumns every column the CALLING step understands, besides the expectation columns this helper
	 * 		owns; anything outside the two sets is a typo rather than an ignored cell.
	 */
	public void runExpectingRejectionIfAny(
			@NonNull final DataTableRow row,
			@NonNull final ImmutableSet<String> otherKnownColumns,
			@NonNull final Runnable action)
	{
		assertNoUnknownColumn(row, otherKnownColumns);

		final Optional<AdMessageKey> expectedAdMessage = row.getAsOptionalString(COLUMNNAME_ErrorAdMessage)
				.filter(Check::isNotBlank)
				.map(AdMessageKey::of);
		final Optional<String> expectedMessage = row.getAsOptionalString(COLUMNNAME_ErrorMessage)
				.filter(Check::isNotBlank);
		final List<String> expectedFields = row.getAsOptionalString(COLUMNNAME_ErrorFields)
				.filter(Check::isNotBlank)
				.map(fields -> Splitter.on(",").trimResults().omitEmptyStrings().splitToList(fields))
				.orElseGet(ImmutableList::of);

		if (!expectedAdMessage.isPresent() && !expectedMessage.isPresent() && expectedFields.isEmpty())
		{
			action.run();
			return;
		}

		final Throwable thrown = catchThrowable(action::run);
		assertThat(thrown).as("the action was expected to be rejected, but it succeeded").isNotNull();
		assertThat(thrown)
				.as("the action was rejected, but with a %s - only an AdempiereException carries the errorCode a"
						+ " rejection is identified by", thrown.getClass().getName())
				.isInstanceOf(AdempiereException.class);

		final String rejectionMessage = thrown.getMessage();
		final String adLanguage = Env.getAD_Language();

		expectedAdMessage.ifPresent(adMessage -> assertRejectedWith((AdempiereException)thrown, adMessage));

		expectedMessage.ifPresent(message -> assertThat(rejectionMessage)
				.as("rejection message %s", message)
				.contains(TranslatableStrings.parse(message).translate(adLanguage)));

		for (final String fieldName : expectedFields)
		{
			assertThat(rejectionMessage)
					.as("rejection message names the differing field %s", fieldName)
					.contains(msgBL.getMsg(adLanguage, AggregationKeyField.valueOf(fieldName).getLabel()));
		}
	}

	/**
	 * An {@code AD_Message} rejection is identified by its {@code errorCode} - exact, and independent of both the
	 * wording and the language. There is no text comparison to fall back on: a rejection arriving here without an
	 * {@code errorCode} is not the named {@code AD_Message}.
	 */
	private void assertRejectedWith(
			@NonNull final AdempiereException thrown,
			@NonNull final AdMessageKey expectedAdMessage)
	{
		// an AD_Message may carry its own ErrorCode, and then that - not the message's value - is the errorCode
		final String expectedErrorCode = CoalesceUtil.coalesceNotNull(
				msgBL.getErrorCode(expectedAdMessage),
				expectedAdMessage.toAD_Message());

		assertThat(thrown.getErrorCode())
				.as("errorCode of the rejection %s", thrown.getMessage())
				.isEqualTo(expectedErrorCode);
	}

	/**
	 * Matches every column against the KNOWN set (this helper's expectation columns plus the calling step's own), so a
	 * misspelled column is an error rather than an ignored cell that would silently drop the claim it carries.
	 */
	private static void assertNoUnknownColumn(
			@NonNull final DataTableRow row,
			@NonNull final ImmutableSet<String> otherKnownColumns)
	{
		final ImmutableSet<String> knownColumns = ImmutableSet.<String>builder()
				.addAll(EXPECTATION_COLUMNS)
				.addAll(otherKnownColumns)
				.build();

		final ImmutableList<String> unknownColumns = row.asMap().keySet()
				.stream()
				.map(columnName -> columnName.startsWith("OPT.") ? columnName.substring("OPT.".length()) : columnName)
				.filter(columnName -> !knownColumns.contains(columnName))
				.collect(ImmutableList.toImmutableList());

		assertThat(unknownColumns)
				.as("unknown columns - known ones are %s", knownColumns)
				.isEmpty();
	}
}
