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
package org.compiere.db;

import org.adempiere.exceptions.DBException;

/**
 * General Database Constants and Utilities
 *
 * @author Jorg Janke
 * @version $Id: Database.java,v 1.3 2006/07/30 00:55:13 jjanke Exp $
 */
public class Database
{
	/** Oracle ID */
	public static final String DB_ORACLE = "Oracle";
	/** PostgreSQL ID */
	public static final String DB_POSTGRESQL = "PostgreSQL";

	/** Supported Databases */
	public static final String[] DB_NAMES = new String[] {
			DB_POSTGRESQL
			// , DB_ORACLE // not supported
	};

	/** Database Classes */
	private static final Class<?>[] DB_CLASSES = new Class<?>[] {
			DB_PostgreSQL.class
			, DB_Oracle.class
	};

	/** Connection Timeout in seconds */
	public static int CONNECTION_TIMEOUT = 10;

	/**
	 * Create a new {@link AdempiereDatabase} of given database type.
	 * 
	 * @return database instance
	 */
	public static AdempiereDatabase newAdempiereDatabase(final String type) throws Exception
	{
		for (int i = 0; i < Database.DB_NAMES.length; i++)
		{
			if (Database.DB_NAMES[i].equals(type))
			{
				return (AdempiereDatabase)Database.DB_CLASSES[i].newInstance();
			}
		}

		throw new DBException("No AdempiereDatabase found for type: " + type);
	}
}   // Database
