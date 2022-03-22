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

import de.metas.edi.api.IDesadvDAO;
import de.metas.edi.model.I_C_OrderLine;
import de.metas.edi.model.I_M_InOutLine;
import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.esb.edi.model.I_EDI_DesadvLine_Pack;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.util.List;

@Interceptor(I_EDI_DesadvLine_Pack.class)
@Component
public class EDI_DesadvLine_Pack
{
	private final IDesadvDAO desadvDAO = Services.get(IDesadvDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Makes sure that the sum of all {@code EDI_DesadvLine_Pack.MovementQty}
	 * values is not bigger than the respective {@code EDI_DesadvLine}'s {code QtyDeliveredInStockingUOM}.
	 * Note that the business logic first sets the desadv-line's value and the desadv-line-pack's value.
	 * Also note that we ignore packs that have no inoutline-id, because they might be there
	 * when a SSCC-label is created before the delivery.
	 * However they are not yet considered in the desadv-line's value.
	 */
	@ModelChange(
			timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = { I_EDI_DesadvLine_Pack.COLUMNNAME_MovementQty, I_EDI_DesadvLine_Pack.COLUMNNAME_M_InOutLine_ID })
	public void validateMovementQtySum(final I_EDI_DesadvLine_Pack desadvLinePack)
	{
		if (desadvLinePack.getM_InOutLine_ID() <= 0)
		{
			return; // nothing to check; this pack's qty is not yet supposed to count for the line's QtyDelivered.
		}

		final BigDecimal otherLinePacksSum = queryBL.createQueryBuilder(I_EDI_DesadvLine_Pack.class)
				.addOnlyActiveRecordsFilter()
				.addNotEqualsFilter(I_EDI_DesadvLine_Pack.COLUMN_EDI_DesadvLine_Pack_ID, desadvLinePack.getEDI_DesadvLine_Pack_ID())
				.addEqualsFilter(I_EDI_DesadvLine_Pack.COLUMN_EDI_DesadvLine_ID, desadvLinePack.getEDI_DesadvLine_ID())
				.addNotEqualsFilter(I_EDI_DesadvLine_Pack.COLUMNNAME_M_InOutLine_ID, null)
				.create()
				.aggregate(I_EDI_DesadvLine_Pack.COLUMNNAME_MovementQty, IQuery.Aggregate.SUM, BigDecimal.class);

		final BigDecimal allLinePacksSum = otherLinePacksSum.add(desadvLinePack.getMovementQty());
		final BigDecimal lineSum = desadvLinePack.getEDI_DesadvLine().getQtyDeliveredInStockingUOM();

		if (allLinePacksSum.compareTo(lineSum) > 0)
		{
			throw new AdempiereException("EDI_DesadvLine.QtyDeliveredInStockingUOM=" + lineSum + ",but the sum of all EDI_DesadvLine_Pack.MovementQtys=" + allLinePacksSum)
					.appendParametersToMessage()
					.setParameter("EDI_DesadvLine_Pack_ID", desadvLinePack.getEDI_DesadvLine_Pack_ID())
					.setParameter("EDI_DesadvLine_ID", desadvLinePack.getEDI_DesadvLine_ID());
		}

	}
}
