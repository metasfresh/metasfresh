package de.metas.document.archive.notification.delay.impl;

import de.metas.inout.InOutId;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_Carrier_ShipmentOrder;
import org.compiere.model.I_Carrier_ShipmentOrder_Parcel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository Tables: M_ShippingPackage, Carrier_ShipmentOrder, Carrier_ShipmentOrder_Parcel
 * <p>Read-only; traverses the carrier tracking chain of a shipment ({@code M_InOut}) to decide
 * whether tracking is still pending (no packages yet, or any parcel lacks a {@code TrackingURL}).
 */
@Repository
public class CarrierTrackingDelayRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

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
