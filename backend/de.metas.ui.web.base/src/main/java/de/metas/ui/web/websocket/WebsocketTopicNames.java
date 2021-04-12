package de.metas.ui.web.websocket;

import com.google.common.base.Preconditions;

import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.user.UserId;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * metasfresh-webui-api
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@UtilityClass
public class WebsocketTopicNames
{
	static final String TOPIC_UserSession = "/userSession";
	static final String TOPIC_Notifications = "/notifications";
	static final String TOPIC_View = "/view";
	static final String TOPIC_Document = "/document";
	static final String TOPIC_Board = "/board";
	public static final String TOPIC_Dashboard = "/dashboard";
	public static final String TOPIC_Devices = "/devices";

	public static WebsocketTopicName buildUserSessionTopicName(@NonNull final UserId adUserId)
	{
		return WebsocketTopicName.ofString(TOPIC_UserSession + "/" + adUserId.getRepoId());
	}

	public static WebsocketTopicName buildNotificationsTopicName(@NonNull final UserId adUserId)
	{
		return WebsocketTopicName.ofString(TOPIC_Notifications + "/" + adUserId.getRepoId());
	}

	public static WebsocketTopicName buildViewNotificationsTopicName(@NonNull final String viewId)
	{
		Check.assumeNotEmpty(viewId, "viewId is not empty");
		return WebsocketTopicName.ofString(TOPIC_View + "/" + viewId);
	}

	public static WebsocketTopicName buildDocumentTopicName(
			@NonNull final WindowId windowId,
			@NonNull final DocumentId documentId)
	{
		return WebsocketTopicName.ofString(TOPIC_Document + "/" + windowId.toJson() + "/" + documentId.toJson());
	}

	public static WebsocketTopicName buildBoardTopicName(final int boardId)
	{
		Preconditions.checkArgument(boardId > 0);
		return WebsocketTopicName.ofString(TOPIC_Board + "/" + boardId);
	}
}
