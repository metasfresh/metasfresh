package org.compiere.apps;


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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ADialogDialogTest
{
	@Test
	public void testIconsAvailable()
	{
		Assertions.assertNotNull(ADialogDialog.i_error, "error icon");
		Assertions.assertNotNull(ADialogDialog.i_inform, "inform icon");
		Assertions.assertNotNull(ADialogDialog.i_question, "question icon");
		Assertions.assertNotNull(ADialogDialog.i_warn, "warn icon");
	}
}