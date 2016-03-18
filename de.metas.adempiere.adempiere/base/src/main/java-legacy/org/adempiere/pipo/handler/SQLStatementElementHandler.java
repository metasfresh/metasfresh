/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 Adempiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *
 * Copyright (C) 2005 Robert Klein. robeklein@hotmail.com
 * Contributor(s): Low Heng Sin hengsin@avantz.com
 *****************************************************************************/
package org.adempiere.pipo.handler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;

import javax.xml.transform.sax.TransformerHandler;

import org.adempiere.pipo.AbstractElementHandler;
import org.adempiere.pipo.Element;
import org.compiere.model.X_AD_Package_Exp_Detail;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class SQLStatementElementHandler extends AbstractElementHandler {

	@Override
	public void startElement(Properties ctx, Element element) throws SAXException {
		String elementValue = element.getElementValue();
		int AD_Backup_ID = -1;
		String Object_Status = null;

		log.info(elementValue);
		Attributes atts = element.attributes;
		String DBType = atts.getValue("DBType");
		String sql = atts.getValue("statement").trim();
		if (sql.endsWith(";"))
			sql = sql.substring(0, sql.length() - 1);
		PreparedStatement pstmt = DB.prepareStatement(sql, getTrxName(ctx));	    
		try {
			if(DBType.equals("ALL")) {
				int n = pstmt.executeUpdate();				
				log.info("Executed SQL Statement: "+ atts.getValue("statement"));
			}
			if (   DB.isPostgreSQL() 
					 && (   DBType.equals("Postgres")
						 || DBType.equals("PostgreSQL")  // backward compatibility with old packages developed by hand
						)
					 ) {
				// Avoid convert layer - command specific for postgresql
				//
				// pstmt = DB.prepareStatement(sql, null);					
				// pstmt.executeUpdate();
				//
				Connection m_con = DB.getConnectionRW();
				try {
					Statement stmt = m_con.createStatement();
					int n = stmt.executeUpdate (atts.getValue("statement"));
					log.info("Executed SQL Statement for PostgreSQL: "+ atts.getValue("statement"));
					// Postgres needs to commit DDL statements
					if (m_con != null && !m_con.getAutoCommit())
						m_con.commit();
					stmt.close();
				} finally {
					m_con.close();
				}
			}
			pstmt.close();
		} catch (Exception e)	{
			log.log(Level.SEVERE,"SQLSatement", e);
		}
	}

	@Override
	public void endElement(Properties ctx, Element element) throws SAXException {
	}

	@Override
	public void create(Properties ctx, TransformerHandler document)
			throws SAXException {
		String SQLStatement = Env.getContext(ctx, X_AD_Package_Exp_Detail.COLUMNNAME_SQLStatement);
		String DBType = Env.getContext(ctx, X_AD_Package_Exp_Detail.COLUMNNAME_DBType);
		AttributesImpl atts = new AttributesImpl();
		createSQLStatmentBinding(atts, SQLStatement, DBType);
		document.startElement("","","SQLStatement",atts);
		document.endElement("","","SQLStatement");
	}

	private AttributesImpl createSQLStatmentBinding( AttributesImpl atts, String SqlStatement, String DBType) 
	{
		atts.clear();
		atts.addAttribute("","","DBType","CDATA",DBType);
		atts.addAttribute("","","statement","CDATA",SqlStatement);
		return atts;
		
	}
}
