package de.metas.shipping.api;

import de.metas.inout.InOutId;
import de.metas.order.OrderId;
import de.metas.shipping.ShipperId;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.compiere.model.I_M_Package;

import javax.annotation.Nullable;
import java.util.Collection;

public interface IShipperTransportationBL extends ISingletonService
{
	void save(@NonNull I_M_ShipperTransportation shipperTransportationRecord);

	/**
	 * Links given {@link I_M_Package} to shipper transportation.
	 *
	 */
	I_M_ShippingPackage createShippingPackage(ShipperTransportationId shipperTransportationId, I_M_Package mpackage);

	/**
	 * Finds and set suitable document type to given shipper transportation.
	 */
	void setC_DocType(I_M_ShipperTransportation shipperTransportation);

	boolean isAnyOrderAssignedToDifferentTransportationOrder(ShipperTransportationId shipperTransportationId, @NonNull Collection<OrderId> orderIds);

	void setShipper(@NonNull I_M_ShipperTransportation shipperTransportation, @NonNull ShipperId shipperId);

	/**
	 * The M_InOut to M_ShipperTransportation link is only ever SET, never cleared, anywhere else in the codebase.
	 * Call this whenever a M_ShippingPackage row that carried the link is removed or deactivated, so a shipment
	 * does not stay permanently linked to a transport order it no longer has any active package on.
	 */
	void unlinkShipmentIfOrphaned(@Nullable InOutId inOutId, @Nullable ShipperTransportationId shipperTransportationId);
}
