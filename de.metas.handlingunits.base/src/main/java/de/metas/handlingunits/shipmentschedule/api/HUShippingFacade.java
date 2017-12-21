package de.metas.handlingunits.shipmentschedule.api;

import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.processor.api.FailTrxItemExceptionHandler;
import org.adempiere.util.Check;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.IHUShipperTransportationBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.shipmentschedule.async.GenerateInOutFromHU.InvoiceMode;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateEnqueueResult;
import de.metas.invoicecandidate.api.impl.PlainInvoicingParams;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;

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
 * Facade which is able to generate shipping documents (generate shipment, add to shipper transportation, generate invoice).
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class HUShippingFacade
{
	private final IHUShipperTransportationBL huShipperTransportationBL = Services.get(IHUShipperTransportationBL.class);
	private final IHUShipmentScheduleDAO huShipmentScheduleDAO = Services.get(IHUShipmentScheduleDAO.class);
	private final IHUShipmentScheduleBL huShipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	//
	// Parameters
	private final ILoggable loggable;
	private final ImmutableList<I_M_HU> hus;
	private final int addToShipperTransportationId;
	private final boolean completeShipments;
	private final InvoiceMode invoiceMode;

	//
	// State
	private List<IShipmentScheduleWithHU> _candidates; // lazy
	private InOutGenerateResult shipmentsGenerateResult;

	@Builder
	private HUShippingFacade(
			@NonNull @Singular("hu") final List<I_M_HU> hus,
			final int addToShipperTransportationId,
			final boolean completeShipments,
			@Nullable final InvoiceMode invoiceMode,
			@Nullable final ILoggable loggable)
	{
		Check.assumeNotEmpty(hus, "hus is not empty");

		this.hus = ImmutableList.copyOf(hus);
		this.addToShipperTransportationId = addToShipperTransportationId;
		this.completeShipments = completeShipments;
		this.invoiceMode = invoiceMode;
		this.loggable = loggable != null ? loggable : Loggables.getNullLoggable();
	}

	public InOutGenerateResult generateShippingDocuments()
	{
		trxManager.runInThreadInheritedTrx(this::generateShippingDocuments0);
		return shipmentsGenerateResult;
	}

	private void generateShippingDocuments0()
	{
		//
		// Add HUs to shipper transportation if needed
		if (addToShipperTransportationId > 0)
		{
			huShipperTransportationBL.addHUsToShipperTransportation(addToShipperTransportationId, hus);
			loggable.addLog("HUs added to M_ShipperTransportation_ID={}", addToShipperTransportationId);
		}

		//
		// Generate shipments
		{
			final List<IShipmentScheduleWithHU> candidates = getCandidates();
			shipmentsGenerateResult = huShipmentScheduleBL
					.createInOutProducerFromShipmentSchedule()
					.setProcessShipments(completeShipments)
					.setCreatePackingLines(false) // the packing lines shall only be created when the shipments are completed
					.setManualPackingMaterial(false) // use the HUs!
					.computeShipmentDate(true) // if this is ever used, it should be on true to keep legacy
					// Fail on any exception, because we cannot create just a part of those shipments.
					// Think about HUs which are linked to multiple shipments: you will not see then in Aggregation POS because are already assigned, but u are not able to create shipment from them again.
					.setTrxItemExceptionHandler(FailTrxItemExceptionHandler.instance)
					.createShipments(candidates);
			loggable.addLog("Generated {}", shipmentsGenerateResult.toString());
		}

		//
		// Generate invoices
		generateInvoicesIfNeeded(shipmentsGenerateResult.getInOuts());
	}

	private void generateInvoicesIfNeeded(final List<I_M_InOut> shipments)
	{
		if (shipments.isEmpty())
		{
			return;
		}

		if (invoiceMode == InvoiceMode.None)
		{
			return;
		}

		final List<Integer> invoiceCandidateIds = invoiceCandDAO.retrieveInvoiceCandidatesQueryForInOuts(shipments).listIds();

		final PlainInvoicingParams invoicingParams = new PlainInvoicingParams();
		invoicingParams.setIgnoreInvoiceSchedule(InvoiceMode.AllWithoutInvoiceSchedule == invoiceMode);

		final IInvoiceCandidateEnqueueResult enqueueResult = invoiceCandBL.enqueueForInvoicing()
				.setLoggable(loggable)
				.setFailOnChanges(false)
				.setInvoicingParams(invoicingParams)
				//
				.enqueueInvoiceCandidateIds(invoiceCandidateIds);
		loggable.addLog("Invoice candidates enqueued: {}", enqueueResult);
	}

	@VisibleForTesting
	public List<IShipmentScheduleWithHU> getCandidates()
	{
		if(_candidates == null)
		{
			_candidates = huShipmentScheduleDAO.retrieveShipmentSchedulesWithHUsFromHUs(hus);
		}
		return _candidates;
	}

	@VisibleForTesting
	public List<I_M_InOut> getGeneratedShipments()
	{
		Check.assumeNotNull(shipmentsGenerateResult, "shipments were generated");
		return shipmentsGenerateResult.getInOuts();
	}
}
