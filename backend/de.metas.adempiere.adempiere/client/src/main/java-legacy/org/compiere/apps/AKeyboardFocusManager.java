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

import java.awt.DefaultKeyboardFocusManager;

/**
 * 	Apps Keyboard Focus Manager
 *	
 *  @author Jorg Janke
 *  @version $Id: AKeyboardFocusManager.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public class AKeyboardFocusManager extends DefaultKeyboardFocusManager
{
	/**
	 * 	Get default Keyboard Focus Mgr
	 *	@return focus manager
	 */
	public static AKeyboardFocusManager get()
	{
		if (s_kfm == null)
			s_kfm = new AKeyboardFocusManager();
		return s_kfm;
	}	//	get
	
	/**	Default Focus Manager				*/
	private static AKeyboardFocusManager	s_kfm = new AKeyboardFocusManager();
	
	/**
	 * 	Constructor
	 */
	public AKeyboardFocusManager ()
	{
		super ();
		setDefaultFocusTraversalPolicy(AFocusTraversalPolicy.get());
	}	//	AKeyboardFocusManager
	
}	//	AKeyboardFocusManager
