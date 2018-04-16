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
package org.adempiere.plaf;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Window;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;

import org.adempiere.images.Images;
import org.adempiere.util.Check;
import org.adempiere.util.collections.ListUtils;
import org.compiere.plaf.PlafRes;
import org.compiere.swing.CButton;
import org.compiere.swing.ColorBlind;
import org.compiere.util.Ini;
import org.compiere.util.ValueNamePair;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.jgoodies.looks.plastic.PlasticTheme;

import de.metas.logging.LogManager;
import de.metas.util.MFColor;

/**
 *  Variable Pluggable Look And Feel.
 *  Provides an easy access to the required currently active PLAF information
 *
 *  @author     Jorg Janke
 *  @version    $Id: AdempierePLAF.java,v 1.3 2006/07/30 00:52:23 jjanke Exp $
 *  
 *  @author Low Heng Sin
 *  @version 2006-11-27
 */
public final class AdempierePLAF
{
	/**	Logger			*/
	private static final transient Logger log = LogManager.getLogger(AdempierePLAF.class.getName());
	
	/****** Background *******************************************************/

	/**
	 *  Return Normal field background color "text".
	 *  Windows = white
	 *  @return Color
	 */
	public static Color getFieldBackground_Normal()
	{
		//  window => white
		return ColorBlind.getDichromatColor(UIManager.getColor("text"));
	}   //  getFieldBackground_Normal

	/**
	 *  Return Error field background
	 *  @return Color
	 */
	public static Color getFieldBackground_Error()
	{
		Color c = UIManager.getColor(AdempiereLookAndFeel.ERROR_BG_KEY);
		if (c == null) 
			c = AdempiereLookAndFeel.DEFAULT_ERROR_BG;
		return ColorBlind.getDichromatColor(c);
	}   //  getFieldBackground_Error

	/**
	 *  Return Mandatory field background color
	 *  @return Color
	 */
	public static Color getFieldBackground_Mandatory()
	{
		Color c = UIManager.getColor(AdempiereLookAndFeel.MANDATORY_BG_KEY);
		if (c == null)
			c = AdempiereLookAndFeel.DEFAULT_MANDATORY_BG;
		return ColorBlind.getDichromatColor(c);
	}   //  getFieldBackground_Mandatory

	/**
	 *  Return Inactive field background color
	 *  @return Color
	 */
	public static Color getFieldBackground_Inactive()
	{
		Color c = UIManager.getColor(AdempiereLookAndFeel.INACTIVE_BG_KEY);
		if ( c != null )
			return ColorBlind.getDichromatColor(c);
		else
			return getFieldBackground_Normal();
	}   //  getFieldBackground_Inactive

	/**
	 *  Return form background color "control".
	 *  Windows = lightGray
	 *  @return Color
	 */
	public static Color getFormBackground()
	{
		return ColorBlind.getDichromatColor(UIManager.getColor("control"));
	}   //  getFormBackground

	/**
	 *	Info Background Color "info"
	 *  Windows = info (light yellow)
	 *  @return Color
	 */
	public static Color getInfoBackground()
	{
		Color c = UIManager.getColor(AdempiereLookAndFeel.INFO_BG_KEY);
		if (c == null) 
			c = UIManager.getColor("info");
		return ColorBlind.getDichromatColor(c);
	}   //  getInfoBackground


	/****** Text *************************************************************/

	/**
	 *	Normal field text foreground color "textText"
	 *  Windows = black
	 *  @return Color
	 */
	public static Color getTextColor_Normal()
	{
		return ColorBlind.getDichromatColor(UIManager.getColor("textText"));
	}   //  getText_Normal

	/**
	 *	OK Text Foreground Color (Theme)
	 *  @return Color
	 */
	public static Color getTextColor_OK()
	{
		return getTextColor_Normal();
	}   //  getText_OK

	/**
	 *	Issue Text Foreground Color (Theme)
	 *  @return Color
	 */
	public static Color getTextColor_Issue()
	{
		Color c = UIManager.getColor(AdempiereLookAndFeel.ERROR_FG_KEY);
		if (c == null)
			c = AdempiereLookAndFeel.DEFAULT_ERROR_FG;
		return ColorBlind.getDichromatColor(c);
	}   //  getText_Issue

	/**
	 *  Label Text foreground Color "controlText"
	 *  Windows = black
	 *  @return Color
	 */
	public static Color getTextColor_Label()
	{
		return ColorBlind.getDichromatColor(UIManager.getColor("controlText"));
	}   //  getTextColor_Label

	/**
	 * 	Get Primary1
	 *	@return primary 1
	 */
	public static Color getPrimary1()
	{
		return ColorBlind.getDichromatColor(MetalLookAndFeel.getCurrentTheme().getPrimaryControlDarkShadow());
	}
	/**
	 * 	Get Primary2
	 *	@return primary 2
	 */
	public static Color getPrimary2()
	{
		return ColorBlind.getDichromatColor(MetalLookAndFeel.getCurrentTheme().getPrimaryControlShadow());
	}
	/**
	 * 	Get Primary3
	 *	@return primary 3
	 */
	public static Color getPrimary3()
	{
		return ColorBlind.getDichromatColor(MetalLookAndFeel.getCurrentTheme().getPrimaryControl());
	}
	/**
	 * 	Get Secondary 1
	 *	@return secondary 1
	 */
	public static Color getSecondary1()
	{
		return ColorBlind.getDichromatColor(MetalLookAndFeel.getCurrentTheme().getControlDarkShadow());
	}
	/**
	 * 	Get Secondary 2
	 *	@return secondary 2
	 */
	public static Color getSecondary2()
	{
		return ColorBlind.getDichromatColor(MetalLookAndFeel.getCurrentTheme().getControlShadow());
	}
	/**
	 * 	Get Secondary 3
	 *	@return secondary 3
	 */
	public static Color getSecondary3()
	{
		return ColorBlind.getDichromatColor(MetalLookAndFeel.getCurrentTheme().getControl());
	}


	/****** Fonts ************************************************************/

	/**
	 *  Get Header Font (window title font)
	 *  @return font
	 */
	public static Font getFont_Header()
	{
		return MetalLookAndFeel.getWindowTitleFont();
	}   //  getFont_Header

	/**
	 *  Get Field Font
	 *  @return font
	 */
	public static Font getFont_Field()
	{
		return UIManager.getFont("TextField.font");
	}   //  getFont_Field

	/**
	 *  Get Label Font
	 *  @return font
	 */
	public static Font getFont_Label()
	{
		return UIManager.getFont("Label.font");
	}   //  setFont_Label

	/**
	 *  Get Small (report) Font
	 *  @return font
	 */
	public static Font getFont_Small()
	{
		return MetalLookAndFeel.getSubTextFont();
	}   //  setFont_Small

	/****** Available L&F ****************************************************/

	/** Available Look and Feels (LAFs) */
	private static final List<ValueNamePair> LOOKANDFEELS;
	/** Default LAF        */
	private static final ValueNamePair LOOKANDFEEL_DEFAULT;
	/** Available Themes   */
	private static final List<ValueNamePair> s_metalThemes = ImmutableList.of();
	private static final List<ValueNamePair> THEMES_PLASTIC;

	//
	//default theme
	private static final ValueNamePair s_vp_metalTheme = null;
	private static final ValueNamePair THEME_PLASTIC_DEFAULT;

	/**
	 * Static Initializer:
	 * <ul>
	 * <li>Fill available Look&Feels
	 * <li>Fill available Themes
	 * <li>Set default L&F
	 * <li>Set default themes
	 * </ul>
	 */
	static
	{
		final List<ValueNamePair> lafList = new ArrayList<>();
		final List<ValueNamePair> plasticThemes = new ArrayList<>();
		
		final ValueNamePair lafAdempiere = new ValueNamePair(AdempiereLookAndFeel.class.getName(), AdempiereLookAndFeel.NAME);
		lafList.add(lafAdempiere);

		final ValueNamePair metasFreshTheme = new ValueNamePair(MetasFreshTheme.class.getName(), MetasFreshTheme.NAME);
		plasticThemes.add(metasFreshTheme);
		
		//
		//  Install discovered PLAFs
		for (final ValueNamePair laf : lafList)
		{
			UIManager.installLookAndFeel(laf.getName(), laf.getValue());
		}
		
		//
		LOOKANDFEELS = ImmutableList.copyOf(lafList);
		LOOKANDFEEL_DEFAULT = lafAdempiere;
		
		THEMES_PLASTIC = ImmutableList.copyOf(plasticThemes);
		THEME_PLASTIC_DEFAULT = metasFreshTheme;
	}

	/**
	 *  Get available Look And Feels
	 *  @return List of ValueNamePair with name and class of Look and Feel
	 */
	public static List<ValueNamePair> getPLAFs()
	{
		return LOOKANDFEELS;
	}   //  getPLAFs
	
	public static ValueNamePair[] getPLAFsAsArray()
	{
		return LOOKANDFEELS.toArray(new ValueNamePair[LOOKANDFEELS.size()]);
	}

	/**
	 *  Get the list of available Metal or Plastic Themes.
	 *  @return List of {@link ValueNamePair}s with Names of Metal Themes
	 */
	public static List<ValueNamePair> getThemes()
	{
		final LookAndFeel l = UIManager.getLookAndFeel();
		if ( l instanceof AdempiereLookAndFeel)
			return THEMES_PLASTIC;
		else if ( l instanceof MetalLookAndFeel)
			return s_metalThemes;
		
		return ImmutableList.of(); // empty
	}   //  getThemes
	
	public static ValueNamePair[] getThemesAsArray()
	{
		final List<ValueNamePair> themes = getThemes();
		return themes.toArray(new ValueNamePair[themes.size()]);
	}

	
	/**************************************************************************
	 *  Set PLAF based on Ini Properties
	 */
	public static void setPLAF()
	{
		final String look = Ini.getProperty(Ini.P_UI_LOOK);
		final String lookTheme = Ini.getProperty(Ini.P_UI_THEME);
		//  Search for PLAF
		ValueNamePair plaf = null;
		for (final ValueNamePair laf : getPLAFs())
		{
			if (laf.getName().equals(look))
			{
				plaf = laf;
				break;
			}
		}
		// Set default L&F
		if (plaf == null)
		{
			plaf = LOOKANDFEEL_DEFAULT;
		}
		
		//
		// Search for plastic themes
		ValueNamePair theme = null;
		if (theme == null) 
		{
			for (final ValueNamePair t : THEMES_PLASTIC)
			{
				if (t.getName().equals(lookTheme))
				{
					theme = t;
					break;
				}
			}
		}
		
		//
		// Search for metal themes
		if (theme == null)
		{
			for (ValueNamePair t : s_metalThemes)
			{
				if (t.getName().equals(lookTheme))
				{
					theme = t;
					break;
				}
			}
		}
		
		if (theme == null)
		{
			theme = THEME_PLASTIC_DEFAULT;
		}
		
		//  Set PLAF
		setPLAF(plaf, theme, true); // updateIni=true
	}   //  setPLAF

	/**
	 *  Set PLAF and update Ini
	 *
	 *  @param plaf     ValueNamePair of the PLAF to be set
	 *  @param themeVNP    Optional Theme name
	 *  @param upateIni     Update setting to INI
	 */
	public static void setPLAF(final ValueNamePair plaf, ValueNamePair themeVNP, final boolean updateIni)
	{
		if (plaf == null)
			return;
		log.info(plaf	+ (themeVNP == null ? "" : (" - " + themeVNP)));

		//	  Look & Feel
		final Class<?> lafClass;
		try {
			lafClass = Class.forName(plaf.getValue());
		}
		catch (Exception e)
		{
			log.error("Failed loading the L&F class", e);
			return;
		}
		
		if (updateIni)
		{
			Ini.setProperty(Ini.P_UI_LOOK, plaf.getName());
			//  Optional Theme
			Ini.setProperty(Ini.P_UI_THEME, ""); // will be updated later in this method
		}
		
		//  Default Theme
		boolean metal = MetalLookAndFeel.class.isAssignableFrom(lafClass);
		boolean adempiere = AdempiereLookAndFeel.class.isAssignableFrom(lafClass);
		if (themeVNP == null && metal)
		{
			if (adempiere)
				themeVNP = THEME_PLASTIC_DEFAULT;
			else
				themeVNP = s_vp_metalTheme;
		}
		if (themeVNP != null && metal && themeVNP.getValue().length() > 0)
		{
			try
			{
				Class<?> c = Class.forName(themeVNP.getValue());
				final MetalTheme metalTheme = (MetalTheme)c.newInstance();
				if (adempiere && metalTheme instanceof PlasticTheme)
					AdempiereLookAndFeel.setCurrentTheme((PlasticTheme)metalTheme);
				else 
					MetalLookAndFeel.setCurrentTheme(metalTheme);
				//
				if (updateIni)
				{
					Ini.setProperty(Ini.P_UI_THEME, themeVNP.getName());
				}
			}
			catch (Exception e)
			{
				log.error("Theme - " + e.getMessage(), e);
			}
		}
		try
		{
			UIManager.setLookAndFeel((LookAndFeel)lafClass.newInstance());
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
		log.info(plaf + " - " + themeVNP);
	}   //  setPLAF

	/**
	 *  Update UI of this and parent Windows
	 *  @param win window
	 */
	public static void updateUI (final Window win)
	{
		if (win == null)
			return;
		Window c = win;
		do
		{
			SwingUtilities.updateComponentTreeUI(c);
			c.invalidate();
			c.pack();
			c.validate();
			c.repaint();
			c = c.getOwner();
		}
		while (c != null);
	}   //  updateUI

	/**
	 *  Reset PLAF Settings
	 */
	public static void reset()
	{
		// Clean Theme Properties
		AdempierePLAF.setPLAF();
	}  // reset

	/**
	 *  Is AdempiereL&F the active L&F
	 *  @return true if L&F is Adempiere
	 */
	public static boolean isActive()
	{
		return UIManager.getLookAndFeel() instanceof AdempiereLookAndFeel;
	}   //  isActive

	/*************************************************************************/

	private static final ResourceBundle s_res = PlafRes.getBundle();

	/**
	 *  Create OK Button
	 *  @return OK button
	 */
	public static CButton getOKButton()
	{
		CButton b = new CButton();
		b.setIcon(Images.getImageIcon2("Ok24"));
		b.setMargin(new Insets(0,10,0,10));
		b.setToolTipText (s_res.getString("OK"));
		return b;
	}   //  getOKButton

	/**
	 *  Create Cancel Button
	 *  @return Cancel button
	 */
	public static CButton getCancelButton()
	{
		CButton b = new CButton();
		b.setIcon(Images.getImageIcon2("Cancel24"));
		b.setMargin(new Insets(0,10,0,10));
		b.setToolTipText (s_res.getString("Cancel"));
		return b;
	}   //  getCancelButton

	/**
	 *  Center Window on Screen and show it
	 *  @param window window
	 */
	public static void showCenterScreen (Window window)
	{
		window.pack();
		Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension wSize = window.getSize();
		window.setLocation(((sSize.width-wSize.width)/2), ((sSize.height-wSize.height)/2));
		window.toFront();
		window.setVisible(true);
	}	//	showCenterScreen

	/**
	 * Invoke the correct method to set current metal based theme.
	 * Supported look and feel are Metal, Plastic and Compiere.  
	 * @param laf Metal based look and feel
	 * @param theme Metal based theme
	 */
	static void setCurrentMetalTheme(final MetalLookAndFeel laf, final MetalTheme theme)
	{
		if (laf instanceof AdempiereLookAndFeel && theme instanceof PlasticTheme)
		{
			AdempiereLookAndFeel.setCurrentTheme((PlasticTheme)theme);
		}
		else
		{
			MetalLookAndFeel.setCurrentTheme(theme);
		}
	}

	public static Color getColor(final String colorKey)
	{
		return UIManager.getColor(colorKey);
	}

	public static Color getColor(final String colorKey, final Color defaultColor)
	{
		final Color color = getColor(colorKey);
		return color == null ? defaultColor : color;
	}

	public static Color getColor(final String colorKey, final String colorKeyDefault, final Color defaultColor)
	{
		Color color = getColor(colorKey);
		if (color != null)
		{
			return color;
		}
		color = getColor(colorKeyDefault);
		if (color != null)
		{
			return color;
		}
		return defaultColor;
	}

	/**
	 * Creates an active value which actual value will always be resolved as the value of the given proxy key.
	 * 
	 * If there is no value for proxy key, the given default value will be returned.
	 * 
	 * @param proxyKey
	 * @param defaultValue
	 * @return active value which forward to the value of given given proxy key.
	 */
	public static UIDefaults.ActiveValue createActiveValueProxy(final String proxyKey, final Object defaultValue)
	{
		return new UIDefaults.ActiveValue()
		{
			@Override
			public Object createValue(final UIDefaults table)
			{
				final Object value = table.get(proxyKey);
				return value != null ? value : defaultValue;
			}
		};
	}

	/**
	 * Gets integer value of given key. If no value was found or the value is not {@link Integer} the default value will be returned.
	 * 
	 * @param key
	 * @param defaultValue
	 * @return integer value or default value
	 */
	public static int getInt(final String key, final int defaultValue)
	{
		final Object value = UIManager.getDefaults().get(key);
		if (value instanceof Integer)
		{
			return ((Integer)value).intValue();
		}
		return defaultValue;
	}
	
	public static final boolean getBoolean(final String key, final boolean defaultValue)
	{
		final Object value = UIManager.getDefaults().get(key);
		if (value instanceof Boolean)
		{
			return ((Boolean)value).booleanValue();
		}
		return defaultValue;
	}
	
	/**
	 * Extracts the "uiClassID" from given component class.
	 * 
	 * Usually this method shall not fail, but in case it fails, the error will be logged and <code>defaultUIClassID</code> will be returned.
	 * 
	 * @param componentClass
	 * @param defaultUIClassID
	 * @return uiClassID or <code>defaultUIClassID</code>.
	 */
	public static final String getUIClassID(final Class<?> componentClass, final String defaultUIClassID)
	{
		Check.assumeNotNull(componentClass, "componentClass not null");
		try
		{
			@SuppressWarnings("unchecked")
			final Set<Field> fields = ReflectionUtils.getFields(componentClass, new Predicate<Field>()
			{

				@Override
				public boolean apply(final Field field)
				{
					return "uiClassID".equals(field.getName());
				}
			});

			final Field field = ListUtils.singleElement(fields);
			if (!field.isAccessible())
			{
				field.setAccessible(true);
			}
			final String uiClassID = (String)field.get(null);
			return uiClassID;
		}
		catch (Exception e)
		{
			log.warn("Failed getting the uiClassID of " + componentClass, e);
			return defaultUIClassID;
		}
	}
	
	public static final String getString(final String key, final String defaultValue)
	{
		final Object value = UIManager.getDefaults().get(key);
		if(value == null)
		{
			return defaultValue;
		}
		return value.toString();
	}
	
	public static void setDefaultBackground(final JComponent comp)
	{
		comp.putClientProperty(AdempiereLookAndFeel.BACKGROUND, MFColor.ofFlatColor(AdempierePLAF.getFormBackground()));
	}
}   //  AdempierePLAF
