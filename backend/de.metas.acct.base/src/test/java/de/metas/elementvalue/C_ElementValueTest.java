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

package de.metas.elementvalue;

import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.acct.model.validator.C_ElementValue;
import de.metas.organization.OrgId;
import de.metas.treenode.TreeNode;
import de.metas.treenode.TreeNodeRepository;
import de.metas.treenode.TreeNodeService;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.assertj.core.api.Assertions;
import org.compiere.model.X_C_ElementValue;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.annotation.Nullable;

@ExtendWith(AdempiereTestWatcher.class)
public class C_ElementValueTest
{
	private ElementValueService elementValueService;
	private TreeNodeService treeNodeService;
	private TreeNodeRepository treeNodeRepo;

	private ChartOfAccounts chartOfAccounts;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		AdempiereTestHelper.setupContext_AD_Client_IfNotSet();
		AdempiereTestHelper.createClientInfo();

		final ChartOfAccountsService chartOfAccountsService = new ChartOfAccountsService(new ChartOfAccountsRepository());
		final TreeNodeRepository treeNodeRepo = new TreeNodeRepository();
		final TreeNodeService treeNodeService = new TreeNodeService(treeNodeRepo, chartOfAccountsService);

		final ElementValueRepository elementValueRepository = new ElementValueRepository();
		ElementValueService elementValueService = new ElementValueService(elementValueRepository, treeNodeService);

		final IAcctSchemaDAO acctSchemasRepo = Services.get(IAcctSchemaDAO.class);
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(new C_ElementValue(acctSchemasRepo, treeNodeService));

		this.elementValueService = elementValueService;
		this.treeNodeService = treeNodeService;
		this.treeNodeRepo = treeNodeRepo;
		this.chartOfAccounts = chartOfAccountsService.createChartOfAccounts(ChartOfAccountsCreateRequest.builder()
				.name("Test")
				.clientId(Env.getClientId())
				.build());
	}

	@Builder(builderMethodName = "elementValue", builderClassName = "$ElementValueBuilder")
	private ElementValueId createElementValue(
			@NonNull final String name,
			@Nullable final ElementValueId parentId,
			final int seqNo,
			final boolean summary)
	{
		final ElementValue elementValue = elementValueService.createOrUpdate(ElementValueCreateOrUpdateRequest.builder()
				.orgId(OrgId.ANY)
				.chartOfAccountsId(chartOfAccounts.getId())
				.value(name)
				.name(name)
				.accountSign(X_C_ElementValue.ACCOUNTSIGN_Natural)
				.accountType(X_C_ElementValue.ACCOUNTTYPE_Asset)
				.isSummary(summary)
				.parentId(parentId)
				.seqNo(seqNo)
				.build());

		treeNodeService.updateTreeNode(elementValue);

		return elementValue.getId();
	}

	private TreeNode getTreeNode(final ElementValueId elementValueId)
	{
		return treeNodeRepo.getTreeNode(elementValueId, chartOfAccounts.getTreeId())
				.orElseThrow(() -> new AdempiereException("TreeNode not found for " + elementValueId));
	}

	@Test
	void moveNodeToNewParent()
	{
		final ElementValueId parentId = elementValue().name("117").parentId(null).seqNo(1000).summary(true).build();
		final ElementValueId ev1Id = elementValue().name("11710").parentId(parentId).seqNo(10).build();
		final ElementValueId ev2Id = elementValue().name("11730").parentId(parentId).seqNo(20).build();
		final ElementValueId ev3Id = elementValue().name("11720").parentId(null).seqNo(999).build();

		elementValueService.changeParentAndReorderByAccountNo(
				ElementValueParentChangeRequest.builder()
						.elementValueId(ev3Id)
						.newParentId(parentId)
						.build());

		final ElementValue ev1 = elementValueService.getById(ev1Id);
		final ElementValue ev2 = elementValueService.getById(ev2Id);
		final ElementValue ev3 = elementValueService.getById(ev3Id);

		final TreeNode node1 = getTreeNode(ev1Id);
		final TreeNode node2 = getTreeNode(ev2Id);
		final TreeNode node3 = getTreeNode(ev3Id);

		Assertions.assertThat(node1.getParentId()).isEqualTo(ev1.getParentId()).isEqualTo(parentId);
		Assertions.assertThat(node2.getParentId()).isEqualTo(ev2.getParentId()).isEqualTo(parentId);
		Assertions.assertThat(node3.getParentId()).isEqualTo(ev3.getParentId()).isEqualTo(parentId);

		Assertions.assertThat(node1.getSeqNo()).isEqualTo(ev1.getSeqNo()).isEqualTo(1);
		Assertions.assertThat(node2.getSeqNo()).isEqualTo(ev2.getSeqNo()).isEqualTo(3);
		Assertions.assertThat(node3.getSeqNo()).isEqualTo(ev3.getSeqNo()).isEqualTo(2);
	}
}
