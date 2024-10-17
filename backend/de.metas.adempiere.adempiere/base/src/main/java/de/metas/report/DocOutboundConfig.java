package de.metas.report;

import com.google.common.collect.ImmutableList;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

@Value
public class DocOutboundConfig
{
	@NonNull DocOutboundConfigId id;
	@NonNull ClientId clientId;
	@NonNull AdTableId tableId;
	@Nullable PrintFormatId printFormatId;
	@Nullable String ccPath;
	@NonNull ImmutableList<DocOutboundConfigCC> lines;

	@Builder(toBuilder = true)
	private DocOutboundConfig(
			@NonNull final DocOutboundConfigId id,
			@NonNull final ClientId clientId,
			@NonNull final AdTableId tableId,
			@Nullable final PrintFormatId printFormatId, 
			@Nullable final String ccPath,
			@Nullable final List<DocOutboundConfigCC> lines)
	{
		this.id = id;
		this.clientId = clientId;
		this.tableId = tableId;
		this.printFormatId = printFormatId;
		this.ccPath = StringUtils.trimBlankToNull(ccPath);
		this.lines = lines != null ? ImmutableList.copyOf(lines) : ImmutableList.of();
	}

	public Optional<DocOutboundConfigCC> getCCByPrintFormatId(@NonNull PrintFormatId printFormatId)
	{
		return getLines()
				.stream()
				.filter(configCC -> PrintFormatId.equals(printFormatId, configCC.getPrintFormatId()))
				.findFirst();
	}
}
