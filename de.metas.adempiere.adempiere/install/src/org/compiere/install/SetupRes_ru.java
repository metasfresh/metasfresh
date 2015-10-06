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
 * Russian Setup Resource Translation
 * 
 * @author Vyacheslav Pedak
 * @version $Id: SetupRes_ru.java,v 1.3 2006/07/30 00:57:42 jjanke Exp $
 */
public class SetupRes_ru extends ListResourceBundle
{
	/**
	 * 	Get Content Array
	 *	@return content
	 */
	public Object[][] getContents ()
	{
		return contents;
	}

	static final Object	contents[][]	= {
		{ "AdempiereServerSetup", "\u0423\u0441\u0442\u0430\u043d\u043e\u0432\u043a\u0430 \u0441\u0435\u0440\u0432\u0435\u0440\u0430 Adempiere"},
		{ "Ok", "Ok"},
		{ "File", "\u0424\u0430\u0439\u043b"},
		{ "Exit", "\u0412\u044b\u0445\u043e\u0434"},
		{ "Help", "\u041f\u043e\u043c\u043e\u0449\u044c"},
		{ "PleaseCheck", "\u041f\u043e\u0436\u0430\u043b\u0443\u0439\u0441\u0442\u0430 \u043f\u0440\u043e\u0432\u0435\u0440\u044c\u0442\u0435"},
		{ "UnableToConnect", "\u041d\u0435\u0432\u043e\u0437\u043c\u043e\u0436\u043d\u043e \u043f\u043e\u043b\u0443\u0447\u0438\u0442\u044c \u0438\u043d\u0444\u043e\u0440\u043c\u0430\u0446\u0438\u044e c \u0441\u0430\u0439\u0442\u0430 Adempiere"},
		{ "AdempiereHomeInfo", "Adempiere Home - \u044d\u0442\u043e \u0433\u043b\u0430\u0432\u043d\u044b\u0439 \u043a\u0430\u0442\u0430\u043b\u043e\u0433"},
		{ "AdempiereHome", "Adempiere Home"},
		{ "WebPortInfo", "\u0412\u0435\u0431 (HTML) \u043f\u043e\u0440\u0442"},
		{ "WebPort", "\u0412\u0435\u0431 \u043f\u043e\u0440\u0442"},
		{ "AppsServerInfo", "\u0418\u043c\u044f \u0441\u0435\u0440\u0432\u0435\u0440\u0430 \u043f\u0440\u0438\u043b\u043e\u0436\u0435\u043d\u0438\u0439"},
		{ "AppsServer", "\u0421\u0435\u0440\u0432\u0435\u0440 \u043f\u0440\u0438\u043b\u043e\u0436\u0435\u043d\u0438\u0439"},
		{ "DatabaseTypeInfo", "\u0422\u0438\u043f \u0431\u0430\u0437\u044b \u0434\u0430\u043d\u043d\u044b\u0445"},
		{ "DatabaseType", "\u0422\u0438\u043f \u0431\u0430\u0437\u044b \u0434\u0430\u043d\u043d\u044b\u0445"},
		{ "DatabaseNameInfo", "\u0418\u043c\u044f \u0431\u0430\u0437\u044b \u0434\u0430\u043d\u043d\u044b\u0445"},
		{ "DatabaseName", "\u0418\u043c\u044f \u0431\u0430\u0437\u044b \u0434\u0430\u043d\u043d\u044b\u0445 (SID)"},
		{ "DatabasePortInfo", "\u041f\u043e\u0440\u0442 \u0411\u0414 Listener"},
		{ "DatabasePort", "\u041f\u043e\u0440\u0442 \u0411\u0414"},
		{ "DatabaseUserInfo", "ID \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044f \u0411\u0414"},
		{ "DatabaseUser", "\u041f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044c \u0411\u0414"},
		{ "DatabasePasswordInfo", "\u041f\u0430\u0440\u043e\u043b\u044c \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044f \u0411\u0414"},
		{ "DatabasePassword", "\u041f\u0430\u0440\u043e\u043b\u044c \u0411\u0414"},
		{ "TNSNameInfo", "TNS \u0438\u043b\u0438 Global Database Name"},
		{ "TNSName", "\u0418\u043c\u044f TNS"},
		{ "SystemPasswordInfo", "\u041f\u0430\u0440\u043e\u043b\u044c \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044f System"},
		{ "SystemPassword", "\u041f\u0430\u0440\u043e\u043b\u044c System"},
		{ "MailServerInfo", "\u0421\u0435\u0440\u0432\u0435\u0440 \u044d\u043b\u0435\u043a\u0442\u0440\u043e\u043d\u043d\u043e\u0439 \u043f\u043e\u0447\u0442\u044b"},
		{ "MailServer", "\u0421\u0435\u0440\u0432\u0435\u0440 \u044d\u043b. \u043f\u043e\u0447\u0442\u044b"},
		{ "AdminEMailInfo", "\u0415\u043c\u0430\u0439\u043b \u0430\u0434\u043c\u0438\u043d\u0438\u0441\u0442\u0440\u0430\u0442\u043e\u0440\u0430"},
		{ "AdminEMail", "\u0415\u043c\u0430\u0439\u043b \u0430\u0434\u043c\u0438\u043d\u0438\u0441\u0442\u0440\u0430\u0442\u043e\u0440\u0430"},
		{ "DatabaseServerInfo", "\u0418\u043c\u044f \u0441\u0435\u0440\u0432\u0435\u0440\u0430 \u0411\u0414"},
		{ "DatabaseServer", "\u0421\u0435\u0440\u0432\u0435\u0440 \u0411\u0414"},
		{ "JavaHomeInfo", "\u041a\u0430\u0442\u0430\u043b\u043e\u0433 Java Home"},
		{ "JavaHome", "Java Home"},
		{ "JNPPortInfo", "\u041f\u043e\u0440\u0442 JNP \u0441\u0435\u0440\u0432\u0435\u0440\u0430 \u043f\u0440\u0438\u043b\u043e\u0436\u0435\u043d\u0438\u0439"},
		{ "JNPPort", "\u041f\u043e\u0440\u0442 JNP"},
		{ "MailUserInfo", "\u041f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044c \u044d\u043b\u0435\u043a\u0442\u0440\u043e\u043d\u043d\u043e\u0439 \u043f\u043e\u0447\u0442\u044b"},
		{ "MailUser", "\u041f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044c \u044d\u043b. \u043f\u043e\u0447\u0442\u044b"},
		{ "MailPasswordInfo", "\u041f\u0430\u0440\u043e\u043b\u044c \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044f \u044d\u043b\u0435\u043a\u0442\u0440\u043e\u043d\u043d\u043e\u0439 \u043f\u043e\u0447\u0442\u044b"},
		{ "MailPassword", "\u041f\u0430\u0440\u043e\u043b\u044c \u0434\u043b\u044f \u044d\u043b. \u043f\u043e\u0447\u0442\u044b"},
		{ "KeyStorePassword", "Key Store Password"},
		{ "KeyStorePasswordInfo", "Password for SSL Key Store"},
		//
		{ "JavaType", "Java VM"},
		{ "JavaTypeInfo", "Java VM Vendor"},
		{ "AppsType", "Server Type"},
		{ "AppsTypeInfo", "J2EE Application Server Type"},
		{ "DeployDir", "Deployment"},
		{ "DeployDirInfo", "J2EE Deployment Directory"},
		{ "ErrorDeployDir", "Error Deployment Directory"},
		//
		{ "TestInfo", "\u041f\u0440\u043e\u0432\u0435\u0440\u043a\u0430 \u043d\u0430\u0441\u0442\u0440\u043e\u0435\u043a"},
		{ "Test", "\u041f\u0440\u043e\u0432\u0435\u0440\u043a\u0430"},
		{ "SaveInfo", "\u0421\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u044c \u043d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438"},
		{ "Save", "\u0421\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u044c"},
		{ "HelpInfo", "\u041f\u043e\u043c\u043e\u0449\u044c"},
		{ "ServerError", "\u041e\u0448\u0438\u0431\u043a\u0430 \u0443\u0441\u0442\u0430\u043d\u043e\u0432\u043a\u0438 \u0441\u0435\u0440\u0432\u0435\u0440\u0430"},
		{ "ErrorJavaHome", "\u041e\u0448\u0438\u0431\u043a\u0430 Java Home"},
		{ "ErrorAdempiereHome", "\u041e\u0448\u0438\u0431\u043a\u0430 Adempiere Home"},
		{ "ErrorAppsServer", "\u041e\u0448\u0438\u0431\u043a\u0430 \u0441\u0435\u0440\u0432\u0435\u0440\u0430 \u043f\u0440\u0438\u043b\u043e\u0436\u0435\u043d\u0438\u0439 (\u043d\u0435 \u0438\u0441\u043f\u043e\u043b\u044c\u0437\u0443\u0439\u0442\u0435 localhost)"},
		{ "ErrorWebPort", "\u041e\u0448\u0438\u0431\u043a\u0430 \u0432\u0435\u0431 \u043f\u043e\u0440\u0442\u0430"},
		{ "ErrorJNPPort", "\u041e\u0448\u0438\u0431\u043a\u0430 \u043f\u043e\u0440\u0442\u0430 JNP"},
		{ "ErrorDatabaseServer", "\u041e\u0448\u0438\u0431\u043a\u0430 \u0441\u0435\u0440\u0432\u0435\u0440\u0430 \u0411\u0414 (\u043d\u0435 \u0438\u0441\u043f\u043e\u043b\u044c\u0437\u0443\u0439\u0442\u0435 localhost)"},
		{ "ErrorDatabasePort", "\u041e\u0448\u0438\u0431\u043a\u0430 \u043f\u043e\u0440\u0442\u0430 \u0411\u0414"},
		{ "ErrorJDBC", "\u041e\u0448\u0438\u0431\u043a\u0430 \u0441\u043e\u0435\u0434\u0438\u043d\u0435\u043d\u0438\u044f \u043f\u043e JDBC"},
		{ "ErrorTNS", "\u041e\u0448\u0438\u0431\u043a\u0430 \u0441\u043e\u0435\u0434\u0438\u043d\u0435\u043d\u0438\u044f \u0447\u0435\u0440\u0435\u0437 TNS"},
		{ "ErrorMailServer", "\u041e\u0448\u0438\u0431\u043a\u0430 \u0441\u0435\u0440\u0432\u0435\u0440\u0430 \u044d\u043b\u0435\u043a\u0442\u0440\u043e\u043d\u043d\u043e\u0439 \u043f\u043e\u0447\u0442\u044b (\u043d\u0435 \u0438\u0441\u043f\u043e\u043b\u044c\u0437\u0443\u0439\u0442\u0435 localhost)"},
		{ "ErrorMail", "\u041e\u0448\u0438\u0431\u043a\u0430 \u043f\u043e\u0447\u0442\u044b"},
		{ "ErrorSave", "\u041e\u0448\u0438\u0431\u043a\u0430 \u0441\u043e\u0445\u0440\u0430\u043d\u0435\u043d\u0438\u044f \u0444\u0430\u0439\u043b\u0430"},
		{ "EnvironmentSaved", "\u041f\u0430\u0440\u0430\u043c\u0435\u0442\u0440\u044b \u0441\u043e\u0445\u0440\u0430\u043d\u0435\u043d\u044b\n\u0412\u0430\u043c \u043d\u0435\u043e\u0431\u0445\u043e\u0434\u0438\u043c\u043e \u043f\u0435\u0440\u0435\u0437\u0430\u043f\u0443\u0441\u0442\u0438\u0442\u044c \u0441\u0435\u0440\u0432\u0435\u0440."},
		{ "RMIoverHTTP", "Tunnel Objects via HTTP"},
		{ "RMIoverHTTPInfo", "RMI over HTTP allows to go through firewalls"}};
}
