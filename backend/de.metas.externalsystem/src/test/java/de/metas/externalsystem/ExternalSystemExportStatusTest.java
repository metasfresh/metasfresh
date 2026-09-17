/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.externalsystem;

import com.google.common.collect.ImmutableSet;
import de.metas.externalsystem.model.X_ExternalSystem_ScriptedExportConversion_Status;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class ExternalSystemExportStatusTest
{
	/** Every enum value round-trips: ofCode(getCode()) == enumValue */
	@Test
	void testCodeRoundTrip()
	{
		for (final ExternalSystemExportStatus status : ExternalSystemExportStatus.values())
		{
			assertThat(ExternalSystemExportStatus.ofCode(status.getCode()))
					.as("round-trip for %s", status)
					.isSameAs(status);
		}
	}

	/** Exactly 7 codes and they match the X_ constants 1:1 */
	@Test
	void testExactlySevenCodesMatchingXConstants()
	{
		final Set<String> expectedCodes = ImmutableSet.of(
				X_ExternalSystem_ScriptedExportConversion_Status.EXPORTSTATUS_Pending,
				X_ExternalSystem_ScriptedExportConversion_Status.EXPORTSTATUS_Enqueued,
				X_ExternalSystem_ScriptedExportConversion_Status.EXPORTSTATUS_SendingStarted,
				X_ExternalSystem_ScriptedExportConversion_Status.EXPORTSTATUS_Sent,
				X_ExternalSystem_ScriptedExportConversion_Status.EXPORTSTATUS_Error,
				X_ExternalSystem_ScriptedExportConversion_Status.EXPORTSTATUS_Invalid,
				X_ExternalSystem_ScriptedExportConversion_Status.EXPORTSTATUS_DontSend
		);

		final Set<String> actualCodes = Arrays.stream(ExternalSystemExportStatus.values())
				.map(ExternalSystemExportStatus::getCode)
				.collect(Collectors.toSet());

		assertThat(actualCodes).isEqualTo(expectedCodes);
	}

	/** Verify individual code values */
	@Test
	void testIndividualCodes()
	{
		assertThat(ExternalSystemExportStatus.Pending.getCode())
				.isEqualTo(X_ExternalSystem_ScriptedExportConversion_Status.EXPORTSTATUS_Pending);
		assertThat(ExternalSystemExportStatus.Enqueued.getCode())
				.isEqualTo(X_ExternalSystem_ScriptedExportConversion_Status.EXPORTSTATUS_Enqueued);
		assertThat(ExternalSystemExportStatus.SendingStarted.getCode())
				.isEqualTo(X_ExternalSystem_ScriptedExportConversion_Status.EXPORTSTATUS_SendingStarted);
		assertThat(ExternalSystemExportStatus.Sent.getCode())
				.isEqualTo(X_ExternalSystem_ScriptedExportConversion_Status.EXPORTSTATUS_Sent);
		assertThat(ExternalSystemExportStatus.Error.getCode())
				.isEqualTo(X_ExternalSystem_ScriptedExportConversion_Status.EXPORTSTATUS_Error);
		assertThat(ExternalSystemExportStatus.Invalid.getCode())
				.isEqualTo(X_ExternalSystem_ScriptedExportConversion_Status.EXPORTSTATUS_Invalid);
		assertThat(ExternalSystemExportStatus.DontSend.getCode())
				.isEqualTo(X_ExternalSystem_ScriptedExportConversion_Status.EXPORTSTATUS_DontSend);
	}

	/** Predicate methods: isError() */
	@Test
	void testIsError()
	{
		assertThat(ExternalSystemExportStatus.Error.isError()).isTrue();
		for (final ExternalSystemExportStatus status : ExternalSystemExportStatus.values())
		{
			if (status != ExternalSystemExportStatus.Error)
			{
				assertThat(status.isError()).as("%s.isError() should be false", status).isFalse();
			}
		}
	}

	/** Predicate methods: isSent() */
	@Test
	void testIsSent()
	{
		assertThat(ExternalSystemExportStatus.Sent.isSent()).isTrue();
		for (final ExternalSystemExportStatus status : ExternalSystemExportStatus.values())
		{
			if (status != ExternalSystemExportStatus.Sent)
			{
				assertThat(status.isSent()).as("%s.isSent() should be false", status).isFalse();
			}
		}
	}

	/** Predicate methods: isPending() */
	@Test
	void testIsPending()
	{
		assertThat(ExternalSystemExportStatus.Pending.isPending()).isTrue();
		assertThat(ExternalSystemExportStatus.Enqueued.isPending()).isFalse();
	}

	/** isProcessing() = Enqueued OR SendingStarted (mirrors EDIExportStatus) */
	@Test
	void testIsProcessing()
	{
		assertThat(ExternalSystemExportStatus.Enqueued.isProcessing()).isTrue();
		assertThat(ExternalSystemExportStatus.SendingStarted.isProcessing()).isTrue();

		assertThat(ExternalSystemExportStatus.Pending.isProcessing()).isFalse();
		assertThat(ExternalSystemExportStatus.Sent.isProcessing()).isFalse();
		assertThat(ExternalSystemExportStatus.Error.isProcessing()).isFalse();
		assertThat(ExternalSystemExportStatus.Invalid.isProcessing()).isFalse();
		assertThat(ExternalSystemExportStatus.DontSend.isProcessing()).isFalse();
	}

	/** isProcessed() = Sent OR DontSend */
	@Test
	void testIsProcessed()
	{
		assertThat(ExternalSystemExportStatus.Sent.isProcessed()).isTrue();
		assertThat(ExternalSystemExportStatus.DontSend.isProcessed()).isTrue();

		assertThat(ExternalSystemExportStatus.Pending.isProcessed()).isFalse();
		assertThat(ExternalSystemExportStatus.Enqueued.isProcessed()).isFalse();
		assertThat(ExternalSystemExportStatus.SendingStarted.isProcessed()).isFalse();
		assertThat(ExternalSystemExportStatus.Error.isProcessed()).isFalse();
		assertThat(ExternalSystemExportStatus.Invalid.isProcessed()).isFalse();
	}

	/** isPendingOrError() */
	@Test
	void testIsPendingOrError()
	{
		assertThat(ExternalSystemExportStatus.Pending.isPendingOrError()).isTrue();
		assertThat(ExternalSystemExportStatus.Error.isPendingOrError()).isTrue();

		assertThat(ExternalSystemExportStatus.Sent.isPendingOrError()).isFalse();
		assertThat(ExternalSystemExportStatus.Invalid.isPendingOrError()).isFalse();
	}

	/** isErrorOrInvalid() */
	@Test
	void testIsErrorOrInvalid()
	{
		assertThat(ExternalSystemExportStatus.Error.isErrorOrInvalid()).isTrue();
		assertThat(ExternalSystemExportStatus.Invalid.isErrorOrInvalid()).isTrue();

		assertThat(ExternalSystemExportStatus.Pending.isErrorOrInvalid()).isFalse();
		assertThat(ExternalSystemExportStatus.Sent.isErrorOrInvalid()).isFalse();
	}

	/** isInvalid() */
	@Test
	void testIsInvalid()
	{
		assertThat(ExternalSystemExportStatus.Invalid.isInvalid()).isTrue();
		assertThat(ExternalSystemExportStatus.Error.isInvalid()).isFalse();
	}

	/** isDontSend() */
	@Test
	void testIsDontSend()
	{
		assertThat(ExternalSystemExportStatus.DontSend.isDontSend()).isTrue();
		assertThat(ExternalSystemExportStatus.Sent.isDontSend()).isFalse();
	}

	/** isEnqueued() */
	@Test
	void testIsEnqueued()
	{
		assertThat(ExternalSystemExportStatus.Enqueued.isEnqueued()).isTrue();
		assertThat(ExternalSystemExportStatus.Pending.isEnqueued()).isFalse();
	}

	/** isSendingStarted() */
	@Test
	void testIsSendingStarted()
	{
		assertThat(ExternalSystemExportStatus.SendingStarted.isSendingStarted()).isTrue();
		assertThat(ExternalSystemExportStatus.Enqueued.isSendingStarted()).isFalse();
	}

	/** isInProgressOrSend() — Enqueued, SendingStarted, Sent */
	@Test
	void testIsInProgressOrSend()
	{
		assertThat(ExternalSystemExportStatus.Enqueued.isInProgressOrSend()).isTrue();
		assertThat(ExternalSystemExportStatus.SendingStarted.isInProgressOrSend()).isTrue();
		assertThat(ExternalSystemExportStatus.Sent.isInProgressOrSend()).isTrue();

		assertThat(ExternalSystemExportStatus.Pending.isInProgressOrSend()).isFalse();
		assertThat(ExternalSystemExportStatus.Error.isInProgressOrSend()).isFalse();
		assertThat(ExternalSystemExportStatus.Invalid.isInProgressOrSend()).isFalse();
		assertThat(ExternalSystemExportStatus.DontSend.isInProgressOrSend()).isFalse();
	}

	/** AD_Reference_ID binding is 542104 */
	@Test
	void testAdReferenceId()
	{
		assertThat(ExternalSystemExportStatus.AD_Reference_ID)
				.isEqualTo(X_ExternalSystem_ScriptedExportConversion_Status.EXPORTSTATUS_AD_Reference_ID);
	}
}
