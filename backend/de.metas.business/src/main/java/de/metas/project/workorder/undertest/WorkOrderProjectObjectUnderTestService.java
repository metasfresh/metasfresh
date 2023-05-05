/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.project.workorder.undertest;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.order.OrderFactory;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.pricing.PricingSystemId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.project.ProjectId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_UOM;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class WorkOrderProjectObjectUnderTestService
{
	private static final String PROVISION_PRICING_SYSTEM_SYSCONFIG = "de.metas.project.workorder.undertest.ProvisionPricingSystem";

	private final WorkOrderProjectObjectUnderTestRepository woProjectObjectUnderTestRepository;

	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	public WorkOrderProjectObjectUnderTestService(@NonNull final WorkOrderProjectObjectUnderTestRepository woProjectObjectUnderTestRepository)
	{
		this.woProjectObjectUnderTestRepository = woProjectObjectUnderTestRepository;
	}

	@NonNull
	public List<WOProjectObjectUnderTest> getByProjectId(@NonNull final ProjectId projectId)
	{
		return woProjectObjectUnderTestRepository.getByProjectId(projectId);
	}

	@NonNull
	public List<WOProjectObjectUnderTest> updateAll(@NonNull final Collection<WOProjectObjectUnderTest> objectUnderTestList)
	{
		return woProjectObjectUnderTestRepository.updateAll(objectUnderTestList);
	}

	public void update(@NonNull final List<WOProjectObjectUnderTest> objectUnderTests)
	{
		woProjectObjectUnderTestRepository.updateAll(objectUnderTests);
	}

	public void createOrder(
			@NonNull final BPartnerId bPartnerId,
			@NonNull final ZonedDateTime datePromised,
			@NonNull final List<WOProjectObjectUnderTest> woProjectObjectUnderTestList)
	{
		if (woProjectObjectUnderTestList.isEmpty())
		{
			throw new AdempiereException("at least one WOProjectObjectUnderTest is required to create an order!")
					.appendParametersToMessage()
					.setParameter("C_BPartner_ID", bPartnerId);
		}

		final DocBaseAndSubType docBaseAndSubType = DocBaseAndSubType.of(DocBaseType.PurchaseOrder, X_C_DocType.DOCSUBTYPE_Provision);

		final DocTypeQuery docTypeQuery = DocTypeQuery.builder()
				.docBaseType(docBaseAndSubType.getDocBaseType())
				.docSubType(docBaseAndSubType.getDocSubType())
				.adClientId(Env.getAD_Client_ID())
				.build();
		final DocTypeId docTypeId = docTypeDAO.getDocTypeId(docTypeQuery);

		final OrderFactory orderFactory = OrderFactory.newPurchaseOrder();

		orderFactory.docType(docTypeId);
		orderFactory.shipBPartner(bPartnerId);
		orderFactory.datePromised(datePromised);

		final PricingSystemId provisioningPricingSystemId = getProvisioningPricingSystem(woProjectObjectUnderTestList.get(0).getOrgId());

		woProjectObjectUnderTestList.forEach(woProjectObjectUnderTest -> createOrderLineForObjectUnderTest(orderFactory,
																										   woProjectObjectUnderTest,
																										   provisioningPricingSystemId));

		orderFactory.createAndComplete();
	}

	@NonNull
	public List<WOProjectObjectUnderTest> getByOrderLineId(@NonNull final OrderLineId orderLineId)
	{
		return woProjectObjectUnderTestRepository.getByOrderLineId(orderLineId);
	}

	private void createOrderLineForObjectUnderTest(
			@NonNull final OrderFactory orderFactory,
			@NonNull final WOProjectObjectUnderTest woProjectObjectUnderTest,
			@NonNull final PricingSystemId provisioningPricingSystemId)
	{
		final ProductId productId = woProjectObjectUnderTest.getProductId();

		if (productId == null)
		{
			throw new AdempiereException("ProductId cannot be null at this point!");
		}

		final I_C_UOM uom = productBL.getStockUOM(productId);

		final Quantity qty = Quantity.of(woProjectObjectUnderTest.getNumberOfObjectsUnderTest(), uom);

		orderFactory.newOrderLine()
				.overridingPricingSystemId(provisioningPricingSystemId)
				.productId(productId)
				.addQty(qty)
				.afterSaveHook((createdOrderLine) -> update(woProjectObjectUnderTest.toBuilder()
																	.orderLineProvisionId(OrderLineId.ofRepoId(createdOrderLine.getC_OrderLine_ID()))
																	.build()));
	}

	private void update(@NonNull final WOProjectObjectUnderTest objectUnderTest)
	{
		update(ImmutableList.of(objectUnderTest));
	}

	@NonNull
	private PricingSystemId getProvisioningPricingSystem(@NonNull final OrgId orgId)
	{
		final int provisionPricingSystemRepoId = sysConfigBL.getIntValue(PROVISION_PRICING_SYSTEM_SYSCONFIG,
																		 -1,
																		 Env.getAD_Client_ID(),
																		 orgId.getRepoId());

		return Optional.of(provisionPricingSystemRepoId)
				.map(PricingSystemId::ofRepoIdOrNull)
				.orElseThrow(() -> new AdempiereException(PROVISION_PRICING_SYSTEM_SYSCONFIG + " AD_SysConfig must be set!"));
	}
}
