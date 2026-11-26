package de.metas.ui.web.window.model;

import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Covers the computed {@code userValidationError} flag: it must be true iff the save error was
 * produced by a user-fixable business rejection (an {@link AdempiereException} marked as a user
 * validation error, e.g. a unique-constraint violation), and false for system/technical errors and
 * for statuses carrying no exception. {@link DocumentCollection} relies on this to keep a
 * user-validation error visible instead of self-healing it away.
 */
class DocumentSaveStatusTest
{
	@Test
	void error_withUserValidationException_isUserValidationError()
	{
		final AdempiereException ex = new AdempiereException("duplicate ValidFrom").markAsUserValidationError();
		final DocumentSaveStatus status = DocumentSaveStatus.error(ex, DocumentSaveStatus.unknown(true));
		assertThat(status.isError()).isTrue();
		assertThat(status.isUserValidationError()).isTrue();
	}

	@Test
	void error_withSystemException_isNotUserValidationError()
	{
		final DocumentSaveStatus status = DocumentSaveStatus.error(new RuntimeException("boom"), DocumentSaveStatus.unknown(true));
		assertThat(status.isError()).isTrue();
		assertThat(status.isUserValidationError()).isFalse();
	}

	@Test
	void statusesWithoutException_areNotUserValidationError()
	{
		assertThat(DocumentSaveStatus.saved().isUserValidationError()).isFalse();
		assertThat(DocumentSaveStatus.unknown(true).isUserValidationError()).isFalse();
		assertThat(DocumentSaveStatus.notSavedJustCreated().isUserValidationError()).isFalse();
	}
}
