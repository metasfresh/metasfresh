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
 *  @author     Adam Bodurka
 *  @version    $Id: PlafRes_pl.java,v 1.2 2006/07/30 00:52:24 jjanke Exp $
 */
public class PlafRes_pl extends ListResourceBundle
{
	/** The data    */
	static final Object[][] contents = new String[][]
	{
	{ "BackColType",            "Typ koloru t\u0142a" },
	{ "BackColType_Flat",       "P\u0142aski" },
	{ "BackColType_Gradient",   "Stopniowany" },
	{ "BackColType_Lines",      "Linie" },
	{ "BackColType_Texture",    "Tekstura" },
	//
	{ "LookAndFeelEditor",      "Edytor Wygl\u0105du" },
	{ "LookAndFeel",            "Wygl\u0105d" },
	{ "Theme",                  "Temat" },
	{ "EditAdempiereTheme",      "Edytuj Temat Compiera" },
	{ "SetDefault",             "Domy\u015blne T\u0142o" },
	{ "SetDefaultColor",        "Domy\u015blny Kolor" },
	{ "Example",                "Przyk\u0142ad" },
	{ "Reset",                  "Resetuj" },
	{ "OK",                     "OK" },
	{ "Cancel",                 "Anuluj" },
	//
	{ "AdempiereThemeEditor",    "Edytor Tematu Compiera" },
	{ "MetalColors",            "Kolory Metalowe" },
	{ "AdempiereColors",         "Kolory Compiera" },
	{ "AdempiereFonts",          "Czcionki Compiera" },
	{ "Primary1Info",           "Tekst Etykiety" },
	{ "Primary1",               "Podstawowy 1" },
	{ "Primary2Info",           "Linia Fokusu, Wybrany CheckBox" },
	{ "Primary2",               "Podstawowy 2" },
	{ "Primary3Info",           "Wybrany Wiersz Tabeli, Wybrany Tekst, T\u0142o Podpowiedzi" },
	{ "Primary3",               "Podstawowy 3" },
	{ "Secondary1Info",         "Linie Obramowania" },
	{ "Secondary1",             "Drugorz\u0119dny 1" },
	{ "Secondary2Info",         "Nieaktywne Zak\u0142adki, Naci\u015bni\u0119te Pola, Nieaktywna Ramka + Tekst" },
	{ "Secondary2",             "Drugorz\u0119dny 2" },
	{ "Secondary3Info",         "T\u0142o" },
	{ "Secondary3",             "Drugorz\u0119dny 3" },
	//
	{ "ControlFontInfo",        "Czcionka Kontrolki" },
	{ "ControlFont",            "Czcionka Etykiety" },
	{ "SystemFontInfo",         "Podpowiedzi, Ga\u0142\u0119zie drzewa" },
	{ "SystemFont",             "Czcionka Systemowa" },
	{ "UserFontInfo",           "Dane wprowadzone przez U\u017cytkownika" },
	{ "UserFont",               "Czcionka Pola" },
//	{ "SmallFontInfo",          "Raporty" },
	{ "SmallFont",              "Ma\u0142a Czcionka" },
	{ "WindowTitleFont",         "Czcionka Tytu\u0142u" },
	{ "MenuFont",               "Czcionka Menu" },
	//
	{ "MandatoryInfo",          "Obowi\u0105zkowe T\u0142o Pola" },
	{ "Mandatory",              "Obowi\u0105zkowe" },
	{ "ErrorInfo",              "B\u0142\u0105d T\u0142a Pola" },
	{ "Error",                  "B\u0142\u0105d" },
	{ "InfoInfo",               "Informacja T\u0142a Pola" },
	{ "Info",                   "Informacja" },
	{ "WhiteInfo",              "Linie" },
	{ "White",                  "Bia\u0142y" },
	{ "BlackInfo",              "Linie, Tekst" },
	{ "Black",                  "Czarny" },
	{ "InactiveInfo",           "Nieaktywne T\u0142o Pola" },
	{ "Inactive",               "Nieaktywny" },
	{ "TextOKInfo",             "OK Pierwszoplanowy Tekst" },
	{ "TextOK",                 "Tekst - OK" },
	{ "TextIssueInfo",          "B\u0142\u0105d Pierwszoplanowego Tekstu" },
	{ "TextIssue",              "Tekst - B\u0142\u0105d" },
	//
	{ "FontChooser",            "Wyb\u00f3r Czcionki" },
	{ "Fonts",                  "Czcionki" },
	{ "Plain",                  "G\u0142adki" },
	{ "Italic",                 "Kursywa" },
	{ "Bold",                   "Pogrubiony" },
	{ "BoldItalic",             "Pogrubiony i Kursywa" },
	{ "Name",                   "Nazwa" },
	{ "Size",                   "Rozmiar" },
	{ "Style",                  "Styl" },
	{ "TestString",             "To jest tylko test!" },
	{ "FontString",             "Czcionka" },
	//
	{ "AdempiereColorEditor",    "Edytor Koloru Compiera" },
	{ "AdempiereType",           "Typ Koloru" },
	{ "GradientUpperColor",     "G\u00f3rny Kolor Stopniowania" },
	{ "GradientLowerColor",     "Dolny Kolor Stopniowania" },
	{ "GradientStart",          "Pocz\u0105tek Stopniowania" },
	{ "GradientDistance",       "Odst\u0119p Stopniowania" },
	{ "TextureURL",             "Tekstura URL" },
	{ "TextureAlpha",           "Tekstura Alpha" },
	{ "TextureTaintColor",      "Kolor t\u0142a Tekstury" },
	{ "LineColor",              "Kolor Linii" },
	{ "LineBackColor",          "Kolor T\u0142a" },
	{ "LineWidth",              "Grubo\u015b\u0107 Linii" },
	{ "LineDistance",           "Odst\u0119p Linii" },
	{ "FlatColor",              "Kolor P\u0142aski" }
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
