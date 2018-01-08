package de.metas.ui.web.material.cockpit;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.IQuery;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import de.metas.fresh.model.I_X_MRP_ProductInfo_Detail_MV;
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.ui.web.material.cockpit.filters.MaterialCockpitFilters;

/*
 * #%L
 * metasfresh-webui-api
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class MaterialCockpitFiltersTest
{

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void createQuery_when_emptyFilters_then_retrieve_nothing()
	{
		final I_X_MRP_ProductInfo_Detail_MV record = newInstance(I_X_MRP_ProductInfo_Detail_MV.class);
		save(record);

		final IQuery<I_MD_Cockpit> query = new MaterialCockpitFilters().createQuery(ImmutableList.of());
		assertThat(query).isNotNull();
		assertThat(query.list()).isEmpty();
	}

}
