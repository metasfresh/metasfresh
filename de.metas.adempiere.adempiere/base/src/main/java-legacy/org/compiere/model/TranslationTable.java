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
package org.compiere.model;

import java.util.ArrayList;

import org.compiere.util.CCache;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *	Translation Table Management
 *	
 *  @author Jorg Janke
 *  @version $Id: TranslationTable.java,v 1.2 2006/07/30 00:54:54 jjanke Exp $
 */
public class TranslationTable
{
	/**
	 * 	Save translation for po
	 *	@param po persistent object
	 *	@param newRecord new
	 *	@return true if no active language or translation saved/reset
	 */
	public static boolean save (PO po, boolean newRecord)
	{
		if (!TranslationTable.isActiveLanguages(false))
			return true;
		TranslationTable table = TranslationTable.get(po.get_TableName());
		if (newRecord)
			return table.createTranslation(po);
		return table.resetTranslationFlag(po);
	}	//	save
	
	/**
	 * 	Delete translation for po
	 *	@param po persistent object
	 *	@return true if no active language or translation deleted
	 */
	public static boolean delete (PO po)
	{
		if (!TranslationTable.isActiveLanguages(false))
			return true;
		TranslationTable table = TranslationTable.get(po.get_TableName());
		return table.deleteTranslation(po);
	}	//	delete
	
	/**
	 * 	Get Number of active Translation Languages
	 *	@param requery requery
	 *	@return number of active Translations
	 */
	public static int getActiveLanguages (boolean requery)
	{
		if (s_activeLanguages != null && !requery)
			return s_activeLanguages.intValue();
		int no = DB.getSQLValue(null, 
			"SELECT COUNT(*) FROM AD_Language WHERE IsActive='Y' AND IsSystemLanguage='Y'");
		s_activeLanguages = new Integer(no);
		return s_activeLanguages.intValue();
	}	//	getActiveLanguages
	
	/**
	 * 	Are there active Translation Languages
	 *	@param requery requery
	 *	@return true active Translations
	 */
	public static boolean isActiveLanguages (boolean requery)
	{
		int no = getActiveLanguages(requery);
		return no > 0;
	}	//	isActiveLanguages
	
	/**
	 * 	Get TranslationTable from Cache
	 *	@param baseTableName base table name
	 *	@return TranslationTable
	 */
	public static TranslationTable get (String baseTableName)
	{
		TranslationTable retValue = (TranslationTable)s_cache.get (baseTableName);
		if (retValue != null)
			return retValue;
		retValue = new TranslationTable (baseTableName);
		s_cache.put (baseTableName, retValue);
		return retValue;
	}	//	get

	/**	Active Translations			*/
	private static Integer	s_activeLanguages = null;
	
	/**	Cache						*/
	private static CCache<String,TranslationTable> s_cache = new CCache<String,TranslationTable>("TranslationTable", 20);

	
	/**
	 * 	Translation Table
	 * 	@param baseTableName base table name
	 */
	protected TranslationTable (String baseTableName)
	{
		if (baseTableName == null)
			throw new IllegalArgumentException("Base Table Name is null");
		m_baseTableName = baseTableName;
		m_trlTableName = baseTableName + "_Trl";
		initColumns();
		log.fine(toString());
	}	//	TranslationTable

	/**	Static Logger	*/
	private static CLogger	log	= CLogger.getCLogger (TranslationTable.class);

	/**	Translation Table Name		*/
	private String				m_trlTableName = null;
	/** Base Table Name				*/
	private String				m_baseTableName = null;
	/** Column Names 				*/
	private ArrayList<String>	m_columns = new ArrayList<String>();
	
	/**
	 * 	Add Translation Columns
	 */
	private void initColumns()
	{
		MTable table = MTable.get(Env.getCtx(), m_trlTableName);
		if (table == null)
			throw new IllegalArgumentException("Table Not found=" + m_trlTableName);
		MColumn[] columns = table.getColumns(false);
		for (int i = 0; i < columns.length; i++)
		{
			MColumn column = columns[i];
			if (column.isStandardColumn())
				continue;
			String columnName = column.getColumnName();
			if (columnName.endsWith("_ID")
				|| columnName.startsWith("AD_Language")
				|| columnName.equals("IsTranslated"))
				continue;
			//
			m_columns.add(columnName);
		}
		if (m_columns.size() == 0)
			throw new IllegalArgumentException("No Columns found=" + m_trlTableName);
	}	//	initColumns
	
	/**
	 * 	Create Translation record from PO
	 *	@param po base table record
	 *	@return true if inserted or no translation
	 */
	public boolean createTranslation (PO po)
	{
		if (!isActiveLanguages(false))
			return true;
		if (po.get_ID() == 0)
			throw new IllegalArgumentException("PO ID is 0");
		//
		StringBuffer sql1 = new StringBuffer();
		sql1.append("INSERT INTO ").append(m_trlTableName).append(" (");
		StringBuffer sql2 = new StringBuffer();
		sql2.append(") SELECT ");
		
		//	Key Columns
		sql1.append(m_baseTableName).append("_ID,AD_Language");
		sql2.append("b.").append(m_baseTableName).append("_ID,l.AD_Language");

		//	Base Columns
		sql1.append(", AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy, IsTranslated");
		sql2.append(", b.AD_Client_ID,b.AD_Org_ID,b.IsActive, b.Created,b.CreatedBy,b.Updated,b.UpdatedBy, 'N'");

		for (int i = 0; i < m_columns.size(); i++)
		{
			String columnName = (String)m_columns.get(i);
			Object value = po.get_Value(columnName);
			//
			if (value == null)
				continue;
			sql1.append(",").append(columnName);
			sql2.append(",b.").append(columnName);
		}
		//
		StringBuffer sql = new StringBuffer();
		sql.append(sql1).append(sql2)
			.append(" FROM AD_Language l, " + m_baseTableName
				+ " b WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND b."
				+ m_baseTableName + "_ID=").append(po.get_ID());
		int no = DB.executeUpdate(sql.toString(), po.get_TrxName());
		log.fine(m_trlTableName + ": ID=" + po.get_ID() + " #" + no);
		return no != 0;
	}	//	createTranslation
	
	/**
	 * 	Reset Translation Flag
	 *	@param po po
	 *	@return true if updated or no translations
	 */
	public boolean resetTranslationFlag (PO po)
	{
		if (!isActiveLanguages(false))
			return true;
		if (po.get_ID() == 0)
			throw new IllegalArgumentException("PO ID is 0");
		//
		StringBuffer sb = new StringBuffer("UPDATE ");
		sb.append(m_trlTableName)
			.append(" SET IsTranslated='N',Updated=now() WHERE ")
			.append(m_baseTableName).append("_ID=").append(po.get_ID());
		int no = DB.executeUpdate(sb.toString(), po.get_TrxName());
		log.fine(m_trlTableName + ": ID=" + po.get_ID() + " #" + no);
		return no != 0;
	}	//	resetTranslationFlag

	/**
	 * 	Delete Translation
	 *	@param po po
	 *	@return true if udeleted or no translations
	 */
	public boolean deleteTranslation (PO po)
	{
		if (!isActiveLanguages(false))
			return true;
		if (po.get_IDOld() == 0)
			throw new IllegalArgumentException("PO Old ID is 0");
		//
		StringBuffer sb = new StringBuffer("DELETE FROM ");
		sb.append(m_trlTableName)
			.append(" WHERE ")
			.append(m_baseTableName).append("_ID=").append(po.get_IDOld());
		int no = DB.executeUpdate(sb.toString(), po.get_TrxName());
		log.fine(m_trlTableName + ": ID=" + po.get_IDOld() + " #" + no);
		return no != 0;
	}	//	resetTranslationFlag

	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("TranslationTable[");
		sb.append(m_trlTableName)
			.append("(").append(m_baseTableName).append(")");
		for (int i = 0; i < m_columns.size(); i++)
			sb.append("-").append(m_columns.get(i));
		sb.append ("]");
		return sb.toString ();
	}	//	toString
	
}	//	TranslationTable
