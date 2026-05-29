package de.metas.shipper.gateway.nshift.config;

import de.metas.shipping.ShipperId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class NShiftConfig
{
	@NonNull ShipperId shipperId;
	/**
	 * The base URL for the nShift test/demo API is <a href="https://demo.shipmentserver.com:8080">...</a>.
	 * The base URL for the nShift production API is <a href="https://www.shipmentserver.com">...</a>
	 */
	@NonNull @Builder.Default String baseUrl = "https://demo.shipmentserver.com:8080";
	@NonNull @Builder.Default String actorId = "123";
	@NonNull String username; //TODO add openID as option or replace basicAuth
	@NonNull String password;
	@NonNull @Builder.Default String trackingUrlTemplate = "https://www.unifaunonline.com/ext.uo.{lang}.track?shipmentNo={shipmentNo}";

	public String getTrackingUrl(@NonNull final String shipmentId, @NonNull final String language)
	{
		return trackingUrlTemplate
				.replace("{lang}", language)
				.replace("{shipmentNo}", shipmentId);
	}

	// TODO
}
