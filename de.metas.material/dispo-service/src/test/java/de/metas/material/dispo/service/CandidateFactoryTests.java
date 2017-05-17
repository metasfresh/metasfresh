package de.metas.material.dispo.service;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Date;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;

import de.metas.material.dispo.Candidate;
import de.metas.material.dispo.CandidateRepository;
import de.metas.material.dispo.Candidate.Type;
import de.metas.material.dispo.service.CandidateFactory;

/*
 * #%L
 * metasfresh-manufacturing-dispo
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class CandidateFactoryTests
{
	private final Date now = SystemTime.asDate();
	private final Date earlier = TimeUtil.addMinutes(now, -10);
	private final Date later = TimeUtil.addMinutes(now, 10);

	private I_AD_Org org;

	private I_C_UOM uom;

	private I_M_Product product;

	private I_M_Warehouse warehouse;

	private CandidateRepository candidateRepository;

	private CandidateFactory candidateFactory;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		org = newInstance(I_AD_Org.class);
		save(org);

		uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		InterfaceWrapperHelper.save(uom);

		product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product.setC_UOM(uom);
		InterfaceWrapperHelper.save(product);

		warehouse = InterfaceWrapperHelper.newInstance(I_M_Warehouse.class);
		InterfaceWrapperHelper.save(warehouse);

		candidateRepository = new CandidateRepository();
		candidateFactory = new CandidateFactory(candidateRepository);

		final Candidate stockCandidate = Candidate.builder()
				.type(Type.STOCK)
				.clientId(org.getAD_Client_ID())
				.orgId(org.getAD_Org_ID())
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.quantity(new BigDecimal("10"))
				.date(now)
				.build();
		candidateRepository.addOrUpdate(stockCandidate);
	}

	/**
	 * Verifies that if a new stock candidate is created with a time before any existing candidates, then that candidate is created with a zero quantity.
	 */
	@Test
	public void createStockCandidate_before_existing()
	{
		final Candidate candidate = Candidate.builder()
				.type(Type.STOCK)
				.clientId(org.getAD_Client_ID())
				.orgId(org.getAD_Org_ID())
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.date(earlier)
				.quantity(BigDecimal.ONE)
				.build();

		final Candidate newCandidateBefore = candidateFactory.createStockCandidate(candidate);
		assertThat(newCandidateBefore.getQuantity(), comparesEqualTo(new BigDecimal("1")));
	}

	/**
	 * Verifies that if a new stock candidate is created with a time after and existing candidates, then that candidate is creates with the predecessor's quantity.
	 */
	@Test
	public void createStockCandidate_after_existing()
	{
		final Candidate candidate = Candidate.builder()
				.type(Type.STOCK)
				.clientId(org.getAD_Client_ID())
				.orgId(org.getAD_Org_ID())
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.date(later)
				.quantity(BigDecimal.ONE)
				.build();

		final Candidate newCandidateAfter = candidateFactory.createStockCandidate(candidate);
		assertThat(newCandidateAfter.getQuantity(), comparesEqualTo(new BigDecimal("11")));
	}
}
