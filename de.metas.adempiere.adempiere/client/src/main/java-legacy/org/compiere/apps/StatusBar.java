/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.apps;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.util.Services;
import org.compiere.model.DataStatusEvent;
import org.compiere.swing.CPanel;
import org.compiere.util.Env;

import de.metas.i18n.IMsgBL;

/**
 *  Status Bar
 *
 *  @author 	Jorg Janke
 *  @version 	$Id: StatusBar.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public class StatusBar extends CPanel implements IStatusBar
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 178093491294124862L;

	/**
	 *	Standard Status Bar
	 */
	public StatusBar()
	{
		this(false);
	}	//	StatusBar

	/**
	 *	Status Bar with additional info
	 *  @param withInfo with info
	 */
	public StatusBar (boolean withInfo)
	{
		super();
		try
		{
			jbInit();
		}
		catch (Exception e)
		{
		}
		
		this.setName("statusBar");
		
		if (!withInfo)
		{
			infoLine.setVisible(false);
		}
	}	//	StatusBar

	private final JLabel statusLine = new JLabel();
	private final JLabel statusDB = new JLabel();
	private final JLabel infoLine = new JLabel();
	//
	private boolean		mt_error;
	private String		mt_text;
	//
	private String      m_text;
	private DataStatusEvent m_dse = null;

	/**
	 *	Static Init
	 *  @throws Exception
	 */
	private void jbInit() throws Exception
	{
		statusLine.setBorder(BorderFactory.createEtchedBorder());
		statusLine.setText("statusLine");
		statusLine.setOpaque(false);
		
		statusDB.setForeground(Color.blue);
		statusDB.setBorder(BorderFactory.createEtchedBorder());
		statusDB.setText("#");
		statusDB.setOpaque(false);
		statusDB.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				showRecordInfo();
			}
		});
		
		infoLine.setFont(AdempierePLAF.getFont_Label());
		infoLine.setBorder(BorderFactory.createLineBorder(AdempierePLAF.getSecondary2()));
		infoLine.setHorizontalAlignment(SwingConstants.CENTER);
		infoLine.setHorizontalTextPosition(SwingConstants.CENTER);
		infoLine.setText("info");

		//
		// Layout
		final BorderLayout mainLayout = new BorderLayout();
		mainLayout.setHgap(2);
		mainLayout.setVgap(2);
		this.setLayout(mainLayout);
		this.add(statusLine, BorderLayout.CENTER);
		this.add(statusDB, BorderLayout.EAST);
		this.add(infoLine, BorderLayout.NORTH);
	}	//	jbInit

	
	/**
	 *	Set Standard Status Line (non error)
	 *  @param text text
	 */
	@Override
	public void setStatusLine(final String text)
	{
		if (text == null)
			setStatusLine("", false);
		else
			setStatusLine(text, false);
	}	//	setStatusLine

	/**
	 *	Set Status Line
	 *  @param text text
	 *  @param error error
	 */
	@Override
	public void setStatusLine (String text, boolean error)
	{
		mt_error = error;
		mt_text = text;
		if (mt_error)
			statusLine.setForeground(AdempierePLAF.getTextColor_Issue());
		else
			statusLine.setForeground(AdempierePLAF.getTextColor_OK());
		
		if(mt_text == null || mt_text.length() == 0)
		{
			statusLine.setText("");
		}
		else
		{
			statusLine.setText(" " + mt_text);
		}
		//
		Thread.yield();
	}	//	setStatusLine

	/**
	 *	Get Status Line text
	 *  @return StatusLine text
	 */
	public String getStatusLine()
	{
		return statusLine.getText().trim();
	}	//	setStatusLine

	/**
	 *  Set ToolTip of StatusLine
	 *  @param tip tip
	 */
	public void setStatusToolTip (String tip)
	{
		statusLine.setToolTipText(tip);
	}   //  setStatusToolTip

	/**
	 *	Set Status DB Info
	 *  @param text text
	 *  @param dse data status event
	 */
	@Override
	public void setStatusDB (String text, DataStatusEvent dse)
	{
	//	log.info( "StatusBar.setStatusDB - " + text + " - " + created + "/" + createdBy);
		if (text == null || text.length() == 0)
		{
			statusDB.setText("");
			statusDB.setVisible(false);
		}
		else
		{
			StringBuilder sb = new StringBuilder(" ");
			sb.append(text).append(" ");
			statusDB.setText(sb.toString());
			if (!statusDB.isVisible())
				statusDB.setVisible(true);
		}

		//  Save
		m_text = text;
		m_dse = dse;
	}	//	setStatusDB

	/**
	 *	Set Status DB Info
	 *  @param text text
	 */
	@Override
	public void setStatusDB (String text)
	{
		setStatusDB (text, null);
	}   //  setStatusDB

	/**
	 *	Set Status DB Info
	 *  @param no no
	 */
	public void setStatusDB (int no)
	{
		setStatusDB (String.valueOf(no), null);
	}   //  setStatusDB

	/**
	 *	Set Info Line
	 *  @param text text
	 */
	@Override
	public void setInfo (String text)
	{
		infoLine.setVisible(true);
		infoLine.setText(text);
	}	//	setInfo

	/**
	 * Show {@link RecordInfo} dialog
	 */
	private void showRecordInfo()
	{
		if (m_dse == null 
			|| m_dse.CreatedBy == null
			|| !Env.getUserRolePermissions().isShowPreference())
		{
			return;
		}
		
		//
		final String title = Services.get(IMsgBL.class).getMsg(Env.getCtx(), "Who") + m_text;
		final RecordInfo info = new RecordInfo(Env.getFrame(this), title, m_dse);
		AEnv.showCenterScreen(info);
	}

	public void removeBorders()
	{
		statusLine.setBorder(BorderFactory.createEmptyBorder());
		statusDB.setBorder(BorderFactory.createEmptyBorder());
		infoLine.setBorder(BorderFactory.createEmptyBorder());
	}
}
