package de.metas.frontend_testing.masterdata.mobile_configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.job.service.CreateShipmentPolicy;
import de.metas.mobile.MobileAuthMethod;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonMobileConfigResponse
{
	@Nullable MobileAuthMethod defaultAuthMethod;
	@Nullable @JsonInclude(JsonInclude.Include.NON_EMPTY) Picking picking;
	@Nullable @JsonInclude(JsonInclude.Include.NON_EMPTY) Distribution distribution;

	//
	//
	//

	@Value
	@Builder
	@Jacksonized
	public static class Picking
	{
		@Nullable PickingJobAggregationType aggregationType;
		@Nullable Boolean allowPickingAnyCustomer;
		@Nullable Boolean allowPickingAnyHU;
		@Nullable CreateShipmentPolicy createShipmentPolicy;
		@Nullable Boolean alwaysSplitHUsEnabled;
		@Nullable Boolean pickWithNewLU;
		@Nullable Boolean allowNewTU;
		@Nullable Boolean allowCompletingPartialPickingJob;
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
