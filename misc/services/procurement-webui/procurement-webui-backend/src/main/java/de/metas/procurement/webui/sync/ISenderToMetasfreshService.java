package de.metas.procurement.webui.sync;

import de.metas.procurement.webui.model.ProductSupply;
import de.metas.procurement.webui.model.Rfq;
import de.metas.procurement.webui.model.WeekSupply;

import java.util.List;
import java.util.concurrent.TimeUnit;

/*
 * #%L
 * de.metas.procurement.webui
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

/**
 * Sends requests to metasfresh server
 */
public interface ISenderToMetasfreshService
{
	void awaitInitialSyncComplete(long timeout, TimeUnit unit) throws InterruptedException;

	void requestFromMetasfreshAllMasterdataAsync();

	void pushDailyReportsAsync(List<ProductSupply> productSupplies);

	void pushWeeklyReportsAsync(List<WeekSupply> weeklySupplies);

	void pushRfqsAsync(List<Rfq> rfqs);

	ISyncAfterCommitCollector syncAfterCommit();
}
