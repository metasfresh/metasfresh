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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Value
public class HuPackingInstructionsVersionId implements RepoIdAware
{
	public static HuPackingInstructionsVersionId ofRepoId(final int repoId)
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
			return new HuPackingInstructionsVersionId(repoId);
		}
	}

	public static HuPackingInstructionsVersionId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	public static int toRepoId(final HuPackingInstructionsVersionId HuPackingInstructionsVersionId)
	{
		return HuPackingInstructionsVersionId != null ? HuPackingInstructionsVersionId.getRepoId() : -1;
	}

	public static HuPackingInstructionsVersionId TEMPLATE = new HuPackingInstructionsVersionId(100);
	public static HuPackingInstructionsVersionId VIRTUAL = new HuPackingInstructionsVersionId(101);

	int repoId;

	private HuPackingInstructionsVersionId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "M_HU_PI_Version_ID");
	}

	public static boolean equals(final HuPackingInstructionsVersionId o1, final HuPackingInstructionsVersionId o2)
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
