package de.metas.frontend_testing.masterdata.mobile_configuration;

import de.metas.handlingunits.picking.job.service.CreateShipmentPolicy;
import de.metas.mobile.MobileAuthMethod;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonMobileConfigRequest
{
	@Nullable MobileAuthMethod defaultAuthMethod;
	@Nullable Picking picking;
	@Nullable Distribution distribution;

	//
	//
	//

	@Value
	@Builder
	@Jacksonized
	public static class Picking
	{
		@Nullable Boolean allowPickingAnyHU;
		@Nullable CreateShipmentPolicy createShipmentPolicy;
		@Nullable Boolean alwaysSplitHUsEnabled;
	}
	
	//
 	//
 	//

	@Value
	@Builder
	@Jacksonized
	public static class Distribution
	{
		@Nullable Boolean allowPickingAnyHU;
	}
}
