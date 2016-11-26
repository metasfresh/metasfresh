package de.metas.ui.web.notification.json;

import java.io.Serializable;

import org.slf4j.Logger;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.logging.LogManager;
import de.metas.ui.web.notification.UserNotification;
import de.metas.ui.web.notification.UserNotification.TargetType;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@SuppressWarnings("serial")
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class JSONNotificationTarget implements Serializable
{
	/**
	 * @param notification
	 * @return JSON target or <code>null</code>
	 */
	static final JSONNotificationTarget of(final UserNotification notification)
	{
		final TargetType targetType = notification.getTargetType();
		switch (targetType)
		{
			case Window:
				final JSONNotificationTarget jsonTarget = new JSONNotificationTarget(targetType);
				jsonTarget.documentType = notification.getTargetDocumentType();
				jsonTarget.documentId = notification.getTargetDocumentId();
				return jsonTarget;
			case None:
				return null;
		}

		logger.warn("Unknown targetType={} for {}. Returning null.", targetType, notification);
		return null;
	}

	private static final Logger logger = LogManager.getLogger(JSONNotificationTarget.class);

	@JsonProperty("targetType")
	private final TargetType targetType;

	//
	// Target: Window/Document
	@JsonProperty("documentType")
	@JsonInclude(JsonInclude.Include.NON_ABSENT)
	private String documentType;
	//
	@JsonProperty("documentId")
	@JsonInclude(JsonInclude.Include.NON_ABSENT)
	private String documentId;

	private JSONNotificationTarget(final TargetType targetType)
	{
		super();
		this.targetType = targetType;
	}

	public TargetType getTargetType()
	{
		return targetType;
	}

	public String getDocumentType()
	{
		return documentType;
	}

	public String getDocumentId()
	{
		return documentId;
	}
}
