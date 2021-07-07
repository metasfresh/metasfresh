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

package de.metas.invoice.interceptor;

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_Invoice_Verification_RunLine;
import org.compiere.model.I_C_Invoice_Verification_SetLine;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_Invoice_Verification_SetLine.class)
@Component
public class C_Invoice_Verification_SetLine
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void deleteVerificationData(final I_C_Invoice_Verification_SetLine setLine)
	{
		queryBL.createQueryBuilder(I_C_Invoice_Verification_RunLine.class)
				.addEqualsFilter(I_C_Invoice_Verification_RunLine.COLUMNNAME_C_Invoice_Verification_SetLine_ID, setLine.getC_Invoice_Verification_SetLine_ID())
				.create()
				.delete();
	}
}
