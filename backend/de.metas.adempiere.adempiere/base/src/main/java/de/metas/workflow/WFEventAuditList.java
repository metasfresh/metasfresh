/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.workflow;

import com.google.common.collect.ImmutableList;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;

@EqualsAndHashCode
@ToString
public final class WFEventAuditList
{
	private final ArrayList<WFEventAudit> list = new ArrayList<>();

	private final HashMap<WFNodeId, WFEventAudit> lastEventByNodeId = new HashMap<>();

	public WFEventAudit getLastOrNull(@NonNull final WFNodeId wfNodeId)
	{
		return lastEventByNodeId.get(wfNodeId);
	}

	public void add(@NonNull final WFEventAudit audit)
	{
		list.add(audit);
		lastEventByNodeId.put(audit.getWfNodeId(), audit);
	}

	public ImmutableList<WFEventAudit> toList()
	{
		return ImmutableList.copyOf(list);
	}

}
