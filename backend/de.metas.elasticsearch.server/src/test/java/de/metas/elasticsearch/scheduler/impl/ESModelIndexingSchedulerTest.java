package de.metas.elasticsearch.scheduler.impl;

import org.junit.Assert;
import org.junit.Test;

import de.metas.elasticsearch.scheduler.async.AsyncAddToIndexProcessor;
import de.metas.elasticsearch.scheduler.async.AsyncRemoveFromIndexProcessor;
import de.metas.elasticsearch.scheduler.impl.ESModelIndexingScheduler;

/*
 * #%L
 * de.metas.elasticsearch.server
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

public class ESModelIndexingSchedulerTest
{
	@Test
	public void test_WorkpackageClassnames()
	{
		Assert.assertEquals(AsyncAddToIndexProcessor.class.getName(), ESModelIndexingScheduler.CLASSNAME_AddToIndexWorkpackageProcessor);
		Assert.assertEquals(AsyncRemoveFromIndexProcessor.class.getName(), ESModelIndexingScheduler.CLASSNAME_RemoveFromIndexWorkpackageProcessor);
	}
}
