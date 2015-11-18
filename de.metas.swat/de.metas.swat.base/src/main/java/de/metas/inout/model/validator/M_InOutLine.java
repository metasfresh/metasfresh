package de.metas.inout.model.validator;

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


import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.util.Services;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.ModelValidator;

import de.metas.inout.IInOutBL;

@Validator(I_M_InOutLine.class)
public class M_InOutLine
{
	public static final M_InOutLine INSTANCE = new M_InOutLine();

	private M_InOutLine()
	{
		super();
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void handleInOutLineDelete(final I_M_InOutLine iol)
	{
		Services.get(IInOutBL.class).deleteMatchInvsForInOutLine(iol); // task 08627
	}
}
