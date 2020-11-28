package de.metas.procurement.sync.util;

import java.nio.ByteBuffer;
import java.util.UUID;

/*
 * #%L
 * de.metas.procurement.sync-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * UUIDs helper
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class UUIDs
{
	public static int toId(final UUID uuid)
	{
		// System.out.println("toId: " + uuid + "====================================");
		if (uuid.version() != 3)
		{
			throw new RuntimeException("not valid uuid: " + uuid + ": version != 3");
		}

		final long msb = uuid.getMostSignificantBits();
		// System.out.println("msb=" + toByteString(msb));
		// System.out.println("lsb=" + toByteString(uuid.getLeastSignificantBits()));

		long id = msb & 0xFFFF0000;
		// System.out.println(" id=" + toByteString(id) + " = " + id);
		id = id >> 8 * 4;
		// System.out.println(" id=" + toByteString(id) + " = " + id);

		return (int)id;
	}

	public static UUID fromId(final int id)
	{
		final byte[] data = ByteBuffer.allocate(16).putInt(id).array();
		// System.out.println("data=" + toString(data));
		data[6] &= 0x0f; /* clear version */
		data[6] |= 0x30; /* set to version 3 */
		data[8] &= 0x3f; /* clear variant */
		data[8] |= 0x80; /* set to IETF variant */
		// System.out.println("data=" + toString(data));

		long msb = 0;
		long lsb = 0;
		for (int i = 0; i < 8; i++)
		{
			msb = msb << 8 | data[i] & 0xff;
		}
		for (int i = 8; i < 16; i++)
		{
			lsb = lsb << 8 | data[i] & 0xff;
		}

		// System.out.println("msb=" + msb);
		// System.out.println("lsb=" + lsb);

		return new UUID(msb, lsb);
	}

	public static String fromIdAsString(final int id)
	{
		return fromId(id).toString();
	}

	public static int toId(final String uuid)
	{
		if (uuid == null || uuid.trim().isEmpty())
		{
			return -1;
		}
		return toId(UUID.fromString(uuid));
	}

	private static final String toString(byte[] data)
	{
		StringBuilder sb = new StringBuilder();
		for (final byte b : data)
		{
			if (sb.length() > 0)
			{
				sb.append(", ");
			}
			sb.append(b);
		}
		return sb
				.insert(0, "[" + data.length + "] ")
				.toString();
	}

	@SuppressWarnings("unused")
	private static final String toByteString(final long data)
	{
		final byte[] dataBytes = ByteBuffer.allocate(8).putLong(data).array();
		return toString(dataBytes);
	}

	private UUIDs()
	{
		super();
	}
}
