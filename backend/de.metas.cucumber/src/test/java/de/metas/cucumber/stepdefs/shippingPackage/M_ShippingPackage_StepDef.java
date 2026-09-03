/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.shippingPackage;

import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Package_StepDefData;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.order.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_ShipperTransportation_StepDefData;
import de.metas.cucumber.stepdefs.shipment.pickingterminal.M_ShippingPackage_StepDefData;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Package;
import org.compiere.model.I_M_Product;

import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Asserts the {@code M_ShippingPackage} rows - the consignment a delivery instruction is loaded with. This is
 * where the article and the quantities live: a delivery planning is put on a delivery instruction by an
 * {@code M_Delivery_Planning_Alloc} link that holds no quantity of its own, so a scenario names the package
 * on the allocation step and asserts its load here.
 *
 * @see de.metas.cucumber.stepdefs.shipment.pickingterminal.M_ShippingPackage_StepDefData
 * @see de.metas.cucumber.stepdefs.deliveryplanning.M_Delivery_Planning_Alloc_StepDef
 */
@RequiredArgsConstructor
public class M_ShippingPackage_StepDef
{
	@NonNull private final M_ShippingPackage_StepDefData shippingPackageTable;
	@NonNull private final M_ShipperTransportation_StepDefData shipperTransportationTable;
	@NonNull private final M_Package_StepDefData packageTable;
	@NonNull private final C_BPartner_Location_StepDefData bPartnerLocationTable;
	@NonNull private final C_BPartner_StepDefData bPartnerTable;
	@NonNull private final C_OrderLine_StepDefData orderLineTable;
	@NonNull private final M_Product_StepDefData productTable;

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Finds the shipping package of one package on one delivery instruction and registers it under an
	 * identifier, so that {@code validate M_Shipping_Package:} can assert it.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_ShippingPackage_ID.Identifier</b> — (required) alias to store the found package under<br>
	 *   <b>M_ShipperTransportation_ID.Identifier</b> — (required, identifier-ref) the delivery instruction the
	 *   package hangs off<br>
	 *   <b>M_Package_ID.Identifier</b> — (required, identifier-ref) the package<br>
	 * @cucumber.depends StepDefData: M_ShippingPackage_StepDefData, M_ShipperTransportation_StepDefData,
	 * M_Package_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And load M_Shipping_Package:
	 *   | M_ShippingPackage_ID.Identifier | M_Package_ID.Identifier | M_ShipperTransportation_ID.Identifier |
	 *   | shippingPackage_1               | package_1               | deliveryInstruction_1                 |
	 * </pre>
	 */
	@And("load M_Shipping_Package:")
	public void load_M_Shipping_Package(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String deliveryInstructionIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ShippingPackage.COLUMNNAME_M_ShipperTransportation_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_ShipperTransportation deliveryInstruction = shipperTransportationTable.get(deliveryInstructionIdentifier);
			assertThat(deliveryInstruction).isNotNull();

			final String packageIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ShippingPackage.COLUMNNAME_M_Package_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Package packageRecord = packageTable.get(packageIdentifier);
			assertThat(packageRecord).isNotNull();

			final I_M_ShippingPackage shippingPackage = queryBL.createQueryBuilder(I_M_ShippingPackage.class)
					.addEqualsFilter(I_M_ShippingPackage.COLUMNNAME_M_Package_ID, packageRecord.getM_Package_ID())
					.addEqualsFilter(I_M_ShippingPackage.COLUMNNAME_M_ShipperTransportation_ID, deliveryInstruction.getM_ShipperTransportation_ID())
					.create()
					.firstOnlyNotNull(I_M_ShippingPackage.class);

			final String shippingPackageIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ShippingPackage.COLUMNNAME_M_ShippingPackage_ID + "." + TABLECOLUMN_IDENTIFIER);
			shippingPackageTable.putOrReplace(shippingPackageIdentifier, shippingPackage);
		}
	}

	/**
	 * Asserts one already-registered shipping package. The package is named by an identifier a previous step
	 * registered - {@code load M_Shipping_Package:}, or the {@code M_ShippingPackage_ID} column of the step
	 * that asserts a delivery instruction's active {@code M_Delivery_Planning_Alloc} rows.
	 * <p>
	 * Only {@code ActualLoadQty} is mandatory: it is the load, the reason the record exists. Every other
	 * column asserts a link the package carries, and is skipped when the row leaves it out.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_ShippingPackage_ID</b> — (required, identifier-ref) the package to assert<br>
	 *   <b>ActualLoadQty</b> — (required) expected loaded quantity<br>
	 *   <b>ActualDischargeQuantity</b> — (optional) expected discharged quantity - Task Q14, mirrored from
	 *   the planning<br>
	 *   <b>PlannedLoadedQuantity</b> — (optional) expected planned load quantity - Task Q14, mirrored from
	 *   the planning<br>
	 *   <b>PlannedDischargeQuantity</b> — (optional) expected planned discharge quantity - Task Q14, mirrored
	 *   from the planning<br>
	 *   <b>M_ShipperTransportation_ID</b> — (optional, identifier-ref) expected delivery instruction<br>
	 *   <b>M_Package_ID</b> — (optional, identifier-ref) expected package<br>
	 *   <b>C_BPartner_Location_ID</b> — (optional, identifier-ref) expected delivery address<br>
	 *   <b>C_BPartner_ID</b> — (optional, identifier-ref) expected business partner<br>
	 *   <b>M_Product_ID</b> — (optional, identifier-ref) expected article<br>
	 *   <b>C_OrderLine_ID</b> — (optional, identifier-ref) expected order line<br>
	 * @cucumber.depends StepDefData: M_ShippingPackage_StepDefData, M_ShipperTransportation_StepDefData,
	 * M_Package_StepDefData, C_BPartner_Location_StepDefData, C_BPartner_StepDefData, C_OrderLine_StepDefData,
	 * M_Product_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And validate M_Shipping_Package:
	 *   | M_ShippingPackage_ID | ActualLoadQty |
	 *   | shippingPackage_1    | 7             |
	 * </pre>
	 */
	@And("validate M_Shipping_Package:")
	public void validate_M_Shipping_Package(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final SoftAssertions softly = new SoftAssertions();

			final StepDefDataIdentifier shippingPackageIdentifier = row.getAsIdentifier(I_M_ShippingPackage.COLUMNNAME_M_ShippingPackage_ID);
			final I_M_ShippingPackage shippingPackage = reloadFromDatabase(shippingPackageIdentifier.lookupNotNullIn(shippingPackageTable));

			softly.assertThat(shippingPackage.getActualLoadQty())
					.as("%s of M_ShippingPackage %s", I_M_ShippingPackage.COLUMNNAME_ActualLoadQty, shippingPackageIdentifier)
					.isEqualTo(row.getAsBigDecimal(I_M_ShippingPackage.COLUMNNAME_ActualLoadQty));

			// Task Q14: all four quantity figures are derived from the planning - assert the other three when
			// the row carries them, same pattern as ActualLoadQty above.
			row.getAsOptionalBigDecimal(I_M_ShippingPackage.COLUMNNAME_ActualDischargeQuantity)
					.ifPresent(expected -> softly.assertThat(shippingPackage.getActualDischargeQuantity())
							.as("%s of M_ShippingPackage %s", I_M_ShippingPackage.COLUMNNAME_ActualDischargeQuantity, shippingPackageIdentifier)
							.isEqualTo(expected));

			row.getAsOptionalBigDecimal(I_M_ShippingPackage.COLUMNNAME_PlannedLoadedQuantity)
					.ifPresent(expected -> softly.assertThat(shippingPackage.getPlannedLoadedQuantity())
							.as("%s of M_ShippingPackage %s", I_M_ShippingPackage.COLUMNNAME_PlannedLoadedQuantity, shippingPackageIdentifier)
							.isEqualTo(expected));

			row.getAsOptionalBigDecimal(I_M_ShippingPackage.COLUMNNAME_PlannedDischargeQuantity)
					.ifPresent(expected -> softly.assertThat(shippingPackage.getPlannedDischargeQuantity())
							.as("%s of M_ShippingPackage %s", I_M_ShippingPackage.COLUMNNAME_PlannedDischargeQuantity, shippingPackageIdentifier)
							.isEqualTo(expected));

			row.getAsOptionalIdentifier(I_M_ShippingPackage.COLUMNNAME_M_ShipperTransportation_ID)
					.filter(StepDefDataIdentifier::isNotNullPlaceholder)
					.ifPresent(identifier -> {
						final I_M_ShipperTransportation deliveryInstruction = identifier.lookupNotNullIn(shipperTransportationTable);
						softly.assertThat(shippingPackage.getM_ShipperTransportation_ID())
								.as("%s of M_ShippingPackage %s", I_M_ShippingPackage.COLUMNNAME_M_ShipperTransportation_ID, shippingPackageIdentifier)
								.isEqualTo(deliveryInstruction.getM_ShipperTransportation_ID());
					});

			row.getAsOptionalIdentifier(I_M_ShippingPackage.COLUMNNAME_M_Package_ID)
					.filter(StepDefDataIdentifier::isNotNullPlaceholder)
					.ifPresent(identifier -> {
						final I_M_Package packageRecord = identifier.lookupNotNullIn(packageTable);
						softly.assertThat(shippingPackage.getM_Package_ID())
								.as("%s of M_ShippingPackage %s", I_M_ShippingPackage.COLUMNNAME_M_Package_ID, shippingPackageIdentifier)
								.isEqualTo(packageRecord.getM_Package_ID());
					});

			row.getAsOptionalIdentifier(I_M_ShippingPackage.COLUMNNAME_C_BPartner_Location_ID)
					.filter(StepDefDataIdentifier::isNotNullPlaceholder)
					.ifPresent(identifier -> {
						final I_C_BPartner_Location bPartnerLocation = identifier.lookupNotNullIn(bPartnerLocationTable);
						softly.assertThat(shippingPackage.getC_BPartner_Location_ID())
								.as("%s of M_ShippingPackage %s", I_M_ShippingPackage.COLUMNNAME_C_BPartner_Location_ID, shippingPackageIdentifier)
								.isEqualTo(bPartnerLocation.getC_BPartner_Location_ID());
					});

			row.getAsOptionalIdentifier(I_M_ShippingPackage.COLUMNNAME_C_BPartner_ID)
					.filter(StepDefDataIdentifier::isNotNullPlaceholder)
					.ifPresent(identifier -> {
						final I_C_BPartner bPartner = identifier.lookupNotNullIn(bPartnerTable);
						softly.assertThat(shippingPackage.getC_BPartner_ID())
								.as("%s of M_ShippingPackage %s", I_M_ShippingPackage.COLUMNNAME_C_BPartner_ID, shippingPackageIdentifier)
								.isEqualTo(bPartner.getC_BPartner_ID());
					});

			row.getAsOptionalIdentifier(I_M_ShippingPackage.COLUMNNAME_M_Product_ID)
					.filter(StepDefDataIdentifier::isNotNullPlaceholder)
					.ifPresent(identifier -> {
						final I_M_Product product = identifier.lookupNotNullIn(productTable);
						softly.assertThat(shippingPackage.getM_Product_ID())
								.as("%s of M_ShippingPackage %s", I_M_ShippingPackage.COLUMNNAME_M_Product_ID, shippingPackageIdentifier)
								.isEqualTo(product.getM_Product_ID());
					});

			row.getAsOptionalIdentifier(I_M_ShippingPackage.COLUMNNAME_C_OrderLine_ID)
					.filter(StepDefDataIdentifier::isNotNullPlaceholder)
					.ifPresent(identifier -> {
						final I_C_OrderLine orderLine = identifier.lookupNotNullIn(orderLineTable);
						softly.assertThat(shippingPackage.getC_OrderLine_ID())
								.as("%s of M_ShippingPackage %s", I_M_ShippingPackage.COLUMNNAME_C_OrderLine_ID, shippingPackageIdentifier)
								.isEqualTo(orderLine.getC_OrderLine_ID());
					});

			softly.assertAll();
		});
	}

	/**
	 * Re-reads the package straight from the database, discarding the in-memory record the identifier table
	 * hands out. Mandatory since Task Q14: the four quantity figures are no longer physical columns written
	 * once at generation, they are {@code ColumnSQL} read-throughs of the planning, so any step that changes
	 * the planning changes what this assertion must see.
	 * <p>
	 * {@link de.metas.cucumber.stepdefs.StepDefData#get(StepDefDataIdentifier)} already calls
	 * {@link org.adempiere.model.InterfaceWrapperHelper#refresh(Object)} on every PO it returns - and for
	 * these four columns that is NOT enough. They carry {@code AD_Column.IsLazyLoading='Y'}, and
	 * {@code PO#load(ResultSet)} skips every lazy column (`if (p_info.isLazyLoading(index)) continue;`), so a
	 * refresh leaves both the previously fetched value in {@code m_oldValues} and its
	 * {@code m_valueLoaded[index] = true} flag untouched; the next getter returns the stale figure without
	 * going to the database at all. A record built from a fresh query has that flag clear, so each getter
	 * loads the current value.
	 * <p>
	 * Without this, the natural way to make a scenario pass is to assert the pre-change figure - and the
	 * assertion then holds against an implementation that never propagates anything, which is the exact class
	 * of defect Task Q14 exists to remove.
	 */
	@NonNull
	private I_M_ShippingPackage reloadFromDatabase(@NonNull final I_M_ShippingPackage shippingPackage)
	{
		return queryBL.createQueryBuilder(I_M_ShippingPackage.class)
				.addEqualsFilter(I_M_ShippingPackage.COLUMNNAME_M_ShippingPackage_ID, shippingPackage.getM_ShippingPackage_ID())
				.create()
				.firstOnlyNotNull(I_M_ShippingPackage.class);
	}
}
