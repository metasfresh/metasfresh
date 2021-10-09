/*
 * #%L
 * de-metas-edi-esb-camel
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.edi.esb.commons.processor.feedback;

import static de.metas.edi.esb.commons.processor.feedback.helper.EDIXmlFeedbackHelper.createEDIFeedbackType;

import javax.xml.namespace.QName;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import de.metas.edi.esb.jaxb.metasfresh.EDIExportStatusEnum;

public class EDIXmlSuccessFeedbackProcessor<T> implements Processor
{
	private final Class<T> feedbackType;
	private final QName feedbackQName;
	private final String recordIdSetter;

	public EDIXmlSuccessFeedbackProcessor(final Class<T> feedbackType, final QName feedbackQName, final String recordIdSetter)
	{
		this.feedbackType = feedbackType;
		this.feedbackQName = feedbackQName;
		this.recordIdSetter = recordIdSetter;
	}

	@Override
	public void process(final Exchange exchange)
	{
		createEDIFeedbackType(exchange, EDIExportStatusEnum.Sent, feedbackType, feedbackQName, recordIdSetter);
	}
}
