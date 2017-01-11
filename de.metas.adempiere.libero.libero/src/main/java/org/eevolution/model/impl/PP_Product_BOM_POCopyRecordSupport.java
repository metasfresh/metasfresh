package org.eevolution.model.impl;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.model.GeneralCopyRecordSupport;
import org.adempiere.model.TableInfoVO;
import org.compiere.model.GridTab;
import org.compiere.model.PO;
import org.eevolution.model.I_PP_Product_BOMLine;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Enables copy-with-details from PP_Product_BOMs
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class PP_Product_BOM_POCopyRecordSupport extends GeneralCopyRecordSupport
{
	@Override
	public List<TableInfoVO> getSuggestedChildren(final PO po, final GridTab gridTab)
	{
		final List<TableInfoVO> list = super.getSuggestedChildren(po, gridTab);

		final List<TableInfoVO> finalList = new ArrayList<TableInfoVO>();
		for (final TableInfoVO childTableInfo : list)
		{
			// for the time being, PP_Product_BOMLine is the only accepted child.
			if (I_PP_Product_BOMLine.Table_Name.equals(childTableInfo.tableName))
			{
				finalList.add(childTableInfo);
			}
		}
		return finalList;
	}
}
