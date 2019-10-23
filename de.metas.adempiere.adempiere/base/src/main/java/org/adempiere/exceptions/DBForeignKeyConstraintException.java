package org.adempiere.exceptions;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;

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
	protected ITranslatableString buildMessage()
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

		return TranslatableStrings.builder()
				.appendADMessage(AD_Message)
				.append("\n")
				.append("\n").appendADElement("Cause").append(": ").append(extractMessage(getCause()))
				.append("\n").append(super.buildMessage())
				.build();
	}

}
