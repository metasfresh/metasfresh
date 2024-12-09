/*
 * #%L
 * de.metas.externalsystem
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

package de.metas.externalsystem.leichmehl;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

<<<<<<< HEAD
=======
import javax.annotation.Nullable;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import java.util.List;

@Value
@Builder
public class LeichMehlPluFileConfigGroup
{
	@NonNull
	LeichMehlPluFileConfigGroupId id;

	@NonNull
	String name;

<<<<<<< HEAD
=======
	/**
	 * {@code AD_Process.Value} of the optional postgREST-Process which we can instruct externalSystems to invoke via the metasfresh process-API.
	 * Supposed to return additional {@code PP_Order} related data.
	 */
	@Nullable
	String customQueryProcessValue;
	
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	@NonNull
	List<ExternalSystemLeichMehlPluFileConfig> externalSystemLeichMehlPluFileConfigs;
}
