package de.metas.lock.api.impl;

import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.assertj.core.api.AbstractBooleanAssert;
import org.compiere.model.I_Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LockManagerTest
{
	private PlainLockManager lockManager;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		this.lockManager = (PlainLockManager)Services.get(ILockManager.class);
	}

	TableRecordReference newRecord()
	{
		final I_Test record = InterfaceWrapperHelper.newInstance(I_Test.class);
		InterfaceWrapperHelper.save(record);
		return TableRecordReference.of(record);
	}

	@NonNull
	private AbstractBooleanAssert<?> assertLocked(final TableRecordReference recordRef, LockOwner lockOwner)
	{
		return assertThat(lockManager.isLocked(recordRef, lockOwner))
				.as("Assert locked: " + recordRef);
	}

	@SuppressWarnings("AssertThatIsZeroOne")
	@Nested
	class split
	{
		@Test
		void standardCase()
		{
			final TableRecordReference recordRef = newRecord();
			assertLocked(recordRef, LockOwner.ANY).isFalse();

			final ILock mainLock = lockManager.lock()
					.setOwner(LockOwner.forOwnerName("MAIN"))
					.setFailIfAlreadyLocked(true)
					.setFailIfNothingLocked(true)
					.setRecordByRecordReference(recordRef)
					.acquire();
			assertLocked(recordRef, mainLock.getOwner()).isTrue();
			assertThat(mainLock.getCountLocked()).isEqualTo(1);
			assertThat(mainLock.getCountTransferredFromParent()).isEqualTo(0);

			final ILock childLock = mainLock.split()
					.setOwner(LockOwner.forOwnerName("CHILD_LOCK"))
					.setRecordByRecordReference(recordRef)
					.acquire();
			assertLocked(recordRef, childLock.getOwner()).isTrue();
			assertThat(childLock.getCountLocked()).isEqualTo(1);
			assertThat(childLock.getCountTransferredFromParent()).isEqualTo(1);
			assertLocked(recordRef, mainLock.getOwner()).isFalse();
			assertThat(mainLock.getCountLocked()).isEqualTo(0);
			assertThat(mainLock.getCountTransferredFromParent()).isEqualTo(0);
		}

		@Test
		void recordWasNotLockedByParent()
		{
			final TableRecordReference recordRef1 = newRecord();
			assertLocked(recordRef1, LockOwner.ANY).isFalse();

			final ILock mainLock = lockManager.lock()
					.setOwner(LockOwner.forOwnerName("MAIN"))
					.setFailIfAlreadyLocked(true)
					.setFailIfNothingLocked(true)
					.setRecordByRecordReference(recordRef1)
					.acquire();
			assertLocked(recordRef1, mainLock.getOwner()).isTrue();
			assertThat(mainLock.getCountLocked()).isEqualTo(1);

			//
			// Expect recordRef2 to be locked even if it was not locked by parent
			// nevertheless, a warning will be logged
			try (IAutoCloseable ignored = lockManager.withFailOnWarnings(false))
			{
				final TableRecordReference recordRef2 = newRecord();

				final ILock childLock = mainLock.split()
						.setOwner(LockOwner.forOwnerName("CHILD_LOCK"))
						.setRecordByRecordReference(recordRef2)
						.acquire();
				assertLocked(recordRef2, childLock.getOwner()).isTrue();
				assertLocked(recordRef2, mainLock.getOwner()).isFalse();

				assertLocked(recordRef1, mainLock.getOwner()).isTrue(); // still locked, nothing changed
			}

			//
			// Expect recordRef3 will fail to be locked because it's not locked by parent
			// (happens in testing, when developer mode is enabled)
			try (IAutoCloseable ignored = lockManager.withFailOnWarnings(true))
			{
				final TableRecordReference recordRef3 = newRecord();

				assertThatThrownBy(() -> {
					mainLock.split()
							.setOwner(LockOwner.forOwnerName("CHILD_LOCK_2"))
							.setRecordByRecordReference(recordRef3)
							.acquire();
				})
						.hasMessageStartingWith("Could not transfer locked record from parent");
			}
		}

	}
}