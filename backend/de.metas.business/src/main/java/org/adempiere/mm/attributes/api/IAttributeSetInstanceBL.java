package org.adempiere.mm.attributes.api;

import com.google.common.base.Predicates;
import de.metas.product.ProductId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.impl.AddAttributesRequest;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;

import javax.annotation.Nullable;
import java.util.function.Predicate;

/**
 * Service to create and update AttributeInstances and AttributeSetInstances.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public interface IAttributeSetInstanceBL extends ISingletonService
{
	/**
	 * Call {@link #buildDescription(I_M_AttributeSetInstance, boolean)} with verbose = false.
	 */
	String buildDescription(I_M_AttributeSetInstance asi);

	/**
	 * Build ASI Description
	 * <p>
	 * e.g. - Product Values - Instance Values
	 *
	 * @param asi may be {@code null}; in that case, an empty string is returned
	 */
	String buildDescription(I_M_AttributeSetInstance asi, boolean verboseDescription);

	/**
	 * Builds and set {@link I_M_AttributeSetInstance#COLUMNNAME_Description}.
	 *
	 * @param asi may be {@code null}; in that case, nothing is done;
	 */
	void setDescription(I_M_AttributeSetInstance asi);

	/**
	 * Creates and saves a new "empty" ASI based on the given product's attribute set.
	 *
	 * @return newly created and saved ASI; never return null
	 */
	I_M_AttributeSetInstance createASI(ProductId productId);

	/**
	 * Get an existing Attribute Set Instance, create a new one if none exists yet.
	 * <p>
	 * In case a new ASI is created, it will be saved and also set to ASI aware ({@link IAttributeSetInstanceAware#setM_AttributeSetInstance(I_M_AttributeSetInstance)}).
	 *
	 * @return existing ASI/newly created ASI
	 */
	I_M_AttributeSetInstance getCreateASI(IAttributeSetInstanceAware asiAware);

	/**
	 * Get/Create {@link I_M_AttributeInstance} for given <code>asiId</code>. If a new ai is created, it is also saved.
	 *
	 * @return attribute instance; never return null
	 */
	I_M_AttributeInstance getCreateAttributeInstance(AttributeSetInstanceId asiId, AttributeId attributeId);

	/**
	 * Convenient way to quickly create/update and save an {@link I_M_AttributeInstance} for {@link AttributeListValue}.
	 *
	 * @param attributeValue attribute value to set; must be not null
	 * @return created/updated attribute instance
	 */
	I_M_AttributeInstance getCreateAttributeInstance(AttributeSetInstanceId asiId, AttributeListValue attributeValue);

	/**
	 * If both the given <code>to</code> and <code>from</code> can be converted to {@link IAttributeSetInstanceAware}s and if <code>from</code>'s ASI-aware has an M_AttributeSetInstance,
	 * then that ASI is copied/cloned to the given <code>to</code> and saved.
	 * <p>
	 * Note that <code>to</code> itself is not saved. Also note that any existing ASI which might already be referenced by <code>to</code> is discarded/ignored.
	 *
	 * @see IAttributeSetInstanceAwareFactoryService#createOrNull(Object)
	 */
	void cloneASI(Object to, Object from);

	default I_M_AttributeSetInstance createASIFromAttributeSet(@NonNull final IAttributeSet attributeSet)
	{
		return createASIFromAttributeSet(attributeSet, Predicates.alwaysTrue());
	}

	/**
	 * Clone the ASI from the given {@code from} to the given {@code to}, or just create a new empty ASI in {@code to} in case from is null or "none"
	 */
	void cloneOrCreateASI(@Nullable Object to, @Nullable Object from);

	I_M_AttributeSetInstance createASIFromAttributeSet(@NonNull IAttributeSet attributeSet, Predicate<I_M_Attribute> filter);

	I_M_AttributeSetInstance createASIWithASFromProductAndInsertAttributeSet(ProductId productId, IAttributeSet attributeSet);

	/**
	 * set in {@link I_M_AttributeInstance} the correct value for given <code>asi</code> and given <code>attribute</code>
	 * <br>
	 * the ai is also saved.
	 */
	void setAttributeInstanceValue(AttributeSetInstanceId asiId, AttributeId attributeId, Object value);

	/**
	 * Similar to {@link #setAttributeInstanceValue(AttributeSetInstanceId, AttributeId, Object)},
	 * but the {@link AttributeId} is loaded from the given {@code attributeCode}.
	 */
	void setAttributeInstanceValue(AttributeSetInstanceId asiId, AttributeCode attributeCode, Object value);

	String getASIDescriptionById(AttributeSetInstanceId asiId);

	void updateASIAttributeFromModel(AttributeCode attributeCode, Object fromModel);

	boolean isStorageRelevant(I_M_AttributeInstance ai);

	ImmutableAttributeSet getImmutableAttributeSetById(AttributeSetInstanceId asiId);

	/**
	 * Synchs the given {@code attributeSet}  to the given {@code asiAware}.
	 * <p/>
	 * Creates a new ASI if neccessary.
	 * Existing attribute instances that are not part of the given {@code attributeSet} are left untouched.
	 * {@code null}-Values from the given {@code attributeSet} are also synched to the given {@code asiAware}.
	 * The given {@code asiAware} is <b>not</b> saved, but the the respective ASI and AIs are saved
	 */
	void syncAttributesToASIAware(IAttributeSet attributeSet, IAttributeSetInstanceAware asiAware);

	AttributeSetInstanceId addAttributes(AddAttributesRequest addAttributesRequest);

	String getAttributeValueOrNull(@NonNull AttributeCode attributeCode, @NonNull AttributeSetInstanceId asiId);
}
