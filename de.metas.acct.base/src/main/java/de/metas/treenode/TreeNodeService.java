package de.metas.treenode;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryUpdater;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_TreeNode;
import org.compiere.model.I_C_ElementValue;
import org.springframework.stereotype.Service;

import de.metas.elementvalue.ElementValue;
import de.metas.elementvalue.ElementValueId;
import de.metas.elementvalue.ElementValueRepository;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
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
@Service
public class TreeNodeService
{
	final ElementValueRepository elementValueRepo = SpringContextHolder.instance.getBean(ElementValueRepository.class);
	final TreeNodeRepository treeNodeRepo = SpringContextHolder.instance.getBean(TreeNodeRepository.class);

	public void updateTreeNode(final I_C_ElementValue elementValueRecord)
	{
		final ElementValueId evId = ElementValueId.ofRepoIdOrNull(elementValueRecord.getC_ElementValue_ID());
		final ElementValue elementValue = elementValueRepo.getById(evId);

		// treeNode base on all the data from element value
		final TreeNode treeNode = treeNodeRepo.toTreeNode(elementValue);

		// save entire info from treenode to treenode record
		treeNodeRepo.save(treeNode);
	}
	
	public void shiftSeqNo(@NonNull final TreeNode treeNode)
	{
		Services.get(IQueryBL.class).createQueryBuilder(I_AD_TreeNode.class)
				.addEqualsFilter(I_AD_TreeNode.COLUMNNAME_AD_Tree_ID, treeNode.getTreeId())
				.addEqualsFilter(I_AD_TreeNode.COLUMNNAME_Parent_ID, treeNode.getParentId())
				.addNotEqualsFilter(I_AD_TreeNode.COLUMNNAME_Node_ID, treeNode.getNodeId())
				.addCompareFilter(I_AD_TreeNode.COLUMNNAME_SeqNo, Operator.GREATER_OR_EQUAL, treeNode.getSeqNo())
				.create()
				.update(tn -> {
					tn.setSeqNo(tn.getSeqNo() + 1);
					return IQueryUpdater.MODEL_UPDATED;
				});
	}

}
