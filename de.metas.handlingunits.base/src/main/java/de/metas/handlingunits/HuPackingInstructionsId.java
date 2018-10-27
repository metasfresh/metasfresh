package de.metas.handlingunits;

import java.util.Objects;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

/*
 * #%L
 * de.metas.handlingunits.base
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

/**
 * aka M_HU_PI_ID
 */
@Value
public class HuPackingInstructionsId implements RepoIdAware
{
	public static HuPackingInstructionsId ofRepoId(final int repoId)
	{
		if (repoId == TEMPLATE.repoId)
		{
			return TEMPLATE;
		}
		else if (repoId == VIRTUAL.repoId)
		{
			return VIRTUAL;
		}
		else
		{
			return new HuPackingInstructionsId(repoId);
		}
	}

	public static HuPackingInstructionsId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	public static int toRepoId(final HuPackingInstructionsId HuPackingInstructionsId)
	{
		return HuPackingInstructionsId != null ? HuPackingInstructionsId.getRepoId() : -1;
	}

	public static HuPackingInstructionsId TEMPLATE = new HuPackingInstructionsId(100);
	public static HuPackingInstructionsId VIRTUAL = new HuPackingInstructionsId(101);

	int repoId;

	private HuPackingInstructionsId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "M_HU_PI_ID");
	}

	public static boolean equals(final HuPackingInstructionsId o1, final HuPackingInstructionsId o2)
	{
		return Objects.equals(o1, o2);
	}

	public boolean isTemplate()
	{
		return isTemplateRepoId(repoId);
	}

	public static boolean isTemplateRepoId(final int repoId)
	{
		return repoId == TEMPLATE.repoId;
	}

	public boolean isVirtual()
	{
		return isVirtualRepoId(repoId);

	}

	public static boolean isVirtualRepoId(final int repoId)
	{
		return repoId == VIRTUAL.repoId;
	}

	public boolean isRealPackingInstructions()
	{
		return isRealPackingInstructionsRepoId(repoId);
	}

	public static boolean isRealPackingInstructionsRepoId(final int repoId)
	{
		return repoId > 0
				&& !isTemplateRepoId(repoId)
				&& !isVirtualRepoId(repoId);
	}
}
