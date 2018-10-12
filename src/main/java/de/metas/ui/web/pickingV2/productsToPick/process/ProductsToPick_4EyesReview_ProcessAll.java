package de.metas.ui.web.pickingV2.productsToPick.process;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.shipmentschedule.api.HUShippingFacade;
import de.metas.handlingunits.shipmentschedule.async.GenerateInOutFromHU.BillAssociatedInvoiceCandidates;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.ui.web.pickingV2.productsToPick.ProductsToPickRow;
import de.metas.util.Loggables;
import de.metas.util.Services;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class ProductsToPick_4EyesReview_ProcessAll extends ProductsToPickViewBasedProcess
{
	private final IHandlingUnitsDAO handlingUnitsRepo = Services.get(IHandlingUnitsDAO.class);
	@Autowired
	private PickingCandidateRepository pickingCandidatesRepo;
	@Autowired
	private PickingCandidateService pickingCandidatesService;

	@Param(parameterName = I_M_ShipperTransportation.COLUMNNAME_M_ShipperTransportation_ID, mandatory = true)
	private int shipperTransportationId;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!getView().isApproved())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not all rows were approved");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		processAllPickingCandidates();
		deliverAndInvoice();
		return MSG_OK;
	}

	private void processAllPickingCandidates()
	{
		pickingCandidatesService.process(getPickingCandidates());
	}

	private void deliverAndInvoice()
	{
		final Set<HuId> huIdsToDeliver = getPickingCandidates()
				.stream()
				.map(PickingCandidate::getPackedToHuId)
				.collect(ImmutableSet.toImmutableSet());

		final List<I_M_HU> husToDeliver = handlingUnitsRepo.getByIds(huIdsToDeliver);

		HUShippingFacade.builder()
				.loggable(Loggables.get())
				.hus(husToDeliver)
				.addToShipperTransportationId(shipperTransportationId)
				.completeShipments(true)
				.failIfNoShipmentCandidatesFound(true)
				.invoiceMode(BillAssociatedInvoiceCandidates.IF_INVOICE_SCHEDULE_PERMITS)
				.createShipperDeliveryOrders(true)
				.build()
				.generateShippingDocuments();
	}

	private List<PickingCandidate> getPickingCandidates()
	{
		final Set<PickingCandidateId> pickingCandidateIds = getAllRows().stream().map(ProductsToPickRow::getPickingCandidateId).collect(ImmutableSet.toImmutableSet());
		return pickingCandidatesRepo.getByIds(pickingCandidateIds);
	}
}
