package de.metas.ordercandidate.api;

import de.metas.externalsystem.ExternalSystemType;
import de.metas.impex.model.I_AD_InputDataSource;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

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
public class OLCandQuery
{
	/**
	 * ID (e.g. document number), of a source document in a remote system; multiple OLCands can have the same ID
	 */
	String externalHeaderId;

	/**
	 * {@link de.metas.externalsystem.model.I_ExternalSystem#COLUMNNAME_Value} of the external system the candidates in question were added with.
	 */
	ExternalSystemType externalSystemType;

	/**
	 * {@link I_AD_InputDataSource#COLUMNNAME_InternalName} of the data source the candidates in question were added with.
	 */
	String inputDataSourceName;

	String externalLineId;

	OrgId orgId;

	@Builder
	private OLCandQuery(
			final String externalHeaderId,
			final ExternalSystemType externalSystemType,
			final String inputDataSourceName,
			final String externalLineId,
			final OrgId orgId)
	{
		if (externalHeaderId != null)
		{
			Check.assumeNotEmpty(externalHeaderId, "If externalHeaderId is specified, then it may not be empty");

			if (externalSystemType == null && externalLineId == null)
			{
				throw new AdempiereException("If externalHeaderId is specified, then externalSystemType or externalLineId should be defined; externalHeaderId=" + externalHeaderId);
			}
		}

		this.externalHeaderId = externalHeaderId;
		this.externalSystemType = externalSystemType;
		this.inputDataSourceName = inputDataSourceName;

		this.externalLineId = externalLineId;
		this.orgId = orgId;
	}
}
