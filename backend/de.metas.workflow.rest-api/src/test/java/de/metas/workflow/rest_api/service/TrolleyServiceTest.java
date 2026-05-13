package de.metas.workflow.rest_api.service;

import de.metas.scannable_code.ScannedCode;
import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.adempiere.warehouse.qrcode.LocatorQRCode;
import org.adempiere.warehouse.qrcode.resolver.LocatorScannedCodeResolverResult;
import org.adempiere.warehouse.qrcode.resolver.LocatorScannedCodeResolverService;
import org.compiere.model.I_M_Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TrolleyServiceTest
{
	private static final UserId USER_A = UserId.ofRepoId(1001);
	private static final UserId USER_B = UserId.ofRepoId(1002);
	private static final LocatorId LOCATOR_ID = LocatorId.ofRepoId(WarehouseId.ofRepoId(2001), 3001);

	private LocatorScannedCodeResolverService resolver;
	private IWarehouseDAO warehouseDAO;
	private TrolleyService service;

	private ScannedCode scannedCode;
	private LocatorQRCode locatorQRCode;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		// Mock warehouse — getById returns an in-transit warehouse
		warehouseDAO = mock(IWarehouseDAO.class);
		final I_M_Warehouse warehouse = mock(I_M_Warehouse.class);
		when(warehouse.isInTransit()).thenReturn(true);
		when(warehouseDAO.getById(LOCATOR_ID.getWarehouseId())).thenReturn(warehouse);
		Services.registerService(IWarehouseDAO.class, warehouseDAO);

		// Mock resolver — resolves a fixed ScannedCode to a fixed LocatorQRCode
		resolver = mock(LocatorScannedCodeResolverService.class);
		scannedCode = ScannedCode.ofString("CART-1");
		locatorQRCode = LocatorQRCode.builder()
				.locatorId(LOCATOR_ID)
				.caption("CART-1")
				.build();
		final LocatorScannedCodeResolverResult resolved = LocatorScannedCodeResolverResult.found(locatorQRCode);
		when(resolver.resolve(scannedCode)).thenReturn(resolved);

		service = new TrolleyService(resolver);
	}

	@Test
	void setCurrent_throwsTrolleyAlreadyAssignedException_whenAnotherUserAlreadyHoldsTheTrolley()
	{
		service.setCurrent(USER_A, scannedCode);
		assertThatThrownBy(() -> service.setCurrent(USER_B, scannedCode))
				.isInstanceOf(TrolleyAlreadyAssignedException.class);
	}

	@Test
	void setCurrent_doesNotBumpOriginalHolder_whenSecondUserScansSameTrolley()
	{
		service.setCurrent(USER_A, scannedCode);
		assertThatThrownBy(() -> service.setCurrent(USER_B, scannedCode))
				.isInstanceOf(TrolleyAlreadyAssignedException.class);
		assertThat(service.getCurrent(USER_A)).isPresent();
		assertThat(service.getCurrent(USER_B)).isEmpty();
	}

	@Test
	void clearCurrent_removesUserFromAssignmentMap()
	{
		service.setCurrent(USER_A, scannedCode);
		service.clearCurrent(USER_A);
		assertThat(service.getCurrent(USER_A)).isEmpty();
		// The trolley must now be free — USER_B can claim it without conflict.
		assertThatCode(() -> service.setCurrent(USER_B, scannedCode)).doesNotThrowAnyException();
	}

	@Test
	void clearCurrent_isIdempotent()
	{
		// user has no trolley — must not throw
		service.clearCurrent(USER_A);
		assertThat(service.getCurrent(USER_A)).isEmpty();
	}
}
