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

package de.metas.camel.externalsystems.metasfresh.restapi.feedback;

import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.metasfresh.restapi.model.JsonMassUpsertFeedbackRequest;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.tomcat.util.buf.StringUtils;

import java.time.Instant;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;
import static de.metas.camel.externalsystems.metasfresh.MetasfreshConstants.FEEDBACK_RESOURCE_URL_HEADER;
import static de.metas.camel.externalsystems.metasfresh.MetasfreshConstants.MASS_UPLOAD_STATISTICS_COLLECTOR_PROPERTY;

public class PrepareFeedbackRequest implements Processor
{
	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final MassUpsertStatisticsCollector massUpsertStatisticsCollector = exchange.getProperty(MASS_UPLOAD_STATISTICS_COLLECTOR_PROPERTY,
																								 MassUpsertStatisticsCollector.class);

		final FeedbackConfig feedbackConfig = massUpsertStatisticsCollector.getFeedbackConfig();

		if (feedbackConfig == null)
		{
			exchange.getIn().setBody(null);
			return;
		}

		exchange.getIn().setHeader(AUTHORIZATION, feedbackConfig.getAuthToken());
		exchange.getIn().setHeader(FEEDBACK_RESOURCE_URL_HEADER, feedbackConfig.getFeedbackResourceURL());

		final JsonMassUpsertFeedbackRequest jsonMassUpsertResponse = buildJsonMassUpsertFeedbackRequest(massUpsertStatisticsCollector);

		exchange.getIn().setBody(jsonMassUpsertResponse, JsonMassUpsertFeedbackRequest.class);
	}

	@NonNull
	private JsonMassUpsertFeedbackRequest buildJsonMassUpsertFeedbackRequest(@NonNull final MassUpsertStatisticsCollector massUpsertStatisticsCollector)
	{
		final ImmutableList<String> errorsCollector = massUpsertStatisticsCollector.getCollectedErrors();

		return JsonMassUpsertFeedbackRequest.builder()
				.batchId(massUpsertStatisticsCollector.getBatchId())
				.finishDate(Instant.now())
				.itemCount(massUpsertStatisticsCollector.getProcessedItemsCount())
				.success(errorsCollector.size() == 0)
				.errorInfo(StringUtils.join(errorsCollector, '\n'))
				.build();
	}
}
