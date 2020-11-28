/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.handlingunits.pporder.api.impl;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.pporder.api.CreateReceiptCandidateRequest;
import de.metas.handlingunits.pporder.api.IHUPPOrderQtyDAO;
import de.metas.handlingunits.pporder.api.PPOrderIssueServiceProductRequest;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.manufacturing.generatedcomponents.GeneratedComponentRequest;
import de.metas.manufacturing.generatedcomponents.ManufacturingComponentGeneratorService;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.ComponentIssueCreateRequest;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@ToString
class PPOrderIssueServiceProductCommand
{
	private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
	private final IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);
	private final IPPOrderBOMDAO ppOrderBOMDAO = Services.get(IPPOrderBOMDAO.class);
	private final IPPCostCollectorBL costCollectorBL = Services.get(IPPCostCollectorBL.class);
	private final IHUPPOrderQtyDAO ppOrderQtyDAO = Services.get(IHUPPOrderQtyDAO.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
	private final ManufacturingComponentGeneratorService manufacturingComponentGeneratorService;

	// Params
	private final PPOrderIssueServiceProductRequest request;
	private final ZonedDateTime date;

	// State
	private I_C_UOM uomEach;
	private IMutableHUContext huContext;
	private I_PP_Order _ppOrderRecord = null; // lazy
	private I_PP_Order_BOMLine _bomLineRecord = null; // lazy
	private Quantity _qtyToIssueForOneFinishedGood = null; // lazy

	@Builder
	private PPOrderIssueServiceProductCommand(
			@NonNull final ManufacturingComponentGeneratorService manufacturingComponentGeneratorService,
			@NonNull final PPOrderIssueServiceProductRequest request)
	{
		this.manufacturingComponentGeneratorService = manufacturingComponentGeneratorService;
		this.request = request;
		this.date = SystemTime.asZonedDateTime();
	}

	public void execute()
	{
		uomEach = uomDAO.getById(UomId.EACH);
		huContext = huContextFactory.createMutableHUContextForProcessing();

		if (getQtyToIssueForOneFinishedGood().intValueExact() <= 0)
		{
			throw new AdempiereException("Nothing to issue");
		}

		final List<I_PP_Order_Qty> finishedGoodsReceiveCandidates = ppOrderQtyDAO.retrieveOrderQtyForFinishedGoodsReceive(request.getPpOrderId());

		for (final I_PP_Order_Qty finishedGoodsReceiveCandidate : finishedGoodsReceiveCandidates)
		{
			final List<I_M_HU> singleItemHUs = splitToOneItemPerHU(finishedGoodsReceiveCandidate);

			for (final I_M_HU singleItemHU : singleItemHUs)
			{
				generateAndIssueTo(singleItemHU);
			}
		}
	}

	private void generateAndIssueTo(final I_M_HU singleItemHU)
	{
		final Quantity qtyToIssueForOneFinishedGood = getQtyToIssueForOneFinishedGood();

		final IAttributeStorage attributes = huContext.getHUAttributeStorageFactory().getAttributeStorage(singleItemHU);

		final ImmutableAttributeSet attributesToChange = manufacturingComponentGeneratorService.generate(GeneratedComponentRequest.builder()
				.productId(getComponentId())
				.qty(qtyToIssueForOneFinishedGood.intValueExact())
				.attributes(ImmutableAttributeSet.copyOf(attributes))
				.clientId(ClientId.ofRepoId(singleItemHU.getAD_Client_ID()))
				.build());

		if (attributesToChange.isEmpty())
		{
			return;
		}

		for (final AttributeCode attributeCode : attributesToChange.getAttributeCodes())
		{
			attributes.setValue(attributeCode, attributesToChange.getValue(attributeCode));
		}
		attributes.saveChangesIfNeeded();

		final I_PP_Order_BOMLine bomLineRecord = getBOMLineRecord();
		costCollectorBL.createIssue(ComponentIssueCreateRequest.builder()
				.orderBOMLine(bomLineRecord)
				.qtyIssue(qtyToIssueForOneFinishedGood)
				.locatorId(LocatorId.ofRepoId(bomLineRecord.getM_Warehouse_ID(), bomLineRecord.getM_Locator_ID()))
				.movementDate(date)
				.build());
	}

	private I_PP_Order getPPOrderRecord()
	{
		return _ppOrderRecord == null
				? _ppOrderRecord = ppOrderBL.getById(request.getPpOrderId())
				: _ppOrderRecord;
	}

	private I_PP_Order_BOMLine getBOMLineRecord()
	{
		return _bomLineRecord == null
				? _bomLineRecord = ppOrderBOMBL.getOrderBOMLineById(request.getPpOrderBOMLineId())
				: _bomLineRecord;
	}

	private ProductId getComponentId()
	{
		return ProductId.ofRepoId(getBOMLineRecord().getM_Product_ID());
	}

	private Quantity getQtyToIssueForOneFinishedGood()
	{
		return _qtyToIssueForOneFinishedGood == null
				? _qtyToIssueForOneFinishedGood = computeQtyToIssueForOneFinishedGood()
				: _qtyToIssueForOneFinishedGood;
	}

	private Quantity computeQtyToIssueForOneFinishedGood()
	{
		return ppOrderBOMBL.getQtyCalculationsBOM(getPPOrderRecord())
				.getLineByOrderBOMLineId(request.getPpOrderBOMLineId())
				.computeQtyRequired(Quantity.of(1, uomDAO.getById(UomId.EACH)));
	}

	private List<I_M_HU> splitToOneItemPerHU(final I_PP_Order_Qty finishedGoodsReceiveCandidate)
	{
		final ProductId finishedGoodProductId = ProductId.ofRepoId(finishedGoodsReceiveCandidate.getM_Product_ID());

		final HuId huId = HuId.ofRepoId(finishedGoodsReceiveCandidate.getM_HU_ID());
		final I_M_HU hu = handlingUnitsBL.getById(huId);

		final IHUStorageFactory huStorageFactory = huContext.getHUStorageFactory();
		final int huQty = huStorageFactory.getStorage(hu)
				.getQuantity(finishedGoodProductId, uomEach)
				.intValueExact();

		if (huQty <= 0)
		{
			// shall not happen but we can live with it
			return ImmutableList.of();
		}
		else if (huQty == 1)
		{
			// Our HU is in desired shape, i.e. one item per HU
			return ImmutableList.of(hu);
		}
		else  // huQty > 1
		{
			final ArrayList<I_M_HU> singleItemHUs = new ArrayList<>();

			// Extract CUs of 1 item each,
			// but leave one item in the original HU
			for (int i = 1; i <= huQty - 1; i++)
			{
				final I_M_HU extractedHU = CollectionUtils.singleElement(
						HUTransformService.newInstance(huContext)
								.husToNewCUs(HUTransformService.HUsToNewCUsRequest.builder()
										.sourceHU(hu)
										.productId(finishedGoodProductId)
										.qtyCU(Quantity.of(1, uomEach))
										.build()));

				singleItemHUs.add(extractedHU);

				ppOrderQtyDAO.save(CreateReceiptCandidateRequest.builder()
						.orderId(PPOrderId.ofRepoId(finishedGoodsReceiveCandidate.getPP_Order_ID()))
						.orgId(OrgId.ofRepoId(finishedGoodsReceiveCandidate.getAD_Org_ID()))
						.date(date)
						.locatorId(IHandlingUnitsBL.extractLocatorId(hu))
						.topLevelHUId(HuId.ofRepoId(extractedHU.getM_HU_ID()))
						.productId(finishedGoodProductId)
						.qtyToReceive(Quantity.of(1, uomEach))
						.build());
			}

			// add (as first item) our original HU which we assume it has only one item left into it
			singleItemHUs.add(0, hu);

			return singleItemHUs;
		}
	}
}
