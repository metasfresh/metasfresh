package de.metas.ui.web.board;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.comparator.FixedOrderByKeyComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;

import de.metas.ui.web.board.BoardCardChangeRequest.BoardCardChangeRequestBuilder;
import de.metas.ui.web.board.json.JSONBoard;
import de.metas.ui.web.board.json.JSONBoard.JSONBoardBuilder;
import de.metas.ui.web.board.json.JSONBoardCard;
import de.metas.ui.web.board.json.JSONBoardCardAddRequest;
import de.metas.ui.web.board.json.JSONBoardCardOrderBy;
import de.metas.ui.web.board.json.JSONBoardLane;
import de.metas.ui.web.board.json.JSONNewCardsViewLayout;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.document.filter.json.JSONDocumentFilterDescriptor;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewRowOverrides;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewResult;
import de.metas.ui.web.view.ViewRowOverridesHelper;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.event.ViewChangesCollector;
import de.metas.ui.web.view.json.JSONFilterViewRequest;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.view.json.JSONViewResult;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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

	private final ConcurrentHashMap<Integer, Set<IView>> boardId2newCardsViewId = new ConcurrentHashMap<>();

	private JSONOptions newJSONOptions()
	{
		return JSONOptions.builder(userSession).build();
	}

	private void addActiveNewCardsView(final int boardId, final IView view)
	{
		final Set<IView> boardActiveViews = boardId2newCardsViewId.computeIfAbsent(boardId, id -> Collections.newSetFromMap(new WeakHashMap<>()));
		synchronized (boardActiveViews)
		{
			boardActiveViews.add(view);
		}
	}

	private void invalidateAllNewCardsViews(final int boardId)
	{
		final Set<IView> boardActiveViews = boardId2newCardsViewId.get(boardId);
		if (boardActiveViews == null)
		{
			return;
		}

		synchronized (boardActiveViews)
		{
			// NOTE: because we are actually not deleting from view but just filtering out the board cardIds,
			// we don't have to actually invalidate the view but just fire an "refresh request" to frontend
			boardActiveViews.forEach(view -> ViewChangesCollector.getCurrentOrAutoflush()
					.collectFullyChanged(view));
		}
	}

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

		final int position = request.getPosition() != null ? request.getPosition() : Integer.MAX_VALUE;
		final BoardCard card = boardsRepo.addCardForDocumentId(boardId, request.getLaneId(), request.getDocumentId(), position);
		invalidateAllNewCardsViews(boardId);
		return JSONBoardCard.of(card, userSession.getAD_Language());
	}

	@GetMapping("/{boardId}/card/{cardId}")
	public JSONBoardCard getCard(@PathVariable("boardId") final int boardId, @PathVariable("cardId") final int cardId)
	{
		userSession.assertLoggedIn();

		final BoardCard card = boardsRepo.getCard(boardId, cardId);
		return JSONBoardCard.of(card, userSession.getAD_Language());
	}

	@GetMapping("/{boardId}/card")
	@ApiOperation("gets cards indexed by cardId")
	public Map<Integer, JSONBoardCard> getCards(@PathVariable("boardId") final int boardId,
			@RequestParam("cardIds") @ApiParam("comma separated cardIds") final String cardIdsListStr)
	{
		userSession.assertLoggedIn();

		final Set<Integer> cardIds = DocumentIdsSelection.ofCommaSeparatedString(cardIdsListStr).toIntSet();
		final List<BoardCard> cards = boardsRepo.getCards(boardId, cardIds);

		final String adLanguage = userSession.getAD_Language();
		return cards.stream()
				.map(card -> JSONBoardCard.of(card, adLanguage))
				.collect(GuavaCollectors.toImmutableMapByKey(JSONBoardCard::getCardId));
	}

	@DeleteMapping("/{boardId}/card/{cardId}")
	public void removeCard(@PathVariable("boardId") final int boardId, @PathVariable("cardId") final int cardId)
	{
		userSession.assertLoggedIn();

		boardsRepo.removeCard(boardId, cardId);
		invalidateAllNewCardsViews(boardId);
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
			if ("position".equals(change.getPath()))
			{
				request.newPosition(change.getValueAsInteger(-1));
			}
		}

		return request.build();
	}

	@PostMapping("/{boardId}/newCardsView")
	public JSONViewResult createNewCardsView(@PathVariable("boardId") final int boardId)
	{
		userSession.assertLoggedIn();

		final BoardDescriptor boardDescriptor = boardsRepo.getBoardDescriptor(boardId);

		final CreateViewRequest request = CreateViewRequest.builder(boardDescriptor.getDocumentWindowId(), JSONViewDataType.list)
				.setStickyFilters(boardDescriptor.getDocumentFilters())
				.build();
		final IView view = viewsRepo.createView(request);
		addActiveNewCardsView(boardId, view);

		return toJSONCardsViewResult(boardId, view, userSession.getAD_Language());
	}

	@GetMapping("/{boardId}/newCardsView/layout")
	public JSONNewCardsViewLayout getNewCardsViewLayout(@PathVariable("boardId") final int boardId)
	{
		userSession.assertLoggedIn();

		final BoardDescriptor boardDescriptor = boardsRepo.getBoardDescriptor(boardId);

		final ViewLayout documentsViewLayout = viewsRepo.getViewLayout(boardDescriptor.getDocumentWindowId(), JSONViewDataType.list, ViewProfileId.NULL);

		final JSONOptions jsonOpts = newJSONOptions();
		final String adLanguage = jsonOpts.getAD_Language();
		return JSONNewCardsViewLayout.builder()
				.caption(documentsViewLayout.getCaption(adLanguage))
				.description(documentsViewLayout.getDescription(adLanguage))
				.emptyResultHint(documentsViewLayout.getEmptyResultHint(adLanguage))
				.emptyResultText(documentsViewLayout.getEmptyResultText(adLanguage))
				.filters(JSONDocumentFilterDescriptor.ofCollection(documentsViewLayout.getFilters(), jsonOpts))
				.orderBys(boardDescriptor.getCardFields().stream()
						.map(cardField -> JSONBoardCardOrderBy.builder()
								.fieldName(cardField.getFieldName())
								.caption(cardField.getCaption().translate(adLanguage))
								.build())
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	@GetMapping("/{boardId}/newCardsView/{viewId}")
	public JSONViewResult getNewCardsView(
			@PathVariable("boardId") final int boardId,
			@PathVariable("viewId") final String viewIdStr,
			@RequestParam("firstRow") final int firstRow,
			@RequestParam("pageLength") final int pageLength,
			@RequestParam(name = "orderBy", required = false) final String orderBysListStr)
	{
		userSession.assertLoggedIn();

		final ViewResult viewResult = viewsRepo.getView(viewIdStr)
				.getPageWithRowIdsOnly(firstRow, pageLength, DocumentQueryOrderBy.parseOrderBysList(orderBysListStr));

		final List<Integer> boardCardIds = boardsRepo.retrieveCardIds(boardId);

		return toJSONCardsViewResult(boardId, viewResult,
				userSession.getAD_Language(), // language
				cardId -> !boardCardIds.contains(cardId) // filter out cards which already exist in our board
		);
	}

	@PostMapping("/{boardId}/newCardsView/{viewId}/filter")
	public JSONViewResult filterNewCardsView( //
			@PathVariable("boardId") final int boardId,
			@PathVariable("viewId") final String viewIdStr,
			@RequestBody final JSONFilterViewRequest jsonRequest)
	{
		final ViewId viewId = ViewId.ofViewIdString(viewIdStr);

		final IView newView = viewsRepo.filterView(viewId, jsonRequest);
		return toJSONCardsViewResult(boardId, newView, userSession.getAD_Language());
	}

	@GetMapping("/{boardId}/newCardsView/{viewId}/filter/{filterId}/field/{parameterName}/typeahead")
	public JSONLookupValuesList getFilterParameterTypeahead(
			@PathVariable("boardId") final int boardId,
			@PathVariable("viewId") final String viewIdStr,
			@PathVariable("filterId") final String filterId,
			@PathVariable("parameterName") final String parameterName,
			@RequestParam(name = "query", required = true) final String query)
	{
		userSession.assertLoggedIn();

		final ViewId viewId = ViewId.ofViewIdString(viewIdStr);
		return viewsRepo.getView(viewId)
				.getFilterParameterTypeahead(filterId, parameterName, query, userSession.toEvaluatee())
				.transform(JSONLookupValuesList::ofLookupValuesList);
	}

	@GetMapping("/{boardId}/newCardsView/{viewId}/filter/{filterId}/field/{parameterName}/dropdown")
	public JSONLookupValuesList getFilterParameterDropdown(
			@PathVariable("boardId") final int boardId,
			@PathVariable("viewId") final String viewIdStr,
			@PathVariable("filterId") final String filterId,
			@PathVariable("parameterName") final String parameterName)
	{
		userSession.assertLoggedIn();

		final ViewId viewId = ViewId.ofViewIdString(viewIdStr);
		return viewsRepo.getView(viewId)
				.getFilterParameterDropdown(filterId, parameterName, userSession.toEvaluatee())
				.transform(JSONLookupValuesList::ofLookupValuesList);
	}

	private final JSONViewResult toJSONCardsViewResult(final int boardId, final ViewResult viewResult, final String adLanguage, Predicate<Integer> cardIdFilter)
	{
		final List<Integer> cardIds = viewResult.getRowIds()
				.stream()
				.filter(DocumentId::isInt).map(DocumentId::toInt)
				.filter(cardIdFilter)
				.collect(ImmutableList.toImmutableList());

		final List<JSONBoardCard> jsonCards = boardsRepo.retrieveCardCandidates(boardId, cardIds)
				.stream()
				.map(card -> JSONBoardCard.of(card, adLanguage))
				.sorted(FixedOrderByKeyComparator.notMatchedAtTheEnd(cardIds, JSONBoardCard::getCardId))
				.collect(ImmutableList.toImmutableList());

		return JSONViewResult.of(viewResult, jsonCards, adLanguage);
	}

	private final JSONViewResult toJSONCardsViewResult(final int boardId, final IView view, final String adLanguage)
	{
		final ViewResult viewResult = ViewResult.ofView(view);
		final IViewRowOverrides rowOverrides = ViewRowOverridesHelper.getViewRowOverrides(view);
		return JSONViewResult.of(viewResult, rowOverrides, adLanguage);
	}
}
