package de.metas;

import org.compiere.model.ModelValidationEngine;
import org.slf4j.Logger;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import de.metas.logging.LogManager;
import de.metas.logging.MetasfreshIssueAppender;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Component
public class ApplicationReadyListener
{
	private static final Logger logger = LogManager.getLogger(ApplicationReadyListener.class);

	@EventListener(ApplicationReadyEvent.class)
	@Order(Orders.FORCE_MODEL_VALIDATION_ENGINE_INIT)
	public void initModelValidationEngine()
	{
		logger.info("Forcing ModelValidationEngine to be inittialized if it wasn't done yet");
		ModelValidationEngine.get();
	}

	@EventListener(ApplicationReadyEvent.class)
	@Order(Orders.ENABLE_ISSUE_LOG_APPENDER) // make sure this executes before ApplicationReadyEvent listeners that have no explicit order
	public void enableIssueReporting()
	{
		final MetasfreshIssueAppender metasfreshIssueAppender = MetasfreshIssueAppender.get();
		if (metasfreshIssueAppender == null)
		{
			logger.info("MetasfreshIssueAppender is NOT configured => won't create AD_Issue records for error log messages ");
			return;
		}
		logger.info("Enabling MetasfreshIssueAppender to create AD_Issue records for error log messages");
		metasfreshIssueAppender.enableIssueReporting();
	}

}
