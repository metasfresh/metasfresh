/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.contracts.FlatrateTransitionId;
import de.metas.document.DocTypeId;
import de.metas.location.CountryId;
import de.metas.material.planning.pporder.PPRoutingId;
import de.metas.organization.OrgId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ResourceId;
import de.metas.uom.UomId;
import de.metas.workflow.WorkflowId;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;

public class StepDefConstants
{
	public static final String TABLECOLUMN_IDENTIFIER = StepDefDataIdentifier.SUFFIX;
	public static final String CODE = "Code";
	public static final String METASFRESH_VALUE = "metasfresh";

	public static final WarehouseId WAREHOUSE_ID = WarehouseId.ofRepoId(540008);
	public static final ResourceId PLANT_ID = ResourceId.ofRepoId(540011);
	public static final ResourceId TEST_PLANT_ID = ResourceId.ofRepoId(540006);
	public static final PPRoutingId WORKFLOW_ID = PPRoutingId.ofRepoId(540075);
	public static final LocatorId LOCATOR_ID = LocatorId.ofRepoId(WAREHOUSE_ID, 540007);
	public static final DocTypeId DOC_TYPE_ID_MMI = DocTypeId.ofRepoId(1000023);
	public static final OrgId ORG_ID = OrgId.MAIN;
	public static final OrgId ORG_ID_SYSTEM = OrgId.ANY;
	public static final CountryId COUNTRY_ID = CountryId.ofRepoId(101);
	public static final FlatrateTransitionId FLATRATE_TRANSITION_ID = FlatrateTransitionId.ofRepoId(1000003);
	public static final ProductCategoryId PRODUCT_CATEGORY_STANDARD_ID = ProductCategoryId.ofRepoId(1000000);
	public static final UomId PCE_UOM_ID = UomId.ofRepoId(100);
	public static final String HU_ATTR_LOT_NUMBER = "Lot-Nummer";
	public static final int MANAGEMENT_CREDIT_LIMIT_TYPE_ID = 540001;

	public static final BPartnerId METASFRESH_AG_BPARTNER_ID = BPartnerId.ofRepoId(2155894);
	public static final BPartnerLocationId METASFRESH_AG_BPARTNER_LOCATION_ID = BPartnerLocationId.ofRepoId(METASFRESH_AG_BPARTNER_ID, 2202690);
}
