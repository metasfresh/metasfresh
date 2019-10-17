package de.metas.materialtracking;

import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.api.IParams;

import de.metas.materialtracking.model.I_M_Material_Tracking;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

/**
 * Used as parameter in {@link IMaterialTrackingBL#linkModelToMaterialTracking(MTLinkRequest)} and also passed to the registered {@link IMaterialTrackingListener}s.<br>
 * Use the {@link #builder()} method to get your own instance.
 *
 */
@Value
@Builder(toBuilder = true)
public class MTLinkRequest
{
	/** What to do if the model is already linked to a different material tracking */
	public enum IfModelAlreadyLinked
	{
		/** If the model is already linked an exception is thrown. */
		FAIL,

		/**
		 * If the model is already linked, it unassigns the model from old material tracking.
		 */
		UNLINK_FROM_PREVIOUS,

		ADD_ADDITIONAL_LINK
	}

	@Default
	IfModelAlreadyLinked ifModelAlreadyLinked = IfModelAlreadyLinked.UNLINK_FROM_PREVIOUS;

	/** Required if {@link IfModelAlreadyLinked#UNLINK_FROM_PREVIOUS}, if there are more than one previously linked material trackings */
	Integer previousMaterialTrackingId;

	@NonNull
	Object model;

	@Default
	IParams params = IParams.NULL;

	@NonNull
	I_M_Material_Tracking materialTrackingRecord;

	/** Can be set if this request is about linking a PP_Order. Note that unfortunately there is no UOM-field; The UOM is implicitly the product's stocking-UOM. */
	BigDecimal qtyIssued;

	/**
	 * Convenience method that uses the {@link InterfaceWrapperHelper} to get the mode as an instance of the given <code>clazz</code>.
	 */
	public <T> T getModel(final Class<T> clazz)
	{
		return InterfaceWrapperHelper.create(model, clazz);
	}
}
