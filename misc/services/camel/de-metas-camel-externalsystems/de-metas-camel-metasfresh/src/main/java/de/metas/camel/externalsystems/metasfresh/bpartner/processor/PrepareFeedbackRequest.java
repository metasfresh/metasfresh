/*
 * #%L
 * de-metas-camel-metasfresh
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.camel.externalsystems.metasfresh.bpartner.processor;

import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.metasfresh.restapi.model.JsonMassUpsertFeedbackRequest;
import de.metas.camel.externalsystems.metasfresh.bpartner.UpsertBPartnersRouteContext;
import de.metas.camel.externalsystems.metasfresh.restapi.feedback.FeedbackConfig;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.tomcat.util.buf.StringUtils;

import java.sql.Timestamp;

import static de.metas.camel.externalsystems.metasfresh.MetasfreshConstants.AUTHORIZATION;
import static de.metas.camel.externalsystems.metasfresh.MetasfreshConstants.RESPONSE_ITEMS_NO_PROPERTY;
import static de.metas.camel.externalsystems.metasfresh.MetasfreshConstants.RESPONSE_URL_HEADER;
import static de.metas.camel.externalsystems.metasfresh.bpartner.FileBPartnersRouteBuilder.ROUTE_PROPERTY_UPSERT_BPARTNERS_CONTEXT;

public class PrepareFeedbackRequest implements Processor
{
	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final Integer responseItemsNo = exchange.getProperty(RESPONSE_ITEMS_NO_PROPERTY, Integer.class);
		final UpsertBPartnersRouteContext upsertBPartnersRouteContext = exchange.getProperty(ROUTE_PROPERTY_UPSERT_BPARTNERS_CONTEXT, UpsertBPartnersRouteContext.class);

		final FeedbackConfig feedbackConfig = getFeedbackConfig(upsertBPartnersRouteContext);

		exchange.getIn().setHeader(AUTHORIZATION, feedbackConfig.getResponseAuthKey());
		exchange.getIn().setHeader(RESPONSE_URL_HEADER, feedbackConfig.getResponseUrl());

		final JsonMassUpsertFeedbackRequest jsonMassUpsertResponse = buildJsonMassUpsertFeedbackRequest(responseItemsNo, upsertBPartnersRouteContext);

		exchange.getIn().setBody(jsonMassUpsertResponse, JsonMassUpsertFeedbackRequest.class);
	}

	@NonNull
	private JsonMassUpsertFeedbackRequest buildJsonMassUpsertFeedbackRequest(
			@NonNull final Integer responseItemsNo,
			@NonNull final UpsertBPartnersRouteContext upsertBPartnersRouteContext)
	{
		final Timestamp finishDate = new Timestamp(System.currentTimeMillis());
		final boolean success = responseItemsNo.equals(upsertBPartnersRouteContext.getTotalItemsToUpsert());

		return JsonMassUpsertFeedbackRequest.builder()
				.batchId(upsertBPartnersRouteContext.getBatchId())
				.finishDate(finishDate)
				.itemCount(responseItemsNo)
				.success(success)
				.errorInfo(getErrorInfo(upsertBPartnersRouteContext))
				.build();
	}

	@NonNull
	private FeedbackConfig getFeedbackConfig(@NonNull final UpsertBPartnersRouteContext upsertBPartnersRouteContext)
	{
		final FeedbackConfig feedbackConfig = upsertBPartnersRouteContext.getFeedbackConfig();

		if (feedbackConfig == null)
		{
			throw new RuntimeException("Feedback config cannot be null at this stage!");
		}
		return feedbackConfig;
	}

	@NonNull
	private String getErrorInfo(@NonNull final UpsertBPartnersRouteContext upsertBPartnersRouteContext)
	{
		final ImmutableList.Builder<String> errorsCollector = upsertBPartnersRouteContext.getErrorsCollector();

		return StringUtils.join(errorsCollector.build(), '\n');
	}
}
