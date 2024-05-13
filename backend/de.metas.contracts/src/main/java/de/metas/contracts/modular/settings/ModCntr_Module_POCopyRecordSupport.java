package de.metas.contracts.modular.settings;

import de.metas.contracts.model.I_ModCntr_Module;
import org.adempiere.model.GeneralCopyRecordSupport;
import org.compiere.model.PO;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class ModCntr_Module_POCopyRecordSupport extends GeneralCopyRecordSupport
{
	@Override
	public void updateSpecialColumnsName(final PO to)
	{
		// DO NOTHING here
	}

	@Override
	protected void onRecordCopied(final PO to, final PO from)
	{
		if (to.get_ColumnIndex(I_ModCntr_Module.COLUMNNAME_Processed) >= 0)
		{
			to.set_CustomColumn(I_ModCntr_Module.COLUMNNAME_Processed, from.get_Value(I_ModCntr_Module.COLUMNNAME_Processed));
		}
	}
}
