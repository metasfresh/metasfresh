
package org.compiere.interfaces;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Properties;

import org.compiere.process.ProcessInfo;
import org.compiere.util.EMail;

/**
 * Interface for adempiere/Server.
 */
public interface Server
{
   public final static String JNDI_NAME = "adempiere/Server";   
   
   public final static String EJB_NAME = "adempiereServer";
   
   /**
    * Post Immediate
    * @param ctx Client Context
    * @param AD_Client_ID Client ID of Document
    * @param AD_Table_ID Table ID of Document
    * @param Record_ID Record ID of this document
    * @param force force posting
    * @param trxName ignore, retained for backward compatibility
    * @return null, if success or error message    */
   public String postImmediate( Properties ctx, int AD_Client_ID, int AD_Table_ID,
		   int Record_ID, boolean force, String trxName);

   /**
    * Process Remote
    * @param ctx Context
    * @param pi Process Info
    * @return resulting Process Info    */
   public ProcessInfo process( Properties ctx, ProcessInfo pi );

   /**
    * Run Workflow (and wait) on Server
    * @param ctx Context
    * @param pi Process Info
    * @param AD_Workflow_ID id
    * @return process info    */
   public ProcessInfo workflow( Properties ctx, ProcessInfo pi, int AD_Workflow_ID );

   /**
    * Create EMail from Server (Request User)
    * @param ctx Context
    * @param AD_Client_ID client
    * @param to recipient email address
    * @param subject subject
    * @param message message
    * @return EMail    */
   public EMail createEMail( Properties ctx, int AD_Client_ID, String to, 
		   String subject, String message );

   /**
    * Create EMail from Server (Request User)
    * @param ctx Context
    * @param AD_Client_ID client
    * @param AD_User_ID user to send email from
    * @param to recipient email address
    * @param subject subject
    * @param message message
    * @return EMail    */
   public EMail createEMail( Properties ctx, int AD_Client_ID, int AD_User_ID,
		   String to, String subject, String message );
      
   /**
    * Execute task on server
    * @param AD_Task_ID task
    * @return execution trace    */
   public String executeTask( int AD_Task_ID );

   /**
    * Cash Reset
    * @param tableName table name
    * @param Record_ID record or 0 for all
    * @return number of records reset    */
   public int cacheReset( String tableName,int Record_ID );

   /**
    * Describes the instance and its content for debugging purpose
    * @return Debugging information about the instance and its content    */
   public String getStatus(  );

   /**
    * Execute db proces on server
    * @param processInfo
    * @param procedureName
    * @return ProcessInfo    */
   public ProcessInfo dbProcess( ProcessInfo processInfo, String procedureName );
}
