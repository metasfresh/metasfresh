package de.metas.frontend_testing.masterdata.mobile_configuration;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
<<<<<<< HEAD
=======
import de.metas.handlingunits.picking.config.mobileui.PickingJobFieldType;
import de.metas.handlingunits.picking.job.model.facets.PickingJobFacetGroup;
>>>>>>> 5287177c7d (mobile UI picking: show ProductNo if configured (#22139))
import de.metas.handlingunits.picking.job.service.CreateShipmentPolicy;
import de.metas.mobile.MobileAuthMethod;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;

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
		@Nullable PickingJobAggregationType aggregationType;
		@Nullable Boolean allowPickingAnyCustomer;
		@Nullable Boolean allowPickingAnyHU;
		@Nullable CreateShipmentPolicy createShipmentPolicy;
		@Nullable Boolean alwaysSplitHUsEnabled;
		@Nullable Boolean allowCompletingPartialPickingJob;
		@Nullable Boolean pickWithNewLU;
		@Nullable Boolean allowNewTU;
		@Nullable Boolean allowSkippingRejectedReason;
		@Nullable Boolean filterByQRCode;
		@Nullable Boolean showLastPickedBestBeforeDateForLines;

		@Nullable List<Customer> customers;
<<<<<<< HEAD
=======
		
		@Nullable List<PickingJobFacetGroup> filters;
		
		@Nullable List<Field> fields;
>>>>>>> 5287177c7d (mobile UI picking: show ProductNo if configured (#22139))

		@Value
		@Builder
		@Jacksonized
		public static class Customer
		{
			@NonNull Identifier customer;
		}

		@Value
		@Builder
		@Jacksonized
		public static class Field
		{
			@NonNull PickingJobFieldType field;
			@Nullable Boolean isShowInSummary;
			@Nullable Boolean isShowInDetailed;
			@Nullable String pattern;
		}
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
