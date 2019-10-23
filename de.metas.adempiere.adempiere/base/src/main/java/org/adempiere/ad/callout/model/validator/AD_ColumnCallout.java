package org.adempiere.ad.callout.model.validator;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.Properties;

import org.adempiere.ad.callout.api.IADColumnCalloutDAO;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_ColumnCallout;
import org.compiere.model.MColumn;
import org.compiere.model.ModelValidator;

import de.metas.util.Services;

@Validator(I_AD_ColumnCallout.class)
public class AD_ColumnCallout
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void setSeqNo(final I_AD_ColumnCallout columnCallout)
	{
		if (columnCallout.getSeqNo() != 0)
		{
			return;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(columnCallout);
		final int adColumnId = columnCallout.getAD_Column_ID();
		final int lastSeqNo = Services.get(IADColumnCalloutDAO.class).retrieveColumnCalloutLastSeqNo(ctx, adColumnId);
		final int seqNo = lastSeqNo + 10;
		columnCallout.setSeqNo(seqNo);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }
			, ifColumnsChanged = I_AD_ColumnCallout.COLUMNNAME_AD_Column_ID)
	public void setAD_Table_ID(final I_AD_ColumnCallout columnCallout)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(columnCallout);
		final String trxName = InterfaceWrapperHelper.getTrxName(columnCallout);
		final int adColumnId = columnCallout.getAD_Column_ID();
		final int AD_Table_ID = MColumn.getTable_ID(ctx, adColumnId, trxName);
		columnCallout.setAD_Table_ID(AD_Table_ID);
	}

}
