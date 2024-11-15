package de.metas.i18n;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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

import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Language;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import de.metas.logging.LogManager;
import de.metas.util.Services;

/**
 * Translation Table Import + Export
 *
 * @author metas-dev <dev@metasfresh.com>
 * @author based on initial version of Jorg Janke
 */
public class TranslationImpExp
{
	public static final TranslationImpExp newInstance()
	{
		return new TranslationImpExp(Env.getCtx());
	}

	/** Logger */
	private static final transient Logger log = LogManager.getLogger(TranslationImpExp.class);

	/** DTD */
	private static final String DTD = "<!DOCTYPE adempiereTrl PUBLIC \"-//ComPiere, Inc.//DTD Adempiere Translation 1.0//EN\" \"http://www.adempiere.com/dtd/adempiereTrl.dtd\">";
	/** XML Element Tag */
	public static final String XML_TAG = "adempiereTrl";
	public static final String XML_TAG2 = "compiereTrl";
	/** XML Attribute Table */
	public static final String XML_ATTRIBUTE_TABLE = "table";
	/** XML Attribute Language */
	public static final String XML_ATTRIBUTE_LANGUAGE = "language";

	/** XML Row Tag */
	public static final String XML_ROW_TAG = "row";
	/** XML Row Attribute ID */
	public static final String XML_ROW_ATTRIBUTE_ID = "id";
	/** XML Row Attribute Translated */
	public static final String XML_ROW_ATTRIBUTE_TRANSLATED = "trl";

	/** XML Value Tag */
	public static final String XML_VALUE_TAG = "value";
	/** XML Value Column */
	public static final String XML_VALUE_ATTRIBUTE_COLUMN = "column";
	/** XML Value Original */
	public static final String XML_VALUE_ATTRIBUTE_ORIGINAL = "original";

	/** Table is centrally maintained */
	private boolean m_IsCentrallyMaintained = false;
	/** Properties */
	private final Properties m_ctx;

	private TranslationImpExp(final Properties ctx)
	{
		m_ctx = ctx;
	}

	/**
	 * Import Translation.
	 * Uses TranslationHandler to update translation
	 *
	 * @param directory file directory
	 * @param AD_Client_ID only certain client if id >= 0
	 * @param AD_Language language
	 * @param Trl_Table table
	 * @return status message
	 */
	public String importTrl(final String directory, final int AD_Client_ID, final String AD_Language, final String Trl_Table)
	{
		final String fileName = directory + File.separator + Trl_Table + "_" + AD_Language + ".xml";
		log.info(fileName);
		final File in = new File(fileName);
		if (!in.exists())
		{
			final String msg = "File does not exist: " + fileName;
			log.error(msg);
			return msg;
		}

		try
		{
			final TranslationHandler handler = new TranslationHandler(AD_Client_ID);
			final SAXParserFactory factory = SAXParserFactory.newInstance();
			// factory.setValidating(true);
			final SAXParser parser = factory.newSAXParser();
			parser.parse(in, handler);
			log.info("Updated={}", handler.getUpdateCount());
			return Services.get(IMsgBL.class).getMsg(m_ctx, "Updated") + "=" + handler.getUpdateCount();
		}
		catch (final Exception e)
		{
			log.error("importTrl", e);
			return e.toString();
		}
	}	// importTrl

	/**************************************************************************
	 * Import Translation
	 *
	 * @param directory file directory
	 * @param AD_Client_ID only certain client if id >= 0
	 * @param AD_Language language
	 * @param Trl_Table translation table _Trl
	 * @return status message
	 */
	public String exportTrl(final String directory, final int AD_Client_ID, final String AD_Language, final String Trl_Table)
	{
		final String fileName = directory + File.separator + Trl_Table + "_" + AD_Language + ".xml";
		log.info(fileName);
		final File out = new File(fileName);

		final boolean isBaseLanguage = Language.isBaseLanguage(AD_Language);
		String tableName = Trl_Table;
		final int pos = tableName.indexOf("_Trl");
		final String Base_Table = Trl_Table.substring(0, pos);
		if (isBaseLanguage)
		{
			tableName = Base_Table;
		}
		final String keyColumn = Base_Table + "_ID";
		final String[] trlColumns = getTrlColumns(Base_Table);
		//
		StringBuffer sql = null;
		try
		{
			final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			// System.out.println(factory.getClass().getName());
			final DocumentBuilder builder = factory.newDocumentBuilder();
			// <!DOCTYPE adempiereTrl SYSTEM "http://www.adempiere.org/dtd/adempiereTrl.dtd">
			// <!DOCTYPE adempiereTrl PUBLIC "-//ComPiere, Inc.//DTD Adempiere Translation 1.0//EN" "http://www.adempiere.org/dtd/adempiereTrl.dtd">
			final Document document = builder.newDocument();
			document.appendChild(document.createComment(Adempiere.getSummaryAscii()));
			document.appendChild(document.createComment(DTD));

			// Root
			final Element root = document.createElement(XML_TAG);
			root.setAttribute(XML_ATTRIBUTE_LANGUAGE, AD_Language);
			root.setAttribute(XML_ATTRIBUTE_TABLE, Base_Table);
			document.appendChild(root);
			//
			sql = new StringBuffer("SELECT ");
			if (isBaseLanguage)
			{
				sql.append("'Y',");							// 1
			}
			else
			{
				sql.append("t.IsTranslated,");
			}
			sql.append("t.").append(keyColumn);				// 2
			//
			for (int i = 0; i < trlColumns.length; i++)
			{
				sql.append(", t.").append(trlColumns[i])
						.append(",o.").append(trlColumns[i]).append(" AS ").append(trlColumns[i]).append("O");
			}
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
				sql.append(haveWhere ? " AND " : " WHERE ").append("(o.IsCentrallyMaintained='N' or o.IsCentrallyMaintained='Y')"); // metas: added "or o.IsCentrallyMaintained='Y'"; tsa: WTF is this code?
				haveWhere = true;
			}
			if (AD_Client_ID >= 0)
			{
				sql.append(haveWhere ? " AND " : " WHERE ").append("o.AD_Client_ID=").append(AD_Client_ID);
			}
			sql.append(" ORDER BY t.").append(keyColumn);
			//
			final PreparedStatement pstmt = DB.prepareStatement(sql.toString(), null);
			if (!isBaseLanguage)
			{
				pstmt.setString(1, AD_Language);
			}
			final ResultSet rs = pstmt.executeQuery();
			int rows = 0;
			while (rs.next())
			{
				final Element row = document.createElement(XML_ROW_TAG);
				row.setAttribute(XML_ROW_ATTRIBUTE_ID, String.valueOf(rs.getInt(2)));	// KeyColumn
				row.setAttribute(XML_ROW_ATTRIBUTE_TRANSLATED, rs.getString(1));		// IsTranslated
				for (int i = 0; i < trlColumns.length; i++)
				{
					final Element value = document.createElement(XML_VALUE_TAG);
					value.setAttribute(XML_VALUE_ATTRIBUTE_COLUMN, trlColumns[i]);
					String origString = rs.getString(trlColumns[i] + "O");			// Original Value
					if (origString == null)
					{
						origString = "";
					}
					String valueString = rs.getString(trlColumns[i]);				// Value
					if (valueString == null)
					{
						valueString = "";
					}
					value.setAttribute(XML_VALUE_ATTRIBUTE_ORIGINAL, origString);
					if (valueString.indexOf("<") != -1 || valueString.indexOf(">") != -1 || valueString.indexOf("&") != -1)
					{
						value.appendChild(document.createCDATASection(valueString));
					}
					else
					{
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
			final DOMSource source = new DOMSource(document);
			final TransformerFactory tFactory = TransformerFactory.newInstance();
			tFactory.setAttribute("indent-number", Integer.valueOf(1)); // teo_sarca [ 1705883 ]
			final Transformer transformer = tFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // teo_sarca [ 1705883 ]
			// Output, wrapped with a writer - teo_sarca [ 1705883 ]
			out.createNewFile();
			final Writer writer = new OutputStreamWriter(new FileOutputStream(out), "utf-8");
			final StreamResult result = new StreamResult(writer);
			// Transform
			transformer.transform(source, result);
			// Close writer - teo_sarca [ 1705883 ]
			writer.close();
		}
		catch (final SQLException e)
		{
			log.error(sql.toString(), e);
			return e.toString();
		}
		catch (final Exception e)
		{
			log.error("", e);
			return e.toString();
		}

		return "";
	}	// exportTrl

	/**
	 * Get Columns for Table
	 *
	 * @param Base_Table table
	 * @return array of translated columns
	 */
	private String[] getTrlColumns(final String Base_Table)
	{
		m_IsCentrallyMaintained = false;
		String sql = "SELECT TableName FROM AD_Table t"
				+ " INNER JOIN AD_Column c ON (c.AD_Table_ID=t.AD_Table_ID AND c.ColumnName='IsCentrallyMaintained') "
				+ "WHERE t.TableName=? AND c.IsActive='Y'";
		try
		{
			final PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setString(1, Base_Table);
			final ResultSet rs = pstmt.executeQuery();
			if (rs.next())
			{
				m_IsCentrallyMaintained = true;
			}
			rs.close();
			pstmt.close();
		}
		catch (final SQLException e)
		{
			log.error(sql, e);
		}

		sql = "SELECT ColumnName "
				+ "FROM AD_Column c"
				+ " INNER JOIN AD_Table t ON (c.AD_Table_ID=t.AD_Table_ID) "
				+ "WHERE t.TableName=?"
				+ " AND c.AD_Reference_ID IN (10,14) "
				+ "ORDER BY IsMandatory DESC, ColumnName";
		final ArrayList<String> list = new ArrayList<>();
		try
		{
			final PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setString(1, Base_Table + "_Trl");
			final ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				final String s = rs.getString(1);
				// System.out.println(s);
				list.add(s);
			}
			rs.close();
			pstmt.close();
		}
		catch (final SQLException e)
		{
			log.error(sql, e);
		}

		// Convert to Array
		final String[] retValue = new String[list.size()];
		list.toArray(retValue);
		return retValue;
	}	// getTrlColumns

	/**************************************************************************
	 * Validate Language.
	 * - Check if AD_Language record exists
	 * - Check Trl table records
	 *
	 * @param AD_Language language
	 */
	public void validateLanguage(final String AD_Language)
	{
		final I_AD_Language language = Services.get(ILanguageDAO.class).retrieveByAD_Language(AD_Language);

		// No AD_Language Record
		if (language == null)
		{
			throw new AdempiereException("Language does not exist: " + AD_Language);
		}
		// Language exists
		if (language.isActive())
		{
			if (language.isBaseLanguage())
			{
				return; // OK
			}
		}
		else
		{
			throw new AdempiereException("Language not active or not system language: " + AD_Language);
		}

		// Validate Translation
		log.info("Start Validating ... {}", language);
		Services.get(ILanguageDAO.class).addMissingTranslations(language);
	}	// validateLanguage

	//
	//
	//
	//
	//
	/**
	 * SAX Handler for parsing Translation
	 *
	 * @author Jorg Janke
	 * @version $Id: TranslationHandler.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
	 */
	private static class TranslationHandler extends DefaultHandler
	{
		/**
		 * Translation Handler
		 *
		 * @param AD_Client_ID only certain client if id >= 0
		 */
		public TranslationHandler(final int AD_Client_ID)
		{
			m_AD_Client_ID = AD_Client_ID;
		}

		/** Client */
		private final int m_AD_Client_ID;
		/** Language */
		private String m_AD_Language = null;
		/** Is Base Language */
		private boolean m_isBaseLanguage = false;
		/** Table */
		private String m_TableName = null;
		/** Update SQL */
		private String m_updateSQL = null;
		/** Current ID */
		private String m_curID = null;
		/** Translated Flag */
		private String m_trl = null;
		/** Current ColumnName */
		private String m_curColumnName = null;
		/** Current Value */
		private StringBuffer m_curValue = null;
		/** SQL */
		private StringBuffer m_sql = null;

		private final Timestamp m_time = new Timestamp(System.currentTimeMillis());
		private int m_updateCount = 0;

		/**************************************************************************
		 * Receive notification of the start of an element.
		 *
		 * @param uri namespace
		 * @param localName simple name
		 * @param qName qualified name
		 * @param attributes attributes
		 * @throws org.xml.sax.SAXException
		 */
		@Override
		public void startElement(final String uri, final String localName, final String qName, final Attributes attributes)
				throws org.xml.sax.SAXException
		{
			// log.debug( "TranslationHandler.startElement", qName); // + " - " + uri + " - " + localName);
			if (qName.equals(XML_TAG) || qName.equals(XML_TAG2))
			{
				m_AD_Language = attributes.getValue(XML_ATTRIBUTE_LANGUAGE);
				m_isBaseLanguage = Language.isBaseLanguage(m_AD_Language);
				m_TableName = attributes.getValue(XML_ATTRIBUTE_TABLE);
				m_updateSQL = "UPDATE " + m_TableName;
				if (!m_isBaseLanguage)
				{
					m_updateSQL += "_Trl";
				}
				m_updateSQL += " SET ";
				log.debug("AD_Language=" + m_AD_Language + ", Base=" + m_isBaseLanguage + ", TableName=" + m_TableName);
			}
			else if (qName.equals(XML_ROW_TAG))
			{
				m_curID = attributes.getValue(XML_ROW_ATTRIBUTE_ID);
				m_trl = attributes.getValue(XML_ROW_ATTRIBUTE_TRANSLATED);
				// log.trace( "ID=" + m_curID);
				m_sql = new StringBuffer();
			}
			else if (qName.equals(XML_VALUE_TAG))
			{
				m_curColumnName = attributes.getValue(XML_VALUE_ATTRIBUTE_COLUMN);
				// log.trace( "ColumnName=" + m_curColName);
			}
			else
			{
				log.error("UNKNOWN TAG: " + qName);
			}
			m_curValue = new StringBuffer();
		}	// startElement

		/**
		 * Receive notification of character data inside an element.
		 *
		 * @param ch buffer
		 * @param start start
		 * @param length length
		 * @throws SAXException
		 */
		@Override
		public void characters(final char ch[], final int start, final int length)
				throws SAXException
		{
			m_curValue.append(ch, start, length);
			// Log.trace(Log.l6_Database+1, "TranslationHandler.characters", m_curValue.toString());
		}	// characters

		/**
		 * Receive notification of the end of an element.
		 *
		 * @param uri namespace
		 * @param localName simple name
		 * @param qName qualified name
		 * @throws SAXException
		 */
		@Override
		public void endElement(final String uri, final String localName, final String qName)
				throws SAXException
		{
			// Log.trace(Log.l6_Database+1, "TranslationHandler.endElement", qName);
			if (qName.equals(XML_TAG) || qName.equals(XML_TAG2))
			{

			}
			else if (qName.equals(XML_ROW_TAG))
			{
				// Set section
				if (m_sql.length() > 0)
				{
					m_sql.append(",");
				}
				m_sql.append("Updated=").append(DB.TO_DATE(m_time, false));
				if (!m_isBaseLanguage)
				{
					if (m_trl != null
							&& ("Y".equals(m_trl) || "N".equals(m_trl)))
					{
						m_sql.append(",IsTranslated='").append(m_trl).append("'");
					}
					else
					{
						m_sql.append(",IsTranslated='Y'");
					}
				}
				// Where section
				m_sql.append(" WHERE ")
						.append(m_TableName).append("_ID=").append(m_curID);
				if (!m_isBaseLanguage)
				{
					m_sql.append(" AND AD_Language='").append(m_AD_Language).append("'");
				}
				if (m_AD_Client_ID >= 0)
				{
					m_sql.append(" AND AD_Client_ID=").append(m_AD_Client_ID);
				}
				// Update section
				m_sql.insert(0, m_updateSQL);

				// Execute
				final int no = DB.executeUpdateAndSaveErrorOnFail(m_sql.toString(), null);
				if (no == 1)
				{
					if (LogManager.isLevelFinest())
					{
						log.debug(m_sql.toString());
					}
					m_updateCount++;
				}
				else if (no == 0)
				{
					log.warn("Not Found - " + m_sql.toString());
				}
				else
				{
					log.error("Update Rows=" + no + " (Should be 1) - " + m_sql.toString());
				}
			}
			else if (qName.equals(XML_VALUE_TAG))
			{
				if (m_sql.length() > 0)
				{
					m_sql.append(",");
				}
				m_sql.append(m_curColumnName).append("=").append(DB.TO_STRING(m_curValue.toString()));
			}
		}	// endElement

		/**
		 * Get Number of updates
		 *
		 * @return update count
		 */
		public int getUpdateCount()
		{
			return m_updateCount;
		}	// getUpdateCount

	}	// TranslationHandler
}	// Translation
