package de.metas.ui.web.window.model;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BooleanSupplier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link DocumentCollection#shouldInvalidateRootOnChildInvalidation(boolean, boolean, boolean, boolean, boolean, boolean, BooleanSupplier)}.
 *
 * Covers the scenario where a child record is invalidated externally and the cached root
 * document is in error state — a <b>system/technical</b> error root must be evicted so the next
 * read loads a clean document from the repository, otherwise the sticky error (and its potentially
 * huge {@code reason} string) survives indefinitely; a <b>user-validation</b> error root (a
 * user-fixable business rejection such as a unique-constraint violation) must instead be KEPT, so
 * the user keeps seeing why their edit was rejected instead of it silently reverting. Plus the two
 * guards that must NOT evict (a new root, or a root owning an unsaved new in-memory included document).
 */
class DocumentCollectionShouldInvalidateRootTest
{
	private static final BooleanSupplier NO_UNSAVED_NEW_CHILD = () -> false;
	private static final BooleanSupplier HAS_UNSAVED_NEW_CHILD = () -> true;

	// Signature reminder (positional):
	// (callerRequestedFullInvalidation, rootHasSaveError, rootSaveErrorIsUserValidation,
	//  rootValidStatusIsValid, rootValidStatusInvalidIsUserValidation, rootIsNew, rootHasUnsavedNewIncludedDocument)

	@Test
	void happyPath_noErrors_noFullInvalidationRequested_keepsRootCached()
	{
		assertThat(DocumentCollection.shouldInvalidateRootOnChildInvalidation(
				false, // callerRequestedFullInvalidation
				false, // rootHasSaveError
				false, // rootSaveErrorIsUserValidation
				true,  // rootValidStatusIsValid
				false, // rootValidStatusInvalidIsUserValidation
				false, // rootIsNew
				NO_UNSAVED_NEW_CHILD
		)).isFalse();
	}

	@Test
	void fullInvalidationRequested_nonNewRootWithoutUnsavedChild_evictsRoot()
	{
		assertThat(DocumentCollection.shouldInvalidateRootOnChildInvalidation(true, false, false, true, false, false, NO_UNSAVED_NEW_CHILD)).isTrue();
		assertThat(DocumentCollection.shouldInvalidateRootOnChildInvalidation(true, true, false, false, false, false, NO_UNSAVED_NEW_CHILD)).isTrue();
	}

	@Test
	void rootInSystemSaveErrorState_childInvalidation_evictsRoot()
	{
		assertThat(DocumentCollection.shouldInvalidateRootOnChildInvalidation(
				false, // callerRequestedFullInvalidation
				true,  // rootHasSaveError
				false, // rootSaveErrorIsUserValidation — a system/technical error
				true,  // rootValidStatusIsValid
				false, // rootValidStatusInvalidIsUserValidation
				false, // rootIsNew
				NO_UNSAVED_NEW_CHILD
		)).isTrue();
	}

	@Test
	void rootValidStatusInvalid_system_childInvalidation_evictsRoot()
	{
		assertThat(DocumentCollection.shouldInvalidateRootOnChildInvalidation(
				false, // callerRequestedFullInvalidation
				false, // rootHasSaveError
				false, // rootSaveErrorIsUserValidation
				false, // rootValidStatusIsValid = false
				false, // rootValidStatusInvalidIsUserValidation — a system/technical invalid state
				false, // rootIsNew
				NO_UNSAVED_NEW_CHILD
		)).isTrue();
	}

	@Test
	void bothErrorFlagsSet_system_evictsRoot()
	{
		assertThat(DocumentCollection.shouldInvalidateRootOnChildInvalidation(false, true, false, false, false, false, NO_UNSAVED_NEW_CHILD)).isTrue();
	}

	// --- user-validation carve-out: a user-fixable business rejection must NOT be self-healed away -------

	@Test
	void rootInUserValidationSaveError_childInvalidation_keepsRoot()
	{
		// save error IS a user-validation error (e.g. duplicate ValidFrom) and the valid-status is valid:
		// the only eviction trigger is the user-validation error, so the root must be KEPT so the user
		// keeps seeing the rejection reason.
		assertThat(DocumentCollection.shouldInvalidateRootOnChildInvalidation(
				false, // callerRequestedFullInvalidation
				true,  // rootHasSaveError
				true,  // rootSaveErrorIsUserValidation
				true,  // rootValidStatusIsValid
				false, // rootValidStatusInvalidIsUserValidation
				false, // rootIsNew
				NO_UNSAVED_NEW_CHILD
		)).isFalse();
	}

	@Test
	void rootValidStatusInvalid_userValidation_childInvalidation_keepsRoot()
	{
		assertThat(DocumentCollection.shouldInvalidateRootOnChildInvalidation(
				false, // callerRequestedFullInvalidation
				false, // rootHasSaveError
				false, // rootSaveErrorIsUserValidation
				false, // rootValidStatusIsValid = false
				true,  // rootValidStatusInvalidIsUserValidation
				false, // rootIsNew
				NO_UNSAVED_NEW_CHILD
		)).isFalse();
	}

	@Test
	void duplicateDateCase_bothFlagsSetBothUserValidation_keepsRoot()
	{
		// The real edit-existing-PLV-to-a-colliding-date case: the failed save sets BOTH saveStatus.error
		// and validStatus.invalid, both carrying the same user-validation DBUniqueConstraintException.
		assertThat(DocumentCollection.shouldInvalidateRootOnChildInvalidation(
				false, // callerRequestedFullInvalidation
				true,  // rootHasSaveError
				true,  // rootSaveErrorIsUserValidation
				false, // rootValidStatusIsValid = false
				true,  // rootValidStatusInvalidIsUserValidation
				false, // rootIsNew
				NO_UNSAVED_NEW_CHILD
		)).isFalse();
	}

	@Test
	void userValidationSaveError_butFullInvalidationRequested_stillEvicts()
	{
		// A whole-table (full) invalidation is an independent eviction reason: it evicts even when the
		// save error itself is only a user-validation error.
		assertThat(DocumentCollection.shouldInvalidateRootOnChildInvalidation(
				true,  // callerRequestedFullInvalidation
				true,  // rootHasSaveError
				true,  // rootSaveErrorIsUserValidation
				true,  // rootValidStatusIsValid
				false, // rootValidStatusInvalidIsUserValidation
				false, // rootIsNew
				NO_UNSAVED_NEW_CHILD
		)).isTrue();
	}

	@Test
	void userValidationSaveError_butSystemInvalidStatus_stillEvicts()
	{
		// Mixed: the save error is user-validation, but the valid-status is invalid for a SYSTEM reason —
		// the system invalid state is still an independent eviction reason.
		assertThat(DocumentCollection.shouldInvalidateRootOnChildInvalidation(
				false, // callerRequestedFullInvalidation
				true,  // rootHasSaveError
				true,  // rootSaveErrorIsUserValidation
				false, // rootValidStatusIsValid = false
				false, // rootValidStatusInvalidIsUserValidation — system invalid
				false, // rootIsNew
				NO_UNSAVED_NEW_CHILD
		)).isTrue();
	}

	// --- guards that must never evict --------------------------------------------------------------------

	@Test
	void newRoot_neverEvicts()
	{
		// a new (not-yet-persisted) root would otherwise be evicted (full-invalidation + save error +
		// invalid status) — but it must be kept, else it vanishes and the user gets a 404
		assertThat(DocumentCollection.shouldInvalidateRootOnChildInvalidation(
				true, true, false, false, false, true /* rootIsNew */, NO_UNSAVED_NEW_CHILD
		)).isFalse();
	}

	@Test
	void rootOwnsUnsavedNewIncludedDocument_neverEvicts()
	{
		assertThat(DocumentCollection.shouldInvalidateRootOnChildInvalidation(
				false, true, false, true, false, false, HAS_UNSAVED_NEW_CHILD  // system save-error root, but owns unsaved new child
		)).isFalse();
		assertThat(DocumentCollection.shouldInvalidateRootOnChildInvalidation(
				false, false, false, false, false, false, HAS_UNSAVED_NEW_CHILD  // system invalid root, but owns unsaved new child
		)).isFalse();
		assertThat(DocumentCollection.shouldInvalidateRootOnChildInvalidation(
				true, true, false, false, false, false, HAS_UNSAVED_NEW_CHILD  // even under full-invalidation, protect the unsaved new child
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
		DocumentCollection.shouldInvalidateRootOnChildInvalidation(false, false, false, true, false, false, counting);
		assertThat(calls.get()).isZero();

		// would-evict path (system save error): the expensive child check IS consulted
		DocumentCollection.shouldInvalidateRootOnChildInvalidation(false, true, false, true, false, false, counting);
		assertThat(calls.get()).isEqualTo(1);

		// new root short-circuits before the child check → still not consulted
		DocumentCollection.shouldInvalidateRootOnChildInvalidation(false, true, false, true, false, true, counting);
		assertThat(calls.get()).isEqualTo(1);

		// user-validation-only error would NOT evict → the child check must NOT run (no eviction reason)
		DocumentCollection.shouldInvalidateRootOnChildInvalidation(false, true, true, true, false, false, counting);
		assertThat(calls.get()).isEqualTo(1);
	}
}
