package de.metas.scannable_code.format.repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.scannable_code.format.ScannableCodeFormat;
import de.metas.scannable_code.format.ScannableCodeFormatId;
import de.metas.scannable_code.format.ScannableCodeFormatPart;
import de.metas.scannable_code.format.ScannableCodeFormatPartType;
import de.metas.scannable_code.format.ScannableCodeFormatsCollection;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_ScannableCode_Format;
import org.compiere.model.I_C_ScannableCode_Format_Part;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import java.time.format.DateTimeFormatter;
import java.util.List;

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
		final ImmutableMap<ScannableCodeFormatId, I_C_ScannableCode_Format> formatRecords = queryBL.createQueryBuilder(I_C_ScannableCode_Format.class)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_C_ScannableCode_Format.COLUMNNAME_C_ScannableCode_Format_ID)
				.create()
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						ScannableCodeFormatRepository::extactScannableCodeFormatId,
						record -> record)
				);

		if (formatRecords.isEmpty())
		{
			return ScannableCodeFormatsCollection.EMPTY;
		}

		final ImmutableSet<ScannableCodeFormatId> formatIds = formatRecords.keySet();
		final ImmutableListMultimap<ScannableCodeFormatId, I_C_ScannableCode_Format_Part> partRecords = queryBL.createQueryBuilder(I_C_ScannableCode_Format_Part.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_ScannableCode_Format_Part.COLUMNNAME_C_ScannableCode_Format_ID, formatIds)
				.orderBy(I_C_ScannableCode_Format_Part.COLUMNNAME_C_ScannableCode_Format_ID)
				.orderBy(I_C_ScannableCode_Format_Part.COLUMNNAME_StartNo)
				.orderBy(I_C_ScannableCode_Format_Part.COLUMNNAME_C_ScannableCode_Format_Part_ID)
				.create()
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						ScannableCodeFormatRepository::extactScannableCodeFormatId,
						record -> record
				));

		return formatRecords.values()
				.stream()
				.map(formatRecord -> fromRecord(formatRecord, partRecords.get(extactScannableCodeFormatId(formatRecord))))
				.collect(ScannableCodeFormatsCollection.collect());
	}

	@NotNull
	private static ScannableCodeFormatId extactScannableCodeFormatId(final I_C_ScannableCode_Format record)
	{
		return ScannableCodeFormatId.ofRepoId(record.getC_ScannableCode_Format_ID());
	}

	@NotNull
	private static ScannableCodeFormatId extactScannableCodeFormatId(final I_C_ScannableCode_Format_Part record)
	{
		return ScannableCodeFormatId.ofRepoId(record.getC_ScannableCode_Format_ID());
	}

	private static ScannableCodeFormat fromRecord(
			@NotNull final I_C_ScannableCode_Format formatRecord,
			@NotNull final List<I_C_ScannableCode_Format_Part> partRecords)
	{
		try
		{
			return ScannableCodeFormat.builder()
					.id(extactScannableCodeFormatId(formatRecord))
					.name(formatRecord.getName())
					.description(formatRecord.getDescription())
					.parts(partRecords.stream()
							.map(ScannableCodeFormatRepository::fromRecord)
							.collect(ImmutableList.toImmutableList()))
					.build();
		}
		catch (Exception ex)
		{
			if (ex instanceof AdempiereException && AdempiereException.isUserValidationError(ex))
			{
				throw ex;
			}
			else
			{
				throw new AdempiereException("Invalid format: " + formatRecord.getName() + " (ID=" + formatRecord.getC_ScannableCode_Format_ID() + ")", ex);
			}
		}
	}

	private static ScannableCodeFormatPart fromRecord(@NotNull final I_C_ScannableCode_Format_Part record)
	{
		final ScannableCodeFormatPartType type = ScannableCodeFormatPartType.ofCode(record.getDataType());
		final ScannableCodeFormatPart.ScannableCodeFormatPartBuilder builder = ScannableCodeFormatPart.builder()
				.startPosition(record.getStartNo())
				.endPosition(record.getEndNo())
				.type(type);

		if (type == ScannableCodeFormatPartType.BestBeforeDate)
		{
			builder.dateFormat(extractDateTimeFormat(record));
		}

		return builder.build();
	}

	private static DateTimeFormatter extractDateTimeFormat(final @NotNull I_C_ScannableCode_Format_Part record)
	{
		final String pattern = StringUtils.trimBlankToNull(record.getDataFormat());
		if (pattern == null)
		{
			return null;
		}

		return DateTimeFormatter.ofPattern(pattern);
	}
}
