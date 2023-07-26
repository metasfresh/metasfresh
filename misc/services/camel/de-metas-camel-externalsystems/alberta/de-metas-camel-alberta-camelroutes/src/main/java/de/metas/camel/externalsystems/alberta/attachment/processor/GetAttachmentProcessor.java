/*
 * #%L
 * de-metas-camel-alberta-camelroutes
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.camel.externalsystems.alberta.attachment.processor;

import de.metas.camel.externalsystems.alberta.ProcessorHelper;
import de.metas.camel.externalsystems.alberta.attachment.GetAttachmentRouteConstants;
import de.metas.camel.externalsystems.alberta.attachment.GetAttachmentRouteContext;
import io.swagger.client.ApiException;
import io.swagger.client.api.AttachmentApi;
import io.swagger.client.model.Attachment;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import javax.annotation.Nullable;
import java.util.List;

public class GetAttachmentProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final GetAttachmentRouteContext routeContext = ProcessorHelper.getPropertyOrThrowError(exchange, GetAttachmentRouteConstants.ROUTE_PROPERTY_GET_ATTACHMENT_CONTEXT, GetAttachmentRouteContext.class);

		final List<Attachment> attachments = getAttachmentsOrNull(routeContext);
		exchange.getIn().setBody(attachments);
	}

	@Nullable
	final List<Attachment> getAttachmentsOrNull(@NonNull final GetAttachmentRouteContext context) throws ApiException
	{
		final String apiKey = context.getApiKey();
		final AttachmentApi attachmentApi = context.getAttachmentApi();

		final var attachments = attachmentApi.getAllAttachments(apiKey, context.getCreatedAfterAttachment(), null);

		return attachments == null || attachments.isEmpty()
				? null
				: attachments;
	}
}
