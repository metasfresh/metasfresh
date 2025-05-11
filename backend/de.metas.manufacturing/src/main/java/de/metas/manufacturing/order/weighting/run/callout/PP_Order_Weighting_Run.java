package de.metas.manufacturing.order.weighting.run.callout;

import de.metas.manufacturing.order.weighting.spec.WeightingSpecifications;
import de.metas.manufacturing.order.weighting.spec.WeightingSpecificationsId;
import de.metas.manufacturing.order.weighting.spec.WeightingSpecificationsRepository;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.OrderBOMLineQuantities;
import de.metas.material.planning.pporder.PPOrderQuantities;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
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
import java.util.Optional;

@Callout(value = I_PP_Order_Weighting_Run.class, recursionAvoidanceLevel = Callout.RecursionAvoidanceLevel.CalloutMethod)
@Component
public class PP_Order_Weighting_Run
{
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final WeightingSpecificationsRepository weightingSpecificationsRepository;
	private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
	private final IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);

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
		updateFromPPOrderIfSet(weightingRun, true);
	}

	@CalloutMethod(columnNames = I_PP_Order_Weighting_Run.COLUMNNAME_PP_Order_BOMLine_ID)
	public void onPP_Order_BOMLine_ID(final I_PP_Order_Weighting_Run weightingRun)
	{
		final PPOrderBOMLineId ppOrderBOMLineId = PPOrderBOMLineId.ofRepoIdOrNull(weightingRun.getPP_Order_BOMLine_ID());
		if (ppOrderBOMLineId == null)
		{
			updateFromPPOrderIfSet(weightingRun, false);
		}
		else
		{
			updateFromPPOrderBOMLine(weightingRun);
		}
	}

	private void updateFromPPOrderIfSet(final I_PP_Order_Weighting_Run weightingRun, final boolean forceUpdateDateDoc)
	{
		final PPOrderId ppOrderId = PPOrderId.ofRepoIdOrNull(weightingRun.getPP_Order_ID());
		if (ppOrderId == null)
		{
			return;
		}

		final I_PP_Order ppOrder = ppOrderBL.getById(ppOrderId);

		if (ppOrder.getDateOrdered() == null || forceUpdateDateDoc)
		{
			weightingRun.setDateDoc(ppOrder.getDateOrdered());
		}

		final WeightingSpecifications weightingSpecifications = getWeightSpecificationsAndUpdateIfNotSet(weightingRun);
		final ProductId productId = ProductId.ofRepoId(ppOrder.getM_Product_ID());
		final PPOrderQuantities ppOrderQtys = ppOrderBL.getQuantities(ppOrder)
				.convertQuantities(qty -> uomConversionBL.convertQuantityTo(qty, productId, weightingSpecifications.getUomId()));
		weightingRun.setPP_Order_BOMLine_ID(-1);
		setProductAndTargetWeight(weightingRun, productId, ppOrderQtys.getQtyRequiredToProduce());
	}

	private void updateFromPPOrderBOMLine(final I_PP_Order_Weighting_Run weightingRun)
	{
		final PPOrderBOMLineId ppOrderBOMLineId = PPOrderBOMLineId.ofRepoId(weightingRun.getPP_Order_BOMLine_ID());
		final I_PP_Order_BOMLine orderBOMLine = ppOrderBOMBL.getOrderBOMLineById(ppOrderBOMLineId);
		final BOMComponentType bomComponentType = BOMComponentType.optionalOfNullableCode(orderBOMLine.getComponentType()).orElse(BOMComponentType.Component);
		if (!bomComponentType.isByOrCoProduct())
		{
			throw new AdempiereException("Only CO/BY-Product BOM Lines are eligible");
		}

		final WeightingSpecifications weightingSpecifications = getWeightSpecificationsAndUpdateIfNotSet(weightingRun);
		final ProductId productId = ProductId.ofRepoId(orderBOMLine.getM_Product_ID());
		final OrderBOMLineQuantities orderBOMLineQtys = ppOrderBOMBL.getQuantities(orderBOMLine)
				.convertQuantities(qty -> uomConversionBL.convertQuantityTo(qty, productId, weightingSpecifications.getUomId()));

		setProductAndTargetWeight(weightingRun, productId, orderBOMLineQtys.getQtyRequired_NegateBecauseIsCOProduct());
	}

	private void setProductAndTargetWeight(I_PP_Order_Weighting_Run weightingRun, ProductId productId, Quantity targetWeight)
	{
		weightingRun.setM_Product_ID(productId.getRepoId());
		weightingRun.setC_UOM_ID(targetWeight.getUomId().getRepoId());
		weightingRun.setTargetWeight(targetWeight.toBigDecimal());
	}

	@NonNull
	private WeightingSpecifications getWeightSpecificationsAndUpdateIfNotSet(final I_PP_Order_Weighting_Run weightingRun)
	{
		final WeightingSpecificationsId currentWeightingSpecificationsId = WeightingSpecificationsId.ofRepoIdOrNull(weightingRun.getPP_Weighting_Spec_ID());
		if (currentWeightingSpecificationsId != null)
		{
			return weightingSpecificationsRepository.getById(currentWeightingSpecificationsId);
		}
		else
		{
			final WeightingSpecificationsId weightingSpecificationsId = weightingSpecificationsRepository.getDefaultId();
			weightingRun.setPP_Weighting_Spec_ID(weightingSpecificationsId.getRepoId());
			return updateFromCurrentWeightSpecifications(weightingRun)
					.orElseThrow(() -> new AdempiereException("No weight specifications found")); // shall not happen
		}
	}

	private Optional<WeightingSpecifications> updateFromCurrentWeightSpecifications(final I_PP_Order_Weighting_Run weightingRun)
	{
		final WeightingSpecificationsId weightingSpecificationsId = WeightingSpecificationsId.ofRepoIdOrNull(weightingRun.getPP_Weighting_Spec_ID());
		if (weightingSpecificationsId == null)
		{
			return Optional.empty();
		}
		else
		{
			final WeightingSpecifications weightingSpecifications = weightingSpecificationsRepository.getById(weightingSpecificationsId);
			weightingRun.setC_UOM_ID(weightingSpecifications.getUomId().getRepoId());
			weightingRun.setTolerance_Perc(weightingSpecifications.getTolerance().toBigDecimal());
			weightingRun.setWeightChecksRequired(weightingSpecifications.getWeightChecksRequired());

			updateFromPPOrderIfSet(weightingRun, false);

			return Optional.of(weightingSpecifications);
		}
	}

	@CalloutMethod(columnNames = I_PP_Order_Weighting_Run.COLUMNNAME_PP_Weighting_Spec_ID)
	public void onPP_Weighting_Spec_ID(final I_PP_Order_Weighting_Run weightingRun)
	{
		updateFromCurrentWeightSpecifications(weightingRun);
	}
}
