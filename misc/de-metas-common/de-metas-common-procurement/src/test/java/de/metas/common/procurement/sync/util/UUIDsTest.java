/*
 * #%L
 * de-metas-common-procurement
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

package de.metas.common.procurement.sync.util;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

public class UUIDsTest
{
	@Test
	public void test()
	{
		test(0, 20);
		test(1000350, 1000360);
		test(10000350, 10000360);
		test(Integer.MAX_VALUE - 1, Integer.MAX_VALUE);
		test(1000050, 1000050);
	}

	private void test(final int fromId, final int toId)
	{
		for (int id = fromId; id <= toId && id >= fromId; id++)
		{
			UUID uuid = UUIDs.fromId(id);
			int id2 = UUIDs.toId(uuid);
			//System.out.println("i=" + id + ", uuid=" + uuid + ", version=" + uuid.version() + " => i=" + id2);
			assertThat(id2).as("Invalid ID: id1=" + id + ", uuid=" + uuid + ", id2=" + id2).isEqualTo(id);
		}
	}
}
