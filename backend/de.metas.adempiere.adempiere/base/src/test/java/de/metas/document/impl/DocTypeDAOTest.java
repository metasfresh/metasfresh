/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.document.impl;

import de.metas.acct.GLCategoryId;
import de.metas.document.DocBaseType;
import de.metas.document.DocSubType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.organization.OrgId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_DocType;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class DocTypeDAOTest
{
	private OrgId org1Id;
	private OrgId org2Id;
	private DocTypeDAO docTypeDAO;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		org1Id = AdempiereTestHelper.createOrgWithTimeZone();
		org2Id = AdempiereTestHelper.createOrgWithTimeZone();
		docTypeDAO = new DocTypeDAO();
	}

	@Test
	void createDocTypeWithDocSubTypeNone()
	{
		// given
		final Properties ctx = Env.getCtx();
		Env.setClientId(ctx, ClientId.METASFRESH);
		Env.setOrgId(ctx, org1Id);

		// when
		final DocTypeId docTypeId = docTypeDAO.createDocType(IDocTypeDAO.DocTypeCreateRequest.builder()
				.ctx(ctx)
				.name("Inventory DocType for " + org2Id)
				.docBaseType(DocBaseType.MaterialPhysicalInventory)
				.docSubType(DocSubType.NONE)
				.adOrgId(org2Id.getRepoId())
				.glCategoryId(GLCategoryId.ofRepoId(123))
				.build());

		// then
		final I_C_DocType docTypeRecord = InterfaceWrapperHelper.load(docTypeId, I_C_DocType.class);
		assertThat(docTypeRecord.getAD_Client_ID()).isEqualTo(ClientId.METASFRESH.getRepoId());
		assertThat(docTypeRecord.getAD_Org_ID()).isEqualTo(org2Id.getRepoId());
		assertThat(docTypeRecord.getDocBaseType()).isEqualTo(DocBaseType.MaterialPhysicalInventory.getCode());
		assertThat(docTypeRecord.getDocSubType()).isNull();
	}

	@Test
	void createDocTypeWithDocSubTypeAny()
	{
		// given
		final Properties ctx = Env.getCtx();
		Env.setClientId(ctx, ClientId.METASFRESH);
		Env.setOrgId(ctx, org1Id);

		// when
		final DocTypeId docTypeId = docTypeDAO.createDocType(IDocTypeDAO.DocTypeCreateRequest.builder()
																	 .ctx(ctx)
																	 .name("Inventory DocType for " + org2Id)
																	 .docBaseType(DocBaseType.MaterialPhysicalInventory)
																	 .docSubType(DocSubType.ANY)
																	 .adOrgId(org2Id.getRepoId())
																	 .glCategoryId(GLCategoryId.ofRepoId(123))
																	 .build());

		// then
		final I_C_DocType docTypeRecord = InterfaceWrapperHelper.load(docTypeId, I_C_DocType.class);
		assertThat(docTypeRecord.getAD_Client_ID()).isEqualTo(ClientId.METASFRESH.getRepoId());
		assertThat(docTypeRecord.getAD_Org_ID()).isEqualTo(org2Id.getRepoId());
		assertThat(docTypeRecord.getDocBaseType()).isEqualTo(DocBaseType.MaterialPhysicalInventory.getCode());
		assertThat(docTypeRecord.getDocSubType()).isNull();
	}

	@Test
	void createDocTypeWithSubType()
	{
		// given
		final Properties ctx = Env.getCtx();
		Env.setClientId(ctx, ClientId.METASFRESH);
		Env.setOrgId(ctx, org1Id);

		// when
		final DocTypeId docTypeId = docTypeDAO.createDocType(IDocTypeDAO.DocTypeCreateRequest.builder()
																		   .ctx(ctx)
																		   .name("StandardOrder DocType for " + org2Id)
																		   .docBaseType(DocBaseType.SalesOrder)
																		   .docSubType(DocSubType.StandardOrder)
				.adOrgId(org2Id.getRepoId())
																		   .glCategoryId(GLCategoryId.ofRepoId(123))
				.build());

		// then
		final I_C_DocType docTypeRecord = InterfaceWrapperHelper.load(docTypeId, I_C_DocType.class);
		assertThat(docTypeRecord.getAD_Client_ID()).isEqualTo(ClientId.METASFRESH.getRepoId());
		assertThat(docTypeRecord.getAD_Org_ID()).isEqualTo(org2Id.getRepoId());
		assertThat(docTypeRecord.getDocBaseType()).isEqualTo(DocBaseType.SalesOrder.getCode());
		assertThat(docTypeRecord.getDocSubType()).isEqualTo(DocSubType.StandardOrder.getCode());
	}

	@Test
	void SubDocTypeQueryAnyAndNone()
	{
		final Properties ctx = Env.getCtx();
		Env.setClientId(ctx, ClientId.METASFRESH);
		Env.setOrgId(ctx, org1Id);

		docTypeDAO.createDocType(IDocTypeDAO.DocTypeCreateRequest.builder()
										 .ctx(ctx)
										 .name("StandardOrder DocType for " + org2Id)
										 .docBaseType(DocBaseType.SalesOrder)
										 .docSubType(DocSubType.StandardOrder)
										 .adOrgId(org2Id.getRepoId())
										 .glCategoryId(GLCategoryId.ofRepoId(123))
										 .build());

		docTypeDAO.createDocType(IDocTypeDAO.DocTypeCreateRequest.builder()
										 .ctx(ctx)
										 .name("SalesOrder DocType for " + org2Id)
										 .docBaseType(DocBaseType.SalesOrder)
										 .docSubType(DocSubType.NONE)
										 .adOrgId(org2Id.getRepoId())
										 .glCategoryId(GLCategoryId.ofRepoId(123))
										 .build());

		final List<I_C_DocType> docTypeList1 = docTypeDAO.retrieveDocTypesByBaseType(DocTypeQuery.builder()
																							.adClientId(ClientId.METASFRESH.getRepoId())
																							.adOrgId(org2Id.getRepoId())
																							.docBaseType(DocBaseType.SalesOrder)
																							.docSubType(DocSubType.NONE)
																							.build());
		assertThat(docTypeList1.size()).isEqualTo(1);

		final List<I_C_DocType> docTypeList2 = docTypeDAO.retrieveDocTypesByBaseType(DocTypeQuery.builder()
																							.adClientId(ClientId.METASFRESH.getRepoId())
																							.adOrgId(org2Id.getRepoId())
																							.docBaseType(DocBaseType.SalesOrder)
																							.docSubType(DocSubType.ANY)
																							.build());
		assertThat(docTypeList2.size()).isEqualTo(2);
	}
}