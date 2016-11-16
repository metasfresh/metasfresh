/**********************************************************************
 * This file is part of Adempiere ERP Bazaar                          * 
 * http://www.adempiere.org                                           * 
 *                                                                    * 
 * Copyright (C) Trifon Trifonov.                                     * 
 * Copyright (C) Contributors                                         * 
 *                                                                    * 
 * This program is free software; you can redistribute it and/or      * 
 * modify it under the terms of the GNU General Public License        * 
 * as published by the Free Software Foundation; either version 2     * 
 * of the License, or (at your option) any later version.             * 
 *                                                                    * 
 * This program is distributed in the hope that it will be useful,    * 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of     * 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the       * 
 * GNU General Public License for more details.                       * 
 *                                                                    * 
 * You should have received a copy of the GNU General Public License  * 
 * along with this program; if not, write to the Free Software        * 
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,         * 
 * MA 02110-1301, USA.                                                * 
 *                                                                    * 
 * Contributors:                                                      * 
 *  - Trifon Trifonov (trifonnt@users.sourceforge.net)                *
 *                                                                    *
 * Sponsors:                                                          *
 *  - e-Evolution (http://www.e-evolution.com/)                       *
 **********************************************************************/

package org.adempiere.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.compiere.model.I_EXP_FormatLine;
import org.compiere.model.MClient;
import org.compiere.model.MColumn;
import org.compiere.model.MEXPFormat;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.X_EXP_FormatLine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;


/**
 *	
 *  @author Trifon Trifonov
 *  @version $Id: $
 */
public class Export extends SvrProcess
{
	private static final String TOTAL_SEGMENTS = "${totalSegments}";

	/** Client Parameter			*/
	protected int	p_AD_Client_ID = 0;
	
	/** Table Parameter */
	protected int p_AD_Table_ID = 0;
	
	/** Record ID */
	protected int p_Record_ID = 0;
	
	/** XML Document */
	private Document outDocument = null; 
	
	/** Date Time Format		*/
//	private SimpleDateFormat	m_dateTimeFormat = null;
	
	/** Date Format			*/
//	private SimpleDateFormat	m_dateFormat = null;
	
	/** Custom Date Format			*/
//	private SimpleDateFormat	m_customDateFormat = null;
	
	/** Table ID */
	int AD_Table_ID = 0;
	
	/**
	 * 	Get Parameters
	 */
	protected void prepare ()
	{
		
		p_Record_ID = getRecord_ID();
		if (p_AD_Client_ID == 0)
			p_AD_Client_ID = Env.getAD_Client_ID(getCtx());
		AD_Table_ID = getTable_ID();
		
		// C_Invoice; AD_Table_ID = 318
		StringBuffer sb = new StringBuffer ("AD_Table_ID=").append(AD_Table_ID);
		sb.append("; Record_ID=").append(getRecord_ID());
		//	Parameter
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("AD_Table_ID"))
				p_AD_Table_ID = para[i].getParameterAsInt();
			else
				log.error("Unknown Parameter: " + name);
		}
		
		// TODO - we can get Language from Business Partner
//		m_dateTimeFormat = DisplayType.getDateFormat(DisplayType.DateTime, Env.getLanguage(getCtx()));
//		m_dateFormat 	 = DisplayType.getDateFormat(DisplayType.Date, Env.getLanguage(getCtx()));
		//
		log.info(sb.toString());
	}

	// create new Document
	Document createNewDocument() throws ParserConfigurationException {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		return documentBuilder.newDocument();

	}
	
	/**
	 * 	Process - Generate Export Format
	 *	@return info
	 */
	protected String doIt () throws Exception
	{
		outDocument = createNewDocument();
		
		MClient client = MClient.get (getCtx(), p_AD_Client_ID);
		log.info(client.toString());
		// TODO - get proper Export Format!
		int EXP_Format_ID = 1000000;
		
		MTable table = MTable.get(getCtx(), AD_Table_ID);
		log.info("Table = " + table);
		PO po = table.getPO (p_Record_ID, get_TrxName());
		
		if (po.get_KeyColumns().length > 1 || po.get_KeyColumns().length < 1) {
			throw new Exception(Msg.getMsg (getCtx(), "ExportMultiColumnNotSupported"));
		}
		MEXPFormat exportFormat = new MEXPFormat(getCtx(), EXP_Format_ID, get_TrxName());
		
		StringBuffer sql = new StringBuffer("SELECT * ")
					.append("FROM ").append(table.getTableName()).append(" ")
				   .append("WHERE ").append(po.get_KeyColumns()[0]).append("=?")
		;
		
		if (exportFormat.getWhereClause() != null & !"".equals(exportFormat.getWhereClause())) {
			sql.append(" AND ").append(exportFormat.getWhereClause());
		}
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
			pstmt.setInt(1, p_Record_ID);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				HashMap<String, Integer> variableMap = new HashMap<String, Integer>();
				variableMap.put(TOTAL_SEGMENTS, new Integer(1));
				
				Element rootElement = outDocument.createElement(exportFormat.getValue());
				rootElement.appendChild(outDocument.createComment(exportFormat.getDescription()));
				outDocument.appendChild(rootElement);
				generateExportFormat(rootElement, exportFormat, rs, po, p_Record_ID, variableMap);
			}
			
		} finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
			} catch (SQLException ex) {/*ignored*/}
			rs = null;
			pstmt = null;
		}
		
/*		int C_EDIProcessorType_ID = ediProcessor.getC_EDIProcessorType_ID();
		X_C_EDIProcessorType ediProcessType = new X_C_EDIProcessorType(getCtx(), C_EDIProcessorType_ID, get_TrxName() );
		
		String javaClass = ediProcessType.getJavaClass();
		try {
			Class clazz = Class.forName(javaClass);
			IOutbandEdiProcessor outbandProcessor = (IOutbandEdiProcessor)clazz.newInstance();
			
			outbandProcessor.process(getCtx(), ediProcessor, result.toString(), "C_Invoice-"+p_Record_ID+".txt",  Trx.get( get_TrxName(), false ));
		} catch (Exception e) {
			result = new StringBuffer( e.toString() );
		}
*/		
		addLog(0, null, null, Msg.getMsg (getCtx(), "ExportProcessResult") + "\n" + outDocument.toString());
		return outDocument.toString();
	}


	/*
	 * Trifon Generate Export Format process; RESULT = 
	 * <C_Invoice>
	 *   <DocumentNo>101</DocumentNo>
	 * </C_Invoice>
	 */
	private void generateExportFormat(Element rootElement, MEXPFormat exportFormat, ResultSet rs, PO masterPO, int masterID, HashMap<String, Integer> variableMap) throws SQLException, Exception 
	{
		Collection<I_EXP_FormatLine> formatLines = exportFormat.getFormatLines();
		@SuppressWarnings("unused")
		boolean elementHasValue = false;
		
		for (I_EXP_FormatLine formatLine : formatLines) 
		{
			if ( formatLine.getType().equals(X_EXP_FormatLine.TYPE_XMLElement) ) {
				// process single XML Attribute
				// Create new element
				Element newElement = outDocument.createElement(formatLine.getValue());

				if (formatLine.getAD_Column_ID() == 0) {
					throw new Exception(Msg.getMsg (getCtx(), "EXPColumnMandatory"));
				}
				MColumn column = MColumn.get(getCtx(), formatLine.getAD_Column_ID());
				if (column == null) {
					throw new Exception(Msg.getMsg (getCtx(), "EXPColumnMandatory"));
				}
				if ( column.isVirtualColumn() ) {
					log.info("This is Virtual Column!");
				} else { }
				//log.info("["+column.getColumnName()+"]");
				
				Object value = rs.getObject(column.getColumnName());
				String valueString = null;
				if (value != null) {
					valueString = value.toString();
				} else {
					if (formatLine.isMandatory()) {
						throw new Exception(Msg.getMsg (getCtx(), "EXPFieldMandatory"));
					}
				}
/*				if (column.getAD_Reference_ID() == DisplayType.Date) {
					if (valueString != null) {
						if (formatLines[i].getDateFormat() != null && !"".equals(formatLines[i].getDateFormat())) {
							m_customDateFormat = new SimpleDateFormat( formatLines[i].getDateFormat() ); // "MM/dd/yyyy"
							//Date date = m_customDateFormat.parse ( valueString );
							valueString = m_customDateFormat.format(Timestamp.valueOf (valueString));
						} else {
							valueString = m_dateFormat.format (Timestamp.valueOf (valueString));
						}
								
					}
				} else if (column.getAD_Reference_ID() == DisplayType.DateTime) {
					if (valueString != null) {
						if (formatLines[i].getDateFormat() != null && !"".equals(formatLines[i].getDateFormat())) {
							m_customDateFormat = new SimpleDateFormat( formatLines[i].getDateFormat() ); // "MM/dd/yyyy"
							//Date date = m_customDateFormat.parse ( valueString );
							valueString = m_customDateFormat.format(Timestamp.valueOf (valueString));
						} else {
							valueString = m_dateTimeFormat.format (Timestamp.valueOf (valueString));
						}
					}
				}*/
				log.info("EXP Field - column=["+column.getColumnName()+"]; value=" + value);
				if (valueString != null && !"".equals(valueString) && !"null".equals(valueString)) {
					Text newText = outDocument.createTextNode(valueString);
					newElement.appendChild(newText);
					rootElement.appendChild(newElement);
					elementHasValue = true;
					//increaseVariable(variableMap, formatLines[i].getVariableName()); // Increase value of Variable if any Variable 
					//increaseVariable(variableMap, TOTAL_SEGMENTS);
				} else {
					// Empty field.
				}
			} else if ( formatLine.getType().equals(X_EXP_FormatLine.TYPE_XMLAttribute) ) {
				// process single XML Attribute
/*				// Create new element
				Element newElement = outDocument.createElement(formatLines[i].getValue());
				if (hasContent) {
					rootElement.appendChild(newElement);
				}*/
				if (formatLine.getAD_Column_ID() == 0) {
					throw new Exception(Msg.getMsg (getCtx(), "EXPColumnMandatory"));
				}
				MColumn column = MColumn.get(getCtx(), formatLine.getAD_Column_ID());
				if (column == null) {
					throw new Exception(Msg.getMsg (getCtx(), "EXPColumnMandatory"));
				}
				if ( column.isVirtualColumn() ) {
					log.info("This is Virtual Column!");
				} else { }
				//log.info("["+column.getColumnName()+"]");
				
				Object value = rs.getObject(column.getColumnName());
				String valueString = null;
				if (value != null) {
					valueString = value.toString();
				} else {
					if (formatLine.isMandatory()) {
						throw new Exception(Msg.getMsg (getCtx(), "EXPFieldMandatory"));
					}
				}
/*				if (column.getAD_Reference_ID() == DisplayType.Date) {
					if (valueString != null) {
						if (formatLines[i].getDateFormat() != null && !"".equals(formatLines[i].getDateFormat())) {
							m_customDateFormat = new SimpleDateFormat( formatLines[i].getDateFormat() ); // "MM/dd/yyyy"
							//Date date = m_customDateFormat.parse ( valueString );
							valueString = m_customDateFormat.format(Timestamp.valueOf (valueString));
						} else {
							valueString = m_dateFormat.format (Timestamp.valueOf (valueString));
						}
								
					}
				} else if (column.getAD_Reference_ID() == DisplayType.DateTime) {
					if (valueString != null) {
						if (formatLines[i].getDateFormat() != null && !"".equals(formatLines[i].getDateFormat())) {
							m_customDateFormat = new SimpleDateFormat( formatLines[i].getDateFormat() ); // "MM/dd/yyyy"
							//Date date = m_customDateFormat.parse ( valueString );
							valueString = m_customDateFormat.format(Timestamp.valueOf (valueString));
						} else {
							valueString = m_dateTimeFormat.format (Timestamp.valueOf (valueString));
						}
					}
				}*/
				log.info("EXP Field - column=["+column.getColumnName()+"]; value=" + value);
				if (valueString != null && !"".equals(valueString) && !"null".equals(valueString)) {
					rootElement.setAttribute(formatLine.getValue(), valueString);
					elementHasValue = true;
					//increaseVariable(variableMap, formatLines[i].getVariableName()); // Increase value of Variable if any Variable 
					//increaseVariable(variableMap, TOTAL_SEGMENTS);
				} else {
					// Empty field.
				}
			} else if ( formatLine.getType().equals(X_EXP_FormatLine.TYPE_EmbeddedEXPFormat) ) {
				// process Embedded Export Format
				
				int embeddedFormat_ID = formatLine.getEXP_EmbeddedFormat_ID();
				MEXPFormat embeddedFormat = new MEXPFormat(getCtx(), embeddedFormat_ID, get_TrxName());
				
				MTable tableEmbedded = MTable.get(getCtx(), embeddedFormat.getAD_Table_ID());
				log.info("Table Embedded = " + tableEmbedded);
				StringBuffer sql = new StringBuffer("SELECT * ")
					   .append("FROM ").append(tableEmbedded.getTableName()).append(" ")
					   .append("WHERE ").append(masterPO.get_KeyColumns()[0]).append("=?")
					   //+ "WHERE " + po.get_WhereClause(false)
				;
				if (embeddedFormat.getWhereClause() != null & !"".equals(embeddedFormat.getWhereClause())) {
					sql.append(" AND ").append(embeddedFormat.getWhereClause());
				}
				ResultSet rsEmbedded = null;
				PreparedStatement pstmt = null;
				try
				{
					pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
					pstmt.setInt(1, masterID);
					rsEmbedded = pstmt.executeQuery();
					while (rsEmbedded.next())
					{
						//System.out.println("Trifon - tableEmbedded.getTableName()_ID = "+ tableEmbedded.getTableName() + "_ID");
						int embeddedID = rsEmbedded.getInt(tableEmbedded.getTableName() + "_ID");
						PO poEmbedded = tableEmbedded.getPO (embeddedID, get_TrxName());
						
						Element embeddedElement = outDocument.createElement(formatLine.getValue());
						embeddedElement.appendChild(outDocument.createComment(formatLine.getDescription()));
						generateExportFormat(embeddedElement, embeddedFormat, rsEmbedded, poEmbedded, embeddedID, variableMap);
						rootElement.appendChild(embeddedElement);
					}
					
				} finally {
					try {
						if (rsEmbedded != null) rsEmbedded.close();
						if (pstmt != null) pstmt.close();
					} catch (SQLException ex) {  }
					rsEmbedded = null;
					pstmt = null;
				}

			} else {
				throw new Exception(Msg.getMsg (getCtx(), "EXPUnknownLineType"));
			}
		}
	}

	/**
	 * @param variableMap
	 * @param variableName
	 */
	@SuppressWarnings("unused")
	private void increaseVariable(HashMap<String, Integer> variableMap,	String variableName) {
		if (variableName != null && !"".equals(variableName) ) {
			Integer var = variableMap.get(variableName);
			if (var == null) {
				var = new Integer(0);
			}
			int intValue = var.intValue();
			intValue++;
			variableMap.put(variableName, new Integer(intValue));
		}
	}
	
}
