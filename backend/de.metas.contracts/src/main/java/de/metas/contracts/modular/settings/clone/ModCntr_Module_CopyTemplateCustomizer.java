/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.contracts.modular.settings.clone;

import de.metas.contracts.model.I_ModCntr_Module;
import de.metas.copy_with_details.template.CopyTemplateCustomizer;
import org.compiere.model.POInfo;
import org.compiere.model.copy.ValueToCopy;
import org.springframework.stereotype.Component;

import static de.metas.contracts.model.I_ModCntr_Module.COLUMNNAME_Name;
import static de.metas.contracts.model.I_ModCntr_Module.COLUMNNAME_Processed;

@Component
public class ModCntr_Module_CopyTemplateCustomizer implements CopyTemplateCustomizer
{
	@Override
	public String getTableName()
	{
		return I_ModCntr_Module.Table_Name;
	}

	@Override
	public ValueToCopy extractValueToCopy(final POInfo poInfo, final String columnName)
	{
		if(COLUMNNAME_Processed.equals(columnName) || COLUMNNAME_Name.equals(columnName))
		{
			return ValueToCopy.DIRECT_COPY;
		}
		else
		{
			return ValueToCopy.NOT_SPECIFIED;
		}
	}
}
