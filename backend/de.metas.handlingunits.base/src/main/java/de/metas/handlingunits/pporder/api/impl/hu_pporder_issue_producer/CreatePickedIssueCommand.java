package de.metas.handlingunits.pporder.api.impl.hu_pporder_issue_producer;

import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.pporder.api.CreateIssueCandidateRequest;
import de.metas.handlingunits.pporder.api.IHUPPOrderQtyDAO;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.logging.LogManager;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.eevolution.api.PPOrderId;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

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
 * Creates Picked Issue Candidate
 */
public class CreatePickedIssueCommand
{
	private static final String MSG_IssuingAggregatedTUsNotAllowed = "de.metas.handlingunits.pporder.api.impl.HUPPOrderIssueProducer.IssuingAggregatedTUsNotAllowed";
	private static final String MSG_IssuingVHUsNotAllowed = "de.metas.handlingunits.pporder.api.impl.HUPPOrderIssueProducer.IssuingVHUsNotAllowed";
	private static final String MSG_IssuingHUWithMultipleProductsNotAllowed = "de.metas.handlingunits.pporder.api.impl.HUPPOrderIssueProducer.IssuingHUsWithMultipleProductsNotAllowed";

	//
	// Services
	private static final Logger logger = LogManager.getLogger(CreatePickedIssueCommand.class);
	private final transient IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
	private final transient IHUPPOrderQtyDAO huPPOrderQtyDAO = Services.get(IHUPPOrderQtyDAO.class);
	private final transient IWarehouseDAO warehousesRepo = Services.get(IWarehouseDAO.class);

	//
	// Parameters
	private final ZonedDateTime movementDate;
	private final I_M_HU issueFromHU;
	private final PPOrderId orderId;

	//
	// Status
	@Nullable
	private Quantity remainingQtyToIssue;

	@Builder
	private CreatePickedIssueCommand(
			@Nullable final ZonedDateTime movementDate,
			@Nullable final Quantity fixedQtyToIssue,
			@NonNull final I_M_HU issueFromHU,
			@Nullable final PPOrderId orderId)
	{
		if (fixedQtyToIssue != null && fixedQtyToIssue.signum() <= 0)
		{
			throw new AdempiereException("fixedQtyToIssue shall be positive or not set at all");
		}

		this.movementDate = movementDate != null ? movementDate : SystemTime.asZonedDateTime();
		this.issueFromHU = issueFromHU;
		remainingQtyToIssue = fixedQtyToIssue;
		this.orderId = orderId;
	}

	public I_PP_Order_Qty execute()
	{
		return huTrxBL.process(this::executeInTrx);
	}

	private I_PP_Order_Qty executeInTrx(final IHUContext huContext)
	{
		final I_PP_Order_Qty candidate = createIssueCandidateOrNull(huContext, issueFromHU);

		if (remainingQtyToIssue != null && remainingQtyToIssue.signum() != 0)
		{
			throw new AdempiereException("Could not issue the whole quantity required. " + remainingQtyToIssue + " remained to be issued");
		}

		return candidate;
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

		final IHUProductStorage productStorage = retrieveProductStorage(huContext, hu);
		if (productStorage == null)
		{
			return null;
		}

		// Actually create and save the candidate
		final I_PP_Order_Qty candidate = createIssueCandidateOrNull(hu, productStorage);
		if (candidate == null)
		{
			return null;
		}
		return candidate;
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
			throw HUException.ofAD_Message(MSG_IssuingHUWithMultipleProductsNotAllowed)
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

			final Quantity qtyToIssue = calculateQtyToIssue(productStorage)
					.switchToSourceIfMorePrecise();
			if (qtyToIssue.isZero())
			{
				return null;
			}

			final I_PP_Order_Qty candidate = huPPOrderQtyDAO.save(CreateIssueCandidateRequest.builder()
																		  .orderId(orderId)
																		  //
																		  .date(movementDate)
																		  //
																		  .locatorId(warehousesRepo.getLocatorIdByRepoIdOrNull(hu.getM_Locator_ID()))
																		  .issueFromHUId(HuId.ofRepoId(hu.getM_HU_ID()))
																		  .productId(productId)
																		  //
																		  .qtyToIssue(qtyToIssue)
																		  //
																		  .build());

			return candidate;
		}
		catch (final RuntimeException rte)
		{
			throw AdempiereException.wrapIfNeeded(rte)
					.appendParametersToMessage()
					.setParameter("M_HU", hu);
		}
	}

	/**
	 * @return how much quantity to take "from" and issue it to given BOM line
	 */
	private Quantity calculateQtyToIssue(@NonNull final IHUProductStorage from)
	{
		if (remainingQtyToIssue != null)
		{
			final Quantity huStorageQty = from.getQty(remainingQtyToIssue.getUOM());
			final Quantity qtyToIssue = huStorageQty.min(remainingQtyToIssue);

			remainingQtyToIssue = remainingQtyToIssue.subtract(qtyToIssue);

			return qtyToIssue;
		}

		return from.getQty();
	}
}
