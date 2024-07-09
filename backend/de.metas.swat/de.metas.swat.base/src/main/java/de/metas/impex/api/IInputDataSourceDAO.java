package de.metas.impex.api;

import de.metas.impex.InputDataSourceId;
import de.metas.impex.api.impl.InputDataSourceQuery;
import de.metas.impex.model.I_AD_InputDataSource;
import de.metas.util.ISingletonService;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Properties;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface IInputDataSourceDAO extends ISingletonService
{
	I_AD_InputDataSource getById(final InputDataSourceId id);

	I_AD_InputDataSource retrieveInputDataSource(Properties ctx, String internalName, boolean throwEx, @Nullable String trxName);

	InputDataSourceId retrieveInputDataSourceIdByInternalName(String internalName);

	void createIfMissing(InputDataSourceCreateRequest request);

	Optional<InputDataSourceId> retrieveInputDataSourceIdBy(InputDataSourceQuery query);

}
