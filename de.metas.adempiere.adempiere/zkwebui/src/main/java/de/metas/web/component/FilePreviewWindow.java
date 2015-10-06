package de.metas.web.component;

/*
 * #%L
 * ADempiere ERP - ZkWebUI Lib
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


import org.zkoss.util.media.AMedia;
import org.zkoss.zk.au.out.AuScript;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Div;
import org.zkoss.zul.Iframe;

public class FilePreviewWindow extends Div
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String ZUL_URI = "/zul/common/FilePreview.zul";

	public static final String ATTR_Data = FilePreviewWindow.class.getName() + "#Data";
	public static final String ATTR_ContentType = FilePreviewWindow.class.getName() + "#ContentType";
	public static final String ATTR_Filename = FilePreviewWindow.class.getName() + "#Filename";

	public static void openNewWindow(byte[] data, String contentType, String filename)
	{
		Sessions.getCurrent().setAttribute(ATTR_Data, data);
		Sessions.getCurrent().setAttribute(ATTR_ContentType, contentType);
		Sessions.getCurrent().setAttribute(ATTR_Filename, filename);

		final String zulRoot = "/webui"; // TODO: hardcoded
		final String url = zulRoot + "/" + ZUL_URI;
		final String target = "FilePreview";
		final int width = 800;
		final int height = 600;
		final String options = "width=" + width
				+ ",height=" + height
				+ ",status=0"
				+ ",toolbar=0"
				+ ",resizable=no"
				+ ",location=no"
				+ ",addressbar=no"
				+ ",menubar=0"
				+ ",directories=0"
				+ ",center=1" // should we center the window? {1 (YES) or 0 (NO)}. overrides top and left
				+ ",modal=yes"
		// + ",left=100"
		// + ",top=100"
		;

		final String javaScript = "var l=(screen.width/2) - " + width + "/2;"
				+ "var t=(screen.height/2) - " + height + "/2;"
				+ "window.open('" + url + "','" + target + "','" + options + ",left='+l+',top='+t)";
		final AuScript script = new AuScript(null, javaScript);

		Clients.response("filePreview_" + System.currentTimeMillis(), script);
	}

	private byte[] data;
	private String contentType;
	private String filename;

	public FilePreviewWindow()
	{
		super();

		if (!loadFromSession())
		{
			detach();
			Clients.response("filePreview_close_" + System.currentTimeMillis(), new AuScript(null, "window.close()"));
			return;
		}

		initUI();
	}

	private boolean loadFromSession()
	{
		data = (byte[])Sessions.getCurrent().getAttribute(ATTR_Data);
		if (data == null || data.length == 0)
		{
			return false;
		}
		contentType = (String)Sessions.getCurrent().getAttribute(ATTR_ContentType);
		if (contentType == null)
		{
			contentType = "application/unknown";
		}
		filename = (String)Sessions.getCurrent().getAttribute(ATTR_Filename);

		return true;
	}

	private void initUI()
	{
		setWidth("100%");
		setHeight("100%");

		final Iframe iframe = new Iframe();
		iframe.setId("previewFrame");
		iframe.setHeight("100%");
		iframe.setWidth("100%");
		final AMedia media = new AMedia(filename, null, contentType, data);
		iframe.setContent(media);
		appendChild(iframe);

		// Borderlayout layout = new Borderlayout();
		//
		// {
		// Center center = new Center();
		// center.setFlex(true);
		// layout.appendChild(center);
		//
		// Iframe iframe = new Iframe();
		// iframe.setId("previewFrame");
		// iframe.setHeight("100%");
		// iframe.setWidth("100%");
		//
		// AMedia media = new AMedia(filename, null, contentType, data);
		// iframe.setContent(media);
		// center.appendChild(iframe);
		// }
		//
		// this.appendChild(layout);
	}
}
