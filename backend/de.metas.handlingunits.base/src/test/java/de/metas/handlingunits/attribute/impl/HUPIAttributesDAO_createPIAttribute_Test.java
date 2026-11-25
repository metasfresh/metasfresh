package de.metas.handlingunits.attribute.impl;

import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.HUPIAttributeCreateRequest;
import de.metas.handlingunits.attribute.IHUPIAttributesDAO;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import de.metas.handlingunits.model.X_M_HU_PI_Attribute;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.javaclasses.JavaClassId;
import de.metas.util.Services;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributesTestHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link HUPIAttributesDAO#createPIAttribute(HUPIAttributeCreateRequest)}.
 */
public class HUPIAttributesDAO_createPIAttribute_Test extends AbstractHUTest
{
	private IHUPIAttributesDAO piAttributesDAO;
	private AttributeId attributeId;
	private HuPackingInstructionsVersionId piVersionId;

	@Override
	protected void initialize()
	{
		piAttributesDAO = Services.get(IHUPIAttributesDAO.class);
		final I_M_Attribute attribute = new AttributesTestHelper().createM_Attribute("test-attribute", X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40, true);
		attributeId = AttributeId.ofRepoId(attribute.getM_Attribute_ID());
		final I_M_HU_PI lu = helper.createHUDefinition("LU", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		piVersionId = Services.get(IHandlingUnitsDAO.class).retrievePICurrentVersionId(HuPackingInstructionsId.ofRepoId(lu.getM_HU_PI_ID()));
	}

	@Test
	public void createsActiveRecordWithAllRequestedValues()
	{
		piAttributesDAO.createPIAttribute(HUPIAttributeCreateRequest.builder()
				.piVersionId(piVersionId)
				.attributeId(attributeId)
				.propagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_NoPropagation)
				.transferStrategyJavaClassId(JavaClassId.ofRepoId(540027))
				.displayed(true)
				.readOnly(true)
				.onlyIfInProductAttributeSet(true)
				.useInASI(true)
				.build());

		final I_M_HU_PI_Attribute record = piAttributesDAO.retrievePIAttributes(piVersionId).getByAttributeId(attributeId);
		assertThat(record.isActive()).isTrue();
		assertThat(record.getM_HU_PI_Version_ID()).isEqualTo(piVersionId.getRepoId());
		assertThat(record.getPropagationType()).isEqualTo(X_M_HU_PI_Attribute.PROPAGATIONTYPE_NoPropagation);
		assertThat(record.getHU_TansferStrategy_JavaClass_ID()).isEqualTo(540027);
		assertThat(record.isDisplayed()).isTrue();
		assertThat(record.isReadOnly()).isTrue();
		assertThat(record.isOnlyIfInProductAttributeSet()).isTrue();
		assertThat(record.isUseInASI()).isTrue();
	}

	@Test
	public void withoutTransferStrategy_leavesItUnset()
	{
		piAttributesDAO.createPIAttribute(HUPIAttributeCreateRequest.builder()
				.piVersionId(piVersionId)
				.attributeId(attributeId)
				.propagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_NoPropagation)
				.build());

		final I_M_HU_PI_Attribute record = piAttributesDAO.retrievePIAttributes(piVersionId).getByAttributeId(attributeId);
		assertThat(record.getHU_TansferStrategy_JavaClass_ID()).isLessThanOrEqualTo(0);
		assertThat(record.isDisplayed()).isFalse();
	}
}
