package de.metas.shipper.gateway.spi;

import de.metas.async.AsyncBatchId;
import de.metas.organization.OrgId;
import de.metas.shipper.gateway.spi.model.DeliveryOrderCreateRequest;
import de.metas.shipping.ShipperId;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.compiere.model.I_M_Package;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalTime;

@Value
public class DeliveryOrderKey
{
	ShipperId shipperId;
	ShipperTransportationId shipperTransportationId;
	OrgId fromOrgId;
	int deliverToBPartnerId;
	int deliverToBPartnerLocationId;
	LocalDate pickupDate;
	LocalTime timeFrom;
	LocalTime timeTo;
	AsyncBatchId asyncBatchId;

	@Builder
	public DeliveryOrderKey(
			@NonNull final ShipperId shipperId,
			@NonNull final ShipperTransportationId shipperTransportationId,
			@NonNull final OrgId fromOrgId,
			final int deliverToBPartnerId,
			final int deliverToBPartnerLocationId,
			@NonNull final LocalDate pickupDate,
			@NonNull final LocalTime timeFrom,
			@NonNull final LocalTime timeTo,
			@Nullable final AsyncBatchId asyncBatchId)
	{
		Check.assume(deliverToBPartnerId > 0, "deliverToBPartnerId > 0");
		Check.assume(deliverToBPartnerLocationId > 0, "deliverToBPartnerLocationId > 0");

		this.shipperId = shipperId;
		this.shipperTransportationId = shipperTransportationId;
		this.fromOrgId = fromOrgId;
		this.deliverToBPartnerId = deliverToBPartnerId;
		this.deliverToBPartnerLocationId = deliverToBPartnerLocationId;
		this.pickupDate = pickupDate;
		this.timeFrom = timeFrom;
		this.timeTo = timeTo;

		this.asyncBatchId = asyncBatchId;
	}

	public static class DeliveryOrderKeyBuilder
	{
		public DeliveryOrderKeyBuilder setFrom(@NonNull final DeliveryOrderCreateRequest request)
		{
			return shipperTransportationId(request.getShipperTransportationId())
					.pickupDate(request.getPickupDate())
					.timeFrom(request.getTimeFrom())
					.timeTo(request.getTimeTo())
					.asyncBatchId(request.getAsyncBatchId());
		}

		public DeliveryOrderKeyBuilder setFrom(@NonNull final I_M_Package mpackage)
		{
			return shipperId(ShipperId.ofRepoId(mpackage.getM_Shipper_ID()))
					.fromOrgId(OrgId.ofRepoId(mpackage.getAD_Org_ID()))
					.deliverToBPartnerId(mpackage.getC_BPartner_ID())
					.deliverToBPartnerLocationId(mpackage.getC_BPartner_Location_ID());
		}

	}
}
