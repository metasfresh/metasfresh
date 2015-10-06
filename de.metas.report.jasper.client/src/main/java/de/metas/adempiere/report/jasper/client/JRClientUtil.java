package de.metas.adempiere.report.jasper.client;

/*
 * #%L
 * adempiereJasper-client
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
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import de.metas.adempiere.report.jasper.JasperUtil;
import de.metas.adempiere.report.jasper.OutputType;

public final class JRClientUtil
{
	// private static final transient CLogger logger = CLogger.getCLogger(JRClientUtil.class);

	private JRClientUtil()
	{
		super();
	}

	public static JasperPrint toJasperPrint(final byte[] data)
	{
		final JasperPrint jasperPrint;
		try
		{
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
			jasperPrint = (JasperPrint)ois.readObject();
		}
		catch (IOException e)
		{
			throw new RuntimeException(e.getLocalizedMessage(), e);
		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException(e.getLocalizedMessage(), e);
		}

		return jasperPrint;
	}

	public static File toPdfTempFile(JasperPrint jasperPrint)
	{
		final File file = JasperUtil.createTempFile(OutputType.PDF, jasperPrint.getName());
		try
		{
			JasperExportManager.exportReportToPdfFile(jasperPrint, file.getAbsolutePath());
		}
		catch (JRException e)
		{
			throw new RuntimeException(e.getLocalizedMessage(), e);
		}

		return file;
	}
}
