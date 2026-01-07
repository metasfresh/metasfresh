package de.metas.material.planning.pporder;

import de.metas.document.engine.DocStatus;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderData;
import de.metas.material.event.pporder.PPOrderLine;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.exception.BOMExpiredException;
import de.metas.material.planning.exception.MrpException;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.IProductBOMBL;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;

import java.math.BigDecimal;
import java.util.Date;

/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2017 metas GmbH
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

@UtilityClass
public class PPOrderUtil
{

	/**
	 * Calculates how much qty is required (standard) for given BOM Line, considering the given quantity of finished goods.
	 */
	public BigDecimal computeQtyRequired(
			@NonNull final PPOrderLine ppOrderLinePojo,
			@NonNull final PPOrder ppOrderPojo,
			@NonNull final BigDecimal qtyFinishedGood)
	{
		final IProductBOMBL bomsService = Services.get(IProductBOMBL.class);

		final ProductId finishedGoodProductId = ProductId.ofRepoId(ppOrderPojo.getPpOrderData().getProductDescriptor().getProductId());
		final I_PP_Product_BOMLine bomLine = getProductBomLine(ppOrderLinePojo);
		return bomsService.computeQtyRequired(bomLine, finishedGoodProductId, qtyFinishedGood);
	}

	public boolean isCoProduct(@NonNull final I_PP_Order_BOMLine bomLine)
	{
		final BOMComponentType componentType = BOMComponentType.ofCode(bomLine.getComponentType());
		return componentType.isCoProduct();
	}

	public boolean isByProduct(@NonNull final I_PP_Order_BOMLine bomLine)
	{
		final BOMComponentType componentType = BOMComponentType.ofCode(bomLine.getComponentType());
		return componentType.isByProduct();
	}

	public boolean isCoOrByProduct(@NonNull final I_PP_Order_BOMLine bomLine)
	{
		final BOMComponentType componentType = BOMComponentType.ofCode(bomLine.getComponentType());
		return componentType.isByOrCoProduct();
	}

	/**
	 * @return true if given BOM Line is a alternative/variant for a main component line
	 */
	public boolean isVariant(@NonNull final I_PP_Order_BOMLine bomLine)
	{
		final BOMComponentType componentType = BOMComponentType.ofCode(bomLine.getComponentType());
		return componentType.isVariant();
	}

	public boolean isReceipt(@NonNull final I_PP_Order_BOMLine bomLine)
	{
		return BOMComponentType.ofCode(bomLine.getComponentType())
				.isReceipt();
	}

	public boolean isMethodChangeVariance(@NonNull final I_PP_Order_BOMLine bomLine)
	{
		// If QtyBatch and QtyBOM is zero, than this is a method variance
		// (a product that "was not" in BOM was used)
		return bomLine.getQtyBatch().signum() == 0 && bomLine.getQtyBOM().signum() == 0;
	}

	/**
	 * Asserts given <code>bomLine</code> is receipt.
	 *
	 * @throws MrpException if BOM Line is not of type receipt (see {@link #isReceipt(I_PP_Order_BOMLine)}).
	 * @see #isReceipt(I_PP_Order_BOMLine)
	 */
	public void assertReceipt(@NonNull final I_PP_Order_BOMLine bomLine)
	{
		Check.assume(isReceipt(bomLine), MrpException.class, "BOM Line shall be of type receipt: {}", bomLine);
	}

	public void assertIssue(@NonNull final I_PP_Order_BOMLine bomLine)
	{
		Check.assume(!isReceipt(bomLine), MrpException.class, "BOM Line shall be of type issue: {}", bomLine);
	}

	public boolean isComponent(@NonNull final I_PP_Order_BOMLine bomLine)
	{
		final BOMComponentType componentType = BOMComponentType.ofCode(bomLine.getComponentType());
		return componentType.isComponentOrPacking();
	}

	public I_PP_Product_BOMLine getProductBomLine(@NonNull final PPOrderLine ppOrderLinePojo)
	{
		return InterfaceWrapperHelper.create(Env.getCtx(), ppOrderLinePojo.getPpOrderLineData().getProductBomLineId(), I_PP_Product_BOMLine.class, ITrx.TRXNAME_None);
	}

	/**
	 * Verifies that the given three parameters fit together.
	 *
	 * @param ppOrderProductId     the {@code M_Product_ID} of a given ppOrder
	 * @param ppOrderStartSchedule the {@code M_Product_ID} of a given ppOrder
	 * @param ppOrderProductBOM    the {@code DateStartSchedule} of a given ppOrder
	 */
	public I_PP_Product_BOM verifyProductBOMAndReturnIt(
			@NonNull final ProductId ppOrderProductId,
			@NonNull final Date ppOrderStartSchedule,
			@NonNull final I_PP_Product_BOM ppOrderProductBOM)
	{
		// Product BOM should be completed
		if (!DocStatus.ofCode(ppOrderProductBOM.getDocStatus()).isCompleted())
		{
			throw new MrpException(StringUtils.formatMessage(
					"Product BOM is not completed; PP_Product_BOM={}",
					ppOrderProductBOM));
		}

		// Product from Order should be same as product from BOM - teo_sarca [ 2817870 ]
		if (ppOrderProductId.getRepoId() != ppOrderProductBOM.getM_Product_ID())
		{
			throw new MrpException("@NotMatch@ @PP_Product_BOM_ID@ , @M_Product_ID@");
		}

		// Product BOM Configuration should be verified - teo_sarca [ 2817870 ]
		final IProductDAO productsRepo = Services.get(IProductDAO.class);
		final I_M_Product product = productsRepo.getById(ppOrderProductBOM.getM_Product_ID());
		if (!product.isVerified())
		{
			throw new MrpException(
					StringUtils.formatMessage(
							"Product with Value={} of Product BOM Configuration not verified; PP_Product_BOM={}; product={}",
							product.getValue(), ppOrderProductBOM, product));
		}

		//
		// Create BOM Head
		final IProductBOMBL productBOMBL = Services.get(IProductBOMBL.class);
		if (!productBOMBL.isValidFromTo(ppOrderProductBOM, ppOrderStartSchedule))
		{
			throw new BOMExpiredException(ppOrderProductBOM, ppOrderStartSchedule);
		}

		return ppOrderProductBOM;
	}

	public static void updateBOMLineWarehouseAndLocatorFromOrder(
			@NonNull final I_PP_Order_BOMLine orderBOMLine,
			@NonNull final I_PP_Order fromOrder)
	{
		orderBOMLine.setAD_Org_ID(fromOrder.getAD_Org_ID());
		orderBOMLine.setM_Warehouse_ID(fromOrder.getM_Warehouse_ID());
		orderBOMLine.setM_Locator_ID(fromOrder.getM_Locator_ID());
	}

	/**
	 * @return {@code true} if the respective ppOrder's matching product planning exists and has {@code PP_Product_Planning.IsPickDirectlyIfFeasible='Y'}
	 */
	public boolean pickIfFeasible(@NonNull final PPOrderData ppOrderData)
	{
		final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);

		final ProductId productId = ProductId.ofRepoId(ppOrderData.getProductDescriptor().getProductId());
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(ppOrderData.getProductDescriptor().getAttributeSetInstanceId());

		final IProductPlanningDAO.ProductPlanningQuery productPlanningQuery = IProductPlanningDAO.ProductPlanningQuery.builder()
				.orgId(ppOrderData.getClientAndOrgId().getOrgId())
				.warehouseId(ppOrderData.getWarehouseId())
				.plantId(ppOrderData.getPlantId())
				.productId(productId)
				.includeWithNullProductId(false)
				.attributeSetInstanceId(asiId)
				.build();

		return productPlanningDAO.find(productPlanningQuery)
				.map(ProductPlanning::isPickDirectlyIfFeasible)
				.orElse(false);
	}
}
