package de.metas.frontend_testing.masterdata.picking_slot;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.picking.api.IPickingSlotDAO;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PickingSlotCreateCommandTest
{
	private MasterdataContext context;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		context = new MasterdataContext();
		context.putIdentifier(Identifier.ofString("L1"), LocatorId.ofRepoId(WarehouseId.ofRepoId(1), 1));
	}

	private JsonPickingSlotCreateResponse create(final String identifier, final String code)
	{
		return PickingSlotCreateCommand.builder()
				.context(context)
				.request(JsonPickingSlotCreateRequest.builder().code(code).build())
				.identifier(Identifier.ofString(identifier))
				.build()
				.execute();
	}

	@Test
	public void suppliedCodeIsUsedAsIs()
	{
		final JsonPickingSlotCreateResponse response = create("slot1", "4018");

		assertThat(response.getCode()).isEqualTo("4018");
		assertThat(Services.get(IPickingSlotDAO.class).getPickingSlotIdAndCaptionByCode("4018")).isPresent();
	}

	@Test
	public void suppliedCodeOfAnExistingSlotIsRejected()
	{
		create("slot1", "4018");

		assertThatThrownBy(() -> create("slot2", "4018"))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("4018");
	}
}
