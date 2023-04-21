/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.edi.model.validator;

import de.metas.esb.edi.model.I_EDI_Desadv_Pack_Item;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.IQuery;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Interceptor(I_EDI_Desadv_Pack_Item.class)
@Component
public class EDI_Desadv_Pack_Item
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Makes sure that the sum of all {@code EDI_Desadv_Pack_Item.MovementQty}
	 * values is not bigger than the respective {@code EDI_DesadvLine}'s {code QtyDeliveredInStockingUOM}.
	 * Note that the business logic first sets the desadv-line's value and the desadv-line-pack's value.
	 * Also note that we ignore packs that have no inoutline-id, because they might be there
	 * when a SSCC-label is created before the delivery.
	 * However they are not yet considered in the desadv-line's value.
	 */
	@ModelChange(
			timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = { I_EDI_Desadv_Pack_Item.COLUMNNAME_MovementQty, I_EDI_Desadv_Pack_Item.COLUMNNAME_M_InOutLine_ID })
	public void validateMovementQtySum(final I_EDI_Desadv_Pack_Item desadvPackItem)
	{
		if (desadvPackItem.getM_InOutLine_ID() <= 0)
		{
			return; // nothing to check; this pack's qty is not yet supposed to count for the line's QtyDelivered.
		}

		final BigDecimal otherPackItemsForLineSum = queryBL.createQueryBuilder(I_EDI_Desadv_Pack_Item.class)
				.addOnlyActiveRecordsFilter()
				.addNotEqualsFilter(I_EDI_Desadv_Pack_Item.COLUMN_EDI_Desadv_Pack_Item_ID, desadvPackItem.getEDI_Desadv_Pack_Item_ID())
				.addEqualsFilter(I_EDI_Desadv_Pack_Item.COLUMN_EDI_DesadvLine_ID, desadvPackItem.getEDI_DesadvLine_ID())
				.addNotNull(I_EDI_Desadv_Pack_Item.COLUMNNAME_M_InOutLine_ID)
				.create()
				.aggregate(I_EDI_Desadv_Pack_Item.COLUMNNAME_MovementQty, IQuery.Aggregate.SUM, BigDecimal.class);

		final BigDecimal allLinePacksSum = otherPackItemsForLineSum.add(desadvPackItem.getMovementQty());
		final BigDecimal lineSum = desadvPackItem.getEDI_DesadvLine().getQtyDeliveredInStockingUOM();

		if (allLinePacksSum.compareTo(lineSum) > 0)
		{
			throw new AdempiereException("EDI_DesadvLine.QtyDeliveredInStockingUOM=" + lineSum + ",but the sum of all EDI_Desadv_Pack_Item.MovementQtys=" + allLinePacksSum)
					.appendParametersToMessage()
					.setParameter("EDI_Desadv_Pack_Item_ID", desadvPackItem.getEDI_Desadv_Pack_Item_ID())
					.setParameter("EDI_DesadvLine_ID", desadvPackItem.getEDI_DesadvLine_ID());
		}
	}
}
