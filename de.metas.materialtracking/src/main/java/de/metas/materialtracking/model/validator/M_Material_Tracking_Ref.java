package de.metas.materialtracking.model.validator;

/*
 * #%L
 * de.metas.materialtracking
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


import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import de.metas.materialtracking.model.I_M_Material_Tracking_Ref;
import de.metas.util.Services;

@Interceptor(I_M_Material_Tracking_Ref.class)
public class M_Material_Tracking_Ref
{
	private static final transient Logger logger = LogManager.getLogger(M_Material_Tracking_Ref.class);

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE },
			ignoreColumnsChanged = { I_M_Material_Tracking_Ref.COLUMNNAME_QtyIssued })
	public void onlyAllowQtyIssuedChanges(final I_M_Material_Tracking_Ref ref)
	{
		// TODO: implement public static Map<String, Pair<Object, Object>> getChanges(Object model) in InterfaceWrapperhelper and use it here
		new AdempiereException("Changes to " + ref + " are not allowed (only exception: QtyIssued).")
				.throwOrLogSevere(
						Services.get(IDeveloperModeBL.class).isEnabled(),
						logger);
	}
}
