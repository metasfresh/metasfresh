/*
 * #%L
 * de.metas.serviceprovider.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.serviceprovider.github;

import com.google.common.base.Joiner;
import de.metas.serviceprovider.issue.Status;
import de.metas.uom.UomId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public interface GithubImporterConstants
{
	int CHUNK_SIZE = 100;
	UomId HOUR_UOM_ID = UomId.ofRepoId(101);

	DateTimeFormatter LABEL_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@AllArgsConstructor
	@Getter
	enum LabelType
	{
		BUDGET(Pattern.compile("^bud:(?<bud>[0-9]+(\\.[0-9]+)?)$"), "bud", "bud:"),
		ESTIMATION(Pattern.compile("^est:(?<est>[0-9]+(\\.[0-9]+)?)$"), "est", "est:"),
		STATUS(Pattern.compile("^status:(?<status>" + Joiner.on("|").join(Status.listCodes()) + ")$"), "status", "status:"),
		DELIVERY_PLATFORM(Pattern.compile("^ins:(?<platform>[A-Za-z0-9]+)$"), "platform", "ins:"),
		PLANNED_UAT(Pattern.compile("^p_uat:(?<uat>[0-9]{4}-[01][0-9]-[0-3][0-9])$"), "uat", "p_uat:"),
		ROUGH_EST(Pattern.compile("^p_est:(?<pest>[0-9]+(\\.[0-9]+)?)$"), "pest", "p_est:"),
		DELIVERED_DATE(Pattern.compile("^del_date:(?<deldate>[0-9]{4}-[01][0-9]-[0-3][0-9])$"), "deldate", "del_date:"),
		COST_CENTER(Pattern.compile("^cost:(?<cost>[A-Za-z0-9_]+)$"), "cost", "cost:"),
		CUSTOMER(Pattern.compile("^cus:(?<customer>[A-Za-z0-9]+)$"), "customer", "cus:"),

		UNKNOWN(Pattern.compile(".*"), "", "");

		private final Pattern pattern;
		private final String groupName;
		private final String labelPrefix;

		public String wrapValue(@NonNull final String value)
		{
			return getLabelPrefix() + value;
		}

		public static Stream<LabelType> streamKnownLabelTypes()
		{
			return Stream.of(values()).filter(label -> !UNKNOWN.equals(label));
		}
	}
}
