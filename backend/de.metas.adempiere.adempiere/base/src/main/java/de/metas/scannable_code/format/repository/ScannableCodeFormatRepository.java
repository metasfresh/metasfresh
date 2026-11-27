package de.metas.scannable_code.format.repository;

import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.error.IErrorManager;
import de.metas.scannable_code.format.ScannableCodeFormat;
import de.metas.scannable_code.format.ScannableCodeFormatCreateRequest;
import de.metas.scannable_code.format.ScannableCodeFormatId;
import de.metas.scannable_code.format.ScannableCodeFormatQuery;
import de.metas.scannable_code.format.ScannableCodeFormatsCollection;
import de.metas.util.Services;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_ScannableCode_Format;
import org.compiere.model.I_C_ScannableCode_Format_Part;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ScannableCodeFormatRepository
{
	@NotNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IErrorManager errorManager;
	@NotNull private final CCache<Integer, ScannableCodeFormatsCollection> cache = CCache.<Integer, ScannableCodeFormatsCollection>builder()
			.tableName(I_C_ScannableCode_Format.Table_Name)
			.additionalTableNameToResetFor(I_C_ScannableCode_Format_Part.Table_Name)
			.build();

	public ScannableCodeFormatRepository()
	{
		this(Services.get(IErrorManager.class));
	}

	public ScannableCodeFormatsCollection getAll()
	{
		return cache.getOrLoad(0, this::retrieveAll);
	}

	public Optional<ScannableCodeFormat> getById(@NonNull final ScannableCodeFormatId id)
	{
		return getAll().getById(id);
	}

	private ScannableCodeFormatsCollection retrieveAll()
	{
		return newLoaderAndSaver().loadAll();
	}

	private ScannableCodeFormatLoaderAndSaver newLoaderAndSaver()
	{
		return ScannableCodeFormatLoaderAndSaver.builder().queryBL(queryBL).errorManager(errorManager).build();
	}

	public ScannableCodeFormat create(@NotNull final ScannableCodeFormatCreateRequest request)
	{
		return newLoaderAndSaver().create(request);
	}

	public int delete(@NotNull final ScannableCodeFormatQuery query)
	{
		final ImmutableSet<ScannableCodeFormatId> formatIds = toSqlQuery(query).create().idsAsSet(ScannableCodeFormatId::ofRepoId);
		if (formatIds.isEmpty())
		{
			return 0;
		}

		queryBL.createQueryBuilder(I_C_ScannableCode_Format_Part.class)
				.addInArrayFilter(I_C_ScannableCode_Format_Part.COLUMNNAME_C_ScannableCode_Format_ID, formatIds)
				.create()
				.delete();

		return queryBL.createQueryBuilder(I_C_ScannableCode_Format.class)
				.addInArrayFilter(I_C_ScannableCode_Format.COLUMNNAME_C_ScannableCode_Format_ID, formatIds)
				.create()
				.delete();
	}

	/**
	 * Deactivates every active format that does not match the given query and returns the IDs of the formats it deactivated (for {@link #activate(Set)}).
	 * Formats are global (a scan is tried against all active ones), so this lets a caller, e.g. a test, work with a known set of active formats.
	 */
	public ImmutableSet<ScannableCodeFormatId> deactivateAllExcept(@NonNull final ScannableCodeFormatQuery keepQuery)
	{
		// the keep query ignores the client (unlike delete): every active format of any client takes part in a scan, like in getAll()
		final IQueryBuilder<I_C_ScannableCode_Format> keepSqlQuery = queryBL.createQueryBuilder(I_C_ScannableCode_Format.class).addOnlyActiveRecordsFilter();
		addQueryFilters(keepSqlQuery, keepQuery);
		final ImmutableSet<ScannableCodeFormatId> keepIds = keepSqlQuery.create().idsAsSet(ScannableCodeFormatId::ofRepoId);

		final ImmutableSet.Builder<ScannableCodeFormatId> deactivatedIds = ImmutableSet.builder();
		try
		{
			queryBL.createQueryBuilder(I_C_ScannableCode_Format.class)
					.addOnlyActiveRecordsFilter()
					.addNotInArrayFilter(I_C_ScannableCode_Format.COLUMNNAME_C_ScannableCode_Format_ID, keepIds) // empty keepIds = all
					.create()
					.stream()
					.forEach(record -> {
						record.setIsActive(false);
						InterfaceWrapperHelper.saveRecord(record);
						deactivatedIds.add(ScannableCodeFormatId.ofRepoId(record.getC_ScannableCode_Format_ID()));
					});
		}
		catch (final RuntimeException e)
		{
			// the caller gets no IDs when this fails, so do not leave a half-done state behind
			try
			{
				activate(deactivatedIds.build());
			}
			catch (final RuntimeException restoreEx)
			{
				e.addSuppressed(restoreEx);
			}
			throw e;
		}
		return deactivatedIds.build();
	}

	/**
	 * Activates the given formats again (inverse of {@link #deactivateAllExcept(ScannableCodeFormatQuery)}).
	 */
	public void activate(@NonNull final Set<ScannableCodeFormatId> formatIds)
	{
		if (formatIds.isEmpty())
		{
			return;
		}

		queryBL.createQueryBuilder(I_C_ScannableCode_Format.class)
				.addInArrayFilter(I_C_ScannableCode_Format.COLUMNNAME_C_ScannableCode_Format_ID, formatIds)
				.create()
				.stream()
				.forEach(record -> {
					record.setIsActive(true);
					InterfaceWrapperHelper.saveRecord(record);
				});
	}

	private IQueryBuilder<I_C_ScannableCode_Format> toSqlQuery(final ScannableCodeFormatQuery query)
	{
		final IQueryBuilder<I_C_ScannableCode_Format> sqlQuery = queryBL.createQueryBuilder(I_C_ScannableCode_Format.class)
				.orderBy(I_C_ScannableCode_Format.COLUMNNAME_C_ScannableCode_Format_ID)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_ScannableCode_Format.COLUMNNAME_AD_Client_ID, ClientId.METASFRESH);

		addQueryFilters(sqlQuery, query);

		return sqlQuery;
	}

	private static void addQueryFilters(@NonNull final IQueryBuilder<I_C_ScannableCode_Format> sqlQuery, @NonNull final ScannableCodeFormatQuery query)
	{
		if (query.getDescription() != null)
		{
			sqlQuery.addEqualsFilter(I_C_ScannableCode_Format.COLUMNNAME_Description, query.getDescription());
		}
	}

	public void deleteParts(@NotNull final ScannableCodeFormatId formatId)
	{
		queryBL.createQueryBuilder(I_C_ScannableCode_Format_Part.class)
				.addEqualsFilter(I_C_ScannableCode_Format_Part.COLUMNNAME_C_ScannableCode_Format_ID, formatId)
				.create()
				.delete();
	}
}
