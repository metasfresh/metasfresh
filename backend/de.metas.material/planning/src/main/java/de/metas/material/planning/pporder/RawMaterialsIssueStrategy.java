/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2024 metas GmbH
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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_AD_WF_Node;
import org.eevolution.api.BOMIssueMethod;

import javax.annotation.Nullable;

@AllArgsConstructor
public enum RawMaterialsIssueStrategy implements ReferenceListAwareEnum
{
	AssignedHUsOnly(X_AD_WF_Node.RAWMATERIALSISSUESTRATEGY_OnlyAssignedHUs),
	DEFAULT(null);

	@Getter
	private final String code;

	public boolean applies(@Nullable final BOMIssueMethod issueMethod)
	{
		switch (this)
		{
			case DEFAULT:
				return true;
			case AssignedHUsOnly:
				return BOMIssueMethod.Planning == issueMethod;
			default:
				throw new AdempiereException("RawMaterialsIssueStrategy not supported!")
						.appendParametersToMessage()
						.setParameter("RawMaterialsIssueStrategy", this);
		}
	}

	@Nullable
	public static String toCode(@Nullable final RawMaterialsIssueStrategy strategy)
	{
		return strategy == null
				? null
				: strategy.getCode();
	}

	@NonNull
	public static RawMaterialsIssueStrategy ofCodeOrDefault(@Nullable final String code)
	{
		if (AssignedHUsOnly.getCode().equals(code))
		{
			return AssignedHUsOnly;
		}

		return DEFAULT;
	}
}
