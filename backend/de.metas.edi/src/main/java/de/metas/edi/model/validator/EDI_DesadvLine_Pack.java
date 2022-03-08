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
	
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void onDesadvLineDelete(final I_EDI_DesadvLine_Pack desadvLinePack)
	{
		Services.get(IQueryBL.class).createQueryBuilder(I_EDI_DesadvLine_Pack.class)
				.addOnlyActiveRecordsFilter()
				.addNotEqualsFilter(I_EDI_DesadvLine_Pack.COLUMN_EDI_DesadvLine_Pack_ID,desadvLinePack.getEDI_DesadvLine_Pack_ID())
				.addEqualsFilter(I_EDI_DesadvLine_Pack.COLUMN_EDI_DesadvLine_ID,desadvLinePack.getEDI_DesadvLine_ID() )
				.create()
				.aggregate(I_EDI_DesadvLine_Pack.COLUMNNAME_MovementQty, IQuery.Aggregate.SUM, BigDecimal.class);
		
		// TODO
		// add desadvLinePack's MovementQty
		// fail if the sum is bigger than the EDI_DesadvLine's movementQty
		
	}
}
