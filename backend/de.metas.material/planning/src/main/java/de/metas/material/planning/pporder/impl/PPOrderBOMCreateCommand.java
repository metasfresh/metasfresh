package de.metas.material.planning.pporder.impl;

import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.logging.LogManager;
import de.metas.material.planning.exception.BOMExpiredException;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.OrderBOMLineQuantities;
import de.metas.material.planning.pporder.PPOrderUtil;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.eevolution.api.IProductBOMBL;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.PPOrderDocBaseType;
import org.eevolution.api.ProductBOMId;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOM;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.slf4j.Logger;

import java.sql.Timestamp;
import java.util.List;

/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

final class PPOrderBOMCreateCommand
{
	private static final Logger logger = LogManager.getLogger(PPOrderBOMCreateCommand.class);
	private final IProductBOMDAO productBOMsRepo = Services.get(IProductBOMDAO.class);
	private final IProductBOMBL productBOMsBL = Services.get(IProductBOMBL.class);
	private final IPPOrderBOMDAO ppOrderBOMsRepo = Services.get(IPPOrderBOMDAO.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final PPOrderBOMBL ppOrderBOMsBL;

	// parameters
	private final I_PP_Order ppOrder;

	@Builder
	private PPOrderBOMCreateCommand(
			@NonNull final PPOrderBOMBL ppOrderBOMsBL,
			@NonNull final I_PP_Order ppOrder)
	{
		this.ppOrderBOMsBL = ppOrderBOMsBL;
		this.ppOrder = ppOrder;
	}

	public void execute()
	{
		final Timestamp dateStartSchedule = ppOrder.getDateStartSchedule();
		final ProductId mainProductId = ProductId.ofRepoId(ppOrder.getM_Product_ID());
		final ProductBOMId productBOMId = ProductBOMId.ofRepoId(ppOrder.getPP_Product_BOM_ID());
		final I_PP_Product_BOM productBOM = productBOMsRepo.getById(productBOMId);

		PPOrderUtil.verifyProductBOMAndReturnIt(
				mainProductId,
				dateStartSchedule,
				productBOM);

		//
		// Create BOM Head
		if (!productBOMsBL.isValidFromTo(productBOM, dateStartSchedule))
		{
			throw new BOMExpiredException(productBOM, dateStartSchedule);
		}

		final I_PP_Order_BOM orderBOM = createOrderBOM(ppOrder, productBOM);

		final List<I_PP_Product_BOMLine> productBOMLines = productBOMsRepo.retrieveLines(productBOM);
		for (final I_PP_Product_BOMLine productBOMLine : productBOMLines)
		{
			if (!productBOMsBL.isValidFromTo(productBOMLine, dateStartSchedule))
			{
				logger.debug("BOM Line skipped - {}", productBOMLine);
				continue;
			}

			createOrderBOMLine(orderBOM, productBOMLine);
		}
	}

	private I_PP_Order_BOM createOrderBOM(
			final I_PP_Order ppOrder,
			final I_PP_Product_BOM bom)
	{
		final I_PP_Order_BOM orderBOM = InterfaceWrapperHelper.newInstance(I_PP_Order_BOM.class, ppOrder);
		orderBOM.setAD_Org_ID(ppOrder.getAD_Org_ID());
		orderBOM.setPP_Order_ID(ppOrder.getPP_Order_ID());

		orderBOM.setBOMType(bom.getBOMType());
		orderBOM.setBOMUse(bom.getBOMUse());
		orderBOM.setM_ChangeNotice_ID(bom.getM_ChangeNotice_ID());
		orderBOM.setHelp(bom.getHelp());
		orderBOM.setProcessing(bom.isProcessing());
		orderBOM.setHelp(bom.getHelp());
		orderBOM.setDescription(bom.getDescription());
		orderBOM.setM_AttributeSetInstance_ID(bom.getM_AttributeSetInstance_ID());
		orderBOM.setM_Product_ID(bom.getM_Product_ID());  // the bom's M_Product_ID is also the ppOrder's M_Product_ID
		orderBOM.setName(bom.getName());
		orderBOM.setRevision(bom.getRevision());
		orderBOM.setValidFrom(bom.getValidFrom());
		orderBOM.setValidTo(bom.getValidTo());
		orderBOM.setValue(bom.getValue());
		orderBOM.setDocumentNo(bom.getDocumentNo());
		orderBOM.setC_UOM_ID(bom.getC_UOM_ID()); // the BOM's C_UOM_ID
		orderBOM.setSerialNo_Sequence_ID(bom.getSerialNo_Sequence_ID());
		orderBOM.setLotNo_Sequence_ID(bom.getLotNo_Sequence_ID());

		ppOrderBOMsRepo.save(orderBOM);
		return orderBOM;
	}

	private void createOrderBOMLine(
			final I_PP_Order_BOM orderBOM,
			final I_PP_Product_BOMLine bomLine)
	{
		final I_PP_Order_BOMLine orderBOMLine = InterfaceWrapperHelper.newInstance(I_PP_Order_BOMLine.class, ppOrder);

		// Set Defaults
		orderBOMLine.setDescription("");

		//
		// Update from PP_Product BOM Line
		ppOrderBOMsBL.updateOrderBOMLine(orderBOMLine, bomLine);

		//
		// Set values from PP_Order:
		orderBOMLine.setAD_Org_ID(orderBOM.getAD_Org_ID());
		orderBOMLine.setPP_Order(ppOrder);
		orderBOMLine.setPP_Order_BOM(orderBOM);

		//
		// Warehouse and Locator
		PPOrderUtil.updateBOMLineWarehouseAndLocatorFromOrder(orderBOMLine, ppOrder);

		final Quantity qtyRequired = computeQtyRequired(orderBOMLine);
		PPOrderBOMBL.updateRecord(orderBOMLine, OrderBOMLineQuantities.ofQtyRequired(qtyRequired));

		//
		// Save & return
		ppOrderBOMsRepo.save(orderBOMLine);
	}

	private Quantity computeQtyRequired(final I_PP_Order_BOMLine orderBOMLine)
	{
		final PPOrderDocBaseType docBaseType = getOrderDocBaseType();
		if (docBaseType.isRepairOrder())
		{
			final UomId uomId = UomId.ofRepoId(orderBOMLine.getC_UOM_ID());
			return Quantitys.zero(uomId);
		}
		else
		{
			final Quantity qtyFinishedGood = ppOrderBOMsBL.getQuantities(ppOrder).getQtyRequiredToProduce();
			return ppOrderBOMsBL.computeQtyRequiredByQtyOfFinishedGoods(orderBOMLine, qtyFinishedGood);
		}
	}

	private PPOrderDocBaseType getOrderDocBaseType()
	{
		final DocTypeId docTypeId = getOrderDocTypeId();
		final DocBaseAndSubType docBaseAndSubType = docTypeDAO.getDocBaseAndSubTypeById(docTypeId);
		return PPOrderDocBaseType.ofDocBaseType(docBaseAndSubType.getDocBaseType());
	}

	private DocTypeId getOrderDocTypeId()
	{
		return DocTypeId.optionalOfRepoId(ppOrder.getC_DocTypeTarget_ID())
				.orElseGet(() -> DocTypeId.ofRepoId(ppOrder.getC_DocType_ID()));
	}
}
