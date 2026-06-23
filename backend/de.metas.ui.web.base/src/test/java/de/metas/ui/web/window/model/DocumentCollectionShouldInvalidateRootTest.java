package de.metas.ui.web.window.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link DocumentCollection#shouldInvalidateRootOnChildInvalidation(boolean, boolean, boolean, boolean)}.
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
				true,  // rootValidStatusIsValid
				false  // rootHasUnsavedNewIncludedDocument
		)).isFalse();
	}

	@Test
	void fullInvalidationRequested_alwaysEvictsRoot()
	{
		assertThat(DocumentCollection.shouldInvalidateRootOnChildInvalidation(true, false, true, false)).isTrue();
		assertThat(DocumentCollection.shouldInvalidateRootOnChildInvalidation(true, true, false, false)).isTrue();
	}

	@Test
	void rootInSaveErrorState_childInvalidation_evictsRoot()
	{
		assertThat(DocumentCollection.shouldInvalidateRootOnChildInvalidation(
				false, // callerRequestedFullInvalidation
				true,  // rootHasSaveError
				true,  // rootValidStatusIsValid — irrelevant once saveError is true
				false  // rootHasUnsavedNewIncludedDocument
		)).isTrue();
	}

	@Test
	void rootValidStatusInvalid_childInvalidation_evictsRoot()
	{
		assertThat(DocumentCollection.shouldInvalidateRootOnChildInvalidation(
				false, // callerRequestedFullInvalidation
				false, // rootHasSaveError
				false, // rootValidStatusIsValid = false
				false  // rootHasUnsavedNewIncludedDocument
		)).isTrue();
	}

	@Test
	void bothErrorFlagsSet_evictsRoot()
	{
		assertThat(DocumentCollection.shouldInvalidateRootOnChildInvalidation(false, true, false, false)).isTrue();
	}

	@Test
	void rootOwnsUnsavedNewIncludedDocument_neverEvicts()
	{
		assertThat(DocumentCollection.shouldInvalidateRootOnChildInvalidation(
				false, true, true, true  // save-error root, but owns unsaved new child
		)).isFalse();
		assertThat(DocumentCollection.shouldInvalidateRootOnChildInvalidation(
				false, false, false, true  // invalid root, but owns unsaved new child
		)).isFalse();
		assertThat(DocumentCollection.shouldInvalidateRootOnChildInvalidation(
				true, true, false, true  // even under full-invalidation, protect the unsaved new child (mirrors the existing new-ROOT protection at the call site)
		)).isFalse();
	}
}
