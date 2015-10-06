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

import org.adempiere.webui.event.TokenEvent;
import org.zkoss.lang.Objects;
import org.zkoss.zk.au.AuRequest;
import org.zkoss.zk.au.Command;
import org.zkoss.zk.mesg.MZk;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Events;

/**
 * Command class to handle user authentication token event
 * @author hengsin
 *
 */
public class TokenCommand extends Command {

	public TokenCommand(String id, int flags) {
		super(id, flags);
	}

	@Override
	protected void process(AuRequest request) {
		final String[] data = request.getData();

		final Component comp = request.getComponent();
		if (comp == null)
			throw new UiException(MZk.ILLEGAL_REQUEST_COMPONENT_REQUIRED, this);
		
		if (data == null || data.length < 2)
			throw new UiException(MZk.ILLEGAL_REQUEST_WRONG_DATA, new Object[] {
					Objects.toString(data), this });
		
		Events.postEvent(new TokenEvent(getId(), comp, data));
	}
}
