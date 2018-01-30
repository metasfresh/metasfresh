package de.metas.vertical.pharma.vendor.gateway.mvs3.availability;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.compiere.Adempiere;

import de.metas.vertical.pharma.vendor.gateway.mvs3.MSV3Util;
import de.metas.vertical.pharma.vendor.gateway.mvs3.common.SubstitutionSaver;
import de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_VerfuegbarkeitAnteil;
import de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort;
import de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_VerfuegbarkeitsantwortArtikel;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.VerfuegbarkeitAnteil;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.VerfuegbarkeitSubstitution;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.VerfuegbarkeitsanfrageEinzelneAntwort;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.VerfuegbarkeitsantwortArtikel;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.vendor.gateway.mvs3
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class AvailabilityPersistanceMapper
{
	public static AvailabilityPersistanceMapper newInstace()
	{
		return new AvailabilityPersistanceMapper();
	}

	private final Map<VerfuegbarkeitAnteil, Integer> verfuegbarkeitAnteil2DataRecordId = new HashMap<>();

	private AvailabilityPersistanceMapper()
	{
	}

	public void store(@NonNull final VerfuegbarkeitsanfrageEinzelneAntwort verfuegbarkeitsanfrageEinzelneAntwort)
	{
		final I_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort verfuegbarkeitsanfrageEinzelneAntwortRecord = //
				createVerfuegbarkeitsanfrageEinzelneAntwortRecord(verfuegbarkeitsanfrageEinzelneAntwort);
		save(verfuegbarkeitsanfrageEinzelneAntwortRecord);

		final List<VerfuegbarkeitsantwortArtikel> artikel = verfuegbarkeitsanfrageEinzelneAntwort.getArtikel();
		for (final VerfuegbarkeitsantwortArtikel singleArticle : artikel)
		{
			final I_MSV3_VerfuegbarkeitsantwortArtikel verfuegbarkeitsantwortArtikelRecord = createVerfuegbarkeitsantwortArtikelRecord(singleArticle);

			verfuegbarkeitsantwortArtikelRecord.setMSV3_VerfuegbarkeitsanfrageEinzelneAntwort(verfuegbarkeitsanfrageEinzelneAntwortRecord);
			save(verfuegbarkeitsantwortArtikelRecord);

			final List<VerfuegbarkeitAnteil> anteile = singleArticle.getAnteile();
			for (final VerfuegbarkeitAnteil anteil : anteile)
			{
				final I_MSV3_VerfuegbarkeitAnteil verfuegbarkeitAnteilRecord = createVerfuegbarkeitAnteilRecord(anteil);

				verfuegbarkeitAnteilRecord.setMSV3_VerfuegbarkeitsantwortArtikel(verfuegbarkeitsantwortArtikelRecord);
				save(verfuegbarkeitAnteilRecord);
				verfuegbarkeitAnteil2DataRecordId.put(anteil, verfuegbarkeitAnteilRecord.getMSV3_VerfuegbarkeitAnteil_ID());
			}
		}
	}

	private I_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort createVerfuegbarkeitsanfrageEinzelneAntwortRecord(
			@NonNull final VerfuegbarkeitsanfrageEinzelneAntwort verfuegbarkeitsanfrageEinzelneAntwort)
	{
		final I_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort verfuegbarkeitsanfrageEinzelneAntwortRecord = //
				newInstance(I_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort.class);
		verfuegbarkeitsanfrageEinzelneAntwortRecord.setMSV3_Id(verfuegbarkeitsanfrageEinzelneAntwort.getId());
		verfuegbarkeitsanfrageEinzelneAntwortRecord.setMSV3_VerfuegbarkeitTyp(verfuegbarkeitsanfrageEinzelneAntwort.getRTyp().toString());
		return verfuegbarkeitsanfrageEinzelneAntwortRecord;
	}

	private I_MSV3_VerfuegbarkeitsantwortArtikel createVerfuegbarkeitsantwortArtikelRecord(@NonNull final VerfuegbarkeitsantwortArtikel singleArticle)
	{
		final I_MSV3_VerfuegbarkeitsantwortArtikel verfuegbarkeitsantwortArtikelRecord = newInstance(I_MSV3_VerfuegbarkeitsantwortArtikel.class);
		verfuegbarkeitsantwortArtikelRecord.setMSV3_AnfrageMenge(singleArticle.getAnfrageMenge());
		verfuegbarkeitsantwortArtikelRecord.setMSV3_AnfragePzn(Long.toString(singleArticle.getAnfragePzn()));

		final SubstitutionSaver substitutionPersistanceMapper = Adempiere.getBean(SubstitutionSaver.class);

		final VerfuegbarkeitSubstitution substitution = singleArticle.getSubstitution();
		verfuegbarkeitsantwortArtikelRecord.setMSV3_VerfuegbarkeitSubstitution(substitutionPersistanceMapper.storeSubstitutionOrNull(substitution));
		return verfuegbarkeitsantwortArtikelRecord;
	}

	private I_MSV3_VerfuegbarkeitAnteil createVerfuegbarkeitAnteilRecord(@NonNull final VerfuegbarkeitAnteil anteil)
	{
		final I_MSV3_VerfuegbarkeitAnteil verfuegbarkeitAnteilRecord = newInstance(I_MSV3_VerfuegbarkeitAnteil.class);
		verfuegbarkeitAnteilRecord.setMSV3_Grund(anteil.getGrund().toString());
		verfuegbarkeitAnteilRecord.setMSV3_Lieferzeitpunkt(MSV3Util.toTimestampOrNull(anteil.getLieferzeitpunkt()));
		verfuegbarkeitAnteilRecord.setMSV3_Menge(anteil.getMenge());
		verfuegbarkeitAnteilRecord.setMSV3_Tourabweichung(anteil.isTourabweichung());
		verfuegbarkeitAnteilRecord.setMSV3_Typ(anteil.getTyp().toString());
		return verfuegbarkeitAnteilRecord;
	}

	public int getVerfuegbarkeitAnteilDataRecordId(@NonNull final VerfuegbarkeitAnteil verfuegbarkeitAnteil)
	{
		return verfuegbarkeitAnteil2DataRecordId.get(verfuegbarkeitAnteil);
	}
}
