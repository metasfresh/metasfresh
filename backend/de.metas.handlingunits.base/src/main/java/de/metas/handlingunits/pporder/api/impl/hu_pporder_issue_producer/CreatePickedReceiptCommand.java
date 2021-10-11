package de.metas.handlingunits.pporder.api.impl.hu_pporder_issue_producer;

import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.pporder.api.CreateReceiptCandidateRequest;
import de.metas.handlingunits.pporder.api.IHUPPOrderQtyDAO;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
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
 * Creates Picked Receipt Candidate
 */
public class CreatePickedReceiptCommand
{
	//
	// Services
	private static final Logger logger = LogManager.getLogger(CreatePickedReceiptCommand.class);
	public final IHandlingUnitsDAO huDAO = Services.get(IHandlingUnitsDAO.class);
	private final transient IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
	private final transient IHUPPOrderQtyDAO huPPOrderQtyDAO = Services.get(IHUPPOrderQtyDAO.class);
	private final transient IWarehouseDAO warehousesRepo = Services.get(IWarehouseDAO.class);

	//
	// Parameters
	private final ZonedDateTime movementDate;
	private final HuId receiveFromHUId;
	private final PPOrderId orderId;

	//
	// Status
	@Nullable
	private Quantity qtyPicked;

	@Builder
	private CreatePickedReceiptCommand(
			@Nullable final ZonedDateTime movementDate,
			@Nullable final Quantity qtyToReceive,
			@NonNull final HuId receiveFromHUId,
			@Nullable final PPOrderId orderId)
	{
		if (qtyToReceive != null && qtyToReceive.signum() <= 0)
		{
			throw new AdempiereException("qtyToReceive shall be positive or not set at all");
		}

		this.movementDate = movementDate != null ? movementDate : SystemTime.asZonedDateTime();
		this.receiveFromHUId = receiveFromHUId;
		qtyPicked = qtyToReceive;
		this.orderId = orderId;
	}

	public I_PP_Order_Qty execute()
	{
		return huTrxBL.process(this::executeInTrx);
	}

	private I_PP_Order_Qty executeInTrx(final IHUContext huContext)
	{
		final I_M_HU hu = huDAO.getById(receiveFromHUId);
		final I_PP_Order_Qty candidate = createReceiptCandidateOrNull(huContext, hu);

		if (qtyPicked != null && qtyPicked.signum() != 0)
		{
			throw new AdempiereException("Could not receipt the whole quantity required. " + qtyPicked );
		}

		return candidate;
	}

	@Nullable
	private I_PP_Order_Qty createReceiptCandidateOrNull(
			@NonNull final IHUContext huContext,
			@NonNull final I_M_HU hu)
	{
		final IHUProductStorage productStorage = retrieveProductStorage(huContext, hu);
		if (productStorage == null)
		{
			return null;
		}

		// Actually create and save the candidate
		final I_PP_Order_Qty candidate = createReceiptCandidateOrNull(hu, productStorage);
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

		return productStorages.get(0);
	}

	@Nullable
	private I_PP_Order_Qty createReceiptCandidateOrNull(
			@NonNull final I_M_HU hu,
			@NonNull final IHUProductStorage productStorage)
	{
		try
		{
			final ProductId productId = productStorage.getProductId();

			final Quantity qtyPicked = substractQtyPicked(productStorage)
					.switchToSourceIfMorePrecise();
			if (qtyPicked.isZero())
			{
				return null;
			}

			final I_PP_Order_Qty candidate = huPPOrderQtyDAO.save(CreateReceiptCandidateRequest.builder()
																		  .orderId(orderId)
																		  .orgId(OrgId.ofRepoId(hu.getAD_Org_ID()))
																		  //
																		  .date(movementDate)
																		  //
																		  .locatorId(warehousesRepo.getLocatorIdByRepoIdOrNull(hu.getM_Locator_ID()))
																		  .topLevelHUId(HuId.ofRepoId(hu.getM_HU_ID()))
																		  .productId(productId)
																		  //
																		  .qtyToReceive(qtyPicked)
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
	 * @return how much quantity to take "from"
	 */
	private Quantity substractQtyPicked(@NonNull final IHUProductStorage from)
	{
		if (qtyPicked != null)
		{
			final Quantity huStorageQty = from.getQty(qtyPicked.getUOM());
			final Quantity qtyToPick = huStorageQty.min(qtyPicked);

			qtyPicked = qtyPicked.subtract(qtyToPick);

			return qtyToPick;
		}

		return from.getQty();
	}
}
