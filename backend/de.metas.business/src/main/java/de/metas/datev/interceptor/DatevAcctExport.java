/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.datev.interceptor;

import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_DatevAcctExport;
import org.compiere.model.I_DatevAcctExportLine;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_DatevAcctExport.class)
@Component
public class DatevAcctExport
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void onDeleteExport_DeleteLines(final I_DatevAcctExport datevAcctExport)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		queryBL.createQueryBuilder(I_DatevAcctExportLine.class)
				.addInArrayFilter(I_DatevAcctExportLine.COLUMNNAME_DatevAcctExport_ID, datevAcctExport.getDatevAcctExport_ID())
				.create()
				.delete();
	}

}
