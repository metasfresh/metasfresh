package de.metas.request.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_R_RequestType;
import org.compiere.model.X_R_Request;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.junit.Before;
import org.junit.Test;

import de.metas.adempiere.model.I_AD_User;
import de.metas.adempiere.model.I_M_Product;
import de.metas.inout.api.impl.QualityNoteDAO;
import de.metas.inout.model.I_M_InOut;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.interfaces.I_C_BPartner;
import de.metas.request.api.IRequestDAO;
import de.metas.request.model.I_R_Request;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class RequestDAOTest
{

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void createRequestForInOutLine()
	{
		final I_M_InOut inout = createInOut();

		final I_M_InOutLine line = createInOutLine(inout);

		final I_R_RequestType soRequestType = createRequestType("RequestType");
		createQualityNoteAttribute();

		final I_R_Request request = Services.get(IRequestDAO.class).createRequestFromInOutLine(line);

		assertThat(request.getAD_Org_ID()).isEqualTo(line.getAD_Org_ID());
		assertThat(request.getM_Product_ID()).isEqualTo(line.getM_Product_ID());
		assertThat(request.getR_RequestType_ID()).isEqualTo(soRequestType.getR_RequestType_ID());
		assertThat(request.getAD_Table_ID()).isEqualTo(getTableId(I_M_InOut.class));
		assertThat(request.getRecord_ID()).isEqualTo(inout.getM_InOut_ID());
		assertThat(request.getC_BPartner_ID()).isEqualTo(inout.getC_BPartner_ID());
		assertThat(request.getAD_User_ID()).isEqualTo(inout.getAD_User_ID());
		assertThat(request.getDateDelivered()).isEqualTo(inout.getMovementDate());
		assertThat(request.getSummary()).isEqualTo(RequestDAO.MSG_R_Request_From_InOut_Summary);
		assertThat(request.getConfidentialType()).isEqualTo(X_R_Request.CONFIDENTIALTYPE_Internal);
		assertThat(request.getM_QualityNote()).isNull();
		assertThat(request.getPerformanceType()).isNullOrEmpty();

	}

	@Test
	public void createRequestForDDOrderLine()
	{
		final I_DD_Order ddOrder = createDDOrder();

		final I_DD_OrderLine line = createDDOrderLine(ddOrder);

		final I_R_RequestType soRequestType = createRequestType("RequestType");
		createQualityNoteAttribute();

		final I_R_Request request = Services.get(IRequestDAO.class).createRequestFromDDOrderLine(line);

		assertThat(request.getAD_Org_ID()).isEqualTo(line.getAD_Org_ID());
		assertThat(request.getM_Product_ID()).isEqualTo(line.getM_Product_ID());
		assertThat(request.getR_RequestType_ID()).isEqualTo(soRequestType.getR_RequestType_ID());
		assertThat(request.getAD_Table_ID()).isEqualTo(getTableId(I_DD_Order.class));
		assertThat(request.getRecord_ID()).isEqualTo(ddOrder.getDD_Order_ID());
		assertThat(request.getC_BPartner_ID()).isEqualTo(ddOrder.getC_BPartner_ID());
		assertThat(request.getAD_User_ID()).isEqualTo(ddOrder.getAD_User_ID());
		assertThat(request.getDateDelivered()).isEqualTo(ddOrder.getDatePromised());
		assertThat(request.getSummary()).isEqualTo(line.getDescription());
		assertThat(request.getConfidentialType()).isEqualTo(X_R_Request.CONFIDENTIALTYPE_Internal);
		assertThat(request.getM_QualityNote()).isNull();
		assertThat(request.getPerformanceType()).isNullOrEmpty();

	}

	private I_DD_Order createDDOrder()
	{
		final I_DD_Order ddOrder = newInstance(I_DD_Order.class);
		
		ddOrder.setIsSOTrx(true);
		
		ddOrder.setC_BPartner(createPartner("Partner 2"));
		
		ddOrder.setAD_User(createUser("User 2"));
		
		ddOrder.setDatePromised(SystemTime.asDayTimestamp());
		
		save(ddOrder);
		return ddOrder;
	}

	private I_DD_OrderLine createDDOrderLine(final I_DD_Order ddOrder)
	{
		final I_DD_OrderLine line = newInstance(I_DD_OrderLine.class);
		line.setDD_Order(ddOrder);
		line.setM_Product(createProduct("Product2"));
		line.setDescription("Description DDOrderLine");

		save(line);
		return line;
	}

	private I_M_Attribute createQualityNoteAttribute()
	{
		final I_M_Attribute attribute = newInstance(I_M_Attribute.class);
		attribute.setValue(QualityNoteDAO.QualityNoteAttribute);

		save(attribute);
		return attribute;
	}

	private I_R_RequestType createRequestType(final String name)
	{
		final I_R_RequestType requestType = newInstance(I_R_RequestType.class);
		requestType.setName(name);
		requestType.setInternalName(RequestTypeDAO.InternalName_CustomerComplaint);
		
		save(requestType);
		return requestType;
	}

	private I_M_InOutLine createInOutLine(final I_M_InOut inout)
	{
		final I_M_InOutLine line = newInstance(I_M_InOutLine.class);
		line.setM_InOut(inout);

		line.setM_Product(createProduct("Product1"));

		line.setQualityDiscountPercent(BigDecimal.TEN);
		save(line);

		return line;
	}

	private I_M_Product createProduct(final String name)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setName(name);
		save(product);

		return product;
	}

	private I_M_InOut createInOut()
	{
		final I_M_InOut inout = newInstance(I_M_InOut.class);
		inout.setIsSOTrx(true);

		inout.setC_BPartner(createPartner("Partner 1"));

		inout.setAD_User(createUser("User1"));

		inout.setMovementDate(SystemTime.asDayTimestamp());
		save(inout);

		return inout;
	}

	private I_AD_User createUser(final String name)
	{
		final I_AD_User user = newInstance(I_AD_User.class);
		user.setName(name);
		save(user);
		return user;

	}

	private I_C_BPartner createPartner(final String name)
	{
		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		partner.setName(name);
		save(partner);

		return partner;
	}
}
