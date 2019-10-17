package org.adempiere.mm.attributes.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.AttributeSetId;
import org.adempiere.mm.attributes.api.IAttributeExcludeDAO;
import org.adempiere.model.I_M_AttributeSetExcludeLine;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_M_AttributeSetExclude;

import de.metas.lang.SOTrx;
import de.metas.util.Services;
import lombok.NonNull;

public class AttributeExcludeDAO implements IAttributeExcludeDAO
{

	@Override
	public List<I_M_AttributeSetExcludeLine> retrieveLines(I_M_AttributeSetExclude attributeSetExclude)
	{
		if (attributeSetExclude == null)
		{
			return null;
		}

		return Services.get(IQueryBL.class).createQueryBuilder(I_M_AttributeSetExcludeLine.class, attributeSetExclude)
				.addEqualsFilter(I_M_AttributeSetExcludeLine.COLUMNNAME_M_AttributeSetExclude_ID, attributeSetExclude.getM_AttributeSetExclude_ID())
				.addOnlyActiveRecordsFilter()
				.create()
				.list(I_M_AttributeSetExcludeLine.class);

	}

	@Override
	public I_M_AttributeSetExclude retrieveAttributeSetExclude(
			@NonNull final AttributeSetId attributeSetId, 
			final int columnId, 
			@NonNull final SOTrx soTrx)
	{
		if (columnId <= 0)
		{
			return null;
		}
		
		final I_AD_Column column = loadOutOfTrx(columnId, I_AD_Column.class);
		// guard against null, when column was not found
		if (column == null)
		{
			return null;
		}
		final int adTableId = column.getAD_Table_ID();
		
		return Services.get(IQueryBL.class).createQueryBuilderOutOfTrx(I_M_AttributeSetExclude.class)
				.addEqualsFilter(I_M_AttributeSetExclude.COLUMNNAME_AD_Table_ID, adTableId)
				.addEqualsFilter(I_M_AttributeSetExclude.COLUMNNAME_IsSOTrx, soTrx.toBoolean())
				.addEqualsFilter(I_M_AttributeSetExclude.COLUMNNAME_M_AttributeSet_ID, attributeSetId)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_M_AttributeSetExclude.class);
	}

}
