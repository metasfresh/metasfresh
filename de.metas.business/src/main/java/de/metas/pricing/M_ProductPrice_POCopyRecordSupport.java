package de.metas.pricing;

import org.adempiere.model.GeneralCopyRecordSupport;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.PO;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class M_ProductPrice_POCopyRecordSupport extends GeneralCopyRecordSupport
{
	@Override
	public Object getValueToCopy(final PO to, final PO from, final String columnName)
	{
		if (I_M_ProductPrice.COLUMNNAME_IsInvalidPrice.equals(columnName))
		{
			return Boolean.TRUE;
		}
		else
		{
			return super.getValueToCopy(to, from, columnName);
		}
	}

}
