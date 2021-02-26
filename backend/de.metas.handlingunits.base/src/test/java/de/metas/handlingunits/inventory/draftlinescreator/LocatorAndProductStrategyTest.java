package de.metas.handlingunits.inventory.draftlinescreator;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.common.collect.ImmutableList;

import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.AttributesKeyPart;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
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

@ExtendWith(AdempiereTestWatcher.class)
public class LocatorAndProductStrategyTest
{
	private HUTestHelper helper;

	private I_C_UOM uomEach;
	private ProductId productId;
	private LocatorId locatorId;

	@BeforeEach
	public void init()
	{
		helper = HUTestHelper.newInstanceOutOfTrx();

		uomEach = helper.uomEach;
		productId = BusinessTestHelper.createProductId("product", uomEach);

		final I_M_Warehouse warehouse = BusinessTestHelper.createWarehouse("WH");
		I_M_Locator locator = BusinessTestHelper.createLocator("locator", warehouse);
		locatorId = LocatorId.ofRepoId(locator.getM_Warehouse_ID(), locator.getM_Locator_ID());

		//
		// IMPOTANT: make sure the attributes are storage revelant, else they are ignored on matching
		helper.attr_BestBeforeDate.setIsStorageRelevant(true);
		saveRecord(helper.attr_BestBeforeDate);

		helper.attr_LotNumber.setIsStorageRelevant(true);
		saveRecord(helper.attr_LotNumber);
	}

	@Builder(builderMethodName = "hu", builderClassName = "$HUBuilder")
	private HuId createHU(
			@NonNull final LocatorId locatorId,
			@Nullable final LocalDate bestBeforeDate,
			@Nullable final String lotNumber)
	{
		final List<I_M_HU> vhus = helper.createHUs(
				helper.createMutableHUContextForProcessingOutOfTrx(),
				helper.huDefVirtual,
				productId,
				Quantity.of(1, uomEach));
		I_M_HU vhu = CollectionUtils.singleElement(vhus);

		vhu.setM_Locator_ID(locatorId.getRepoId());
		vhu.setHUStatus(X_M_HU.HUSTATUS_Active);
		InterfaceWrapperHelper.save(vhu);

		final IAttributeStorage vhuAttributes = helper.createMutableHUContext()
				.getHUAttributeStorageFactory()
				.getAttributeStorage(vhu);
		vhuAttributes.setSaveOnChange(true);
		vhuAttributes.setValue(AttributeConstants.ATTR_BestBeforeDate, bestBeforeDate);
		vhuAttributes.setValue(AttributeConstants.ATTR_LotNumber, lotNumber);

		final HuId huId = HuId.ofRepoId(vhu.getM_HU_ID());

		System.out.println("Created " + huId + " with BestBeforeDate=" + bestBeforeDate + ", LotNumber=" + lotNumber);

		return huId;
	}

	@Builder(builderMethodName = "asi", builderClassName = "$ASIBuilder")
	private AttributeSetInstanceId createASI(
			@Nullable final LocalDate bestBeforeDate,
			@Nullable final String lotNumber)
	{
		final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);

		final I_M_AttributeSetInstance asi = attributeSetInstanceBL.createASI(productId);
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoId(asi.getM_AttributeSetInstance_ID());

		if (!Check.isBlank(lotNumber))
		{
			attributeSetInstanceBL.setAttributeInstanceValue(asiId, AttributeConstants.ATTR_LotNumber, lotNumber);
		}

		if (bestBeforeDate != null)
		{
			attributeSetInstanceBL.setAttributeInstanceValue(asiId, AttributeConstants.ATTR_BestBeforeDate, bestBeforeDate);
		}

		System.out.println("Created " + asiId + " with BestBeforeDate=" + bestBeforeDate + ", LotNumber=" + lotNumber);

		return asiId;
	}

	@Test
	public void searchWithLotAndBestBeforeDate_but_onlyOneHUWithoutAttributesExist()
	{
		@SuppressWarnings("unused")
		final HuId huWithoutAttributes = hu().locatorId(locatorId).build();

		final ImmutableList<HuForInventoryLine> result = LocatorAndProductStrategy.builder()
				.huForInventoryLineFactory(new HuForInventoryLineFactory())
				.locatorId(locatorId)
				.productId(productId)
				.asiId(asi()
						.lotNumber("lot1")
						.bestBeforeDate(LocalDate.parse("2020-10-10"))
						.build())
				.build()
				.streamHus()
				.collect(ImmutableList.toImmutableList());

		assertThat(result).isEmpty();
	}

	@Test
	public void preciseMatch()
	{
		final HuId huId = hu()
				.locatorId(locatorId)
				.lotNumber("lot1")
				.bestBeforeDate(LocalDate.parse("2020-10-10"))
				.build();

		final ImmutableList<HuForInventoryLine> result = LocatorAndProductStrategy.builder()
				.huForInventoryLineFactory(new HuForInventoryLineFactory())
				.locatorId(locatorId)
				.productId(productId)
				.asiId(asi()
						.lotNumber("lot1")
						.bestBeforeDate(LocalDate.parse("2020-10-10"))
						.build())
				.build()
				.streamHus()
				.collect(ImmutableList.toImmutableList());

		assertThat(result).hasSize(1);
		assertThat(result.get(0))
				.usingRecursiveComparison()
				.isEqualTo(HuForInventoryLine.builder()
						.orgId(OrgId.ANY)
						.huId(huId)
						.quantityBooked(Quantity.of(1, uomEach))
						.quantityCount(Quantity.of(1, uomEach))
						.productId(productId)
						.storageAttributesKey(AttributesKey.ofParts(
								AttributesKeyPart.ofStringAttribute(AttributeId.ofRepoId(helper.attr_LotNumber.getM_Attribute_ID()), "lot1"),
								AttributesKeyPart.ofDateAttribute(AttributeId.ofRepoId(helper.attr_BestBeforeDate.getM_Attribute_ID()), LocalDate.parse("2020-10-10"))))
						.locatorId(locatorId)
						.markAsCounted(false)
						.build());
	}

}
