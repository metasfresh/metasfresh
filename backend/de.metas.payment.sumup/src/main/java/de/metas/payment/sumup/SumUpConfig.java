package de.metas.payment.sumup;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@EqualsAndHashCode
public class SumUpConfig
{
	@NonNull @Getter private final SumUpConfigId id;
	@Getter private final boolean isActive;
	@NonNull @Getter private final String apiKey;
	@NonNull @Getter private final SumUpMerchantCode merchantCode;
	@Nullable @Getter private final SumUpCardReaderExternalId defaultCardReaderExternalId;
	@NonNull @Getter private final List<SumUpCardReader> cardReaders;
	@NonNull private final ImmutableMap<SumUpCardReaderExternalId, SumUpCardReader> cardReadersByExternalId;

	@Builder(toBuilder = true)
	private SumUpConfig(
			@NonNull final SumUpConfigId id,
			final boolean isActive,
			@NonNull final String apiKey,
			@NonNull final SumUpMerchantCode merchantCode,
			@NonNull final List<SumUpCardReader> cardReaders,
			@Nullable final SumUpCardReaderExternalId defaultCardReaderExternalId)
	{
		this.id = id;
		this.isActive = isActive;
		this.apiKey = apiKey;
		this.merchantCode = merchantCode;
		this.cardReaders = ImmutableList.copyOf(cardReaders);
		this.cardReadersByExternalId = Maps.uniqueIndex(cardReaders, SumUpCardReader::getExternalId);
		this.defaultCardReaderExternalId = determineDefaultCardReaderExternalId(defaultCardReaderExternalId, this.cardReadersByExternalId);
	}

	private static SumUpCardReaderExternalId determineDefaultCardReaderExternalId(
			@Nullable final SumUpCardReaderExternalId suggestedDefaultCardReaderExternalId,
			@NonNull Map<SumUpCardReaderExternalId, SumUpCardReader> cardReadersByExternalId)
	{
		SumUpCardReaderExternalId defaultCardReaderExternalId = suggestedDefaultCardReaderExternalId;

		if (defaultCardReaderExternalId != null)
		{
			final SumUpCardReader defaultCardReader = cardReadersByExternalId.get(defaultCardReaderExternalId);
			if (defaultCardReader == null || !defaultCardReader.isActive())
			{
				defaultCardReaderExternalId = null;
			}
		}

		if (defaultCardReaderExternalId == null && !cardReadersByExternalId.isEmpty())
		{
			final List<SumUpCardReader> activeCardReaders = cardReadersByExternalId.values().stream()
					.filter(SumUpCardReader::isActive)
					.collect(Collectors.toList());
			if (activeCardReaders.size() == 1)
			{
				defaultCardReaderExternalId = activeCardReaders.get(0).getExternalId();
			}
		}

		return defaultCardReaderExternalId;
	}

	@NonNull
	public SumUpCardReaderExternalId getDefaultCardReaderExternalIdNotNull()
	{
		if (defaultCardReaderExternalId == null)
		{
			throw new AdempiereException("No default card reader was configured for " + this);
		}
		return defaultCardReaderExternalId;
	}
}
