package de.metas.handlingunits.pporder.api;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.material.planning.pporder.DraftPPOrderQuantities;
import de.metas.uom.IUOMDAO;
import de.metas.util.ISingletonService;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.PPOrderId;

import java.util.List;
import java.util.Set;

/*
 * #%L
 * de.metas.handlingunits.base
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

public interface IHUPPOrderQtyBL extends ISingletonService
{
	void reverseDraftCandidate(I_PP_Order_Qty candidate);

	static I_C_UOM extractUOM(@NonNull final I_PP_Order_Qty ppOrderQty)
	{
		final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
		return uomDAO.getById(ppOrderQty.getC_UOM_ID());
	}

	DraftPPOrderQuantities getDraftPPOrderQuantities(@NonNull PPOrderId ppOrderId);

	void updateDraftReceiptCandidate(@NonNull UpdateDraftReceiptCandidateRequest request);

	boolean isFinishedGoodsReceipt(@NonNull final I_PP_Order_Qty ppOrderQty);

	Set<HuId> getFinishedGoodsReceivedHUIds(@NonNull Set<PPOrderId> ppOrderIds);

	Set<HuId> getFinishedGoodsReceivedHUIds(@NonNull PPOrderId ppOrderId);

	void setNewLUAndSave(@NonNull List<I_PP_Order_Qty> candidates, @NonNull HuId newLUId);
}
