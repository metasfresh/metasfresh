package de.metas.printing;

/*
 * #%L
 * de.metas.printing.base
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

import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.util.Services;
import org.adempiere.service.ISysConfigBL;

import java.math.BigDecimal;

public final class Printing_Constants
{
	private Printing_Constants()
	{
	}

	public static final String ENTITY_TYPE = "de.metas.printing";

	/**
	 * @see http://dewiki908/mediawiki/index.php/03758_Drucker_Kalibrierung_%282013010410000055%29# Detailbeschreibung_fachlich
	 */
	public static final BigDecimal AD_PrinterHW_Calibration_JASPER_PIXEL_PER_MM = new BigDecimal("2.835");

	/**
	 * @see http://dewiki908/mediawiki/index.php/03758_Drucker_Kalibrierung_%282013010410000055%29# Detailbeschreibung_fachlich
	 */
	public static final BigDecimal AD_PrinterHW_Calibration_JASPER_REF_X_MM = new BigDecimal("170.04");

	/**
	 * @see http://dewiki908/mediawiki/index.php/03758_Drucker_Kalibrierung_%282013010410000055%29# Detailbeschreibung_fachlich
	 */
	public static final BigDecimal AD_PrinterHW_Calibration_JASPER_REF_Y_MM = new BigDecimal("170.04");

	public static final String SYSCONFIG_Printing_PREFIX = ENTITY_TYPE + ".";

	public static final String SYSCONFIG_PrintingClientApplet_PREFIX = SYSCONFIG_Printing_PREFIX + "client.";

	private static final String SYSCONFIG_Enabled = ENTITY_TYPE + ".Enabled";

	/**
	 * Internal name for pdf printing async batch
	 */
	public static final String C_Async_Batch_InternalName_PDFPrinting = "PDFPrinting";

	public static final Topic USER_NOTIFICATIONS_TOPIC = Topic.builder()
			.name("de.metas.printing.UserNotifications")
			.type(Type.DISTRIBUTED)
			.build();

	/**
	 * @return true if printing module is enabled; false if printing module is fully disabled
	 */
	public static final boolean isEnabled()
	{
		final boolean defaultValue = true; // enabled by default
		return Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_Enabled, defaultValue);
	}
}
