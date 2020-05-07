package de.metas.document.references;

import java.util.Properties;

import org.adempiere.ad.element.api.AdWindowId;
import org.compiere.model.I_AD_Column;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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


public interface IZoomSource
{
	Properties getCtx();

	Evaluatee createEvaluationContext();

	String getTrxName();

	AdWindowId getAD_Window_ID();

	String getTableName();

	int getAD_Table_ID();

	/**
	 * The name of the column from which the zoom originates.<br>
	 * In other words, the name of the column for which the system tries to load {@link IZoomProvider}s.
	 *
	 * Example: when looking for zoom-targets from a {@code C_Order} window, then this is {@code C_Order_ID}.
	 *
	 * @return The name of the single ID column which has {@link I_AD_Column#COLUMN_IsGenericZoomOrigin} {@code ='Y'}.
	 *         May also return {@code null}. In this case, expect no exception, but also no zoom providers to be created.
	 */
	String getKeyColumnNameOrNull();

	/**
	 * @return {@code true} if the zoom source shall be considered by {@link GenericZoomProvider}.
	 *         We have dedicated flag because the generic zoom provide can be very nice<br>
	 *         (no need to set up relation types) but also very performance intensive.
	 */
	boolean isGenericZoomOrigin();

	int getRecord_ID();

	default boolean isSOTrx()
	{
		return Env.isSOTrx(getCtx());
	}

	boolean hasField(String columnName);

	Object getFieldValue(String columnName);

	default boolean getFieldValueAsBoolean(final String columnName)
	{
		return DisplayType.toBoolean(getFieldValue(columnName));
	}
}