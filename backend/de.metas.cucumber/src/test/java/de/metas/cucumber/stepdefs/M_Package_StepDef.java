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

package de.metas.cucumber.stepdefs;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_ShipperTransportation_StepDefData;
import de.metas.cucumber.stepdefs.shipper.M_Shipper_StepDefData;
import de.metas.inout.InOutId;
import de.metas.mpackage.PackageId;
import de.metas.product.ProductId;
import de.metas.shipping.ShipperId;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_M_Package;
import org.compiere.model.I_M_Shipper;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.assertThat;

@AllArgsConstructor
public class M_Package_StepDef
{
	private final M_Package_StepDefData packageTable;
	private final M_Shipper_StepDefData shipperTable;
	private final C_BPartner_Location_StepDefData bPartnerLocationTable;
	private final C_BPartner_StepDefData bPartnerTable;
	private final M_ShipperTransportation_StepDefData deliveryInstructionTable;
	private final M_Product_StepDefData productTable;

	private final M_InOut_StepDefData shipmentTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@And("^load M_Package for M_ShipperTransportation: (.*)$")
	public void load_M_Package(@NonNull final String deliveryInstructionIdentifier, @NonNull final DataTable dataTable)
	{
		final I_M_ShipperTransportation deliveryInstruction = deliveryInstructionTable.get(deliveryInstructionIdentifier);
		assertThat(deliveryInstruction).isNotNull();

		final List<I_M_ShippingPackage> shippingPackages = queryBL.createQueryBuilder(I_M_ShippingPackage.class)
				.addEqualsFilter(I_M_ShippingPackage.COLUMNNAME_M_ShipperTransportation_ID, deliveryInstruction.getM_ShipperTransportation_ID())
				.orderByDescending(I_M_ShippingPackage.COLUMNNAME_Created)
				.create()
				.list();

		final List<Map<String, String>> dataMaps = dataTable.asMaps();
		// it's possible that other packages have been added to the transportation order

		final SoftAssertions softly = new SoftAssertions();
		for (final Map<String, String> row : dataMaps)
		{
			I_M_ShippingPackage shippingPackage = null;

			final String productIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_ShippingPackage.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			final String shipmentIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_ShippingPackage.COLUMNNAME_M_InOut_ID + "." + TABLECOLUMN_IDENTIFIER);
			softly.assertThat(CoalesceUtil.firstNotBlank(productIdentifier, shipmentIdentifier)).as("Product or shipment identifiers").isNotBlank();

			if (Check.isNotBlank(productIdentifier))
			{
				final ProductId productId = ProductId.ofRepoId(productTable.get(productIdentifier).getM_Product_ID());

				shippingPackage = shippingPackages.stream()
						.filter(pkg -> Objects.equals(pkg.getM_Product_ID(), productId.getRepoId()))
						.findFirst()
						.orElse(null);
			}
			else if (Check.isNotBlank(shipmentIdentifier))
			{
				final InOutId shipmentId = InOutId.ofRepoId(shipmentTable.get(shipmentIdentifier).getM_InOut_ID());
				shippingPackage = shippingPackages.stream()
						.filter(pkg -> Objects.equals(pkg.getM_InOut_ID(), shipmentId.getRepoId()))
						.findFirst()
						.orElse(null);
			}
			softly.assertThat(shippingPackage).as("Shipping package").isNotNull();

			if (shippingPackage != null)
			{
				final I_M_Package packageRecord = InterfaceWrapperHelper.load(shippingPackage.getM_Package_ID(), I_M_Package.class);
				softly.assertThat(packageRecord).as("Package").isNotNull();

				final String packageIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_Package.COLUMNNAME_M_Package_ID + "." + TABLECOLUMN_IDENTIFIER);
				packageTable.putOrReplace(packageIdentifier, packageRecord);
			}
		}

		softly.assertAll();
	}

	@And("^validate M_Packages for shipment (.*)$")
	public void validateMPackages(@NonNull String shipmentIdentifierStr, @NonNull final DataTable dataTable)
	{
		final InOutId shipmentId = shipmentTable.getId(shipmentIdentifierStr);
		final List<I_M_Package> packageRecords = retrievePackagesByShipmentId(shipmentId)
				.sorted(Comparator.comparing(I_M_Package::getM_Package_ID)) // creation order
				.collect(Collectors.toList());

		final DataTableRows expectations = DataTableRows.of(dataTable);
		assertThat(packageRecords).hasSize(expectations.size());
		expectations
				.setAdditionalRowIdentifierColumnName(I_M_Package.COLUMNNAME_M_Package_ID)
				.forEach((expectation, index) -> validateMPackage(packageRecords.get(index), expectation));
	}

	public void validateMPackage(@NonNull I_M_Package packageRecord, @NonNull final DataTableRow row)
	{
		final SoftAssertions softly = new SoftAssertions();

		row.getAsOptionalIdentifier(I_M_Package.COLUMNNAME_M_Shipper_ID)
				.map(shipperTable::getId)
				.ifPresent(shipperId -> softly.assertThat(ShipperId.ofRepoIdOrNull(packageRecord.getM_Shipper_ID()))
						.as("M_Shipper_ID")
						.isEqualTo(shipperId));
		row.getAsOptionalIdentifier(I_M_Package.COLUMNNAME_C_BPartner_ID)
				.map(bPartnerTable::getId)
				.ifPresent(bpartnerId -> softly.assertThat(BPartnerId.ofRepoIdOrNull(packageRecord.getC_BPartner_ID()))
						.as("C_BPartner_ID")
						.isEqualTo(bpartnerId));
		row.getAsOptionalIdentifier(I_M_Package.COLUMNNAME_C_BPartner_Location_ID)
				.map(bPartnerLocationTable::getId)
				.ifPresent(bpartnerLocationId -> softly.assertThat(BPartnerLocationId.ofRepoIdOrNull(packageRecord.getC_BPartner_ID(), packageRecord.getC_BPartner_Location_ID()))
						.as("C_BPartner_Location_ID")
						.isEqualTo(bpartnerLocationId));
		row.getAsOptionalBigDecimal(I_M_Package.COLUMNNAME_PackageWeight)
				.ifPresent(packageWeight -> softly.assertThat(packageRecord.getPackageWeight())
						.as("PackageWeight")
						.isEqualByComparingTo(packageWeight));

		softly.assertAll();

		row.getAsOptionalIdentifier()
				.ifPresent(packageIdentifier -> packageTable.putOrReplace(packageIdentifier, packageRecord));
	}

	@And("validate M_Package:")
	public void validate_M_Package(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.forEach(row -> {
					final SoftAssertions softly = new SoftAssertions();

					final String packageIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_Package.COLUMNNAME_M_Package_ID + "." + TABLECOLUMN_IDENTIFIER);
					final I_M_Package packageRecord = packageTable.get(packageIdentifier);
					softly.assertThat(packageRecord).isNotNull();

					final String shipperIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_Package.COLUMNNAME_M_Shipper_ID + "." + TABLECOLUMN_IDENTIFIER);
					final I_M_Shipper shipper = shipperTable.get(shipperIdentifier);
					softly.assertThat(shipper).isNotNull();
					softly.assertThat(packageRecord.getM_Shipper_ID()).as(I_M_Package.COLUMNNAME_M_Shipper_ID).isEqualTo(shipper.getM_Shipper_ID());

					final String bpartnerIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_Package.COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
					if (Check.isNotBlank(bpartnerIdentifier))
					{
						final I_C_BPartner bPartner = bPartnerTable.get(bpartnerIdentifier);
						softly.assertThat(bPartner).isNotNull();
						softly.assertThat(packageRecord.getC_BPartner_ID()).as(I_M_Package.COLUMNNAME_C_BPartner_ID).isEqualTo(bPartner.getC_BPartner_ID());
					}

					final String bpartnerLocIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_Package.COLUMNNAME_C_BPartner_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
					if (Check.isNotBlank(bpartnerLocIdentifier))
					{
						final I_C_BPartner_Location bPartnerLocation = bPartnerLocationTable.get(bpartnerLocIdentifier);
						softly.assertThat(bPartnerLocation).isNotNull();
						softly.assertThat(packageRecord.getC_BPartner_Location_ID()).as(I_M_Package.COLUMNNAME_C_BPartner_Location_ID).isEqualTo(bPartnerLocation.getC_BPartner_Location_ID());
					}

					row.getAsOptionalLocalDateTimestamp(I_M_Package.COLUMNNAME_ShipDate)
							.ifPresent(shipDate -> softly.assertThat(packageRecord.getShipDate()).as(I_M_Package.COLUMNNAME_ShipDate).isEqualTo(shipDate));

					row.getAsOptionalBigDecimal(I_M_Package.COLUMNNAME_PackageWeight)
							.ifPresent(packageWeight -> softly.assertThat(packageRecord.getPackageWeight()).as(I_M_Package.COLUMNNAME_PackageWeight).isEqualByComparingTo(packageWeight));

					softly.assertAll();
				});
	}

	private Stream<I_M_Package> retrievePackagesByShipmentId(final InOutId shipmentId)
	{
		final List<I_M_ShippingPackage> shippingPackages = retrieveShippingPackagesByShipmentId(shipmentId);
		final ImmutableSet<PackageId> packageIds = extractPackageIds(shippingPackages);
		return streamPackagesByIds(packageIds);
	}

	private List<I_M_ShippingPackage> retrieveShippingPackagesByShipmentId(final InOutId shipmentId)
	{
		return queryBL.createQueryBuilder(I_M_ShippingPackage.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ShippingPackage.COLUMNNAME_M_InOut_ID, shipmentId)
				.create()
				.list();
	}

	private static ImmutableSet<PackageId> extractPackageIds(final List<I_M_ShippingPackage> shippingPackages)
	{
		return shippingPackages.stream()
				.map(shippingPackage -> PackageId.ofRepoId(shippingPackage.getM_Package_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}

	private Stream<I_M_Package> streamPackagesByIds(final Collection<PackageId> packageIds)
	{
		if (packageIds.isEmpty())
		{
			return Stream.empty();
		}

		return queryBL.createQueryBuilder(I_M_Package.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_Package.COLUMNNAME_M_Package_ID, packageIds)
				.orderBy(I_M_Package.COLUMNNAME_M_Package_ID)
				.create()
				.stream();
	}
}
