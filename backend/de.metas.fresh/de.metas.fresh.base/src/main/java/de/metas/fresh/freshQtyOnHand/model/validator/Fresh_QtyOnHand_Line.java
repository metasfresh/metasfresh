package de.metas.fresh.freshQtyOnHand.model.validator;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.fresh.model.I_Fresh_QtyOnHand;
import de.metas.fresh.model.I_Fresh_QtyOnHand_Line;
import de.metas.i18n.AdMessageKey;
import de.metas.material.planning.ProductPlanningService;
import de.metas.product.ResourceId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.model.ModelValidator;

@Interceptor(I_Fresh_QtyOnHand_Line.class)
@Callout(I_Fresh_QtyOnHand_Line.class)
public class Fresh_QtyOnHand_Line
{
	@Init
	public void registerCallout()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void onBeforeSave(final I_Fresh_QtyOnHand_Line line)
	{
		// Make sure that we always keep DateDoc in sync with the document header
		final I_Fresh_QtyOnHand header = line.getFresh_QtyOnHand();
		line.setDateDoc(header.getDateDoc());

		// NOTE: ASIKey will be set automatically by Fresh_QtyOnHand_Line_OnUpdate_Trigger
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void fireStouckEstimatedEvent(final I_Fresh_QtyOnHand_Line line)
	{

	}

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_CHANGE,
			ModelValidator.TYPE_BEFORE_NEW
	}, ifColumnsChanged = {
			I_Fresh_QtyOnHand_Line.COLUMNNAME_M_Warehouse_ID,
	})
	@CalloutMethod(columnNames = I_Fresh_QtyOnHand_Line.COLUMNNAME_M_Warehouse_ID)
	public void fireWarehouseChanges(@NonNull final I_Fresh_QtyOnHand_Line line)
	{
		final ProductPlanningService productPlanningService = SpringContextHolder.instance.getBean(ProductPlanningService.class);

		final ResourceId plantId = productPlanningService.getPlantOfWarehouse(WarehouseId.ofRepoId(line.getM_Warehouse_ID()))
				.orElseThrow(() -> new AdempiereException(AdMessageKey.of("Fresh_QtyOnHand_Line.MissingWarehousePlant"))
						.markAsUserValidationError());

		line.setPP_Plant_ID(plantId.getRepoId());
	}
}
