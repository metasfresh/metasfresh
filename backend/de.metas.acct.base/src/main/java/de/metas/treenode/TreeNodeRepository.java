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

import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.tree.AdTreeId;
import org.compiere.model.I_AD_TreeNode;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class TreeNodeRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	private static TreeNode toTreeNode(@NonNull final I_AD_TreeNode record)
	{
		return TreeNode.builder()
				.chartOfAccountsTreeId(AdTreeId.ofRepoId(record.getAD_Tree_ID()))
				.nodeId(ElementValueId.ofRepoId(record.getNode_ID()))
				.parentId(ElementValueId.ofRepoIdOrNull(record.getParent_ID()))
				.seqNo(record.getSeqNo())
				.build();
	}

	public void save(@NonNull final TreeNode treeNode)
	{
		final I_AD_TreeNode existingRecord = retrieveTreeNodeRecord(treeNode.getNodeId(), treeNode.getChartOfAccountsTreeId());
		if (existingRecord == null)
		{
			createNew(treeNode);
		}
		else
		{
			updateRecordAndSave(existingRecord, treeNode);
		}
	}

	private void createNew(@NonNull final TreeNode treeNode)
	{
		final I_AD_TreeNode record = newInstance(I_AD_TreeNode.class);
		updateRecordAndSave(record, treeNode);
	}

	private void updateRecordAndSave(final I_AD_TreeNode record, final @NonNull TreeNode from)
	{
		record.setAD_Tree_ID(from.getChartOfAccountsTreeId().getRepoId());
		record.setNode_ID(from.getNodeId().getRepoId());
		record.setParent_ID(from.getParentId() != null ? from.getParentId().getRepoId() : 0);
		record.setSeqNo(from.getSeqNo());

		saveRecord(record);
	}

	private I_AD_TreeNode retrieveTreeNodeRecord(@NonNull final ElementValueId elementValueId, @NonNull final AdTreeId chartOfAccountsTreeId)
	{
		return queryBL.createQueryBuilder(I_AD_TreeNode.class)
				.addEqualsFilter(I_AD_TreeNode.COLUMNNAME_AD_Tree_ID, chartOfAccountsTreeId)
				.addEqualsFilter(I_AD_TreeNode.COLUMNNAME_Node_ID, elementValueId)
				.create()
				.firstOnly(I_AD_TreeNode.class);
	}

	public Optional<TreeNode> getTreeNode(@NonNull final ElementValueId elementValueId, @NonNull final AdTreeId chartOfAccountsTreeId)
	{
		final I_AD_TreeNode treeNodeRecord = retrieveTreeNodeRecord(elementValueId, chartOfAccountsTreeId);
		return treeNodeRecord != null
				? Optional.of(toTreeNode(treeNodeRecord))
				: Optional.empty();
	}

	public void recreateTree(@NonNull final List<TreeNode> treeNodes)
	{
		final AdTreeId chartOfAccountsTreeId = extractSingleChartOfAccountsTreeId(treeNodes);

		deleteByTreeId(chartOfAccountsTreeId);
		treeNodes.forEach(this::createNew);
	}

	private static AdTreeId extractSingleChartOfAccountsTreeId(final @NonNull List<TreeNode> treeNodes)
	{
		final ImmutableSet<AdTreeId> chartOfAccountsTreeIds = treeNodes.stream()
				.map(TreeNode::getChartOfAccountsTreeId)
				.collect(ImmutableSet.toImmutableSet());

		return CollectionUtils.singleElement(chartOfAccountsTreeIds);
	}

	private void deleteByTreeId(final AdTreeId chartOfAccountsTreeId)
	{
		queryBL.createQueryBuilder(I_AD_TreeNode.class)
				.addEqualsFilter(I_AD_TreeNode.COLUMNNAME_AD_Tree_ID, chartOfAccountsTreeId)
				.create()
				.deleteDirectly();
	}

	public ImmutableSet<TreeNode> getByTreeId(final AdTreeId chartOfAccountsTreeId)
	{
		return queryBL.createQueryBuilder(I_AD_TreeNode.class)
				.addEqualsFilter(I_AD_TreeNode.COLUMNNAME_AD_Tree_ID, chartOfAccountsTreeId)
				.create()
				.stream()
				.map(TreeNodeRepository::toTreeNode)
				.collect(ImmutableSet.toImmutableSet());
	}

}
