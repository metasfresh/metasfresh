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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.util.StringUtils;

/***
 * Convert from oracle syntax to sql 92 standard
 * @author Low Heng Sin
 *
 */
public abstract class Convert_SQL92 extends Convert {

	/**	Logger	*/
	private static Logger	log	= LogManager.getLogger(Convert_SQL92.class);

	/**************************************************************************
	 *  Convert Outer Join.
	 *  Converting joins can ve very complex when multiple tables/keys are involved.
	 *  The main scenarios supported are two tables with multiple key columns
	 *  and multiple tables with single key columns.
	 *  <pre>
	 *      SELECT a.Col1, b.Col2 FROM tableA a, tableB b WHERE a.ID=b.ID(+)
	 *      => SELECT a.Col1, b.Col2 FROM tableA a LEFT OUTER JOIN tableB b ON (a.ID=b.ID)
	 *
	 *      SELECT a.Col1, b.Col2 FROM tableA a, tableB b WHERE a.ID(+)=b.ID
	 *      => SELECT a.Col1, b.Col2 FROM tableA a RIGHT OUTER JOIN tableB b ON (a.ID=b.ID)
	 *  Assumptions:
	 *  - No outer joins in sub queries (ignores sub-queries)
	 *  - OR condition ignored (not sure what to do, should not happen)
	 *  Limitations:
	 *  - Parameters for outer joins must be first - as sequence of parameters changes
	 *  </pre>
	 *  @param sqlStatement
	 *  @return converted statement
	 */
	protected String convertOuterJoin (String sqlStatement)
	{
		boolean trace = false;
		//
		int fromIndex = StringUtils.findIndexOf (sqlStatement.toUpperCase(), " FROM ");
		int whereIndex = StringUtils.findIndexOf(sqlStatement.toUpperCase(), " WHERE ");
		//begin vpj-cd e-evolution 03/14/2005 PostgreSQL
		//int endWhereIndex = Util.findIndexOf(sqlStatement.toUpperCase(), " GRPUP BY ");
		int endWhereIndex = StringUtils.findIndexOf(sqlStatement.toUpperCase(), " GROUP BY ");
        //end vpj-cd e-evolution 03/14/2005	PostgreSQL
		if (endWhereIndex == -1)
			endWhereIndex = StringUtils.findIndexOf(sqlStatement.toUpperCase(), " ORDER BY ");
		if (endWhereIndex == -1)
			endWhereIndex = sqlStatement.length();
		//
		if (trace)
		{
			log.info("OuterJoin<== " + sqlStatement);
		//	log.info("From=" + fromIndex + ", Where=" + whereIndex + ", End=" + endWhereIndex + ", Length=" + sqlStatement.length());
		}
		//
		String selectPart = sqlStatement.substring(0, fromIndex);
		String fromPart = sqlStatement.substring(fromIndex, whereIndex);
		String wherePart = sqlStatement.substring(whereIndex, endWhereIndex);
		String rest = sqlStatement.substring(endWhereIndex);

		//  find/remove all (+) from WHERE clase ------------------------------
		String newWherePart = wherePart;
		ArrayList<String> joins = new ArrayList<String>();
		int pos = newWherePart.indexOf("(+)");
		while (pos != -1)
		{
			//  find starting point
			int start = newWherePart.lastIndexOf(" AND ", pos);
			int startOffset = 5;
			if (start == -1)
			{
				start = newWherePart.lastIndexOf(" OR ", pos);
				startOffset = 4;
			}
			if (start == -1)
			{
				start = newWherePart.lastIndexOf("WHERE ", pos);
				startOffset = 6;
			}
			if (start == -1)
			{
				String error = "Start point not found in clause " + wherePart;
				log.error(error);
				m_conversionError = error;
				return sqlStatement;
			}
			//  find end point
			int end = newWherePart.indexOf(" AND ", pos);
			if (end == -1)
				end = newWherePart.indexOf(" OR ", pos);
			if (end == -1)
				end = newWherePart.length();
		//	log.info("<= " + newWherePart + " - Start=" + start + "+" + startOffset + ", End=" + end);

			//  extract condition
			String condition = newWherePart.substring(start+startOffset, end);
			joins.add(condition);
			if (trace)
				log.info("->" + condition);
			//  new WHERE clause
			newWherePart = newWherePart.substring(0, start) + newWherePart.substring(end);
		//	log.info("=> " + newWherePart);
			//
			pos = newWherePart.indexOf("(+)");
		}
		//  correct beginning
		newWherePart = newWherePart.trim();
		if (newWherePart.startsWith("AND "))
			newWherePart = "WHERE" + newWherePart.substring(3);
		else if (newWherePart.startsWith("OR "))
			newWherePart = "WHERE" + newWherePart.substring(2);
		if (trace)
			log.info("=> " + newWherePart);

		//  Correct FROM clause -----------------------------------------------
		//  Disassemble FROM
		String[] fromParts = fromPart.trim().substring(4).split(",");
		HashMap<String,String> fromAlias = new HashMap<String,String>();      //  tables to be processed
		HashMap<String,String> fromLookup = new HashMap<String,String>();     //  used tabled
		for (int i = 0; i < fromParts.length; i++)
		{
			String entry = fromParts[i].trim();
			String alias = entry;   //  no alias
			String table = entry;
			int aPos = entry.lastIndexOf(' ');
			if (aPos != -1)
			{
				alias = entry.substring(aPos+1);
				table = entry.substring(0, entry.indexOf(' ')); // may have AS
			}
			fromAlias.put(alias, table);
			fromLookup.put(alias, table);
			if (trace)
				log.info("Alias=" + alias + ", Table=" + table);
		}

		/** Single column
			SELECT t.TableName, w.Name FROM AD_Table t, AD_Window w
			WHERE t.AD_Window_ID=w.AD_Window_ID(+)
			--	275 rows
			SELECT t.TableName, w.Name FROM AD_Table t
			LEFT OUTER JOIN AD_Window w ON (t.AD_Window_ID=w.AD_Window_ID)

			SELECT t.TableName, w.Name FROM AD_Table t, AD_Window w
			WHERE t.AD_Window_ID(+)=w.AD_Window_ID
			--	239 rows
			SELECT t.TableName, w.Name FROM AD_Table t
			RIGHT OUTER JOIN AD_Window w ON (t.AD_Window_ID=w.AD_Window_ID)

		**  Multiple columns
			SELECT tn.Node_ID,tn.Parent_ID,tn.SeqNo,tb.IsActive
			FROM AD_TreeNode tn, AD_TreeBar tb
			WHERE tn.AD_Tree_ID=tb.AD_Tree_ID(+) AND tn.Node_ID=tb.Node_ID(+)
			  AND tn.AD_Tree_ID=10
			--  235 rows
			SELECT	tn.Node_ID,tn.Parent_ID,tn.SeqNo,tb.IsActive
			FROM AD_TreeNode tn LEFT OUTER JOIN AD_TreeBar tb
			  ON (tn.Node_ID=tb.Node_ID AND tn.AD_Tree_ID=tb.AD_Tree_ID AND tb.AD_User_ID=0)
			WHERE tn.AD_Tree_ID=10

			SELECT tn.Node_ID,tn.Parent_ID,tn.SeqNo,tb.IsActive
			FROM AD_TreeNode tn, AD_TreeBar tb
			WHERE tn.AD_Tree_ID=tb.AD_Tree_ID(+) AND tn.Node_ID=tb.Node_ID(+)
			 AND tn.AD_Tree_ID=10 AND tb.AD_User_ID(+)=0
			--  214 rows
			SELECT tn.Node_ID,tn.Parent_ID,tn.SeqNo,tb.IsActive
			FROM AD_TreeNode tn LEFT OUTER JOIN AD_TreeBar tb
			  ON (tn.Node_ID=tb.Node_ID AND tn.AD_Tree_ID=tb.AD_Tree_ID AND tb.AD_User_ID=0)
			WHERE tn.AD_Tree_ID=10

		 */
		StringBuffer newFrom = new StringBuffer ();
		for (int i = 0; i < joins.size(); i++)
		{
			Join first = new Join (joins.get(i));
			first.setMainTable(fromLookup.get(first.getMainAlias()));
			fromAlias.remove(first.getMainAlias());     //  remove from list
			first.setJoinTable(fromLookup.get(first.getJoinAlias()));
			fromAlias.remove(first.getJoinAlias());     //  remove from list
			if (trace)
				log.info("-First: " + first);
			//
			if (newFrom.length() == 0)
				newFrom.append(" FROM ");
			else
				newFrom.append(", ");
			newFrom.append(first.getMainTable()).append(" ").append(first.getMainAlias())
				.append(first.isLeft() ? " LEFT" : " RIGHT").append(" OUTER JOIN ")
				.append(first.getJoinTable()).append(" ").append(first.getJoinAlias())
				.append(" ON (").append(first.getCondition());
			//  keep it open - check for other key comparisons
			for (int j = i+1; j < joins.size(); j++)
			{
				Join second = new Join (joins.get(j));
				second.setMainTable(fromLookup.get(second.getMainAlias()));
				second.setJoinTable(fromLookup.get(second.getJoinAlias()));
				if ((first.getMainTable().equals(second.getMainTable())
						&& first.getJoinTable().equals(second.getJoinTable()))
					|| second.isConditionOf(first) )
				{
					if (trace)
						log.info("-Second/key: " + second);
					newFrom.append(" AND ").append(second.getCondition());
					joins.remove(j);                        //  remove from join list
					fromAlias.remove(first.getJoinAlias()); //  remove from table list
					//----
					for (int k = i+1; k < joins.size(); k++)
					{
						Join third = new Join (joins.get(k));
						third.setMainTable(fromLookup.get(third.getMainAlias()));
						third.setJoinTable(fromLookup.get(third.getJoinAlias()));
						if (third.isConditionOf(second))
						{
							if (trace)
								log.info("-Third/key: " + third);
							newFrom.append(" AND ").append(third.getCondition());
							joins.remove(k);                            //  remove from join list
							fromAlias.remove(third.getJoinAlias());     //  remove from table list
						}
						else if (trace)
							log.info("-Third/key-skip: " + third);
					}
				}
				else if (trace)
					log.info("-Second/key-skip: " + second);
			}
			newFrom.append(")");    //  close ON
			//  check dependency on first table
			for (int j = i+1; j < joins.size(); j++)
			{
				Join second = new Join (joins.get(j));
				second.setMainTable(fromLookup.get(second.getMainAlias()));
				second.setJoinTable(fromLookup.get(second.getJoinAlias()));
				if (first.getMainTable().equals(second.getMainTable()))
				{
					if (trace)
						log.info("-Second/dep: " + second);
					//   FROM (AD_Field f LEFT OUTER JOIN AD_Column c ON (f.AD_Column_ID = c.AD_Column_ID))
					//  LEFT OUTER JOIN AD_FieldGroup fg ON (f.AD_FieldGroup_ID = fg.AD_FieldGroup_ID),
					newFrom.insert(6, '(');     //  _FROM ...
					newFrom.append(')');        //  add parantesis on previous relation
					//
					newFrom.append(second.isLeft() ? " LEFT" : " RIGHT").append(" OUTER JOIN ")
						.append(second.getJoinTable()).append(" ").append(second.getJoinAlias())
						.append(" ON (").append(second.getCondition());
					joins.remove(j);                            //  remove from join list
					fromAlias.remove(second.getJoinAlias());    //  remove from table list
					//  additional join colums would come here
					newFrom.append(")");    //  close ON
					//----
					for (int k = i+1; k < joins.size(); k++)
					{
						Join third = new Join (joins.get(k));
						third.setMainTable(fromLookup.get(third.getMainAlias()));
						third.setJoinTable(fromLookup.get(third.getJoinAlias()));
						if (second.getJoinTable().equals(third.getMainTable()))
						{
							if (trace)
								log.info("-Third-dep: " + third);
							//   FROM ((C_BPartner p LEFT OUTER JOIN AD_User c ON (p.C_BPartner_ID=c.C_BPartner_ID))
							//  LEFT OUTER JOIN C_BPartner_Location l ON (p.C_BPartner_ID=l.C_BPartner_ID))
							//  LEFT OUTER JOIN C_Location a ON (l.C_Location_ID=a.C_Location_ID)
							newFrom.insert(6, '(');     //  _FROM ...
							newFrom.append(')');        //  add parantesis on previous relation
							//
							newFrom.append(third.isLeft() ? " LEFT" : " RIGHT").append(" OUTER JOIN ")
								.append(third.getJoinTable()).append(" ").append(third.getJoinAlias())
								.append(" ON (").append(third.getCondition());
							joins.remove(k);                            //  remove from join list
							fromAlias.remove(third.getJoinAlias());     //  remove from table list
							//  additional join colums would come here
							newFrom.append(")");    //  close ON
						}
						else if (trace)
							log.info("-Third-skip: " + third);
					}
				}
				else if (trace)
					log.info("-Second/dep-skip: " + second);
			}   //  dependency on first table
		}
		//  remaining Tables
		Iterator<String> it = fromAlias.keySet().iterator();
		while (it.hasNext())
		{
			Object alias = it.next();
			Object table = fromAlias.get(alias);
			newFrom.append(", ").append(table);
			if (!table.equals(alias))
				newFrom.append(" ").append(alias);
		}
		if (trace)
			log.info(newFrom.toString());
		//
		StringBuffer retValue = new StringBuffer (sqlStatement.length()+20);
		retValue.append(selectPart)
			.append(newFrom).append(" ")
			.append(newWherePart).append(rest);
		//
		if (trace)
			log.info("OuterJoin==> " + retValue.toString());
		return retValue.toString();
	}   //  convertOuterJoin

	/**************************************************************************
	 *  Converts Decode.
	 *  <pre>
	 *      DECODE (a, 1, 'one', 2, 'two', 'none')
	 *       => CASE WHEN a = 1 THEN 'one' WHEN a = 2 THEN 'two' ELSE 'none' END
	 *  </pre>
	 *  @param sqlStatement
	 *  @return converted statement
	 */
	protected String convertDecode(String sqlStatement, int fromIndex)
	{
	//	log.info("DECODE<== " + sqlStatement);
		String statement = sqlStatement;
		StringBuffer sb = new StringBuffer("CASE");

		int index = statement.toUpperCase().indexOf("DECODE", fromIndex);
		if (index <= 0) return sqlStatement;

		char previousChar = statement.charAt(index - 1);
		if (!(Character.isWhitespace(previousChar) || isOperator(previousChar)))
			return sqlStatement;

		String firstPart = statement.substring(0,index);

		//  find the opening (
		index = index + 6;
		while (index < statement.length()) {
			char c = statement.charAt(index);
			if (Character.isWhitespace(c)) {
				index++;
				continue;
			}
			if (c == '(') break;
			return sqlStatement;
		}

		statement = statement.substring(index+1);

		//  find the expression "a" - find first , ignoring ()
		index = StringUtils.findIndexOf (statement, ',');
		String expression = statement.substring(0, index).trim();
	//	log.info("Expression=" + expression);

		//  Pairs "1, 'one',"
		statement = statement.substring(index+1);
		index = StringUtils.findIndexOf (statement, ',');
		while (index != -1)
		{
			String first = statement.substring(0, index);
			char cc = statement.charAt(index);
			statement = statement.substring(index+1);
		//	log.info("First=" + first + ", Char=" + cc);
			//
			boolean error = false;
			if (cc == ',')
			{
				index = StringUtils.findIndexOf (statement, ',',')');
				if (index == -1)
					error = true;
				else
				{
					String second = statement.substring(0, index);
					sb.append(" WHEN ").append(expression).append("=").append(first.trim())
						.append(" THEN ").append(second.trim());
		//			log.info(">>" + sb.toString());
					statement = statement.substring(index+1);
					index = StringUtils.findIndexOf (statement, ',',')');
				}
			}
			else if (cc == ')')
			{
				sb.append(" ELSE ").append(first.trim()).append(" END");
		//		log.info(">>" + sb.toString());
				index = -1;
			}
			else
				error = true;
			if (error)
			{
				log.error("SQL=(" + sqlStatement
					+ ")\n====Result=(" + sb.toString()
					+ ")\n====Statement=(" + statement
					+ ")\n====First=(" + first
					+ ")\n====Index=" + index);
				m_conversionError = "Decode conversion error";
			}
		}
		sb.append(statement);
		sb.insert(0, firstPart);
	//	log.info("DECODE==> " + sb.toString());
		return sb.toString();
	}	//  convertDecode

	/***************************************************************************
	 * Converts Delete.
	 *
	 * <pre>
	 *        DELETE C_Order i WHERE
	 *         =&gt; DELETE FROM C_Order WHERE
	 * </pre>
	 *
	 * @param sqlStatement
	 * @return converted statement
	 */
	protected String convertDelete(String sqlStatement) {

		int index = sqlStatement.toUpperCase().indexOf("DELETE ");
		if (index < 7) {
			return "DELETE FROM " + sqlStatement.substring(index + 7);

		}

		return sqlStatement;
	} // convertDelete

	/**
	 * Is character a valid sql operator
	 * @param c
	 * @return boolean
	 */
	protected boolean isOperator(char c)
	{
		if ('=' == c)
			return true;
		else if ('<' == c)
			return true;
		else if ('>' == c)
			return true;
		else if ('|' == c)
			return true;
		else if ('(' == c)
			return true;
		else if (')' == c)
			return true;
		else if ('+' == c)
			return true;
		else if ('-' == c)
			return true;
		else if ('*' == c)
			return true;
		else if ('/' == c)
			return true;
		else if ('!' == c)
			return true;
		else if (',' == c)
			return true;
		else if ('?' == c)
			return true;
		else if ('#' == c)
			return true;
		else if ('@' == c)
			return true;
		else if ('~' == c)
			return true;
		else if ('&' == c)
			return true;
		else if ('^' == c)
			return true;
		else if ('!' == c)
			return true;
		else
			return false;
	}

}
