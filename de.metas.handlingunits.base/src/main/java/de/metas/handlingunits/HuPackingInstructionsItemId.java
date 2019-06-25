package de.metas.handlingunits;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2019 metas GmbH
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
 * aka M_HU_PI_Item_ID
 */
@Value
public class HuPackingInstructionsItemId implements RepoIdAware
{
	@JsonCreator
	public static HuPackingInstructionsItemId ofRepoId(final int repoId)
	{
		if (repoId == TEMPLATE_MATERIAL_ITEM.repoId)
		{
			return TEMPLATE_MATERIAL_ITEM;
		}
		if (repoId == TEMPLATE_PACKING_ITEM.repoId)
		{
			return TEMPLATE_PACKING_ITEM;
		}
		else if (repoId == VIRTUAL.repoId)
		{
			return VIRTUAL;
		}
		else
		{
			return new HuPackingInstructionsItemId(repoId);
		}
	}

	public static HuPackingInstructionsItemId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	public static int toRepoId(final HuPackingInstructionsItemId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	public static final HuPackingInstructionsItemId TEMPLATE_MATERIAL_ITEM = new HuPackingInstructionsItemId(540004);
	private static final HuPackingInstructionsItemId TEMPLATE_PACKING_ITEM = new HuPackingInstructionsItemId(100);
	public static final HuPackingInstructionsItemId VIRTUAL = new HuPackingInstructionsItemId(101);

	int repoId;

	private HuPackingInstructionsItemId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "M_HU_PI_Item_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(final HuPackingInstructionsItemId o1, final HuPackingInstructionsItemId o2)
	{
		return Objects.equals(o1, o2);
	}

	public boolean isTemplate()
	{
		return isTemplateRepoId(repoId);
	}

	public static boolean isTemplateRepoId(final int repoId)
	{
		return repoId == TEMPLATE_MATERIAL_ITEM.repoId
				|| repoId == TEMPLATE_PACKING_ITEM.repoId;
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
