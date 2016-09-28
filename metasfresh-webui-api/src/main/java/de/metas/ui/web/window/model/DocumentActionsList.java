package de.metas.ui.web.window.model;

import java.util.List;

import org.adempiere.ad.process.ISvrProcessPrecondition.PreconditionsContext;
import org.adempiere.util.GuavaCollectors;

import com.google.common.collect.ImmutableList;

/*
 * #%L
 * metasfresh-webui-api
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

public class DocumentActionsList
{
	public static final DocumentActionsList of(final List<DocumentAction> documentActions)
	{
		if (documentActions == null || documentActions.isEmpty())
		{
			return EMPTY;
		}
		return new DocumentActionsList(documentActions);
	}

	private static final DocumentActionsList EMPTY = new DocumentActionsList(ImmutableList.of());

	private final List<DocumentAction> documentActions;
	private final boolean hasPreconditions;

	private DocumentActionsList(final List<DocumentAction> documentActions)
	{
		super();
		this.documentActions = ImmutableList.copyOf(documentActions);

		boolean hasPreconditions = false;
		for (final DocumentAction action : documentActions)
		{
			if (action.hasPreconditions())
			{
				hasPreconditions = true;
				break;
			}
		}
		this.hasPreconditions = hasPreconditions;
	}

	public DocumentActionsList getApplicableActions(final PreconditionsContext context)
	{
		if (!hasPreconditions)
		{
			return this;
		}

		final List<DocumentAction> applicableActions = documentActions
				.stream()
				.filter(action -> action.isApplicableOn(context))
				.collect(GuavaCollectors.toImmutableList());

		if (applicableActions.size() == documentActions.size())
		{
			return this;
		}

		return DocumentActionsList.of(applicableActions);
	}

	public List<DocumentAction> asList()
	{
		return documentActions;
	}
}
