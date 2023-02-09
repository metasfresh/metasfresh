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

import com.google.common.collect.ImmutableList;
import de.metas.elementvalue.ChartOfAccountsService;
import de.metas.elementvalue.ElementValue;
import lombok.NonNull;
import org.adempiere.model.tree.AdTreeId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreeNodeService
{
	private final TreeNodeRepository treeNodeRepo;
	private final ChartOfAccountsService chartOfAccountsService;

	public TreeNodeService(
			@NonNull final TreeNodeRepository treeNodeRepo,
			@NonNull final ChartOfAccountsService chartOfAccountsService)
	{
		this.treeNodeRepo = treeNodeRepo;
		this.chartOfAccountsService = chartOfAccountsService;
	}

	public void updateTreeNode(@NonNull final ElementValue elementValue)
	{
		// treeNode base on all the data from element value
		final TreeNode treeNode = toTreeNode(elementValue);

		// save entire info from treenode to treenode record
		treeNodeRepo.save(treeNode);
	}

	@NonNull
	public TreeNode toTreeNode(@NonNull final ElementValue elementValue)
	{
		final AdTreeId chartOfAccountsTreeId = chartOfAccountsService.getById(elementValue.getChartOfAccountsId()).getTreeId();
		return TreeNode.builder()
				.chartOfAccountsTreeId(chartOfAccountsTreeId)
				.nodeId(elementValue.getId())
				.parentId(elementValue.getParentId())
				.seqNo(elementValue.getSeqNo())
				.build();
	}

	private ImmutableList<TreeNode> toTreeNodes(final @NonNull List<ElementValue> elementValues)
	{
		return elementValues.stream()
				.map(this::toTreeNode)
				.collect(ImmutableList.toImmutableList());
	}

	public void recreateTree(@NonNull final List<ElementValue> elementValues)
	{
		final ImmutableList<TreeNode> nodes = toTreeNodes(elementValues);
		treeNodeRepo.recreateTree(nodes);
	}
}
