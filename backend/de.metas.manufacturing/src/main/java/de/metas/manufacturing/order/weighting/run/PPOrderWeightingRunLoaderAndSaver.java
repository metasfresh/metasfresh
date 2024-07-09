package de.metas.manufacturing.order.weighting.run;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Range;
import de.metas.manufacturing.order.weighting.spec.WeightingSpecificationsId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import de.metas.util.lang.SeqNo;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order_Weighting_Run;
import org.eevolution.model.I_PP_Order_Weighting_RunCheck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.Collectors;

class PPOrderWeightingRunLoaderAndSaver
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final HashMap<PPOrderWeightingRunId, I_PP_Order_Weighting_Run> recordsByRunId = new HashMap<>();
	private final HashSet<PPOrderWeightingRunId> runIdToAvoidSaving = new HashSet<>();

	private final HashMap<PPOrderWeightingRunId, ArrayList<I_PP_Order_Weighting_RunCheck>> checkRecordsByRunId = new HashMap<>();

	public void addToCacheAndAvoidSaving(@NonNull final I_PP_Order_Weighting_Run record)
	{
		@NonNull final PPOrderWeightingRunId runId = extractId(record);
		recordsByRunId.put(runId, record);
		runIdToAvoidSaving.add(runId);
	}

	public PPOrderWeightingRun getById(final PPOrderWeightingRunId id)
	{
		final I_PP_Order_Weighting_Run runRecord = getRecordById(id);

		final ImmutableList<PPOrderWeightingRunCheck> checks = getCheckRecordsByRunId(id)
				.stream()
				.map(PPOrderWeightingRunLoaderAndSaver::fromRecord)
				.collect(ImmutableList.toImmutableList());

		return fromRecord(runRecord, checks);
	}

	public void updateById(
			@NonNull final PPOrderWeightingRunId id,
			@NonNull Consumer<PPOrderWeightingRun> consumer)
	{
		final PPOrderWeightingRun run = getById(id);
		consumer.accept(run);
		save(run);
	}

	public UomId getUomId(final PPOrderWeightingRunId id)
	{
		final I_PP_Order_Weighting_Run record = getRecordById(id);
		return extractUomId(record);
	}

	private I_PP_Order_Weighting_Run getRecordById(@NonNull final PPOrderWeightingRunId id)
	{
		return recordsByRunId.computeIfAbsent(id, this::retrieveRecord);
	}

	private I_PP_Order_Weighting_Run retrieveRecord(final @NonNull PPOrderWeightingRunId id)
	{
		return queryBL.createQueryBuilder(I_PP_Order_Weighting_Run.class)
				.addEqualsFilter(I_PP_Order_Weighting_Run.COLUMNNAME_PP_Order_Weighting_Run_ID, id)
				.create()
				.firstOnlyOptional(I_PP_Order_Weighting_Run.class)
				.orElseThrow(() -> new AdempiereException("@NotFound@ " + id));
	}

	private ArrayList<I_PP_Order_Weighting_RunCheck> getCheckRecordsByRunId(final PPOrderWeightingRunId id)
	{
		return checkRecordsByRunId.computeIfAbsent(id, this::retrieveCheckRecordsByRunId);
	}

	private ArrayList<I_PP_Order_Weighting_RunCheck> retrieveCheckRecordsByRunId(final PPOrderWeightingRunId id)
	{
		return queryBL.createQueryBuilder(I_PP_Order_Weighting_RunCheck.class)
				.addEqualsFilter(I_PP_Order_Weighting_RunCheck.COLUMNNAME_PP_Order_Weighting_Run_ID, id)
				.create()
				.stream()
				.collect(Collectors.toCollection(ArrayList::new));
	}

	private static PPOrderWeightingRunCheck fromRecord(@NonNull final I_PP_Order_Weighting_RunCheck record)
	{
		return PPOrderWeightingRunCheck.builder()
				.id(extractId(record))
				.lineNo(SeqNo.ofInt(record.getLine()))
				.weight(Quantitys.of(record.getWeight(), UomId.ofRepoId(record.getC_UOM_ID())))
				.isToleranceExceeded(record.isToleranceExceeded())
				.description(record.getDescription())
				.build();
	}

	@NonNull
	private static PPOrderWeightingRunCheckId extractId(final @NonNull I_PP_Order_Weighting_RunCheck record)
	{
		return PPOrderWeightingRunCheckId.ofRepoId(record.getPP_Order_Weighting_Run_ID(), record.getPP_Order_Weighting_RunCheck_ID());
	}

	private static PPOrderWeightingRun fromRecord(
			@NonNull final I_PP_Order_Weighting_Run record,
			@NonNull final ImmutableList<PPOrderWeightingRunCheck> checks)
	{
		final UomId uomId = extractUomId(record);

		return PPOrderWeightingRun.builder()
				.id(extractId(record))
				.ppOrderId(PPOrderId.ofRepoId(record.getPP_Order_ID()))
				.ppOrderBOMLineId(PPOrderBOMLineId.ofRepoIdOrNull(record.getPP_Order_BOMLine_ID()))
				.dateDoc(record.getDateDoc().toInstant())
				.description(record.getDescription())
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.weightingSpecificationsId(WeightingSpecificationsId.ofRepoId(record.getPP_Weighting_Spec_ID()))
				.tolerance(Percent.of(record.getTolerance_Perc()))
				.weightChecksRequired(record.getWeightChecksRequired())
				.targetWeight(Quantitys.of(record.getTargetWeight(), uomId))
				.targetWeightRange(Range.closed(
						Quantitys.of(record.getMinWeight(), uomId),
						Quantitys.of(record.getMaxWeight(), uomId)))
				.isToleranceExceeded(record.isToleranceExceeded())
				.isProcessed(record.isProcessed())
				.checks(checks)
				.orgId(OrgId.ofRepoIdOrAny(record.getAD_Org_ID()))
				.build();
	}

	private static UomId extractUomId(final I_PP_Order_Weighting_Run record)
	{
		return UomId.ofRepoId(record.getC_UOM_ID());
	}

	@NonNull
	static PPOrderWeightingRunId extractId(final @NonNull I_PP_Order_Weighting_Run record)
	{
		return PPOrderWeightingRunId.ofRepoId(record.getPP_Order_Weighting_Run_ID());
	}

	public void save(final PPOrderWeightingRun weightingRun)
	{
		final I_PP_Order_Weighting_Run record = getRecordById(weightingRun.getId());
		updateRecord(record, weightingRun);
		saveRecordIfAllowed(record);

		final ArrayList<I_PP_Order_Weighting_RunCheck> checkRecords = getCheckRecordsByRunId(weightingRun.getId());
		final ImmutableMap<PPOrderWeightingRunCheckId, I_PP_Order_Weighting_RunCheck> checkRecordsById = Maps.uniqueIndex(checkRecords, PPOrderWeightingRunLoaderAndSaver::extractId);

		//
		// UPDATE
		final HashSet<PPOrderWeightingRunCheckId> savedIds = new HashSet<>();
		for (PPOrderWeightingRunCheck check : weightingRun.getChecks())
		{
			final I_PP_Order_Weighting_RunCheck checkRecord = checkRecordsById.get(check.getId());
			if (checkRecord == null)
			{
				throw new AdempiereException("@NotFound@ " + check.getId()); // shall not happen
			}

			updateRecord(checkRecord, check);
			InterfaceWrapperHelper.save(checkRecord);
			savedIds.add(check.getId());
		}

		//
		// DELETE
		if (checkRecords.size() != savedIds.size())
		{
			for (Iterator<I_PP_Order_Weighting_RunCheck> it = checkRecords.iterator(); it.hasNext(); )
			{
				final I_PP_Order_Weighting_RunCheck checkRecord = it.next();
				final PPOrderWeightingRunCheckId id = extractId(checkRecord);
				if (!savedIds.contains(id))
				{
					it.remove();
					InterfaceWrapperHelper.delete(checkRecord);
				}
			}
		}
	}

	private void saveRecordIfAllowed(final I_PP_Order_Weighting_Run record)
	{
		if (runIdToAvoidSaving.contains(extractId(record)))
		{
			return;
		}

		InterfaceWrapperHelper.save(record);
	}

	private void updateRecord(@NonNull final I_PP_Order_Weighting_Run record, @NonNull final PPOrderWeightingRun from)
	{
		record.setMinWeight(from.getTargetWeightRange().lowerEndpoint().toBigDecimal());
		record.setMaxWeight(from.getTargetWeightRange().upperEndpoint().toBigDecimal());
		record.setIsToleranceExceeded(from.isToleranceExceeded());
		record.setProcessed(from.isProcessed());
	}

	private void updateRecord(final I_PP_Order_Weighting_RunCheck checkRecord, final PPOrderWeightingRunCheck from)
	{
		checkRecord.setWeight(from.getWeight().toBigDecimal());
		checkRecord.setC_UOM_ID(from.getWeight().getUomId().getRepoId());
		checkRecord.setIsToleranceExceeded(from.isToleranceExceeded());
	}
}
