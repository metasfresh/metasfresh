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
package org.compiere.dbPort;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 *  Convert SQL to Target DB
 *
 *  @author     Jorg Janke, Victor Perez
 *  
 *  @author Teo Sarca, www.arhipac.ro
 *  		<li>BF [ 2782095 ] Do not log *Access records
 *  			https://sourceforge.net/tracker/?func=detail&aid=2782095&group_id=176962&atid=879332
 *  		<li>TODO: BF [ 2782611 ] Migration scripts are not UTF8
 *  			https://sourceforge.net/tracker/?func=detail&aid=2782611&group_id=176962&atid=879332
 *  @author Teo Sarca
 *  		<li>BF [ 3137355 ] PG query not valid when contains quotes and backslashes
 *  			https://sourceforge.net/tracker/?func=detail&aid=3137355&group_id=176962&atid=879332	
 */
public abstract class Convert
{
	/** RegEx: insensitive and dot to include line end characters   */
	public static final int         REGEX_FLAGS = Pattern.CASE_INSENSITIVE | Pattern.DOTALL;

	/** Last Conversion Error           */
	protected String                  m_conversionError = null;

	/**	Logger	*/
	private static final transient Logger log = LogManager.getLogger(Convert.class);
	

	/**
	 *  Convert SQL Statement (stops at first error).
	 *  If an error occurred hadError() returns true.
	 *  You can get details via getConversionError()
	 *  @param sqlStatements
	 *  @return Array of converted Statements
	 */
	public final List<String> convert (String sqlStatements)
	{
		m_conversionError = null;
		if (sqlStatements == null || sqlStatements.isEmpty())
		{
			m_conversionError = "SQL_Statement is null or has zero length";
			log.info(m_conversionError);
			return null;
		}
		//
		return convertIt (sqlStatements);
	}   //  convert

	/**
	 *  Return last conversion error or null.
	 *  @return lst conversion error
	 */
	public final String getConversionError()
	{
		return m_conversionError;
	}   //  getConversionError

	
	/**************************************************************************
	 *  Conversion routine (stops at first error).
	 *  <pre>
	 *  - convertStatement
	 *      - convertWithConvertMap
	 *      - convertComplexStatement
	 *      - decode, sequence, exception
	 *  </pre>
	 *  @param sqlStatements
	 *  @return array of converted statements
	 */
	protected final List<String> convertIt (String sqlStatements)
	{
		final List<String> result = convertStatement(sqlStatements);     //  may return more than one target statement
		if (result == null)
		{
			return Collections.emptyList();
		}
		return result;
	}   //  convertIt

	/**
	 * Clean up Statement. Remove trailing spaces, carriage return and tab 
	 * 
	 * @param statement
	 * @return sql statement
	 */
	protected final String cleanUpStatement(String statement)
	{
		String clean = statement.trim();

		// Convert cr/lf/tab to single space
		Matcher m = Pattern.compile("\\s+").matcher(clean);
		clean = m.replaceAll(" ");

		clean = clean.trim();
		return clean;
	} // removeComments
	
	/**
	 * Utility method to replace quoted string with a predefined marker
	 * @param retValue
	 * @param retVars
	 * @return string
	 */
	protected final String replaceQuotedStrings(String inputValue, final Collection<String> retVars)
	{
		// save every value  
		// Carlos Ruiz - globalqss - better matching regexp
		retVars.clear();
		
		// First we need to replace double quotes to not be matched by regexp - Teo Sarca BF [3137355 ]
		final String quoteMarker = "<--QUOTE"+System.currentTimeMillis()+"-->";
		inputValue = inputValue.replace("''", quoteMarker);
		
		Pattern p = Pattern.compile("'[[^']*]*'");
		Matcher m = p.matcher(inputValue);
		int i = 0;
		StringBuffer retValue = new StringBuffer(inputValue.length());
		while (m.find()) {
			String var = inputValue.substring(m.start(), m.end()).replace(quoteMarker, "''"); // Put back quotes, if any
			retVars.add(var);
			m.appendReplacement(retValue, "<--" + i + "-->");
			i++;
		}
		m.appendTail(retValue);
		return retValue.toString()
			.replace(quoteMarker, "''") // Put back quotes, if any
		;
	}

	/**
	 * Utility method to recover quoted string store in retVars
	 * @param retValue
	 * @param retVars
	 * @return string
	 */
	protected final String recoverQuotedStrings(String retValue, Collection<String> retVars)
	{
		int i = 0;
		for (String replacement : retVars)
		{
			//hengsin, special character in replacement can cause exception
			replacement = escapeQuotedString(replacement);
			retValue = retValue.replace("<--" + i + "-->", replacement);
			i++;
		}
		return retValue;
	}
	
	/**
	 * hook for database specific escape of quoted string ( if needed )
	 * @param in
	 * @return string
	 */
	protected String escapeQuotedString(String in)
	{
		return in;
	}
	
	/**
	 * Convert simple SQL Statement. Based on ConvertMap
	 * 
	 * @param sqlStatement
	 * @return converted Statement
	 */
	private final String applyConvertMap(String sqlStatement) {
		// Error Checks
		if (sqlStatement.toUpperCase().indexOf("EXCEPTION WHEN") != -1) {
			String error = "Exception clause needs to be converted: "
					+ sqlStatement;
			log.info(error);
			m_conversionError = error;
			return sqlStatement;
		}

		// Carlos Ruiz - globalqss
		// Standard Statement -- change the keys in ConvertMap
		
		String retValue = sqlStatement;

		// for each iteration in the conversion map
		final ConvertMap convertMap = getConvertMap();
		if (convertMap != null)
		{
//			final Iterator<Pattern> iter = convertMap.keySet().iterator();
			for (final Map.Entry<Pattern, String> pattern2replacement : convertMap.getPattern2ReplacementEntries())
//			while (iter.hasNext())
			{
			    // replace the key on convertmap (i.e.: number by numeric)   
				final Pattern regex = pattern2replacement.getKey();
				final String replacement = pattern2replacement.getValue();
				try
				{
					final Matcher matcher = regex.matcher(retValue);
					retValue = matcher.replaceAll(replacement);
	
				}
				catch (Exception e)
				{
					String error = "Error expression: " + regex + " - " + e;
					log.info(error);
					m_conversionError = error;
				}
			}
		}
		
		return retValue;
	} // convertSimpleStatement
	
	/**
	 * do convert map base conversion
	 * @param sqlStatement
	 * @return string
	 */
	protected final String convertWithConvertMap(String sqlStatement)
	{
		try 
		{
			sqlStatement = applyConvertMap(cleanUpStatement(sqlStatement));
		}
		catch (RuntimeException e)
		{
			log.warn("Failed converting {}", sqlStatement, e);
		}
		
		return sqlStatement;
	}
	
	/**
	 * Get convert map for use in sql convertion
	 * @return map
	 */
	protected ConvertMap getConvertMap()
	{
		return null;
	}
	
	/**
	 *  Convert single Statements.
	 *  - remove comments
	 *      - process FUNCTION/TRIGGER/PROCEDURE
	 *      - process Statement
	 *  @param sqlStatement
	 *  @return converted statement
	 */
	protected abstract List<String> convertStatement (String sqlStatement);

	/**
	 * Mark given keyword as native.
	 * 
	 * Some prefixes/suffixes can be added, but this depends on implementation.
	 * 
	 * @param keyword
	 * @return keyword with some prefix/suffix markers.
	 */
	public String markNative(String keyword)
	{
		return keyword;
	}
}   //  Convert
