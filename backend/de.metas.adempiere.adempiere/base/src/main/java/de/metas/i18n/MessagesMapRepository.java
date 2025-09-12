/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.i18n;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Stopwatch;
import de.metas.cache.CCache;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Message;
import org.compiere.util.DB;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static de.metas.i18n.MessageFormatter.normalizeToJavaMessageFormat;

class MessagesMapRepository
{
	private static final Logger logger = LogManager.getLogger(MessagesMapRepository.class);

	private final CCache<Integer, MessagesMap> messagesCache = CCache.<Integer, MessagesMap>builder()
			.cacheName(I_AD_Message.Table_Name + "#by#ADLanguage")
			.tableName(I_AD_Message.Table_Name)
			.initialCapacity(1)
			.expireMinutes(0)
			.build();

	public MessagesMap getMap()
	{
		return messagesCache.getOrLoad(0, MessagesMapRepository::retrieveMap);
	}

	private static MessagesMap retrieveMap()
	{
		if (Adempiere.isUnitTestMode())
		{
			return MessagesMap.EMPTY;
		}

		final Stopwatch stopwatch = Stopwatch.createStarted();
		final String sql = "SELECT"
				+ " m.AD_Message_ID, m.Value, m.MsgText, m.MsgTip, m.ErrorCode,"
				+ " trl.AD_Language, trl.MsgText as trl_MsgText, trl.MsgTip as trl_MsgTip"
				+ " FROM AD_Message m"
				+ " LEFT OUTER JOIN AD_Message_Trl trl on trl.AD_Message_ID=m.AD_Message_ID"
				+ " WHERE m.IsActive='Y'"
				+ " ORDER BY m.AD_Message_ID";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			rs = pstmt.executeQuery();

			final ArrayList<Message> list = new ArrayList<>(2200);

			AdMessageId currentAdMessageId = null;
			AdMessageKey currentAdMessage = null;
			ImmutableTranslatableString.ImmutableTranslatableStringBuilder msgTextBuilder = null;
			ImmutableTranslatableString.ImmutableTranslatableStringBuilder msgTipBuilder = null;
			String errorCode = null;

			while (rs.next())
			{
				final AdMessageId adMessageId = AdMessageId.ofRepoId(rs.getInt("AD_Message_ID"));
				if (!AdMessageId.equals(adMessageId, currentAdMessageId))
				{
					if (currentAdMessageId != null)
					{
						list.add(Message.ofTextTipAndErrorCode(currentAdMessageId, currentAdMessage, msgTextBuilder.build(), msgTipBuilder.build(), errorCode));
						currentAdMessageId = null;
						currentAdMessage = null;
						msgTextBuilder = null;
						msgTipBuilder = null;
						errorCode = null;
					}
				}

				if (currentAdMessageId == null)
				{
					currentAdMessageId = adMessageId;
					currentAdMessage = AdMessageKey.of(rs.getString("Value"));
					msgTextBuilder = ImmutableTranslatableString.builder().defaultValue(normalizeToJavaMessageFormat(rs.getString("MsgText")));
					msgTipBuilder = ImmutableTranslatableString.builder().defaultValue(normalizeToJavaMessageFormat(rs.getString("MsgTip")));
					errorCode = rs.getString("ErrorCode");
				}

				final String adLanguage = rs.getString("AD_Language");
				if (Check.isBlank(adLanguage))
				{
					continue;
				}
				msgTextBuilder.trl(adLanguage, normalizeToJavaMessageFormat(rs.getString("trl_MsgText")));
				msgTipBuilder.trl(adLanguage, normalizeToJavaMessageFormat(rs.getString("trl_MsgTip")));
			}

			if (currentAdMessageId != null)
			{
				list.add(Message.ofTextTipAndErrorCode(currentAdMessageId, currentAdMessage, msgTextBuilder.build(), msgTipBuilder.build(), errorCode));
			}

			final MessagesMap result = new MessagesMap(list);
			logger.info("Loaded {} in {}", result, stopwatch);
			return result;
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	@VisibleForTesting
	static String normalizeToJavaMessageFormat(@Nullable final String text)
	{
		if (text == null)
		{
			return "";
		}
		if (text.isEmpty())
		{
			return text;
		}

		int firstIdx = text.indexOf("{}");
		if (firstIdx < 0)
		{
			return text;
		}

		String inStr = text;
		int idx = firstIdx;
		final StringBuilder outStr = new StringBuilder();
		int nextPlaceholderIndex = 0;
		while (idx != -1)
		{
			outStr.append(inStr, 0, idx);            // up to {}
			inStr = inStr.substring(idx + 2);    // continue after current {}

			final int placeholderIndex = nextPlaceholderIndex;
			nextPlaceholderIndex++;
			outStr.append("{").append(placeholderIndex).append("}");

			idx = inStr.indexOf("{}");
		}

		outStr.append(inStr);                            // add remainder
		return outStr.toString();
	}

	public void cacheReset()
	{
		messagesCache.reset();
	}
}
