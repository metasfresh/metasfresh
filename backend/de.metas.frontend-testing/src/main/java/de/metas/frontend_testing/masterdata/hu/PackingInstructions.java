package de.metas.frontend_testing.masterdata.hu;

import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
public class PackingInstructions
{
	@NonNull I_M_HU_PI tuPI;
	@NonNull BigDecimal qtyCUsPerTU;

	@Nullable I_M_HU_PI_Item luPIItem;
	@Nullable QtyTU qtyTUs;

	public BigDecimal getQtyCUs()
	{
		if (luPIItem == null)
		{
			return qtyCUsPerTU;
		}
		else
		{
			final QtyTU qtyTUs = Check.assumeNotNull(this.qtyTUs, "qtyTUs must be set when luPIItem is set: {}", this);
			return qtyCUsPerTU.multiply(qtyTUs.toBigDecimal());
		}
	}
}
