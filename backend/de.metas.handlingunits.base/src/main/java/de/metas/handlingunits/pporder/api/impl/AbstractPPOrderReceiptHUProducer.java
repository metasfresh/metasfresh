/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.handlingunits.pporder.api.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.document.sequence.DocSequenceId;
import de.metas.handlingunits.ClearanceStatus;
import de.metas.handlingunits.ClearanceStatusInfo;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.IHUCapacityBL;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.IHUProducerAllocationDestination;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.attribute.HUAttributeConstants;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.attribute.IPPOrderProductAttributeBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.hutransaction.IHUTransactionCandidate;
import de.metas.handlingunits.hutransaction.impl.HUTransactionCandidate;
import de.metas.handlingunits.impl.IDocumentLUTUConfigurationManager;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_PP_Cost_Collector;
import de.metas.handlingunits.model.I_PP_Order;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.handlingunits.pporder.api.CreateReceiptCandidateRequest;
import de.metas.handlingunits.pporder.api.CreateReceiptCandidateRequest.CreateReceiptCandidateRequestBuilder;
import de.metas.handlingunits.pporder.api.HUPPOrderIssueReceiptCandidatesProcessor;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.IHUPPOrderQtyBL;
import de.metas.handlingunits.pporder.api.IHUPPOrderQtyDAO;
import de.metas.handlingunits.pporder.api.IPPOrderReceiptHUProducer;
import de.metas.handlingunits.pporder.api.ReceiveTUsToLUResult;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Capacity;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.ILotNumberBL;
import org.adempiere.mm.attributes.api.LotNoContext;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.IClientOrgAware;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.api.PPCostCollectorId;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order_BOM;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/* package */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
abstract class AbstractPPOrderReceiptHUProducer implements IPPOrderReceiptHUProducer
{
	private final static AdMessageKey MESSAGE_ClearanceStatusInfo_Manufactured = AdMessageKey.of("ClearanceStatusInfo.Manufactured");

	// Services
	private final IHUPPOrderBL ppOrderBL = Services.get(IHUPPOrderBL.class);
	private final IHUPPOrderQtyDAO huPPOrderQtyDAO = Services.get(IHUPPOrderQtyDAO.class);
	private final IPPOrderProductAttributeBL ppOrderProductAttributeBL = Services.get(IPPOrderProductAttributeBL.class);
	private final IPPOrderBOMDAO ppOrderBOMDAO = Services.get(IPPOrderBOMDAO.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);
	private final IHUPIItemProductBL huPIItemProductBL = Services.get(IHUPIItemProductBL.class);
	private final IHUCapacityBL huCapacityBL = Services.get(IHUCapacityBL.class);
	private final ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final IBPartnerOrgBL partnerOrgBL = Services.get(IBPartnerOrgBL.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IHUPPOrderQtyBL ppOrderQtyBL = Services.get(IHUPPOrderQtyBL.class);
	private final ILotNumberBL lotNumberBL = Services.get(ILotNumberBL.class);

	// Parameters
	private final PPOrderId ppOrderId;
	private transient I_M_HU_LUTU_Configuration _lutuConfiguration; // lazy
	private ZonedDateTime _movementDate;
	private LocatorId locatorId;
	private PickingCandidateId pickingCandidateId;
	@Nullable
	private String lotNumber;

	@SuppressWarnings("OptionalAssignedToNull")
	private Optional<String> lotNumberFromSequence = null;
	@Nullable
	private LocalDate _bestBeforeDate;
	//
	private boolean processReceiptCandidates;

	private LUTUSpec receiveUsingLUTUSpec;

	private final LinkedHashSet<PPCostCollectorId> createdCostCollectorIds = new LinkedHashSet<>();

	//
	// Abstract methods
	// @formatter:off
	protected abstract ProductId getProductId();
	protected abstract Object getAllocationRequestReferencedModel();
	protected abstract IAllocationSource createAllocationSource();
	protected abstract IDocumentLUTUConfigurationManager createReceiptLUTUConfigurationManager();
	protected abstract void addAssignedHUs(final Collection<I_M_HU> hus);
	// @formatter:on

	public AbstractPPOrderReceiptHUProducer(@NonNull final PPOrderId ppOrderId)
	{
		this.ppOrderId = ppOrderId;
	}

	private PPOrderId getPpOrderId()
	{
		return ppOrderId;
	}

	@Override
	public final List<I_M_HU> createDraftReceiptCandidatesAndPlanningHUs()
	{
		final ReceiptCandidatesAndHUs result = trxManager.callInNewTrx(() -> {
			final I_M_HU_LUTU_Configuration lutuConfig = getCreateLUTUConfiguration();
			final Quantity qtyCUsTotal = lutuConfigurationFactory.calculateQtyCUsTotal(lutuConfig);
			if (qtyCUsTotal.isZero())
			{
				throw new AdempiereException("Zero quantity to receive");
			}
			else if (qtyCUsTotal.isInfinite())
			{
				throw new AdempiereException("Quantity to receive was not determined");
			}

			return createReceiptCandidatesAndHUs(qtyCUsTotal);
		});

		return result.getHus();
	}

	@Override
	public I_M_HU receiveVHU(@NonNull final Quantity qtyToReceive)
	{
		final List<I_M_HU> vhus = receiveHUs(qtyToReceive, StandardLUTUSpec.CUs).getHus();

		if (vhus.isEmpty())
		{
			throw new AdempiereException("No VHU was created for " + qtyToReceive);
		}
		else if (vhus.size() == 1)
		{
			return vhus.get(0);
		}
		else
		{
			throw new AdempiereException("More than one VHU was created for " + qtyToReceive + ": " + vhus);
		}
	}

	@Override
	public HuId receiveTUsToNewLU(
			@NonNull final Quantity qtyToReceive,
			@NonNull final HUPIItemProductId tuPIItemProductId,
			@NonNull HuPackingInstructionsItemId luPIItemId)
	{
		final ReceiptCandidatesAndHUs luResult = receiveHUs(qtyToReceive, StandardLUTUSpec.TUs_on_SingleLU(tuPIItemProductId, luPIItemId));
		final I_M_HU lu = luResult.getSingleHU();
		final HuId luId = HuId.ofRepoId(lu.getM_HU_ID());

		final ImmutableList<I_PP_Order_Qty> receiptCandidates = luResult.getReceiptCandidates();
		ppOrderQtyBL.setNewLUAndSave(receiptCandidates, luId);

		return luId;
	}

	@Override
	public ReceiveTUsToLUResult receiveTUsToExistingLU(
			@NonNull final Quantity qtyToReceive,
			@NonNull final HUPIItemProductId tuPIItemProductId,
			@NonNull final I_M_HU existingLU)
	{
		final ReceiptCandidatesAndHUs tusResult = receiveTUs(qtyToReceive, tuPIItemProductId);
		final ImmutableList<I_M_HU> tusOrVhus = tusResult.getHus();
		final HuId luId = HUTransformService.newInstance().tusToExistingLU(tusOrVhus, existingLU);

		return ReceiveTUsToLUResult.builder()
				.luId(luId)
				.tusOrVhus(tusOrVhus)
				.build();
	}

	private ReceiptCandidatesAndHUs receiveTUs(@NonNull final Quantity qtyToReceive, @NonNull final HUPIItemProductId tuPIItemProductId)
	{
		return receiveHUs(qtyToReceive, StandardLUTUSpec.TUs_or_CUs(tuPIItemProductId));
	}

	private ReceiptCandidatesAndHUs receiveHUs(@NonNull final Quantity qtyToReceive, @NonNull final LUTUSpec lutuSpec)
	{
		this.receiveUsingLUTUSpec = lutuSpec;
		return receiveHUs(qtyToReceive);
	}

	@Override
	public I_M_HU receiveSingleTU(@NonNull final Quantity qtyToReceive, final @NonNull HuPackingInstructionsId tuPackingInstructionsId)
	{
		final ReceiptCandidatesAndHUs result = receiveHUs(qtyToReceive, PreciseTUSpec.of(tuPackingInstructionsId, qtyToReceive));
		return result.getSingleHU();
	}

	@Override
	public List<I_M_HU> receiveIndividualPlanningCUs(@NonNull final Quantity qtyToReceive)
	{
		this.processReceiptCandidates = false;
		this.receiveUsingLUTUSpec = PreciseTUSpec.of(HuPackingInstructionsId.VIRTUAL,
				Quantity.of(BigDecimal.ONE, qtyToReceive.getUOM()));

		return trxManager.callInThreadInheritedTrx(() -> createReceiptCandidatesAndHUs(qtyToReceive).getHus());
	}

	private ReceiptCandidatesAndHUs receiveHUs(@NonNull final Quantity qtyToReceive)
	{
		this.processReceiptCandidates = true;
		return trxManager.callInThreadInheritedTrx(() -> createReceiptCandidatesAndHUs(qtyToReceive));
	}

	private ReceiptCandidatesAndHUs createReceiptCandidatesAndHUs(@NonNull final Quantity qtyToReceive)
	{
		//
		// Create HU Context
		trxManager.assertThreadInheritedTrxExists();
		final IMutableHUContext huContext = handlingUnitsBL.createMutableHUContext(Env.getCtx(), ITrx.TRXNAME_ThreadInherited);
		//
		final ReceiptCandidateRequestProducer ppOrderReceiptCandidateCollector = newReceiptCandidateRequestProducer();
		huContext.getTrxListeners().callAfterLoad(ppOrderReceiptCandidateCollector::collectAllocationResults);

		//
		// Create Allocation Source
		final IAllocationSource ppOrderAllocationSource = createAllocationSource();

		//
		// Create Allocation Destination
		final IHUProducerAllocationDestination huProducerDestination = createAllocationDestination()
				// Make sure we are generating the HUs in Planning (task 08077)
				.setHUStatus(X_M_HU.HUSTATUS_Planning);

		//
		// Create Allocation Request
		final IAllocationRequest allocationRequest = createAllocationRequest(huContext, qtyToReceive, ppOrderReceiptCandidateCollector.orgId);

		//
		// Execute transfer
		final HULoader loader = HULoader.of(ppOrderAllocationSource, huProducerDestination)
				.setAllowPartialUnloads(false)
				.setAllowPartialLoads(false);
		final IAllocationResult allocationResult = loader.load(allocationRequest);
		Check.assume(allocationResult.isCompleted(), "Result shall be completed: {}", allocationResult);

		//
		// Generate the HUs
		final ImmutableList<I_M_HU> planningHUs = huProducerDestination.getCreatedHUs();

		//
		// Update received HUs
		InterfaceWrapperHelper.setThreadInheritedTrxName(planningHUs); // just to be sure
		updateReceivedHUs(huContext, planningHUs, ppOrderReceiptCandidateCollector.coByProductOrderBOMLineId);

		//
		// Create receipt candidates
		final ImmutableList<I_PP_Order_Qty> receiptCandidates = createAndProcessReceiptCandidatesIfRequested(ppOrderReceiptCandidateCollector.getRequests());

		// Refresh the planning HUs if neeed.
		// e.g. if processed those  "planning" HUs, will no longer have HUStatus=P but HUStatus=A
		if (processReceiptCandidates)
		{
			InterfaceWrapperHelper.refreshAll(planningHUs);
		}

		//
		// Return created HUs
		return ReceiptCandidatesAndHUs.builder()
				.hus(planningHUs)
				.receiptCandidates(receiptCandidates)
				.build();
	}

	private ImmutableList<I_PP_Order_Qty> createAndProcessReceiptCandidatesIfRequested(final ImmutableList<CreateReceiptCandidateRequest> requests)
	{
		if (requests.isEmpty())
		{
			return ImmutableList.of();
		}

		final ImmutableList<I_PP_Order_Qty> receiptCandidates = huPPOrderQtyDAO.saveAll(requests);

		if (processReceiptCandidates && !receiptCandidates.isEmpty())
		{
			// Process the receipt candidates we just created
			// => HU will be activated, a receipt cost collector will be generated,
			final List<I_PP_Cost_Collector> costCollectors = HUPPOrderIssueReceiptCandidatesProcessor.newInstance()
					.setCandidatesToProcess(receiptCandidates)
					.process();

			costCollectors.stream()
					.map(costCollector -> PPCostCollectorId.ofRepoId(costCollector.getPP_Cost_Collector_ID()))
					.forEach(createdCostCollectorIds::add);
		}

		return receiptCandidates;
	}

	protected abstract ReceiptCandidateRequestProducer newReceiptCandidateRequestProducer();

	private void updateReceivedHUs(
			final IHUContext huContext,
			final Collection<I_M_HU> hus,
			@Nullable final PPOrderBOMLineId coByProductOrderBOMLineId)
	{
		if (hus.isEmpty())
		{
			return;
		}

		//
		// Modify the HU Attributes based on the attributes already existing from issuing (task 08177)
		ppOrderProductAttributeBL.updateHUAttributes(hus, getPpOrderId(), coByProductOrderBOMLineId);

		final IAttributeStorageFactory huAttributeStorageFactory = huContext.getHUAttributeStorageFactory();

		for (final I_M_HU hu : hus)
		{
			final IAttributeStorage huAttributes = huAttributeStorageFactory.getAttributeStorage(hu);
			huAttributes.setSaveOnChange(true);

			final HuId huId = HuId.ofRepoId(hu.getM_HU_ID());
			if (Check.isNotBlank(lotNumber))
			{
				huAttributes.setValue(AttributeConstants.ATTR_LotNumber, lotNumber);

			}
			else
			{
				final String lotNumber = getOrLoadLotNumberFromSeq();
				if (Check.isNotBlank(lotNumber)
						&& huAttributes.hasAttribute(AttributeConstants.ATTR_LotNumber))
				{
					huAttributesBL.updateHUAttributeRecursive(huId, AttributeConstants.ATTR_LotNumber, lotNumber, null);
				}
			}
			if (huAttributes.hasAttribute(AttributeConstants.ATTR_BestBeforeDate))
			{
				final LocalDate bestBeforeDate = getBestBeforeDate();
				if (bestBeforeDate != null)
				{
					huAttributes.setValue(AttributeConstants.ATTR_BestBeforeDate, bestBeforeDate);
				}
			}

			huAttributesBL.updateHUAttributeRecursive(
					huId,
					HUAttributeConstants.ATTR_PP_Order_ID,
					ppOrderId.getRepoId(),
					null);
		}

		//
		// Assign HUs to PP_Order/PP_Order_BOMLine
		addAssignedHUs(hus);
	}

	@Nullable
	private String getOrLoadLotNumberFromSeq()
	{
		//noinspection OptionalAssignedToNull
		if (lotNumberFromSequence == null)
		{
			final I_PP_Order_BOM ppOrderBom = ppOrderBOMDAO.getByOrderIdOrNull(ppOrderId);
			final DocSequenceId sequenceId = DocSequenceId.ofRepoIdOrNull(ppOrderBom.getLotNo_Sequence_ID());
			Optional<String> lotNumber = Optional.empty();
			if (sequenceId != null)
			{
				lotNumber = lotNumberBL.getAndIncrementLotNo(LotNoContext.builder()
						.sequenceId(sequenceId)
						.clientId(ClientId.ofRepoId(ppOrderBom.getAD_Client_ID()))
						.build());

			}
			this.lotNumberFromSequence = lotNumber;
		}
		return lotNumberFromSequence.orElse(null);
	}

	@Override
	public final IPPOrderReceiptHUProducer movementDate(@NonNull final ZonedDateTime movementDate)
	{
		_movementDate = movementDate;
		return this;
	}

	protected final ZonedDateTime getMovementDate()
	{
		if (_movementDate == null)
		{
			_movementDate = SystemTime.asZonedDateTime();
		}
		return _movementDate;
	}

	@Override
	public final IPPOrderReceiptHUProducer locatorId(@NonNull final LocatorId locatorId)
	{
		this.locatorId = locatorId;
		return this;
	}

	protected final LocatorId getLocatorId()
	{
		return locatorId;
	}

	private IAllocationRequest createAllocationRequest(final IHUContext huContext, final Quantity qtyToReceive, final OrgId orgId)
	{
		final ProductId productId = getProductId();
		final ZonedDateTime date = getMovementDate();
		final Object referencedModel = getAllocationRequestReferencedModel();

		final I_M_Product product = productDAO.getById(productId);
		final ClearanceStatus clearanceStatus = ClearanceStatus.ofNullableCode(product.getHUClearanceStatus());
		final ClearanceStatusInfo clearanceStatusInfo;
		if (clearanceStatus != null)
		{
			final String language = getOrgUserOrLoggedInUSerLanguage(referencedModel);
			clearanceStatusInfo = ClearanceStatusInfo.builder()
					.clearanceStatus(clearanceStatus)
					.clearanceNote(msgBL.getMsg(language, MESSAGE_ClearanceStatusInfo_Manufactured))
					.clearanceDate(InstantAndOrgId.ofInstant(date.toInstant(), OrgId.ofRepoId(product.getAD_Org_ID())))
					.build();

		}
		else
		{
			clearanceStatusInfo = null;
		}

		return AllocationUtils.createQtyRequest(huContext,
				productId, // product
				qtyToReceive, // the quantity to receive
				date, // transaction date
				referencedModel, // referenced model
				true, // forceQtyAllocation: make sure we will transfer the given qty, no matter what
				clearanceStatusInfo // clearance status
		);
	}

	private String getOrgUserOrLoggedInUSerLanguage(final Object referencedModel)
	{
		if (referencedModel instanceof IClientOrgAware)
		{
			final OrgId orgId = OrgId.ofRepoId(((IClientOrgAware)referencedModel).getAD_Org_ID());
			return partnerOrgBL.getOrgLanguageOrLoggedInUserLanguage(orgId);
		}
		return Env.getADLanguageOrBaseLanguage();
	}

	private IHUProducerAllocationDestination createAllocationDestination()
	{
		if (receiveUsingLUTUSpec != null)
		{
			if (receiveUsingLUTUSpec instanceof StandardLUTUSpec)
			{
				final StandardLUTUSpec standardLUTUSpec = (StandardLUTUSpec)receiveUsingLUTUSpec;
				if (standardLUTUSpec.isCUs())
				{
					return HUProducerDestination.ofVirtualPI()
							.setLocatorId(getLocatorId());
				}
				else
				{
					final I_M_HU_PI_Item_Product tuPIItemProduct = huPIItemProductBL.getRecordById(standardLUTUSpec.getTuPIItemProductId());
					final I_C_UOM uom = IHUPIItemProductBL.extractUOMOrNull(tuPIItemProduct);
					final Capacity tuCapacity = huCapacityBL.getCapacity(tuPIItemProduct, getProductId(), uom);

					final HuPackingInstructionsItemId tuPackingInstructionsItemId = HuPackingInstructionsItemId.ofRepoId(tuPIItemProduct.getM_HU_PI_Item_ID());

					final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
					lutuProducer.setLocatorId(getLocatorId());
					lutuProducer.setTUPI(handlingUnitsBL.getPI(tuPackingInstructionsItemId));
					lutuProducer.setIsHUPlanningReceiptOwnerPM(true);
					lutuProducer.addCUPerTU(tuCapacity);

					final HuPackingInstructionsItemId luPIItemId = standardLUTUSpec.getLuPIItemId();
					if (luPIItemId == null)
					{
						lutuProducer.setNoLU();
					}
					else
					{
						final I_M_HU_PI_Item luPIItem = handlingUnitsBL.getPackingInstructionItemById(luPIItemId);
						final I_M_HU_PI luPI = handlingUnitsBL.getPI(luPIItem);
						lutuProducer.setLUItemPI(luPIItem);
						lutuProducer.setLUPI(luPI);
						lutuProducer.setMaxTUsPerLU(standardLUTUSpec.getMaxTUsPerLU() != null ? standardLUTUSpec.getMaxTUsPerLU() : QtyTU.ofBigDecimal(luPIItem.getQty()));
						lutuProducer.setMaxLUs(standardLUTUSpec.getMaxLUs() > 0 ? standardLUTUSpec.getMaxLUs() : Integer.MAX_VALUE);
					}

					return lutuProducer;
				}
			}
			else if (receiveUsingLUTUSpec instanceof PreciseTUSpec)
			{
				final PreciseTUSpec preciseTUSpec = (PreciseTUSpec)receiveUsingLUTUSpec;

				final LUTUProducerDestination tuProducer = new LUTUProducerDestination();
				tuProducer.setLocatorId(getLocatorId());
				tuProducer.setTUPI(handlingUnitsBL.getPI(preciseTUSpec.getTuPackingInstructionsId()));
				tuProducer.setIsHUPlanningReceiptOwnerPM(true);
				tuProducer.addCUPerTU(Capacity.createCapacity(preciseTUSpec.getQtyCUsPerTU(), getProductId()));
				tuProducer.setNoLU();

				return tuProducer;
			}
			else
			{
				throw new AdempiereException("Unknown LU/TU spec: " + receiveUsingLUTUSpec);
			}
		}
		else
		{
			final I_M_HU_LUTU_Configuration lutuConfiguration = getCreateLUTUConfiguration();
			return lutuConfigurationFactory.createLUTUProducerAllocationDestination(lutuConfiguration);
		}
	}

	@Override
	public final IPPOrderReceiptHUProducer packUsingLUTUConfiguration(@NonNull final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		_lutuConfiguration = lutuConfiguration;
		return this;
	}

	private I_M_HU_LUTU_Configuration getCreateLUTUConfiguration()
	{
		if (_lutuConfiguration == null)
		{
			_lutuConfiguration = createReceiptLUTUConfigurationManager()
					.startEditing() // start editing just to make sure the LU/TU configuration is created if does not already exist
					.pushBackToModel()
					.getLUTUConfiguration();
		}
		return _lutuConfiguration;
	}

	@Override
	public IPPOrderReceiptHUProducer pickingCandidateId(final PickingCandidateId pickingCandidateId)
	{
		this.pickingCandidateId = pickingCandidateId;
		return this;
	}

	protected final PickingCandidateId getPickingCandidateId()
	{
		return pickingCandidateId;
	}

	@Override
	public IPPOrderReceiptHUProducer lotNumber(final String lotNumber)
	{
		this.lotNumber = StringUtils.trimBlankToNull(lotNumber);
		return this;
	}

	@Override
	public IPPOrderReceiptHUProducer bestBeforeDate(@Nullable final LocalDate bestBeforeDate)
	{
		this._bestBeforeDate = bestBeforeDate;
		return this;
	}

	@Nullable
	private LocalDate getBestBeforeDate()
	{
		if (_bestBeforeDate == null)
		{
			_bestBeforeDate = computeBestBeforeDate();
		}
		return _bestBeforeDate;
	}

	@Nullable
	LocalDate computeBestBeforeDate()
	{
		if (huAttributesBL.isAutomaticallySetBestBeforeDate())
		{
			final int guaranteeDaysMin = productDAO.getProductGuaranteeDaysMinFallbackProductCategory(getProductId());
			if (guaranteeDaysMin <= 0)
			{
				return null;
			}

			final I_PP_Order ppOrderPO = ppOrderBL.getById(getPpOrderId());
			final LocalDate datePromised = TimeUtil.asLocalDateNonNull(ppOrderPO.getDatePromised());

			return datePromised.plusDays(guaranteeDaysMin);
		}
		else
		{
			return null;
		}
	}

	@Override
	public ImmutableSet<PPCostCollectorId> getCreatedCostCollectorIds()
	{
		return ImmutableSet.copyOf(createdCostCollectorIds);
	}

	//
	//
	//
	//
	//

	/**
	 * Aggregates {@link HUTransactionCandidate}s and creates manufacturing receipt candidates requests.
	 */
	protected static final class ReceiptCandidateRequestProducer
	{
		private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		private final PPOrderId orderId;
		private final PPOrderBOMLineId coByProductOrderBOMLineId;
		private final OrgId orgId;
		private final ZonedDateTime date;
		private final LocatorId locatorId;
		private final PickingCandidateId pickingCandidateId;

		private final Map<AggregationKey, CreateReceiptCandidateRequestBuilder> requests = new HashMap<>();

		@Builder
		private ReceiptCandidateRequestProducer(
				@NonNull final PPOrderId orderId,
				@Nullable final PPOrderBOMLineId coByProductOrderBOMLineId,
				@NonNull final OrgId orgId,
				@NonNull final ZonedDateTime date,
				@Nullable final LocatorId locatorId,
				@Nullable final PickingCandidateId pickingCandidateId)
		{
			this.orderId = orderId;
			this.coByProductOrderBOMLineId = coByProductOrderBOMLineId;
			this.orgId = orgId;
			this.date = date;
			this.locatorId = locatorId;
			this.pickingCandidateId = pickingCandidateId;
		}

		public ImmutableList<CreateReceiptCandidateRequest> getRequests()
		{
			return requests.values()
					.stream()
					.map(CreateReceiptCandidateRequestBuilder::build)
					.collect(ImmutableList.toImmutableList());
		}

		public void collectAllocationResults(final IHUContext IGNORED, @NonNull final List<IAllocationResult> loadResults)
		{
			loadResults.stream()
					.flatMap(loadResult -> loadResult.getTransactions().stream())
					.forEach(this::collectHUTransactionCandidate);
		}

		private void collectHUTransactionCandidate(final IHUTransactionCandidate huTransaction)
		{
			final I_M_HU hu = huTransaction.getM_HU();
			if (hu == null)
			{
				return;
			}

			final Quantity quantity = huTransaction.getQuantity();
			if (quantity.isZero())
			{
				return;
			}

			final I_M_HU topLevelHU = handlingUnitsBL.getTopLevelParent(hu);

			final LocatorId effectiveLocatorId = CoalesceUtil.coalesceSuppliers(
					() -> locatorId,
					huTransaction::getLocatorId,
					() -> IHandlingUnitsBL.extractLocatorIdOrNull(topLevelHU));
			if (effectiveLocatorId == null)
			{
				throw new AdempiereException("Cannot figure out on which locator to receive.")
						.setParameter("providedLocatorId", locatorId)
						.setParameter("huTransaction", huTransaction)
						.setParameter("topLevelHU", topLevelHU)
						.appendParametersToMessage();
			}

			final AggregationKey key = AggregationKey.builder()
					.locatorId(effectiveLocatorId)
					.topLevelHUId(HuId.ofRepoId(topLevelHU.getM_HU_ID()))
					.productId(huTransaction.getProductId())
					.build();

			requests.computeIfAbsent(key, this::createReceiptCandidateRequestBuilder)
					.addQtyToReceive(quantity);
		}

		private CreateReceiptCandidateRequestBuilder createReceiptCandidateRequestBuilder(final AggregationKey key)
		{
			return CreateReceiptCandidateRequest.builder()
					.orderId(orderId)
					.orderBOMLineId(coByProductOrderBOMLineId)
					.orgId(orgId)
					.date(date)
					.locatorId(key.getLocatorId())
					.topLevelHUId(key.getTopLevelHUId())
					.productId(key.getProductId())
					.pickingCandidateId(pickingCandidateId);
		}

		@Builder
		@Value
		private static class AggregationKey
		{
			@NonNull
			LocatorId locatorId;
			@NonNull
			HuId topLevelHUId;
			@NonNull
			ProductId productId;
		}
	}

	//
	//
	//
	//
	//

	private interface LUTUSpec
	{
	}

	@Value
	@Builder
	private static class StandardLUTUSpec implements LUTUSpec
	{
		public static final StandardLUTUSpec CUs = StandardLUTUSpec.builder().tuPIItemProductId(HUPIItemProductId.VIRTUAL_HU).build();

		@Nullable HuPackingInstructionsItemId luPIItemId;
		@NonNull HUPIItemProductId tuPIItemProductId;
		@Nullable QtyTU maxTUsPerLU;
		int maxLUs;

		public static StandardLUTUSpec TUs_or_CUs(@NonNull HUPIItemProductId tuPIItemProductId)
		{
			if (tuPIItemProductId.isVirtualHU())
			{
				return CUs;
			}
			else
			{
				return StandardLUTUSpec.builder().tuPIItemProductId(tuPIItemProductId).build();
			}
		}

		public static StandardLUTUSpec TUs_on_SingleLU(@NonNull HUPIItemProductId tuPIItemProductId, @NonNull HuPackingInstructionsItemId luPIItemId)
		{
			return StandardLUTUSpec.builder()
					.luPIItemId(luPIItemId)
					.tuPIItemProductId(tuPIItemProductId)
					.maxTUsPerLU(QtyTU.ofInt(Integer.MAX_VALUE))
					.maxLUs(1)
					.build();
		}

		public boolean isCUs()
		{
			return tuPIItemProductId.isVirtualHU() && luPIItemId == null;
		}
	}

	@Value(staticConstructor = "of")
	private static class PreciseTUSpec implements LUTUSpec
	{
		@NonNull HuPackingInstructionsId tuPackingInstructionsId;
		@NonNull Quantity qtyCUsPerTU;
	}

	//
	//
	//
	//
	//

	@Value
	@Builder
	private static class ReceiptCandidatesAndHUs
	{
		@NonNull ImmutableList<I_M_HU> hus;
		@NonNull ImmutableList<I_PP_Order_Qty> receiptCandidates;

		public I_M_HU getSingleHU() {return CollectionUtils.singleElement(hus);}
	}

}
