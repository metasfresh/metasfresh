package de.metas.rfq.impl;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;

import de.metas.rfq.IRfqTopicDAO;
import de.metas.rfq.model.I_C_RfQ_Topic;
import de.metas.rfq.model.I_C_RfQ_TopicSubscriber;
import de.metas.rfq.model.I_C_RfQ_TopicSubscriberOnly;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class RfqTopicDAO implements IRfqTopicDAO
{
	@Override
	public List<I_C_RfQ_TopicSubscriber> retrieveSubscribers(final I_C_RfQ_Topic rfqTopic)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_RfQ_TopicSubscriber.class, rfqTopic)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_RfQ_TopicSubscriber.COLUMN_C_RfQ_Topic_ID, rfqTopic.getC_RfQ_Topic_ID())
				.orderBy()
				.addColumn(I_C_RfQ_TopicSubscriber.COLUMN_C_RfQ_TopicSubscriber_ID)
				.endOrderBy()
				.create()
				.list(I_C_RfQ_TopicSubscriber.class);
	}

	@Override
	public List<I_C_RfQ_TopicSubscriberOnly> retrieveRestrictions(final I_C_RfQ_TopicSubscriber rfqSubscriber)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_RfQ_TopicSubscriberOnly.class, rfqSubscriber)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_RfQ_TopicSubscriberOnly.COLUMN_C_RfQ_TopicSubscriber_ID, rfqSubscriber.getC_RfQ_TopicSubscriber_ID())
				.create()
				.list(I_C_RfQ_TopicSubscriberOnly.class);
	}

}
