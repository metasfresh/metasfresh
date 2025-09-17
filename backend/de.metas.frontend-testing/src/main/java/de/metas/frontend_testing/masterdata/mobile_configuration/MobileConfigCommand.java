package de.metas.frontend_testing.masterdata.mobile_configuration;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.distribution.config.MobileUIDistributionConfig;
import de.metas.distribution.config.MobileUIDistributionConfig.MobileUIDistributionConfigBuilder;
import de.metas.distribution.config.MobileUIDistributionConfigRepository;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.picking.config.mobileui.AllowedPickToStructures;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfile;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileRepository;
import de.metas.handlingunits.picking.config.mobileui.PickToStructure;
import de.metas.handlingunits.picking.config.mobileui.PickingCustomerConfig;
import de.metas.handlingunits.picking.config.mobileui.PickingCustomerConfigsCollection;
import de.metas.handlingunits.picking.config.mobileui.PickingJobOptions;
import de.metas.handlingunits.picking.config.mobileui.PickingJobOptions.PickingJobOptionsBuilder;
import de.metas.manufacturing.config.MobileUIManufacturingConfig;
import de.metas.manufacturing.config.MobileUIManufacturingConfig.MobileUIManufacturingConfigBuilder;
import de.metas.manufacturing.config.MobileUIManufacturingConfigRepository;
import de.metas.mobile.MobileConfig;
import de.metas.mobile.MobileConfig.MobileConfigBuilder;
import de.metas.mobile.MobileConfigService;
import de.metas.user.UserId;
import de.metas.util.OptionalBoolean;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;

@Builder
public class MobileConfigCommand
{
	@NonNull private final MobileConfigService mobileConfigService;
	@NonNull private final MobileUIPickingUserProfileRepository mobilePickingConfigRepository;
	@NonNull private final MobileUIDistributionConfigRepository mobileDistributionConfigRepository;
	@NonNull private final MobileUIManufacturingConfigRepository mobileManufacturingConfigRepository;

	@NonNull private final MasterdataContext context;
	@NonNull private final JsonMobileConfigRequest request;

	public JsonMobileConfigResponse execute()
	{
		final MobileConfig config = updateMobileConfig();
		final JsonMobileConfigResponse.Picking picking = updatePickingConfig();
		final JsonMobileConfigResponse.Distribution distribution = updateDistributionConfig();
		final JsonMobileConfigResponse.Manufacturing manufacturing = updateManufacturingConfig();

		return JsonMobileConfigResponse.builder()
				.defaultAuthMethod(config.getDefaultAuthMethod())
				.picking(picking)
				.distribution(distribution)
				.manufacturing(manufacturing)
				.build();
	}

	private MobileConfig updateMobileConfig()
	{
		final MobileConfigBuilder configBuilder = mobileConfigService.getConfig()
				.orElse(MobileConfig.DEFAULT)
				.toBuilder();

		if (request.getDefaultAuthMethod() != null)
		{
			configBuilder.defaultAuthMethod(request.getDefaultAuthMethod());
		}

		final MobileConfig config = configBuilder.build();

		mobileConfigService.save(config);
		return config;
	}

	private JsonMobileConfigResponse.Picking updatePickingConfig()
	{
		final JsonMobileConfigRequest.Picking picking = request.getPicking();
		if (picking == null)
		{
			return null;
		}

		final MobileUIPickingUserProfile profile = mobilePickingConfigRepository.getProfile();
		final MobileUIPickingUserProfile.MobileUIPickingUserProfileBuilder newProfileBuilder = profile.toBuilder()
				.defaultPickingJobOptions(updatePickingJobOptions(profile.getDefaultPickingJobOptions(), picking))
				.customerConfigs(updatePickingCustomers(profile.getCustomerConfigs(), picking.getCustomers()))
				.isFilterByBarcode(picking.getFilterByQRCode() != null && picking.getFilterByQRCode())
				.isConsiderOnlyJobScheduledToWorkplace(picking.getConsiderOnlyJobScheduledToWorkplace() != null ? picking.getConsiderOnlyJobScheduledToWorkplace() : false);

		if (picking.getAllowPickingAnyCustomer() != null)
		{
			newProfileBuilder.isAllowPickingAnyCustomer(picking.getAllowPickingAnyCustomer());
		}

		MobileUIPickingUserProfile newProfile = newProfileBuilder.build();
		mobilePickingConfigRepository.save(newProfile);

		newProfile = mobilePickingConfigRepository.getProfile(); // reload to make sure we are returning exactly what's in DB

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
				.filterByQRCode(profile.isFilterByBarcode())
				.allowCompletingPartialPickingJob(profile.getDefaultPickingJobOptions().isAllowCompletingPartialPickingJob())
				.isAnonymousPickHUsOnTheFly(profile.getDefaultPickingJobOptions().isAnonymousPickHUsOnTheFly())
				.displayPickingSlotSuggestions(profile.getDefaultPickingJobOptions().getDisplayPickingSlotSuggestions().toBooleanOrNull())
				.considerOnlyJobScheduledToWorkplace(profile.isConsiderOnlyJobScheduledToWorkplace())
				.build();
	}

	private static PickingJobOptions updatePickingJobOptions(final PickingJobOptions pickingJobOptions, final JsonMobileConfigRequest.Picking from)
	{
		final PickingJobOptionsBuilder builder = pickingJobOptions.toBuilder();
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

	private JsonMobileConfigResponse.Distribution updateDistributionConfig()
	{
		final JsonMobileConfigRequest.Distribution distribution = request.getDistribution();
		if (distribution == null)
		{
			return null;
		}

		final MobileUIDistributionConfigBuilder newConfigBuilder = mobileDistributionConfigRepository.getConfig().toBuilder();
		if (distribution.getAllowPickingAnyHU() != null)
		{
			newConfigBuilder.allowPickingAnyHU(distribution.getAllowPickingAnyHU());
		}

		final MobileUIDistributionConfig newConfig = newConfigBuilder.build();
		mobileDistributionConfigRepository.save(newConfig);

		return JsonMobileConfigResponse.Distribution.builder()
				.allowPickingAnyHU(newConfig.isAllowPickingAnyHU())
				.build();
	}

	private JsonMobileConfigResponse.Manufacturing updateManufacturingConfig()
	{
		final JsonMobileConfigRequest.Manufacturing manufacturing = request.getManufacturing();
		if (manufacturing == null)
		{
			return null;
		}

		final UserId loginUserId = context.getIdOfType(UserId.class);
		final MobileUIManufacturingConfigBuilder newConfigBuilder = mobileManufacturingConfigRepository.getConfig(loginUserId, ClientId.METASFRESH).toBuilder();
		if (manufacturing.getIsScanResourceRequired() != null)
		{
			newConfigBuilder.isScanResourceRequired(OptionalBoolean.ofBoolean(manufacturing.getIsScanResourceRequired()));
		}
		if (manufacturing.getIsAllowIssuingAnyHU() != null)
		{
			newConfigBuilder.isAllowIssuingAnyHU(OptionalBoolean.ofBoolean(manufacturing.getIsAllowIssuingAnyHU()));
		}

		final MobileUIManufacturingConfig newConfig = newConfigBuilder.build();
		mobileManufacturingConfigRepository.saveUserConfig(newConfig, loginUserId);

		return JsonMobileConfigResponse.Manufacturing.builder()
				.isScanResourceRequired(newConfig.getIsScanResourceRequired().toBooleanOrNull())
				.isAllowIssuingAnyHU(newConfig.getIsAllowIssuingAnyHU().toBooleanOrNull())
				.build();
	}
}
