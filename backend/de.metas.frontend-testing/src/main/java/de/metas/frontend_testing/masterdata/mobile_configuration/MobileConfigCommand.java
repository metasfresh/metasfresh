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
	@NonNull private final PickingJobOptionsWriter jobOptionsWriter = new PickingJobOptionsWriter();

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

	/** Writes the CU quantity entry mode and CU label format of the profile and its customers; runs after {@link #updatePickingConfig()}. */
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
		jobOptionsWriter.deleteUnusedPickingJobOptions(CustomerCUQtyEntryWriter.JOB_OPTIONS_NAME_PREFIX);

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

	@Nullable
	private ScannableCodeFormatId resolveFormatIdOrNull(@Nullable final String formatName)
	{
		return formatName != null ? resolveFormatId(formatName) : null;
	}

	private ScannableCodeFormatId resolveFormatId(@NonNull final String formatName)
	{
		final ImmutableList<ScannableCodeFormat> matches = scannableCodeFormatService.getAll().toList()
				.stream()
				.filter(format -> formatName.equals(format.getName()))
				.filter(CustomQRCodeFormatCommand::isGeneratedFormat)
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
