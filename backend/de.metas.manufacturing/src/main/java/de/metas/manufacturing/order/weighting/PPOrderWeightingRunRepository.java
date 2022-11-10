package de.metas.manufacturing.order.weighting;

import com.google.common.collect.ImmutableList;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order_Weighting_Run;
import org.eevolution.model.I_PP_Order_Weighting_RunCheck;
import org.springframework.stereotype.Repository;

@Repository
public class PPOrderWeightingRunRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public PPOrderWeightingRun getById(final PPOrderWeightingRunId id)
	{
		final I_PP_Order_Weighting_Run runRecord = queryBL.createQueryBuilder(I_PP_Order_Weighting_Run.class)
				.addEqualsFilter(I_PP_Order_Weighting_Run.COLUMNNAME_PP_Order_Weighting_Run_ID, id)
				.create()
				.firstOnly(I_PP_Order_Weighting_Run.class);

		final ImmutableList<PPOrderWeightingRunCheck> checks = queryBL.createQueryBuilder(I_PP_Order_Weighting_RunCheck.class)
				.addEqualsFilter(I_PP_Order_Weighting_RunCheck.COLUMNNAME_PP_Order_Weighting_Run_ID, id)
				.create()
				.stream()
				.map(PPOrderWeightingRunRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());

		return fromRecord(runRecord, checks);
	}

	private static PPOrderWeightingRunCheck fromRecord(@NonNull final I_PP_Order_Weighting_RunCheck record)
	{
		return PPOrderWeightingRunCheck.builder()
				.lineNo(record.getLine())
				.weight(Quantitys.create(record.getWeight(), UomId.ofRepoId(record.getC_UOM_ID())))
				.description(record.getDescription())
				.build();
	}

	private static PPOrderWeightingRun fromRecord(
			@NonNull final I_PP_Order_Weighting_Run record,
			@NonNull final ImmutableList<PPOrderWeightingRunCheck> checks)
	{
		final UomId uomId = UomId.ofRepoId(record.getC_UOM_ID());

		return PPOrderWeightingRun.builder()
				.id(PPOrderWeightingRunId.ofRepoId(record.getPP_Order_Weighting_Run_ID()))
				.ppOrderId(PPOrderId.ofRepoId(record.getPP_Order_ID()))
				.ppOrderBOMLineId(PPOrderBOMLineId.ofRepoIdOrNull(record.getPP_Order_BOMLine_ID()))
				.dateDoc(record.getDateDoc().toInstant())
				.description(record.getDescription())
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.weightingSpecificationsId(WeightingSpecificationsId.ofRepoId(record.getPP_Weighting_Spec_ID()))
				.tolerance(Percent.of(record.getTolerance_Perc()))
				.weightChecksRequired(record.getWeightChecksRequired())
				.targetWeight(Quantitys.create(record.getTargetWeight(), uomId))
				.targetWeightMin(Quantitys.create(record.getMinWeight(), uomId))
				.targetWeightMax(Quantitys.create(record.getMaxWeight(), uomId))
				.isToleranceExceeded(record.isToleranceExceeded())
				.isProcessed(record.isProcessed())
				.checks(checks)
				.build();
	}

	public int getNextLineNo(final PPOrderWeightingRunId weightingRunId)
	{
		final int lastLineNo = queryBL.createQueryBuilder(I_PP_Order_Weighting_RunCheck.class)
				.addEqualsFilter(I_PP_Order_Weighting_RunCheck.COLUMNNAME_PP_Order_Weighting_Run_ID, weightingRunId)
				.create()
				.maxInt(I_PP_Order_Weighting_RunCheck.COLUMNNAME_Line);

		return Math.max(lastLineNo, 0) / 10 * 10 + 10;
	}
}
