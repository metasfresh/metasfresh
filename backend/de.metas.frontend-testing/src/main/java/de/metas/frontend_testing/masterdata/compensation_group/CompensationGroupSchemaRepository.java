package de.metas.frontend_testing.masterdata.compensation_group;

import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.order.compensationGroup.GroupTemplateId;
import de.metas.order.model.I_C_CompensationGroup_Schema;
import de.metas.order.model.I_C_CompensationGroup_SchemaLine;
import de.metas.order.model.I_C_CompensationGroup_Schema_TemplateLine;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Writes the compensation-group schema records requested by {@link CreateCompensationGroupSchemaCommand}.
 */
class CompensationGroupSchemaRepository
{
	@NonNull
	GroupTemplateId createSchema(@NonNull final String name, final boolean isInheritPackingInstruction)
	{
		final I_C_CompensationGroup_Schema record = newInstance(I_C_CompensationGroup_Schema.class);
		record.setAD_Org_ID(MasterdataContext.ORG_ID.getRepoId());
		record.setName(name);
		record.setIsActive(true);
		record.setIsInheritPackingInstruction(isInheritPackingInstruction);
		saveRecord(record);

		return GroupTemplateId.ofRepoId(record.getC_CompensationGroup_Schema_ID());
	}

	void createTemplateLine(
			@NonNull final GroupTemplateId schemaId,
			@NonNull final ProductId productId,
			@NonNull final UomId uomId,
			final int seqNo,
			@NonNull final JsonCompensationGroupSchemaTemplateLine line)
	{
		final I_C_CompensationGroup_Schema_TemplateLine record = newInstance(I_C_CompensationGroup_Schema_TemplateLine.class);
		record.setAD_Org_ID(MasterdataContext.ORG_ID.getRepoId());
		record.setC_CompensationGroup_Schema_ID(schemaId.getRepoId());
		record.setM_Product_ID(productId.getRepoId());
		record.setC_UOM_ID(uomId.getRepoId());
		record.setQty(line.getQty() != null ? line.getQty() : BigDecimal.ZERO);
		record.setSeqNo(seqNo);
		record.setIsActive(true);
		record.setIsWithoutCharge(Boolean.TRUE.equals(line.getIsWithoutCharge()));
		record.setIsAllowSeparateInvoicing(Boolean.TRUE.equals(line.getIsAllowSeparateInvoicing()));
		record.setIsHideWhenPrinting(Boolean.TRUE.equals(line.getIsHideWhenPrinting()));
		saveRecord(record);
	}

	void createCompensationLine(
			@NonNull final GroupTemplateId schemaId,
			@NonNull final ProductId productId,
			@Nullable final BigDecimal percentage,
			final int seqNo)
	{
		final I_C_CompensationGroup_SchemaLine record = newInstance(I_C_CompensationGroup_SchemaLine.class);
		record.setAD_Org_ID(MasterdataContext.ORG_ID.getRepoId());
		record.setC_CompensationGroup_Schema_ID(schemaId.getRepoId());
		record.setM_Product_ID(productId.getRepoId());
		record.setCompleteOrderDiscount(percentage);
		record.setSeqNo(seqNo);
		record.setIsActive(true);
		saveRecord(record);
	}
}
