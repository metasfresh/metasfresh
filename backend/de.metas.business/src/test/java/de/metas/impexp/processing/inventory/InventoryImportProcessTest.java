package de.metas.impexp.processing.inventory;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.ZonedDateTime;

import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.mm.attributes.api.impl.AttributesTestHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_I_Inventory;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.metas.business.BusinessTestHelper;
import de.metas.impexp.format.ImportTableDescriptorRepository;
import de.metas.impexp.processing.DBFunctionsRepository;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed
 * in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class InventoryImportProcessTest
{
	private InventoryImportProcess inventoryImportProcess;

	private IAttributeDAO attributeDAO;
	private AttributesTestHelper attributesTestHelper;

	private I_C_UOM uomEach;
	private ProductId productId;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		SpringContextHolder.registerJUnitBean(new DBFunctionsRepository());
		SpringContextHolder.registerJUnitBean(new ImportTableDescriptorRepository());

		inventoryImportProcess = new InventoryImportProcess();

		attributeDAO = Services.get(IAttributeDAO.class);
		attributesTestHelper = new AttributesTestHelper();
		setupAttributes();

		uomEach = BusinessTestHelper.createUomEach();
		final I_M_AttributeSet attributeSet = attributesTestHelper.createM_AttributeSet();
		I_M_Product product = BusinessTestHelper.createProduct("product", uomEach);
		product.setM_AttributeSet_ID(attributeSet.getM_AttributeSet_ID());
		saveRecord(product);
		productId = ProductId.ofRepoId(product.getM_Product_ID());
	}

	private void setupAttributes()
	{
		attributesTestHelper.createM_Attribute(
				AttributeConstants.ATTR_BestBeforeDate.getCode(),
				X_M_Attribute.ATTRIBUTEVALUETYPE_Date,
				true); // isInstanceAttribute
		attributesTestHelper.createM_Attribute(
				AttributeConstants.ATTR_LotNumber.getCode(),
				X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40,
				true); // isInstanceAttribute
		attributesTestHelper.createM_Attribute(
				AttributeConstants.ATTR_TE.getCode(),
				X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40,
				true); // isInstanceAttribute
		attributesTestHelper.createM_Attribute(
				AttributeConstants.ATTR_DateReceived.getCode(),
				X_M_Attribute.ATTRIBUTEVALUETYPE_Date,
				true); // isInstanceAttribute
	}

	@Nested
	public class extractASI
	{
		@Test
		public void noAttributes()
		{
			final I_I_Inventory importRecord = newInstance(I_I_Inventory.class);
			importRecord.setM_Product_ID(productId.getRepoId());

			final AttributeSetInstanceId asiId = inventoryImportProcess.extractASI(importRecord);
			final ImmutableAttributeSet asi = attributeDAO.getImmutableAttributeSetById(asiId);
			assertThat(asi).isSameAs(ImmutableAttributeSet.EMPTY);
		}

		@Test
		public void withAllAttributesSet()
		{
			attributesTestHelper.createM_Attribute("a1", X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40, true);
			attributesTestHelper.createM_Attribute("a2", X_M_Attribute.ATTRIBUTEVALUETYPE_Number, true);
			attributesTestHelper.createM_Attribute("a3", X_M_Attribute.ATTRIBUTEVALUETYPE_Date, true);
			attributesTestHelper.createM_Attribute("a4", X_M_Attribute.ATTRIBUTEVALUETYPE_List, true);

			final I_I_Inventory importRecord = newInstance(I_I_Inventory.class);
			importRecord.setM_Product_ID(productId.getRepoId());

			final Timestamp bestBeforeDate = SystemTime.asTimestamp();
			final Timestamp dateReceived = SystemTime.asTimestamp();
			final ZonedDateTime date = SystemTime.asZonedDateTime();
			importRecord.setHU_BestBeforeDate(bestBeforeDate);
			importRecord.setLot("1234");
			importRecord.setTE("te1111");
			importRecord.setDateReceived(dateReceived);

			importRecord.setAttributeCode1("a1");
			importRecord.setAttributeValueString1("string1");
			importRecord.setAttributeCode2("a2");
			importRecord.setAttributeValueString2("12.345678");
			importRecord.setAttributeCode3("a3");
			importRecord.setAttributeValueString3(date.format(Env.DATE_FORMAT));
			importRecord.setAttributeCode4("a4");
			importRecord.setAttributeValueString4("listItem1");

			final AttributeSetInstanceId asiId = inventoryImportProcess.extractASI(importRecord);
			final ImmutableAttributeSet asi = attributeDAO.getImmutableAttributeSetById(asiId);
			assertThat(asi)
					.usingRecursiveComparison()
					.isEqualTo(ImmutableAttributeSet.builder()
							.attributeValue(AttributeConstants.ATTR_BestBeforeDate, bestBeforeDate)
							.attributeValue(AttributeConstants.ATTR_LotNumber, "1234")
							.attributeValue(AttributeConstants.ATTR_TE, "te1111")
							.attributeValue(AttributeConstants.ATTR_DateReceived, dateReceived)
							.attributeValue(AttributeCode.ofString("a1"), "string1")
							.attributeValue(AttributeCode.ofString("a2"), new BigDecimal("12.345678"))
							.attributeValue(AttributeCode.ofString("a3"), TimeUtil.asTimestamp(date))
							.attributeValue(AttributeCode.ofString("a4"), "listItem1")
							.build());
		}

		@Test
		public void overlappingAttributes()
		{
			final I_I_Inventory importRecord = newInstance(I_I_Inventory.class);
			importRecord.setM_Product_ID(productId.getRepoId());

			final ZonedDateTime bestBeforeDate = SystemTime.asZonedDateTime();
			final ZonedDateTime dateReceived = SystemTime.asZonedDateTime();
			importRecord.setHU_BestBeforeDate(TimeUtil.asTimestamp(bestBeforeDate));
			importRecord.setLot("1234");
			importRecord.setTE("TE-OK");
			importRecord.setDateReceived(TimeUtil.asTimestamp(dateReceived));

			importRecord.setAttributeCode1(AttributeConstants.ATTR_BestBeforeDate.getCode());
			importRecord.setAttributeValueString1(bestBeforeDate.plusYears(1).format(Env.DATE_FORMAT));
			importRecord.setAttributeCode2(AttributeConstants.ATTR_LotNumber.getCode());
			importRecord.setAttributeValueString2("1234-wrong");
			importRecord.setAttributeCode3(AttributeConstants.ATTR_TE.getCode());
			importRecord.setAttributeValueString3("TE-wrong");
			importRecord.setAttributeCode4(AttributeConstants.ATTR_DateReceived.getCode());
			importRecord.setAttributeValueString4(dateReceived.plusYears(1).format(Env.DATE_FORMAT));

			final AttributeSetInstanceId asiId = inventoryImportProcess.extractASI(importRecord);
			final ImmutableAttributeSet asi = attributeDAO.getImmutableAttributeSetById(asiId);
			assertThat(asi)
					.usingRecursiveComparison()
					.isEqualTo(ImmutableAttributeSet.builder()
							.attributeValue(AttributeConstants.ATTR_BestBeforeDate, TimeUtil.asTimestamp(bestBeforeDate))
							.attributeValue(AttributeConstants.ATTR_LotNumber, "1234")
							.attributeValue(AttributeConstants.ATTR_TE, "TE-OK")
							.attributeValue(AttributeConstants.ATTR_DateReceived, TimeUtil.asTimestamp(dateReceived))
							.build());
		}

	}
}
