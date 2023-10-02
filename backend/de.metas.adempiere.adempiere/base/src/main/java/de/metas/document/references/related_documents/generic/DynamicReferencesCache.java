package de.metas.document.references.related_documents.generic;

import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.cache.CacheMgt;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.util.OptionalBoolean;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.compiere.model.I_AD_Issue;
import org.compiere.model.POInfo;
import org.compiere.util.DB;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Set;

@Service
class DynamicReferencesCache
{
	private static final ImmutableSet<String> TABLENAMES_TO_AVOID_CACHING = ImmutableSet.<String>builder()
			.add(I_AD_Issue.Table_Name) // because it's often written => often cache reset
			.build();

	private final CCache<String, TableInfo> byTableName = CCache.<String, TableInfo>builder()
			.initialCapacity(100)
			.build();

	@PostConstruct
	public void postConstruct()
	{
		CacheMgt.get().addCacheResetListener(this::onCacheReset);
	}

	private long onCacheReset(@NonNull final CacheInvalidateMultiRequest multiRequest)
	{
		if (multiRequest.isResetAll())
		{
			return byTableName.reset();
		}
		else
		{
			final Set<String> tableNamesEffective = multiRequest.getTableNamesEffective();
			byTableName.removeAll(tableNamesEffective);
			return tableNamesEffective.size();
		}
	}

	public OptionalBoolean hasReferences(@NonNull final String tableName, @NonNull final String referencedTableName)
	{
		if (TABLENAMES_TO_AVOID_CACHING.contains(tableName))
		{
			return OptionalBoolean.UNKNOWN;
		}

		return OptionalBoolean.ofBoolean(getTableInfo(tableName).containsReferencedTableName(referencedTableName));
	}

	private TableInfo getTableInfo(@NonNull final String tableName)
	{
		return byTableName.getOrLoad(tableName, this::retrieveTableInfo);
	}

	private TableInfo retrieveTableInfo(@NonNull final String tableName)
	{
		return TableInfo.builder()
				.tableName(tableName)
				.referencedTableNames(retrieveReferencedTableNames(tableName))
				.build();
	}

	private static ImmutableSet<String> retrieveReferencedTableNames(final @NonNull String tableName)
	{
		if (!hasDynamicReference(tableName))
		{
			return ImmutableSet.of();
		}

		return DB.retrieveUniqueRows(
				"SELECT DISTINCT t.TableName FROM " + tableName + " d "
						+ " INNER JOIN AD_Table t on t.AD_Table_ID=d.AD_Table_ID"
						+ " WHERE d.AD_Table_ID is not null"
						+ " ORDER BY t.TableName",
				null,
				rs -> rs.getString(1)
		);
	}

	private static boolean hasDynamicReference(final @NonNull String tableName)
	{
		final POInfo poInfo = POInfo.getPOInfoNotNull(tableName);
		return poInfo.hasColumnName("AD_Table_ID")
				&& !poInfo.isVirtualColumn("AD_Table_ID")
				&& poInfo.hasColumnName("Record_ID");
	}

	@Value
	@Builder
	private static class TableInfo
	{
		@NonNull String tableName;
		@NonNull ImmutableSet<String> referencedTableNames;

		public boolean containsReferencedTableName(@NonNull final String referencedTableName) {return referencedTableNames.contains(referencedTableName);}
	}
}
