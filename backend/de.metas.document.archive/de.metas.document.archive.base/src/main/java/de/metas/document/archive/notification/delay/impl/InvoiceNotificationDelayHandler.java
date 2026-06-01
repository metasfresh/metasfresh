package de.metas.document.archive.notification.delay.impl;

import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.notification.delay.DocOutboundNotificationDelayHandler;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_Carrier_ShipmentOrder;
import org.compiere.model.I_Carrier_ShipmentOrder_Parcel;
import org.compiere.model.I_M_InOutLine;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Delays invoice notification until all carrier parcels of the invoice's shipments
 * have a TrackingURL assigned — controlled by SysConfig {@link #SYSCONFIG_DelayUntilCarrierConfirmed}.
 */
@Component
public class InvoiceNotificationDelayHandler implements DocOutboundNotificationDelayHandler
{
	public static final String SYSCONFIG_DelayUntilCarrierConfirmed = "delayNotificationUntilShipmentConfirmedByCarrier";

	@Override
	public String getTableName()
	{
		return I_C_Invoice.Table_Name;
	}

	@Override
	public boolean shouldDelaySending(@NonNull final I_C_Doc_Outbound_Log log)
	{
		if (!Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_DelayUntilCarrierConfirmed, false))
		{
			return false;
		}

		final int invoiceId = log.getRecord_ID();
		if (invoiceId <= 0)
		{
			return false;
		}

		final List<I_C_InvoiceLine> invoiceLines = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_InvoiceLine.class)
				.addEqualsFilter(I_C_InvoiceLine.COLUMNNAME_C_Invoice_ID, invoiceId)
				.addCompareFilter(I_C_InvoiceLine.COLUMNNAME_M_InOutLine_ID, CompareQueryFilter.Operator.GREATER, 0)
				.create()
				.list();

		final Set<Integer> inOutIds = new HashSet<Integer>();
		for (final I_C_InvoiceLine invoiceLine : invoiceLines)
		{
			final int inOutLineId = invoiceLine.getM_InOutLine_ID();
			if (inOutLineId <= 0)
			{
				continue;
			}
			final I_M_InOutLine inOutLine = Services.get(IQueryBL.class)
					.createQueryBuilder(I_M_InOutLine.class)
					.addEqualsFilter(I_M_InOutLine.COLUMNNAME_M_InOutLine_ID, inOutLineId)
					.create()
					.firstOnly();
			if (inOutLine != null && inOutLine.getM_InOut_ID() > 0)
			{
				inOutIds.add(inOutLine.getM_InOut_ID());
			}
		}

		for (final int inOutId : inOutIds)
		{
			if (hasPendingCarrierTracking(inOutId))
			{
				return true;
			}
		}

		return false;
	}

	private boolean hasPendingCarrierTracking(final int inOutId)
	{
		final List<I_M_ShippingPackage> packages = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ShippingPackage.class)
				.addEqualsFilter(I_M_ShippingPackage.COLUMNNAME_M_InOut_ID, inOutId)
				.addCompareFilter(I_M_ShippingPackage.COLUMNNAME_M_ShipperTransportation_ID, CompareQueryFilter.Operator.GREATER, 0)
				.create()
				.list();

		for (final I_M_ShippingPackage pkg : packages)
		{
			final int shippingTransportationId = pkg.getM_ShipperTransportation_ID();

			final List<I_Carrier_ShipmentOrder> orders = Services.get(IQueryBL.class)
					.createQueryBuilder(I_Carrier_ShipmentOrder.class)
					.addEqualsFilter(I_Carrier_ShipmentOrder.COLUMNNAME_M_ShipperTransportation_ID, shippingTransportationId)
					.create()
					.list();

			for (final I_Carrier_ShipmentOrder order : orders)
			{
				final List<I_Carrier_ShipmentOrder_Parcel> parcels = Services.get(IQueryBL.class)
						.createQueryBuilder(I_Carrier_ShipmentOrder_Parcel.class)
						.addEqualsFilter(I_Carrier_ShipmentOrder_Parcel.COLUMNNAME_Carrier_ShipmentOrder_ID, order.getCarrier_ShipmentOrder_ID())
						.create()
						.list();

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
