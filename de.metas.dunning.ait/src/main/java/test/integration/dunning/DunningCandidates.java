package test.integration.dunning;

/*
 * #%L
 * de.metas.dunning.ait
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


import org.junit.Test;

import de.metas.adempiere.ait.event.AIntegrationTestDriver;
import de.metas.adempiere.ait.helper.Helper;
import de.metas.adempiere.ait.helper.IHelper;
import de.metas.adempiere.ait.test.annotation.IntegrationTest;

public class DunningCandidates extends AIntegrationTestDriver
{

	@Override
	public IHelper newHelper()
	{
		return new Helper();
	}

	@IntegrationTest(
			tasks = "03114",
			desc = "Creates dunning candidates")
	@Test
	public void flatFeeOrderFirst()
	{
		// TODO
	}

}
