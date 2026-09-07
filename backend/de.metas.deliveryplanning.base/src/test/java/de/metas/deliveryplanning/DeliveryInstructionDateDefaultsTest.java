/*
 * #%L
 * de.metas.deliveryplanning.base
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

package de.metas.deliveryplanning;

import com.google.common.collect.ImmutableList;
import de.metas.document.dimension.DimensionService;
import de.metas.document.engine.DocStatus;
import de.metas.product.ProductId;
import de.metas.shipping.MPackageRepository;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.uom.UomId;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.I_M_Delivery_Planning_Alloc;
import org.compiere.model.I_M_Package;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The delivery instruction's dates are DEFAULTS seeded from the plannings allocated to it: each field is
 * filled only while it is still empty, on creation and on every later add.
 * <p>
 * Running on every add rather than only at creation is what makes the feature usable on real data: a
 * planning whose upstream date chain produced nothing leaves the instruction empty, and only a later add can
 * supply the dates. The null guard is what makes running every time safe - it can only fill blanks.
 */
class DeliveryInstructionDateDefaultsTest
{
	private DeliveryPlanningRepository deliveryPlanningRepository;
	private DeliveryPlanningAllocRepository deliveryPlanningAllocRepository;
	private DeliveryInstructionRepository deliveryInstructionRepository;
	private DeliveryInstructionService deliveryInstructionService;
	private M_ShipperTransportation_WriteCounter headerWriteCounter;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		deliveryPlanningRepository = new DeliveryPlanningRepository(Mockito.mock(DimensionService.class));
		deliveryPlanningAllocRepository = new DeliveryPlanningAllocRepository();
		deliveryInstructionRepository = new DeliveryInstructionRepository(Mockito.mock(DimensionService.class));
		deliveryInstructionService = new DeliveryInstructionService(
				deliveryPlanningRepository, deliveryPlanningAllocRepository, deliveryInstructionRepository, new MPackageRepository());

		headerWriteCounter = new M_ShipperTransportation_WriteCounter();
		POJOLookupMap.get().addModelValidator(headerWriteCounter);
	}

	/** The instant a request carries. Fixed to UTC midnight so no assertion here depends on the machine's zone. */
	private static Instant dayInstant(final int dayOfMonth)
	{
		return LocalDate.of(2026, 3, dayOfMonth).atStartOfDay(ZoneId.of("UTC")).toInstant();
	}

	/** The same point in time as {@link #dayInstant(int)}, in the type the {@code M_ShipperTransportation} columns use. */
	private static Timestamp day(final int dayOfMonth)
	{
		return TimeUtil.asTimestamp(dayInstant(dayOfMonth));
	}

	private static I_M_ShipperTransportation draftDeliveryInstruction(
			@Nullable final Timestamp etd,
			@Nullable final Timestamp eta)
	{
		final I_M_ShipperTransportation record = InterfaceWrapperHelper.newInstance(I_M_ShipperTransportation.class);
		record.setDocumentNo("DATE-DEFAULTS");
		record.setDocStatus(DocStatus.Drafted.getCode());
		record.setETD(etd);
		record.setETA(eta);
		return record;
	}

	private static I_M_ShipperTransportation savedDeliveryInstruction(
			@Nullable final Timestamp etd,
			@Nullable final Timestamp eta)
	{
		final I_M_ShipperTransportation record = draftDeliveryInstruction(etd, eta);
		InterfaceWrapperHelper.save(record);
		return record;
	}

	/**
	 * A real, persisted row - not a fabricated id - because {@code createAllocations} now reads the
	 * planning back to recompute the instruction's {@code DeliveredState}, so a request naming an id with no
	 * backing row fails there instead of silently "succeeding" as it used to.
	 */
	private static DeliveryPlanningId createDeliveryPlanning()
	{
		final I_M_Delivery_Planning record = InterfaceWrapperHelper.newInstance(I_M_Delivery_Planning.class);
		InterfaceWrapperHelper.save(record);
		return DeliveryPlanningId.ofRepoId(record.getM_Delivery_Planning_ID());
	}

	private static DeliveryPlanningAllocCreateRequest allocRequest(
			@Nullable final Instant etd,
			@Nullable final Instant eta,
			@Nullable final String loadingTime)
	{
		return DeliveryPlanningAllocCreateRequest.builder()
				.deliveryPlanningId(createDeliveryPlanning())
				.shippingPackage(DeliveryPlanningAllocCreateRequest.ShippingPackageData.builder()
						.productId(ProductId.ofRepoId(540010))
						.uomId(UomId.ofRepoId(uom().getC_UOM_ID()))
						.build())
				.headerDateCandidate(DeliveryPlanningAllocCreateRequest.HeaderDateCandidate.builder()
						.etd(etd)
						.eta(eta)
						.loadingTime(loadingTime)
						.build())
				.build();
	}

	private static I_C_UOM uomInstance;

	private static I_C_UOM uom()
	{
		if (uomInstance == null)
		{
			uomInstance = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
			InterfaceWrapperHelper.save(uomInstance);
		}
		return uomInstance;
	}

	private static DeliveryInstructionDates resolve(
			@NonNull final I_M_ShipperTransportation instruction,
			@NonNull final DeliveryPlanningAllocCreateRequest... requests)
	{
		final List<DeliveryPlanningAllocCreateRequest> requestList = ImmutableList.copyOf(requests);
		return DeliveryPlanningService.resolveInstructionDatesForAllocation(instruction, requestList);
	}

	@Test
	@DisplayName("an add fills every empty date from the added planning, and derives ATD/ATA from the filled fields")
	void addFillsEmptyDatesAndDerivesActuals()
	{
		final I_M_ShipperTransportation instruction = draftDeliveryInstruction(null, null);

		final DeliveryInstructionDates resolved = resolve(instruction, allocRequest(dayInstant(3), dayInstant(7), "08:00"));

		assertThat(resolved.getEtd()).as("ETD seeded from the planning").isEqualTo(dayInstant(3));
		assertThat(resolved.getEta()).as("ETA seeded from the planning").isEqualTo(dayInstant(7));
		assertThat(resolved.getLoadingTime()).as("LoadingTime seeded from the planning").isEqualTo("08:00");
		assertThat(resolved.getAtd())
				.as("ATD derives from the instruction's ETD FIELD after the fill, exactly as the transport-order "
						+ "precedent does, so a planner-set ETD propagates into ATD")
				.isEqualTo(dayInstant(3));
		assertThat(resolved.getAta()).as("ATA derives from the filled ETA").isEqualTo(dayInstant(7));
	}

	@Test
	@DisplayName("a date the instruction already carries is never overwritten, but its empty siblings are still filled")
	void existingDateSurvivesWhileEmptySiblingsAreFilled()
	{
		final I_M_ShipperTransportation instruction = draftDeliveryInstruction(day(1), null);

		final DeliveryInstructionDates resolved = resolve(instruction, allocRequest(dayInstant(3), dayInstant(7), null));

		assertThat(resolved.getEtd())
				.as("these are defaults - a value entered before the allocation must be kept")
				.isEqualTo(dayInstant(1));
		assertThat(resolved.getEta())
				.as("per field, not per document: one field being set must not skip the whole seed")
				.isEqualTo(dayInstant(7));
		assertThat(resolved.getAtd()).as("ATD follows the planner's ETD, not the planning's").isEqualTo(dayInstant(1));
	}

	@Test
	@DisplayName("a planning with no dates leaves the instruction's resolution empty - no derived actuals from nothing")
	void planningWithoutDatesLeavesTheInstructionEmpty()
	{
		final I_M_ShipperTransportation instruction = draftDeliveryInstruction(null, null);

		final DeliveryInstructionDates resolved = resolve(instruction, allocRequest(null, null, null));

		assertThat(resolved.getEtd()).isNull();
		assertThat(resolved.getEta()).isNull();
		assertThat(resolved.getAtd())
				.as("an unset ETD must never derive a phantom ATD")
				.isNull();
		assertThat(resolved.getAta()).isNull();
	}

	@Test
	@DisplayName("createAllocations persists a pre-set date untouched and writes its empty sibling - the real write path, not just the resolution")
	void createAllocationsPersistsExistingDateAndFillsEmptySibling()
	{
		final I_M_ShipperTransportation instruction = savedDeliveryInstruction(day(1), null);
		final ShipperTransportationId instructionId = ShipperTransportationId.ofRepoId(instruction.getM_ShipperTransportation_ID());

		final DeliveryPlanningAllocCreateRequest request = allocRequest(dayInstant(3), dayInstant(7), "08:00");
		final DeliveryInstructionDates resolvedDates = resolve(instruction, request);

		deliveryInstructionService.createAllocations(instructionId, ImmutableList.of(request), resolvedDates);

		final I_M_ShipperTransportation reloaded = InterfaceWrapperHelper.load(instructionId, I_M_ShipperTransportation.class);
		assertThat(reloaded.getETD())
				.as("the date already on the instruction before the allocation must survive the actual write")
				.isEqualTo(day(1));
		assertThat(reloaded.getETA())
				.as("the empty sibling must actually be persisted, not only resolved in memory")
				.isEqualTo(day(7));
		assertThat(reloaded.getATD()).as("ATD persisted from the pre-set ETD").isEqualTo(day(1));
		assertThat(reloaded.getATA()).as("ATA persisted from the newly-filled ETA").isEqualTo(day(7));
	}

	@Test
	@DisplayName("createAllocations writes no derived actuals when the planning carries no dates - the real write path, not just the resolution")
	void createAllocationsPersistsNoActualsWhenPlanningHasNoDates()
	{
		final I_M_ShipperTransportation instruction = savedDeliveryInstruction(null, null);
		final ShipperTransportationId instructionId = ShipperTransportationId.ofRepoId(instruction.getM_ShipperTransportation_ID());

		final DeliveryPlanningAllocCreateRequest request = allocRequest(null, null, null);
		final DeliveryInstructionDates resolvedDates = resolve(instruction, request);

		deliveryInstructionService.createAllocations(instructionId, ImmutableList.of(request), resolvedDates);

		final I_M_ShipperTransportation reloaded = InterfaceWrapperHelper.load(instructionId, I_M_ShipperTransportation.class);
		assertThat(reloaded.getETD()).isNull();
		assertThat(reloaded.getETA()).isNull();
		assertThat(reloaded.getATD()).as("an unset ETD must never persist a phantom ATD").isNull();
		assertThat(reloaded.getATA()).isNull();
	}

	/**
	 * Pins the instant that travels instruction ETA -> {@code MPackageCreateRequest} -> {@code M_Package.ShipDate}.
	 * The request carries an {@code Instant} while both ends are {@code java.sql.Timestamp} columns, so the
	 * conversion happens twice; this asserts the point in time is identical on both sides - no zone applied, no
	 * truncation - and, because the ETA asserted here is one the add itself filled, that the header is still
	 * written before the packages are built.
	 */
	@Test
	@DisplayName("the allocation's M_Package carries the instruction's ETA as its ShipDate - the same instant, unshifted")
	void allocationPackageShipDateIsTheInstructionEtaVerbatim()
	{
		final I_M_ShipperTransportation instruction = savedDeliveryInstruction(null, null);
		final ShipperTransportationId instructionId = ShipperTransportationId.ofRepoId(instruction.getM_ShipperTransportation_ID());

		final DeliveryPlanningAllocCreateRequest request = allocRequest(dayInstant(3), dayInstant(7), "08:00");
		final DeliveryInstructionDates resolvedDates = resolve(instruction, request);

		final DeliveryPlanningAllocId allocId = deliveryInstructionService
				.createAllocations(instructionId, ImmutableList.of(request), resolvedDates)
				.get(0);

		final I_M_Delivery_Planning_Alloc alloc = InterfaceWrapperHelper.load(allocId, I_M_Delivery_Planning_Alloc.class);
		final I_M_ShippingPackage shippingPackage = InterfaceWrapperHelper.load(alloc.getM_ShippingPackage_ID(), I_M_ShippingPackage.class);
		final I_M_Package mPackage = InterfaceWrapperHelper.load(shippingPackage.getM_Package_ID(), I_M_Package.class);

		assertThat(mPackage.getShipDate())
				.as("ShipDate is seeded from the instruction's ETA - which this very add filled, so the header write "
						+ "has to happen before the package is built")
				.isEqualTo(day(7));
		assertThat(mPackage.getShipDate().toInstant())
				.as("the exact point in time survives the request's Instant <-> Timestamp conversions")
				.isEqualTo(day(7).toInstant());
	}

	/**
	 * Counts the saves that actually reach the instruction header. {@code POJOLookupMap.save} fires
	 * {@code BEFORE_CHANGE} on every save of an ALREADY-PERSISTED row - whether or not any column value differs -
	 * so a fire here means {@code saveRecord} was really called, which is exactly what the no-op guard suppresses.
	 */
	@Interceptor(I_M_ShipperTransportation.class)
	static class M_ShipperTransportation_WriteCounter
	{
		private int writes = 0;

		@ModelChange(types = ModelChangeType.BEFORE_CHANGE)
		public void count(@NonNull final I_M_ShipperTransportation record)
		{
			writes++;
		}
	}

	/** An instruction that already carries all six date fields, so a re-resolution of the same values is a no-op. */
	private static I_M_ShipperTransportation savedInstructionCarryingAllDates()
	{
		final I_M_ShipperTransportation record = draftDeliveryInstruction(day(1), day(7));
		record.setATD(day(1));
		record.setATA(day(7));
		record.setLoadingTime("08:00");
		record.setDeliveryTime("17:00");
		InterfaceWrapperHelper.save(record);
		return record;
	}

	/** The dates {@link #savedInstructionCarryingAllDates()} carries, with {@code ETA} left free to vary. */
	private static DeliveryInstructionDates datesWithEta(@Nullable final Instant eta)
	{
		return DeliveryInstructionDates.builder()
				.etd(dayInstant(1))
				.eta(eta)
				.atd(dayInstant(1))
				.ata(dayInstant(7))
				.loadingTime("08:00")
				.deliveryTime("17:00")
				.build();
	}

	/**
	 * The no-op guard in {@code DeliveryInstructionRepository#updateDates}. It matters beyond saving a round trip:
	 * a header save fires the {@code M_ShipperTransportation} interceptor chain, which syncs the header's dates back
	 * DOWN onto every allocated planning. The guard therefore has to compare both sides in the SAME type - compare a
	 * resolved date against the record's own in DIFFERENT types and every field answers "differs", the guard is
	 * permanently open, and every resolution writes.
	 */
	@Test
	@DisplayName("updateDates writes nothing when every resolved date already equals the one on the instruction")
	void updateDatesDoesNotWriteWhenNothingChanged()
	{
		final I_M_ShipperTransportation instruction = savedInstructionCarryingAllDates();
		final int writesBefore = headerWriteCounter.writes;

		deliveryInstructionRepository.updateDates(instruction, datesWithEta(dayInstant(7)));

		assertThat(headerWriteCounter.writes)
				.as("a resolution that changes nothing must cost no write, so no sync-down onto the plannings is fired")
				.isEqualTo(writesBefore);
	}

	@Test
	@DisplayName("updateDates does write when a single resolved date differs")
	void updateDatesWritesWhenOneDateDiffers()
	{
		final I_M_ShipperTransportation instruction = savedInstructionCarryingAllDates();
		final int writesBefore = headerWriteCounter.writes;

		deliveryInstructionRepository.updateDates(instruction, datesWithEta(dayInstant(9)));

		assertThat(headerWriteCounter.writes)
				.as("the guard must let a real change through - one differing field is enough")
				.isEqualTo(writesBefore + 1);
		assertThat(instruction.getETA()).as("the differing date is the one that lands on the record").isEqualTo(day(9));
	}
}
