package de.metas.distribution.config;

import com.google.common.collect.ImmutableList;
import de.metas.cache.CCache;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_MobileUI_UserProfile_DD;
import org.compiere.model.I_MobileUI_UserProfile_DD_CaptionItem;
import org.compiere.model.I_MobileUI_UserProfile_DD_Sort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MobileUIDistributionConfigRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, MobileUIDistributionConfig> cache = CCache.<Integer, MobileUIDistributionConfig>builder()
			.tableName(I_MobileUI_UserProfile_DD.Table_Name)
			.build();

	private static final MobileUIDistributionConfig DEFAULT_CONFIG = MobileUIDistributionConfig.builder()
			.allowPickingAnyHU(false)
			.captionFormat(CaptionFormat.ofNonEmptyList(ImmutableList.of(
					CaptionFormatItem.builder().field(DistributionJobCaptionField.SourceDoc).build(),
					CaptionFormatItem.builder().field(DistributionJobCaptionField.WarehouseFrom).build(),
					CaptionFormatItem.builder().field(DistributionJobCaptionField.WarehouseTo).build(),
					CaptionFormatItem.builder().field(DistributionJobCaptionField.PickDate).build(),
					CaptionFormatItem.builder().field(DistributionJobCaptionField.Plant).build(),
					CaptionFormatItem.builder().field(DistributionJobCaptionField.ProductValueAndName).build(),
					CaptionFormatItem.builder().field(DistributionJobCaptionField.Qty).build()
			)))
			.sorting(DistributionJobSorting.DEFAULT)
			.build();

	public MobileUIDistributionConfig getConfig()
	{
		return cache.getOrLoad(0, this::retrieveConfig);
	}

	private MobileUIDistributionConfig retrieveConfig()
	{
		final I_MobileUI_UserProfile_DD record = retrieveRecord().orElse(null);
		if (record == null) {return DEFAULT_CONFIG;}

		return MobileUIDistributionConfig.builder()
				.allowPickingAnyHU(record.isAllowPickingAnyHU())
				.captionFormat(retrieveCaptionFormat(record.getMobileUI_UserProfile_DD_ID()).orElse(DEFAULT_CONFIG.getCaptionFormat()))
				.sorting(retrieveSorting(record.getMobileUI_UserProfile_DD_ID()).orElse(DEFAULT_CONFIG.getSorting()))
				.maxLaunchers(QueryLimit.ofInt(record.getMaxLaunchers()))
				.maxStartedLaunchers(QueryLimit.ofInt(record.getMaxStartedLaunchers()))
				.isAllowStartNextJobOnly(record.isAllowStartNextJobOnly())
				.build();
	}

	private Optional<I_MobileUI_UserProfile_DD> retrieveRecord()
	{
		return queryBL.createQueryBuilder(I_MobileUI_UserProfile_DD.class)
				.addOnlyActiveRecordsFilter()
				.firstOnlyOptional();
	}

	private Optional<CaptionFormat> retrieveCaptionFormat(final int MobileUI_UserProfile_DD_ID)
	{
		return queryBL.createQueryBuilder(I_MobileUI_UserProfile_DD_CaptionItem.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MobileUI_UserProfile_DD_CaptionItem.COLUMNNAME_MobileUI_UserProfile_DD_ID, MobileUI_UserProfile_DD_ID)
				.orderBy(I_MobileUI_UserProfile_DD_CaptionItem.COLUMNNAME_SeqNo)
				.stream()
				.map(MobileUIDistributionConfigRepository::fromRecord)
				.collect(CaptionFormat.collect());
	}

	private static CaptionFormatItem fromRecord(I_MobileUI_UserProfile_DD_CaptionItem record)
	{
		return CaptionFormatItem.builder()
				.field(DistributionJobCaptionField.ofCode(record.getFieldName()))
				.build();
	}

	private Optional<DistributionJobSorting> retrieveSorting(final int MobileUI_UserProfile_DD_ID)
	{
		return queryBL.createQueryBuilder(I_MobileUI_UserProfile_DD_Sort.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MobileUI_UserProfile_DD_Sort.COLUMNNAME_MobileUI_UserProfile_DD_ID, MobileUI_UserProfile_DD_ID)
				.orderBy(I_MobileUI_UserProfile_DD_Sort.COLUMNNAME_SeqNo)
				.orderBy(I_MobileUI_UserProfile_DD_Sort.COLUMNNAME_MobileUI_UserProfile_DD_Sort_ID)
				.stream()
				.map(MobileUIDistributionConfigRepository::fromRecord)
				.collect(DistributionJobSorting.collect());
	}

	private static DistributionJobSortingItem fromRecord(I_MobileUI_UserProfile_DD_Sort record)
	{
		return DistributionJobSortingItem.of(
				DistributionJobSortingField.ofCode(record.getFieldName()),
				Direction.ofIsAscendingFlag(record.isAscending())
		);
	}

	public void save(final MobileUIDistributionConfig newConfig)
	{
		final I_MobileUI_UserProfile_DD record = retrieveRecord()
				.orElseGet(() -> InterfaceWrapperHelper.newInstance(I_MobileUI_UserProfile_DD.class));

		record.setIsActive(true);
		record.setIsAllowPickingAnyHU(newConfig.isAllowPickingAnyHU());

		record.setMaxLaunchers(newConfig.getMaxLaunchers().toIntOrZero());
		record.setMaxStartedLaunchers(newConfig.getMaxStartedLaunchers().toIntOrZero());
		record.setIsAllowStartNextJobOnly(newConfig.isAllowStartNextJobOnly());

		InterfaceWrapperHelper.save(record);
	}
}
