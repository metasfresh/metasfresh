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
package org.compiere.install;

import java.util.ListResourceBundle;

/**
 *	Setup Resources
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: SetupRes_ml.java,v 1.2 2006/07/30 00:57:42 jjanke Exp $
 */
public class SetupRes_my extends ListResourceBundle
{
	/**	Translation Info	*/
	static final Object[][] contents = new String[][]{
	{ "AdempiereServerSetup", "Pemasangan Pelayan" },
	{ "Ok", 				"Ok" },
	{ "File", 				"Fail" },
	{ "Exit", 				"Keluar" },
	{ "Help", 				"Bantuan" },
	{ "PleaseCheck", 		"Sila Periksa" },
	{ "UnableToConnect",	"Tidak mendapat bantuan dari laman projek ADempiere" },

	{ "AdempiereHomeInfo", 	"Adempiere Home adalah folder utama" },
	{ "AdempiereHome", 		"Adempiere Home" },
	{ "WebPortInfo", 		"Web (HTML) Port" },
	{ "WebPort", 			"Web Port" },
	{ "AppsServerInfo", 	"Nama Pelayan ADempiere" },
	{ "AppsServer", 		"Pelayan Aplikasi" },
	{ "DatabaseTypeInfo", 	"Jenis Database" },
	{ "DatabaseType", 		"Jenis Database" },
	{ "DatabaseNameInfo", 	"Nama Database " },
	{ "DatabaseName", 		"Nama Database (SID)" },
	{ "DatabasePortInfo", 	"Database Listener Port" },
	{ "DatabasePort", 		"Database Port" },
	{ "DatabaseUserInfo", 	"Database Adempiere ID Pengguna Database Adempiere" },
	{ "DatabaseUser", 		"Pengguna Database" },
	{ "DatabasePasswordInfo", "Kata-Laluan Pengguna" },
	{ "DatabasePassword", 	"Kata-Laluan Jenis Database" },
	{ "TNSNameInfo", 		"TNS atau Nama Database Global" },
	{ "TNSName", 			"Nama TNS" },
	{ "SystemPasswordInfo", "Kata-Laluan Pengguna Sistem" },
	{ "SystemPassword", 	"Kata-Laluan Pengguna Sistem" },
	{ "MailServerInfo", 	"Pelayan Mel" },
	{ "MailServer", 		"Pelayan Mel" },
	{ "AdminEMailInfo", 	"Emel Pentadbiran ADempiere" },
	{ "AdminEMail", 		"Emel Pentadbiran" },
	{ "DatabaseServerInfo", "Nama Pelayan Database" },
	{ "DatabaseServer", 	"Pelayan Database" },
	{ "JavaHomeInfo", 		"Java Home Folder" },
	{ "JavaHome", 			"Java Home" },
	{ "JNPPortInfo", 		"Application Server JNP Port" },
	{ "JNPPort", 			"JNP Port" },
	{ "MailUserInfo", 		"Pengguna Emel Adempiere" },
	{ "MailUser", 			"Pengguna Emel" },
	{ "MailPasswordInfo", 	"Kata-Laluan Pengguna Emel ADempiere" },
	{ "MailPassword", 		"Kata-Laluan Emel " },
	{ "KeyStorePassword",		"Kata-laluan Stor Kunci" },
	{ "KeyStorePasswordInfo",	"Kata-laluan Stor Kunci SSL" },
	//
	{ "JavaType",				"Java VM"},
	{ "JavaTypeInfo",			"Pembekal Java VM"},
	{ "AppsType",				"Jenis Pelayan"},
	{ "AppsTypeInfo",			"Jenis Pelayan Aplikasi J2EE"},
	{ "DeployDir",				"Pelaksanaan"},
	{ "DeployDirInfo",			"Direktori Pelaksanaan J2EE"},
	{ "ErrorDeployDir",			"Error Deployment Directory"},
	//
	{ "TestInfo", 			"Uji Setup" },
	{ "Test", 				"Uji" },
	{ "SaveInfo", 			"Simpan Info Setup" },
	{ "Save", 				"Simpan" },
	{ "HelpInfo", 			"Dapatkan Bantuan" },

	{ "ServerError", 		"Server Setup Error" },
	{ "ErrorJavaHome", 		"Error Java Home" },
	{ "ErrorAdempiereHome", 	"Error Adempiere Home" },
	{ "ErrorAppsServer", 	"Error Apps Server (jangan guna localhost)" },
	{ "ErrorWebPort", 		"Error Web Port" },
	{ "ErrorJNPPort", 		"Error JNP Port" },
	{ "ErrorDatabaseServer", "Error Database Server (jangan guna localhost)" },
	{ "ErrorDatabasePort", 	"Error Database Port" },
	{ "ErrorJDBC", 			"Error sambungan JDBC" },
	{ "ErrorTNS", 			"Error sambungan TNS" },
	{ "ErrorMailServer", 	"Error Mail Server (jangan guna localhost)" },
	{ "ErrorMail", 			"Error Mail" },
	{ "ErrorSave", 			"Error Simpan Fail" },

	{ "EnvironmentSaved",	"Environment saved\nAnda perlu ON semula pelayan." }
	};

	/**
	 * 	Get Contents
	 * 	@return contents
	 */
	public Object[][] getContents()
	{
		return contents;
	}	//	getContents

}	//	SerupRes
