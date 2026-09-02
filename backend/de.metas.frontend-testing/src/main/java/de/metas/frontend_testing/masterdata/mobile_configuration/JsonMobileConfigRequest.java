package de.metas.frontend_testing.masterdata.mobile_configuration;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.handlingunits.picking.config.mobileui.PickAttribute;
import de.metas.handlingunits.picking.config.mobileui.PickToStructure;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.config.mobileui.PickingJobFieldType;
import de.metas.handlingunits.picking.job.model.facets.PickingJobFacetGroup;
import de.metas.handlingunits.picking.job.service.CreateShipmentPolicy;
import de.metas.mobile.MobileAuthMethod;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.mm.attributes.AttributeCode;

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
		@Nullable Boolean completeJobAutomatically;
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
		@Nullable Boolean pickingSlotRequired;
		@Nullable Boolean displayPickingSlotSuggestions;
		@Nullable Boolean activeWorkplaceRequired;
		@Nullable Boolean considerOnlyJobScheduledToWorkplace;
		@Nullable Boolean allowQuickPackAll;
		@Nullable Boolean massPrinting;
		@Nullable Boolean showQtyAvailableForLines;
		@Nullable Boolean showPromptWhenOverPicking;
		@Nullable Boolean warnShelfLifeUndercut;

		@Nullable List<Customer> customers;
		
		@Nullable List<PickingJobFacetGroup> filters;
		
		@Nullable List<Field> fields;

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
		@Nullable String captionFormat;
		@Nullable String orderBys;

		@Nullable Boolean requireTrolley;
		@Nullable Boolean requireScanningProductCode;
		@Nullable Boolean navigateToJobsListAfterPickFromComplete;
		@Nullable Boolean completeJobAutomatically;

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
		@Nullable String receiveUnitType;
		@Nullable Boolean isAllowFinishedGoodsReceiveToLU;
		@Nullable Boolean isAllowFinishedGoodsReceiveToTU;
		@Nullable Boolean isSkipFinishedGoodsReceiveTargetStep;
		@Nullable Boolean isCaptureCatchWeightAtReceipt;
		@Nullable Boolean isAllowReceiveWithoutPackingItem;

		/**
		 * Ordered list of {@code M_Attribute.Value} codes to configure as the mfg editable-attribute list
		 * (global-only, v1 - see {@code de.metas.manufacturing.config.MobileUIManufacturingConfig#getEditableAttributeCodesInOrder()}).
		 * When present (an empty list included), REPLACES the current global list; {@code null} leaves it untouched.
		 * Each attribute must already exist (e.g. via the {@code attributes} masterdata section).
		 */
		@Nullable List<AttributeCode> editableAttributes;
	}
}
