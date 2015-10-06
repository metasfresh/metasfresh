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

import java.awt.Component;
import java.awt.Container;

import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.LayoutFocusTraversalPolicy;

import org.compiere.swing.CEditor;


/**
 *  Adempiere Application Focus Traversal Policy
 *
 *  @author     Jorg Janke
 *  @version    $Id: AFocusTraversalPolicy.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public class AFocusTraversalPolicy extends LayoutFocusTraversalPolicy
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5082175252327849893L;

	/**
	 *  Get singleton
	 *  @return AFocusTraversalPolicy
	 */
	public static AFocusTraversalPolicy get()
	{
		if (s_policy == null)
			s_policy = new AFocusTraversalPolicy();
		return s_policy;
	}   //  get

	/** Default Policy          */
	private static AFocusTraversalPolicy s_policy = new AFocusTraversalPolicy();

	/**************************************************************************
	 *  Constructor
	 */
	public AFocusTraversalPolicy ()
	{
		super();
	}   //  AFocusTraversalPolicy

	boolean m_default = false;
	
	/**
	 * 	Get Default Component
	 *	@param aContainer container
	 *	@return default or null
	 */
	@Override
	public Component getDefaultComponent (Container aContainer)
	{
	//	info ("Root: ", aContainer);
		m_default = true;
		Component c = super.getDefaultComponent (aContainer);
	//	info ("  Default: ", c);
		m_default = false;
		return c;
	}	//	getDefaultComponent

	/**
	 * Determines whether the specified <code>Component</code>
	 * is an acceptable choice as the new focus owner.
	 * This method performs the following sequence of operations:
	 * <ol>
	 * <li>Checks whether <code>aComponent</code> is visible, displayable,
	 *     enabled, and focusable.  If any of these properties is
	 *     <code>false</code>, this method returns <code>false</code>.
	 * <li>If <code>aComponent</code> is an instance of <code>JTable</code>,
	 *     returns <code>true</code>.
	 * <li>If <code>aComponent</code> is an instance of <code>JComboBox</code>,
	 *     then returns the value of
	 *     <code>aComponent.getUI().isFocusTraversable(aComponent)</code>.
	 * <li>If <code>aComponent</code> is a <code>JComponent</code>
	 *     with a <code>JComponent.WHEN_FOCUSED</code>
	 *     <code>InputMap</code> that is neither <code>null</code>
	 *     nor empty, returns <code>true</code>.
	 * <li>Returns the value of
	 *     <code>DefaultFocusTraversalPolicy.accept(aComponent)</code>.
	 * </ol>
	 *
	 * @param aComponent the <code>Component</code> whose fitness
	 *                   as a focus owner is to be tested
	 * @see java.awt.Component#isVisible
	 * @see java.awt.Component#isDisplayable
	 * @see java.awt.Component#isEnabled
	 * @see java.awt.Component#isFocusable
	 * @see javax.swing.plaf.ComboBoxUI#isFocusTraversable
	 * @see javax.swing.JComponent#getInputMap()
	 * @see java.awt.DefaultFocusTraversalPolicy#accept
	 * @return <code>true</code> if <code>aComponent</code> is a valid choice
	 *         for a focus owner;
	 *         otherwise <code>false</code>
	 */
	@Override
	protected boolean accept(Component aComponent)
	{
		if (!super.accept(aComponent))
			return false;

		//  TabbedPane
		if (aComponent instanceof JTabbedPane)
			return false;
		//  R/O Editors
		if (aComponent instanceof CEditor)
		{
			final CEditor ed = (CEditor)aComponent;
			if (!ed.isReadWrite())
				return false;
			if (m_default	//	get Default Focus 
				&& ("AD_Client_ID".equals(aComponent.getName()) || "AD_Org_ID".equals(aComponent.getName()) ))
				return false;
		}
		//  Toolbar Buttons
		if (aComponent.getParent() instanceof JToolBar)
			return false;
		//
		return true;
	}   //  accept

	/**************************************************************************
	 *  Dump info
	 *  @param title
	 *  @param c
	 */
	private void info (String title, Component c)
	{
		System.out.print (title);
		if (c == null)
			System.out.println (" - null");
		else
		{
			System.out.print (c.getClass().getName());
			System.out.println (" - " + c.getName());
		}
	}   //  info

}   //  AFocusTraversalPolicy
