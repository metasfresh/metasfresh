/*
 * #%L
 * metasfresh-material-planning
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

package de.metas.material.planning.pporder;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_AD_Workflow;

public enum PPRoutingType implements ReferenceListAwareEnum
{
	Manufacturing(X_AD_Workflow.WORKFLOWTYPE_Manufacturing),
	Quality(X_AD_Workflow.WORKFLOWTYPE_Quality),
	Repair(X_AD_Workflow.WORKFLOWTYPE_Repair),
	;

	private static final ReferenceListAwareEnums.ValuesIndex<PPRoutingType> index = ReferenceListAwareEnums.index(values());

	@Getter
	private final String code;

	PPRoutingType(@NonNull final String code)
	{
		this.code = code;
	}

	public static PPRoutingType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}
}
