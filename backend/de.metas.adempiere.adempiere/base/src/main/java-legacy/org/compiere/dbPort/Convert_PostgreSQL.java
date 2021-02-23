/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 Adempiere, Inc. All Rights Reserved.               *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.compiere.dbPort;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.compiere.db.Database;

import de.metas.util.StringUtils;

/**
 * Convert Oracle SQL to PostgreSQL SQL
 *
 * @author Victor Perez, Low Heng Sin, Carlos Ruiz
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * <li>BF [ 1824256 ] Convert sql casts
 */
public class Convert_PostgreSQL extends Convert_SQL92
{
	private static final String NATIVE_MARKER = "NATIVE_" + Database.DB_POSTGRESQL + "_KEYWORD";

	private final ConvertMap m_map;
	private final String sharedNonce;

	public Convert_PostgreSQL()
	{
		m_map = ConvertMap_PostgreSQL.getConvertMap();
		sharedNonce = generateNonce();
	}

	/**
	 *  Generate fairly hard to guess numeric string
	 */
	private static String generateNonce()
	{
		final long randomLong = ThreadLocalRandom.current()
				.nextLong(100000000000000000L, 999999999999999999L);
		return Long.toString(randomLong);
	}

	@Override
	protected ConvertMap getConvertMap()
	{
		return m_map;
	}

	/**
	 * Convert single Statements. - remove comments - process
	 * FUNCTION/TRIGGER/PROCEDURE - process Statement
	 *
	 * @param sqlStatement
	 * @return converted statement
	 */
	@Override
	protected List<String> convertStatement(String sqlStatement)
	{
		/** Vector to save previous values of quoted strings **/
		final List<String> retVars = new ArrayList<String>();

		final String nonce = sharedNonce;
		String statement = replaceQuotedStrings(sqlStatement, retVars, nonce);
		statement = convertWithConvertMap(statement);
		statement = statement.replace(NATIVE_MARKER, "");

		String cmpString = statement.toUpperCase();
		boolean isCreate = cmpString.startsWith("CREATE ");

		// Process
		if (isCreate && cmpString.indexOf(" FUNCTION ") != -1)
			;
		else if (isCreate && cmpString.indexOf(" TRIGGER ") != -1)
			;
		else if (isCreate && cmpString.indexOf(" PROCEDURE ") != -1)
			;
		else if (isCreate && cmpString.indexOf(" VIEW ") != -1)
			;
		else if (cmpString.indexOf("ALTER TABLE") != -1)
		{
			statement = recoverQuotedStrings(statement, retVars, nonce);
			retVars.clear();
			statement = convertDDL(convertComplexStatement(statement));
		/*
		} else if (cmpString.indexOf("ROWNUM") != -1) {
			result.add(convertRowNum(convertComplexStatement(convertAlias(statement))));*/
		}
		else if (cmpString.indexOf("DELETE ") != -1
				&& cmpString.indexOf("DELETE FROM") == -1)
		{
			statement = convertDelete(statement);
			statement = convertComplexStatement(convertAlias(statement));
		}
		else if (cmpString.indexOf("DELETE FROM") != -1)
		{
			statement = convertComplexStatement(convertAlias(statement));
		}
		else if (cmpString.indexOf("UPDATE ") != -1)
		{
			statement = convertComplexStatement(convertUpdate(convertAlias(statement)));
		}
		else
		{
			statement = convertComplexStatement(convertAlias(statement));
		}
		if (retVars.size() > 0)
		{
			statement = recoverQuotedStrings(statement, retVars, nonce);
		}

		return Collections.singletonList(statement);
	} // convertStatement

	@Override
	protected String escapeQuotedString(String in)
	{
		StringBuffer out = new StringBuffer();
		boolean escape = false;
		int size = in.length();
		for (int i = 0; i < size; i++)
		{
			char c = in.charAt(i);
			out.append(c);
			if (c == '\\')
			{
				escape = true;
				out.append(c);
			}
		}
		if (escape)
		{
			return "E" + out.toString();
		}
		else
		{
			return out.toString();
		}
	}

	/***************************************************************************
	 * Converts Decode and Outer Join.
	 *
	 * <pre>
	 *        DECODE (a, 1, 'one', 2, 'two', 'none')
	 *         =&gt; CASE WHEN a = 1 THEN 'one' WHEN a = 2 THEN 'two' ELSE 'none' END
	 *
	 * </pre>
	 *
	 * @param sqlStatement
	 * @return converted statement
	 */
	protected String convertComplexStatement(String sqlStatement)
	{
		String retValue = sqlStatement;

		// Convert all decode parts
		int found = retValue.toUpperCase().indexOf("DECODE");
		int fromIndex = 0;
		while (found != -1)
		{
			retValue = convertDecode(retValue, fromIndex);
			fromIndex = found + 6;
			found = retValue.toUpperCase().indexOf("DECODE", fromIndex);
		}

		// Outer Join Handling -----------------------------------------------
		int index = retValue.toUpperCase().indexOf("SELECT ");
		if (index != -1 && retValue.indexOf("(+)", index) != -1)
			retValue = convertOuterJoin(retValue);

		// Convert datatypes from CAST(.. as datatypes):
		retValue = convertCast(retValue);

		return retValue;
	} // convertComplexStatement

	private static final String PATTERN_String = "\'([^']|(''))*\'";
	private static final String PATTERN_DataType = "([\\w]+)(\\(\\d+\\))?";
	private static final String PATTERN_CAST_STR =
			"\\bCAST\\b[\\s]*\\([\\s]*"                    // CAST<sp>(<sp>
					+ "((" + PATTERN_String + ")|([^\\s]+))"        //	arg1				1(2,3)
					+ "[\\s]*AS[\\s]*"                        //	<sp>AS<sp>
					+ "(" + PATTERN_DataType + ")"                //	arg2 (datatype)		4
					+ "\\s*\\)";                                //	<sp>)
	private static final Pattern PATTERN_CAST = Pattern.compile(PATTERN_CAST_STR, Pattern.CASE_INSENSITIVE);
	private static final int PATTERN_CAST_gidx_arg1 = 1;
	private static final int PATTERN_CAST_gidx_arg2 = 7;    // datatype w/o length

	/**
	 * Convert datatypes from CAST sentences
	 * <pre>
	 * 		cast(NULL as NVARCHAR2(255))
	 * 		=&gt;cast(NULL as VARCHAR)
	 * </pre>
	 */
	private String convertCast(String sqlStatement)
	{
		final Matcher matcher = PATTERN_CAST.matcher(sqlStatement);

		final ConvertMap convertMap = getConvertMap();
		StringBuffer retValue = new StringBuffer(sqlStatement.length());
		while (matcher.find())
		{
			String arg1 = matcher.group(PATTERN_CAST_gidx_arg1);
			String arg2 = matcher.group(PATTERN_CAST_gidx_arg2);
			//
			String datatype = convertMap.getDataTypeReplacement(arg2.toUpperCase());
			if (datatype == null)
				datatype = arg2;
			matcher.appendReplacement(retValue, "cast(" + arg1 + " as " + datatype + ")");
		}
		matcher.appendTail(retValue);
		return retValue.toString();
	}

	/**
	 * Convert RowNum.
	 *
	 * <pre>
	 *        SELECT Col1 FROM tableA WHERE ROWNUM=1
	 *        =&gt; SELECT Col1 FROM tableA LIMIT 1
	 *    Assumptions/Limitations:
	 *    - RowNum not used in SELECT part
	 * </pre>
	 *
	 * @param sqlStatement
	 * @return converted statement
	 */
	/*
	private String convertRowNum(String sqlStatement) {
		// log.info("RowNum<== " + sqlStatement);

        sqlStatement = Pattern.compile("rownum",REGEX_FLAGS).matcher(sqlStatement).replaceAll("ROWNUM");

		String retValue = null;

		// find into (select from where)

		int s_end = 0;
		int s_start = -1;
		String select = sqlStatement;
		String convert = "";
		while (true) {
			s_end = 0;
			s_start = select.indexOf("(SELECT");

			if (s_start == -1)
				break;

			convert = convert + select.substring(0, s_start);
			// System.out.println("convert:" + convert);
			int open = -1;
			for (int i = s_start; i < select.length(); i++) {
				char c = select.charAt(i);
				if (c == '(')
					open++;

				if (c == ')')
					open--;

				if (open == -1) {
					s_end = i + 1;
					break;
				}
			}

			String subselect = select.substring(s_start, s_end);
			// System.out.println("subselect:" +subselect);
			// System.out.println("select:" +select);

			if (subselect.indexOf("AND ROWNUM=1") > 1) {
				subselect = subselect.substring(0, subselect.length() - 1)
						+ " LIMIT 1 )";
				// System.out.println("subselect:" +subselect);
				convert = convert + Util.replace(subselect, "AND ROWNUM=1", "");
				// System.out.println("convert:" + convert);
			} else if (subselect.indexOf(" WHERE ROWNUM=1 AND") > 1) {
				subselect = subselect.substring(0, subselect.length() - 1)
						+ " LIMIT 1 )";
				// System.out.println("subselect:" +subselect);
				convert = convert
						+ Util.replace(subselect, " WHERE ROWNUM=1 AND",
								" WHERE ");
				// System.out.println("convert:" + convert);
			} else {
				convert = convert + subselect;
			}

			select = select.substring(s_end);
			retValue = select;

		}
		// System.out.println("convert:" + convert);
		// System.out.println("select:" + select);
		if (retValue == null)
			retValue = sqlStatement;

		if (retValue.indexOf("AND ROWNUM=1") > 1) {
			int rownum = retValue.indexOf("AND ROWNUM=1");
			if (retValue.substring(0, rownum).contains("WHERE")) {
				retValue = Util.replace(retValue, "AND ROWNUM=1", " LIMIT 1");
				return convert + retValue;
			} else {
				retValue = Util.replace(retValue, "AND ROWNUM=1", "");
				return convert + retValue + " LIMIT 1";
			}

		} else if (retValue.indexOf("AND ROWNUM= 1") > 1) {
			int rownum = retValue.indexOf("AND ROWNUM= 1");
			if (retValue.substring(0, rownum).contains("WHERE")) {

				retValue = Util.replace(retValue, "AND ROWNUM= 1", " LIMIT 1");
				return convert + retValue;
			} else {
				retValue = Util.replace(retValue, "AND ROWNUM= 1", "");
				return convert + retValue + " LIMIT 1";
			}
		} else if (retValue.indexOf("AND ROWNUM = 1") > 1) {
			int rownum = retValue.indexOf("AND ROWNUM = 1");
			if (retValue.substring(0, rownum).contains("WHERE")) {

				retValue = Util.replace(sqlStatement, "AND ROWNUM = 1",
						" LIMIT 1");
				return convert + retValue;
			} else {
				retValue = Util.replace(sqlStatement, "AND ROWNUM = 1", "");
				return convert + retValue + " LIMIT 1";
			}
		} else if (retValue.indexOf("AND ROWNUM =1") > 1) {
			int rownum = retValue.indexOf("AND ROWNUM =1");
			if (retValue.substring(0, rownum).contains("WHERE")) {

				retValue = Util.replace(retValue, "AND ROWNUM =1", " LIMIT 1");
				return convert + retValue;
			} else {
				retValue = Util.replace(retValue, "AND ROWNUM =1", "");
				return convert + retValue + " LIMIT 1";
			}
		} else if (retValue.indexOf("ROWNUM=1") > 1) {
			int rownum = retValue.indexOf("ROWNUM=1");
			//System.out.println("retValue" + retValue);
			if (retValue.substring(0, rownum).contains("WHERE")) {
				retValue = Util.replace(retValue, "ROWNUM=1 ", " LIMIT 1");
				return convert + retValue;
			} else {
				retValue = Util.replace(retValue, "ROWNUM=1", "");
				return convert + retValue + " LIMIT 1";
			}
		}
		// log.info("RowNum==> " + retValue);
		return convert + retValue;

		//
		// log.info("RowNum==> " + retValue);
		// return retValue;
		// end e-evolution PostgreSQL
	} // convertRowNum
	*/

	/***************************************************************************
	 * Converts Update.
	 *
	 * <pre>
	 *        UPDATE C_Order i SET
	 *         =&gt; UPDATE C_Order SET
	 * </pre>
	 *
	 * @param sqlStatement
	 * @return converted statement
	 */

	private String convertUpdate(String sqlStatement)
	{
		String targetTable = null;
		String targetAlias = null;

		String sqlUpper = sqlStatement.toUpperCase();
		StringBuffer token = new StringBuffer();
		String previousToken = null;
		int charIndex = 0;
		int sqlLength = sqlUpper.length();
		int cnt = 0;
		boolean isUpdate = false;

		//get target table and alias
		while (charIndex < sqlLength)
		{
			char c = sqlStatement.charAt(charIndex);
			if (Character.isWhitespace(c))
			{
				if (token.length() > 0)
				{
					cnt++;
					if (cnt == 1)
						isUpdate = "UPDATE".equalsIgnoreCase(token.toString());
					else if (cnt == 2)
						targetTable = token.toString();
					else if (cnt == 3)
					{
						targetAlias = token.toString().trim();
						if ("SET".equalsIgnoreCase(targetAlias)) //no alias
							targetAlias = targetTable;
					}
					previousToken = token.toString();
					token = new StringBuffer();
				}
			}
			else
			{
				if ("SET".equalsIgnoreCase(previousToken))
					break;
				else
					token.append(c);
			}
			charIndex++;
		}

		if (isUpdate && targetTable != null && sqlUpper.charAt(charIndex) == '(')
		{
			int updateFieldsBegin = charIndex;
			String updateFields = null;

			String select = "";

			//get the sub query
			String beforePreviousToken = null;
			previousToken = null;
			token = new StringBuffer();
			while (charIndex < sqlLength)
			{
				char c = sqlUpper.charAt(charIndex);
				if (Character.isWhitespace(c))
				{
					if (token.length() > 0)
					{
						String currentToken = token.toString();
						if ("(".equals(currentToken) || (currentToken != null && currentToken.startsWith("(")))
						{
							if ((")".equals(beforePreviousToken) ||
									(beforePreviousToken != null && beforePreviousToken.endsWith(")"))) &&
									"=".equals(previousToken))
							{
								select = sqlStatement.substring(charIndex - currentToken.length());
								updateFields = sqlStatement.substring(updateFieldsBegin, charIndex);
								updateFields = updateFields.substring(0, updateFields.lastIndexOf(')'));
								break;
							}
							else if (")=".equals(previousToken))
							{
								select = sqlStatement.substring(charIndex - currentToken.length());
								updateFields = sqlStatement.substring(updateFieldsBegin, charIndex);
								updateFields = updateFields.substring(0, updateFields.lastIndexOf(')'));
								break;
							}
							else if (previousToken != null && previousToken.endsWith(")="))
							{
								select = sqlStatement.substring(charIndex - currentToken.length());
								updateFields = sqlStatement.substring(updateFieldsBegin, charIndex);
								updateFields = updateFields.substring(0, updateFields.lastIndexOf(')'));
								break;
							}

						}
						if (")=(".equals(currentToken))
						{
							select = sqlStatement.substring(charIndex - 1);
							updateFields = sqlStatement.substring(updateFieldsBegin, charIndex);
							updateFields = updateFields.substring(0, updateFields.lastIndexOf(')'));
							break;
						}
						else if (currentToken.endsWith(")=(SELECT"))
						{
							select = sqlStatement.substring(charIndex - 7);
							updateFields = sqlStatement.substring(updateFieldsBegin, charIndex);
							updateFields = updateFields.substring(0, updateFields.lastIndexOf(')'));
							break;
						}
						else if ("=(".equals(currentToken) || (currentToken != null && currentToken.startsWith("=(")))
						{
							if (")".equals(previousToken) || (previousToken != null && previousToken.endsWith(")")))
							{
								select = sqlStatement.substring(charIndex - currentToken.length());
								updateFields = sqlStatement.substring(updateFieldsBegin, charIndex);
								updateFields = updateFields.substring(0, updateFields.lastIndexOf(')'));
								break;
							}
						}
						beforePreviousToken = previousToken;
						previousToken = token.toString();
						token = new StringBuffer();
					}
				}
				else
				{
					token.append(c);
				}
				charIndex++;
			}
			if (updateFields != null && updateFields.startsWith("("))
				updateFields = updateFields.substring(1);

			int subQueryEnd = 0;
			int subQueryStart = select.indexOf('(');
			String subWhere = null;
			int open = -1;
			for (int i = subQueryStart; i < select.length(); i++)
			{
				char c = select.charAt(i);
				if (c == '(')
					open++;

				if (c == ')')
					open--;

				if (open == -1)
				{
					subQueryEnd = i + 1;
					break;
				}
			}

			String mainWhere = "";
			String otherUpdateFields = "";
			//get update where clause
			token = new StringBuffer();
			for (int i = subQueryEnd; i < select.length(); i++)
			{
				char c = select.charAt(i);
				if (Character.isWhitespace(c))
				{
					if (token.length() > 0)
					{
						if ("WHERE".equalsIgnoreCase(token.toString()))
						{
							otherUpdateFields = select.substring(subQueryEnd, i - 5).trim();
							mainWhere = select.substring(i + 1);
							break;
						}
						token = new StringBuffer();
					}
				}
				else
				{
					token.append(c);
				}
			}

			String subQuery = select.substring(subQueryStart, subQueryEnd);

			//get join table and alias
			String joinTable = null;
			String joinAlias = null;
			token = new StringBuffer();
			previousToken = null;
			int joinFieldsBegin = 0;
			String joinFields = null;
			String joinFromClause = null;
			int joinFromClauseStart = 0;
			open = -1;
			for (int i = 0; i < subQuery.length(); i++)
			{
				char c = subQuery.charAt(i);
				if (Character.isWhitespace(c))
				{
					if (token.length() > 0 && open < 0)
					{
						if ("FROM".equalsIgnoreCase(previousToken))
						{
							joinTable = token.toString();
						}
						if ("WHERE".equalsIgnoreCase(token.toString()))
						{
							subWhere = subQuery.substring(i + 1, subQuery.length() - 1);
							joinFromClause = subQuery.substring(joinFromClauseStart, i - 5).trim();
							break;
						}
						if ("FROM".equalsIgnoreCase(token.toString()))
						{
							joinFields = subQuery.substring(joinFieldsBegin, i - 4);
							joinFromClauseStart = i;
						}
						if (previousToken != null && previousToken.equals(joinTable))
						{
							joinAlias = token.toString();
						}
						previousToken = token.toString();
						token = new StringBuffer();
					}
				}
				else
				{
					if (joinFieldsBegin == 0)
					{
						if (token.length() == 0 &&
								("SELECT".equalsIgnoreCase(previousToken) ||
										(previousToken != null && previousToken.toUpperCase().endsWith("SELECT"))))
							joinFieldsBegin = i;
					}
					else if (c == '(')
						open++;
					else if (c == ')')
						open--;
					token.append(c);
				}
			}
			if (joinFromClause == null)
				joinFromClause = subQuery.substring(joinFromClauseStart).trim();
			if (joinAlias == null)
				joinAlias = joinTable;

			//construct update clause
			StringBuffer Update = new StringBuffer("UPDATE ");
			Update.append(targetTable);
			if (!targetAlias.equals(targetTable))
				Update.append(" " + targetAlias);

			Update.append(" SET ");

			int f = updateFields.length();
			int fj = joinFields.length();
			String updateField = null;
			String joinField = null;

			boolean useSubQuery = false;
			if (useAggregateFunction(joinFields))
				useSubQuery = true;

			while (f > 0)
			{
				f = StringUtils.findIndexOf(updateFields, ',');
				if (f < 0)
				{
					updateField = updateFields;
					joinField = joinFields.trim();
					if (joinField.indexOf('.') < 0 && isIdentifier(joinField))
					{
						joinField = joinAlias + "." + joinField;
					}

					Update.append(updateField.trim());
					Update.append("=");
					if (useSubQuery)
					{
						Update.append("( SELECT ");
						Update.append(joinField);
						Update.append(" FROM ");
						Update.append(joinFromClause);
						Update.append(" WHERE ");
						Update.append(subWhere.trim());
						Update.append(" ) ");
						Update.append(otherUpdateFields);
						if (mainWhere != null)
						{
							Update.append(" WHERE ");
							Update.append(mainWhere);
						}
					}
					else
					{
						Update.append(joinField);
						Update.append(otherUpdateFields);
						Update.append(" FROM ");
						Update.append(joinFromClause);
						Update.append(" WHERE ");
						subWhere = addAliasToIdentifier(subWhere, joinAlias);
						Update.append(subWhere.trim());

						if (mainWhere != null)
							mainWhere = " AND " + mainWhere;

						else
							mainWhere = "";

						mainWhere = addAliasToIdentifier(mainWhere, targetAlias);
						Update.append(mainWhere);
					}
				}
				else
				{

					updateField = updateFields.substring(0, f);
					fj = StringUtils.findIndexOf(joinFields, ',');
					// fieldsjoin.indexOf(',');

					joinField = fj > 0 ? joinFields.substring(0, fj).trim() : joinFields.trim();
					if (joinField.indexOf('.') < 0 && isIdentifier(joinField))
					{
						joinField = joinAlias + "." + joinField;
					}
					Update.append(updateField.trim());
					Update.append("=");
					if (useSubQuery)
					{
						Update.append("( SELECT ");
						Update.append(joinField);
						Update.append(" FROM ");
						Update.append(joinFromClause);
						Update.append(" WHERE ");
						Update.append(subWhere.trim());
						Update.append(" ) ");
					}
					else
					{
						Update.append(joinField);
					}
					Update.append(",");
					joinFields = joinFields.substring(fj + 1);
				}

				updateFields = updateFields.substring(f + 1);

				// System.out.println("Update" + Update);
			}

			return Update.toString();

		}
		// System.out.println("Convert Update:"+sqlUpdate);
		return sqlStatement;

	} // convertDecode

	/**
	 * Check if one of the field is using standard sql aggregate function
	 *
	 * @param fields
	 * @return boolean
	 */
	private boolean useAggregateFunction(String fields)
	{
		String fieldsUpper = fields.toUpperCase();
		int size = fieldsUpper.length();
		StringBuffer buffer = new StringBuffer();
		String token = null;
		for (int i = 0; i < size; i++)
		{
			char ch = fieldsUpper.charAt(i);
			if (Character.isWhitespace(ch))
			{
				if (buffer.length() > 0)
				{
					token = buffer.toString();
					buffer = new StringBuffer();
				}
			}
			else
			{
				if (isOperator(ch))
				{
					if (buffer.length() > 0)
					{
						token = buffer.toString();
						buffer = new StringBuffer();
					}
					else
					{
						token = null;
					}
					if (ch == '(' && token != null)
					{
						if (token.equals("SUM") || token.equals("MAX") || token.equals("MIN")
								|| token.equals("COUNT") || token.equals("AVG"))
						{
							return true;
						}
					}
				}
				else
					buffer.append(ch);
			}
		}

		return false;
	}

	/**
	 * Add table alias to identifier in where clause
	 *
	 * @param where
	 * @param alias
	 * @return converted where clause
	 */
	private String addAliasToIdentifier(String where, String alias)
	{
		String sqlkey = "AND,OR,FROM,WHERE,JOIN,BY,GROUP,IN,INTO,SELECT,NOT,SET,UPDATE,DELETE,HAVING,IS,NULL,EXISTS,BETWEEN,LIKE,INNER,OUTER";

		StringTokenizer st = new StringTokenizer(where);
		String result = "";
		String token = "";
		int o = -1;
		while (true)
		{
			token = st.nextToken();
			String test = token.startsWith("(") ? token.substring(1) : token;
			if (sqlkey.indexOf(test) == -1)
			{

				token = token.trim();
				//skip subquery, non identifier and fully qualified identifier
				if (o != -1)
					result = result + " " + token;
				else
				{
					result = result + " ";
					StringBuffer t = new StringBuffer();
					for (int i = 0; i < token.length(); i++)
					{
						char c = token.charAt(i);
						if (isOperator(c))
						{
							if (t.length() > 0)
							{
								if (c == '(')
									result = result + t.toString();
								else if (isIdentifier(t.toString()) &&
										t.toString().indexOf('.') == -1)
									result = result + alias + "." + t.toString();
								else
									result = result + t.toString();
								t = new StringBuffer();
							}
							result = result + c;
						}
						else
						{
							t.append(c);
						}
					}
					if (t.length() > 0)
					{
						if ("SELECT".equalsIgnoreCase(t.toString().toUpperCase()))
						{
							o = 0;
							result = result + t.toString();
						}
						else if (isIdentifier(t.toString()) &&
								t.toString().indexOf('.') == -1)
							result = result + alias + "." + t.toString();
						else
							result = result + t.toString();
					}
				}

				if (o != -1)
				{
					for (int i = 0; i < token.length(); i++)
					{
						char c = token.charAt(i);
						if (c == '(')
							o++;
						if (c == ')')
							o--;
					}
				}

			}
			else
			{
				result = result + " " + token;
				if ("SELECT".equalsIgnoreCase(test))
				{
					o = 0;
				}
			}
			if (!st.hasMoreElements())
				break;
		}
		return result;
	}

	/**
	 * Check if token is a valid sql identifier
	 *
	 * @param token
	 * @return True if token is a valid sql identifier, false otherwise
	 */
	private boolean isIdentifier(String token)
	{
		int size = token.length();
		for (int i = 0; i < size; i++)
		{
			char c = token.charAt(i);
			if (isOperator(c))
				return false;
		}
		if (token.startsWith("'") && token.endsWith("'"))
			return false;
		else
		{
			try
			{
				new BigDecimal(token);
				return false;
			}
			catch (NumberFormatException e)
			{
			}
		}

		if (isSQLFunctions(token))
			return false;

		return true;
	}

	private boolean isSQLFunctions(String token)
	{
		if (token.equalsIgnoreCase("current_timestamp"))
			return true;
		else if (token.equalsIgnoreCase("current_time"))
			return true;
		else if (token.equalsIgnoreCase("current_date"))
			return true;
		else if (token.equalsIgnoreCase("localtime"))
			return true;
		else if (token.equalsIgnoreCase("localtimestamp"))
			return true;
		return false;
	}

	// begin vpj-cd e-evolution 08/02/2005

	/***************************************************************************
	 * convertAlias - for compatibility with 8.1
	 *
	 * @param sqlStatement
	 * @return converted statementf
	 */
	private String convertAlias(String sqlStatement)
	{
		String[] tokens = sqlStatement.split("\\s");
		String table = null;
		String alias = null;
		if ("UPDATE".equalsIgnoreCase(tokens[0]))
		{
			if ("SET".equalsIgnoreCase(tokens[2]))
				return sqlStatement;
			table = tokens[1];
			alias = tokens[2];
		}
		else if ("INSERT".equalsIgnoreCase(tokens[0]))
		{
			if ("VALUES".equalsIgnoreCase(tokens[3]) ||
					"SELECT".equalsIgnoreCase(tokens[3]))
				return sqlStatement;
			if (tokens[2].indexOf('(') > 0)
				return sqlStatement;
			else if ((tokens[3].indexOf('(') < 0) ||
					tokens[3].indexOf('(') > 0)
			{
				table = tokens[2];
				alias = tokens[3];
			}
			else
			{
				return sqlStatement;
			}
		}
		else if ("DELETE".equalsIgnoreCase(tokens[0]))
		{
			if (tokens.length < 4)
				return sqlStatement;
			if ("WHERE".equalsIgnoreCase(tokens[3]))
				return sqlStatement;
			table = tokens[2];
			alias = tokens[3];
		}
		if (table != null && alias != null)
		{
			if (alias.indexOf('(') > 0)
				alias = alias.substring(0, alias.indexOf('('));
			String converted = sqlStatement.replaceFirst("\\s" + alias + "\\s", " ");
			converted = converted.replaceAll("\\b" + alias + "\\.", table + ".");
			converted = converted.replaceAll("[+]" + alias + "\\.", "+" + table + ".");
			converted = converted.replaceAll("[-]" + alias + "\\.", "-" + table + ".");
			converted = converted.replaceAll("[*]" + alias + "\\.", "*" + table + ".");
			converted = converted.replaceAll("[/]" + alias + "\\.", "/" + table + ".");
			converted = converted.replaceAll("[%]" + alias + "\\.", "%" + table + ".");
			converted = converted.replaceAll("[<]" + alias + "\\.", "<" + table + ".");
			converted = converted.replaceAll("[>]" + alias + "\\.", ">" + table + ".");
			converted = converted.replaceAll("[=]" + alias + "\\.", "=" + table + ".");
			converted = converted.replaceAll("[|]" + alias + "\\.", "|" + table + ".");
			converted = converted.replaceAll("[(]" + alias + "\\.", "(" + table + ".");
			converted = converted.replaceAll("[)]" + alias + "\\.", ")" + table + ".");
			return converted;
		}
		else
		{
			return sqlStatement;
		}
	} //
	// end vpj-cd e-evolution 02/24/2005 PostgreSQL

	// begin vpj-cd 08/02/2005
	// ALTER TABLE AD_FieldGroup MODIFY IsTab CHAR(1) DEFAULT N;
	// ALTER TABLE AD_FieldGroup ALTER COLUMN IsTab TYPE CHAR(1); ALTER TABLE
	// AD_FieldGroup ALTER COLUMN SET DEFAULT 'N';
	private String convertDDL(String sqlStatement)
	{
		if (sqlStatement.toUpperCase().indexOf("ALTER TABLE ") == 0)
		{
			String action = null;
			int begin_col = -1;
			if (sqlStatement.toUpperCase().indexOf(" MODIFY ") > 0)
			{
				action = " MODIFY ";
				begin_col = sqlStatement.toUpperCase().indexOf(" MODIFY ")
						+ action.length();
			}
			else if (sqlStatement.toUpperCase().indexOf(" ADD ") > 0)
			{
				if (sqlStatement.toUpperCase().indexOf(" ADD CONSTRAINT ") < 0 &&
						sqlStatement.toUpperCase().indexOf(" ADD FOREIGN KEY ") < 0)
				{
					action = " ADD ";
					begin_col = sqlStatement.toUpperCase().indexOf(" ADD ")
							+ action.length();
				}
			}

			// System.out.println( "MODIFY :" +
			// sqlStatement.toUpperCase().indexOf(" MODIFY "));
			// System.out.println( "ADD :" +
			// sqlStatement.toUpperCase().indexOf(" ADD "));
			// System.out.println( "begincolumn:" + sqlStatement +
			// "begincolumn:" + begin_col );

			if (begin_col < 0)
				return sqlStatement;

			int end_col = 0;
			int begin_default = -1;

			String column = null;
			String type = null;
			String defaultvalue = null;
			String nullclause = null;
			String DDL = null;

			if (begin_col != -1)
			{
				column = sqlStatement.substring(begin_col);
				end_col = begin_col + column.indexOf(' ');
				column = sqlStatement.substring(begin_col, end_col);
				// System.out.println(" column:" + column + " begincolumn:" +
				// begin_col + "en column:" + end_col );
				// System.out.println(" type " + sqlStatement.substring(end_col
				// + 1));
				String rest = sqlStatement.substring(end_col + 1);

				if (action.equals(" ADD "))
				{
					if (rest.toUpperCase().indexOf(" DEFAULT ") != -1)
					{
						String beforeDefault = rest.substring(0, rest.toUpperCase().indexOf(" DEFAULT "));
						begin_default = rest.toUpperCase().indexOf(
								" DEFAULT ") + 9;
						defaultvalue = rest.substring(begin_default);
						int nextspace = defaultvalue.indexOf(' ');
						if (nextspace > -1)
						{
							rest = defaultvalue.substring(nextspace);
							defaultvalue = defaultvalue.substring(0, defaultvalue.indexOf(' '));
						}
						else
						{
							rest = "";
						}
						if (defaultvalue.equalsIgnoreCase("NULL"))
						{
							DDL = sqlStatement.substring(0, begin_col
									- action.length())
									+ " ADD COLUMN "
									+ column
									+ " " + beforeDefault.trim()
									+ " DEFAULT "
									+ defaultvalue.trim() + " " + rest.trim();
						}
						else
						{
							// Check if default value is already quoted, no need to double quote
							if (defaultvalue.startsWith("'") && defaultvalue.endsWith("'"))
							{
								DDL = sqlStatement.substring(0, begin_col
										- action.length())
										+ " ADD COLUMN "
										+ column
										+ " " + beforeDefault.trim()
										+ " DEFAULT "
										+ defaultvalue.trim() + " " + rest.trim();
							}
							else
							{
								DDL = sqlStatement.substring(0, begin_col
										- action.length())
										+ " ADD COLUMN "
										+ column
										+ " " + beforeDefault.trim()
										+ " DEFAULT '"
										+ defaultvalue.trim() + "' " + rest.trim();
							}
						}
					}
					else
					{
						DDL = sqlStatement
								.substring(0, begin_col - action.length())
								+ action + "COLUMN " + column + " " + rest.trim();
					}
				}
				else if (action.equals(" MODIFY "))
				{
					rest = rest.trim();
					if (rest.toUpperCase().startsWith("NOT ") || rest.toUpperCase().startsWith("NULL ")
							|| rest.toUpperCase().equals("NULL") || rest.toUpperCase().equals("NOT NULL"))
					{
						type = null;
					}
					else
					{
						int typeEnd = rest.indexOf(' ');
						type = typeEnd > 0 ? rest.substring(0, typeEnd).trim() : rest;
						rest = typeEnd > 0 ? rest.substring(typeEnd) : "";
					}

					final int idxDefault = rest.toUpperCase().indexOf(" DEFAULT ");
					if (idxDefault != -1)
					{
						if (idxDefault > 0)
						{
							// metas: 03306: It seems that the data type contains more then one words
							type += " " + rest.substring(0, idxDefault).trim();
							rest = rest.substring(idxDefault);
						}
						begin_default = rest.toUpperCase().indexOf(
								" DEFAULT ") + 9;
						defaultvalue = rest.substring(begin_default);
						int nextspace = defaultvalue.indexOf(' ');
						if (nextspace > -1)
						{
							rest = defaultvalue.substring(nextspace);
							defaultvalue = defaultvalue.substring(0, defaultvalue.indexOf(' '));
						}
						else
						{
							rest = "";
						}
						// Check if default value is already quoted
						defaultvalue = defaultvalue.trim();
						if (defaultvalue.startsWith("'") && defaultvalue.endsWith("'"))
							defaultvalue = defaultvalue.substring(1, defaultvalue.length() - 1);

						if (rest != null && rest.toUpperCase().indexOf("NOT NULL") >= 0)
							nullclause = "NOT NULL";
						else if (rest != null && rest.toUpperCase().indexOf("NULL") >= 0)
							nullclause = "NULL";

						// return DDL;
					}
					else if (rest != null && rest.toUpperCase().indexOf("NOT NULL") >= 0)
					{
						// metas: 03306: It seems that the data type contains more then one words
						final int idx = rest.toUpperCase().indexOf("NOT NULL");
						if (idx > 0)
						{
							type += " " + rest.substring(0, idx).trim();
							rest = rest.substring(idx);
						}
						rest = rest.substring("NOT NULL".length());

						nullclause = "NOT NULL";

					}
					else if (rest != null && rest.toUpperCase().indexOf("NULL") >= 0)
					{
						// metas: 03306: It seems that the data type contains more then one words
						final int idx = rest.toUpperCase().indexOf("NULL");
						if (idx > 0)
						{
							type += " " + rest.substring(0, idx).trim();
							rest = rest.substring(idx);
						}
						rest = rest.substring("NULL".length());

						nullclause = "NULL";
					}

					// metas: 03306: It seems that the data type contains more then one words
					if (rest.length() > 0)
					{
						type += " " + rest.trim();
						rest = "";
					}
					type = type == null ? null : type.trim();

					DDL = "INSERT INTO t_alter_column values('";
					String tableName = sqlStatement.substring(0, begin_col - action.length());
					tableName = tableName.toUpperCase().replaceAll("ALTER TABLE", "");
					tableName = tableName.trim().toLowerCase();
					DDL = DDL + tableName + "','" + column + "',";
					if (type != null)
						DDL = DDL + "'" + type + "',";
					else
						DDL = DDL + "null,";
					if (nullclause != null)
						DDL = DDL + "'" + nullclause + "',";
					else
						DDL = DDL + "null,";
					if (defaultvalue != null)
						DDL = DDL + "'" + defaultvalue + "'";
					else
						DDL = DDL + "null";
					DDL = DDL + ")";
				}
				return DDL;
			}
		}

		return sqlStatement;
	}

	@Override
	public String markNative(final String keyword)
	{
		if (keyword == null)
		{
			throw new IllegalArgumentException("keyword is null");
		}
		return NATIVE_MARKER + keyword;
	}
} // Convert
