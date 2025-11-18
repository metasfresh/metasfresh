package de.metas.distribution.config;

import com.google.common.collect.ImmutableList;
import de.metas.cache.CCache;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_MobileUI_UserProfile_DD;
import org.compiere.model.I_MobileUI_UserProfile_DD_CaptionItem;
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
					CaptionFormatItem.builder().field(DistributionJobField.SourceDoc).build(),
					CaptionFormatItem.builder().field(DistributionJobField.WarehouseFrom).build(),
					CaptionFormatItem.builder().field(DistributionJobField.WarehouseTo).build(),
					CaptionFormatItem.builder().field(DistributionJobField.PickDate).build(),
					CaptionFormatItem.builder().field(DistributionJobField.Plant).build(),
					CaptionFormatItem.builder().field(DistributionJobField.ProductValueAndName).build(),
					CaptionFormatItem.builder().field(DistributionJobField.Qty).build()
			)))
			.build();

	public MobileUIDistributionConfig getConfig()
	{
		return cache.getOrLoad(0, this::retrieveConfig);
	}

	private MobileUIDistributionConfig retrieveConfig()
	{
		final I_MobileUI_UserProfile_DD record = retrieveRecord().orElse(null);
		if (record == null) {return DEFAULT_CONFIG;}

		final CaptionFormat captionFormat = retrieveCaptionFormat(record.getMobileUI_UserProfile_DD_ID()).orElse(DEFAULT_CONFIG.getCaptionFormat());

		return fromRecord(record, captionFormat);
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
				.field(DistributionJobField.ofCode(record.getFieldName()))
				.build();
	}

	private static MobileUIDistributionConfig fromRecord(
			@NonNull final I_MobileUI_UserProfile_DD record,
			@NonNull final CaptionFormat captionFormat)
	{
		return MobileUIDistributionConfig.builder()
				.allowPickingAnyHU(record.isAllowPickingAnyHU())
				.captionFormat(captionFormat)
				.build();
	}

	public void save(final MobileUIDistributionConfig newConfig)
	{
		final I_MobileUI_UserProfile_DD record = retrieveRecord()
				.orElseGet(() -> InterfaceWrapperHelper.newInstance(I_MobileUI_UserProfile_DD.class));

		record.setIsAllowPickingAnyHU(newConfig.isAllowPickingAnyHU());
		record.setIsActive(true);

		InterfaceWrapperHelper.save(record);
	}
}
