package de.metas.handlingunits.attribute;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.product.ProductId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.compiere.model.I_M_Attribute;

import javax.annotation.Nullable;
import java.math.BigDecimal;

public interface IHUAttributesBL extends ISingletonService
{

	/**
	 * Gets underlying {@link I_M_HU} from given <code>attributeSet</code>.
	 * <p>
	 * If there is no underlying HU, this method will throw an exception
	 *
	 * @param attributeSet not null
	 * @return underlying HU; never return <code>null</code>
	 * @throws IllegalArgumentException if there is no underlying HU
	 */
	I_M_HU getM_HU(IAttributeSet attributeSet);

	/**
	 * Gets underlying {@link I_M_HU} from given <code>attributeSet</code>.
	 * <p>
	 * If there is no underlying HU, this method will return <code>null</code>.
	 *
	 * @return underlying HU or <code>null</code> if given <code>attributeSet</code> does not support HUs.
	 */
	I_M_HU getM_HU_OrNull(IAttributeSet attributeSet);

	/**
	 * Iterates the HU-tree of the given HU and sets the given attribute to the given attributeValue.
	 *
	 * @param onlyHUStatus may be <code>null</code> or empty. Otherwise, only HUs with the given status are updated. However, all HUs are iterated.
	 * @deprecated
	 */
	@Deprecated
	void updateHUAttributeRecursive(
			I_M_HU hu,
			I_M_Attribute attribute,
			@Nullable Object attributeValue,
			@Nullable String onlyHUStatus);

	/**
	 * Iterates the HU-tree of the given HU and sets the given attribute to the given attributeValue.
	 * <p>
	 * Note: for complex scenarios (distributing a weight onto an HU-tree), see {@link de.metas.handlingunits.attribute.propagation.IHUAttributePropagator} and {@link de.metas.handlingunits.attribute.strategy.IAttributeStrategy}.
	 *
	 * @param onlyHUStatus may be <code>null</code> or empty. Otherwise, only HUs with the given status are updated. However, all HUs are iterated.
	 */
	void updateHUAttributeRecursive(
			@NonNull HuId huId,
			@NonNull AttributeCode attributeId,
			@Nullable Object attributeValue,
			@Nullable String onlyHUStatus);

	/**
	 * @return quality discount percent (between 0...100); never return null
	 */
	BigDecimal getQualityDiscountPercent(I_M_HU hu);

	boolean isAutomaticallySetLotNumber();

	boolean isAutomaticallySetBestBeforeDate();

	void validateMandatoryShipmentAttributes(HuId huId, ProductId productId);

	void validateMandatoryPickingAttributes(HuId huId, ProductId productId);

	boolean areMandatoryPickingAttributesFulfilled(@NonNull HuId huId,
			@NonNull ProductId productId);

	void transferAttributesForSingleProductHUs(@NonNull I_M_HU huFrom, @NonNull I_M_HU huTo);

	void updateHUAttribute(@NonNull HuId huId, @NonNull AttributeCode attributeCode, @Nullable Object attributeValue);
}
