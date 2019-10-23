package de.metas;

import org.compiere.model.ModelValidationEngine;

import de.metas.logging.MetasfreshIssueAppender;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

/** Used to coordinate the ordering of components on a "global" metasfresh scale...<b>in cases where it's necessary</b>. */
@UtilityClass
public class Orders
{
	/** See {@link ApplicationReadyListener#initModelValidationEngine()}. */
	public static final int FORCE_MODEL_VALIDATION_ENGINE_INIT = 90;

	/**
	 * Make sure that {@link MetasfreshIssueAppender} is enabled only <b>after</b> the {@link ModelValidationEngine} was initialized.
	 * See {@link ApplicationReadyListener#enableIssueReporting()}
	 */
	public static final int ENABLE_ISSUE_LOG_APPENDER = 100;
}
