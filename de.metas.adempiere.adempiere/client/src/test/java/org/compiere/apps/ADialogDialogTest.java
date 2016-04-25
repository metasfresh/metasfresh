package org.compiere.apps;

import org.junit.Assert;
import org.junit.Test;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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

public class ADialogDialogTest
{
	@Test
	public void testIconsAvailable()
	{
		Assert.assertNotNull("error icon", ADialogDialog.i_error);
		Assert.assertNotNull("inform icon", ADialogDialog.i_inform);
		Assert.assertNotNull("question icon", ADialogDialog.i_question);
		Assert.assertNotNull("warn icon", ADialogDialog.i_warn);
	}
}
