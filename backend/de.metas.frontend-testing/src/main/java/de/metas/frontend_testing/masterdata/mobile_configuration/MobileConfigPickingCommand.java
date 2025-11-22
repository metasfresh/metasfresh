package de.metas.frontend_testing.masterdata.mobile_configuration;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.picking.config.mobileui.AllowedPickToStructures;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfile;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileService;
import de.metas.handlingunits.picking.config.mobileui.PickAttribute;
import de.metas.handlingunits.picking.config.mobileui.PickAttributesConfig;
import de.metas.handlingunits.picking.config.mobileui.PickToStructure;
import de.metas.handlingunits.picking.config.mobileui.PickingCustomerConfig;
import de.metas.handlingunits.picking.config.mobileui.PickingCustomerConfigsCollection;
import de.metas.handlingunits.picking.config.mobileui.PickingJobOptions;
import de.metas.util.OptionalBoolean;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Builder
class MobileConfigPickingCommand
{
	@NonNull private final MobileUIPickingUserProfileService mobilePickingConfigService;

	@NonNull private final MasterdataContext context;
	@NonNull private final JsonMobileConfigRequest.Picking request;

	public JsonMobileConfigResponse.Picking execute()
	{
		mobilePickingConfigService.update((profile) -> {
			final MobileUIPickingUserProfile.MobileUIPickingUserProfileBuilder newProfileBuilder = profile.toBuilder()
					.defaultPickingJobOptions(updatePickingJobOptions(profile.getDefaultPickingJobOptions(), request))
					.customerConfigs(updatePickingCustomers(profile.getCustomerConfigs(), request.getCustomers()))
					.isFilterByBarcode(request.getFilterByQRCode() != null && request.getFilterByQRCode())
					.isActiveWorkplaceRequired(request.getActiveWorkplaceRequired() != null ? request.getActiveWorkplaceRequired() : false)
					.isConsiderOnlyJobScheduledToWorkplace(request.getConsiderOnlyJobScheduledToWorkplace() != null ? request.getConsiderOnlyJobScheduledToWorkplace() : false)
					.isAllowQuickPackAll(request.getAllowQuickPackAll() != null ? request.getAllowQuickPackAll() : false);

			if (request.getAllowPickingAnyCustomer() != null)
			{
				newProfileBuilder.isAllowPickingAnyCustomer(request.getAllowPickingAnyCustomer());
			}

			return newProfileBuilder.build();
		});

		final MobileUIPickingUserProfile newProfile = mobilePickingConfigService.getProfile(); // reload to make sure we are returning exactly what's in DB

		// guard against common config errors
		if (newProfile.getDefaultPickingJobOptions().getAllowedPickToStructures().toAllowedSet().isEmpty())
		{
			throw new AdempiereException("Picking profile shall have at least one pick to structure available");
		}

		return toJson(newProfile);
	}

	private static JsonMobileConfigResponse.Picking toJson(final MobileUIPickingUserProfile profile)
	{
		return JsonMobileConfigResponse.Picking.builder()
				.aggregationType(profile.getDefaultPickingJobOptions().getAggregationType())
				.allowPickingAnyCustomer(profile.isAllowPickingAnyCustomer())
				.allowPickingAnyHU(profile.getDefaultPickingJobOptions().isAllowPickingAnyHU())
				.createShipmentPolicy(profile.getDefaultPickingJobOptions().getCreateShipmentPolicy())
				.alwaysSplitHUsEnabled(profile.getDefaultPickingJobOptions().isAlwaysSplitHUsEnabled())
				.shipOnCloseLU(profile.getDefaultPickingJobOptions().isShipOnCloseLU())
				.pickTo(profile.getDefaultPickingJobOptions().getAllowedPickToStructures().toAllowedSet())
				.readAttributes(profile.getDefaultPickingJobOptions().getPickAttributes().getAttributesToReadSet())
				.filterByQRCode(profile.isFilterByBarcode())
				.allowCompletingPartialPickingJob(profile.getDefaultPickingJobOptions().isAllowCompletingPartialPickingJob())
				.isAnonymousPickHUsOnTheFly(profile.getDefaultPickingJobOptions().isAnonymousPickHUsOnTheFly())
				.displayPickingSlotSuggestions(profile.getDefaultPickingJobOptions().getDisplayPickingSlotSuggestions().toBooleanOrNull())
				.activeWorkplaceRequired(profile.isActiveWorkplaceRequired())
				.considerOnlyJobScheduledToWorkplace(profile.isConsiderOnlyJobScheduledToWorkplace())
				.build();
	}

	private static PickingJobOptions updatePickingJobOptions(final PickingJobOptions pickingJobOptions, final JsonMobileConfigRequest.Picking from)
	{
		final PickingJobOptions.PickingJobOptionsBuilder builder = pickingJobOptions.toBuilder();
		if (from.getAggregationType() != null)
		{
			builder.aggregationType(from.getAggregationType());
		}
		if (from.getAllowPickingAnyHU() != null)
		{
			builder.isAllowPickingAnyHU(from.getAllowPickingAnyHU());
		}
		if (from.getCreateShipmentPolicy() != null)
		{
			builder.createShipmentPolicy(from.getCreateShipmentPolicy());
		}
		if (from.getAlwaysSplitHUsEnabled() != null)
		{
			builder.isAlwaysSplitHUsEnabled(from.getAlwaysSplitHUsEnabled());
		}
		builder.isShipOnCloseLU(from.getShipOnCloseLU() != null ? from.getShipOnCloseLU() : false);

		builder.allowedPickToStructures(extractAllowedPickToStructures(from));
		builder.pickAttributes(extractPickAttributes(from));

		if (from.getAllowCompletingPartialPickingJob() != null)
		{
			builder.isAllowCompletingPartialPickingJob(from.getAllowCompletingPartialPickingJob());
		}
		if (from.getAllowSkippingRejectedReason() != null)
		{
			builder.isAllowSkippingRejectedReason(from.getAllowSkippingRejectedReason());
		}
		if (from.getShowLastPickedBestBeforeDateForLines() != null)
		{
			builder.isShowLastPickedBestBeforeDateForLines(from.getShowLastPickedBestBeforeDateForLines());
		}
		if (from.getAnonymousPickHUsOnTheFly() != null)
		{
			builder.isAnonymousPickHUsOnTheFly(from.getAnonymousPickHUsOnTheFly());
		}

		builder.displayPickingSlotSuggestions(OptionalBoolean.ofNullableBoolean(from.getDisplayPickingSlotSuggestions()));

		return builder.build();
	}

	private static PickAttributesConfig extractPickAttributes(final JsonMobileConfigRequest.Picking from)
	{
		final Set<PickAttribute> readAttributes = from.getReadAttributes();
		if (readAttributes == null)
		{
			return PickAttributesConfig.DEFAULT;
		}

		final PickAttributesConfig.PickAttributesConfigBuilder builder = PickAttributesConfig.builder();
		Stream.of(PickAttribute.values()).forEach(pickAttribute -> builder.attributeToRead(pickAttribute, readAttributes.contains(pickAttribute)));
		return builder.build();
	}

	private static AllowedPickToStructures extractAllowedPickToStructures(final JsonMobileConfigRequest.Picking from)
	{
		final HashMap<PickToStructure, Boolean> allowedPickToStructures = new HashMap<>();
		if (from.getPickTo() != null)
		{
			from.getPickTo().forEach(pickToStructure -> allowedPickToStructures.put(pickToStructure, true));
		}

		//noinspection deprecation
		final Boolean pickWithNewLU = from.getPickWithNewLU();
		if (pickWithNewLU != null)
		{
			allowedPickToStructures.put(PickToStructure.LU_TU, pickWithNewLU);
			allowedPickToStructures.put(PickToStructure.LU_CU, pickWithNewLU);
		}

		//noinspection deprecation
		final Boolean allowNewTU = from.getAllowNewTU();
		if (allowNewTU != null)
		{
			allowedPickToStructures.put(PickToStructure.TU, allowNewTU);
		}

		return AllowedPickToStructures.ofMap(allowedPickToStructures);
	}

	private PickingCustomerConfigsCollection updatePickingCustomers(
			@NonNull final PickingCustomerConfigsCollection customers,
			@Nullable final List<JsonMobileConfigRequest.Picking.Customer> fromCustomersList)
	{
		if (fromCustomersList == null)
		{
			return customers;
		}
		else if (fromCustomersList.isEmpty())
		{
			return PickingCustomerConfigsCollection.EMPTY;
		}

		final ImmutableList<PickingCustomerConfig> result = CollectionUtils.<PickingCustomerConfig, JsonMobileConfigRequest.Picking.Customer, BPartnerId>syncLists()
				.target(customers)
				.targetKeyExtractor(PickingCustomerConfig::getCustomerId)
				.source(fromCustomersList)
				.sourceKeyExtractor(fromCustomer -> context.getId(fromCustomer.getCustomer(), BPartnerId.class))
				.mergeFunction((target, from) -> {
					if (target == null)
					{
						final BPartnerId customerId = context.getId(from.getCustomer(), BPartnerId.class);
						return PickingCustomerConfig.builder().customerId(customerId).build();
					}
					else
					{
						return target;
					}
				})
				.execute();

		return PickingCustomerConfigsCollection.ofCollection(result);
	}
}
