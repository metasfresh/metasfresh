package de.metas.rfq.model.interceptor;

import java.sql.Timestamp;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;
import org.compiere.util.TimeUtil;

import de.metas.rfq.IRfqBL;
import de.metas.rfq.model.I_C_RfQResponseLine;
import de.metas.rfq.model.I_C_RfQResponseLineQty;

/*
 * #%L
 * de.metas.rfq
 * %%
 * Copyright (C) 2016 metas GmbH
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

@Interceptor(I_C_RfQResponseLineQty.class)
public class C_RfQResponseLineQty
{
	public static final ModelDynAttributeAccessor<I_C_RfQResponseLineQty, Boolean> DYNATTR_DisableResponseLineUpdate //
	= new ModelDynAttributeAccessor<>(I_C_RfQResponseLineQty.class.getName() + "#DisableResponseLineUpdate", Boolean.class);

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE } //
	, ifColumnsChanged = I_C_RfQResponseLineQty.COLUMNNAME_DatePromised)
	public void validateDatePromised(final I_C_RfQResponseLineQty rfqResponseLineQty)
	{
		final Timestamp datePromised = rfqResponseLineQty.getDatePromised();
		if (datePromised == null)
		{
			throw new FillMandatoryException(I_C_RfQResponseLineQty.COLUMNNAME_DatePromised);
		}

		final I_C_RfQResponseLine rfqResponseLine = rfqResponseLineQty.getC_RfQResponseLine();
		final Timestamp dateWorkStart = TimeUtil.truncToDay(rfqResponseLine.getDateWorkStart());
		final Timestamp dateWorkComplete = TimeUtil.truncToDay(rfqResponseLine.getDateWorkComplete());
		if (!TimeUtil.isBetween(datePromised, dateWorkStart, dateWorkComplete))
		{
			throw new AdempiereException("@Invalid@ @DatePromised@: " + datePromised + " (" + dateWorkStart + " - " + dateWorkComplete + ")");
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_DELETE } //
	, ifColumnsChanged = I_C_RfQResponseLineQty.COLUMNNAME_QtyPromised)
	public void updateC_RfQResponseLine_QtyPromised(final I_C_RfQResponseLineQty rfqResponseLineQty)
	{
		if (isAllowResponseLineUpdate(rfqResponseLineQty))
		{
			final I_C_RfQResponseLine rfqResponseLine = rfqResponseLineQty.getC_RfQResponseLine();
			Services.get(IRfqBL.class).updateQtyPromisedAndSave(rfqResponseLine);
		}
	}

	public static void disableResponseLineUpdate(final I_C_RfQResponseLineQty rfqResponseLineQty)
	{
		DYNATTR_DisableResponseLineUpdate.setValue(rfqResponseLineQty, Boolean.TRUE);
	}

	private static boolean isAllowResponseLineUpdate(final I_C_RfQResponseLineQty rfqResponseLineQty)
	{
		return !DYNATTR_DisableResponseLineUpdate.isSet(rfqResponseLineQty);
	}
}
