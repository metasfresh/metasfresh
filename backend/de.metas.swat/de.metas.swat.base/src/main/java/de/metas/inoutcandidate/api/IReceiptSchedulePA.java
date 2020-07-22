/*
 * #%L
 * de.metas.swat.base
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

package de.metas.inoutcandidate.api;

import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.order.OrderId;
import de.metas.process.PInstanceId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;

import java.util.Properties;

/**
 * Implementers give database access to {@link I_M_ReceiptSchedule} instances (DAO).
 *
 * @author dp
 *
 */

public interface IReceiptSchedulePA extends ISingletonService
{
	IQueryBuilder<I_M_ReceiptSchedule> createQueryForShipmentScheduleSelection(Properties ctx, IQueryFilter<I_M_ReceiptSchedule> userSelectionFilter);

	boolean existsExportedReceiptScheduleForOrder(@NonNull final OrderId orderId);

	void updateExportStatus(final String exportStatus, final PInstanceId pinstanceId);
}
