package de.metas.payment.sumup.repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.payment.sumup.SumUpCardReader;
import de.metas.payment.sumup.SumUpCardReaderExternalId;
import de.metas.payment.sumup.SumUpConfig;
import de.metas.payment.sumup.SumUpConfigId;
import de.metas.payment.sumup.repository.model.I_SUMUP_CardReader;
import de.metas.payment.sumup.repository.model.I_SUMUP_Config;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class SumUpConfigRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, SumUpConfigMap> cache = CCache.<Integer, SumUpConfigMap>builder()
			.tableName(I_SUMUP_Config.Table_Name)
			.additionalTableNameToResetFor(I_SUMUP_CardReader.Table_Name)
			.build();

	public SumUpConfig get()
	{
		return getMap().getDefault();
	}

	public SumUpConfig getById(@NonNull SumUpConfigId id)
	{
		return getMap().getById(id);
	}

	private SumUpConfigMap getMap()
	{
		return cache.getOrLoad(0, this::retrieveConfigMap);
	}

	private SumUpConfigMap retrieveConfigMap()
	{
		final List<I_SUMUP_Config> configRecords = retrieveConfigRecords();
		if (configRecords.isEmpty())
		{
			return SumUpConfigMap.EMPTY;
		}

		final Set<SumUpConfigId> configIds = configRecords.stream().map(SumUpConfigRepository::extractConfigId).collect(Collectors.toSet());
		final ImmutableListMultimap<SumUpConfigId, I_SUMUP_CardReader> cardReaderRecordsByConfigId = streamCardReaderRecords(configIds)
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						SumUpConfigRepository::extractConfigId,
						cardReaderRecord -> cardReaderRecord
				));

		return configRecords.stream()
				.map(configRecord -> fromRecord(configRecord, cardReaderRecordsByConfigId.get(extractConfigId(configRecord))))
				.collect(GuavaCollectors.collectUsingListAccumulator(SumUpConfigMap::new));
	}

	private static @NonNull SumUpConfigId extractConfigId(final I_SUMUP_Config configRecord)
	{
		return SumUpConfigId.ofRepoId(configRecord.getSUMUP_Config_ID());
	}

	private static @NonNull SumUpConfigId extractConfigId(final I_SUMUP_CardReader cardReaderRecord)
	{
		return SumUpConfigId.ofRepoId(cardReaderRecord.getSUMUP_Config_ID());
	}

	private @NonNull List<I_SUMUP_Config> retrieveConfigRecords()
	{
		return queryBL.createQueryBuilder(I_SUMUP_Config.class)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_SUMUP_Config.COLUMN_SUMUP_Config_ID)
				.create()
				.list();
	}

	private Stream<I_SUMUP_CardReader> streamCardReaderRecords(final Collection<SumUpConfigId> configIds)
	{
		if (configIds.isEmpty())
		{
			return Stream.empty();
		}

		return queryBL.createQueryBuilder(I_SUMUP_CardReader.class)
				.addInArrayFilter(I_SUMUP_CardReader.COLUMNNAME_SUMUP_Config_ID, configIds)
				.orderBy(I_SUMUP_CardReader.COLUMNNAME_SUMUP_CardReader_ID)
				.create()
				.stream();
	}

	public void save(final SumUpConfig config)
	{
		updateById(config.getId(), prevConfig -> config);
	}

	public SumUpConfig updateById(@NonNull final SumUpConfigId configId, @NonNull UnaryOperator<SumUpConfig> updater)
	{
		final I_SUMUP_Config configRecord = InterfaceWrapperHelper.load(configId, I_SUMUP_Config.class);
		final HashMap<SumUpCardReaderExternalId, I_SUMUP_CardReader> cardReaderRecordsByExternalId = streamCardReaderRecords(ImmutableSet.of(configId))
				.collect(GuavaCollectors.toHashMapByKey(cardReaderRecord -> SumUpCardReaderExternalId.ofString(cardReaderRecord.getExternalId())));

		final SumUpConfig configBeforeChanges = fromRecord(configRecord, cardReaderRecordsByExternalId.values());
		final SumUpConfig config = updater.apply(configBeforeChanges);
		if (Objects.equals(configBeforeChanges, config))
		{
			return configBeforeChanges;
		}

		updateRecord(configRecord, config);

		final HashMap<SumUpCardReaderExternalId, Integer> cardReaderRepoIdsByExternalId = new HashMap<>();
		for (final SumUpCardReader cardReader : config.getCardReaders())
		{
			I_SUMUP_CardReader cardReaderRecord = cardReaderRecordsByExternalId.remove(cardReader.getExternalId());
			if (cardReaderRecord == null)
			{
				cardReaderRecord = InterfaceWrapperHelper.newInstance(I_SUMUP_CardReader.class);
			}

			updateRecord(cardReaderRecord, cardReader);
			cardReaderRecord.setSUMUP_Config_ID(configRecord.getSUMUP_Config_ID());
			cardReaderRecord.setAD_Org_ID(configRecord.getAD_Org_ID());
			InterfaceWrapperHelper.save(cardReaderRecord);
			cardReaderRepoIdsByExternalId.put(SumUpCardReaderExternalId.ofString(cardReaderRecord.getExternalId()), cardReaderRecord.getSUMUP_CardReader_ID());
		}

		configRecord.setSUMUP_CardReader_ID(getCardReaderRepoId(config.getDefaultCardReaderExternalId(), cardReaderRepoIdsByExternalId));

		InterfaceWrapperHelper.deleteAll(cardReaderRecordsByExternalId.values());
		InterfaceWrapperHelper.save(configRecord);

		return config;
	}

	private static int getCardReaderRepoId(@Nullable final SumUpCardReaderExternalId cardReaderExternalId, @NonNull final Map<SumUpCardReaderExternalId, Integer> cardReaderRepoIdsByExternalId)
	{
		if (cardReaderExternalId == null)
		{
			return -1;
		}
		else
		{
			final Integer repoId = cardReaderRepoIdsByExternalId.get(cardReaderExternalId);
			return repoId != null && repoId > 0 ? repoId : -1;
		}
	}

	private static SumUpConfig fromRecord(final I_SUMUP_Config configRecord, final Collection<I_SUMUP_CardReader> cardReaderRecords)
	{
		final ArrayList<SumUpCardReader> cardReaders = new ArrayList<>();
		SumUpCardReaderExternalId defaultCardReaderExternalId = null;
		for (final I_SUMUP_CardReader cardReaderRecord : cardReaderRecords)
		{
			final SumUpCardReader cardReader = fromRecord(cardReaderRecord);
			cardReaders.add(cardReader);

			if (cardReaderRecord.getSUMUP_CardReader_ID() == configRecord.getSUMUP_CardReader_ID())
			{
				defaultCardReaderExternalId = cardReader.getExternalId();
			}
		}

		if (defaultCardReaderExternalId == null && !cardReaders.isEmpty())
		{
			final List<SumUpCardReader> activeCardReaders = cardReaders.stream()
					.filter(SumUpCardReader::isActive)
					.collect(Collectors.toList());
			if (activeCardReaders.size() == 1)
			{
				defaultCardReaderExternalId = activeCardReaders.get(0).getExternalId();
			}
		}

		return SumUpConfig.builder()
				.id(extractConfigId(configRecord))
				.isActive(configRecord.isActive())
				.apiKey(configRecord.getApiKey())
				.merchantCode(configRecord.getSUMUP_merchant_code())
				.defaultCardReaderExternalId(defaultCardReaderExternalId)
				.cardReaders(ImmutableList.copyOf(cardReaders))
				.build();
	}

	private static void updateRecord(final I_SUMUP_Config record, SumUpConfig from)
	{
		record.setIsActive(from.isActive());
		record.setApiKey(from.getApiKey());
		record.setSUMUP_merchant_code(from.getMerchantCode());
	}

	private static SumUpCardReader fromRecord(final I_SUMUP_CardReader record)
	{
		return SumUpCardReader.builder()
				.name(record.getName())
				.externalId(SumUpCardReaderExternalId.ofString(record.getExternalId()))
				.isActive(record.isActive())
				.build();
	}

	private static void updateRecord(final I_SUMUP_CardReader record, final SumUpCardReader from)
	{
		record.setName(from.getName());
		record.setExternalId(from.getExternalId().getAsString());
		record.setIsActive(from.isActive());
	}

	//
	//
	//

	private static class SumUpConfigMap
	{
		public static SumUpConfigMap EMPTY = new SumUpConfigMap(ImmutableList.of());

		private final ImmutableMap<SumUpConfigId, SumUpConfig> byId;
		private final SumUpConfig defaultConfig;

		private SumUpConfigMap(final List<SumUpConfig> list)
		{
			this.byId = Maps.uniqueIndex(list, SumUpConfig::getId);

			final List<SumUpConfig> activeConfigs = list.stream()
					.filter(SumUpConfig::isActive)
					.collect(ImmutableList.toImmutableList());
			this.defaultConfig = activeConfigs.size() == 1 ? activeConfigs.get(0) : null;
		}

		public SumUpConfig getById(final @NonNull SumUpConfigId id)
		{
			final SumUpConfig config = byId.get(id);
			if (config == null)
			{
				throw new AdempiereException("No SumUp config found for " + id);
			}
			return config;
		}

		public SumUpConfig getDefault()
		{
			if (defaultConfig == null)
			{
				throw new AdempiereException("No default SumUp config found");
			}
			return defaultConfig;
		}
	}
}
