package de.metas.elementvalue;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_AD_TreeNode;
import org.compiere.model.I_C_Element;
import org.compiere.model.I_C_ElementValue;
import org.springframework.stereotype.Repository;

import de.metas.organization.OrgId;
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
@Repository
public class ElementValueRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public I_C_ElementValue getByIdInTrx(final ElementValueId id)
	{
		return load(id, I_C_ElementValue.class);
	}

	@NonNull
	public ElementValue toElementValue(@NonNull final I_C_ElementValue record)
	{
		return ElementValue.builder()
				.id(ElementValueId.ofRepoId(record.getC_ElementValue_ID()))
				.elementId(ElementId.ofRepoId(record.getC_Element_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.value(record.getName())
				.name(record.getName())
				.build();
	}

	public I_AD_TreeNode updateTreeNodeParentAndSeqNo(@NonNull final ElementValue elementValue, int parentId, int seqNo)
	{
		final I_AD_TreeNode treeNodeRecord = getTreeNode(elementValue);
		if (treeNodeRecord == null)
		{
			return null;
		}

		treeNodeRecord.setParent_ID(parentId);
		treeNodeRecord.setSeqNo(seqNo);

		saveRecord(treeNodeRecord);

		return treeNodeRecord;
	}

	public I_AD_TreeNode getTreeNode(@NonNull final ElementValue elementValue)
	{
		final TreeId treeId = getTreeId(elementValue);

		return queryBL.createQueryBuilder(I_AD_TreeNode.class)
				.addEqualsFilter(I_AD_TreeNode.COLUMNNAME_AD_Org_ID, elementValue.getOrgId())
				.addEqualsFilter(I_AD_TreeNode.COLUMNNAME_AD_Tree_ID, treeId)
				.addEqualsFilter(I_AD_TreeNode.COLUMNNAME_Node_ID, elementValue.getId())
				.create()
				.firstOnly(I_AD_TreeNode.class);
	}

	private TreeId getTreeId(@NonNull final ElementValue elementValue)
	{
		final I_C_ElementValue record = load(elementValue.getId(), I_C_ElementValue.class);
		final I_C_Element element = record.getC_Element();
		return TreeId.ofRepoId(element.getAD_Tree_ID());
	}
}
