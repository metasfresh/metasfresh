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
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.attribute.M_AttributeSetInstance_StepDefData;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.product.ProductId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.BOMType;
import org.eevolution.api.BOMUse;
import org.eevolution.api.ProductBOMId;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.eevolution.model.I_PP_Product_BOMVersions;
import org.eevolution.model.X_PP_Product_BOM;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.eevolution.model.I_PP_Product_BOMLine.COLUMNNAME_M_AttributeSetInstance_ID;

public class PP_Product_Bom_StepDef
{
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private static final int DEFAULT_C_DOCTYPE_ID = 541027;
	private static final LocalDate DEFAULT_ValidFrom = LocalDate.parse("2000-01-01");

	private final M_Product_StepDefData productTable;
	private final PP_Product_BOM_StepDefData productBOMTable;
	private final PP_Product_BOMVersions_StepDefData productBomVersionsTable;
	private final PP_Product_BOMLine_StepDefData productBOMLineTable;
	private final M_AttributeSetInstance_StepDefData attributeSetInstanceTable;

	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

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
		DataTableRows.of(dataTable).forEach(this::createPP_Product_BOM);
	}

	@Given("metasfresh contains PP_Product_BOMLines")
	public void add_PP_Product_BOMLine(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName("PP_Product_BOMLine")
				.forEach(this::createPP_Product_BOMLine);
	}

	@And("^the PP_Product_BOM identified by (.*) is completed$")
	public void product_BOM_is_completed(@NonNull final String productBOMIdentifier)
	{
		final I_PP_Product_BOM productBOM = productBOMTable.get(productBOMIdentifier);
		productBOM.setDocAction(IDocument.ACTION_Complete);
		documentBL.processEx(productBOM, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
	}

	private void createPP_Product_BOMLine(@NonNull final DataTableRow row)
	{
		final I_PP_Product_BOM bom = row.getAsIdentifier(I_PP_Product_BOM.COLUMNNAME_PP_Product_BOM_ID).lookupNotNullIn(productBOMTable);
		final ProductBOMId bomId = ProductBOMId.ofRepoId(bom.getPP_Product_BOM_ID());

		final I_M_Product productRecord = row.getAsIdentifier(I_M_Product.COLUMNNAME_M_Product_ID).lookupNotNullIn(productTable);

		final I_PP_Product_BOMLine bomLine = CoalesceUtil.coalesceSuppliersNotNull(
				() -> queryBL.createQueryBuilder(I_PP_Product_BOMLine.class)
						.addEqualsFilter(I_PP_Product_BOMLine.COLUMNNAME_M_Product_ID, productRecord.getM_Product_ID())
						.addEqualsFilter(I_PP_Product_BOMLine.COLUMNNAME_PP_Product_BOM_ID, bomId)
						.create()
						.firstOnly(I_PP_Product_BOMLine.class),
				() -> newInstance(I_PP_Product_BOMLine.class));

		bomLine.setPP_Product_BOM_ID(bomId.getRepoId());
		bomLine.setM_Product_ID(productRecord.getM_Product_ID());

		final Optional<String> x12de355Code = row.getAsOptionalString(I_C_UOM.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
		if (x12de355Code.isPresent())
		{
			final UomId uomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(x12de355Code.get()));
			bomLine.setC_UOM_ID(uomId.getRepoId());
		}
		else
		{
			bomLine.setC_UOM_ID(productRecord.getC_UOM_ID());
		}

		final BOMComponentType componentType = row.getAsOptionalEnum(I_PP_Product_BOMLine.COLUMNNAME_ComponentType, BOMComponentType.class).orElse(BOMComponentType.Component);
		bomLine.setComponentType(componentType.getCode());

		final Timestamp validFrom = row.getAsOptionalLocalDate(I_PP_Product_BOMLine.COLUMNNAME_ValidFrom)
				.map(localDate -> Timestamp.valueOf(localDate.atStartOfDay()))
				.orElseGet(bom::getValidFrom);
		bomLine.setValidFrom(validFrom);

		final boolean isPercentage = row.getAsOptionalBoolean(I_PP_Product_BOMLine.COLUMNNAME_IsQtyPercentage).orElseFalse();
		final BigDecimal qty = row.getAsBigDecimal(I_PP_Product_BOMLine.COLUMNNAME_QtyBatch);
		if (isPercentage)
		{
			bomLine.setIsQtyPercentage(true);
			bomLine.setQtyBatch(qty);
		}
		else
		{
			bomLine.setQtyBOM(qty);
		}

		row.getAsOptionalIdentifier(COLUMNNAME_M_AttributeSetInstance_ID)
				.map(attributeSetInstanceTable::getId)
				.ifPresent(asiId -> bomLine.setM_AttributeSetInstance_ID(asiId.getRepoId()));

		saveRecord(bomLine);

		row.getAsOptionalIdentifier().ifPresent(identifier -> productBOMLineTable.putOrReplace(identifier, bomLine));
	}

	private void createPP_Product_BOM(@NonNull final DataTableRow row)
	{
		final I_PP_Product_BOMVersions bomVersions = createBOMVersions(row);

		final I_M_Product productRecord = row.getAsIdentifier(I_PP_Product_BOM.COLUMNNAME_M_Product_ID).lookupNotNullIn(productTable);

		final LocalDateTime validFrom = row.getAsOptionalLocalDate(I_PP_Product_BOM.COLUMNNAME_ValidFrom).orElse(DEFAULT_ValidFrom).atStartOfDay();

		final BOMType bomType = row.getAsOptionalEnum(I_PP_Product_BOM.COLUMNNAME_BOMType, BOMType.class).orElse(BOMType.CurrentActive);

		final I_PP_Product_BOM productBOMRecord = getExistingBOM(ProductId.ofRepoId(productRecord.getM_Product_ID()), bomType)
				.orElseGet(() -> newInstance(I_PP_Product_BOM.class));

		productBOMRecord.setM_Product_ID(productRecord.getM_Product_ID());
		productBOMRecord.setC_UOM_ID(productRecord.getC_UOM_ID());
		productBOMRecord.setValue(productRecord.getValue());
		productBOMRecord.setName(productRecord.getName());
		productBOMRecord.setBOMType(bomType.getCode());
		productBOMRecord.setBOMUse(BOMUse.Manufacturing.getCode());
		productBOMRecord.setValidFrom(Timestamp.valueOf(validFrom));
		productBOMRecord.setPP_Product_BOMVersions_ID(bomVersions.getPP_Product_BOMVersions_ID());
		productBOMRecord.setC_DocType_ID(DEFAULT_C_DOCTYPE_ID);
		productBOMRecord.setDateDoc(TimeUtil.asTimestamp(Instant.now()));
		productBOMRecord.setDocStatus(X_PP_Product_BOM.DOCSTATUS_Drafted);

		row.getAsOptionalIdentifier(COLUMNNAME_M_AttributeSetInstance_ID)
				.map(attributeSetInstanceTable::getId)
				.ifPresent(asiId -> productBOMRecord.setM_AttributeSetInstance_ID(asiId.getRepoId()));

		saveRecord(productBOMRecord);

		productRecord.setIsBOM(true);
		productRecord.setIsVerified(true);
		saveRecord(productRecord);

		row.setAdditionalRowIdentifierColumnName("PP_Product_BOM");
		row.getAsOptionalIdentifier()
				.ifPresent(identifier -> productBOMTable.putOrReplace(identifier, productBOMRecord));
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

	private I_PP_Product_BOMVersions createBOMVersions(@NonNull final DataTableRow row)
	{
		final I_PP_Product_BOMVersions existingBOMVersions = row.getAsOptionalIdentifier(I_PP_Product_BOMVersions.COLUMNNAME_PP_Product_BOMVersions_ID)
				.flatMap(productBomVersionsTable::getOptional)
				.orElse(null);
		if (existingBOMVersions != null)
		{
			return existingBOMVersions;
		}

		final I_M_Product productRecord = row.getAsIdentifier(I_PP_Product_BOMVersions.COLUMNNAME_M_Product_ID).lookupNotNullIn(productTable);

		final I_PP_Product_BOMVersions bomVersionsRecord = CoalesceUtil.coalesceSuppliersNotNull(
				() -> queryBL.createQueryBuilder(I_PP_Product_BOMVersions.class)
						.addEqualsFilter(I_PP_Product_BOMVersions.COLUMNNAME_M_Product_ID, productRecord.getM_Product_ID())
						.create()
						.firstOnly(I_PP_Product_BOMVersions.class),
				() -> createNewBomVersion(productRecord));

		row.getAsOptionalIdentifier(I_PP_Product_BOMVersions.COLUMNNAME_PP_Product_BOMVersions_ID)
				.ifPresent(identifier -> productBomVersionsTable.putOrReplace(identifier, bomVersionsRecord));

		return bomVersionsRecord;
	}

	private static I_PP_Product_BOMVersions createNewBomVersion(final I_M_Product productRecord)
	{
		final I_PP_Product_BOMVersions bomVersionsRecord = newInstance(I_PP_Product_BOMVersions.class);
		bomVersionsRecord.setM_Product_ID(productRecord.getM_Product_ID());
		bomVersionsRecord.setName(productRecord.getName());

		saveRecord(bomVersionsRecord);
		return bomVersionsRecord;
	}
}