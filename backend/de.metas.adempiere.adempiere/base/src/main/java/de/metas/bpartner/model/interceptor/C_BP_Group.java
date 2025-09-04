/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.bpartner.model.interceptor;

import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.ModelValidator;
import org.compiere.util.DB;
import org.compiere.util.Trx;
import org.springframework.stereotype.Component;

@Interceptor(I_C_BP_Group.class)
@Component
public class C_BP_Group
{
	public static final int MAX_INHERITANCE_LEVELS = 2;

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = I_C_BP_Group.COLUMNNAME_Parent_BP_Group_ID)
	public void limitGroupInheritance(@NonNull final I_C_BP_Group bpGroup)
	{
		if (bpGroup.getParent_BP_Group_ID() == bpGroup.getC_BP_Group_ID())
		{
			throw new AdempiereException("@Invalid@ @Parent_BP_Group_ID@");
		}
		final int maxInheritanceLevels = DB.countMaxInheritanceLevels(Trx.TRXNAME_ThreadInherited, I_C_BP_Group.Table_Name, I_C_BP_Group.COLUMNNAME_Parent_BP_Group_ID);

		if (maxInheritanceLevels > MAX_INHERITANCE_LEVELS)
		{
			throw new AdempiereException("@Invalid@ @Parent_BP_Group_ID@");
		}

	}
}
