package de.metas.ui.web.window.model;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BooleanSupplier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link DocumentCollection#shouldInvalidateRootOnChildInvalidation(boolean, boolean, boolean, boolean, BooleanSupplier)}.
 *
 * Covers the scenario where a child record is invalidated externally and the cached root
 * document is in error state — the root must be evicted so the next read loads a clean
 * document from the repository, otherwise the sticky error (and its potentially huge
 * {@code reason} string) survives indefinitely; plus the two guards that must NOT evict
 * (a new root, or a root owning an unsaved new in-memory included document).
 */
class DocumentCollectionShouldInvalidateRootTest
{
	private static final BooleanSupplier NO_UNSAVED_NEW_CHILD = () -> false;
	private static final BooleanSupplier HAS_UNSAVED_NEW_CHILD = () -> true;

	@Test
	void happyPath_noErrors_noFullInvalidationRequested_keepsRootCached()
	{
		assertThat(DocumentCollection.shouldInvalidateRootOnChildInvalidation(
				false, // callerRequestedFullInvalidation
				false, // rootHasSaveError
				true,  // rootValidStatusIsValid
				false, // rootIsNew
				NO_UNSAVED_NEW_CHILD
		)).isFalse();
	}

	@Test
	void fullInvalidationRequested_nonNewRootWithoutUnsavedChild_evictsRoot()
	{
		assertThat(DocumentCollection.shouldInvalidateRootOnChildInvalidation(true, false, true, false, NO_UNSAVED_NEW_CHILD)).isTrue();
		assertThat(DocumentCollection.shouldInvalidateRootOnChildInvalidation(true, true, false, false, NO_UNSAVED_NEW_CHILD)).isTrue();
	}

	@Test
	void rootInSaveErrorState_childInvalidation_evictsRoot()
	{
		assertThat(DocumentCollection.shouldInvalidateRootOnChildInvalidation(
				false, // callerRequestedFullInvalidation
				true,  // rootHasSaveError
				true,  // rootValidStatusIsValid — irrelevant once saveError is true
				false, // rootIsNew
				NO_UNSAVED_NEW_CHILD
		)).isTrue();
	}

	@Test
	void rootValidStatusInvalid_childInvalidation_evictsRoot()
	{
		assertThat(DocumentCollection.shouldInvalidateRootOnChildInvalidation(
				false, // callerRequestedFullInvalidation
				false, // rootHasSaveError
				false, // rootValidStatusIsValid = false
				false, // rootIsNew
				NO_UNSAVED_NEW_CHILD
		)).isTrue();
	}

	@Test
	void bothErrorFlagsSet_evictsRoot()
	{
		assertThat(DocumentCollection.shouldInvalidateRootOnChildInvalidation(false, true, false, false, NO_UNSAVED_NEW_CHILD)).isTrue();
	}

	@Test
	void newRoot_neverEvicts()
	{
		// a new (not-yet-persisted) root would otherwise be evicted (full-invalidation + save error +
		// invalid status) — but it must be kept, else it vanishes and the user gets a 404
		assertThat(DocumentCollection.shouldInvalidateRootOnChildInvalidation(
				true, true, false, true /* rootIsNew */, NO_UNSAVED_NEW_CHILD
		)).isFalse();
	}

	@Test
	void rootOwnsUnsavedNewIncludedDocument_neverEvicts()
	{
		assertThat(DocumentCollection.shouldInvalidateRootOnChildInvalidation(
				false, true, true, false, HAS_UNSAVED_NEW_CHILD  // save-error root, but owns unsaved new child
		)).isFalse();
		assertThat(DocumentCollection.shouldInvalidateRootOnChildInvalidation(
				false, false, false, false, HAS_UNSAVED_NEW_CHILD  // invalid root, but owns unsaved new child
		)).isFalse();
		assertThat(DocumentCollection.shouldInvalidateRootOnChildInvalidation(
				true, true, false, false, HAS_UNSAVED_NEW_CHILD  // even under full-invalidation, protect the unsaved new child
		)).isFalse();
	}

	@Test
	void unsavedNewChildCheck_isEvaluatedLazily()
	{
		final AtomicInteger calls = new AtomicInteger();
		final BooleanSupplier counting = () -> {
			calls.incrementAndGet();
			return false;
		};

		// happy path: the root would not be evicted anyway → the expensive child check must NOT run
		DocumentCollection.shouldInvalidateRootOnChildInvalidation(false, false, true, false, counting);
		assertThat(calls.get()).isZero();

		// would-evict path (save error): the expensive child check IS consulted
		DocumentCollection.shouldInvalidateRootOnChildInvalidation(false, true, true, false, counting);
		assertThat(calls.get()).isEqualTo(1);

		// new root short-circuits before the child check → still not consulted
		DocumentCollection.shouldInvalidateRootOnChildInvalidation(false, true, true, true, counting);
		assertThat(calls.get()).isEqualTo(1);
	}
}
