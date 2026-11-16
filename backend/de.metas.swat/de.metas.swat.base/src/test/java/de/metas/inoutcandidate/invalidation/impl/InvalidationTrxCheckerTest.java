package de.metas.inoutcandidate.invalidation.impl;

import com.google.common.collect.ImmutableSet;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleUpdater;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateRepository;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.inoutcandidate.picking_bom.PickingBOMService;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_Product;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

class InvalidationTrxCheckerTest
{
	private ITrxManager trxManager;
	private InvalidationTrxChecker checker;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		trxManager = Services.get(ITrxManager.class);
		checker = new InvalidationTrxChecker();
	}

	private I_C_OrderLine newOrderLine(final String trxName)
	{
		final I_C_OrderLine orderLine = InterfaceWrapperHelper.newInstance(I_C_OrderLine.class, PlainContextAware.newWithTrxName(Env.getCtx(), trxName));
		InterfaceWrapperHelper.save(orderLine);
		return orderLine;
	}

	private I_M_InOut newInOut(final String trxName)
	{
		final I_M_InOut inOut = InterfaceWrapperHelper.newInstance(I_M_InOut.class, PlainContextAware.newWithTrxName(Env.getCtx(), trxName));
		InterfaceWrapperHelper.save(inOut);
		return inOut;
	}

	@Test
	void sameTrx_passes()
	{
		trxManager.runInThreadInheritedTrx(() -> {
			final String threadTrxName = trxManager.getThreadInheritedTrxName();
			final I_C_OrderLine orderLine = newOrderLine(threadTrxName);
			checker.checkRecordTrx(orderLine);
		});
	}

	@Test
	void differentActiveTrx_throws()
	{
		final String otherTrxName = trxManager.createTrxName("test-record-trx", true);
		trxManager.getTrx(otherTrxName).start();
		try
		{
			trxManager.runInThreadInheritedTrx(() -> {
				final I_C_OrderLine orderLine = newOrderLine(otherTrxName);
				assertThatThrownBy(() -> checker.checkRecordTrx(orderLine))
						.isInstanceOf(AdempiereException.class)
						.hasMessageContaining(otherTrxName);
			});
		}
		finally
		{
			trxManager.getTrx(otherTrxName).close();
		}
	}

	@Test
	void noThreadTrx_passes()
	{
		final String recordTrxName = trxManager.createTrxName("test-record-trx", true);
		trxManager.getTrx(recordTrxName).start();
		try
		{
			final I_C_OrderLine orderLine = newOrderLine(recordTrxName);
			assertThat(trxManager.isNull(trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone))).as("setup: no thread-inherited trx").isTrue();

			checker.checkRecordTrx(orderLine);
		}
		finally
		{
			trxManager.getTrx(recordTrxName).close();
		}
	}

	/** The record's trx already ended (or never started): lenient, not checked. */
	@Test
	void recordTrxEnded_passes()
	{
		final String recordTrxName = trxManager.createTrxName("test-record-trx", true);
		final ITrx recordTrx = trxManager.getTrx(recordTrxName);
		recordTrx.start();
		final I_C_OrderLine orderLine = newOrderLine(recordTrxName);
		recordTrx.close();
		assertThat(trxManager.isActive(recordTrxName)).as("setup: record trx ended").isFalse();

		trxManager.runInThreadInheritedTrx(() -> checker.checkRecordTrx(orderLine));
	}

	private IShipmentScheduleInvalidateRepository repo;

	private ShipmentScheduleInvalidateBL newBl(final InvalidationTrxChecker trxChecker)
	{
		repo = Mockito.mock(IShipmentScheduleInvalidateRepository.class);
		Services.registerService(IShipmentScheduleInvalidateRepository.class, repo);
		Services.registerService(IShipmentScheduleUpdater.class, Mockito.mock(IShipmentScheduleUpdater.class)); // isRunning() == false
		return new ShipmentScheduleInvalidateBL(new PickingBOMService(), trxChecker);
	}

	/** Shape of {@code C_OrderLine_ShipmentSchedule.invalidateShipmentSchedules}: runAfterCommit, then the BL calls. */
	@Test
	void afterCommitOrderLineCaller_passes()
	{
		final List<String> warnings = new ArrayList<>();
		final ShipmentScheduleInvalidateBL bl = newBl(new InvalidationTrxChecker(() -> true, () -> 1_000_000L, warnings::add));
		final AtomicReference<Throwable> failure = new AtomicReference<>();
		final AtomicLong callbackCompleted = new AtomicLong();

		trxManager.runInThreadInheritedTrx(() -> {
			final I_C_OrderLine orderLine = newOrderLine(trxManager.getThreadInheritedTrxName());
			newShipmentSchedule(trxManager.getThreadInheritedTrxName(), orderLine); // so that the BL call is not a no-op
			trxManager.runAfterCommit(() -> {
				try
				{
					bl.invalidateJustForOrderLine(orderLine);
					bl.notifySegmentChangedForOrderLine(orderLine);
					// the trx restarts on its first DB access, with the same name as the record's
					trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone).start();
					bl.invalidateJustForOrderLine(orderLine);
					bl.notifySegmentChangedForOrderLine(orderLine);
					callbackCompleted.incrementAndGet();
				}
				catch (final Throwable t) // after-commit listener errors are logged and skipped, so capture them here
				{
					failure.set(t);
				}
			});
		});

		assertThat(failure.get()).as("failure inside the after-commit callback").isNull();
		assertThat(warnings).as("mismatch warnings (the check can only warn inside the callback)").isEmpty();
		assertThat(callbackCompleted).as("callback ran to its end").hasValue(1);
	}

	/** Shape of {@code M_InOut_Shipment.invalidateShipmentSchedulesForLines}: runAfterCommit, then the BL call. */
	@Test
	void afterCommitShipmentCaller_passes()
	{
		final List<String> warnings = new ArrayList<>();
		final ShipmentScheduleInvalidateBL bl = newBl(new InvalidationTrxChecker(() -> true, () -> 1_000_000L, warnings::add));
		final AtomicReference<Throwable> failure = new AtomicReference<>();
		final AtomicLong callbackCompleted = new AtomicLong();

		trxManager.runInThreadInheritedTrx(() -> {
			final I_M_InOut inOut = newInOut(trxManager.getThreadInheritedTrxName());
			newAllocatedInOutLine(trxManager.getThreadInheritedTrxName(), inOut); // so that the BL call is not a no-op
			trxManager.runAfterCommit(() -> {
				try
				{
					bl.invalidateJustForLines(inOut);
					trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone).start();
					bl.invalidateJustForLines(inOut);
					callbackCompleted.incrementAndGet();
				}
				catch (final Throwable t)
				{
					failure.set(t);
				}
			});
		});

		assertThat(failure.get()).as("failure inside the after-commit callback").isNull();
		assertThat(warnings).as("mismatch warnings (the check can only warn inside the callback)").isEmpty();
		assertThat(callbackCompleted).as("callback ran to its end").hasValue(1);
	}

	private I_M_ShipmentSchedule newShipmentSchedule(final String trxName, final I_C_OrderLine orderLine)
	{
		final I_M_ShipmentSchedule schedule = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class, PlainContextAware.newWithTrxName(Env.getCtx(), trxName));
		schedule.setIsActive(true);
		schedule.setM_Product_ID(1);
		schedule.setAD_Table_ID(InterfaceWrapperHelper.getTableId(I_C_OrderLine.class));
		schedule.setRecord_ID(orderLine.getC_OrderLine_ID());
		InterfaceWrapperHelper.save(schedule);
		return schedule;
	}

	/** An inout line of the given inout, allocated (QtyPicked) to a schedule, so that BL calls on the inout / line have something to flag. */
	private I_M_InOutLine newAllocatedInOutLine(final String trxName, final I_M_InOut inOut)
	{
		final I_M_InOutLine line = InterfaceWrapperHelper.newInstance(I_M_InOutLine.class, PlainContextAware.newWithTrxName(Env.getCtx(), trxName));
		line.setM_InOut_ID(inOut.getM_InOut_ID());
		InterfaceWrapperHelper.save(line);

		final I_M_ShipmentSchedule schedule = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class, PlainContextAware.newWithTrxName(Env.getCtx(), trxName));
		schedule.setM_Product_ID(1);
		InterfaceWrapperHelper.save(schedule);

		final I_M_ShipmentSchedule_QtyPicked alloc = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule_QtyPicked.class, PlainContextAware.newWithTrxName(Env.getCtx(), trxName));
		alloc.setM_ShipmentSchedule_ID(schedule.getM_ShipmentSchedule_ID());
		alloc.setM_InOutLine_ID(line.getM_InOutLine_ID());
		InterfaceWrapperHelper.save(alloc);
		return line;
	}

	/** The records of one trx, handed to a BL call. */
	private static final class Records
	{
		I_C_OrderLine orderLine;
		I_M_InOut inOut;
		I_M_InOutLine inOutLine;
		I_M_ShipmentSchedule schedule;
	}

	private static Arguments blCase(final String name, final BiConsumer<ShipmentScheduleInvalidateBL, Records> call, final boolean stocked)
	{
		return arguments(name + (stocked ? " [stocked product: segment path]" : " [no product: narrowed to self]"), stocked, call);
	}

	static Stream<Arguments> blMethods()
	{
		final List<Arguments> args = new ArrayList<>();
		for (final boolean stocked : new boolean[] { false, true })
		{
			args.add(blCase("flagForRecompute(I_M_InOutLine)", (bl, r) -> bl.flagForRecompute(r.inOutLine), stocked));
			args.add(blCase("invalidateJustForLines", (bl, r) -> bl.invalidateJustForLines(r.inOut), stocked));
			args.add(blCase("notifySegmentsChangedForShipment", (bl, r) -> bl.notifySegmentsChangedForShipment(r.inOut), stocked));
			args.add(blCase("notifySegmentChangedForShipmentLine", (bl, r) -> bl.notifySegmentChangedForShipmentLine(r.inOutLine), stocked));
			args.add(blCase("notifySegmentChangedForShipmentSchedule", (bl, r) -> bl.notifySegmentChangedForShipmentSchedule(r.schedule), stocked));
			args.add(blCase("notifySegmentChangedForShipmentScheduleInclSched", (bl, r) -> bl.notifySegmentChangedForShipmentScheduleInclSched(r.schedule), stocked));
			args.add(blCase("notifySegmentChangedForOrderLine", (bl, r) -> bl.notifySegmentChangedForOrderLine(r.orderLine), stocked));
			args.add(blCase("invalidateJustForOrderLine", (bl, r) -> bl.invalidateJustForOrderLine(r.orderLine), stocked));
		}
		return args.stream();
	}

	private int newProduct(final boolean stocked)
	{
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product.setValue("p" + stocked);
		product.setName("p" + stocked);
		product.setProductType(X_M_Product.PRODUCTTYPE_Item);
		product.setIsStocked(stocked);
		InterfaceWrapperHelper.save(product);
		return product.getM_Product_ID();
	}

	/**
	 * Every record-based BL method rejects records in an active trx other than the thread trx (outside after-commit, so it throws), and no flag is written first.
	 * Each method runs twice: with no product (it narrows to a delegate that has its own check) and with a stocked product (the segment path,
	 * which has no nested check), so the method's own check is what throws in the stocked variant of the three methods that branch.
	 */
	@ParameterizedTest(name = "{0}")
	@MethodSource("blMethods")
	void blMethod_recordInOtherActiveTrx_throws(final String name, final boolean stocked, final BiConsumer<ShipmentScheduleInvalidateBL, Records> call)
	{
		final ShipmentScheduleInvalidateBL bl = newBl(new InvalidationTrxChecker());
		final String otherTrxName = trxManager.createTrxName("test-record-trx", true);
		trxManager.getTrx(otherTrxName).start();
		try
		{
			final int productId = stocked ? newProduct(true) : 0;
			final Records records = new Records();
			records.orderLine = newOrderLine(otherTrxName);
			records.orderLine.setM_Product_ID(productId);
			InterfaceWrapperHelper.save(records.orderLine);
			records.inOut = newInOut(otherTrxName);
			records.inOutLine = newAllocatedInOutLine(otherTrxName, records.inOut);
			records.inOutLine.setM_Product_ID(productId);
			InterfaceWrapperHelper.save(records.inOutLine);
			records.schedule = newShipmentSchedule(otherTrxName, records.orderLine);

			trxManager.runInThreadInheritedTrx(() -> assertThatThrownBy(() -> call.accept(bl, records))
					.as(name)
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining("Recompute flags for"));
			Mockito.verifyZeroInteractions(repo); // no repository write happened before the check threw
		}
		finally
		{
			trxManager.getTrx(otherTrxName).close();
		}
	}

	/** The id-only overload checks the caller's data trx, and no flag is written before the check throws. */
	@Test
	void idOverload_differentTrx_throws()
	{
		final ShipmentScheduleInvalidateBL bl = newBl(new InvalidationTrxChecker());
		final String otherTrxName = trxManager.createTrxName("test-data-trx", true);
		trxManager.getTrx(otherTrxName).start();
		try
		{
			trxManager.runInThreadInheritedTrx(() -> assertThatThrownBy(() -> bl.flagForRecompute(ImmutableSet.of(ShipmentScheduleId.ofRepoId(1)), otherTrxName))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining("Recompute flags for")
					.hasMessageContaining(otherTrxName));
			Mockito.verifyZeroInteractions(repo);
		}
		finally
		{
			trxManager.getTrx(otherTrxName).close();
		}
	}

	@Test
	void idOverload_sameTrx_flags()
	{
		final ShipmentScheduleInvalidateBL bl = newBl(new InvalidationTrxChecker());
		final ImmutableSet<ShipmentScheduleId> ids = ImmutableSet.of(ShipmentScheduleId.ofRepoId(1));

		trxManager.runInThreadInheritedTrx(() -> bl.flagForRecompute(ids, trxManager.getThreadInheritedTrxName()));

		Mockito.verify(repo).invalidateShipmentSchedules(ids);
	}

	/** In an after-commit callback (listener errors are swallowed) a mismatch must only warn, and the flag write must still happen. */
	@Test
	void mismatchInAfterCommitCallback_warnsAndDoesNotThrow_evenInThrowMode()
	{
		final List<String> warnings = new ArrayList<>();
		final InvalidationTrxChecker throwModeChecker = new InvalidationTrxChecker(() -> true, () -> 1_000_000L, warnings::add);
		final String otherTrxName = trxManager.createTrxName("test-record-trx", true);
		trxManager.getTrx(otherTrxName).start();
		final AtomicReference<Throwable> failure = new AtomicReference<>();
		try
		{
			final I_C_OrderLine orderLine = newOrderLine(otherTrxName);
			trxManager.runInThreadInheritedTrx(() -> trxManager.runAfterCommit(() -> {
				try
				{
					trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone).start(); // restarted: the thread trx is open for writes again
					throwModeChecker.checkRecordTrx(orderLine);
				}
				catch (final Throwable t)
				{
					failure.set(t);
				}
			}));
		}
		finally
		{
			trxManager.getTrx(otherTrxName).close();
		}

		assertThat(failure.get()).isNull();
		assertThat(warnings).hasSize(1);
		assertThat(warnings.get(0)).contains("Recompute flags for").contains(otherTrxName);
	}

	/** Production mode (no fail): warns, throttled to one per 60 s, and the next warning carries the suppressed count. */
	@Test
	void productionWarnMode_throttlesAndCountsSuppressed()
	{
		final List<String> warnings = new ArrayList<>();
		final AtomicLong clock = new AtomicLong(1_000_000L);
		final InvalidationTrxChecker prodChecker = new InvalidationTrxChecker(() -> false, clock::get, warnings::add);
		final String otherTrxName = trxManager.createTrxName("test-record-trx", true);
		trxManager.getTrx(otherTrxName).start();
		try
		{
			final I_C_OrderLine orderLine = newOrderLine(otherTrxName);
			trxManager.runInThreadInheritedTrx(() -> {
				prodChecker.checkRecordTrx(orderLine); // warns
				clock.addAndGet(10_000);
				prodChecker.checkRecordTrx(orderLine); // suppressed
				prodChecker.checkRecordTrx(orderLine); // suppressed
				assertThat(warnings).as("within 60 s").hasSize(1);
				assertThat(warnings.get(0)).doesNotContain("suppressed");

				clock.addAndGet(60_000);
				prodChecker.checkRecordTrx(orderLine); // warns again, with the count
			});
		}
		finally
		{
			trxManager.getTrx(otherTrxName).close();
		}

		assertThat(warnings).hasSize(2);
		assertThat(warnings.get(1)).contains("2 similar warnings suppressed");
	}
}
