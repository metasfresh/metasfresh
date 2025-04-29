package de.metas.material.event.supplyrequired;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;

import static de.metas.material.event.MaterialEventConstants.MD_CANDIDATE_TABLE_NAME;

@Value
@Builder
@Jacksonized
public class NoSupplyAdviceEvent implements MaterialEvent
{
	public static final String TYPE = "NoSupplyAdviceEvent";

	@NonNull SupplyRequiredDescriptor supplyRequiredDescriptor;

	public static NoSupplyAdviceEvent of(@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor)
	{
		return NoSupplyAdviceEvent.builder().supplyRequiredDescriptor(supplyRequiredDescriptor).build();
	}

	@JsonIgnore
	@Override
	public EventDescriptor getEventDescriptor()
	{
		return supplyRequiredDescriptor.getEventDescriptor();
	}

	@JsonIgnore
	public int getSupplyCandidateId() {return supplyRequiredDescriptor.getSupplyCandidateId();}

	@Nullable
	@Override
	public TableRecordReference getSourceTableReference()
	{
		return TableRecordReference.ofNullable(MD_CANDIDATE_TABLE_NAME, supplyRequiredDescriptor.getPpOrderCandidateId());
	}

	@Override
	public String getEventName() {return TYPE;}
}
