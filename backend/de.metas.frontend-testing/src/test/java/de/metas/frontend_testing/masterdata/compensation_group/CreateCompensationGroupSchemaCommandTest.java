package de.metas.frontend_testing.masterdata.compensation_group;

import com.google.common.collect.ImmutableList;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.frontend_testing.masterdata.product.CreateProductCommand;
import de.metas.frontend_testing.masterdata.product.JsonCreateProductRequest;
import de.metas.frontend_testing.masterdata.product.JsonCreateProductResponse;
import de.metas.order.compensationGroup.GroupTemplateId;
import de.metas.order.model.I_C_CompensationGroup_Schema;
import de.metas.order.model.I_C_CompensationGroup_Schema_TemplateLine;
import de.metas.product.ProductId;
import de.metas.product.ProductRepository;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static de.metas.util.Services.get;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies the {@link CreateCompensationGroupSchemaCommand} builder used by the frontend-testing REST API:
 * <ul>
 *   <li>a {@code C_CompensationGroup_Schema} row is created with the requested name,</li>
 *   <li>each template line is persisted as a {@code C_CompensationGroup_Schema_TemplateLine} with the right
 *       {@code IsWithoutCharge} flag,</li>
 *   <li>a product can be linked to the new schema via {@code M_Product.C_CompensationGroup_Schema_ID}.</li>
 * </ul>
 */
public class CreateCompensationGroupSchemaCommandTest
{
	private MasterdataContext context;
	private ProductRepository productRepository;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		context = new MasterdataContext();
		productRepository = new ProductRepository();
	}

	@Test
	public void execute_createsSchemaAndTemplateLines_andLinksTriggerProduct()
	{
		// given: two bundle-member products
		final JsonCreateProductResponse member1 = createProduct("MEMBER_1");
		final JsonCreateProductResponse member2 = createProduct("MEMBER_2");

		// and: a trigger product
		final JsonCreateProductResponse trigger = createProduct("TRIGGER");

		// when: build a schema with 2 template lines (one without charge, one normal)
		final JsonCompensationGroupSchemaRequest schemaRequest = JsonCompensationGroupSchemaRequest.builder()
				.name("Bundle Schema A")
				.templateLines(ImmutableList.of(
						JsonCompensationGroupSchemaTemplateLine.builder()
								.product(Identifier.ofString("member1"))
								.qty(new BigDecimal("1"))
								.isWithoutCharge(true)
								.build(),
						JsonCompensationGroupSchemaTemplateLine.builder()
								.product(Identifier.ofString("member2"))
								.qty(new BigDecimal("2"))
								.isWithoutCharge(false)
								.isAllowSeparateInvoicing(true)
								.build()))
				.build();

		final JsonCompensationGroupSchemaResponse response = CreateCompensationGroupSchemaCommand.builder()
				.context(context)
				.request(schemaRequest)
				.identifier(Identifier.ofString("schemaA"))
				.build()
				.execute();

		// then: schema row exists with the expected name
		assertThat(response).isNotNull();
		assertThat(response.getName()).isEqualTo("Bundle Schema A");
		assertThat(response.getTemplateLineCount()).isEqualTo(2);

		final GroupTemplateId schemaId = response.getId();
		final I_C_CompensationGroup_Schema schemaRecord = InterfaceWrapperHelper.load(schemaId, I_C_CompensationGroup_Schema.class);
		assertThat(schemaRecord).isNotNull();
		assertThat(schemaRecord.getName()).isEqualTo("Bundle Schema A");
		assertThat(schemaRecord.isActive()).isTrue();

		// and: the schema identifier is registered in the context
		final GroupTemplateId resolved = context.getId(Identifier.ofString("schemaA"), GroupTemplateId.class);
		assertThat(resolved).isEqualTo(schemaId);

		// and: 2 template line rows exist with the right flags + qty
		final List<I_C_CompensationGroup_Schema_TemplateLine> lines = get(IQueryBL.class)
				.createQueryBuilder(I_C_CompensationGroup_Schema_TemplateLine.class, Env.getCtx(), null)
				.addEqualsFilter(I_C_CompensationGroup_Schema_TemplateLine.COLUMNNAME_C_CompensationGroup_Schema_ID, schemaId.getRepoId())
				.orderBy(I_C_CompensationGroup_Schema_TemplateLine.COLUMNNAME_SeqNo)
				.create()
				.list();
		assertThat(lines).hasSize(2);

		assertThat(lines.get(0).getM_Product_ID()).isEqualTo(member1.getId().getRepoId());
		assertThat(lines.get(0).getQty()).isEqualByComparingTo(new BigDecimal("1"));
		assertThat(lines.get(0).isWithoutCharge()).isTrue();
		assertThat(lines.get(0).isAllowSeparateInvoicing()).isFalse();

		assertThat(lines.get(1).getM_Product_ID()).isEqualTo(member2.getId().getRepoId());
		assertThat(lines.get(1).getQty()).isEqualByComparingTo(new BigDecimal("2"));
		assertThat(lines.get(1).isWithoutCharge()).isFalse();
		assertThat(lines.get(1).isAllowSeparateInvoicing()).isTrue();

		// and: linking a trigger product to the schema works
		final ProductId triggerId = trigger.getId();
		final I_M_Product triggerRecord = InterfaceWrapperHelper.load(triggerId, I_M_Product.class);
		triggerRecord.setC_CompensationGroup_Schema_ID(schemaId.getRepoId());
		InterfaceWrapperHelper.save(triggerRecord);

		final I_M_Product reloaded = InterfaceWrapperHelper.load(triggerId, I_M_Product.class);
		assertThat(reloaded.getC_CompensationGroup_Schema_ID()).isEqualTo(schemaId.getRepoId());
	}

	@Test
	public void execute_withoutTemplateLines_succeedsWithEmptyCount()
	{
		final JsonCompensationGroupSchemaRequest request = JsonCompensationGroupSchemaRequest.builder()
				.name("Empty Schema")
				.build();

		final JsonCompensationGroupSchemaResponse response = CreateCompensationGroupSchemaCommand.builder()
				.context(context)
				.request(request)
				.identifier(Identifier.ofString("emptySchema"))
				.build()
				.execute();

		assertThat(response.getName()).isEqualTo("Empty Schema");
		assertThat(response.getTemplateLineCount()).isZero();

		final List<I_C_CompensationGroup_Schema_TemplateLine> lines = get(IQueryBL.class)
				.createQueryBuilder(I_C_CompensationGroup_Schema_TemplateLine.class, Env.getCtx(), null)
				.addEqualsFilter(I_C_CompensationGroup_Schema_TemplateLine.COLUMNNAME_C_CompensationGroup_Schema_ID, response.getId().getRepoId())
				.create()
				.list();
		assertThat(lines).isEmpty();
	}

	private JsonCreateProductResponse createProduct(final String value)
	{
		final String identifier = value.toLowerCase().replace("_", "");
		final JsonCreateProductRequest request = JsonCreateProductRequest.builder()
				.value(value)
				.name(value)
				.build();

		return CreateProductCommand.builder()
				.productRepository(productRepository)
				.context(context)
				.request(request)
				.identifier(Identifier.ofString(identifier))
				.build()
				.execute();
	}
}
