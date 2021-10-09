/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.fresh.product;

import com.google.common.collect.ImmutableList;
import de.metas.vertical.healthcare.alberta.model.I_M_Product_AlbertaArticle;
import de.metas.vertical.healthcare.alberta.model.I_M_Product_AlbertaBillableTherapy;
import de.metas.vertical.healthcare.alberta.model.I_M_Product_AlbertaPackagingUnit;
import de.metas.vertical.healthcare.alberta.model.I_M_Product_AlbertaTherapy;
import org.adempiere.model.CopyRecordSupportTableInfo;
import org.adempiere.model.GeneralCopyRecordSupport;
import org.compiere.model.I_M_Product_Acct;
import org.compiere.model.I_M_Product_Allergen;
import org.compiere.model.I_M_Product_Nutrition;
import org.compiere.model.PO;

import java.util.List;

public class ProductPOCopyRecordSupport extends GeneralCopyRecordSupport
{
	@Override
	public List<CopyRecordSupportTableInfo> getSuggestedChildren(final PO po, final List<CopyRecordSupportTableInfo> suggestedChildren)
	{
		return super.getSuggestedChildren(po, suggestedChildren)
				.stream()
				.filter(childTableInfo -> I_M_Product_Acct.Table_Name.equals(childTableInfo.getTableName())
						|| I_M_Product_AlbertaArticle.Table_Name.equals(childTableInfo.getTableName())
						|| I_M_Product_AlbertaBillableTherapy.Table_Name.equals(childTableInfo.getTableName())
						|| I_M_Product_AlbertaTherapy.Table_Name.equals(childTableInfo.getTableName())
						|| I_M_Product_AlbertaPackagingUnit.Table_Name.equals(childTableInfo.getTableName())
						|| I_M_Product_Allergen.Table_Name.equals(childTableInfo.getTableName())
						|| I_M_Product_Nutrition.Table_Name.equals(childTableInfo.getTableName())
				)
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * Other than the default, we also permit {@code M_Product_Acct} to be copied
	 */
	protected boolean isCopyTable(final String tableName)
	{
		return I_M_Product_Acct.Table_Name.equals(tableName) || super.isCopyTable(tableName);
	}
}
