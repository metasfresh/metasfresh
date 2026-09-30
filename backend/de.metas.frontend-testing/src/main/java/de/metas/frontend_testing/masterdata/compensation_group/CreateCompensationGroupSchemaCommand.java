package de.metas.frontend_testing.masterdata.compensation_group;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.order.compensationGroup.GroupTemplateId;
import de.metas.order.model.I_C_CompensationGroup_Schema;
import de.metas.order.model.I_C_CompensationGroup_Schema_TemplateLine;
import de.metas.product.ProductId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.util.List;

/**
 * Creates one {@link I_C_CompensationGroup_Schema} plus its regular template lines
 * from a {@link JsonCompensationGroupSchemaRequest}.
 * <p>
 * Persistence is done directly via {@link InterfaceWrapperHelper} — same pattern as the other
 * low-level masterdata builders in this module (e.g. {@code CreateShipperCommand}). This avoids
 * pulling in the higher-level {@code GroupTemplateRepository} (which is geared toward read access
 * and loads full {@code GroupTemplate} aggregates).
 */
@Builder
public class CreateCompensationGroupSchemaCommand
{
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	@NonNull private final MasterdataContext context;
	@NonNull private final JsonCompensationGroupSchemaRequest request;
	@NonNull private final Identifier identifier;

	public JsonCompensationGroupSchemaResponse execute()
	{
		final String name = request.getName() != null ? request.getName() : identifier.toUniqueString();

		final I_C_CompensationGroup_Schema schema = InterfaceWrapperHelper.newInstance(I_C_CompensationGroup_Schema.class);
		schema.setAD_Org_ID(MasterdataContext.ORG_ID.getRepoId());
		schema.setName(name);
		schema.setIsActive(true);
		if (Boolean.TRUE.equals(request.getIsInheritPackingInstruction()))
		{
			schema.setIsInheritPackingInstruction(true);
		}
		InterfaceWrapperHelper.save(schema);

		final GroupTemplateId schemaId = GroupTemplateId.ofRepoId(schema.getC_CompensationGroup_Schema_ID());
		context.putIdentifier(identifier, schemaId);

		final List<JsonCompensationGroupSchemaTemplateLine> lines = request.getTemplateLines();
		final int lineCount = lines == null ? 0 : lines.size();
		if (lines != null)
		{
			int seqNo = 10;
			for (final JsonCompensationGroupSchemaTemplateLine line : lines)
			{
				createTemplateLine(schemaId, line, seqNo);
				seqNo += 10;
			}
		}

		return JsonCompensationGroupSchemaResponse.builder()
				.id(schemaId)
				.name(name)
				.templateLineCount(lineCount)
				.build();
	}

	private void createTemplateLine(
			@NonNull final GroupTemplateId schemaId,
			@NonNull final JsonCompensationGroupSchemaTemplateLine line,
			final int seqNo)
	{
		final ProductId productId = context.getId(line.getProduct(), ProductId.class);
		final UomId uomId;
		if (line.getUom() != null)
		{
			uomId = uomDAO.getUomIdByX12DE355(line.getUom());
		}
		else
		{
			// Read the stock UOM directly from the product record to avoid an extra C_UOM lookup
			// (the product builder writes M_Product.C_UOM_ID; that's the authoritative stock UOM).
			final I_M_Product productRecord = InterfaceWrapperHelper.load(productId, I_M_Product.class);
			uomId = UomId.ofRepoId(productRecord.getC_UOM_ID());
		}

		final I_C_CompensationGroup_Schema_TemplateLine record = InterfaceWrapperHelper.newInstance(I_C_CompensationGroup_Schema_TemplateLine.class);
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
		InterfaceWrapperHelper.save(record);
	}
}
