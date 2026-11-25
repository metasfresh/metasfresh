package de.metas.handlingunits.attribute;

import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.javaclasses.JavaClassId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeId;

import javax.annotation.Nullable;

/**
 * A new, active {@code M_HU_PI_Attribute} of one packing-instruction version.
 */
@Value
@Builder
public class HUPIAttributeCreateRequest
{
	@NonNull HuPackingInstructionsVersionId piVersionId;
	@NonNull AttributeId attributeId;
	/** one of the {@code M_HU_PI_Attribute.PropagationType} reference-list codes, see {@code X_M_HU_PI_Attribute.PROPAGATIONTYPE_*} */
	@NonNull String propagationType;
	/** {@code M_HU_PI_Attribute.HU_TansferStrategy_JavaClass_ID} */
	@Nullable JavaClassId transferStrategyJavaClassId;
	boolean displayed;
	boolean readOnly;
	boolean onlyIfInProductAttributeSet;
	boolean useInASI;
}
