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


import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;

import de.metas.document.refid.api.IReferenceNoDAO;
import de.metas.document.refid.model.I_C_ReferenceNo_Type;
import de.metas.document.refid.model.I_C_ReferenceNo_Type_Table;
import de.metas.util.Check;
import de.metas.util.Services;

@Validator(I_C_ReferenceNo_Type.class)
public class C_ReferenceNo_Type
{
	private final Main parent;

	protected C_ReferenceNo_Type(Main parent)
	{
		Check.assume(parent != null, "parent is not null");
		this.parent = parent;
	}
	
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void removeTableAssignments(I_C_ReferenceNo_Type type)
	{
		for (I_C_ReferenceNo_Type_Table assignment : Services.get(IReferenceNoDAO.class).retrieveTableAssignments(type))
		{
			InterfaceWrapperHelper.delete(assignment);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_DELETE })
	public void updateInstanceValidator(I_C_ReferenceNo_Type type)
	{
		parent.updateInstanceValidator(type);
	}
}
