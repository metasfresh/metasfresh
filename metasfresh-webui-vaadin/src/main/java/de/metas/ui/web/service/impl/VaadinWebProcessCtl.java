package de.metas.ui.web.service.impl;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.apps.ProcessCtl;
import org.compiere.process.ProcessInfo;
import org.compiere.util.ASyncProcess;
import org.slf4j.Logger;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;

import de.metas.logging.LogManager;
import de.metas.ui.web.service.IWebProcessCtl;
import de.metas.ui.web.service.impl.VaadinImageProvider.VaadinImageResource;
import de.metas.ui.web.window.model.action.Action.ActionEvent;

/*
 * #%L
 * metasfresh-webui-vaadin
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class VaadinWebProcessCtl implements IWebProcessCtl
{
	private static final Logger logger = LogManager.getLogger(VaadinWebProcessCtl.class);

	@Override
	public void reportAsync(final ProcessInfo pi, final ActionEvent event)
	{
		final ASyncProcess asyncCtrl = new ASyncProcess()
		{
			private boolean locked = false;

			@Override
			public void lockUI(final ProcessInfo pi)
			{
				locked = true;

				final Notification notification = new Notification("Executing " + event.getAction().getCaption(), Notification.Type.TRAY_NOTIFICATION);
				notification.setIcon(VaadinImageResource.getResource(event.getAction().getIcon()));
				notification.show(Page.getCurrent());
			}

			@Override
			public void unlockUI(final ProcessInfo pi)
			{
				locked = false;
			}

			@Override
			public boolean isUILocked()
			{
				return locked;
			}

			@Override
			public void executeASync(final ProcessInfo pi)
			{
				logger.debug("executeASync: {}", pi);
			}
		};
		
		ProcessCtl.process(asyncCtrl, pi.getWindowNo(), pi, ITrx.TRX_None); // calls lockUI, unlockUI
	}
}
