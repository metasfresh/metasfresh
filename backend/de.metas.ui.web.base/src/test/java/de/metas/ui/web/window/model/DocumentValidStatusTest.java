package de.metas.ui.web.window.model;

import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Covers the computed {@code userValidationError} flag: true iff the invalid state was produced by a
 * user-fixable business rejection (an {@link AdempiereException} marked as a user validation error),
 * false for system/technical errors, for a mandatory-not-filled state (no exception), and for the
 * valid / initial states. {@link DocumentCollection} relies on this to keep such an error visible.
 */
class DocumentValidStatusTest
{
	@Test
	void invalid_withUserValidationException_isUserValidationError()
	{
		final AdempiereException ex = new AdempiereException("duplicate ValidFrom").markAsUserValidationError();
		final DocumentValidStatus status = DocumentValidStatus.invalid(ex);
		assertThat(status.isValid()).isFalse();
		assertThat(status.isUserValidationError()).isTrue();
	}

	@Test
	void invalid_withSystemException_isNotUserValidationError()
	{
		final DocumentValidStatus status = DocumentValidStatus.invalid(new RuntimeException("boom"));
		assertThat(status.isValid()).isFalse();
		assertThat(status.isUserValidationError()).isFalse();
	}

	@Test
	void nonExceptionStatuses_areNotUserValidationError()
	{
		assertThat(DocumentValidStatus.documentValid().isUserValidationError()).isFalse();
		assertThat(DocumentValidStatus.invalidFieldMandatoryNotFilled("MyField", true).isUserValidationError()).isFalse();
		assertThat(DocumentValidStatus.documentInitiallyInvalid().isUserValidationError()).isFalse();
	}
}
