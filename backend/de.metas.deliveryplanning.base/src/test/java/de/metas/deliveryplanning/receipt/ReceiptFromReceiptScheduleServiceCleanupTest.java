package de.metas.deliveryplanning.receipt;

import de.metas.handlingunits.document.IHUAllocations;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * What a failed batch receive must undo.
 * <p>
 * A row's HUs are COMMITTED by the generator's own transaction and allocated against the receipt schedule as
 * they are made - {@code QtyMoved} moves there, not at the receipt. So a failure part-way through a batch would
 * otherwise leave earlier rows' HUs committed, counted against the schedule, and on no receipt; a retry would
 * read a smaller remainder and silently skip the row whose goods were never booked.
 * <p>
 * These cover the cleanup's own decisions - which schedule each HU is destroyed against, and that one refusal
 * does not abandon the rest - because both were wrong in earlier attempts at this code.
 */
class ReceiptFromReceiptScheduleServiceCleanupTest
{
	private final Map<I_M_ReceiptSchedule, List<I_M_HU>> destroyedBySchedule = new HashMap<>();
	private I_M_HU refuseThisOne = null;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		destroyedBySchedule.clear();
		refuseThisOne = null;
	}

	/** Records what the cleanup destroys, and against which schedule, without an HU graph. */
	private IHUAllocations allocationsFor(final I_M_ReceiptSchedule receiptSchedule)
	{
		final IHUAllocations allocations = mock(IHUAllocations.class);
		org.mockito.Mockito.doAnswer(invocation -> {
			final I_M_HU hu = invocation.getArgument(0);
			if (hu == refuseThisOne)
			{
				throw new RuntimeException("this HU refuses to be destroyed");
			}
			destroyedBySchedule.computeIfAbsent(receiptSchedule, k -> new ArrayList<>()).add(hu);
			return null;
		}).when(allocations).destroyAssignedHU(org.mockito.ArgumentMatchers.any());
		return allocations;
	}

	/** Saved so each gets its own id - two unsaved records both carry ID 0 and are indistinguishable as keys. */
	private static I_M_ReceiptSchedule schedule()
	{
		final I_M_ReceiptSchedule receiptSchedule = InterfaceWrapperHelper.newInstance(I_M_ReceiptSchedule.class);
		InterfaceWrapperHelper.save(receiptSchedule);
		return receiptSchedule;
	}

	private static I_M_HU hu()
	{
		final I_M_HU hu = InterfaceWrapperHelper.newInstance(I_M_HU.class);
		InterfaceWrapperHelper.save(hu);
		return hu;
	}

	@Nested
	@DisplayName("what a failed batch undoes")
	class Cleanup
	{
		@Test
		@DisplayName("every HU is destroyed against the schedule it was allocated against, not against the first one")
		void destroysAgainstItsOwnSchedule()
		{
			final I_M_ReceiptSchedule scheduleA = schedule();
			final I_M_ReceiptSchedule scheduleB = schedule();
			final I_M_HU huA = hu();
			final I_M_HU huB = hu();

			ReceiptFromReceiptScheduleService.destroyQuietly(
					ImmutableList.of(new ReceiptFromReceiptScheduleService.GeneratedHUs(scheduleA, ImmutableList.of(huA)),
							new ReceiptFromReceiptScheduleService.GeneratedHUs(scheduleB, ImmutableList.of(huB))),
					new RuntimeException("the receive failed"),
					ReceiptFromReceiptScheduleServiceCleanupTest.this::allocationsFor);

			assertThat(destroyedBySchedule.get(scheduleA))
					.as("destroying against the wrong schedule would leave QtyMoved wrong on BOTH of them")
					.containsExactly(huA);
			assertThat(destroyedBySchedule.get(scheduleB)).containsExactly(huB);
		}

		@Test
		@DisplayName("one HU refusing does not abandon the others - a cleanup that stops half way is not a cleanup")
		void oneRefusalDoesNotStopTheRest()
		{
			final I_M_ReceiptSchedule receiptSchedule = schedule();
			final I_M_HU first = hu();
			final I_M_HU refuses = hu();
			final I_M_HU last = hu();
			refuseThisOne = refuses;

			final RuntimeException cause = new RuntimeException("the receive failed");
			ReceiptFromReceiptScheduleService.destroyQuietly(
					ImmutableList.of(new ReceiptFromReceiptScheduleService.GeneratedHUs(receiptSchedule, ImmutableList.of(first, refuses, last))),
					cause,
					ReceiptFromReceiptScheduleServiceCleanupTest.this::allocationsFor);

			assertThat(destroyedBySchedule.get(receiptSchedule))
					.as("the HU AFTER the refusal still has to be cleaned up")
					.containsExactly(first, last);
		}

		@Test
		@DisplayName("a cleanup failure is suppressed onto the original error, never thrown over it")
		void cleanupFailureNeverReplacesTheRealError()
		{
			final I_M_ReceiptSchedule receiptSchedule = schedule();
			final I_M_HU refuses = hu();
			refuseThisOne = refuses;

			final RuntimeException cause = new RuntimeException("the receive failed");
			ReceiptFromReceiptScheduleService.destroyQuietly(
					ImmutableList.of(new ReceiptFromReceiptScheduleService.GeneratedHUs(receiptSchedule, ImmutableList.of(refuses))),
					cause,
					ReceiptFromReceiptScheduleServiceCleanupTest.this::allocationsFor);

			assertThat(cause.getSuppressed())
					.as("why the receive failed must survive - a cleanup problem must not become the error the operator reads")
					.hasSize(1);
			assertThat(cause.getMessage()).isEqualTo("the receive failed");
		}

		@Test
		@DisplayName("nothing generated, nothing to undo")
		void nothingGenerated()
		{
			ReceiptFromReceiptScheduleService.destroyQuietly(ImmutableList.of(), new RuntimeException("failed before anything was made"), ReceiptFromReceiptScheduleServiceCleanupTest.this::allocationsFor);

			assertThat(destroyedBySchedule).isEmpty();
		}
	}
}
