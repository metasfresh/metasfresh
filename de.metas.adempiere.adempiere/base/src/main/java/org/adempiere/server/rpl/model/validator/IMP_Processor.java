package org.adempiere.server.rpl.model.validator;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
import org.adempiere.server.rpl.IImportProcessor;
import org.adempiere.server.rpl.api.IIMPProcessorBL;
import org.adempiere.server.rpl.api.IIMPProcessorDAO;
import org.compiere.model.I_IMP_Processor;
import org.compiere.model.ModelValidator;

import de.metas.util.Services;

@Validator(I_IMP_Processor.class)
public class IMP_Processor
{
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteParametersAndLogs(final I_IMP_Processor impProcessor)
	{
		final IIMPProcessorDAO dao = Services.get(IIMPProcessorDAO.class);
		
		dao.deleteParameters(impProcessor);
		dao.deleteLogs(impProcessor, true); // deleteAll=true
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }
			, ifColumnsChanged = I_IMP_Processor.COLUMNNAME_IMP_Processor_Type_ID)
	public void recreateParameters(final I_IMP_Processor impProcessor)
	{
		final IIMPProcessorDAO dao = Services.get(IIMPProcessorDAO.class);
		
		dao.deleteParameters(impProcessor);
		final IImportProcessor proc = Services.get(IIMPProcessorBL.class).getIImportProcessor(impProcessor);
		proc.createInitialParameters(impProcessor);
	}
}
