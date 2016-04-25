package org.apache.ecs;

/*
 * #%L
 * de.metas.adempiere.adempiere.patched-ecs
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


import org.junit.Assert;
import org.junit.Test;

public class ECSDefaultsTest
{
	/**
	 * Make sure {@link ECSDefaults#resource} is loaded
	 * 
	 * @task http://dewiki908/mediawiki/index.php/06794_Chat_Button_crashes_Window
	 */
	@Test
	public void testResourceAvailable()
	{
		Assert.assertNotNull("ECSDefaults.resource shall be not null", ECSDefaults.resource);
	}
}
