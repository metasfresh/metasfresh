/*
 * #%L
 * de.metas.materialtracking
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.materialtracking.model.validator;

import com.google.common.collect.ImmutableList;
import org.adempiere.model.CopyRecordSupportTableInfo;
import org.adempiere.model.GeneralCopyRecordSupport;
import org.compiere.model.PO;

import java.util.List;

public class PP_OrderPOCopyRecordSupport extends GeneralCopyRecordSupport
{
	@Override
	public List<CopyRecordSupportTableInfo> getSuggestedChildren(final PO po, final List<CopyRecordSupportTableInfo> suggestedChildren)
	{
		 // TODO tbp: i believe all tabs should be ignored, as all their content is created at order creation.
		//				if we clone the tabs as well, we shall have duplicates, some of which link to the previous order (clone source), not the cloned one
		return ImmutableList.of();
		// return super.getSuggestedChildren(po, suggestedChildren)
		// 		.stream()
		// 		.filter(childTableInfo -> I_PP_Order_BOM.Table_Name.equals(childTableInfo.getTableName())
		//
		// 				)
		//
		// 		.collect(ImmutableList.toImmutableList());
	}
}
