package de.metas.ui.web.replenish.repository;

import com.google.common.collect.ImmutableSet;
import de.metas.i18n.AdMessageKey;
import de.metas.material.replenish.ReplenishInfo;
import de.metas.material.replenish.ReplenishInfoRepository;
import de.metas.product.IProductBL;
import de.metas.replenishment.I_M_Material_Needs_Planner_V;
import de.metas.ui.web.replenish.process.MaterialNeedsPlannerRow;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.sql.save.SaveHandler;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.GridTabVO;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class MaterialNeedsRepositorySaver implements SaveHandler
{
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final ReplenishInfoRepository replenishInfoRepository;

	@Override
	public @NonNull Set<String> getHandledTableName() {return ImmutableSet.of(I_M_Material_Needs_Planner_V.Table_Name);}

	@Override
	public boolean isReadonly(@NonNull final GridTabVO gridTabVO) {return false;}

	@Override
	public SaveResult save(@NonNull final Document document)
	{
		replenishInfoRepository.save(toReplenishInfo(document));

		return SaveResult.builder()
				.needsRefresh(true)
				.idNew(document.getDocumentId())
				.build();
	}

	private ReplenishInfo toReplenishInfo(@NotNull final Document document)
	{
		return MaterialNeedsPlannerRow.ofDocument(document).toReplenishInfo();
	}

	@Override
	public void delete(@NonNull final Document document)
	{
		throw new AdempiereException(AdMessageKey.of("AccessCannotDelete"));
	}
}
