/******************************************************************************
 * Copyright (C) 2010 Carlos Ruiz                                             *
 * Copyright (C) 2009 Quality Systems & Solutions - globalqss                 *
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

package org.adempiere.webui;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.sys.IdGenerator;

public class AdempiereIdGenerator implements IdGenerator {

	@Override
	public String nextComponentUuid(Desktop desktop, Component comp) {
		String id = (String) comp.getAttribute("zk_component_ID");
		if (id != null && id.length() > 0)
			return id;
		String prefix = (String) comp.getAttribute("zk_component_prefix");
		if (prefix == null || prefix.length() == 0)
			prefix = "zk_comp_";
		int     i = Integer.parseInt(desktop.getAttribute("Id_Num").toString());
		i++;// Start from 1
		desktop.setAttribute("Id_Num", String.valueOf(i));
		return prefix + i;
	}

	@Override
	public String nextDesktopId(Desktop desktop) {
		if (desktop.getAttribute("Id_Num") == null) {
			String number = "0";
			desktop.setAttribute("Id_Num", number);
		}
		return null;
	}

	@Override
	public String nextPageUuid(Page page) {
		return null;
	}

}
