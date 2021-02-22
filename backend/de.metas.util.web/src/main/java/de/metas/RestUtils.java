/*
 * #%L
 * de.metas.util.web
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas;

import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgQuery;
import de.metas.util.Services;
import de.metas.util.web.exception.MissingResourceException;
import lombok.experimental.UtilityClass;
import org.compiere.util.Env;

import javax.annotation.Nullable;

import static de.metas.util.Check.isNotBlank;

@UtilityClass
public class RestUtils
{
	public static OrgId retrieveOrgIdOrDefault(@Nullable final String orgCode)
	{
		final OrgId orgId;
		if (isNotBlank(orgCode))
		{
			orgId = Services.get(IOrgDAO.class)
					.retrieveOrgIdBy(OrgQuery.ofValue(orgCode))
					.orElseThrow(() -> MissingResourceException.builder()
							.resourceName("organisation")
							.resourceIdentifier(orgCode).build());
		}
		else
		{
			orgId = Env.getOrgId();
		}
		return orgId;
	}
}
