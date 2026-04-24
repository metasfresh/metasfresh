package de.metas.ui.web.window.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies that {@link DocumentSaveStatus#toString()} and
 * {@link DocumentValidStatus#toString()} cap their {@code reason} portion, so the
 * {@code toString()} of an enclosing {@link Document} cannot trigger exponential
 * growth when an exception message is built from {@code document.toString()} and
 * then becomes the next {@code reason}.
 *
 * Regression test: a poisoned document can accumulate thousands of recursively-nested
 * {@code Document{...}} snapshots in {@code validStatus.reason} / {@code saveStatus.reason},
 * producing 100+ MB JSON responses. Capping the reason inside {@code toString()} breaks
 * the doubling feedback loop.
 */
class DocumentStatusReasonTruncationTest
{
	/** Max length applied inside the toString() methods. Must match the value used in the production code. */
	private static final int EXPECTED_REASON_CAP = 200;

	/** Slack for the other toString parts (flags, prefix/suffix, truncation marker). */
	private static final int TOSTRING_OVERHEAD = 500;

	private static String longMessage()
	{
		// 10 000 chars — well above the cap and large enough to detect any unbounded
		// embedding in a single-iteration test.
		final StringBuilder sb = new StringBuilder(10_000);
		for (int i = 0; i < 10_000; i++)
		{
			sb.append('x');
		}
		return sb.toString();
	}

	@Test
	void documentSaveStatus_toString_bounds_reason_length()
	{
		final String bigMessage = longMessage();

		final DocumentSaveStatus status = DocumentSaveStatus.error(
				new RuntimeException(bigMessage),
				DocumentSaveStatus.savedJustLoaded());

		final String s = status.toString();

		assertThat(s.length())
				.as("DocumentSaveStatus.toString() must cap reason to avoid recursive blow-up")
				.isLessThan(EXPECTED_REASON_CAP + TOSTRING_OVERHEAD);

		assertThat(s)
				.as("truncation marker must be present when reason exceeds cap")
				.contains("...(+")
				.contains("chars)");
	}

	@Test
	void documentValidStatus_toString_bounds_reason_length()
	{
		final String bigMessage = longMessage();

		final DocumentValidStatus status = DocumentValidStatus.invalid(new RuntimeException(bigMessage));

		final String s = status.toString();

		assertThat(s.length())
				.as("DocumentValidStatus.toString() must cap reason to avoid recursive blow-up")
				.isLessThan(EXPECTED_REASON_CAP + TOSTRING_OVERHEAD);

		assertThat(s)
				.as("truncation marker must be present when reason exceeds cap")
				.contains("...(+")
				.contains("chars)");
	}

	@Test
	void documentSaveStatus_toString_keeps_short_reason_intact()
	{
		final DocumentSaveStatus status = DocumentSaveStatus.error(
				new RuntimeException("short reason"),
				DocumentSaveStatus.savedJustLoaded());

		assertThat(status.toString())
				.as("short reasons must pass through unchanged")
				.contains("short reason")
				.doesNotContain("...(+");
	}

	@Test
	void documentValidStatus_toString_keeps_short_reason_intact()
	{
		final DocumentValidStatus status = DocumentValidStatus.invalid(new RuntimeException("short reason"));

		assertThat(status.toString())
				.as("short reasons must pass through unchanged")
				.contains("short reason")
				.doesNotContain("...(+");
	}
}
