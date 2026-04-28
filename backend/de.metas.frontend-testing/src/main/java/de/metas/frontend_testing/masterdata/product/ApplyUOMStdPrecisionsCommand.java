package de.metas.frontend_testing.masterdata.product;

import de.metas.frontend_testing.masterdata.uom.JsonUOMRequest;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Map;

import static org.adempiere.model.InterfaceWrapperHelper.save;

/**
 * Always sets UOM StdPrecision for each UOM referenced by products to ensure test isolation
 * (otherwise a previous test's precision change would leak into subsequent tests).
 * <p>
 * Explicit precisions from the {@code uoms} section take priority.
 * UOMs referenced by products but not declared in {@code uoms} get a default (0 for PCE/MJ, 3 for KGM, 2 for the rest).
 */
public class ApplyUOMStdPrecisionsCommand
{
	private static final String DEFAULT_UOM_CODE = "PCE";

	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@Nullable private final Map<String, JsonUOMRequest> uoms;
	@Nullable private final Map<String, JsonCreateProductRequest> products;

	private ApplyUOMStdPrecisionsCommand(
			@Nullable final Map<String, JsonUOMRequest> uoms,
			@Nullable final Map<String, JsonCreateProductRequest> products)
	{
		this.uoms = uoms;
		this.products = products;
	}

	public static ApplyUOMStdPrecisionsCommand of(
			@Nullable final Map<String, JsonUOMRequest> uoms,
			@Nullable final Map<String, JsonCreateProductRequest> products)
	{
		return new ApplyUOMStdPrecisionsCommand(uoms, products);
	}

	public void execute()
	{
		final HashSet<String> allUomCodes = collectAllUomCodes();
		if (allUomCodes.isEmpty())
		{
			return;
		}

		for (final String uomCode : allUomCodes)
		{
			final int precision = getExplicitPrecision(uomCode) != null
					? getExplicitPrecision(uomCode)
					: getDefaultStdPrecision(uomCode);

			final UomId uomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(uomCode));
			final I_C_UOM uomRecord = uomDAO.getById(uomId);
			uomRecord.setStdPrecision(precision);
			save(uomRecord);
		}
	}

	@Nullable
	private Integer getExplicitPrecision(@NonNull final String uomCode)
	{
		if (uoms == null)
		{
			return null;
		}
		final JsonUOMRequest uomRequest = uoms.get(uomCode);
		return uomRequest != null ? uomRequest.getPrecision() : null;
	}

	private HashSet<String> collectAllUomCodes()
	{
		final HashSet<String> result = new HashSet<>();

		// From explicit uoms section
		if (uoms != null)
		{
			result.addAll(uoms.keySet());
		}

		// From products
		if (products != null)
		{
			for (final JsonCreateProductRequest productRequest : products.values())
			{
				result.add(productRequest.getUom() != null ? productRequest.getUom().getCode() : DEFAULT_UOM_CODE);

				final java.util.List<JsonCreateProductRequest.UOMConversion> conversions = productRequest.getUomConversions();
				if (conversions != null)
				{
					for (final JsonCreateProductRequest.UOMConversion conv : conversions)
					{
						result.add(conv.getFrom().getCode());
						result.add(conv.getTo().getCode());
					}
				}
			}
		}

		return result;
	}

	private static int getDefaultStdPrecision(@NonNull final String x12de355)
	{
		switch (x12de355)
		{
			case "PCE": // Stück
			case "MJ":  // Minute
				return 0;
			case "KGM": // Kilogramm
				return 3;
			default:
				return 2;
		}
	}
}
