package de.metas.scannable_code.format.repository;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import de.metas.logging.LogManager;
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
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Builder
class ScannableCodeFormatLoaderAndSaver
{
	private static final Logger logger = LogManager.getLogger(ScannableCodeFormatLoaderAndSaver.class);

	@NotNull private final IQueryBL queryBL;

	private final HashMap<ScannableCodeFormatId, I_C_ScannableCode_Format> formatRecordsCache = new HashMap<>();
	private final ArrayListMultimap<ScannableCodeFormatId, I_C_ScannableCode_Format_Part> partRecordsCache = ArrayListMultimap.create();

	public ScannableCodeFormatsCollection loadAll()
	{
		warmUpAll();
		final Set<ScannableCodeFormatId> formatIds = formatRecordsCache.keySet();

		return formatIds.stream()
				.map(this::getByIdOrNull)
				.filter(Objects::nonNull)
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

	/**
	 * One broken format must not stop the other formats from working: log it and skip it.
	 */
	@Nullable
	private ScannableCodeFormat getByIdOrNull(@NotNull final ScannableCodeFormatId formatId)
	{
		try
		{
			return getById(formatId);
		}
		catch (final Exception ex)
		{
			final I_C_ScannableCode_Format formatRecord = formatRecordsCache.get(formatId);
			logger.warn("Skipping invalid scannable code format {} (ID={}): {}", formatRecord != null ? formatRecord.getName() : "?", formatId.getRepoId(), ex.getLocalizedMessage(), ex);
			return null;
		}
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
					.parts(fromRecords(partRecords))
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

	/**
	 * Parts keep the given order (that's the parsing order); only the end of a part with an empty {@code EndNo} is resolved here.
	 * Empty (0) {@code EndNo}: the part ends where the part with the next higher {@code StartNo} starts, or it reads to the end of the code if it's the last one.
	 */
	private static ImmutableList<ScannableCodeFormatPart> fromRecords(@NotNull final List<I_C_ScannableCode_Format_Part> partRecords)
	{
		final List<I_C_ScannableCode_Format_Part> partRecordsSortedByStartNo = partRecords.stream()
				.sorted(Comparator.comparingInt(I_C_ScannableCode_Format_Part::getStartNo))
				.collect(ImmutableList.toImmutableList());

		return partRecords.stream()
				.map(partRecord -> fromRecord(partRecord, computeEndNo(partRecord, partRecordsSortedByStartNo)))
				.collect(ImmutableList.toImmutableList());
	}

	private static int computeEndNo(
			@NotNull final I_C_ScannableCode_Format_Part partRecord,
			@NotNull final List<I_C_ScannableCode_Format_Part> partRecordsSortedByStartNo)
	{
		if (partRecord.getEndNo() != ScannableCodeFormatPart.OPEN_END)
		{
			return partRecord.getEndNo();
		}

		for (final I_C_ScannableCode_Format_Part other : partRecordsSortedByStartNo)
		{
			if (other != partRecord && other.getStartNo() == partRecord.getStartNo())
			{
				throw new AdempiereException("Part starting at " + partRecord.getStartNo() + " has no End No, but another part starts at the same position");
			}
		}

		int index = -1;
		for (int i = 0; i < partRecordsSortedByStartNo.size(); i++)
		{
			if (partRecordsSortedByStartNo.get(i) == partRecord)
			{
				index = i;
				break;
			}
		}
		if (index < 0 || index == partRecordsSortedByStartNo.size() - 1)
		{
			return ScannableCodeFormatPart.OPEN_END; // last part: to the end of the code
		}

		final int endNo = partRecordsSortedByStartNo.get(index + 1).getStartNo() - 1;
		if (endNo < partRecord.getStartNo())
		{
			throw new AdempiereException("Part starting at " + partRecord.getStartNo() + " has no End No, but the next part starts at " + (endNo + 1));
		}
		return endNo;
	}

	private static ScannableCodeFormatPart fromRecord(@NotNull final I_C_ScannableCode_Format_Part record, final int endNo)
	{
		final ScannableCodeFormatPartType type = ScannableCodeFormatPartType.ofCode(record.getDataType());
		final ScannableCodeFormatPart.ScannableCodeFormatPartBuilder builder = ScannableCodeFormatPart.builder()
				.startPosition(record.getStartNo())
				.endPosition(endNo)
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
