package org.adempiere.mm.attributes.listeners.expiry;

import java.util.List;

import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.IModelAttributeSetInstanceListener;

import com.google.common.collect.ImmutableList;

import de.metas.order.grossprofit.model.I_C_OrderLine;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.fresh.base
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

public class OrderLineMonthsUntilExpiryModelASIListener implements IModelAttributeSetInstanceListener
{
	private final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);

	private static final ImmutableList<String> SOURCE_COLUMN_NAMES = ImmutableList.of(I_C_OrderLine.COLUMNNAME_M_Product_ID);

	@Override
	public String getSourceTableName()
	{
		return I_C_OrderLine.Table_Name;
	}

	@Override
	public List<String> getSourceColumnNames()
	{
		return SOURCE_COLUMN_NAMES;
	}

	@Override
	public void modelChanged(final Object model)
	{
		attributeSetInstanceBL.updateASIAttributeFromModel(AttributeConstants.ATTR_MonthsUntilExpiry, model);
	}
}
