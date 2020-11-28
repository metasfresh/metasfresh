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
package org.compiere.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import org.adempiere.plaf.AdempiereLookAndFeel;
import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.plaf.AdempiereTabbedPaneUI;
import org.adempiere.plaf.UIAction;

import de.metas.util.MFColor;

/**
 *  Adempiere Color Tabbed Pane
 *
 *  @author     Jorg Janke
 *  @version    $Id: CTabbedPane.java,v 1.2 2006/07/30 00:52:24 jjanke Exp $
 */
public class CTabbedPane extends JTabbedPane
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5845265820245543812L;


	/**
	 * Creates an empty <code>TabbedPane</code> with a default
	 * tab placement of <code>JTabbedPane.TOP</code> and default
	 * tab layout policy of <code>JTabbedPane.WRAP_TAB_LAYOUT</code>.
	 */
	public CTabbedPane()
	{
		super();
		init();
	}   //  CTabbedPane

	/**
	 * Creates an empty <code>TabbedPane</code> with the specified tab placement
	 * of either: <code>JTabbedPane.TOP</code>, <code>JTabbedPane.BOTTOM</code>,
	 * <code>JTabbedPane.LEFT</code>, or <code>JTabbedPane.RIGHT</code>, and a
	 * default tab layout policy of <code>JTabbedPane.WRAP_TAB_LAYOUT</code>.
	 *
	 * @param tabPlacement the placement for the tabs relative to the content
	 */
	public CTabbedPane(int tabPlacement)
	{
		super(tabPlacement);
		init();
	}   //  CTabbedPane

	/**
	 * Creates an empty <code>TabbedPane</code> with the specified tab placement
	 * and tab layout policy.  Tab placement may be either:
	 * <code>JTabbedPane.TOP</code>, <code>JTabbedPane.BOTTOM</code>,
	 * <code>JTabbedPane.LEFT</code>, or <code>JTabbedPane.RIGHT</code>.
	 * Tab layout policy may be either: <code>JTabbedPane.WRAP_TAB_LAYOUT</code>
	 * or <code>JTabbedPane.SCROLL_TAB_LAYOUT</code>.
	 *
	 * @param tabPlacement the placement for the tabs relative to the content
	 * @param tabLayoutPolicy the policy for laying out tabs when all tabs will not fit on one run
	 * @exception IllegalArgumentException if tab placement or tab layout policy are not
	 *            one of the above supported values
	 */
	public CTabbedPane(int tabPlacement, int tabLayoutPolicy)
	{
		super (tabPlacement, tabLayoutPolicy);
		init();
	}   //  CTabbedPane

	/**
	 *  Common Init
	 */
	private void init()
	{
	}   //  init

	/** Hide the tabs if this tabbed pane has only one tab */
	public final void setHideIfOneTab(final boolean hideIfOneTab)
	{
		putClientProperty(AdempiereLookAndFeel.HIDE_IF_ONE_TAB, hideIfOneTab);
	}
	
	public final void setAlignVerticalTabsWithHorizontalTabs(final boolean enabled)
	{
		putClientProperty(AdempiereTabbedPaneUI.KEY_AlignVerticalTabsWithHorizontalTabs_Enabled, enabled);
	}
	
	/**************************************************************************
	 *  Set Background - ignored by UI -
	 *  @param bg ignored
	 */
	@Override
	public void setBackground (Color bg)
	{
		if (bg.equals(getBackground()))
			return;
		super.setBackground (bg);
		setBackgroundColor (MFColor.ofFlatColor(bg));
	}   //  setBackground

	/**
	 *  Set Standard Background
	 */
	public void setBackgroundColor ()
	{
		setBackgroundColor (null);
	}   //  setBackground

	/**
	 *  Set Background
	 *  @param bg AdempiereColor for Background, if null set standard background
	 */
	public void setBackgroundColor (MFColor bg)
	{
		if (bg == null)
			bg = MFColor.ofFlatColor(AdempierePLAF.getFormBackground());
		putClientProperty(AdempiereLookAndFeel.BACKGROUND, bg);
		super.setBackground (bg.getFlatColor());
		//
		repaint();
	}   //  setBackground

	/**
	 *  Get Background
	 *  @return Color for Background
	 */
	public MFColor getBackgroundColor ()
	{
		try
		{
			return (MFColor)getClientProperty(AdempiereLookAndFeel.BACKGROUND);
		}
		catch (Exception e)
		{
			System.err.println("ClientProperty: " + e.getMessage());
		}
		return null;
	}   //  getBackgroundColor

	
	/**************************************************************************
	 * Insert tab.
	 * If the component is a JPanel, the backround is set to the default
	 * AdempiereColor (and Opaque) if nothing was defined.
	 * Redquired as otherwise a gray background would be pained.
	 * <p>
	 * Inserts a <code>component</code>, at <code>index</code>,
	 * represented by a <code>title</code> and/or <code>icon</code>,
	 * either of which may be <code>null</code>. If <code>icon</code>
	 * is non-<code>null</code> and it implements
	 * <code>ImageIcon</code> a corresponding disabled icon will automatically
	 * be created and set on the tabbedpane.
	 * Uses java.util.Vector internally, see <code>insertElementAt</code>
	 * for details of insertion conventions.
	 *
	 * @param text the title with Mnemonic to be displayed in this tab
	 * @param icon the icon to be displayed in this tab
	 * @param component The component to be displayed when this tab is clicked.
	 * @param tip the tooltip to be displayed for this tab
	 * @param index the position to insert this new tab
	 */
	@Override
	public void insertTab (String text, Icon icon, Component component, String tip, int index)
	{
		String title = text;
		if (!title.startsWith("<html>"))
		{
			int pos = title.indexOf('&');
			if (pos != -1)
				title = title.substring(0, pos) + title.substring(pos+1);
		}
		//	Enforce Tool Tip
		if (tip == null || tip.length() == 0)
			tip = title;
		super.insertTab (title, icon, component, tip, index);
		//  Set component background
		if (component instanceof JPanel)
		{
			JPanel p = (JPanel)component;
			if (p.getClientProperty(AdempiereLookAndFeel.BACKGROUND) == null)
			{
				//AdempiereColor.setBackground(p);
				//p.setOpaque(true);
			}
		}
		//	Set first	
		if (index == 0)
			getActionMap().put(ACTION_SELECT, s_action);
		//
		if (!setMnemonicAt(index, text))
		{	//	Only one - set direct
			if (index < 9)
				setMnemonicAt(index, '1'+index);
			else if (index == 9)
				setMnemonicAt(index, '0');
		}
		else	//	additional ALT-1..0
		{
			if (index < 9)
				getInputMap(WHEN_IN_FOCUSED_WINDOW)
					.put(KeyStroke.getKeyStroke(KeyEvent.VK_1+index, Event.ALT_MASK), ACTION_SELECT);
			else if (index == 9)
				getInputMap(WHEN_IN_FOCUSED_WINDOW)
					.put(KeyStroke.getKeyStroke(KeyEvent.VK_0, Event.ALT_MASK), ACTION_SELECT);
		}
		//
	}   //  insertTab

	/**
	 * 	Set Title At
	 *	@param index index
	 *	@param text title with opt Mnemonic
	 */
	@Override
	public void setTitleAt (int index, String text)
	{
		String title = text;
		if (!title.startsWith("<html>"))
		{
			int pos = title.indexOf('&');
			if (pos != -1)					//	We have a nemonic - creates ALT-_
				title = title.substring(0, pos) + title.substring(pos+1);
		}
		super.setTitleAt (index, title);
	//	setMnemonicAt(index, text);
	}	//	setTitleAt
	
	
	/**
	 * 	Set Mnemonic for Index based on text
	 *	@param index for index
	 *	@param text text
	 */
	public boolean setMnemonicAt (int index, String text)
	{
		//	logistics - remove old
		while (m_mnemonic.size() < index+1)
			m_mnemonic.add((char)0);
		char keyCode = m_mnemonic.get(index);
		if (keyCode != 0)
			getInputMap(WHEN_IN_FOCUSED_WINDOW)
				.remove(KeyStroke.getKeyStroke(keyCode, Event.ALT_MASK));
		m_mnemonic.set(index, (char)0);
		//
		if (!text.startsWith("<html>"))
		{
			int pos = text.indexOf('&');
			if (pos != -1 && text.length() > pos)	//	We have a nemonic - creates ALT-_
			{
				keyCode = text.toUpperCase().charAt(pos+1);
				if (keyCode != ' ')
				{
					setMnemonicAt(index, keyCode);
					m_mnemonic.set(index, keyCode);
					return true;
				}
			}
		}
		/**
		keyCode = text.toUpperCase().charAt(0);
		if (m_mnemonic.contains(keyCode))
		{
			keyCode = 0;
			//	Beginning new word
			int pos = text.indexOf(' ');
			while (pos != -1 && text.length() > pos)
			{
				char c = text.toUpperCase().charAt(pos+1);
				if (Character.isLetterOrDigit(c) && !m_mnemonic.contains(c))
				{
					keyCode = c;
					break;
				}
				pos = text.indexOf(' ', pos+1);
			}
			//	Any character
			if (keyCode == 0)
			{
				for (int i = 1; i < text.length(); i++)
				{
					char c = text.toUpperCase().charAt(i);
					if (Character.isLetterOrDigit(c) && !m_mnemonic.contains(c))
					{
						keyCode = c;
						break;
					}
				}
			}
			//	First character fallback
		//	if (mnemonic == 0)
		//		mnemonic = text.toUpperCase().charAt(0);
		}
		if (keyCode != 0)
		{
			setMnemonicAt(index, keyCode);
			m_mnemonic.set(index, keyCode);
		}
		**/
		return false;
	}	//	setMnemonicAt

	/** Used Mnemonics		*/
	private ArrayList<Character> m_mnemonic = new ArrayList<>(10);

	
	/**
	 *  String representation
	 *  @return String representation
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder ("CTabbedPane [");
		sb.append(super.toString());
		MFColor bg = getBackgroundColor();
		if (bg != null)
			sb.append(bg.toString());
		sb.append("]");
		return sb.toString();
	}   //  toString

	/** Select Action Text			*/
	private static final String		ACTION_SELECT = "CAS";
	/** Select Action				*/
	private static final CTAction s_action = new CTAction(ACTION_SELECT);
	
	/**
	 * 	Select Action
	 *	
	 *  @author Jorg Janke
	 *  @version $Id: CTabbedPane.java,v 1.2 2006/07/30 00:52:24 jjanke Exp $
	 */
	private static class CTAction extends UIAction
	{
		/**
		 * 	Constructor
		 */
		public CTAction (String actionName)
		{
			super (actionName);
		}	//	Action

		@Override
		public void actionPerformed (ActionEvent e)
		{
            String key = getName();
            if (!key.equals(ACTION_SELECT) 
            	|| !(e.getSource() instanceof CTabbedPane))
            	return;
            CTabbedPane pane = (CTabbedPane)e.getSource();
            String command = e.getActionCommand();
            if (command == null || command.length() != 1)
            	return;
            int index = command.charAt(0)-'1';
            if (index > -1 && index < pane.getTabCount())
            	pane.setSelectedIndex(index);
            else
            	System.out.println("Action: " + e);
		}	//	actionPerformed
		
	}	//	Action

}   //  CTabbedPane
