/******************************************************************************
 * Copyright (C) 2008 Low Heng Sin                                            *
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
package org.adempiere.webui.window;

import java.io.InputStream;

import org.adempiere.webui.component.Window;
import org.adempiere.webui.session.SessionManager;
import org.zkoss.util.media.AMedia;
import org.zkoss.zul.Iframe;

/**
 * 
 * @author Low Heng Sin
 *
 */
public class SimplePDFViewer extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6417954023873414350L;

	public SimplePDFViewer(String title, InputStream pdfInput) {
		Iframe iframe = new Iframe();
		iframe.setId("reportFrame");
		int height = Double.valueOf(SessionManager.getAppDesktop().getClientInfo().desktopHeight * 0.85).intValue();
		this.setHeight(height + "px");
		
		height = height - 30;
		iframe.setHeight(height + "px");
		iframe.setWidth("100%");
		AMedia media = new AMedia(getTitle(), "pdf", "application/pdf", pdfInput);
		iframe.setContent(media);
		
		this.setBorder("normal");
		this.appendChild(iframe);
		this.setClosable(true);
		this.setTitle(title);
		
		int width = Double.valueOf(SessionManager.getAppDesktop().getClientInfo().desktopWidth * 0.80).intValue();
		this.setWidth(width + "px");
	}
}
