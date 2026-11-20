package de.metas.frontend_testing.masterdata.mobile_configuration;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.distribution.mobileui.config.MobileUIDistributionConfigRepository;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.frontend_testing.masterdata.custom_qrcode_format.CustomQRCodeFormatCommand;
import de.metas.handlingunits.picking.config.mobileui.CUQtyEntryMode;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileService;
import de.metas.manufacturing.config.MobileUIManufacturingConfigRepository;
import de.metas.mobile.MobileConfig;
import de.metas.mobile.MobileConfig.MobileConfigBuilder;
import de.metas.mobile.MobileConfigService;
import de.metas.scannable_code.format.ScannableCodeFormat;
import de.metas.scannable_code.format.ScannableCodeFormatId;
import de.metas.scannable_code.format.service.ScannableCodeFormatService;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Builder
public class MobileConfigCommand
{
	@NonNull private final MobileConfigService mobileConfigService;
	@NonNull private final MobileUIPickingUserProfileService mobilePickingConfigService;
	@NonNull private final MobileUIDistributionConfigRepository mobileDistributionConfigRepository;
	@NonNull private final MobileUIManufacturingConfigRepository mobileManufacturingConfigRepository;
	@NonNull private final ScannableCodeFormatService scannableCodeFormatService;
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);

	@NonNull private final MasterdataContext context;
	@NonNull private final JsonMobileConfigRequest request;

	public JsonMobileConfigResponse execute()
	{
		final MobileConfig config = updateMobileConfig();
		final JsonMobileConfigResponse.Picking picking = updatePickingConfig();
		updatePickingCUQtyEntry();
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
		if (request.getPicking() == null)
		{
			return null;
		}

		return MobileConfigPickingCommand.builder()
				.mobilePickingConfigService(mobilePickingConfigService)
				.context(context)
				.request(request.getPicking())
				.build().execute();
	}

	/**
	 * Writes the CU quantity entry mode and CU label format of the profile and of its customers.
	 * Every value is written on each run (absent = default mode / no format), so no state leaks from an earlier masterdata run.
	 * Runs after {@link #updatePickingConfig()}, so the profile header and customer rows exist.
	 */
	private void updatePickingCUQtyEntry()
	{
		final JsonMobileConfigRequest.Picking picking = request.getPicking();
		if (picking == null)
		{
			return;
		}

		trxManager.runInThreadInheritedTrx(() -> updatePickingCUQtyEntryInTrx(picking));
	}

	private void updatePickingCUQtyEntryInTrx(@NonNull final JsonMobileConfigRequest.Picking picking)
	{
		// the rows of earlier runs' customers (replaced by MobileConfigPickingCommand above) are not referenced any more
		mobilePickingConfigService.deleteUnusedPickingJobOptions(CustomerCUQtyEntryWriter.JOB_OPTIONS_NAME_PREFIX);

		// absent = the profile's mandatory mode: the profile writer stores CUQtyEntryMode.DEFAULT for an unset mode
		final CUQtyEntryMode mode = CUQtyEntryMode.ofNullableCode(picking.getCuQtyEntryMode());
		final ScannableCodeFormatId formatId = resolveFormatIdOrNull(picking.getCuLabelFormatName());
		mobilePickingConfigService.update(profile -> profile.toBuilder()
				.defaultPickingJobOptions(profile.getDefaultPickingJobOptions().toBuilder()
						.cuQtyEntryMode(mode)
						.cuLabelFormatId(formatId)
						.build())
				.build());

		if (picking.getCustomers() != null)
		{
			final CustomerCUQtyEntryWriter customerWriter = new CustomerCUQtyEntryWriter(mobilePickingConfigService);
			for (final JsonMobileConfigRequest.Picking.Customer customer : picking.getCustomers())
			{
				customerWriter.write(
						context.getId(customer.getCustomer(), BPartnerId.class),
						CUQtyEntryMode.ofNullableCode(customer.getCuQtyEntryMode()),
						resolveFormatIdOrNull(customer.getCuLabelFormatName()));
			}
		}
	}

	/** Returns the format id, or null (= no format) when the name is absent. */
	@Nullable
	private ScannableCodeFormatId resolveFormatIdOrNull(@Nullable final String formatName)
	{
		return formatName != null ? resolveFormatId(formatName) : null;
	}

	/**
	 * Unlinks the profile and job options rows from the CU label formats tagged by {@link CustomQRCodeFormatCommand},
	 * so that these formats can be deleted (and re-created) by the masterdata request.
	 */
	public static void unlinkTaggedCULabelFormats(
			@NonNull final ScannableCodeFormatService scannableCodeFormatService,
			@NonNull final MobileUIPickingUserProfileService mobilePickingConfigService)
	{
		final ImmutableList<ScannableCodeFormatId> taggedFormatIds = scannableCodeFormatService.getAll().toList()
				.stream()
				.filter(format -> CustomQRCodeFormatCommand.TAGGING_DESCRIPTION.equals(format.getDescription()))
				.map(ScannableCodeFormat::getId)
				.collect(ImmutableList.toImmutableList());
		mobilePickingConfigService.unlinkCULabelFormats(taggedFormatIds);
	}

	/**
	 * Resolves a CU label format by its name among the formats created by the masterdata (see {@link CustomQRCodeFormatCommand}).
	 */
	private ScannableCodeFormatId resolveFormatId(@NonNull final String formatName)
	{
		final ImmutableList<ScannableCodeFormat> matches = scannableCodeFormatService.getAll().toList()
				.stream()
				.filter(format -> formatName.equals(format.getName()))
				.filter(format -> CustomQRCodeFormatCommand.TAGGING_DESCRIPTION.equals(format.getDescription()))
				.collect(ImmutableList.toImmutableList());
		if (matches.size() != 1)
		{
			throw new AdempiereException("CU label format '" + formatName + "' must match exactly one custom QR code format, found " + matches.size());
		}
		return matches.get(0).getId();
	}

	private JsonMobileConfigResponse.Distribution updateDistributionConfig()
	{
		if (request.getDistribution() == null)
		{
			return null;
		}

		return MobileConfigDistributionCommand.builder()
				.mobileDistributionConfigRepository(mobileDistributionConfigRepository)
				.request(request.getDistribution())
				.build().execute();
	}

	private JsonMobileConfigResponse.Manufacturing updateManufacturingConfig()
	{
		if (request.getManufacturing() == null)
		{
			return null;
		}

		return MobileConfigManufacturingCommand.builder()
				.mobileManufacturingConfigRepository(mobileManufacturingConfigRepository)
				.context(context)
				.request(request.getManufacturing())
				.build().execute();
	}
}
