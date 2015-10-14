package org.compiere.interfaces;

/*
 * #%L
 * ADempiere ERP - Base
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

/**
 * Interface for adempiere/Status.
 */
public interface Status
{
   public final static String JNDI_NAME="adempiere/Status";
   
   public final static String EJB_NAME="adempiereStatus";
   
   /**
    * Get Version (Date)
    * @return version e.g. 2002-09-02    */
   public String getDateVersion(  );

   /**
    * Get Main Version
    * @return main version - e.g. Version 2.4.3b    */
   public String getMainVersion(  );

   /**
    * Get Database Type
    * @return Database Type    */
   public String getDbType(  );

   /**
    * Get Database Host
    * @return Database Host Name    */
   public String getDbHost(  );

   /**
    * Get Database Port
    * @return Database Port    */
   public int getDbPort(  );

   /**
    * Get Database SID
    * @return Database SID    */
   public String getDbName(  );

   /**
    * Get Database URL
    * @return Database URL    */
   public String getConnectionURL(  );

   /**
    * Get Database UID
    * @return Database User Name    */
   public String getDbUid(  );

   /**
    * Get Database PWD
    * @return Database User Password    */
   public String getDbPwd(  );

   /**
    * Get Connection Manager Host
    * @return Connection Manager Host    */
   public String getFwHost(  );

   /**
    * Get Connection Manager Port
    * @return Connection Manager Port    */
   public int getFwPort(  );

   /**
    * Get Version Count
    * @return number of version inquiries    */
   public int getVersionCount(  );

   /**
    * Get Database Count
    * @return number of database inquiries    */
   public int getDatabaseCount(  );

   /**
    * Describes the instance and its content for debugging purpose
    * @return Debugging information about the instance and its content    */
   public String getStatus(  );
}
