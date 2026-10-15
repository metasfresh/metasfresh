package de.metas.order.compensationGroup.calibration;

import com.google.common.collect.ImmutableList;
import de.metas.order.compensationGroup.GroupTemplate;
import de.metas.order.compensationGroup.GroupTemplateRegularLine;
import de.metas.order.compensationGroup.GroupTemplateRegularLineId;
import de.metas.order.model.I_C_CompensationGroup_CalibrationRule;
import de.metas.order.model.I_C_CompensationGroup_Schema;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.lang.Percent;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("OptionalGetWithoutIsPresent")
class CompensationGroupCalibrationServiceTest
{
	private CompensationGroupCalibrationService service;
	private I_C_UOM uom;
	private int bpartnerId;
	private int lineSeq = 0;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		service = new CompensationGroupCalibrationService(CompensationGroupCalibrationRuleRepository.newInstanceForUnitTesting());

		uom = newInstance(I_C_UOM.class);
		uom.setStdPrecision(2);
		saveRecord(uom);

		final I_C_BP_Group bpGroup = newInstance(I_C_BP_Group.class);
		saveRecord(bpGroup);
		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		bpartner.setC_BP_Group_ID(bpGroup.getC_BP_Group_ID());
		saveRecord(bpartner);
		bpartnerId = bpartner.getC_BPartner_ID();
	}

	private I_C_Order order(final boolean soTrx)
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setIsSOTrx(soTrx);
		order.setC_BPartner_ID(bpartnerId);
		saveRecord(order);
		return order;
	}

	private ProductId product(final boolean isMenu)
	{
		final I_M_Product_Category category = newInstance(I_M_Product_Category.class);
		saveRecord(category);
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setM_Product_Category_ID(category.getM_Product_Category_ID());
		product.setC_UOM_ID(uom.getC_UOM_ID());
		if (isMenu)
		{
			final I_C_CompensationGroup_Schema schema = newInstance(I_C_CompensationGroup_Schema.class);
			saveRecord(schema);
			product.setC_CompensationGroup_Schema_ID(schema.getC_CompensationGroup_Schema_ID());
		}
		saveRecord(product);
		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	private I_C_CompensationGroup_CalibrationRule rule(final ProductId productId, final String factor)
	{
		final I_C_CompensationGroup_CalibrationRule rule = newInstance(I_C_CompensationGroup_CalibrationRule.class);
		rule.setSeqNo(10);
		rule.setC_BPartner_ID(bpartnerId);
		rule.setM_Product_ID(productId.getRepoId());
		rule.setGroupCompensationCalibrationFactor(new BigDecimal(factor));
		saveRecord(rule);
		return rule;
	}

	private GroupTemplateRegularLine line(final ProductId productId, final String qty)
	{
		return GroupTemplateRegularLine.builder()
				.id(GroupTemplateRegularLineId.ofRepoId(++lineSeq))
				.productId(productId)
				.qty(Quantity.of(new BigDecimal(qty), uom))
				.build();
	}

	private static GroupTemplate template(final GroupTemplateRegularLine... lines)
	{
		return GroupTemplate.builder().name("t").regularLinesToAdd(ImmutableList.copyOf(lines)).build();
	}

	@Test
	void purchaseOrder_isNotCalibrated()
	{
		final ProductId p = product(false);
		rule(p, "50");
		assertThat(service.computeCalibrations(order(false), template(line(p, "100")))).isSameAs(GroupCalibrations.NONE);
	}

	@Test
	void noMatchingRule_noCalibration()
	{
		final ProductId p = product(false);
		final GroupTemplateRegularLine l = line(p, "100");
		// no rule matched: there is no calibration at all, the line is created like an uncalibrated one
		assertThat(service.computeCalibrations(order(true), template(l))).isSameAs(GroupCalibrations.NONE);
	}

	@Test
	void purchaseOrder_noCalibrationWithoutRule()
	{
		final ProductId p = product(false);
		final GroupTemplateRegularLine l = line(p, "100");
		assertThat(service.computeCalibrations(order(false), template(l)).getByTemplateLineId(l.getId())).isEmpty();
	}

	@Test
	void matchingRule_scalesAndStoresRule()
	{
		final ProductId p = product(false);
		final I_C_CompensationGroup_CalibrationRule rule = rule(p, "50");
		final GroupTemplateRegularLine l = line(p, "100");
		final CalibrationRule c = service.computeCalibrations(order(true), template(l)).getByTemplateLineId(l.getId()).get();
		assertThat(c.getFactor()).isEqualTo(Percent.of(50));
		// 100 x menu qty 2 = 200 (uncalibrated), x 50 % = 100
		assertThat(c.computeQtyCalibrated(Quantity.of(new BigDecimal("200"), uom)).toBigDecimal()).isEqualByComparingTo("100");
		assertThat(c.getId()).isEqualTo(CalibrationRuleId.ofRepoId(rule.getC_CompensationGroup_CalibrationRule_ID()));
	}

	@Test
	void factorZero_qtyZeroAndStoresRule()
	{
		final ProductId p = product(false);
		final I_C_CompensationGroup_CalibrationRule rule = rule(p, "0");
		final GroupTemplateRegularLine l = line(p, "100");
		final CalibrationRule c = service.computeCalibrations(order(true), template(l)).getByTemplateLineId(l.getId()).get();
		assertThat(c.getFactor()).isEqualTo(Percent.ZERO);
		assertThat(c.computeQtyCalibrated(Quantity.of(new BigDecimal("100"), uom)).toBigDecimal()).isEqualByComparingTo("0");
		assertThat(c.getId()).isEqualTo(CalibrationRuleId.ofRepoId(rule.getC_CompensationGroup_CalibrationRule_ID()));
	}

	@Test
	void menuLine_isNotCalibratedEvenWithMatchingRule()
	{
		final ProductId menu = product(true);
		rule(menu, "50");
		final GroupTemplateRegularLine l = line(menu, "1");
		assertThat(service.computeCalibrations(order(true), template(l)).getByTemplateLineId(l.getId())).isEmpty();
	}
}
