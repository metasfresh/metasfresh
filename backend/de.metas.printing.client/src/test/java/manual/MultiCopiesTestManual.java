package manual;

/*
 * #%L
 * de.metas.printing.esb.client
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

import org.junit.jupiter.api.Disabled;

/**
 * Comment out the ignore flag to run the test
 *
 * @author ts
 *
 */
@Disabled
public class MultiCopiesTestManual extends AbstractMultiPageTestManual
{
	@Override
	protected int getTrayNumberForPage1()
	{
		return 6;
	}

	@Override
	protected int getTrayNumberForPage2()
	{
		return getTrayNumberForPage1();
	}

	@Override
	protected int getCopies()
	{
		return 2;
	}
}
