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

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.adempiere.ad.language.ILanguageDAO;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.MLanguage;
import org.compiere.util.DB;
import org.compiere.util.Language;
import org.compiere.util.Msg;
import org.slf4j.Logger;
import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.metas.logging.LogManager;
import de.metas.logging.LogManager;


/**
 *	Translation Table Import + Export
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: Translation.java,v 1.3 2006/07/30 00:51:28 jjanke Exp $
 */
public class Translation
{
	/**
	 * 	Translation
	 *	@param ctx context
	 */
	public Translation (Properties ctx)
	{
		m_ctx = ctx;
	}	//	Translation
	
	/**	DTD						*/
	public static final String DTD = "<!DOCTYPE adempiereTrl PUBLIC \"-//ComPiere, Inc.//DTD Adempiere Translation 1.0//EN\" \"http://www.adempiere.com/dtd/adempiereTrl.dtd\">";
	/**	XML Element Tag			*/
	public static final String	XML_TAG = "adempiereTrl";
	public static final String	XML_TAG2 = "compiereTrl";
	/**	XML Attribute Table			*/
	public static final String	XML_ATTRIBUTE_TABLE = "table";
	/** XML Attribute Language		*/
	public static final String	XML_ATTRIBUTE_LANGUAGE = "language";

	/**	XML Row Tag					*/
	public static final String	XML_ROW_TAG = "row";
	/** XML Row Attribute ID		*/
	public static final String	XML_ROW_ATTRIBUTE_ID = "id";
	/** XML Row Attribute Translated	*/
	public static final String	XML_ROW_ATTRIBUTE_TRANSLATED = "trl";

	/**	XML Value Tag				*/
	public static final String	XML_VALUE_TAG = "value";
	/** XML Value Column			*/
	public static final String	XML_VALUE_ATTRIBUTE_COLUMN = "column";
	/** XML Value Original			*/
	public static final String	XML_VALUE_ATTRIBUTE_ORIGINAL = "original";

	/**	Table is centrally maintained	*/
	private boolean			m_IsCentrallyMaintained = false;
	/**	Logger						*/
	private Logger			log = LogManager.getLogger(getClass());
	/** Properties					*/
	private Properties		m_ctx = null;

	
	/**
	 * 	Import Translation.
	 * 	Uses TranslationHandler to update translation
	 *	@param directory file directory
	 * 	@param AD_Client_ID only certain client if id >= 0
	 * 	@param AD_Language language
	 * 	@param Trl_Table table
	 * 	@return status message
	 */
	public String importTrl (String directory, int AD_Client_ID, String AD_Language, String Trl_Table)
	{
		String fileName = directory + File.separator + Trl_Table + "_" + AD_Language + ".xml";
		log.info(fileName);
		File in = new File (fileName);
		if (!in.exists())
		{
			String msg = "File does not exist: " + fileName;
			log.error(msg);
			return msg;
		}

		try
		{
			TranslationHandler handler = new TranslationHandler(AD_Client_ID);
			SAXParserFactory factory = SAXParserFactory.newInstance();
		//	factory.setValidating(true);
			SAXParser parser = factory.newSAXParser();
			parser.parse(in, handler);
			log.info("Updated=" + handler.getUpdateCount());
			return Msg.getMsg(m_ctx, "Updated") + "=" + handler.getUpdateCount();
		}
		catch (Exception e)
		{
			log.error("importTrl", e);
			return e.toString();
		}
	}	//	importTrl

	
	/**************************************************************************
	 * 	Import Translation
	 *	@param directory file directory
	 * 	@param AD_Client_ID only certain client if id >= 0
	 * 	@param AD_Language language
	 * 	@param Trl_Table translation table _Trl
	 * 	@return status message
	 */
	public String exportTrl (String directory, int AD_Client_ID, String AD_Language, String Trl_Table)
	{
		String fileName = directory + File.separator + Trl_Table + "_" + AD_Language + ".xml";
		log.info(fileName);
		File out = new File(fileName);

		boolean isBaseLanguage = Language.isBaseLanguage(AD_Language);
		String tableName = Trl_Table;
		int pos = tableName.indexOf("_Trl");
		String Base_Table = Trl_Table.substring(0, pos);
		if (isBaseLanguage)
			tableName =  Base_Table;
		String keyColumn = Base_Table + "_ID";
		String[] trlColumns = getTrlColumns (Base_Table);
		//
		StringBuffer sql = null;
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			//	System.out.println(factory.getClass().getName());
			DocumentBuilder builder = factory.newDocumentBuilder();
			//	<!DOCTYPE adempiereTrl SYSTEM "http://www.adempiere.org/dtd/adempiereTrl.dtd">
			//	<!DOCTYPE adempiereTrl PUBLIC "-//ComPiere, Inc.//DTD Adempiere Translation 1.0//EN" "http://www.adempiere.org/dtd/adempiereTrl.dtd">
			Document document = builder.newDocument();
			document.appendChild(document.createComment(Adempiere.getSummaryAscii()));
			document.appendChild(document.createComment(DTD));

			//	Root
			Element root = document.createElement(XML_TAG);
			root.setAttribute(XML_ATTRIBUTE_LANGUAGE, AD_Language);
			root.setAttribute(XML_ATTRIBUTE_TABLE, Base_Table);
			document.appendChild(root);
			//
			sql = new StringBuffer ("SELECT ");
			if (isBaseLanguage)
				sql.append("'Y',");							//	1
			else
				sql.append("t.IsTranslated,");
			sql.append("t.").append(keyColumn);				//	2
			//
			for (int i = 0; i < trlColumns.length; i++)
				sql.append(", t.").append(trlColumns[i])
					.append(",o.").append(trlColumns[i]).append(" AS ").append(trlColumns[i]).append("O");
			//
			sql.append(" FROM ").append(tableName).append(" t")
				.append(" INNER JOIN ").append(Base_Table)
				.append(" o ON (t.").append(keyColumn).append("=o.").append(keyColumn).append(")");
			boolean haveWhere = false;
			if (!isBaseLanguage)
			{
				sql.append(" WHERE t.AD_Language=?");
				haveWhere = true;
			}
			if (m_IsCentrallyMaintained)
			{
				sql.append (haveWhere ? " AND " : " WHERE ").append ("(o.IsCentrallyMaintained='N' or o.IsCentrallyMaintained='Y')"); // metas: added "or o.IsCentrallyMaintained='Y'"; tsa: WTF is this code?
				haveWhere = true;
			}
			if (AD_Client_ID >= 0)
				sql.append(haveWhere ? " AND " : " WHERE ").append("o.AD_Client_ID=").append(AD_Client_ID);
			sql.append(" ORDER BY t.").append(keyColumn);
			//
			PreparedStatement pstmt = DB.prepareStatement(sql.toString(), null);
			if (!isBaseLanguage)
				pstmt.setString(1, AD_Language);
			ResultSet rs = pstmt.executeQuery();
			int rows = 0;
			while (rs.next())
			{
				Element row = document.createElement (XML_ROW_TAG);
				row.setAttribute(XML_ROW_ATTRIBUTE_ID, String.valueOf(rs.getInt(2)));	//	KeyColumn
				row.setAttribute(XML_ROW_ATTRIBUTE_TRANSLATED, rs.getString(1));		//	IsTranslated
				for (int i = 0; i < trlColumns.length; i++)
				{
					Element value = document.createElement (XML_VALUE_TAG);
					value.setAttribute(XML_VALUE_ATTRIBUTE_COLUMN, trlColumns[i]);
					String origString = rs.getString(trlColumns[i] + "O");			//	Original Value
					if (origString == null)
						origString = "";
					String valueString = rs.getString(trlColumns[i]);				//	Value
					if (valueString == null)
						valueString = "";
					value.setAttribute(XML_VALUE_ATTRIBUTE_ORIGINAL, origString);
					if (valueString.indexOf("<") != -1 || valueString.indexOf(">") != -1 || valueString.indexOf("&") != -1) {
						value.appendChild(document.createCDATASection(valueString));
					} else {
						value.appendChild(document.createTextNode(valueString));
					}
					row.appendChild(value);
				}
				root.appendChild(row);
				rows++;
			}
			rs.close();
			pstmt.close();
			log.info("Records=" + rows 
				+ ", DTD=" + document.getDoctype()
				+ " - " + Trl_Table);
			//
			DOMSource source = new DOMSource(document);
			TransformerFactory tFactory = TransformerFactory.newInstance();
			tFactory.setAttribute("indent-number", Integer.valueOf(1)); // teo_sarca [ 1705883 ]
			Transformer transformer = tFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // teo_sarca [ 1705883 ]
			//	Output, wrapped with a writer - teo_sarca [ 1705883 ]
			out.createNewFile();
			Writer writer = new OutputStreamWriter(new FileOutputStream(out), "utf-8"); 
			StreamResult result = new StreamResult(writer);
			//	Transform
			transformer.transform (source, result);
			// Close writer - teo_sarca [ 1705883 ] 
			writer.close();
		}
		catch (SQLException e)
		{
			log.error(sql.toString(), e);
			return e.toString();
		}
		catch (Exception e)
		{
			log.error("", e);
			return e.toString();
		}

		return "";
	}	//	exportTrl

	
	/**
	 * 	Get Columns for Table
	 * 	@param Base_Table table
	 * 	@return array of translated columns
	 */
	private String[] getTrlColumns (String Base_Table)
	{
		m_IsCentrallyMaintained = false;
		String sql = "SELECT TableName FROM AD_Table t"
			+ " INNER JOIN AD_Column c ON (c.AD_Table_ID=t.AD_Table_ID AND c.ColumnName='IsCentrallyMaintained') "
			+ "WHERE t.TableName=? AND c.IsActive='Y'";
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setString(1, Base_Table);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
				m_IsCentrallyMaintained = true;
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.error(sql, e);
		}

		sql = "SELECT ColumnName "
			+ "FROM AD_Column c"
			+ " INNER JOIN AD_Table t ON (c.AD_Table_ID=t.AD_Table_ID) "
			+ "WHERE t.TableName=?"
			+ " AND c.AD_Reference_ID IN (10,14) "
			+ "ORDER BY IsMandatory DESC, ColumnName";
		ArrayList<String> list = new ArrayList<String>();
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setString(1, Base_Table + "_Trl");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				String s = rs.getString(1);
			//	System.out.println(s); 
				list.add(s);
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.error(sql, e);
		}

		//	Convert to Array
		String[] retValue = new String[list.size()];
		list.toArray(retValue);
		return retValue;
	}	//	getTrlColumns

	
	/**************************************************************************
	 * 	Validate Language.
	 *  - Check if AD_Language record exists
	 *  - Check Trl table records
	 * 	@param AD_Language language
	 * 	@return "" if validated - or error message
	 */
	public String validateLanguage (String AD_Language)
	{
		String sql = "SELECT * "
			+ "FROM AD_Language "
			+ "WHERE AD_Language=?";
		MLanguage language = null;
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setString(1, AD_Language);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
				language = new MLanguage (m_ctx, rs, null);
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.error(sql, e);
			return e.toString();
		}

		//	No AD_Language Record
		if (language == null)
		{
			log.error("Language does not exist: " + AD_Language);
			return "Language does not exist: " + AD_Language;
		}
		//	Language exists
		if (language.isActive())
		{
			if (language.isBaseLanguage())
				return "";
		}
		else
		{
			log.error("Language not active or not system language: " + AD_Language);
			return "Language not active or not system language: " + AD_Language;
		}
		
		//	Validate Translation
		log.info("Start Validating ... {}", language);
		Services.get(ILanguageDAO.class).addMissingTranslations(language);
		return "";
	}	//	validateLanguage

	
	/**
	 * 	Process
	 * 	@param directory directory
	 * 	@param AD_Language language
	 * 	@param mode mode
	 */
	private void process (String directory, String AD_Language, String mode)
	{
		File dir = new File(directory);
		if (!dir.exists())
			dir.mkdir();
		dir = new File(directory);
		if (!dir.exists())
		{
			System.out.println("Cannot create directory " + directory);
			System.exit(1);
		}

		String 	sql = "SELECT Name, TableName "
			+ "FROM AD_Table "
			+ "WHERE TableName LIKE '%_Trl' "
			+ "ORDER BY Name";
		ArrayList<String> trlTables = new ArrayList<String>();
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
				trlTables.add(rs.getString(2));
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.error(sql, e);
		}

		for (int i = 0; i < trlTables.size(); i++)
		{
			String table = trlTables.get(i);
			if (mode.startsWith("i"))
				importTrl(directory, -1, AD_Language, table);
			else
				exportTrl(directory, -1, AD_Language, table);
		}
	}	//	process
}	//	Translation
