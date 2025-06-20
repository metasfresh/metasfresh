package de.metas.scannable_code.format.repository;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import de.metas.scannable_code.format.ScannableCodeFormat;
import de.metas.scannable_code.format.ScannableCodeFormatCreateRequest;
import de.metas.scannable_code.format.ScannableCodeFormatId;
import de.metas.scannable_code.format.ScannableCodeFormatPart;
import de.metas.scannable_code.format.ScannableCodeFormatPartType;
import de.metas.scannable_code.format.ScannableCodeFormatsCollection;
import de.metas.util.time.PatternedDateTimeFormatter;
import lombok.Builder;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_ScannableCode_Format;
import org.compiere.model.I_C_ScannableCode_Format_Part;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Builder
class ScannableCodeFormatLoaderAndSaver
{
	@NotNull private final IQueryBL queryBL;

	private final HashMap<ScannableCodeFormatId, I_C_ScannableCode_Format> formatRecordsCache = new HashMap<>();
	private final ArrayListMultimap<ScannableCodeFormatId, I_C_ScannableCode_Format_Part> partRecordsCache = ArrayListMultimap.create();

	public ScannableCodeFormatsCollection loadAll()
	{
		warmUpAll();
		final Set<ScannableCodeFormatId> formatIds = formatRecordsCache.keySet();

		return formatIds.stream()
				.map(this::getById)
				.collect(ScannableCodeFormatsCollection.collect());
	}

	private void warmUpAll()
	{
		formatRecordsCache.clear();
		partRecordsCache.clear();

		queryBL.createQueryBuilder(I_C_ScannableCode_Format.class)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_C_ScannableCode_Format.COLUMNNAME_C_ScannableCode_Format_ID)
				.create()
				.forEach(this::addToCache);

		final Set<ScannableCodeFormatId> formatIds = formatRecordsCache.keySet();
		if (formatIds.isEmpty())
		{
			return;
		}

		queryBL.createQueryBuilder(I_C_ScannableCode_Format_Part.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_ScannableCode_Format_Part.COLUMNNAME_C_ScannableCode_Format_ID, formatIds)
				.orderBy(I_C_ScannableCode_Format_Part.COLUMNNAME_C_ScannableCode_Format_ID)
				.orderBy(I_C_ScannableCode_Format_Part.COLUMNNAME_StartNo)
				.orderBy(I_C_ScannableCode_Format_Part.COLUMNNAME_C_ScannableCode_Format_Part_ID)
				.create()
				.forEach(this::addToCache);
	}

	private void addToCache(final I_C_ScannableCode_Format formatRecord)
	{
		formatRecordsCache.put(extactScannableCodeFormatId(formatRecord), formatRecord);
	}

	private void addToCache(final I_C_ScannableCode_Format_Part partRecord)
	{
		partRecordsCache.put(extactScannableCodeFormatId(partRecord), partRecord);
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

	private ScannableCodeFormat getById(@NotNull final ScannableCodeFormatId formatId)
	{
		final I_C_ScannableCode_Format formatRecord = formatRecordsCache.get(formatId);
		if (formatRecord == null)
		{
			throw new AdempiereException("No format found for " + formatId);
		}

		final List<I_C_ScannableCode_Format_Part> partRecords = partRecordsCache.get(formatId);

		return fromRecord(formatRecord, partRecords);
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
							.map(ScannableCodeFormatLoaderAndSaver::fromRecord)
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

		if (type == ScannableCodeFormatPartType.Constant)
		{
			builder.constantValue(record.getConstantValue());
		}
		else if (type.isDate())
		{
			builder.dateFormat(PatternedDateTimeFormatter.ofNullablePattern((record.getDataFormat())));
		}

		return builder.build();
	}

	public ScannableCodeFormat create(@NotNull final ScannableCodeFormatCreateRequest request)
	{
		final I_C_ScannableCode_Format record = InterfaceWrapperHelper.newInstance(I_C_ScannableCode_Format.class);
		record.setName(request.getName());
		record.setDescription(request.getDescription());
		InterfaceWrapperHelper.saveRecord(record);
		addToCache(record);
		final ScannableCodeFormatId formatId = ScannableCodeFormatId.ofRepoId(record.getC_ScannableCode_Format_ID());

		request.getParts().forEach(part -> createPart(part, formatId));

		return getById(formatId);
	}

	private void createPart(@NotNull final ScannableCodeFormatCreateRequest.Part part, @NotNull final ScannableCodeFormatId formatId)
	{
		final I_C_ScannableCode_Format_Part record = InterfaceWrapperHelper.newInstance(I_C_ScannableCode_Format_Part.class);
		record.setC_ScannableCode_Format_ID(formatId.getRepoId());
		record.setStartNo(part.getStartPosition());
		record.setEndNo(part.getEndPosition());
		record.setDataType(part.getType().getCode());
		record.setDataFormat(PatternedDateTimeFormatter.toPattern(part.getDateFormat()));
		record.setConstantValue(part.getConstantValue());
		record.setDecimalPointPosition(part.getDecimalPointPosition());
		record.setDescription(part.getDescription());
		InterfaceWrapperHelper.saveRecord(record);
		addToCache(record);
	}

}
