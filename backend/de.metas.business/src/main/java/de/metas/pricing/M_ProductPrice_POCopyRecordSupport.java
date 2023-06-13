package de.metas.pricing;

import de.metas.copy_with_details.GeneralCopyRecordSupport;
import lombok.NonNull;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.PO;
import org.compiere.model.copy.ValueToCopy;

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
	protected ValueToCopy getValueToCopy_Before(@NonNull final PO to, @NonNull final PO from, @NonNull final String columnName)
	{
		if (I_M_ProductPrice.COLUMNNAME_IsInvalidPrice.equals(columnName))
		{
			return ValueToCopy.explicitValueToSet(Boolean.TRUE);
		}
		else
		{
			return ValueToCopy.NOT_SPECIFIED;
		}
	}

}
