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

package de.metas.deliveryplanning.receipt;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.common.util.time.SystemTime;
import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.deliveryplanning.ReceiptScheduleAndDeliveryPlanningId;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.document.sequence.DocSequenceId;
import de.metas.handlingunits.receiptschedule.impl.ReceiptScheduleHUAllocations;
import de.metas.handlingunits.document.IHUAllocations;
import de.metas.handlingunits.receiptschedule.impl.ReceiptScheduleHUGenerator;
import de.metas.handlingunits.receiptschedule.ReceiptScheduleLUTUConfigurations;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.ClearanceStatus;
import de.metas.handlingunits.ClearanceStatusInfo;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.attribute.HUAttributeUpdateRequest;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactoryService;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InOut;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.TranslatableStrings;
import de.metas.inout.InOutId;
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.api.impl.ReceiptMovementDateRule;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.InstantAndOrgId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Value;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.ILotNumberBL;
import org.adempiere.mm.attributes.api.LotNoContext;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.function.Function;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

/**
 * The ONE way a material receipt is created out of a receipt schedule here, for a delivery planning and for a
 * bare receipt schedule alike - so that the nullable {@code M_Delivery_Planning_ID} cannot be forgotten by one
 * of two copies, as the HU-editor path does today.
 */
@Service
@RequiredArgsConstructor
public class ReceiptFromReceiptScheduleService
{
	private static final AdMessageKey MESSAGE_ClearanceStatusInfo_Receipt = AdMessageKey.of("ClearanceStatusInfo.Receipt");

	@NonNull private final DeliveryPlanningService deliveryPlanningService;

	private final IHUReceiptScheduleBL huReceiptScheduleBL = Services.get(IHUReceiptScheduleBL.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final ILotNumberBL lotNumberBL = Services.get(ILotNumberBL.class);
	private final IBPartnerOrgBL partnerOrgBL = Services.get(IBPartnerOrgBL.class);
	private final IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);
	private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
	private final ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);
	private final IAttributeStorageFactoryService attributeStorageFactoryService = Services.get(IAttributeStorageFactoryService.class);

	public CreateReceiptFromReceiptScheduleResult receiveCUs(
			@NonNull final ReceiptScheduleAndDeliveryPlanningId sourceIds,
			@Nullable final BigDecimal qtyToReceiveOverride)
	{
		final I_M_ReceiptSchedule receiptSchedule = huReceiptScheduleBL.getById(sourceIds.getReceiptScheduleId());
		final Quantity qtyToReceive = qtyToReceiveOverride != null
				? Quantitys.of(qtyToReceiveOverride, UomId.ofRepoId(receiptSchedule.getC_UOM_ID()))
				: getQtyToReceive(receiptSchedule, sourceIds.getDeliveryPlanningId());

		final HuId vhuId = createPlanningVHU(receiptSchedule, qtyToReceive);
		if (vhuId == null)
		{
			throw new AdempiereException("Nothing to receive for " + sourceIds.getReceiptScheduleId())
					.markAsUserValidationError();
		}

		final CreateReceiptFromReceiptScheduleResult result = createReceipt(CreateReceiptFromReceiptScheduleRequest.builder()
				.receiptScheduleId(sourceIds.getReceiptScheduleId())
				.deliveryPlanningId(sourceIds.getDeliveryPlanningId())
				.huIdsToReceive(ImmutableSet.of(vhuId))
				.movementDateRule(ReceiptMovementDateRule.CURRENT_DATE)
				.build());

		return result;
	}

	/**
	 * Every row of the selection is received in ONE call, so the receipts come out grouped by the STANDARD
	 * criteria - {@code InOutProducer#isNewReceiptRequired}'s header aggregation key plus an unchanged
	 * {@code C_Order_ID} - exactly as the receipt-schedule window's batch would group them. The delivery
	 * planning does not enter the grouping: it lives on the receipt LINE, so one receipt can carry several.
	 * <p>
	 * Several rows can point at ONE schedule (a split shares it), so each row draws only its own planning's
	 * share, and a row with nothing left contributes nothing.
	 *
	 * @return the receipts created, in creation order; empty when the whole selection had nothing left to receive.
	 */
	public ImmutableList<InOutId> receiveRows(@NonNull final List<ReceiptScheduleAndDeliveryPlanningId> rows)
	{
		// Every HU this call creates, tracked so a failure part-way through can undo them.
		//
		// The generation commits in its OWN transaction (ReceiptScheduleHUGenerator#generateWithinOwnTransaction)
		// and allocates against the receipt schedule as it goes - QtyMoved moves THERE, not at the receipt. So an
		// exception on a later row would otherwise leave the earlier rows' HUs committed, counted against the
		// schedule, and attached to no receipt at all; a retry would then read a smaller remainder and silently
		// skip the row whose goods were never booked. The generator's own reuse pass is what normally reclaims
		// such strays, and this call deliberately turns that off (see createPackedHUs), so the cleanup is ours.
		final List<GeneratedHUs> generated = new ArrayList<>();
		try
		{
			return receiveRows0(rows, generated);
		}
		catch (final RuntimeException ex)
		{
			destroyQuietly(generated, ex);
			throw ex;
		}
	}

	private ImmutableList<InOutId> receiveRows0(
			@NonNull final List<ReceiptScheduleAndDeliveryPlanningId> rows,
			@NonNull final List<GeneratedHUs> generated)
	{
		final List<I_M_ReceiptSchedule> receiptSchedules = new ArrayList<>();
		final LinkedHashMap<HuId, DeliveryPlanningId> deliveryPlanningIdByHuId = new LinkedHashMap<>();
		final ImmutableSet.Builder<HuId> huIdsToReceive = ImmutableSet.builder();

		for (final ReceiptScheduleAndDeliveryPlanningId row : rows)
		{
			final I_M_ReceiptSchedule receiptSchedule = huReceiptScheduleBL.getById(row.getReceiptScheduleId());
			// An earlier receive may have booked against this very schedule - the split case, where several
			// plannings share one. Without this the row would draw the quantity that receive already took.
			InterfaceWrapperHelper.refresh(receiptSchedule);

			final DeliveryPlanningId deliveryPlanningId = row.getDeliveryPlanningId();
			final ImmutableSet<HuId> rowHuIds = createPackedHUs(receiptSchedule, deliveryPlanningId, generated);
			if (rowHuIds.isEmpty())
			{
				// Nothing left on this schedule: skip the row rather than failing the whole selection - the
				// batch behaviour a dispatcher receiving a screenful of rows needs.
				continue;
			}

			// Deduplicated because a split's siblings hand over the SAME schedule: the HUs carry the
			// quantities, so the schedule only has to be processed once.
			if (receiptSchedules.stream().noneMatch(alreadyThere -> alreadyThere.getM_ReceiptSchedule_ID() == receiptSchedule.getM_ReceiptSchedule_ID()))
			{
				receiptSchedules.add(receiptSchedule);
			}
			huIdsToReceive.addAll(rowHuIds);

			if (deliveryPlanningId != null)
			{
				// EVERY HU of the row, not one: a packed row can yield several TUs and an LU, and the receipt
				// line's planning is resolved per HU (InOutProducerFromReceiptScheduleHU#extractDeliveryPlanningId).
				rowHuIds.forEach(huId -> deliveryPlanningIdByHuId.put(huId, deliveryPlanningId));
			}
		}

		final ImmutableSet<HuId> huIds = huIdsToReceive.build();
		if (huIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final InOutGenerateResult result = generateReceipts(
				receiptSchedules, huIds, deliveryPlanningIdByHuId, ReceiptMovementDateRule.CURRENT_DATE);

		return result.getInOuts().stream()
				.map(receipt -> InOutId.ofRepoId(receipt.getM_InOut_ID()))
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * Split out so a test can substitute the allocations without standing up an HU graph - the cleanup's own
	 * logic (which schedule each HU is destroyed against, and that one failure does not abandon the rest) is what
	 * needs covering, not this construction.
	 */
	@VisibleForTesting
	IHUAllocations huAllocationsFor(@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		return new ReceiptScheduleHUAllocations(receiptSchedule);
	}

	/** One row's generated HUs together with the schedule they were allocated against. */
	@Value
	@VisibleForTesting
	static class GeneratedHUs
	{
		@NonNull I_M_ReceiptSchedule receiptSchedule;
		@NonNull List<I_M_HU> hus;
	}

	/**
	 * Undoes what a failed batch created, so nothing stays counted against a receipt schedule it never reached a
	 * receipt on.
	 * <p>
	 * Via {@link IHUAllocations#destroyAssignedHU}, NOT a bare {@code markDestroyed}. Destroying the HU alone
	 * flips its status and nothing else: the {@code M_ReceiptSchedule_Alloc} row survives, pointing at a
	 * destroyed HU, and {@code QtyMoved} - which that table's AFTER_DELETE hook is what decrements - stays
	 * inflated. A retry would then read a smaller remainder and silently skip the row whose goods were never
	 * booked, which is the whole failure this cleanup exists to prevent. Deleting the allocation is what reverts
	 * the quantity; the destroy on its own would only look like a fix.
	 * <p>
	 * Each HU is destroyed against ITS OWN schedule's allocations, and one HU's failure does not abandon the
	 * rest - {@code markDestroyed(Collection)} aborts the whole loop on the first HU that refuses (one that has
	 * packages assigned, say), which in a cleanup path would silently leave everything after it behind.
	 * <p>
	 * Failures are suppressed onto the original exception: the caller is already leaving with a real error, and
	 * losing it to a cleanup problem would hide why the receive failed in the first place.
	 * <p>
	 * Two properties of the collaborator worth knowing before changing anything here, both checked rather than
	 * assumed:
	 * <ul>
	 * <li>{@code AbstractHUAllocations#destroyAssignedHU} deletes the allocation FIRST and marks the HU destroyed
	 * second, so an HU that refused would be left with its allocation already gone. Not reachable from here: the
	 * only refusal {@code markDestroyed} raises is {@code ERR_HUHasPackages}, and an HU generated seconds earlier
	 * in this very batch carries no {@code M_Package}. Catching per HU does not widen that either - the delete
	 * precedes the throw whether or not the loop goes on.</li>
	 * <li>{@code generateWithinOwnTransaction} returns TOP-LEVEL HUs, so these can be LUs whose TUs are not
	 * listed. Enough for the quantity, which is what this cleanup is for:
	 * {@code HUReceiptScheduleDAO#deleteHandlingUnitAllocations} matches {@code M_LU_HU_ID} as well as
	 * {@code M_TU_HU_ID}, so every nested TU's allocation row goes with its LU's. The nested TUs keep their own
	 * status under the destroyed LU - as they do after the generator's own reuse pass, which destroys top-level
	 * HUs the same way.</li>
	 * </ul>
	 */
	private void destroyQuietly(@NonNull final List<GeneratedHUs> generated, @NonNull final RuntimeException cause)
	{
		destroyQuietly(generated, cause, this::huAllocationsFor);
	}

	/**
	 * The cleanup loop itself, taking the allocations lookup as a function so it can be exercised without
	 * constructing this service - its own constructor needs a Spring context, which has nothing to do with the
	 * decisions being made here.
	 */
	@VisibleForTesting
	static void destroyQuietly(
			@NonNull final List<GeneratedHUs> generated,
			@NonNull final RuntimeException cause,
			@NonNull final Function<I_M_ReceiptSchedule, IHUAllocations> allocationsFactory)
	{
		for (final GeneratedHUs entry : generated)
		{
			final IHUAllocations huAllocations = allocationsFactory.apply(entry.getReceiptSchedule());
			for (final I_M_HU hu : entry.getHus())
			{
				try
				{
					huAllocations.destroyAssignedHU(hu);
				}
				catch (final RuntimeException cleanupFailure)
				{
					cause.addSuppressed(cleanupFailure);
				}
			}
		}
	}

	/**
	 * A split copies {@code M_ReceiptSchedule_ID} onto every new planning, so N plannings share ONE schedule and
	 * the schedule's remaining quantity is the whole ORDER LINE's: reading the schedule for a planned row would
	 * let the first planning consume the entire line and starve its siblings. A receipt occupies the DISCHARGE
	 * end, which is why the discharge figure is the one read.
	 */
	public Quantity getQtyToReceive(
			@NonNull final I_M_ReceiptSchedule receiptSchedule,
			@Nullable final DeliveryPlanningId deliveryPlanningId)
	{
		final Quantity scheduleRemainder = getDefaultQtyToReceive(receiptSchedule);

		return getPlannedShareToReceive(receiptSchedule, deliveryPlanningId)
				.map(plannedShare -> plannedShare.min(scheduleRemainder))
				.orElse(scheduleRemainder);
	}

	/**
	 * Empty for an unplanned row, and for a planning that carries no discharge figure yet (zero, the value a
	 * freshly generated planning has) - not an error but "the planning has not said". The caller picks the
	 * fallback: the schedule's remainder for a CU receive, the packing's own total for an HU receive.
	 */
	public Optional<Quantity> getPlannedShareToReceive(
			@NonNull final I_M_ReceiptSchedule receiptSchedule,
			@Nullable final DeliveryPlanningId deliveryPlanningId)
	{
		if (deliveryPlanningId == null)
		{
			return Optional.empty();
		}

		final BigDecimal plannedDischargeQty = deliveryPlanningService.getPlannedDischargeQuantity(deliveryPlanningId);
		if (plannedDischargeQty == null || plannedDischargeQty.signum() <= 0)
		{
			return Optional.empty();
		}

		return Optional.of(Quantitys.of(plannedDischargeQty, UomId.ofRepoId(receiptSchedule.getC_UOM_ID())));
	}

	/**
	 * The whole ORDER LINE's remainder, which is why it is only ever the FALLBACK of {@link #getQtyToReceive} and
	 * never the answer for a planned row.
	 */
	public Quantity getDefaultQtyToReceive(@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		final StockQtyAndUOMQty qtyToMove = receiptScheduleBL.getQtyToMove(receiptSchedule);
		final BigDecimal qty = qtyToMove == null || qtyToMove.signum() <= 0
				? BigDecimal.ZERO
				: qtyToMove.getStockQty().toBigDecimal();

		return Quantitys.of(qty, UomId.ofRepoId(receiptSchedule.getC_UOM_ID()));
	}

	/**
	 * {@link CreateReceiptFromReceiptScheduleRequest#getDeliveryPlanningId()} is handed to
	 * {@code CreateReceiptsParameters} rather than written onto the finished document, because this call completes
	 * the receipt before returning.
	 */
	public CreateReceiptFromReceiptScheduleResult createReceipt(@NonNull final CreateReceiptFromReceiptScheduleRequest request)
	{
		final I_M_ReceiptSchedule receiptSchedule = huReceiptScheduleBL.getById(request.getReceiptScheduleId());

		final InOutGenerateResult result = generateReceipts(
				ImmutableList.of(receiptSchedule),
				request.getHuIdsToReceive(),
				deliveryPlanningIdByHuId(request.getHuIdsToReceive(), request.getDeliveryPlanningId()),
				request.getMovementDateRule());

		final I_M_InOut receipt = result.getSingleInOut(I_M_InOut.class);

		return CreateReceiptFromReceiptScheduleResult.builder()
				.receiptId(InOutId.ofRepoId(receipt.getM_InOut_ID()))
				.receivedHuIds(request.getHuIdsToReceive())
				.productId(ProductId.ofRepoId(receiptSchedule.getM_Product_ID()))
				.build();
	}

	/**
	 * Every HU in {@code deliveryPlanningIdByHuId} gets its planning stamped onto the receipt LINE it lands on,
	 * so one receipt can carry several plannings.
	 */
	private InOutGenerateResult generateReceipts(
			@NonNull final List<I_M_ReceiptSchedule> receiptSchedules,
			@NonNull final ImmutableSet<HuId> huIdsToReceive,
			@NonNull final Map<HuId, DeliveryPlanningId> deliveryPlanningIdByHuId,
			@NonNull final ReceiptMovementDateRule movementDateRule)
	{
		return huReceiptScheduleBL.processReceiptSchedules(
				IHUReceiptScheduleBL.CreateReceiptsParameters.builder()
						.commitEachReceiptIndividually(false)
						.movementDateRule(movementDateRule)
						.ctx(Env.getCtx())
						.destinationLocatorIdOrNull(null) // use receipt schedules' destination-warehouse settings
						.printReceiptLabels(true)
						.receiptSchedules(receiptSchedules)
						.selectedHuIds(huIdsToReceive)
						.deliveryPlanningIdByHuId(deliveryPlanningIdByHuId)
						.build());
	}

	/**
	 * The single-planning shape of the map: every HU of this receive belongs to the one planning the request
	 * names, and an unplanned request maps none.
	 * <p>
	 * Public because the HU-EDITOR path builds the very same map at CONFIRM time - out of the HUs the operator
	 * ended up with rather than out of the ones that were generated; see {@code HUEditorReceiptSources}. ONE
	 * definition, because a second one keyed differently would silently yield receipt lines with no planning.
	 */
	public static ImmutableMap<HuId, DeliveryPlanningId> deliveryPlanningIdByHuId(
			@NonNull final Set<HuId> huIds,
			@Nullable final DeliveryPlanningId deliveryPlanningId)
	{
		if (deliveryPlanningId == null)
		{
			return ImmutableMap.of();
		}
		return huIds.stream().collect(ImmutableMap.toImmutableMap(huId -> huId, huId -> deliveryPlanningId));
	}

	/**
	 * Returns {@code null} when there is nothing to receive, so a caller receiving a whole selection can skip an
	 * exhausted line instead of failing the batch.
	 */
	@Nullable
	public HuId createPlanningVHU(
			@NonNull final ReceiptScheduleId receiptScheduleId,
			@NonNull final Quantity qtyToReceive)
	{
		return createPlanningVHU(huReceiptScheduleBL.getById(receiptScheduleId), qtyToReceive);
	}

	/**
	 * The HUs to receive for ONE row, built to that row's own packing configuration.
	 * <p>
	 * Empty when there is nothing left to receive, which a batch skips rather than failing on.
	 * <p>
	 * The cap is the whole difficulty. {@link ReceiptScheduleLUTUConfigurations#adjustToDefaults} derives the
	 * LU/TU quantities from the SCHEDULE, and a split copies {@code M_ReceiptSchedule_ID} onto every planning it
	 * creates - so without {@link ReceiptScheduleLUTUConfigurations#capToPlannedShare} the first planning of a
	 * split would pack, and book, the entire order line and starve its siblings. An UNPLANNED row has no share
	 * and is left at the schedule-derived figure, which is the right one there.
	 */
	private ImmutableSet<HuId> createPackedHUs(
			@NonNull final I_M_ReceiptSchedule receiptSchedule,
			@Nullable final DeliveryPlanningId deliveryPlanningId,
			@NonNull final List<GeneratedHUs> generated)
	{
		final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(receiptSchedule.getAD_Client_ID(), receiptSchedule.getAD_Org_ID());
		final IMutableHUContext huContext = huContextFactory.createMutableHUContextForProcessing(Env.getCtx(), clientAndOrgId);

		final Quantity plannedShare = getPlannedShareToReceive(receiptSchedule, deliveryPlanningId).orElse(null);

		final I_M_HU_LUTU_Configuration lutuConfig = ReceiptScheduleLUTUConfigurations.getCurrent(receiptSchedule);
		final I_M_HU_LUTU_Configuration effectiveConfig = ReceiptScheduleLUTUConfigurations.newDefaultCopy(lutuConfig, receiptSchedule);
		ReceiptScheduleLUTUConfigurations.capToPlannedShare(effectiveConfig, plannedShare);
		lutuConfigurationFactory.save(effectiveConfig);

		final ReceiptScheduleHUGenerator huGenerator = ReceiptScheduleHUGenerator.newInstance(huContext)
				.addM_ReceiptSchedule(receiptSchedule)
				.setUpdateReceiptScheduleDefaultConfiguration(false)
				// A batch receives SEVERAL plannings of one schedule, each with its own share, so their HUs have
				// to coexist. The generator's default would let this row's generation find the previous row's
				// planning HUs, judge them unfit for a configuration capped to a different share, and destroy
				// them - the split's first sibling would silently lose its goods on the way to the receipt.
				.setReuseExistingPlanningHUs(false);
		huGenerator.setM_HU_LUTU_Configuration(effectiveConfig);

		final Quantity qtyCUsTotal = huGenerator.getLUTUProducerAllocationDestination().calculateTotalQtyCU();
		if (qtyCUsTotal.isInfinite())
		{
			throw new AdempiereException("LU/TU configuration is resulting to infinite quantity: " + effectiveConfig);
		}

		// The configuration's capacity is NOT the share, and assuming it was is how a sibling gets robbed.
		// capToPlannedShare rounds the TU count UP - it has to, because a partial TU still has to be received -
		// so for any share that is not an exact multiple of QtyCUsPerTU the capped configuration holds MORE than
		// the share: 15 CUs at ten per TU gives two TUs, i.e. capacity 20. Allocating that capacity would draw
		// five units belonging to the planning's sibling on the same receipt schedule. The allocation is
		// therefore clamped separately, exactly as the per-row receive does it
		// (ReceiptDispositionDeliveryPlanningReceiveHUsProcess#getQtyToAllocate).
		final Quantity qtyToAllocate = plannedShare != null ? qtyCUsTotal.min(plannedShare) : qtyCUsTotal;
		if (qtyToAllocate.signum() <= 0)
		{
			return ImmutableSet.of();
		}
		huGenerator.setQtyToAllocateTarget(qtyToAllocate);

		final List<I_M_HU> hus = huGenerator.generateWithinOwnTransaction();

		// Recorded HERE, not where this method returns. generateWithinOwnTransaction has already COMMITTED these
		// in its own transaction, so anything that throws below - updatePlanningHUAttributes writes attributes and
		// draws a lot number from a sequence - would otherwise leave them committed and untracked, and the cleanup
		// would never learn they exist.
		generated.add(new GeneratedHUs(receiptSchedule, hus));

		// The same finishing step the per-row receive applies to the HUs it generates
		// (ReceiptDispositionDeliveryPlanningReceiveHUsProcess): lot number, best-before and vendor, read off the
		// receipt schedule. It sets ATTRIBUTES only and deliberately does not touch HUStatus - the generator
		// already creates these in Planning, which is what createReceipts requires.
		updatePlanningHUAttributes(hus, receiptSchedule);

		return hus.stream()
				.map(hu -> HuId.ofRepoId(hu.getM_HU_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}

	@Nullable
	private HuId createPlanningVHU(
			@NonNull final I_M_ReceiptSchedule receiptSchedule,
			@NonNull final Quantity qtyToReceive)
	{
		if (qtyToReceive.signum() <= 0)
		{
			return null;
		}

		final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(receiptSchedule.getAD_Client_ID(), receiptSchedule.getAD_Org_ID());
		final IMutableHUContext huContextInitial = huContextFactory.createMutableHUContextForProcessing(Env.getCtx(), clientAndOrgId);

		final I_M_Product product = productDAO.getById(receiptSchedule.getM_Product_ID());
		final ClearanceStatus clearanceStatus = ClearanceStatus.ofNullableCode(product.getHUClearanceStatus());
		final ClearanceStatusInfo clearanceStatusInfo;
		if (clearanceStatus != null)
		{
			final String language = partnerOrgBL.getOrgLanguageOrLoggedInUserLanguage(clientAndOrgId.getOrgId());
			clearanceStatusInfo = ClearanceStatusInfo.builder()
					.clearanceStatus(clearanceStatus)
					.clearanceNote(TranslatableStrings.adMessage(MESSAGE_ClearanceStatusInfo_Receipt).translate(language))
					.clearanceDate(InstantAndOrgId.ofInstant(SystemTime.asInstant(), clientAndOrgId.getOrgId()))
					.build();
		}
		else
		{
			clearanceStatusInfo = null;
		}

		final IAllocationRequest allocationRequest = AllocationUtils.builder()
				.setHUContext(huContextInitial)
				.setDateAsToday()
				.setProduct(product)
				.setQuantity(qtyToReceive)
				.setFromReferencedModel(receiptSchedule)
				.setForceQtyAllocation(true)
				.setClearanceStatusInfo(clearanceStatusInfo)
				.create();

		// make sure the attributes are initialized (task 09717)
		huReceiptScheduleBL.setInitialAttributeValueDefaults(allocationRequest, ImmutableList.of(receiptSchedule));

		final IAllocationSource allocationSource = huReceiptScheduleBL.createAllocationSource(receiptSchedule);
		final HUProducerDestination huProducer = HUProducerDestination.ofVirtualPI();

		HULoader.of(allocationSource, huProducer)
				.setAllowPartialUnloads(false)
				.setAllowPartialLoads(false)
				.load(allocationRequest);

		final List<I_M_HU> hus = huProducer.getCreatedHUs();
		if (hus == null || hus.size() != 1)
		{
			throw new HUException("One and only one VHU was expected but we got: " + hus);
		}
		final I_M_HU vhu = hus.get(0);

		updatePlanningHUAttributes(ImmutableList.of(vhu), receiptSchedule);

		return HuId.ofRepoId(vhu.getM_HU_ID());
	}

	/**
	 * Takes the whole batch rather than one HU at a time: the lot number drawn from the doc-type sequence is drawn
	 * ONCE per receive and shared by every HU of it. Called per HU it would burn one sequence number each and
	 * label the HUs of one receive with different lots.
	 */
	public void updatePlanningHUAttributes(
			@NonNull final Collection<I_M_HU> hus,
			@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		final IAttributeStorageFactory attributeStorageFactory = attributeStorageFactoryService.createHUAttributeStorageFactory();
		final Supplier<String> lotNoFromSeq = Suppliers.memoize(() -> loadLotNoFromSeq(receiptSchedule))::get;

		for (final I_M_HU hu : hus)
		{
			final IAttributeStorage huAttributes = attributeStorageFactory.getAttributeStorage(hu);

			setAttributeLotNumber(hu, huAttributes, lotNoFromSeq);
			setAttributeBBD(receiptSchedule, huAttributes);
			setVendorValueFromReceiptSchedule(receiptSchedule, huAttributes);
		}
	}

	private void setAttributeLotNumber(
			@NonNull final I_M_HU hu,
			@NonNull final IAttributeStorage huAttributes,
			@NonNull final Supplier<String> lotNoFromSeq)
	{
		if (huAttributes.hasAttribute(AttributeConstants.ATTR_LotNumber)
				&& Check.isBlank(huAttributes.getValueAsString(AttributeConstants.ATTR_LotNumber))
				&& huAttributesBL.isAutomaticallySetLotNumber())
		{
			huAttributesBL.updateHUAttributeRecursive(HuId.ofRepoId(hu.getM_HU_ID()), HUAttributeUpdateRequest.builder()
					.attributeCode(AttributeConstants.ATTR_LotNumber)
					.attributeValue(hu.getValue())
					.build());
		}
		else
		{
			final String lotNumber = lotNoFromSeq.get();
			if (Check.isNotBlank(lotNumber))
			{
				huAttributesBL.updateHUAttributeRecursive(HuId.ofRepoId(hu.getM_HU_ID()), HUAttributeUpdateRequest.builder()
						.attributeCode(AttributeConstants.ATTR_LotNumber)
						.attributeValue(lotNumber)
						.build());
			}
		}
	}

	@Nullable
	private String loadLotNoFromSeq(@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		final I_C_DocType docType = docTypeDAO.getById(DocTypeId.ofRepoId(receiptSchedule.getC_DocType_ID()));
		final DocSequenceId lotNoSequenceId = DocSequenceId.ofRepoIdOrNull(docType.getLotNo_Sequence_ID());
		if (lotNoSequenceId == null)
		{
			return null;
		}

		final Optional<String> lotNumber = lotNumberBL.getAndIncrementLotNo(LotNoContext.builder()
				.sequenceId(lotNoSequenceId)
				.clientId(ClientId.ofRepoId(receiptSchedule.getAD_Client_ID()))
				.build());
		return lotNumber.orElse(null);
	}

	private void setAttributeBBD(
			@NonNull final I_M_ReceiptSchedule receiptSchedule,
			@NonNull final IAttributeStorage huAttributes)
	{
		if (huAttributes.hasAttribute(AttributeConstants.ATTR_BestBeforeDate)
				&& huAttributes.getValueAsLocalDate(AttributeConstants.ATTR_BestBeforeDate) == null
				&& huAttributesBL.isAutomaticallySetBestBeforeDate()
				&& receiptSchedule.getMovementDate() != null)
		{
			final LocalDate bestBeforeDate = computeBestBeforeDate(
					ProductId.ofRepoId(receiptSchedule.getM_Product_ID()),
					TimeUtil.asLocalDate(receiptSchedule.getMovementDate()));
			if (bestBeforeDate != null)
			{
				huAttributes.setValue(AttributeConstants.ATTR_BestBeforeDate, bestBeforeDate);
				huAttributes.saveChangesIfNeeded();
			}
		}
	}

	private void setVendorValueFromReceiptSchedule(
			@NonNull final I_M_ReceiptSchedule receiptSchedule,
			@NonNull final IAttributeStorage huAttributes)
	{
		if (huAttributes.hasAttribute(AttributeConstants.ATTR_Vendor_BPartner_ID)
				&& huAttributes.getValueAsInt(AttributeConstants.ATTR_Vendor_BPartner_ID) > -1)
		{
			final int bpId = receiptSchedule.getC_BPartner_ID();
			if (bpId > 0)
			{
				huAttributes.setValue(AttributeConstants.ATTR_Vendor_BPartner_ID, bpId);
				huAttributes.setSaveOnChange(true);
				huAttributes.saveChangesIfNeeded();
			}
		}
	}

	@Nullable
	private LocalDate computeBestBeforeDate(@NonNull final ProductId productId, @NonNull final LocalDate datePromised)
	{
		final int guaranteeDaysMin = productDAO.getProductGuaranteeDaysMinFallbackProductCategory(productId);
		if (guaranteeDaysMin <= 0)
		{
			return null;
		}
		return datePromised.plusDays(guaranteeDaysMin);
	}
}
