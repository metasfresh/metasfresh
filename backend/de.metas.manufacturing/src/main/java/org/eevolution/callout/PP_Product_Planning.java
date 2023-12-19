package org.eevolution.callout;

import de.metas.material.maturing.MaturingConfigId;
import de.metas.material.maturing.MaturingConfigLine;
import de.metas.material.maturing.MaturingConfigLineId;
import de.metas.material.maturing.MaturingConfigRepository;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.impl.ProductPlanningDAO;
import de.metas.product.ProductId;
import de.metas.util.Services;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.model.ModelValidator;
import org.eevolution.model.I_PP_Product_Planning;
import org.springframework.stereotype.Component;

import java.util.List;

@Callout(I_PP_Product_Planning.class)
@Interceptor(I_PP_Product_Planning.class)
@Component
public class PP_Product_Planning
{
	final MaturingConfigRepository maturingConfigRepo;

	public PP_Product_Planning(final MaturingConfigRepository maturingConfigRepo)
	{
		this.maturingConfigRepo = maturingConfigRepo;
	}

	@Init
	public void registerCallout()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = {I_PP_Product_Planning.COLUMNNAME_IsMatured})
	public void onIsMatured(final I_PP_Product_Planning productPlanningRecord)
	{
		if (productPlanningRecord.isMatured())
		{
			final List<MaturingConfigLine> configLines = maturingConfigRepo.getByMaturedProductId(ProductId.ofRepoIdOrNull(productPlanningRecord.getM_Product_ID()));
			if (configLines.isEmpty() || configLines.size() > 1)
			{
				return;
			}

			final MaturingConfigLine line = configLines.get(0);
			productPlanningRecord.setM_Maturing_Configuration_ID(MaturingConfigId.toRepoId(line.getMaturingConfigId()));
			productPlanningRecord.setM_Maturing_Configuration_Line_ID(MaturingConfigLineId.toRepoId(line.getId()));
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_NEW },
			ifColumnsChanged = { I_PP_Product_Planning.COLUMNNAME_IsMatured })
	public void validateMandatoryBOMVersionsAndWarehouseId(final I_PP_Product_Planning productPlanningRecord)
	{
		final ProductPlanning productPlanning = ProductPlanningDAO.fromRecord(productPlanningRecord);

		if (!productPlanning.isMatured())
		{
			return;
		}

		if (productPlanning.getBomVersionsId() == null)
		{
			throw new FillMandatoryException(I_PP_Product_Planning.COLUMNNAME_PP_Product_BOMVersions_ID);
		}

		if (productPlanning.getWarehouseId() == null)
		{
			throw new FillMandatoryException(I_PP_Product_Planning.COLUMNNAME_M_Warehouse_ID);
		}
	}
}
