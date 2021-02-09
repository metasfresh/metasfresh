/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package org.adempiere.mm.attributes.api.impl;

import de.metas.util.Services;
import org.adempiere.mm.attributes.api.ISubProducerAttributeDAO;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import de.metas.interfaces.I_C_BP_Relation;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.X_C_BP_Relation;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.*;

public class SubProducerAttributeDAOTest
{
	private I_C_BPartner bp;
	private I_C_BPartner bp2;
	private ISubProducerAttributeDAO dao;

	@BeforeEach
	public void before()
	{
		AdempiereTestHelper.get().init();
		dao = Services.get(ISubProducerAttributeDAO.class);
		final Properties ctx = Env.getCtx();
		Env.setClientId(ctx, ClientId.METASFRESH);
		Env.setOrgId(ctx, AdempiereTestHelper.createOrgWithTimeZone());

		setUpBpRelationData();
	}

	@Test
	public void testRetrieveSubProducerBPartners() {
		List<I_C_BPartner> bpPartnerList = dao.retrieveSubProducerBPartners(Env.getCtx(), bp.getC_BPartner_ID());
		assertThat(bpPartnerList.size()).isEqualTo(1);

		bpPartnerList = dao.retrieveSubProducerBPartners(Env.getCtx(), bp2.getC_BPartner_ID());
		assertThat(bpPartnerList.size()).isEqualTo(0);
	}

	private void setUpBpRelationData() {
		bp = newInstance(I_C_BPartner.class);
		bp.setIsActive(true);
		bp.setName("Test Partner");
		save(bp);

		I_C_BP_Relation bpRelation = newInstance(I_C_BP_Relation.class);
		bpRelation.setC_BPartner_ID(bp.getC_BPartner_ID());
		bpRelation.setName("Test Rel1");
		bpRelation.setRole(X_C_BP_Relation.ROLE_MainProducer);
		bpRelation.setIsActive(true);
		save(bpRelation);

		bpRelation = newInstance(I_C_BP_Relation.class);
		bpRelation.setC_BPartner_ID(bp.getC_BPartner_ID());
		bpRelation.setName("Test Rel2");
		bpRelation.setRole(null);
		bpRelation.setIsActive(true);
		save(bpRelation);

		bpRelation = newInstance(I_C_BP_Relation.class);
		bpRelation.setC_BPartner_ID(bp.getC_BPartner_ID());
		bpRelation.setName("Test Rel3");
		bpRelation.setRole(X_C_BP_Relation.ROLE_MainProducer);
		bpRelation.setIsActive(false);
		save(bpRelation);

		bpRelation = newInstance(I_C_BP_Relation.class);
		bpRelation.setC_BPartner_ID(bp.getC_BPartner_ID());
		bpRelation.setName("Test Rel4");
		bpRelation.setRole(X_C_BP_Relation.ROLE_Caregiver);
		bpRelation.setIsActive(true);
		save(bpRelation);

		bpRelation = newInstance(I_C_BP_Relation.class);
		bpRelation.setC_BPartner_ID(bp.getC_BPartner_ID());
		bpRelation.setName("Test Rel5");
		bpRelation.setRole(X_C_BP_Relation.ROLE_MainProducer);
		bpRelation.setIsActive(true);
		save(bpRelation);

		bp2 = newInstance(I_C_BPartner.class);
		bp2.setIsActive(true);
		bp2.setName("Test Partner");
		save(bp2);

		bpRelation = newInstance(I_C_BP_Relation.class);
		bpRelation.setC_BPartner_ID(bp2.getC_BPartner_ID());
		bpRelation.setName("Test Rel21");
		bpRelation.setRole(null);
		bpRelation.setIsActive(true);
		save(bpRelation);
	}
}
