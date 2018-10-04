package de.metas.adempiere.addon;

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



import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.metas.adempiere.service.IPackagingBL;
import de.metas.adempiere.service.impl.PackagingBL;
import de.metas.inoutcandidate.api.IPackagingDAO;
import de.metas.inoutcandidate.api.impl.PackagingDAO;
import de.metas.util.Services;

public class PackingAddonTest {

	/**
	 * Checks if the right services are registered
	 */
	@Test
	public void test()
	{
		// note: the addon itself has been removed, but this test shall verify that the services are still registered (via autodetection)
		assertNotNull(Services.get(IPackagingBL.class));
		assertTrue(Services.get(IPackagingBL.class) instanceof PackagingBL);
		
		assertNotNull(Services.get(IPackagingDAO.class));
		assertTrue(Services.get(IPackagingDAO.class) instanceof PackagingDAO);
	}
}
