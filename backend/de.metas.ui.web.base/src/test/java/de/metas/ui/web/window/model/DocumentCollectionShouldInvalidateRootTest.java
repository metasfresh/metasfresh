package de.metas.ui.web.window.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link DocumentCollection#shouldInvalidateRootOnChildInvalidation(boolean, boolean, boolean)}.
 *
 * Covers the scenario where a child record is invalidated externally and the cached root
 * document is in error state — the root must be evicted so the next read loads a clean
 * document from the repository, otherwise the sticky error (and its potentially huge
 * {@code reason} string) survives indefinitely.
 */
class DocumentCollectionShouldInvalidateRootTest
{
	@Test
	void happyPath_noErrors_noFullInvalidationRequested_keepsRootCached()
	{
		assertThat(DocumentCollection.shouldInvalidateRootOnChildInvalidation(
				false, // callerRequestedFullInvalidation
				false, // rootHasSaveError
				true   // rootValidStatusIsValid
		)).isFalse();
	}

	@Test
	void fullInvalidationRequested_alwaysEvictsRoot()
	{
		assertThat(DocumentCollection.shouldInvalidateRootOnChildInvalidation(true, false, true)).isTrue();
		assertThat(DocumentCollection.shouldInvalidateRootOnChildInvalidation(true, true, false)).isTrue();
	}

	@Test
	void rootInSaveErrorState_childInvalidation_evictsRoot()
	{
		assertThat(DocumentCollection.shouldInvalidateRootOnChildInvalidation(
				false, // callerRequestedFullInvalidation
				true,  // rootHasSaveError
				true   // rootValidStatusIsValid — irrelevant once saveError is true
		)).isTrue();
	}

	@Test
	void rootValidStatusInvalid_childInvalidation_evictsRoot()
	{
		assertThat(DocumentCollection.shouldInvalidateRootOnChildInvalidation(
				false, // callerRequestedFullInvalidation
				false, // rootHasSaveError
				false  // rootValidStatusIsValid = false
		)).isTrue();
	}

	@Test
	void bothErrorFlagsSet_evictsRoot()
	{
		assertThat(DocumentCollection.shouldInvalidateRootOnChildInvalidation(false, true, false)).isTrue();
	}
}
