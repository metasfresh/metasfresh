package de.metas.material.planning.event;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.material.planning.IMRPNoteBuilder;
import de.metas.material.planning.IMRPNotesCollector;
import de.metas.material.planning.ProductPlanningBL;

/*
 * #%L
 * metasfresh-material-planning
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class,
		ShutdownListener.class,
		ProductPlanningBL.class })
public class SupplyRequiredHandlerUtilsTest
{

	@Test
	public void collectNote_avoids_stackoverflowerror()
	{
		final IMRPNotesCollector mrpNotesCollector = SupplyRequiredHandlerUtils.mkMRPNotesCollector();
		final IMRPNoteBuilder mrpNoteBuilder = mrpNotesCollector.newMRPNoteBuilder(null, "mrpErrorCode");
		mrpNotesCollector.collectNote(mrpNoteBuilder);
	}

}
