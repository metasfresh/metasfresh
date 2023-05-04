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
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.project.ProjectId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

@Service
public class WorkOrderProjectObjectUnderTestService
{
	private final WorkOrderProjectObjectUnderTestRepository woProjectObjectUnderTestRepository;

	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);

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

		woProjectObjectUnderTestList.forEach(woProjectObjectUnderTest -> createOrderLineAndSetOnObjectUnderTest(orderFactory, woProjectObjectUnderTest));

		orderFactory.createAndComplete();
	}

	@NonNull
	public List<WOProjectObjectUnderTest> getByOrderLineId(@NonNull final OrderLineId orderLineId)
	{
		return woProjectObjectUnderTestRepository.getByOrderLineId(orderLineId);
	}

	private void createOrderLineAndSetOnObjectUnderTest(
			@NonNull final OrderFactory orderFactory,
			@NonNull final WOProjectObjectUnderTest woProjectObjectUnderTest)
	{
		final ProductId productId = woProjectObjectUnderTest.getProductId();

		if (productId == null)
		{
			throw new AdempiereException("ProductId cannot be null at this point!");
		}

		final I_C_UOM uom = productBL.getStockUOM(productId);

		final Quantity qty = Quantity.of(woProjectObjectUnderTest.getNumberOfObjectsUnderTest(), uom);

		orderFactory.newOrderLine()
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
}
