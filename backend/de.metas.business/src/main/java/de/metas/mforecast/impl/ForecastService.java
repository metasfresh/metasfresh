/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.mforecast.impl;

import com.google.common.collect.ImmutableSet;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.AdMessageKey;
import de.metas.mforecast.ForecastRequest;
import de.metas.mforecast.IForecastDAO;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_Forecast;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForecastService
{
	private final static AdMessageKey FORECAST_COMPLETED_NOTIFICATION = AdMessageKey.of("ForecastDocumentCompleted");

	@NonNull private final IForecastDAO forecastDAO = Services.get(IForecastDAO.class);
	@NonNull private final IDocumentBL docActionBL = Services.get(IDocumentBL.class);
	@NonNull private final INotificationBL userNotifications = Services.get(INotificationBL.class);

	@NonNull
	public ImmutableSet<ForecastId> createForecasts(@NonNull final List<ForecastRequest> requests)
	{
		return requests.stream()
				.map(forecastDAO::createForecast)
				.collect(ImmutableSet.toImmutableSet());
	}

	public void completeAndNotifyUser(@NonNull final ImmutableSet<ForecastId> ids, @NonNull final UserId userId)
	{
		forecastDAO.streamRecordsByIds(ids).forEach(forecast -> {
			complete(forecast);
			sendForecastCompleteNotification(forecast, userId);
		});
	}

	private void complete(@NonNull final I_M_Forecast forecast)
	{
		docActionBL.processEx(forecast, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
	}

	private void sendForecastCompleteNotification(@NonNull final I_M_Forecast forecast, @NonNull final UserId userId)
	{
		final UserNotificationRequest userNotificationRequest = UserNotificationRequest.builder()
				.recipientUserId(userId)
				.contentADMessage(FORECAST_COMPLETED_NOTIFICATION)
				.contentADMessageParam(forecast.getName() + "|" + forecast.getM_Forecast_ID())
				.targetAction(UserNotificationRequest.TargetRecordAction.of(TableRecordReference.of(forecast)))
				.build();
		userNotifications.send(userNotificationRequest);
	}
}
