package de.metas.material.planning;

import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.util.Properties;

/**
 * MRP working context.
 * <p>
 * It will continuously get updated by MRP classes while they perform. See it as a work-file.
 *
 * @author tsa
 */
public interface IMaterialPlanningContext extends IContextAware
{
	//
	// Context
	@Override
	Properties getCtx();

	void setCtx(Properties ctx);

	@Override
	String getTrxName();

	void setTrxName(String trxName);

	//
	// Planning Segment
	//@formatter:off
	ClientId getClientId();

	@Nullable ResourceId getPlantId();
	void setPlantId(@Nullable ResourceId plantId);

	void setClientId(@NonNull ClientId clientId);

	OrgId getOrgId();
	void setOrgId(@NonNull OrgId orgId);

	@Nullable WarehouseId getWarehouseId();
	void setWarehouseId(@Nullable WarehouseId warehouseId);

	@Nullable ProductId getProductId();
	void setProductId(ProductId productId);

	AttributeSetInstanceId getAttributeSetInstanceId();
	void setAttributeSetInstanceId(@NonNull AttributeSetInstanceId attributeSetInstanceId);
	//@formatter:on

	ProductPlanning getProductPlanning();

	void setProductPlanning(ProductPlanning productPlanningRecord);

	void assertContextConsistent();

	ProductPlanning getPpOrderProductPlanning();

	void setPpOrderProductPlanning(@Nullable ProductPlanning ppOrderProductPlanning);
}
