package de.metas.adempiere.addon;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.compiere.apps.AppsAction;

public class AppsActionAdapter implements ActionListener {

	private AppsActionListener appsActionListener;

	public AppsActionListener getAppsActionListener() {
		return appsActionListener;
	}

	public void setAppsActionListener(AppsActionListener appsActionListener) {
		this.appsActionListener = appsActionListener;
	}

	public void setAppsAction(AppsAction appsAction) {
		this.appsAction = appsAction;
	}

	private AppsAction appsAction;

	public AppsAction getAppsAction() {
		return appsAction;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		appsActionListener.actionPerformed(e, appsAction);
	}
}
