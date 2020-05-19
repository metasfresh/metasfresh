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

/**
 *
 */
package de.metas.elementvalue;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.junit.jupiter.api.Assertions.assertEquals;

import de.metas.acct.api.impl.ElementValueId;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_TreeNode;
import org.compiere.model.I_C_Element;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.X_C_Element;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.acct.model.validator.C_ElementValue;
import de.metas.treenode.TreeNodeRepository;
import de.metas.treenode.TreeNodeService;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;

public class C_ElementValueTest
{
	private ElementValueService elementValueService;

	private I_C_ElementValue parent;
	private I_C_ElementValue ev1;
	private I_C_ElementValue ev2;
	private I_C_ElementValue ev3;
	private I_AD_TreeNode node1;
	private I_AD_TreeNode node2;
	private I_AD_TreeNode node3;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		AdempiereTestHelper.setupContext_AD_Client_IfNotSet();
		AdempiereTestHelper.createClientInfo();

		final ElementValueRepository elementValueRepository = new ElementValueRepository();
		final TreeNodeRepository treeNodeRepository = new TreeNodeRepository(elementValueRepository);

		final TreeNodeService treeNodeService = new TreeNodeService(elementValueRepository, treeNodeRepository);
		elementValueService = new ElementValueService(elementValueRepository);

		// register services
		SpringContextHolder.registerJUnitBean(elementValueRepository);
		SpringContextHolder.registerJUnitBean(elementValueService);
		SpringContextHolder.registerJUnitBean(treeNodeRepository);
		SpringContextHolder.registerJUnitBean(treeNodeService);

		final IAcctSchemaDAO acctSchemasRepo = Services.get(IAcctSchemaDAO.class);
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(new C_ElementValue(acctSchemasRepo, treeNodeService));

		prepareData();
	}

	@Test
	void test_PushingParentAndSeqNoToTreeNode()
	{

		final ElementValueRequest request = ElementValueRequest.builder()
				.elementValueId(ElementValueId.ofRepoId(ev3.getC_ElementValue_ID()))
				.parentId(ElementValueId.ofRepoId(parent.getC_ElementValue_ID()))
				.build();

		elementValueService.updateElementValueAndResetSequences(request);

		InterfaceWrapperHelper.refresh(ev3);
		InterfaceWrapperHelper.refresh(node3);

		assertEquals(parent.getC_ElementValue_ID(), ev3.getParent_ID());
		assertEquals(ev3.getParent_ID(), node3.getParent_ID());
		assertEquals(ev3.getSeqNo(), node3.getSeqNo());
		assertEquals(ev2.getSeqNo(), node2.getSeqNo());
		assertEquals(ev1.getSeqNo(), node1.getSeqNo());
	}

	@Test
	void test_ResetSeqNo()
	{

		final ElementValueRequest request = ElementValueRequest.builder()
				.elementValueId(ElementValueId.ofRepoId(ev3.getC_ElementValue_ID()))
				.parentId(ElementValueId.ofRepoId(parent.getC_ElementValue_ID()))
				.build();

		elementValueService.updateElementValueAndResetSequences(request);

		InterfaceWrapperHelper.refresh(node1);
		InterfaceWrapperHelper.refresh(node2);
		InterfaceWrapperHelper.refresh(node3);

		assertEquals(1, node1.getSeqNo());
		assertEquals(3, node2.getSeqNo());
		assertEquals(2, node3.getSeqNo());

	}

	private void prepareData()
	{

		final I_C_Element el = ElementBuilder()
				.name("Test")
				.build();

		parent = ElemenValuetBuilder()
				.name("117")
				.C_Element_ID(el.getC_Element_ID())
				.Parent_ID(0)
				.seqNo(1000)
				.build();

		TreeNodeBuilder()
				.C_ElementValue_ID(parent.getC_ElementValue_ID())
				.Parent_ID(0)
				.seqNo(1000)
				.build();

		ev1 = ElemenValuetBuilder()
				.name("11710")
				.C_Element_ID(el.getC_Element_ID())
				.Parent_ID(parent.getC_ElementValue_ID())
				.seqNo(10)
				.build();

		node1 = TreeNodeBuilder()
				.C_ElementValue_ID(ev1.getC_ElementValue_ID())
				.Parent_ID(parent.getC_ElementValue_ID())
				.seqNo(10)
				.build();

		ev2 = ElemenValuetBuilder()
				.name("11730")
				.C_Element_ID(el.getC_Element_ID())
				.Parent_ID(parent.getC_ElementValue_ID())
				.seqNo(20)
				.build();

		node2 = TreeNodeBuilder()
				.C_ElementValue_ID(ev2.getC_ElementValue_ID())
				.Parent_ID(parent.getC_ElementValue_ID())
				.seqNo(20)
				.build();

		ev3 = ElemenValuetBuilder()
				.name("11720")
				.C_Element_ID(el.getC_Element_ID())
				.Parent_ID(0)
				.seqNo(999)
				.build();

		node3 = TreeNodeBuilder()
				.C_ElementValue_ID(ev3.getC_ElementValue_ID())
				.Parent_ID(0)
				.seqNo(999)
				.build();

	}

	@Builder(builderMethodName = "ElementBuilder")
	private I_C_Element prepareElement(@NonNull final String name)
	{
		final I_C_Element element = newInstance(I_C_Element.class);
		element.setAD_Tree_ID(1);
		element.setAD_Org_ID(1);
		element.setName(name);
		element.setElementType(X_C_Element.ELEMENTTYPE_Account);
		InterfaceWrapperHelper.setValue(element, I_C_Element.COLUMNNAME_AD_Client_ID, 1);
		save(element);

		return element;
	}

	@Builder(builderMethodName = "ElemenValuetBuilder")
	private I_C_ElementValue prepareElementValue(@NonNull final String name, final int C_Element_ID, final int Parent_ID, final int seqNo)
	{
		final I_C_ElementValue ev = newInstance(I_C_ElementValue.class);
		ev.setC_Element_ID(C_Element_ID);
		ev.setValue(name);
		ev.setName(name);
		ev.setParent_ID(Parent_ID);
		ev.setSeqNo(seqNo);
		save(ev);

		return ev;
	}

	@Builder(builderMethodName = "TreeNodeBuilder")
	private I_AD_TreeNode prepareTreeNode(final int C_ElementValue_ID, final int Parent_ID, final int seqNo)
	{
		final I_AD_TreeNode tn = newInstance(I_AD_TreeNode.class);
		tn.setAD_Tree_ID(1);
		tn.setNode_ID(C_ElementValue_ID);
		tn.setParent_ID(Parent_ID);
		tn.setSeqNo(seqNo);
		save(tn);

		return tn;
	}
}
