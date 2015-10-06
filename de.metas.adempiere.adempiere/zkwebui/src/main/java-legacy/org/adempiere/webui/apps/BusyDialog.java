/******************************************************************************
 * Copyright (C) 2010 Low Heng Sin                                            *
 * Copyright (C) 2010 Idalica Corporation                                     *
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
package org.adempiere.webui.apps;

import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.Window;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;

/**
 * 
 * @author hengsin
 *
 */
public class BusyDialog extends Window {

	private static final long serialVersionUID = -779475945298887887L;

	public BusyDialog() {
		super();
		Hbox box = new Hbox();
		box.setStyle("padding: 5px");
		appendChild(box);
		box.appendChild(new Label(Msg.getMsg(Env.getCtx(), "Processing")));
		Image image = new Image();
		box.appendChild(image);
		image.setHeight("16px");
		image.setWidth("16px");
		image.setSrc("~./zk/img/progress3.gif");
		setPosition("center");
		setShadow(true);		
	}

}
