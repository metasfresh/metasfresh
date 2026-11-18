package de.metas.frontend_testing.masterdata.mobile_configuration;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.distribution.mobileui.config.MobileUIDistributionConfigRepository;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.frontend_testing.masterdata.custom_qrcode_format.CustomQRCodeFormatCommand;
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
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_MobileUI_UserProfile_Picking;
import org.compiere.model.I_MobileUI_UserProfile_Picking_Job;
import org.compiere.model.X_MobileUI_UserProfile_Picking;

import javax.annotation.Nullable;

@Builder
public class MobileConfigCommand
{
	@NonNull private final MobileConfigService mobileConfigService;
	@NonNull private final MobileUIPickingUserProfileService mobilePickingConfigService;
	@NonNull private final MobileUIDistributionConfigRepository mobileDistributionConfigRepository;
	@NonNull private final MobileUIManufacturingConfigRepository mobileManufacturingConfigRepository;
	@NonNull private final ScannableCodeFormatService scannableCodeFormatService;
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
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

		trxManager.runInThreadInheritedTrx(() -> {
			final I_MobileUI_UserProfile_Picking profileRecord = queryBL
					.createQueryBuilder(I_MobileUI_UserProfile_Picking.class)
					.addOnlyActiveRecordsFilter()
					.create()
					.firstOnlyNotNull(I_MobileUI_UserProfile_Picking.class);

			profileRecord.setCUQtyEntryMode(cuQtyEntryModeOrDefault(picking.getCuQtyEntryMode()));
			profileRecord.setC_ScannableCode_Format_ID(resolveFormatIdOrNoFormat(picking.getCuLabelFormatName()));
			InterfaceWrapperHelper.saveRecord(profileRecord);

			if (picking.getCustomers() != null)
			{
				for (final JsonMobileConfigRequest.Picking.Customer customer : picking.getCustomers())
				{
					updateCustomerCUQtyEntry(profileRecord, customer);
				}
			}
		});
	}

	private void updateCustomerCUQtyEntry(
			@NonNull final I_MobileUI_UserProfile_Picking profileRecord,
			@NonNull final JsonMobileConfigRequest.Picking.Customer customer)
	{
		final BPartnerId customerId = context.getId(customer.getCustomer(), BPartnerId.class);
		CustomerCUQtyEntryWriter.write(profileRecord, customerId, customer.getCuQtyEntryMode(), resolveFormatIdOrNoFormat(customer.getCuLabelFormatName()));
	}

	/** The profile column is mandatory with default MANUAL. */
	@NonNull
	private static String cuQtyEntryModeOrDefault(@Nullable final String cuQtyEntryMode)
	{
		return cuQtyEntryMode != null ? cuQtyEntryMode : X_MobileUI_UserProfile_Picking.CUQTYENTRYMODE_MANUAL;
	}

	/** Returns the format repo id, or 0 (= no format) when the name is absent. */
	private int resolveFormatIdOrNoFormat(@Nullable final String formatName)
	{
		return formatName != null ? resolveFormatId(formatName).getRepoId() : 0;
	}

	/**
	 * Unlinks the profile and job options rows from the CU label formats tagged by {@link CustomQRCodeFormatCommand},
	 * so that these formats can be deleted (and re-created) by the masterdata request.
	 */
	public static void unlinkTaggedCULabelFormats(@NonNull final ScannableCodeFormatService scannableCodeFormatService)
	{
		final ImmutableList<Integer> taggedFormatIds = scannableCodeFormatService.getAll().toList()
				.stream()
				.filter(format -> CustomQRCodeFormatCommand.TAGGING_DESCRIPTION.equals(format.getDescription()))
				.map(format -> format.getId().getRepoId())
				.collect(ImmutableList.toImmutableList());
		if (taggedFormatIds.isEmpty())
		{
			return;
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		for (final I_MobileUI_UserProfile_Picking record : queryBL.createQueryBuilder(I_MobileUI_UserProfile_Picking.class)
				.addInArrayFilter(I_MobileUI_UserProfile_Picking.COLUMNNAME_C_ScannableCode_Format_ID, taggedFormatIds)
				.create()
				.list())
		{
			record.setC_ScannableCode_Format_ID(0);
			InterfaceWrapperHelper.saveRecord(record);
		}
		for (final I_MobileUI_UserProfile_Picking_Job record : queryBL.createQueryBuilder(I_MobileUI_UserProfile_Picking_Job.class)
				.addInArrayFilter(I_MobileUI_UserProfile_Picking_Job.COLUMNNAME_C_ScannableCode_Format_ID, taggedFormatIds)
				.create()
				.list())
		{
			record.setC_ScannableCode_Format_ID(0);
			InterfaceWrapperHelper.saveRecord(record);
		}
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
