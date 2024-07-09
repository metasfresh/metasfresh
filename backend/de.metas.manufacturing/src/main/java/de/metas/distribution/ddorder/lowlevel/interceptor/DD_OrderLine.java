package de.metas.distribution.ddorder.lowlevel.interceptor;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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

import de.metas.copy_with_details.CopyRecordFactory;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelService;
import de.metas.logging.LogManager;
import de.metas.material.planning.pporder.LiberoException;
import de.metas.product.ResourceId;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_DD_OrderLine_Alternative;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.List;

@Interceptor(I_DD_OrderLine.class)
class DD_OrderLine
{
	private final transient Logger logger = LogManager.getLogger(getClass());
	private final DDOrderLowLevelService ddOrderLowLevelService;

	DD_OrderLine(final DDOrderLowLevelService ddOrderLowLevelService) {this.ddOrderLowLevelService = ddOrderLowLevelService;}

	@Init
	public void init()
	{
		CopyRecordFactory.enableForTableName(I_DD_OrderLine.Table_Name);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE)
	public void setIsDelivered(final I_DD_OrderLine ddOrderLine)
	{
		final BigDecimal qtyOrdered = ddOrderLine.getQtyOrdered();
		final BigDecimal qtyDelivered = ddOrderLine.getQtyDelivered();
		final boolean delivered = qtyDelivered.compareTo(qtyOrdered) >= 0;
		ddOrderLine.setIsDelivered(delivered);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }
			, ifColumnsChanged = {
			I_DD_OrderLine.COLUMNNAME_AD_Org_ID,
			I_DD_OrderLine.COLUMNNAME_M_Locator_ID,
			I_DD_OrderLine.COLUMNNAME_M_Product_ID,
	})
	public void setPP_Plant_From_ID(final I_DD_OrderLine ddOrderLine)
	{
		ResourceId plantFromId = ddOrderLowLevelService.findPlantFromOrNull(ddOrderLine);

		//
		// If no plant was found for "Warehouse From" we shall use the Destination Plant.
		//
		// Example when applies: MRP generated a DD order to move materials from a Raw materials warehouse to Plant warehouse.
		// The raw materials warehouse is not assigned to a Plant so no further planning will be calculated.
		// I see it as perfectly normal to use the Destination Plant in this case.
		if (plantFromId == null)
		{
			plantFromId = ResourceId.ofRepoIdOrNull(ddOrderLine.getDD_Order().getPP_Plant_ID());
		}

		if (plantFromId == null)
		{
			final LiberoException ex = new LiberoException("@NotFound@ @PP_Plant_ID@"
					+ "\n @M_Marehouse_ID@: " + ddOrderLine.getM_Locator_ID()
					+ "\n @M_Product_ID@: " + ddOrderLine.getM_Product_ID()
					+ "\n @DD_OrderLine_ID@: " + ddOrderLine
			);
			logger.warn(ex.getLocalizedMessage(), ex);
		}
		ddOrderLine.setPP_Plant_From_ID(plantFromId.getRepoId());
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteDDOrderLineAlternatives(final I_DD_OrderLine ddOrderLine)
	{
		final List<I_DD_OrderLine_Alternative> ddOrderLineAlternatives = ddOrderLowLevelService.retrieveAllAlternatives(ddOrderLine);
		for (final I_DD_OrderLine_Alternative ddOrderLineAlt : ddOrderLineAlternatives)
		{
			InterfaceWrapperHelper.delete(ddOrderLineAlt);
		}
	}

	/**
	 * @implSpec task http://dewiki908/mediawiki/index.php/08583_Erfassung_Packvorschrift_in_DD_Order_ist_crap_%28108882381939%29
	 * ("UOM In manual DD_OrderLine shall always be the uom of the product ( as talked with Mark) ")
	 */
	@ModelChange(
			timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_DD_OrderLine.COLUMNNAME_M_Product_ID })
	public void setUOMInDDOrderLine(final I_DD_OrderLine ddOrderLine)
	{
		ddOrderLowLevelService.updateUomFromProduct(ddOrderLine);
	}
}
