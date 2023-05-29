package de.metas.inoutcandidate.document.dimension;

import de.metas.bpartner.BPartnerId;
import de.metas.document.dimension.Dimension;
import de.metas.document.dimension.DimensionFactory;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.product.ProductId;
import de.metas.sectionCode.SectionCodeId;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ShipmentScheduleDimensionFactory implements DimensionFactory<I_M_ShipmentSchedule>
{
	@Override
	public String getHandledTableName()
	{
		return I_M_ShipmentSchedule.Table_Name;
	}

	@Override
	@NonNull
	public Dimension getFromRecord(@NonNull final I_M_ShipmentSchedule record)
	{
		return Dimension.builder()
				.sectionCodeId(SectionCodeId.ofRepoIdOrNull(record.getM_SectionCode_ID()))
				.productId(ProductId.ofRepoIdOrNull(record.getM_Product_ID()))
				.bpartnerId2(BPartnerId.ofRepoIdOrNull(record.getC_BPartner2_ID()))
				.build();
	}

	@Override
	public void updateRecord(@NonNull final I_M_ShipmentSchedule record, @NonNull final Dimension from)
	{
		record.setM_SectionCode_ID(SectionCodeId.toRepoId(from.getSectionCodeId()));
		//record.setM_Product_ID(ProductId.toRepoId(from.getProductId())); // don't override the product
		record.setC_BPartner2_ID(BPartnerId.toRepoId(from.getBpartnerId2()));
	}

	@Override
	public void updateRecordUserElements(@NonNull final I_M_ShipmentSchedule record, @NonNull final Dimension from)
	{
		//no user elements here
	}
}
