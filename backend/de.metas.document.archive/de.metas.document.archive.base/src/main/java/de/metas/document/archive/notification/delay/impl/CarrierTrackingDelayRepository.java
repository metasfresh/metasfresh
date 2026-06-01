package de.metas.document.archive.notification.delay.impl;

import com.google.common.collect.ImmutableSet;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.invoice.InvoiceId;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_Carrier_ShipmentOrder;
import org.compiere.model.I_Carrier_ShipmentOrder_Parcel;
import org.compiere.model.I_M_InOutLine;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class CarrierTrackingDelayRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/** @return the distinct shipments ({@code M_InOut}) the invoice's lines were derived from. */
	public ImmutableSet<InOutId> retrieveShipmentIds(@NonNull final InvoiceId invoiceId)
	{
		final ImmutableSet<InOutLineId> inOutLineIds = queryBL
				.createQueryBuilder(I_C_InvoiceLine.class)
				.addEqualsFilter(I_C_InvoiceLine.COLUMNNAME_C_Invoice_ID, invoiceId)
				.addCompareFilter(I_C_InvoiceLine.COLUMNNAME_M_InOutLine_ID, Operator.GREATER, 0)
				.addOnlyActiveRecordsFilter()
				.create()
				.list()
				.stream()
				.map(line -> InOutLineId.ofRepoIdOrNull(line.getM_InOutLine_ID()))
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());

		if (inOutLineIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		return queryBL
				.createQueryBuilder(I_M_InOutLine.class)
				.addInArrayFilter(I_M_InOutLine.COLUMNNAME_M_InOutLine_ID, inOutLineIds)
				.addOnlyActiveRecordsFilter()
				.create()
				.list()
				.stream()
				.map(line -> InOutId.ofRepoIdOrNull(line.getM_InOut_ID()))
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

	/** @return true if carrier tracking is still pending (no packages yet, or any parcel still lacks a TrackingURL). */
	public boolean isCarrierTrackingPending(@NonNull final InOutId inOutId)
	{
		final List<I_M_ShippingPackage> packages = queryBL
				.createQueryBuilder(I_M_ShippingPackage.class)
				.addEqualsFilter(I_M_ShippingPackage.COLUMNNAME_M_InOut_ID, inOutId)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();

		if (packages.isEmpty())
		{
			return true;
		}

		for (final I_M_ShippingPackage shippingPackage : packages)
		{
			final ShipperTransportationId transportationId = ShipperTransportationId.ofRepoIdOrNull(shippingPackage.getM_ShipperTransportation_ID());
			if (transportationId == null)
			{
				return true;
			}

			final List<I_Carrier_ShipmentOrder> shipmentOrders = queryBL
					.createQueryBuilder(I_Carrier_ShipmentOrder.class)
					.addEqualsFilter(I_Carrier_ShipmentOrder.COLUMNNAME_M_ShipperTransportation_ID, transportationId)
					.addOnlyActiveRecordsFilter()
					.create()
					.list();

			if (shipmentOrders.isEmpty())
			{
				return true;
			}

			for (final I_Carrier_ShipmentOrder shipmentOrder : shipmentOrders)
			{
				final List<I_Carrier_ShipmentOrder_Parcel> parcels = queryBL
						.createQueryBuilder(I_Carrier_ShipmentOrder_Parcel.class)
						.addEqualsFilter(I_Carrier_ShipmentOrder_Parcel.COLUMNNAME_Carrier_ShipmentOrder_ID, shipmentOrder.getCarrier_ShipmentOrder_ID())
						.addOnlyActiveRecordsFilter()
						.create()
						.list();

				if (parcels.isEmpty())
				{
					return true;
				}

				for (final I_Carrier_ShipmentOrder_Parcel parcel : parcels)
				{
					if (Check.isBlank(parcel.getTrackingURL()))
					{
						return true;
					}
				}
			}
		}

		return false;
	}
}
