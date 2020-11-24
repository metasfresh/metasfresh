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

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_AD_TreeNode;
import org.compiere.model.I_C_Element;
import org.springframework.stereotype.Repository;

import de.metas.elementvalue.ElementId;
import de.metas.elementvalue.ElementValue;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.elementvalue.ElementValueRepository;
import de.metas.util.Services;
import lombok.NonNull;

@Repository
public class TreeNodeRepository
{
	private final ElementValueRepository elementValueRepo;

	public TreeNodeRepository(@NonNull final ElementValueRepository elementValueRepo)
	{
		this.elementValueRepo = elementValueRepo;
	}

	@NonNull
	private static TreeNode toTreeNode(@NonNull final I_AD_TreeNode record)
	{
		return TreeNode.builder()
				.treeId(TreeId.ofRepoId(record.getAD_Tree_ID()))
				.nodeId(ElementValueId.ofRepoId(record.getNode_ID()))
				.parentId(ElementValueId.ofRepoId(record.getParent_ID()))
				.seqNo(record.getSeqNo())
				.build();
	}

	@NonNull
	public TreeNode toTreeNode(@NonNull final ElementValue elementValue)
	{
		final TreeId treeId = getTreeId(elementValue.getElementId());

		return TreeNode.builder()
				.nodeId(elementValue.getId())
				.parentId(elementValue.getParentId())
				.treeId(treeId)
				.seqNo(elementValue.getSeqNo())
				.build();

	}

	public void save(@NonNull final TreeNode treeNode)
	{
		I_AD_TreeNode treeNodeRecord = retrieveTreeNodeRecord(treeNode);

		if (treeNodeRecord == null)
		{
			treeNodeRecord = newInstance(I_AD_TreeNode.class);
		}

		treeNodeRecord.setAD_Tree_ID(treeNode.getTreeId().getRepoId());
		treeNodeRecord.setNode_ID(treeNode.getNodeId().getRepoId());
		treeNodeRecord.setParent_ID(treeNode.getParentId().getRepoId());
		treeNodeRecord.setSeqNo(treeNode.getSeqNo());

		saveRecord(treeNodeRecord);
	}

	private I_AD_TreeNode retrieveTreeNodeRecord(@NonNull final TreeNode treeNode)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_TreeNode.class)
				.addEqualsFilter(I_AD_TreeNode.COLUMNNAME_AD_Tree_ID, treeNode.getTreeId())
				.addEqualsFilter(I_AD_TreeNode.COLUMNNAME_Node_ID, treeNode.getNodeId())
				.create()
				.firstOnly(I_AD_TreeNode.class);
	}

	private TreeId getTreeId(@NonNull final ElementId elementId)
	{
		final I_C_Element element = elementValueRepo.getElementRecordById(elementId);
		return TreeId.ofRepoId(element.getAD_Tree_ID());
	}
}
