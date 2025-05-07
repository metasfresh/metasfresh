package de.metas.frontend_testing.expectations.request;

import de.metas.frontend_testing.masterdata.Identifier;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonHUExpectation
{
	@NonNull Match match;

	@Nullable List<Storage> storages;

	//
	//
	//

	@Value
	@Builder
	@Jacksonized
	public static class Match
	{
		@Nullable String byQRCode;
	}

	//
	//
	//

	@Value
	@Builder
	@Jacksonized
	public static class Storage
	{
		@NonNull Identifier product;
		@NonNull QtyAndUOMString qty;
	}

}
