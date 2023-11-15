package de.metas.handlingunits.pporder.api.impl.hu_pporder_issue_producer;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.IPPOrderProductAttributeBL;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.pporder.api.CreateIssueCandidateRequest;
import de.metas.handlingunits.pporder.api.IHUPPOrderQtyBL;
import de.metas.handlingunits.pporder.api.IHUPPOrderQtyDAO;
import de.metas.handlingunits.pporder.api.IssueCandidateGeneratedBy;
import de.metas.handlingunits.sourcehu.SourceHUsService;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.i18n.AdMessageKey;
import de.metas.logging.LogManager;
import de.metas.material.planning.pporder.DraftPPOrderQuantities;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.eevolution.api.BOMComponentIssueMethod;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * Creates Draft Issue Candidates
 */
public class CreateDraftIssuesCommand
{
	private static final AdMessageKey MSG_IssuingAggregatedTUsNotAllowed = AdMessageKey.of("de.metas.handlingunits.pporder.api.impl.HUPPOrderIssueProducer.IssuingAggregatedTUsNotAllowed");
	private static final AdMessageKey MSG_IssuingVHUsNotAllowed = AdMessageKey.of("de.metas.handlingunits.pporder.api.impl.HUPPOrderIssueProducer.IssuingVHUsNotAllowed");
	private static final AdMessageKey MSG_IssuingHUWithMultipleProductsNotAllowed = AdMessageKey.of("de.metas.handlingunits.pporder.api.impl.HUPPOrderIssueProducer.IssuingHUsWithMultipleProductsNotAllowed");
	private static final AdMessageKey MSG_IssuingNotClearedHUsNotAllowed = AdMessageKey.of("de.metas.handlingunits.pporder.IssuingNotClearedHUsNotAllowed");

	//
	// Services
	private static final Logger logger = LogManager.getLogger(CreateDraftIssuesCommand.class);
	private final transient IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);
	private final transient IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
	private final transient IPPOrderProductAttributeBL ppOrderProductAttributeBL = Services.get(IPPOrderProductAttributeBL.class);
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final transient IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final transient IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);
	private final transient IHUPPOrderQtyDAO huPPOrderQtyDAO = Services.get(IHUPPOrderQtyDAO.class);
	private final transient IHUPPOrderQtyBL huPPOrderQtyBL = Services.get(IHUPPOrderQtyBL.class);
	private final transient IWarehouseDAO warehousesRepo = Services.get(IWarehouseDAO.class);

	private final transient SourceHUsService sourceHuService = SourceHUsService.get();
	
	//
	// Parameters
	private final ImmutableList<I_PP_Order_BOMLine> targetOrderBOMLines;
	private final ZonedDateTime movementDate;
	private final boolean considerIssueMethodForQtyToIssueCalculation;
	private final ImmutableList<I_M_HU> issueFromHUs;
	private final boolean changeHUStatusToIssued;
	private final IssueCandidateGeneratedBy generatedBy;

	//
	// Status
	@Nullable
	private Quantity remainingQtyToIssue;

	@Builder
	private CreateDraftIssuesCommand(
			@NonNull final List<I_PP_Order_BOMLine> targetOrderBOMLines,
			@Nullable final ZonedDateTime movementDate,
			@Nullable final Quantity fixedQtyToIssue,
			final boolean considerIssueMethodForQtyToIssueCalculation,
			@NonNull final Collection<I_M_HU> issueFromHUs,
			@Nullable final Boolean changeHUStatusToIssued,
			//
			@Nullable final IssueCandidateGeneratedBy generatedBy)
	{
		validateSourceHUs(issueFromHUs);

		Check.assumeNotEmpty(targetOrderBOMLines, "Parameter targetOrderBOMLines is not empty");
		if (fixedQtyToIssue != null && fixedQtyToIssue.signum() <= 0)
		{
			throw new AdempiereException("fixedQtyToIssue shall be positive or not set at all");
		}

		this.targetOrderBOMLines = ImmutableList.copyOf(targetOrderBOMLines);
		this.movementDate = movementDate != null ? movementDate : SystemTime.asZonedDateTime();
		this.considerIssueMethodForQtyToIssueCalculation = considerIssueMethodForQtyToIssueCalculation;
		this.issueFromHUs = ImmutableList.copyOf(issueFromHUs);
		this.changeHUStatusToIssued = changeHUStatusToIssued != null ? changeHUStatusToIssued : true;

		remainingQtyToIssue = fixedQtyToIssue;

		this.generatedBy = generatedBy;
	}

	public List<I_PP_Order_Qty> execute()
	{
		if (issueFromHUs.isEmpty())
		{
			return ImmutableList.of();
		}

		// NOTE: we would prefer to always run this out of transactions,
		// but in some cases like issuing from Swing POS we cannot enforce it because in that case
		// the candidates are created and processed in one uber-transaction
		// trxManager.assertThreadInheritedTrxNotExists();

		return huTrxBL.process(this::executeInTrx);
	}

	private List<I_PP_Order_Qty> executeInTrx(final IHUContext huContext)
	{
		final ImmutableList<I_PP_Order_Qty> candidates = issueFromHUs.stream()
				.map(hu -> createIssueCandidateOrNull(huContext, hu))
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());

		if (remainingQtyToIssue != null && remainingQtyToIssue.signum() != 0)
		{
			throw new AdempiereException("Could not issue the whole quantity required. " + remainingQtyToIssue + " remained to be issued");
		}

		return candidates;
	}

	@Nullable
	private I_PP_Order_Qty createIssueCandidateOrNull(
			@NonNull final IHUContext huContext,
			@NonNull final I_M_HU hu)
	{
		// Stop if we had to issue a fixed quantity and we already issued it
		if (remainingQtyToIssue != null && remainingQtyToIssue.signum() <= 0)
		{
			return null;
		}

		final String huStatus = hu.getHUStatus();
		if (!X_M_HU.HUSTATUS_Active.equals(huStatus) && !X_M_HU.HUSTATUS_Issued.equals(huStatus)
		)
		{
			throw new HUException("HU shall be Active or Issued but it was `" + huStatus + "`")
					.setParameter("hu", hu);
		}

		final IHUProductStorage productStorage = retrieveProductStorage(huContext, hu);
		if (productStorage == null)
		{
			return null;
		}

		removeHuFromParentIfAny(huContext, hu);
		// Actually create and save the candidate
		final I_PP_Order_Qty candidate = createIssueCandidateOrNull(hu, productStorage);
		if (candidate == null)
		{
			return null;
		}

		// update the HU's status so that it's not moved somewhere else etc
		if (changeHUStatusToIssued)
		{
			huStatusBL.setHUStatus(huContext, hu, X_M_HU.HUSTATUS_Issued);
			handlingUnitsDAO.saveHU(hu);
		}

		return candidate;
	}

	/**
	 * If not a top level HU, take it out first
	 */
	private void removeHuFromParentIfAny(
			@NonNull final IHUContext huContext,
			@NonNull final I_M_HU hu)
	{
		if (handlingUnitsBL.isTopLevel(hu))
		{
			return; // nothing to do
		}

		if (handlingUnitsBL.isAggregateHU(hu))
		{
			throw new HUException(MSG_IssuingAggregatedTUsNotAllowed)
					.markAsUserValidationError();
		}
		if (handlingUnitsBL.isVirtual(hu))
		{
			throw new HUException(MSG_IssuingVHUsNotAllowed)
					.markAsUserValidationError();
		}
		else
		{
			huTrxBL.extractHUFromParentIfNeeded(huContext, hu);
		}
	}

	@Nullable
	private IHUProductStorage retrieveProductStorage(
			@NonNull final IHUContext huContext,
			@NonNull final I_M_HU hu)
	{
		final List<IHUProductStorage> productStorages = huContext.getHUStorageFactory()
				.getStorage(hu)
				.getProductStorages();

		// Empty HU
		if (productStorages.isEmpty())
		{
			logger.warn("{}: Skip {} from issuing because its storage is empty", this, hu);
			return null; // no candidate
		}

		if (productStorages.size() != 1)
		{
			throw new HUException(MSG_IssuingHUWithMultipleProductsNotAllowed)
					.markAsUserValidationError()
					.setParameter("HU", hu)
					.setParameter("ProductStorages", productStorages);
		}

		return productStorages.get(0);
	}

	@Nullable
	private I_PP_Order_Qty createIssueCandidateOrNull(
			@NonNull final I_M_HU hu,
			@NonNull final IHUProductStorage productStorage)
	{
		try
		{
			final ProductId productId = productStorage.getProductId();
			final I_PP_Order_BOMLine targetBOMLine = getTargetOrderBOMLine(productId);

			final Quantity qtyToIssue = calculateQtyToIssue(targetBOMLine, productStorage)
					.switchToSourceIfMorePrecise();
			if (qtyToIssue.isZero())
			{
				return null;
			}

			final I_PP_Order_Qty candidate = huPPOrderQtyDAO.save(CreateIssueCandidateRequest.builder()
					.orderId(PPOrderId.ofRepoId(targetBOMLine.getPP_Order_ID()))
					.orderBOMLineId(PPOrderBOMLineId.ofRepoId(targetBOMLine.getPP_Order_BOMLine_ID()))
					//
					.date(movementDate)
					//
					.locatorId(warehousesRepo.getLocatorIdByRepoIdOrNull(hu.getM_Locator_ID()))
					.issueFromHUId(HuId.ofRepoId(hu.getM_HU_ID()))
					.productId(productId)
					//
					.qtyToIssue(qtyToIssue)
					//
					.generatedBy(generatedBy)
					//
					.build());

			ppOrderProductAttributeBL.addPPOrderProductAttributesFromIssueCandidate(candidate);

			// Clean up source-HUs.
			// If we don't do this, addSourceHuMarker will fail when we call ReverseDraftIssues.reverseDraftIssue
			sourceHuService.deleteSourceHuMarker(HuId.ofRepoId(hu.getM_HU_ID()));

			return candidate;
		}
		catch (final RuntimeException rte)
		{
			throw AdempiereException.wrapIfNeeded(rte)
					.appendParametersToMessage()
					.setParameter("M_HU", hu);
		}
	}

	private I_PP_Order_BOMLine getTargetOrderBOMLine(@NonNull final ProductId productId)
	{
		final List<I_PP_Order_BOMLine> targetBOMLines = targetOrderBOMLines;

		//
		// Find the BOM line which is strictly matching our product
		final I_PP_Order_BOMLine targetBOMLine = targetBOMLines
				.stream()
				.filter(bomLine -> bomLine.getM_Product_ID() == productId.getRepoId())
				.findFirst()
				.orElse(null);
		if (targetBOMLine != null)
		{
			return targetBOMLine;
		}

		//
		// Find a BOM line which accepts any product
		return targetBOMLines
				.stream()
				.filter(I_PP_Order_BOMLine::isAllowIssuingAnyProduct)
				.findFirst()
				.orElseThrow(() -> new HUException("No BOM line found for productId=" + productId + " in " + targetBOMLines));
	}

	/**
	 * @return how much quantity to take "from" and issue it to given BOM line
	 */
	private Quantity calculateQtyToIssue(final I_PP_Order_BOMLine targetBOMLine, final IHUProductStorage from)
	{
		//
		// Case: enforced qty to issue
		if (remainingQtyToIssue != null)
		{
			final Quantity huStorageQty = from.getQty(remainingQtyToIssue.getUOM());
			final Quantity qtyToIssue = huStorageQty.min(remainingQtyToIssue);

			remainingQtyToIssue = remainingQtyToIssue.subtract(qtyToIssue);

			return qtyToIssue;
		}

		if (considerIssueMethodForQtyToIssueCalculation)
		{
			//
			// Case: if this is an Issue BOM Line, IssueMethod is Backflush and we did not over-issue on it yet
			// => enforce the capacity to Projected Qty Required (i.e. standard Qty that needs to be issued on this line).
			// initial concept: http://dewiki908/mediawiki/index.php/07433_Folie_Zuteilung_Produktion_Fertigstellung_POS_%28102170996938%29
			// additional (use of projected qty required): http://dewiki908/mediawiki/index.php/07601_Calculation_of_Folie_in_Action_Receipt_%28102017845369%29
			final BOMComponentIssueMethod issueMethod = BOMComponentIssueMethod.ofNullableCode(targetBOMLine.getIssueMethod());
			if (BOMComponentIssueMethod.IssueOnlyForReceived.equals(issueMethod))
			{
				final PPOrderId ppOrderId = PPOrderId.ofRepoId(targetBOMLine.getPP_Order_ID());
				final DraftPPOrderQuantities draftQtys = huPPOrderQtyBL.getDraftPPOrderQuantities(ppOrderId);
				return ppOrderBOMBL.computeQtyToIssueBasedOnFinishedGoodReceipt(targetBOMLine, from.getC_UOM(), draftQtys);
			}
		}

		return from.getQty();
	}

	private void validateSourceHUs(@NonNull final Collection<I_M_HU> sourceHUs)
	{
		for (final I_M_HU hu : sourceHUs)
		{
			if (!handlingUnitsBL.isHUHierarchyCleared(HuId.ofRepoId(hu.getM_HU_ID())))
			{
				throw new HUException(MSG_IssuingNotClearedHUsNotAllowed)
						.markAsUserValidationError()
						.setParameter("M_HU_ID", hu.getM_HU_ID());
			}
		}
	}
}
