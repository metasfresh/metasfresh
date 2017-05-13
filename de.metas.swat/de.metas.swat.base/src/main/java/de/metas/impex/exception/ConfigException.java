package de.metas.impex.exception;

/*
 * #%L
 * de.metas.swat.base
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


import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;

import de.metas.i18n.Msg;

/**
 * 
 * Exception to be thrown when an edi export fails dues to (user) config problems. That's why it is a checked exception.
 * 
 * @author ts
 * 
 */
public final class ConfigException extends AdempiereException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 333990082991623238L;

	public static final String ARCHIVE_NOT_A_DIR_1P = "EdiArchiveNotADir";

	public static final String ARCHIVE_CANT_WRITE_1P = "EdiArchiveCantWrite";

	public static final String ARCHIVE_DIR_NOT_WRITABLE_1P = "EdiArchiveDirNotWriable";

	public static final String ARCHIVE_RENAME_FAILED_2P = "EdiArchiveFileRenameFailed";

	/**
	 * Message should indicate that a required record in the C_BPartner_EDI table is missing.
	 */
	public static final String BPARTNER_EDI_MISSING = "EdiBPartnerEDIMissing";

	public static final String COLUMN_MANDATORY = "EDIColumnMandatory";

	/**
	 * Message expected to be something like "Missing Date Format"
	 */
	public static final String DATE_FMT_MISSING = "EdiMissingDateFormat";

	public static final String DATE_FMT_PARSE_ERROR_2P = "EdiDateParsefailed";

	public static final String EDI_MULTI_COLUMN_NOT_SUPPORTED = "EDIMultiColumnNotSupported";

	public static final String EDI_NOT_FOUND_PERSISTENT_CLASS = "EDINotFoundPersistentClass";

	/**
	 * <li>1st param: the EDI processor where the EDI loader is defined <li>2nd param: the missing EDI loader class
	 */
	public static final String EDILOADER_CANT_INIT_2P = "EdiMissingEdiLoader";

	/**
	 * Message is used if a value would have to be changed for export in a way that changes the value's meaning.
	 * 
	 * <li>1st param: the original value before change <li>2st param: value after change <br>
	 * Example: Say, the EDI format defines <li>no escape character <li>Field separator ',' <li>Numbers are exported
	 * with ',' as decimal point <br>
	 * In that case, the field separator ',' would be required to be removed from the exported numbers, leading to e.g.
	 * 2,99 being exported as 299.
	 * 
	 */
	public static final String FIELD_EXPORT_CHANGE_2P = "EdiValueChangedByExport";

	public static final String FIELD_MANDATORY = "EDIFieldMandatory";

	public static final String FORMAT_MISSING = "EDINoFormat";

	public static final String HEADER_COLUMN_MANDATORY = "EDIHeaderColumnMandatory";

	public static final String LINE_TYPE_UNKNOWN_1P = "EdiUnknownLineType";

	/**
	 * <li>1st param: the name of the folder that can't be read
	 */
	public static final String LOCALFOLDER_CANT_READ_1P = "EdiCantReadLocalFolder";

	public static final String LOOKUP_COLUMN_LOOKUP_ERROR_2P = "EdiLookupError";

	/**
	 * <li>1st param: lookup column <li>2nd param: lookup value
	 */
	public static final String LOOKUP_COLUMN_LOOKUP_NONE_2P = "EdiLookupFoundNone";

	/**
	 * <li>1st param: lookup column <li>2nd param: lookup value
	 */
	public static final String LOOKUP_COLUMN_LOOKUP_TOOMANY_2P = "EdiLookupFoundTooMany";

	public static final String MISSING_EMBEDDED_DATA = "EdiMissingEmbeddedData";

	public static final String MISSING_EVENTLISTENER_1P = "EDIMissingEventListenerClass";

	public static final String MULTIPLE_BPARTNER_LOCATIONS_1P = "EdiMultipleBPartnerLocations";

	public static final String ORG_GLN_MISSING_1P = "EdiMissingOrgGLN";

	public static final String PROCESSOR_MISSING = "EDINoProcessor";

	public static final String SEGMENT_UNEXPECTED_END = "EdiUnexpectedSegmentEnd";

	/**
	 * <li>1st param: exported edi data
	 */
	public static final String SUBMIT_EDI_RESULT_1P = "EDISubmitProcessResult";

	public static final String VARIABLE_EVAL_FAILED_1P = "EdiVariableEvalFailed";

	public static final String VARIABLE_EXPR_MISSING = "EdiVariableExpressionMissing";

	public static final String FILE_ACCESS_FAILED_2P = "ImpexFileAccessFailed_2P";

	public ConfigException(final String adMsg, Object... msgParam)
	{

		super(Msg.getMsg(Env.getCtx(), adMsg, msgParam));
	}

	public ConfigException(final String adMsg, Throwable cause, Object... msgParam)
	{
		super(Msg.getMsg(Env.getCtx(), adMsg, msgParam), cause);
	}

	public static void throwNew(final String adMsg, Object... msgParam)
			throws ConfigException
	{
		throwNew(adMsg, null, msgParam);
	}

	public static void throwNew(final String adMsg, Throwable cause, Object... msgParam)
			throws ConfigException
	{
		final String msg = Msg.getMsg(Env.getCtx(), adMsg, msgParam);
		throw new ConfigException(msg, cause);
	}

}
