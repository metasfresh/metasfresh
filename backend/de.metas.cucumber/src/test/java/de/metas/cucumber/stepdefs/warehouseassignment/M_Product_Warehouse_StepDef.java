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

package de.metas.cucumber.stepdefs.warehouseassignment;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.warehouseassignment.ProductWarehouseAssignmentService;
import de.metas.warehouseassignment.ProductWarehouseAssignments;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Warehouse;
import org.compiere.model.I_M_Warehouse;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class M_Product_Warehouse_StepDef
{
	private final ProductWarehouseAssignmentService productWarehouseAssignmentService = SpringContextHolder.instance.getBean(ProductWarehouseAssignmentService.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final M_Product_StepDefData productTable;
	private final M_Product_Warehouse_StepDefData productWarehouseAssignmentTable;
	private final M_Warehouse_StepDefData warehouseTable;

	public M_Product_Warehouse_StepDef(
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final M_Product_Warehouse_StepDefData productWarehouseAssignmentTable,
			@NonNull final M_Warehouse_StepDefData warehouseTable)
	{
		this.productTable = productTable;
		this.productWarehouseAssignmentTable = productWarehouseAssignmentTable;
		this.warehouseTable = warehouseTable;
	}

	@And("metasfresh contains M_Product_Warehouse")
	public void metasfresh_contains_warehouse_assignments(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			createWarehouseAssignment(tableRow);
		}
	}

	@And("locate warehouse assignments")
	public void locate_assignments_by_product_id(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			locate_assignments_by_product_id(tableRow);
		}
	}

	@And("validate warehouse assignments")
	public void validate_assignments(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			validate_assignment(tableRow);
		}
	}

	private void createWarehouseAssignment(@NonNull final Map<String, String> tableRow)
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_Product_Warehouse.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_M_Product productRecord = productTable.get(productIdentifier);
		final ProductId productId = ProductId.ofRepoId(productRecord.getM_Product_ID());

		final String warehouseIdentifierCandidate = DataTableUtil.extractStringForColumnName(tableRow, I_M_Product_Warehouse.COLUMNNAME_M_Warehouse_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final ImmutableSet<WarehouseId> warehouseIds = StepDefUtil.extractIdentifiers(warehouseIdentifierCandidate)
				.stream()
				.map(warehouseTable::get)
				.map(I_M_Warehouse::getM_Warehouse_ID)
				.map(WarehouseId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());

		final ProductWarehouseAssignments productWarehouseAssignment = ProductWarehouseAssignments.builder()
				.productId(productId)
				.warehouseIds(warehouseIds)
				.build();

		productWarehouseAssignmentService.save(productWarehouseAssignment);
		final ImmutableList<I_M_Product_Warehouse> createdWarehouseAssignments = getByProductId(productId);

		if (createdWarehouseAssignments.size() != warehouseIds.size())
		{
			throw new AdempiereException("No of created assignments is different than the number of specified warehouses !")
					.appendParametersToMessage()
					.setParameter("CreatedWarehouseAssignments", createdWarehouseAssignments)
					.setParameter("WarehouseIds", warehouseIds);
		}

		final String identifierCandidates = DataTableUtil.extractStringForColumnName(tableRow, I_M_Product_Warehouse.COLUMNNAME_M_Product_Warehouse_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final ImmutableList<String> assignmentIdentifiers = StepDefUtil.extractIdentifiers(identifierCandidates);

		assertThat(assignmentIdentifiers).isNotEmpty();
		assertThat(createdWarehouseAssignments.size()).isEqualTo(assignmentIdentifiers.size());

		for (int assignmentIdentifier = 0; assignmentIdentifier < createdWarehouseAssignments.size(); assignmentIdentifier++)
		{
			productWarehouseAssignmentTable.putOrReplace(assignmentIdentifiers.get(assignmentIdentifier), createdWarehouseAssignments.get(assignmentIdentifier));
		}
	}

	private void validate_assignment(@NonNull final Map<String, String> tableRow)
	{
		final String assignmentIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_Product_Warehouse.COLUMNNAME_M_Product_Warehouse_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_M_Product_Warehouse warehouseAssignment = productWarehouseAssignmentTable.get(assignmentIdentifier);

		final String warehouseIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_Product_Warehouse.COLUMNNAME_M_Warehouse_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_M_Warehouse warehouseRecord = warehouseTable.get(warehouseIdentifier);

		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_Product_Warehouse.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_M_Product product = productTable.get(productIdentifier);

		assertThat(warehouseAssignment.getM_Warehouse_ID()).as(I_M_Product_Warehouse.COLUMNNAME_M_Warehouse_ID).isEqualTo(warehouseRecord.getM_Warehouse_ID());
		assertThat(warehouseAssignment.getM_Product_ID()).as(I_M_Product_Warehouse.COLUMNNAME_M_Product_ID).isEqualTo(product.getM_Product_ID());
	}

	private void locate_assignments_by_product_id(@NonNull final Map<String, String> tableRow)
	{
		final String assignmentIdentifierCandidate = DataTableUtil.extractStringForColumnName(tableRow, I_M_Product_Warehouse.COLUMNNAME_M_Product_Warehouse_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final ImmutableList<String> assignmentIdentifiers = StepDefUtil.extractIdentifiers(assignmentIdentifierCandidate);

		if (assignmentIdentifiers.isEmpty())
		{
			throw new RuntimeException("No assignment identifier present for column: " + I_M_Product_Warehouse.COLUMNNAME_M_Product_Warehouse_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		}

		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_Product_Warehouse.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_M_Product productRecord = productTable.get(productIdentifier);

		final ImmutableList<I_M_Product_Warehouse> sortedWarehouseAssignments = getByProductId(ProductId.ofRepoId(productRecord.getM_Product_ID()))
				.stream()
				.sorted(Comparator.comparing(I_M_Product_Warehouse::getM_Warehouse_ID))
				.collect(ImmutableList.toImmutableList());

		if (sortedWarehouseAssignments.size() != assignmentIdentifiers.size())
		{
			throw new AdempiereException("There's a discrepancy between the no of found assignments and no of identifiers !")
					.appendParametersToMessage()
					.setParameter("No of assignments", sortedWarehouseAssignments.size())
					.setParameter("No of identifiers", assignmentIdentifiers.size())
					.setParameter(I_M_Product.COLUMNNAME_M_Product_ID, productRecord.getM_Product_ID());
		}

		for (int assignmentIndex = 0; assignmentIndex < sortedWarehouseAssignments.size(); assignmentIndex++)
		{
			productWarehouseAssignmentTable.putOrReplace(assignmentIdentifiers.get(assignmentIndex), sortedWarehouseAssignments.get(assignmentIndex));
		}
	}

	@NonNull
	private ImmutableList<I_M_Product_Warehouse> getByProductId(@NonNull final ProductId productId)
	{
		return queryBL.createQueryBuilder(I_M_Product_Warehouse.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Product_Warehouse.COLUMNNAME_M_Product_ID, productId)
				.list();
	}
}
