package de.metas.ui.web.board;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.CompositeStringExpression;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.ad.validationRule.IValidationRuleFactory;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.DBUniqueConstraintException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.RecordZoomWindowFinder;
import org.adempiere.util.NumberUtils;
import org.adempiere.util.Services;
import org.adempiere.util.collections.ListUtils;
import org.compiere.model.I_AD_User;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Evaluatees;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.currency.Amount;
import de.metas.currency.ICurrencyDAO;
import de.metas.i18n.DateTimeTranslatableString;
import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.i18n.NumberTranslatableString;
import de.metas.logging.LogManager;
import de.metas.ui.web.base.model.I_WEBUI_Board;
import de.metas.ui.web.base.model.I_WEBUI_Board_CardField;
import de.metas.ui.web.base.model.I_WEBUI_Board_Lane;
import de.metas.ui.web.base.model.I_WEBUI_Board_RecordAssignment;
import de.metas.ui.web.board.BoardCardFieldDescriptor.BoardFieldLoader;
import de.metas.ui.web.board.BoardDescriptor.BoardDescriptorBuilder;
import de.metas.ui.web.board.json.events.JSONBoardChangedEventsList;
import de.metas.ui.web.board.json.events.JSONBoardChangedEventsList.JSONBoardChangedEventsListBuilder;
import de.metas.ui.web.board.json.events.JSONBoardLaneChangedEvent;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.websocket.WebSocketConfig;
import de.metas.ui.web.websocket.WebsocketSender;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider.LookupScope;
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;
import de.metas.ui.web.window.descriptor.sql.DocumentFieldValueLoader;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlLookupDescriptor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

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
	private static final transient Logger logger = LogManager.getLogger(BoardDescriptorRepository.class);

	@Autowired
	private DocumentDescriptorFactory documentDescriptors;

	@Autowired
	private WebsocketSender websocketSender;

	private final CCache<Integer, BoardDescriptor> boardDescriptors = CCache.<Integer, BoardDescriptor> newCache(I_WEBUI_Board.Table_Name + "#BoardDescriptor", 50, 0)
			.addResetForTableName(I_WEBUI_Board_Lane.Table_Name)
			.addResetForTableName(I_WEBUI_Board_CardField.Table_Name);

	private void sendEvents(final BoardDescriptor board, final JSONBoardChangedEventsList events)
	{
		if (events.isEmpty())
		{
			return;
		}

		final String websocketEndpoint = board.getWebsocketEndpoint();
		websocketSender.convertAndSend(websocketEndpoint, events);
		logger.trace("Notified WS {}: {}", websocketEndpoint, events);
	}

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
		// Board document mappings
		final String tableName = Services.get(IADTableDAO.class).retrieveTableName(boardPO.getAD_Table_ID());
		final String keyColumnName = InterfaceWrapperHelper.getKeyColumnName(tableName);
		final String userIdColumnName = "UpdatedBy"; // TODO: hardcoded

		//
		// Board document info
		int adWindowId = 0; // TODO boardPO.getAD_Window_ID();
		if (adWindowId <= 0)
		{
			adWindowId = RecordZoomWindowFinder.findAD_Window_ID(tableName);
		}
		final WindowId documentWindowId = WindowId.of(adWindowId);

		final DocumentEntityDescriptor documentEntityDescriptor = documentDescriptors.getDocumentEntityDescriptor(documentWindowId);
		final SqlDocumentEntityDataBindingDescriptor documentBinding = documentEntityDescriptor.getDataBinding(SqlDocumentEntityDataBindingDescriptor.class);
		final String tableAlias = documentBinding.getTableAlias();

		//
		// Board document lookup provider
		final int adValRuleId = boardPO.getAD_Val_Rule_ID();
		final LookupDescriptorProvider documentLookupDescriptorProvider = SqlLookupDescriptor.builder()
				.setCtxTableName(null)
				.setCtxColumnName(keyColumnName)
				.setDisplayType(DisplayType.Search)
				.setWidgetType(DocumentFieldWidgetType.Lookup)
				.setAD_Val_Rule_ID(adValRuleId)
				.buildProvider();

		//
		// Board descriptor
		final IModelTranslationMap boardTrlMap = InterfaceWrapperHelper.getModelTranslationMap(boardPO);
		final BoardDescriptorBuilder boardDescriptor = BoardDescriptor.builder()
				.boardId(boardPO.getWEBUI_Board_ID())
				.caption(boardTrlMap.getColumnTrl(I_WEBUI_Board.COLUMNNAME_Name, boardPO.getName()))
				//
				.documentWindowId(documentWindowId)
				.documentLookupDescriptorProvider(documentLookupDescriptorProvider)
				// Mapping
				.tableName(tableName)
				.tableAlias(tableAlias)
				.keyColumnName(keyColumnName)
				.userIdColumnName(userIdColumnName)
				//
				.websocketEndpoint(WebSocketConfig.buildBoardTopicName(boardId));

		//
		// Source document filters: AD_Val_Rule_ID
		if (adValRuleId > 0)
		{
			final IValidationRule validationRule = Services.get(IValidationRuleFactory.class).create(
					tableName
					, adValRuleId
					, null // ctx table name
					, null // ctx column name
					);
			final String sqlWhereClause = validationRule.getPrefilterWhereClause()
					.evaluate(Evaluatees.ofCtx(Env.getCtx()), OnVariableNotFound.Fail);

			final DocumentFilter adValRuleFilter = DocumentFilter.builder()
					.setFilterId("AD_Val_Rule_" + adValRuleId)
					.addParameter(DocumentFilterParam.ofSqlWhereClause(true, sqlWhereClause))
					.build();
			boardDescriptor.documentFilter(adValRuleFilter);
		}

		//
		// Lanes
		{
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
		}

		//
		// Board card fields
		{
			queryBL.createQueryBuilderOutOfTrx(I_WEBUI_Board_CardField.class)
					.addEqualsFilter(I_WEBUI_Board_CardField.COLUMN_WEBUI_Board_ID, boardId)
					.addOnlyActiveRecordsFilter()
					.orderBy()
					.addColumn(I_WEBUI_Board_CardField.COLUMN_SeqNo)
					.addColumn(I_WEBUI_Board_CardField.COLUMN_WEBUI_Board_CardField_ID)
					.endOrderBy()
					.create()
					.stream(I_WEBUI_Board_CardField.class)
					.map(cardFieldPO -> createBoardCardFieldDescriptor(cardFieldPO, documentEntityDescriptor))
					.forEach(cardField -> boardDescriptor.cardFieldByFieldName(cardField.getFieldName(), cardField));
		}

		//
		return boardDescriptor.build();
	}

	private final BoardCardFieldDescriptor createBoardCardFieldDescriptor(final I_WEBUI_Board_CardField cardFieldPO, final DocumentEntityDescriptor documentEntityDescriptor)
	{
		final String fieldName = cardFieldPO.getAD_Column().getColumnName(); // TODO: might be not so performant, we just need the ColumnName

		final DocumentFieldDescriptor documentField = documentEntityDescriptor.getField(fieldName);
		final SqlDocumentFieldDataBindingDescriptor fieldBinding = documentField.getDataBindingNotNull(SqlDocumentFieldDataBindingDescriptor.class);
		final DocumentFieldWidgetType widgetType = documentField.getWidgetType();
		final boolean isDisplayColumnAvailable = fieldBinding.isUsingDisplayColumn();

		final ImmutableSet<String> sqlSelectValues;
		final BoardFieldLoader fieldLoader;
		if (widgetType == DocumentFieldWidgetType.Amount && documentEntityDescriptor.hasField(WindowConstants.FIELDNAME_C_Currency_ID))
		{
			sqlSelectValues = ImmutableSet.of(fieldBinding.getSqlSelectValue(), WindowConstants.FIELDNAME_C_Currency_ID);
			fieldLoader = (rs, adLanguage) -> {
				final BigDecimal valueBD = rs.getBigDecimal(fieldBinding.getColumnName());
				if (valueBD == null)
				{
					return null;
				}

				final int currencyId = rs.getInt(WindowConstants.FIELDNAME_C_Currency_ID);
				final String currencyCode = Services.get(ICurrencyDAO.class).getISO_Code(Env.getCtx(), currencyId);
				if (currencyCode == null)
				{
					return valueBD;
				}

				return Amount.of(valueBD, currencyCode);

			};

		}
		else
		{
			sqlSelectValues = ImmutableSet.of(fieldBinding.getSqlSelectValue());
			final DocumentFieldValueLoader documentFieldValueLoader = fieldBinding.getDocumentFieldValueLoader();
			final LookupDescriptor lookupDescriptor = documentField.getLookupDescriptor(LookupScope.DocumentField);
			fieldLoader = (rs, adLanguage) -> documentFieldValueLoader.retrieveFieldValue(rs, isDisplayColumnAvailable, adLanguage, lookupDescriptor);
		}

		return BoardCardFieldDescriptor.builder()
				.caption(documentField.getCaption())
				.fieldName(fieldBinding.getColumnName())
				.widgetType(widgetType)
				.sqlSelectValues(sqlSelectValues)
				//
				.usingDisplayColumn(isDisplayColumnAvailable)
				.sqlSelectDisplayValue(fieldBinding.getSqlSelectDisplayValue())
				.sqlOrderBy(fieldBinding.getSqlOrderBy())
				.fieldLoader(fieldLoader)
				.build();
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
		final Set<Integer> onlyCardIds = ImmutableSet.of();
		return retrieveCards(boardId, onlyCardIds);
	}

	public BoardCard getCard(final int boardId, final int cardId)
	{
		Preconditions.checkArgument(cardId >= 0, "cardId >= 0"); // zero is OK because we might have recordId=0
		final Set<Integer> onlyCardIds = ImmutableSet.of(cardId);

		return ListUtils.singleElement(retrieveCards(boardId, onlyCardIds));
	}

	public List<BoardCard> getCards(final int boardId, final Collection<Integer> cardIds)
	{
		Preconditions.checkArgument(!cardIds.isEmpty(), "cardIds shall not be empty");
		return retrieveCards(boardId, cardIds);
	}

	private int getLaneIdForCardId(final int boardId, final int cardId)
	{
		return getCard(boardId, cardId).getLaneId();
	}

	private List<BoardCard> retrieveCards(final int boardId, final Collection<Integer> onlyCardIds)
	{
		final BoardDescriptor boardDescriptor = getBoardDescriptor(boardId);

		final String keyColumnName = boardDescriptor.getKeyColumnName();
		final String userIdColumnName = boardDescriptor.getUserIdColumnName();

		//
		final List<Object> sqlParams = new ArrayList<>();
		final CompositeStringExpression.Builder sqlExpr;
		{
			final IStringExpression sqlSelectDocument = buildSqlSelectDocument(boardDescriptor);

			final String tableAlias = "r";
			final String keyColumnNameFQ = tableAlias + "." + keyColumnName;
			final String userIdColumnNameFQ = tableAlias + "." + userIdColumnName;
			final SqlLookupDescriptor documentLookup = SqlLookupDescriptor.cast(boardDescriptor.getDocumentLookupDescriptorProvider().provideForScope(LookupScope.DocumentField));

			sqlExpr = IStringExpression.composer()
					.append("SELECT ")
					.append("\n  a." + I_WEBUI_Board_RecordAssignment.COLUMNNAME_WEBUI_Board_Lane_ID)
					.append("\n, a." + I_WEBUI_Board_RecordAssignment.COLUMNNAME_Record_ID)
					.append("\n, (").append(documentLookup.getSqlForFetchingDisplayNameByIdExpression(keyColumnNameFQ)).append(") AS card$caption")
					//
					.append("\n, u." + I_AD_User.COLUMNNAME_AD_User_ID + " AS card$user_id")
					.append("\n, u." + I_AD_User.COLUMNNAME_Avatar_ID + " AS card$user_avatar_id")
					.append("\n, u." + I_AD_User.COLUMNNAME_Name + " AS card$user_fullname")
					//
					.append("\n, " + tableAlias + ".*") // all exported document fields
					//
					.append("\n FROM ").append(I_WEBUI_Board_RecordAssignment.Table_Name).append(" a")
					.append("\n INNER JOIN (").append(sqlSelectDocument).append(") " + tableAlias + " ON (" + keyColumnNameFQ + " =a." + I_WEBUI_Board_RecordAssignment.COLUMNNAME_Record_ID + ")")
					.append("\n LEFT OUTER JOIN " + I_AD_User.Table_Name + " u ON (u." + I_AD_User.COLUMNNAME_AD_User_ID + " = " + userIdColumnNameFQ + ")")
					.append("\n WHERE ")
					.append("\n a." + I_WEBUI_Board_RecordAssignment.COLUMNNAME_WEBUI_Board_ID + "=?");
			sqlParams.add(boardId);

			if (!onlyCardIds.isEmpty())
			{
				sqlExpr.append("\n AND ").append(DB.buildSqlList(I_WEBUI_Board_RecordAssignment.COLUMNNAME_Record_ID, onlyCardIds, sqlParams));
			}
		}

		final String sql = sqlExpr.build().evaluate(Evaluatees.empty(), OnVariableNotFound.Fail);

		return retrieveCardsFromSql(sql, sqlParams, boardDescriptor);
	}

	/** @return all cardIds contained in given <code>boardId</code> */
	public List<Integer> retrieveCardIds(final int boardId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_WEBUI_Board_RecordAssignment.class)
				.addEqualsFilter(I_WEBUI_Board_RecordAssignment.COLUMN_WEBUI_Board_ID, boardId)
				.create()
				.listDistinct(I_WEBUI_Board_RecordAssignment.COLUMNNAME_Record_ID, Integer.class);
	}

	public List<BoardCard> retrieveCardCandidates(final int boardId, final List<Integer> cardIds)
	{
		if (cardIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final BoardDescriptor boardDescriptor = getBoardDescriptor(boardId);

		final String keyColumnName = boardDescriptor.getKeyColumnName();
		final String userIdColumnName = boardDescriptor.getUserIdColumnName();

		//
		final List<Object> sqlParams = new ArrayList<>();
		final CompositeStringExpression.Builder sqlExpr;
		{
			final IStringExpression sqlSelectDocument = buildSqlSelectDocument(boardDescriptor);

			final String tableAlias = "r";
			final String keyColumnNameFQ = tableAlias + "." + keyColumnName;
			final String userIdColumnNameFQ = tableAlias + "." + userIdColumnName;
			final SqlLookupDescriptor documentLookup = SqlLookupDescriptor.cast(boardDescriptor.getDocumentLookupDescriptorProvider().provideForScope(LookupScope.DocumentField));

			sqlExpr = IStringExpression.composer()
					.append("SELECT ")
					.append("\n  NULL AS " + I_WEBUI_Board_RecordAssignment.COLUMNNAME_WEBUI_Board_Lane_ID)
					.append("\n, " + keyColumnNameFQ + " AS " + I_WEBUI_Board_RecordAssignment.COLUMNNAME_Record_ID)
					.append("\n, (").append(documentLookup.getSqlForFetchingDisplayNameByIdExpression(keyColumnNameFQ)).append(") AS card$caption")
					//
					.append("\n, u." + I_AD_User.COLUMNNAME_AD_User_ID + " AS card$user_id")
					.append("\n, u." + I_AD_User.COLUMNNAME_Avatar_ID + " AS card$user_avatar_id")
					.append("\n, u." + I_AD_User.COLUMNNAME_Name + " AS card$user_fullname")
					//
					.append("\n, " + tableAlias + ".*") // all exported document fields
					//
					.append("\n FROM (").append(sqlSelectDocument).append(") " + tableAlias)
					.append("\n LEFT OUTER JOIN " + I_AD_User.Table_Name + " u ON (u." + I_AD_User.COLUMNNAME_AD_User_ID + " = " + userIdColumnNameFQ + ")");

			sqlExpr.append("\n WHERE ")
					.append("\n " + DB.buildSqlList(keyColumnNameFQ, cardIds, sqlParams));
		}

		final String sql = sqlExpr.build().evaluate(Evaluatees.empty(), OnVariableNotFound.Fail);

		return retrieveCardsFromSql(sql, sqlParams, boardDescriptor);
	}

	private static final IStringExpression buildSqlSelectDocument(final BoardDescriptor boardDescriptor)
	{
		final String tableName = boardDescriptor.getTableName();
		final String tableAlias = boardDescriptor.getTableAlias();
		final String keyColumnName = boardDescriptor.getKeyColumnName();
		final String userIdColumnName = boardDescriptor.getUserIdColumnName();

		final LinkedHashSet<String> sqlSelectValuesList = new LinkedHashSet<>();
		sqlSelectValuesList.add(keyColumnName);
		sqlSelectValuesList.add(userIdColumnName);
		final List<IStringExpression> sqlSelectDisplayNamesList = new ArrayList<>();
		boardDescriptor.getCardFields()
				.forEach(cardField -> {
					sqlSelectValuesList.addAll(cardField.getSqlSelectValues());

					if (cardField.isUsingDisplayColumn())
					{
						sqlSelectDisplayNamesList.add(cardField.getSqlSelectDisplayValue());
					}
				});

		final CompositeStringExpression.Builder sql = IStringExpression.composer();
		sql.append("SELECT ")
				.append("\n").append(tableAlias).append(".*"); // Value fields

		if (!sqlSelectDisplayNamesList.isEmpty())
		{
			sql.append("\n, ").appendAllJoining("\n, ", sqlSelectDisplayNamesList); // DisplayName fields
		}

		sql.append("\n FROM (")
				.append("\n   SELECT ")
				.append("\n   ").append(Joiner.on("\n   , ").join(sqlSelectValuesList))
				.append("\n FROM ").append(tableName)
				.append("\n WHERE IsActive='Y'")
				.append("\n ) " + tableAlias); // FROM

		return sql.build();
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
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	private BoardCard createCard(final ResultSet rs, final BoardDescriptor boardDescriptor) throws SQLException
	{
		final String adLanguage = null;
		final int laneId = rs.getInt(I_WEBUI_Board_RecordAssignment.COLUMNNAME_WEBUI_Board_Lane_ID);
		final int recordId = rs.getInt(I_WEBUI_Board_RecordAssignment.COLUMNNAME_Record_ID);
		final String caption = rs.getString("card$caption");
		final int userId = rs.getInt("card$user_id");
		final String userAvatarId = rs.getString("card$user_avatar_id");
		final String userFullname = rs.getString("card$user_fullname");

		//
		// Retrieve card fields
		final Map<String, Object> cardValues = new LinkedHashMap<>();
		for (final BoardCardFieldDescriptor cardField : boardDescriptor.getCardFields())
		{
			final BoardFieldLoader fieldLoader = cardField.getFieldLoader();
			final Object fieldValue = fieldLoader.retrieveValueAsJson(rs, adLanguage);
			if (fieldValue == null)
			{
				continue;
			}

			cardValues.put(cardField.getFieldName(), fieldValue);
		}
		final ITranslatableString description = buildDescription(cardValues, boardDescriptor);

		return BoardCard.builder()
				.cardId(recordId)
				.laneId(laneId)
				//
				.caption(ImmutableTranslatableString.constant(caption))
				.description(description)
				.documentPath(DocumentPath.rootDocumentPath(boardDescriptor.getDocumentWindowId(), DocumentId.of(recordId)))
				//
				.user(BoardCardUser.builder()
						.userId(userId)
						.avatarId(userAvatarId)
						.fullname(userFullname)
						.build())
				//
				.build();
	}

	private static final ITranslatableString buildDescription(final Map<String, Object> cardValues, final BoardDescriptor board)
	{
		final List<ITranslatableString> fieldDescriptions = cardValues.entrySet()
				.stream()
				.map(fieldNameAndValue -> {
					final String fieldName = fieldNameAndValue.getKey();
					final Object fieldValue = fieldNameAndValue.getValue();
					final BoardCardFieldDescriptor cardField = board.getCardFieldByName(fieldName);
					return buildDescription(fieldValue, cardField);
				})
				.filter(fieldDescription -> fieldDescription != null)
				.collect(ImmutableList.toImmutableList());

		return ITranslatableString.compose("\n", fieldDescriptions);
	}

	private static ITranslatableString toDisplayValue(final Object value, final DocumentFieldWidgetType widgetType)
	{
		if (value == null)
		{
			return ITranslatableString.empty();
		}

		//
		// Determine by value type
		if (value instanceof Amount)
		{
			final Amount amount = (Amount)value;
			return ITranslatableString.compose(" ",
					NumberTranslatableString.of(amount.getValue(), DisplayType.Amount),
					ITranslatableString.constant(amount.getCurrencyCode()));

		}

		//
		// Determine by widgetType
		if (widgetType == DocumentFieldWidgetType.Password)
		{
			// hide passwords
			return ITranslatableString.constant("*****");
		}
		else if (widgetType.isText())
		{
			return ITranslatableString.constant(value.toString());
		}
		else if (widgetType == DocumentFieldWidgetType.Date)
		{
			return DateTimeTranslatableString.ofDate((java.util.Date)value);
		}
		else if (widgetType == DocumentFieldWidgetType.DateTime)
		{
			return DateTimeTranslatableString.ofDateTime((java.util.Date)value);
		}
		else if (widgetType == DocumentFieldWidgetType.Integer)
		{
			return ITranslatableString.constant(value.toString());
		}
		else if (widgetType.isNumeric())
		{
			final BigDecimal valueBD = NumberUtils.asBigDecimal(value, null);
			if (valueBD != null)
			{
				return NumberTranslatableString.of(valueBD, widgetType.getDisplayType());
			}
		}
		else if (widgetType.isLookup())
		{
			if (value instanceof LookupValue)
			{
				return ((LookupValue)value).getDisplayNameTrl();
			}
			else if (value instanceof JSONLookupValue)
			{
				return ImmutableTranslatableString.constant(((JSONLookupValue)value).getCaption().trim());
			}
		}
		else if (widgetType.isBoolean())
		{
			final boolean valueBoolean = DisplayType.toBoolean(value);
			return Services.get(IMsgBL.class).getTranslatableMsgText(valueBoolean ? "Y" : "N");
		}

		return ITranslatableString.constant(value.toString());
	}

	private static final ITranslatableString buildDescription(final Object value, final BoardCardFieldDescriptor cardField)
	{
		if (value == null)
		{
			return null;
		}

		final ITranslatableString valueStr = toDisplayValue(value, cardField.getWidgetType());
		return ITranslatableString.compose(": ", cardField.getCaption(), valueStr);
	}

	private LaneCardsSequence retrieveCardIdsOrdered(final int boardId, final int laneId)
	{
		final List<Integer> cardIds = Services.get(IQueryBL.class)
				.createQueryBuilder(I_WEBUI_Board_RecordAssignment.class)
				.addEqualsFilter(I_WEBUI_Board_RecordAssignment.COLUMN_WEBUI_Board_ID, boardId)
				.addEqualsFilter(I_WEBUI_Board_RecordAssignment.COLUMN_WEBUI_Board_Lane_ID, laneId)
				.orderBy()
				.addColumn(I_WEBUI_Board_RecordAssignment.COLUMN_SeqNo)
				.addColumn(I_WEBUI_Board_RecordAssignment.COLUMN_WEBUI_Board_RecordAssignment_ID)
				.endOrderBy()
				.create()
				.listDistinct(I_WEBUI_Board_RecordAssignment.COLUMNNAME_Record_ID, Integer.class);
		return new LaneCardsSequence(laneId, cardIds);
	}

	private final void updateCardsOrder(final int boardId, final int laneId, final List<Integer> cardIds)
	{
		final String sql = "UPDATE " + I_WEBUI_Board_RecordAssignment.Table_Name
				+ " SET " + I_WEBUI_Board_RecordAssignment.COLUMNNAME_SeqNo + "=?"
				+ " WHERE " + I_WEBUI_Board_RecordAssignment.COLUMNNAME_WEBUI_Board_ID + "=?"
				+ " AND " + I_WEBUI_Board_RecordAssignment.COLUMNNAME_WEBUI_Board_Lane_ID + "=?"
				+ " AND " + I_WEBUI_Board_RecordAssignment.COLUMNNAME_Record_ID + "=?";
		PreparedStatement pstmt = null;
		try
		{
			for (int newSeqNo = 0; newSeqNo < cardIds.size(); newSeqNo++)
			{
				final int cardId = cardIds.get(newSeqNo);

				if (pstmt == null)
				{
					pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
				}
				DB.setParameters(pstmt, new Object[] { newSeqNo, boardId, laneId, cardId });
				pstmt.addBatch();
			}

			if (pstmt == null)
			{
				return;
			}

			pstmt.executeBatch();
		}
		catch (final SQLException ex)
		{
			throw DBException.wrapIfNeeded(ex);
		}
		finally
		{
			DB.close(pstmt);
		}
	}

	private final LaneCardsSequence changeCardsOrder(final int boardId, final int laneId, final Consumer<LaneCardsSequence> reorderCards)
	{
		final LaneCardsSequence orderedCardIdsOld = retrieveCardIdsOrdered(boardId, laneId);
		final LaneCardsSequence orderedCardIdsNew = orderedCardIdsOld.copy();

		reorderCards.accept(orderedCardIdsNew);

		updateCardsOrder(boardId, laneId, orderedCardIdsNew.getCardIds());

		return orderedCardIdsNew;
	}

	public BoardCard addCardForDocumentId(final int boardId, final int laneId, @NonNull final DocumentId documentId, final int position)
	{
		final BoardDescriptor board = getBoardDescriptor(boardId);
		board.assertLaneIdExists(laneId);

		final int cardId = documentId.toInt();

		final JSONBoardChangedEventsListBuilder eventsCollector = JSONBoardChangedEventsList.builder();

		Services.get(ITrxManager.class).run(ITrx.TRXNAME_ThreadInherited, () -> {
			try
			{
				final I_WEBUI_Board_RecordAssignment assignment = InterfaceWrapperHelper.newInstance(I_WEBUI_Board_RecordAssignment.class);
				assignment.setAD_Org_ID(Env.CTXVALUE_AD_Org_ID_Any);
				assignment.setWEBUI_Board_ID(boardId);
				assignment.setWEBUI_Board_Lane_ID(laneId);
				assignment.setRecord_ID(cardId);
				assignment.setSeqNo(Integer.MAX_VALUE); // will be updated later
				InterfaceWrapperHelper.save(assignment);
			}
			catch (final DBUniqueConstraintException ex)
			{
				throw new AdempiereException("Card was already added to this board", ex)
						.setParameter("boardI", boardId)
						.setParameter("laneId", laneId)
						.setParameter("cardId", cardId);
			}

			final LaneCardsSequence orderedCardIds = changeCardsOrder(boardId, laneId, cardIds -> cardIds.addCardIdAtPosition(cardId, position));
			eventsCollector.event(JSONBoardLaneChangedEvent.of(boardId, laneId, orderedCardIds.getCardIds()));
		});

		final BoardCard card = getCard(boardId, cardId);

		sendEvents(board, eventsCollector.build());
		return card;
	}

	public void removeCard(final int boardId, final int cardId)
	{
		final BoardDescriptor board = getBoardDescriptor(boardId);

		final JSONBoardChangedEventsListBuilder eventsCollector = JSONBoardChangedEventsList.builder();

		Services.get(ITrxManager.class).run(ITrx.TRXNAME_ThreadInherited, () -> {
			final int laneId = getLaneIdForCardId(boardId, cardId);

			final int deletedCount = Services.get(IQueryBL.class)
					.createQueryBuilder(I_WEBUI_Board_RecordAssignment.class)
					.addEqualsFilter(I_WEBUI_Board_RecordAssignment.COLUMN_WEBUI_Board_ID, boardId)
					.addEqualsFilter(I_WEBUI_Board_RecordAssignment.COLUMN_Record_ID, cardId)
					.create()
					.deleteDirectly();

			if (deletedCount > 0)
			{
				final LaneCardsSequence orderedCardIds = changeCardsOrder(boardId, laneId, cardIds -> cardIds.removeCardId(cardId));
				eventsCollector.event(JSONBoardLaneChangedEvent.of(boardId, laneId, orderedCardIds.getCardIds()));
			}
		});

		sendEvents(board, eventsCollector.build());
	}

	public BoardCard changeCard(final int boardId, final int cardId, final BoardCardChangeRequest request)
	{
		final BoardDescriptor board = getBoardDescriptor(boardId);

		final JSONBoardChangedEventsListBuilder eventsCollector = JSONBoardChangedEventsList.builder();

		Services.get(ITrxManager.class)
				.run(ITrx.TRXNAME_ThreadInherited, () -> {
					final int oldLaneId = getLaneIdForCardId(boardId, cardId);
					int laneIdEffective = oldLaneId;

					boolean positionChanged = false;

					if (request.getNewLaneId() > 0 && request.getNewLaneId() != oldLaneId)
					{
						final int newLaneId = request.getNewLaneId();
						changeLane(boardId, cardId, newLaneId); // move card to new lane

						final LaneCardsSequence oldLane_cardIds = changeCardsOrder(boardId, oldLaneId, cardIds -> cardIds.removeCardId(cardId)); // update cards order in old lane
						eventsCollector.event(JSONBoardLaneChangedEvent.of(boardId, oldLane_cardIds.getLaneId(), oldLane_cardIds.getCardIds()));

						final LaneCardsSequence newLane_cardIds = changeCardsOrder(boardId, newLaneId, cardIds -> cardIds.addCardIdAtPosition(cardId, request.getNewPosition())); // update cards order in new lane
						eventsCollector.event(JSONBoardLaneChangedEvent.of(boardId, newLane_cardIds.getLaneId(), newLane_cardIds.getCardIds()));

						laneIdEffective = newLaneId;
						positionChanged = true;
					}

					if (!positionChanged && request.getNewPosition() >= 0)
					{
						final int newPosition = request.getNewPosition();
						final LaneCardsSequence laneCardIds = changeCardsOrder(boardId, laneIdEffective, cardIds -> cardIds.addCardIdAtPosition(cardId, newPosition)); // update card's order
						eventsCollector.event(JSONBoardLaneChangedEvent.of(boardId, laneCardIds.getLaneId(), laneCardIds.getCardIds()));

						positionChanged = true;
					}
				});

		final BoardCard card = getCard(boardId, cardId);

		sendEvents(board, eventsCollector.build());
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

	@EqualsAndHashCode
	@ToString
	private static final class LaneCardsSequence
	{
		private final int laneId;
		private final List<Integer> cardIds;

		public LaneCardsSequence(final int laneId, final List<Integer> cardIds)
		{
			this.laneId = laneId;
			this.cardIds = new ArrayList<>(cardIds);
		}

		public LaneCardsSequence copy()
		{
			return new LaneCardsSequence(laneId, cardIds);
		}

		public int getLaneId()
		{
			return laneId;
		}

		private List<Integer> getCardIds()
		{
			return cardIds;
		}

		public void addCardIdAtPosition(final int cardId, final int position)
		{
			Preconditions.checkArgument(cardId > 0, "cardId > 0");

			if (position < 0)
			{
				cardIds.remove((Object)cardId);
				cardIds.add(cardId);
			}
			else if (position == Integer.MAX_VALUE || position > cardIds.size() - 1)
			{
				cardIds.remove((Object)cardId);
				cardIds.add(cardId);
			}
			else
			{
				cardIds.remove((Object)cardId);
				cardIds.add(position, cardId);
			}
		}

		public void removeCardId(final int cardId)
		{
			Preconditions.checkArgument(cardId > 0, "cardId > 0");

			cardIds.remove((Object)cardId);
		}
	}
}
