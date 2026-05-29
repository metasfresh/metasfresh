package de.metas.manufacturing.order.weighting.run;

import de.metas.organization.OrgId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.lang.SeqNo;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.eevolution.model.I_PP_Order_Weighting_Run;
import org.eevolution.model.I_PP_Order_Weighting_RunCheck;
import org.springframework.stereotype.Repository;

import java.util.function.Consumer;

@Repository
public class PPOrderWeightingRunRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public PPOrderWeightingRun getById(final PPOrderWeightingRunId id)
	{
		final PPOrderWeightingRunLoaderAndSaver loader = new PPOrderWeightingRunLoaderAndSaver();
		return loader.getById(id);
	}

	public SeqNo getNextLineNo(final PPOrderWeightingRunId weightingRunId)
	{
		final int lastLineNo = queryBL.createQueryBuilder(I_PP_Order_Weighting_RunCheck.class)
				.addEqualsFilter(I_PP_Order_Weighting_RunCheck.COLUMNNAME_PP_Order_Weighting_Run_ID, weightingRunId)
				.create()
				.maxInt(I_PP_Order_Weighting_RunCheck.COLUMNNAME_Line);

		return SeqNo.ofInt(lastLineNo).next();
	}

	public void save(@NonNull final PPOrderWeightingRun weightingRun)
	{
		final PPOrderWeightingRunLoaderAndSaver saver = new PPOrderWeightingRunLoaderAndSaver();
		saver.save(weightingRun);
	}

	public UomId getUomId(final PPOrderWeightingRunId id)
	{
		final PPOrderWeightingRunLoaderAndSaver loader = new PPOrderWeightingRunLoaderAndSaver();
		return loader.getUomId(id);
	}

	public void updateWhileSaving(
			@NonNull final I_PP_Order_Weighting_Run record,
			@NonNull final Consumer<PPOrderWeightingRun> consumer)
	{
		final PPOrderWeightingRunId runId = PPOrderWeightingRunLoaderAndSaver.extractId(record);

		final PPOrderWeightingRunLoaderAndSaver loaderAndSaver = new PPOrderWeightingRunLoaderAndSaver();
		loaderAndSaver.addToCacheAndAvoidSaving(record);
		loaderAndSaver.updateById(runId, consumer);
	}

	public void updateById(
			@NonNull final PPOrderWeightingRunId runId,
			@NonNull final Consumer<PPOrderWeightingRun> consumer)
	{
		final PPOrderWeightingRunLoaderAndSaver loaderAndSaver = new PPOrderWeightingRunLoaderAndSaver();
		loaderAndSaver.updateById(runId, consumer);
	}

	public void deleteChecks(final PPOrderWeightingRunId runId)
	{
		queryBL.createQueryBuilder(I_PP_Order_Weighting_RunCheck.class)
				.addEqualsFilter(I_PP_Order_Weighting_RunCheck.COLUMNNAME_PP_Order_Weighting_Run_ID, runId)
				.create()
				.delete();
	}

	public PPOrderWeightingRunCheckId addRunCheck(
			@NonNull final PPOrderWeightingRunId weightingRunId,
			@NonNull final SeqNo lineNo,
			@NonNull final Quantity weight,
			@NonNull final OrgId orgId)
	{
		final I_PP_Order_Weighting_RunCheck record = InterfaceWrapperHelper.newInstance(I_PP_Order_Weighting_RunCheck.class);
		record.setAD_Org_ID(orgId.getRepoId());
		record.setPP_Order_Weighting_Run_ID(weightingRunId.getRepoId());
		record.setLine(lineNo.toInt());
		record.setWeight(weight.toBigDecimal());
		record.setC_UOM_ID(weight.getUomId().getRepoId());
		InterfaceWrapperHelper.save(record);

		return PPOrderWeightingRunCheckId.ofRepoId(weightingRunId, record.getPP_Order_Weighting_RunCheck_ID());
	}
}
