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

import org.adempiere.ad.language.ILanguageDAO;
import org.adempiere.util.Services;
import org.compiere.model.MLanguage;

import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;


/**
 *	Language Translation Maintenance Process
 *	
 *  @author Jorg Janke
 *  @version $Id: LanguageMaintenance.java,v 1.3 2006/07/30 00:51:28 jjanke Exp $
 */
public class LanguageMaintenance extends SvrProcess
{
	// services
	private final ILanguageDAO languageDAO = Services.get(ILanguageDAO.class);
	
	/**	The Language ID			*/
	private int		p_AD_Language_ID = 0;
	/** Maintenance Mode		*/
	private String	p_MaintenanceMode = null;
	
	/**	Add						*/
	public static String	MAINTENANCEMODE_Add = "A";
	/** Delete					*/
	public static String	MAINTENANCEMODE_Delete = "D";
	/** Re-Create				*/
	public static String	MAINTENANCEMODE_ReCreate = "R";
	
	/**	The Language			*/
	private MLanguage 	m_language = null;
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("MaintenanceMode"))
				p_MaintenanceMode = (String)para[i].getParameter();
			else
				log.error("Unknown Parameter: " + name);
		}
		p_AD_Language_ID = getRecord_ID();
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	@Override
	protected String doIt() throws Exception
	{
		m_language = new MLanguage (getCtx(), p_AD_Language_ID, get_TrxName());
		log.info("Mode=" + p_MaintenanceMode + ", ID=" + p_AD_Language_ID
			+ " - " + m_language);
		
		if (m_language.isBaseLanguage())
			throw new Exception ("Base Language has no Translations");
		
		int deleteNo = 0;
		int insertNo = 0;
		
		//	Delete
		if (MAINTENANCEMODE_Delete.equals(p_MaintenanceMode)
			|| MAINTENANCEMODE_ReCreate.equals(p_MaintenanceMode))
		{
			deleteNo = languageDAO.removeTranslations(m_language);
		}
		//	Add
		if (MAINTENANCEMODE_Add.equals(p_MaintenanceMode)
			|| MAINTENANCEMODE_ReCreate.equals(p_MaintenanceMode))
		{
			if (m_language.isActive() && m_language.isSystemLanguage())
			{
				insertNo = languageDAO.addMissingTranslations(m_language);
			}
			else
				throw new Exception ("Language not active System Language");
		}
		//	Delete
		if (MAINTENANCEMODE_Delete.equals(p_MaintenanceMode))
		{
			if (m_language.isSystemLanguage())
			{
				m_language.setIsSystemLanguage(false);
				m_language.save();
			}
		}
		
		return "@Deleted@=" + deleteNo + " - @Inserted@=" + insertNo;
	}	//	doIt

	
}	//	LanguageMaintenance
