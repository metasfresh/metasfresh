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
package org.compiere.print;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Properties;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.compiere.Adempiere;
import org.compiere.util.DisplayType;
import org.compiere.util.Trace;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *	Print Data Structure.
 * 	Created by DataEngine
 *  A Structure has rows, wich contain elements.
 *  Elements can be end nodes (PrintDataElements) or data structures (PrintData).
 *  The row data is sparse - i.e. null if not existing.
 *  A Structure has optional meta info about content (PrintDataColumn).
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: PrintData.java,v 1.3 2006/07/30 00:53:02 jjanke Exp $
 */
public class PrintData implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5013410697934610197L;

	/**
	 * 	Data Parent Constructor
	 * 	@param ctx context
	 * 	@param name data element name
	 */
	public PrintData (Properties ctx, String name)
	{
		if (name == null)
			throw new IllegalArgumentException("Name cannot be null");
		m_ctx = ctx;
		m_name = name;
	}	//	PrintData

	/**
	 * 	Data Parent Constructor
	 * 	@param ctx context
	 * 	@param name data element name
	 *  @param nodes ArrayList with nodes (content not checked)
	 */
	public PrintData (Properties ctx, String name, ArrayList<Object> nodes)
	{
		if (name == null)
			throw new IllegalArgumentException("Name cannot be null");
		m_ctx = ctx;
		m_name = name;
		if (nodes != null)
			m_nodes = nodes;
	}	//	PrintData

	/**	Context						*/
	private Properties	m_ctx;
	/**	Data Structure Name			*/
	private String 		m_name;
	/** Data Structure rows			*/
	private ArrayList<ArrayList<Object>>	m_rows = new ArrayList<ArrayList<Object>>();
	/** Current Row Data Structure elements		*/
	private ArrayList<Object>				m_nodes = null;
	/**	Current Row					*/
	private int			m_row = -1;
	/**	List of Function Rows		*/
	private ArrayList<Integer>	m_functionRows = new ArrayList<Integer>();

	/**	Table has LevelNo			*/
	private boolean		m_hasLevelNo = false;
	/**	Level Number Indicator		*/
	private static final String	LEVEL_NO = "LEVELNO";

	/**	Optional Column Meta Data	*/
	private PrintDataColumn[]	m_columnInfo = null;
	/**	Optional sql				*/
	private String				m_sql = null;
	/** Optional TableName			*/
	private String				m_TableName = null;

	/**	XML Element Name			*/
	public static final String	XML_TAG = "adempiereData";
	/**	XML Row Name				*/
	public static final String	XML_ROW_TAG = "row";
	/**	XML Attribute Name			*/
	public static final String	XML_ATTRIBUTE_NAME = "name";
	/** XML Attribute Count			*/
	public static final String	XML_ATTRIBUTE_COUNT = "count";
	/** XML Attribute Number		*/
	public static final String	XML_ATTRIBUTE_NO = "no";
	/** XML Attribute Function Row	*/
	public static final String	XML_ATTRIBUTE_FUNCTION_ROW = "function_row";

	/**	Logger			*/
	private static Logger log = LogManager.getLogger(PrintData.class);
	
	/**
	 * 	Get Context
	 * 	@return context
	 */
	public Properties getCtx()
	{
		return m_ctx;
	}	//	getName

	/**
	 * 	Get Name
	 * 	@return name
	 */
	public String getName()
	{
		return m_name;
	}	//	getName

	/*************************************************************************/

	/**
	 * 	Set optional Column Info
	 * 	@param newInfo Column Info
	 */
	public void setColumnInfo (PrintDataColumn[] newInfo)
	{
		m_columnInfo = newInfo;
	}	//	setColumnInfo

	/**
	 * 	Get optional Column Info
	 * 	@return Column Info
	 */
	public PrintDataColumn[] getColumnInfo()
	{
		return m_columnInfo;
	}	//	getColumnInfo

	/**
	 * 	Set SQL (optional)
	 * 	@param sql SQL
	 */
	public void setSQL (String sql)
	{
		m_sql = sql;
	}	//	setSQL

	/**
	 * 	Get optional SQL
	 * 	@return SQL
	 */
	public String getSQL()
	{
		return m_sql;
	}	//	getSQL

	/**
	 * 	Set TableName (optional)
	 * 	@param TableName TableName
	 */
	public void setTableName (String TableName)
	{
		m_TableName = TableName;
	}	//	setTableName

	/**
	 * 	Get optional TableName
	 * 	@return TableName
	 */
	public String getTableName()
	{
		return m_TableName;
	}	//	getTableName

	/**
	 * 	String representation
	 * 	@return info
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("PrintData[");
		sb.append(m_name).append(",Rows=").append(m_rows.size());
		if (m_TableName != null)
			sb.append(",TableName=").append(m_TableName);
		sb.append("]");
		return sb.toString();
	}	//	toString


	
	/**************************************************************************
	 * 	Returns true if no Nodes in row
	 * 	@return true if no Nodes in row
	 */
	public boolean isEmpty()
	{
		if (m_nodes == null)
			return true;
		return m_nodes.size() == 0;
	}	//	isEmpty

	/**
	 * 	Return Number of nodes in row
	 * 	@return number of nodes in row
	 */
	public int getNodeCount()
	{
		if (m_nodes == null)
			return 0;
		return m_nodes.size();
	}	//	getNodeCount

	
	/**************************************************************************
	 * 	Add Row
	 *  @param functionRow true if function row
	 * 	@param levelNo	Line detail Level Number 0=Normal
	 */
	public void addRow (boolean functionRow, int levelNo)
	{
		m_nodes = new ArrayList<Object>();
		m_row = m_rows.size();
		m_rows.add (m_nodes);
		if (functionRow)
			m_functionRows.add(new Integer(m_row));
		if (m_hasLevelNo && levelNo != 0)
			addNode(new PrintDataElement(LEVEL_NO, new Integer(levelNo), DisplayType.Integer, null));
	}	//	addRow

	/**
	 * 	Set Row Index
	 * 	@param row row index
	 * 	@return true if success
	 */
	public boolean setRowIndex (int row)
	{
		if (row < 0 || row >= m_rows.size())
			return false;
		m_row = row;
		m_nodes = (ArrayList<Object>)m_rows.get(m_row);
		return true;
	}

	/**
	 * 	Set Row Index to next
	 * 	@return true if success
	 */
	public boolean setRowNext()
	{
		return setRowIndex(m_row+1);
	}	//	setRowNext

	/**
	 * 	Get Row Count
	 * 	@return row count
	 */
	public int getRowCount()
	{
		return m_rows.size();
	}	//	getRowCount

	/**
	 * 	Get Current Row Index
	 * 	@return row index
	 */
	public int getRowIndex()
	{
		return m_row;
	}	//	getRowIndex

	/**
	 * 	Is the Row a Function Row
	 * 	@param row row no
	 * 	@return true if function row
	 */
	public boolean isFunctionRow (int row)
	{
		return m_functionRows.contains(new Integer(row));
	}	//	isFunctionRow

	/**
	 * 	Is the current Row a Function Row
	 * 	@return true if function row
	 */
	public boolean isFunctionRow ()
	{
		return m_functionRows.contains(new Integer(m_row));
	}	//	isFunctionRow

	/**
	 * 	Is the current Row a Function Row
	 * 	@return true if function row
	 */
	public boolean isPageBreak ()
	{
		//	page break requires function and meta data
		if (isFunctionRow() && m_nodes != null)
		{
			for (int i = 0; i < m_nodes.size(); i++)
			{
				Object o = m_nodes.get(i);
				if (o instanceof PrintDataElement)
				{
					PrintDataElement pde = (PrintDataElement)o;
					if (pde.isPageBreak())
						return true;
				}
			}
		}
		return false;
	}	//	isPageBreak

	/**
	 * 	PrintData has Level No
	 * 	@param hasLevelNo true if sql contains LevelNo
	 */
	public void setHasLevelNo (boolean hasLevelNo)
	{
		m_hasLevelNo = hasLevelNo;
	}	//	hasLevelNo

	/**
	 * 	PrintData has Level No
	 * 	@return true if sql contains LevelNo
	 */
	public boolean hasLevelNo()
	{
		return m_hasLevelNo;
	}	//	hasLevelNo

	/**
	 * 	Get Line Level Number for current row
	 * 	@return line level no 0 = default
	 */
	public int getLineLevelNo ()
	{
		if (m_nodes == null || !m_hasLevelNo)
			return 0;

		for (int i = 0; i < m_nodes.size(); i++)
		{
			Object o = m_nodes.get (i);
			if (o instanceof PrintDataElement)
			{
				PrintDataElement pde = (PrintDataElement)o;
				if (LEVEL_NO.equals (pde.getColumnName()))
				{
					Integer ii = (Integer)pde.getValue();
					return ii.intValue();
				}
			}
		}
		return 0;
	}	//	getLineLevel

	/*************************************************************************/

	/**
	 * 	Add Parent node to Data Structure row
	 * 	@param parent parent
	 */
	public void addNode (PrintData parent)
	{
		if (parent == null)
			throw new IllegalArgumentException("Parent cannot be null");
		if (m_nodes == null)
			addRow(false, 0);
		m_nodes.add (parent);
	}	//	addNode

	/**
	 * 	Add node to Data Structure row
	 * 	@param node node
	 */
	public void addNode (PrintDataElement node)
	{
		if (node == null)
			throw new IllegalArgumentException("Node cannot be null");
		if (m_nodes == null)
			addRow(false, 0);
		m_nodes.add (node);
	}	//	addNode

	/**
	 * 	Get Node with index in row
	 * 	@param index index
	 * 	@return PrintData(Element) of index or null
	 */
	public Object getNode (int index)
	{
		if (m_nodes == null || index < 0 || index >= m_nodes.size())
			return null;
		return m_nodes.get(index);
	}	//	getNode

	/**
	 * 	Get Node with Name in row
	 * 	@param name name
	 * 	@return PrintData(Element) with Name or null
	 */
	public Object getNode (String name)
	{
		int index = getIndex (name);
		if (index < 0)
			return null;
		return m_nodes.get(index);
	}	//	getNode

	/**
	 * 	Get Node with AD_Column_ID in row
	 * 	@param AD_Column_ID AD_Column_ID
	 * 	@return PrintData(Element) with AD_Column_ID or null
	 */
	public Object getNode (Integer AD_Column_ID)
	{
		int index = getIndex (AD_Column_ID.intValue());
		if (index < 0)
			return null;
		return m_nodes.get(index);
	}	//	getNode

	/**
	 * 	Get Primary Key in row
	 * 	@return PK or null
	 */
	public PrintDataElement getPKey()
	{
		if (m_nodes == null)
			return null;
		for (int i = 0; i < m_nodes.size(); i++)
		{
			Object o = m_nodes.get(i);
			if (o instanceof PrintDataElement)
			{
				PrintDataElement pde = (PrintDataElement)o;
				if (pde.isPKey())
					return pde;
			}
		}
		return null;
	}	//	getPKey

	/**
	 * 	Get Index of Node in Structure (not recursing) row
	 * 	@param columnName name
	 * 	@return index or -1
	 */
	public int getIndex (String columnName)
	{
		if (m_nodes == null)
			return -1;
		for (int i = 0; i < m_nodes.size(); i++)
		{
			Object o = m_nodes.get(i);
			if (o instanceof PrintDataElement)
			{
				if (columnName.equals(((PrintDataElement)o).getColumnName()))
					return i;
			}
			else if (o instanceof PrintData)
			{
				if (columnName.equals(((PrintData)o).getName()))
					return i;
			}
			else
				log.error("Element not PrintData(Element) " + o.getClass().getName());
		}
		//	As Data is stored sparse, there might be lots of NULL values
	//	log.error("PrintData.getIndex - Element not found - " + name);
		return -1;
	}	//	getIndex

	/**
	 * 	Get Index of Node in Structure (not recursing) row
	 * 	@param AD_Column_ID AD_Column_ID
	 * 	@return index or -1
	 */
	public int getIndex (int AD_Column_ID)
	{
		if (m_columnInfo == null)
			return -1;
		for (int i = 0; i < m_columnInfo.length; i++)
		{
			if (m_columnInfo[i].getAD_Column_ID() == AD_Column_ID)
				return getIndex(m_columnInfo[i].getColumnName());
		}
		log.error("Column not found - AD_Column_ID=" + AD_Column_ID);
		if (AD_Column_ID == 0)
			Trace.printStack();
		return -1;
	}	//	getIndex

	
	/**************************************************************************
	 * 	Dump All Data - header and rows
	 */
	public void dump()
	{
		dump(this);
	}	//	dump

	/**
	 * 	Dump All Data
	 */
	public void dumpHeader()
	{
		dumpHeader(this);
	}	//	dumpHeader

	/**
	 * 	Dump All Data
	 */
	public void dumpCurrentRow()
	{
		dumpRow(this, m_row);
	}	//	dump

	/**
	 * 	Dump all PrintData - header and rows
	 *  @param pd print data
	 */
	private static void dump (PrintData pd)
	{
		dumpHeader(pd);
		for (int i = 0; i < pd.getRowCount(); i++)
			dumpRow(pd, i);
	}	//	dump

	/**
	 * 	Dump PrintData Header
	 *  @param pd print data
	 */
	private static void dumpHeader (PrintData pd)
	{
		log.info(pd.toString());
		if (pd.getColumnInfo() != null)
		{
			for (int i = 0; i < pd.getColumnInfo().length; i++)
				log.info(i + ": " + pd.getColumnInfo()[i]);
		}
	}	//	dump

	/**
	 * 	Dump Row
	 *  @param pd print data
	 * 	@param row row
	 */
	private static void dumpRow (PrintData pd, int row)
	{
		log.info("Row #" + row);
		if (row < 0 || row >= pd.getRowCount())
		{
			log.warn("- invalid -");
			return;
		}
		pd.setRowIndex(row);
		if (pd.getNodeCount() == 0)
		{
			log.info("- n/a -");
			return;
		}
		for (int i = 0; i < pd.getNodeCount(); i++)
		{
			Object obj = pd.getNode(i);
			if (obj == null)
				log.info("- NULL -");
			else if (obj instanceof PrintData)
			{
				log.info("- included -");
				dump((PrintData)obj);
			}
			else if (obj instanceof PrintDataElement)
			{
				log.info(((PrintDataElement)obj).toStringX());
			}
			else
				log.info("- INVALID: " + obj);
		}
	}	//	dumpRow

	
	/**************************************************************************
	 * 	Get XML Document representation
	 * 	@return XML document
	 */
	public Document getDocument()
	{
		Document document = null;
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			//	System.out.println(factory.getClass().getName());
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.newDocument();
			document.appendChild(document.createComment(Adempiere.getSummaryAscii()));
		}
		catch (Exception e)
		{
			System.err.println(e);
			e.printStackTrace();
		}
		//	Root
		Element root = document.createElement(PrintData.XML_TAG);
		root.setAttribute(XML_ATTRIBUTE_NAME, getName());
		root.setAttribute(XML_ATTRIBUTE_COUNT, String.valueOf(getRowCount()));
		document.appendChild(root);
		processXML (this, document, root);
		return document;
	}	//	getDocument

	/**
	 * 	Process PrintData Tree
	 * 	@param pd Print Data
	 * 	@param document document
	 *  @param root element to add to
	 */
	private static void processXML (PrintData pd, Document document, Element root)
	{
		for (int r = 0; r < pd.getRowCount(); r++)
		{
			pd.setRowIndex(r);
			Element row = document.createElement(PrintData.XML_ROW_TAG);
			row.setAttribute(XML_ATTRIBUTE_NO, String.valueOf(r));
			if (pd.isFunctionRow())
				row.setAttribute(XML_ATTRIBUTE_FUNCTION_ROW, "yes");
			root.appendChild(row);
			//
			for (int i = 0; i < pd.getNodeCount(); i++)
			{
				Object o = pd.getNode(i);
				if (o instanceof PrintData)
				{
					PrintData pd_x = (PrintData)o;
					Element element = document.createElement(PrintData.XML_TAG);
					element.setAttribute(XML_ATTRIBUTE_NAME, pd_x.getName());
					element.setAttribute(XML_ATTRIBUTE_COUNT, String.valueOf(pd_x.getRowCount()));
					row.appendChild(element);
					processXML (pd_x, document, element);		//	recursive call
				}
				else if (o instanceof PrintDataElement)
				{
					PrintDataElement pde = (PrintDataElement)o;
					if (!pde.isNull())
					{
						Element element = document.createElement(PrintDataElement.XML_TAG);
						element.setAttribute(PrintDataElement.XML_ATTRIBUTE_NAME, pde.getColumnName());
						if (pde.hasKey())
							element.setAttribute(PrintDataElement.XML_ATTRIBUTE_KEY, pde.getValueKey());
						element.appendChild(document.createTextNode(pde.getValueDisplay(null)));	//	not formatted
						row.appendChild(element);
					}
				}
				else
					log.error("Element not PrintData(Element) " + o.getClass().getName());
			}	//	columns
		}	//	rows
	}	//	processTree


	/**
	 * 	Create XML representation to StreamResult
	 * 	@param result StreamResult
	 * 	@return true if success
	 */
	public boolean createXML (StreamResult result)
	{
		try
		{
			DOMSource source = new DOMSource(getDocument());
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			transformer.transform (source, result);
		}
		catch (Exception e)
		{
			log.error("(StreamResult)", e);
			return false;
		}
		return true;
	}	//	createXML

	/**
	 * 	Create XML representation to File
	 * 	@param fileName file name
	 * 	@return true if success
	 */
	public boolean createXML (String fileName)
	{
		try
		{
			File file = new File(fileName);
			file.createNewFile();
			StreamResult result = new StreamResult(file);
			createXML (result);
		}
		catch (Exception e)
		{
			log.error("(file)", e);
			return false;
		}
		return true;
	}	//	createXMLFile

	
	/**************************************************************************
	 *	Create PrintData from XML
	 *	@param ctx context
	 * 	@param input InputSource
	 * 	@return PrintData
	 */
	public static PrintData parseXML (Properties ctx, File input)
	{
		log.info(input.toString());
		PrintData pd = null;
		try
		{
			PrintDataHandler handler = new PrintDataHandler(ctx);
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			parser.parse(input, handler);
			pd = handler.getPrintData();
		}
		catch (Exception e)
		{
			log.error("", e);
		}
		return pd;
	}	//	parseXML
}	//	PrintData
