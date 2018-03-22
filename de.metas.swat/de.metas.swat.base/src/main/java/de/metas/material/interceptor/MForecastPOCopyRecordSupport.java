package de.metas.material.interceptor;

import java.util.List;

import org.adempiere.model.GeneralCopyRecordSupport;
import org.adempiere.model.CopyRecordSupportTableInfo;
import org.compiere.model.I_M_ForecastLine;
import org.compiere.model.PO;

import com.google.common.collect.ImmutableList;


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
 * Enables copy-with-details from M_Forecast
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class MForecastPOCopyRecordSupport extends GeneralCopyRecordSupport
{


	@Override
	public List<CopyRecordSupportTableInfo> getSuggestedChildren(final PO po, final List<CopyRecordSupportTableInfo> suggestedChildren)
	{
		return super.getSuggestedChildren(po, suggestedChildren)
				.stream()
				.filter(childTableInfo -> I_M_ForecastLine.Table_Name.equals(childTableInfo.getTableName()))
				.collect(ImmutableList.toImmutableList());
	}

}
