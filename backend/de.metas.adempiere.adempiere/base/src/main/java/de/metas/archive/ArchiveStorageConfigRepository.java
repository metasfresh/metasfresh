package de.metas.archive;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Archive_Storage;
import org.springframework.stereotype.Repository;

import java.nio.file.Paths;
import java.util.List;

@Repository
public class ArchiveStorageConfigRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, ArchiveStorageConfigMap> cache = CCache.<Integer, ArchiveStorageConfigMap>builder()
			.tableName(I_AD_Archive_Storage.Table_Name)
			.build();

	@VisibleForTesting
	public static ArchiveStorageConfigRepository newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();

		final I_AD_Archive_Storage dbRecord = InterfaceWrapperHelper.newInstance(I_AD_Archive_Storage.class);
		dbRecord.setAD_Archive_Storage_ID(ArchiveStorageConfigId.DATABASE.getRepoId());
		dbRecord.setType(ArchiveStorageType.DATABASE.getCode());
		InterfaceWrapperHelper.save(dbRecord);

		return new ArchiveStorageConfigRepository();
	}

	public ArchiveStorageConfig getById(@NonNull ArchiveStorageConfigId id) {return getMap().getById(id);}

	private ArchiveStorageConfigMap getMap() {return cache.getOrLoad(0, this::retrieveMap);}

	private ArchiveStorageConfigMap retrieveMap()
	{
		return queryBL.createQueryBuilder(I_AD_Archive_Storage.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(ArchiveStorageConfigRepository::fromRecord)
				.collect(GuavaCollectors.collectUsingListAccumulator(ArchiveStorageConfigMap::new));
	}

	private static ArchiveStorageConfig fromRecord(@NonNull final I_AD_Archive_Storage record)
	{
		final ArchiveStorageType type = ArchiveStorageType.ofCode(record.getType());
		final ArchiveStorageConfig.ArchiveStorageConfigBuilder builder = ArchiveStorageConfig.builder()
				.id(ArchiveStorageConfigId.ofRepoId(record.getAD_Archive_Storage_ID()))
				.type(type);

		switch (type)
		{
			case DATABASE -> {}
			case FILE_SYSTEM ->
			{
				final String path = StringUtils.trimBlankToOptional(record.getPath()).orElseThrow(() -> new FillMandatoryException(I_AD_Archive_Storage.COLUMNNAME_Path));
				builder.filesystem(
						ArchiveStorageConfig.Filesystem.builder()
								.path(Paths.get(path))
								.build()
				);
			}
		}

		return builder.build();
	}

	//
	//
	//
	//
	//

	private static class ArchiveStorageConfigMap
	{
		private final ImmutableMap<ArchiveStorageConfigId, ArchiveStorageConfig> byId;

		private ArchiveStorageConfigMap(final List<ArchiveStorageConfig> list)
		{
			byId = Maps.uniqueIndex(list, ArchiveStorageConfig::getId);
		}

		public ArchiveStorageConfig getById(final @NonNull ArchiveStorageConfigId id)
		{
			final ArchiveStorageConfig config = byId.get(id);
			if (config == null)
			{
				throw new AdempiereException("No config found for " + id);
			}
			return config;
		}
	}
}
