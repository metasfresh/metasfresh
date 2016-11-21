package de.metas.adempiere.report.jasper.client;

/*
 * #%L
 * de.metas.report.jasper.client
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


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import net.sf.jasperreports.engine.JasperPrint;

public final class JRClientUtil
{
	// private static final transient Logger logger = CLogMgt.getLogger(JRClientUtil.class);

	private JRClientUtil()
	{
		super();
	}

	public static JasperPrint toJasperPrint(final byte[] data)
	{
		try
		{
			final ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
			final JasperPrint jasperPrint = (JasperPrint)ois.readObject();
			return jasperPrint;
		}
		catch (IOException e)
		{
			throw new RuntimeException(e.getLocalizedMessage(), e);
		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException(e.getLocalizedMessage(), e);
		}
	}
}
