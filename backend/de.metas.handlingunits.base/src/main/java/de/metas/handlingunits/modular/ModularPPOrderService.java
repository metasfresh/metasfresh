/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.handlingunits.modular;

import com.google.common.collect.ImmutableList;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.i18n.AdMessageKey;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_M_Warehouse;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ModularPPOrderService
{
	private static final AdMessageKey MSG_NotModularOrder = AdMessageKey.of("NotModularOrder");
	private static final AdMessageKey MSG_NoContractModularOrder = AdMessageKey.of("NoContractModularOrder");
	private static final AdMessageKey MSG_NotModularContract = AdMessageKey.of("NotModularContract");
	private static final AdMessageKey MSG_ModularContractDifferentBPartners = AdMessageKey.of("ModularContractDifferentBPartners");
	private static final AdMessageKey MSG_OneProductEligibleToBeIssued = AdMessageKey.of("OneProductEligibleToBeIssued");
	private static final AdMessageKey MSG_ManufacturingModularContractDifferentProducts = AdMessageKey.of("ManufacturingModularContractDifferentProducts");

	private final IPPOrderBL orderBL = Services.get(IPPOrderBL.class);
	private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IPPOrderBOMDAO orderBOMDAO = Services.get(IPPOrderBOMDAO.class);

	public void validateModularOrder(@NonNull final PPOrderId ppOrderId)
	{
		final I_PP_Order ppOrderRecord = orderBL.getById(ppOrderId);

		final DocTypeId docTypeId = DocTypeId.ofRepoId(ppOrderRecord.getC_DocTypeTarget_ID());
		if (!docTypeBL.isModularManufacturingOrder(docTypeId))
		{
			throw new AdempiereException(MSG_NotModularOrder)
					.appendParametersToMessage()
					.setParameter("PP_Order_ID", ppOrderRecord.getPP_Order_ID())
					.markAsUserValidationError();
		}

		final FlatrateTermId modularFlatrateTermId = FlatrateTermId.ofRepoIdOrNull(ppOrderRecord.getModular_Flatrate_Term_ID());
		if (modularFlatrateTermId == null)
		{
			throw new AdempiereException(MSG_NoContractModularOrder)
					.appendParametersToMessage()
					.setParameter("PP_Order_ID", ppOrderRecord.getPP_Order_ID())
					.markAsUserValidationError();
		}

		if (!flatrateBL.isModularContract(modularFlatrateTermId))
		{
			throw new AdempiereException(MSG_NotModularContract)
					.appendParametersToMessage()
					.setParameter("PP_Order_ID", ppOrderRecord.getPP_Order_ID())
					.setParameter("C_Flatrate_Term_ID", modularFlatrateTermId.getRepoId())
					.markAsUserValidationError();
		}

		final I_C_Flatrate_Term modularContract = flatrateBL.getById(modularFlatrateTermId);
		if (!isMatchingWarehouse(ppOrderRecord, modularContract))
		{
			throw new AdempiereException(MSG_ModularContractDifferentBPartners)
					.appendParametersToMessage()
					.setParameter("M_Warehouse_ID", ppOrderRecord.getM_Warehouse_ID())
					.setParameter("C_Flatrate_Term_ID", modularContract.getC_Flatrate_Term_ID())
					.markAsUserValidationError();
		}

		final ImmutableList<I_PP_Order_BOMLine> linesEligibleToBeIssued = getLinesEligibleToBeIssued(ppOrderId);
		if (linesEligibleToBeIssued.size() != 1)
		{
			throw new AdempiereException(MSG_OneProductEligibleToBeIssued)
					.appendParametersToMessage()
					.setParameter("PP_Order_ID", ppOrderRecord.getPP_Order_ID())
					.markAsUserValidationError();
		}

		if (linesEligibleToBeIssued.get(0).getM_Product_ID() != modularContract.getM_Product_ID())
		{
			throw new AdempiereException(MSG_ManufacturingModularContractDifferentProducts)
					.appendParametersToMessage()
					.setParameter("PP_Order_ID", ppOrderRecord.getPP_Order_ID())
					.setParameter("Line M_Product_ID", linesEligibleToBeIssued.get(0).getM_Product_ID())
					.setParameter("Contract M_Product_ID", modularContract.getM_Product_ID())
					.markAsUserValidationError();
		}
	}

	@NonNull
	private ImmutableList<I_PP_Order_BOMLine> getLinesEligibleToBeIssued(@NonNull final PPOrderId ppOrderId)
	{
		return orderBOMDAO.retrieveOrderBOMLines(ppOrderId)
				.stream()
				.filter(ppOrderBomLine -> Optional.ofNullable(ppOrderBomLine.getComponentType())
						.flatMap(BOMComponentType::optionalOfNullableCode)
						.filter(BOMComponentType::isIssue)
						.isPresent())
				.collect(ImmutableList.toImmutableList());
	}

	private boolean isMatchingWarehouse(
			@NonNull final I_PP_Order ppOrderRecord,
			@NonNull final I_C_Flatrate_Term modularContract)
	{
		final I_M_Warehouse warehouseRecord = warehouseBL.getById(WarehouseId.ofRepoId(ppOrderRecord.getM_Warehouse_ID()));

		return modularContract.getBill_BPartner_ID() == warehouseRecord.getC_BPartner_ID();
	}
}
