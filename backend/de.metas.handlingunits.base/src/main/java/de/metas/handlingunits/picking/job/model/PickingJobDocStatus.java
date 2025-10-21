package de.metas.handlingunits.picking.job.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.document.engine.DocStatus;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

@AllArgsConstructor
public enum PickingJobDocStatus
{
	Drafted(DocStatus.Drafted),
	Completed(DocStatus.Completed),
	Voided(DocStatus.Voided),
	;

	private final DocStatus docStatus;

	private static final ImmutableMap<DocStatus, PickingJobDocStatus> byDocStatus = Maps.uniqueIndex(ImmutableList.copyOf(values()), PickingJobDocStatus::getDocStatus);

	public static PickingJobDocStatus ofCode(@NonNull final String docStatusCode)
	{
		final PickingJobDocStatus pickingJobDocStatus = byDocStatus.get(DocStatus.ofCode(docStatusCode));
		if (pickingJobDocStatus == null)
		{
			throw new AdempiereException("No " + PickingJobDocStatus.class + " found for " + docStatusCode);
		}
		return pickingJobDocStatus;
	}

	public String getCode()
	{
		return docStatus.getCode();
	}

	private DocStatus getDocStatus()
	{
		return docStatus;
	}

	public boolean isProcessed() {return isCompleted() || this.equals(Voided);}

	public boolean isCompleted()
	{
		return this.equals(Completed);
	}
}
