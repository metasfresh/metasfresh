package de.metas.hu_consolidation.mobile.job;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.document.engine.DocStatus;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;

@RequiredArgsConstructor
public enum HUConsolidationJobStatus
{
	Drafted(DocStatus.Drafted),
	Completed(DocStatus.Completed),
	Voided(DocStatus.Voided),
	;

	private final DocStatus docStatus;

	private static final ImmutableMap<DocStatus, HUConsolidationJobStatus> byDocStatus = Maps.uniqueIndex(ImmutableList.copyOf(values()), HUConsolidationJobStatus::getDocStatus);

	public static HUConsolidationJobStatus ofCode(@NonNull final String docStatusCode)
	{
		final HUConsolidationJobStatus jobStatus = byDocStatus.get(DocStatus.ofCode(docStatusCode));
		if (jobStatus == null)
		{
			throw new AdempiereException("No " + HUConsolidationJobStatus.class + " found for " + docStatusCode);
		}
		return jobStatus;
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
