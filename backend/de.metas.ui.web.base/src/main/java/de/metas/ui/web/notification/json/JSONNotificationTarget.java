package de.metas.ui.web.notification.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.logging.LogManager;
import de.metas.notification.UserNotification;
import de.metas.notification.UserNotificationTargetType;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.slf4j.Logger;

import java.io.Serializable;

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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class JSONNotificationTarget implements Serializable
{
	/**
	 * @return JSON target or <code>null</code>
	 */
	static JSONNotificationTarget of(final UserNotification notification)
	{
		final UserNotificationTargetType targetType = notification.getTargetType();
		switch (targetType)
		{
			case Window:
				return JSONNotificationTarget.builder()
						.targetType(UserNotificationTargetType.Window)
						.windowId(notification.getTargetWindowIdAsString())
						.documentId(notification.getTargetDocumentId())
						.build();
			case View:
				return JSONNotificationTarget.builder()
						.targetType(UserNotificationTargetType.View)
						.windowId(notification.getTargetWindowIdAsString())
						.viewId(notification.getTargetViewId())
						.build();
			case None:
				return null;
			default:
				logger.warn("Unknown targetType={} for {}. Returning null.", targetType, notification);
				return null;
		}

	}

	private static final Logger logger = LogManager.getLogger(JSONNotificationTarget.class);

	@JsonProperty("targetType")
	UserNotificationTargetType targetType;

	//
	// Target: Window/Document
	@JsonProperty("windowId")
	@JsonInclude(JsonInclude.Include.NON_ABSENT)
	String windowId;
	//
	@JsonProperty("documentType")
	@JsonInclude(JsonInclude.Include.NON_ABSENT)
	@Deprecated
	String documentType;
	//
	@JsonProperty("documentId")
	@JsonInclude(JsonInclude.Include.NON_ABSENT)
	String documentId;

	//
	// Target: View
	@JsonProperty("viewId")
	@JsonInclude(JsonInclude.Include.NON_ABSENT)
	String viewId;

	@Builder
	@JsonCreator
	private JSONNotificationTarget(
			@JsonProperty("targetType") @NonNull final UserNotificationTargetType targetType,
			@JsonProperty("windowId") final String windowId,
			@JsonProperty("documentId") final String documentId,
			@JsonProperty("viewId") final String viewId)
	{
		this.targetType = targetType;

		this.windowId = windowId;
		this.documentType = windowId;
		this.documentId = documentId;

		this.viewId = viewId;
	}
}
