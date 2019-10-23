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


public final class ConvertMap_PostgreSQL
{
	/**
	 * Map of "Match Pattern" to "replacement string"
	 */
	private static final ConvertMap  s_pg = new ConvertMap();
	
	private ConvertMap_PostgreSQL()
	{
		super();
	}
	
	/**
	 *  Return Map for PostgreSQL
	 *  @return TreeMap with pattern as key and the replacement as value
	 */
	public static ConvertMap getConvertMap()
	{
		if (s_pg.isEmpty())
		{
			initConvertMap();
		}
		return s_pg;
	}   //  getConvertMap
	
	/**
	 *  PostgreSQL Init
	 */
	static private void initConvertMap()
	{
		//      Oracle Pattern                  Replacement

		//  Data Types
		s_pg.addDataType("NUMBER",                "NUMERIC");
		// metas: 03306: use timestamp without time zone (explicit)
		s_pg.addDataType("DATE",                  "TIMESTAMP WITHOUT TIME ZONE");
		s_pg.addDataType("VARCHAR2",              "VARCHAR");
		s_pg.addDataType("NVARCHAR2",             "VARCHAR");
		s_pg.addDataType("NCHAR",                 "CHAR");
        //begin vpj-cd e-evolution 03/11/2005 PostgreSQL
		s_pg.addDataType("BLOB",                  "BYTEA");                 //  BLOB not directly supported
		s_pg.addDataType("CLOB",                  "TEXT");                //  CLOB not directly supported
		s_pg.addPattern("\\bLIMIT\\b","\"limit\""); 
		s_pg.addPattern("\\bACTION\\b","\"action\""); 
		s_pg.addPattern("\\bold\\b","\"old\""); 
		s_pg.addPattern("\\bnew\\b","\"new\""); 
		//s_pg.addPattern("\\bBLOB\\b",                  "OID");                 //  BLOB not directly supported
		//s_pg.addPattern("\\bCLOB\\b",                  "OID");                //  CLOB not directly supported
        //end vpj-cd e-evolution 03/11/2005 PostgreSQL
		
		//  Storage
		s_pg.addPattern("\\bCACHE\\b",                 "");
		s_pg.addPattern("\\bUSING INDEX\\b",           "");
		s_pg.addPattern("\\bTABLESPACE\\s\\w+\\b",     "");
		s_pg.addPattern("\\bSTORAGE\\([\\w\\s]+\\)",   "");
		//
		s_pg.addPattern("\\bBITMAP INDEX\\b",          "INDEX");

		//  Functions
		s_pg.addPattern("\\bSYSDATE\\b",               "CURRENT_TIMESTAMP");   //  alternative: NOW()
        //begin vpj-cd e-evolution 03/11/2005 PostgreSQL		                                     
		s_pg.addPattern("\\bDUMP\\b",               "MD5"); 	
		s_pg.addPattern("END CASE",               "END");
		s_pg.addPattern("\\bgetDate\\b\\(\\)",               "CURRENT_TIMESTAMP");   //  alternative: NOW()
		//end vpj-cd e-evolution 03/11/2005 PostgreSQL
		s_pg.addPattern("\\bNVL\\b",                   "COALESCE");
		s_pg.addPattern("\\bTO_DATE\\b",               "TO_TIMESTAMP");
		//
		s_pg.addPattern("\\bDBMS_OUTPUT.PUT_LINE\\b",  "RAISE NOTICE");
		s_pg.addPattern("\\bTO_NCHAR\\b",              "");

		//  Temporary
		s_pg.addPattern("\\bGLOBAL TEMPORARY\\b",      "TEMPORARY");
		s_pg.addPattern("\\bON COMMIT DELETE ROWS\\b", "");
		s_pg.addPattern("\\bON COMMIT PRESERVE ROWS\\b",   "");

        //DDL
                
        // begin vpj-cd e-evolution 08/02/2005 PostgreSQL
		//s_pg.addPattern("\\bMODIFY\\b","ALTER COLUMN");						
        //s_pg.addPattern("\\bDEFAULT\\b","SET DEFAULT");
		// end vpj-cd e-evolution 08/02/2005 PostgreSQL
                
		//  DROP TABLE x CASCADE CONSTRAINTS
		s_pg.addPattern("\\bCASCADE CONSTRAINTS\\b",   "");

		//  Select
		s_pg.addPattern("\\sFROM\\s+DUAL\\b",          "");

		//  Statements
		s_pg.addPattern("\\bELSIF\\b",                 "ELSE IF");
		// begin vpj-cd e-evolution 03/11/2005 PostgreSQL
		s_pg.addPattern("\\bREC \\b",                 "AS REC ");				
		// end vpj-cd e-evolution 03/11/2005 PostgreSQL

		//  Sequences
		s_pg.addPattern("\\bSTART WITH\\b",            "START");
		s_pg.addPattern("\\bINCREMENT BY\\b",          "INCREMENT");
	}   //  initPostgreSQL
}
