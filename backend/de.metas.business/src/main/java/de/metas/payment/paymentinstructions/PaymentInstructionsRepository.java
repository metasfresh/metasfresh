package de.metas.payment.paymentinstructions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_PaymentInstruction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PaymentInstructionsRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, PaymentInstructionsMap> cache = CCache.<Integer, PaymentInstructionsMap>builder()
			.tableName(I_C_PaymentInstruction.Table_Name)
			.build();

	public PaymentInstructions getById(@NonNull PaymentInstructionsId id) {return getMap().getById(id);}

	private PaymentInstructionsMap getMap() {return cache.getOrLoad(0, this::retrieveMap);}

	private PaymentInstructionsMap retrieveMap()
	{
		final ImmutableList<PaymentInstructions> list = queryBL
				.createQueryBuilder(I_C_PaymentInstruction.class)
				.create()
				.stream()
				.map(PaymentInstructionsRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());

		return new PaymentInstructionsMap(list);
	}

	private static PaymentInstructions fromRecord(final I_C_PaymentInstruction record)
	{
		return PaymentInstructions.builder()
				.id(PaymentInstructionsId.ofRepoId(record.getC_PaymentInstruction_ID()))
				.name(StringUtils.trimBlankToNull(record.getName()))
				.active(record.isActive())
				.build();
	}

	//
	//
	//

	private static class PaymentInstructionsMap
	{
		private final ImmutableMap<PaymentInstructionsId, PaymentInstructions> byId;

		PaymentInstructionsMap(List<PaymentInstructions> list)
		{
			this.byId = Maps.uniqueIndex(list, PaymentInstructions::getId);
		}

		public PaymentInstructions getById(final PaymentInstructionsId id)
		{
			final PaymentInstructions paymentInstructions = byId.get(id);
			if (paymentInstructions == null)
			{
				throw new AdempiereException("No Payment Instructions found for " + id);
			}
			return paymentInstructions;
		}
	}
}
