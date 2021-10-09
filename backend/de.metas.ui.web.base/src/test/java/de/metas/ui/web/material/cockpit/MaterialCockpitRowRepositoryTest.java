package de.metas.ui.web.material.cockpit;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.material.cockpit.filters.MaterialCockpitFilters;
import de.metas.ui.web.material.cockpit.rowfactory.MaterialCockpitRowFactory;
import de.metas.ui.web.view.template.IRowsData;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class MaterialCockpitRowRepositoryTest
{
	private MaterialCockpitRowRepository materialCockpitRowRepository;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		materialCockpitRowRepository = new MaterialCockpitRowRepository(
				new MaterialCockpitFilters(),
				new MaterialCockpitRowFactory());
	}

	@Test
	public void retrieveRows_when_emptyFilters_then_return_nothing()
	{
		final IRowsData<MaterialCockpitRow> result = materialCockpitRowRepository.createRowsData(DocumentFilterList.EMPTY);
		assertThat(result.getAllRows()).isEmpty();
		assertThat(result.getTopLevelRows()).isEmpty();
	}

}
