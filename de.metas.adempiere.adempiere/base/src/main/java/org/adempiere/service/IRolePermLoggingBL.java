package org.adempiere.service;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBForeignKeyConstraintException;

import de.metas.document.DocTypeId;
import de.metas.util.ISingletonService;

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
	public void logDocActionAccess(int AD_Role_ID, DocTypeId docTypeId, String docAction, Boolean access);

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
