package de.metas.ui.web.board;

import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.GuavaCollectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Multimap;

import de.metas.ui.web.board.BoardCardChangeRequest.BoardCardChangeRequestBuilder;
import de.metas.ui.web.board.json.JSONBoard;
import de.metas.ui.web.board.json.JSONBoard.JSONBoardBuilder;
import de.metas.ui.web.board.json.JSONBoardCard;
import de.metas.ui.web.board.json.JSONBoardCardAddRequest;
import de.metas.ui.web.board.json.JSONBoardLane;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewResult;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.view.json.JSONViewResult;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;

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

@RestController
@RequestMapping(BoardRestController.ENDPOINT)
public class BoardRestController
{
	public static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/board";

	@Autowired
	private UserSession userSession;

	@Autowired
	private BoardDescriptorRepository boardsRepo;

	@Autowired
	private IViewsRepository viewsRepo;

	@GetMapping("/{boardId}")
	public JSONBoard getBoard(@PathVariable("boardId") final int boardId)
	{
		userSession.assertLoggedIn();

		final String adLanguage = userSession.getAD_Language();
		final BoardDescriptor boardDescriptor = boardsRepo.getBoardDescriptor(boardId);

		final Multimap<Integer, JSONBoardCard> cardsByLaneId = boardsRepo.getCards(boardId)
				.stream()
				.map(card -> JSONBoardCard.of(card, adLanguage))
				.collect(GuavaCollectors.toImmutableListMultimap(JSONBoardCard::getLaneId));

		final JSONBoardBuilder jsonBoard = JSONBoard.builder()
				.boardId(boardId)
				.caption(boardDescriptor.getCaption().translate(adLanguage))
				.websocketEndpoint(boardDescriptor.getWebsocketEndpoint());

		boardDescriptor.getLanes()
				.values().stream()
				.map(lane -> JSONBoardLane.builder()
						.laneId(lane.getLaneId())
						.caption(lane.getCaption().translate(adLanguage))
						.cards(cardsByLaneId.get(lane.getLaneId()))
						.build())
				.forEach(jsonBoard::lane);

		return jsonBoard.build();
	}

	@PostMapping("/{boardId}/card")
	public JSONBoardCard addCard(@PathVariable("boardId") final int boardId, @RequestBody final JSONBoardCardAddRequest request)
	{
		userSession.assertLoggedIn();

		final BoardCard card = boardsRepo.addCardForDocumentId(boardId, request.getLaneId(), request.getDocumentId());
		return JSONBoardCard.of(card, userSession.getAD_Language());
	}

	@PostMapping("/{boardId}/card/{cardId}")
	public JSONBoardCard getCard(@PathVariable("boardId") final int boardId, @PathVariable("cardId") final int cardId)
	{
		final BoardCard card = boardsRepo.getCard(boardId, cardId);
		return JSONBoardCard.of(card, userSession.getAD_Language());
	}

	@DeleteMapping("/{boardId}/card/{cardId}")
	public void removeCard(@PathVariable("boardId") final int boardId, @PathVariable("cardId") final int cardId)
	{
		userSession.assertLoggedIn();

		boardsRepo.removeCard(boardId, cardId);
	}

	@PatchMapping("/{boardId}/card/{cardId}")
	public JSONBoardCard patchCard(@PathVariable("boardId") final int boardId, @PathVariable("cardId") final int cardId, @RequestBody final List<JSONDocumentChangedEvent> changes)
	{
		userSession.assertLoggedIn();

		final BoardCard card = boardsRepo.changeCard(boardId, cardId, createBoardCardChangeRequest(changes));
		return JSONBoardCard.of(card, userSession.getAD_Language());
	}

	private static final BoardCardChangeRequest createBoardCardChangeRequest(final List<JSONDocumentChangedEvent> changes)
	{
		if (changes.isEmpty())
		{
			throw new AdempiereException("no changes requested");
		}

		final BoardCardChangeRequestBuilder request = BoardCardChangeRequest.builder();
		for (final JSONDocumentChangedEvent change : changes)
		{
			if (!change.isReplace())
			{
				continue;
			}

			if ("laneId".equals(change.getPath()))
			{
				request.newLaneId(change.getValueAsInteger(-1));
			}
		}

		return request.build();
	}

	@GetMapping("/{boardId}/newCardsView")
	public JSONViewResult get(@PathVariable("boardId") final int boardId)
	{
		userSession.assertLoggedIn();

		final BoardDescriptor boardDescriptor = boardsRepo.getBoardDescriptor(boardId);

		final CreateViewRequest request = CreateViewRequest.builder(boardDescriptor.getRecordWindowId(), JSONViewDataType.list)
				// TODO: apply board's AD_Val_Rule_ID
				.build();
		final IView view = viewsRepo.createView(request);
		return JSONViewResult.of(ViewResult.ofView(view));
	}
}
