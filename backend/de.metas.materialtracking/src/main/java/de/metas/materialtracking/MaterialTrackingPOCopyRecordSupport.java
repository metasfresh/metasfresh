/*
 * #%L
 * de.metas.materialtracking
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.materialtracking;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import org.adempiere.model.CopyRecordSupportTableInfo;
import org.adempiere.model.GeneralCopyRecordSupport;
import org.compiere.model.PO;

import java.util.List;
import java.util.Set;

public class MaterialTrackingPOCopyRecordSupport extends GeneralCopyRecordSupport
{
	private final static Set<String> COLUMNS_TO_SUFFIX = ImmutableSet.of(I_M_Material_Tracking.COLUMNNAME_Lot);
	private final static String CLONED_SUFFIX = "_cloned";


	@Override
	public Object getValueToCopy(final PO to, final PO from, final String columnName)
	{
		if (COLUMNS_TO_SUFFIX.contains(columnName))
		{
			final String oldValue = String.valueOf(from.get_Value(columnName));
			return String.valueOf(oldValue).concat(CLONED_SUFFIX);
		}

		return super.getValueToCopy(to, from, columnName);
	}

	@Override
	public List<CopyRecordSupportTableInfo> getSuggestedChildren(final PO po, final List<CopyRecordSupportTableInfo> suggestedChildren)
	{
		//cloning without children
		return ImmutableList.of();
	}

}
