package de.metas.ui.web.board.json;

import org.adempiere.util.time.SystemTime;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;

import de.metas.ui.web.window.datatypes.json.JSONDate;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Builder
@Value
public class JSONBoardCardChangedEvent
{
	public static JSONBoardCardChangedEvent cardAdded(final int boardId, final int cardId)
	{
		return builder()
				.changeType(JSONBoardCardChangedEvent.ChangeType.add)
				.boardId(boardId)
				.cardId(cardId)
				.build();
	}
	
	public static JSONBoardCardChangedEvent cardRemoved(final int boardId, final int cardId)
	{
		return builder()
				.changeType(JSONBoardCardChangedEvent.ChangeType.remove)
				.boardId(boardId)
				.cardId(cardId)
				.build();
	}
	
	public static JSONBoardCardChangedEvent cardChanged(final int boardId, final int cardId)
	{
		return builder()
				.changeType(JSONBoardCardChangedEvent.ChangeType.change)
				.boardId(boardId)
				.cardId(cardId)
				.build();
	}



	@Default
	private final String timestamp = JSONDate.toJson(SystemTime.millis());

	private final int boardId;
	private final int cardId;

	public static enum ChangeType
	{
		add, remove, change
	}

	@NonNull
	private final ChangeType changeType;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final JSONBoardCard card;
}
