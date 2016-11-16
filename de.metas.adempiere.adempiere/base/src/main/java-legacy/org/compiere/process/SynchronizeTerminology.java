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
 *****************************************************************************/
package org.compiere.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.process.SvrProcess;

import org.compiere.model.M_Element;
import org.compiere.util.DB;

/**
 *	Synchronize Column with Database
 *	
 *  @author Marek Mosiewicz http://www.jotel.com.pl
 */
public class SynchronizeTerminology extends SvrProcess
{
	/**	Static Logger	*/
	private static Logger	s_log	= LogManager.getLogger(SynchronizeTerminology.class);
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
	}	//	prepare

	/**
	 * 	Process
	 *	@return message
	 *	@throws Exception
	 */
	@Override
	protected String doIt() throws Exception
	{
		//TODO Error handling
		String sql = null;
		try {
			int no;
			sql="SELECT DISTINCT ColumnName, Name, Description, Help, EntityType "
				+"FROM	AD_COLUMN c WHERE NOT EXISTS "
				+"(SELECT 1 FROM AD_ELEMENT e "
				+" WHERE UPPER(c.ColumnName)=UPPER(e.ColumnName))"; 
			PreparedStatement pstmt = DB.prepareStatement(sql, get_TrxName());
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next()){
				String columnName = rs.getString(1);
				String name = rs.getString(2);
				String desc = rs.getString(3);
				String help =rs.getString(4);
				String entityType=rs.getString(5);
				M_Element elem = new M_Element(getCtx(),columnName,entityType,get_TrxName());
				elem.setDescription(desc);
				elem.setHelp(help);
				elem.setPrintName(name);
				elem.save();
			}
			pstmt.close();
			rs.close();
			sql="SELECT DISTINCT ColumnName, Name, Description, Help, EntityType "
				+" FROM	AD_PROCESS_PARA p "
				+" WHERE NOT EXISTS "
				+" (SELECT 1 FROM AD_ELEMENT e "
				+" WHERE UPPER(p.ColumnName)=UPPER(e.ColumnName))";
			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery ();
			while (rs.next()){
				String columnName = rs.getString(1);
				String name = rs.getString(2);
				String desc = rs.getString(3);
				String help =rs.getString(4);
				String entityType=rs.getString(5);
				//TODO AD_SEQ system !!!
				M_Element elem = new M_Element(getCtx(),columnName,entityType,get_TrxName());
				elem.setDescription(desc);
				elem.setHelp(help);
				elem.setPrintName(name);
				elem.save();
			}
			pstmt.close();
			rs.close();
			log.info("Adding missing Element Translations");
			sql="INSERT INTO AD_ELEMENT_TRL (AD_Element_ID, AD_LANGUAGE, AD_Client_ID, AD_Org_ID,"
				+" IsActive, Created, CreatedBy, Updated, UpdatedBy,"
				+" Name, PrintName, Description, Help, IsTranslated)"
				+" SELECT m.AD_Element_ID, l.AD_LANGUAGE, m.AD_Client_ID, m.AD_Org_ID,"
				+" m.IsActive, m.Created, m.CreatedBy, m.Updated, m.UpdatedBy,"
				+" m.Name, m.PrintName, m.Description, m.Help, 'N'"
				+" FROM	AD_ELEMENT m, AD_LANGUAGE l"
				+" WHERE	l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'"
				+" AND	AD_Element_ID || AD_LANGUAGE NOT IN "
				+" (SELECT AD_Element_ID || AD_LANGUAGE FROM AD_ELEMENT_TRL)";
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  rows updated: "+no);


			log.info("Creating link from Element to Column");
			sql="UPDATE	AD_COLUMN c"
				+" SET		AD_Element_id =" 
				+" 	(SELECT AD_Element_ID FROM AD_ELEMENT e" 
				+" 	WHERE UPPER(c.ColumnName)=UPPER(e.ColumnName))"
				+" 	WHERE AD_Element_ID IS NULL";
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  rows updated: "+no);


			log.info("Deleting unused Elements");
			sql="DELETE	AD_ELEMENT_TRL"
				+" 	WHERE	AD_Element_ID IN"
				+" 	(SELECT AD_Element_ID FROM AD_ELEMENT e "
				+" 	WHERE NOT EXISTS"
				+" 	(SELECT 1 FROM AD_COLUMN c WHERE UPPER(e.ColumnName)=UPPER(c.ColumnName))"
				+" 	AND NOT EXISTS"
				+" 	(SELECT 1 FROM AD_PROCESS_PARA p WHERE UPPER(e.ColumnName)=UPPER(p.ColumnName)))";
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  rows deleted: "+no);

			sql="DELETE	AD_ELEMENT e"
				+" 	WHERE AD_Element_ID >= 1000000 AND NOT EXISTS"
				+" 	(SELECT 1 FROM AD_COLUMN c WHERE UPPER(e.ColumnName)=UPPER(c.ColumnName))"
				+" 	AND NOT EXISTS"
				+" 	(SELECT 1 FROM AD_PROCESS_PARA p WHERE UPPER(e.ColumnName)=UPPER(p.ColumnName))";
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  rows deleted: "+no);


			//	Columns
			log.info("Synchronize Column");
			sql=" 	UPDATE AD_COLUMN c"
				+" 		SET	(ColumnName, Name, Description, Help) =" 
				+" 	           (SELECT ColumnName, Name, Description, Help" 
				+" 	            FROM AD_ELEMENT e WHERE c.AD_Element_ID=e.AD_Element_ID),"
				+" 			Updated = now()"
				+" 	WHERE EXISTS (SELECT 1 FROM AD_ELEMENT e "
				+" 				WHERE c.AD_Element_ID=e.AD_Element_ID"
				+" 				  AND (c.ColumnName <> e.ColumnName OR c.Name <> e.Name "
				+" 					OR COALESCE(c.Description,' ') <> COALESCE(e.Description,' ') OR COALESCE(c.Help,' ') <> COALESCE(e.Help,' ')))";
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  rows updated: "+no);

			//	Fields should now be synchronized
			log.info("Synchronize Field");
			sql=DB.convertSqlToNative(" 	UPDATE AD_FIELD f"
				+" 		SET (Name, Description, Help) = "
				+" 	            (SELECT e.Name, e.Description, e.Help"
				+" 	            FROM AD_ELEMENT e, AD_COLUMN c"
				+" 	    	    WHERE e.AD_Element_ID=c.AD_Element_ID AND c.AD_Column_ID=f.AD_Column_ID),"
				+" 			Updated = now()"
				+" 	WHERE f.IsCentrallyMaintained='Y' AND f.IsActive='Y'"
				+" 	 AND EXISTS (SELECT 1 FROM AD_ELEMENT e, AD_COLUMN c"
				+" 				WHERE f.AD_Column_ID=c.AD_Column_ID"
				+" 				  AND c.AD_Element_ID=e.AD_Element_ID AND c.AD_Process_ID IS NULL"
				+" 				  AND (f.Name <> e.Name OR COALESCE(f.Description,' ') <> COALESCE(e.Description,' ') OR COALESCE(f.Help,' ') <> COALESCE(e.Help,' ')))");
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  rows updated: "+no);

			//	Field Translations
			log.info("Synchronize Field Translations");
			sql="UPDATE AD_FIELD_TRL trl"
				+" SET Name = (SELECT e.Name FROM AD_ELEMENT_TRL e, AD_COLUMN c, AD_FIELD f"
				+"			WHERE e.AD_LANGUAGE=trl.AD_LANGUAGE AND e.AD_Element_ID=c.AD_Element_ID "
				+"			  AND c.AD_Column_ID=f.AD_Column_ID AND f.AD_Field_ID=trl.AD_Field_ID),"
				+"	Description = (SELECT e.Description FROM AD_ELEMENT_TRL e, AD_COLUMN c, AD_FIELD f"
				+"			WHERE e.AD_LANGUAGE=trl.AD_LANGUAGE AND e.AD_Element_ID=c.AD_Element_ID "
				+"			  AND c.AD_Column_ID=f.AD_Column_ID AND f.AD_Field_ID=trl.AD_Field_ID),"
				+"	Help = (SELECT e.Help FROM AD_ELEMENT_TRL e, AD_COLUMN c, AD_FIELD f"
				+"			WHERE e.AD_LANGUAGE=trl.AD_LANGUAGE AND e.AD_Element_ID=c.AD_Element_ID "
				+"			  AND c.AD_Column_ID=f.AD_Column_ID AND f.AD_Field_ID=trl.AD_Field_ID),"
				+"	IsTranslated = (SELECT e.IsTranslated FROM AD_ELEMENT_TRL e, AD_COLUMN c, AD_FIELD f"
				+"			WHERE e.AD_LANGUAGE=trl.AD_LANGUAGE AND e.AD_Element_ID=c.AD_Element_ID "
				+"			  AND c.AD_Column_ID=f.AD_Column_ID AND f.AD_Field_ID=trl.AD_Field_ID),"
				+"	Updated = now()"
				+" WHERE EXISTS (SELECT 1 FROM AD_FIELD f, AD_ELEMENT_TRL e, AD_COLUMN c"
				+"		WHERE trl.AD_Field_ID=f.AD_Field_ID"
				+"		  AND f.AD_Column_ID=c.AD_Column_ID"
				+"		  AND c.AD_Element_ID=e.AD_Element_ID AND c.AD_Process_ID IS NULL"
				+"		  AND trl.AD_LANGUAGE=e.AD_LANGUAGE"
				+"		  AND f.IsCentrallyMaintained='Y' AND f.IsActive='Y'"
				+"		  AND (trl.Name <> e.Name OR COALESCE(trl.Description,' ') <> COALESCE(e.Description,' ') OR COALESCE(trl.Help,' ') <> COALESCE(e.Help,' ')))";
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  rows updated: "+no);

			//	Fields should now be synchronized
			log.info("Synchronize PO Field");
			sql="UPDATE AD_FIELD f"
				+" SET Name = (SELECT e.PO_Name FROM AD_ELEMENT e, AD_COLUMN c"
				+" 			WHERE e.AD_Element_ID=c.AD_Element_ID AND c.AD_Column_ID=f.AD_Column_ID),"
				+" 	Description = (SELECT e.PO_Description FROM AD_ELEMENT e, AD_COLUMN c"
				+" 			WHERE e.AD_Element_ID=c.AD_Element_ID AND c.AD_Column_ID=f.AD_Column_ID),"
				+" 	Help = (SELECT e.PO_Help FROM AD_ELEMENT e, AD_COLUMN c"
				+" 			WHERE e.AD_Element_ID=c.AD_Element_ID AND c.AD_Column_ID=f.AD_Column_ID),"
				+" 	Updated = now()"
				+" WHERE f.IsCentrallyMaintained='Y' AND f.IsActive='Y'"
				+" AND EXISTS (SELECT 1 FROM AD_ELEMENT e, AD_COLUMN c"
				+" 		WHERE f.AD_Column_ID=c.AD_Column_ID"
				+" 		  AND c.AD_Element_ID=e.AD_Element_ID AND c.AD_Process_ID IS NULL"
				+" 		  AND (f.Name <> e.PO_Name OR COALESCE(f.Description,' ') <> COALESCE(e.PO_Description,' ') OR COALESCE(f.Help,' ') <> COALESCE(e.PO_Help,' '))"
				+" 		  AND e.PO_Name IS NOT NULL)"
				+" AND EXISTS (SELECT 1 FROM AD_TAB t, AD_WINDOW w"
				+" 		WHERE f.AD_Tab_ID=t.AD_Tab_ID"
				+" 		  AND t.AD_Window_ID=w.AD_Window_ID"
				+" 		  AND w.IsSOTrx='N')";
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  rows updated: "+no);

			//	Field Translations
			log.info("Synchronize PO Field Translations");
			sql=" UPDATE AD_FIELD_TRL trl"
				+" SET Name = (SELECT e.PO_Name FROM AD_ELEMENT_TRL e, AD_COLUMN c, AD_FIELD f"
				+" 		WHERE e.AD_LANGUAGE=trl.AD_LANGUAGE AND e.AD_Element_ID=c.AD_Element_ID" 
				+" 		  AND c.AD_Column_ID=f.AD_Column_ID AND f.AD_Field_ID=trl.AD_Field_ID),"
				+" Description = (SELECT e.PO_Description FROM AD_ELEMENT_TRL e, AD_COLUMN c, AD_FIELD f"
				+" 		WHERE e.AD_LANGUAGE=trl.AD_LANGUAGE AND e.AD_Element_ID=c.AD_Element_ID "
				+" 		  AND c.AD_Column_ID=f.AD_Column_ID AND f.AD_Field_ID=trl.AD_Field_ID),"
				+" Help = (SELECT e.PO_Help FROM AD_ELEMENT_TRL e, AD_COLUMN c, AD_FIELD f"
				+" 		WHERE e.AD_LANGUAGE=trl.AD_LANGUAGE AND e.AD_Element_ID=c.AD_Element_ID" 
				+" 		  AND c.AD_Column_ID=f.AD_Column_ID AND f.AD_Field_ID=trl.AD_Field_ID),"
				+" IsTranslated = (SELECT e.IsTranslated FROM AD_ELEMENT_TRL e, AD_COLUMN c, AD_FIELD f"
				+" 		WHERE e.AD_LANGUAGE=trl.AD_LANGUAGE AND e.AD_Element_ID=c.AD_Element_ID "
				+" 		  AND c.AD_Column_ID=f.AD_Column_ID AND f.AD_Field_ID=trl.AD_Field_ID),"
				+" Updated = now()"
				+" WHERE EXISTS (SELECT 1 FROM AD_FIELD f, AD_ELEMENT_TRL e, AD_COLUMN c"
				+" 	WHERE trl.AD_Field_ID=f.AD_Field_ID"
				+" 	  AND f.AD_Column_ID=c.AD_Column_ID"
				+" 	  AND c.AD_Element_ID=e.AD_Element_ID AND c.AD_Process_ID IS NULL"
				+" 	  AND trl.AD_LANGUAGE=e.AD_LANGUAGE"
				+" 	  AND f.IsCentrallyMaintained='Y' AND f.IsActive='Y'"
				+" 	  AND (trl.Name <> e.PO_Name OR COALESCE(trl.Description,' ') <> COALESCE(e.PO_Description,' ') OR COALESCE(trl.Help,' ') <> COALESCE(e.PO_Help,' '))"
				+" 	  AND e.PO_Name IS NOT NULL)"
				+" AND EXISTS (SELECT 1 FROM AD_FIELD f, AD_TAB t, AD_WINDOW w"
				+" 	WHERE trl.AD_Field_ID=f.AD_Field_ID"
				+" 	  AND f.AD_Tab_ID=t.AD_Tab_ID"
				+" 	  AND t.AD_Window_ID=w.AD_Window_ID"
				+" 	  AND w.IsSOTrx='N')";
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  rows updated: "+no);


			//	Fields from Process
			log.info("Synchronize Field from Process");
			sql="UPDATE AD_FIELD f"
				+" SET Name = (SELECT p.Name FROM AD_PROCESS p, AD_COLUMN c WHERE p.AD_Process_ID=c.AD_Process_ID"
				+" 			AND c.AD_Column_ID=f.AD_Column_ID),"
				+" 	Description = (SELECT p.Description FROM AD_PROCESS p, AD_COLUMN c WHERE p.AD_Process_ID=c.AD_Process_ID"
				+" 			AND c.AD_Column_ID=f.AD_Column_ID),"
				+" 	Help = (SELECT p.Help FROM AD_PROCESS p, AD_COLUMN c WHERE p.AD_Process_ID=c.AD_Process_ID"
				+" 			AND c.AD_Column_ID=f.AD_Column_ID),"
				+" 	Updated = now()"
				+" WHERE f.IsCentrallyMaintained='Y' AND f.IsActive='Y'" 
				+" AND EXISTS (SELECT 1 FROM AD_PROCESS p, AD_COLUMN c"
				+" 		WHERE c.AD_Process_ID=p.AD_Process_ID AND f.AD_Column_ID=c.AD_Column_ID"
				+" 		AND (f.Name<>p.Name OR COALESCE(f.Description,' ')<>COALESCE(p.Description,' ') OR COALESCE(f.Help,' ')<>COALESCE(p.Help,' ')))";
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  rows updated: "+no);

			//	Field Translations from Process
			log.info("Synchronize Field Trl from Process Trl");
			sql="UPDATE AD_FIELD_TRL trl"
				+" SET Name = (SELECT p.Name FROM AD_PROCESS_TRL p, AD_COLUMN c, AD_FIELD f" 
				+" 			WHERE p.AD_Process_ID=c.AD_Process_ID AND c.AD_Column_ID=f.AD_Column_ID"
				+" 			AND f.AD_Field_ID=trl.AD_Field_ID AND p.AD_LANGUAGE=trl.AD_LANGUAGE),"
				+" 	Description = (SELECT p.Description FROM AD_PROCESS_TRL p, AD_COLUMN c, AD_FIELD f" 
				+" 			WHERE p.AD_Process_ID=c.AD_Process_ID AND c.AD_Column_ID=f.AD_Column_ID"
				+" 			AND f.AD_Field_ID=trl.AD_Field_ID AND p.AD_LANGUAGE=trl.AD_LANGUAGE),"
				+" 	Help = (SELECT p.Help FROM AD_PROCESS_TRL p, AD_COLUMN c, AD_FIELD f "
				+" 			WHERE p.AD_Process_ID=c.AD_Process_ID AND c.AD_Column_ID=f.AD_Column_ID"
				+" 			AND f.AD_Field_ID=trl.AD_Field_ID AND p.AD_LANGUAGE=trl.AD_LANGUAGE),"
				+" 	IsTranslated = (SELECT p.IsTranslated FROM AD_PROCESS_TRL p, AD_COLUMN c, AD_FIELD f" 
				+" 			WHERE p.AD_Process_ID=c.AD_Process_ID AND c.AD_Column_ID=f.AD_Column_ID"
				+" 			AND f.AD_Field_ID=trl.AD_Field_ID AND p.AD_LANGUAGE=trl.AD_LANGUAGE),"
				+" 	Updated = now()"
				+" WHERE EXISTS (SELECT 1 FROM AD_PROCESS_TRL p, AD_COLUMN c, AD_FIELD f"
				+" 		WHERE c.AD_Process_ID=p.AD_Process_ID AND f.AD_Column_ID=c.AD_Column_ID"
				+" 		AND f.AD_Field_ID=trl.AD_Field_ID AND p.AD_LANGUAGE=trl.AD_LANGUAGE"
				+" 		AND f.IsCentrallyMaintained='Y' AND f.IsActive='Y'"
				+" 		AND (trl.Name<>p.Name OR COALESCE(trl.Description,' ')<>COALESCE(p.Description,' ') OR COALESCE(trl.Help,' ')<>COALESCE(p.Help,' ')))";
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  rows updated: "+no);

			//	Sync Parameter ColumnName
			sql="UPDATE	AD_PROCESS_PARA f"
				+" SET	ColumnName = (SELECT e.ColumnName FROM AD_ELEMENT e"
				// +" 			WHERE UPPER(e.ColumnName)=UPPER(f.ColumnName))"
				+" 			WHERE e.ColumnName=f.ColumnName)"
				+" WHERE f.IsCentrallyMaintained='Y' AND f.IsActive='Y'"
				+" AND EXISTS (SELECT 1 FROM AD_ELEMENT e"
				+" WHERE UPPER(e.ColumnName)=UPPER(f.ColumnName)"
				+" AND e.ColumnName<>f.ColumnName)";
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  rows updated: "+no);

			//	Parameter Fields
			sql="UPDATE	AD_PROCESS_PARA p"
				+" SET	IsCentrallyMaintained = 'N'"
				+" WHERE	IsCentrallyMaintained <> 'N'"
				+" AND NOT EXISTS (SELECT 1 FROM AD_ELEMENT e WHERE p.ColumnName=e.ColumnName)"; 
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  rows updated: "+no);

			//	Parameter Fields
			log.info("Synchronize Process Parameter");
			sql="UPDATE AD_PROCESS_PARA f"
				+" SET Name = (SELECT e.Name FROM AD_ELEMENT e"
				+" 			WHERE e.ColumnName=f.ColumnName),"
				+" 	Description = (SELECT e.Description FROM AD_ELEMENT e"
				+" 			WHERE e.ColumnName=f.ColumnName),"
				+" 	Help = (SELECT e.Help FROM AD_ELEMENT e"
				+" 			WHERE e.ColumnName=f.ColumnName),"
				+" 	Updated = now()"
				+" WHERE f.IsCentrallyMaintained='Y' AND f.IsActive='Y'"
				+" AND EXISTS (SELECT 1 FROM AD_ELEMENT e"
				+" 		WHERE e.ColumnName=f.ColumnName"
				+" 		  AND (f.Name <> e.Name OR COALESCE(f.Description,' ') <> COALESCE(e.Description,' ') OR COALESCE(f.Help,' ') <> COALESCE(e.Help,' ')))";
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  rows updated: "+no);

			//	Parameter Translations
			log.info("Synchronize Process Parameter Trl");
			sql="UPDATE AD_PROCESS_PARA_TRL trl"
				+" SET Name = (SELECT et.Name FROM AD_ELEMENT_TRL et, AD_ELEMENT e, AD_PROCESS_PARA f"
				+" 			WHERE et.AD_LANGUAGE=trl.AD_LANGUAGE AND et.AD_Element_ID=e.AD_Element_ID"
				+" 			  AND e.ColumnName=f.ColumnName AND f.AD_Process_Para_ID=trl.AD_Process_Para_ID),"
				+" 	Description = (SELECT et.Description FROM AD_ELEMENT_TRL et, AD_ELEMENT e, AD_PROCESS_PARA f"
				+" 			WHERE et.AD_LANGUAGE=trl.AD_LANGUAGE AND et.AD_Element_ID=e.AD_Element_ID"
				+" 			  AND e.ColumnName=f.ColumnName AND f.AD_Process_Para_ID=trl.AD_Process_Para_ID),"
				+" 	Help = (SELECT et.Help FROM AD_ELEMENT_TRL et, AD_ELEMENT e, AD_PROCESS_PARA f"
				+" 			WHERE et.AD_LANGUAGE=trl.AD_LANGUAGE AND et.AD_Element_ID=e.AD_Element_ID"
				+" 			  AND e.ColumnName=f.ColumnName AND f.AD_Process_Para_ID=trl.AD_Process_Para_ID),"
				+" 	IsTranslated = (SELECT et.IsTranslated FROM AD_ELEMENT_TRL et, AD_ELEMENT e, AD_PROCESS_PARA f"
				+" 			WHERE et.AD_LANGUAGE=trl.AD_LANGUAGE AND et.AD_Element_ID=e.AD_Element_ID"
				+" 			  AND e.ColumnName=f.ColumnName AND f.AD_Process_Para_ID=trl.AD_Process_Para_ID),"
				+" 	Updated = now()"
				+" WHERE EXISTS (SELECT 1 FROM AD_ELEMENT_TRL et, AD_ELEMENT e, AD_PROCESS_PARA f"
				+" 			WHERE et.AD_LANGUAGE=trl.AD_LANGUAGE AND et.AD_Element_ID=e.AD_Element_ID"
				+" 			  AND e.ColumnName=f.ColumnName AND f.AD_Process_Para_ID=trl.AD_Process_Para_ID"
				+" 			  AND f.IsCentrallyMaintained='Y' AND f.IsActive='Y'"
				+" 			  AND (trl.Name <> et.Name OR COALESCE(trl.Description,' ') <> COALESCE(et.Description,' ') OR COALESCE(trl.Help,' ') <> COALESCE(et.Help,' ')))";
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  rows updated: "+no);

			//	Workflow Node - Window
			log.info("Synchronize Workflow Node from Window");
			sql="UPDATE AD_WF_NODE n"
				+" SET Name = (SELECT w.Name FROM AD_WINDOW w"
				+" 			WHERE w.AD_Window_ID=n.AD_Window_ID),"
				+" 	Description = (SELECT w.Description FROM AD_WINDOW w"
				+" 			WHERE w.AD_Window_ID=n.AD_Window_ID),"
				+" 	Help = (SELECT w.Help FROM AD_WINDOW w"
				+" 			WHERE w.AD_Window_ID=n.AD_Window_ID)"
				+" WHERE n.IsCentrallyMaintained = 'Y'"
				+"  AND EXISTS  (SELECT 1 FROM AD_WINDOW w"
				+" 		WHERE w.AD_Window_ID=n.AD_Window_ID"
				+" 		  AND (w.Name <> n.Name OR COALESCE(w.Description,' ') <> COALESCE(n.Description,' ') OR COALESCE(w.Help,' ') <> COALESCE(n.Help,' ')))";
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  rows updated: "+no);

			//	Workflow Translations - Window
			log.info("Synchronize Workflow Node Trl from Window Trl");
			sql="UPDATE AD_WF_NODE_TRL trl"
				+" SET Name = (SELECT t.Name FROM AD_WINDOW_TRL t, AD_WF_NODE n"
				+" 			WHERE trl.AD_WF_Node_ID=n.AD_WF_Node_ID AND n.AD_Window_ID=t.AD_Window_ID"
				+" 			  AND trl.AD_LANGUAGE=t.AD_LANGUAGE),"
				+" 	Description = (SELECT t.Description FROM AD_WINDOW_TRL t, AD_WF_NODE n"
				+" 			WHERE trl.AD_WF_Node_ID=n.AD_WF_Node_ID AND n.AD_Window_ID=t.AD_Window_ID"
				+" 			  AND trl.AD_LANGUAGE=t.AD_LANGUAGE),"
				+" 	Help = (SELECT t.Help FROM AD_WINDOW_TRL t, AD_WF_NODE n"
				+" 			WHERE trl.AD_WF_Node_ID=n.AD_WF_Node_ID AND n.AD_Window_ID=t.AD_Window_ID"
				+" 			  AND trl.AD_LANGUAGE=t.AD_LANGUAGE)"
				+" WHERE EXISTS (SELECT 1 FROM AD_WINDOW_TRL t, AD_WF_NODE n"
				+" 		WHERE trl.AD_WF_Node_ID=n.AD_WF_Node_ID AND n.AD_Window_ID=t.AD_Window_ID"
				+" 		  AND trl.AD_LANGUAGE=t.AD_LANGUAGE AND n.IsCentrallyMaintained='Y' AND n.IsActive='Y'"
				+" 		  AND (trl.Name <> t.Name OR COALESCE(trl.Description,' ') <> COALESCE(t.Description,' ') OR COALESCE(trl.Help,' ') <> COALESCE(t.Help,' ')))";
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  rows updated: "+no);

			//	Workflow Node - Form
			log.info("Synchronize Workflow Node from Form");
			sql=DB.convertSqlToNative("UPDATE AD_WF_NODE n"
				+" SET (Name, Description, Help) = (SELECT f.Name, f.Description, f.Help" 
				+" 		FROM AD_FORM f"
				+" 		WHERE f.AD_Form_ID=n.AD_Form_ID)"
				+" WHERE n.IsCentrallyMaintained = 'Y'"
				+" AND EXISTS  (SELECT 1 FROM AD_FORM f"
				+" 		WHERE f.AD_Form_ID=n.AD_Form_ID"
				+" 		  AND (f.Name <> n.Name OR COALESCE(f.Description,' ') <> COALESCE(n.Description,' ') OR COALESCE(f.Help,' ') <> COALESCE(n.Help,' ')))");
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  rows updated: "+no);

			//	Workflow Translations - Form
			log.info("Synchronize Workflow Node Trl from Form Trl");
			sql=DB.convertSqlToNative(" UPDATE AD_WF_NODE_TRL trl"
				+" SET (Name, Description, Help) = (SELECT t.Name, t.Description, t.Help"
				+" 	FROM AD_FORM_TRL t, AD_WF_NODE n"
				+" 	WHERE trl.AD_WF_Node_ID=n.AD_WF_Node_ID AND n.AD_Form_ID=t.AD_Form_ID"
				+" 	  AND trl.AD_LANGUAGE=t.AD_LANGUAGE)"
				+" WHERE EXISTS (SELECT 1 FROM AD_FORM_TRL t, AD_WF_NODE n"
				+" 		WHERE trl.AD_WF_Node_ID=n.AD_WF_Node_ID AND n.AD_Form_ID=t.AD_Form_ID"
				+" 		  AND trl.AD_LANGUAGE=t.AD_LANGUAGE AND n.IsCentrallyMaintained='Y' AND n.IsActive='Y'"
				+" 		  AND (trl.Name <> t.Name OR COALESCE(trl.Description,' ') <> COALESCE(t.Description,' ') OR COALESCE(trl.Help,' ') <> COALESCE(t.Help,' ')))");
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  rows updated: "+no);

			//	Workflow Node - Report
			log.info("Synchronize Workflow Node from Process");
			sql=DB.convertSqlToNative("UPDATE AD_WF_NODE n"
				+" SET (Name, Description, Help) = (SELECT f.Name, f.Description, f.Help" 
				+" 		FROM AD_PROCESS f"
				+" 		WHERE f.AD_Process_ID=n.AD_Process_ID)"
				+" WHERE n.IsCentrallyMaintained = 'Y'"
				+" AND EXISTS  (SELECT 1 FROM AD_PROCESS f"
				+" 		WHERE f.AD_Process_ID=n.AD_Process_ID"
				+" 		  AND (f.Name <> n.Name OR COALESCE(f.Description,' ') <> COALESCE(n.Description,' ') OR COALESCE(f.Help,' ') <> COALESCE(n.Help,' ')))");
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  rows updated: "+no);

			//	Workflow Translations - Form
			log.info("Synchronize Workflow Node Trl from Process Trl");
			sql=DB.convertSqlToNative("UPDATE AD_WF_NODE_TRL trl"
				+" SET (Name, Description, Help) = (SELECT t.Name, t.Description, t.Help"
				+" FROM AD_PROCESS_TRL t, AD_WF_NODE n"
				+" WHERE trl.AD_WF_Node_ID=n.AD_WF_Node_ID AND n.AD_Process_ID=t.AD_Process_ID"
				+"  AND trl.AD_LANGUAGE=t.AD_LANGUAGE)"
				+" WHERE EXISTS (SELECT 1 FROM AD_PROCESS_TRL t, AD_WF_NODE n"
				+" WHERE trl.AD_WF_Node_ID=n.AD_WF_Node_ID AND n.AD_Process_ID=t.AD_Process_ID"
				+"  AND trl.AD_LANGUAGE=t.AD_LANGUAGE AND n.IsCentrallyMaintained='Y' AND n.IsActive='Y'"
				+"  AND (trl.Name <> t.Name OR COALESCE(trl.Description,' ') <> COALESCE(t.Description,' ') OR COALESCE(trl.Help,' ') <> COALESCE(t.Help,' ')))");
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  rows updated: "+no);

			//  Need centrally maintained flag here!
			log.info("Synchronize PrintFormatItem Name from Element");
			sql="UPDATE AD_PRINTFORMATITEM pfi"
				+" SET Name = (SELECT e.Name "
				+" FROM AD_ELEMENT e, AD_COLUMN c"
				+" WHERE e.AD_Element_ID=c.AD_Element_ID"
				+" AND c.AD_Column_ID=pfi.AD_Column_ID)"
				+" WHERE pfi.IsCentrallyMaintained='Y'"
				+" AND EXISTS (SELECT 1 "
				+" FROM AD_ELEMENT e, AD_COLUMN c"
				+" WHERE e.AD_Element_ID=c.AD_Element_ID"
				+" AND c.AD_Column_ID=pfi.AD_Column_ID"
				+" AND e.Name<>pfi.Name)"
				+" AND EXISTS (SELECT 1 FROM AD_CLIENT" 
				+" WHERE AD_Client_ID=pfi.AD_Client_ID AND IsMultiLingualDocument='Y')";
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  rows updated: "+no);

			log.info("Synchronize PrintFormatItem PrintName from Element");
			sql="UPDATE AD_PRINTFORMATITEM pfi"
				+" SET PrintName = (SELECT e.PrintName "
				+" FROM AD_ELEMENT e, AD_COLUMN c"
				+" WHERE e.AD_Element_ID=c.AD_Element_ID"
				+" AND c.AD_Column_ID=pfi.AD_Column_ID)"
				+" WHERE pfi.IsCentrallyMaintained='Y'"
				+" AND EXISTS (SELECT 1 "
				+" FROM AD_ELEMENT e, AD_COLUMN c, AD_PRINTFORMAT pf"
				+" WHERE e.AD_Element_ID=c.AD_Element_ID"
				+" AND c.AD_Column_ID=pfi.AD_Column_ID"
				+" AND LENGTH(pfi.PrintName) > 0"
				+" AND e.PrintName<>pfi.PrintName"
				+" AND pf.AD_PrintFormat_ID=pfi.AD_PrintFormat_ID"
				+" AND pf.IsForm='N' AND IsTableBased='Y')"
				+" AND EXISTS (SELECT 1 FROM AD_CLIENT "
				+" WHERE AD_Client_ID=pfi.AD_Client_ID AND IsMultiLingualDocument='Y')";
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  rows updated: "+no);

			log.info("Synchronize PrintFormatItem Trl from Element Trl (Multi-Lingual)");
			sql="UPDATE AD_PRINTFORMATITEM_TRL trl"
				+" SET PrintName = (SELECT e.PrintName" 
				+" FROM AD_ELEMENT_TRL e, AD_COLUMN c, AD_PRINTFORMATITEM pfi"
				+" WHERE e.AD_LANGUAGE=trl.AD_LANGUAGE"
				+" AND e.AD_Element_ID=c.AD_Element_ID"
				+" AND c.AD_Column_ID=pfi.AD_Column_ID"
				+" AND pfi.AD_PrintFormatItem_ID=trl.AD_PrintFormatItem_ID)"
				+" WHERE EXISTS (SELECT 1 "
				+" FROM AD_ELEMENT_TRL e, AD_COLUMN c, AD_PRINTFORMATITEM pfi, AD_PRINTFORMAT pf"
				+" WHERE e.AD_LANGUAGE=trl.AD_LANGUAGE"
				+" AND e.AD_Element_ID=c.AD_Element_ID"
				+" AND c.AD_Column_ID=pfi.AD_Column_ID"
				+" AND pfi.AD_PrintFormatItem_ID=trl.AD_PrintFormatItem_ID"
				+" AND pfi.IsCentrallyMaintained='Y'"
				+" AND LENGTH(pfi.PrintName) > 0"
				+" AND (e.PrintName<>trl.PrintName OR trl.PrintName IS NULL)"
				+" AND pf.AD_PrintFormat_ID=pfi.AD_PrintFormat_ID "
				+" AND pf.IsForm='N' AND IsTableBased='Y')"
				+" AND EXISTS (SELECT 1 FROM AD_CLIENT "
				+" WHERE AD_Client_ID=trl.AD_Client_ID AND IsMultiLingualDocument='Y')";
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  rows updated: "+no);

			log.info("Synchronize PrintFormatItem Trl (Not Multi-Lingual)");
			sql="UPDATE AD_PRINTFORMATITEM_TRL trl"
				+" SET PrintName = (SELECT pfi.PrintName" 
				+" FROM AD_PRINTFORMATITEM pfi"
				+" WHERE pfi.AD_PrintFormatItem_ID=trl.AD_PrintFormatItem_ID)"
				+" WHERE EXISTS (SELECT 1 "
				+" FROM AD_PRINTFORMATITEM pfi, AD_PRINTFORMAT pf"
				+" WHERE pfi.AD_PrintFormatItem_ID=trl.AD_PrintFormatItem_ID"
				+" AND pfi.IsCentrallyMaintained='Y'"
				+" AND LENGTH(pfi.PrintName) > 0"
				+" AND pfi.PrintName<>trl.PrintName"
				+" AND pf.AD_PrintFormat_ID=pfi.AD_PrintFormat_ID "
				+" AND pf.IsForm='N' AND pf.IsTableBased='Y')"
				+" AND EXISTS (SELECT 1 FROM AD_CLIENT "
				+" WHERE AD_Client_ID=trl.AD_Client_ID AND IsMultiLingualDocument='N')";
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  rows updated: "+no);

			log.info("Reset PrintFormatItem Trl where not used in base table");
			sql="UPDATE AD_PRINTFORMATITEM_TRL trl"
				+" SET PrintName = NULL"
				+" WHERE PrintName IS NOT NULL"
				+" AND EXISTS (SELECT 1"
				+" FROM AD_PRINTFORMATITEM pfi"
				+" WHERE pfi.AD_PrintFormatItem_ID=trl.AD_PrintFormatItem_ID" 
				+" AND pfi.IsCentrallyMaintained='Y'"
				+" AND (LENGTH (pfi.PrintName) = 0 OR pfi.PrintName IS NULL))";
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  rows updated: "+no);

			/**
				SELECT 	e.PrintName "Element", pfi.PrintName "FormatItem", trl.AD_Language, trl.PrintName "Trl"
				FROM 	AD_Element e
				  INNER JOIN AD_Column c ON (e.AD_Element_ID=c.AD_Element_ID)
				  INNER JOIN AD_PrintFormatItem pfi ON (c.AD_Column_ID=pfi.AD_Column_ID)
				  INNER JOIN AD_PrintFormatItem_Trl trl ON (pfi.AD_PrintFormatItem_ID=trl.AD_PrintFormatItem_ID)
				WHERE pfi.AD_PrintFormatItem_ID=?
			 **/

			//	Sync Names - Window
			log.info("Synchronizing Menu with Window");
			sql="UPDATE	AD_MENU m"
				+" SET		Name = (SELECT Name FROM AD_WINDOW w WHERE m.AD_Window_ID=w.AD_Window_ID),"
				+" Description = (SELECT Description FROM AD_WINDOW w WHERE m.AD_Window_ID=w.AD_Window_ID)"
				+" WHERE	AD_Window_ID IS NOT NULL"
				+" AND Action = 'W'";
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  rows updated: "+no);

			sql="UPDATE	AD_MENU_TRL mt"
				+" SET		Name = (SELECT wt.Name FROM AD_WINDOW_TRL wt, AD_MENU m "
				+" WHERE mt.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Window_ID=wt.AD_Window_ID "
				+" AND mt.AD_LANGUAGE=wt.AD_LANGUAGE),"
				+" Description = (SELECT wt.Description FROM AD_WINDOW_TRL wt, AD_MENU m "
				+" WHERE mt.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Window_ID=wt.AD_Window_ID "
				+" AND mt.AD_LANGUAGE=wt.AD_LANGUAGE),"
				+" IsTranslated = (SELECT wt.IsTranslated FROM AD_WINDOW_TRL wt, AD_MENU m "
				+" WHERE mt.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Window_ID=wt.AD_Window_ID "
				+" AND mt.AD_LANGUAGE=wt.AD_LANGUAGE)"
				+" WHERE EXISTS (SELECT 1 FROM AD_WINDOW_TRL wt, AD_MENU m "
				+" WHERE mt.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Window_ID=wt.AD_Window_ID "
				+" AND mt.AD_LANGUAGE=wt.AD_LANGUAGE"
				+" AND m.AD_Window_ID IS NOT NULL"
				+" AND m.Action = 'W')";
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  rows updated: "+no);

			// Sync Names - Process
			log.info("Synchronizing Menu with Processes");
			sql="UPDATE	AD_MENU m"
				+" SET		Name = (SELECT p.Name FROM AD_PROCESS p WHERE m.AD_Process_ID=p.AD_Process_ID),"
				+" Description = (SELECT p.Description FROM AD_PROCESS p WHERE m.AD_Process_ID=p.AD_Process_ID)"
				+" WHERE	m.AD_Process_ID IS NOT NULL"
				+" AND	m.Action IN ('R', 'P')";
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  rows updated: "+no);

			sql="UPDATE	AD_MENU_TRL mt"
				+" SET		Name = (SELECT pt.Name FROM AD_PROCESS_TRL pt, AD_MENU m"
				+" WHERE mt.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Process_ID=pt.AD_Process_ID"
				+" AND mt.AD_LANGUAGE=pt.AD_LANGUAGE),"
				+" Description = (SELECT pt.Description FROM AD_PROCESS_TRL pt, AD_MENU m"
				+" WHERE mt.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Process_ID=pt.AD_Process_ID"
				+" AND mt.AD_LANGUAGE=pt.AD_LANGUAGE),"
				+" IsTranslated = (SELECT pt.IsTranslated FROM AD_PROCESS_TRL pt, AD_MENU m"
				+" WHERE mt.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Process_ID=pt.AD_Process_ID"
				+" AND mt.AD_LANGUAGE=pt.AD_LANGUAGE)"
				+" WHERE EXISTS (SELECT 1 FROM AD_PROCESS_TRL pt, AD_MENU m"
				+" WHERE mt.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Process_ID=pt.AD_Process_ID"
				+" AND mt.AD_LANGUAGE=pt.AD_LANGUAGE"
				+" AND m.AD_Process_ID IS NOT NULL"
				+" AND	Action IN ('R', 'P'))";
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  rows updated: "+no);

			//	Sync Names = Form
			log.info("Synchronizing Menu with Forms");
			sql="UPDATE	AD_MENU m"
				+" SET		Name = (SELECT Name FROM AD_FORM f WHERE m.AD_Form_ID=f.AD_Form_ID),"
				+" Description = (SELECT Description FROM AD_FORM f WHERE m.AD_Form_ID=f.AD_Form_ID)"
				+" WHERE	AD_Form_ID IS NOT NULL"
				+" AND	Action = 'X'";
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  rows updated: "+no);

			sql="UPDATE	AD_MENU_TRL mt"
				+" SET		Name = (SELECT ft.Name FROM AD_FORM_TRL ft, AD_MENU m"
				+" WHERE mt.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Form_ID=ft.AD_Form_ID"
				+" AND mt.AD_LANGUAGE=ft.AD_LANGUAGE),"
				+" Description = (SELECT ft.Description FROM AD_FORM_TRL ft, AD_MENU m"
				+" WHERE mt.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Form_ID=ft.AD_Form_ID"
				+" AND mt.AD_LANGUAGE=ft.AD_LANGUAGE),"
				+" IsTranslated = (SELECT ft.IsTranslated FROM AD_FORM_TRL ft, AD_MENU m"
				+" WHERE mt.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Form_ID=ft.AD_Form_ID"
				+" AND mt.AD_LANGUAGE=ft.AD_LANGUAGE)"
				+" WHERE EXISTS (SELECT 1 FROM AD_FORM_TRL ft, AD_MENU m"
				+" WHERE mt.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Form_ID=ft.AD_Form_ID"
				+" AND mt.AD_LANGUAGE=ft.AD_LANGUAGE"
				+" AND m.AD_Form_ID IS NOT NULL"
				+" AND	Action = 'X')";
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  rows updated: "+no);

			//	Sync Names - Workflow
			log.info("Synchronizing Menu with Workflows");
			sql="UPDATE	AD_MENU m"
				+" SET		Name = (SELECT p.Name FROM AD_WORKFLOW p WHERE m.AD_Workflow_ID=p.AD_Workflow_ID),"
				+" Description = (SELECT p.Description FROM AD_WORKFLOW p WHERE m.AD_Workflow_ID=p.AD_Workflow_ID)"
				+" WHERE	m.AD_Workflow_ID IS NOT NULL"
				+" AND	m.Action = 'F'";
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  rows updated: "+no);

			sql="UPDATE	AD_MENU_TRL mt"
				+" SET		Name = (SELECT pt.Name FROM AD_WORKFLOW_TRL pt, AD_MENU m"
				+" WHERE mt.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Workflow_ID=pt.AD_Workflow_ID"
				+" AND mt.AD_LANGUAGE=pt.AD_LANGUAGE),"
				+" Description = (SELECT pt.Description FROM AD_WORKFLOW_TRL pt, AD_MENU m"
				+" WHERE mt.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Workflow_ID=pt.AD_Workflow_ID"
				+" AND mt.AD_LANGUAGE=pt.AD_LANGUAGE),"
				+" IsTranslated = (SELECT pt.IsTranslated FROM AD_WORKFLOW_TRL pt, AD_MENU m"
				+" WHERE mt.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Workflow_ID=pt.AD_Workflow_ID"
				+" AND mt.AD_LANGUAGE=pt.AD_LANGUAGE)"
				+" WHERE EXISTS (SELECT 1 FROM AD_WORKFLOW_TRL pt, AD_MENU m"
				+" WHERE mt.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Workflow_ID=pt.AD_Workflow_ID"
				+" AND mt.AD_LANGUAGE=pt.AD_LANGUAGE"
				+" AND m.AD_Workflow_ID IS NOT NULL"
				+" AND	Action = 'F')";
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  rows updated: "+no);

			//	Sync Names = Task
			log.info("Synchronizing Menu with Tasks");
			sql="UPDATE	AD_MENU m"
				+" SET		Name = (SELECT Name FROM AD_TASK f WHERE m.AD_Task_ID=f.AD_Task_ID),"
				+" Description = (SELECT Description FROM AD_TASK f WHERE m.AD_Task_ID=f.AD_Task_ID)"
				+" WHERE	AD_Task_ID IS NOT NULL"
				+" AND	Action = 'T'";
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  rows updated: "+no);

			sql="UPDATE	AD_MENU_TRL mt"
				+" SET		Name = (SELECT ft.Name FROM AD_TASK_TRL ft, AD_MENU m"
				+" WHERE mt.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Task_ID=ft.AD_Task_ID"
				+" AND mt.AD_LANGUAGE=ft.AD_LANGUAGE),"
				+" Description = (SELECT ft.Description FROM AD_TASK_TRL ft, AD_MENU m"
				+" WHERE mt.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Task_ID=ft.AD_Task_ID"
				+" AND mt.AD_LANGUAGE=ft.AD_LANGUAGE),"
				+" IsTranslated = (SELECT ft.IsTranslated FROM AD_TASK_TRL ft, AD_MENU m"
				+" WHERE mt.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Task_ID=ft.AD_Task_ID"
				+" AND mt.AD_LANGUAGE=ft.AD_LANGUAGE)"
				+" WHERE EXISTS (SELECT 1 FROM AD_TASK_TRL ft, AD_MENU m"
				+" WHERE mt.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Task_ID=ft.AD_Task_ID"
				+" AND mt.AD_LANGUAGE=ft.AD_LANGUAGE"
				+" AND m.AD_Task_ID IS NOT NULL"
				+" AND	Action = 'T')";
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  rows updated: "+no);

			//  Column Name + Element
			log.info("Synchronizing Column with Element");
			sql=DB.convertSqlToNative("UPDATE AD_COLUMN c"
				+" SET (Name,Description,Help) =" 
				+" (SELECT e.Name,e.Description,e.Help "
				+" FROM AD_ELEMENT e WHERE c.AD_Element_ID=e.AD_Element_ID)"
				+" WHERE EXISTS "
				+" (SELECT 1 FROM AD_ELEMENT e "
				+" WHERE c.AD_Element_ID=e.AD_Element_ID"
				+" AND c.Name<>e.Name)");
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  rows updated: "+no);
			sql="UPDATE AD_COLUMN_TRL ct"
				+" SET Name = (SELECT e.Name"
				+" FROM AD_COLUMN c INNER JOIN AD_ELEMENT_TRL e ON (c.AD_Element_ID=e.AD_Element_ID)"
				+" WHERE ct.AD_Column_ID=c.AD_Column_ID AND ct.AD_LANGUAGE=e.AD_LANGUAGE)"
				+" WHERE EXISTS "
				+" (SELECT 1 FROM AD_COLUMN c INNER JOIN AD_ELEMENT_TRL e ON (c.AD_Element_ID=e.AD_Element_ID)"
				+" WHERE ct.AD_Column_ID=c.AD_Column_ID AND ct.AD_LANGUAGE=e.AD_LANGUAGE"
				+" AND ct.Name<>e.Name)";
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  rows updated: "+no);


			//  Table Name + Element
			log.info("Synchronizing Table with Element");
			sql=DB.convertSqlToNative("UPDATE AD_TABLE t "
				+"SET (Name,Description) = (SELECT e.Name,e.Description FROM AD_ELEMENT e " 
				+"WHERE t.TableName||'_ID'=e.ColumnName) "
				+"WHERE EXISTS (SELECT 1 FROM AD_ELEMENT e " 
				+"WHERE t.TableName||'_ID'=e.ColumnName "
				+"AND t.Name<>e.Name)");
			no = DB.executeUpdate(sql, false, get_TrxName());

			log.info("  rows updated: " +no);
			sql="UPDATE AD_TABLE_TRL tt" 
				+" SET Name = (SELECT e.Name "
				+" FROM AD_TABLE t INNER JOIN AD_ELEMENT ex ON (t.TableName||'_ID'=ex.ColumnName)"
				+" INNER JOIN AD_ELEMENT_TRL e ON (ex.AD_Element_ID=e.AD_Element_ID)"
				+" WHERE tt.AD_Table_ID=t.AD_Table_ID AND tt.AD_LANGUAGE=e.AD_LANGUAGE)"
				+" WHERE EXISTS (SELECT 1 "
				+" FROM AD_TABLE t INNER JOIN AD_ELEMENT ex ON (t.TableName||'_ID'=ex.ColumnName)"
				+" INNER JOIN AD_ELEMENT_TRL e ON (ex.AD_Element_ID=e.AD_Element_ID)"
				+" WHERE tt.AD_Table_ID=t.AD_Table_ID AND tt.AD_LANGUAGE=e.AD_LANGUAGE"
				+" AND tt.Name<>e.Name)";
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  trl rows updated: "+no);

			//  Trl Table Name + Element
			sql=DB.convertSqlToNative("UPDATE AD_TABLE t"
				+" SET (Name,Description) = (SELECT e.Name||' Trl', e.Description "
				+" FROM AD_ELEMENT e "
				+" WHERE SUBSTR(t.TableName,1,LENGTH(t.TableName)-4)||'_ID'=e.ColumnName)"
				+" WHERE TableName LIKE '%_Trl'"
				+" AND EXISTS (SELECT 1 FROM AD_ELEMENT e "
				+" WHERE SUBSTR(t.TableName,1,LENGTH(t.TableName)-4)||'_ID'=e.ColumnName"
				+" AND t.Name<>e.Name)");
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  trl rows updated: "+no);
			sql=" UPDATE AD_TABLE_TRL tt"
				+" SET Name = (SELECT e.Name || ' **'"
				+" FROM AD_TABLE t INNER JOIN AD_ELEMENT ex ON (SUBSTR(t.TableName,1,LENGTH(t.TableName)-4)||'_ID'=ex.ColumnName)"
				+" INNER JOIN AD_ELEMENT_TRL e ON (ex.AD_Element_ID=e.AD_Element_ID)"
				+" WHERE tt.AD_Table_ID=t.AD_Table_ID AND tt.AD_LANGUAGE=e.AD_LANGUAGE)"
				+" WHERE EXISTS (SELECT 1 "
				+" FROM AD_TABLE t INNER JOIN AD_ELEMENT ex ON (SUBSTR(t.TableName,1,LENGTH(t.TableName)-4)||'_ID'=ex.ColumnName)"
				+" INNER JOIN AD_ELEMENT_TRL e ON (ex.AD_Element_ID=e.AD_Element_ID)"
				+" WHERE tt.AD_Table_ID=t.AD_Table_ID AND tt.AD_LANGUAGE=e.AD_LANGUAGE"
				+" AND t.TableName LIKE '%_Trl'"
				+" AND tt.Name<>e.Name)";
			no = DB.executeUpdate(sql, false, get_TrxName());	  	
			log.info("  trl rows updated: "+no);
		} catch (Exception e) {
			log.error("@Failed@: "+e.getLocalizedMessage(), e);
			throw e;
		}

		return "@OK@";
	}
}
