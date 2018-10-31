package de.metas.document.refid.modelvalidator;

/*
 * #%L
 * de.metas.document.refid
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


import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.compiere.model.ModelValidator;

import de.metas.document.refid.api.IReferenceNoDAO;
import de.metas.document.refid.model.I_C_ReferenceNo;
import de.metas.document.refid.model.I_C_ReferenceNo_Doc;
import de.metas.util.Services;

@Validator(I_C_ReferenceNo.class)
public class C_ReferenceNo
{
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteReferenceNoDocs(final I_C_ReferenceNo referenceNo)
	{
		final IReferenceNoDAO dao = Services.get(IReferenceNoDAO.class);
		final List<I_C_ReferenceNo_Doc> docList = dao.retrieveAllDocAssignments(referenceNo);
		dao.removeDocAssignments(docList);
	}
}
