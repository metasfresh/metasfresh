package de.metas.image;

import de.metas.cache.CCache;
import de.metas.organization.ClientAndOrgId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Image;
import org.compiere.util.MimeType;
import org.springframework.stereotype.Repository;

@Repository
public class AdImageRepository
{
	private final CCache<AdImageId, AdImage> cache = CCache.<AdImageId, AdImage>builder()
			.cacheMapType(CCache.CacheMapType.LRU)
			.initialCapacity(1000)
			.tableName(I_AD_Image.Table_Name)
			.build();

	public AdImage getById(@NonNull final AdImageId adImageId)
	{
		return cache.getOrLoad(adImageId, this::retrieveById);
	}

	private AdImage retrieveById(@NonNull final AdImageId adImageId)
	{
		final I_AD_Image record = InterfaceWrapperHelper.load(adImageId, I_AD_Image.class);
		if (record == null)
		{
			throw new AdempiereException("No AD_Image found for " + adImageId);
		}

		return fromRecord(record);
	}

	public static AdImage fromRecord(@NonNull I_AD_Image record)
	{
		final String filename = record.getName();

		return AdImage.builder()
				.id(AdImageId.ofRepoId(record.getAD_Image_ID()))
				.lastModified(record.getUpdated().toInstant())
				.filename(filename)
				.contentType(MimeType.getMimeType(filename))
				.data(record.getBinaryData())
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(record.getAD_Client_ID(), record.getAD_Org_ID()))
				.build();
	}
}
