package de.metas.report;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@EqualsAndHashCode
@ToString
public class DocOutboundConfig
{
	@NonNull DocOutboundConfigId id;
	@NonNull ClientId clientId;
	@NonNull AdTableId tableId;
	@Nullable PrintFormatId printFormatId;
	@NonNull private final ArrayList<DocOutboundConfigCC> lines;

	@Builder(toBuilder = true)
	private DocOutboundConfig(
			@NonNull final DocOutboundConfigId id,
			@NonNull final ClientId clientId,
			@NonNull final AdTableId tableId,
			@Nullable final PrintFormatId printFormatId,
			@Nullable final List<DocOutboundConfigCC> lines)
	{
		this.id = id;
		this.clientId = clientId;
		this.tableId = tableId;
		this.printFormatId = printFormatId;
		this.lines = lines != null ? new ArrayList<>(lines) : new ArrayList<>();
	}

	public ImmutableList<DocOutboundConfigCC> getLines()
	{
		return ImmutableList.copyOf(lines);
	}

	public Optional<DocOutboundConfigCC> getCCByPrintFormatId(@NonNull PrintFormatId printFormatId)
	{
		return getLines()
				.stream()
				.filter(configCC -> printFormatId.equals(configCC.getPrintFormatId()))
				.findFirst();
	}
}
