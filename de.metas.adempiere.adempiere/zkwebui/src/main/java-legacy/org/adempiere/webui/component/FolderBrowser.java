/******************************************************************************
 * Copyright (C) 2009 Low Heng Sin                                            *
 * Copyright (C) 2009 Idalica Corporation                                     *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.webui.component;

import java.io.File;

import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.window.FDialog;
import org.compiere.util.Ini;
import org.compiere.util.ValueNamePair;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.North;
import org.zkoss.zkex.zul.South;

/**
 * Directory and File Browser
 * @author Elaine
 *
 */
public class FolderBrowser extends Window implements EventListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -923063158885832812L;
	
	private Textbox txtPath = new Textbox();
	private Listbox listDir = new Listbox();
	private ConfirmPanel confirmPanel = new ConfirmPanel(true);
	
	private final File root = new File(Ini.getAdempiereHome());
	
	private boolean showDirOnly = false;
	private String path;
	
	public FolderBrowser()
	{
		this(false);
	}
	
	public FolderBrowser(boolean showDirOnly)
	{
		this.showDirOnly = showDirOnly;
		
		setTitle(showDirOnly ? "Directory Browser" : "File Browser");
		setWidth("500px");
		setHeight("500px");
		setBorder("normal");
		
		Borderlayout contentLayout = new Borderlayout();
		appendChild(contentLayout);
		
		North north = new North();
		contentLayout.appendChild(north);
		north.appendChild(txtPath);
		
		Center center = new Center();
		center.setFlex(true);
		contentLayout.appendChild(center);
		center.appendChild(listDir);
		
		South south = new South();
		south.setStyle("border: none");
		contentLayout.appendChild(south);
		south.appendChild(confirmPanel);
		
		txtPath.setWidth("475px");
		txtPath.setReadonly(true);
		
		getFileListing(root.getPath());
		listDir.setMultiple(false);
		listDir.addDoubleClickListener(this);
		
		confirmPanel.addActionListener(this);

		AEnv.showWindow(this);
	}
	
	private void getFileListing(String dirPath)
	{		
		File dir = new File(dirPath);
		if(!dir.exists())
			return;
				
		if(dir.isDirectory())
		{
			listDir.removeAllItems();
			
			if(!dir.getParent().equals(root.getParent()))
			{
				ListItem li = new ListItem(dir.getName(), dir.getParent());
				li.setImage("images/Undo16.png");
				listDir.appendChild(li);
			}

			File[] files = dir.listFiles();
			
			for(File file: files)
			{
				if(file.isDirectory())
				{
					ListItem li = new ListItem(file.getName(), file.getAbsolutePath());
					li.setImage("images/Folder16.png");
					listDir.appendChild(li);
				}
			}
			
			if(!showDirOnly)
			{
				for(File file: files)
				{
					if(file.isFile())
					{
						listDir.addItem(new ValueNamePair(file.getAbsolutePath(), file.getName()));
					}
				}
			}
		}
		
		txtPath.setValue(dir.getAbsolutePath());
	}
	
	public void onEvent(Event e) throws Exception 
	{	
		if(e.getName() == Events.ON_DOUBLE_CLICK && e.getTarget() instanceof ListItem)
		{
			int index = listDir.getSelectedIndex();
			ValueNamePair vnp = listDir.getItemAtIndex(index).toValueNamePair();
			getFileListing(vnp.getValue());
		}
		if(e.getTarget() == confirmPanel.getButton(ConfirmPanel.A_OK))
		{
			path = txtPath.getValue();
			if(path != null)
			{
				File file = new File(path);
				
				if(showDirOnly)
				{
					if(!file.isDirectory() || !file.exists())
					{
						FDialog.error(0, "Invalid directory");
						return;
					}
				}
				else
				{
					if(!file.isFile() || !file.exists())
					{
						FDialog.error(0, "Invalid file");
						return;
					}
				}
				
				path = file.getAbsolutePath();
			}
			dispose();
		}
		else if(e.getTarget() == confirmPanel.getButton(ConfirmPanel.A_CANCEL))
		{
			path = null;
			dispose();
		}
	}
	
	public String getPath()
	{
		return path;
	}
}
