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

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.common.util.time.SystemTime;
import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.deliveryplanning.ReceiptScheduleAndDeliveryPlanningId;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.document.sequence.DocSequenceId;
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
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
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
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * The ONE way a material receipt is created out of a receipt schedule in this branch - for a delivery planning
 * and for a bare receipt schedule alike.
 * <p>
 * It exists because there are two windows that receive the same goods: the delivery-planning window, whose
 * generate-receipt process has always carried the planning id, and the receipt-logistics window, whose grid
 * unions planned and unplanned rows and therefore has to do <b>both</b> from one action. Written twice, the
 * planning id is a thing one of the two copies forgets - which is exactly what the HU-editor path does today,
 * producing a receipt whose {@code M_Delivery_Planning_ID} is never set. Written once, here, the id is simply a
 * nullable field of {@link CreateReceiptFromReceiptScheduleRequest} and the caller cannot skip it by accident.
 * <p>
 * Why it lives in {@code de.metas.deliveryplanning.base}: it is the only module that both sees
 * {@link DeliveryPlanningId} (so the request can be typed rather than passing a bare repo id) and is visible
 * from every caller - the delivery-planning generate process, the receipt-logistics window's actions, and the
 * cucumber test classpath.
 */
@Service
public class ReceiptFromReceiptScheduleService
{
	private static final AdMessageKey MESSAGE_ClearanceStatusInfo_Receipt = AdMessageKey.of("ClearanceStatusInfo.Receipt");

	@NonNull private final DeliveryPlanningService deliveryPlanningService;

	public ReceiptFromReceiptScheduleService(@NonNull final DeliveryPlanningService deliveryPlanningService)
	{
		this.deliveryPlanningService = deliveryPlanningService;
	}

	private final IHUReceiptScheduleBL huReceiptScheduleBL = Services.get(IHUReceiptScheduleBL.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final ILotNumberBL lotNumberBL = Services.get(ILotNumberBL.class);
	private final IBPartnerOrgBL partnerOrgBL = Services.get(IBPartnerOrgBL.class);
	private final IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);

	/**
	 * The WHOLE of a "receive CUs" action, for either row type of the receipt-logistics grid: one planning VHU
	 * carrying {@code qtyToReceiveOverride} (or the schedule's own remaining quantity when none is given), the
	 * receipt booked against it, and - on a planned row - the planning's quantity rules applied.
	 * <p>
	 * It lives here rather than in the action so that the action is a thin adapter over behaviour that can be
	 * driven, and asserted, without a WebUI view: {@code de.metas.cucumber} deliberately excludes
	 * {@code de.metas.ui.web.base}, so a scenario proving AC10 - receiving a planned row leaves the same state
	 * as receiving that planning from the delivery-planning window - has to reach the behaviour at this level.
	 */
	public CreateReceiptFromReceiptScheduleResult receiveCUs(
			@NonNull final ReceiptScheduleAndDeliveryPlanningId sourceIds,
			@Nullable final BigDecimal qtyToReceiveOverride)
	{
		final I_M_ReceiptSchedule receiptSchedule = huReceiptScheduleBL.getById(sourceIds.getReceiptScheduleId());
		final Quantity qtyToReceive = qtyToReceiveOverride != null
				? Quantitys.of(qtyToReceiveOverride, UomId.ofRepoId(receiptSchedule.getC_UOM_ID()))
				: getDefaultQtyToReceive(receiptSchedule);

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

		applyPlanningQuantityRules(sourceIds.getDeliveryPlanningId(), qtyToReceive);

		return result;
	}

	/** The schedule's own remaining quantity to move - what "receive CUs" offers when the operator states nothing. */
	public Quantity getDefaultQtyToReceive(@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		final StockQtyAndUOMQty qtyToMove = receiptScheduleBL.getQtyToMove(receiptSchedule);
		final BigDecimal qty = qtyToMove == null || qtyToMove.signum() <= 0
				? BigDecimal.ZERO
				: qtyToMove.getStockQty().toBigDecimal();

		return Quantitys.of(qty, UomId.ofRepoId(receiptSchedule.getC_UOM_ID()));
	}

	/**
	 * On a PLANNED row only: the quantity received becomes the planning's planned discharge quantity, because a
	 * receipt occupies the DISCHARGE end. This is what {@code M_Delivery_Planning_GenerateReceipt} does after
	 * generating, and it is half of what AC10 means by "the same result" - the other half, the actual discharge
	 * quantity and the {@code Processed} flag, is derived by the completion interceptor from the planning id the
	 * request carried.
	 * <p>
	 * An unplanned row has no planning to write to; that is the whole of the {@code null} branch.
	 */
	public void applyPlanningQuantityRules(
			@Nullable final DeliveryPlanningId deliveryPlanningId,
			@NonNull final Quantity qtyReceived)
	{
		if (deliveryPlanningId != null)
		{
			deliveryPlanningService.setPlannedDischargeQuantity(deliveryPlanningId, qtyReceived);
		}
	}

	/**
	 * Creates and COMPLETES the receipt for the request's schedule, booking exactly the request's HUs.
	 * <p>
	 * {@link CreateReceiptFromReceiptScheduleRequest#getDeliveryPlanningId()} is handed to
	 * {@code CreateReceiptsParameters} rather than written onto the finished document, because this call
	 * completes the receipt before returning; see the request's javadoc for why the ordering is load-bearing.
	 */
	public CreateReceiptFromReceiptScheduleResult createReceipt(@NonNull final CreateReceiptFromReceiptScheduleRequest request)
	{
		final I_M_ReceiptSchedule receiptSchedule = huReceiptScheduleBL.getById(request.getReceiptScheduleId());

		final InOutGenerateResult result = huReceiptScheduleBL.processReceiptSchedules(
				IHUReceiptScheduleBL.CreateReceiptsParameters.builder()
						.commitEachReceiptIndividually(false)
						.movementDateRule(request.getMovementDateRule())
						.ctx(Env.getCtx())
						.destinationLocatorIdOrNull(null) // use receipt schedules' destination-warehouse settings
						.printReceiptLabels(true)
						.receiptSchedules(ImmutableList.of(receiptSchedule))
						.selectedHuIds(request.getHuIdsToReceive())
						.deliveryPlanningId(DeliveryPlanningId.toRepoId(request.getDeliveryPlanningId()))
						.build());

		final I_M_InOut receipt = result.getSingleInOut(I_M_InOut.class);

		return CreateReceiptFromReceiptScheduleResult.builder()
				.receiptId(InOutId.ofRepoId(receipt.getM_InOut_ID()))
				.receivedHuIds(request.getHuIdsToReceive())
				.productId(ProductId.ofRepoId(receiptSchedule.getM_Product_ID()))
				.build();
	}

	/**
	 * Creates a single planning VHU carrying {@code qtyToReceive} for the given receipt schedule - the "receive
	 * CUs" shape, i.e. bare units with no packing instruction.
	 * <p>
	 * Returns {@code null} when there is nothing to receive, so that a caller receiving a whole selection can
	 * skip an exhausted line instead of failing the batch.
	 */
	@Nullable
	public HuId createPlanningVHU(
			@NonNull final ReceiptScheduleId receiptScheduleId,
			@NonNull final Quantity qtyToReceive)
	{
		return createPlanningVHU(huReceiptScheduleBL.getById(receiptScheduleId), qtyToReceive);
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
		final IMutableHUContext huContextInitial = Services.get(IHUContextFactory.class).createMutableHUContextForProcessing(Env.getCtx(), clientAndOrgId);

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
	 * Copies the receipt schedule's Lot number, Best-Before-Date and Vendor attributes onto the freshly created
	 * planning HUs. Mirrors {@code ReceiptScheduleBasedProcess.updateAttributes} - which lives in
	 * {@code de.metas.ui.web.base} and is therefore not reachable as an API from here.
	 * <p>
	 * Takes the whole batch rather than one HU at a time because the lot number drawn from the doc-type
	 * sequence is drawn ONCE per receive and shared by every HU of it - the memo the process base keeps in an
	 * instance field, which a shared service cannot. Called per HU it would burn one sequence number each and
	 * label the HUs of one receive with different lots.
	 */
	public void updatePlanningHUAttributes(
			@NonNull final Collection<I_M_HU> hus,
			@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		final IAttributeStorageFactory attributeStorageFactory = Services.get(IAttributeStorageFactoryService.class).createHUAttributeStorageFactory();
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
