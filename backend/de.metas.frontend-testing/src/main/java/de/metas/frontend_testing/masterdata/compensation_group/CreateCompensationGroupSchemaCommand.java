package de.metas.frontend_testing.masterdata.compensation_group;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.order.compensationGroup.GroupTemplateId;
import de.metas.order.model.I_C_CompensationGroup_Schema;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;

import java.util.List;

/**
 * Creates one {@link I_C_CompensationGroup_Schema} plus its regular template lines and its compensation
 * (e.g. percent discount) lines from a {@link JsonCompensationGroupSchemaRequest}.
 * <p>
 * The records are written by {@link CompensationGroupSchemaRepository}. The higher-level
 * {@code GroupTemplateRepository} is not used: it is geared toward read access and loads full
 * {@code GroupTemplate} aggregates.
 */
@Builder
public class CreateCompensationGroupSchemaCommand
{
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final CompensationGroupSchemaRepository schemaRepository = new CompensationGroupSchemaRepository();

	@NonNull private final MasterdataContext context;
	@NonNull private final JsonCompensationGroupSchemaRequest request;
	@NonNull private final Identifier identifier;

	public JsonCompensationGroupSchemaResponse execute()
	{
		final String name = request.getName() != null ? request.getName() : identifier.toUniqueString();

		final GroupTemplateId schemaId = schemaRepository.createSchema(name, Boolean.TRUE.equals(request.getIsInheritPackingInstruction()));
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

		final List<JsonCompensationGroupSchemaCompensationLine> compensationLines = request.getCompensationLines();
		if (compensationLines != null)
		{
			int seqNo = 10;
			for (final JsonCompensationGroupSchemaCompensationLine compensationLine : compensationLines)
			{
				final ProductId productId = context.getId(compensationLine.getProduct(), ProductId.class);
				schemaRepository.createCompensationLine(schemaId, productId, compensationLine.getPercentage(), seqNo);
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
		final UomId uomId = line.getUom() != null
				? uomDAO.getUomIdByX12DE355(line.getUom())
				// the product's stock UOM: M_Product.C_UOM_ID, as written by the product builder
				: UomId.ofRepoId(productBL.getById(productId).getC_UOM_ID());

		schemaRepository.createTemplateLine(schemaId, productId, uomId, seqNo, line);
	}
}
