/******************************************************************************
 * Copyright (C) 2008 Low Heng Sin  All Rights Reserved.                      *
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

package org.adempiere.webui.editor;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.adempiere.webui.component.FilenameBox;
import org.adempiere.webui.event.ValueChangeEvent;
import org.compiere.model.GridField;
import org.compiere.util.CLogger;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Fileupload;

/**
 *
 * @author Low Heng Sin
 *
 */
public class WFilenameEditor extends WEditor
{
	private static final String[] LISTENER_EVENTS = {Events.ON_CLICK, Events.ON_CHANGE, Events.ON_OK};

	private static final CLogger log = CLogger.getCLogger(WFilenameEditor.class);

	private String oldValue;

	public WFilenameEditor(GridField gridField)
	{
		super(new FilenameBox(), gridField);
		getComponent().setButtonImage("/images/Open16.png");
		getComponent().addEventListener(Events.ON_CLICK, this);
	}

	@Override
	public FilenameBox getComponent()
	{
		return (FilenameBox) component;
	}

	@Override
	public void setValue(Object value)
	{
        if (value == null)
        {
        	oldValue = null;
            getComponent().setText("");
        }
        else
        {
        	oldValue = String.valueOf(value);
            getComponent().setText(oldValue);
        }
	}

	@Override
	public Object getValue()
	{
		return getComponent().getText();
	}

	@Override
	public String getDisplay()
	{
		return getComponent().getText();
	}

	@Override
	public boolean isReadWrite() {
		return getComponent().isEnabled();
	}

	@Override
	public void setReadWrite(boolean readWrite) {
		getComponent().setEnabled(readWrite);
	}

	public void onEvent(Event event)
	{
		if (Events.ON_CHANGE.equals(event.getName()) || Events.ON_OK.equals(event.getName()))
		{
			String newValue = getComponent().getText();
			if (oldValue != null && newValue != null && oldValue.equals(newValue)) {
	    	    return;
	    	}
	        if (oldValue == null && newValue == null) {
	        	return;
	        }
			ValueChangeEvent changeEvent = new ValueChangeEvent(this, this.getColumnName(), oldValue, newValue);
			fireValueChange(changeEvent);
		}
		else if (Events.ON_CLICK.equals(event.getName()))
		{
			cmd_file();
		}
	}

	/**
	 *  Load file
	 */
	private void cmd_file()
	{
		//  Show File Open Dialog
		Media file = null;

		try
		{
			file = Fileupload.get(true);

			if (file == null)
				return;
		}
		catch (InterruptedException e)
		{
			log.warning(e.getLocalizedMessage());
			return;
		}

		// String fileName = System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + ;
		// File tempFile = new File(fileName);

		FileOutputStream fos = null;
		String fileName = null;
		try {

			File tempFile = File.createTempFile("adempiere_", "_"+file.getName());
			fileName = tempFile.getAbsolutePath();

			fos = new FileOutputStream(tempFile);
			byte[] bytes = null;
			if (file.inMemory()) {
				bytes = file.getByteData();
			} else {
				InputStream is = file.getStreamData();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buf = new byte[ 1000 ];
				int byteread = 0;
				while (( byteread=is.read(buf) )!=-1)
					baos.write(buf,0,byteread);
				bytes = baos.toByteArray();
			}

			fos.write(bytes);
			fos.flush();
			fos.close();
		} catch (IOException e) {
			log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			return;
		} finally {
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {}
		}

		getComponent().setText(fileName);
	}   //  cmd_file

	public String[] getEvents()
    {
        return LISTENER_EVENTS;
    }
}
