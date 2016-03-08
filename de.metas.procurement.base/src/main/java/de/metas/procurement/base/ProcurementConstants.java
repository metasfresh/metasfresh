package de.metas.procurement.base;

import org.compiere.util.CLogger;

import de.metas.event.Topic;
import de.metas.event.Type;

/*
 * #%L
 * de.metas.procurement.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public final class ProcurementConstants
{
	private static final String LOGGER_NAME = CLogger.createModuleLoggerNameForPackage(ProcurementConstants.class);

	public static final CLogger getLogger()
	{
		return CLogger.getCLogger(LOGGER_NAME);
	}
	
	public static final Topic EVENTBUS_TOPIC_PurchaseOrderGenerated = Topic.builder()
			.setName("de.metas.procurement.base.PurchaseOrderGenerated")
			.setType(Type.REMOTE)
			.build();


	private ProcurementConstants()
	{
	}
}
