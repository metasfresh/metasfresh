package de.metas.distribution.config;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import de.metas.cache.CCache;
import de.metas.util.Services;
import de.metas.util.lang.SeqNoProvider;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_MobileUI_UserProfile_DD;
import org.compiere.model.I_MobileUI_UserProfile_DD_CaptionItem;
import org.compiere.model.I_MobileUI_UserProfile_DD_Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class MobileUIDistributionConfigRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, MobileUIDistributionConfig> cache = CCache.<Integer, MobileUIDistributionConfig>builder()
			.tableName(I_MobileUI_UserProfile_DD.Table_Name)
			.build();

	private static final MobileUIDistributionConfig DEFAULT_CONFIG = MobileUIDistributionConfig.builder()
			.allowPickingAnyHU(false)
			.captionFormat(DistributionJobCaptionFormat.ofNonEmptyList(ImmutableList.of(
					DistributionJobCaptionFormatItem.builder().field(DistributionJobCaptionField.SourceDoc).build(),
					DistributionJobCaptionFormatItem.builder().field(DistributionJobCaptionField.WarehouseFrom).build(),
					DistributionJobCaptionFormatItem.builder().field(DistributionJobCaptionField.WarehouseTo).build(),
					DistributionJobCaptionFormatItem.builder().field(DistributionJobCaptionField.PickDate).build(),
					DistributionJobCaptionFormatItem.builder().field(DistributionJobCaptionField.Plant).build(),
					DistributionJobCaptionFormatItem.builder().field(DistributionJobCaptionField.ProductValueAndName).build(),
					DistributionJobCaptionFormatItem.builder().field(DistributionJobCaptionField.Qty).build()
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

	private Optional<DistributionJobCaptionFormat> retrieveCaptionFormat(final int MobileUI_UserProfile_DD_ID)
	{
		return retrieveCaptionFormatItemRecords(MobileUI_UserProfile_DD_ID)
				.map(MobileUIDistributionConfigRepository::fromRecord)
				.collect(DistributionJobCaptionFormat.collect());
	}

	private Stream<I_MobileUI_UserProfile_DD_CaptionItem> retrieveCaptionFormatItemRecords(final int MobileUI_UserProfile_DD_ID)
	{
		return queryBL.createQueryBuilder(I_MobileUI_UserProfile_DD_CaptionItem.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MobileUI_UserProfile_DD_CaptionItem.COLUMNNAME_MobileUI_UserProfile_DD_ID, MobileUI_UserProfile_DD_ID)
				.orderBy(I_MobileUI_UserProfile_DD_CaptionItem.COLUMNNAME_SeqNo)
				.stream();
	}

	private static DistributionJobCaptionFormatItem fromRecord(I_MobileUI_UserProfile_DD_CaptionItem record)
	{
		return DistributionJobCaptionFormatItem.builder()
				.field(extractField(record))
				.build();
	}

	private static @NonNull DistributionJobCaptionField extractField(final I_MobileUI_UserProfile_DD_CaptionItem record)
	{
		return DistributionJobCaptionField.ofCode(record.getFieldName());
	}

	private Optional<DistributionJobSorting> retrieveSorting(final int MobileUI_UserProfile_DD_ID)
	{
		return retrieveSortingRecords(MobileUI_UserProfile_DD_ID)
				.map(MobileUIDistributionConfigRepository::fromRecord)
				.collect(DistributionJobSorting.collect());
	}

	private Stream<I_MobileUI_UserProfile_DD_Sort> retrieveSortingRecords(final int MobileUI_UserProfile_DD_ID)
	{
		return queryBL.createQueryBuilder(I_MobileUI_UserProfile_DD_Sort.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MobileUI_UserProfile_DD_Sort.COLUMNNAME_MobileUI_UserProfile_DD_ID, MobileUI_UserProfile_DD_ID)
				.orderBy(I_MobileUI_UserProfile_DD_Sort.COLUMNNAME_SeqNo)
				.orderBy(I_MobileUI_UserProfile_DD_Sort.COLUMNNAME_MobileUI_UserProfile_DD_Sort_ID)
				.stream();
	}

	private static DistributionJobSortingItem fromRecord(I_MobileUI_UserProfile_DD_Sort record)
	{
		return DistributionJobSortingItem.of(
				extractField(record),
				Direction.ofIsAscendingFlag(record.isAscending())
		);
	}

	@NonNull
	private static DistributionJobSortingField extractField(final I_MobileUI_UserProfile_DD_Sort record)
	{
		return DistributionJobSortingField.ofCode(record.getFieldName());
	}

	public void save(final MobileUIDistributionConfig newConfig)
	{
		//
		// Config Header
		final int configId;
		{
			final I_MobileUI_UserProfile_DD configRecord = retrieveRecord()
					.orElseGet(() -> InterfaceWrapperHelper.newInstance(I_MobileUI_UserProfile_DD.class));
			updateRecord(configRecord, newConfig);
			InterfaceWrapperHelper.save(configRecord);
			configId = configRecord.getMobileUI_UserProfile_DD_ID();
		}

		//
		// Caption Format
		{
			final ArrayListMultimap<DistributionJobCaptionField, I_MobileUI_UserProfile_DD_CaptionItem> records = ArrayListMultimap.create();
			retrieveCaptionFormatItemRecords(configId).forEach(record -> records.put(extractField(record), record));

			final SeqNoProvider seqNoProvider = SeqNoProvider.ofInt(10);
			newConfig.getCaptionFormat().getItems().forEach(item -> {
				final List<I_MobileUI_UserProfile_DD_CaptionItem> recordsOfField = records.get(item.getField());
				final I_MobileUI_UserProfile_DD_CaptionItem record;
				if (!recordsOfField.isEmpty())
				{
					record = recordsOfField.remove(0);
				}
				else
				{
					record = InterfaceWrapperHelper.newInstance(I_MobileUI_UserProfile_DD_CaptionItem.class);
					record.setMobileUI_UserProfile_DD_ID(configId);
				}

				record.setIsActive(true);
				record.setFieldName(item.getField().getCode());
				record.setSeqNo(seqNoProvider.getAndIncrement().toInt());

				InterfaceWrapperHelper.save(record);
			});

			InterfaceWrapperHelper.deleteAll(records.values());
		}

		//
		// Sorting
		{
			final ArrayListMultimap<DistributionJobSortingField, I_MobileUI_UserProfile_DD_Sort> records = ArrayListMultimap.create();
			retrieveSortingRecords(configId).forEach(record -> records.put(extractField(record), record));

			final SeqNoProvider seqNoProvider = SeqNoProvider.ofInt(10);
			newConfig.getSorting().getItems().forEach(item -> {
				final List<I_MobileUI_UserProfile_DD_Sort> recordsOfField = records.get(item.getField());
				final I_MobileUI_UserProfile_DD_Sort record;
				if (!recordsOfField.isEmpty())
				{
					record = recordsOfField.remove(0);
				}
				else
				{
					record = InterfaceWrapperHelper.newInstance(I_MobileUI_UserProfile_DD_Sort.class);
					record.setMobileUI_UserProfile_DD_ID(configId);
				}

				record.setIsActive(true);
				record.setFieldName(item.getField().getCode());
				record.setIsAscending(item.getDirection().isAscending());
				record.setSeqNo(seqNoProvider.getAndIncrement().toInt());

				InterfaceWrapperHelper.save(record);
			});

			InterfaceWrapperHelper.deleteAll(records.values());
		}

	}

	private static void updateRecord(final I_MobileUI_UserProfile_DD record, final MobileUIDistributionConfig from)
	{
		record.setIsActive(true);
		record.setIsAllowPickingAnyHU(from.isAllowPickingAnyHU());

		record.setMaxLaunchers(from.getMaxLaunchers().toIntOrZero());
		record.setMaxStartedLaunchers(from.getMaxStartedLaunchers().toIntOrZero());
		record.setIsAllowStartNextJobOnly(from.isAllowStartNextJobOnly());
	}
}
