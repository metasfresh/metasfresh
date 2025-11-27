package de.metas.frontend_testing.masterdata.mobile_configuration;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.handlingunits.picking.config.mobileui.PickAttribute;
import de.metas.handlingunits.picking.config.mobileui.PickToStructure;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.job.service.CreateShipmentPolicy;
import de.metas.mobile.MobileAuthMethod;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.ad.dao.QueryLimit;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

@Value
@Builder
@Jacksonized
public class JsonMobileConfigRequest
{
	@Nullable MobileAuthMethod defaultAuthMethod;
	@Nullable Picking picking;
	@Nullable Distribution distribution;
	@Nullable Manufacturing manufacturing;

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
		@Nullable Boolean shipOnCloseLU;

		@Nullable Set<PickToStructure> pickTo;
		@Nullable Set<PickAttribute> readAttributes;
		@Nullable @Deprecated Boolean pickWithNewLU;
		@Nullable @Deprecated Boolean allowNewTU;

		@Nullable Boolean allowSkippingRejectedReason;
		@Nullable Boolean filterByQRCode;
		@Nullable Boolean showLastPickedBestBeforeDateForLines;
		@Nullable Boolean anonymousPickHUsOnTheFly;
		@Nullable Boolean displayPickingSlotSuggestions;
		@Nullable Boolean activeWorkplaceRequired;
		@Nullable Boolean considerOnlyJobScheduledToWorkplace;
		@Nullable Boolean allowQuickPackAll;

		@Nullable List<Customer> customers;

		@Value
		@Builder
		@Jacksonized
		public static class Customer
		{
			@NonNull Identifier customer;
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
		@Nullable String captionFormat;
		@Nullable String orderBys;

		@Nullable QueryLimit maxLaunchers;
		@Nullable QueryLimit maxStartedLaunchers;
		@Nullable Boolean allowStartNextJobOnly;

	}

	//
	//
	//

	@Value
	@Builder
	@Jacksonized
	public static class Manufacturing
	{
		@Nullable Boolean isScanResourceRequired;
		@Nullable Boolean isAllowIssuingAnyHU;
	}
}
