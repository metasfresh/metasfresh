package de.metas.frontend_testing.expectations;

import de.metas.frontend_testing.expectations.request.JsonManufacturingExpectation;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.logging.LogManager;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import org.eevolution.api.PPOrderId;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static de.metas.frontend_testing.expectations.assertions.Assertions.assertThat;
import static de.metas.frontend_testing.expectations.assertions.Assertions.softly;
import static de.metas.frontend_testing.expectations.assertions.Assertions.softlyPutContext;

@Builder
public class AssertManufacturingExpectationsCommand
{
	@NonNull private static final Logger logger = LogManager.getLogger(AssertPickingExpectationsCommand.class);
	@NonNull private final AssertExpectationsCommandServices services;
	@NonNull private final MasterdataContext context;

	@NonNull private final Map<String, JsonManufacturingExpectation> expectations;

	void execute()
	{
		for (final Map.Entry<String, JsonManufacturingExpectation> expectationEntry : expectations.entrySet())
		{
			final String matcherStr = expectationEntry.getKey();
			final JsonManufacturingExpectation expectation = expectationEntry.getValue();
			assertManufacturing(matcherStr, expectation);
		}

	}

	private void assertManufacturing(final String matcherStr, final JsonManufacturingExpectation expectation)
	{
		final PPOrderId ppOrderId = getPPOrderIdByMatcherString(matcherStr);

		if (expectation.getReceivedHUs() != null)
		{
			final ArrayList<I_PP_Order_Qty> actuals = new ArrayList<>(services.getPPOrderQtyForFinishedGoodsReceive(ppOrderId));
			assertReceivedHUs(expectation.getReceivedHUs(), actuals);
		}
	}

	private PPOrderId getPPOrderIdByMatcherString(@NotNull final String matcherStr)
	{
		final Identifier ppOrderIdentifier = Identifier.ofString(matcherStr);
		return context.getOptionalId(ppOrderIdentifier, PPOrderId.class)
				.orElseGet(() -> ppOrderIdentifier.toId(PPOrderId.class));
	}

	public void assertReceivedHUs(final List<JsonManufacturingExpectation.ReceivedHU> expectations, final ArrayList<I_PP_Order_Qty> actuals)
	{
		assertThat(actuals).hasSameSize(expectations);

		// To have a predictable order
		// NOTE: in some cases (don't know why) the PP_Order_Qty_ID is not respecting the order they were actually received ?!
		// so, as a workaround we are ordering them in order the HUs were created.
		actuals.sort(Comparator.comparing(I_PP_Order_Qty::getM_HU_ID));

		softly(() -> {
			softlyPutContext("expectations", expectations);
			softlyPutContext("actuals", actuals);
			for (int i = 0; i < expectations.size(); i++)
			{
				softlyPutContext("index", i);

				final JsonManufacturingExpectation.ReceivedHU expectation = expectations.get(i);
				final I_PP_Order_Qty actual = actuals.get(i);
				assertReceivedHU(expectation, actual);
			}
		});
	}

	private void assertReceivedHU(final JsonManufacturingExpectation.ReceivedHU expectation, final I_PP_Order_Qty actual)
	{
		softly(() -> {
			softlyPutContext("expectation", expectation);
			softlyPutContext("actual", actual);

			if (expectation.getLu() != null)
			{
				final HuId luId = getLuId(actual);
				context.putSameOrMissingId("lu", expectation.getLu(), luId, HuId.class);
			}
			if (expectation.getTu() != null)
			{
				final HuId tuId = getTuId(actual);
				context.putSameOrMissingId("tu", expectation.getTu(), tuId, HuId.class);
			}

			if (expectation.getQty() != null)
			{
				assertThat(getQty(actual)).as("qty").isEqualTo(expectation.getQty().toQuantity());
			}
		});
	}

	private HuId getLuId(final I_PP_Order_Qty record)
	{
		final HuId huId = HuId.ofRepoId(record.getM_HU_ID());
		return getLuId(huId);
	}

	@Nullable
	private HuId getLuId(final HuId huId)
	{
		final IHandlingUnitsBL handlingUnitsBL = services.handlingUnitsBL;
		final I_M_HU hu = handlingUnitsBL.getById(huId);
		final I_M_HU lu = handlingUnitsBL.getLoadingUnitHU(hu);
		return lu != null ? HuId.ofRepoId(lu.getM_HU_ID()) : null;
	}

	private HuId getTuId(final I_PP_Order_Qty record)
	{
		final HuId huId = HuId.ofRepoId(record.getM_HU_ID());
		return getTuId(huId);
	}

	@Nullable
	private HuId getTuId(final HuId huId)
	{
		final IHandlingUnitsBL handlingUnitsBL = services.handlingUnitsBL;
		final I_M_HU hu = handlingUnitsBL.getById(huId);
		final I_M_HU tu = handlingUnitsBL.getTransportUnitHU(hu);
		return tu != null ? HuId.ofRepoId(tu.getM_HU_ID()) : null;
	}

	private Quantity getQty(final I_PP_Order_Qty record)
	{
		return Quantitys.of(record.getQty(), UomId.ofRepoId(record.getC_UOM_ID()));
	}
}
