/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.cucumber.stepdefs.billofmaterial;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.attribute.M_AttributeSetInstance_StepDefData;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.BOMType;
import org.eevolution.api.BOMUse;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.eevolution.model.I_PP_Product_BOMVersions;
import org.eevolution.model.X_PP_Product_BOM;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.eevolution.model.I_PP_Product_BOMLine.COLUMNNAME_M_AttributeSetInstance_ID;

public class PP_Product_Bom_StepDef
{
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private static final int DEFAULT_C_DOCTYPE_ID = 541027;

	private final M_Product_StepDefData productTable;
	private final PP_Product_BOM_StepDefData productBOMTable;
	private final PP_Product_BOMVersions_StepDefData productBomVersionsTable;
	private final PP_Product_BOMLine_StepDefData productBOMLineTable;
	private final M_AttributeSetInstance_StepDefData attributeSetInstanceTable;

	public PP_Product_Bom_StepDef(
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final PP_Product_BOM_StepDefData productBOMTable,
			@NonNull final PP_Product_BOMVersions_StepDefData productBomVersionsTable,
			@NonNull final PP_Product_BOMLine_StepDefData productBOMLineTable,
			@NonNull final M_AttributeSetInstance_StepDefData attributeSetInstanceTable
	)
	{
		this.productTable = productTable;
		this.productBOMTable = productBOMTable;
		this.productBomVersionsTable = productBomVersionsTable;
		this.productBOMLineTable = productBOMLineTable;
		this.attributeSetInstanceTable = attributeSetInstanceTable;
	}

	@Given("metasfresh contains PP_Product_BOM")
	public void add_PP_Product_Bom(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps();
		for (final Map<String, String> tableRow : tableRows)
		{
			createPP_Product_BOM(tableRow);
		}
	}

	@Given("metasfresh contains PP_Product_BOMLines")
	public void add_PP_Product_BOMLine(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps();
		for (final Map<String, String> tableRow : tableRows)
		{
			createPP_Product_BOMLine(tableRow);
		}
	}

	@And("^the PP_Product_BOM identified by (.*) is completed$")
	public void product_BOM_is_completed(@NonNull final String productBOMIdentifier)
	{
		final I_PP_Product_BOM productBOM = productBOMTable.get(productBOMIdentifier);
		productBOM.setDocAction(IDocument.ACTION_Complete);
		documentBL.processEx(productBOM, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
	}

	private void createPP_Product_BOMLine(@NonNull final Map<String, String> tableRow)
	{
		final String bomIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Product_BOM.COLUMNNAME_PP_Product_BOM_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_PP_Product_BOM productBOMRecord = productBOMTable.get(bomIdentifier);

		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_Product.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_M_Product productRecord = productTable.get(productIdentifier);

		final I_PP_Product_BOMLine bomLine = CoalesceUtil.coalesceSuppliersNotNull(
				() -> queryBL.createQueryBuilder(I_PP_Product_BOMLine.class)
						.addEqualsFilter(I_PP_Product_BOMLine.COLUMNNAME_M_Product_ID, productRecord.getM_Product_ID())
						.addEqualsFilter(I_PP_Product_BOMLine.COLUMNNAME_PP_Product_BOM_ID, productBOMRecord.getPP_Product_BOM_ID())
						.create()
						.firstOnly(I_PP_Product_BOMLine.class),
				() -> newInstance(I_PP_Product_BOMLine.class));

		bomLine.setPP_Product_BOM_ID(productBOMRecord.getPP_Product_BOM_ID());
		bomLine.setM_Product_ID(productRecord.getM_Product_ID());
		bomLine.setC_UOM_ID(productRecord.getC_UOM_ID());

		final String componentTypeCode = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_PP_Product_BOMLine.COLUMNNAME_ComponentType);
		if (Check.isNotBlank(componentTypeCode))
		{
			bomLine.setComponentType(BOMComponentType.ofCode(componentTypeCode).getCode());
		}
		else
		{
			bomLine.setComponentType(BOMComponentType.Component.getCode());
		}

		final Timestamp validFrom = DataTableUtil.extractDateTimestampForColumnName(tableRow, I_PP_Product_BOMLine.COLUMNNAME_ValidFrom);
		final BigDecimal qtyBatch = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_PP_Product_BOMLine.COLUMNNAME_QtyBatch);
		bomLine.setValidFrom(validFrom);

		final boolean isPercentage = DataTableUtil.extractBooleanForColumnNameOr(tableRow, I_PP_Product_BOMLine.COLUMNNAME_IsQtyPercentage, false);
		if (isPercentage)
		{
			bomLine.setIsQtyPercentage(true);
			bomLine.setQtyBatch(qtyBatch);
		}
		else
		{
			bomLine.setQtyBOM(qtyBatch);
		}

		final String asiIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_M_AttributeSetInstance_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(asiIdentifier))
		{
			final I_M_AttributeSetInstance asiRecord = attributeSetInstanceTable.get(asiIdentifier);
			assertThat(asiRecord).isNotNull();

			bomLine.setM_AttributeSetInstance_ID(asiRecord.getM_AttributeSetInstance_ID());
		}

		saveRecord(bomLine);

		final String recordIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, "PP_Product_BOMLine");
		productBOMLineTable.putOrReplace(recordIdentifier, bomLine);
	}

	private void createPP_Product_BOM(@NonNull final Map<String, String> tableRow)
	{
		final I_PP_Product_BOMVersions bomVersions = createBOMVersions(tableRow);

		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_Product.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_M_Product productRecord = productTable.get(productIdentifier);

		final Timestamp validFrom = DataTableUtil.extractDateTimestampForColumnName(tableRow, I_PP_Product_BOM.COLUMNNAME_ValidFrom);

		final BOMType bomType = Optional.ofNullable(DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_PP_Product_BOM.COLUMNNAME_BOMType))
				.map(BOMType::ofCode)
				.orElse(BOMType.CurrentActive);

		final I_PP_Product_BOM productBOMRecord = getExistingBOM(ProductId.ofRepoId(productRecord.getM_Product_ID()), bomType)
				.orElseGet(() -> newInstance(I_PP_Product_BOM.class));

		productBOMRecord.setM_Product_ID(productRecord.getM_Product_ID());
		productBOMRecord.setC_UOM_ID(productRecord.getC_UOM_ID());
		productBOMRecord.setValue(productRecord.getValue());
		productBOMRecord.setName(productRecord.getName());
		productBOMRecord.setBOMType(bomType.getCode());
		productBOMRecord.setBOMUse(BOMUse.Manufacturing.getCode());
		productBOMRecord.setValidFrom(validFrom);
		productBOMRecord.setPP_Product_BOMVersions_ID(bomVersions.getPP_Product_BOMVersions_ID());
		productBOMRecord.setC_DocType_ID(DEFAULT_C_DOCTYPE_ID);
		productBOMRecord.setDateDoc(TimeUtil.asTimestamp(Instant.now()));
		productBOMRecord.setDocStatus(X_PP_Product_BOM.DOCSTATUS_Drafted);

		final String asiIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_M_AttributeSetInstance_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(asiIdentifier))
		{
			final I_M_AttributeSetInstance asiRecord = attributeSetInstanceTable.get(asiIdentifier);
			assertThat(asiRecord).isNotNull();

			productBOMRecord.setM_AttributeSetInstance_ID(asiRecord.getM_AttributeSetInstance_ID());
		}

		saveRecord(productBOMRecord);

		productRecord.setIsBOM(true);
		productRecord.setIsVerified(true);
		saveRecord(productRecord);

		final String recordIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, "PP_Product_BOM");
		productBOMTable.putOrReplace(recordIdentifier, productBOMRecord);
	}

	private Optional<I_PP_Product_BOM> getExistingBOM(@NonNull final ProductId productId, @NonNull final BOMType bomType)
	{
		final List<I_PP_Product_BOM> boms = queryBL.createQueryBuilder(I_PP_Product_BOM.class)
				.addEqualsFilter(I_PP_Product_BOM.COLUMNNAME_M_Product_ID, productId)
				.addEqualsFilter(I_PP_Product_BOM.COLUMNNAME_BOMType, bomType)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();

		if (boms.isEmpty())
		{
			return Optional.empty();
		}
		else if (boms.size() == 1)
		{
			return Optional.of(boms.get(0));
		}
		else
		{
			throw new AdempiereException("More than one BOM found for " + productId + ": \n" + toString(boms));
		}
	}

	@NonNull
	private String toString(final List<I_PP_Product_BOM> boms)
	{
		if (boms.isEmpty())
		{
			return "(no BOMs)";
		}

		final StringBuilder bomInfo = new StringBuilder();
		for (final I_PP_Product_BOM bom : boms)
		{
			if (bomInfo.length() > 0)
			{
				bomInfo.append("\n");
			}
			bomInfo.append(bom);
			productBOMTable.getFirstIdentifierByRecord(bom)
					.ifPresent(identifier -> bomInfo.append(", identified by `").append(identifier).append("`"));
		}

		return bomInfo.toString();
	}

	private I_PP_Product_BOMVersions createBOMVersions(@NonNull final Map<String, String> tableRow)
	{
		final String bomVersionsIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Product_BOMVersions.COLUMNNAME_PP_Product_BOMVersions_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		if (productBomVersionsTable.getOptional(bomVersionsIdentifier).isPresent())
		{
			return productBomVersionsTable.getOptional(bomVersionsIdentifier).get();
		}

		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_Product.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_M_Product productRecord = productTable.get(productIdentifier);

		final I_PP_Product_BOMVersions bomVersionsRecord = CoalesceUtil.coalesceSuppliersNotNull(
				() -> queryBL.createQueryBuilder(I_PP_Product_BOMVersions.class)
						.addEqualsFilter(I_PP_Product_BOMVersions.COLUMNNAME_M_Product_ID, productRecord.getM_Product_ID())
						.create()
						.firstOnly(I_PP_Product_BOMVersions.class),
				() -> newInstance(I_PP_Product_BOMVersions.class));

		bomVersionsRecord.setM_Product_ID(productRecord.getM_Product_ID());
		bomVersionsRecord.setName(productRecord.getName());

		saveRecord(bomVersionsRecord);

		productBomVersionsTable.putOrReplace(bomVersionsIdentifier, bomVersionsRecord);

		return bomVersionsRecord;
	}
}