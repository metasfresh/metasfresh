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
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;

import de.metas.dunning.model.I_C_DunningDoc_Line_Source;
import de.metas.dunning.model.I_C_Dunning_Candidate;

@Validator(I_C_DunningDoc_Line_Source.class)
public class C_DunningDoc_Line_Source
{
	/**
	 * When we are deleting the line make sure we uncheck the Processed flag from candidate
	 * 
	 * @param source
	 */
	@ModelChange(timings = ModelValidator.TYPE_AFTER_DELETE)
	public void unprocessCandidate(final I_C_DunningDoc_Line_Source source)
	{
		final I_C_Dunning_Candidate candidate = source.getC_Dunning_Candidate();
		candidate.setProcessed(false);
		InterfaceWrapperHelper.save(candidate);
	}
}
