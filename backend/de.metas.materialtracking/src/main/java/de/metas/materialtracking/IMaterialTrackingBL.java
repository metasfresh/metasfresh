package de.metas.materialtracking;

import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.I_M_Material_Tracking_Ref;
import de.metas.util.ISingletonService;
import org.compiere.model.X_C_DocType;

public interface IMaterialTrackingBL extends ISingletonService
{
	String C_DocType_INVOICE_DOCSUBTYPE_QI_DownPayment = X_C_DocType.DOCSUBTYPE_DownPayment;

	String C_DocType_INVOICE_DOCSUBTYPE_QI_FinalSettlement = X_C_DocType.DOCSUBTYPE_QualityInspection;

	String C_DocType_PPORDER_DOCSUBTYPE_QualityInspection = X_C_DocType.DOCSUBTYPE_QualityInspection;

	/**
	 * @return true if material tracking module is enabled
	 */
	boolean isEnabled();

	/**
	 * Enable/Disable material tracking module.
	 *
	 * NOTE: this is just a flag, it won't prevent anything but depending functionalities can decide if they shall perform or not, based on this flag.
	 */
	void setEnabled(boolean enabled);

	/**
	 * Registers material tracking listener for given table name.
	 *
	 * When a model from given table name is linked or unlinked to a material tracking this listener will be called.
	 */
	void addModelTrackingListener(String tableName, IMaterialTrackingListener listener);

	/**
	 * Link given model to a material tracking record. The beforeModelLinked() and afterModelLinked() methods of all registered {@link IMaterialTrackingListener}s will be called before resp. after the
	 * new {@link I_M_Material_Tracking_Ref} record is saved.
	 *
	 * If model was previously assigned to another material tracking and {@link MTLinkRequest#isAssumeNotAlreadyAssigned()} is <code>false</code>, then it will be unlinked first.
	 */
	void linkModelToMaterialTracking(MTLinkRequest req);

	/**
	 * Unlink given model from ANY material tracking.
	 *
	 * @return <code>true</code> if there was a <code>M_MaterialTracking_Ref</code> to delete.
	 */
	boolean unlinkModelFromMaterialTrackings(Object model);

	/**
	 * Unlink given given <code>model</code> from the given <code>materialTracking </code>, if such a link exists.
	 *
	 * @return <code>true</code> if there was a <code>M_MaterialTracking_Ref</code> to delete.
	 */
	boolean unlinkModelFromMaterialTrackings(Object model, I_M_Material_Tracking materialTracking);

}
