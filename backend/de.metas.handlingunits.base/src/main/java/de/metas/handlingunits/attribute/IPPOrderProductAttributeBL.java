package de.metas.handlingunits.attribute;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Cost_Collector;
import de.metas.handlingunits.model.I_PP_Order_ProductAttribute;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import de.metas.util.ISingletonService;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Collection;

public interface IPPOrderProductAttributeBL extends ISingletonService
{

	/**
	 * Transfer PP_Order's attributes (see {@link I_PP_Order_ProductAttribute}) to
	 * <ul>
	 * <li>given HUs
	 * <li>all already received HUs
	 * </ul>
	 * only if the HUs is for a MainProduct
	 *
	 * @param coByProductOrderBOMLineId null if this is the MainProduct, non-null if this is a By, Co, or anything else Product
	 */
	void updateHUAttributes(@NonNull Collection<I_M_HU> hus, @NonNull PPOrderId fromPPOrderId, @Nullable PPOrderBOMLineId coByProductOrderBOMLineId);

	/**
	 * Create new PP_Order_ProductAttribute entries for the given cost collector
	 */
	void addPPOrderProductAttributes(I_PP_Cost_Collector costCollector);

	/**
	 * Create new PP_Order_ProductAttribute entries for the given Issue Candidate
	 */
	void addPPOrderProductAttributesFromIssueCandidate(I_PP_Order_Qty issueCandidate);
}
