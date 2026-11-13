package de.metas.frontend_testing.masterdata.sales_order;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonSalesOrderCreateRequestTest
{
	private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

	private static final String BASE = "\"warehouse\":\"wh\",\"datePromised\":\"2026-03-21T00:00:00.000+01:00\",\"lines\":[{\"product\":\"P1\",\"qty\":1}]";

	@Test
	public void paymentTerm_isReadFromJson()
	{
		final JsonSalesOrderCreateRequest request = read("{\"bpartner\":\"BP1\",\"paymentTerm\":\"1000\"," + BASE + "}");
		assertThat(request.getPaymentTerm()).isEqualTo("1000");
	}

	@Test
	public void paymentTerm_isOptional()
	{
		assertThat(read("{\"bpartner\":\"BP1\"," + BASE + "}").getPaymentTerm()).isNull();
	}

	private JsonSalesOrderCreateRequest read(final String json)
	{
		try
		{
			return objectMapper.readValue(json, JsonSalesOrderCreateRequest.class);
		}
		catch (final Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}
