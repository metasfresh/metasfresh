/*
 * #%L
 * de-metas-camel-leichundmehl
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.util;

import com.sun.xml.bind.marshaller.CharacterEscapeHandler;

import java.io.IOException;
import java.io.Writer;

public class NoEscapeHandler implements CharacterEscapeHandler
{
	private NoEscapeHandler()
	{
		super();
	}

	public static final NoEscapeHandler INSTANCE = new NoEscapeHandler();

	public void escape(final char[] buf, final int start, final int len, final boolean isAttValue, final Writer out) throws IOException
	{
		for (int i = start; i < start + len; i++)
		{
			out.write(buf[i]);
		}
		return;
	}
}

