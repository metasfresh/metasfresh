/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 Adempiere, Inc. All Rights Reserved.               *
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
package org.adempiere.plaf;

import java.awt.Color;
import java.awt.Component;

import javax.swing.UIDefaults;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;

import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.painter.MattePainter;

import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.PlasticTheme;

/**
 * Adempiere Look & Feel, based on JGoodies look and feel
 * 
 * @author vpj-cd, Low Heng Sin
 * @author tsa
 */
public class AdempiereLookAndFeel extends com.jgoodies.looks.plastic.Plastic3DLookAndFeel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5681571518701552477L;
	
	/**
	 *  Constructor
	 */
	public AdempiereLookAndFeel()
	{
		super();
	//	System.setProperty("awt.visualbell", "true");
	}

	/** The name                    */
	public static final     String  NAME = "Adempiere";

	/** The Theme */
	private static final PlasticTheme THEME_DEFAULT = new org.adempiere.plaf.AdempiereTheme();
	private static PlasticTheme THEME_CURRENT = THEME_DEFAULT;

	/** Key of Client Property to paint in CompiereColor    */
	public static final String  BACKGROUND = "CompiereBackground";
	/** Key of Client Property for Rectangle Items - if exists, the standard background is used */
	public static final String  BACKGROUND_FILL = "CompiereBackgroundFill";
	public static final String  TABLEVEL = "TabLevel";
	
	public static final String HIDE_IF_ONE_TAB = "adempiere.hideIfOneTab";

	public final static ColorUIResource DEFAULT_MANDATORY_BG = new ColorUIResource(224, 224, 255); //  blue-isch
	public final static ColorUIResource DEFAULT_ERROR_BG = new ColorUIResource(255, 204, 204); //  red-isch
	public final static ColorUIResource DEFAULT_ERROR_FG = new ColorUIResource(204, 0, 0);     //  dark red
	public final static ColorUIResource DEFAULT_INACTIVE_BG = new ColorUIResource(234, 234, 234);	//	light gray
	public final static ColorUIResource DEFAULT_INFO_BG = new ColorUIResource(253, 237, 207);	//	light yellow
	
	public final static String ERROR_BG_KEY = "TextField.errorBackground";
	public final static String ERROR_FG_KEY = "TextField.errorForeground";
	/** Background color of mandatory fields */
	public final static String MANDATORY_BG_KEY = "TextField.mandatoryBackground";
	public final static String INACTIVE_BG_KEY = "TextField.inactiveBackground";
	public final static String INFO_BG_KEY = "Info.background";

	/**
	 *  The Name
	 *  @return Name
	 */
	@Override
	public String getName()
	{
		return NAME;
	}   //  getName

	/**
	 *  The ID
	 *  @return Name
	 */
	@Override
	public String getID()
	{
		return NAME;
	}   //  getID

	/**
	 *  The Description
	 *  @return description
	 */
	@Override
	public String getDescription()
	{
		return "Adempiere Look & Feel - (c) 2001-2005 Victor Perez";
	}   //  getDescription

	/**
	 * Creates the mapping from UI class IDs to <code>ComponentUI</code> classes,
	 * putting the ID-<code>ComponentUI</code> pairs in the passed-in defaults table.
	 * Each <code>JComponent</code> class specifies its own UI class ID string.
	 *
	 * @param table UI Defaults
	 */
	@Override
	protected void initClassDefaults(final UIDefaults table)
	{	    
		super.initClassDefaults( table);
		
		//  Overwrite
		putDefault(table, AdempiereComboBoxUI.uiClassID, AdempiereComboBoxUI.class);
		putDefault(table, AdempiereLabelUI.uiClassID, AdempiereLabelUI.class);
		table.putDefaults(AdempiereTabbedPaneUI.getUIDefaults());
		putDefault(table, AdempiereEditorPaneUI.uiClassID, AdempiereEditorPaneUI.class);
		table.putDefaults(AdempiereSplitPaneUI.getUIDefaults());
		
		//
		// Task Pane UI:
		putDefault(table, JXTaskPane.uiClassID, AdempiereTaskPaneUI.class);
		table.put("TaskPaneContainer.backgroundPainter", new MattePainter(AdempierePLAF.getFormBackground()));
		table.put("TaskPane.background", new ColorUIResource(0xF4, 0xF4, 0xF4));

	}   //  initClassDefaults

	/**
	 * Put "uiKey - ClassName" pair in UIDefaults
	 * 
	 * @param table {@link UIDefaults} table
	 * @param uiKey
	 * @param uiClass
	 */
	private <T extends ComponentUI> void putDefault (final UIDefaults table, final String uiKey, final Class<T> uiClass)
	{
		try
		{
			String className = uiClass.getName();
			table.put(uiKey, className);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}   //  putDefault

	/**
	 *  For overwriting Component defaults
	 *  @param table
	 */
	@Override
	protected void initSystemColorDefaults (final UIDefaults table)
	{
		super.initSystemColorDefaults( table);
	}   //  initSystemColorDefaults

	/**
	 *  For overwriting Component defaults
	 *  @param table
	 */
	@Override
	protected void initComponentDefaults (final UIDefaults table)
	{
		super.initComponentDefaults(table);

		//  ComboBox defaults
		final Color c = table.getColor("TextField.background");
		table.put("ComboBox.background", c);
		table.put("ComboBox.listBackground", c);
		
		// globalqss
		final Class<?> lf = com.jgoodies.looks.plastic.PlasticLookAndFeel.class;
		table.put("Tree.openIcon", makeIcon(lf, "icons/TreeOpen.gif"));
		table.put("Tree.closedIcon", makeIcon(lf, "icons/TreeClosed.gif"));
		table.put("Tree.leafIcon", makeIcon(lf, "icons/TreeLeaf.gif"));
	}   //  initComponentDefaults
	
	/**
	 *  Set Current Theme
	 *  @param theme metal theme
	 */
	public static void setCurrentTheme (final PlasticTheme theme)
	{
		if (theme != null)
		{
			THEME_CURRENT = theme;
			PlasticLookAndFeel.setCurrentTheme(THEME_CURRENT);
		}
	}

	/**
	 *  Get Current Theme
	 *  @return Metal Theme
	 */
	public static PlasticTheme getCurrentTheme()
	{
		return THEME_CURRENT;
	}

	/**
	 *  Error Feedback.
	 *  <p>
	 *  Invoked when the user attempts an invalid operation,
	 *  such as pasting into an uneditable <code>JTextField</code>
	 *  that has focus.
	 *  </p>
	 *  <p>
	 *  If the user has enabled visual error indication on
	 *  the desktop, this method will flash the caption bar
	 *  of the active window. The user can also set the
	 *  property awt.visualbell=true to achieve the same
	 *  results.
	 *  </p>
	 *  @param component Component the error occurred in, may be
	 *			null indicating the error condition is
	 *			not directly associated with a
	 *			<code>Component</code>.
	 */
	@Override
	public void provideErrorFeedback (Component component)
	{
		super.provideErrorFeedback (component);
	}
	
}   //  AdempiereLookAndFeel
