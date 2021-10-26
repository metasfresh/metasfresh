package de.metas.ordercandidate.api;

import de.metas.impex.model.I_AD_InputDataSource;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * de.metas.swat.base
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
@Builder
public class OLCandQuery
{
	/**
	 * ID (e.g. document number), of a source document in a remote system; multiple OLCands can have the same ID
	 */
	String externalHeaderId;

	/**
	 * {@link I_AD_InputDataSource#COLUMNNAME_InternalName} of the data source the candidates in question were added with.
	 */
	String inputDataSourceName;

	String externalLineId;

	OrgId orgId;

	public OLCandQuery(
			final String externalHeaderId,
			final String inputDataSourceName,
			final String externalLineId,
			final OrgId orgId)
	{
		if (externalHeaderId != null)
		{
			Check.assumeNotEmpty(externalHeaderId, "If externalHeaderId is specified, then it may not be empty");

			if (inputDataSourceName == null && externalLineId == null)
			{
				Check.assumeNotEmpty(inputDataSourceName, "If externalHeaderId is specified, then inputDataSourceName or externalLineId should be defined; externalHeaderId={}", externalHeaderId);
				Check.assumeNotEmpty(externalLineId, "If externalHeaderId is specified, then inputDataSourceName or externalLineId should be defined; externalHeaderId={}", externalHeaderId);
			}
		}

		this.externalHeaderId = externalHeaderId;
		this.inputDataSourceName = inputDataSourceName;

		this.externalLineId = externalLineId;
		this.orgId = orgId;
	}
}
