package de.metas.handlingunits.shipmentschedule.api;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.processor.api.FailTrxItemExceptionHandler;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_M_Package;
import org.compiere.model.I_M_Shipper;
import org.compiere.util.TimeUtil;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.IHUShipperTransportationBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.shipmentschedule.async.GenerateInOutFromHU.BillAssociatedInvoiceCandidates;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateEnqueueResult;
import de.metas.invoicecandidate.api.impl.PlainInvoicingParams;
import de.metas.shipper.gateway.commons.ShipperGatewayFacade;
import de.metas.shipper.gateway.spi.model.DeliveryOrderCreateRequest;
import de.metas.shipping.model.I_M_ShipperTransportation;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;

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
@ToString(exclude = { "huShipperTransportationBL", "huShipmentScheduleDAO", "huShipmentScheduleBL", "invoiceCandDAO", "invoiceCandBL", "trxManager" })
public class HUShippingFacade
{
	private final IHUShipperTransportationBL huShipperTransportationBL = Services.get(IHUShipperTransportationBL.class);
	private final IHUShipmentScheduleDAO huShipmentScheduleDAO = Services.get(IHUShipmentScheduleDAO.class);
	private final IHUShipmentScheduleBL huShipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final Supplier<ShipperGatewayFacade> shipperGatewayFacadeSupplier = //
			() -> Adempiere.getBean(ShipperGatewayFacade.class);

	//
	// Parameters
	private final int addToShipperTransportationId;
	private final boolean completeShipments;
	private final BillAssociatedInvoiceCandidates invoiceMode;
	private final boolean createShipperDeliveryOrders;
	private LocalDate _shipperDeliveryOrderPickupDate = null; // lazy, will be fetched from Shipper Transportation
	private final ImmutableList<I_M_HU> hus;
	private final ILoggable loggable;

	//
	// State
	private List<ShipmentScheduleWithHU> _candidates; // lazy
	private InOutGenerateResult shipmentsGenerateResult;
	private final ArrayList<I_M_Package> mpackagesCreated = new ArrayList<>();

	@Builder
	private HUShippingFacade(
			@NonNull @Singular("hu") final List<I_M_HU> hus,
			final int addToShipperTransportationId,
			final boolean completeShipments,
			@Nullable final BillAssociatedInvoiceCandidates invoiceMode,
			final boolean createShipperDeliveryOrders,
			@Nullable final ILoggable loggable)
	{
		Check.assumeNotEmpty(hus, "hus is not empty");

		this.hus = ImmutableList.copyOf(hus);
		this.addToShipperTransportationId = addToShipperTransportationId;
		this.completeShipments = completeShipments;
		this.invoiceMode = invoiceMode;
		this.createShipperDeliveryOrders = createShipperDeliveryOrders;
		this.loggable = loggable != null ? loggable : Loggables.getNullLoggable();
	}

	@VisibleForTesting
	public List<ShipmentScheduleWithHU> getCandidates()
	{
		if (_candidates == null)
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

	public InOutGenerateResult generateShippingDocuments()
	{
		trxManager.runInThreadInheritedTrx(this::generateShippingDocuments0);
		return shipmentsGenerateResult;
	}

	private void generateShippingDocuments0()
	{
		addHUsToShipperTransportationIfNeeded();
		generateShipments();
		generateInvoicesIfNeeded();
		generateShipperDeliveryOrdersIfNeeded();
	}

	private void addHUsToShipperTransportationIfNeeded()
	{
		if (addToShipperTransportationId > 0)
		{
			final List<I_M_Package> result = huShipperTransportationBL.addHUsToShipperTransportation(addToShipperTransportationId, hus);
			mpackagesCreated.addAll(result);
			loggable.addLog("HUs added to M_ShipperTransportation_ID={}", addToShipperTransportationId);
		}
	}

	private void generateShipments()
	{
		final List<ShipmentScheduleWithHU> candidates = getCandidates();
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
		loggable.addLog("Generated {}", shipmentsGenerateResult);

	}

	private void generateInvoicesIfNeeded()
	{
		Check.assumeNotNull(shipmentsGenerateResult, "shipments generated");

		final List<I_M_InOut> shipments = shipmentsGenerateResult.getInOuts();
		if (shipments.isEmpty())
		{
			return;
		}

		if (invoiceMode == BillAssociatedInvoiceCandidates.NO)
		{
			return;
		}

		final List<Integer> invoiceCandidateIds = invoiceCandDAO.retrieveInvoiceCandidatesQueryForInOuts(shipments).listIds();

		final PlainInvoicingParams invoicingParams = new PlainInvoicingParams();

		final boolean adhereToInvoiceSchedule = invoiceMode == BillAssociatedInvoiceCandidates.IF_INVOICE_SCHEDULE_PERMITS;
		invoicingParams.setIgnoreInvoiceSchedule(!adhereToInvoiceSchedule);

		final IInvoiceCandidateEnqueueResult enqueueResult = invoiceCandBL.enqueueForInvoicing()
				.setLoggable(loggable)
				.setFailOnChanges(false)
				.setInvoicingParams(invoicingParams)
				//
				.enqueueInvoiceCandidateIds(invoiceCandidateIds);
		loggable.addLog("Invoice candidates enqueued: {}", enqueueResult);
	}

	private void generateShipperDeliveryOrdersIfNeeded()
	{
		if (!createShipperDeliveryOrders)
		{
			return;
		}
		Check.errorIf(addToShipperTransportationId <= 0, "If createShipperDeliveryOrders=true, then addToShipperTransportationId needs to be > 0; this={}", this);

		mpackagesCreated
				.stream()
				.collect(GuavaCollectors.toImmutableListMultimap(I_M_Package::getM_Shipper_ID))
				.asMap()
				.forEach(this::generateShipperDeliveryOrderIfNeeded);
	}

	private void generateShipperDeliveryOrderIfNeeded(
			final int shipperId,
			@NonNull final Collection<I_M_Package> mpackages)
	{
		final I_M_Shipper shipper = Check.assumeNotNull(
				loadOutOfTrx(shipperId, I_M_Shipper.class),
				"An M_Shipper record for shipperId={} exists",
				shipperId);

		final String shipperGatewayId = shipper.getShipperGateway();
		if (Check.isEmpty(shipperGatewayId, true))
		{
			return;
		}

		final ShipperGatewayFacade shipperGatewayFacade = shipperGatewayFacadeSupplier.get();
		if (!shipperGatewayFacade.hasServiceSupport(shipperGatewayId))
		{
			return;
		}

		final Set<Integer> mpackageIds = mpackages.stream()
				.map(I_M_Package::getM_Package_ID).collect(ImmutableSet.toImmutableSet());

		final DeliveryOrderCreateRequest request = DeliveryOrderCreateRequest.builder()
				.pickupDate(getShipperDeliveryOrderPickupDate())
				.packageIds(mpackageIds)
				.shipperTransportationId(addToShipperTransportationId)
				.shipperGatewayId(shipperGatewayId)
				.build();
		shipperGatewayFacade.createAndSendDeliveryOrdersForPackages(request);
	}

	public LocalDate getShipperDeliveryOrderPickupDate()
	{
		if (_shipperDeliveryOrderPickupDate == null)
		{
			Check.assume(addToShipperTransportationId > 0, "addToShipperTransportationId > 0");
			final I_M_ShipperTransportation shipperTransportation = load(addToShipperTransportationId, I_M_ShipperTransportation.class);
			_shipperDeliveryOrderPickupDate = TimeUtil.asLocalDate(shipperTransportation.getDateDoc());
		}
		return _shipperDeliveryOrderPickupDate;
	}
}
