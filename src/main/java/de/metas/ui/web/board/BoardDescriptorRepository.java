package de.metas.ui.web.board;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.CompositeStringExpression;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.RecordZoomWindowFinder;
import org.adempiere.util.Services;
import org.adempiere.util.collections.ListUtils;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Evaluatees;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;

import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.ui.web.base.model.I_WEBUI_Board;
import de.metas.ui.web.base.model.I_WEBUI_Board_Lane;
import de.metas.ui.web.base.model.I_WEBUI_Board_RecordAssignment;
import de.metas.ui.web.board.BoardDescriptor.BoardDescriptorBuilder;
import de.metas.ui.web.board.json.JSONBoardCardChangedEvent;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.websocket.WebSocketConfig;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider.LookupScope;
import de.metas.ui.web.window.descriptor.sql.SqlLookupDescriptor;
import lombok.NonNull;

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

@Repository
public class BoardDescriptorRepository
{
	private final CCache<Integer, BoardDescriptor> boardDescriptors = CCache.<Integer, BoardDescriptor> newCache(I_WEBUI_Board.Table_Name + "#BoardDescriptor", 50, 0)
			.addResetForTableName(I_WEBUI_Board_Lane.Table_Name);

	@Autowired
	private SimpMessagingTemplate websocketMessagingTemplate;

	public BoardDescriptor getBoardDescriptor(final int boardId)
	{
		return boardDescriptors.getOrLoad(boardId, () -> createBoardDescriptor(boardId));
	}

	private BoardDescriptor createBoardDescriptor(final int boardId)
	{
		//
		// Retrieve the board PO
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final I_WEBUI_Board boardPO = queryBL.createQueryBuilderOutOfTrx(I_WEBUI_Board.class)
				.addEqualsFilter(I_WEBUI_Board.COLUMN_WEBUI_Board_ID, boardId)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_WEBUI_Board.class);
		if (boardPO == null)
		{
			throw new EntityNotFoundException("No board found for ID=" + boardId);
		}

		//
		// Board record mappings
		final String tableName = Services.get(IADTableDAO.class).retrieveTableName(boardPO.getAD_Table_ID());
		final String keyColumnName = InterfaceWrapperHelper.getKeyColumnName(tableName);
		final String keyColumnNameFQ = tableName + "." + keyColumnName;

		//
		// Board record info
		int adWindowId = 0; // TODO boardPO.getAD_Window_ID();
		if (adWindowId <= 0)
		{
			adWindowId = RecordZoomWindowFinder.findAD_Window_ID(tableName);
		}
		final WindowId recordWindowId = WindowId.of(adWindowId);

		final IModelTranslationMap boardTrlMap = InterfaceWrapperHelper.getModelTranslationMap(boardPO);
		final BoardDescriptorBuilder boardDescriptor = BoardDescriptor.builder()
				.boardId(boardPO.getWEBUI_Board_ID())
				.caption(boardTrlMap.getColumnTrl(I_WEBUI_Board.COLUMNNAME_Name, boardPO.getName()))
				//
				.recordWindowId(recordWindowId)
				// Mapping
				.tableName(tableName)
				.keyColumnName(keyColumnName)
				.keyColumnNameFQ(keyColumnNameFQ)
				.adValRuleId(boardPO.getAD_Val_Rule_ID())
				//
				.websocketEndpoint(WebSocketConfig.buildBoardTopicName(boardId));

		//
		// Lanes
		queryBL.createQueryBuilderOutOfTrx(I_WEBUI_Board_Lane.class)
				.addEqualsFilter(I_WEBUI_Board_Lane.COLUMN_WEBUI_Board_ID, boardId)
				.addOnlyActiveRecordsFilter()
				.orderBy()
				.addColumn(I_WEBUI_Board_Lane.COLUMN_SeqNo)
				.addColumn(I_WEBUI_Board_Lane.COLUMN_WEBUI_Board_Lane_ID) // just have a predictable order
				.endOrderBy()
				.create()
				.stream(I_WEBUI_Board_Lane.class)
				.map(this::createBoardLaneDescriptor)
				.forEach(lane -> boardDescriptor.lane(lane.getLaneId(), lane));

		//
		return boardDescriptor.build();
	}

	private final BoardLaneDescriptor createBoardLaneDescriptor(final I_WEBUI_Board_Lane lanePO)
	{
		final IModelTranslationMap laneTrlMap = InterfaceWrapperHelper.getModelTranslationMap(lanePO);
		return BoardLaneDescriptor.builder()
				.laneId(lanePO.getWEBUI_Board_Lane_ID())
				.caption(laneTrlMap.getColumnTrl(I_WEBUI_Board_Lane.COLUMNNAME_Name, lanePO.getName()))
				.build();
	}

	public List<BoardCard> getCards(final int boardId)
	{
		return retrieveCards(boardId, -1/* cardId */);
	}

	public BoardCard getCard(final int boardId, final int cardId)
	{
		return ListUtils.singleElement(retrieveCards(boardId, cardId));
	}

	private List<BoardCard> retrieveCards(final int boardId, final int cardId)
	{
		final BoardDescriptor boardDescriptor = getBoardDescriptor(boardId);

		final String tableName = boardDescriptor.getTableName();
		final String tableAlias = "r";
		final String keyColumnName = boardDescriptor.getKeyColumnName();
		final String keyColumnNameFQ = tableAlias + "." + keyColumnName;
		final String userIdColumnNameFQ = tableAlias + "." + "UpdatedBy"; // FIXME hardcoded

		final LookupDescriptorProvider recordLookupProvider = SqlLookupDescriptor.builder()
				.setColumnName(keyColumnName)
				.setDisplayType(DisplayType.Search)
				.setWidgetType(DocumentFieldWidgetType.Lookup)
				.setAD_Val_Rule_ID(boardDescriptor.getAdValRuleId())
				.buildProvider();

		final SqlLookupDescriptor recordLookup = SqlLookupDescriptor.cast(recordLookupProvider.provideForScope(LookupScope.DocumentField));

		recordLookup.getSqlForFetchingExpression();

		final List<Object> sqlParams = new ArrayList<>();
		final CompositeStringExpression.Builder sqlExpr = IStringExpression.composer()
				.append("SELECT ")
				.append("\n  a." + I_WEBUI_Board_RecordAssignment.COLUMNNAME_WEBUI_Board_Lane_ID)
				.append("\n, a." + I_WEBUI_Board_RecordAssignment.COLUMNNAME_Record_ID)
				.append("\n, (").append(recordLookup.getSqlForFetchingDisplayNameByIdExpression(keyColumnNameFQ)).append(") AS card$caption")
				//
				.append("\n, u." + org.compiere.model.I_AD_User.COLUMNNAME_AD_User_ID + " AS card$user_id")
				.append("\n, u." + org.compiere.model.I_AD_User.COLUMNNAME_Avatar_ID + " AS card$user_avatar_id")
				.append("\n, u." + org.compiere.model.I_AD_User.COLUMNNAME_Name + " AS card$user_fullname")
				//
				.append("\n FROM ").append(I_WEBUI_Board_RecordAssignment.Table_Name).append(" a")
				.append("\n INNER JOIN " + tableName + " " + tableAlias + " ON (" + keyColumnNameFQ + " =a." + I_WEBUI_Board_RecordAssignment.COLUMNNAME_Record_ID + ")")
				.append("\n LEFT OUTER JOIN " + org.compiere.model.I_AD_User.Table_Name + " u ON (u.AD_User_ID = " + userIdColumnNameFQ + ")")
				.append("\n WHERE ")
				.append("\n a." + I_WEBUI_Board_RecordAssignment.COLUMNNAME_WEBUI_Board_ID + "=?")
				.append("\n AND " + tableAlias + ".IsActive='Y'");
		sqlParams.add(boardId);

		if (cardId >= 0) // zero is also ok because we might have records with ID zero
		{
			sqlExpr.append("\n AND " + I_WEBUI_Board_RecordAssignment.COLUMNNAME_Record_ID + "=?");
			sqlParams.add(cardId);
		}

		final String sql = sqlExpr.build().evaluate(Evaluatees.empty(), OnVariableNotFound.Fail);

		return retrieveCardsFromSql(sql, sqlParams, boardDescriptor);
	}

	private final List<BoardCard> retrieveCardsFromSql(final String sql, final List<Object> sqlParams, final BoardDescriptor boardDescriptor)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();

			final ImmutableList.Builder<BoardCard> cards = ImmutableList.builder();
			while (rs.next())
			{
				final BoardCard card = createCard(rs, boardDescriptor);
				if (card == null)
				{
					continue;
				}

				cards.add(card);
			}

			return cards.build();
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex, sql, sqlParams);
		}
	}

	private BoardCard createCard(final ResultSet rs, final BoardDescriptor boardDescriptor) throws SQLException
	{
		final int laneId = rs.getInt(I_WEBUI_Board_RecordAssignment.COLUMNNAME_WEBUI_Board_Lane_ID);
		final int recordId = rs.getInt(I_WEBUI_Board_RecordAssignment.COLUMNNAME_Record_ID);
		final String caption = rs.getString("card$caption");
		final int userId = rs.getInt("card$user_id");
		final String userAvatarId = rs.getString("card$user_avatar_id");
		final String userFullname = rs.getString("card$user_fullname");
		final String description = ""; // TODO fetch description from fields
		return BoardCard.builder()
				.cardId(recordId)
				.laneId(laneId)
				//
				.caption(ImmutableTranslatableString.constant(caption))
				.description(ImmutableTranslatableString.constant(description))
				.documentPath(DocumentPath.rootDocumentPath(boardDescriptor.getRecordWindowId(), DocumentId.of(recordId)))
				//
				.user(BoardCardUser.builder()
						.userId(userId)
						.avatarId(userAvatarId)
						.fullname(userFullname)
						.build())
				//
				.build();
	}

	public BoardCard addCardForDocumentId(final int boardId, final int laneId, @NonNull final DocumentId documentId)
	{
		final BoardDescriptor board = getBoardDescriptor(boardId);
		board.assertLaneIdExists(laneId);

		final int cardId = documentId.toInt();

		final I_WEBUI_Board_RecordAssignment assignment = InterfaceWrapperHelper.newInstance(I_WEBUI_Board_RecordAssignment.class);
		assignment.setAD_Org_ID(Env.CTXVALUE_AD_Org_ID_Any);
		assignment.setWEBUI_Board_ID(boardId);
		assignment.setWEBUI_Board_Lane_ID(laneId);
		assignment.setRecord_ID(cardId);
		InterfaceWrapperHelper.save(assignment);

		final BoardCard card = getCard(boardId, cardId);

		websocketMessagingTemplate.convertAndSend(board.getWebsocketEndpoint(), JSONBoardCardChangedEvent.cardAdded(boardId, cardId));
		return card;
	}

	public void removeCard(final int boardId, final int cardId)
	{
		final BoardDescriptor board = getBoardDescriptor(boardId);

		final int deletedCount = Services.get(IQueryBL.class)
				.createQueryBuilder(I_WEBUI_Board_RecordAssignment.class)
				.addEqualsFilter(I_WEBUI_Board_RecordAssignment.COLUMN_WEBUI_Board_ID, boardId)
				.addEqualsFilter(I_WEBUI_Board_RecordAssignment.COLUMN_Record_ID, cardId)
				.create()
				.deleteDirectly();

		if (deletedCount > 0)
		{
			websocketMessagingTemplate.convertAndSend(board.getWebsocketEndpoint(), JSONBoardCardChangedEvent.cardRemoved(boardId, cardId));
		}
	}

	public BoardCard changeCard(final int boardId, final int cardId, final BoardCardChangeRequest request)
	{
		final BoardDescriptor board = getBoardDescriptor(boardId);

		Services.get(ITrxManager.class)
				.run(ITrx.TRXNAME_ThreadInherited, () -> {
					if (request.getNewLaneId() > 0)
					{
						changeLane(boardId, cardId, request.getNewLaneId());
					}
				});

		final BoardCard card = getCard(boardId, cardId);

		websocketMessagingTemplate.convertAndSend(board.getWebsocketEndpoint(), JSONBoardCardChangedEvent.cardChanged(boardId, cardId));
		return card;
	}

	private void changeLane(final int boardId, final int cardId, final int newLaneId)
	{
		final int countUpdate = Services.get(IQueryBL.class)
				.createQueryBuilder(I_WEBUI_Board_RecordAssignment.class)
				.addEqualsFilter(I_WEBUI_Board_RecordAssignment.COLUMN_WEBUI_Board_ID, boardId)
				.addEqualsFilter(I_WEBUI_Board_RecordAssignment.COLUMN_Record_ID, cardId)
				.create()
				.updateDirectly()
				.addSetColumnValue(I_WEBUI_Board_RecordAssignment.COLUMNNAME_WEBUI_Board_Lane_ID, newLaneId)
				.execute();
		if (countUpdate <= 0)
		{
			throw new AdempiereException("Card it's not part this Board")
					.setParameter("boardId", boardId)
					.setParameter("cardId", cardId);
		}
	}
}
