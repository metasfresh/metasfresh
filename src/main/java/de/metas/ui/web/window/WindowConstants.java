package de.metas.ui.web.window;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;

import com.google.common.collect.ImmutableSet;

import de.metas.logging.LogManager;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Window miscellaneous constants.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
// NOTE to dev: please keep this class in the root package (e.g. de.metas.ui.web.window) because at least the "logger" depends on this
public final class WindowConstants
{
	/**
	 * Root logger for "window" functionality.
	 *
	 * Changing the level of this logger will affect all loggers.
	 */
	public static final Logger logger = LogManager.getLogger(WindowConstants.class.getPackage().getName());

	public static final String FIELDNAME_DocumentNo = "DocumentNo";
	public static final String FIELDNAME_Value = "Value";
	public static final String FIELDNAME_Name = "Name";
	public static final String FIELDNAME_IsActive = "IsActive";
	public static final String FIELDNAME_DocStatus = "DocStatus";
	public static final String FIELDNAME_DocAction = "DocAction";
	public static final String FIELDNAME_IsSOTrx = "IsSOTrx";
	public static final String FIELDNAME_Processing = "Processing";
	public static final String FIELDNAME_Processed = "Processed";
	public static final String FIELDNAME_C_DocType_ID = "C_DocType_ID";
	public static final String FIELDNAME_C_DocTypeTarget_ID = "C_DocTypeTarget_ID";
	public static final String FIELDNAME_OrderType = "OrderType";
	public static final String FIELDNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";
	public static final String FIELDNAME_Line = "Line";

	public static final String FIELDNAME_AD_Client_ID = "AD_Client_ID";
	public static final String FIELDNAME_AD_Org_ID = "AD_Org_ID";
	public static final String FIELDNAME_Created = "Created";
	public static final String FIELDNAME_CreatedBy = "CreatedBy";
	public static final String FIELDNAME_Updated = "Updated";
	public static final String FIELDNAME_UpdatedBy = "UpdatedBy";
	public static final Set<String> FIELDNAMES_CreatedUpdated = ImmutableSet.of(FIELDNAME_Created, FIELDNAME_CreatedBy, FIELDNAME_Updated, FIELDNAME_UpdatedBy);

	public static final String FIELDNAME_DocumentSummary = "V$DocumentSummary";

	public static final String CONTEXTVAR_NextLineNo = "CtxVar_NextLineNo";

	private WindowConstants()
	{
	}

	private static final AtomicBoolean protocolDebugging = new AtomicBoolean(false);

	public static boolean isProtocolDebugging()
	{
		return protocolDebugging.get();
	}

	public static void setProtocolDebugging(final boolean protocolDebugging)
	{
		final boolean protocolDebuggingPrev = WindowConstants.protocolDebugging.getAndSet(protocolDebugging);

		if (protocolDebuggingPrev == protocolDebugging)
		{
			return;
		}
		System.out.println("--------------------------------------------------------------------------------------------");
		if (protocolDebugging)
		{
			System.out.println("Protocol debugging was enabled");
		}
		else
		{
			System.out.println("Protocol debugging was disabled");
		}
		System.out.println("--------------------------------------------------------------------------------------------");
	}
}
