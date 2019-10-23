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

import java.util.ListResourceBundle;

/**
 *  Base Resource Bundle
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: ALoginRes_ml.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public final class ALoginRes_ml extends ListResourceBundle
{
	/** Translation Content     */
	static final Object[][] contents = new String[][]
	{
	{ "Connection",         "Sambungan" },
	{ "Defaults",           "Sedia-ada" },
	{ "Login",              "Laluan Masuk" },
	{ "File",               "Fail" },
	{ "Exit",               "Keluar" },
	{ "Help",               "Bantuan" },
	{ "About",              "Maklumat" },
	{ "Host",               "Host" },
	{ "Database",           "Pengkalan Data" },
	{ "User",               "Pengguna" },
	{ "EnterUser",          "Masuk Pengguna" },
	{ "Password",           "Kata Laluan" },
	{ "EnterPassword",      "Masuk Kata Laluan" },
	{ "Language",           "Bahasa" },
	{ "SelectLanguage",     "Pilih Bahasa Anda" },
	{ "Role",               "Peranan" },
	{ "Client",             "Klien" },
	{ "Organization",       "Organisasi" },
	{ "Date",               "Tarikh" },
	{ "Warehouse",          "Tempat Simpanan" },
	{ "Printer",            "Pencetak" },
	{ "Connected",          "Dapat dihubungi" },
	{ "NotConnected",       "Tidak dapat dihubungi" },
	{ "DatabaseNotFound",   "Pangkalan Data tidak terjumpa" },
	{ "UserPwdError",       "Kata laluan tidak tepat" },
	{ "RoleNotFound",       "Peranan tidak terjumpa/lengkap" },
	{ "Authorized",         "Mendapat Kelulusan" },
	{ "Ok",                 "Ok" },
	{ "Cancel",             "Batal" },
	{ "VersionConflict",    "Versi Bercanggah:" },
	{ "VersionInfo",        "Pelayan <> Pengguna" },
	{ "PleaseUpgrade",      "Sila gunakan program upgrade" }
	};

	/**
	 *  Get Contents
	 *  @return context
	 */
	public Object[][] getContents()
	{
		return contents;
	}   //  getContents
}   //  ALoginRes
