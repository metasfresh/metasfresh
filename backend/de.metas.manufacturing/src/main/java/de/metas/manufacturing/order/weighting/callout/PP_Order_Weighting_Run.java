package de.metas.manufacturing.order.weighting.callout;

import de.metas.manufacturing.order.weighting.WeightingSpecifications;
import de.metas.manufacturing.order.weighting.WeightingSpecificationsId;
import de.metas.manufacturing.order.weighting.WeightingSpecificationsRepository;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.I_PP_Order_Weighting_Run;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Callout(value = I_PP_Order_Weighting_Run.class, recursionAvoidanceLevel = Callout.RecursionAvoidanceLevel.CalloutMethod)
@Component
public class PP_Order_Weighting_Run
{
	private final WeightingSpecificationsRepository weightingSpecificationsRepository;
	private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
	private final IPPOrderBOMDAO ppOrderBOMDAO = Services.get(IPPOrderBOMDAO.class);

	public PP_Order_Weighting_Run(
			@NonNull final WeightingSpecificationsRepository weightingSpecificationsRepository)
	{
		this.weightingSpecificationsRepository = weightingSpecificationsRepository;
	}

	@PostConstruct
	public void postConstruct()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = I_PP_Order_Weighting_Run.COLUMNNAME_PP_Order_ID)
	public void onPP_Order_ID(final I_PP_Order_Weighting_Run weightingRun)
	{
		final PPOrderId ppOrderId = PPOrderId.ofRepoIdOrNull(weightingRun.getPP_Order_ID());
		if (ppOrderId == null)
		{
			return;
		}

		final I_PP_Order ppOrder = ppOrderBL.getById(ppOrderId);

		weightingRun.setDateDoc(ppOrder.getDateOrdered());
		weightingRun.setPP_Order_BOMLine_ID(-1);
		weightingRun.setM_Product_ID(ppOrder.getM_Product_ID());
		weightingRun.setC_UOM_ID(ppOrder.getC_UOM_ID());
		weightingRun.setTargetWeight(ppOrder.getQtyOrdered());

		weightingRun.setPP_Weighting_Spec_ID(weightingSpecificationsRepository.get().getId().getRepoId());
	}

	@CalloutMethod(columnNames = I_PP_Order_Weighting_Run.COLUMNNAME_PP_Order_BOMLine_ID)
	public void onPP_Order_BOMLine_ID(final I_PP_Order_Weighting_Run weightingRun)
	{
		final PPOrderBOMLineId ppOrderBOMLineId = PPOrderBOMLineId.ofRepoIdOrNull(weightingRun.getPP_Order_BOMLine_ID());
		if (ppOrderBOMLineId == null)
		{
			return;
		}

		final I_PP_Order_BOMLine orderBOMLine = ppOrderBOMDAO.getOrderBOMLineById(ppOrderBOMLineId);
		final BOMComponentType bomComponentType = BOMComponentType.optionalOfNullableCode(orderBOMLine.getComponentType()).orElse(BOMComponentType.Component);
		if (!bomComponentType.isByOrCoProduct())
		{
			throw new AdempiereException("Only CO/BY-Product BOM Lines are eligible");
		}

		weightingRun.setM_Product_ID(orderBOMLine.getM_Product_ID());
		weightingRun.setC_UOM_ID(orderBOMLine.getC_UOM_ID());
		weightingRun.setTargetWeight(orderBOMLine.getQtyRequiered().negate()); // negate because it's a co/by-product
	}

	@CalloutMethod(columnNames = I_PP_Order_Weighting_Run.COLUMNNAME_PP_Weighting_Spec_ID)
	public void onPP_Weighting_Spec_ID(final I_PP_Order_Weighting_Run weightingRun)
	{
		final WeightingSpecificationsId specificationsId = WeightingSpecificationsId.ofRepoIdOrNull(weightingRun.getPP_Weighting_Spec_ID());
		if (specificationsId == null)
		{
			return;
		}

		final WeightingSpecifications weightingSpecifications = weightingSpecificationsRepository.getById(specificationsId);
		weightingRun.setTolerance_Perc(weightingSpecifications.getTolerance().toBigDecimal());
		weightingRun.setWeightChecksRequired(weightingSpecifications.getWeightChecksRequired());
	}
}
