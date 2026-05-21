/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.cucumber.stepdefs.tourplanning;

import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.tourplanning.model.I_M_DeliveryDay;
import de.metas.tourplanning.model.I_M_Tour;
import de.metas.tourplanning.model.I_M_TourVersion;
import de.metas.tourplanning.model.I_M_TourVersionLine;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner_Location;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/** Step definitions for M_Tour, M_TourVersion, M_TourVersionLine, and M_DeliveryDay tour planning entities. */
@RequiredArgsConstructor
public class M_Tour_StepDef
{
	private final M_Tour_StepDefData tourTable;
	private final M_TourVersion_StepDefData tourVersionTable;
	private final M_TourVersionLine_StepDefData tourVersionLineTable;
	private final M_DeliveryDay_StepDefData deliveryDayTable;
	private final C_BPartner_Location_StepDefData locationTable;

	@Given("metasfresh contains M_Tours:")
	public void metasfresh_contains_M_Tours(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createTour);
	}

	@Given("metasfresh contains M_TourVersions:")
	public void metasfresh_contains_M_TourVersions(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createTourVersion);
	}

	@Given("metasfresh contains M_TourVersionLines:")
	public void metasfresh_contains_M_TourVersionLines(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createTourVersionLine);
	}

	@Given("metasfresh contains M_DeliveryDays:")
	public void metasfresh_contains_M_DeliveryDays(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createDeliveryDay);
	}

	private void createTour(@NonNull final DataTableRow row)
	{
		final String name = row.suggestValueAndName().getName();

		final I_M_Tour tour = InterfaceWrapperHelper.newInstance(I_M_Tour.class);
		tour.setName(name);
		saveRecord(tour);

		row.getAsOptionalIdentifier().ifPresent(id -> tourTable.putOrReplace(id, tour));
	}

	private void createTourVersion(@NonNull final DataTableRow row)
	{
		final I_M_Tour tour = row.getAsIdentifier(I_M_TourVersion.COLUMNNAME_M_Tour_ID).lookupNotNullIn(tourTable);
		final String name = row.suggestValueAndName().getName();

		final I_M_TourVersion tv = InterfaceWrapperHelper.newInstance(I_M_TourVersion.class);
		tv.setM_Tour_ID(tour.getM_Tour_ID());
		tv.setName(name);
		row.getAsOptionalLocalDateTimestamp(I_M_TourVersion.COLUMNNAME_ValidFrom).ifPresent(tv::setValidFrom);
		saveRecord(tv);

		row.getAsOptionalIdentifier().ifPresent(id -> tourVersionTable.putOrReplace(id, tv));
	}

	private void createTourVersionLine(@NonNull final DataTableRow row)
	{
		final I_M_TourVersion tv = row.getAsIdentifier(I_M_TourVersionLine.COLUMNNAME_M_TourVersion_ID).lookupNotNullIn(tourVersionTable);
		final I_C_BPartner_Location location = row.getAsIdentifier(I_M_TourVersionLine.COLUMNNAME_C_BPartner_Location_ID).lookupNotNullIn(locationTable);

		final I_M_TourVersionLine tvl = InterfaceWrapperHelper.newInstance(I_M_TourVersionLine.class);
		tvl.setM_TourVersion_ID(tv.getM_TourVersion_ID());
		tvl.setC_BPartner_ID(location.getC_BPartner_ID());
		tvl.setC_BPartner_Location_ID(location.getC_BPartner_Location_ID());
		// IsToBeFetched=false → customer delivery; IsToBeFetched=true → vendor pickup
		tvl.setIsToBeFetched(row.getAsBoolean(I_M_TourVersionLine.COLUMNNAME_IsToBeFetched));
		tvl.setSeqNo(row.getAsInt(I_M_TourVersionLine.COLUMNNAME_SeqNo));
		saveRecord(tvl);

		row.getAsOptionalIdentifier().ifPresent(id -> tourVersionLineTable.putOrReplace(id, tvl));
	}

	private void createDeliveryDay(@NonNull final DataTableRow row)
	{
		final I_C_BPartner_Location location = row.getAsIdentifier(I_M_DeliveryDay.COLUMNNAME_C_BPartner_Location_ID).lookupNotNullIn(locationTable);
		final I_M_Tour tour = row.getAsIdentifier(I_M_DeliveryDay.COLUMNNAME_M_Tour_ID).lookupNotNullIn(tourTable);
		final I_M_TourVersion tv = row.getAsIdentifier(I_M_DeliveryDay.COLUMNNAME_M_TourVersion_ID).lookupNotNullIn(tourVersionTable);

		final I_M_DeliveryDay dd = InterfaceWrapperHelper.newInstance(I_M_DeliveryDay.class);
		dd.setC_BPartner_ID(location.getC_BPartner_ID());
		dd.setC_BPartner_Location_ID(location.getC_BPartner_Location_ID());
		dd.setDeliveryDate(row.getAsInstantTimestamp(I_M_DeliveryDay.COLUMNNAME_DeliveryDate));
		dd.setDeliveryDateTimeMax(row.getAsInstantTimestamp(I_M_DeliveryDay.COLUMNNAME_DeliveryDateTimeMax));
		// IsToBeFetched=false → SO delivery days; IsToBeFetched=true → PO (fetched-by-vendor) delivery days
		dd.setIsToBeFetched(row.getAsBoolean(I_M_DeliveryDay.COLUMNNAME_IsToBeFetched));
		dd.setIsManual(true);
		dd.setProcessed(false);
		dd.setM_Tour_ID(tour.getM_Tour_ID());
		dd.setM_TourVersion_ID(tv.getM_TourVersion_ID());
		saveRecord(dd);

		row.getAsOptionalIdentifier().ifPresent(id -> deliveryDayTable.putOrReplace(id, dd));
	}
}
