/*
 * #%L
 * de.metas.serviceprovider.base
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

package de.metas.serviceprovider.external.label;

import de.metas.organization.OrgId;
import de.metas.serviceprovider.github.GithubImporterConstants;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.util.regex.Matcher;

@Builder
@Value
public class IssueLabel
{
	@NonNull
	OrgId orgId;

	@NonNull
	String value;

	public boolean matchesType(@NonNull final GithubImporterConstants.LabelType labelType)
	{
		final Matcher matcher = labelType.getPattern().matcher(value);

		return matcher.matches();
	}

	@NonNull
	public String getValueForType(@NonNull final GithubImporterConstants.LabelType labelType)
	{
		final Matcher matcher = labelType.getPattern().matcher(value);

		if (!matcher.matches())
		{
			throw new AdempiereException("Value doesn't match the label patten!")
					.appendParametersToMessage()
					.setParameter("labelType", labelType.name())
					.setParameter("value", value);
		}

		return matcher.group(labelType.getGroupName());
	}
}
