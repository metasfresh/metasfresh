package de.metas.shipper.gateway.spi;

import de.metas.async.AsyncBatchId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.organization.OrgId;
import de.metas.shipper.gateway.spi.model.DeliveryOrderCreateRequest;
import de.metas.shipping.ShipperId;
import de.metas.shipping.model.ShipperTransportationId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.compiere.model.I_M_Package;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalTime;

@Value
@Builder
public class DeliveryOrderKey
{
	@NonNull ShipperId shipperId;
	@NonNull ShipperTransportationId shipperTransportationId;
	@NonNull OrgId fromOrgId;
	@NonNull BPartnerLocationId deliverToBPartnerLocationId;
	@NonNull LocalDate pickupDate;
	@NonNull LocalTime timeFrom;
	@NonNull LocalTime timeTo;
	@Nullable AsyncBatchId asyncBatchId;

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
					.deliverToBPartnerLocationId(BPartnerLocationId.ofRepoId(mpackage.getC_BPartner_ID(), mpackage.getC_BPartner_Location_ID()));
		}

	}
}
