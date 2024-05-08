package de.metas.order.voidorderandrelateddocs;

import java.util.List;

import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ITableRecordReference;

import de.metas.order.OrderId;
import de.metas.order.voidorderandrelateddocs.VoidOrderAndRelatedDocsHandler.RecordsToHandleKey;
import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
@Builder(toBuilder = true)
public class VoidOrderAndRelatedDocsRequest
{
	String voidedOrderDocumentNoPrefix;

	OrderId orderId;

	/**
	 * The record references to handle (cancel, void, reverse etc).
	 * They pair's key is used to identify the matching {@link VoidOrderAndRelatedDocsHandler} implementation(s).
	 */
	IPair<RecordsToHandleKey, List<ITableRecordReference>> recordsToHandle;
}
