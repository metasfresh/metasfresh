/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.acct.api;

import de.metas.util.Services;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IElementValueDAOTest
{
	IElementValueDAO elementValueDAO = Services.get(IElementValueDAO.class);

	@Test
	void getElementValueIdsBetween1()
	{
		elementValueDAO.getElementValueIdsBetween("10", "20");  // TODO tbp: test this

				/*
SELECT value, rpad(value, 10, '0') valuePadded, length(value)
FROM c_elementvalue
WHERE TRUE
  AND rpad(value, 10, '0') BETWEEN rpad('10', 10, '0') AND rpad('200', 10, '0')
--   AND length(value) >
ORDER BY 2, 1
;
		 */
		/*
		values: 1, 10, 11, 19, 100, 110, 1000, 1010, 1020,
				2, 20, 21, 29, 200, 210, 2000, 2010, 2020



		from: 10
		to: 200

		expected:     10, 11, 19, 100, 110, 1000, 1010, 1020,
				   2, 20, 21,     200
		NOT expected: 1, 29, 210, 2000, 2010, 2020

*/
	}


	@Test
	void getElementValueIdsBetween2()
	{
		/*
SELECT value, rpad(value, 10, '0') valuePadded, length(value)
FROM c_elementvalue
WHERE TRUE
  AND rpad(value, 10, '0') BETWEEN rpad('106', 10, '0') AND rpad('200', 10, '0')
--   AND length(value) >
ORDER BY 2, 1
;
		 */
		 // TODO tbp: need teos help in figuring this one out
		/*
		values: 1, 10, 11, 19, 100, 110, 1000, 1010, 1020,
				2, 20, 21, 29, 200, 210, 2000, 2010, 2020



		from: 106
		to: 200

		expected:                       110, 1000, 1010, 1020,
				     2, 20, 21, 200
		NOT expected: 1, 10, 11, 19, 100,
		             29, 210, 2000, 2010, 2020

*/
	}
}
