package de.metas.material.planning;

import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_S_Resource;
import org.eevolution.model.I_PP_Product_Planning;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.Date;
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

	ResourceId getPlantId();

	OrgId getOrgId();
	void setOrgId(@NonNull OrgId orgId);

	@Nullable WarehouseId getWarehouseId();
	void setWarehouseId(@Nullable WarehouseId warehouseId);

	@Nullable ProductId getProductId();
	void setProductId(ProductId productId);

	AttributeSetInstanceId getAttributeSetInstanceId();
	void setAttributeSetInstanceId(@NonNull AttributeSetInstanceId attributeSetInstanceId);
	//@formatter:on

	Date getDate();

	Timestamp getDateAsTimestamp();

	ProductPlanning getProductPlanning();

	void setProductPlanning(I_PP_Product_Planning productPlanningRecord);

	void assertContextConsistent();

	I_PP_Product_Planning getPpOrderProductPlanning();

	void setPpOrderProductPlanning(@Nullable I_PP_Product_Planning ppOrderProductPlanning);
}
