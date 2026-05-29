package de.metas.handlingunits.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static de.metas.business.BusinessTestHelper.createBPartner;

/**
 * Unit test for {@link HandlingUnitsDAO#retrieveParentPIItemsForParentPI(I_M_HU_PI, String, I_C_BPartner)} method.
 *
 * @author tsa
 */
public class HandlingUnitsDAO_retrieveParentPIItemsForParentPI_Test extends AbstractHUTest
{
	protected HandlingUnitsDAO dao;

	private BPartnerId bpartner_NULL;
	private BPartnerId bpartner1;
	private BPartnerId bpartner2;
	private BPartnerId bpartner3;

	private I_M_HU_PI tuPI1;
	private I_M_HU_PI tuPI_withoutLU;

	private I_M_HU_PI luPI;
	private I_M_HU_PI_Item lu_tu1_noBP_item;
	private I_M_HU_PI_Item lu_tu1_bp1_item;
	private I_M_HU_PI_Item lu_tu1_bp2_item;

	@Override
	protected void initialize()
	{
		dao = new HandlingUnitsDAO();
		Services.registerService(IHandlingUnitsDAO.class, dao);

		bpartner_NULL = null;
		bpartner1 = BPartnerId.ofRepoId(createBPartner("BP1").getC_BPartner_ID());
		bpartner2 = BPartnerId.ofRepoId(createBPartner("BP2").getC_BPartner_ID());
		bpartner3 = BPartnerId.ofRepoId(createBPartner("BP3").getC_BPartner_ID());

		tuPI1 = helper.createHUDefinition("TU1", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		tuPI_withoutLU = helper.createHUDefinition("TU_withoutLU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);

		luPI = helper.createHUDefinition("LU", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		lu_tu1_noBP_item = helper.createHU_PI_Item_IncludedHU(luPI, tuPI1, new BigDecimal("10"), bpartner_NULL);
		lu_tu1_bp1_item = helper.createHU_PI_Item_IncludedHU(luPI, tuPI1, new BigDecimal("20"), bpartner1);
		lu_tu1_bp2_item = helper.createHU_PI_Item_IncludedHU(luPI, tuPI1, new BigDecimal("30"), bpartner2);
	}

	@Test
	public void test_NoBPartner()
	{
		assertLUItem(lu_tu1_noBP_item, tuPI1, bpartner_NULL);
	}

	@Test
	public void test_NoBPartner_And_NoDefaultConfiguration()
	{
		// Delete the standard default configuration
		InterfaceWrapperHelper.delete(lu_tu1_noBP_item);

		assertLUItem(null, tuPI1, bpartner_NULL);
	}

	@Test
	public void test_PartnerWithoutParticularConfiguration()
	{
		assertLUItem(lu_tu1_noBP_item, tuPI1, bpartner3);
	}

	@Test
	public void test_PartnerWithoutParticularConfiguration_And_NoDefaultConfiguration()
	{
		// Delete the standard default configuration
		InterfaceWrapperHelper.delete(lu_tu1_noBP_item);

		assertLUItem(null, tuPI1, bpartner3);
	}

	@Test
	public void test_PartnerWithParticularConfiguration()
	{
		assertLUItem(lu_tu1_bp1_item, tuPI1, bpartner1);
		assertLUItem(lu_tu1_bp2_item, tuPI1, bpartner2);
	}

	@Test
	public void test_PartnerWithParticularConfiguration_And_NoDefaultConfiguration()
	{
		// Delete the standard default configuration
		InterfaceWrapperHelper.delete(lu_tu1_noBP_item);

		assertLUItem(lu_tu1_bp1_item, tuPI1, bpartner1);
		assertLUItem(lu_tu1_bp2_item, tuPI1, bpartner2);
	}

	@Test
	public void test_TU_WithoutLU()
	{
		assertLUItem(null, tuPI_withoutLU, bpartner_NULL);
	}

	@Test
	public void test_TU_assignedToMultipleLUs()
	{
		final I_M_HU_PI luPI1 = helper.createHUDefinition("LU1", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		final I_M_HU_PI luPI2 = helper.createHUDefinition("LU2", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		final I_M_HU_PI tuPI1 = helper.createHUDefinition("TU1_MultipleLUs", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item lu1_tu1_item = helper.createHU_PI_Item_IncludedHU(luPI1, tuPI1, new BigDecimal("10"), bpartner_NULL);
		final I_M_HU_PI_Item lu2_tu1_item = helper.createHU_PI_Item_IncludedHU(luPI2, tuPI1, new BigDecimal("10"), bpartner_NULL);

		// We are expecting the first one created because there is no DefaultLU
		assertLUItem(lu1_tu1_item, tuPI1, bpartner_NULL);

		// Set the LU2 as default and test again
		luPI2.setIsDefaultLU(true);
		InterfaceWrapperHelper.save(luPI2);
		assertLUItem(lu2_tu1_item, tuPI1, bpartner_NULL);
	}

	private void assertLUItem(final I_M_HU_PI_Item expectedLUItem, final I_M_HU_PI tuPI, final BPartnerId bpartnerId)
	{
		final I_M_HU_PI_Item actualLUItem = dao.retrieveDefaultParentPIItem(tuPI, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit, bpartnerId);
		final String message = "Invalid LU Item"
				+ "\n tuPI=" + tuPI
				+ "\n bpartner=" + bpartnerId
				+ "\n expected LU PI item=" + expectedLUItem
				+ "\n actual LU PI item=" + actualLUItem
				+ "\n\n";
		Assertions.assertEquals(expectedLUItem, actualLUItem, message);
	}
}
