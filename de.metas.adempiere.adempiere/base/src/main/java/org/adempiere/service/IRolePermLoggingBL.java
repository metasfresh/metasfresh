package org.adempiere.service;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBForeignKeyConstraintException;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.util.ISingletonService;

public interface IRolePermLoggingBL extends ISingletonService
{
	/**
	 * @throws NoSuchForeignKeyException if there is no DB record for the given {@code id}.
	 */
	public void logWindowAccess(int AD_Role_ID, int id, Boolean access);

	/**
	 * @throws NoSuchForeignKeyException if there is no DB record for the given {@code id}.
	 */
	public void logWindowAccess(int AD_Role_ID, int id, Boolean access, String description);

	/**
	 * @throws NoSuchForeignKeyException if there is no DB record for the given {@code id}.
	 */
	public void logFormAccess(int AD_Role_ID, int id, Boolean access);

	/**
	 * @throws NoSuchForeignKeyException if there is no DB record for the given {@code id}.
	 */
	public void logProcessAccess(int AD_Role_ID, int id, Boolean access);

	/**
	 * @throws NoSuchForeignKeyException if there is no DB record for the given {@code id}.
	 */
	public void logTaskAccess(int AD_Role_ID, int id, Boolean access);

	/**
	 * @throws NoSuchForeignKeyException if there is no DB record for the given {@code id}.
	 */
	public void logWorkflowAccess(int AD_Role_ID, int id, Boolean access);

	/**
	 * @throws NoSuchForeignKeyException if there is no DB record for the given {@code C_DocType_ID}.
	 */
	public void logDocActionAccess(int AD_Role_ID, int C_DocType_ID, String docAction, Boolean access);

	/**
	 * Thrown by the {@code log*()} methods if the respective given {@code id} does not reference an actually existing record.
	 * 
	 * @author metas-dev <dev@metasfresh.com>
	 *
	 */
	public class NoSuchForeignKeyException extends AdempiereException
	{
		private static final long serialVersionUID = -2418261128456411589L;

		public NoSuchForeignKeyException(final String message, final DBForeignKeyConstraintException cause)
		{
			super(message, cause);
		}
	}
}
