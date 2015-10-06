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


import java.io.File;
import java.io.FileNotFoundException;

import net.sf.jasperreports.engine.JasperPrint;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.webui.component.Window;
import org.zkoss.util.media.AMedia;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.North;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Toolbarbutton;

import de.metas.adempiere.report.jasper.JasperUtil;
import de.metas.adempiere.report.jasper.OutputType;
import de.metas.adempiere.report.jasper.client.JRClientUtil;

// metas: tsa: moved from mvcForms because JasperReports is no longer a dependency for mvcForms 
public class ZkJRViewer extends Window
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 105938143857598057L;

	private Iframe iframe;

	public ZkJRViewer(final String title)
	{
		setTitle(title);
		init();
	}

	private void init()
	{
		final Borderlayout layout = new Borderlayout();
		layout.setStyle("position: absolute; height: 99%; width: 99%");
		appendChild(layout);
		setStyle("width: 100%; height: 100%; position: absolute");

		final Toolbar toolbar = new Toolbar();
		toolbar.setHeight("26px");
		final Toolbarbutton button = new Toolbarbutton();
		button.setImage("/images/Print24.png");
		button.setTooltiptext("Print");
		toolbar.appendChild(button);

		final North north = new North();
		layout.appendChild(north);
		north.appendChild(toolbar);

		final Center center = new Center();
		center.setFlex(true);
		layout.appendChild(center);
		iframe = new Iframe();
		iframe.setId("reportFrame");
		iframe.setHeight("100%");
		iframe.setWidth("100%");

		center.appendChild(iframe);

		setBorder("normal");
	}

	public void setData(final byte[] data, final OutputType outputType)
	{
		final File file;
		if (OutputType.JasperPrint == outputType)
		{
			final JasperPrint jasperPrint = JRClientUtil.toJasperPrint(data);
			file = JRClientUtil.toPdfTempFile(jasperPrint);
		}
		else
		{
			file = JasperUtil.toTempFile(data, outputType, getTitle());
		}

		final AMedia media;
		try
		{
			media = new AMedia(getTitle(), "pdf", "application/pdf", file, true);
		}
		catch (final FileNotFoundException e)
		{
			throw new AdempiereException("Failed rendering the report: " + e.getLocalizedMessage(), e);
		}
		iframe.setContent(media);
	}
}
