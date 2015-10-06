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

import java.applet.Applet;
import java.awt.HeadlessException;
import java.awt.TextArea;

import org.compiere.Adempiere;
import org.compiere.util.Env;
import org.compiere.util.Splash;


/**
 *	Application Applet
 *	
 *  @author Jorg Janke
 *  @version $Id: AApplet.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public class AApplet extends Applet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3725929727697509228L;

	/**
	 * 	Adempiere Application Applet
	 *	@throws java.awt.HeadlessException
	 */
	public AApplet () throws HeadlessException
	{
		super ();
	}	//	AApplet

	
	/**************************************************************************
	 * 	init
	 */
	@Override
	public void init ()
	{
		super.init ();
		TextArea ta = new TextArea(Adempiere.getSummary());
		add (ta);
	}	//	init
	
	/**
	 * 	start
	 */
	@Override
	public void start ()
	{
		super.start ();
		showStatus(Adempiere.getSummary());
		//
		Splash splash = Splash.getSplash();
		Adempiere.startup(true);	//	needs to be here for UI
		AMenu menu = new AMenu();
	}	//	start
	
	/**
	 * 	stop
	 */
	@Override
	public void stop ()
	{
		super.stop ();
	}	//	stop
	
	/**
	 * 	destroy
	 */
	@Override
	public void destroy ()
	{
		super.destroy ();
		Env.exitEnv(0);
	}	//	destroy
	
}	//	AApplet
