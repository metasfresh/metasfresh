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
package org.compiere.plaf;

import java.util.ListResourceBundle;

/**
 *  Translation Texts for Look & Feel (German)
 *
 *  @author     Jorg Janke
 *  @version    $Id: PlafRes_de.java,v 1.2 2006/07/30 00:52:23 jjanke Exp $
 */
public class PlafRes_de extends ListResourceBundle
{
	/** The data    */
	static final Object[][] contents = new String[][]
	{
	{ "BackColType",            "Hintergrund Farbtyp" },
	{ "BackColType_Flat",       "Flach" },
	{ "BackColType_Gradient",   "Gradient" },
	{ "BackColType_Lines",      "Linie" },
	{ "BackColType_Texture",    "Textur" },
	//
	{ "LookAndFeelEditor",      "Benutzeroberflächen Editor" },
	{ "LookAndFeel",            "Oberfläche" },
	{ "Theme",                  "Thema" },
	{ "EditAdempiereTheme",      "Adempiere Thema bearbeiten" },
	{ "SetDefault",             "Standard Hintergrund" },
	{ "SetDefaultColor",        "Hintergrund Farbe" },
	{ "ColorBlind",             "Farbblindheit" },
	{ "Example",                "Beispiel" },
	{ "Reset",                  "Zurücksetzen" },
	{ "OK",                     "Ja" },
	{ "Cancel",                 "Abbruch" },
	//
	{ "AdempiereThemeEditor",    "Adempiere Thema Editor" },
	{ "MetalColors",            "Metal Farben" },
	{ "AdempiereColors",         "Adempiere Farben" },
	{ "AdempiereFonts",          "Adempiere Schriften" },
	{ "Primary1Info",           "Schatten, Separator" },
	{ "Primary1",               "Primär 1" },
	{ "Primary2Info",           "Fokuslinien, Aktives Men�" },
	{ "Primary2",               "Primär 2" },
	{ "Primary3Info",           "Tabelle Selected Row, Selected Text, Tool Tip Hintergrund" },
	{ "Primary3",               "Primär 3" },
	{ "Secondary1Info",         "Rahmen Lines" },
	{ "Secondary1",             "Sekundär 1" },
	{ "Secondary2Info",         "Inaktive Tabs, Pressed Felder, Inaktive Rahmen + Text" },
	{ "Secondary2",             "Sekundär 2" },
	{ "Secondary3Info",         "Hintergrund" },
	{ "Secundary3",             "Sekundär 3" },
	//
	{ "ControlFontInfo",        "Labels" },
	{ "ControlFont",            "Standard Schrift" },
	{ "SystemFontInfo",         "Tool Tip" },
	{ "SystemFont",             "System Schrift" },
	{ "UserFontInfo",           "Entered Data" },
	{ "UserFont",               "Nutzer Schrift" },
	{ "SmallFont",              "Kleine Schrift" },
	{ "WindowTitleFont",         "Titel Schrift" },
	{ "MenuFont",               "Menü Schrift" },
	//
	{ "MandatoryInfo",          "Erforderliches Feld Hintergrund" },
	{ "Mandatory",              "Erforderlich" },
	{ "ErrorInfo",              "Fehler Feld Hintergrund" },
	{ "Error",                  "Fehler" },
	{ "InfoInfo",               "Info Feld Hintergrund" },
	{ "Info",                   "Info" },
	{ "WhiteInfo",              "Linien" },
	{ "White",                  "Weiß" },
	{ "BlackInfo",              "Linien, Text" },
	{ "Black",                  "Schwarz" },
	{ "InactiveInfo",           "Inaktiv Feld Hintergrund" },
	{ "Inactive",               "Inaktiv" },
	{ "TextOKInfo",             "OK Text Fordergrund" },
	{ "TextOK",                 "Text - OK" },
	{ "TextIssueInfo",          "Fehler Text Fordergrund" },
	{ "TextIssue",              "Text - Fehler" },
	//
	{ "FontChooser",            "Schrift Auswahl" },
	{ "Fonts",                  "Schriften" },
	{ "Plain",                  "Normal" },
	{ "Italic",                 "Italic" },
	{ "Bold",                   "Fett" },
	{ "BoldItalic",             "Fett & Italic" },
	{ "Name",                   "Name" },
	{ "Size",                   "Größe" },
	{ "Style",                  "Stil" },
	{ "TestString",             "Dies ist nur ein Test! The quick brown Fox is doing something. 12,3456.78 BuchstabeLEins = l1 BuchstabeONull = O0" },
	{ "FontString",             "Schrift" },
	//
	{ "AdempiereColorEditor",    "Adempiere Farben Auswahl" },
	{ "AdempiereType",           "Farbtyp" },
	{ "GradientUpperColor",     "Gradient obere Farbe" },
	{ "GradientLowerColor",     "Gradient untere Farbe" },
	{ "GradientStart",          "Gradient Start" },
	{ "GradientDistance",       "Gradient Distanz" },
	{ "TextureURL",             "Textur URL" },
	{ "TextureAlpha",           "Textur Alpha" },
	{ "TextureTaintColor",      "Textur Tönung Farbe" },
	{ "LineColor",              "Linie Farbe" },
	{ "LineBackColor",          "Hintergrund Farbe" },
	{ "LineWidth",              "Linie Breite" },
	{ "LineDistance",           "Linie Distanz" },
	{ "FlatColor",              "Flache Farbe" }
	};

	/**
	 * Get Contents
	 * @return contents
	 */
	public Object[][] getContents()
	{
		return contents;
	}
}   //  Res_de
