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
import de.metas.copy_with_details.CopyRecordSupportTableInfo;
import de.metas.copy_with_details.GeneralCopyRecordSupport;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import lombok.NonNull;
import org.compiere.model.PO;
import org.compiere.model.copy.ValueToCopy;

import java.util.List;

public class MaterialTrackingPOCopyRecordSupport extends GeneralCopyRecordSupport
{
	private final static ImmutableSet<String> COLUMNS_TO_SUFFIX = ImmutableSet.of(I_M_Material_Tracking.COLUMNNAME_Lot);
	private final static String CLONED_SUFFIX = "_cloned";

	@Override
	protected ValueToCopy getValueToCopy_Before(@NonNull final PO to, @NonNull final PO from, @NonNull final String columnName)
	{
		if (COLUMNS_TO_SUFFIX.contains(columnName))
		{
			final String oldValue = String.valueOf(from.get_Value(columnName));
			return ValueToCopy.explicitValueToSet(oldValue.concat(CLONED_SUFFIX));
		}
		else
		{
			return ValueToCopy.NOT_SPECIFIED;
		}
	}

	@Override
	public List<CopyRecordSupportTableInfo> getSuggestedChildren(final PO po)
	{
		//cloning without children
		return ImmutableList.of();
	}

}
