package de.metas.handlingunits.picking.job.service;

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
	@Nullable String packId;
}
