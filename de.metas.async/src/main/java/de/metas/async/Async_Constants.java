package de.metas.async;

import java.util.Properties;

/*
 * #%L
 * de.metas.async
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


import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.event.Topic;
import de.metas.event.Type;

public final class Async_Constants
{
	public static final String MSG_WORKPACKAGES_CREATED = "de.metas.async.C_QueueC_WorkPackages_Created_1P";

	public static final String ENTITY_TYPE = "de.metas.async";

	/**
	 * If a package has been skipped by a {@link IWorkpackageProcessor}, then this constant is the default timeout before that work package is once again returned by
	 * {@link #retrieveAndLockNextWorkPackage(Properties, int, int)}.
	 *
	 */
	public static final int DEFAULT_RETRY_TIMEOUT_MILLIS = 5000;

	public static final String C_Async_Batch = "C_Async_Batch";

	public static final Topic EVENTBUS_WORKPACKAGE_PROCESSING_ERRORS = Topic.builder()
			.name("de.metas.async.WorkpackageProcessingErrors")
			.type(Type.REMOTE)
			.build();
}
