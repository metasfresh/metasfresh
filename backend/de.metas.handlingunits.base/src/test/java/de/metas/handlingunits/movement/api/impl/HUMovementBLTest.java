package de.metas.handlingunits.movement.api.impl;

import de.metas.acct.AcctSchemaTestHelper;
import de.metas.acct.api.AcctSchemaId;
import de.metas.handlingunits.model.I_M_MovementLine;
import de.metas.handlingunits.movement.api.IHUMovementBL;
import de.metas.organization.OrgId;
import de.metas.product.IProductActivityProvider;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.util.Services;
import lombok.Builder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests {@link HUMovementBL}.
 *
 * @author RC
 */
public class HUMovementBLTest
{
	/**
	 * Service under test
	 */
	private HUMovementBL huMovementBL;

	private IContextAware context;
	private static final AcctSchemaId acctSchemaId = AcctSchemaId.ofRepoId(1);

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		Env.setClientId(Env.getCtx(), ClientId.METASFRESH);
		AcctSchemaTestHelper.registerAcctSchemaDAOWhichAlwaysProvides(acctSchemaId);

		context = PlainContextAware.newOutOfTrx();

		//
		// Service under test
		huMovementBL = (HUMovementBL)Services.get(IHUMovementBL.class);
	}

	@Test
	public void test_setPackingMaterialCActivity()
	{
		final ProductId productId = ProductId.ofRepoId(111);
		final I_M_MovementLine movementLine = movementLine().orgId(OrgId.MAIN).productId(productId).locatorActivityId(ActivityId.ofRepoId(201)).build();

		final IProductActivityProvider productActivityProvider = Mockito.mock(IProductActivityProvider.class);
		Mockito.doReturn(ActivityId.ofRepoId(678)).when(productActivityProvider).getActivityForAcct(ClientId.METASFRESH, OrgId.MAIN, productId);
		Services.registerService(IProductActivityProvider.class, productActivityProvider);

		huMovementBL.setPackingMaterialCActivity(movementLine);

		assertThat(movementLine.getC_Activity_ID()).isEqualTo(678);
	}

	@Builder(builderMethodName = "movementLine", builderClassName = "$MovementLineBuilder")
	private I_M_MovementLine createMovementLine(
			final OrgId orgId,
			final ProductId productId,
			final ActivityId locatorActivityId)
	{
		final I_M_Locator locatorTo = createLocator(locatorActivityId);

		final I_M_MovementLine movementLine = InterfaceWrapperHelper.newInstance(I_M_MovementLine.class, context);
		movementLine.setM_Product_ID(productId.getRepoId());
		movementLine.setM_LocatorTo_ID(locatorTo.getM_Locator_ID());
		movementLine.setAD_Org_ID(orgId.getRepoId());
		InterfaceWrapperHelper.save(movementLine);

		return movementLine;
	}

	private I_M_Locator createLocator(final ActivityId activityId)
	{
		final I_M_Warehouse warehouseDest = InterfaceWrapperHelper.newInstance(I_M_Warehouse.class, context);
		warehouseDest.setC_Activity_ID(activityId.getRepoId());
		InterfaceWrapperHelper.save(warehouseDest);

		final I_M_Locator locatorTo = InterfaceWrapperHelper.newInstance(I_M_Locator.class, context);
		locatorTo.setM_Warehouse_ID(warehouseDest.getM_Warehouse_ID());
		InterfaceWrapperHelper.save(locatorTo);
		return locatorTo;
	}
}
