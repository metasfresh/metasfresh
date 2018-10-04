package de.metas.workflow.model.validator;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.ModelValidator;

import de.metas.util.Services;
import de.metas.workflow.api.IWorkflowBL;

@Interceptor(I_C_Invoice.class)
public class C_Invoice
{
	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void createDocResponsible(final I_C_Invoice invoice)
	{
		Services.get(IWorkflowBL.class).createDocResponsible(invoice, invoice.getAD_Org_ID());
	}
}
