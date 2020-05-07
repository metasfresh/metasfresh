package de.metas.dunning.modelvalidator;

/*
 * #%L
 * de.metas.dunning
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


import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

import de.metas.document.IDocumentLocationBL;
import de.metas.dunning.api.impl.DunningDocDocumentLocationAdapter;
import de.metas.dunning.model.I_C_DunningDoc;
import de.metas.workflow.api.IWorkflowBL;

@Validator(I_C_DunningDoc.class)
public class C_DunningDoc
{
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }
			, ifColumnsChanged = I_C_DunningDoc.COLUMNNAME_Processed)
	public void createDocResponsible(final I_C_DunningDoc dunningDoc)
	{
		if (dunningDoc.isProcessed())
		{
			Services.get(IWorkflowBL.class).createDocResponsible(dunningDoc, dunningDoc.getAD_Org_ID());
		}
	}

	/**
	 * Updates <code>C_DunningDoc.BPartnerAddress</code> by calling {@link IDocumentLocationBL#setBPartnerAddress(org.adempiere.document.model.IDocumentLocation)} when the the
	 * <code>C_BPartner_Location_ID</code> column is changed.
	 *
	 * @param dunningDoc
	 * @task 07359
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }
			, ifColumnsChanged = I_C_DunningDoc.COLUMNNAME_C_BPartner_Location_ID)
	public void updateAddressField(final I_C_DunningDoc dunningDoc)
	{
		final IDocumentLocationBL documentLocationBL = Services.get(IDocumentLocationBL.class);
		documentLocationBL.setBPartnerAddress(new DunningDocDocumentLocationAdapter(dunningDoc));
	}
}
