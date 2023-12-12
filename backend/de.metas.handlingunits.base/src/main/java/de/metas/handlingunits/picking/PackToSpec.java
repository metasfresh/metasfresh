package de.metas.handlingunits.picking;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import de.metas.common.util.CoalesceUtil;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsId;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * Specification for packing the picked products
 */
@Value
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class PackToSpec
{
	public static final PackToSpec VIRTUAL = new PackToSpec(HuPackingInstructionsId.VIRTUAL, null);

	@Nullable HuPackingInstructionsId genericPackingInstructionsId;
	@Nullable HUPIItemProductId tuPackingInstructionsId;

	public static PackToSpec ofTUPackingInstructionsId(@NonNull final HUPIItemProductId tuPackingInstructionsId)
	{
		if (tuPackingInstructionsId.isVirtualHU())
		{
			return VIRTUAL;
		}
		else
		{
			return new PackToSpec(null, tuPackingInstructionsId);
		}
	}

	public static PackToSpec ofGenericPackingInstructionsId(@NonNull final HuPackingInstructionsId genericPackingInstructionsId)
	{
		if (genericPackingInstructionsId.isVirtual())
		{
			return VIRTUAL;
		}
		else
		{
			return new PackToSpec(genericPackingInstructionsId, null);
		}
	}

	private PackToSpec(
			@Nullable final HuPackingInstructionsId genericPackingInstructionsId,
			@Nullable final HUPIItemProductId tuPackingInstructionsId)
	{
		if (CoalesceUtil.countNotNulls(genericPackingInstructionsId, tuPackingInstructionsId) != 1)
		{
			throw new AdempiereException("Only one of packingInstructionsId or huPIItemProductId shall be specified");
		}

		this.genericPackingInstructionsId = genericPackingInstructionsId;
		this.tuPackingInstructionsId = tuPackingInstructionsId;
	}

	public boolean isVirtual()
	{
		if (tuPackingInstructionsId != null)
		{
			return tuPackingInstructionsId.isVirtualHU();
		}
		else
		{
			return genericPackingInstructionsId != null && genericPackingInstructionsId.isVirtual();
		}
	}

	public interface CaseMapper<T>
	{
		T packToVirtualHU();

		T packToTU(HUPIItemProductId tuPackingInstructionsId);

		T packToGenericHU(HuPackingInstructionsId genericPackingInstructionsId);
	}

	public <T> T map(@NonNull final CaseMapper<T> mapper)
	{
		if (isVirtual())
		{
			return mapper.packToVirtualHU();
		}
		else if (tuPackingInstructionsId != null)
		{
			return mapper.packToTU(tuPackingInstructionsId);
		}
		else
		{
			return mapper.packToGenericHU(Objects.requireNonNull(genericPackingInstructionsId));
		}
	}
}
