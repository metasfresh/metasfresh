package de.metas.inventory.mobileui.launchers;

import de.metas.inventory.InventoryId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.api.Params;

@Value
@Builder
public class InventoryWFProcessStartParams
{
	@NonNull InventoryId inventoryId;

	private static final String PARAM_inventoryId = "inventoryId";

	public Params toParams()
	{
		return Params.builder()
				.value(PARAM_inventoryId, inventoryId.getRepoId())
				.build();
	}

	public static InventoryWFProcessStartParams ofParams(final Params params)
	{
		try
		{
			return builder()
					.inventoryId(params.getParameterAsIdNotNull(PARAM_inventoryId, InventoryId.class))
					.build();
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Invalid params: " + params, ex);
		}
	}

	public static InventoryWFProcessStartParams of(@NonNull final InventoryJobReference jobRef)
	{
		return builder()
				.inventoryId(jobRef.getInventoryId())
				.build();
	}

}
