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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import org.adempiere.user.api.IUserDAO;
import org.adempiere.util.Services;
import org.compiere.apps.AppsAction;
import org.compiere.swing.CButton;
import org.compiere.swing.CDialog;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.util.Env;

import de.metas.i18n.Msg;
import net.miginfocom.swing.MigLayout;

public class PosLogin extends CDialog implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8490567722808711399L;
	private PosBasePanel posPanel;
	private PosTextField username;
	private PosTextField pin;
	private CButton bProcess;

	/**
	 * 	Constructor
	 *	@param posPanel POS Panel
	 */
	public PosLogin (PosBasePanel posPanel)
	{
		super (Env.getFrame(posPanel),Msg.translate(posPanel.getCtx(), "Login"), true);
		init();
		this.posPanel = posPanel;
	}

	private void init() {
		CPanel panel = new CPanel();
		panel.setLayout(new MigLayout());
		getContentPane().add(panel);
		
		panel.add(new CLabel(Msg.translate(posPanel.getCtx(),"SalesRep_ID")));
		
		username = new PosTextField(Msg.translate(posPanel.getCtx(),"SalesRep_ID"),
		posPanel, posPanel.p_pos.getOSK_KeyLayout_ID());	
		
		panel.add( username, "wrap");
		
		panel.add(new CLabel(Msg.translate(posPanel.getCtx(), "UserPIN")));
		
		pin = new PosTextField(Msg.translate(posPanel.getCtx(), "UserPIN"), posPanel, posPanel.p_pos.getOSNP_KeyLayout_ID());
		
		panel.add(pin, "");
		
		AppsAction act = AppsAction.builder()
				.setAction("Ok")
				.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0))
				.build();
		act.setDelegate(this);
		bProcess = (CButton)act.getButton();
		bProcess.setFocusable(false);
		panel.add (bProcess, "h 50!, w 50!");
		
		pack();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if ( e.getSource().equals(bProcess) )
		{
			Services.get(IUserDAO.class).retrieveLoginUserByUserIdAndPassword(username.getText(), pin.getText());
		}
		
		dispose();
	}

}
