/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interface to wrap PreparedStatement
 * 
 * @author Low Heng Sin
 *
 */
public interface CPreparedStatement extends CStatement, PreparedStatement
{
	/**
	 * Same as {@link #executeQuery()} but it will log migration scripts too (if they are enabled).
	 * 
	 * Please note, {@link #executeQuery()} does not log migration scripts because usually they are SELECTs which does not change the database, so no migration scripts shall be logged.
	 */
	ResultSet executeQueryAndLogMigationScripts() throws SQLException;
}	// CPreparedStatement
