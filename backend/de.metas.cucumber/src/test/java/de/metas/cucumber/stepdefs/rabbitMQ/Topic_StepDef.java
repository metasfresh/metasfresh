/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.cucumber.stepdefs.rabbitMQ;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.event.Topic;
import de.metas.event.Type;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;

import java.util.List;
import java.util.Map;

public class Topic_StepDef
{
	private final Topic_StepDefData topicTable;

	public Topic_StepDef(@NonNull final Topic_StepDefData topicTable)
	{
		this.topicTable = topicTable;
	}

	@And("topic is created")
	public void register_topic(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			registerTopic(tableRow);
		}
	}

	private void registerTopic(@NonNull final Map<String, String> tableRow)
	{
		final String identifier = DataTableUtil.extractStringForColumnName(tableRow, "Identifier");
		final String topicName = DataTableUtil.extractStringForColumnName(tableRow, "Topic.Name");
		final String topicType = DataTableUtil.extractStringForColumnName(tableRow, "Topic.Type");

		final Topic topic = Topic.of(topicName, Type.valueOf(topicType));

		topicTable.put(identifier, topic);
	}
}
