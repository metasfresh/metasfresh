package de.metas.ui.web.handlingunits;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.reservation.HUReservationRepository;

/*
 * #%L
 * metasfresh-webui-api
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class, ShutdownListener.class, HUReservationRepository.class })
public class HUEditorRowFiltersTest
{

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void toHUQueryBuilderPart()
	{
		final I_M_HU huRecord = newInstance(I_M_HU.class);
		huRecord.setM_Locator_ID(20);
		saveRecord(huRecord);


		final HUEditorRowFilter allFilter = HUEditorRowFilter.ALL;
		final IHUQueryBuilder huQueryBuilderPart = HUEditorRowFilters.toHUQueryBuilderPart(allFilter);
		assertThat(huQueryBuilderPart.first()).isNotNull();
		assertThat(huQueryBuilderPart.first().getM_HU_ID()).isEqualTo(huRecord.getM_HU_ID());
	}
}
