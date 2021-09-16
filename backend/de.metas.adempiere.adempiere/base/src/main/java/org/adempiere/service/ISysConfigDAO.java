package org.adempiere.service;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.organization.ClientAndOrgId;
import de.metas.util.ISingletonService;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public interface ISysConfigDAO extends ISingletonService
{
	/**
	 * Notes:
	 * <ul>
	 * <li>If we run as spring application, then values can also be set via commandLine <code>-Dname=value</code> or via spring <code>application.properties</code> to take precendence over the
	 * <code>AD_SysConfig</code> record.<br>
	 * But note that in this case, <code>AD_Client_ID</code> and <code>AD_Org_ID</code> are ignored.
	 * <li>If there is more than one matching record, the value of the first <code>AD_SysConfig</code> record, according to <code>ORDER BY AD_Client_ID DESC, AD_Org_ID DESC</code> will be returned.
	 * </ul>
	 */
	Optional<String> getValue(String name, ClientAndOrgId clientAndOrgId);

	List<String> retrieveNamesForPrefix(String prefix, ClientAndOrgId clientAndOrgId);

	void setValue(@NonNull final String name, @Nullable final String value, @NonNull final ClientAndOrgId clientAndOrgId);

	void setValue(@NonNull final String name, final boolean value, @NonNull final ClientAndOrgId clientAndOrgId);

	void setValue(@NonNull final String name, final int value, @NonNull final ClientAndOrgId clientAndOrgId);
}
