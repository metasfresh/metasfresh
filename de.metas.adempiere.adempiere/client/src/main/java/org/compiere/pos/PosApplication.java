package org.compiere.pos;

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

import java.awt.KeyboardFocusManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Properties;

import javax.swing.JFrame;

import org.adempiere.ad.session.ISessionBL;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.apps.ADialog;
import org.compiere.apps.AEnv;
import org.compiere.apps.AKeyboardFocusManager;
import org.compiere.apps.ALogin;
import org.compiere.swing.CFrame;
import org.compiere.util.Env;
import org.compiere.util.Splash;

import de.metas.i18n.Msg;

public class PosApplication {

	private Properties m_ctx;

	PosApplication() {
		Adempiere.startup(true);	//	needs to be here for UI
		Splash splash = Splash.getSplash();
		final CFrame frame = new CFrame();
		//  Focus Traversal
		KeyboardFocusManager.setCurrentKeyboardFocusManager(AKeyboardFocusManager.get());
	//	FocusManager.getCurrentManager().setDefaultFocusTraversalPolicy(AFocusTraversalPolicy.get());
	//	this.setFocusTraversalPolicy(AFocusTraversalPolicy.get());


		ALogin login = new ALogin(splash, Env.getCtx());
		if (!login.initLogin())		//	no automatic login
		{
			//	Center the window
			try
			{
				AEnv.showCenterScreen(login);	//	HTML load errors
			}
			catch (Exception ex)
			{
			}
			if (!login.isConnected() || !login.isOKpressed())
				AEnv.exit(1);
		}

		//  Check Build
		// we already check the server version via ClientUpdateValidator and that's enough
//		if (!DB.isBuildOK(m_ctx))
//			AEnv.exit(1);

		//  Check DB	(AppsServer Version checked in Login)
		// DB.isDatabaseOK(m_ctx); 	// we already check the server version via ClientUpdateValidator and that's enough


		splash.setText(Msg.getMsg(m_ctx, "Loading"));
		splash.toFront();
		splash.paint(splash.getGraphics());

		//
		if (!Adempiere.startupEnvironment(true)) // Load Environment
			System.exit(1);
		Services.get(ISessionBL.class).getCurrentOrCreateNewSession(Env.getCtx());		//	Start Session

		int m_WindowNo = Env.createWindowNo(frame);

	//  Default Image
		frame.setIconImage(Adempiere.getProductIconSmall());

		// Setting close operation/listener - teo_sarca [ 1684168 ]
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (!ADialog.ask(0, null, "ExitApplication?"))
					return;
				frame.dispose();
			}
		});

		PosBasePanel pos = new PosBasePanel();
		pos.init(0,frame);
		frame.pack();
		splash.dispose();
		splash = null;
		frame.setVisible(true);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		new PosApplication();

	}

}
