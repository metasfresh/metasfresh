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
 *  @author     Robin Hoo, Redhuan D. Oon (OPEN FIX SDN BHD - 2003)
 *  @version    $Id: PlafRes_ms.java,v 1.2 2006/07/30 00:52:23 jjanke Exp $
 */
public class PlafRes_ms extends ListResourceBundle
{
	/** The data    */
	static final Object[][] contents = new String[][]
	{
	{ "BackColType",            "Jenis Warna Latar Belakang" },
	{ "BackColType_Flat",       "Leper" },
	{ "BackColType_Gradient",   "Gradient" },
	{ "BackColType_Lines",      "Garisan" },
	{ "BackColType_Texture",    "Texture" },
	//
	{ "LookAndFeelEditor",      "Bentuk Paparan Editor" },
	{ "LookAndFeel",            "Bentuk Paparan" },
	{ "Theme",                  "Tema" },
	{ "EditAdempiereTheme",      "Edit Tema Adempiere" },
	{ "SetDefault",             "Default LatarBelakang" },
	{ "SetDefaultColor",        "Warna LatarBelankang" },
	{ "ColorBlind",             "Kecacatan Warna" },
	{ "Example",                "Contoh" },
	{ "Reset",                  "Reset" },
	{ "OK",                     "OK" },
	{ "Cancel",                 "Batal" },
	//
	{ "AdempiereThemeEditor",    "Tema Adempiere Editor" },
	{ "MetalColors",            "Warna Metal" },
	{ "AdempiereColors",         "Warna Adempiere" },
	{ "AdempiereFonts",          "Adempiere Fonts" },
	{ "Primary1Info",           "Bayang, Pemisah" },
	{ "Primary1",               "Primary 1" },
	{ "Primary2Info",           "Garisan Fokus, Menu Terpilih" },
	{ "Primary2",               "Primary 2" },
	{ "Primary3Info",           "Table Selected Row, Selected Text, ToolTip Background" },
	{ "Primary3",               "Primary 3" },
	{ "Secondary1Info",         "Garis Sempadan" },
	{ "Secondary1",             "Menengah 1" },
	{ "Secondary2Info",         "Tab Tak Aktif, Medan Tertekan, Sempadan Tak Aktif + Teks" },
	{ "Secondary2",             "Menengah 2" },
	{ "Secondary3Info",         "LatarBelakang" },
	{ "Secondary3",             "Menengah 3" },
	//
	{ "ControlFontInfo",        "Fon Kawalan" },
	{ "ControlFont",            "Fon Label" },
	{ "SystemFontInfo",         "Tool Tip, Tree nodes" },
	{ "SystemFont",             "Fon Sistem" },
	{ "UserFontInfo",           "User Entered Data" },
	{ "UserFont",               "Fon Medan" },
//	{ "SmallFontInfo",          "Reports" },
	{ "SmallFont",              "Fon Kecil" },
	{ "WindowTitleFont",         "Fon Tajuk" },
	{ "MenuFont",               "Fon Menu" },
	//
	{ "MandatoryInfo",          "Medan LatarBelakang Wajib" },
	{ "Mandatory",              "Wajib" },
	{ "ErrorInfo",              "Error Field LatarBelakang" },
	{ "Error",                  "Error" },
	{ "InfoInfo",               "Info Field Background" },
	{ "Info",                   "Info" },
	{ "WhiteInfo",              "Garisan" },
	{ "White",                  "Putih" },
	{ "BlackInfo",              "Garisan, Teks" },
	{ "Black",                  "Hitam" },
	{ "InactiveInfo",           "Inactive Field Background" },
	{ "Inactive",               "Tak Aktif" },
	{ "TextOKInfo",             "OK Text Foreground" },
	{ "TextOK",                 "Teks - OK" },
	{ "TextIssueInfo",          "Error Text Foreground" },
	{ "TextIssue",              "Teks - Error" },
	//
	{ "FontChooser",            "Pemilih Fon" },
	{ "Fonts",                  "Fon" },
	{ "Plain",                  "Biasa" },
	{ "Italic",                 "Italic" },
	{ "Bold",                   "Bold" },
	{ "BoldItalic",             "Bold & Italic" },
	{ "Name",                   "Nama" },
	{ "Size",                   "Saiz" },
	{ "Style",                  "Stail" },
	{ "TestString",             "Ini cuma ujian! The quick brown Fox is doing something. 12,3456.78 LetterLOne = l1 LetterOZero = O0" },
	{ "FontString",             "Fon" },
	//
	{ "AdempiereColorEditor",    "Adempiere Color Editor" },
	{ "AdempiereType",           "Jenis Warna" },
	{ "GradientUpperColor",     "Gradient Upper Color" },
	{ "GradientLowerColor",     "Gradient Lower Color" },
	{ "GradientStart",          "Gradient Start" },
	{ "GradientDistance",       "Gradient Distance" },
	{ "TextureURL",             "Texture URL" },
	{ "TextureAlpha",           "Texture Alpha" },
	{ "TextureTaintColor",      "Texture Taint Color" },
	{ "LineColor",              "Warna Garisan" },
	{ "LineBackColor",          "Warna LatarBelakang" },
	{ "LineWidth",              "Lebar Garisan" },
	{ "LineDistance",           "Jarak Garisan" },
	{ "FlatColor",              "Warna Leper" }
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
