package de.metas.ui.web.pickingV2.productsToPick;

import de.metas.ad_reference.ADReferenceService;
import de.metas.adempiere.model.I_M_Product;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.handlingunits.model.I_M_Locator;
import de.metas.handlingunits.model.I_M_Warehouse;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.reservation.HUReservationRepository;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.sourcehu.HuId2SourceHUsService;
import de.metas.handlingunits.test.misc.builders.HUPIAttributeBuilder;
import de.metas.handlingunits.trace.HUTraceRepository;
import de.metas.picking.api.PickingConfigRepository;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.ui.web.pickingV2.productsToPick.rows.factory.ProductsToPickRowsDataFactory;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.user.UserRepository;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.time.LocalDate;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

final class PickingV2TestHelper
{
	public final I_C_UOM uomKg;
	public final AttributeId bestBeforeDateAttributeId;
	private int huPIAttributeId_VHU_BestBeforeDate;

	public PickingV2TestHelper()
	{
		uomKg = createUOM("Kg");
		bestBeforeDateAttributeId = createAttribute(AttributeConstants.ATTR_BestBeforeDate, X_M_Attribute.ATTRIBUTEVALUETYPE_Date);
		createVirtualPI();
	}

	public AttributeId createAttribute(
			@NonNull final AttributeCode attributeCode,
			@NonNull final String valueType)
	{
		final I_M_Attribute attribute = newInstance(I_M_Attribute.class);
		attribute.setValue(attributeCode.getCode());
		attribute.setName(attributeCode.getCode());
		attribute.setAttributeValueType(valueType);
		saveRecord(attribute);
		return AttributeId.ofRepoId(attribute.getM_Attribute_ID());
	}

	public I_C_UOM createUOM(final String symbol)
	{
		final I_C_UOM record = newInstance(I_C_UOM.class);
		record.setUOMSymbol(symbol);
		saveRecord(record);
		return record;
	}

	public ProductId createProduct(final String code)
	{
		final I_M_Product record = newInstance(I_M_Product.class);
		record.setValue(code);
		record.setName(code);
		saveRecord(record);
		return ProductId.ofRepoId(record.getM_Product_ID());
	}

	public WarehouseId createWarehouse()
	{
		final I_M_Warehouse record = newInstance(I_M_Warehouse.class);
		saveRecord(record);
		return WarehouseId.ofRepoId(record.getM_Warehouse_ID());
	}

	public LocatorId createLocator(final WarehouseId warehouseId)
	{
		final I_M_Locator record = newInstance(I_M_Locator.class);
		record.setM_Warehouse_ID(warehouseId.getRepoId());
		saveRecord(record);
		return LocatorId.ofRecord(record);
	}

	@Builder(builderMethodName = "prepareExistingHU", builderClassName = "ExistingHUBuilder")
	private HuId createExistingHU(
			@NonNull final LocatorId locatorId,
			@Nullable final LocalDate bestBeforeDate,
			@NonNull final ProductId productId,
			@NonNull final Quantity qty)
	{
		final I_M_HU hu = newInstance(I_M_HU.class);
		hu.setM_HU_PI_Version_ID(HuPackingInstructionsVersionId.VIRTUAL.getRepoId());
		hu.setHUStatus(X_M_HU.HUSTATUS_Active);
		hu.setM_Locator_ID(locatorId.getRepoId());
		saveRecord(hu);

		final I_M_HU_Attribute huAttribute = newInstance(I_M_HU_Attribute.class);
		huAttribute.setM_HU_ID(hu.getM_HU_ID());
		huAttribute.setM_HU_PI_Attribute_ID(huPIAttributeId_VHU_BestBeforeDate);
		huAttribute.setM_Attribute_ID(bestBeforeDateAttributeId.getRepoId());
		huAttribute.setValueDate(TimeUtil.asTimestamp(bestBeforeDate));
		saveRecord(huAttribute);

		final I_M_HU_Storage huStorage = newInstance(I_M_HU_Storage.class);
		huStorage.setM_HU_ID(hu.getM_HU_ID());
		huStorage.setM_Product_ID(productId.getRepoId());
		huStorage.setQty(qty.toBigDecimal());
		huStorage.setC_UOM_ID(qty.getUomId().getRepoId());
		saveRecord(huStorage);

		return HuId.ofRepoId(hu.getM_HU_ID());
	}

	public void createVirtualPI()
	{
		final I_M_HU_PI pi = InterfaceWrapperHelper.newInstance(I_M_HU_PI.class);
		pi.setName("VirtualPI");
		pi.setM_HU_PI_ID(HuPackingInstructionsId.VIRTUAL.getRepoId());
		saveRecord(pi);

		final I_M_HU_PI_Version version = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Version.class);
		version.setName(pi.getName());
		version.setM_HU_PI_ID(pi.getM_HU_PI_ID());
		version.setIsCurrent(true);
		version.setHU_UnitType(X_M_HU_PI_Version.HU_UNITTYPE_VirtualPI);
		version.setM_HU_PI_Version_ID(HuPackingInstructionsVersionId.VIRTUAL.getRepoId());
		saveRecord(version);

		final I_M_HU_PI_Attribute huPIAttribute = HUPIAttributeBuilder.newInstance(bestBeforeDateAttributeId)
				.setM_HU_PI_Version(HuPackingInstructionsVersionId.VIRTUAL)
				.create();
		huPIAttributeId_VHU_BestBeforeDate = huPIAttribute.getM_HU_PI_Attribute_ID();
	}

	public ProductsToPickRowsDataFactory createProductsToPickRowsDataFactory()
	{
		final IBPartnerBL bpartnersService = createAndRegisterBPartnerBL();

		final HUReservationRepository huReservationRepository = new HUReservationRepository();
		final HUReservationService huReservationService = new HUReservationService(huReservationRepository);

		final PickingCandidateRepository pickingCandidateRepository = new PickingCandidateRepository();

		final HUTraceRepository huTraceRepository = new HUTraceRepository();
		final HuId2SourceHUsService sourceHUsRepository = new HuId2SourceHUsService(huTraceRepository);

		final PickingCandidateService pickingCandidateService = new PickingCandidateService(
				new PickingConfigRepository(),
				pickingCandidateRepository,
				sourceHUsRepository,
				huReservationService,
				bpartnersService,
				ADReferenceService.newMocked(),
				InventoryService.newInstanceForUnitTesting()
		);

		return ProductsToPickRowsDataFactory.builder()
				.pickingCandidateService(pickingCandidateService)
				.locatorLookup(this::generateLocatorLookupById)
				.considerAttributes(false)
				.build();
	}

	private BPartnerBL createAndRegisterBPartnerBL()
	{
		final UserRepository userRepository = new UserRepository();
		final BPartnerBL bpartnersService = new BPartnerBL(userRepository);
		Services.registerService(IBPartnerBL.class, bpartnersService);
		return bpartnersService;
	}

	@Nullable
	private IntegerLookupValue generateLocatorLookupById(final Object idObj)
	{
		if (idObj == null)
		{
			return null;
		}
		final int id = NumberUtils.asIntegerOrNull(idObj);
		return IntegerLookupValue.of(id, "locator-" + id);
	}

}
