/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.contracts.callorder.detail.document;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.callorder.CallOrderContractService;
import de.metas.contracts.callorder.CallOrderService;
import de.metas.contracts.callorder.detail.CallOrderDetailRepo;
import de.metas.contracts.callorder.detail.UpsertCallOrderDetailRequest;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.springframework.stereotype.Service;

@Service
public class DocumentChangeHandler_InOut implements DocumentChangeHandler<I_M_InOut>
{
	private final IInOutDAO shipmentDAO = Services.get(IInOutDAO.class);

	@NonNull
	private final CallOrderContractService callOrderContractService;
	@NonNull
	private final CallOrderService callOrderService;
	@NonNull
	private final CallOrderDetailRepo detailRepo;

	public DocumentChangeHandler_InOut(
			final @NonNull CallOrderContractService callOrderContractService,
			final @NonNull CallOrderService callOrderService,
			final @NonNull CallOrderDetailRepo detailRepo)
	{
		this.callOrderContractService = callOrderContractService;
		this.callOrderService = callOrderService;
		this.detailRepo = detailRepo;
	}

	@Override
	public void onComplete(final I_M_InOut shipment)
	{
		shipmentDAO.retrieveLines(shipment).forEach(this::syncInOutLine);
	}

	@Override
	public void onReverse(final I_M_InOut shipment)
	{
		shipmentDAO.retrieveLines(shipment).forEach(this::syncInOutLine);
	}

	@Override
	public void onReactivate(final I_M_InOut shipment)
	{
		detailRepo.resetDeliveredQtyForShipment(InOutId.ofRepoId(shipment.getM_InOut_ID()));
	}

	private void syncInOutLine(@NonNull final I_M_InOutLine inOutLine)
	{
		final FlatrateTermId flatrateTermId = FlatrateTermId.ofRepoIdOrNull(inOutLine.getC_Flatrate_Term_ID());

		if (flatrateTermId == null)
		{
			return;
		}

		if (!callOrderContractService.isCallOrderContract(flatrateTermId))
		{
			return;
		}

		callOrderContractService.validateCallOrderInOutLine(inOutLine, flatrateTermId);

		final UpsertCallOrderDetailRequest request = UpsertCallOrderDetailRequest.builder()
				.callOrderContractId(flatrateTermId)
				.shipmentLine(inOutLine)
				.build();

		callOrderService.handleCallOrderDetailUpsert(request);
	}
}
