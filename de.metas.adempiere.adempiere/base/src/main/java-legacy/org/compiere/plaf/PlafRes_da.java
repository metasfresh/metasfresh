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
 *  Translation Texts for Look & Feel
 *
 *  @author     Jorg Janke
 *  @version    $Id: PlafRes_da.java,v 1.2 2006/07/30 00:52:23 jjanke Exp $
 */
public class PlafRes_da extends ListResourceBundle
{
	/** The data    */
	static final Object[][] contents = new String[][]
	{
	{ "BackColType",            "Baggrund: Farvetype" },
	{ "BackColType_Flat",       "Fast" },
	{ "BackColType_Gradient",   "Farveforløb" },
	{ "BackColType_Lines",      "Linjer" },
	{ "BackColType_Texture",    "Struktur" },
	//
	{ "LookAndFeelEditor",      "Redigér & udseende" },
	{ "LookAndFeel",            "Udseende" },
	{ "Theme",                  "Tema" },
	{ "EditAdempiereTheme",      "Redigér Adempiere tema" },
	{ "SetDefault",             "Baggrund: Standard" },
	{ "SetDefaultColor",        "Baggrundsfarve" },
	{ "ColorBlind",             "Farvereduktion" },
	{ "Example",                "Eksempel" },
	{ "Reset",                  "Gendan" },
	{ "OK",                     "OK" },
	{ "Cancel",                 "Annullér" },
	//
	{ "AdempiereThemeEditor",    "Adempiere-tema: Redigér" },
	{ "MetalColors",            "Metal-farver" },
	{ "AdempiereColors",         "Adempiere-farver" },
	{ "AdempiereFonts",          "Adempiere-skrifttyper" },
	{ "Primary1Info",           "Skygge, Separator" },
	{ "Primary1",               "Primær 1" },
	{ "Primary2Info",           "Markeret element, Markeret menu" },
	{ "Primary2",               "Primær 2" },
	{ "Primary3Info",           "Markeret række i tabel, Markeret tekst, Værktøjstip - baggr." },
	{ "Primary3",               "Prim�r 3" },
	{ "Secondary1Info",         "Rammelinjer" },
	{ "Secondary1",             "Sekundær 1" },
	{ "Secondary2Info",         "Ikke-aktive faner, Markerede felter, Ikke-aktiv ramme + tekst" },
	{ "Secondary2",             "Sekundær 2" },
	{ "Secondary3Info",         "Baggrund" },
	{ "Secondary3",             "Sekundær 3" },
	//
	{ "ControlFontInfo",        "Skrifttype: Knapper" },
	{ "ControlFont",            "Skrifttype: Etiket" },
	{ "SystemFontInfo",         "Værktøjstip, Strukturknuder" },
	{ "SystemFont",             "Skrifttype: System" },
	{ "UserFontInfo",           "Anvend" },
	{ "UserFont",               "Skrifttype: Felt" },
//	{ "SmallFontInfo",          "Rapporter" },
	{ "SmallFont",              "Lille" },
	{ "WindowTitleFont",         "Skrifttype: Titellinje" },
	{ "MenuFont",               "Skrifttype: Menu" },
	//
	{ "MandatoryInfo",          "Tvungen feltbaggrund" },
	{ "Mandatory",              "Tvungen" },
	{ "ErrorInfo",              "Fejl: Feltbaggrund" },
	{ "Error",                  "Fejl" },
	{ "InfoInfo",               "Info: Feltbaggrund" },
	{ "Info",                   "Info" },
	{ "WhiteInfo",              "Linjer" },
	{ "White",                  "Hvid" },
	{ "BlackInfo",              "Linjer, Tekst" },
	{ "Black",                  "Sort" },
	{ "InactiveInfo",           "Inaktiv feltbaggrund" },
	{ "Inactive",               "Inaktiv" },
	{ "TextOKInfo",             "OK: Tekstforgrund" },
	{ "TextOK",                 "Tekst: OK" },
	{ "TextIssueInfo",          "Fejl: Tekstforgrund" },
	{ "TextIssue",              "Tekst: Fejl" },
	//
	{ "FontChooser",            "Skriftype" },
	{ "Fonts",                  "Skrifttyper" },
	{ "Plain",                  "Normal" },
	{ "Italic",                 "Kursiv" },
	{ "Bold",                   "Fed" },
	{ "BoldItalic",             "Fed & kursiv" },
	{ "Name",                   "Navn" },
	{ "Size",                   "Størrelse" },
	{ "Style",                  "Type" },
	{ "TestString",             "Dette er en prøve! 12.3456,78 BogstavLEn = l1 BogstavONul = O0" },
	{ "FontString",             "Skrifttype" },
	//
	{ "AdempiereColorEditor",    "Adempiere-farveeditor" },
	{ "AdempiereType",           "Farvetype" },
	{ "GradientUpperColor",     "Farveforløb: Farve 1" },
	{ "GradientLowerColor",     "Farveforløb: Farve 2" },
	{ "GradientStart",          "Farveforløb: Start" },
	{ "GradientDistance",       "Farveforløb: Afstand" },
	{ "TextureURL",             "Struktur: URL" },
	{ "TextureAlpha",           "Struktur: Alpha" },
	{ "TextureTaintColor",      "Struktur: Pletvis" },
	{ "LineColor",              "Linjefarve" },
	{ "LineBackColor",          "Baggrundsfarve" },
	{ "LineWidth",              "Linjebredde" },
	{ "LineDistance",           "Linjeafstand" },
	{ "FlatColor",              "Fast farve" }
	};

	/**
	 * Get Contents
	 * @return contents
	 */
	public Object[][] getContents()
	{
		return contents;
	}
}   //  Res
