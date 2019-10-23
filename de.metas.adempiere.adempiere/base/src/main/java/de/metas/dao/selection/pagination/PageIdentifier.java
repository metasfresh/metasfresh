package de.metas.dao.selection.pagination;

import static de.metas.util.Check.assume;

import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

@Value
@Builder
public class PageIdentifier
{
	public static final String SEPARATOR = "_";
	private static final Splitter SPLITTER = Splitter.on(SEPARATOR).trimResults();
	private static final Joiner JOINER = Joiner.on(SEPARATOR);

	public static PageIdentifier ofCombinedString(@NonNull String completePageId)
	{
		final List<String> split = SPLITTER.splitToList(completePageId);
		assume(split.size() == 2, "Param completePageId needs to consist of two components; completePageId={}", completePageId);

		return new PageIdentifier(split.get(0), split.get(1));
	}

	@NonNull
	String selectionUid;

	@NonNull
	String pageUid;

	public String getCombinedUid()
	{
		return JOINER.join(selectionUid, pageUid);
	}
}
