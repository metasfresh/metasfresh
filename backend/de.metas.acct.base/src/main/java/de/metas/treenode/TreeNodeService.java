/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.treenode;

import org.compiere.model.I_C_ElementValue;
import org.springframework.stereotype.Service;

import de.metas.elementvalue.ElementValue;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.elementvalue.ElementValueRepository;
import lombok.NonNull;

@Service
public class TreeNodeService
{
	private final ElementValueRepository elementValueRepo;
	private final TreeNodeRepository treeNodeRepo;

	public TreeNodeService(
			@NonNull final ElementValueRepository elementValueRepo,
			@NonNull final TreeNodeRepository treeNodeRepo)
	{
		this.elementValueRepo = elementValueRepo;
		this.treeNodeRepo = treeNodeRepo;
	}

	public void updateTreeNode(@NonNull final I_C_ElementValue elementValueRecord)
	{
		final ElementValueId evId = ElementValueId.ofRepoIdOrNull(elementValueRecord.getC_ElementValue_ID());
		final ElementValue elementValue = elementValueRepo.getById(evId);

		// treeNode base on all the data from element value
		final TreeNode treeNode = treeNodeRepo.toTreeNode(elementValue);

		// save entire info from treenode to treenode record
		treeNodeRepo.save(treeNode);
	}
}
