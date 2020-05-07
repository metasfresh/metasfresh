package org.adempiere.exceptions;

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


import org.adempiere.util.Services;

import de.metas.i18n.IMsgBL;

/**
 * Exception thrown on database foreign key violation.
 * 
 * @author tsa
 *
 */
public class DBForeignKeyConstraintException extends DBException
{

	/**
	 *
	 */
	private static final long serialVersionUID = -2810401877944246237L;

	private static final String AD_Message = "DBForeignKeyConstraintException";
	
	public DBForeignKeyConstraintException(final Throwable cause)
	{
		super(cause);
	}

	@Override
	protected String buildMessage()
	{
		// TODO:
		// * extract the foreign key name
		// * find the local and foreign table and column names
		// * display a nice error message: Datensatz {} kann nicht gelöscht werden, weil er von anderen Datensätzen referenziert wird

		// NOTE:
		// * here is how others extracted the foreign key name: org.hibernate.dialect.new TemplatedViolatedConstraintNameExtracter() {...}.extractConstraintName(SQLException)

		// Examples or error messages:
		// ERROR: update or delete on table "c_bpartner" violates foreign key constraint "cbpartner_ppmrp" on table "pp_mrp"
		// Detail: Key (c_bpartner_id)=(2000037) is still referenced from table "pp_mrp".
		//

		return Services.get(IMsgBL.class).getMsg(getADLanguage(), AD_Message)
				+ "\n\n" + super.buildMessage();
	}

}
