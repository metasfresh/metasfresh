package org.adempiere.webui.window;

/*
 * #%L
 * de.metas.swat.zkwebui
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


import org.adempiere.exceptions.AdempiereException;
import org.adempiere.webui.component.Window;
import org.adempiere.webui.session.SessionManager;
import org.compiere.process.ProcessInfo;
import org.compiere.report.JRViewerProvider;

import de.metas.adempiere.report.jasper.OutputType;

//metas: tsa: moved from mvcForms because JasperReports is no longer a dependency for mvcForms
//metas: tsa: fully changed
public class ZkJRViewerProvider implements JRViewerProvider
{
	@Override
	public void openViewer(final byte[] data, final OutputType type, final String title, final ProcessInfo pi)
	{
		ZkJRViewer viewer = null;
		try
		{
			viewer = new ZkJRViewer(title);
			viewer.setData(data, type);
		}
		catch (final Exception e)
		{
			if (viewer != null)
			{
				viewer.dispose();
				viewer = null;
			}
			throw new AdempiereException(e.getLocalizedMessage(), e);
		}

		viewer.setAttribute(Window.MODE_KEY, Window.MODE_EMBEDDED);
		viewer.setAttribute(Window.INSERT_POSITION_KEY, Window.INSERT_NEXT);
		SessionManager.getAppDesktop().showWindow(viewer);
	}

	@Override
	public OutputType getDesiredOutputType()
	{
		return OutputType.PDF;
	}
	// metas: end
}
