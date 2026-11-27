package de.metas.frontend_testing.masterdata.mobile_configuration;

import de.metas.handlingunits.picking.config.mobileui.CUQtyEntryMode;
import de.metas.handlingunits.picking.config.mobileui.PickToStructure;
import de.metas.handlingunits.picking.config.mobileui.PickingJobOptions;
import de.metas.handlingunits.picking.config.mobileui.PickingJobOptionsId;
import de.metas.handlingunits.picking.config.mobileui.PickingLineGroupBy;
import de.metas.handlingunits.picking.config.mobileui.PickingLineSortBy;
import de.metas.scannable_code.format.ScannableCodeFormatId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.compiere.model.I_MobileUI_UserProfile_Picking;
import org.compiere.model.I_MobileUI_UserProfile_Picking_BPartner;
import org.compiere.model.I_MobileUI_UserProfile_Picking_Job;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/** Masterdata writes of the mobile UI frontend tests on the picking job options rows and the profile's CU label format. Only the frontend tests change these rows this way. */
public final class PickingJobOptionsWriter
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/** Creates a picking job options row from the given options (pick attributes and {@code completeJobAutomatically} are not stored on it). */
	@NonNull
	public PickingJobOptionsId createPickingJobOptions(@NonNull final String name, @NonNull final PickingJobOptions from)
	{
		final I_MobileUI_UserProfile_Picking_Job record = InterfaceWrapperHelper.newInstance(I_MobileUI_UserProfile_Picking_Job.class);
		record.setName(name);
		record.setPickingJobAggregationType(from.getAggregationType() != null ? from.getAggregationType().getCode() : null);
		record.setIsAllowPickingAnyHU(from.isAllowPickingAnyHU());
		record.setIsAlwaysSplitHUsEnabled(from.isAlwaysSplitHUsEnabled());
		record.setIsShipOnCloseLU(from.isShipOnCloseLU());
		record.setAllowPickToStructure_LU_TU(StringUtils.ofBoolean(from.isStrictlyAllowed(PickToStructure.LU_TU)));
		record.setAllowPickToStructure_TU(StringUtils.ofBoolean(from.isStrictlyAllowed(PickToStructure.TU)));
		record.setAllowPickToStructure_LU_CU(StringUtils.ofBoolean(from.isStrictlyAllowed(PickToStructure.LU_CU)));
		record.setAllowPickToStructure_CU(StringUtils.ofBoolean(from.isStrictlyAllowed(PickToStructure.CU)));
		record.setIsCatchWeightTUPickingEnabled(from.isCatchWeightTUPickingEnabled());
		record.setIsConsiderSalesOrderCapacity(from.isConsiderSalesOrderCapacity());
		record.setIsAllowSkippingRejectedReason(from.isAllowSkippingRejectedReason());
		record.setIsShowConfirmationPromptWhenOverPick(from.isShowConfirmationPromptWhenOverPick());
		record.setIsAllowCompletingPartialPickingJob(from.isAllowCompletingPartialPickingJob());
		record.setIsShowLastPickedBestBeforeDateForLines(from.isShowLastPickedBestBeforeDateForLines());
		record.setIsAnonymousHuPickedOnTheFly(from.isAnonymousPickHUsOnTheFly());
		record.setIsDisplayPickingSlotSuggestions(from.getDisplayPickingSlotSuggestions().toBooleanString());
		record.setCreateShipmentPolicy(from.getCreateShipmentPolicy().getCode());
		record.setPickingLineGroupBy(from.getPickingLineGroupBy().map(PickingLineGroupBy::getCode).orElse(null));
		record.setPickingLineSortBy(from.getPickingLineSortBy().map(PickingLineSortBy::getCode).orElse(null));
		record.setCUQtyEntryMode(from.getCUQtyEntryMode().map(CUQtyEntryMode::getCode).orElse(null)); // empty = use the profile's
		record.setC_ScannableCode_Format_ID(ScannableCodeFormatId.toRepoId(from.getCULabelFormatId().orElse(null)));
		InterfaceWrapperHelper.saveRecord(record);
		return PickingJobOptionsId.ofRepoId(record.getMobileUI_UserProfile_Picking_Job_ID());
	}

	public void updatePickingJobOptionsCUQtyEntry(
			@NonNull final PickingJobOptionsId id,
			@Nullable final CUQtyEntryMode mode,
			@Nullable final ScannableCodeFormatId formatId)
	{
		final I_MobileUI_UserProfile_Picking_Job record = InterfaceWrapperHelper.load(id, I_MobileUI_UserProfile_Picking_Job.class);
		record.setCUQtyEntryMode(mode != null ? mode.getCode() : null);
		record.setC_ScannableCode_Format_ID(ScannableCodeFormatId.toRepoId(formatId));
		InterfaceWrapperHelper.saveRecord(record);
	}

	public void unlinkCULabelFormats(@NonNull final Collection<ScannableCodeFormatId> formatIds)
	{
		if (formatIds.isEmpty())
		{
			return;
		}
		final List<Integer> formatRepoIds = formatIds.stream().map(ScannableCodeFormatId::getRepoId).collect(Collectors.toList());

		clearCULabelFormat(I_MobileUI_UserProfile_Picking.class, I_MobileUI_UserProfile_Picking.COLUMNNAME_C_ScannableCode_Format_ID, formatRepoIds);
		clearCULabelFormat(I_MobileUI_UserProfile_Picking_Job.class, I_MobileUI_UserProfile_Picking_Job.COLUMNNAME_C_ScannableCode_Format_ID, formatRepoIds);
	}

	private <T> void clearCULabelFormat(@NonNull final Class<T> recordClass, @NonNull final String formatColumnName, @NonNull final List<Integer> formatRepoIds)
	{
		for (final T record : queryBL.createQueryBuilder(recordClass)
				.addInArrayFilter(formatColumnName, formatRepoIds)
				.create()
				.list())
		{
			InterfaceWrapperHelper.setValue(record, formatColumnName, null);
			InterfaceWrapperHelper.saveRecord(record);
		}
	}

	/** Deletes the picking job options rows with the given name prefix that no customer row references. */
	public void deleteUnusedPickingJobOptions(@NonNull final String namePrefix)
	{
		final IQuery<I_MobileUI_UserProfile_Picking_BPartner> usedRefs = queryBL.createQueryBuilder(I_MobileUI_UserProfile_Picking_BPartner.class)
				.addNotNull(I_MobileUI_UserProfile_Picking_BPartner.COLUMNNAME_MobileUI_UserProfile_Picking_Job_ID)
				.create();

		final List<I_MobileUI_UserProfile_Picking_Job> unused = queryBL.createQueryBuilder(I_MobileUI_UserProfile_Picking_Job.class)
				.addNotInSubQueryFilter(I_MobileUI_UserProfile_Picking_Job.COLUMNNAME_MobileUI_UserProfile_Picking_Job_ID, I_MobileUI_UserProfile_Picking_BPartner.COLUMNNAME_MobileUI_UserProfile_Picking_Job_ID, usedRefs)
				.addStringStartsWith(I_MobileUI_UserProfile_Picking_Job.COLUMNNAME_Name, namePrefix)
				.create()
				.list();
		InterfaceWrapperHelper.deleteAll(unused);
	}
}
