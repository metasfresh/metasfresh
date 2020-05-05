package de.metas.security.permissions.bpartner_hierarchy.handlers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

import de.metas.bpartner.BPartnerId;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

@ToString(of = "handlersByTableName")
public class BPartnerDependentDocumentHandlersMap
{
	public static BPartnerDependentDocumentHandlersMap of(@NonNull final List<BPartnerDependentDocumentHandler> handlers)
	{
		return new BPartnerDependentDocumentHandlersMap(handlers);
	}

	private final ImmutableMap<String, BPartnerDependentDocumentHandler> handlersByTableName;

	private BPartnerDependentDocumentHandlersMap(@NonNull final List<BPartnerDependentDocumentHandler> handlers)
	{
		handlersByTableName = Maps.uniqueIndex(handlers, BPartnerDependentDocumentHandler::getDocumentTableName);
	}

	public ImmutableSet<String> getTableNames()
	{
		return handlersByTableName.keySet();
	}

	public Stream<TableRecordReference> streamBPartnerRelatedRecords(@NonNull final BPartnerId bpartnerId)
	{
		return handlersByTableName.values()
				.stream()
				.flatMap(handler -> handler.streamRelatedDocumentsByBPartnerId(bpartnerId));
	}

	public Optional<BPartnerDependentDocument> extractBPartnerDependentDocumentFromDocumentObj(final Object documentObj)
	{
		final String documentTableName = InterfaceWrapperHelper.getModelTableName(documentObj);
		final BPartnerDependentDocumentHandler handler = handlersByTableName.get(documentTableName);
		if (handler == null)
		{
			return Optional.empty();
		}

		final BPartnerDependentDocument doc = handler.extractBPartnerDependentDocumentFromDocumentObj(documentObj);
		return Optional.of(doc);
	}
}
