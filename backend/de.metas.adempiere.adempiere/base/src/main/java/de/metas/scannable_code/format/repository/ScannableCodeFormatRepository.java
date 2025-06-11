package de.metas.scannable_code.format.repository;

import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.scannable_code.format.ScannableCodeFormat;
import de.metas.scannable_code.format.ScannableCodeFormatCreateRequest;
import de.metas.scannable_code.format.ScannableCodeFormatId;
import de.metas.scannable_code.format.ScannableCodeFormatQuery;
import de.metas.scannable_code.format.ScannableCodeFormatsCollection;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_ScannableCode_Format;
import org.compiere.model.I_C_ScannableCode_Format_Part;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

@Repository
public class ScannableCodeFormatRepository
{
	@NotNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NotNull private final CCache<Integer, ScannableCodeFormatsCollection> cache = CCache.<Integer, ScannableCodeFormatsCollection>builder()
			.tableName(I_C_ScannableCode_Format.Table_Name)
			.additionalTableNameToResetFor(I_C_ScannableCode_Format_Part.Table_Name)
			.build();

	public ScannableCodeFormatsCollection getAll()
	{
		return cache.getOrLoad(0, this::retrieveAll);
	}

	private ScannableCodeFormatsCollection retrieveAll()
	{
		return newLoaderAndSaver().loadAll();
	}

	private ScannableCodeFormatLoaderAndSaver newLoaderAndSaver()
	{
		return ScannableCodeFormatLoaderAndSaver.builder().queryBL(queryBL).build();
	}

	public ScannableCodeFormat create(@NotNull final ScannableCodeFormatCreateRequest request)
	{
		return newLoaderAndSaver().create(request);
	}

	public int delete(@NotNull final ScannableCodeFormatQuery query)
	{
		final ImmutableSet<ScannableCodeFormatId> formatIds = toSqlQuery(query).create().listIds(ScannableCodeFormatId::ofRepoId);
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

	private IQueryBuilder<I_C_ScannableCode_Format> toSqlQuery(final ScannableCodeFormatQuery query)
	{
		final IQueryBuilder<I_C_ScannableCode_Format> sqlQuery = queryBL.createQueryBuilder(I_C_ScannableCode_Format.class)
				.orderBy(I_C_ScannableCode_Format.COLUMNNAME_C_ScannableCode_Format_ID)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_ScannableCode_Format.COLUMNNAME_AD_Client_ID, ClientId.METASFRESH);

		if (query.getDescription() != null)
		{
			sqlQuery.addEqualsFilter(I_C_ScannableCode_Format.COLUMNNAME_Description, query.getDescription());
		}

		return sqlQuery;
	}
}
