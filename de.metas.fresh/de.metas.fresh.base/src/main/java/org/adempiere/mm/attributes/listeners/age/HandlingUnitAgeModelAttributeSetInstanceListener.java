package org.adempiere.mm.attributes.listeners.age;

import com.google.common.collect.ImmutableList;
import de.metas.order.grossprofit.model.I_C_OrderLine;
import org.adempiere.mm.attributes.api.IModelAttributeSetInstanceListener;
import org.adempiere.mm.attributes.api.impl.AgeAttributeUpdater;

import java.util.List;

/*
 * #%L
 * metasfresh-pharma
 * %%
 * Copyright (C) 2018 metas GmbH
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
 * Introduced with https://github.com/metasfresh/metasfresh/issues/4768
 * See documentation of the problem in https://github.com/metasfresh/metasfresh/issues/4012
 * TODO: Solve this problem from the root and get rid of these particular listeners
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class HandlingUnitAgeModelAttributeSetInstanceListener implements IModelAttributeSetInstanceListener
{
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
	public void modelChanged(Object model)
	{
		new AgeAttributeUpdater(model).updateASI();
	}
}
