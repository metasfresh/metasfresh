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
import de.metas.deliveryplanning.DeliveryPlanningList.AdmissibilityField;
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
 * Asserts that a delivery-planning action was REJECTED with the message a feature file names, in a language-independent
 * way: an {@code AD_Message} rejection is matched by the {@code AD_Message} itself rather than by its wording, so no
 * text is frozen into a feature file.
 * <p>
 * Shared by {@link M_Delivery_Instruction_StepDef} and {@link M_Delivery_Planning_StepDef} (both get the same instance
 * from PicoContainer): the aggregation actions, the delivery instruction's document actions and the planning's
 * close/reopen all reject the same way, so they assert the same way.
 */
public class DeliveryPlanningRejectionHelper
{
	/** the {@code AD_Message} the action is expected to be rejected with */
	private static final String COLUMNNAME_ErrorAdMessage = "ErrorAdMessage";
	/** a raw rejection text, {@code @token@}s included, for a rejection that carries no {@code AD_Message} */
	private static final String COLUMNNAME_ErrorMessage = "ErrorMessage";
	/** the {@link AdmissibilityField}s the rejection message has to name, all of them, in ONE message */
	private static final String COLUMNNAME_ErrorFields = "ErrorFields";

	private static final ImmutableSet<String> EXPECTATION_COLUMNS = ImmutableSet.of(
			COLUMNNAME_ErrorAdMessage, COLUMNNAME_ErrorMessage, COLUMNNAME_ErrorFields);

	@NonNull private final IMsgBL msgBL = Services.get(IMsgBL.class);

	/**
	 * Runs the given action, and - when the row names any expected rejection - asserts that it was rejected that way
	 * instead of succeeding. A row naming none just runs the action.
	 */
	public void runExpectingRejectionIfAny(@NonNull final DataTableRow row, @NonNull final Runnable action)
	{
		assertNoMisspelledExpectationColumn(row);

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
		assertThat(thrown).as("the action was expected to be rejected, but it succeeded").isInstanceOf(AdempiereException.class);

		final String rejectionMessage = thrown.getMessage();
		final String adLanguage = Env.getAD_Language();

		expectedAdMessage.ifPresent(adMessage -> assertRejectedWith((AdempiereException)thrown, adMessage, adLanguage));

		expectedMessage.ifPresent(message -> assertThat(rejectionMessage)
				.as("rejection message %s", message)
				.contains(TranslatableStrings.parse(message).translate(adLanguage)));

		for (final String fieldName : expectedFields)
		{
			assertThat(rejectionMessage)
					.as("rejection message names the differing field %s", fieldName)
					.contains(msgBL.getMsg(adLanguage, AdmissibilityField.valueOf(fieldName).getLabel()));
		}
	}

	/**
	 * An {@code AD_Message} rejection is identified by its {@code errorCode}, which {@link AdempiereException} derives
	 * from the {@code AD_Message} the {@code ITranslatableString} was built from (its own {@code ErrorCode} when it has
	 * one, else the message's value) - exact, and independent of both the wording and the language.
	 * <p>
	 * The text comparison below it is not a second-best alternative but the only thing left on ONE path: a rejection
	 * thrown from a {@code BEFORE_}-timing document interceptor is not propagated, it is turned into a process message
	 * and re-thrown as a {@code DocumentProcessingException} built from the rendered TEXT, which carries no
	 * {@code errorCode} of its own. The expected text is asserted to be non-blank first, so an {@code AD_Message} whose
	 * text begins with its {@code {0}} parameter cannot degrade the assertion into {@code contains("")}.
	 */
	private void assertRejectedWith(
			@NonNull final AdempiereException thrown,
			@NonNull final AdMessageKey expectedAdMessage,
			@NonNull final String adLanguage)
	{
		if (thrown.getErrorCode() != null)
		{
			// the same coalesce AdempiereException applies: an AD_Message may carry its own ErrorCode, and then
			// THAT is what the exception ends up with rather than the message's value
			final String expectedErrorCode = CoalesceUtil.coalesceNotNull(
					msgBL.getErrorCode(expectedAdMessage),
					expectedAdMessage.toAD_Message());

			assertThat(thrown.getErrorCode())
					.as("errorCode of the rejection")
					.isEqualTo(expectedErrorCode);
			return;
		}

		final String expectedText = textBeforeFirstParameter(msgBL.getMsg(adLanguage, expectedAdMessage));
		assertThat(expectedText)
				.as("the text of %s before its first parameter - an empty one would assert nothing", expectedAdMessage.toAD_Message())
				.isNotBlank();

		assertThat(thrown.getMessage())
				.as("rejection message of %s", expectedAdMessage.toAD_Message())
				.contains(expectedText);
	}

	/**
	 * A misspelled OPTIONAL expectation column would silently drop the claim it carries - a scenario asserting both an
	 * {@code AD_Message} and the fields it has to name would stay green having asserted only the message. Any unknown
	 * {@code Error…} column is therefore an error rather than an ignored cell.
	 */
	private static void assertNoMisspelledExpectationColumn(@NonNull final DataTableRow row)
	{
		final ImmutableList<String> unknownColumns = row.asMap().keySet()
				.stream()
				.map(columnName -> columnName.startsWith("OPT.") ? columnName.substring("OPT.".length()) : columnName)
				.filter(columnName -> columnName.startsWith("Error"))
				.filter(columnName -> !EXPECTATION_COLUMNS.contains(columnName))
				.collect(ImmutableList.toImmutableList());

		assertThat(unknownColumns)
				.as("unknown Error… columns - known ones are %s", EXPECTATION_COLUMNS)
				.isEmpty();
	}

	/**
	 * Only the part before an {@code AD_Message}'s first {@code {0}} placeholder is compared, since the parameters are
	 * runtime ids.
	 */
	@NonNull
	private static String textBeforeFirstParameter(@NonNull final String adMessageText)
	{
		final int firstParameterIndex = adMessageText.indexOf('{');
		return firstParameterIndex >= 0 ? adMessageText.substring(0, firstParameterIndex).trim() : adMessageText;
	}
}
