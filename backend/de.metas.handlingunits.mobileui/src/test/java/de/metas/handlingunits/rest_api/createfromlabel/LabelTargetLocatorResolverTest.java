package de.metas.handlingunits.rest_api.createfromlabel;

import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.workplace.WorkplaceId;
import de.metas.workplace.WorkplaceRepository;
import de.metas.workplace.WorkplaceService;
import de.metas.workplace.WorkplaceUserAssignRepository;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_C_Workplace;
import org.compiere.model.I_M_Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.annotation.Nullable;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Only the location-rule branches the handheld fixtures cannot set up (no organisation-warehouse option in the frontend-testing
 * masterdata); the workplace legs are proven by the HU Manager Playwright spec.
 */
class LabelTargetLocatorResolverTest
{
	private static final UserId userId = UserId.ofRepoId(1_000_001);
	private static final ClientId clientId = ClientId.ofRepoId(1_000_020);
	private static final OrgId orgId = OrgId.ofRepoId(1_000_010);

	private WorkplaceService workplaceService;
	private LabelTargetLocatorResolver resolver;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		workplaceService = new WorkplaceService(new WorkplaceRepository(), new WorkplaceUserAssignRepository());
		resolver = new LabelTargetLocatorResolver(workplaceService, Services.get(IWarehouseDAO.class), Services.get(IOrgDAO.class));
	}

	private static WarehouseId newWarehouseId(final int repoId) {return WarehouseId.ofRepoId(repoId);}

	private static LocatorId newLocator(@NonNull final WarehouseId warehouseId, final String value, final boolean isDefault, final boolean isActive)
	{
		final I_M_Locator record = InterfaceWrapperHelper.newInstance(I_M_Locator.class);
		InterfaceWrapperHelper.setValue(record, I_M_Locator.COLUMNNAME_AD_Client_ID, clientId.getRepoId());
		record.setM_Warehouse_ID(warehouseId.getRepoId());
		record.setValue(value);
		record.setIsDefault(isDefault);
		record.setIsActive(isActive);
		InterfaceWrapperHelper.saveRecord(record);
		return LocatorId.ofRepoId(warehouseId, record.getM_Locator_ID());
	}

	private static void newOrgInfo(@NonNull final OrgId orgId, @Nullable final WarehouseId warehouseId)
	{
		final I_AD_OrgInfo record = InterfaceWrapperHelper.newInstance(I_AD_OrgInfo.class);
		record.setAD_Org_ID(orgId.getRepoId());
		record.setM_Warehouse_ID(WarehouseId.toRepoId(warehouseId));
		InterfaceWrapperHelper.saveRecord(record);
	}

	private void assignWorkplace(@NonNull final WarehouseId warehouseId, @Nullable final LocatorId pickFromLocatorId)
	{
		final I_C_Workplace record = InterfaceWrapperHelper.newInstance(I_C_Workplace.class);
		record.setName("wp");
		record.setM_Warehouse_ID(warehouseId.getRepoId());
		record.setPickFrom_Locator_ID(LocatorId.toRepoId(pickFromLocatorId));
		InterfaceWrapperHelper.saveRecord(record);
		workplaceService.assignWorkplace(userId, WorkplaceId.ofRepoId(record.getC_Workplace_ID()));
	}

	private static int countLocators()
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_Locator.class).create().count();
	}

	@Test
	void noWorkplace_usesOrgDefaultWarehouseDefaultLocator()
	{
		final WarehouseId orgWarehouse = newWarehouseId(1_000_101);
		newLocator(orgWarehouse, "first", false, true);
		final LocatorId defaultLocator = newLocator(orgWarehouse, "second-default", true, true);
		newOrgInfo(orgId, orgWarehouse);

		assertThat(resolver.resolve(userId, orgId, clientId)).contains(defaultLocator);
	}

	@Test
	void noWorkplace_noOrgWarehouse_noLocator()
	{
		newOrgInfo(orgId, null);

		assertThat(resolver.resolve(userId, orgId, clientId)).isEmpty();
	}

	@Test
	void workplaceWins_overOrgWarehouse()
	{
		final WarehouseId workplaceWarehouse = newWarehouseId(1_000_102);
		final LocatorId pickFrom = newLocator(workplaceWarehouse, "pick-from", false, true);
		assignWorkplace(workplaceWarehouse, pickFrom);

		final WarehouseId orgWarehouse = newWarehouseId(1_000_101);
		newLocator(orgWarehouse, "org-default", true, true);
		newOrgInfo(orgId, orgWarehouse);

		assertThat(resolver.resolve(userId, orgId, clientId)).contains(pickFrom);
	}

	/** failure scenario: an inactive pick-from location is proposed, and the create step then refuses it; the warehouse default is proposed instead */
	@Test
	void workplacePickFromLocatorInactive_fallsBackToWorkplaceWarehouseDefault()
	{
		final WarehouseId workplaceWarehouse = newWarehouseId(1_000_102);
		final LocatorId inactivePickFrom = newLocator(workplaceWarehouse, "pick-from-inactive", false, false);
		final LocatorId defaultLocator = newLocator(workplaceWarehouse, "wp-default", true, true);
		assignWorkplace(workplaceWarehouse, inactivePickFrom);

		assertThat(resolver.resolve(userId, orgId, clientId)).contains(defaultLocator);
	}

	/** failure scenario: a pick-from location of another client is proposed, and the create step then refuses it (client check); the warehouse default is proposed instead */
	@Test
	void workplacePickFromLocatorOfOtherClient_fallsBackToWorkplaceWarehouseDefault()
	{
		final WarehouseId workplaceWarehouse = newWarehouseId(1_000_102);
		final LocatorId otherClientPickFrom = newLocator(workplaceWarehouse, "pick-from-other-client", false, true);
		final I_M_Locator record = InterfaceWrapperHelper.load(otherClientPickFrom.getRepoId(), I_M_Locator.class);
		InterfaceWrapperHelper.setValue(record, I_M_Locator.COLUMNNAME_AD_Client_ID, clientId.getRepoId() + 1);
		InterfaceWrapperHelper.saveRecord(record);
		final LocatorId defaultLocator = newLocator(workplaceWarehouse, "wp-default", true, true);
		assignWorkplace(workplaceWarehouse, otherClientPickFrom);

		assertThat(resolver.resolve(userId, orgId, clientId)).contains(defaultLocator);
	}

	/** failure scenario: the warehouse default location belongs to another client, so the create step would refuse it; it is not proposed (the usable one is) */
	@Test
	void warehouseDefaultLocatorOfOtherClient_notProposed()
	{
		final WarehouseId orgWarehouse = newWarehouseId(1_000_101);
		final LocatorId otherClientDefault = newLocator(orgWarehouse, "default-other-client", true, true);
		final I_M_Locator record = InterfaceWrapperHelper.load(otherClientDefault.getRepoId(), I_M_Locator.class);
		InterfaceWrapperHelper.setValue(record, I_M_Locator.COLUMNNAME_AD_Client_ID, clientId.getRepoId() + 1);
		InterfaceWrapperHelper.saveRecord(record);
		final LocatorId usable = newLocator(orgWarehouse, "usable", false, true);
		newOrgInfo(orgId, orgWarehouse);

		assertThat(resolver.resolve(userId, orgId, clientId)).contains(usable);
	}

	/** failure scenario: the proposal is a read — a warehouse without an active locator must not get one created */
	@Test
	void warehouseWithoutActiveLocator_noLocator_noInsert()
	{
		final WarehouseId orgWarehouse = newWarehouseId(1_000_101);
		newLocator(orgWarehouse, "inactive", true, false);
		newOrgInfo(orgId, orgWarehouse);
		final int locatorsBefore = countLocators();

		assertThat(resolver.resolve(userId, orgId, clientId)).isEmpty();
		assertThat(countLocators()).isEqualTo(locatorsBefore);
	}

	/** failure scenario: session org * (no org warehouse) and an org without an AD_OrgInfo row must give no location, not an exception */
	@ParameterizedTest
	@ValueSource(booleans = { true, false })
	void orgAnyOrWithoutOrgInfo_noLocator(final boolean orgAny)
	{
		// an org warehouse exists, but not for the org asked for
		final WarehouseId orgWarehouse = newWarehouseId(1_000_101);
		newLocator(orgWarehouse, "org-default", true, true);
		newOrgInfo(orgId, orgWarehouse);

		final OrgId asked = orgAny ? OrgId.ANY : OrgId.ofRepoId(1_000_011); // no AD_OrgInfo row for 1_000_011
		assertThat(resolver.resolve(userId, asked, clientId)).isEmpty();
	}
}
