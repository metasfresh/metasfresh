package de.metas.handlingunits.picking.job.service;

import de.metas.handlingunits.picking.job.model.CULabelPackId;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.LocalDate;

@Value
@Builder
public class CULabelData
{
	@Nullable String productNo;
	@Nullable String lotNo;
	@Nullable LocalDate bestBeforeDate;
	@Nullable CULabelPackId packId;
}
