/**
 * 
 */
package de.metas.web.dashboard;

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


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;

import org.adempiere.webui.component.ToolBarButton;
import org.adempiere.webui.dashboard.DashboardPanel;
import org.compiere.model.MSysConfig;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Div;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Separator;

/**
 * File downloads panel
 * 
 * @author teo_sarca
 */
public class DPDownloads extends DashboardPanel implements EventListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1963117704839103735L;

	public static final String SYSCONFIG_DirPath = "de.metas.dashboard.DPDownloads.DirPath";
	private static final String ATTR_File = "file";

	private final CLogger log = CLogger.getCLogger(getClass());
	private HtmlBasedComponent bxFileList;

	public DPDownloads()
	{
		init();
		refreshData();
	}

	private void init()
	{
		bxFileList = new Div();
		bxFileList.setStyle("overflow: auto; overflow-y:auto; max-height: 300px");
		// bxFileList.setHeight("300px");
		// this.setHeight("300px");
		appendChild(bxFileList);
	}

	@Override
	public void updateUI()
	{
		refreshData();
	}

	private void refreshData()
	{
		clearList();

		final String dirname = MSysConfig.getValue(DPDownloads.SYSCONFIG_DirPath, null, Env.getAD_Client_ID(Env.getCtx()));
		if (dirname == null)
		{
			log.warning("No Download directory defined (see sysconfig var " + DPDownloads.SYSCONFIG_DirPath + ")");
			return;
		}
		final File dir = new File(dirname);
		if (!dir.canRead())
		{
			log.warning("Directory does not exists or is not readable - " + dir);
			return;
		}

		for (final File f : dir.listFiles())
		{
			if (f.isFile() && f.canRead() && !f.isHidden())
			{
				addFile(f);
			}
		}
	}

	private void clearList()
	{
		for (final Object c : bxFileList.getChildren())
		{
			bxFileList.removeChild((Component)c);
		}
	}

	private void addFile(final File f)
	{
		final String name = f.getName();
		final ToolBarButton item = new ToolBarButton(f.getAbsolutePath());
		item.setLabel(name);
		item.setAttribute(DPDownloads.ATTR_File, f);
		item.setImage(getIconFile(f));
		// item.setDraggable(DELETE_FAV_DROPPABLE);
		item.addEventListener(Events.ON_CLICK, this);
		bxFileList.appendChild(item);
		bxFileList.appendChild(new Separator("horizontal"));
	}

	private byte[] getBytes(final File file)
	{
		byte[] data = null;
		FileInputStream fis = null;
		try
		{
			fis = new FileInputStream(file);
			final ByteArrayOutputStream os = new ByteArrayOutputStream();
			final byte[] buffer = new byte[1024 * 8];   // 8kB
			int length = -1;
			while ((length = fis.read(buffer)) != -1)
			{
				os.write(buffer, 0, length);
			}
			fis.close();
			data = os.toByteArray();
			os.close();
		}
		catch (final IOException e)
		{
			log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
		finally
		{
			if (fis != null)
			{
				try
				{
					fis.close();
				}
				catch (final IOException e)
				{
					// if closing fails within the finally-block there is nothing more to do
				}
			}
			fis = null;
		}
		return data;
	}

	private String getIconFile(final File file)
	{
		final String defaultIcon = "images/filetypes/icon_generic.gif";
		final String name = file.getName();
		final int idx = name.lastIndexOf('.');
		if (idx <= 0)
		{
			return defaultIcon;
		}

		final String ext = name.substring(idx + 1).toLowerCase();
		String icon = "images/filetypes/icon_" + ext + ".gif";
		final String iconFilepath = Executions.getCurrent().getDesktop().getWebApp().getRealPath(icon);
		if (!new File(iconFilepath).exists())
		{
			// log.fine("Icon file not found - "+icon);
			icon = defaultIcon;
		}
		return icon;
	}

	@Override
	public void onEvent(final Event event) throws Exception
	{
		if (event.getName().equals(Events.ON_CLICK) && event.getTarget() instanceof ToolBarButton)
		{
			final ToolBarButton item = (ToolBarButton)event.getTarget();
			final File file = (File)item.getAttribute(DPDownloads.ATTR_File);
			if (file == null)
			{
				return;
			}

			final String name = file.getName();
			final byte[] data = getBytes(file);
			final AMedia media = new AMedia(name, null, null, data);
			Filedownload.save(media);
		}
	}

}
