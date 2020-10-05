package de.metas.inoutcandidate.spi.impl;

import de.metas.adempiere.docline.sort.api.IDocLineSortDAO;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUPackingMaterialsCollector;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.IHUContextProcessor;
import de.metas.handlingunits.allocation.IHUContextProcessorExecutor;
import de.metas.handlingunits.attribute.IHUTransactionAttributeBuilder;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferRequest;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferRequestBuilder;
import de.metas.handlingunits.attribute.strategy.impl.HUAttributeTransferRequestBuilder;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InOut;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.model.I_M_ReceiptSchedule_Alloc;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL.CreateReceiptsParameters;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleDAO;
import de.metas.handlingunits.snapshot.IHUSnapshotDAO;
import de.metas.handlingunits.snapshot.ISnapshotProducer;
import de.metas.handlingunits.spi.IHUPackingMaterialCollectorSource;
import de.metas.handlingunits.spi.impl.HUPackingMaterialDocumentLineCandidate;
import de.metas.handlingunits.spi.impl.HUPackingMaterialsCollector;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.handlingunits.util.HUTopLevel;
import de.metas.inout.api.IQualityNoteDAO;
import de.metas.inout.model.I_M_QualityNote;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UOMPrecision;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactoryService;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

/**
 * Generates material receipt from {@link I_M_ReceiptSchedule_Alloc} (with HUs).
 */
public class InOutProducerFromReceiptScheduleHU extends de.metas.inoutcandidate.api.impl.InOutProducer
{
	//
	// Services
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHUReceiptScheduleDAO huReceiptScheduleDAO = Services.get(IHUReceiptScheduleDAO.class);
	private final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
	private final IHUAssignmentBL huAssignmentBL = Services.get(IHUAssignmentBL.class);
	private final IHUInOutBL huInOutBL = Services.get(IHUInOutBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IUOMDAO uomdao = Services.get(IUOMDAO.class);

	//
	// Params
	private final IHUContext _huContext;

	/**
	 * When generating receipt lines pick only those allocation lines which have HUs from this Set.
	 * <p>
	 * If the set is null, it means no restriction will be applied, so any HU will be considered.
	 */
	private final Set<HuId> selectedHUIds;

	/**
	 * Collects packing materials in order to generate Packing Material receipt lines
	 */
	private final HUPackingMaterialsCollector packingMaterialsCollector;

	private final ISnapshotProducer<I_M_HU> huSnapshotProducer;

	public InOutProducerFromReceiptScheduleHU(
			final CreateReceiptsParameters parameters,
			final InOutGenerateResult result)
	{
		super(result,
				true, // complete=true
				parameters.getMovementDateRule(),
				parameters.getExternalInfoByReceiptScheduleId());

		this.selectedHUIds = parameters.getSelectedHuIds();
		Check.assume(selectedHUIds == null || !selectedHUIds.isEmpty(), "selectedHUIds shall be null or not empty: {}", selectedHUIds);

		// the HU-context shall use the tread-inherited trx because it is executed by ITrxItemProcessorExecutorService and instantiated before the executor-services internal trxName is known.
		_huContext = handlingUnitsBL.createMutableHUContext(trxManager.createThreadContextAware(parameters.getCtx()));

		packingMaterialsCollector = new HUPackingMaterialsCollector(_huContext);

		huSnapshotProducer = Services.get(IHUSnapshotDAO.class)
				.createSnapshot()
				.setContext(_huContext);
	}

	/**
	 * @return context; never returns null
	 */
	private IHUContext getHUContext()
	{
		return _huContext;
	}

	@Override
	protected List<I_M_InOutLine> createCurrentReceiptLines()
	{
		final IHUContext huContext = getHUContext();
		final I_M_ReceiptSchedule rs = InterfaceWrapperHelper.create(getCurrentReceiptSchedule(), I_M_ReceiptSchedule.class);

		//
		//
		final HUReceiptLineCandidatesBuilder receiptLineCandidatesBuilder = new HUReceiptLineCandidatesBuilder(rs);
		receiptLineCandidatesBuilder.setHUContext(huContext);
		final List<I_M_ReceiptSchedule_Alloc> allocsAll = huReceiptScheduleDAO.retrieveHandlingUnitAllocations(rs, huContext.getTrxName());
		for (final I_M_ReceiptSchedule_Alloc alloc : allocsAll)
		{
			if (!isRsaEligible(alloc))
			{
				continue;
			}

			//
			// Case: it's just a LU assignment => just unassign it
			final I_M_HU hu = alloc.getM_TU_HU();
			if (!isHUEligible(hu))
			{
				unassignHU(alloc);
				continue;
			}

			receiptLineCandidatesBuilder.add(alloc);
		}

		//
		// If total Qty to receive is ZERO, skip this receipt schedule
		if (receiptLineCandidatesBuilder.getQtyAndQuality().isZero())
		{
			return Collections.emptyList();
		}

		//
		// Create receipt lines from each receipt line candidate
		final List<I_M_InOutLine> receiptLines = new ArrayList<>();
		for (final HUReceiptLineCandidate receiptLineCandidate : receiptLineCandidatesBuilder.getHUReceiptLineCandidates())
		{
			receiptLines.addAll(createReceiptLines(receiptLineCandidate));
		}

		//
		// Return created receipt lines
		return receiptLines;
	}

	private List<I_M_InOutLine> createReceiptLines(@NonNull final HUReceiptLineCandidate receiptLineCandidate)
	{
		final IHUContext huContext = getHUContext();
		final I_M_ReceiptSchedule rs = receiptLineCandidate.getM_ReceiptSchedule();
		final ReceiptQty qtyAndQuality = receiptLineCandidate.getQtyAndQuality();

		// The receipt lines created.
		// Could be maximum 2: one with QtyWithoutIssues, one with QtyWithIssues
		final List<I_M_InOutLine> receiptLines = new ArrayList<>(2);

		//
		// Create receipt line for QtyWithoutIssues
		final StockQtyAndUOMQty qtyWithoutIssues = qtyAndQuality.getQtyWithoutIssues();
		if (qtyWithoutIssues.signum() > 0)
		{
			final Percent qualityDiscountPercent = Percent.ZERO;
			final String qualityNoticesString = "";
			final boolean isInDispute = false;
			final I_M_InOutLine receiptLineWithoutIssues = createReceiptLine(receiptLineCandidate,
					qtyWithoutIssues,
					qualityDiscountPercent,
					qualityNoticesString,
					isInDispute);

			//
			// Assign handling units to receipt line
			transferHandlingUnits(huContext, rs, receiptLineCandidate.getReceiptScheduleAllocs(), receiptLineWithoutIssues);

			receiptLines.add(receiptLineWithoutIssues);
		}

		//
		// If there is Qty with issues, an extra receipt line is needed (with qtyWithIssues)
		final StockQtyAndUOMQty qtyWithIssues = qtyAndQuality.getQtyWithIssuesExact();
		if (qtyWithIssues.signum() > 0)
		{
			final Percent qualityDiscountPercent = qtyAndQuality.getQualityDiscountPercent();
			final String qualityNoticesString = qtyAndQuality.getQualityNotices().asQualityNoticesString();
			final boolean isInDispute = true;
			final I_M_InOutLine receiptLineWithIssues = createReceiptLine(receiptLineCandidate,
					qtyWithIssues,
					qualityDiscountPercent,
					qualityNoticesString,
					isInDispute);

			// make sure the M_AttributeSetInstance of the new line also contains the QualityNotice attribute
			addQualityToASI(receiptLineWithIssues, receiptLineCandidate);

			InterfaceWrapperHelper.save(receiptLineWithIssues);
			receiptLines.add(receiptLineWithIssues);
		}

		return receiptLines;
	}

	/**
	 * Add the value of M_QualityNote from the receiptLineCandidate to the M_AttributeSetInstance of the receipt line
	 */
	private void addQualityToASI(
			final I_M_InOutLine receiptLineWithIssues,
			final HUReceiptLineCandidate receiptLineCandidate)
	{
		final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

		final IAttributeSetInstanceAwareFactoryService attributeSetInstanceAwareFactoryService = Services.get(IAttributeSetInstanceAwareFactoryService.class);
		final IAttributeSetInstanceAware asiAware = attributeSetInstanceAwareFactoryService.createOrNull(receiptLineWithIssues);

		final I_M_AttributeSetInstance asi = Services.get(IAttributeSetInstanceBL.class).getCreateASI(asiAware);

		final Properties ctx = InterfaceWrapperHelper.getCtx(receiptLineWithIssues);
		final String trxName = InterfaceWrapperHelper.getTrxName(receiptLineWithIssues);

		final AttributeId qualityNoteAttributeId = Services.get(IQualityNoteDAO.class).getQualityNoteAttributeId();
		if (qualityNoteAttributeId == null)
		{
			// nothing to do
			return;
		}

		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoId(asi.getM_AttributeSetInstance_ID());
		I_M_AttributeInstance ai = attributeDAO.retrieveAttributeInstance(asiId, qualityNoteAttributeId);

		if (ai == null)
		{
			// throw new AdempiereException("No attribute instance was found."
			// + "\n ASI=" + asi
			// + "\n Attribute=" + huTrxAttribute.getM_Attribute());

			ai = attributeDAO.createNewAttributeInstance(ctx, asi, qualityNoteAttributeId, trxName);
		}

		final I_M_QualityNote qualityNote = receiptLineCandidate.get_qualityNote();

		// provide the quality note in the ASI if it was set
		if (qualityNote != null)
		{
			ai.setValue(qualityNote.getValue());

			// save the attribute instance
			InterfaceWrapperHelper.save(ai);
		}

		// set the asi to the receipt line
		receiptLineWithIssues.setM_AttributeSetInstance(asi);

	}

	/**
	 * Creates additional receipt lines for packing materials.
	 */
	@Override
	protected final List<I_M_InOutLine> createBottomReceiptLines()
	{
		createHUSnapshots();

		final List<I_M_InOutLine> receiptLines_PackingMaterials = createBottomReceiptLines_PackingMaterials();
		return receiptLines_PackingMaterials;
	}

	private void createHUSnapshots()
	{
		// Create the snapshots for all enqueued HUs so far.
		huSnapshotProducer.createSnapshots();

		// Set the Snapshot_UUID to current receipt (for later recall and reporting).
		final I_M_InOut receipt = getCurrentReceipt(I_M_InOut.class);
		receipt.setSnapshot_UUID(huSnapshotProducer.getSnapshotId());
		InterfaceWrapperHelper.save(receipt);
	}

	/**
	 * Creates additional receipt lines for packing materials.
	 *
	 * @return additional packing material receipt lines that were created.
	 */
	private List<I_M_InOutLine> createBottomReceiptLines_PackingMaterials()
	{
		//
		// Sets M_Product_ID sort comparator to use
		final I_M_InOut receipt = getCurrentReceipt(I_M_InOut.class);
		final Comparator<Integer> candidatesSortComparator = Services.get(IDocLineSortDAO.class).findDocLineSort()
				.setContext(getCtx())
				.setC_BPartner_ID(receipt.getC_BPartner_ID())
				.setC_DocType(loadOutOfTrx(receipt.getC_DocType_ID(), I_C_DocType.class))
				.findProductIdsComparator();
		packingMaterialsCollector.setProductIdSortComparator(candidatesSortComparator);

		//
		// Retrieve packing material receipt line candidates
		final List<HUPackingMaterialDocumentLineCandidate> candidates = packingMaterialsCollector.getAndClearCandidates();
		if (candidates.isEmpty())
		{
			return Collections.emptyList();
		}

		//
		// Create one receipt line for each candidate
		final List<I_M_InOutLine> receiptLines = new ArrayList<>();
		for (final HUPackingMaterialDocumentLineCandidate candidate : candidates)
		{
			final I_M_InOutLine packagingReceiptLine = createPackingMaterialReceiptLine(candidate);
			if (packagingReceiptLine == null)
			{
				continue;
			}

			// task 09502: set the reference from line to packing-line
			final Set<IHUPackingMaterialCollectorSource> sourceIols = candidate.getSources();
			sourceIols.stream()
					.filter(source -> source instanceof InOutLineHUPackingMaterialCollectorSource)
					.map(source -> ((InOutLineHUPackingMaterialCollectorSource)source).getM_InOutLine())
					.forEach(sourceReceiptLine -> {
						sourceReceiptLine.setM_PackingMaterial_InOutLine(packagingReceiptLine);
						InterfaceWrapperHelper.save(sourceReceiptLine);
					});

			receiptLines.add(packagingReceiptLine);
			// 07734 note: we don't need to explicitly link the new receipt line here. It will be done by MaterialTrackableDocumentByASIInterceptor (its M_InOut subclass).
			// having set the M_Material_Tracking_ID in IHUInOutBL.updatePackingMaterialInOutLine(receiptLine, candidate); was enough
		}

		//
		// Return created packing material receipt lines
		return receiptLines;
	}

	/**
	 * Create receipt line for given packing material receipt line candidate.
	 * <p>
	 * This method will also search for packing material receipt schedules and will try to allocate receipt line to them.
	 *
	 * @return created receipt line
	 */
	private I_M_InOutLine createPackingMaterialReceiptLine(final HUPackingMaterialDocumentLineCandidate candidate)
	{
		//
		// Create packing material receipt line
		final I_M_InOutLine receiptLine = InterfaceWrapperHelper.create(newReceiptLine(), I_M_InOutLine.class);
		huInOutBL.updatePackingMaterialInOutLine(receiptLine, candidate);
		receiptLine.setC_OrderLine(null); // make sure packing material order lines does not link to an order (07969)
		InterfaceWrapperHelper.save(receiptLine);

		//
		// Create Receipt schedule allocations if possible.
		// If no packing material receipt schedules were found for this product, there is nothing we can do
		final ProductId productId = candidate.getProductId();
		final List<I_M_ReceiptSchedule> packingMaterialReceiptSchedules = huReceiptScheduleDAO.retrievePackingMaterialReceiptSchedules(getCtx(), getCurrentHeaderAggregationKey(), productId.getRepoId());
		if (!packingMaterialReceiptSchedules.isEmpty())
		{
			receiptScheduleBL.createReceiptScheduleAllocations(packingMaterialReceiptSchedules, receiptLine);
		}

		//
		// Make sure C_OrderLine_ID is not set to packing materials receipt line (07969)
		receiptLine.setC_OrderLine(null);
		Check.assume(receiptLine.getC_OrderLine_ID() <= 0, "Receipt line shall not have an order line set"); // NOTE: these was an old problem in the past, so better safe then sorry (07969)
		InterfaceWrapperHelper.save(receiptLine); // make sure it's saved

		return receiptLine;
	}

	private I_M_InOutLine createReceiptLine(
			final HUReceiptLineCandidate receiptLineCandidate,
			@NonNull final StockQtyAndUOMQty qtyToReceive,
			@NonNull final Percent qualityDiscountPercent,
			final String qualityNote,
			final boolean isInDispute)
	{
		final I_M_ReceiptSchedule rs = receiptLineCandidate.getM_ReceiptSchedule();
		//
		// Create receipt line
		final I_M_InOutLine receiptLine = InterfaceWrapperHelper.create(newReceiptLine(), I_M_InOutLine.class);
		updateReceiptLine(receiptLine, rs);

		if (receiptLineCandidate.getSubProducer_BPartner_ID() > 0)
		{
			receiptLine.setSubProducer_BPartner_ID(receiptLineCandidate.getSubProducer_BPartner_ID());
		}

		final Optional<Quantity> catchQty = qtyToReceive.getUOMQtyOpt();

		final boolean hasUsableCatchQty = catchQty.isPresent() && !catchQty.get().isZero() && !catchQty.get().isInfinite();
		if (hasUsableCatchQty)
		{
			final UOMPrecision catchQtyRecision = uomdao.getStandardPrecision(catchQty.get().getUomId());
			final Quantity roundedCatchQty = catchQty.get().setScale(catchQtyRecision, RoundingMode.HALF_UP);

			receiptLine.setCatch_UOM_ID(roundedCatchQty.getUomId().getRepoId());
			receiptLine.setQtyDeliveredCatch(roundedCatchQty.toBigDecimal());
		}

		final UomId uomId = UomId.ofRepoId(receiptLineCandidate.getC_UOM().getC_UOM_ID());
		final Quantity qtyEntered = uomConversionBL.convertQuantityTo(qtyToReceive.getStockQty(), UOMConversionContext.of(qtyToReceive.getProductId()), uomId);
		receiptLine.setQtyEntered(qtyEntered.toBigDecimal());
		receiptLine.setC_UOM_ID(uomId.getRepoId());
		receiptLine.setMovementQty(qtyToReceive.getStockQty().toBigDecimal());

		receiptLine.setQualityDiscountPercent(qualityDiscountPercent.toBigDecimal());
		receiptLine.setQualityNote(qualityNote);
		receiptLine.setIsInDispute(isInDispute);

		//
		// Save and return
		InterfaceWrapperHelper.save(receiptLine);
		return receiptLine;
	}

	private boolean isRsaEligible(final I_M_ReceiptSchedule_Alloc rsa)
	{
		if (!rsa.isActive())
		{
			return false;
		}

		// Already received allocations are not eligible
		if (rsa.getM_InOutLine_ID() > 0)
		{
			return false;
		}

		if (!isInSelectedHUs(rsa))
		{
			return false;
		}

		return true;
	}

	private boolean isHUEligible(final I_M_HU hu)
	{
		final boolean huExistsAndSaved = hu != null && hu.getM_HU_ID() > 0;
		if (!huExistsAndSaved)
		{
			return false;
		}

		final boolean huDestroyed = handlingUnitsBL.isDestroyedRefreshFirst(hu);
		if (huDestroyed)
		{
			return false;
		}
		return true;
	}

	private boolean isInSelectedHUs(final I_M_ReceiptSchedule_Alloc rsa)
	{
		//
		// We don't have LU or TU on this allocation line => don't accept it because it's not Handling Units related
		final HuId tuHUId = HuId.ofRepoIdOrNull(rsa.getM_TU_HU_ID());
		final HuId luHUId = HuId.ofRepoIdOrNull(rsa.getM_LU_HU_ID());
		if (tuHUId == null && luHUId == null)
		{
			return false;
		}

		//
		// Accept all HUs if we don't have restrictions set
		if (selectedHUIds == null)
		{
			return true;
		}

		//
		// Check if TU is in our scope
		if (tuHUId != null && selectedHUIds.contains(tuHUId))
		{
			return true;
		}

		//
		// Check if LU is in our scope
		if (luHUId != null && selectedHUIds.contains(luHUId))
		{
			return true;
		}

		//
		// TU nor LU is not in our scope => don't accept it
		return false;
	}

	/**
	 * Transfer handling units from <code>allocs</code> to <code>receiptLine</code>.
	 * <p>
	 * Also collect the packing materials.
	 *
	 * @param rs
	 * @param allocs
	 * @param receiptLine
	 */
	private void transferHandlingUnits(
			final IHUContext huContext,
			final I_M_ReceiptSchedule rs,
			final List<I_M_ReceiptSchedule_Alloc> allocs,
			final I_M_InOutLine receiptLine)
	{
		//
		// Gebinde Lager packing materials collector; will be used when the HU's owner is "us"
		final IHUPackingMaterialsCollector<?> destroyedHUPackingMaterialsCollector = huContext.getHUPackingMaterialsCollector();

		//
		// We only collect top level HUs for transfer.
		// HUStatus, BPartner and locator will be transfered to children HUs via model validator.
		final Set<HUTopLevel> husToAssign = new TreeSet<>(); // NOTE: we are using HUTopLevel.compareTo() for unicity
		final Map<Integer, I_M_HU> husToUnassign = new HashMap<>();
		for (final I_M_ReceiptSchedule_Alloc rsa : allocs)
		{
			final int tuHUId = rsa.getM_TU_HU_ID();
			Check.assume(tuHUId > 0, "tuHUId > 0 (rsa={})", rsa);
			final I_M_HU tuHU = rsa.getM_TU_HU();

			//
			// Collect top level HUs from rsa line
			final I_M_HU topLevelHU;

			final int luHUId = rsa.getM_LU_HU_ID();
			if (luHUId > 0)
			{
				final I_M_HU luHU = rsa.getM_LU_HU();
				topLevelHU = luHU;
			}
			else
			{
				// Case of TUs without LUs. Add them instead.
				topLevelHU = tuHU;
			}

			husToUnassign.put(topLevelHU.getM_HU_ID(), topLevelHU);

			//
			// Collect packing materials
			final IHUPackingMaterialCollectorSource receiptLineSource = InOutLineHUPackingMaterialCollectorSource.builder()
					.inoutLine(receiptLine)
					.collectHUPipToSource(false)
					.build();

			//
			// 08162: Only collect them if the owner is not us. Otherwise, take them from the Gebinde Lager
			if (!tuHU.isHUPlanningReceiptOwnerPM())
			{
				packingMaterialsCollector.releasePackingMaterialForTU(tuHU, receiptLineSource);
			}
			else
			{
				destroyedHUPackingMaterialsCollector.requirePackingMaterialForTU(tuHU);
			}

			if (luHUId > 0)
			{
				final I_M_HU luHU = rsa.getM_LU_HU();
				if (!luHU.isHUPlanningReceiptOwnerPM())
				{
					packingMaterialsCollector.releasePackingMaterialForLU(luHU, receiptLineSource);
				}
				else
				{
					destroyedHUPackingMaterialsCollector.requirePackingMaterialForLU(luHU);
				}
			}

			//
			// Unassign HU (i.e. deactivate current receipt schedule allocation)
			unassignHU(rsa);

			// Collect HUs to assign
			{
				final I_M_HU vhu = null; // don't set the VHU because we want to have unique husToAssign per LU/TU
				husToAssign.add(new HUTopLevel(topLevelHU, rsa.getM_LU_HU(), rsa.getM_TU_HU(), vhu));
			}
		}

		//
		// 08162: Process all TU/LU retrievals from Gebinde Lager in a single movement
		fetchPackingMaterialsFromGebindeLager(huContext);

		//
		// Update receipt line's QtyEnteredTU
		{
			// NOTE: we are reseting the CountTUs because we don't want these TUs to be counted to next receipt line (we already set them here)
			final int countTUs = packingMaterialsCollector.getAndResetCountTUs();
			final BigDecimal countTUsBD = BigDecimal.valueOf(countTUs);
			receiptLine.setQtyEnteredTU(countTUsBD);
			receiptLine.setQtyTU_Calculated(countTUsBD);
			InterfaceWrapperHelper.save(receiptLine);
		}

		//
		// Un-assign collected HUs from Receipt Schedule
		{
			final String trxName = InterfaceWrapperHelper.getTrxName(receiptLine);
			huAssignmentBL.unassignHUs(rs, husToUnassign.values(), trxName);
		}

		//
		// Assign collected HUs to new receipt line
		// NOTE: we will do attribute transfer only for these.
		final Set<Integer> topLevelHUIdsAssigned = new HashSet<>();
		for (final HUTopLevel huToAssign : husToAssign)
		{
			final I_M_HU topLevelHU = huToAssign.getM_HU_TopLevel();

			//
			// Create top-level assignment if none exists
			final int huId = topLevelHU.getM_HU_ID();
			if (topLevelHUIdsAssigned.add(huId))
			{
				transferHandlingUnit(huContext, rs, topLevelHU, receiptLine);
			}

			final I_M_HU luHU = huToAssign.getM_LU_HU();
			final I_M_HU tuHU = huToAssign.getM_TU_HU();
			huAssignmentBL
					.createTradingUnitDerivedAssignmentBuilder(huContext.getCtx(), receiptLine, topLevelHU, luHU, tuHU, huContext.getTrxName())
					.build();
		}
	}

	/**
	 * Process in an {@link IHUContextProcessorExecutor} retrieval of packing materials from Gebinde Lager. Will process them all at once in a single movement
	 */
	private void fetchPackingMaterialsFromGebindeLager(final IHUContext huContext)
	{
		huTrxBL.createHUContextProcessorExecutor(huContext)
				.run((IHUContextProcessor)huContext1 -> IHUContextProcessor.NULL_RESULT);
	}

	/**
	 * Assign <code>hu</code> to receipt line. Also transfer the attributes from HU to receipt line's ASI.
	 *
	 * @param hu top level HU (LU, TU, VHU)
	 */
	private void transferHandlingUnit(final IHUContext huContext,
			final I_M_ReceiptSchedule rs,
			final I_M_HU hu,
			final I_M_InOutLine receiptLine)
	{
		//
		// Assign it to Receipt Line
		assignHU(receiptLine, hu);

		//
		// Transfer attributes from HU to receipt line's ASI
		final IHUContextProcessorExecutor executor = huTrxBL.createHUContextProcessorExecutor(huContext);
		executor.run((IHUContextProcessor)huContext1 -> {
			final IHUTransactionAttributeBuilder trxAttributesBuilder = executor.getTrxAttributesBuilder();
			final IAttributeStorageFactory attributeStorageFactory = trxAttributesBuilder.getAttributeStorageFactory();
			final IAttributeStorage huAttributeStorageFrom = attributeStorageFactory.getAttributeStorage(hu);
			final IAttributeStorage receiptLineAttributeStorageTo = attributeStorageFactory.getAttributeStorage(receiptLine);

			final IHUStorageFactory storageFactory = huContext1.getHUStorageFactory();
			final IHUStorage huStorageFrom = storageFactory.getStorage(hu);

			final IHUAttributeTransferRequestBuilder requestBuilder = new HUAttributeTransferRequestBuilder(huContext1)
					.setProductId(ProductId.ofRepoId(rs.getM_Product_ID()))
					.setQty(receiptScheduleBL.getQtyMoved(rs))
					.setUOM(loadOutOfTrx(rs.getC_UOM_ID(), I_C_UOM.class))
					.setAttributeStorageFrom(huAttributeStorageFrom)
					.setAttributeStorageTo(receiptLineAttributeStorageTo)
					.setHUStorageFrom(huStorageFrom);

			final IHUAttributeTransferRequest request = requestBuilder.create();
			trxAttributesBuilder.transferAttributes(request);

			return IHUContextProcessor.NULL_RESULT;
		});

		//
		// Create HU snapshots
		huSnapshotProducer.addModel(hu);
	}

	private void unassignHU(final I_M_ReceiptSchedule_Alloc rsa)
	{
		//
		// De-activate this allocation because we moved the HU to receipt line
		// TODO: provide proper implementation. See http://dewiki908/mediawiki/index.php/06103_Create_proper_HU_transactions_when_creating_Receipt_from_Schedules_%28102206835942%29
		rsa.setIsActive(false);
		InterfaceWrapperHelper.save(rsa);
	}

	private void assignHU(final I_M_InOutLine receiptLine, final I_M_HU hu)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(receiptLine);
		huAssignmentBL.assignHU(receiptLine, hu, trxName);
	}
}
