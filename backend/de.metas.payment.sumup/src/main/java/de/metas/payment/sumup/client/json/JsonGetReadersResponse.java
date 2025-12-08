package de.metas.payment.sumup.client.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.metas.payment.sumup.SumUpCardReaderExternalId;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

/**
 * @see <a href="https://developer.sumup.com/api/readers/create-reader">spec</a>
 */
@Value
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonGetReadersResponse
{
	List<Item> items;

	@Value
	@Builder
	@Jacksonized
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Item
	{
		SumUpCardReaderExternalId id;
		String name;
		//...
	}
}
