package org.adempiere.mm.attributes.api;

import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Stream;

public interface IAttributeSetInstanceDAO extends ISingletonService
{
	I_M_AttributeSetInstance getRecordById(@NonNull AttributeSetInstanceId attributeSetInstanceId);

	List<I_M_AttributeInstance> retrieveAttributeInstancesByAsiIds(@NonNull Set<AttributeSetInstanceId> asiIds);

	Stream<I_M_AttributeInstance> streamAttributeInstances(
			@NonNull AttributeSetInstanceId asiId,
			@NonNull Set<AttributeId> attributeIds);

	/**
	 * Creates a new {@link I_M_AttributeInstance}.
	 * NOTE: it is not saving it
	 */
	I_M_AttributeInstance createNewAttributeInstance(
			Properties ctx,
			I_M_AttributeSetInstance asi,
			@NonNull AttributeId attributeId,
			String trxName);

	/**
	 * Retrieves all attribute instances associated with an attribute instance set.
	 *
	 * @param attributeSetInstance may be {@code null}, in which case an empty list is returned.
	 * @return a list of the given {@code attributeSetInstance}'s attribute instances, ordered by M_AttributeUse.SeqNo
	 */
	List<I_M_AttributeInstance> retrieveAttributeInstances(@Nullable I_M_AttributeSetInstance attributeSetInstance);

	List<I_M_AttributeInstance> retrieveAttributeInstances(@NonNull AttributeSetInstanceId asiId);

	/**
	 * @param attributeSetInstanceId may be {@code null} or "none". In that case, always {@code null} is returned.
	 * @return the attribute instance with the given {@code attributeSetInstanceId} and {@code attributeId}, or {@code null}.
	 */
	I_M_AttributeInstance retrieveAttributeInstance(AttributeSetInstanceId attributeSetInstanceId, AttributeId attributeId);

	void save(@NonNull I_M_AttributeSetInstance asi);

	void save(@NonNull I_M_AttributeInstance ai);

	/**
	 * @return attributeIds ordered by M_AttributeUse.SeqNo
	 */
	Set<AttributeId> getAttributeIdsByAttributeSetInstanceId(@NonNull AttributeSetInstanceId attributeSetInstanceId);
}
